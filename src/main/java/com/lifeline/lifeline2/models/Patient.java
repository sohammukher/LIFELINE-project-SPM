package com.lifeline.lifeline2.models;


import java.sql.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="patient")
@Component
public class Patient {

	
	@Column(name="first_name")
	String fname;
	
	@Column(name="last_name")
	String lname;
	
	@Column(name="address")
	String addr;
	
	@Column(name="contact")
	long mobile;
	
	@Id
	@Column(name="email")
	String email;
	
	@Column(name="symptoms")
	String chars;
	
	@Column(name="past_medical_hist")
	String history;
	
	@Column(name="counsellor_app_id")
	int cid;
	
	@Column(name="doctor_app_id")
	int did;
	
	@Column(name="self_assessment_id")
	int sid;

	@Column(name="date_of_birth")
	Date birthday;
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String toString() {
		return fname+" ~ "+lname+" ~ "+addr+" ~ "+mobile+" ~ "+email; 
	}
	

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChars() {
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	
	
}
