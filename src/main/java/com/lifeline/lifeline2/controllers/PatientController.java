package com.lifeline.lifeline2.controllers;

import java.sql.Date;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.services.LoginService;
import com.lifeline.lifeline2.services.PatientService;


@Controller
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/saveSelfAssessment")
	public ResponseEntity<String> saveSelfAssessment(@RequestBody String assessment_details) throws JsonMappingException, JsonProcessingException, ClassNotFoundException, SQLException {
		System.out.println("Reached save patient :: "+assessment_details);
		String response = patientService.saveSelfAssessment(assessment_details);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/pastSelfAssesments")
	public ResponseEntity<String> pastSelfAssesments(@RequestBody String id) throws JsonMappingException, JsonProcessingException, ClassNotFoundException, SQLException {
		System.out.println("pastSelfAssesments :: For "+id);
		String response = patientService.getPastAssessments(id);
		 return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getAppointments")
	public ResponseEntity<String> getAppointments(@RequestBody String id) throws JsonMappingException, JsonProcessingException {
		System.out.println("showAppointments :: For "+id);
		String response = patientService.getAppointments(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/updateAppointments")
	public ResponseEntity<String> updateAppointments(@RequestBody String req){
		System.out.println("updateAppointments :: "+req);
		String response = patientService.updateAppointmentStatus(req);	
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/addPatient")
	public String addPatient(@RequestParam String firstName, @RequestParam String lastName,
							 @RequestParam String email, @RequestParam String address1,
							 @RequestParam String address2, @RequestParam String mobileNumber,
							 @RequestParam String password, @RequestParam Date birthday) {
		System.out.println("addPatient:: Going to add a patient > "+firstName);
		Patient patient = new Patient();
		
		patient.setFname(firstName);
		patient.setLname(lastName);
		patient.setEmail(email);
		patient.setMobile(Long.parseLong(mobileNumber));
		String address = address1 +" "+address2;
		patient.setAddr(address);
		patient.setBirthday(birthday);
		
		System.out.println("Patient info : "+patient);
		
		patientService.saveUser(patient);

		loginService.saveLoginCreds(email, password, "P");
		
		return "redirect:/user_login";	
		}
	
	@GetMapping("/updatePatient")
	public void updatePatient(@RequestParam String address1,
							 @RequestParam String email, @RequestParam String mobileNumber,
							 @RequestParam String chars, @RequestParam String history) {
		System.out.println("updatePatient:: Going to update a patient > "+mobileNumber);
		Patient patient = new Patient();
		
		patient.setMobile(Long.parseLong(mobileNumber));
		String address = address1 ;
		patient.setAddr(address);
		patient.setChars(chars);
		patient.setHistory(history);
		
		System.out.println("Patient info : "+patient);
		
		String response = patientService.updatePatientProfile(address, mobileNumber, chars, history, email);
		//if(response.equals("success"))
		goPatientProfile(email, null);	
		}
	
	@GetMapping("/self_assessment")
	public String goAssessment(@RequestParam String id, Model model) {
		model.addAttribute("pid", id);
		System.out.println("goAssessment :: The goAssessment pid is : "+id);
		return "self_assessment";
	}
	
	@GetMapping("/patient/{pid}")
	public String goPatient(@PathVariable String pid, Model model) {
		model.addAttribute("pid", pid);
		System.out.println("goPatient:: The patient pid is : "+pid);
		return "patient";
	}
	
	
	public String goPatientProfile(String id, Model model) {
		System.out.println("The profile id is : "+id);
		Patient patient = patientService.getPatient(id);
		System.out.println(patient);
		model.addAttribute("type", "P");
		model.addAttribute(patient);
		return "profile";
	}
	
	@GetMapping("/appointments")
	public String showAppointments(@RequestParam String id, Model model) {
		System.out.println("showAppointments :: For "+id);
		 //model.addAttribute("appointments", patientService.getAppointments(id));
		return "appointments";
	}
}
