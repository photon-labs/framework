
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
			
			configuration.loadPage(false);
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".envlistname").text(), "Production", "Configuration popup service Tested");
			}, 1500);
		});
		
		asyncTest("Test - Add environment Test", function() {
		
			mockConfigurationList = mockFunction();
			when(mockConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"","properties":{"r2":"2value","ddd":"ddd","ww":"weee"},"type":"Other","desc":""},{"envName":"Production","name":"bb","properties":{"context":"1","admin_username":"1","deploy_dir":"1","additional_context":"","port":"1","admin_password":"1","certificate":"1","type":"1","remoteDeployment":"1","host":"1","protocol":"http"},"type":"Server","desc":""}]}]};
				var templateData = {};
				templateData.configurationList = configurationListresponse.data;
				configuration.renderTemplate(templateData, commonVariables.contentPlaceholder)
				
			});
			
			configuration.configurationlistener.configurationAPI.configuration = mockConfigurationList;
			
			configuration.loadPage(false);
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("ul[name=envList]");
				$(commonVariables.contentPlaceholder).find("input[name=addEnv]").click(configuration.configurationlistener.addEnvEvent("test", "test", toAppend));
				equal(toAppend.find('li[name=test]').attr('name'), "test", "Add  Environment Tested");
			}, 1500);
		});
		
		asyncTest("Test - Save Environment Test", function() {
		
			mockConfigurationList = mockFunction();
			when(mockConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"","properties":{"r2":"2value","ddd":"ddd","ww":"weee"},"type":"Other","desc":""},{"envName":"Production","name":"bb","properties":{"context":"1","admin_username":"1","deploy_dir":"1","additional_context":"","port":"1","admin_password":"1","certificate":"1","type":"1","remoteDeployment":"1","host":"1","protocol":"http"},"type":"Server","desc":""}]},{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"test","desc":"test","configurations":[]}]};
				var templateData = {};
				templateData.configurationList = configurationListresponse.data;
				configuration.renderTemplate(templateData, commonVariables.contentPlaceholder)
				
			});
			
			configuration.configurationlistener.configurationAPI.configuration = mockConfigurationList;
			
			configuration.loadPage(false);
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("ul[name=envList]");
				$(commonVariables.contentPlaceholder).find("input[name=saveEnvironment]").click(configuration.configurationlistener.saveEnvEvent("", toAppend, function(response){}));
				equal($(commonVariables.contentPlaceholder).find('ul[name=envList] li[name=test]').attr('name'), "test", "Save Environment Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Delete Environment Test", function() {
		
			mockConfigurationList = mockFunction();
			when(mockConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"","properties":{"r2":"2value","ddd":"ddd","ww":"weee"},"type":"Other","desc":""},{"envName":"Production","name":"bb","properties":{"context":"1","admin_username":"1","deploy_dir":"1","additional_context":"","port":"1","admin_password":"1","certificate":"1","type":"1","remoteDeployment":"1","host":"1","protocol":"http"},"type":"Server","desc":""}]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]}]};
				var templateData = {};
				templateData.configurationList = configurationListresponse.data;
				configuration.renderTemplate(templateData, commonVariables.contentPlaceholder)
				
			});
			
			configuration.configurationlistener.configurationAPI.configuration = mockConfigurationList;
			
			configuration.loadPage(false);
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("input[name='deleteEnv']");
				$(commonVariables.contentPlaceholder).find("input[name='deleteEnv']").click(configuration.deleteEnv(toAppend));
				equal($(commonVariables.contentPlaceholder).find('tbody[name=EnvLists] tr').html(), null,  "Delete Environment Tested");
			}, 1500);
		});
		
		asyncTest("Test - Clone Environment Test", function() {
		
			mockConfigurationList = mockFunction();
			when(mockConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"","properties":{"r2":"2value","ddd":"ddd","ww":"weee"},"type":"Other","desc":""},{"envName":"Production","name":"bb","properties":{"context":"1","admin_username":"1","deploy_dir":"1","additional_context":"","port":"1","admin_password":"1","certificate":"1","type":"1","remoteDeployment":"1","host":"1","protocol":"http"},"type":"Server","desc":""}]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]}]};
				var templateData = {};
				templateData.configurationList = configurationListresponse.data;
				configuration.renderTemplate(templateData, commonVariables.contentPlaceholder)
				
			});
			
			configuration.configurationlistener.configurationAPI.configuration = mockConfigurationList;
			
			configuration.loadPage(false);
			
			setTimeout(function() {
				start();
				$(commonVariables.contentPlaceholder).find("input[name='cloneEnvr']").click(configuration.configurationlistener.constructJson("New", "New", false, function(response){
					equal("New", response.name,  "Clone Environment Tested");
				}));
			}, 1500);
		});
		
	}};
});