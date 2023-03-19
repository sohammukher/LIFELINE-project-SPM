package com.lifeline.lifeline2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Manager;
import com.lifeline.lifeline2.repositories.ManagerRepository;


@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository ;
	//@Autowired
	//private M_DoctorRepository mdrepo;
	
	public void saveUser(Manager manager) {
		System.out.println("saveUser :: manager is >> "+manager);
		manager.setMid("");
		managerRepository.save(manager);
	}
	
//	public List<mDoctor> getDoctors() {
//		System.out.println("getDoctors :: ");
//		List<mDoctor> list_of_doctors  = mdrepo.getDoctors();
//		System.out.println(list_of_doctors.get(0).toString());
//		return list_of_doctors;
//	}
}
