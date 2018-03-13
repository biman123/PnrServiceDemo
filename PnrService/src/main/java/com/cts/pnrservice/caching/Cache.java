package com.cts.pnrservice.caching;

import com.cts.pnrservice.model.PassengerInfoInner;
import com.cts.pnrservice.model.PnrDetails;

public interface Cache {

	public void addToCache(String key, Object item);

	public void addToCache(String key, Object item, long expireTimeInSeconds);

	public Object getSegInfo(String key, PnrDetails type);
	
	public Object getPassInfo(String key, PassengerInfoInner type);

	public void delete(String key);
}
