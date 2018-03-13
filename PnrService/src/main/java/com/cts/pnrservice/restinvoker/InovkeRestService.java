package com.cts.pnrservice.restinvoker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.pnrservice.model.AdvisoryMessage;
import com.cts.pnrservice.model.CreatePassengerResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@org.springframework.stereotype.Component
public class InovkeRestService {
	
	 /*@Autowired
	 RestTemplate restTemplate;
	 
	 @Bean
	 public RestTemplate restTemplate() {
        return new RestTemplate();
	 }*/
	 
	 private final RestTemplate restTemplate;

	  public InovkeRestService(RestTemplate rest) {
	    this.restTemplate = rest;
	  }
	
	@HystrixCommand(fallbackMethod = "createPassengersFallback")
	public ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> createPassenger(com.cts.pnrservice.model.CreatePassengerRequest reqobj, String url) throws Exception{
		System.out.println("Calling Passeneger Rest Service with pnr number-->"+reqobj.getRecordLocatorInfo().getPnrNo());
		System.out.println("full request-->"+reqobj);
		System.out.println("URL-->"+url);
		
		String createPassServiceURl = url;
		//RestTemplate restTemplates = new RestTemplate();
		ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> response = restTemplate.postForEntity(createPassServiceURl, reqobj, com.cts.pnrservice.model.CreatePassengerResponse.class);
		System.out.println("Scuccess in calling Partner");  
		return response;
	}
	
	public ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> createPassengersFallback(com.cts.pnrservice.model.CreatePassengerRequest reqobj, String url) {
		System.out.println("Came into fall back method");
		CreatePassengerResponse CreatePassengerResponse = new CreatePassengerResponse();
		AdvisoryMessage advMessage = new AdvisoryMessage();
		
		advMessage.freeformText("Error in creating Passenger. Please try After sometime");
		CreatePassengerResponse.setAdvisoryMessage(advMessage);
		
		ResponseEntity<com.cts.pnrservice.model.CreatePassengerResponse> response = new ResponseEntity<CreatePassengerResponse>(CreatePassengerResponse, HttpStatus.OK);
		
	    return response;
	}
}
