package com.demo.spring.dto;

import java.util.List;

public class OwnerDTO {

	private Integer ownerId;
	private String ownerName;
	private String city;
	private List<PetDTO> pets;
	
	public OwnerDTO() {
		
	}

	public OwnerDTO(Integer ownerId, String ownerName, String city, List<PetDTO> pets) {
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.city = city;
		this.pets = pets;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<PetDTO> getPets() {
		return pets;
	}

	public void setPets(List<PetDTO> pets) {
		this.pets = pets;
	}
	
}
