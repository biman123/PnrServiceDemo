package com.cts.pnrservice.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PnrAppProperties {
	@Value("${partner.passenger.service.host}")
	private String host;
	
	@Value("${partner.passenger.service.port}")
	private String port;
	
	@Value("${partner.passenger.service.contextPath}")
	private String contextPath;
	
	@Value("${partner.passenger.service.url}")
	private String url;
	
	@Value("${pnr.cache.host}")
	private String cacheHost;
	
	@Value("${pnr.cache.port}")
	private String cachePort;
	
	@Value("${pnr.cache.password}")
	private String cachePassword;

	public String getCachePassword() {
		return cachePassword;
	}

	public void setCachePassword(String cachePassword) {
		this.cachePassword = cachePassword;
	}

	public String getCacheHost() {
		return cacheHost;
	}

	public void setCacheHost(String cacheHost) {
		this.cacheHost = cacheHost;
	}

	public String getCachePort() {
		return cachePort;
	}

	public void setCachePort(String cachePort) {
		this.cachePort = cachePort;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
