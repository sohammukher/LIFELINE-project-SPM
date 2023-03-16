package com.lifeline.lifeline2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String>{

	//TO get user password from table
	@Query("SELECT us from doctor us where us.email=?1")
	public Doctor getDoctorByemail(String email);
}
