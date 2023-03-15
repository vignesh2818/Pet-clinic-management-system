package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.demo.spring.repositories.SpecialityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpecilaityRestControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	SpecialityRepository specRepo;
	
	@Test
	void testAddSpecSuccess() throws Exception {
		Speciality speciality=new Speciality(207,"radiology");
		ObjectMapper mapper = new ObjectMapper();
		String specialityJson = mapper.writeValueAsString(speciality);

		when(specRepo.existsById(207)).thenReturn(false);

		mvc.perform(post("/speciality/addspec").content(specialityJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Added"));
	}
	
	@Test
	void testAddSpecFailure() throws Exception {
		Speciality speciality=new Speciality(203,"radiology");
		ObjectMapper mapper = new ObjectMapper();
		String specialityJson = mapper.writeValueAsString(speciality);

		when(specRepo.existsById(203)).thenReturn(true);

		mvc.perform(post("/speciality/addspec").content(specialityJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Already Exists"));
	}
	
	@Test
	void testremoveSpecSuccess() throws Exception {
		when(specRepo.existsById(202)).thenReturn(true);
		mvc.perform(delete("/speciality/remove/202").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Removed"));
	}
	
	@Test
	void testremoveSpecFailure() throws Exception {
		when(specRepo.existsById(2030)).thenReturn(false);
		mvc.perform(delete("/speciality/remove/2030").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Found"));
	}
	@Test
	void testListAllSpecilaity() throws Exception {
		List<Speciality> specList = new ArrayList<>();
		specList.add(new Speciality());
		when(specRepo.findAll()).thenReturn(specList);
		mvc.perform(get("/speciality/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	void testfindSpecSuccess() throws Exception {
		Speciality speciality=new Speciality(203,"radiology");
		when(specRepo.findById(203)).thenReturn(Optional.of(speciality));

		mvc.perform(get("/speciality/find/203")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.specialityName").value("radiology"));
	}
	
	@Test
	void testfindSpecFailure() throws Exception {
		when(specRepo.findById(2030)).thenReturn(Optional.empty());

		mvc.perform(get("/speciality/find/2030")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Found"));
	}
	

}
