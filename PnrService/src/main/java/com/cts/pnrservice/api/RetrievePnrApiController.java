package com.cts.pnrservice.api;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.pnrservice.model.PassengerDetails;
import com.cts.pnrservice.model.PassengerInfo;
import com.cts.pnrservice.model.PassengerInfoInner;
import com.cts.pnrservice.model.PnrDetails;
import com.cts.pnrservice.model.RetrievePnrResponse;
import com.cts.pnrservice.model.SegmentInfo;
import com.cts.pnrservice.model.SegmentInfoInner;
import com.cts.pnrservice.properties.PnrAppProperties;
import com.cts.pnrservice.service.IPnrService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-16T07:29:02.569Z")

@Controller
public class RetrievePnrApiController implements RetrievePnrApi {
	@Autowired
	IPnrService createPnrSrv;
	
	@Autowired
	private PnrAppProperties pnrAppProperties;
	
    public ResponseEntity<RetrievePnrResponse> retrievePnr( @NotNull@ApiParam(value = "PnrNo", required = true) @RequestParam(value = "PnrNo", required = true) String pnrNo) {
    	String completeURL = pnrAppProperties.getUrl()+"/getPassengerDetails";
    	
    	RetrievePnrResponse responce = new RetrievePnrResponse();
    	 
    	PassengerInfo pssInfoFrError = new PassengerInfo();
		PassengerInfoInner passInnerInfoFrError = null;
		
		SegmentInfo segInfoFrError = new SegmentInfo();
		SegmentInfoInner segInnerInfoFrError = null;
    	try {
    		SegmentInfo segInfo = new SegmentInfo();
    		PassengerInfo passInfo = new PassengerInfo();
    		
    		PnrDetails pnrDetails = new PnrDetails();
    		List<PassengerDetails> passengerDtlsLst =null;
        	
    		pnrDetails = createPnrSrv.retrievePnrDetails(pnrNo,completeURL);
    		
    		if(!pnrDetails.getPNRNumber().contains("Error in retriving PNR ,Please try after some time"))
    		//passengerDtlsLst = createPnrSrv.retrievePassengerDetails(pnrNo,completeURL);
    		passengerDtlsLst = pnrDetails.getPassengerDtlsLst();
        	
        	if(pnrDetails != null && passengerDtlsLst != null) {
        		SegmentInfoInner segInnerInfo = new SegmentInfoInner();
        		segInnerInfo.setArrivalCode(pnrDetails.getArrivalCode());
        		segInnerInfo.setArrivalPoint(pnrDetails.getArrivalPoint());
        		segInnerInfo.setClassOfService(pnrDetails.getClassOfService());
        		segInnerInfo.setDate(pnrDetails.getDate());
        		segInnerInfo.setDeparturePoint(pnrDetails.getDeparturePoint());
        		segInnerInfo.setFlightNumber(pnrDetails.getFlightNumber());
        		
        		segInfo.add(segInnerInfo);
        		responce.segmentInfo(segInfo);
        		
        		for(PassengerDetails passDtls:passengerDtlsLst) {
        			PassengerInfoInner passInnerInfo = new PassengerInfoInner();
        			
        			passInnerInfo.setCustomerId("");
        			passInnerInfo.setFirstName(passDtls.getFirstName());
        			passInnerInfo.setLastName(passDtls.getLastName());
        			passInnerInfo.setPhoneNumber(passDtls.getPhoneNumber());
        			
        			passInfo.add(passInnerInfo);
        		}
        		
        		responce.setPassengerInfo(passInfo);
        	}else {
        		
        		responce = new RetrievePnrResponse();
        		SegmentInfoInner segInnerInfo1 = new SegmentInfoInner();
        	
        		
        		segInfo.add(segInnerInfo1);
        		responce.segmentInfo(segInfo);
        		
        		PassengerInfoInner passInnerInfo1 = new PassengerInfoInner();
        		passInnerInfo1.setCustomerId("Error in retriving PNR ,Please try after some time");
    			
    			passInfo.add(passInnerInfo1);
    			responce.setPassengerInfo(passInfo);
        	}
        }catch(Exception ex) {
        	ex.printStackTrace();
        	
        	responce = new RetrievePnrResponse();
        	
        	segInnerInfoFrError = new SegmentInfoInner();
			
        	segInnerInfoFrError.setArrivalCode("No Data Found");
        	segInnerInfoFrError.setArrivalPoint("No Data Found");
        	segInnerInfoFrError.setClassOfService("No Data Found");
        	segInnerInfoFrError.setDate("No Data Found");
        	segInnerInfoFrError.setDeparturePoint("No Data Found");
        	segInnerInfoFrError.setFlightNumber("No Data Found");
			
        	segInfoFrError.add(segInnerInfoFrError);
        	
        	passInnerInfoFrError = new PassengerInfoInner();
			
        	passInnerInfoFrError.setFirstName("No Data Found");
        	passInnerInfoFrError.setLastName("No Data Found");
        	passInnerInfoFrError.setPhoneNumber("No Data Found");
        	passInnerInfoFrError.setCustomerId("No Data Found");
			
        	pssInfoFrError.add(passInnerInfoFrError);
        	
        	responce.setPassengerInfo(pssInfoFrError);
        	responce.setSegmentInfo(segInfoFrError); 
		}
    	
        return new ResponseEntity<RetrievePnrResponse>(responce, HttpStatus.OK);
    }

}
