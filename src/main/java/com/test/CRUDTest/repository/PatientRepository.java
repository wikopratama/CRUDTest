package com.test.CRUDTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.CRUDTest.model.Patient;

	@Repository
	public interface PatientRepository extends JpaRepository<Patient, Long>{

}