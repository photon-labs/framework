define(["functionalTest/functionalTest"], function(FunctionalTest) {
	
	return {
		runTests: function (configData) {
			module("FunctionalTest.js");
			var functionalTest = new FunctionalTest(), self = this;
			asyncTest("Functional test template render test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.qualityContext+"/functionalFramework?appDirName=test",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Functional test framework fetched Successfully","exception":null,"data":{"functionalFramework":"webdriver"},"response":null});
				  }
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=functional",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Test Suites listed successfully","exception":null,"data":[{"name":"TestSuite","file":null,"total":5.0,"time":"186.351","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null});
				  }
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						

				var functionalTestAPI =  new Clazz.com.components.functionalTest.js.api.FunctionalTestAPI();
				functionalTestAPI.localVal.setSession("appDirName" , "test");
				
				commonVariables.navListener.onMytabEvent("functionalTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Functional Test", "Functional test template rendering verified");
				}, 1000);
			});
		}
	};
});
