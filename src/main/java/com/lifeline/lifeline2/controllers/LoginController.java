package com.lifeline.lifeline2.controllers;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifeline.lifeline2.models.Login;
import com.lifeline.lifeline2.services.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	
	@GetMapping("/validateUser")
	public String validateUser(@RequestParam String userid, @RequestParam String pwd, Model model) {
		
		Login login = new Login();
		
		login.setUid(userid.trim());
		login.setPassword(pwd.trim());
		
		Login ln = loginService.getLoginbyUser(login);
		System.out.println("DB login object : "+ln);
		
		String type="";
		try {
			type = ln.getType();
		}
		catch(Exception e) {
			type="";
		}
		Base64.Decoder simpleDecoder = Base64.getDecoder();
		String decodedString = new String(simpleDecoder.decode(ln.getPassword().getBytes()));
		System.out.println("Decoded string : "+pwd+"  -  "+decodedString);
		
	   if(!type.equals("")) {
	   if(decodedString.equals(login.getPassword())) {
		   if(ln.getApproved().equalsIgnoreCase("Y")) {
		model.addAttribute("status", "success");
		if(type.equals("P"))
			return "redirect:/patient/" + ln.getUid();
		else if(type.equals("D"))
			return "redirect:/doctor/" + ln.getUid();
		else if(type.equals("C"))
			return "redirect:/counsellor/" + ln.getUid();
		else if(type.equals("M"))
			return "redirect:/manager/" + ln.getUid();
		   }
			else {
				model.addAttribute("status", "failed");
				model.addAttribute("message", "Not yet authorised by the manager!");
			}
		}
		else {
			model.addAttribute("status", "failed");
			model.addAttribute("message", "Invalid password");
		}
		}
		else {
			model.addAttribute("status", "failed");
			model.addAttribute("message", "Invalid username");
		}
		
		return "user_login";
	}
}
