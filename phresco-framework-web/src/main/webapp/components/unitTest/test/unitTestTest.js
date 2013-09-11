define(["unitTest/unitTest"], function(UnitTest) {
	return { 
		runTests : function (configData) {
			module("UnitTest.js");
			var unitTest = new UnitTest(), self = this, unitTestMock, testsuitesMock, parametersMock, executeTestMock;
			
			asyncTest("Unit Test Template Render Test", function() {
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ100001","data":{"reportOptions":["js","java"],"projectModules":["phresco-framework","phresco-framework-impl", "phresco-framework-web"]},"status":"success"});
				  	}
				});

				self.testsuitesMock = $.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=unit&techReport=js&moduleName=phresco-framework",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ010001","data":null,"status":"success"});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.api.localVal.setSession("appDirName", "test");
				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Unit Test", "Unit test template rendering tested");
					self.techOptionsTest();
				}, 3000);
			});
		},
		
		techOptionsTest : function() {
			var self = this;
			asyncTest("Unit Test Technology Options Render Test", function() {
				setTimeout(function() {
					start();
					equal($('#reportOptionsDrop').text(), "js", "Unit test tech options rendering tested");
					self.moduleOptionsTest();
				}, 1500);
			});
		},

		moduleOptionsTest : function() {
			var self = this;
			asyncTest("Unit Test Module Options Render Test", function() {
				setTimeout(function() {
					start();
					equal($('#modulesDrop').text(), "phresco-framework", "Unit test module options rendering tested");
					self.noTestsuitesTest();
				}, 1500);
			});
		},

		noTestsuitesTest : function() {
			var self = this;
			asyncTest("Unit Test No Testsuites Test", function() {
				setTimeout(function() {
					start();
					equal($('#messagedisp').length, 1, "Unit test no testsuites tested");
					self.testsuitesRenderTest();
				}, 1500);
			});
		},

		testsuitesRenderTest : function() {
			var self = this;
			asyncTest("Unit Test Testsuites Render Test", function() {
				$(commonVariables.contentPlaceholder).empty();
				$.mockjaxClear(self.unitTestMock);
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ100001","data":{"reportOptions":["js","java"],"projectModules":["phresco-framework","phresco-framework-impl", "phresco-framework-web"]},"status":"success"});
				  	}
				});

				$.mockjaxClear(self.testsuitesMock);
				self.testsuitesMock = $.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=unit&techReport=js&moduleName=phresco-framework",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":"Test Suites listed successfully","exception":null,"data":[{"name":"SampleUnitTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.api.localVal.setSession("appDirName", "test");
				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test testsuites render tested");
					self.graphicalViewTest();
				}, 3000);
			});
		},

		graphicalViewTest : function() {
			var self = this;
			asyncTest("Unit Test Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testSuites").css("display"), "block", "Unit test graphical view tested");
					self.tabularViewTest();
				}, 3000);
			});
		},

		tabularViewTest : function() {
			var self = this;
			asyncTest("Unit Test Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Unit test tabular view tested");
					self.techOptionChangeTest();
				}, 3000);
			});
		},

		techOptionChangeTest : function() {
			var self = this;
			asyncTest("Unit Test Tech Options Change Test", function() {
				$('#testResult').html("");
				$(commonVariables.contentPlaceholder).find('.reportOption:first').click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test tech options change tested");
					self.moduleOptionChangeTest();
				}, 1500);
			});
		},

		moduleOptionChangeTest : function() {
			var self = this;
			asyncTest("Unit Test Module Options Change Test", function() {
				$('#testResult').html("");
				$(commonVariables.contentPlaceholder).find('.projectModule:first').click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test module options change tested");
					self.runUnitTestWithNoParamTest();
				}, 1500);
			});
		},

		runUnitTestWithNoParamTest : function() {
			var self = this;
			asyncTest("Unit Test Run Test Without Parameters Test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=unit&appId=6d6753e8-b081-48d8-9924-70a14f3663d4",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[],"status":"success"});
				  	}
				});

				self.executeTestMock = $.mockjax({
					url: commonVariables.webserviceurl+"app/runUnitTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=unit-test&phase=unit-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&&displayName=Admin",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				commonVariables.appDirName = "test";
				$(".headerAppId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test run test without parameters tested");
					self.testBtnClickTest();
				}, 4000);
			});
		},

		testBtnClickTest : function() {
			var self = this;
			asyncTest("Unit Test Test-Btn Click Test", function() {
				$.mockjaxClear(self.parametersMock);
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":"showSettings,environmentName"}]},"multiple":"false","value":"java","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"2.3.0.8001-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				setTimeout(function() {
					start();
					equal($('#testAgainst').length, 1, "Unit test test-btn click tested");
					self.runUnitTestBtnClickTest();
				}, 4000);
			});
		},

		runUnitTestBtnClickTest : function() {
			var self = this;
			asyncTest("Unit Test Run Test-Btn Click Test", function() {
				$.mockjaxClear(self.executeTestMock);
				self.executeTestMock = $.mockjax({
					url: commonVariables.webserviceurl+"app/runUnitTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=unit-test&phase=unit-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&testAgainst=java&environmentName=Production&displayName=Admin",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#runUnitTest").click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test run test-btn click tested");
					self.testcasesRenderTest();
				}, 4000);
			});
		},

		testcasesRenderTest : function() {
			var self = this;
			asyncTest("Unit Test Testcases Render Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testreports?appDirName=test&testType=unit&testSuite=SampleUnitTest&techReport=js&moduleName=phresco-framework",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ000002","data":[{"name":"hello","file":null,"time":"0.015","assertions":0.0,"testClass":"com.photon.phresco.service.TestCase","line":0.0,"testCaseFailure":null,"testCaseError":null}],"status":"success"});
				  	}
				});

				$(commonVariables.contentPlaceholder).find("a[name=testDescription]:first").click();
				setTimeout(function() {
					start();
					equal($('#testcases').length, 1, "Unit test testcases render tested");
					self.noParametersTest();
				}, 4000);
			});
		},

		noParametersTest : function() {
			var self = this;
			asyncTest("Unit Test No Parameters Test", function() {
				$.mockjaxClear(self.parametersMock);
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":null,"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				setTimeout(function() {
					start();
					equal($('.dyn_popup').css("display"), "none", "Unit test no parameters tested");
					self.showConsole();
				}, 2000);
			});
		},

		showConsole : function() {
			var self = this;
			asyncTest("Unit Test Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					equal("", "", "Unit test open console tested");
					self.optionsFailureTest();
				}, 3000);
			});
		},

		optionsFailureTest : function() {
			var self = this;
			asyncTest("Unit Test Failed To Get The Report Options Test", function() {
				$(commonVariables.contentPlaceholder).empty();
				$.mockjaxClear(self.unitTestMock);
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ110001","data":"","status":"error"});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.api.localVal.setSession("appDirName", "test");
				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.msgdisplay').text(), "Unable to get unit test report options", "Unit test failed to get the report options tested");
					require(["componentTestTest"], function(componentTestTest){
						componentTestTest.runTests();
					});
				}, 4000);
			});
		}
	};
});