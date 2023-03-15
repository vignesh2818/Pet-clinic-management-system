package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.SpecialityDTO;

import com.demo.spring.exceptions.SpecialityExistsException;
import com.demo.spring.exceptions.SpecialityNotFoundException;
import com.demo.spring.service.SpecialityService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping("/speciality")
public class SpecialityController {

	@Autowired
	SpecialityService specService;
	
	@PostMapping(path="/addspec",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.addspec")
	public ResponseEntity<Message> addSpec(@RequestBody SpecialityDTO specdto) throws SpecialityExistsException{
		return ResponseEntity.ok(specService.addSpeciality(specdto));
	}
	
	@DeleteMapping(path="/remove/{specId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.removespec")
	public ResponseEntity<Message> deleteVet(@PathVariable("specId") int specialityId) throws SpecialityNotFoundException{
		return ResponseEntity.ok(specService.removeSpeciality(specialityId));
	}
	
	@GetMapping(path="/list",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listspeciality")
	public ResponseEntity<List<Speciality>> listVets(){
		return ResponseEntity.ok(specService.listAllSpecialities());
	}
	
	@GetMapping(path="/find/{specId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.findspeciality")
	public ResponseEntity<Speciality> findOneSpec(@PathVariable("specId") int specialityId) throws SpecialityNotFoundException{
		return ResponseEntity.ok(specService.findSpec(specialityId));
	}

	@ExceptionHandler(SpecialityNotFoundException.class)
	public ResponseEntity<Message> handleSpecialityNotFoundException(SpecialityNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Speciality Not Found"));
	}
	
	@ExceptionHandler(SpecialityExistsException.class)
	public ResponseEntity<Message> handleSpecialityExistsException(SpecialityExistsException ex){
		return ResponseEntity.status(404).body(new Message("Speciality Already Exists"));
	}
}
