package com.example.demo.Exceptions;

public class ElementNotFound extends RuntimeException{
	private String message;

	public ElementNotFound() {
		
	}
	
	public ElementNotFound(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
