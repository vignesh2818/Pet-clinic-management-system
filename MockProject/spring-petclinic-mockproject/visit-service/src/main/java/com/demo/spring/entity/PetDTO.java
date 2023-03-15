package com.demo.spring.entity;
import java.util.List;

public class PetDTO {

	private Integer petId;
	private String petName;
	private String petType;
	private Integer ownerId;
	private List<Visit> visitList;
	private List<Integer> petIds;
	
	public PetDTO() {
		
	}

	public PetDTO(Integer petId, String petName, String petType, Integer ownerId, List<Visit> visitList,
			List<Integer> petIds) {
		this.petId = petId;
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
		this.visitList = visitList;
		this.petIds = petIds;
	}
	
	

	public PetDTO(Integer petId, String petName, String petType, Integer ownerId, List<Visit> visitList) {
		super();
		this.petId = petId;
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
		this.visitList = visitList;
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

	public List<Visit> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<Visit> visitList) {
		this.visitList = visitList;
	}

	public List<Integer> getPetIds() {
		return petIds;
	}
}