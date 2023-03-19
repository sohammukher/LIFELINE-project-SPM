package com.lifeline.lifeline2.models;

import java.time.LocalDateTime;

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
@Table(name="appointment")
@Component
public class Appointment {

	@Id
	@Column(name="appointment_id")
	int aid;

	
	@Column(name="doctor_id")
	String did;
	
	@Column(name="patient_id")
	String pid;
	
	@Column(name="counselor_id")
	String cid;
	
	@Column(name="appointment_time")
	LocalDateTime aTime;
	
	@Column(name="status")
	String status;
	
	@Column(name="cancelled_by")
	String cancelled_by;
	
	
	public String getCancelled_by() {
		return cancelled_by;
	}
	public void setCancelled_by(String cancelled_by) {
		this.cancelled_by = cancelled_by;
	}
	public String toString() {
		return aid+" ~ "+did+" ~ "+pid+" ~ "+cid+" ~ "+aTime+" ~ "+status;
	}
	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	
	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public LocalDateTime getaTime() {
		return aTime;
	}

	public void setaTime(LocalDateTime aTime) {
		this.aTime = aTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

		
	
	
	}
