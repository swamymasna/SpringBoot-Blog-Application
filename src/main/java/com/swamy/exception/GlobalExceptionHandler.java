package com.swamy.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	// Handle Specific Exceptions
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND, new Date(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value = BlogApiException.class)
	public ResponseEntity<ErrorMessage> handleBlogApiException(BlogApiException exception, WebRequest webRequest) {

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST, new Date(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

	}

	// Handle Global Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleExceptions(Exception exception, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
				new Date(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String>errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error)->{
			String fieldValue = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.putIfAbsent(fieldValue, message);
		});
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	/*
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
		
		Map<String, String>errors = new HashMap<>();
		
		exception.getBindingResult().getAllErrors().forEach((error)->{
			String fieldValue = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldValue, message);
		});
		
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	*/
}

