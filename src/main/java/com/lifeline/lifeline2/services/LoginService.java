package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeline.lifeline2.models.Login;
import com.lifeline.lifeline2.repositories.LoginRepository;

import net.minidev.json.JSONObject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	public void saveLoginCreds(String userid, String pwd, String type) {
		Login login = new Login();
		
		Base64.Encoder simpleEncoder = Base64.getEncoder();
		String encodedPassword = simpleEncoder.encodeToString(pwd.getBytes());
		System.out.println("Encoded password : "+pwd+"  -  "+encodedPassword);
		login.setUid(userid);
		login.setPassword(encodedPassword);
		login.setType(type);
		if(type.equals("P"))
			login.setApproved("Y");
		System.out.println("Login info is >> "+login);
		loginRepository.save(login);
	}
	
	public Login getLoginbyUser(Login login) {
		System.out.println("Login info is >> "+login);
		return loginRepository.getLoginByuid(login.getUid());
	}
	
	public String updateUser(String req) {
		JSONObject response = new JSONObject();
		try {
			System.out.println("updateDoctor:: Going to update the doctor status"+req);			
			ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(req);
	        String email = rootNode.get("id").asText();
	        String action = rootNode.get("action").asText();
	        String ac = "";
	        String query = "";
	        int rowsUpdated=0;
	        Statement st = DBAccess.getConnection();
	        if(action.equals("accept")) {
	        	ac="Y";
	        	 query = "UPDATE user_login SET approved = \'"+ac+"\' WHERE "
						+ "user_id = \""+email+"\";";	
	        	  rowsUpdated = st.executeUpdate(query);
	        }
	        else {
	        	System.out.println("going to delete the user");
	        	ac="N";
	        	 query = "DELETE FROM user_login  WHERE "
						+ "user_id = \""+email+"\";";
	        	 System.out.println(query);
	        	  boolean ans =  st.execute(query);
	        }
	        
	       
//			String query = "UPDATE user_login SET approved = \'"+ac+"\' WHERE "
//					+ "user_id = \""+email+"\";";
			//UPDATE user_login SET approved = 'Y' WHERE user_id = "xx@qw.com";

			System.out.println(query);
			
	        System.out.println("Updated "+rowsUpdated+ " row");
			response.put("status", "success");
		}
		catch(Exception e)
		{
			System.out.println("Failed to extract value.-- "+e.getMessage());
			response.put("status", "failed");
		}
		return response.toJSONString();
	}

	public String removeUser(String req) {
		
		JSONObject response = new JSONObject();
		try {
			System.out.println("removeUser:: Going to remove the user status"+req);			
			ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(req);
	        String email = rootNode.get("id").asText();
	      
	        
	        Statement st = DBAccess.getConnection();
			String query = "DELETE user_login WHERE "
					+ "user_id = \""+email+"\";";
			System.out.println(query);
			int rowsUpdated = st.executeUpdate(query);
	        System.out.println("D "+rowsUpdated+ " row");
			response.put("status", "success");
		}
		catch(Exception e)
		{
			System.out.println("Failed to extract value.");
			response.put("status", "failed");
		}
		return response.toJSONString();
	} 
}
