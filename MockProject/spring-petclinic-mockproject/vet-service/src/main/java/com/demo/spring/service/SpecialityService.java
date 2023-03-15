package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.SpecialityDTO;

import com.demo.spring.exceptions.SpecialityExistsException;
import com.demo.spring.exceptions.SpecialityNotFoundException;

import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.util.Message;

@Service
public class SpecialityService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	SpecialityRepository specRepo;

	// Add Speciality

	public Message addSpeciality(SpecialityDTO specialitydto) throws SpecialityExistsException {
		if (specRepo.existsById(specialitydto.getSpecialityId())) {
			logger.error("Speciality is already Present!!!");
			throw new SpecialityExistsException();
		} else {
			Speciality speciality = new Speciality(specialitydto.getSpecialityId(), specialitydto.getSpecialityName());
			logger.info("Speciality added successfully with specialityName:{}",specialitydto.getSpecialityName());
			specRepo.save(speciality);
			return new Message("Speciality Added");
		}
	}

	// Remove Speciality

	public Message removeSpeciality(int specialityId) throws SpecialityNotFoundException {
		if (specRepo.existsById(specialityId)) {
			specRepo.deleteById(specialityId);
			logger.info("Speciality has been deleted successfully with specialityId:{}",specialityId);
			return new Message("Speciality Removed");
		} else {
			logger.error("Speciality has not been registered!!");
			throw new SpecialityNotFoundException();
		}
	}

	// List all specialities

	public List<Speciality> listAllSpecialities() {
		logger.info("List of Specialities retrived successfully..");
		return specRepo.findAll();
	}

	// find speciality
	public Speciality findSpec(int specialityId) throws SpecialityNotFoundException {
		Optional<Speciality> specOp = specRepo.findById(specialityId);
		if (specOp.isPresent()) {
			logger.info("Retrieving the details of Speciality with specialityId:{}",specialityId);
			return specOp.get();
		} else {
			logger.error("Speciality has not been registered!!");
			throw new SpecialityNotFoundException();
		}
	}
}