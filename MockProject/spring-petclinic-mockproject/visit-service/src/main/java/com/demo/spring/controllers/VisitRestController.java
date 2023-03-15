package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.PetDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.entity.VisitDTO;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.service.VisitService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping("/visit")

public class VisitRestController {

	@Autowired
	VisitService visitService;

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.createvisit")
	public ResponseEntity<Message> createVisit(@RequestBody VisitDTO visitdto) throws PetNotFoundException {
		return ResponseEntity.ok(visitService.createVisit(visitdto));
	}

	@GetMapping(path = "/list/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listvisitbypetid")
	public ResponseEntity<List<Visit>> listVisitsForPet(@PathVariable("petId") int petId) throws PetNotFoundException {
		return ResponseEntity.ok(visitService.listVisitsForPet(petId));
	}

	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listvisits")
	public ResponseEntity<List<PetDTO>> listVistsBylistofPetId(@RequestParam("petId") List<Integer> petIds)
			throws PetNotFoundException {
		return ResponseEntity.ok(visitService.listVistsBylistofPetId(petIds));

	}
	
	@GetMapping(path="/listvisit",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listallvisits")
	public ResponseEntity<List<Visit>> listVets(){
		return ResponseEntity.ok(visitService.listAllVisits());
	}

	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<Message> handlePetNotFoundException(PetNotFoundException ex) {
		return ResponseEntity.status(404).body(new Message("Pet not found"));
	}

}
