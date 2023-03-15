package com.demo.spring.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
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


import com.demo.spring.dto.SpecialityDTO;
import com.demo.spring.dto.VetDTO;
import com.demo.spring.dto.VetsDTO;

@Controller
public class UIController2 {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/findvet")
	public ModelAndView findVetById(@RequestParam(name = "vetId", required = true) int vetId) {
		ModelAndView mv = new ModelAndView();
		String message = "Vet not found ";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<VetDTO> response = restTemplate.exchange("http://localhost:9191/vet/find/" + vetId,
				HttpMethod.GET, request, VetDTO.class);
		if(response.getBody().getVetName()!=null) {
			DecimalFormat df = new DecimalFormat("#");
			df.setMaximumFractionDigits(10);
			String pn = (df.format(response.getBody().getVetContact()));
			mv.addObject("vet", response.getBody());
			mv.setViewName("findvet");
			mv.addObject("pn", pn);
		}
		else {
			mv.addObject("msg", message);
			mv.setViewName("findvet_failed");
		}
		
		return mv;
	}
	
	
	@GetMapping(path = "/listvets")
	public ModelAndView findAllVet() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<VetDTO>> response = restTemplate.exchange("http://localhost:9191/vet/list",
				HttpMethod.GET, request, new ParameterizedTypeReference<List<VetDTO>>() {
				});
		mv.addObject("vetList", response.getBody());
		mv.setViewName("listvets");
		return mv;
	}
	
	@GetMapping(path = "/listbyspecId")
	public ModelAndView findAllVetbySpecId(@RequestParam(name = "specialityId", required = true) int specialityId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		String str = restTemplate
				.exchange("http://localhost:9191/vet/listvet/" + specialityId, HttpMethod.GET, request, String.class)
				.getBody();
		if (str != null && str.equals("[]")) {
			mv.addObject("response", "Speciality not found");
		} else {

		ResponseEntity<List<VetDTO>> response = restTemplate.exchange("http://localhost:9191/vet/listvet/"+ specialityId,
				HttpMethod.GET, request, new ParameterizedTypeReference<List<VetDTO>>() {
				});
		List<VetDTO> listDto = response.getBody();
		List<VetsDTO> listdto = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(10);
		for (VetDTO V : listDto) {
			String pn = (df.format(V.getVetContact()));
			listdto.add(new VetsDTO(V.getVetId(),V.getVetName(),pn,V.getSpecialityId()));

		}
		mv.addObject("vetListId", response.getBody());
		}
		mv.setViewName("listbyspecId");
		return mv;
	}
	
	@PostMapping(path = "/saveVet")
	public ModelAndView saveVet(VetDTO vetdto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<VetDTO> request = new HttpEntity<>(vetdto, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/vet/addvet", HttpMethod.POST,
				request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("create_success");
		return mv;
	}
	
	@PostMapping(path = "/deleteVet")
	public ModelAndView deleteOneVet(@RequestParam(name = "vetId", required = true) int vetId) {
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<VetDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/vet/remove/" + vetId,
				HttpMethod.DELETE, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("delete_success");
		return mv;
	}
	
	@PostMapping(path = "/updateVet")
	public ModelAndView updateOneVet(@RequestParam(name = "vetId", required = true) int vetId,
			@RequestParam(name = "vetName", required = true) String vetName) {
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<VetDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:9191/vet/update/" + vetId + "/" + vetName, HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("update_success");
		return mv;
	}
	
	@PostMapping(path = "/addspeciality")
	public ModelAndView createSpeciality(SpecialityDTO specdto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<SpecialityDTO> request = new HttpEntity<>(specdto, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/speciality/addspec", HttpMethod.POST,
				request, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("create_success");
		return mv;
	}
	
	@PostMapping(path = "/deleteSpec")
	public ModelAndView deleteOneSpec(@RequestParam(name = "specialityId", required = true) int specialityId) {
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<SpecialityDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/speciality/remove/" + specialityId,
				HttpMethod.DELETE, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("delete_success");
		return mv;
	}
	
	
	@GetMapping(path = "/listspec")
	public ModelAndView findAllSpec() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<SpecialityDTO>> response = restTemplate.exchange("http://localhost:9191/speciality/list",
				HttpMethod.GET, request, new ParameterizedTypeReference<List<SpecialityDTO>>() {
				});
		mv.addObject("specList", response.getBody());
		mv.setViewName("listspec");
		return mv;
	}
	
	
	@GetMapping(path = "/findSpec")
	public ModelAndView findSpecById(@RequestParam(name = "specialityId", required = true) int specialityId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<SpecialityDTO> response = restTemplate.exchange("http://localhost:9191/speciality/find/" +specialityId,
				HttpMethod.GET, request, SpecialityDTO.class);
		mv.addObject("speciality", response.getBody());
		mv.setViewName("findSpec");
		return mv;
	}

}
