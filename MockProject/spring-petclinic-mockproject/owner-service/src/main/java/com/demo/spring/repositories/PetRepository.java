package com.demo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

}
