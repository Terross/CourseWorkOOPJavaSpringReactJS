package com.example.demo.model;

import javax.persistence.MappedSuperclass;

import com.sun.istack.NotNull;

@MappedSuperclass
public abstract class Person {
	
	@NotNull
	protected String firstName;
	
	@NotNull
	protected String secondName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	
}
