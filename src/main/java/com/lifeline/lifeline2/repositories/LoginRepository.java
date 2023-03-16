package com.lifeline.lifeline2.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifeline.lifeline2.models.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String>{
	
	//TO get user password from table
		@Query("SELECT us from user_login us where us.user_id=?1")
		public Login getLoginByuid(String userid);
}
