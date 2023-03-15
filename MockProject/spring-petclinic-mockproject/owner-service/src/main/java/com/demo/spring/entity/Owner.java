package com.demo.spring.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MYOWNER")
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="owner_id")
	@SequenceGenerator(sequenceName = "owner_sequence",initialValue = 101, name="owner_id" )
	@Column(name="OWNERID")
	private Integer ownerId;
	
	@Column(name="OWNERNAME")
	private String ownerName;
	
	@Column(name="CITY")
	private String city;
	
	@OneToMany
	@JoinColumn(name="OWNERID")
	private List<Pet> pets;
	
	public Owner() {
		
	}
	
	public Owner(Integer ownerId,String ownerName,String city) {
		this.ownerId=ownerId;
		this.ownerName = ownerName;
		this.city=city;
	}
	

	public Owner(Integer ownerId, String ownerName, String city, List<Pet> pets) {
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.city = city;
		this.pets = pets;
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

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	
}
