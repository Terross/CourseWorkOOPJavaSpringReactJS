package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	class ElementNotFoundForm {
		private String message;
		
		public ElementNotFoundForm() {
			
		}
		
		public ElementNotFoundForm(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Object> handleValidationExceptions(ValidationException ex){
		ValidationExceptionForm exceptionForm = new ValidationExceptionForm(ex.getMessage(),
				ex.getWrongFields());
		return new ResponseEntity<Object>(exceptionForm, HttpStatus.ACCEPTED);
	}
	
	@ExceptionHandler(ElementNotFound.class)
	public final ResponseEntity<Object> handleExistExceptions(ElementNotFound ex){
		ElementNotFoundForm elementNotFoundForm = new ElementNotFoundForm(ex.getMessage());
		return new ResponseEntity<Object>(elementNotFoundForm, HttpStatus.BAD_REQUEST);
	}
}
