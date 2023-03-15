package com.demo.spring.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

	@Query("select v from Visit v where v.petId=:petId")
	public List<Visit> findByPetId(int petId);
	
	
}
