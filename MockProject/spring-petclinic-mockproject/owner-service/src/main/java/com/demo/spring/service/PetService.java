package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.demo.spring.entity.Pet;
import com.demo.spring.entity.PetDTO;
import com.demo.spring.exceptions.PetAlreadyExistsException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.repositories.PetRepository;
import com.demo.spring.util.Message;

@Service
public class PetService {

	private Logger logger= LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	PetRepository petRepo;

	public Pet findPetById(int petId) throws PetNotFoundException {
		Optional<Pet> petOp = petRepo.findById(petId);
		if (petOp.isPresent()) {
			logger.info("Retrieving details of the Pet with petId:{}",petId);
			return petOp.get();
		} else {
			logger.error("Pet is not registered!!");
			throw new PetNotFoundException();
		}
	}
	
	
	public List<Pet> listAllPets(){
		logger.info("List of Pets retrived successfully..");
		return petRepo.findAll();
	}
	
	public Message createOnePet(PetDTO petdto) throws  PetAlreadyExistsException {
		if (petRepo.existsById(petdto.getPetId())) {
			logger.error("Pet is already present");
			throw new PetAlreadyExistsException();
		} else {
			Pet pet=new Pet(petdto.getPetName(),petdto.getPetType(),petdto.getOwnerId());
			petRepo.save(pet);
			logger.info("Pet has been created successfully with petName:{}",petdto.getPetName());
			 return new Message("Pet Created");
		}
	}
}
