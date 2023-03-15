package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.Vet;
import com.demo.spring.entity.VetDTO;
import com.demo.spring.exceptions.SpecialityNotFoundException;
import com.demo.spring.exceptions.VetExistsException;
import com.demo.spring.exceptions.VetNotFoundException;
import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.repositories.VetRepository;
import com.demo.spring.util.Message;

@Service
public class VetService {

	private Logger logger= LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	VetRepository vetRepo;
	
	@Autowired
	SpecialityRepository specRepo;
	
	// Search a vet based on speciality

	public Vet findVet(int vetId) {
		Optional<Vet> vetOp = vetRepo.findById(vetId);
		if(vetOp.isPresent()) {
			logger.info("Retrieving the details of Vet by vetId:{}",vetId);
			return vetOp.get();
		}else {
			logger.error("Vet has not been found!");
			throw new VetNotFoundException();
		}
	}
	
	//List all vets
	
	public List<Vet> listAllVets(){
		logger.info("List of Vets retrived successfully..");
		return vetRepo.findAll();
	}
	
	//List all vet for a speciality
	
	public List<Vet> listAllVetforSpec(int specialityId) throws SpecialityNotFoundException{
		Optional<Speciality> specOp = specRepo.findById(specialityId);
		if(specOp.isPresent()) {
			 logger.info("List of Vets retrived successfully for SpecialityId:{}",specialityId);
			 return vetRepo.findAllBySpecId(specialityId);
		}else {
			logger.error("Speciality not found!!");
			throw new SpecialityNotFoundException();
		}
	}
	
	 //Add a vet
	public Message addVet(VetDTO vetdto) throws  VetExistsException{
		if (vetRepo.existsById(vetdto.getVetId())) {
			logger.error("Vet already Present!!");
			throw new VetExistsException();
		} else {
			Vet vet = new Vet(vetdto.getVetId(),vetdto.getVetName(),vetdto.getVetContact(),vetdto.getSpecialityId());
			logger.info("Vet added successfully with vetName:{}",vetdto.getVetName());
			 vetRepo.save(vet);
			 return new Message("Vet Added");
		}
	}
	
	//Remove a vet
	public Message removeVet(int vetId) {
		if(vetRepo.existsById(vetId)) {
			vetRepo.deleteById(vetId);
			logger.info("Vet has been deleted successfully with vetId:{}",vetId);
			return new Message("Vet Removed");
		}else {
			logger.error("Vet has not been registered!!");
			throw new VetNotFoundException();
		}
	}
	
	//Update a vetName
    public Message updateName(int vetId,String vetName) {
    	Optional<Vet> vetOp=vetRepo.findById(vetId);
    	if(vetOp.isPresent()) {
    		vetRepo.updateName(vetId, vetName);
    		logger.info("Vet with vetId:{} has updated vetName:{}",vetId,vetName);
    		return new Message("Name Updated");
    	}else {
    		logger.error("Vet doesnt exist!!");
    		throw new VetNotFoundException();
    	}
    }
}
