define(["manualTest/manualTest"], function(ManualTest) {
	
	return {
		// list test suites 
		
		runTests: function (configData) {
			var self = this;
			var moduleName = "ManualTest.js";
			module(moduleName);
			asyncTest("Manual test loading template", function() {
				$.mockjax({
//					http://localhost:1234/framework/rest/api/manual/testsuites?appDirName=11-html5multichanneljquerywidget&_=1375086451181
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=11-html5multichanneljquerywidget',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuites returned Successfully","exception":null,"responseCode":null,"data":{"testSuites":[{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":16.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":16.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":11.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Welcome Page","file":null,"time":null,"total":11.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":55.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Home Page","file":null,"time":null,"total":55.0},{"errors":14.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":7.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Projects Page","file":null,"time":null,"total":15.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":53.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import application","file":null,"time":null,"total":53.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":289.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"iPhone Native","file":null,"time":null,"total":289.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":26.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Settings Page","file":null,"time":null,"total":26.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":43.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Help Page","file":null,"time":null,"total":43.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"xzsddada","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdsd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"xzxsz","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dffdf","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"aaaaaaa","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"frfgrfg","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":2.0,"testSteps":null,"name":"b","file":null,"time":null,"total":2.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":2.0,"testSteps":null,"name":"qqqqqqqqq","file":null,"time":null,"total":2.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"qqqqqqqqqqq","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"q1111111","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"aaaa","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":521.0,"totalTestCoverage":407.0,"total":528.0},"status":null});
				  }
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				commonVariables.api.localVal.setSession("appDirName" , "11-html5multichanneljquerywidget");
				commonVariables.navListener.onMytabEvent("manualTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Manual Test", "Manual test template rendering verified");
					self.runAddManualTestsuite(moduleName);
				}, 100);
			});
		},
		
		// add testsuite
		runAddManualTestsuite: function (moduleName) {
			module(moduleName);
			var self = this;
			asyncTest("Adding Testsuite in manual test", function() {
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=addnewTestsuite&appDirName=11-html5multichanneljquerywidget',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuite Added Successfully","exception":null,"responseCode":null,"data":null,"status":null});
				  }
				});
				
				$.mockjaxClear();
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + 'testsuites?appDirName=11-html5multichanneljquerywidget',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testsuites returned Successfully","exception":null,"responseCode":null,"data":{"testSuites":[{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"HeliOS-Installation","file":null,"time":null,"total":14.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":16.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Login","file":null,"time":null,"total":16.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":11.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Welcome Page","file":null,"time":null,"total":11.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":55.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Home Page","file":null,"time":null,"total":55.0},{"errors":14.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":14.0,"testCoverage":7.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"Projects Page","file":null,"time":null,"total":15.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":53.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Import application","file":null,"time":null,"total":53.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":289.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"iPhone Native","file":null,"time":null,"total":289.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":26.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Settings Page","file":null,"time":null,"total":26.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":43.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"Help Page","file":null,"time":null,"total":43.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"xzsddada","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"sdsd","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"xzxsz","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"dffdf","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":1.0,"testSteps":null,"name":"aaaaaaa","file":null,"time":null,"total":1.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"frfgrfg","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":2.0,"testSteps":null,"name":"b","file":null,"time":null,"total":2.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":100.0,"testCases":null,"tests":2.0,"testSteps":null,"name":"qqqqqqqqq","file":null,"time":null,"total":2.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"qqqqqqqqqqq","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"q1111111","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"aaaa","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"addnewTestsuite","file":null,"time":null,"total":0.0},{"errors":0.0,"assertions":null,"success":0.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null,"name":"undefined","file":null,"time":null,"total":0.0}],"totalSuccess":0.0,"totalFailure":0.0,"totalNotApplicable":0.0,"totalBlocked":0.0,"totalNotExecuted":521.0,"totalTestCoverage":307.0,"total":527.0},"status":null});
				  }
				});
				
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Manual Test", "Testsuite added and returned successfully");
					self.runAddManualTestcase();
				}, 100);
			});
		},
		
		// add testcase
		runAddManualTestcase: function (moduleName) {
			module(moduleName);
			asyncTest("Adding testcases to a testsuite", function() {
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=aaaaaaa&appDirName=11-html5multichanneljquerywidget',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testcases Added Successfully","exception":null,"responseCode":null,"data":null,"status":null});
				  }
				});
				
				$.mockjax({
				  url : commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=aaaaaaa&appDirName=11-html5multichanneljquerywidget',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Testcases returned Successfully","exception":null,"responseCode":null,"data":[{"severity":"","featureId":"dsdsdsdasasasAS","testCaseId":"SasaS","steps":"sSas","expectedResult":"ASsASas","actualResult":"ASsSs","bugComment":"SssS","requirementId":"","testCaseype":"","testInput":"","priority":"","description":"asAS","status":"success"}],"status":null});
				  }
				});
				
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Manual Test", "Adding Manual testcases succeeded");
				}, 100);
			});
		},
		
	};
});