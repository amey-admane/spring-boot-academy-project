package com.acrnome.academy_amey.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private String message;

	private HttpStatus errorCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

}
