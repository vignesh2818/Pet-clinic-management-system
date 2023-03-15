package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableConfigurationProperties(MyServerConfiguration.class)
public class PetClinicGatewayApplication {

	public static void main(String[] args) {
	SpringApplication.run(PetClinicGatewayApplication.class, args);
	
	}
	/*
	@Bean
	public RouteLocator appRoutes(RouteLocatorBuilder routeBuilder,MyServerConfiguration config) {
		String ownerserver=config.getOwnerServer();
		String vetserver=config.getVetServer();
		String visitserver=config.getVisitServer();
		return routeBuilder
				.routes()
				.route(p->
				        p.path("/owner/**")
				        .uri(ownerserver))
				.route(p->
				        p.path("/vet/**")
				        .uri(vetserver))
				.route(p->
				        p.path("/visit/**")
				        .uri(visitserver))
				.build();
	}

}


@ConfigurationProperties
class MyServerConfiguration {
	private String ownerServer="http://localhost:8181/";
	private String vetServer="http://localhost:8187/";
	private String visitServer="http://localhost:8191/";
	public String getOwnerServer() {
		return ownerServer;
	}
	public void setOwnerServer(String ownerServer) {
		this.ownerServer = ownerServer;
	}
	public String getVetServer() {
		return vetServer;
	}
	public void setVetServer(String vetServer) {
		this.vetServer = vetServer;
	}
	public String getVisitServer() {
		return visitServer;
	}
	public void setVisitServer(String visitServer) {
		this.visitServer = visitServer;
	}
	*/
}
	