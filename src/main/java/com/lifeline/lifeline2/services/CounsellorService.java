package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.repositories.CounsellorRepository;


@Service
public class CounsellorService {

	@Autowired
	private CounsellorRepository counsellorRepository ;
	
	public void saveUser(Counsellor counsellor) {
		System.out.println("counsellor is >> "+counsellor);
		counsellorRepository.save(counsellor);
	}
	
	public Counsellor getCounsellor(String cid) {
		System.out.println("getCounsellor:: Going to fetch details of "+cid);
		Counsellor c = counsellorRepository.getCounsellorByemail(cid);
		System.out.println("Fetched Counsellor is :"+c);
		return c;
	}
}
