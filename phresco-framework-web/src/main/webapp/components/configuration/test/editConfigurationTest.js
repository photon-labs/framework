
define(["configuration/editConfiguration"], function(EditConfiguration) {

	return { runTests: function (configData) {
		
		module("EditConfiguration.js;EditConfiguration");
		

		var editConfiguration = new EditConfiguration();

		asyncTest("Test - Edit onfiguration Render Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('input[name=EnvName]').val(), "Production", "Edit Configuration Render Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Add configuration Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			var response = {"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{"Apache Tomcat":["7.0.x"],"Apache Server":["2.2","2.0"],"IIS":["7.5"]},"settingsTemplate":{"appliesToTechs":[{"creationDate":1359036240000,"helpText":null,"system":false,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee html jquery","id":"74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee iphone hybrid","id":"6e222405-9831-4eed-81d9-c9870711d914","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"loreal iphone hybrid","id":"059fb4a0-7224-4a05-a5ec-c4b9bebb2485","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"loreal html jquery","id":"cb3cac8f-012b-4966-aa1b-d06f1fc03b7f","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":true,"possibleValues":["http","https"],"multiple":false,"propertyTemplates":null,"key":"protocol","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Protocol","id":"1ba69a28-c263-4751-85de-a06aef198759","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"host","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Host","id":"8a9f2d1e-9829-4710-b070-54d5277f07f0","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"port","type":"Number","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Port","id":"bed36f46-df33-4cef-b8ac-f59b9867e29a","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"admin_username","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Username","id":"578439f9-dda0-4359-8aec-0debe88b8e79","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"admin_password","type":"Password","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Password","id":"3cb9fed8-6d0d-43ff-8e90-0cbad1ed6410","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"remoteDeployment","type":"Boolean","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Remote Deployment","id":"c0029d24-7cfc-475a-a981-095388b1e9e2","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"certificate","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Certificate","id":"cdc44d80-fa82-4791-91ab-16761ee0b580","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"type","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Server Type","id":"2051ba38-03e1-4bf1-a63c-df956d642afe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"version","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Version","id":"2051ba38-03e1-4bf1-a63c-df956d642wafe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"deploy_dir","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Deploy Directory","id":"7c993072-0e94-4339-8cdc-2c8b48637f5b","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Context","id":"e779c012-4c45-467d-b146-67a0a59e8f32","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"additional_context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Additional Context Path","id":"6b945b56-5410-464a-9176-f4f042e36d31","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","05c80933-95d4-46c8-a58d-ceceb4bcce48","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1359036240000,"helpText":null,"system":true,"name":"Server","id":"config_Server","description":"Server Configuration","status":null}}};
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.constructHtml(response, '', "Server", toAppend));
				equal(toAppend.find("tr[type=Server]").attr('configtype'), "Server", "Add configuration Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Add configuration For Other Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.htmlForOther('', toAppend));
				equal(toAppend.find("tr.otherConfig").attr('name'), "Other", "Add configuration For Other Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Remove configuration Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.htmlForOther('', toAppend));
				toAppend.find("a[name=removeConfig]").click(editConfiguration.configurationlistener.removeConfigFunction(toAppend.find("a[name=removeConfig]")));
				equal(toAppend.find("tr.row_bg").attr('type'), "otherConfig", "Remove configuration Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Click to add For Other Configuration Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.htmlForOther('', toAppend));
				toAppend.find("a[name=addOther]").click(editConfiguration.configurationlistener.addOtherConfig(toAppend.find(".otherConfig:last")));
				equal(toAppend.find("tr.otherConfig").attr('name'), "Other", "Click to add For Other Configuration Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Click to Remove For Other Configuration Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.htmlForOther('', toAppend));
				toAppend.find("a[name=removeOther]").click(editConfiguration.configurationlistener.removeClick(toAppend.find("a[name=removeOther]")));
				equal(toAppend.find("tr.otherConfig").attr('name'), undefined, "Click to Remove For Other Configuration Tested");
			}, 1500);
		}); 
		
		asyncTest("Test - Crone Expression render Test", function() {
		
			mockEditConfigurationList = mockFunction();
			when(mockEditConfigurationList)(anything()).then(function(arg) {
				
				var configurationListresponse = {"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}};
				var templateData = {};
				templateData.configurations = configurationListresponse.data;
				templateData.configType = ["Scheduler","Server","Database"];
				editConfiguration.renderTemplate(templateData, commonVariables.contentPlaceholder);
				
			});
			
			editConfiguration.configurationlistener.configurationAPI.configuration = mockEditConfigurationList;
			
			var response = {"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{},"settingsTemplate":{"appliesToTechs":[{"creationDate":1357659881000,"helpText":null,"system":false,"name":"Android Hybrid","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1357659882000,"helpText":null,"system":false,"name":"Android Library","id":"tech-android-library","displayName":null,"description":null,"status":null},{"creationDate":1357659882000,"helpText":null,"system":false,"name":"Android Native","id":"tech-android-native","displayName":null,"description":null,"status":null},{"creationDate":1357659882000,"helpText":null,"system":false,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1357659883000,"helpText":null,"system":false,"name":"Blackberry Hybrid","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1357659883000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1357659883000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1357659884000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1357659884000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1357659884000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1357659885000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1357659885000,"helpText":null,"system":false,"name":"iPhone Hybrid","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1357659885000,"helpText":null,"system":false,"name":"Iphone Library","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"creationDate":1357659886000,"helpText":null,"system":false,"name":"iPhone Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},{"creationDate":1357659886000,"helpText":null,"system":false,"name":"Iphone Workspace","id":"tech-iphone-workspace","displayName":null,"description":null,"status":null},{"creationDate":1357659886000,"helpText":null,"system":false,"name":"Java Standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"creationDate":1357659887000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1357659887000,"helpText":null,"system":false,"name":"Node JS Web Service","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"creationDate":1357659887000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1357659888000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1357659888000,"helpText":null,"system":false,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null},{"creationDate":1357659888000,"helpText":null,"system":false,"name":"Windows Metro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"creationDate":1357659889000,"helpText":null,"system":false,"name":"Windows Phone","id":"tech-win-phone","displayName":null,"description":null,"status":null},{"creationDate":1357659889000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"scheduler","type":"Scheduler","defaultValue":null,"creationDate":1357659889000,"helpText":"","system":false,"name":"Cron Expression","id":"cabe362d-146c-46bd-afe6-f675cd4312e8","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1357659881000,"helpText":null,"system":true,"name":"Scheduler","id":"087d817f-6b87-4a3b-ad3e-0d854d7ea1f6","description":"CI Configuration","status":null}}};
			
			editConfiguration.loadPage();
			
			setTimeout(function() {
				start();
				var toAppend = $(commonVariables.contentPlaceholder).find("tbody[name=ConfigurationLists]");
				$(commonVariables.contentPlaceholder).find("ul[name=configurations] li").click(editConfiguration.configurationlistener.constructHtml(response, '', "Server", toAppend));
				toAppend.find('input[name=scheduleOption]').click(editConfiguration.configurationlistener.currentEvent('Weekly', toAppend.find('tr #scheduleExpression:last')));
				equal(toAppend.find('#schedule_weekly').attr('id'), "schedule_weekly", "Crone Expression render");
			}, 1500);
		}); 
	}};
});