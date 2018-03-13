package com.cts.pnrservice.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.cts.pnrservice.properties.PnrAppProperties;

@Configuration
public class CacheConfig {
	
	@Autowired
	private PnrAppProperties pnrAppProperties;

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
	//	redisConnectionFactory.setHostName("redis://redis:");
		redisConnectionFactory.setHostName(pnrAppProperties.getCacheHost());
		redisConnectionFactory.setPort(Integer.parseInt(pnrAppProperties.getCachePort().trim()));
	    //redisConnectionFactory.setPassword(pnrAppProperties.getCachePassword());
		return redisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		System.out.println("redisTemplate::"+redisTemplate);
		return redisTemplate;
	}

/*	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}*/

	/*@Bean
	public String getHostName(Environment env) {
		return env.getProperty("db.host");
	}

	@Bean
	public String getPortName(Environment env) {
		return env.getProperty("db.port");
	}

	@Bean
	public String getUserName(Environment env) {
		return env.getProperty("db.username");
	}

	@Bean
	public String getPwd(Environment env) {
		return env.getProperty("db.password");
	}

	@Bean
	public String getName(Environment env) {
		return env.getProperty("db.name");
	}*/
}
