package com.lifeline.lifeline2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SpringBootApplication
public class Lifeline2Application {

	public static void main(String[] args) {
		SpringApplication.run(Lifeline2Application.class, args);
	}

//	protected void configure(HttpSecurity http) throws Exception {
//	     http.csrf().disable();
//	}
}
