package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Manager;
import com.lifeline.lifeline2.repositories.ManagerRepository;


@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository ;
	
	public void saveUser(Manager manager) {
		System.out.println("manager is >> "+manager);
		managerRepository.save(manager);
	}
}
