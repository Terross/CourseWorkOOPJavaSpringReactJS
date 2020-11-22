package com.example.demo.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionForm {
	private String message;
	private ArrayList<String> wrongFields;
	
	public ValidationExceptionForm() {
		
	}
	
	public ValidationExceptionForm(String message, ArrayList<String> wrongField) {
		this.message = message;
		this.wrongFields = wrongField;
	}

	public String getMessage() {
		return message;
	}

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
