package com.lifeline.lifeline2.services;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeline.lifeline2.models.Appointment;
import com.lifeline.lifeline2.models.AssessmentDetails;
import com.lifeline.lifeline2.models.Patient;
import com.lifeline.lifeline2.repositories.AppointmentRepository;
import com.lifeline.lifeline2.repositories.AssessmentDetailRepository;
import com.lifeline.lifeline2.repositories.PatientRepository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository ;
	@Autowired
	private AssessmentDetailRepository assessmentDetailRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public void saveUser(Patient patient) {
		System.out.println("saveUser:: patient is >> "+patient);
		patientRepository.save(patient);
	}
	
	public Patient getPatient(String pid) {
		System.out.println("getPatient:: Going to fetch details of "+pid);
		Patient p = patientRepository.getReferenceById(pid);
		System.out.println("Fetched patient is :"+p);
		return p;
	}
	
	public String saveSelfAssessment(String assessment_details) throws JsonMappingException, JsonProcessingException, ClassNotFoundException, SQLException {
		JSONObject rr = new JSONObject();	
		rr.put("pid", "x@x.com");
		getAppointments(rr.toJSONString());
		JSONObject response = new JSONObject();		
		try {
		System.out.println("saveSelfAssessment:: Going to save this self assessment form "+assessment_details);
		AssessmentDetails ad = new AssessmentDetails();
		
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(assessment_details);
        
        String pid = rootNode.get("pId").asText();
        System.out.println("pId : "+pid);
        int score=0;
        try {
         String score_st = rootNode.get("score").asText();
         score = Integer.parseInt(score_st);
        }
        catch(Exception e) {
        	System.out.println("could not find the score");
        	score = 0;
        }
        System.out.println("score : "+score);
        ad.setScore(score);
        //ObjectMapper formMapper = new ObjectMapper();
        JsonNode form_rootNode = rootNode.get("form");
        
        System.out.println("form details : "+form_rootNode.get(0).asText());
        ad.setEmail(pid);
        ad.setAns1(form_rootNode.get(0).get("answer").asText());
        ad.setAns2(form_rootNode.get(1).get("answer").asText());
        ad.setAns3(form_rootNode.get(2).get("answer").asText());
        ad.setAns4(form_rootNode.get(3).get("answer").asText());
        ad.setAns5(form_rootNode.get(4).get("answer").asText());
        ad.setAns6(form_rootNode.get(5).get("answer").asText());
        ad.setAns7(form_rootNode.get(6).get("answer").asText());
        ad.setAns8(form_rootNode.get(7).get("answer").asText());
        ad.setAns9(form_rootNode.get(8).get("answer").asText());
        
        ad.setDate(java.time.LocalDate.now());
        ad.setTime(java.time.LocalDateTime.now());
        ad.setStatus("Pending");
        
        AssessmentDetails adp = assessmentDetailRepository.getAssessmentDetailsbyEmail(pid);
        //getAppointments("x@x.com");
        if(adp != null) {
        System.out.println(adp.toString());
        if(compareAssessmentDetails(adp, ad)) {
		 assessmentDetailRepository.save(ad);
		 response.put("status", "Success");
		 response.put("message", "");
        }
        else {
        	response.put("status", "failed");
        	response.put("message", "Already filled out assessment form today. [You can fill self assessment only once in a day]");
        
        }
        
		System.out.println("Saved  assessment  is :"+adp);
        }
        else {
    		System.out.println("else    is :"+adp);

   		 assessmentDetailRepository.save(ad);
   		response.put("status", "Success");
		 response.put("message", "");
        }
        adp = assessmentDetailRepository.getAssessmentDetailsbyEmail(pid);
        int sid = adp.getSid();
       // Patient p1 =  patientRepository.updatePatientSidbyEmail(sid, pid.trim());
        
		}
		catch(Exception e) {
			response.put("status", "Failed");
			 response.put("message", "Failed");
		}
		return response.toJSONString();
	}
	
	public boolean compareAssessmentDetails(AssessmentDetails ad1, AssessmentDetails ad2) {
		if(ad1.getDate().equals(ad2.getDate()))
		return false;
		return true;
	}
	
	public String getAppointments(String req) throws JsonMappingException, JsonProcessingException {
		JSONObject response = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(req);
        
        String pid = rootNode.get("pid").asText();
		System.out.println("getAppointments::"+pid);
		try {
		List<Appointment> list_of_appointments = appointmentRepository.findBypid(pid);
		System.out.println(list_of_appointments.toString());
		
		JSONArray appointments = new JSONArray();
		
		for(int i=0; i < list_of_appointments.size() ;i++) {
			JSONObject ap = new JSONObject();
			ap.put("ap_time", list_of_appointments.get(i).getaTime());
			ap.put("status", list_of_appointments.get(i).getStatus());
			ap.put("ap_id", list_of_appointments.get(i).getAid());
			ap.put("did", list_of_appointments.get(i).getDid());
			ap.put("pid", list_of_appointments.get(i).getPid());
			ap.put("cid", list_of_appointments.get(i).getCid());
			ap.put("cancel_by", list_of_appointments.get(i).getCancelled_by());
			appointments.add(ap);
		}
		
		response.put("appointments", appointments);
		response.put("status", "success");
		}
		catch(Exception e) {
			response.put("appointments", "");
			response.put("status", "failed");
		}
		System.out.println(response.toJSONString());
		return response.toJSONString();
	}

	public String updateAppointmentStatus(String req) {
		// TODO Auto-generated method stub
		JSONObject response = new JSONObject();
		try {
			System.out.println("saveSelfAssessment:: Going to save this self assessment form "+req);
			AssessmentDetails ad = new AssessmentDetails();
			
			ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(req);
	        
	        String pid = rootNode.get("pid").asText();
	        String aid = rootNode.get("aid").asText();
	        String ops = rootNode.get("operation").asText();
	        String ac= "", query="";
	        if(ops.equalsIgnoreCase("accept")) {
	        	ac="ACTIVE";
	        	 query = "UPDATE appointment SET status = \'"+ac+"\' WHERE "
						+ "patient_id = \""+pid+"\""+ " and appointment_id = \""+aid+"\";";
	        }
	        else {
	        	ac="CANCEL";
	        	 query = "UPDATE appointment SET status = \'"+ac+"\' , cancelled_by = "
	        			+ " \' P\' WHERE "
						+ "patient_id = \""+pid+"\""+ " and appointment_id = \""+aid+"\";";
	        }
	        System.out.println(">> "+query);
	        Statement st = DBAccess.getConnection();
			
			//UPDATE user_login SET approved = 'Y' WHERE user_id = "xx@qw.com";

			System.out.println(query);
			int rowsUpdated = st.executeUpdate(query);
	        System.out.println("Updated "+rowsUpdated+ " row");
			response.put("status", "success");
	        
		}
		catch(Exception e)
		{
			System.out.println("Failed to extract value.");
			response.put("status", "failed");
		}
		return response.toJSONString();
	}

	public String getAllPatients() throws SQLException, ClassNotFoundException {
		System.out.println("getAllPatients:: Going to fetch details of ");
		JSONObject patientsList = new JSONObject();
		try {
		Statement st = DBAccess.getConnection();
		String query = "SELECT * FROM managerPatientView;";
		
		ResultSet resultSet = st.executeQuery(query);
		
		JSONArray patients = new JSONArray();
		
		ResultSetMetaData md = resultSet.getMetaData();
		System.out.println(">> "+md.getColumnCount());
		
		if(md.getColumnCount() > 0) {
		while(resultSet.next()) {
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String addr = resultSet.getString("address");
			Date dob = resultSet.getDate("date_of_birth");
			String symptoms = resultSet.getString("symptoms");
			System.out.println(name+ " - "+email+ " - "+addr+ " - "+dob+" - "+symptoms);
			JSONObject p = new JSONObject();
			p.put("name", name);
			p.put("email", email);
			p.put("address", addr);
			p.put("date_of_birth", dob);
			p.put("symptoms", symptoms);
			
			patients.add(p);
		}
		
		patientsList.put("patients", patients);
		patientsList.put("status", "Success");
		}
		else {
			patientsList.put("status", "There are no patients in database.");
		}
		}
		catch(Exception e) {
			patientsList.put("status", "Failed to fetch data.");
		}
		return patientsList.toJSONString();
	}
	
	public String getPastAssessments(String req) throws SQLException, ClassNotFoundException, JsonMappingException, JsonProcessingException {
		System.out.println("getPastAssessments:: Going to fetch details ");
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(req);
        
        String email = rootNode.get("id").asText();
		JSONObject response = new JSONObject();
		try {
		Statement st = DBAccess.getConnection();
		String query = "SELECT * FROM self_assessment where email = \""+email+"\";";
		
		ResultSet resultSet = st.executeQuery(query);
		
		JSONArray assessments = new JSONArray();
		
		ResultSetMetaData md = resultSet.getMetaData();
		System.out.println(">> "+md.getColumnCount());
		
		if(md.getColumnCount() > 0) {
		while(resultSet.next()) {
			String ans1 = resultSet.getString("question1");
			String ans2 = resultSet.getString("question2");
			String ans3 = resultSet.getString("question3");
			String ans4 = resultSet.getString("question4");
			String ans5 = resultSet.getString("question5");
			String ans6 = resultSet.getString("question6");
			String ans7 = resultSet.getString("question7");
			String ans8 = resultSet.getString("question8");
			String ans9 = resultSet.getString("question9");
			String score = resultSet.getString("self_assessment_score");
			
			Date date  = resultSet.getDate("date_taken");
			System.out.println("date >> "+date);
			Time time = resultSet.getTime("time_taken");
			System.out.println("time >> "+time);
			String status  = resultSet.getString("appointment_status");
	
			System.out.println(date+ " - "+time+ " - "+status+ " - "+ans1+" - "+ans2);
			JSONObject p = new JSONObject();
			p.put("date", date.toString());
			p.put("time", time.toString());
			p.put("status", status);
			p.put("ans1", ans1);
			p.put("ans2", ans2);
			p.put("ans3", ans3);
			p.put("ans4", ans4);
			p.put("ans5", ans5);
			p.put("ans6", ans6);
			p.put("ans7", ans7);
			p.put("ans8", ans8);
			p.put("ans9", ans9);
			p.put("score", score );
			System.out.println("p >>> "+p.toJSONString());
			assessments.add(p);
		}
		
		response.put("assessments", assessments);
		response.put("status", "Success");
		}
		else {
			response.put("assessments", "There are no assessment");
			response.put("status", "Failed");
		}
		}
		catch(Exception e) {
			response.put("status", "Failed to fetch data.");
		}
		System.out.println(">>> "+response.toJSONString());
		return response.toJSONString();
	}

	
	public String updatePatientProfile(String address, String mobileNumber, String chars, String history,
			String email) {
		String response ="";
		try {
			System.out.println("updatePatientProfile:: Going to update the patient ");			
				        
	        Statement st = DBAccess.getConnection();
			String query = "UPDATE patient SET address = \'"+address+"\' , symptoms ="
					+ " \'"+ chars+"\' , past_medical_hist = \'"+ history+"\' WHERE "
					+ "email = \""+email+"\";";
			//UPDATE user_login SET approved = 'Y' WHERE user_id = "xx@qw.com";

			System.out.println(query);
			int rowsUpdated = st.executeUpdate(query);
	        System.out.println("Updated "+rowsUpdated+ " row");
			response = "success";
		}
		catch(Exception e)
		{
			System.out.println("Failed to extract value.");
			response = "failed";
		}
		//return response.toJSONString();
		
		return response;
	}
	
	
}
