package com.demo.spring.entity;

public class VetDTO {

	private Integer vetId;
	
	private String vetName;
	
	private Double vetContact;
	
	private Integer specialityId;
	
	public VetDTO() {
		
	}

	public VetDTO(Integer vetId, String vetName, Double vetContact, Integer specialityId) {
		this.vetId = vetId;
		this.vetName = vetName;
		this.vetContact = vetContact;
		this.specialityId = specialityId;
	}

	public Integer getVetId() {
		return vetId;
	}

	public String getVetName() {
		return vetName;
	}

	public Double getVetContact() {
		return vetContact;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}
	
}
