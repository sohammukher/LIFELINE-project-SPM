package com.lifeline.lifeline2.services;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public String saveSelfAssessment(String assessment_details) throws JsonMappingException, JsonProcessingException {
		String result="";
		
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
		 result = "success";
        }
        else {
        	result = "Already filled out assessment form today. [You can fill self assessment only once in a day]";
        }
        
		System.out.println("Saved  assessment  is :"+adp);
        }
        else {
    		System.out.println("else    is :"+adp);

   		 assessmentDetailRepository.save(ad);
   		result = "success";
        }
        adp = assessmentDetailRepository.getAssessmentDetailsbyEmail(pid);
        int sid = adp.getSid();
       // Patient p1 =  patientRepository.updatePatientSidbyEmail(sid, pid.trim());
        
		}
		catch(Exception e) {
			result = "Failed to process";
		}
		return result;
	}
	
	public boolean compareAssessmentDetails(AssessmentDetails ad1, AssessmentDetails ad2) {
		if(ad1.getDate().equals(ad2.getDate()))
		return false;
		return true;
	}
	
	public List<Appointment> getAppointments(String pid) {
		
		System.out.println("getAppointments::"+pid);
		List<Appointment> list_of_appointments = appointmentRepository.findBypid(pid);
		System.out.println(list_of_appointments.toString());
		return list_of_appointments;
	}

	public String updateAppointmentStatus(String req) {
		// TODO Auto-generated method stub
		try {
			System.out.println("saveSelfAssessment:: Going to save this self assessment form "+req);
			AssessmentDetails ad = new AssessmentDetails();
			
			ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(req);
	        
	        String pid = rootNode.get("pid").asText();
	        String aid = rootNode.get("aid").asText();
	        String ops = rootNode.get("operation").asText();
	        
		}
		catch(Exception e)
		{
			System.out.println("Failed to extract value.");
		}
		return null;
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
			
		}
		}
		catch(Exception e) {
			patientsList.put("status", "Failed to fetch data.");
		}
		return patientsList.toJSONString();
	}
}
