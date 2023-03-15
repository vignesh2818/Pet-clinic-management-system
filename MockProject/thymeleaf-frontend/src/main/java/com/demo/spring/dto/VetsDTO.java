package com.demo.spring.dto;

public class VetsDTO {

	private Integer vetId;
	
	private String vetName;
	
	private String vetContact;
	
	private Integer specialityId;
	
	public VetsDTO() {
		
	}

	public VetsDTO(Integer vetId, String vetName, String vetContact, Integer specialityId) {
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

	public String getVetContact() {
		return vetContact;
	}

	public void setVetContact(String vetContact) {
		this.vetContact = vetContact;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}
	
}
