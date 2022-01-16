package com.github.nagatosingle.configure;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.nagatosingle.service.impl.NagatoAccountServiceImpl;
import com.github.nagatosingle.service.interfaces.AccountService;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class NagatoAuthInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtTokenService tokenService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private NagatoAccountServiceImpl accountService;
	public Map<String, Object> map;
	public LinkedHashMap<String, Object> visitor;
	public LinkedHashMap<String, Object> user;

	@PostConstruct
	public void init() {
		map = yamlParserClassPathBased(new ClassPathResource("uri-matchlist.yaml"));
		visitor = (LinkedHashMap<String, Object>) map.get("visitor");
		user = (LinkedHashMap<String, Object>) map.get("user");
	}

	//基于 URL 的登录拦截
	//直接读配置文件中的url表
	//注解配置文件双重检验
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();

		AtomicReference<ArrayList<String>> allowed = allowedList(request, user, visitor);
//		ArrayList<String> banned = new ArrayList<>();
//		if (request.getHeader("Authorization") != null) {
//			//validate token
//			// if validated then incomingRole = true
//			incomingRole = tokenService.validateToken(request.getHeader("Authorization"));
//		}


//		allowed.get().contains(uri)
		if (verifyUri(uri, allowed)) {
			// 防止重复提交

			return true;
		} else {
//			if (request.getHeader("Authorization") != null)
//				return tokenService.validateToken(request.getHeader("Authorization"));
//			else {
				response.setStatus(401);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				JSONObject obj = new JSONObject();
				obj.put("message", "斯密马赛, 没有权限访问当前api");
				out.print(obj);
				out.flush();
				return false;
		}
	}


	public AtomicReference<ArrayList<String>> allowedList(HttpServletRequest request,
	                                                             LinkedHashMap<String, Object> user,
	                                                             LinkedHashMap<String, Object> visitor) {
		AtomicReference<ArrayList<String>> allowed = new AtomicReference<>();
		boolean incomingRole = false;
		if (request.getHeader("Authorization") != null)
//			incomingRole = true;
			// 如果当前请求携带token, 先验证token合法性. 如果不合法直接返回**空队列**
			if (tokenService.validateToken(request.getHeader("Authorization")))
				incomingRole = true;
			else
				return allowed;
		if (incomingRole) {
			user.forEach((k, v) -> {
				if (k.equals("allowed"))
					allowed.set((ArrayList<String>) v);
			});
		} else {
			visitor.forEach((k, v) -> {
				if (k.equals("allowed"))
					allowed.set((ArrayList<String>) v);
			});
		}

		return allowed;
	}


	private Boolean verifyUri(String uri, AtomicReference<ArrayList<String>> allowed) {
		// 这里就是对如果token验证失败的特判
		if (allowed.get() == null) return false;
		ArrayList<String> list = allowed.get();
		log.info("我list呢??" + String.valueOf(list));
		log.info("uri是什么啊草 : " + uri);
		for (String s : list) {
			if (s.contains("*")) {
				String parsed = s.substring(0, s.indexOf("*") - 1);
				log.info("uri * parsed : " + parsed);
				if (uri.contains(parsed)) {
					return true;
				}
			} else if (StringUtils.equals(s, uri)) {
				log.info("uri passed");
				return true;
			}
		}

		return false;
	}


	public static Map<String, Object> yamlParserClassPathBased(ClassPathResource... classpath) {
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		System.out.println(classpath.length);
		if (classpath.length == 0)
			yaml.setResources(new ClassPathResource("uri-matchlist.yaml"));
		else
			yaml.setResources(classpath);


		return yaml.getObject();
	}
}
