
/**
 * Phresco Framework
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
	 * Common
	 */
	String PHR1C00001 = "PHR1C00001"; // parameter returned successfully
	String PHR1C10001 = "PHR1C10001"; // no parameter available
	String PHR1C10002 = "PHR1C10002"; // parameter not fetched
	String PHR1C10003 = "PHR1C10003"; // unable to read 'phresco.functionalTest.selenium.tool' property in project's pom.xml
	String PHR1C00002 = "PHR1C00002"; // no parameter available
	String PHR2C00001 = "PHR2C00001"; // log copied successfully
	String PHR2C10001 = "PHR2C10001"; // log copy failed
	String PHR3C00001 = "PHR3C00001"; // folder opened successfully
	String PHR3C10001 = "PHR3C10001"; // failed to open folder
	String PHR4C00001 = "PHR4C00001"; // path copied successfully
	String PHR4C10001 = "PHR4C10001"; // failed to copy path
	String PHR5C00001 = "PHR5C00001"; // map updated successfully
	String PHR5C10001 = "PHR5C10001"; // failed to update map as appinfos could not be retrieved
	String PHR6C00001 = "PHR6C00001"; // dependency fetched successfully
	String PHR6C10001 = "PHR6C10001"; // unable to fetch dependency
	String PHR7C00001 = "PHR7C00001"; // template content returned successfully
	String PHR7C10001 = "PHR7C10001"; // unable to fetch template content
	String PHR8C00001 = "PHR8C00001"; // mandatory parameter check failed
	String PHR9C10001 = "PHR9C10001"; // unable to complete validation
	String PHR9C00001 = "PHR9C00001"; // completed validation successfully
	String PHR10C00001 = "PHR10C00001"; // Lock details returned successfully
	String PHR10C00002 = "PHR10C00002"; // No Lock details to return
	String PHR10C10001 = "PHR10C10001"; // failed to return lock details
	String PHR11C00001 = "PHR11C00001"; // No process is running
	String PHR11C00002 = "PHR11C00002"; // process stopped successfully
	String PHR11C10001 = "PHR11C10001"; // failed to read the process.json
	String PHR11C10002 = "PHR11C10002"; // unable to parse the process.json
	String PHR11C10003 = "PHR11C10003"; // failed to kill the process
	String PHR12C00001 = "PHR12C00001"; // Downloads returned successfully
	String PHR12C00002 = "PHR12C00002"; // No downloads available
	String PHR12C10001 = "PHR12C10001"; // unauthorized user
	String PHR12C10002 = "PHR12C10002"; // Failed to return downloads
	String PHR13C00001 = "PHR13C00001"; // check for remote machine success
	String PHR13C10001 = "PHR13C10001"; // check for remote machine failed
	String PHR14C00001 = "PHR14C00001"; // Report sent
	String PHR14C10001 = "PHR14C10001"; // Report sending failed
	
	
	/*
	 * Login page
	 */
	String PHR100001 = "PHR100001"; // Login Success
	String PHR110001 = "PHR110001"; // 'user' object does not exist or is empty
	String PHR110002 = "PHR110002"; // unauthorized user for phresco
	String PHR110003 = "PHR110003"; // invalid credentials or auth service is down
	String PHR110004 = "PHR110004"; // service is down
	String PHR110005 = "PHR110005"; // 'user.json' file not found
	String PHR110006 = "PHR110006"; // parsing failed for file 'user.json'
	String PHR110007 = "PHR110007"; // user doesn't have valid permissions
	String PHR100008 = "PHR100008"; // Password emailed
	String PHR110008 = "PHR110008"; // Invalid user
	String PHR100009 = "PHR100009"; // Password changed successfully
	String PHR110009 = "PHR110009"; // Password updation failed
	String PHR110010 = "PHR110010"; // User not local

	
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
	String PHR200053 = "PHR200053"; // Successfully checked for scm project
	String PHR210053 = "PHR210053"; // Failed to check for scm project
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
	String PHR210040 = "PHR210040"; // invalid name
	String PHR210041 = "PHR210041"; // project name already exists
	String PHR210042 = "PHR210042"; // project code already exists
	String PHR210043 = "PHR210043"; // app code already exists
	String PHR210044 = "PHR210044"; // app directory already exists
	String PHR200024 = "PHR200024"; // apptypes listed successfully
	String PHR210045 = "PHR210045"; // unable to fetch apptypes
	String PHR200025 = "PHR200025"; // application pilots listed successfully
	String PHR210046 = "PHR210046"; // failed to fetch list of application pilots
	String PHR200026 = "PHR200026"; // project deleted successfully
	String PHR210047 = "PHR210047"; // unable to delete the project
	String PHR210048 = "PHR210048"; // Project Imported Succesfully.but, test checkout is not applicable for this multi-app Url
	String PHR200027 = "PHR200027"; // report fetched successfully
	String PHR210049 = "PHR210049"; // Invalid host or port
	String PHR210050 = "PHR210050"; // Invalid stream
	String PHR210051 = "PHR210051"; // To retrive submodule's dependents
	String PHR210052 = "PHR210052"; // Failed to retrive submodule's dependents
	String PHR210054 = "PHR210053"; // Project is not Phresco Specific
	String PHR210055 = "PHR210055"; // invalid user
	

	
	/*
	 * App Info Page
	 */
	String PHR310001 = "PHR310001"; // unauthorized user
	String PHR300001 = "PHR300001"; // configuration listed successfully
	String PHR310002 = "PHR310002"; // unable to fetch server/database configuration
	String PHR310003 = "PHR310003"; // unable to fetch webservice configuration
	String PHR300002 = "PHR300002"; // technology options fetched successfully
	String PHR310004 = "PHR310004"; // failed to get technology options
	String PHR310005 = "PHR310005"; // unable to fetch functional frameworks
	
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
	String PHR410005 = "PHR410005"; // unable to fetch artifact infos for dependency features
	String PHR400004 = "PHR400004"; // selected features listed successfully
	String PHR410006 = "PHR410006"; // unable to fetch selected features
	String PHR400005 = "PHR400005"; // feature configuration popup returned successfully
	String PHR410007 = "PHR410007"; // error occurred while fetching feature configurations
	String PHR400006 = "PHR400006"; // feature configurations updated successfully
	String PHR410008 = "PHR410008"; // unable to update feature configurations
	String PHR410009 = "PHR410009"; // failed to fetch environments from configuration file
	String PHR410010 = "PHR410010"; // unauthorized user
	String PHR410011 = "PHR410011"; // unable to fetch dependent features
	String PHR400007 = "PHR400007"; // dependency features fetched successfully
	String PHR400008 = "PHR400008"; // dependency features Not available
	
	/*
	 * Code Quality Page
	 */
	String PHR510001 = "PHR510001"; // sonar not yet started
	String PHR500001 = "PHR500001"; // dependency returned successfully
	String PHR510002 = "PHR510002"; // dependency not fetched
	String PHR510003 = "PHR510003"; // report not available
	String PHR510004 = "PHR510004"; // code validation failed
	String PHR500002 = "PHR500002"; // code validation started successfully
	String PHR510005 = "PHR510005"; // unable to fetch 'phresco.code.validate.report' property in project's pom.xml
	String PHR510006 = "PHR510006"; // failed to read functional test dir from project's pom.xml or create pom.xml in that dir
	String PHR510007 = "PHR510007"; // no IP address for the host could be found
	String PHR510008 = "PHR510008"; // an I/O exception has occurred
	String PHR500003 = "PHR500003"; // report fetched successfully
	String PHR510009 = "PHR510009"; // report not fetched
	String PHR500004 = "PHR500004"; // sonar url returned successfully
	String PHR510010 = "PHR510010"; // Error in sonar url 
	String PHR510011 = "PHR510011"; // sonar url returned failed
	String PHR500005 = "PHR500005"; //No report available
	String PHR500006 = "PHR500006"; //sonar Params saved successfully
	String PHR510012 = "PHR510012"; // sonar Params save failed
	
	
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
	String PHR610010 = "PHR610010"; // failed to update configurations for the environment
	String PHR600007 = "PHR600007"; // environment cloned successfully
	String PHR610011 = "PHR610011"; // failed to clone environment
	String PHR600008 = "PHR600008"; // cron expression calculated successfully
	String PHR610012 = "PHR610012"; // cron expression not available
	String PHR610013 = "PHR610013"; // could not fetch app infos in order to list environments
	String PHR600009 = "PHR600009"; // https server certificate returned successfully
	String PHR610014 = "PHR610014"; // no certificate found
	String PHR600010 = "PHR600010"; // https server certificate added successfully
	String PHR610015 = "PHR610015"; // no certificate to add
	String PHR610016 = "PHR610016"; // unable to add https server certificate
	String PHR600011 = "PHR600011"; // file uploaded successfully
	String PHR610017 = "PHR610017"; // failed to upload file
	String PHR610018 = "PHR610018"; // no file to upload
	String PHR610019 = "PHR610019"; // no file structure found
	String PHR600012 = "PHR600012"; // file structure returned for browsing
	String PHR610020 = "PHR610020"; // failed to return file structure for browsing
	String PHR610021 = "PHR610021"; // file cannot be iterated
	String PHR600013 = "PHR600013"; // entire file structure returned successfully
	String PHR610022 = "PHR610022"; // unable to return entire file structure for browsing
	String PHR600014 = "PHR600014"; // configuration deleted successfully
	String PHR610023 = "PHR610023"; // configuration not available to delete
	String PHR610024 = "PHR610024"; // configurations fail on validation
	String PHR600015 = "PHR600015"; // configurations created successfully
	String PHR610025 = "PHR610025"; // failed to list environments for particular envName
	String PHR610026 = "PHR610026"; // failed to read environments from phresco-env-config.xml
	String PHR610027 = "PHR610027"; // failed to get environments from phresco-fav-config.xml
	String PHR610028 = "PHR610028"; // unable to fetch environments
	String PHR610029 = "PHR610029"; // failed to clone environment during manipulation of phresco-env-config.xml
	String PHR610030 = "PHR610030"; // failed to read zip file while uploading
	String PHR600016 = "PHR600016"; // no certificate found
	String PHR600017 = "PHR600017"; // no certificate to add
	String PHR600018 = "PHR600018"; // no file to upload
	String PHR600019 = "PHR600019"; // no file structure found
	String PHR600020 = "PHR600020"; // environments updated successfully
	String PHR600021 = "PHR600021"; // theme validated  successfully
	String PHR610031 = "PHR610031"; // theme validation failed
	String PHR600022 = "PHR600022"; // content validated successfully
	String PHR610032 = "PHR610032"; // content validation failed
	String PHR600023 = "PHR600023"; // configuration types returned successfully
	String PHR610033 = "PHR610033"; // failed to retrieve configuration types
	String PHR600024 = "PHR600024"; // feature configurations returned successfully
	String PHR610034 = "PHR610034"; // failed to fetch the list of feature configurations
	String PHR600025 = "PHR600025"; // upload File successfully
	String PHR610035 = "PHR610035"; // unable to upload file
	String PHR610036 = "PHR610036"; // upload File failed
	String PHR610037 = "PHR610037"; // unble to get configuration
	String PHR610038 = "PHR610038"; // unable to get input stream File
	String PHR600026 = "PHR600026"; // upload File listed successfully
	String PHR610039 = "PHR610039"; // unable to get target directory
	String PHR610040 = "PHR610040"; // unable to get configuartion
	String PHR600027 = "PHR600027"; // upload File removed successfully
	String PHR610041 = "PHR610041"; // unable to remove the uploaded file
	String PHR610042 = "PHR610042"; // Duplicate Environment
	String PHR610043 = "PHR610043"; // release failed
	String PHR610044 = "PHR610044"; // release failed
	String PHR610045 = "PHR610045"; // release failed
	String PHR610046 = "PHR610046"; // release failed
	String PHR600028 = "PHR600028"; // release success
	String PHR600029 = "PHR600029"; // validation success
	
	/*
	 * Build Page
	 */
	String PHR710001 = "PHR710001"; // no build available
	String PHR700001 = "PHR700001"; // buildinfo listed successfully
	String PHR710002 = "PHR710002"; // buildinfo list failed
	String PHR710003 = "PHR710003"; // failed to read zipped buildinfo file
	String PHR710004 = "PHR710004"; // failed to delete build
	String PHR700002 = "PHR700002"; // build deleted successfully
	String PHR710005 = "PHR710005"; // run against source not yet performed
	String PHR700003 = "PHR700003"; // connection alive
	String PHR710006 = "PHR710006"; // connection not alive
	String PHR710007 = "PHR710007"; // failed to check status
	String PHR710008 = "PHR710008"; // unable to build application
	String PHR700004 = "PHR700004"; // application build started successfully
	String PHR700005 = "PHR700005"; // application deploy started successfully
	String PHR710009 = "PHR710009"; // unable to deploy application
	String PHR700006 = "PHR700006"; // run against source started successfully
	String PHR710010 = "PHR710010"; // run against source failed
	String PHR710011 = "PHR710011"; // could not retrieve particular buildinfo file
	String PHR710012 = "PHR710012"; // unable to download IPA since build number is empty
	String PHR710013 = "PHR710013"; // could not read the project info path or the IPA download path
	String PHR710014 = "PHR710014"; // failed to download IPA
	String PHR700007 = "PHR700007"; // process build started successfully
	String PHR710015 = "PHR710015"; // failed to complete process-build
	String PHR700008 = "PHR700008"; // stop server process started successfully
	String PHR710016 = "PHR710016"; // stop server failed
	String PHR700009 = "PHR700009"; // restart server process started successfully
	String PHR710017 = "PHR710017"; // restart server failed
	String PHR710018 = "PHR710018"; // build name already exists
	String PHR710019 = "PHR710019"; // build number already exists
	String PHR710020 = "PHR710020"; // selected environment in global settings does not have selected configurations
	String PHR710021 = "PHR710021"; // selected environment in this app does not have selected configurations
	String PHR700010 = "PHR700010"; // no builds available
	String PHR700011 = "PHR700011"; // minified files returned successfully
	String PHR700012 = "PHR700012"; // No minified files to return
	String PHR710022 = "PHR710022"; // fail to return  minified files
	String PHR710023 = "PHR710023"; // fail to load pom.xml
	String PHR700013 = "PHR700013"; // minification done successfully
	String PHR710024 = "PHR710024"; // fail to perfrom minification

	/*
	 * Quality Assurance Page
	 */
	
	/*
	 * Common Constants
	 */
	String PHRQ010001 = "PHRQ010001"; // test result not available
	String PHRQ000001 = "PHRQ000001"; // test suites listed successfully
	String PHRQ010002 = "PHRQ010002"; // failed to retrieve test suites
	String PHRQ000002 = "PHRQ000002"; // test cases listed successfully
	String PHRQ010003 = "PHRQ010003"; // test case not available
	String PHRQ010004 = "PHRQ010004"; // failed to list test cases
	String PHRQ000003 = "PHRQ000003"; // test result not available
	String PHRQ000004 = "PHRQ000004"; // test case not available
	
	/*
	 * Unit
	 */
	String PHRQ100001 = "PHRQ100001"; // parameter returned successfully
	String PHRQ110001 = "PHRQ110001"; // unable to get unit test report options
	String PHRQ100002 = "PHRQ100002"; // unit test started successfully
	String PHRQ110002 = "PHRQ110002"; // failed to complete unit test
	
	
	
	
	
	/*
	 * Component
	 */
	String PHRQ200001 = "PHRQ200001"; // component test executed successfully
	String PHRQ210001 = "PHRQ210001"; // failed to execute component test
	
	/*
	 * Functional
	 */
	String PHRQ300001 = "PHRQ300001"; // functional test framework fetched successfully
	String PHRQ310001 = "PHRQ310001"; // failed to get the functional test framework
	String PHRQ300002 = "PHRQ300002"; // 'start hub' process started successfully
	String PHRQ310002 = "PHRQ310002"; // failed to start hub
	String PHRQ300003 = "PHRQ300003"; // 'start node' process started successfully
	String PHRQ310003 = "PHRQ310003"; // failed to start node
	String PHRQ300004 = "PHRQ300004"; // functional test started successfully
	String PHRQ310004 = "PHRQ310004"; // failed to complete functional test
	String PHRQ300005 = "PHRQ300005"; // 'stop hub' process started successfully
	String PHRQ310005 = "PHRQ310005"; // failed to stop hub
	String PHRQ300006 = "PHRQ300006"; // 'stop node' process started successfully
	String PHRQ310006 = "PHRQ310006"; // failed to stop node
	String PHRQ300007 = "PHRQ300007"; // 'check for hub' done successfully
	String PHRQ310007 = "PHRQ310007"; // failed to check for hub
	String PHRQ300008 = "PHRQ300008"; // 'check for node' done successfully
	String PHRQ310008 = "PHRQ310008"; // failed to check for node
	String PHRQ300009 = "PHRQ300009"; // 'show started hub log' started successfully
	String PHRQ310009 = "PHRQ310009"; // failed to show started hub log
	String PHRQ300010 = "PHRQ300010"; // 'show started node log' started successfully
	String PHRQ310010 = "PHRQ310010"; // failed to show started node log
	String PHRQ300011 = "PHRQ300011"; // 'start appium' process started successfully
	String PHRQ310011 = "PHRQ310011"; // failed to start appium
	String PHRQ300012 = "PHRQ300012"; // 'stop appium' process started successfully
	String PHRQ310012 = "PHRQ310012"; // failed to stop appium
		
	/*
	 * Manual
	 */
	String PHRQ410001 = "PHRQ410001"; // failed to get manual template
	String PHRQ410002 = "PHRQ410002"; // failed to upload template
	String PHRQ400001 = "PHRQ400001"; // template uploaded successfully
	String PHRQ410003 = "PHRQ410003"; // manual test file does not exist
	String PHRQ400002 = "PHRQ400002"; // test suites returned successfully
	String PHRQ410004 = "PHRQ410004"; // failed to retrieve test suites
	String PHRQ400003 = "PHRQ400003"; // test cases returned successfully
	String PHRQ410005 = "PHRQ410005"; // failed to retrieve test cases
	String PHRQ400004 = "PHRQ400004"; // test suite added successfully
	String PHRQ410006 = "PHRQ410006"; // failed to add test suite
	String PHRQ400005 = "PHRQ400005"; // test case added successfully
	String PHRQ410007 = "PHRQ410007"; // failed to add test case
	String PHRQ400006 = "PHRQ400006"; // test case updated successfully
	String PHRQ410008 = "PHRQ410008"; // failed to update test case
	String PHRQ400007 = "PHRQ400007"; // TestCase Validation completed successfully
	String PHRQ410009 = "PHRQ410009"; // failed to validate test case
	String PHRQ400008 = "PHRQ400008"; // TestSuite deleted successfully
	String PHRQ400009 = "PHRQ400009"; // TestCase deleted successfully
	String PHRQ410010 = "PHRQ410010"; // failed to delete test cases
	
	/*
	 * Performance
	 */
	String PHRQ500001 = "PHRQ500001"; // performance test options returned successfully
	String PHRQ510001 = "PHRQ510001"; // unable to get performance test result options
	String PHRQ500002 = "PHRQ500002"; // test result files returned successfully
	String PHRQ510002 = "PHRQ510002"; // test not yet executed for this particular type
	String PHRQ510003 = "PHRQ510003"; // failed to return test result files
	String PHRQ500003 = "PHRQ500003"; // devices returned successfully
	String PHRQ510004 = "PHRQ510004"; // failed to return devices
	String PHRQ500004 = "PHRQ500004"; // performance test results returned successfully
	String PHRQ510005 = "PHRQ510005"; // unable to get performance test results
	String PHRQ500005 = "PHRQ500005"; // performance test started successfully
	String PHRQ510006 = "PHRQ510006"; // could not perform performance test
	
	
	/**
	 * Zap Test
	 */
	String PHRQ100003 = "PHRQ100003"; //  zap test started successfully
	String PHRQ110004 = "PHRQ110004"; // failed to complete zap test
	String PHRQ500007 = "PHRQ500007"; // zap test started successfully
	String PHRQ500008 = "PHRQ500008"; // zap test started failed
	String PHRQ500009 = "PHRQ500009"; // zap started successfully
	String PHRQ500010 = "PHRQ5000010"; // zap started failed
	String PHRQ500011 = "PHRQ500011"; // zap stop successfully
	String PHRQ500012 = "PHRQ500012"; // zap stop started failed
	String PHRQ110005 = "PHRQ110005";
	String PHRQ110006 = "PHRQ110006";
	
	
	
	
	
	/*
	 * Load
	 */
	String PHRQ600001 = "PHRQ600001"; // load test options returned successfully
	String PHRQ610001 = "PHRQ610001"; // unable to get load test result options
	String PHRQ600002 = "PHRQ600002"; // load test results returned successfully
	String PHRQ610002 = "PHRQ610002"; // unable to get load test results
	String PHRQ600003 = "PHRQ600003"; // load test started successfully
	String PHRQ610003 = "PHRQ610003"; // could not perform load test
	
	/*
	 * Integration Test
	 */
	String PHRQ700001 = "PHRQ700001"; // integration test started successfully
	String PHRQ710001 = "PHRQ710001"; // could not perform integration test
	
	/*
	 * CI Page
	 */
	String PHR800001 = "PHR800001"; // builds returned successfully
	String PHR810001 = "PHR810001"; // could not retrieve builds
	String PHR800002 = "PHR800002"; // job(s) created successfully
	String PHR810002 = "PHR810002"; // no job(s) found or failed to write job(s) in file
	String PHR810003 = "PHR810003"; // Job(s) creation Failed
	String PHR800003 = "PHR800003"; // Job Validation completed successfully
	String PHR810004 = "PHR810004"; // failed to write job(s) in file
	String PHR810005 = "PHR810005"; // unable to update job(s)
	String PHR800004 = "PHR800004"; // continuous delivery fetched successfully
	String PHR810006 = "PHR810006"; // could not find delivery with this particular name
	String PHR810007 = "PHR810007"; // failed to fetch continuous delivery
	String PHR800005 = "PHR800005"; // continuous delivery listed successfully
	String PHR810008 = "PHR810008"; // failed to fetch required info while listing continuous delivery
	String PHR800006 = "PHR800006"; // job deleted successfully
	String PHR810009 = "PHR810009"; // failed to delete job
	String PHR800007 = "PHR800007"; // build deleted successfully
	String PHR810010 = "PHR810010"; // could not find the job for this build
	String PHR810011 = "PHR810011"; // failed to delete build
	String PHR800008 = "PHR800008"; // build triggered successfully
	String PHR810012 = "PHR810012"; // failed to trigger build
	String PHR810013 = "PHR810013"; // build failed
	String PHR810014 = "PHR810014"; // failed to return mail configuration
	String PHR800009 = "PHR800009"; // Mail configuration retrieved successfully
	String PHR810015 = "PHR810015"; // unable to save mail configuration
	String PHR800010 = "PHR800010"; // build download URL retrieved successfully
	String PHR810016 = "PHR810016"; // failed to access 'ciJob.info' file
	String PHR810017 = "PHR810017"; // jenkins not found
	String PHR800011 = "PHR800011"; // jenkins is alive
	String PHR800012 = "PHR800012"; // job status returned successfully
	String PHR810018 = "PHR810018"; // failed to get job status
	String PHR800013 = "PHR800013"; // job templates listed successfully
	String PHR810019 = "PHR810019"; // failed to fetch job templates from file
	String PHR800014 = "PHR800014"; // job template retrieved successfully
	String PHR810020 = "PHR810020"; // job template not found
	String PHR810021 = "PHR810021"; // failed to retrieve job templates from file
	String PHR800015 = "PHR800015"; // name validated successfully
	String PHR810022 = "PHR810022"; // failed to retrieve job templates for validation
	String PHR800016 = "PHR800016"; // job template added successfully
	String PHR810023 = "PHR810023"; // failed to write job template in file
	String PHR810024 = "PHR810024"; // failure while editing job templates
	String PHR800017 = "PHR800017"; // job templates updated successfully
	String PHR810025 = "PHR810025"; // unable to update job templates
	String PHR800018 = "PHR800018"; // job template deleted successfully
	String PHR810026 = "PHR810026"; // job template is empty
	String PHR810027 = "PHR810027"; // failed to delete job template
	String PHR800019 = "PHR800019"; // job templates fetched successfully
	String PHR810028 = "PHR810028"; // failed to fetch job templates
	String PHR810029 = "PHR810029"; // failed to fetch environments
	String PHR810030 = "PHR810030"; // json exception returned while fetching job templates
	String PHR810031 = "PHR810031"; // job already exists
	String PHR810032 = "PHR810032"; // could not retrieve jenkins info
	String PHR810033 = "PHR810033"; // job(s) have already been created using this job template
	String PHR800020 = "PHR800020"; // job(s) Updated successfully
	String PHR800021 = "PHR800021"; // confluence configuration retrieved successfully
	String PHR810034 = "PHR810034"; // returned Confluence configuration Failed
	String PHR800022 = "PHR800022"; // confluence configuration saved successfully
	String PHR810035 = "PHR810035"; // build download url retrieve failed
	String PHR800023 = "PHR800023"; // last build status returned successfully
	String PHR810036 = "PHR810036"; // failed to get last build status
	String PHR800024 = "PHR800024"; // Returned Jenkins url successfully
	String PHR810037 = "PHR810037"; // failed to get jenkins url
	String PHR810038 = "PHR810038"; // job(s) have already been created using this job template with Repo
	String PHR810039 = "PHR810039"; // job(s) have already been created using this job template for this App
	String PHR800025 = "PHR800025"; // Returned list of modules successfully
	String PHR810040 = "PHR810040"; // Failed to return multi modules
	String PHR810041 = "PHR810041"; // Name already exists after module appending
	String PHR810042 = "PHR810042"; // CI pipeline name already exists
	String PHR800026 = "PHR800026"; // TestFlight configuration retrieved successfully
	String PHR810043 = "PHR810043"; // returned TestFlight configuration Failed
	String PHR810044 = "PHR810044"; // returned TFS configuration successfully
	String PHR810045 = "PHR810045"; // returned TFS configuration Failed
	String PHR810046 = "PHR810046"; // Setup details retrieved successfully
	String PHR810047 = "PHR810047"; // Setup details retrieval Failed
	String PHR800027 = "PHR800027"; // Keychains retrieved successfully
	String PHR810048 = "PHR810048"; // Keychains retrieval Failed
	/*
	 * Upgrade Service
	 */
	String PHR910001 = "PHR910001"; //  unauthorized user
	String PHR900001 = "PHR900001"; // update info returned successfuly
	String PHR910002 = "PHR910002"; // failed to look f or update avaliable
	String PHR900002 = "PHR900002"; // framework upgraded successfully
	String PHR910003 = "PHR910003"; // unauthorized user
	String PHR910004 = "PHR910004"; // failed to upgrade framework
	
	
	
	/*
	 * Process Lock 
	 */
	String PHR10LC0001 = "PHR10LC0001"; // Code in progress
	String PHR10LB0001 = "PHR10LB0001"; // Build in progress
	String PHR10LR0001 = "PHR10LR0001"; // Run against source in progress
	String PHR10LD0001 = "PHR10LD0001"; // Deploy in progress
	String PHR10LUT001 = "PHR10LUT001"; // Unit testing in progress
	String PHR10LCT001 = "PHR10LCT001"; // Component testing in progress
	String PHR10LFT001 = "PHR10LFT001"; // Functional testing in progress
	String PHR10LPT001 = "PHR10LPT001"; // Performance testing in progress
	String PHR10LLT001 = "PHR10LLT001"; // Load testing in progress
	String PHR10LAR001 = "PHR10LAR001"; // Add to repo in progress
	String PHR10LUR001 = "PHR10LUR001"; // commit in progress
	String PHR10LCR001 = "PHR10LCR001"; // update in progress
	String PHR10LIM001 = "PHR10LIM001"; // import in progress
	String PHR10LMIN01 = "PHR10LMIN01"; //minification in progress
	
	/*
	 * Dashboard Service
	 */
	String PHRD000001 = "PHRD000001"; // Dashboard configured retrieved successfully
	String PHRD000002 = "PHRD000002"; // Dashboard retrieved successfully
	String PHRD000003 = "PHRD000003"; // Dashboard update  successfully
	String PHRD000004 = "PHRD000004"; // Dashboard widget added successfully
	String PHRD000005 = "PHRD000005"; // Dashboard widget retrieved successfully
	String PHRD000006 = "PHRD000006"; // Dashboard widget updated successfully
	String PHRD000007 = "PHRD000007"; // All dashboard widgets listed successfully
	String PHRD000008 = "PHRD000008"; // All application dashboards listed successfully
	String PHRD000009 = "PHRD000009"; // Search results returned successfully
	String PHRD000011 = "PHRD000011"; // Dashboard deleted successfully
	String PHRD000012 = "PHRD000012"; // Widget deleted successfully

	String PHRD010001 = "PHRD010001"; // Dashboard configuration failed
	String PHRD010002 = "PHRD010002"; // Dashboard not found
	String PHRD010003 = "PHRD010003"; // Dashboard updation failed
	String PHRD010004 = "PHRD010004"; // Dashboard widget adding failed
	String PHRD010005 = "PHRD010005"; // Dashboard widget not found
	String PHRD010006 = "PHRD010006"; // Dashboard widget updation failed
	String PHRD010007 = "PHRD010007"; // No widgets present
	String PHRD010008 = "PHRD010008"; // No dashboards present
	String PHRD010009 = "PHRD100009"; // Unknown dashboard datatype passed
	String PHRD010010 = "PHRD100010"; // Exception occurred during the search data
	String PHRD010011 = "PHRD010011"; // Dashboard deletion failed
	String PHRD010012 = "PHRD010012"; // Widget deletion failed
	

	/*
	 * Liquibase Service
	 */
	String PHRL000001 = "PHRL000001"; // Liquibase DbDoc started successfully
	String PHRL010001 = "PHRL010001"; // unable to execute Liquibase DbDoc
	String PHRL000002 = "PHRL000002"; // Liquibase Update started successfully
	String PHRL010002 = "PHRL010002"; // unable to execute Liquibase Update
	String PHRL000003 = "PHRL000003"; // Liquibase Install started successfully
	String PHRL010003 = "PHRL010003"; // unable to execute Liquibase Install
	String PHRL000004 = "PHRL000004"; // Liquibase DbDiff started successfully
	String PHRL010004 = "PHRL010004"; // unable to execute Liquibase DbDiff
	String PHRL000005 = "PHRL000005"; // Liquibase Status started successfully
	String PHRL010005 = "PHRL010005"; // unable to execute Liquibase Status
	String PHRL000006 = "PHRL000006"; // Liquibase Rollback by Count started successfully
	String PHRL010006 = "PHRL010006"; // unable to execute Liquibase Rollback by Count
	String PHRL000007 = "PHRL000007"; // Liquibase Rollback to Date started successfully
	String PHRL010007 = "PHRL010007"; // unable to execute Liquibase Rollback to Date
	String PHRL000008 = "PHRL000008"; // Liquibase Rollback to Tag started successfully
	String PHRL010008 = "PHRL010008"; // unable to execute Liquibase Rollback to Tag
	String PHRL000009 = "PHRL000009"; // Liquibase Tag started successfully
	String PHRL010009 = "PHRL010009"; // unable to execute Liquibase Tag
	String PHRL000010 = "PHRL000010"; // environment names retrieved successfully
	String PHRL010010 = "PHRL010010"; // unable to retrieve environment names from phresco-env-config.xml
	String PHRL000011 = "PHRL000011"; // configuration names retrieved successfully
	String PHRL010011 = "PHRL010011"; // unable to retrieve configuration names from phresco-env-config.xml
	String PHRL010012 = "PHRL010012"; // unsupported database type
	String PHRL000012 = "PHRL000012"; // database tags retrieved successfully
	String PHRL010013 = "PHRL010013"; // unable to retrieve tags
	
	/*
	 * UploadBuild Service
	 */
	String PHRU000001 = "PHRU000001"; // Build uploaded to TestFlight server successfully
	String PHRU010001 = "PHRU010001"; // Build uploading failed
	String PHRU010002 = "PHRU010002"; // unable to upload build since build number is empty

	
	/**
	 * Repository service
	 */
	String PHRSR00001 = "PHRSR00001"; //Source repo branches and tags listed successfully
	String PHRSR10001 = "PHRSR10001"; //Failed to list source repo branches and tags
	String PHRSR00002 = "PHRSR00002"; //Branch created successfully
	String PHRSR10002 = "PHRSR10002"; //Failed to checkout
	String PHRSR10003 = "PHRSR10003"; //Failed to create branch
	String PHRSR10004 = "PHRSR10004"; //Failed to create branch or download in to workspace
	String PHRSR10005 = "PHRSR10005"; //Failed to create tag
	String PHRSR00003 = "PHRSR00003"; //Tag created succesfully
	String PHRSR10006 = "PHRSR10006"; //Delete temp failure
	String PHRSR00007 = "PHRSR00007"; //Release started successfully
	String PHRSR10008 = "PHRSR10008"; //Release startup failed
	String PHRSR10007 = "PHRSR10007"; //Authentication Failure
	String PHRSR10009 = "PHRSR10009"; //Credential save failed
	String PHRSR00009 = "PHRSR00009"; //Credential save successfully
	
	/**
	 * Publication Configuration
	 */
	
	String PHRTR0001 = "PHRTR0001"; // Publication Saved successfully
	String PHRTR0002 = "PHRTR0002"; // Publication retrieved successfully
	String PHRTR0003 = "PHRTR0003"; // Publication List fetched successfully
	String PHRTR0004 = "PHRTR0004"; // Publication Target fetched successfully
	String PHRTR0005 = "PHRTR0005"; // Publication created successfully
	String PHRTR0006 = "PHRTR0006"; // Environment fetched successfully
	String PHRTR0007 = "PHRTR0007"; // Site published successfully
	String PHRTR0008 = "PHRTR0008"; // Publish queue status fetched successfully
	String PHRTR0009 = "PHRTR0009"; // Publication clone created successfully
	String PHRTR0010 = "PHRTR0010"; // Site Unpublished successfully
	String PHRTR0011 = "PHRTR0011"; // Published environment  fetched successfully
	
	String PHRTR1001 = "PHRTR1001"; // Publication Save Failed
	String PHRTR1002 = "PHRTR1002"; // Publication retrieve failed
	String PHRTR1003 = "PHRTR1003"; // Publication fetch failed
	String PHRTR1004 = "PHRTR1004"; // content or theme  creation needed to proceed
	String PHRTR1005 = "PHRTR1005"; // publication creation failed
	String PHRTR1006 = "PHRTR1006"; // check Publicationconfig.xml File
	String PHRTR1007 = "PHRTR1007"; // Failed to fetch environment3
	String PHRTR1008 = "PHRTR1008"; // Failed to publish site
	String PHRTR1009 = "PHRTR1009"; // Failed to fetch publish queue status
	String PHRTR1010 = "PHRTR1010"; // Publication cloned Failed
	String PHRTR1011 = "PHRTR1011"; // Failed to UnPublish site
	String PHRTR0012 = "PHRTR0012"; // Environment published  fetched Failed
}
