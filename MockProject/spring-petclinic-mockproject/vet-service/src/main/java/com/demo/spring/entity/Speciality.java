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
@Table(name="SPECIALITY")
public class Speciality {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="speciality_id")
	@SequenceGenerator(sequenceName = "speciality_sequence",initialValue = 200, name="speciality_id" )
	@Column(name="SPECIALITYID")
	private Integer specialityId;

	
	@Column(name="SPECIALITYNAME")
	private String specialityName;
	
	@OneToMany
	@JoinColumn(name="SPECIALITYID")
	private List<Vet> vets;
	
	public Speciality() {
		
	}

	public Speciality(Integer specialityId, String specialityName) {
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
