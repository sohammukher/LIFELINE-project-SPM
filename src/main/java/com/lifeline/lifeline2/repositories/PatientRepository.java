package com.lifeline.lifeline2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifeline.lifeline2.models.Login;
import com.lifeline.lifeline2.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String>{

	
	//TO update patient information
//			@Query("UPDATE patient set address=?1, contact = ?2, symptoms= ?3, past_medical_hist=?4 where email=?5")
//			public Patient updatePatientProfile(String address,String cont, String sym, String hist, String email);
}
