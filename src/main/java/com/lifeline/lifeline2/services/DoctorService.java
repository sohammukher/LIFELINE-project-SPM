package com.lifeline.lifeline2.services;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeline.lifeline2.models.AssessmentDetails;
import com.lifeline.lifeline2.models.Doctor;
import com.lifeline.lifeline2.repositories.DoctorRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository ;
	
	public void saveUser(Doctor doctor) {
		System.out.println("doctor is >> "+doctor);
		doctorRepository.save(doctor);
	}
	
	public Doctor getDoctor(String cid) {
		System.out.println("getCounsellor:: Going to fetch details of "+cid);
		Doctor d = doctorRepository.getDoctorByemail(cid);
		System.out.println("Fetched Counsellor is :"+d);
		return d;
	}
	
	public String getAllDoctors() throws SQLException, ClassNotFoundException {
		System.out.println("getAllDoctors:: Going to fetch details of ");
		//List<Doctor> docList = doctorRepository.findAll();
		//System.out.println("Fetched Counsellor is :"+docList);
		JSONObject docList = new JSONObject();
		try {
		Statement st = DBAccess.getConnection();
		String query = "SELECT * FROM managerDocView;";
		
		ResultSet resultSet = st.executeQuery(query);
		
		JSONArray waitlistedDoctors = new JSONArray();
		JSONArray activeDoctors = new JSONArray();
		
		ResultSetMetaData md = resultSet.getMetaData();
		System.out.println(">> "+md.getColumnCount());
		if(md.getColumnCount() > 0) {
		
		while(resultSet.next()) {
			String name = resultSet.getString("name");
			String did = resultSet.getString("doctor_id");
			String email = resultSet.getString("email");
			String isApproved = resultSet.getString("approved");
			System.out.println(name+ " - "+did+ " - "+email+ " - "+isApproved);
			JSONObject doc = new JSONObject();
			doc.put("name", name);
			doc.put("id", did);
			doc.put("email", email);
			if(isApproved.equals("Y")) {
				activeDoctors.add(doc);
			}
			else {
				waitlistedDoctors.add(doc);
			}
		}
		docList.put("waitlistedDoctors", waitlistedDoctors);
		docList.put("activeDoctors", activeDoctors);
		docList.put("status", "Success");
		}
		else {
			docList.put("status", "There are no doctors in database.");
		}
		}
		catch(Exception e) {
			docList.put("status", "Failed to fetch data.");
		}
		return docList.toJSONString();
	}

	
	
}
