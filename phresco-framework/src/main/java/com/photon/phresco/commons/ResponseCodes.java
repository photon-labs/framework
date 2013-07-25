/**
 * Phresco Framework
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
package com.photon.phresco.commons;


public interface ResponseCodes {
	
	/*
	 * Generic Exception
	 */
	
	String PHR000000 = "PHR000000"; // Unexpected Failure at server end
	
	/*
	 * Login page
	 */
	String PHR100001 = "PHR100001"; // Login Success
	String PHR110001 = "PHR110001"; // 'user' object does not exist or is empty
	String PHR110002 = "PHR110002"; // unauthorized user for phresco
	String PHR110003 = "PHR110003"; // invalid credentials or auth service is down
	String PHR110004 = "PHR110004"; // auth service is down
	String PHR110005 = "PHR110005"; // 'user.json' file not found
	String PHR110006 = "PHR110006"; // parsing failed for file 'user.json'
	String PHR110007 = "PHR110007"; // user doesn't have valid permissions
	
	/*
	 * Project Page
	 */
	String PHR200001 = "PHR200001"; // projects listed successfully
	String PHR210001 = "PHR210001"; // failed to list projects
	String PHR200002 = "PHR200002"; // application infos returned successfully
	String PHR210002 = "PHR210002"; // failed to get application infos
	String PHR200003 = "PHR200003"; // no application info available
	String PHR210003 = "PHR210003"; // unauthorized user
	String PHR200004 = "PHR200004"; // project created successfully
	String PHR210004 = "PHR210004"; // project creation failed
	String PHR200005 = "PHR200005"; // project edited successfully
	String PHR210005 = "PHR210005"; // failed to edit project
	String PHR200006 = "PHR200006"; // project updated successfully
	String PHR210006 = "PHR210006"; // failed to update project
	String PHR210007 = "PHR210007"; // could not open file 'project.info'
	String PHR210008 = "PHR210008"; // failed to update features
	String PHR200007 = "PHR200007"; // features updated successfully
	String PHR210009 = "PHR210009"; // failed to update application
	String PHR210010 = "PHR210010"; // I/O error while closing BufferedReader
	String PHR200008 = "PHR200008"; // application updated successfully
	String PHR200009 = "PHR200009"; // application edited successfully
	String PHR210011 = "PHR210011"; // could not delete application, connection still alive
	String PHR210012 = "PHR210012"; // unable to delete application
	String PHR210013 = "PHR210013"; // could not open file 'runagainstsource.info'
	String PHR200010 = "PHR200010"; // application deleted successfully
	String PHR200011 = "PHR200011"; // permission for user returned successfully
	String PHR210014 = "PHR210014"; // unable to fetch permission for user
	String PHR210015 = "PHR210015"; // unable to download report
	String PHR210016 = "PHR210016"; // unable to access or read report
	String PHR200012 = "PHR200012"; // no report to download
	
}