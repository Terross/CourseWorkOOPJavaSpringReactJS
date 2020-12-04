package com.example.demo.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException{
	private String message;
	private ArrayList<String> wrongFields;
	
	public ValidationException() {
		
	}
	
	public ValidationException(String message, ArrayList<String> wrongField) {
		this.message = message;
		this.wrongFields = wrongField;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public boolean returnStatus() { return false; }

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getWrongFields() {
		return wrongFields;
	}

	public void setWrongFields(ArrayList<String> wrongField) {
		this.wrongFields = wrongField;
	}
}
