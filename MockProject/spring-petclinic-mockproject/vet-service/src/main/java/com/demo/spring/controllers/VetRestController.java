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

import com.demo.spring.entity.Vet;
import com.demo.spring.entity.VetDTO;
import com.demo.spring.exceptions.SpecialityNotFoundException;
import com.demo.spring.exceptions.VetExistsException;
import com.demo.spring.exceptions.VetNotFoundException;
import com.demo.spring.service.VetService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping("/vet")
public class VetRestController {

	@Autowired
	VetService vetService;
	
	@GetMapping(path="/find/{vetId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.findvetbyid")
	public ResponseEntity<Vet> findOneVet(@PathVariable("vetId") int vetId){
		return ResponseEntity.ok(vetService.findVet(vetId));
	}
	
	@GetMapping(path="/list",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listvet")
	public ResponseEntity<List<Vet>> listVets(){
		return ResponseEntity.ok(vetService.listAllVets());
	}
	
	@GetMapping(path="/listvet/{specId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.listvetbyid")
	public ResponseEntity<List<Vet>> listVetsbySpecId(@PathVariable("specId") int specialityId) throws SpecialityNotFoundException{
		return ResponseEntity.ok(vetService.listAllVetforSpec(specialityId));
	}
	
	@PostMapping(path="/addvet",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.addvet")
	public ResponseEntity<Message> addVet(@RequestBody VetDTO vetdto) throws VetExistsException {
		return ResponseEntity.ok(vetService.addVet(vetdto));
	}
	
	@DeleteMapping(path="/remove/{vetId}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.removevet")
	public ResponseEntity<Message> deleteVet(@PathVariable("vetId") int vetId) throws VetNotFoundException{
		return ResponseEntity.ok(vetService.removeVet(vetId));
	}
	
	@PatchMapping(path="/update/{vetId}/{vetName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="requests.updatevet")
	public ResponseEntity<Message> updateVet(@PathVariable("vetId") int vetId,@PathVariable("vetName") String vetName)throws VetNotFoundException{
		return ResponseEntity.ok(vetService.updateName(vetId, vetName));
	}
	
	@ExceptionHandler(VetNotFoundException.class)
	public ResponseEntity<Message> handleVetNotFoundException(VetNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Vet Not Found"));
	}
	
	@ExceptionHandler(VetExistsException.class)
	public ResponseEntity<Message> handleVetExistsException(VetExistsException ex){
		return ResponseEntity.status(404).body(new Message("Vet Already Exists"));
	}
	

	@ExceptionHandler(SpecialityNotFoundException.class)
	public ResponseEntity<Message> handleSpecialityNotfoundException(SpecialityNotFoundException ex){
		return ResponseEntity.status(404).body(new Message("Speciality Not Found"));
	}
	
}
