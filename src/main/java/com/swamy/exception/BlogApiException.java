package com.swamy.exception;

public class BlogApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlogApiException() {
	}

	public BlogApiException(String message) {
		super(message);
	}
}
