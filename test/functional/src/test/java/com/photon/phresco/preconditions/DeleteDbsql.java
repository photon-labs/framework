package com.photon.phresco.preconditions;

import java.sql.*;

public class DeleteDbsql{
//  public static void main(String[] args) {
public DeleteDbsql(String methodName) {
 Connection con = null;
 String url = "jdbc:mysql://localhost:3306/";

 String driverName = "com.mysql.jdbc.Driver";
 String userName = "root";
 String password = "";
 try{
  Class.forName(driverName).newInstance();
 con = DriverManager.getConnection(url, userName, password);
 try{
  Statement st = con.createStatement();
  String table = "DROP DATABASE "+methodName;
  //String table2 = "DROP DATABASE ";
  /*String databaseName=methodName;
  String table=table2+databaseName;*/
  System.out.println("---"+table);
  st.executeUpdate(table);
  
  System.out.println("Database deleted successfully!");
  }
  catch(SQLException s){
  System.out.println("Database does not exist!");
  }
  con.close();
  }
  catch (Exception e){
  e.printStackTrace();
  }
  }

}