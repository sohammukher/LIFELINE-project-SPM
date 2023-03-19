package com.lifeline.lifeline2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifeline.lifeline2.models.AssessmentDetails;

public interface AssessmentDetailRepository extends JpaRepository<AssessmentDetails, Integer>{

	//TO get user password from table
	@Query(value = "SELECT * FROM self_assessment HAVING email = ?1 ORDER BY self_assessment_id DESC LIMIT 1", nativeQuery = true)
	public AssessmentDetails getAssessmentDetailsbyEmail(String email);
}
