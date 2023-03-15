package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.OwnerDTO;
import com.demo.spring.entity.Pet;
import com.demo.spring.entity.PetDTO;
import com.demo.spring.exceptions.OwnerExistsException;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetAlreadyExistsException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.service.OwnerService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping("/owner")
public class OwnerRestController {

	@Autowired
	OwnerService ownerService;
	
	@GetMapping(path="/find/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.findbyid")
	public ResponseEntity<Owner> findOwnerById(@PathVariable("id") Integer ownerId) throws OwnerNotFoundException{
		return ResponseEntity.ok(ownerService.findOwnerById(ownerId));
	}
	
	@GetMapping(path="/",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listownerspets")
	public ResponseEntity<List<List<Pet>>> listAllOwnersWithPets(){
		return ResponseEntity.ok(ownerService.listAllOwnerWithPets());
	}
	
	@DeleteMapping(path="/delete/{ownerId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.deletebyid")
	public ResponseEntity<Message> deleteOwner(@PathVariable("ownerId") Integer ownerId) throws OwnerNotFoundException{
		return ResponseEntity.ok(ownerService.deleteOwner(ownerId));
	}
	
	@PatchMapping(path="/update/{id}/{city}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.updatecity")
	public ResponseEntity<Message> updateOwnerCity(@PathVariable("id") Integer ownerId,@PathVariable("city") String city) throws OwnerNotFoundException{
		return ResponseEntity.ok(ownerService.updateOwnerCity(ownerId, city));
	}
	
	@PostMapping(path="/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.createowner")
	public ResponseEntity<Message> createOneOwner(@RequestBody OwnerDTO ownerdto) throws OwnerExistsException{
		return ResponseEntity.ok(ownerService.createOneOwner(ownerdto));
	}
	
	
	@PostMapping(path="/add/{ownerId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.addpetowner")
	public ResponseEntity<Message> addPet(@RequestBody PetDTO petdto,@PathVariable int ownerId) throws PetAlreadyExistsException, OwnerNotFoundException{
		return ResponseEntity.ok(ownerService.addPetstoOwner(petdto,ownerId));
	}

	
	@DeleteMapping(path="/delete/{petId}/{ownerId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.deletepetowner")
	public ResponseEntity<Message> deletePet(@PathVariable("petId") int petId,@PathVariable("ownerId") int ownerId) throws OwnerNotFoundException, PetNotFoundException{
		return ResponseEntity.ok(ownerService.removePetsfromOwner(petId,ownerId));
	}
	
	@ExceptionHandler(OwnerNotFoundException.class)
	public ResponseEntity<Message> handleOwnerNotFoundException(OwnerNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Owner not Found"));
	}
	
	@ExceptionHandler(OwnerExistsException.class)
	public ResponseEntity<Message> handleOwnerExistsException(OwnerExistsException ex){
		return ResponseEntity.status(404).body(new Message("Owner already exists"));
	}
	
	@ExceptionHandler(PetAlreadyExistsException.class)
	public ResponseEntity<Message> handlePetAlreadyExistsException(PetAlreadyExistsException ex){
		return ResponseEntity.status(404).body(new Message("Pet already exists"));
	}
	
	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<Message> handlePetNotFoundException(PetNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Pet not found"));
	}
}
