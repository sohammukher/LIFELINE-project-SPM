package com.lifeline.lifeline2.controllers;


//JDBC IMPORT
import java.sql.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
	public ResponseEntity<String> saveSelfAssessment(@RequestBody String pat) {
		System.out.println("Reached save patient : " + pat);

		return ResponseEntity.ok("{status: \"TRUE\"}");
	}

	@GetMapping("/addPatient")
	public String addPatient(@RequestParam String firstName, @RequestParam String lastName,
							 @RequestParam String email, @RequestParam String address1,
							 @RequestParam String address2, @RequestParam String mobileNumber,
							 @RequestParam String password, @RequestParam Date birthday) {
		System.out.println("addPatient:: Going to add a patient > " + firstName);
		Patient patient = new Patient();

		patient.setFname(firstName);
		patient.setLname(lastName);
		patient.setEmail(email);
		patient.setMobile(Long.parseLong(mobileNumber));
		String address = address1 + " " + address2;
		patient.setAddr(address);
		patient.setBirthday(birthday);

		System.out.println("Patient info : " + patient);

		patientService.saveUser(patient);

		loginService.saveLoginCreds(email, password, "P");

		return "redirect:/user_login";
	}

	@GetMapping("/updatePatient")
	public void updatePatient(@RequestParam String address1,
							  @RequestParam String email, @RequestParam String mobileNumber,
							  @RequestParam String chars, @RequestParam String history) {
		System.out.println("updatePatient:: Going to update a patient > " + mobileNumber);
		Patient patient = new Patient();

		patient.setMobile(Long.parseLong(mobileNumber));
		String address = address1;
		patient.setAddr(address);
		patient.setChars(chars);
		patient.setHistory(history);

		System.out.println("Patient info : " + patient);

		//patientService.updatePatientProfile(address, mobileNumber, chars, history, email);

		goPatientProfile(email, null);
	}

	@GetMapping("/self_assessment")
	public String goAssessment(@RequestParam String id, Model model) {
		model.addAttribute("pid", id);
		System.out.println("goAssessment :: The goAssessment pid is : " + id);
		return "self_assessment";
	}

	@GetMapping("/patient/{pid}")
	public String goPatient(@PathVariable String pid, Model model) {
		model.addAttribute("pid", pid);
		System.out.println("goPatient:: The patient pid is : " + pid);
		return "patient";
	}


	public String goPatientProfile(String id, Model model) {
		System.out.println("The profile id is : " + id);
		Patient patient = patientService.getPatient(id);
		System.out.println(patient);
		model.addAttribute("type", "P");
		model.addAttribute(patient);
		return "profile";
	}

	@GetMapping("/getPatientAssessmentAPI/{pid}")
	public ResponseEntity<JSONArray> getPatientAssessment(@PathVariable String pid, Model model) throws Exception {


		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "SELECT patient.first_name , patient.last_name, patient.contact, patient.email, self_assessment.self_assessment_score, self_assessment.comments \n" +
				"FROM sql9600624.patient\n" +
				"INNER JOIN sql9600624.self_assessment\n" +
				"ON patient.self_assment_id=self_assessment.self_assessment_id;";

		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query


//			resultSet.next();
//			String name = resultSet.getString("fname");//Column Alias

//			System.out.println("SOHAM PRINTING HERE "+name);

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "?";
					}
				})
				.collect(Collectors.toList());

		JSONArray result = new JSONArray();
		while (resultSet.next()) {
			JSONObject row = new JSONObject();
			colNames.forEach(cn -> {
				try {
					row.put(cn, resultSet.getObject(cn));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			result.add(row);// Contains the JSON_ARRAY
		}


		//Conversion Ended....


		//Printing The JSON ARRAY :

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);

		// Default print without indent factor
		System.out.println(jsonObject);

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());
		st.close();
		con.close();

		System.out.println("SOHAM INSIDE SELF ASSESSMENT API : "+pid);
		System.out.println("HIT THE END, It worked, NEW NEW");

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}


