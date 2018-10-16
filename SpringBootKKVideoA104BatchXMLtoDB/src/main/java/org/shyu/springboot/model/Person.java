package org.shyu.springboot.model;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = -5769644060847725219L;
	
	private String lastName;
	private String firstName;
	private String email;
	private int age;
	
	public Person() {
	}
	
	public Person(String firstName, String lastName, String email, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString() {
		return "Last Name: " + this.getLastName() + ", First Name: " + this.getFirstName() + ", Email: " + this.getEmail() + ", Age: " + this.getAge();
	}
}
