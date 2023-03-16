package com.lifeline.lifeline2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lifeline.lifeline2.services.ManagerService;


@Controller
public class ManagerController {
	
	@Autowired
	private ManagerService managerService =  new ManagerService();
	
	@GetMapping("/manager")
	public String goManager() {
		return "manager";
	}
	
	
}
