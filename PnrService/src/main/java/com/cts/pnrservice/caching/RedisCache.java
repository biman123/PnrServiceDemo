package com.cts.pnrservice.caching;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cts.pnrservice.model.PassengerInfoInner;
import com.cts.pnrservice.model.PnrDetails;
import com.cts.pnrservice.model.SegmentInfoInner;

@Component
public class RedisCache implements Cache {
	
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public RedisCache(RedisTemplate<String, Object> redisTemplate){
		this.redisTemplate = redisTemplate;
		
		
	}
	
	@Override
	public void addToCache(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void addToCache(String key, Object value, long expireInSeconds) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, expireInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public  PassengerInfoInner getPassInfo(String key, PassengerInfoInner type) {
		return (PassengerInfoInner) redisTemplate.opsForValue().get(key);
	}
	
	@Override
	public  PnrDetails getSegInfo(String key, PnrDetails type) {
		return (PnrDetails) redisTemplate.opsForValue().get(key);
	}
	

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
