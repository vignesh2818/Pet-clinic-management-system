package com.demo.spring.dto;

import java.util.List;

public class SpecialityDTO {

    private Integer specialityId;
	
	private String specialityName;
	
	private List<VetDTO> vets;
	
	public SpecialityDTO() {
		
	}

	public SpecialityDTO(Integer specialityId, String specialityName) {
		this.specialityId = specialityId;
		this.specialityName = specialityName;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public List<VetDTO> getVets() {
		return vets;
	}

	public void setVets(List<VetDTO> vets) {
		this.vets = vets;
	}
	
}
