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


		@GetMapping("/getCounselorAppointments/counselor_email}")  //API #2
	public ResponseEntity<JSONArray> getCounselorAppointments(@PathVariable String counselor_email, Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "SELECT patient.first_name, patient.last_name, patient.email, \n" +
				"appointment.appointment_time, appointment.status\n" +
				"FROM patient\n" +
				"JOIN appointment\n" +
				"ON patient.counsellor_app_id=appointment.counselor_id\n" +
				"WHERE appointment.counselor_id= (\n" +
				"SELECT counselor_id FROM counselor WHERE email=counselor_email\n" +
				");";

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

		System.out.println("HIT THE END, API 2 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getCounselorAppointments/doctor_email")  //API #4
	public ResponseEntity<JSONArray> getCounselorAppointmentsDoctor(@PathVariable String doctor_email, Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "SELECT patient.first_name, patient.last_name, patient.email,\n" +
				"\t\tself_assessment.self_assessment_score,\n" +
				"\t\tappointment.appointment_time, appointment.status\n" +
				"\t\tFROM patient\n" +
				"\t\tJOIN self_assessment\n" +
				"\t\tON patient.self_assessment_id=self_assessment.self_assessment_id\n" +
				"\t\tJOIN appointment\n" +
				"\t\tON patient.doctor_app_id=appointment.doctor_id\n" +
				"\t\tWHERE appointment.doctor_id= (\n" +
				"\t\tSELECT doctor_id FROM doctor WHERE email=\"p@t.com\"\n" +
				"\t\t);";

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

		System.out.println("HIT THE END, API 4 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}


