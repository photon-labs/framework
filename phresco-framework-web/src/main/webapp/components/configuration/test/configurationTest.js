
define(["configuration/configuration"], function(Configuration) {

	return { runTests: function (configData) {
		
		module("configuration.js;Configuration");
		
		var configuration = new Configuration();
		asyncTest("Test - Configuration Page render", function() {
		
			mockConfigurationList = mockFunction();
			when(mockConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"","properties":{"r2":"2value","ddd":"ddd","ww":"weee"},"type":"Other","desc":""},{"envName":"Production","name":"bb","properties":{"context":"1","admin_username":"1","deploy_dir":"1","additional_context":"","port":"1","admin_password":"1","certificate":"1","type":"1","remoteDeployment":"1","host":"1","protocol":"http"},"type":"Server","desc":""}]}]};
				var templateData = {};
				templateData.configurationList = configurationListresponse.data;
				configuration.renderTemplate(templateData, commonVariables.contentPlaceholder)
				
			});
			
			configuration.configurationlistener.configurationAPI.configuration = mockConfigurationList;
			
			configuration.loadPage();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("td[name=envNames]").text(), "Production  (Default) ", "Configuration Service Tested");
				equal($(commonVariables.contentPlaceholder).find(".envlistname").text(), "Production", "Configuration popup service Tested");
			}, 1500);
		});
		
	}};
});