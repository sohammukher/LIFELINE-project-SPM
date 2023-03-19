package com.lifeline.lifeline2.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lifeline.lifeline2.services.CounsellorService;
import com.lifeline.lifeline2.services.DoctorService;
import com.lifeline.lifeline2.services.LoginService;
import com.lifeline.lifeline2.services.ManagerService;
import com.lifeline.lifeline2.services.PatientService;


@Controller
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private CounsellorService counsellorService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PatientService patientService;
	
	
	@GetMapping("/manager")
	public String goManager() {
		return "manager";
	}
	
	@GetMapping("/manager/getdoctorslist")
	public ResponseEntity<String> getDoctorsList() throws ClassNotFoundException, SQLException {
		System.out.println("getDoctorsList :: ");
		String response = doctorService.getAllDoctors();
		System.out.println("After searching doctors list.\n" + response);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/manager/getcounsellorslist")
	public ResponseEntity<String> getcounsellorslist() throws ClassNotFoundException, SQLException {
		System.out.println("getcounsellorslist :: ");
		String response = counsellorService.getAllCounsellors();
		System.out.println("After searching counsellors list.\n" + response);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/manager/getpatientslist")
	public ResponseEntity<String> getpatientslist() throws ClassNotFoundException, SQLException {
		System.out.println("getpatientslist :: ");
		String response = patientService.getAllPatients();
		System.out.println("After searching doctors list.\n" + response);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/manager/removeUser")
	public ResponseEntity<String> removeUsers(@RequestBody String req) throws ClassNotFoundException, SQLException {
		System.out.println("removeUsers :: "+req);
		String response = loginService.removeUser(req);
		System.out.println("After searching users list.\n" + response);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/manager/updateUser")
	public ResponseEntity<String> updateUsers(@RequestBody String req) throws ClassNotFoundException, SQLException {
		System.out.println("updateUsers :: "+req);
		String response = loginService.updateUser(req);
		System.out.println("After searching users list.\n" + response);
		return ResponseEntity.ok(response);
	}
	
}
