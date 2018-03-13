package com.cts.pnrservice.api;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.pnrservice.model.AdvisoryMessage;
import com.cts.pnrservice.model.CreatePnrRequest;
import com.cts.pnrservice.model.CreatePnrResponse;
import com.cts.pnrservice.model.PnrDetails;
import com.cts.pnrservice.model.RecordLocatorInfo;
import com.cts.pnrservice.model.SegmentInfo;
import com.cts.pnrservice.model.SegmentInfoInner;
import com.cts.pnrservice.properties.PnrAppProperties;
import com.cts.pnrservice.service.IPnrService;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-16T07:29:02.569Z")

@Controller
public class CreatePnrApiController implements CreatePnrApi {
	@Autowired
	IPnrService createPnrSrv;
	
	@Autowired
	private PnrAppProperties pnrAppProperties;
	
    public ResponseEntity<CreatePnrResponse> createPnr(@ApiParam(value = "" ,required=true )  @Valid @RequestBody CreatePnrRequest body) {
    	String completeURL = pnrAppProperties.getUrl()+"/createPassenger";
    	
    	String pnrNumber = "";
    	CreatePnrResponse responce = new CreatePnrResponse();
    	AdvisoryMessage advMessage = new AdvisoryMessage();
    	RecordLocatorInfo recordlocater = new RecordLocatorInfo();
    	try {
			//pnrNumber = new pnrCreationServiceImpl().createPnr(body, dbProperties);
    		SegmentInfo segInfo = body.getSegmentInfo();
			SegmentInfoInner segDetailsInfo = segInfo.get(0);
			
			if(segDetailsInfo != null) {
				PnrDetails pnrDtls = new PnrDetails();
				pnrDtls.setArrivalCode(segDetailsInfo.getArrivalCode());
				pnrDtls.setArrivalPoint(segDetailsInfo.getArrivalPoint());
				pnrDtls.setClassOfService(segDetailsInfo.getClassOfService());
				pnrDtls.setDate(segDetailsInfo.getDate());
				pnrDtls.setDeparturePoint(segDetailsInfo.getDeparturePoint());
				pnrDtls.setFlightNumber(segDetailsInfo.getFlightNumber());
				
				pnrNumber = this.createPnrSrv.createPnr(body, pnrDtls, completeURL);
			}else {
				recordlocater.setPnrNo(pnrNumber);
				advMessage.setFreeformText("Issue during PNR Creation");
				
				responce.setAdvisoryMessage(advMessage);
				responce.setPassengerInfo(recordlocater);
			}
			
			if(pnrNumber != null && pnrNumber.length() == 5) {
				recordlocater.setPnrNo(pnrNumber);
				advMessage.setFreeformText("PNR Number generated Successfully!!!");
				
				responce.setAdvisoryMessage(advMessage);
				responce.setPassengerInfo(recordlocater);
			}else{
				recordlocater.setPnrNo("");
				if(pnrNumber != null && pnrNumber.length() > 0) {
					advMessage.setFreeformText(pnrNumber);
				}else {
					advMessage.setFreeformText("Issue during PNR Creation");
				}
				
				responce.setAdvisoryMessage(advMessage);
				responce.setPassengerInfo(recordlocater);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			recordlocater.setPnrNo(pnrNumber);
			advMessage.setFreeformText("Issue during PNR Creation");
			
			responce.setAdvisoryMessage(advMessage);
			responce.setPassengerInfo(recordlocater);
		}
    	
    	 return new ResponseEntity<CreatePnrResponse>(responce, HttpStatus.OK);
    }

}
