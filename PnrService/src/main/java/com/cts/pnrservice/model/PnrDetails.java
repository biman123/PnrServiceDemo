package com.cts.pnrservice.model;

import java.io.Serializable;
import java.util.List;

public class PnrDetails implements Serializable{
	private String PNRNumber;
	private String ArrivalCode;
	private String ArrivalPoint;
	private String ClassOfService;
	private String Date;
	private String DeparturePoint;
	private String FlightNumber;
	private List<PassengerDetails> passengerDtlsLst;
	
	public List<PassengerDetails> getPassengerDtlsLst() {
		return passengerDtlsLst;
	}
	public void setPassengerDtlsLst(List<PassengerDetails> passengerDtlsLst) {
		this.passengerDtlsLst = passengerDtlsLst;
	}
	public String getPNRNumber() {
		return PNRNumber;
	}
	public void setPNRNumber(String pNRNumber) {
		PNRNumber = pNRNumber;
	}
	public String getArrivalCode() {
		return ArrivalCode;
	}
	public void setArrivalCode(String arrivalCode) {
		ArrivalCode = arrivalCode;
	}
	public String getArrivalPoint() {
		return ArrivalPoint;
	}
	public void setArrivalPoint(String arrivalPoint) {
		ArrivalPoint = arrivalPoint;
	}
	public String getClassOfService() {
		return ClassOfService;
	}
	public void setClassOfService(String classOfService) {
		ClassOfService = classOfService;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getDeparturePoint() {
		return DeparturePoint;
	}
	public void setDeparturePoint(String departurePoint) {
		DeparturePoint = departurePoint;
	}
	public String getFlightNumber() {
		return FlightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		FlightNumber = flightNumber;
	}
}
