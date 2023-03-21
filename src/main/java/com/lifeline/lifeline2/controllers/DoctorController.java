package com.lifeline.lifeline2.controllers;

import java.sql.*;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Doctor;
import com.lifeline.lifeline2.services.DoctorService;
import com.lifeline.lifeline2.services.LoginService;


@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService = new DoctorService();
	@Autowired
	private LoginService loginService = new LoginService();

	@GetMapping("/addDoctor")
	public String addDoctor(@RequestParam String firstName, @RequestParam String lastName,
							@RequestParam String email, @RequestParam String address1,
							@RequestParam String address2, @RequestParam String mobileNumber,
							@RequestParam String password, @RequestParam Date birthday,
							@RequestParam String registrationNumber) {
		System.out.println("Going to add a Doctor > " + firstName + "  " + mobileNumber);
		Doctor doctor = new Doctor();

		doctor.setFname(firstName);
		doctor.setLname(lastName);
		doctor.setEmail(email);
		doctor.setMobile(Long.parseLong(mobileNumber));
		String address = address1 + " " + address2;
		doctor.setAddr(address);
		doctor.setDid(registrationNumber);
		doctor.setBirthday(birthday);
		System.out.println("Doctor info : " + doctor);

		doctorService.saveUser(doctor);

		loginService.saveLoginCreds(email, password, "D");

		return "redirect:/user_login";
	}

	@GetMapping("/doctor/{cid}")
	public String goDoctor(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		System.out.println("goDoctor:: The doctor cid is : " + cid);
		return "doctor";
	}

	public String goDoctorProfile(String id, Model model) {
		System.out.println("The Doctor id is : " + id);
		Doctor doctor = doctorService.getDoctor(id);
		System.out.println(doctor);
		model.addAttribute("type", "D");
		model.addAttribute(doctor);
		return "profile";
	}

	// Soham API Starts HERE.......


	@GetMapping("/getSelfAssessmentScores")  //API #1
	public ResponseEntity<JSONArray> getSelfAssessmentScores(Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "SELECT patient.first_name, patient.last_name, patient.email,\n" +
				"self_assessment.question1, self_assessment.question2,self_assessment.question3,self_assessment.question4,self_assessment.question5,self_assessment.question6,self_assessment.question7,self_assessment.question8,self_assessment.question9,\n" +
				"self_assessment.self_assessment_score\n" +
				"FROM patient\n" +
				"JOIN self_assessment\n" +
				"ON patient.self_assessment_id=self_assessment.self_assessment_id;";

		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
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

//		System.out.println("SOHAM INSIDE SELF ASSESSMENT API : "+pid);
		System.out.println("HIT THE END, It worked, NEW NEW");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getAllDoctors")  //API #3
	public ResponseEntity<JSONArray> getAllDoctors(Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "SELECT doctor_id, first_name, last_name, email\n" +
				"\t\tFROM doctor;";

		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
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

		System.out.println("HIT THE END, API 3 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

