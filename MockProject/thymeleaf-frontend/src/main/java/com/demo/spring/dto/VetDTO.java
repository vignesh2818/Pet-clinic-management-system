package com.demo.spring.dto;

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

	public void setVetId(Integer vetId) {
		this.vetId = vetId;
	}

	public String getVetName() {
		return vetName;
	}

	public void setVetName(String vetName) {
		this.vetName = vetName;
	}

	public Double getVetContact() {
		return vetContact;
	}

	public void setVetContact(Double vetContact) {
		this.vetContact = vetContact;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}
	
}
