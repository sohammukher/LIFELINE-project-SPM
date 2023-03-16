package com.lifeline.lifeline2.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Doctor;
import com.lifeline.lifeline2.services.DoctorService;
import com.lifeline.lifeline2.services.LoginService;


@Controller
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService =  new DoctorService();
	@Autowired
	private LoginService loginService = new LoginService();
	
	@GetMapping("/addDoctor")
	public String addDoctor  (@RequestParam String firstName, @RequestParam String lastName,
							 @RequestParam String email, @RequestParam String address1,
							 @RequestParam String address2, @RequestParam String mobileNumber,
							 @RequestParam String password, @RequestParam Date birthday,
							 @RequestParam String registrationNumber) {
		System.out.println("Going to add a Doctor > "+firstName +"  "+mobileNumber);
		Doctor doctor = new Doctor();
		
		doctor.setFname(firstName);
		doctor.setLname(lastName);
		doctor.setEmail(email);
		doctor.setMobile(Long.parseLong(mobileNumber));
		String address = address1 +" "+address2;
		doctor.setAddr(address);
		doctor.setDid(registrationNumber);
		doctor.setBirthday(birthday);
		System.out.println("Doctor info : "+doctor);
		
		doctorService.saveUser(doctor);
		
		loginService.saveLoginCreds(email, password, "D");

		return "redirect:/user_login";	
		}
	
	@GetMapping("/doctor/{cid}")
	public String goDoctor(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		System.out.println("goDoctor:: The doctor cid is : "+cid);
		return "doctor";
	}
	
	public String goDoctorProfile(String id, Model model) {
		System.out.println("The Doctor id is : "+id);
		Doctor doctor = doctorService.getDoctor(id);
		System.out.println(doctor);
		model.addAttribute("type", "D");
		model.addAttribute(doctor);
		return "profile";
	}	

	
	
}
