package com.cts.pnrservice.restinvoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.pnrservice.model.AdvisoryMessage;
import com.cts.pnrservice.model.CreatePassengerResponse;
import com.cts.pnrservice.model.GetPassengerDetailsResponse;
import com.cts.pnrservice.model.PassengerInfo;
import com.cts.pnrservice.model.PassengerInfoInner;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@org.springframework.stereotype.Component
public class InovkePartnerService {
	
	@Autowired
	 RestTemplate restTemplate;
	 /*
	 @Bean
	 public RestTemplate restTemplate() {
        return new RestTemplate();
	 }*/
	 
	// private final RestTemplate restTemplate;

	  /*public InovkePartnerService(RestTemplate rest) {
	    this.restTemplate = rest;
	  }*/
	
	@HystrixCommand(fallbackMethod = "createPassengersFallback" )
	public ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> createPassenger(com.cts.pnrservice.model.CreatePassengerRequest reqobj, String url) throws Exception{
		System.out.println("Calling Passeneger  Service with pnr number-->"+reqobj.getRecordLocatorInfo().getPnrNo());
		System.out.println("full request-->"+reqobj);
		System.out.println("Passeneger service URL-->"+url);
		
		String createPassServiceURl = url;
		//RestTemplate restTemplates = new RestTemplate();
		ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> response = restTemplate.postForEntity(createPassServiceURl, reqobj, com.cts.pnrservice.model.CreatePassengerResponse.class);
		System.out.println("Scuccessfully called Passenger service");
		return response;
	}
	
	public ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> createPassengersFallback(com.cts.pnrservice.model.CreatePassengerRequest reqobj, String url) {
		System.out.println("** inside fall back method**");
		CreatePassengerResponse CreatePassengerResponse = new CreatePassengerResponse();
		AdvisoryMessage advMessage = new AdvisoryMessage();
		
		advMessage.freeformText("Error in PNR creation,please try after sometime");
		CreatePassengerResponse.setAdvisoryMessage(advMessage);
		
		ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> response = new ResponseEntity<CreatePassengerResponse>(CreatePassengerResponse, HttpStatus.OK);
		
	    return response;
	}
	
	@HystrixCommand(fallbackMethod = "retrievePassengersFallback" )
	public ResponseEntity<com.cts.pnrservice.model.GetPassengerDetailsResponse> retrievePassenger(String pnrNum, String url) throws Exception{
		System.out.println("Calling Passeneger  Service with pnr number-->"+pnrNum);
		url = url +"?PnrNo="+pnrNum;
		System.out.println("Passeneger service URL-->"+url);
		String createPassServiceURl = url;
		ResponseEntity<com.cts.pnrservice.model.GetPassengerDetailsResponse> response = restTemplate.getForEntity(createPassServiceURl, com.cts.pnrservice.model.GetPassengerDetailsResponse.class);
		System.out.println("Scuccessfully called Retrieve Passenger service");
		return response;
	} 
	
	public ResponseEntity<com.cts.pnrservice.model.GetPassengerDetailsResponse> retrievePassengersFallback(String pnrNumber, String url) {
		System.out.println("** inside fall back method retrievePassengersFallback**");
		GetPassengerDetailsResponse res = new GetPassengerDetailsResponse();
		
		PassengerInfo passInfo = new PassengerInfo();
		
		PassengerInfoInner passInnerInfo = new PassengerInfoInner();
		
		passInnerInfo.setCustomerId("Error in Passeneger Retrieval");
		passInnerInfo.setFirstName("Error in Passeneger Retrieval");
		passInnerInfo.setLastName("Error in Passeneger Retrieval");
		passInnerInfo.setPhoneNumber("Error in Passeneger Retrieval");
	
		passInfo.add(passInnerInfo);
		
		res.setPassengerInfo(passInfo);
		
		ResponseEntity<com.cts.pnrservice.model.GetPassengerDetailsResponse> response = new ResponseEntity<GetPassengerDetailsResponse>(res, HttpStatus.OK);
		
	    return response;
	}
}
