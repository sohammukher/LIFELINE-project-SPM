package com.lifeline.lifeline2.repositories;


import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeline.lifeline2.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

	//TO get user password from table
//	@Query(value = "SELECT * FROM self_assessment HAVING email = ?1 ORDER BY self_assessment_id DESC LIMIT 1", nativeQuery = true)
//	public AssessmentDetails getAssessmentDetailsbyEmail(String email);
			
	@Query("SELECT ap FROM appointment ap WHERE ap.patient_id = ?1")
	public List<Appointment> findBypid(String pid);

}
