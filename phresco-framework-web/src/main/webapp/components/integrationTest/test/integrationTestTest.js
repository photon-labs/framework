define(["integrationTest/integrationTest"], function(IntegrationTest) {
	return { 
		runTests : function (configData) {
			module("IntegrationTest.js");
			var integrationTest = new IntegrationTest(), self = this, unitTestMock, testsuitesMock, parametersMock, executeTestMock;
			
			asyncTest("Integration Test Template Render Test", function() {
				self.integrationTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=TestJquery&testType=integration",
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
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("integrationTest");
				setTimeout(function() {
					start();
					var len = $('td.manual').parents('table').find('tr').length;
					equal(len-1, 1, "Integration test template rendering tested");
					self.graphicalViewTest();
				}, 1000);
			});
		},
		graphicalViewTest : function() {
			var self = this;
			asyncTest("Integration Test Testcase Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testSuites").css("display"), "block", "Integration test testcase graphical view tested");
					 self.tabularViewTest();
				}, 3000);
			});
		},
		tabularViewTest : function() {
			var self = this;
			asyncTest("Integration Test Testcase Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Integration test testcase tabular view tested");
				}, 3000);
			});
		}
	};
});