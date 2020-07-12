package com.in28minutes.rest.webservices.restfulwebservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotCreatedException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostNotCreatedException(String message) {
		super(message);
		
	}
	
	

}
