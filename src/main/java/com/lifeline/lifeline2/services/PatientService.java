package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.repositories.PatientRepository;


@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository ;
	
	public void saveUser(Patient patient) {
		System.out.println("saveUser:: patient is >> "+patient);
		patientRepository.save(patient);
	}
	
	public Patient getPatient(String pid) {
		System.out.println("getPatient:: Going to fetch details of "+pid);
		Patient p = patientRepository.getReferenceById(pid);
		System.out.println("Fetched patient is :"+p);
		return p;
	}
	
//	public Patient updatePatientProfile(String address,String cont, String sym, String hist, String email) {
//		
//		return patientRepository.updatePatientProfile(address, cont, sym, hist, email);
//	}
}
