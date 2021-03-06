package com.example.springjavafx.repositories;

import com.example.springjavafx.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	@Query
	Doctor findByName(String name);
}
