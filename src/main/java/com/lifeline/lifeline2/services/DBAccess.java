package com.lifeline.lifeline2.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess {
	static Connection con ;
	public static Statement getConnection() throws SQLException, ClassNotFoundException {

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

	//String url = "jdbc:mysql://sql9.freesqldatabase.com/sql9600624";
//	String user = "sql9600624";
//	String password = "MUQNntyZ4Y";
	
	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	

	con = DriverManager.getConnection(url, user, password);
	Statement st = con.createStatement();
	
	return st;
	}
	
	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
