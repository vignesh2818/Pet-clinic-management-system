package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.PetDTO;
import com.demo.spring.dto.VisitDTO;

@Controller
public class UIController3 {

	@Autowired
	RestTemplate restTemplate;
	

	@PostMapping(path = "/createv")
	public ModelAndView createOneVisit(VisitDTO visitdto) {
		ModelAndView mv = new ModelAndView();
	       HttpHeaders headers = new HttpHeaders();
	        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	        HttpEntity<VisitDTO> request = new HttpEntity<>(visitdto, headers);
	        ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/visit/create", HttpMethod.POST,
	                request, String.class);
	        mv.addObject("response", response.getBody());
	        mv.setViewName("visit_success");
	        return mv;
	}
	
	@GetMapping(path = "/listvpet")
	public ModelAndView findAllVisits(@RequestParam(name = "petId", required = true) int petId) {
		 ModelAndView mv = new ModelAndView();
		 String message = "Pet not found";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<Void> request = new HttpEntity<>(headers);
			ResponseEntity<PetDTO> response1 = restTemplate.exchange("http://localhost:9191/pet/findp/" + petId,
					HttpMethod.GET, request, PetDTO.class);
			System.out.println(response1.getBody().getPetName());
			if (response1.getBody().getPetName() != null) {
				System.out.println(response1.getBody().getPetName());
				ResponseEntity<List<VisitDTO>> response = restTemplate.exchange("http://localhost:9191/visit/list/" + petId,
						HttpMethod.GET, request, new ParameterizedTypeReference<List<VisitDTO>>() {
						});
				mv.addObject("visitList", response.getBody());
				mv.setViewName("listvisitsbyPetid");

			} else {
				mv.addObject("msg", message);
				mv.setViewName("create_failed");
			}

			return mv;
	}
	
	@GetMapping(path = "/listv2")
	public ModelAndView listAllVisit() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<VisitDTO>> response = restTemplate.exchange("http://localhost:9191/visit/listvisit",
				HttpMethod.GET, request, new ParameterizedTypeReference<List<VisitDTO>>() {
				});
		mv.addObject("visitList1", response.getBody());
		mv.setViewName("listvisit2");
		return mv;

	}
	
}
