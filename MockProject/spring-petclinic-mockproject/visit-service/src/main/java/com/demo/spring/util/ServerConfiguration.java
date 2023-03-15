package com.demo.spring.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration {

	String ownerServer="http://localhost:8181";
	String visitServer="http://localhost:8191";
	public String getOwnerServer() {
		return ownerServer;
	}
	public void setOwnerServer(String ownerServer) {
		this.ownerServer = ownerServer;
	}
	public String getVisitServer() {
		return visitServer;
	}
	public void setVisitServer(String visitServer) {
		this.visitServer = visitServer;
	}
	
}
