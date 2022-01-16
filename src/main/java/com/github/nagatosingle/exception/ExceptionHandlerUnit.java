package com.github.nagatosingle.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.github.nagatosingle.configure", "com.github.nagatosignle.service"})
public class ExceptionHandlerUnit {
//	@ExceptionHandler(OAuth2Exception.class)
//	public ResponseEntity<?> badCredentials() {
//		System.out.println("通过ExceptionHandler");
//
//		return new ResponseEntity<>(
//				new NagatoResponseEntity()
//						.message("用户名或密码错误"),
//				HttpStatus.BAD_REQUEST
//		);
//	}
}
