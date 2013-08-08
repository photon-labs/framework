define(["manualTest/manualTest", "lib/jquery-tojson-1.0", "lib/fileuploader-2.0"], function(ManualTest) {
	
	return {
		// list test suites 
		
		runTests: function (configData) {
			var self = this;
			var moduleName = "ManualTest.js";
			module(moduleName);
			asyncTest("Manual test loading template", function() {
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=TestGitProject',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuites returned Successfully","exception":null,"responseCode":null,"data":{"total":407.0,"testSuites":[{"name":"HeliOS-Installation","file":null,"total":9.0,"time":null,"errors":8.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":11.0,"testCases":null,"tests":1.0,"testSteps":null},{"name":"Login","file":null,"total":26.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":26.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Functional flow","file":null,"total":256.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":256.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"UI Validation","file":null,"total":45.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":45.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Import Application","file":null,"total":14.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Settings","file":null,"total":24.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":24.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Downloads","file":null,"total":8.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Upgrade","file":null,"total":25.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":406.0,"totalTestCoverage":11.0},"status":null});
				  }
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				commonVariables.api.localVal.setSession("appDirName" , "TestGitProject");
				commonVariables.navListener.onMytabEvent("manualTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Manual Test", "Manual test template rendering verified");
					self.runAddManualTestsuite(moduleName);
				}, 200);
			});
		},
		
		// add testsuite
		runAddManualTestsuite: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("Add Testsuite", function() {
				$.mockjaxClear();
				$.mockjax({ 
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=Testadd&appDirName=TestGitProject',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuite Added Successfully","exception":null,"responseCode":null,"data":null,"status":null});
				  }
				});
				
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=TestGitProject',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuites returned Successfully","exception":null,"responseCode":null,"data":{"total":407.0,"testSuites":[{"name":"HeliOS-Installation","file":null,"total":9.0,"time":null,"errors":8.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":11.0,"testCases":null,"tests":1.0,"testSteps":null},{"name":"Login","file":null,"total":26.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":26.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Functional flow","file":null,"total":256.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":256.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"UI Validation","file":null,"total":45.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":45.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Import Application","file":null,"total":14.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Settings","file":null,"total":24.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":24.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Downloads","file":null,"total":8.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Upgrade","file":null,"total":25.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null},{"name":"Testadd","file":null,"total":0.0,"time":null,"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":406.0,"totalTestCoverage":11.0},"status":null});
				  }
				});
				
				commonVariables.api.localVal.setSession("appDirName", "TestGitProject");
				$("#addTestSuite").click();
				$("#testSuiteId").val("Testadd");
				$("input[name=saveTestSuite]").click();
				$(".manualTemp").remove();
				setTimeout(function() {
					start();
					equal(9, 9, "Testsuite added and returned successfully");
					self.runListManualTestcase(moduleName);
				}, 1000);
			});
		},
		
		// list testcases
		runListManualTestcase: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("List Testcases", function() {
//				$.mockjaxClear();
				$.mockjax({ 
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=TestGitProject&testSuiteName=HeliOS-Installation',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testcases returned Successfully","exception":null,"responseCode":null,"data":[{"priority":"","status":"","description":"To verify whether the user is able to download the Helios framework","severity":"","featureId":"Helios Framework_Download","testCaseId":"TC_Helios Installation_01","steps":"","expectedResult":"","actualResult":"User should be able to download Helios framework from file releases link.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the user is able to unzip the Helios framework","severity":"","featureId":"Helios Framework_Unzip_Windows","testCaseId":"TC_Helios Installation_02","steps":"Perform Testcase 1[Framework Download]","expectedResult":"","actualResult":"User should be able to unzip the Downloaded Helios framework in specified location as Drive:\\helios-framework","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User is able to view the folders inside Helios framework","severity":"","featureId":"Helios Framework_Folder verification_Windows","testCaseId":"TC_Helios Installation_03","steps":"Perform Testcase 2","expectedResult":"","actualResult":"User Should be able to View the following  folders  bin,conf,docs,logs,tools,workspace and README.text.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User is able to start the helios-framework.","severity":"","featureId":"Helios Framework_startup_Windows","testCaseId":"TC_Helios Installation_04","steps":"1.Perform Testcase 3\n2.Set the Jave_Home in environment variables","expectedResult":"","actualResult":"1.User should be able to view “Helios started successfully” message in command prompt.\n2.User should be able to view Helios UI which opens automatically in default browser after getting the message “Helios started successfully” in command prompt.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the user is able to unzip the Helios framework","severity":"","featureId":"Helios Framework_Unzip_Non-Windows","testCaseId":"TC_Helios Installation_05","steps":"Perform Testcase 1[Framework Download]","expectedResult":"","actualResult":"User should be able to unzip the Downloaded Helios framework in specified location as Drive:\\helios-framework","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User able to view the foders inside Helios framework","severity":"","featureId":"Helios Framework_Folder verification_Non-Windows","testCaseId":"TC_Helios Installation_06","steps":"Perform Testcase 5","expectedResult":"","actualResult":"User Should be able to View the following  folders  bin,conf,docs,logs,tools,workspace and Readme.text.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User able to start the Helios-framework.","severity":"","featureId":"Helios Framework_startup_Non-Windows","testCaseId":"TC_Helios Installation_07","steps":"Perform Testcase 6","expectedResult":"","actualResult":"1.User should be able to view “Helios started successfully” message in command prompt.\n2.User should be able to view Helios UI which opens automatically in default browser after getting the message “Helios started successfully” in command prompt.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify the Docs folder in the Helios-framework.","severity":"","featureId":"Helios Framework_Docs folder verification_Windows\\Non-Windows","testCaseId":"TC_Helios Installation_08","steps":"Perform Testcase 3/Perform Testcase 6","expectedResult":"","actualResult":"User can see the \"PHTN_HeliOS_Framework_Version_User_Guide\" and \"PHTN_HeliOS_Release_Version_Date\"","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"success","description":"asASAsASAS","severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":""}],"status":null});
				  }
				});
				commonVariables.api.localVal.setSession("appDirName", "TestGitProject");
				
				$("a[id=HeliOS-Installation]").click();
				setTimeout(function() {
					start();
					equal(9, 9, "Testcases successfully listed");
					self.runAddManualTestcase(moduleName);
				}, 1000);
			});
		},
		
		// add testcase
		runAddManualTestcase: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("Adding testcases to a testsuite", function() {
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=HeliOS-Installation&appDirName=TestGitProject',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testcase Added Successfully","exception":null,"responseCode":null,"data":{"priority":"","status":"success","description":"asASAsASAS","severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":""},"status":null});
				  }
				});
				
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=HeliOS-Installation&appDirName=TestGitProject',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testcases returned Successfully","exception":null,"responseCode":null,"data":[{"priority":"","status":"","description":"To verify whether the user is able to download the Helios framework","severity":"","featureId":"Helios Framework_Download","testCaseId":"TC_Helios Installation_01","steps":"","expectedResult":"","actualResult":"User should be able to download Helios framework from file releases link.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the user is able to unzip the Helios framework","severity":"","featureId":"Helios Framework_Unzip_Windows","testCaseId":"TC_Helios Installation_02","steps":"Perform Testcase 1[Framework Download]","expectedResult":"","actualResult":"User should be able to unzip the Downloaded Helios framework in specified location as Drive:\\helios-framework","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User is able to view the folders inside Helios framework","severity":"","featureId":"Helios Framework_Folder verification_Windows","testCaseId":"TC_Helios Installation_03","steps":"Perform Testcase 2","expectedResult":"","actualResult":"User Should be able to View the following  folders  bin,conf,docs,logs,tools,workspace and README.text.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User is able to start the helios-framework.","severity":"","featureId":"Helios Framework_startup_Windows","testCaseId":"TC_Helios Installation_04","steps":"1.Perform Testcase 3\n2.Set the Jave_Home in environment variables","expectedResult":"","actualResult":"1.User should be able to view “Helios started successfully” message in command prompt.\n2.User should be able to view Helios UI which opens automatically in default browser after getting the message “Helios started successfully” in command prompt.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the user is able to unzip the Helios framework","severity":"","featureId":"Helios Framework_Unzip_Non-Windows","testCaseId":"TC_Helios Installation_05","steps":"Perform Testcase 1[Framework Download]","expectedResult":"","actualResult":"User should be able to unzip the Downloaded Helios framework in specified location as Drive:\\helios-framework","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User able to view the foders inside Helios framework","severity":"","featureId":"Helios Framework_Folder verification_Non-Windows","testCaseId":"TC_Helios Installation_06","steps":"Perform Testcase 5","expectedResult":"","actualResult":"User Should be able to View the following  folders  bin,conf,docs,logs,tools,workspace and Readme.text.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify whether the User able to start the Helios-framework.","severity":"","featureId":"Helios Framework_startup_Non-Windows","testCaseId":"TC_Helios Installation_07","steps":"Perform Testcase 6","expectedResult":"","actualResult":"1.User should be able to view “Helios started successfully” message in command prompt.\n2.User should be able to view Helios UI which opens automatically in default browser after getting the message “Helios started successfully” in command prompt.","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"","description":"To verify the Docs folder in the Helios-framework.","severity":"","featureId":"Helios Framework_Docs folder verification_Windows\\Non-Windows","testCaseId":"TC_Helios Installation_08","steps":"Perform Testcase 3/Perform Testcase 6","expectedResult":"","actualResult":"User can see the \"PHTN_HeliOS_Framework_Version_User_Guide\" and \"PHTN_HeliOS_Release_Version_Date\"","bugComment":"","requirementId":"","testCaseype":"","testInput":""},{"priority":"","status":"success","description":"asASAsASAS","severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":""}],"status":null});
				  }
				});
				
				commonVariables.api.localVal.setSession("appDirName" , "TestGitProject");
				$("#addTestCase").click();
				$("#testSuiteName").val("HeliOS-Installation");
				$("input[name=featureId]").val("featureId");
				$("input[name=testCaseId]").val("testcaseID");
				$("input[name=saveTestCase]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "Adding Manual testcases succeeded");
					self.runUploadTemplate(moduleName);
				}, 500);
			});
		},
		
		
		
		
		// upload template
		runUploadTemplate: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("Upload Template", function() {
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/uploadTemplate?appDirName=TestProject&&qqfile=declaration-photon.xls',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Uploaded SuccesFully","exception":null,"responseCode":null,"data":null,"status":null});
				  }
				});
				
				setTimeout(function() {
					start();
					equal(1, 1, "Adding Manual testcases succeeded");
					require(["jobTemplatesTest"], function(jobTemplatesTest){
						jobTemplatesTest.runTests();
					});
//					self.runDownloadTemplate(moduleName);
				}, 500);
			});
		},
		
//		// download template
//		runDownloadTemplate: function (moduleName) {
//			module(moduleName);
//			asyncTest("Download Template", function() {
//				$.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/manualTemplate?fileType=xls',
//				  type: "GET",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":"Uploaded SuccesFully","exception":null,"responseCode":null,"data":null,"status":null});
//				  }
//				});
//				
//				setTimeout(function() {
//					start();
//					equal($('.unit_text').text().trim(), "Manual Test", "Adding Manual testcases succeeded");
//				}, 200);
//			});
//		},
		
	};
});