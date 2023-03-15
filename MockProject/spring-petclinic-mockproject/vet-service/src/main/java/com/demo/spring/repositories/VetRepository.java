package com.demo.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.entity.Vet;

public interface VetRepository extends JpaRepository<Vet, Integer> {

	
   @Query("select v from Vet v where v.specialityId=:specialityId")
	public List<Vet> findAllBySpecId(Integer specialityId);

   @Query("select v from Vet v where v.vetId=:vetId and v.specialityId=:specialityId")
    public Optional<Vet> findBySpecialityId(int vetId ,int specialityId);
   
   @Query("update Vet v set v.vetName=:vetName where v.vetId=:vetId")
   @Modifying
   @Transactional
   public int updateName(int vetId,String vetName);
}
