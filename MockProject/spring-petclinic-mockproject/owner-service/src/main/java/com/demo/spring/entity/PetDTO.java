package com.demo.spring.entity;

public class PetDTO {

	private Integer petId;
	private String petName;
	private String petType;
	private Integer ownerId;
	
	public PetDTO() {
		
	}

	
	public PetDTO(String petName, String petType, Integer ownerId) {
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
	}

	public Integer getPetId() {
		return petId;
	}

	public String getPetName() {
		return petName;
	}

	public String getPetType() {
		return petType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
}
