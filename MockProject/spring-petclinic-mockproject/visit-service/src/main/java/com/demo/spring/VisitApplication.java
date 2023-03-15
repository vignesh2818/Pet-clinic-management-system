package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableDiscoveryClient
public class VisitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitApplication.class, args);

	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate2() {
		return new RestTemplate();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate3() {
		return new RestTemplate();
	}
	
	@Bean
	public OpenAPI openApiDoc() {
		return new OpenAPI().info(new Info().description("Mock Project")
				.license(new License().name("Free for All"))
				.title("Visit_Service")
				.version("1.0"));
	}
}
