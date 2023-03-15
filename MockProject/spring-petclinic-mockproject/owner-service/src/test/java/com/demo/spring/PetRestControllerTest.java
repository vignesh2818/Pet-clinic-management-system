package com.demo.spring;

import static org.mockito.Mockito.when;
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
import com.demo.spring.entity.Pet;
import com.demo.spring.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PetRestControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	PetRepository petRepo;
	
	@Test
	 void testfindPetSuccess() throws Exception {
		Pet pet = new Pet(1, "Dollar", "Dog",101);
		when(petRepo.findById(1)).thenReturn(Optional.of(pet));

		mvc.perform(get("/pet/findp/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.petName").value("Dollar"));
	}
	
	
	@Test
     void testfindPetFailure() throws Exception {
		when(petRepo.findById(160)).thenReturn(Optional.empty());

		mvc.perform(get("/pet/findp/160")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet not found"));
	}	
	
	@Test
     void testPetList() throws Exception {
        List<Pet> list = new ArrayList<>();
        list.add(new Pet(1,"Dollar", "Dog",101));
        when(petRepo.findAll()).thenReturn(list);
        mvc.perform(get("/pet/list/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
	
	@Test
	 void testSavepetSuccess() throws Exception {
		Pet pet =new Pet(15, "Dollar", "Dog",101);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);

		when(petRepo.existsById(15)).thenReturn(false);

		mvc.perform(post("/pet/create").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet Created"));
	}
	
	@Test
	 void testSavepetFailure() throws Exception {
		Pet pet =new Pet(6, "Dollar", "Dog",101);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);

		when(petRepo.existsById(6)).thenReturn(true);

		mvc.perform(post("/pet/create").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet already exists"));
	}


}
