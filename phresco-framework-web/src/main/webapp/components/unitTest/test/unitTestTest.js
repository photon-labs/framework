define(["unitTest/unitTest"], function(UnitTest) {
	
	return { runTests: function (configData) {
		module("UnitTest.js;UnitTest");
		var unitTest = new UnitTest();
		
		asyncTest("Unit test technology options dropdown verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":["js","java"],"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#reportOptionsDrop').text(), "js", "Unit test technology options dropdown verified");
			}, 1500);
		});
		
		asyncTest("Unit test modules options dropdown verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":["phresco-framework","phresco-framework-impl", "phresco-framework-web"]},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#modulesDrop').text(), "phresco-framework", "Unit test modules options dropdown verified");
			}, 1500);
		});
		
		asyncTest("Unit test execution permission verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#unitTestBtn').is(':disabled'), false, "Unit test execution permission verified");
			}, 1500);
		});
		
		asyncTest("Unit test result view permission verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#unitTestBtn').is(':disabled'), false, "Unit test result view permission verified");
			}, 1500);
		});
		
		asyncTest("Unit test testsuites list verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div').find('a[name=testDescription]').text(), "Phpunittest", "Unit test testsuites list verified");
			}, 1500);
		});
		
		asyncTest("Unit test testcases list verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Suites listed successfully","exception":null,"data":[{"name":"Phpunittest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null};
				var data = {};
				data.testSuites = response.data;
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);

			setTimeout(function() {
				var response = {"message":"Test Cases listed successfully","exception":null,"data":[{"name":"testString","file":"C:\\Documents and Settings\\test\\workspace\\projects\\php\\test\\unit\\src\\test\\php\\phresco\\tests\\Home.php","time":"0.002949","assertions":1.0,"testClass":"Home","line":30.0,"testCaseFailure":null,"testCaseError":null}],"response":null};
				$(commonVariables.contentPlaceholder).find('a[name=testDescription]').click(unitTest.testResult.testResultListener.constructTestReport(response.data))
				
				start();
				equal($(commonVariables.contentPlaceholder).find('#testCases table tbody tr:first td:first').text(), "testString", "Unit test testcases list verified");
			}, 1500);
		});
		
		asyncTest("Unit test no testsuites warning msg verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('#testSuites').find('.alert-block').html(), "Test Result not available", "Unit test no testsuites warning msg verified");
			}, 1500);
		});
		
		asyncTest("Unit test pdf report list verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				var response =  [{"time":"May 23 2013 10.05","type":"detail","fileName":"unit_detail_May 23 2013 10.05.pdf"}];
				$(commonVariables.contentPlaceholder).find('#pdfIcon').click(unitTest.testResult.testResultListener.listPdfReports(response))
				start();
				equal($(commonVariables.contentPlaceholder).find('#availablePdfRptsTbdy').find('tr:first td:first').html(), "May 23 2013 10.05", "Unit test pdf report list verified");
			}, 1500);
		});
		
		/* asyncTest("Unit test dynamic param verification", function() {
			mockGetUnitTestOptions = mockFunction();
			
			when(mockGetUnitTestOptions)(anything()).then(function(arg) {
				var response =  {"message":"Unit test report returned successfully","exception":null,"data":{"reportOptions":null,"projectModules":null},"response":null};
				
				var userPermissions = {"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false};
				
				var data = {};
				data.reportOptions = response.data.reportOptions;
				data.projectModules = response.data.projectModules;
				data.userPermissions = userPermissions;
				
				unitTest.renderTemplate(data, commonVariables.contentPlaceholder);
			});
			
			unitTest.preRender = mockGetUnitTestOptions;
			
			mockGetTestsuites = mockFunction();
			when(mockGetTestsuites)(anything()).then(function(arg) {
				var response =  {"message":"Test Result not available","exception":null,"data":null,"response":null};
				var data = {};
				data.testSuites = response.data;
				data.message = response.message
				unitTest.testResult.renderTemplate(data, $(commonVariables.contentPlaceholder).find('#testResult div.widget-maincontent-div')); 
			});
			
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				retVal.preRender = mockGetTestsuites;
			});
			
			mockGetDynamicParams = mockFunction();
			when(mockGetDynamicParams)(anything()).then(function(arg) {
				var response =  {"response":null,"message":"Parameter returned successfully","exception":null,"data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":"environmentName,showSettings"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.12000"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"test","key":null,"dependency":null},{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}]};
				commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
					
				});
			});
			
			unitTest.loadPage(false);
			
			setTimeout(function() {
				
				$(commonVariables.contentPlaceholder).find("#dynamicContent").html(response);
				
				start();
				equal($(commonVariables.contentPlaceholder).find('#dynamicContent').find('#testAgainst').val(), "java", "Unit test dynamic param verified");
			}, 1500);
		});*/
	}};
});