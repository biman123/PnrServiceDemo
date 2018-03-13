package com.cts.pnrservice.requestbuilder;

import com.cts.pnrservice.model.CreatePassengerRequest;
import com.cts.pnrservice.model.CreatePnrRequest;
import com.cts.pnrservice.model.PassengerInfo;
import com.cts.pnrservice.model.RecordLocatorInfo;
import com.cts.pnrservice.model.RequestInfo;

public class CreatePassengerRequestBuilder {
	public CreatePassengerRequest createPassebegerRequestBuild(CreatePnrRequest createPnr, String recordLocater)throws Exception {
		System.out.println("Creating Request");
		CreatePassengerRequest creatPassRequ = new CreatePassengerRequest();
		
		RequestInfo rqInfo = createPnr.getRequestInfo();
		PassengerInfo passinfo = createPnr.getPassengerInfo();
		RecordLocatorInfo loctInfo = new RecordLocatorInfo();
		loctInfo.setPnrNo(recordLocater);
		
		creatPassRequ.setRequestInfo(rqInfo);
		creatPassRequ.setPassengerInfo(passinfo);
		creatPassRequ.setRecordLocatorInfo(loctInfo);
		
		return creatPassRequ;
	}
}
