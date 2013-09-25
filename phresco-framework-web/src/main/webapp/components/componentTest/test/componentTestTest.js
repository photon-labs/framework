define(["componentTest/componentTest"], function(ComponentTest) {
	return { 
		runTests : function (configData) {
			module("ComponentTest.js");
			var componentTest = new ComponentTest(), self = this;
			$(commonVariables.contentPlaceholder).html("");
			asyncTest("Component Test Template Render Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=component",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":"Test Suites listed successfully","exception":null,"data":[{"name":"SampleComponentTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				$('.hProjectId').val("7406c2c3-4de1-4d19-a709-961764a65485");
				commonVariables.api.localVal.setProjectInfo({"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"cccccc","appDirName":"test","techInfo":{"version":"7.x","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"Black Berry","techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"status":null,"description":null,"creationDate":1378204910000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"cccccc","id":"5bc15f33-faba-49fc-95fa-bcc9b3d0e93a","displayName":null,"status":null,"description":null,"creationDate":1378204910000,"helpText":null,"system":false}],"projectCode":"ccccccc","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"ccccccc","id":"7406c2c3-4de1-4d19-a709-961764a65485","displayName":null,"status":null,"description":"","creationDate":1378204910000,"helpText":null,"system":false}},"status":"success"});
				commonVariables.navListener.onMytabEvent("componentTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Component Test", "Component test template rendering tested");
					self.testsuitesRenderTest();
				}, 4000);
			});
		},

		testsuitesRenderTest : function() {
			var self = this;
			asyncTest("Component Test Testsuites Render Test", function() {
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleComponentTest", "Component test testsuites render tested");
					self.graphicalViewTest();
				}, 1500);
			});
		},

		graphicalViewTest : function() {
			var self = this;
			asyncTest("Component Test Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testSuites").css("display"), "block", "Component test graphical view tested");
					self.tabularViewTest();
				}, 1500);
			});
		},

		tabularViewTest : function() {
			var self = this;
			asyncTest("Component Test Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Component test tabular view tested");
					self.showConsole();
				}, 1500);
			});
		},

		showConsole : function() {
			var self = this;
			asyncTest("Component Test Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					equal("", "", "Component test open console tested");
					self.testcasesRenderTest();
				}, 3000);
			});
		},

		testcasesRenderTest : function() {
			var self = this;
			asyncTest("Component Test Testcases Render Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testreports?appDirName=test&testType=component&testSuite=SampleComponentTest",
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
					equal($('#testcases').length, 1, "Component test testcases render tested");
					self.runComponentTestWithNoParamTest();
				}, 4000);
			});
		},

		runComponentTestWithNoParamTest : function() {
			var self = this;
			asyncTest("Component Test Run Test Without Parameters Test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=component&appId=6d6753e8-b081-48d8-9924-70a14f3663d4",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=component-test&phase=component-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[],"status":"success"});
				  	}
				});

				self.executeTestMock = $.mockjax({
					url: commonVariables.webserviceurl+"app/runComponentTest?appDirName=test&username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=component-test&phase=component-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&&displayName=Admin",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"responsive-web","appDirName":"test","techInfo":{"version":"3.10.3","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1354009498000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"1e22614f-0490-4600-9f2d-dba1d200e700","displayName":null,"status":null,"description":null,"creationDate":1365682608000,"helpText":null,"system":false}],"selectedDatabases":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac","c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"customerIds":["photon"],"used":false,"name":"responsive-web","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":null,"creationDate":1379332579000,"helpText":null,"system":true}],"projectCode":"UNITTest","noOfApps":2,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"customerIds":["photon"],"used":false,"name":"UNITTest","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1379332635000,"helpText":null,"system":false}},"status":"success"};
				$('.hProjectId').val(projectInfo.data.projectInfo.appInfos[0].id)
				commonVariables.api.localVal.setProjectInfo(projectInfo);
				
				commonVariables.appDirName = "test";
				
				commonVariables.api.localVal.setSession('username', "admin");
				$(".headerAppId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$(commonVariables.contentPlaceholder).find("#componentTestBtn").click();
				
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleComponentTest", "Component test run test without parameters tested");
					self.testBtnClickTest();
				}, 4500);
			});
		},

		testBtnClickTest : function() {
			var self = this;
			asyncTest("Component Test Test-Btn Click Test", function() {
				$.mockjaxClear(self.parametersMock);
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=component-test&phase=component-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":"showSettings,environmentName"}]},"multiple":"false","value":"java","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"2.3.0.8001-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#componentTestBtn").click();
				setTimeout(function() {
					start();
					equal($('#testAgainst').length, 1, "Component test test-btn click tested");
					self.runComponentTestBtnClickTest();
				}, 4000);
			});
		},

		runComponentTestBtnClickTest : function() {
			var self = this;
			asyncTest("Component Test Run Test-Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/runComponentTest?appDirName=test&username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=component-test&phase=component-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&testAgainst=java&environmentName=Production&displayName=Admin",
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
				$(commonVariables.contentPlaceholder).find("#runComponentTest").click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleComponentTest", "Component test run test-btn click tested");
					require(["functionalTestTest"], function(functionalTestTest){
						functionalTestTest.runTests();
					});
				}, 4000);
			});
		}
	};
});