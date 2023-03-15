package com.demo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

}
