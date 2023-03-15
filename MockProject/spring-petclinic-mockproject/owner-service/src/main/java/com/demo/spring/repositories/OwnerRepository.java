package com.demo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

	@Query("update Owner o set o.city=:city where o.ownerId=:ownerId")
	@Modifying
	@Transactional
	public int updateOwnerCity(Integer ownerId,String city);
	
}
