package com.demo.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.entity.PetDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.entity.VisitDTO;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.repositories.VisitRepository;
import com.demo.spring.util.Message;
import com.demo.spring.util.ServerConfiguration;

@Service
@EnableConfigurationProperties(ServerConfiguration.class)
public class VisitService {

	@Autowired
	ServerConfiguration servers;
	
	@Autowired
	VisitRepository visitRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restTemplate2;

	@Autowired
	RestTemplate restTemplate3;
	
	String peturl="/pet/findp/";
	
	String accept="Accept";

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message createVisit(VisitDTO visitdto) throws PetNotFoundException {
		PetDTO petdto = null;
		int petId = visitdto.getPetId();

		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		ResponseEntity<PetDTO> response1 = restTemplate.exchange("http://owner-service"+ peturl + petId,
				HttpMethod.GET, req, PetDTO.class);
		petdto = response1.getBody();
		if (petdto.getPetName() != null) {
			Visit visit = new Visit(visitdto.getVisitId(), visitdto.getOwnerId(), visitdto.getPetId(),
					visitdto.getSymptoms(), visitdto.getSpecialityId(), visitdto.getDatetime());
			visitRepo.save(visit);
			logger.info("New visit has been created successfully");
			return new Message("Visit Saved");
		} else {
			logger.error("Pet has not been registered!!!!");
			throw new PetNotFoundException();
		}
	}

	public List<Visit> listVisitsForPet(int petId) throws PetNotFoundException {
		PetDTO petdto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req1 = new HttpEntity<>(headers);
		ResponseEntity<PetDTO> response2 = restTemplate2.exchange("http://owner-service"+ peturl + petId,
				HttpMethod.GET, req1, PetDTO.class);
		petdto = response2.getBody();
		logger.info(response2.getBody());
		if (petdto.getPetName() != null) {
			List<Visit> visitList = visitRepo.findByPetId(petId);
			logger.info("List of visits for a pet having petId:{} has been retrieved successfully",petId);
			return visitList;
		} else {
			logger.error("Pet has not been found!!!!");
			throw new PetNotFoundException();
		}
	}

	public List<PetDTO> listVistsBylistofPetId(List<Integer> petIds) throws PetNotFoundException {
		PetDTO petdto = null;
		List<PetDTO> petdtolist = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();

		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req2 = new HttpEntity<>(headers);
		for (Integer i : petIds) {
			ResponseEntity<PetDTO> response3 = restTemplate3.exchange("http://owner-service"+ peturl + i,
					HttpMethod.GET, req2, PetDTO.class);
			petdto = response3.getBody();
			if (petdto.getPetName() != null) {
				List<Visit> visitList = visitRepo.findByPetId(i);
				petdto.setVisitList(visitList);
				petdtolist.add(petdto);
			} else {
				logger.error("Pet has not been found!!!!");
				throw new PetNotFoundException();
			}
		}
		logger.info("List of visits for pets having set of petIds  has been retrieved successfully");
		return petdtolist;
	}
	
	
	public List<Visit> listAllVisits(){
		logger.info("List of Visits retrived successfully..");
		return visitRepo.findAll();
	}

}
