package com.example.demo.controller;

public class Status {
	private String message;
	public Status() {

    }
    public Status(String message) {
        this.message = message;
    }
    public Status(Double d) {
    	this.message = Double.toString(d);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
