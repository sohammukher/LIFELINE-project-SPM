package com.lifeline.lifeline2.services;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.repositories.CounsellorRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


@Service
public class CounsellorService {

	@Autowired
	private CounsellorRepository counsellorRepository ;
	
	public void saveUser(Counsellor counsellor) {
		System.out.println("counsellor is >> "+counsellor);
		counsellorRepository.save(counsellor);
	}
	
	public Counsellor getCounsellor(String cid) {
		System.out.println("getCounsellor:: Going to fetch details of "+cid);
		Counsellor c = counsellorRepository.getCounsellorByemail(cid);
		System.out.println("Fetched Counsellor is :"+c);
		return c;
	}
	
	
	public String getAllCounsellors() throws SQLException, ClassNotFoundException {
		System.out.println("getAllCounsellors:: Going to fetch details of ");
		//System.out.println("Fetched Counsellor is :"+docList);
		JSONObject docList = new JSONObject();
		try {
		Statement st = DBAccess.getConnection();
		String query = "SELECT * FROM managerCounsellorView;";
		
		ResultSet resultSet = st.executeQuery(query);
		
		JSONArray waitlistedCounsellors = new JSONArray();
		JSONArray activeCounsellors = new JSONArray();
		
		ResultSetMetaData md = resultSet.getMetaData();
		System.out.println(">> "+md.getColumnCount());
		
		
		while(resultSet.next()) {
			String name = resultSet.getString("name");
			String did = resultSet.getString("counselor_id");
			String email = resultSet.getString("email");
			String isApproved = resultSet.getString("approved");
			System.out.println(name+ " - "+did+ " - "+email+ " - "+isApproved);
			JSONObject doc = new JSONObject();
			doc.put("name", name);
			doc.put("id", did);
			doc.put("email", email);
			if(isApproved.equals("Y")) {
				activeCounsellors.add(doc);
			}
			else {
				waitlistedCounsellors.add(doc);
			}
		}
		
		docList.put("waitlistedCounsellors", waitlistedCounsellors);
		docList.put("activeDoctors", activeCounsellors);
		docList.put("status", "Success");
		}
		catch(Exception e) {
			docList.put("status", "Failed to fetch data.");
		}
		return docList.toJSONString();
	}
}
