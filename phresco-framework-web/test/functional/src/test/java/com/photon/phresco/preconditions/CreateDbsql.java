package com.photon.phresco.preconditions;

import java.sql.*;

public class CreateDbsql {
	
	public void CreateDatabase(String methodName) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String driverName = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		try {
			System.out.println("creating Database ");
			Class.forName(driverName);
			con = DriverManager.getConnection(url, userName, password);
			try {
				Statement st = con.createStatement();
				String table = "CREATE DATABASE " + methodName;
				st.executeUpdate(table);
				System.out.println("Database created successfully!");
			} catch (SQLException s) {
				System.out.println("Database already exists!");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}