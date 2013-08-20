define(["functionalTest/functionalTest"], function(FunctionalTest) {
	return { 
		runTests : function (configData) {
			module("FunctionalTest.js");
			var functionalTest = new FunctionalTest(), self = this;
			$(commonVariables.contentPlaceholder).empty();
			asyncTest("Functional Test Template Render Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/functionalFramework?appDirName=test",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ300001","data":{"functionalFramework":"UIAutomation"},"status":"success"});
				  	}
				});

				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=functional",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ000001","data":[{"name":"TestSuite","file":null,"total":5.0,"time":"186.351","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"status":"success"});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.api.localVal.setSession("appDirName", "test");
				commonVariables.navListener.onMytabEvent("functionalTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Functional Test", "Functional test template rendering tested");
					self.testsuitesRenderTest();
				}, 1500);
			});
		},

		testsuitesRenderTest : function() {
			var self = this;
			asyncTest("Functional Test Testsuites Render Test", function() {
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "TestSuite", "Functional test testsuites render tested");
					self.testBtnClickTest();
				}, 1500);
			});
		},

		testBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Test-Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=functional-test&phase=functional-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"usb","value":"-Dandroid.device=\"usb\""},{"key":"emulator","value":"-Dandroid.device=\"emulator\""}]},"name":{"value":[{"value":"Devices","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"devices","possibleValues":{"value":[{"value":"USB","key":"usb","dependency":null},{"value":"Emulator","key":"emulator","dependency":null},{"value":"Serial Number","key":"serialNumber","dependency":"serialNumber"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Serial Number","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"serialNumber","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Signing","lang":"en"}]},"type":"boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"signing","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true}],"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#functionalTestBtn").click();
				setTimeout(function() {
					start();
					equal($('#devices').length, 1, "Functional test test-btn click tested");
					self.runFunctionalTestBtnClickTest();
				}, 1500);
			});
		},

		runFunctionalTestBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Run Test-Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/runFunctionalTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&devices=usb&serialNumber=",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ300004","status":"STARTED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"23b53d9f-5519-4318-bc09-15aaa7403b37","service_exception":"","configErrorMsg":null});
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#runFunctionalTest").click();
				setTimeout(function() {
					start();
					equal($('#testConsole').text(), "STARTED", "Functional test run test-btn click tested");
					require(["manualTestTest"], function(manualTestTest){
						manualTestTest.runTests();
					});
				}, 1500);
			});
		}
	};
});