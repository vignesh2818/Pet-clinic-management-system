package com.demo.spring.dto;

public class PetDTO {

	private Integer petId;
	private String petName;
	private String petType;
	private Integer ownerId;
	
	public PetDTO() {
		
	}

	public PetDTO(Integer petId, String petName, String petType, Integer ownerId) {
		this.petId = petId;
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
	}
	public PetDTO( String petName, String petType, Integer ownerId) {
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
}
