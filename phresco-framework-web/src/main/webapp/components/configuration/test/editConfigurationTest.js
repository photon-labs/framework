
define(["configuration/editConfiguration"], function(EditConfiguration) {

	return {
	
	setUserInfo : function(){
		commonVariables.api.localVal.setSession('a20e25b3-61be-499f-8108-ae170a94b4c0', '{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"appInfos":[{"pomFile":null,"modules":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"version":"3.4.2","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"e5fe85d7-f01b-4d3d-8a58-3c3ae75cc0d2","displayName":null,"description":null,"status":null}],"projectCode":"test","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"test","id":"a20e25b3-61be-499f-8108-ae170a94b4c0","displayName":null,"description":"","status":null}},"status":"success"}');
		$('.hProjectId').val('a20e25b3-61be-499f-8108-ae170a94b4c0');
	},
	
	runTests: function (runOtherTests) {
		
		module("EditConfiguration.js;EditConfiguration");
		var editConfiguration = new EditConfiguration(), editConfig, self=this, certificate, serverAlive;

		asyncTest("Test - Edit Configuration Render Test", function() {
			self.setUserInfo();
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=wordpress-WordPress&envName=Production",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}});
				}
			});
			
			editConfiguration.configurationlistener.editConfiguration("Production", "true");
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('input[name=EnvName]').val(), "Production", "Edit Configuration Render Tested");
				self.addConfiguration(editConfiguration);
			}, 1500);
		}); 
	},
	
	addConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Add Configuration Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Server&techId=tech-wordpress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{"Apache Server":["2.3","2.2"],"Wamp":["2.1e"]},"settingsTemplate":{"appliesToTechs":[{"creationDate":1359036240000,"helpText":null,"system":false,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee html jquery","id":"74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee iphone hybrid","id":"6e222405-9831-4eed-81d9-c9870711d914","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"loreal iphone hybrid","id":"059fb4a0-7224-4a05-a5ec-c4b9bebb2485","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"loreal html jquery","id":"cb3cac8f-012b-4966-aa1b-d06f1fc03b7f","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":true,"possibleValues":["http","https"],"propertyTemplates":null,"multiple":false,"key":"protocol","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Protocol","id":"1ba69a28-c263-4751-85de-a06aef198759","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Host","id":"8a9f2d1e-9829-4710-b070-54d5277f07f0","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"port","type":"Number","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Port","id":"bed36f46-df33-4cef-b8ac-f59b9867e29a","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"admin_username","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Username","id":"578439f9-dda0-4359-8aec-0debe88b8e79","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"admin_password","type":"Password","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Admin Password","id":"3cb9fed8-6d0d-43ff-8e90-0cbad1ed6410","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"remoteDeployment","type":"Boolean","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Remote Deployment","id":"c0029d24-7cfc-475a-a981-095388b1e9e2","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"certificate","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Certificate","id":"cdc44d80-fa82-4791-91ab-16761ee0b580","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"type","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Server Type","id":"2051ba38-03e1-4bf1-a63c-df956d642afe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"version","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Version","id":"2051ba38-03e1-4bf1-a63c-df956d642wafe","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"deploy_dir","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Deploy Directory","id":"7c993072-0e94-4339-8cdc-2c8b48637f5b","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Context","id":"e779c012-4c45-467d-b146-67a0a59e8f32","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":[],"propertyTemplates":null,"multiple":false,"key":"additional_context","type":"String","defaultValue":null,"creationDate":1359036240000,"helpText":"","system":false,"name":"Additional Context Path","id":"6b945b56-5410-464a-9176-f4f042e36d31","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","05c80933-95d4-46c8-a58d-ceceb4bcce48","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1359036240000,"helpText":null,"system":true,"name":"Server","id":"config_Server","description":"Server Configuration","status":null}}});
				}

			});
			
			editConfiguration.configurationlistener.addConfiguration("Server");
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Server", "Add Configuration Tested");
				self.addServerConfigurationValidation(editConfiguration);
			}, 1500);
		});
	},
	
	addServerConfigurationValidation : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Server already added Test", function() {
			
			editConfiguration.configurationlistener.addConfiguration("Server");
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').length, 1, "Server already added Test");
				self.serverChangeEvent(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	serverChangeEvent : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Server Change Event Test", function() {
			
			$('select[name=type]').val('Wamp');
			$('select[name=type]').change();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("select[currentconfig=Server]").val(), "2.1e", "Server Change Event Tested");
				self.remoteDeployTrue(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	remoteDeployTrue : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Remote Deploy True Event Test", function() {
			
			$("input[name=remoteDeployment]").attr("checked", false);
			$("input[name=remoteDeployment]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("input[name=deploy_dir]").css('display'), "none", "Remote Deploy True Tested");
				self.remoteDeployFalse(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	remoteDeployFalse : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Remote Deploy False Event Test", function() {
			
			$("input[name=remoteDeployment]").attr("checked", true);
			$("input[name=remoteDeployment]").click();
			
			setTimeout(function() {
				start();
				notEqual($(commonVariables.contentPlaceholder).find("input[name=deploy_dir]").css('display'), "none", "Remote Deploy Click Tested");
				self.addCertificate(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	addCertificate : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Add Certificate Test", function() {
			
			self.certificate = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/returnCertificate?host=localhost&port=3030&appDirName=wordpress-WordPress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Https server certificate returned successfully","exception":null,"data":{"certificateAvailable":true,"projectLocation":"C:\\Documents and Settings\\sathiyaselvan_s\\workspace\\projects\\111-php","certificates":[{"displayName":"CN=kumar_s","subjectDN":"CN=kumar_s, OU=phresco, O=photon, L=chennai, ST=TN, C=IN"}]}});
				}
			});
			
			$("input[name=remoteDeployment]").attr("checked", true);
			$('select[name=protocol]').val('https');
			$("input[name=host]").val('localhost');
			$("input[name=port]").val('3030');
			$("input[name=remoteDeployment]").click();
			$('a[name=remote_deploy]').click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('select[name=certificateValue]').val(), "CN=kumar_s", "Add Certificate Tested");
				self.saveCertificate(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	saveCertificate : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Save Certificate Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/addCertificate",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Https server certificate added successfully","exception":null,"data":".phresco\\certificates\\new-Server.crt"});
				}
			});
			
			$('select[name=protocol]').val('https');
			$("input[name=host]").val('localhost');
			$("input[name=port]").val('3030');
			$('input[name=EnvName]').val("Production");
			editConfiguration.configurationlistener.saveCertificate("CN=kumar_s");
			$("input[name=selectFilePath]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('input[name=certificate]').val(), "CN=kumar_s", "Save Certificate Tested");
				self.serverActiveEvent(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	serverActiveEvent : function(editConfiguration) {
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
			
			editConfiguration.configurationlistener.isAliveCheck('localhost', '3030','Server','http');
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".active").text(), "Active", "Server Active Event Tested");
				self.serverInActiveEvent(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	serverInActiveEvent : function(editConfiguration) {
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
			
			editConfiguration.configurationlistener.isAliveCheck('localhost', '3030','Server','http');
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".inactive").text(), "In Active", "Server In Active Event Tested");
				self.removeConfiguration(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	removeConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Remove Configuration Test", function() {
			
			$("a[name=removeConfig]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), undefined, "Remove Configuration Tested");
				self.addOtherConfiguration(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	addOtherConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Add Other Configuration Test", function() {
			
			editConfiguration.configurationlistener.htmlForOther("", "", "");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Other", "Add Other Configuration Tested");
				self.validationForConfiguration(editConfiguration);
			}, 1500);
		}); 
	},
	
	validationForConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Validation for Configuration Test", function() {
			
			editConfiguration.configurationlistener.validation();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#ConfigOther").attr('placeholder'), "Enter Configuration Name", "Validation for Configuration Tested");
				self.validationForKey(editConfiguration);
			}, 1500);
		}); 
	},
	
	validationForKey : function(editConfiguration) {
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
				self.addOtherConfigurationKeyValue(editConfiguration);
			}, 1500);
		}); 
	},
	
	
	addOtherConfigurationKeyValue : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Add Other Configuration Key Test", function() {
			
			$("a[name=addOther]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('tbody[name=ConfigurationLists] tr').length, 5, "Add Other Configuration Key Tested");
				self.removeOtherConfigurationKeyValue(editConfiguration);
			}, 1500);
		}); 
	},
	
	removeOtherConfigurationKeyValue : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Remove Other Configuration Key Test", function() {
			
			$("a[name=removeOther]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('tbody[name=ConfigurationLists] tr').length, 3, "Remove Other Configuration Key Tested");
				self.addDatabaseConfiguration(editConfiguration);
			}, 1500);
		}); 
	},
	
	addDatabaseConfiguration : function(editConfiguration) {
		var self=this;
			$("a[name=removeConfig]").click();
		asyncTest("Test - Add Database Configuration Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Database&techId=tech-wordpress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600004","data":{"downloadInfo":{"DB":["10"],"MYSQL":["2008"]},"settingsTemplate":{"envSpecific":true,"favourite":false,"possibleTypes":[],"appliesToTechs":[{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"Node JS Web Service","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"php test","id":"f746e81f-1b44-4ce0-9899-6c9e32c6924f","displayName":null,"description":null,"status":null},{"creationDate":1359036654000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"creationDate":1359036240000,"helpText":null,"system":false,"name":"estee html jquery","id":"74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"customProp":false,"properties":[{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"dbname","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"DB Name","id":"207740c7-175b-4ece-aac9-c5b03a0bd495","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"type","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"DB Type","id":"c8747e03-e22f-4b22-bffa-8c67b4bea342","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"username","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Username","id":"808536dd-f6fe-4114-a11b-d23e52ff8628","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"version","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Version","id":"c8747e03-e22f-4b22-bffa-8c67sb4bea342","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Host","id":"117ab816-32d6-4186-8a0e-c845154d4526","displayName":null,"description":null,"status":null},{"required":true,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"port","type":"Number","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Port","id":"6e6e7c5d-d5ec-4aec-8c3c-4a61acce96aa","displayName":null,"description":null,"status":null},{"required":false,"appliesTo":["tech-phpdru6","tech-phpdru7","tech-html5-jquery-mobile-widget","tech-html5-jquery-widget","tech-html5","tech-html5-mobile-widget","tech-nodejs-webservice","tech-php","tech-wordpress","74a3c4e0-d8ba-47fb-9325-4f6f1be612e8","3bfc67a1-588d-41f0-9b8a-2807317d5c70","tech-java-webservice","tech-java-standalone"],"possibleValues":[],"propertyTemplates":null,"settingsTemplateId":"config_Database","multiple":false,"key":"password","type":"Password","defaultValue":null,"creationDate":1359036654000,"helpText":"","system":false,"name":"Password","id":"aecfb889-53c1-40c3-af71-24df5a271ed2","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","c32171c4-90e5-4ede-9c51-0ff370eae974","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1359036654000,"helpText":null,"system":true,"name":"Database","id":"config_Database","description":"Database Configuration","status":null}},"status":"success"});
				}

			});
			
			editConfiguration.configurationlistener.addConfiguration("Database");
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Database", "Add Database Configuration Tested");
				self.databaseChangeEvent(editConfiguration);
			}, 1500);
		});
	},
	
	databaseChangeEvent : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Database Change Event Test", function() {
			
			$('select[name=type]').val('MYSQL');
			$('select[name=type]').change();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("select[currentconfig=Database1]").val(), "2008", "Database Change Event Tested");
				self.addEmailConfiguration(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	addEmailConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Email Configuration Validation Test", function() {
			
			$("a[name=removeConfig]").click();
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Email&techId=tech-wordpress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{},"settingsTemplate":{"appliesToTechs":[{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Php","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Drupal 6","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Drupal 7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Dotnet","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Nodejs Webservice","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Java Webservice","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"incoming_mail_server","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Incoming Mail Server","id":"41479c58-73c1-40f7-bd47-984cc6b5e3e5","displayName":null,"description":"Name or IPAddress of the incoming email server","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"incoming_mail_port","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Incoming Port","id":"39d432af-b223-4115-b127-d03081631f29","displayName":null,"description":"Name or IPAddress of the incoming email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Outgoing Server Name","id":"fdb07cbd-6d77-4fa4-b438-3389312e098a","displayName":null,"description":"Name or IPAddress of the email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"port","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Outgoing Port","id":"01d2a388-c2cb-4335-a928-18846203857d","displayName":null,"description":"Name or IPAddress of the outgoing email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"username","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Username","id":"7bee741b-2321-413f-aa11-8322642a7a8d","displayName":null,"description":"Username of the Email address","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"password","type":"Password","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Password","id":"0df73568-2d98-43b8-bbc6-05eca1454f49","displayName":null,"description":"Password for the email address","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"emailid","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Email Id","id":"21c590a3-3508-4974-b57e-ac572a21f388","displayName":null,"description":"Email Id","status":null}],"type":null,"displayName":null,"customerIds":["photon"],"used":false,"creationDate":1349685730343,"helpText":null,"system":true,"name":"Email","id":"config_Email","description":"Email configuration","status":null}}});
				}

			});
			
			editConfiguration.configurationlistener.addConfiguration("Email");
			
			setTimeout(function() {
				start();
				$(".configName").val('');
				$("input[name=UpdateConfiguration]").click();
				equal($(commonVariables.contentPlaceholder).find(".configName").attr('placeholder'), "Configuration Name", "Email Configuration Validation Tested");
				self.addEmailConfigurationTextBoxValidation(editConfiguration);
			}, 1500);
		}); 
	},
	
	addEmailConfigurationTextBoxValidation : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Email Configuration Text Box Validation Test", function() {
			
			$(".configName").val('web');
			$("input[configkey=configKeyEmailhost]").val('');
			$("input[name=UpdateConfiguration]").click();
			setTimeout(function() {
				start();	
				equal($(commonVariables.contentPlaceholder).find("input[name=host]").attr('placeholder'), "Enter Value", "Email Configuration text box Validation Tested");
				self.addEmailConfigurationEmailValidation(editConfiguration);
			}, 1500);
		}); 
	}, 
	
	addEmailConfigurationEmailValidation : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Email Configuration Email Validation Test", function() {
			
			$(".configName").val('web');
			$("input[name=host]").val('localhost');
			$("input[name=port]").val('3030');
			$("input[name=username]").val('ssss');
			$("input[name=password]").val('ssss');
			$("input[name=emailid]").val('ssss');
			$("input[name=UpdateConfiguration]").click();
			setTimeout(function() {
				start();	
				equal($(commonVariables.contentPlaceholder).find("input[name=emailid]").attr('placeholder'), "Please Enter valid email address", "Email Configuration Email Validation Tested");
				self.updateConfiguration(editConfiguration);
			}, 1500);
		}); 
	}, 
	
	updateConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Update Configuration Test", function() {
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName=wordpress-WordPress&envName=Production&customerId=photon&userId=admin&oldEnvName=Production&defaultEnv=true&desc=Production%20Environment%20is%20used%20for%20Development%20purpose%20only",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Configurations Updated Successfully","exception":null,"data":"Success"});
				}

			});
			
			setTimeout(function() {
				start();
				$(".configName").val('email');
				$("input[name=host]").val('localhost');
				$("input[name=port]").val('3030');
				$("input[name=username]").val('ssss');
				$("input[name=password]").val('ssss');
				$("input[name=emailid]").val('ssss@gmail.com');
				$("input[name=UpdateConfiguration]").click();
				equal($(commonVariables.contentPlaceholder).find(".configName").val(), "email", "Update Configuration Tested");
				self.addEmailConfigurationValidation(editConfiguration);
			}, 1500);
		}); 
	},
	
	addEmailConfigurationValidation : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Email already added Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=wordpress-WordPress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}]});
				}
			});
			
			editConfiguration.configurationlistener.addConfiguration("Email");
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').length, 1, "Email already added Test");
				self.componetConfiguration(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	componetConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Component Configuration Render Test", function() {
			$("a[name=removeConfig]").click();
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/configType?userId=admin&type=Components&customerId=photon&appDirName=wordpress-WordPress",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600023","data":["Coupons","Settings","Shop","Photo"],"status":"success"});
				}
			});
			$(".configName").val('Production');
			editConfiguration.configurationlistener.addConfiguration("Components");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('tr.row_bg').attr('configtype'), "Components", "Component Configuration Render Tested");
				self.componetConfigPropertyRender(editConfiguration);
			}, 1500);
		}); 
	},
	
	componetConfigPropertyRender : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Component Configuration key's Render Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/showFeatureConfigs?userId=admin&customerId=photon&appDirName=wordpress-WordPress&envName=Production&featureName=Shop",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600024","data":{"propertyTemplates":[{"required":false,"possibleValues":null,"appliesTo":null,"propertyTemplates":null,"multiple":false,"settingsTemplateId":null,"key":"Environment.SHOPLANDING","type":null,"defaultValue":null,"creationDate":1379418716561,"helpText":null,"system":false,"name":"Environment SHOPLANDING","id":"21ee19e8-e0b0-4bcb-8592-7618c92b2898","displayName":null,"description":null,"status":null}],"hasCustomProperty":true,"properties":{"Environment.SHOPLANDING":"http://staging-apps.usablenet.com/mt/qa1.walgreens.com/store/catalog/shopLanding?un_jtt_redirect"}},"status":"success"});
				}
			});
			
			$("select[compType=components0]").val('Shop');
			$("select[compType=components0]").change();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('input.Components0Configuration').val(), "http://staging-apps.usablenet.com/mt/qa1.walgreens.com/store/catalog/shopLanding?un_jtt_redirect", "Component Configuration key's Render Tested");
				self.editComponetConfig(editConfiguration);
			}, 1500);
		}); 
	},
	
	editComponetConfig : function(editConfiguration) {
		var self=this;
		$("#content").html('');
		asyncTest("Test - Edit Component Configuration Render Test", function() {
			$.mockjaxClear(self.editConfig);
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=wordpress-WordPress&envName=Production",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600002","data":{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"new","properties":{"components":"Shop","Environment.SHOPLANDING":"http://staging-apps.usablenet.com/mt/qa1.walgreens.com/store/catalog/shopLanding?un_jtt_redirect"},"type":"Components","desc":"desc"}]},"status":"success"});
				}
			});
			
			editConfiguration.configurationlistener.editConfiguration("Production", "true");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('input.Components1Configuration').val(), "http://staging-apps.usablenet.com/mt/qa1.walgreens.com/store/catalog/shopLanding?un_jtt_redirect", "Edit Component Configuration Render Tested");
				self.nonEnvAddConfigurationRender(editConfiguration);
			}, 2000);
		}); 
	},
	
	nonEnvAddConfigurationRender : function(editConfiguration) {
		var self=this;
		$("#content").html('');
		asyncTest("Test - Non Environment Add Configuration Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Theme&techId=tech-wordpress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600004","data":{"downloadInfo":{},"settingsTemplate":{"envSpecific":false,"favourite":false,"appliesToTechs":[{"creationDate":1379062654386,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null}],"possibleTypes":[],"customProp":false,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"validateTheme","type":"Actions","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Validate Theme","id":"4248a10e-14cd-430a-bb13-0aef022b4071","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"convertTheme","type":"Actions","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Convert Theme","id":"aa1e4f97-d8d2-456e-a44c-ee4d454f3000","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7","tech-html5-jquery-mobile-widget"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"themeFile","type":"FileType","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Theme File","id":"6b7b3cc6-3b82-4fdf8-9364-a762fed02c13","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon"],"used":false,"creationDate":1356526713062,"helpText":null,"system":true,"name":"Theme","id":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","description":"Theme Configuration","status":null}},"status":"success"});
				}

			});
			
			editConfiguration.configurationlistener.nonEnvConfig("Theme", "false");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".row_bg").attr('type'), "Theme", "Non Add Environment Configuration Test");
				self.nonEnvAddConfigurationValidate(editConfiguration);
			}, 2000);
		}); 
	},
	
	nonEnvAddConfigurationValidate : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Non Environment Validate Test", function() {
		
			$(".configName").val('test');
			$("input[name=UpdateConfiguration]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".row_bg").attr('type'), "Theme", "Non Environment Validate Tested");
				self.nonEnvEditConfigurationRender(editConfiguration);
			}, 2000);
		}); 
	},
	
	nonEnvEditConfigurationRender : function(editConfiguration) {
		var self=this;
		$("#content").html('');
		asyncTest("Test - Non Environment Edit Configuration Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Theme&techId=tech-wordpress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600004","data":{"downloadInfo":{},"settingsTemplate":{"envSpecific":false,"favourite":false,"appliesToTechs":[{"creationDate":1379062654386,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null}],"possibleTypes":[],"customProp":false,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"validateTheme","type":"Actions","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Validate Theme","id":"4248a10e-14cd-430a-bb13-0aef022b4071","displayName":null,"description":null,"status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"convertTheme","type":"Actions","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Convert Theme","id":"aa1e4f97-d8d2-456e-a44c-ee4d454f3000","displayName":null,"description":null,"status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"appliesTo":["tech-phpdru7","tech-html5-jquery-mobile-widget"],"multiple":false,"settingsTemplateId":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","key":"themeFile","type":"FileType","defaultValue":null,"creationDate":1356526713000,"helpText":"","system":false,"name":"Theme File","id":"6b7b3cc6-3b82-4fdf8-9364-a762fed02c13","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon"],"used":false,"creationDate":1356526713062,"helpText":null,"system":true,"name":"Theme","id":"f57f0b9c-89b5-4ffe-8c8d-d7e2b0cdbaab","description":"Theme Configuration","status":null}},"status":"success"});
				}

			});
			
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=wordpress-WordPress&configName=test&isEnvSpecific=false",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600002","data":{"envName":null,"name":"test","properties":{"files":"/do_not_checkin/theme\\test.zip"},"type":"Theme","desc":"terstrt"},"status":"success"});
				}
			});
		
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/listUploadedFiles?appDirName=wordpress-WordPress&isEnvSpecific=false&configName=test&envName=&configType=Theme",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"","data":["/do_not_checkin/theme/test.zip"],"status":"success"});
				}
			});
			
			editConfiguration.configurationlistener.editConfiguration("test", "false");
			
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".row_bg").attr('type'), "Theme1", "Non Environment Edit Configuration Test");
			}, 2500);
			self.removeFile(editConfiguration);
		}); 
	},
	
	removeFile : function(editConfiguration) {
		var self=this;
		
		asyncTest("Test - Remove file Test", function() {
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/removeFile?appDirName=wordpress-WordPress&configType=Theme&propName=themeFile&fileName=test.zip&envName=&configName=test",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"","data":["/do_not_checkin/theme/test.zip"],"status":"success"});
				}
			});
			
			$("a[name=removeFile]").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('ul.qq-upload-list').html(), "", "Remove file Tested");
				self.cancleEventTest(editConfiguration);
			}, 1500);
		}); 
	},
	
	cancleEventTest : function(editConfiguration) {
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
	},
	
	};
});