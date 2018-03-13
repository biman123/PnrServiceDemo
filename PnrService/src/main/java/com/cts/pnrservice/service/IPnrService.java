package com.cts.pnrservice.service;

import java.sql.SQLException;
import java.util.List;

import com.cts.pnrservice.model.CreatePnrRequest;
import com.cts.pnrservice.model.PassengerDetails;
import com.cts.pnrservice.model.PnrDetails;

public interface IPnrService {
	public String createPnr(CreatePnrRequest createPnr, PnrDetails pnrDetails, String passengerURL) throws SQLException,Exception;
	
	public PnrDetails retrievePnrDetails(String pnrNum, String partnerURL) throws SQLException,Exception;
	public List<PassengerDetails> retrievePassengerDetails(String pnrNum, String partnerURL) throws SQLException,Exception;
}
