package com.lifeline.lifeline2;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifeline.lifeline2.controllers.CounsellorController;
import com.lifeline.lifeline2.controllers.DoctorController;
import com.lifeline.lifeline2.controllers.PatientController;

@Controller
public class ApplicationController {
	
	@Autowired
	PatientController patientController;
	@Autowired
	CounsellorController counsellorController;
	@Autowired
	DoctorController doctorController;

	
	
	@GetMapping("/index")
	public String goIndex() {
		return "index";
	}
	
	@GetMapping("/register")
	public String goRegister() {
		return "register";
	}
	
	@GetMapping("/user_login")
	public String goLogin() {
		return "user_login";
	}
	
	@GetMapping("/profile")
	public void goProfile(@RequestParam String id, @RequestParam String type, Model model) {
		System.out.println("The profile id is : "+id+"  "+type);
		
		if(type.equals("P"))
		{
			patientController.goPatientProfile(id, model);
		}
		else if(type.equals("C"))
		{
			counsellorController.goCounsellorProfile(id, model);
		}
		else if(type.equals("D"))
		{
			doctorController.goDoctorProfile(id, model);
		}
			
		//return "profile";
	}
	
	
}
