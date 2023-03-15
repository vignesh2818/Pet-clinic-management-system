package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT")
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_id")
	@SequenceGenerator(sequenceName = "visit_sequence", initialValue = 500, name = "visit_id")
	@Column(name = "VISITID")
	private Integer visitId;

	@Column(name = "OWNERID")
	private Integer ownerId;

	@Column(name = "PETID")
	private Integer petId;

	@Column(name = "SYMPTOMS")
	private String symptoms;

	@Column(name = "SPECIALITYID")
	private Integer specialityId;

	@Column(name = "DATETIME")
	private String datetime;

	public Visit() {

	}

	public Visit(Integer visitId, Integer ownerId, Integer petId, String symptoms, Integer specialityId,
			String datetime) {
		this.visitId = visitId;
		this.ownerId = ownerId;
		this.petId = petId;
		this.symptoms = symptoms;
		this.specialityId = specialityId;
		this.datetime = datetime;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public Integer getPetId() {
		return petId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public String getDatetime() {
		return datetime;
	}

}
