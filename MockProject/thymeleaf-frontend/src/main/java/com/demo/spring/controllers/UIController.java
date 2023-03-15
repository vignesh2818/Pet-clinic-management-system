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

import com.demo.spring.dto.OwnerDTO;
import com.demo.spring.dto.PetDTO;

@Controller
public class UIController {

	//String msg = "message";
	@Autowired
	RestTemplate restTemplate;

	// Owner-Pet UI Controllers
	@GetMapping(path = "/findone")
	public ModelAndView findById(@RequestParam(name = "ownerId", required = true) int ownerId) {
		String omsg = "Owner not Found";
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<OwnerDTO> response = restTemplate.exchange("http://localhost:9191/owner/find/" + ownerId,
				HttpMethod.GET, request, OwnerDTO.class);
		if (response.getBody().getOwnerName() != null) {
			mv.addObject("own", response.getBody());
			mv.setViewName("findone");
		} else {
			mv.addObject("msg", omsg);
			mv.setViewName("findowner_failed");
		}
		return mv;
	}

	@GetMapping(path = "/findonepet")
	public ModelAndView findByPetId(@RequestParam(name = "petId", required = true) int petId) {
		ModelAndView mv = new ModelAndView();
		String pmsg="Pet not found";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PetDTO> response = restTemplate.exchange("http://localhost:9191/pet/findp/" + petId,
				HttpMethod.GET, request, PetDTO.class);
		if(response.getBody().getPetName()!=null) {
			mv.addObject("pet", response.getBody());
			mv.setViewName("findonepet");
		}else {
			mv.addObject("msg", pmsg);
			mv.setViewName("findpet_failed");
		}
		
		return mv;
	}

	@GetMapping(path = "/list")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<List<PetDTO>>> response = restTemplate.exchange("http://localhost:9191/owner/",
				HttpMethod.GET, request, new ParameterizedTypeReference<List<List<PetDTO>>>() {
				});

		mv.addObject("petList", response.getBody());
		mv.setViewName("list");
		return mv;
	}

	@PostMapping(path = "/createOwner")
	public ModelAndView createOwner(OwnerDTO ownerdto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<OwnerDTO> request = new HttpEntity<>(ownerdto, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/owner/create", HttpMethod.POST,
				request, String.class);
		if (response.getBody().contains("Owner already exists")) {
			mv.addObject("response", response.getBody());
			mv.setViewName("create_failed");
			return mv;
		} else {
			mv.addObject("response", response.getBody());
			mv.setViewName("create_success");
			return mv;
		}
	}

	@PostMapping(path = "/deleteOwner")
	public ModelAndView deleteOneOwner(@RequestParam(name = "ownerId", required = true) int ownerId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<OwnerDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/owner/delete/" + ownerId,
				HttpMethod.DELETE, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("delete_success");
		return mv;
	}

	@PostMapping(path = "/updateOwner")
	public ModelAndView updateOwner(@RequestParam(name = "ownerId", required = true) int ownerId,
			@RequestParam(name = "city", required = true) String city) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<OwnerDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:9191/owner/update/" + ownerId + "/" + city, HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("update_success");
		return mv;
	}

	@PostMapping(path = "/addpet")
	public ModelAndView addPetToOwner(PetDTO petDto, int ownerId) {
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PetDTO> request = new HttpEntity<>(petDto, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/owner/add/" + ownerId,
				HttpMethod.POST, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("pet_added");
		return mv;
	}
	

	@PostMapping(path = "/deletepet")
	public ModelAndView deletepet(@RequestParam(name = "petId", required = true) int petId,
			@RequestParam(name = "ownerId", required = true) int ownerId) {
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<OwnerDTO> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:9191/owner/delete/" + petId + "/" + ownerId, HttpMethod.DELETE, request,
				String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("pet_deleted");
		return mv;
	}

	@GetMapping(path = "/listall")
	public ModelAndView findAllPet() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<PetDTO>> response = restTemplate.exchange("http://localhost:9191/pet/list/", HttpMethod.GET,
				request, new ParameterizedTypeReference<List<PetDTO>>() {
				});

		mv.addObject("petList", response.getBody());
		mv.setViewName("listall");
		return mv;
	}

	@PostMapping(path = "/createPet")
	public ModelAndView createOnePet(PetDTO petdto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PetDTO> request = new HttpEntity<>(petdto, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9191/pet/create", HttpMethod.POST,
				request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("create_success");
		return mv;
	}

}
