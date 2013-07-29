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
	String PHR200013 = "PHR200013"; // report deleted successfully
	String PHR210017 = "PHR210017"; // failed to delete report
	String PHR210018 = "PHR210018"; // could not access application home to delete report
	String PHR200014 = "PHR200014"; // no report to delete
	String PHR200015 = "PHR200015"; // no reports available
	String PHR210019 = "PHR210019"; // unable to fetch reports
	String PHR200016 = "PHR200016"; // report generated successfully
	String PHR210020 = "PHR210020"; // at least one test report should be available or sonar report should be available
	String PHR210021 = "PHR210021"; // failed to generate report
	String PHR200017 = "PHR200017"; // project imported successfully
	String PHR210022 = "PHR210022"; // phresco compliant project does not exist
	String PHR210023 = "PHR210023"; // invalid credentials
	String PHR210024 = "PHR210024"; // invalid URL
	String PHR210025 = "PHR210025"; // invalid revision
	String PHR210026 = "PHR210026"; // failed to import project
	String PHR210027 = "PHR210027"; // project already exists
	String PHR200018 = "PHR200018"; // project updated successfully
	String PHR210028 = "PHR210028"; // failed to update project
	String PHR210029 = "PHR210029"; // this is not a working copy
	String PHR210030 = "PHR210030"; // no files to update
	String PHR200019 = "PHR200019"; // project added successfully
	String PHR210031 = "PHR210031"; // failed to add project
	String PHR210032 = "PHR210032"; // this git repository does not exist
	String PHR200020 = "PHR200020"; // project committed successfully
	String PHR210033 = "PHR210033"; // failed to commit project to repository
	String PHR210034 = "PHR210034"; // no files to commit
	String PHR210035 = "PHR210035"; // repository does not exist for commit
	String PHR210036 = "PHR210036"; // repository does not exist
	String PHR200021 = "PHR200021"; // repository exists for commit
	String PHR210037 = "PHR210037"; // repository does not exist for update
	String PHR200022 = "PHR200022"; // repository exists for update
	String PHR210038 = "PHR210038"; // invalid URL or repository moved temporarily
	String PHR210039 = "PHR210039"; // failed to fetch log messages
	String PHR200023 = "PHR200023"; // log messages returned successfully
	
	/*
	 * App Info Page
	 */
	String PHR310001 = "PHR310001"; // unauthorized user
	String PHR300001 = "PHR300001"; // configuration listed successfully
	String PHR310002 = "PHR310002"; // unable to fetch configuration
	String PHR310003 = "PHR310003"; // unable to fetch webservice configuration
	String PHR300002 = "PHR300002"; // technology options fetched successfully
	String PHR310004 = "PHR310004"; // failed to get technology options
	
	/*
	 * Features Page
	 */
	String PHR410001 = "PHR410001"; // unauthorized user
	String PHR400001 = "PHR400001"; // application features listed successfully
	String PHR410002 = "PHR410002"; // unable to fetch application features
	String PHR400002 = "PHR400002"; // description fetched successfully
	String PHR410003 = "PHR410003"; // unable to fetch description
	String PHR400003 = "PHR400003"; // dependency features listed successfully
	String PHR410004 = "PHR410004"; // no dependency features available
	String PHR410005 = "PHR410005"; // unable to fetch dependency features
	String PHR400004 = "PHR400004"; // selected features listed successfully
	String PHR410006 = "PHR410006"; // unable to fetch selected features
	
	/*
	 * Code Quality Page
	 */
	
	
	
	/*
	 * Configuration Page
	 */
	String PHR600001 = "PHR600001"; // environments added successfully
	String PHR610001 = "PHR610001"; // failed to add environments
	String PHR600002 = "PHR600002"; // environments listed successfully
	String PHR610002 = "PHR610002"; // failed to list environments
	String PHR610003 = "PHR610003"; // default environment cannot be deleted
	String PHR600003 = "PHR600003"; // environment deleted successfully
	String PHR610004 = "PHR610004"; // failed to delete environment
	String PHR610005 = "PHR610005"; // environment not available to delete
	String PHR610006 = "PHR610006"; // unauthorized user
	String PHR600004 = "PHR600004"; // configuration template fetched successfully
	String PHR610007 = "PHR610007"; // unable to fetch configuration template
	String PHR610008 = "PHR610008"; // URL parameter is missing
	String PHR610009 = "PHR610009"; // unable to check connection
	String PHR600005 = "PHR600005"; // checked connection successfully
	String PHR600006 = "PHR600006"; // configurations updated successfully
	String PHR610010 = "PHR610010"; // failed to be update configurations for the environment
	String PHR600007 = "PHR600007"; // environment cloned successfully
	String PHR610011 = "PHR610011"; // failed to clone environment
	String PHR600008 = "PHR600008"; // cron expression calculated successfully
	String PHR610012 = "PHR610012"; // cron expression not available
	String PHR610013 = "PHR610013"; // unable to fetch environments
	String PHR600009 = "PHR600009"; // https server certificate returned successfully
	String PHR610014 = "PHR610014"; // no certificate found
	String PHR600010 = "PHR600010"; // https server certificate added successfully
	String PHR610015 = "PHR610015"; // no certificate to add
	String PHR610016 = "PHR610016"; // unable to add https server certificate
	String PHR600011 = "PHR600011"; // file uploaded successfully
	String PHR610017 = "PHR610017"; // failed to upload file
	String PHR610018 = "PHR610018"; // no file to upload
	String PHR610019 = "PHR610019"; // no browse file structure found
	String PHR600012 = "PHR600012"; // browse file structure returned
	String PHR610020 = "PHR610020"; // failed to return browse file structure
	String PHR610021 = "PHR610021"; // file cannot be iterated
	String PHR600013 = "PHR600013"; // file browse entire structure returned successfully
	String PHR610022 = "PHR610022"; // unable to return file browse entire structure
}