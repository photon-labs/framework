define(["functionalTest/functionalTest"], function(FunctionalTest) {
	return { 
		runTests : function (configData) {
			module("FunctionalTest.js");
			var functionalTest = new FunctionalTest(), self = this, connectionAliveMock;
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
				}, 4000);
			});
		},

		testsuitesRenderTest : function() {
			var self = this;
			asyncTest("Functional Test Testsuites Render Test", function() {
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "TestSuite", "Functional test testsuites render tested");
					self.graphicalViewTest();
				}, 1500);
			});
		},

		graphicalViewTest : function() {
			var self = this;
			asyncTest("Functional Test Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testSuites").css("display"), "block", "Functional test graphical view tested");
					self.tabularViewTest();
				}, 1500);
			});
		},

		tabularViewTest : function() {
			var self = this;
			asyncTest("Functional Test Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Functional test tabular view tested");
					self.showConsole();
				}, 1500);
			});
		},

		showConsole : function() {
			var self = this;
			asyncTest("Functional Test Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					equal("", "", "Functional test open console tested");
					self.startHubBtnClickTest();
				}, 3000);
			});
		},

		startHubBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Start Hub Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=start-hub&phase=start-hub&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Hub Port","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"port","possibleValues":null,"multiple":"false","value":"4444","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"New Session Wait Timeout","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"newSessionWaitTimeout","possibleValues":null,"multiple":"false","value":"-1","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Servlets","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"servlets","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Prioritizer","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"prioritizer","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Capability Matcher","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"capabilityMatcher","possibleValues":null,"multiple":"false","value":"org.openqa.grid.internal.utils.DefaultCapabilityMatcher","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Throw On Capability Not Present","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"throwOnCapabilityNotPresent","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Node Polling","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"nodePolling","possibleValues":null,"multiple":"false","value":"5000","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"CleanUp Cycle","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"cleanUpCycle","possibleValues":null,"multiple":"false","value":"5000","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Timeout","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"timeout","possibleValues":null,"multiple":"false","value":"300000","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Browser Timeout","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"browserTimeout","possibleValues":null,"multiple":"false","value":"0","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Max Session","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"maxSession","possibleValues":null,"multiple":"false","value":"5","sort":false,"show":true}],"status":"success"});
				  	}
				});

				commonVariables.appDirName = "test";
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#startHub").click();
				setTimeout(function() {
					start();
					equal($('#port').length, 1, "Functional test start hub btn click tested");
					self.startHubTest();
				}, 4000);
			});
		},

		startHubTest : function() {
			var self = this;
			asyncTest("Functional Test Run Start Hub Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/startHub?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&port=4444&newSessionWaitTimeout=-1&servlets=&prioritizer=&capabilityMatcher=org.openqa.grid.internal.utils.DefaultCapabilityMatcher&throwOnCapabilityNotPresent=on&nodePolling=5000&cleanUpCycle=5000&timeout=300000&browserTimeout=0&maxSession=5",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ300002","log":"SocketConnector@0.0.0.0:","status":"COMPLETED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"3754f2af-f61c-4a93-8fa3-79b0d56c6d1b","service_exception":"","configErrorMsg":null});
				  	}
				});

				self.connectionAliveMock = $.mockjax({
					url: commonVariables.webserviceurl+"quality/connectionAliveCheck?appDirName=test&fromPage=hubStatus",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify(true);
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#executeStartHub").click();
				setTimeout(function() {
					start();
					equal($('#stopHub').length, 1, "Functional test run start hub btn click tested");
					self.stopHubTest();
				}, 4000);
			});
		},

		stopHubTest : function() {
			var self = this;
			asyncTest("Functional Test Stop Hub Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/stopHub?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":null,"log":null,"status":"COMPLETED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"ea6cef8b-be21-4db6-9bb9-382f12678a9c","service_exception":null,"configErrorMsg":null});
				  	}
				});
				$.mockjaxClear(self.connectionAliveMock);
				self.connectionAliveMock = $.mockjax({
					url: commonVariables.webserviceurl+"quality/connectionAliveCheck?appDirName=test&fromPage=hubStatus",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify(false);
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#stopHub").click();
				setTimeout(function() {
					start();
					equal($('#startHub').length, 1, "Functional test stop hub btn click tested");
					self.startNodeBtnClickTest();
				}, 4000);
			});
		},

		startNodeBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Start Node Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=start-node&phase=start-node&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Hub Host","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"hubHost","possibleValues":null,"multiple":"false","value":"localhost","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Hub Port","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"hubPort","possibleValues":null,"multiple":"false","value":"4444","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Node Port","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"nodeport","possibleValues":null,"multiple":"false","value":"5555","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Max Session","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"maxSession","possibleValues":null,"multiple":"false","value":"5","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Selenium Protocol","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"seleniumProtocol","possibleValues":null,"multiple":"false","value":"WebDriver","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Register","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"register","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Register Cycle","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"registerCycle","possibleValues":null,"multiple":"false","value":"5000","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Proxy","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"proxy","possibleValues":null,"multiple":"false","value":"org.openqa.grid.selenium.proxy.DefaultRemoteProxy","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Browser Info","lang":"en"}]},"type":"map","childs":{"child":[{"name":{"value":{"value":"Browser Name","lang":"en"}},"type":"List","key":"browserName","possibleValues":{"value":[{"value":"Firefox","key":"firefox"},{"value":"Google Chrome","key":"chrome"},{"value":"Internet Explorer","key":"internet explorer"},{"value":"Safari","key":"safari"},{"value":"Opera","key":"opera"},{"value":"Non GUI(Html Unit)","key":"htmlunit"},{"value":"Iphone WebDriver","key":"iPhone"}]},"dynamicParameter":null,"required":"true","description":"Name of the browsers"},{"name":{"value":{"value":"Max Instances","lang":"en"}},"type":"String","key":"browserMaxInstances","possibleValues":null,"dynamicParameter":null,"required":"true","description":"no of instances of the browser"}]},"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"browserInfo","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
				  	}
				});

				commonVariables.appDirName = "test";
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#startNode").click();
				setTimeout(function() {
					start();
					equal($('#hubHost').length, 1, "Functional test start node btn click tested");
					self.startNodeTest();
				}, 4000);
			});
		},

		startNodeTest : function() {
			var self = this;
			asyncTest("Functional Test Run Start Node Btn Click Test", function() {
				$("input[name=browserMaxInstances]").val("2");
				$.mockjax({
					url: commonVariables.webserviceurl+"app/startNode?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&hubHost=localhost&hubPort=4444&nodeport=5555&maxSession=5&seleniumProtocol=WebDriver&register=true&registerCycle=5000&proxy=org.openqa.grid.selenium.proxy.DefaultRemoteProxy&browserName=firefox&browserMaxInstances=2",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ300002","log":"Done: /status","status":"COMPLETED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"3754f2af-f61c-4a93-8fa3-79b0d56c6d1b","service_exception":"","configErrorMsg":null});
				  	}
				});

				$.mockjaxClear(self.connectionAliveMock);
				self.connectionAliveMock = $.mockjax({
					url: commonVariables.webserviceurl+"quality/connectionAliveCheck?appDirName=test&fromPage=nodeStatus",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify(true);
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#executeStartNode").click();
				setTimeout(function() {
					start();
					equal($('#stopNode').length, 1, "Functional test run start node btn click tested");
					self.stopNodeTest();
				}, 4000);
			});
		},

		stopNodeTest : function() {
			var self = this;
			asyncTest("Functional Test Stop Node Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/stopNode?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":null,"log":null,"status":"COMPLETED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"ea6cef8b-be21-4db6-9bb9-382f12678a9c","service_exception":null,"configErrorMsg":null});
				  	}
				});
				$.mockjaxClear(self.connectionAliveMock);
				self.connectionAliveMock = $.mockjax({
					url: commonVariables.webserviceurl+"quality/connectionAliveCheck?appDirName=test&fromPage=nodeStatus",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify(false);
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#stopNode").click();
				setTimeout(function() {
					start();
					equal($('#startNode').length, 1, "Functional test stop node btn click tested");
					self.testBtnClickTest();
				}, 4000);
			});
		},

		testBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Test-Btn Click Test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=functional&appId=6d6753e8-b081-48d8-9924-70a14f3663d4",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
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
				$(".headerAppId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$(commonVariables.contentPlaceholder).find("#functionalTestBtn").click();
				setTimeout(function() {
					start();
					equal($('#devices').length, 1, "Functional test test-btn click tested");
					self.runFunctionalTestBtnClickTest();
				}, 4000);
			});
		},

		runFunctionalTestBtnClickTest : function() {
			var self = this;
			asyncTest("Functional Test Run Test-Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"app/runFunctionalTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=functional-test&phase=functional-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&devices=usb&serialNumber=&displayName=Admin",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ300004","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"23b53d9f-5519-4318-bc09-15aaa7403b37","service_exception":"","configErrorMsg":null});
				  	}
				});

				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"Component-html5jquerymobilewidget","appDirName":"Component-html5jquerymobilewidget","techInfo":{"version":"1.6","multiModule":false,"appTypeId":"web-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1374049645000,"helpText":null,"system":false},"functionalFramework":"grid","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["jslib_jquery-amd","jslib_jquery-ui-amd","jslib_jsonpath-amd","jslib_xml2json-amd"],"selectedComponents":["75b584d5-ebe0-48b4-beca-caf97469f812"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":"","phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"customerIds":null,"used":false,"name":"Component-html5jquerymobilewidget","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false}],"projectCode":"Component","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"Component","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1374044561000,"helpText":null,"system":false},"status":"success"};
				commonVariables.api.localVal.setJson('appdetails', projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#runFunctionalTest").click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "TestSuite", "Functional test run test-btn click tested");
					require(["manualTestTest"], function(manualTestTest){
						manualTestTest.runTests();
					});
				}, 4000);
			});
		}
	};
});