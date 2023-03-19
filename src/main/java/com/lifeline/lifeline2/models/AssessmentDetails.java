package com.lifeline.lifeline2.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
@Table(name="self_assessment")
@Component
public class AssessmentDetails {

	@Id
	@Column(name="self_assessment_id")
	int sid;
	
	@Column(name="email")
	String email;
	
	@Column(name="question1")
	String ans1;
	
	@Column(name="question2")
	String ans2;
	
	@Column(name="question3")
	String ans3;
	
	@Column(name="question4")
	String ans4;
	
	@Column(name="question5")
	String ans5;
	
	@Column(name="question6")
	String ans6;
	
	@Column(name="question7")
	String ans7;
	
	@Column(name="question8")
	String ans8;
	
	@Column(name="question9")
	String ans9;
	
	@Column(name = "self_assessment_score")
	int score;

	@Column(name = "date_taken")
	LocalDate date;
	
	@Column(name = "time_taken") 
	LocalDateTime  time;
	
	@Column(name = "appointment_status")
	String status;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime localDateTime) {
		this.time = localDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String toString() {
		return sid+" ~ "+email+" ~ "+ans1+" ~ "+ans2+" ~ "+ans3+" ~ "+ans4+" ~ "+ans5+" ~ "+ans6
				+" ~ "+ans7+" ~ "+ans8+" ~ "+ans9+" ~ "+score;
	}
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns3() {
		return ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	public String getAns4() {
		return ans4;
	}

	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	public String getAns5() {
		return ans5;
	}

	public void setAns5(String ans5) {
		this.ans5 = ans5;
	}

	public String getAns6() {
		return ans6;
	}

	public void setAns6(String ans6) {
		this.ans6 = ans6;
	}

	public String getAns7() {
		return ans7;
	}

	public void setAns7(String ans7) {
		this.ans7 = ans7;
	}

	public String getAns8() {
		return ans8;
	}

	public void setAns8(String ans8) {
		this.ans8 = ans8;
	}

	public String getAns9() {
		return ans9;
	}

	public void setAns9(String ans9) {
		this.ans9 = ans9;
	}
	
	
}
