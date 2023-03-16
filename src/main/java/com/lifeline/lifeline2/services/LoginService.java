package com.lifeline.lifeline2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifeline.lifeline2.models.Login;
import com.lifeline.lifeline2.repositories.LoginRepository;
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
		System.out.println("Login info is >> "+login);
		loginRepository.save(login);
	}
	
	public Login getLoginbyUser(Login login) {
		System.out.println("Login info is >> "+login);
		return loginRepository.getLoginByuid(login.getUid());
	}
}
