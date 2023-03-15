package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.Vet;
import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.repositories.VetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VetRestControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	VetRepository vetRepo;
	
	@MockBean
	SpecialityRepository specRepo;
	
	@Test
	void testfindVetSuccess() throws Exception {
		Vet vet = new Vet(70,"Vignesh",8933451879.0,200);
		when(vetRepo.findById(70)).thenReturn(Optional.of(vet));

		mvc.perform(get("/vet/find/70")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.vetName").value("Vignesh"));
	}
	
	@Test
	void testfindVetFailure() throws Exception {
		Vet vet = new Vet(84,"Vignesh",8933451879.0,290);
		when(vetRepo.findById(84)).thenReturn(Optional.empty());

		mvc.perform(get("/vet/find/84")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Not Found"));
	}
	
	@Test
	void testListAllVets() throws Exception {
		List<Vet> vetList = new ArrayList<>();
		vetList.add(new Vet());
		when(vetRepo.findAll()).thenReturn(vetList);
		mvc.perform(get("/vet/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}
	
	@Test
	void testListVetsByspecIdSuccess() throws Exception {
		List<Vet> vetList = new ArrayList<>();
		Speciality speciality = new Speciality(200,"ortho");
		when(specRepo.findById(200)).thenReturn(Optional.of(speciality));
		vetList.add(new Vet());
		when(vetRepo.findAllBySpecId(200)).thenReturn(vetList);
		mvc.perform(get("/vet/listvet/200")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}
	
	@Test
	void testListVetsByspecIdFailure() throws Exception {
		List<Vet> vetList = new ArrayList<>();
		//Speciality speciality = new Speciality(8900,"ortho");
		when(specRepo.findById(200)).thenReturn(Optional.empty());
		vetList.add(new Vet());
		when(vetRepo.findAllBySpecId(200)).thenReturn(vetList);
		mvc.perform(get("/vet/listvet/8900")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Found"));

	}
	
	@Test
	void testAddVetSuccess() throws Exception {
		Vet vet = new Vet(84,"Vignesh",8933451879.0,200);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);

		when(vetRepo.existsById(84)).thenReturn(false);

		mvc.perform(post("/vet/addvet").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Added"));
	}
	
	@Test
	void testAddVetFailure() throws Exception {
		Vet vet = new Vet(77,"Vignesh",8933451879.0,200);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);

		when(vetRepo.existsById(77)).thenReturn(true);

		mvc.perform(post("/vet/addvet").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Already Exists"));
	}
	
	@Test
	void testremoveVetSuccess() throws Exception {
		when(vetRepo.existsById(73)).thenReturn(true);
		mvc.perform(delete("/vet/remove/73").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Removed"));
	}
	
	@Test
	void testremoveVetFailure() throws Exception {
		when(vetRepo.existsById(99)).thenReturn(false);
		mvc.perform(delete("/vet/remove/99").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Not Found"));
	}
	
	@Test
	void testupdateVetNameSuccess() throws Exception {
		Vet vet = new Vet(79,"Vignesh",6923571287.0,205);
		when(vetRepo.findById(79)).thenReturn(Optional.of(vet));
		when(vetRepo.updateName(79,"Raghu")).thenReturn(1);
		
		mvc.perform(patch("/vet/update/79/Raghu"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Name Updated"));
		
	}
	
	@Test
	void testupdateVetNameFailure() throws Exception {
		when(vetRepo.findById(79)).thenReturn(Optional.empty());
		when(vetRepo.updateName(79,"Raghu")).thenReturn(1);
		
		mvc.perform(patch("/vet/update/79/Raghu"))
		.andDo(print())
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("Vet Not Found"));
		
	}
	
	
}
