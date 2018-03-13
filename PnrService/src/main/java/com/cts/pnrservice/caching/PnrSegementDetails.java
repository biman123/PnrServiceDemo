package com.cts.pnrservice.caching;

import java.io.Serializable;

import com.cts.pnrservice.model.PassengerInfoInner;
import com.cts.pnrservice.model.SegmentInfoInner;

public class PnrSegementDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SegmentInfoInner segmentInfoInner;

	private PassengerInfoInner passengerInfoInner;

	public SegmentInfoInner getSegmentInfoInner() {
		return segmentInfoInner;
	}

	public void setSegmentInfoInner(SegmentInfoInner segmentInfoInner) {
		this.segmentInfoInner = segmentInfoInner;
	}

	public PassengerInfoInner getPassengerInfoInner() {
		return passengerInfoInner;
	}

	public void setPassengerInfoInner(PassengerInfoInner passengerInfoInner) {
		this.passengerInfoInner = passengerInfoInner;
	}

}
