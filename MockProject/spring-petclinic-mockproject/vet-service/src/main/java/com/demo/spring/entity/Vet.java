package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="VET")
public class Vet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="vet_id")
	@SequenceGenerator(sequenceName = "vet_sequence",initialValue = 70, name="vet_id" )
	@Column(name="VETID")
	private Integer vetId;
	
	@Column(name="VETNAME")
	private String vetName;
	
	@Column(name="VETCONTACT")
	private Double vetContact;
	
	@Column(name="SPECIALITYID")
	private Integer specialityId;
	
	public Vet() {
		
	}

	public Vet( String vetName, Double vetContact, Integer specialityId) {
		this.vetName = vetName;
		this.vetContact = vetContact;
		this.specialityId = specialityId;
	}
	public Vet(Integer vetId,String vetName, Double vetContact, Integer specialityId) {
		this.vetId=vetId;
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
