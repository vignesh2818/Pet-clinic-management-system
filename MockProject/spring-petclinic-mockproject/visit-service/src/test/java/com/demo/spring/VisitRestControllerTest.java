package com.demo.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.entity.PetDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.entity.VisitDTO;
import com.demo.spring.repositories.VisitRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.Mockito.when;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"ownerServer=http://localhost:${wiremock.server.port}",
		"visitServer=http://localhost:${wiremock.server.port}" })
@AutoConfigureWireMock(port = 0)

class VisitRestControllerTest {

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	int port;

	@MockBean
	VisitRepository visitRepo;

	@Test
	void testlistVisitsForPetSuccess() throws Exception {

		List<Visit> visits = new ArrayList<>();
		visits.add(new Visit(500, 102, 3, "injury", 200, "17-11-2022_9:30"));
		PetDTO petdto = new PetDTO(3, "chintu", "cat", 102, visits);
		String petJson = new ObjectMapper().writeValueAsString(petdto);

		stubFor(get(urlEqualTo("/pet/findp/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(petJson)));

		when(visitRepo.findByPetId(3)).thenReturn(visits);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<List<Visit>> response = testRestTemplate.exchange("http://localhost:" + port + "/visit/list/3",
				HttpMethod.GET, req, new ParameterizedTypeReference<List<Visit>>() {
				});
		System.out.println(response.getBody());
		Assertions.assertEquals(1, response.getBody().size());
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	 void testlistVisitsForPetFailure() throws Exception {
		Message message = new Message("Pet not found");
		String messageJson = new ObjectMapper().writeValueAsString(message);
		VisitDTO visitDto = new VisitDTO(500, 102, 3, "injury", 200, "17-11-2022_9:30");
		Visit visit = new Visit(visitDto.getVisitId(), visitDto.getOwnerId(), visitDto.getPetId(),
				visitDto.getSymptoms(), visitDto.getSpecialityId(), visitDto.getDatetime());
		List<Visit> visitsL = new ArrayList<>();
		visitsL.add(visit);
		stubFor(get(urlEqualTo("/pet/findp/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<VisitDTO> req = new HttpEntity<>(visitDto, headers);
		ResponseEntity<String> response2 = testRestTemplate.exchange("http://localhost:" + port + "/visit/list/3",
				HttpMethod.GET, req, String.class);
		System.out.println(response2.getBody());
		Assertions.assertTrue(response2.getBody().contains("Pet not found"));
	}

	@Test
	void testaddVisitSuccess() throws Exception {
		VisitDTO visitDto = new VisitDTO(500, 102, 3, "injury", 200, "17-11-2022_9:30");
		Visit visit = new Visit(visitDto.getVisitId(), visitDto.getOwnerId(), visitDto.getPetId(),
				visitDto.getSymptoms(), visitDto.getSpecialityId(), visitDto.getDatetime());
		List<Visit> visitsL = new ArrayList<>();
		visitsL.add(visit);
		PetDTO petdto = new PetDTO(3, "jockey", "Cat", 102, visitsL);
		String petJson = new ObjectMapper().writeValueAsString(petdto);
		stubFor(get(urlEqualTo("/pet/findp/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(petJson)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<VisitDTO> req = new HttpEntity<>(visitDto, headers);
		ResponseEntity<String> response2 = testRestTemplate.exchange("http://localhost:" + port + "/visit/create",
				HttpMethod.POST, req, String.class);
		System.out.println(response2.getBody());
		Assertions.assertTrue(response2.getStatusCode().is2xxSuccessful());
	}

	@Test
	void testaddVisitFailure() throws Exception {
		Message message = new Message("Pet not found");
		String msg = new ObjectMapper().writeValueAsString(message);
		List<Visit> visitsL = new ArrayList<>();
		stubFor(get(urlEqualTo("/pet/findp/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(msg)));
		VisitDTO visitDto = new VisitDTO(500, 102, 39, "injury", 200, "17-11-2022_9:30");
		Visit visit = new Visit(visitDto.getVisitId(), visitDto.getOwnerId(), visitDto.getPetId(),
				visitDto.getSymptoms(), visitDto.getSpecialityId(), visitDto.getDatetime());
		visitsL.add(visit);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Visit> req = new HttpEntity<>(visit, headers);
		ResponseEntity<String> response2 = testRestTemplate.exchange("http://localhost:" + port + "/visit/create",
				HttpMethod.POST, req, String.class);
		System.out.println(response2.getBody());
		Assertions.assertTrue(response2.getBody().contains("Pet not found"));
	}

	@Test
	void testListVisitsForListOfPetIdSuccess() throws Exception {

		List<Integer> listId = new ArrayList<>();
		listId.add(3);
		Visit visit = new Visit(500, 102, 3, "injury", 200, "17-11-2022_9:30");
		List<Visit> visits = new ArrayList<>();
		visits.add(visit);
		PetDTO petdto = new PetDTO(3, "jockey", "Cat", 102, visits);
		String petJson = new ObjectMapper().writeValueAsString(petdto);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req1 = new HttpEntity<>(headers);

		stubFor(get(urlEqualTo("/pet/findp/3"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(petJson)));
		when(visitRepo.findByPetId(3)).thenReturn(visits);
		ResponseEntity<List<PetDTO>> response = testRestTemplate.exchange(
				"http://localhost:" + port + "/visit/list?petId=3", HttpMethod.GET, req1,
				new ParameterizedTypeReference<List<PetDTO>>() {
				});
		System.out.println(response.getBody().get(0));
		Assertions.assertEquals(1, response.getBody().size());
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	
}
