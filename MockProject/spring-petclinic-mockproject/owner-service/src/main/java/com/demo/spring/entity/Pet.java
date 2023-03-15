package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MYPET")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="pet_id")
	@SequenceGenerator(sequenceName = "pet_sequence",initialValue = 1, name="pet_id")
	@Column(name="PETID")
    private Integer petId;
	
	@Column(name="PETNAME")
    private String petName;
	
	@Column(name="PETTYPE")
    private String petType;
	
	@Column(name="OWNERID")
    private Integer ownerId;
    
    public Pet() {
		
	}
	
    public Pet(String petName, String petType, Integer ownerId) {
		this.petName = petName;
		this.petType = petType;
		this.ownerId = ownerId;
	}
	
	
	public Pet(Integer petId,String petName, String petType, Integer ownerId) {
		this.petId=petId;
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
