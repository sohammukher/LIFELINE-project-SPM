package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Doctor;
import com.lifeline.lifeline2.repositories.DoctorRepository;


@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository ;
	
	public void saveUser(Doctor doctor) {
		System.out.println("doctor is >> "+doctor);
		doctorRepository.save(doctor);
	}
	
	public Doctor getDoctor(String cid) {
		System.out.println("getCounsellor:: Going to fetch details of "+cid);
		Doctor d = doctorRepository.getDoctorByemail(cid);
		System.out.println("Fetched Counsellor is :"+d);
		return d;
	}
}
