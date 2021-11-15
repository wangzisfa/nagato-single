package com.github.nagatosingle.exception;


import com.github.nagatosingle.entity.response.NagatoResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.github.nagatosingle.configure"})
public class ExceptionHandlerUnit {
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentials() {


		return new ResponseEntity<>(
				new NagatoResponseEntity()
						.message("用户名或密码错误"),
				HttpStatus.BAD_REQUEST
		);
	}

}
