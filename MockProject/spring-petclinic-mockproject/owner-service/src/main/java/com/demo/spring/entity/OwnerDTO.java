package com.demo.spring.entity;

public class OwnerDTO {

	private Integer ownerId;
	private String ownerName;
	private String city;
	
	public OwnerDTO() {
		
	}

	public OwnerDTO(Integer ownerId, String ownerName, String city) {
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.city = city;
		
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
