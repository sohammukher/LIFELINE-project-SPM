package com.lifeline.lifeline2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Login;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor, String>{

	//TO get user password from table
			@Query("SELECT us from counselor us where us.email=?1")
			public Counsellor getCounsellorByemail(String email);
}
