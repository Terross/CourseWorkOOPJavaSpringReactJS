package com.example.demo.controller;

public class Status {
	private String message;
	public Status() {

    }
    public Status(String message) {
        this.message = message;
    }
    public Status(Boolean status) {
	    if(status) {
	        this.message = "Success";
        } else {
	        this.message = "Error";
        }
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
