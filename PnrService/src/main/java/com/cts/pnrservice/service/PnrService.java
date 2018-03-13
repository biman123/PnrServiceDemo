package com.cts.pnrservice.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.pnrservice.caching.RedisCache;
import com.cts.pnrservice.dataobjects.IPnrDAO;
import com.cts.pnrservice.model.CreatePnrRequest;
import com.cts.pnrservice.model.PassengerDetails;
import com.cts.pnrservice.model.PnrDetails;

@Service
public class PnrService implements IPnrService {
	@Autowired
	  private IPnrDAO pnrDAO;
	
	@Autowired
	RedisCache redisCache;
	
	public PnrService(RedisCache redisCache){
		System.out.println("redisCache::" + redisCache);
		this.redisCache = redisCache;
	}

	@Override
	public String createPnr(CreatePnrRequest createPnr, PnrDetails pnrDetails, String passengerURL) throws SQLException, Exception {
		return this.pnrDAO.createPnr(createPnr, pnrDetails, passengerURL);
	}

	@Override
	public PnrDetails retrievePnrDetails(String pnrNum, String partnerURL) throws SQLException, Exception {
		return this.pnrDAO.retrievePnrDetails(pnrNum,partnerURL);
	}

	@Override
	public List<PassengerDetails> retrievePassengerDetails(String pnrNum, String completeUrl) throws SQLException, Exception {
		return this.pnrDAO.retrievePassengerDetails(pnrNum, completeUrl);
	}

}
