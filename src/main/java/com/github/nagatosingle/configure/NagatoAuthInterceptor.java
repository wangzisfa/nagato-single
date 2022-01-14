package com.github.nagatosingle.configure;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.nagatosingle.utils.jwt.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class NagatoAuthInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtTokenService tokenService;
	private final LinkedHashMap<String, Object> visitor;
	private final LinkedHashMap<String, Object> user;
	public NagatoAuthInterceptor() {
		Map<String, Object> map = yamlParserClassPathBased(new ClassPathResource("uri-matchlist.yaml"));

		this.visitor = (LinkedHashMap<String, Object>) map.get("visitor");
		this.user = (LinkedHashMap<String, Object>) map.get("user");
	}

	//基于 URL 的登录拦截
	//直接读配置文件中的url表
	//注解配置文件双重检验
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
//		Map<String, Object> map = yamlParserClassPathBased(new ClassPathResource("uri-matchlist.yaml"));
//
//		LinkedHashMap<String, Object> visitor = (LinkedHashMap<String, Object>) map.get("visitor");
//		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) map.get("user");
		boolean incomingRole = false;
		AtomicReference<ArrayList<String>> allowed = new AtomicReference<>(new ArrayList<>());
//		ArrayList<String> banned = new ArrayList<>();
//		if (request.getHeader("Authorization") != null) {
//			//validate token
//			// if validated then incomingRole = true
//			incomingRole = tokenService.validateToken(request.getHeader("Authorization"));
//		}
		
		if (request.getHeader("Authorization") != null)
			incomingRole = true;
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

//		allowed.get().contains(uri)
		if (verifyUri(uri, allowed)) {
			return true;
		} else {
			if (request.getHeader("Authorization") != null)
				return tokenService.validateToken(request.getHeader("Authorization"));
			else {
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
	}

	private Boolean verifyUri(String uri, AtomicReference<ArrayList<String>> allowed) {
		ArrayList<String> list = allowed.get();
		for (String s : list) {
			if (s.contains("*")) {
				String parsed = s.substring(0, s.indexOf("*") - 1);
				log.info("uri * parsed : " + parsed);
				if (uri.contains(parsed)) {
					return true;
				}
			} else if (StringUtils.equals(s, uri))
				return true;
		}

		return false;
	}


	private static Map<String, Object> yamlParserClassPathBased(ClassPathResource... classpath) {
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		System.out.println(classpath.length);
		if (classpath.length == 0)
			yaml.setResources(new ClassPathResource("uri-matchlist.yaml"));
		else
			yaml.setResources(classpath);


		return yaml.getObject();
	}
}
