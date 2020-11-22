package com.example.demo.controller;

public class Status {
	private String message;
	public Status() {

    }
    public Status(String message) {
        this.message = message;
    }
    public Status(Double price) {
    	this.message = Double.toString(price);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
