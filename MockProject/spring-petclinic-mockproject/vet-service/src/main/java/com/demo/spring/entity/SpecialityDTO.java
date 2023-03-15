package com.demo.spring.entity;

import java.util.List;

public class SpecialityDTO {

    private Integer specialityId;
	
	private String specialityName;
	
	private List<Vet> vets;
	
	public SpecialityDTO() {
		
	}

	public SpecialityDTO(Integer specialityId, String specialityName) {
		this.specialityId = specialityId;
		this.specialityName = specialityName;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}


	public String getSpecialityName() {
		return specialityName;
	}


	public List<Vet> getVets() {
		return vets;
	}
	
}
