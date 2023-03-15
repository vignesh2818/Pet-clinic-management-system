package com.demo.spring.entity;


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

	public Integer getOwnerId() {
		return ownerId;
	}

	public Integer getPetId() {
		return petId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public String getDatetime() {
		return datetime;
	}
}
