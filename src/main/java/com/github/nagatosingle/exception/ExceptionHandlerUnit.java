package com.github.nagatosingle.exception;


import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.github.nagatosingle.configure"})
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
