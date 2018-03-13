package com.cts.pnrservice.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.cts.pnrservice.caching.RedisCache;
import com.cts.pnrservice.constants.QueryConstants;
import com.cts.pnrservice.model.AdvisoryMessage;
import com.cts.pnrservice.model.CreatePassengerRequest;
import com.cts.pnrservice.model.CreatePassengerResponse;
import com.cts.pnrservice.model.CreatePnrRequest;
import com.cts.pnrservice.model.GetPassengerDetailsResponse;
import com.cts.pnrservice.model.PassengerDetails;
import com.cts.pnrservice.model.PassengerInfo;
import com.cts.pnrservice.model.PassengerInfoInner;
import com.cts.pnrservice.model.PnrDetails;
import com.cts.pnrservice.requestbuilder.CreatePassengerRequestBuilder;
import com.cts.pnrservice.restinvoker.InovkePartnerService;
import com.cts.pnrservice.utils.DateUtils;

@Repository
public class PnrDAO implements IPnrDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private InovkePartnerService inovkeRestService;
	
	@Autowired
	public RedisCache redisCache ;

	public PnrDAO(RedisCache redisCache){
		System.out.println("redisCache" + redisCache);
		this.redisCache = redisCache;
	}
	
	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
	   return builder.build();
	}
	
	@Override
	public String createPnr(CreatePnrRequest createPnr, PnrDetails pnrDetails, String passengerURL) throws SQLException, Exception {
		String pnrNumber = "";
		AdvisoryMessage resAdvMessage = new AdvisoryMessage();
		String sql = QueryConstants.QUERY_GET_MAX_PNR_NUMBER;
		pnrNumber = this.jdbcTemplate.queryForObject(sql, String.class);
		//System.out.println("PNR Number from DB-->"+pnrNumber);
		
		sql = QueryConstants.QUERY_INSERT_PNR_DETAILS;
		this.jdbcTemplate.update(sql, new Object[] {  pnrNumber, pnrDetails.getArrivalCode(), pnrDetails.getArrivalPoint(),pnrDetails.getClassOfService(), DateUtils.getSqlTimeStampFromMMDDYYYY(pnrDetails.getDate()), pnrDetails.getDeparturePoint(), pnrDetails.getFlightNumber() });
		
		//System.out.println("Wrote PNR details Successfully"); 
		
		Thread.sleep(500);
		
		CreatePassengerRequestBuilder buildRequest = new CreatePassengerRequestBuilder();
		CreatePassengerRequest createPassReq = buildRequest.createPassebegerRequestBuild(createPnr, pnrNumber);
		try {
			ResponseEntity<CreatePassengerResponse> createPassResEntity = inovkeRestService.createPassenger(createPassReq, passengerURL);
			CreatePassengerResponse createPassResObj = createPassResEntity.getBody();
			resAdvMessage = createPassResObj.getAdvisoryMessage();
			if(resAdvMessage != null) {
				if(resAdvMessage.getFreeformText().contains("Passenger created for pnrNo")){
					System.out.println("Got required Data from Partner");
					redisCache.addToCache(pnrNumber, pnrDetails);
					//System.out.println("Redis cash implemented");
				}else{
					System.out.println("deleted 1");
					deletePnrDetailsOnFailure(pnrNumber);
					pnrNumber = resAdvMessage.getFreeformText();
				}
			}else {
				System.out.println("deleted 2");
				pnrNumber = "";
				deletePnrDetailsOnFailure(pnrNumber);
				pnrNumber = "";
			}
		}catch(Exception ex) {
			System.out.println("Deleting becasue of Exception");
			deletePnrDetailsOnFailure(pnrNumber);
			pnrNumber = resAdvMessage.getFreeformText();
			throw ex;
		}
		
		return pnrNumber;
	}
	
	public void deletePnrDetailsOnFailure(String pnrNumber)throws Exception {
		String sql = QueryConstants.QUERY_DELETE_PNR_DETAILS;
		this.jdbcTemplate.update(sql, new Object[] { pnrNumber});
		System.out.println("Deleted "+pnrNumber+" Successfully");
	}

	@Override
	public PnrDetails retrievePnrDetails(String pnrNum, String partnerURL) throws SQLException, Exception {
		String sql = QueryConstants.QUERY_GET_PNR_DETAILS;
		PnrDetails pnrDetails = null;
		
		pnrDetails = redisCache.getSegInfo(pnrNum, pnrDetails);
		if(pnrDetails == null) {
			ResponseEntity<GetPassengerDetailsResponse> getPassResEntity = inovkeRestService.retrievePassenger(pnrNum, partnerURL);
			
			GetPassengerDetailsResponse getPassRes = getPassResEntity.getBody();
			//get(0).getFirstName().contains("Error in Passeneger Retrieval")
			if(getPassRes.getPassengerInfo() == null)
			{
				pnrDetails = new PnrDetails();
				pnrDetails.setPNRNumber("Error in retriving PNR ,Please try after some time");
			}
			else
			{
				pnrDetails = new PnrDetails();
				pnrDetails = this.jdbcTemplate.queryForObject(sql, new Object[] { pnrNum },  new PnrDetailsRowMapper());
				
				PassengerInfo passInfo = getPassRes.getPassengerInfo();
				List<PassengerDetails> passengerDtlsLst = new ArrayList<PassengerDetails>(); 
				PassengerDetails passDtls = null;
				
				for(PassengerInfoInner passengerDtls: passInfo) {
					passDtls = new PassengerDetails();
					
					passDtls.setFirstName(passengerDtls.getFirstName());
					passDtls.setLastName(passengerDtls.getLastName());
					passDtls.setPhoneNumber(passengerDtls.getPhoneNumber());
					passDtls.setPNRNumber(pnrNum);
					
					passengerDtlsLst.add(passDtls);
				}
				pnrDetails.setPassengerDtlsLst(passengerDtlsLst);
				
				redisCache.addToCache(pnrNum, pnrDetails);
			}
		}else {
			System.out.println("Retrieved Pnr Details from cache");
			pnrDetails.setPNRNumber(pnrNum);
		}
		
		return pnrDetails;
	}

	@Override
	public List<PassengerDetails> retrievePassengerDetails(String pnrNum, String completeurl) throws SQLException, Exception {
		String sql = QueryConstants.QUERY_GET_PASSENGER_DETAILS;
		System.out.println("Entered into retrievePassengerDetails SQl-->"+sql);
	    List<PassengerDetails> passengerList = this.jdbcTemplate.query(sql, new Object[] { pnrNum }, new PassengerDetailsRowMapper());
	    /*for (PassengerDetails pass : passengerList) {
	      System.out.println("passenger FirstName " + pass.getFirstName());
	    }*/
	    
	    System.out.println("calling retrieve passeneger details partner"); 
	    
	    
	    System.out.println("Retrieved all the Passengers");
	    
	    return passengerList;
	}

}
