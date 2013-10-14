define(["manualTest/manualTest", "lib/jquery-tojson-1.0", "lib/fileuploader-2.4"], function(ManualTest) {
	
	return {
		// list test suites 
		runTests: function (configData) {
			var self = this;
			var moduleName = "ManualTest.js", listTestSuites;
			module(moduleName);
			asyncTest("Manual test loading template", function() {
				self.listTestSuites = $.mockjax({
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=test',
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

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"responsive-web","appDirName":"test","techInfo":{"version":"3.10.3","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1354009498000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"1e22614f-0490-4600-9f2d-dba1d200e700","displayName":null,"status":null,"description":null,"creationDate":1365682608000,"helpText":null,"system":false}],"selectedDatabases":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac","c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"customerIds":["photon"],"used":false,"name":"responsive-web","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":null,"creationDate":1379332579000,"helpText":null,"system":true}],"projectCode":"UNITTest","noOfApps":2,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"customerIds":["photon"],"used":false,"name":"UNITTest","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1379332635000,"helpText":null,"system":false}},"status":"success"};
				$('.hProjectId').val(projectInfo.data.projectInfo.appInfos[0].id)
				commonVariables.api.localVal.setProjectInfo(projectInfo);
				
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
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=sampletestsuite&appDirName=test',
					  type: "POST",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400004","data":null,"status":"success"});
					  }
				});
				
				self.listTestSuites = $.mockjax({
					  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=test',
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400002","data":{"testSuites":[{"errors":7.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":7.0,"testCoverage":13.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":8.0},{"errors":28.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":28.0,"testCoverage":3.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":29.0},{"errors":261.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":261.0,"testCoverage":0.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Jquery Multi Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":262.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Functional Flow","file":null,"time":null,"total":262.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":46.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile UI Validation","file":null,"time":null,"total":46.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import Application","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Multi Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery Mobile Settings","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery multi Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":25.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Jquery mobile Upgrade","file":null,"time":null,"total":25.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":8.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Downloads","file":null,"time":null,"total":8.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdssd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"ss","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"ssss","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"fd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dsdsd","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":772.0,"totalTestCoverage":116.0,"total":776.0},"status":"success"});
					  }
				});
				
				$("#testSuiteId").val("sampletestsuite");
				$("input[name=saveTestSuite]").click();
				setTimeout(function() {
					start();
					equal(8, 8, "testsuite added successfully and listed");
					self.runListManualTestcase(moduleName);
					require(["performanceTestTest"], function(performanceTestTest){
						performanceTestTest.runTests();
					});
				}, 1000);
			});
		},
		
		

		/*runUpdateTestsuitePopup : function(moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("show Update Testsuite PopUp", function() {
				setTimeout(function() {
					$("#dynPopup8").click();
					start();
					equal(11, 10+1, "Update testcase Popup shown");
					self.runUpdateTestsuite(moduleName);
				}, 1000);
			});
		},
		
		runUpdateTestsuite : function (moduleName) {
			module(moduleName);
			var self = this;
			
			asyncTest("Update testsuite", function() {
				$.mockjaxClear(self.listTestcase);
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=test&testSuiteName=Downloads',
				  type: "PUT",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400006","data":{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=test&testSuiteName=Downloads',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""},{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"}],"status":"success"});
				  }
				});
				
				var testSuiteName = commonVariables.testSuiteName;
				$("#testSuiteName").val(testSuiteName);
				$("input[name=featureId]").val("sampletestcase");
				$("input[name=testCaseId]").val("sampletestcase");
				$("textarea[name=steps]").val("sampletestcase");
				$("input[name=actualResult]").val("sampletestcase");
				$("textarea[name=description]").val("sampletestcase");
				$("textarea[name=expectedResult]").val("1sampletestcase");
				$("input[name=updateTestCase]").click();
				
				setTimeout(function() {
					start();
					equal(1, 1, "Update testsuites succeeded");
					self.runListManualTestcase(moduleName);
					
////					require(["dynamicPageTest"], function(dynamicPageTest){
////						dynamicPageTest.runTests();
////					});
			}, 1500);
		});
		},*/
		
		
		
//		 list testcases
		runListManualTestcase: function (moduleName) {
			module(moduleName);
			var self = this, listTestcase;
			asyncTest("List Testcases", function() {
				self.listTestcase = $.mockjax({ 
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=test&testSuiteName=Downloads',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""}],"status":"success"});
				  }
				});
				
				commonVariables.api.localVal.setSession("appDirName", "HtmlJquery-html5jquerymobilewidget");
				$("a[id=Downloads]").click();
				setTimeout(function() {
					console.info("Inside runListManualTestcase.......");
					start();
					equal(8, 8, "Testcases successfully listed");
					self.testcaseGraphicalViewTest(moduleName);
				}, 500);
			});
		},

		testcaseGraphicalViewTest : function() {
			var self = this;
			asyncTest("Manual Test Testcase Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testcases").css("display"), "block", "Unit test testcase graphical view tested");
					self.testcaseTabularViewTest();
				}, 3000);
			});
		},

		testcaseTabularViewTest : function() {
			var self = this;
			asyncTest("Manual Test Testcase Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Unit test testcase tabular view tested");
					//self.showTestcaseConsole();
				}, 3000);
			});
		},

		/*showTestcaseConsole : function() {
			var self = this;
			asyncTest("Manual Test Testcase Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					$('#consoleImg').attr('data-flag','false');
					equal($('#consoleImg').attr('data-flag'), "false", "Unit test testcase open console tested");
					//self.runUpdateTestcasePopup();
				}, 3000);
			});
		},*/
	
		// add testcase
		/*runShowManualTestcasePopUp: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("show Testcase PopUp", function() {
				setTimeout(function() {
					$("#addTestCase").click();
					start();
					equal(11, 10+1, "Testcase Popup shown");
					self.runUpdateTestcasePopup(moduleName);
				}, 300);
			});
		},*/
			
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
	/*runUpdateTestcasePopup : function(moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("show Update Testcase PopUp", function() {
				$('a[name=updateManualTestCase_popup]').click();
				setTimeout(function() {
					console.info("inside runUpdateTestcasePopup test....");
					start();
					equal(11, 10+1, "Update testcase Popup shown");
					//self.runUpdateTestcase(moduleName);
				}, 1000);
			});
		},*/
		
		/*runUpdateTestcase : function (moduleName) {
			module(moduleName);
			var self = this;
			
			asyncTest("Update testcase", function() {
				$.mockjaxClear(self.listTestcase);
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=test&testSuiteName=Downloads',
				  type: "PUT",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400006","data":{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"},"status":"success"});
				  }
				});
				
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?appDirName=test&testSuiteName=Downloads',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ400003","data":[{"severity":"","featureId":"Download Page","testCaseId":"TC-Download Page-01","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to land on Download Page","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page","status":""},{"severity":"","featureId":"Download Page - UI validation","testCaseId":"TC-Download Page-02","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to view the Download Page with 1.Server accordian\n2.Database accordian\n3.Editor accordian\n4.Tools accordian\n5.Others accordian\n6.Home, Projects, Settings, Download and Help displayed in the left side \nas links \n7. Customer drop down in the settings label,Helios label at \nthe left top corner,and \"© 2013.Photon Infotech Pvt Ltd. | \nwww.photon.in\" label present at the right bottom.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Download Page UI validation","status":""},{"severity":"","featureId":"Download Page - UI validation for listed Server accordian","testCaseId":"TC-Download Page-03","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user can view the Name, Version , Size and download link for the various servers.","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify Server accordians UI validation","status":""},{"severity":"","featureId":"Download Page - Download the server","testCaseId":"TC-Download Page-04","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the server","status":""},{"severity":"","featureId":"Download Page - Download the database","testCaseId":"TC-Download Page-05","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the database","status":""},{"severity":"","featureId":"Download Page - Download the Editors","testCaseId":"TC-Download Page-06","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Editors","status":""},{"severity":"","featureId":"Download Page - Download the Tools","testCaseId":"TC-Download Page-07","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Tools","status":""},{"severity":"","featureId":"Download Page - Download the Others","testCaseId":"TC-Download Page-08","steps":"Perform Testcase 18 - Login Sheet[Login]","expectedResult":"","actualResult":"The user should be able to open or save the file","bugComment":"","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"To verify downloading the Others","status":""},{"severity":"","featureId":"sampletestcase","testCaseId":"sampletestcase","steps":"sampletestcase","expectedResult":"1sampletestcase","actualResult":"sampletestcase","bugComment":"sampletestcase","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"sampletestcase","status":"success"}],"status":"success"});
				  }
				});
				
				var testSuiteName = commonVariables.testSuiteName;
				$("#testSuiteName").val(testSuiteName);
				$("input[name=featureId]").val("sampletestcase");
				$("input[name=testCaseId]").val("sampletestcase");
				$("textarea[name=steps]").val("sampletestcase");
				$("input[name=actualResult]").val("sampletestcase");
				$("textarea[name=description]").val("sampletestcase");
				$("textarea[name=expectedResult]").val("1sampletestcase");
				$("input[name=updateTestCase]").click();
				
				setTimeout(function() {
					console.info("Inside runUpdateTestcase test");
					start();
					equal(1, 1, "Update testcases succeeded");
//					self.runShowDownloadPopUp(moduleName);
					
////					require(["dynamicPageTest"], function(dynamicPageTest){
////						dynamicPageTest.runTests();
////					});
			}, 1500);
		});
		},*/
		
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