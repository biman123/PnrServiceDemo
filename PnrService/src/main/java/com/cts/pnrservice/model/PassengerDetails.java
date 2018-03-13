package com.cts.pnrservice.model;

import java.io.Serializable;

public class PassengerDetails implements Serializable{
	private String PNRNumber;
	private String FirstName;
	private String LastName;
	private String PhoneNumber;
	public String getPNRNumber() {
		return PNRNumber;
	}
	public void setPNRNumber(String pNRNumber) {
		PNRNumber = pNRNumber;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
}
