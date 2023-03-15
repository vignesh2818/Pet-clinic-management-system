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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.Pet;
import com.demo.spring.repositories.OwnerRepository;
import com.demo.spring.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OwnerRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	OwnerRepository ownerRepo;

	@MockBean
	PetRepository petRepo;

	@Test
     void testfindOwnerSuccess() throws Exception {
		Owner owner = new Owner(101, "Vignesh", "Bangalore");
		when(ownerRepo.findById(101)).thenReturn(Optional.of(owner));

		mvc.perform(get("/owner/find/101")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.ownerName").value("Vignesh"));
	}

	@Test
     void testfindOwnerFailure() throws Exception {
		when(ownerRepo.findById(101)).thenReturn(Optional.empty());
		mvc.perform(get("/owner/find/101")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not Found"));
	}

	@Test
     void testSaveOwnerSuccess() throws Exception {
		Owner owner = new Owner(108, "Vignesh", "Jaipur");
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);

		when(ownerRepo.existsById(108)).thenReturn(false);

		mvc.perform(post("/owner/create").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner Saved"));
	}

	@Test
	 void testSaveOwnerFailure() throws Exception {
		Owner owner = new Owner(101, "Aman", "Lucknow");
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepo.existsById(101)).thenReturn(true);

		mvc.perform(post("/owner/create").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner already exists"));
	}

	@Test
	 void testdeleteOwnerSuccess() throws Exception {
		when(ownerRepo.existsById(105)).thenReturn(true);
		mvc.perform(delete("/owner/delete/105").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner Deleted"));
	}

	@Test
	 void testdeleteOwnerFailure() throws Exception {
		when(ownerRepo.existsById(107)).thenReturn(false);
		mvc.perform(delete("/owner/delete/107").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not Found"));
	}

	@Test
	 void testListAllOwners() throws Exception {
		List<Owner> oList = new ArrayList<>();
		oList.add(new Owner());
		when(ownerRepo.findAll()).thenReturn(oList);
		mvc.perform(get("/owner/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}


	@Test
	 void testupdateCitySuccess() throws Exception {
		Owner owner = new Owner(103, "Aman", "Ranchi");
		when(ownerRepo.findById(103)).thenReturn(Optional.of(owner));
		when(ownerRepo.updateOwnerCity(103, "Mumbai")).thenReturn(1);

		mvc.perform(patch("/owner/update/103/Mumbai")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("city updated"));

	}

	@Test
	 void testupdateCityFailure() throws Exception {
		when(ownerRepo.findById(103)).thenReturn(Optional.empty());
		when(ownerRepo.updateOwnerCity(103, "Mumbai")).thenReturn(1);

		mvc.perform(patch("/owner/update/103/Mumbai")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not Found"));

	}


	@Test
	 void testaddPetToOwnerSuccess() throws Exception {
		Pet pet = new Pet(11, "Dollar", "Dog", 104);
		List<Pet> pets = new ArrayList<>();
		pets.add(pet);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);

		when(ownerRepo.findById(104))
				.thenReturn(Optional.of(new Owner(104, "Vignesh", "Jaipur", pets)));

		mvc.perform(post("/owner/add/104").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet saved"));
	}
	
	@Test
	 void testaddPetToOwnerFailure() throws Exception {
		Pet pet = new Pet(11, "Dollar", "Dog", 111);
		List<Pet> pets = new ArrayList<>();
		pets.add(pet);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);

		when(ownerRepo.findById(111))
				.thenReturn(Optional.empty());

		mvc.perform(post("/owner/add/111").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not Found"));
	}
	
	  @Test
       void removePetfromOwnerSuccess() throws Exception{
	        Owner owner=new Owner(104, "Vignesh", "Jaipur");
	        List<Pet> pets = new ArrayList<>();
	        pets.add(new Pet(11, "Dollar", "Dog", 104));
	        owner.setPets(pets);
	        when(ownerRepo.findById(103)).thenReturn(Optional.of(owner));
	        when(petRepo.existsById(11)).thenReturn(true);
	        
	        mvc.perform(delete("/owner/delete/11/103")).andExpect(status().isOk())
	        .andExpect(jsonPath("$.status").value("Pet Deleted"));
	        
	    }
	  
	  @Test
       void removePetfromOwnerFailure() throws Exception{
	        Owner owner=new Owner(111, "Vignesh", "Jaipur");
	        List<Pet> pets = new ArrayList<>();
	        pets.add(new Pet(16, "Dollar", "Dog", 111));
	        owner.setPets(pets);
	        when(ownerRepo.findById(111)).thenReturn(Optional.empty());
	        when(petRepo.existsById(16)).thenReturn(false);
	        mvc.perform(delete("/owner/delete/16/111")).andExpect(status().isNotFound())
	        .andExpect(jsonPath("$.status").value("Owner not Found"));
	        
	    }

}
