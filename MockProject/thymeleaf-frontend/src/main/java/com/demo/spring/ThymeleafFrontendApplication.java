package com.demo.spring;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ThymeleafFrontendApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafFrontendApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/find1").setViewName("findone");
		registry.addViewController("/findp").setViewName("findonepet");
		registry.addViewController("/list/").setViewName("listall");
		registry.addViewController("/createowner").setViewName("create");
		registry.addViewController("/delete").setViewName("removeOwner");
		registry.addViewController("/update").setViewName("update");
		registry.addViewController("/addP").setViewName("addpettoowner");
		registry.addViewController("/remove").setViewName("removepetfromowner");
		registry.addViewController("/create").setViewName("createPet");
		registry.addViewController("/").setViewName("homeowner");

		registry.addViewController("/v").setViewName("homevet");
		registry.addViewController("/find").setViewName("findvet");
		registry.addViewController("/list").setViewName("listvets");
		registry.addViewController("/listvet").setViewName("listbyspecId");
		registry.addViewController("/addvet").setViewName("saveVet");
		registry.addViewController("/removevet").setViewName("deletevet");
		registry.addViewController("/updatevetname").setViewName("updatevet");
		registry.addViewController("/addSpec").setViewName("createSpec");
		registry.addViewController("/removespec").setViewName("deletespec");
		registry.addViewController("/listspec").setViewName("listspec");
		registry.addViewController("/findspec").setViewName("findSpec");
		
		
		
		registry.addViewController("/addvisit").setViewName("createvisit");
		registry.addViewController("/listvisits").setViewName("listvisitsbyPetid");
		//registry.addViewController("/listv2").setViewName("listvisit2");
		
		registry.addViewController("/index").setViewName("homemain");
		registry.addViewController("/homeowner").setViewName("homeowner");
		registry.addViewController("/homevet").setViewName("homevet");
		registry.addViewController("/homevisit").setViewName("homevisit");
	}

	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

       return restTemplate;
    }

}
