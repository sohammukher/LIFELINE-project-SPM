package com.lifeline.lifeline2.controllers;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.services.CounsellorService;
import com.lifeline.lifeline2.services.LoginService;


@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService = new CounsellorService();
	@Autowired
	private LoginService loginService = new LoginService();

	@GetMapping("/addCounsellor")
	public String addCounsellor(@RequestParam String firstName, @RequestParam String lastName,
								@RequestParam String email, @RequestParam String address1,
								@RequestParam String address2, @RequestParam String mobileNumber,
								@RequestParam String password, @RequestParam Date birthday,
								@RequestParam String registrationNumber) {
		System.out.println("Going to add a Counsellor > " + firstName);
		Counsellor counsellor = new Counsellor();

		counsellor.setFname(firstName);
		counsellor.setLname(lastName);
		counsellor.setEmail(email);
		counsellor.setMobile(Long.parseLong(mobileNumber));
		String address = address1 + " " + address2;
		counsellor.setAddr(address);
		counsellor.setCid(registrationNumber);
		counsellor.setBirthday(birthday);
		System.out.println("Counsellor info : " + counsellor);

		counsellorService.saveUser(counsellor);

		loginService.saveLoginCreds(email, password, "C");

		return "redirect:/user_login";
	}

	@GetMapping("/counsellor/{cid}")
	public String goCounsellor(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		System.out.println("goCounsellor:: The counsellor cid is : " + cid);
		return "counsellor";
	}

	public String goCounsellorProfile(String id, Model model) {
		System.out.println("The Counsellor id is : " + id);
		Counsellor counsellor = counsellorService.getCounsellor(id);
		System.out.println(counsellor);
		model.addAttribute("type", "C");
		model.addAttribute(counsellor);
		return "profile";
	}


	@GetMapping("/getCounselorAppointments1/{counselor_email}")  //API #2
	public ResponseEntity<JSONArray> getCounselorAppointments1(@PathVariable String counselor_email, Model model) throws Exception {

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
		Connection con = null;
		Statement st = null;
		JSONArray result = null;

		try{
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

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

			result = new JSONArray();
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

		}catch (Exception e){
			System.out.println(e);

		}
		finally {
			st.close();
			con.close();
			System.out.println("Connection Closed ");
		}

		System.out.println("HIT THE END, API 2 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getCounselorAppointments/{doctor_email}")  //API #4
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
				"\t\tSELECT doctor_id FROM doctor WHERE email=\""+doctor_email+"\"\n" +
				"\t\t);";

		Connection con = null;
		Statement st = null;
		JSONArray result = null;


		try {
			 con = DriverManager.getConnection(url, user, password);
			 st = con.createStatement();

			ResultSet resultSet = st.executeQuery(query);//Executing Query

			//-------------------------

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

			result = new JSONArray();
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
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", result);

			// Default print without indent factor
			System.out.println(jsonObject);

			// Pretty print with 2 indent factor
			System.out.println(jsonObject.toString());

		}
		catch (Exception e){
			System.out.println(e);

		}
		finally {
			st.close();
			con.close();
			System.out.println("Connection Closed");
		}

		//Printing The JSON ARRAY :


		System.out.println("HIT THE END, API 4 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//	@PostMapping("/updateAppointment")
	//------------------After Thymeleaf Fix-------------------------------

//	2 POST API and 1 PUT Below this ---> SOHAM ------------------------->

//	----------POST 1-------------
	@PostMapping("/bookCounsellorAppointment")
	public ResponseEntity<String> bookCounsellorAppointment(@RequestBody String payload) throws Exception {
		System.out.println("Inside bookCounsellorAppointment:" + payload);

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver



		//Taking Data From the Payload
		Object obj= JSONValue.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;


		// POST Data Parameters
		String patient_email = (String) jsonObject.get("patient_email");
		String appointment_datetime = (String) jsonObject.get("appointment_datetime");
		String counselor_email = (String) jsonObject.get("counselor_email");

		System.out.println(patient_email+" "+appointment_datetime+" "+counselor_email);


		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "INSERT into appointment (patient_id,counselor_id,appointment_time,status)\n" +
				"VALUES ("+"\'"+patient_email+"\'"+",\n" +
				"(SELECT counselor_id FROM counselor WHERE email= \'"+counselor_email+"\'),'"+appointment_datetime+"',\'PENDING\');";

		System.out.println("Final Query is : "+query);

		//----------------------------------------EXECUTING QUERY----------------
		Connection con = null;
		Statement st = null;


		//----------------------------------------EXECUTING QUERY----------------
		try {
			con=DriverManager.getConnection(url,user,password);
			st=con.createStatement();

			st.executeUpdate(query);//ExecutingQuery


		}
		catch( Exception e){
			System.out.println(e);
		}

		finally {
			st.close();
			con.close();
			System.out.println("Connection Closed......");

		}
		System.out.println("API SUCCESS");
		//------------------------------------------------------------------------

		return ResponseEntity.ok("status: success");

	}

	//	----------POST 2-------------
	@PostMapping("/bookDoctorAppointment")
	public ResponseEntity<String> bookDoctorAppointment(@RequestBody String payload) throws Exception {
		System.out.println("Inside bookDoctorAppointment:" + payload);

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver



		//Taking Data From the Payload
		Object obj= JSONValue.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;


		// POST Data Parameters
		String patient_email = (String) jsonObject.get("patient_email");
		Integer doctor_id = (Integer) jsonObject.get("doctor_id");
		String appointment_datetime = (String) jsonObject.get("appointment_datetime");

		System.out.println(patient_email+" "+appointment_datetime+" "+doctor_id);

		String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
		String user = "sql9600624";
		String password = "MUQNntyZ4Y";
		String query = "INSERT into appointment (patient_id,doctor_id,appointment_time,status)\n" +
				"VALUES ('"+patient_email+"','"+doctor_id+"', '"+appointment_datetime+"',\"PENDING\");";

		System.out.println("Final Query is : "+query);
		Connection con = null;
		Statement st = null;


		//----------------------------------------EXECUTING QUERY----------------
		try {
			 con=DriverManager.getConnection(url,user,password);
			 st=con.createStatement();

			st.executeUpdate(query);//ExecutingQuery

		}
		catch( Exception e){
			System.out.println(e);
		}

		finally {
			st.close();
			con.close();
			System.out.println("Connection Closed Successfully");
		}




		System.out.println("API SUCCESS");
		//------------------------------------------------------------------------

		return ResponseEntity.ok("status: success");

	}

//	---------------------------------PUT REQUEST-----------------
@PutMapping("/updateAppointment")
public ResponseEntity<String> updateAppointment(@RequestBody String payload) throws Exception {

	System.out.println("Inside updateAppointment:" + payload);

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver



	//Taking Data From the Payload
	Object obj= JSONValue.parse(payload);

	JSONObject jsonObject = (JSONObject) obj;


	// POST Data Parameters
	String patient_email = (String) jsonObject.get("patient_email");
	String doctor_email = (String) jsonObject.get("doctor_email");
	String appointment_datetime = (String) jsonObject.get("appointment_datetime");

	System.out.println(patient_email+" "+appointment_datetime+" "+doctor_email);

	String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
	String user = "sql9600624";
	String password = "MUQNntyZ4Y";
	String query = "UPDATE appointment\n" +
			"SET appointment_time='"+appointment_datetime+"'\n" +
			"WHERE patient_id='"+patient_email+"' AND doctor_id=(SELECT doctor_id from doctor WHERE email="+doctor_email+");";

	System.out.println("Final Query is : "+query);
	Connection con = null;
	Statement st = null;


	//----------------------------------------EXECUTING QUERY----------------
	try {
		con=DriverManager.getConnection(url,user,password);
		st=con.createStatement();

		st.executeUpdate(query);//ExecutingQuery

	}
	catch( Exception e){
		System.out.println(e);
	}

	finally {
		st.close();
		con.close();
		System.out.println("Connection Closed Successfully");
	}


	System.out.println("API SUCCESS");
	//------------------------------------------------------------------------

	return ResponseEntity.ok("status: success");
}
}


