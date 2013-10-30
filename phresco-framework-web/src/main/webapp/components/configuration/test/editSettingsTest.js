
define(["configuration/editSettings"], function(EditSettings) {

	return {
		setUserInfo : function(){
			commonVariables.api.localVal.setSession('a20e25b3-61be-499f-8108-ae170a94b4c0', '{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"appInfos":[{"pomFile":null,"modules":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"version":"3.4.2","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"e5fe85d7-f01b-4d3d-8a58-3c3ae75cc0d2","displayName":null,"description":null,"status":null}],"projectCode":"test","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"test","id":"a20e25b3-61be-499f-8108-ae170a94b4c0","displayName":null,"description":"","status":null}},"status":"success"}');
			$('.hProjectId').val('a20e25b3-61be-499f-8108-ae170a94b4c0');
		},
		
		runTests: function () {
			module("editSettings.js;EditSettings");
			var editSettings = new EditSettings(), editConfig, self=this, certificate, serverAlive, settingsTemplate;

			asyncTest("Test - Edit Configuration Render Test", function() {
				self.setUserInfo();
				
				self.configList = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?projectCode=wordpress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}]});
					}
				});
				
				self.editConfig = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"?projectCode=wordpress&envName=Production",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				}); 
				
				$("img[name=editproject]").click();
				commonVariables.navListener.onMytabEvent("projectsettings");
				console.info("editConfiguration", $("a[name=editConfiguration]"));
				editSettings.configurationlistener.editConfiguration("Production", "true");
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('input[name=EnvName]').val(), "Production", "Edit Configuration Render Tested");
					self.addConfiguration(editSettings);
				}, 1500);
			});
		},
		
		addConfiguration : function(editSettings) {
			var self=this;
			asyncTest("Test - Add Configuration Test", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&customerId=photon&userId=admin&type=Server",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{"Apache Server":["2.3","2.2"],"Wamp":["2.1e"]},"settingsTemplate":{"appliesToTechs":[{"creationDate":1359036240000,"helpText":null,"system":false,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee html jquery","id":"74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee iphone hybrid","id":"6e222405-9831-4eed-81d9-c9870711d914","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"loreal iphone hybrid","id":"059fb4a0-7224-4a05-a5ec-c4b9bebb2485","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"loreal html jquery","id":"cb3cac8f-012b-4966-aa1b-d06f1fc03b7f","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":true,"possibleValues":["http","https"],"propertyTemplates":null,"multiple":false,"key":"protocol","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Protocol","id":"1ba69a28-c263-4751-85de-a06aef198759","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Host","id":"8a9f2d1e-9829-4710-b070-54d5277f07f0","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"port","type":"Number","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Port","id":"bed36f46-df33-4cef-b8ac-f59b9867e29a","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"admin_username","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Username","id":"578439f9-dda0-4359-8aec-0debe88b8e79","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"admin_password","type":"Password","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Password","id":"3cb9fed8-6d0d-43ff-8e90-0cbad1ed6410","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"remoteDeployment","type":"Boolean","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Remote Deployment","id":"c0029d24-7cfc-475a-a981-095388b1e9e2","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"certificate","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Certificate","id":"cdc44d80-fa82-4791-91ab-16761ee0b580","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"type","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Server Type","id":"2051ba38-03e1-4bf1-a63c-df956d642afe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"version","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Version","id":"2051ba38-03e1-4bf1-a63c-df956d642wafe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"deploy_dir","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Deploy Directory","id":"7c993072-0e94-4339-8cdc-2c8b48637f5b","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Context","id":"e779c012-4c45-467d-b146-67a0a59e8f32","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"additional_context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Additional Context Path","id":"6b945b56-5410-464a-9176-f4f042e36d31","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","05c80933-95d4-46c8-a58d-ceceb4bcce48","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1359036240000,"helpText":null,"system":true,"name":"Server","id":"config_Server","description":"Server Configuration","status":null}}});
					}

				});
				
				editSettings.configurationlistener.addConfiguration("Server");
				setTimeout(function() {
					start();
					console.info("")
					equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Server", "Add Configuration Tested");
					self.addServerConfigurationValidation(editSettings);
				}, 1500);
			});
		},
		
		addServerConfigurationValidation : function(editSettings) {
			var self=this;
			asyncTest("Test - Server already added Test", function() {
				
				editSettings.configurationlistener.addConfiguration("Server");
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.row_bg').length, 1, "Server already added Test");
					self.serverChangeEvent(editSettings);
				}, 1500);
			}); 
			
		},
		
		serverChangeEvent : function(editSettings) {
			var self=this;
			asyncTest("Test - Server Change Event Test", function() {
				
				$('select[name=type]').val('Wamp');
				$('select[name=type]').change();
				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("select[currentconfig=Server]").val(), "2.1e", "Server Change Event Tested");
					self.remoteDeployTrue(editSettings);
				}, 1500);
			}); 
		},
		
		remoteDeployTrue : function(editSettings) {
			var self=this;
			asyncTest("Test - Remote Deploy True Event Test", function() {
				
				$("input[name=remoteDeployment]").attr("checked", false);
				$("input[name=remoteDeployment]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("input[name=deploy_dir]").css('display'), "none", "Remote Deploy True Tested");
					self.remoteDeployFalse(editSettings);
				}, 1500);
			}); 
			
		},
		
		remoteDeployFalse : function(editSettings) {
			var self=this;
			asyncTest("Test - Remote Deploy False Event Test", function() {
				
				$("input[name=remoteDeployment]").attr("checked", true);
				$("input[name=remoteDeployment]").click();
				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("input[name=deploy_dir]").css('display'), "none", "Remote Deploy Click Tested");
					self.serverActiveEvent(editSettings);
				}, 1500);
			}); 
			
		},
		
		serverActiveEvent : function(editSettings) {
			var self=this;
			asyncTest("Test - Server Active Event Test", function() {
			
				self.serverAlive = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url=http,localhost,3030",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600005","data":true,"status":"success"});
					}
				});
				
				editSettings.configurationlistener.isAliveCheck('localhost', '3030','Server','http');
				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find(".active").text(), "Active", "Server Active Event Tested");
					self.serverInActiveEvent(editSettings);
				}, 1500);
			}); 
			
		},
	
		serverInActiveEvent : function(editSettings) {
			var self=this;
			asyncTest("Test - Server In Active Event Test", function() {
				$.mockjaxClear(self.serverAlive);
				self.serverAlive = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url=http,localhost,3030",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600005","data":false,"status":"success"});
					}
				});
				
				editSettings.configurationlistener.isAliveCheck('localhost', '3030','Server','http');
				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find(".inactive").text(), "In Active", "Server In Active Event Tested");
					self.removeConfiguration(editSettings);
				}, 1500);
			}); 
			
		},
	
		removeConfiguration : function(editSettings) {
			var self=this;
			asyncTest("Test - Remove Configuration Test", function() {
				
				$("a[name=removeConfig]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), undefined, "Remove Configuration Tested");
					self.addOtherConfiguration(editSettings);
				}, 1500);
			}); 
			
		},
	
		addOtherConfiguration : function(editSettings) {
			var self=this;
			asyncTest("Test - Add Other Configuration Test", function() {
				
				editSettings.configurationlistener.htmlForOther("", "", "");
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Other", "Add Other Configuration Tested");
					self.validationForConfiguration(editSettings);
				}, 1500);
			}); 
		},
	
		validationForConfiguration : function(editSettings) {
			var self=this;
			asyncTest("Test - Validation for Configuration Test", function() {
				
				editSettings.configurationlistener.validation();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#ConfigOther").attr('placeholder'), "Enter Configuration Name", "Validation for Configuration Tested");
					self.validationForKey(editSettings);
				}, 1500);
			}); 
		},
	
		validationForKey : function(editSettings) {
			var self=this;
			asyncTest("Test - Validation for Other Configuration key Test", function() {
				
				$.mockjax({
					  url: commonVariables.webserviceurl+"util/checkLock?actionType=configUpdate&appId=6d6753e8-b081-48d8-9924-70a14f3663d4",
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
					  }
					});
					
				$(".headerAppId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");
				$("#ConfigOther").val('testOther');
				$("input[name=UpdateConfiguration]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.otherKey').attr('placeholder'), "Enter Key", "Validation for Other Configuration key Test");
					equal($(commonVariables.contentPlaceholder).find('.otherKeyValue').attr('placeholder'), "Enter Value", "Validation for Other Configuration key Value Test");
					self.addOtherConfigurationKeyValue(editSettings);
				}, 1500);
			}); 
		},
	
		addOtherConfigurationKeyValue : function(editSettings) {
			var self=this;
			asyncTest("Test - Add Other Configuration Key Test", function() {
				
				$("a[name=addOther]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('tbody[name=ConfigurationLists] tr').length, 5, "Add Other Configuration Key Tested");
					self.removeOtherConfigurationKeyValue(editSettings);
				}, 1500);
			}); 
		},
	
		removeOtherConfigurationKeyValue : function(editSettings) {
			var self=this;
			asyncTest("Test - Remove Other Configuration Key Test", function() {
				
				$("a[name=removeOther]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('tbody[name=ConfigurationLists] tr').length, 3, "Remove Other Configuration Key Tested");
					self.addDatabaseConfiguration(editSettings);
				}, 1500);
			}); 
		},
	
		addDatabaseConfiguration : function(editSettings) {
			var self=this;
			$("a[name=removeConfig]").click();
			asyncTest("Test - Add Database Configuration Test", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&customerId=photon&userId=admin&type=Database",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600004","data":{"downloadInfo":{"DB":["10"],"MYSQL":["2008"]},"settingsTemplate":{"envSpecific":true,"favourite":false,"possibleTypes":[],"appliesToTechs":[{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Node JS Web Service","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"php test","id":"f746e81f-1b44-4ce0-9899-6c9e32c6924f","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee html jquery","id":"74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"customProp":false,"properties":[{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"dbname","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"DB Name","id":"207740c7-175b-4ece-aac9-c5b03a0bd495","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"type","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"DB Type","id":"c8747e03-e22f-4b22-bffa-8c67b4bea342","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"username","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Username","id":"808536dd-f6fe-4114-a11b-d23e52ff8628","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"version","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Version","id":"c8747e03-e22f-4b22-bffa-8c67sb4bea342","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Host","id":"117ab816-32d6-4186-8a0e-c845154d4526","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"port","type":"Number","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Port","id":"6e6e7c5d-d5ec-4aec-8c3c-4a61acce96aa","displayName":null,"description":null,"status":null},{"required":false,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"password","type":"Password","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Password","id":"aecfb889-53c1-40c3-af71-24df5a271ed2","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1359036654000,"helpText":null,"system":true,"name":"Database","id":"config_Database","description":"Database Configuration","status":null}},"status":"success"});
					}

				});
				
				editSettings.configurationlistener.addConfiguration("Database");
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Database", "Add Database Configuration Tested");
					self.updateConfiguration(editSettings);
				}, 1500);
			});
		},
	
		updateConfiguration : function(editSettings) {
			var self=this;
			asyncTest("Test - Update Configuration Test", function() {
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?projectCode=wordpress&envName=Production&customerId=photon&userId=admin&oldEnvName=Production&defaultEnv=true&desc=Production%20Environment%20is%20used%20for%20Development%20purpose%20only",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Configurations Updated Successfully","exception":null,"data":"Success"});
					}

				});
				
				$(".configName").val('email');
				$("input[name=host]").val('localhost');
				$("input[name=port]").val('3030');
				$("input[name=username]").val('ssss');
				$("input[name=password]").val('ssss');
				$("input[name=emailid]").val('ssss@gmail.com');
				$("input[name=UpdateConfiguration]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find(".configName").val(), "email", "Update Configuration Tested");
					self.cancleEventTest(editSettings);
				}, 1500);
			}); 
		},
	
		cancleEventTest : function(editSettings) {
			var self=this;
			asyncTest("Test - Cancel Event Configuration Test", function() {
				
				$("#cancelEditConfig").click();
				
				setTimeout(function() {
					start();
					equal($("#configurationPage").attr('id'), "configurationPage", "Cancel Event Configuration Tested");
					require(["unitTestTest"], function(unitTestTest){
						unitTestTest.runTests();
					}); 
				}, 1500);
			}); 
		}
		
	};
});