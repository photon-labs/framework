define(["integrationTest/integrationTest"], function(IntegrationTest) {
	return { 
		runTests : function (configData) {
			module("IntegrationTest.js");
			var integrationTest = new IntegrationTest(), self = this, unitTestMock, testsuitesMock, parametersMock, executeTestMock;
			
			asyncTest("Integration Test Template Render Test", function() {
				self.integrationTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=TestJquery&testType=integration&projectCode=test-query",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ000001","data":[{"errors":0.0,"success":2.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"assertions":null,"tests":0.0,"testCases":null,"testSteps":null,"name":"TestSuite","file":null,"time":"0","total":2.0}],"status":"success"});
				  	}
				});


				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("integrationTest");
				setTimeout(function() {
					start();
					var len = $('td.manual').parents('table').find('tr').length;
					equal(len, 2, "Integration test template rendering tested");
					self.integrationTestDynamicParam();
				}, 3000);
			});
		},
		integrationTestDynamicParam : function() {
			var self = this;
			asyncTest("Integration Test Dynamic parameter render Test", function() {
				$.mockjax({
				  	url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=null&goal=integration-test&phase=integration-test&customerId=photon&userId=admin&projectCode=test-query",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.1.0.3003-SNAPSHOT"}},"class":"com.photon.phresco.impl.IntegrationTestParameterImpl"},"required":"true","editable":"true","description":null,"key":"environment","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":"success"});
				  	}
				});
				
				$(commonVariables.contentPlaceholder).find('#integrationTestBtn').click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('select#environment').val(), "Production", "Integration Test Dynamic parameter render Tested");
					self.integrationTestConsole();
				}, 3000);
			});
		},
		
		integrationTestConsole : function() {
			var self = this;
			asyncTest("Integration Test console render Test", function() {
				$.mockjax({
				  	url: commonVariables.webserviceurl+"app/runIntegrationTest?appDirName=&username=admin&customerId=photon&goal=integration-test&phase=integration-test&environment=Production&displayName=Admin&projectCode=test-query",
				 	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});
				
				$(commonVariables.contentPlaceholder).find('#runIntegrationTest').click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('select#environment').val(), "Production", "Integration Test console render Tested");
				}, 3000);
			});
		},
		
	};
});