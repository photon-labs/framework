define(["componentTest/componentTest"], function(ComponentTest) {
	
	return { runTests: function (configData) {
		module("ComponentTest.js;ComponentTest");
		var componentTest = new ComponentTest();
		
		asyncTest("Component test execution permission verification", function() {
			
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"sampleComponentTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#componentTestBtn').is(':disabled'), false, "Component test execution permission verified");
			}, 1500);
		});
		
		asyncTest("Component test result view permission verification", function() {
			
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":false,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"sampleComponentTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#componentTestBtn').is(':disabled'), true, "Component test result view permission verified");
			}, 1500);
		});
		
		asyncTest("Component test testsuites list verification", function() {
			
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"sampleComponentTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div').find('a[name=testDescription]').text(), "sampleComponentTest", "Component test testsuites list verified");
			}, 1500);
		});
		
		asyncTest("Component test testcases list verification", function() {
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"sampleComponentTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);

			setTimeout(function() {
				var response = {"message":"Test Cases listed successfully","exception":null,"data":[{"name":"testString","file":"C:\\Documents and Settings\\test\\workspace\\projects\\php\\test\\unit\\src\\test\\php\\phresco\\tests\\Home.php","time":"0.002949","assertions":1.0,"testClass":"Home","line":30.0,"testCaseFailure":null,"testCaseError":null}],"response":null};
				$(commonVariables.contentPlaceholder).find('a[name=testDescription]').click(componentTest.testResult.testResultListener.constructTestReport(response.data));
				
				start();
				equal($(commonVariables.contentPlaceholder).find('#testCases table tbody tr:first td:first').text(), "testString", "Component test testcases list verified");
			}, 1500);
		});
		
		asyncTest("Component test no testsuites warning msg verification", function() {
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#testSuites').find('.alert-block').html(), "Test Result not available", "Component test no testsuites warning msg verified");
			}, 1500);
		});
		
		asyncTest("Component test pdf report list verification", function() {
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);
			
			setTimeout(function() {
				var response =  [{"time":"May 23 2013 10.05","type":"detail","fileName":"component_detail_May 23 2013 10.05.pdf"}];
				$(commonVariables.contentPlaceholder).find('#pdfIcon').click(componentTest.testResult.testResultListener.listPdfReports(response));
				start();
				equal($(commonVariables.contentPlaceholder).find('#availablePdfRptsTbdy').find('tr:first td:first').html(), "May 23 2013 10.05", "Component test pdf report list verified");
			}, 1500);
		});
		
		/* asyncTest("Component test dynamic param verification", function() {
			mockPreRender = mockFunction();
			when(mockPreRender)(anything()).then(function(arg) {
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
					
				var data = {};
				data.userPermissions = userPermissions;
				componentTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			componentTest.preRender = mockPreRender;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message;
				componentTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			componentTest.loadPage(false);
			
			setTimeout(function() {
				var response =  '<tr id="testAgainstControl" name="chkCnt"><td>Test Against<sup>*</sup></td></tr><tr id="showSettingsControl" name="chkCnt"><td>Show Settings</td><td><input type="checkbox" name="showSettings" id="showSettings" additionalparam="dependency=environmentName" dependency="environmentName" showFlag="true" value="false"></td></tr><tr id="environmentNameControl" name="chkCnt"><td>Environment<sup>*</sup></td><td><select dynamicParam="true" name="environmentName" id="environmentName" psblDependency="environmentName,showSettings" dependency="" enableOnchangeFunction="false"><option value="Production">Production</option></select></td></tr>;'
				$(commonVariables.contentPlaceholder).find("#dynamicContent").html(response);
				
				start();
				equal($(commonVariables.contentPlaceholder).find('#dynamicContent').find('#environmentName').val(), "Production", "Component test dynamic param verified");
			}, 1500);
		}); */
	}};
});