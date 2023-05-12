package com.acrnome.academy_amey.exceptions;

import org.springframework.http.HttpStatus;

public class MasterToolsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private  String message;
	private  String errorCode;

	private  HttpStatus statusCode;


	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public MasterToolsException(String message, String errorCode,HttpStatus statusCode) {
		this.message = message;
		this.errorCode = errorCode;
		this.statusCode = statusCode;
	}
}
