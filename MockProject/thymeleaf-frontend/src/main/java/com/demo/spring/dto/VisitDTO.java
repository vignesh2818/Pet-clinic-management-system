package com.demo.spring.dto;


public class VisitDTO {

	private Integer visitId;
	
	
	private Integer ownerId;
	

	private Integer petId;
	
	
	private String symptoms;
	

	private Integer specialityId;
	
	
	private String datetime;
	
	
	
	public VisitDTO() {
		
	}

	public VisitDTO(Integer visitId, Integer ownerId, Integer petId, String symptoms, Integer specialityId,
			String datetime) {
		this.visitId = visitId;
		this.ownerId = ownerId;
		this.petId = petId;
		this.symptoms = symptoms;
		this.specialityId = specialityId;
		this.datetime = datetime;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
