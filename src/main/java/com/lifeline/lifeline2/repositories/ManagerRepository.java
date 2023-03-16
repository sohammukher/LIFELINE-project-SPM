package com.lifeline.lifeline2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifeline.lifeline2.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String>{

}
