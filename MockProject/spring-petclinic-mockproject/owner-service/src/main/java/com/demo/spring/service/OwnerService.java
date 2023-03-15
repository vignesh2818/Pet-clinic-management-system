package com.demo.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.OwnerDTO;
import com.demo.spring.entity.Pet;
import com.demo.spring.entity.PetDTO;
import com.demo.spring.repositories.OwnerRepository;
import com.demo.spring.repositories.PetRepository;
import com.demo.spring.util.Message;
import com.demo.spring.exceptions.OwnerExistsException;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetAlreadyExistsException;
import com.demo.spring.exceptions.PetNotFoundException;

@Service
public class OwnerService {

	private Logger logger= LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	OwnerRepository ownerRepo;

	@Autowired
	PetRepository petRepo;

	// Can search owner based on its id
	public Owner findOwnerById(int ownerId) throws OwnerNotFoundException {
		Optional<Owner> ownerOp = ownerRepo.findById(ownerId);
		if (ownerOp.isPresent()) {
			logger.info("Retrieving details of the Owner with ownerid{}",ownerId);
			return ownerOp.get();
		} else {
			logger.error("Owner is not registered!!!");
			throw new OwnerNotFoundException();
		}
	}

	// Can list all owners with their pets
	public List<List<Pet>> listAllOwnerWithPets() {
		List<Owner> owners = ownerRepo.findAll();
		List<List<Pet>> ownerList = owners.stream().map(Owner::getPets).collect(Collectors.toList());
		logger.info("List of all oweners with their pets retrived successfully..");
		return ownerList;
	}

	// Delete Owner
	public Message deleteOwner(Integer ownerId) throws OwnerNotFoundException {
		if (ownerRepo.existsById(ownerId)) {
			ownerRepo.deleteById(ownerId);
			logger.info("Owner has been deleted successfully with ownerId:{}",ownerId);
			return new Message("Owner Deleted");
		} else {
			logger.error("Owner doesnt exist!!!");
			throw new OwnerNotFoundException();
		}
	}

	// Update city of Owner
	public Message updateOwnerCity(int ownerId, String city) throws OwnerNotFoundException {
		Optional<Owner> ownerOp = ownerRepo.findById(ownerId);
		if (ownerOp.isPresent()) {
			ownerRepo.updateOwnerCity(ownerId, city);
			logger.info("Owner with ownerId:{} has updated city:{}",ownerId,city);
			return new Message("city updated");
		} else {
			logger.error("Owner has not been registered!!!");
			throw new OwnerNotFoundException();
		}
	}
	

	// create a new owner
	public Message createOneOwner(OwnerDTO ownerdto) throws OwnerExistsException {
		if (ownerRepo.existsById(ownerdto.getOwnerId())) {
			logger.error("Owner is already present");
			throw new OwnerExistsException();
		} else {
			Owner owner = new Owner(ownerdto.getOwnerId(),ownerdto.getOwnerName(),ownerdto.getCity());
			 ownerRepo.save(owner);
			 logger.info("Owner created successfully:{}",ownerdto.getOwnerName());
			 return new Message("Owner Saved");
		}
	}

	
	// add new pets to owner
	public Message addPetstoOwner(PetDTO petdto,int ownerId) throws PetAlreadyExistsException, OwnerNotFoundException {
		Optional<Owner> ownerOp = ownerRepo.findById(ownerId);
		if (ownerOp.isPresent()) {
			Owner owner = ownerOp.get();
			if (petRepo.existsById(petdto.getPetId())) {
				logger.error("Pet already exists!!");
				throw new PetAlreadyExistsException();
			} else {
				Pet pet= new Pet(petdto.getPetName(),petdto.getPetType(),petdto.getOwnerId());
				owner.getPets().add(pet);
				logger.info("Pet has been added to owner sucsessfully with petName:{}",petdto.getPetName());
				petRepo.save(pet);
				return new Message("Pet saved");
			}
		} else {
			logger.error("Owner has not been found!!!");
			throw new OwnerNotFoundException();
		}
	}
	
	

	// remove pets from owner
	public Message removePetsfromOwner(int petId, int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		boolean exist=false;
		Optional<Owner> ownerOp = ownerRepo.findById(ownerId);
		if (ownerOp.isPresent()) {
			Owner owner = ownerOp.get();
			List<Pet> pets = owner.getPets();
			for (Pet pet : pets) {
				int petid=pet.getPetId();
				if (petId==petid) {
					petRepo.delete(pet);
					logger.info("Pet with petId:{} has been removed from owner",petId);
					exist = true;
				} 
				}if(exist==false) {
					logger.error("Pet not been found");
					throw new PetNotFoundException();
			}
		} else {
			logger.error("Owner is not registered!!!");
			throw new OwnerNotFoundException();
		}
		return new Message("Pet Deleted");
	}

}
