define(["manualTest/manualTest", "lib/jquery-tojson-1.0", "lib/fileuploader-2.0"], function(ManualTest) {
	
	return {
		// list test suites 
		runTests: function (configData) {
			var self = this;
			var moduleName = "ManualTest.js", listTestSuites;
			module(moduleName);
			asyncTest("Manual test loading template", function() {
				self.listTestSuites = $.mockjax({
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=HtmlJquery-html5jquerymobilewidget',
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400002","data":{"testSuites":[{"errors":7.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":7.0,"testCoverage":13.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":8.0},{"errors":28.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":28.0,"testCoverage":3.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":29.0},{"errors":261.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":261.0,"testCoverage":0.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Jquery Multi Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":262.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import Application","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery multi Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery mobile Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Downloads","file":null,"time":null,"total":8.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdssd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"ss","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"ssss","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"fd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dsdsd","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":772.0,"totalTestCoverage":116.0,"total":776.0},"status":"success"});
					  }
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				commonVariables.api.localVal.setSession("appDirName" , "HtmlJquery-html5jquerymobilewidget");
				commonVariables.navListener.onMytabEvent("manualTest");
				setTimeout(function() {
					start();
					equal("Manual Test", "Manual Test", "Manual test template rendering verified");
					self.runshowTestsuitePopUp(moduleName);
				}, 1000);
			});
		},
		
		// add testsuite
		runshowTestsuitePopUp: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("show Testsuite PopUp", function() {
				setTimeout(function() {
					$("#addTestSuite").click();
					start();
					equal(11, 10+1, "Popup shown");
					self.runAddTestsuite(moduleName);
				}, 500);
			});
		},
		
		runAddTestsuite: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("Manual test loading template", function() {
				$.mockjaxClear(self.listTestSuites);
				$.mockjax({ 
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=sampletestsuite&appDirName=HtmlJquery-html5jquerymobilewidget',
					  type: "POST",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400004","data":null,"status":"success"});
					  }
				});
				
				self.listTestSuites = $.mockjax({
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=HtmlJquery-html5jquerymobilewidget',
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400002","data":{"testSuites":[{"errors":7.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":7.0,"testCoverage":13.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":8.0},{"errors":28.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":28.0,"testCoverage":3.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":29.0},{"errors":261.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":261.0,"testCoverage":0.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Jquery Multi Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":262.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import Application","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery multi Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery mobile Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Downloads","file":null,"time":null,"total":8.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdssd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"ss","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"ssss","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"fd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dsdsd","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":772.0,"totalTestCoverage":116.0,"total":776.0},"status":"success"});
					  }
				});
				
				commonVariables.api.localVal.setSession("appDirName", "HtmlJquery-html5jquerymobilewidget");
				$("#testSuiteId").val("sampletestsuite");
				$("input[name=saveTestSuite]").click();
				setTimeout(function() {
					start();
					equal(8, 8, "testsuite added successfully and listed");
//					self.runListManualTestcase(moduleName);
					require(["performanceTestTest"], function(performanceTestTest){
						performanceTestTest.runTests();
					});
				}, 1000);
			});
		},
		
		
////		 list testcases
//		runListManualTestcase: function (moduleName) {
//			module(moduleName);
//			var self = this, listTestcase;
//			asyncTest("List Testcases", function() {
//				self.listTestcase = $.mockjax({ 
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=HtmlJquery-html5jquerymobilewidget&testSuiteName=Downloads',
//				  type: "GET",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""}],"status":"success"});
//				  }
//				});
//				
//				commonVariables.api.localVal.setSession("appDirName", "HtmlJquery-html5jquerymobilewidget");
//				$("a[id=Downloads]").click();
//				setTimeout(function() {
//					start();
//					equal(8, 8, "Testcases successfully listed");
//					self.runShowManualTestcasePopUp(moduleName);
//				}, 500);
//			});
//		},
		
//		// add testcase
//		runShowManualTestcasePopUp: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("show Testcase PopUp", function() {
//				setTimeout(function() {
//					$("#addTestCase").click();
//					start();
//					equal(11, 10+1, "Testcase Popup shown");
////					self.runAddTestcase(moduleName);
//				}, 300);
//			});
//		},
			
//		runAddTestcase: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			
//			asyncTest("Adding testcases to a testsuite", function() {
//				$.mockjaxClear(self.listTestcase);
//				$.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=Downloads&appDirName=HtmlJquery-html5jquerymobilewidget',
//				  type: "POST",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400005","data":{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"},"status":"success"});
//				  }
//				});
//				
//				self.listTestcase = $.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=HtmlJquery-html5jquerymobilewidget&testSuiteName=Downloads',
//				  type: "GET",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""},{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"}],"status":"success"});
//				  }
//				});
//				
//				commonVariables.api.localVal.setSession("appDirName" , "HtmlJquery-html5jquerymobilewidget");
//				var testSuiteName = commonVariables.testSuiteName;
//				$("#testSuiteName").val(testSuiteName);
//				$("input[name=featureId]").val("sampletestcase");
//				$("input[name=testCaseId]").val("sampletestcase");
//				$("input[name=saveTestCase]").click();
//				
//				setTimeout(function() {
//					start();
//					equal(1, 1, "Adding Manual testcases succeeded");
////					self.runUpdateTestcasePopup(moduleName);
//				}, 500);
//			});
//		},
//		
//		runUpdateTestcasePopup : function(moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("show Update Testcase PopUp", function() {
//				setTimeout(function() {
//					$("#dynPopup8").click();
//					start();
//					equal(11, 10+1, "Update testcase Popup shown");
//					self.runUpdateTestcase(moduleName);
//				}, 1000);
//			});
//		},
//		
//		runUpdateTestcase : function (moduleName) {
//			module(moduleName);
//			var self = this;
//			
//			asyncTest("Update testcase", function() {
//				$.mockjaxClear(self.listTestcase);
//				$.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=HtmlJquery-html5jquerymobilewidget&testSuiteName=Downloads',
//				  type: "PUT",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400006","data":{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"},"status":"success"});
//				  }
//				});
//				
//				$.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=HtmlJquery-html5jquerymobilewidget&testSuiteName=Downloads',
//				  type: "GET",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""},{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"}],"status":"success"});
//				  }
//				});
//				
//				commonVariables.api.localVal.setSession("appDirName", "HtmlJquery-html5jquerymobilewidget");
//				var testSuiteName = commonVariables.testSuiteName;
//				$("#testSuiteName").val(testSuiteName);
//				$("input[name=featureId]").val("sampletestcase");
//				$("input[name=testCaseId]").val("sampletestcase");
//				$("textarea[name=steps]").val("sampletestcase");
//				$("input[name=actualResult]").val("sampletestcase");
//				$("textarea[name=description]").val("sampletestcase");
//				$("textarea[name=expectedResult]").val("1sampletestcase");
//				$("input[name=updateTestCase]").click();
//				
//				setTimeout(function() {
//					start();
//					equal(1, 1, "Update testcases succeeded");
////					self.runShowDownloadPopUp(moduleName);
//					
////					require(["dynamicPageTest"], function(dynamicPageTest){
////						dynamicPageTest.runTests();
////					});
//				}, 1500);
//			});
//		},
		
//		runTestcaseEmptyCheck: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			
//			asyncTest("Adding testcases to a testsuite", function() {
//				$.mockjaxClear(self.listTestcase);
//				$.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=Downloads&appDirName=HtmlJquery-html5jquerymobilewidget',
//				  type: "POST",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400005","data":{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"},"status":"success"});
//				  }
//				});
//				
//				self.listTestcase = $.mockjax({
//				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=HtmlJquery-html5jquerymobilewidget&testSuiteName=Downloads',
//				  type: "GET",
//				  dataType: "json",
//				  contentType: "application/json",
//				  status: 200,
//				  response : function() {
//					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""},{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"}],"status":"success"});
//				  }
//				});
//				
//				commonVariables.api.localVal.setSession("appDirName" , "HtmlJquery-html5jquerymobilewidget");
//				var testSuiteName = commonVariables.testSuiteName;
//				$("#testSuiteName").val(testSuiteName);
//				$("input[name=featureId]").val("");
//				$("input[name=testCaseId]").val("");
//				$("input[name=saveTestCase]").click();
//				
//				setTimeout(function() {
//					start();
//					equal(1, 1, "Adding Manual testcases succeeded");
//					self.runUpdateTestcasePopup(moduleName);
//				}, 1500);
//			});
//		},
//		
//		
		
////		 download template
//		runShowDownloadPopUp: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("Show Download PopUp", function() {
//				setTimeout(function() {
//					$('#show_downloadTemplate_popup').click();
//					start();
//					equal(11, 10+1, "Update testcase Popup shown");
////					self.runGraphicalView(moduleName);
//				}, 1000);
//			});
//		},
		
		// add testsuite
//		runGraphicalView: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("Graphical view ", function() {
//				setTimeout(function() {
//					$("#graphicalView").click();
//					start();
//					equal(11, 10+1, "graphical View rendered");
//					self.runTabularView(moduleName);
//				}, 1000);
//			});
//		},
		
		// add testsuite
//		runTabularView: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("Tabular view ", function() {
//				setTimeout(function() {
//					$("#tabularView").click();
//					start();
//					equal(11, 10+1, "Tabular view rendered");
//					self.runShowUploadPopup(moduleName);
//				}, 1000);
//			});
//		},
		
		// add testsuite
//		runShowUploadPopup: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("PopUp Upload template opened ", function() {
//				setTimeout(function() {
//					$('#show_uploadTemplate_popup').click();
//					start();
//					equal(11, 11, "PopUp Upload template opened");
////					self.runuploadTemplate(moduleName);
//				}, 1000);
//			});
//		},
		
		// upload template
//		runuploadTemplate: function (moduleName) {
//			module(moduleName);
//			var self = this;
//			asyncTest("Manual test loading template", function() {
//				$.mockjaxClear(self.listTestSuites);
//				self.listTestSuites = $.mockjax({
//					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=HtmlJquery-html5jquerymobilewidget',
//					  type: "GET",
//					  dataType: "json",
//					  contentType: "application/json",
//					  status: 200,
//					  response : function() {
//						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400002","data":{"testSuites":[{"errors":7.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":7.0,"testCoverage":13.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":8.0},{"errors":28.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":28.0,"testCoverage":3.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":29.0},{"errors":261.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":261.0,"testCoverage":0.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Jquery Multi Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":262.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import Application","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery multi Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery mobile Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Downloads","file":null,"time":null,"total":8.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdssd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"ss","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"ssss","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"fd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dsdsd","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":772.0,"totalTestCoverage":116.0,"total":776.0},"status":"success"});
//					  }
//				});
//				
//				commonVariables.api.localVal.setSession("appDirName", "sdsdd");
//				self.createUploader();
//				$("input[name=saveTestSuite]").click();
//				setTimeout(function() {
//					start();
//					equal(8, 8, "testsuite added successfully and listed");
//					self.runListManualTestcase(moduleName);
//				}, 1000);
//			});
//		},
	};
});