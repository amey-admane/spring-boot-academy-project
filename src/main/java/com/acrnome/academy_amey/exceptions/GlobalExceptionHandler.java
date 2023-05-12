package com.acrnome.academy_amey.exceptions;

import java.io.IOException;

import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.MethodNotAllowedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	

	  @ExceptionHandler({IllegalArgumentException.class}) 
	  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	  public ErrorResponse handleIllegalArgumentException(Exception e) {
		  ErrorResponse error = new ErrorResponse();
		  error.setMessage(e.getMessage().split(":")[0]);
		  error.setErrorCode(HttpStatus.PRECONDITION_FAILED);
		 	  
		  return error; 
	  }	
	  
	  @ExceptionHandler({ArrayIndexOutOfBoundsException.class, IOException.class,MethodNotAllowedException.class, UnsupportedOperationException.class}) 
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  public ErrorResponse handleIOException(Exception e) {

		  ErrorResponse error = new ErrorResponse();
		  error.setMessage(e.getMessage().split(":")[0]);
		  error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		  return error; 
	  }	
	  
	 
	  @ExceptionHandler({SQLException.class, DataIntegrityViolationException.class,NullPointerException.class,BadSqlGrammarException.class})
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  public ErrorResponse handleDataIntegrety(Exception e) {

		  ErrorResponse error = new ErrorResponse();
		  error.setMessage("Error in SQL Statement, Check the object format or values passed");
		  error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		  return error; 
	  }	
	  
	  @ExceptionHandler({EmptyResultDataAccessException.class}) 
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public ErrorResponse  handleEmptyResultDataAccessException(Exception e) {
		  
		  ErrorResponse error = new ErrorResponse();
		  error.setMessage(e.getMessage().split(":")[0]);
		  error.setErrorCode(HttpStatus.NOT_FOUND);
		  return error; 
	  }	
	  
	  @ExceptionHandler({HttpRequestMethodNotSupportedException.class}) 
	  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	  public ErrorResponse  handleMethoNotAllowedException(Exception e) {
		  
		  ErrorResponse error = new ErrorResponse();
		  error.setMessage(e.getMessage().split(":")[0]);
		  error.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED);
		  return error; 
	  }	
	  
	  
	  @ExceptionHandler({ HttpMessageNotReadableException.class,MissingServletRequestPartException.class}) 
	  @ResponseStatus(HttpStatus.BAD_GATEWAY)
	  public ErrorResponse  handleNoBodyFoundException(Exception e) {
		  
		  ErrorResponse error = new ErrorResponse();
		  error.setMessage(e.getMessage().split(":")[0]);
		  error.setErrorCode(HttpStatus.BAD_GATEWAY);
		  return error; 
	  }
	  
	  
	  @ExceptionHandler({MasterToolsException.class}) 
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  public ResponseEntity<MasterToolsErrorResponse> customException(MasterToolsException e) {

		  MasterToolsErrorResponse error = new MasterToolsErrorResponse();
		  error.setMessage(e.getMessage());
		  error.setErrorCode(e.getErrorCode());
		  return ResponseEntity.status(e.getStatusCode())
				  .body(error); 
	  }
	  
	
}
