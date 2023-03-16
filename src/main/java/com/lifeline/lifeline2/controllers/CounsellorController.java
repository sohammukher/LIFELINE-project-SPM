package com.lifeline.lifeline2.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.services.CounsellorService;
import com.lifeline.lifeline2.services.LoginService;


@Controller
public class CounsellorController {
	
	@Autowired
	private CounsellorService counsellorService =  new CounsellorService();
	@Autowired
	private LoginService loginService = new LoginService();
	
	@GetMapping("/addCounsellor")
	public String addCounsellor  (@RequestParam String firstName, @RequestParam String lastName,
							 @RequestParam String email, @RequestParam String address1,
							 @RequestParam String address2, @RequestParam String mobileNumber,
							 @RequestParam String password, @RequestParam Date birthday,
							 @RequestParam String registrationNumber) {
		System.out.println("Going to add a Counsellor > "+firstName);
		Counsellor counsellor = new Counsellor();
		
		counsellor.setFname(firstName);
		counsellor.setLname(lastName);
		counsellor.setEmail(email);
		counsellor.setMobile(Long.parseLong(mobileNumber));
		String address = address1 +" "+address2;
		counsellor.setAddr(address);
		counsellor.setCid(registrationNumber);
		counsellor.setBirthday(birthday);
		System.out.println("Counsellor info : "+counsellor);
		
		counsellorService.saveUser(counsellor);
		
		loginService.saveLoginCreds(email, password, "C");

		return "redirect:/user_login";	
		}
	
	@GetMapping("/counsellor/{cid}")
	public String goCounsellor(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		System.out.println("goCounsellor:: The counsellor cid is : "+cid);
		return "counsellor";
	}
	
	public String goCounsellorProfile(String id, Model model) {
		System.out.println("The Counsellor id is : "+id);
		Counsellor counsellor = counsellorService.getCounsellor(id);
		System.out.println(counsellor);
		model.addAttribute("type", "C");
		model.addAttribute(counsellor);
		return "profile";
	}	
	
}
