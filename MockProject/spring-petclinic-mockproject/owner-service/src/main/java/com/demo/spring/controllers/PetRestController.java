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
import org.springframework.web.bind.annotation.RestController;


import com.demo.spring.entity.Pet;
import com.demo.spring.entity.PetDTO;
import com.demo.spring.exceptions.PetAlreadyExistsException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.service.PetService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping("/pet")
public class PetRestController {

	@Autowired
	PetService petService;
	
	@GetMapping(path="/findp/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.findpetbyid")
	public ResponseEntity<Pet> findPet(@PathVariable("id") Integer petId) throws PetNotFoundException{
		return ResponseEntity.ok(petService.findPetById(petId));
	}
	
	@GetMapping(path="/list/",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listpets")
	public ResponseEntity<List<Pet>> listAllPets(){
		return ResponseEntity.ok(petService.listAllPets());
	}
	
	@PostMapping(path="/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.createpet")
	public ResponseEntity<Message> createOnePet(@RequestBody PetDTO petdto) throws PetAlreadyExistsException{
		return ResponseEntity.ok(petService.createOnePet(petdto));
	}
	
	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<Message> handlePetNotFoundException(PetNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Pet not found"));
	}
	
	@ExceptionHandler(PetAlreadyExistsException.class)
	public ResponseEntity<Message> handlePetAlreadyExistsException(PetAlreadyExistsException ex){
		return ResponseEntity.status(404).body(new Message("Pet already exists"));
	}
}
