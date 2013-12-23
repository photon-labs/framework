define(["repository/sourceRepository"], function(SourceRepository) {
	return { 
		runTests : function (configData) {
			module("sourceRepository.js");
			var self = this;
			
			asyncTest("Source Repository Test Template Render Test", function() {
			
				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				commonVariables.api.localVal.setSession('projectId', "b95a4b23-c7a9-47db-b5a6-f35c6fdb8568");
				$.mockjax({
					  url: commonVariables.webserviceurl +"repository/browseGitRepo?userId=admin&customerId=photon&projectId=b95a4b23-c7a9-47db-b5a6-f35c6fdb8568",
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":null,"data":["<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><item name=\"rootItem\" type=\"folder\"><item name=\"https://github.com/phresco/commons.git\" type=\"folder\"><item name=\"Branches\" type=\"folder\"><item name=\"2.0.0\" type=\"file\"/><item name=\"2.0.0.34002-beta\" type=\"file\"/><item name=\"2.1.0\" type=\"file\"/><item name=\"2.2.0\" type=\"file\"/><item name=\"2.3.0\" type=\"file\"/><item name=\"2.3.1\" type=\"file\"/><item name=\"3.0.0\" type=\"file\"/><item name=\"3.1.0\" type=\"file\"/><item name=\"3.2.0\" type=\"file\"/><item name=\"HEAD\" type=\"file\"/><item name=\"beta\" type=\"file\"/><item name=\"master\" type=\"file\"/></item><item name=\"Tags\" type=\"folder\"><item name=\"2.0.0.32000\" type=\"file\"/><item name=\"2.0.0.37001\" type=\"file\"/><item name=\"2.2.0.4002\" type=\"file\"/><item name=\"2.2.1.1000\" type=\"file\"/><item name=\"2.3.0.9000\" type=\"file\"/><item name=\"3.0.0.23004\" type=\"file\"/><item name=\"3.0.0.24000\" type=\"file\"/><item name=\"3.0.0.24003\" type=\"file\"/><item name=\"3.0.0.25000\" type=\"file\"/><item name=\"3.0.0.25002\" type=\"file\"/><item name=\"3.0.0.25003\" type=\"file\"/><item name=\"3.0.0.26000\" type=\"file\"/><item name=\"3.0.0.26002\" type=\"file\"/><item name=\"3.0.0.26003\" type=\"file\"/><item name=\"3.0.0.26004\" type=\"file\"/><item name=\"3.0.0.26005\" type=\"file\"/><item name=\"3.0.0.27000\" type=\"file\"/><item name=\"3.0.0.27001\" type=\"file\"/><item name=\"3.0.0.27002\" type=\"file\"/><item name=\"3.0.0.28000\" type=\"file\"/><item name=\"3.1.0.1001\" type=\"file\"/><item name=\"3.1.0.1002\" type=\"file\"/><item name=\"3.1.0.1003\" type=\"file\"/><item name=\"3.1.0.4000\" type=\"file\"/><item name=\"3.1.0.4001\" type=\"file\"/><item name=\"3.1.0.4002\" type=\"file\"/><item name=\"3.1.0.5000\" type=\"file\"/><item name=\"3.2.0.5002\" type=\"file\"/><item name=\"beta-2.0.0.34002\" type=\"file\"/></item></item></item></root>"],"status":null});
					  }	
				});
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("sourceRepo");
				setTimeout(function() {
					start();
					var len = $('input[name=rep_release]').length;
					equal(len, 1, "Source Repository test template rendering tested");
				}, 3000);
			});
		}		
	};
});