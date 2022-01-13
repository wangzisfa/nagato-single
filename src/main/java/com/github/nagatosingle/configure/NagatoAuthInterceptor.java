package com.github.nagatosingle.configure;


import com.github.nagatosingle.utils.jwt.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class NagatoAuthInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtTokenService tokenService;

	//基于 URL 的登录拦截
	//直接读配置文件中的url表
	//注解配置文件双重检验
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		Map<String, Object> map = yamlParserClassPathBased(new ClassPathResource("uri-matchlist.yaml"));
		
		LinkedHashMap<String, Object> visitor = (LinkedHashMap<String, Object>) map.get("visitor");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) map.get("user");
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
		
		
		if (allowed.get().contains(uri)) {
			return true;
		} else {
			if (request.getHeader("Authorization") != null)
				return tokenService.validateToken(request.getHeader("Authorization"));
			else return false;
		}
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
