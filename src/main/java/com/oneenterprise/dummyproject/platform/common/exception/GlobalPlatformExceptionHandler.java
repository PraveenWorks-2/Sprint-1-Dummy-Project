package com.oneenterprise.dummyproject.platform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oneenterprise.dummyproject.platform.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalPlatformExceptionHandler {
    
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex){
		return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneral(Exception ex){
		return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
