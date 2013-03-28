/**
 * Phresco Framework Root
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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