
define(["configuration/editConfiguration"], function(EditConfiguration) {

	return { runTests: function (runOtherTests) {
		
		module("EditConfiguration.js;EditConfiguration");
		

		var editConfiguration = new EditConfiguration(), editConfig, self=this, certificate;

		asyncTest("Test - Edit Configuration Render Test", function() {
		
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
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Server&techId=tech-html5-jquery-mobile-widget",
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
				self.serverAliveEvent(editConfiguration);
			}, 1500);
		}); 
		
	},
	
	serverAliveEvent : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Server Alive Event Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/connectionAliveCheck?url=http,localhost,3030",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = false;
				}
			});
			
			editConfiguration.configurationlistener.isAliveCheck('localhost', '3030','Server','http');
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".inactive").text(), "In Active", "Server Alive Event Tested");
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
			
			editConfiguration.configurationlistener.htmlForOther("");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find('.row_bg').attr('configtype'), "Other", "Add Other Configuration Tested");
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
				self.updateConfiguration(editConfiguration);
			}, 1500);
		}); 
	},
	
	updateConfiguration : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Update Configuration Test", function() {
			
			$("a[name=removeConfig]").click();
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=Email&techId=tech-html5-jquery-mobile-widget",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{},"settingsTemplate":{"appliesToTechs":[{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Php","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Drupal 6","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Drupal 7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Dotnet","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Nodejs Webservice","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"creationDate":1349685730343,"helpText":null,"system":false,"name":"Java Webservice","id":"tech-java-webservice","displayName":null,"description":null,"status":null}],"possibleTypes":null,"customProp":false,"favourite":false,"envSpecific":true,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"incoming_mail_server","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Incoming Mail Server","id":"41479c58-73c1-40f7-bd47-984cc6b5e3e5","displayName":null,"description":"Name or IPAddress of the incoming email server","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"incoming_mail_port","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Incoming Port","id":"39d432af-b223-4115-b127-d03081631f29","displayName":null,"description":"Name or IPAddress of the incoming email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"host","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Outgoing Server Name","id":"fdb07cbd-6d77-4fa4-b438-3389312e098a","displayName":null,"description":"Name or IPAddress of the email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"port","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Outgoing Port","id":"01d2a388-c2cb-4335-a928-18846203857d","displayName":null,"description":"Name or IPAddress of the outgoing email server","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"username","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Username","id":"7bee741b-2321-413f-aa11-8322642a7a8d","displayName":null,"description":"Username of the Email address","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"password","type":"Password","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Password","id":"0df73568-2d98-43b8-bbc6-05eca1454f49","displayName":null,"description":"Password for the email address","status":null},{"required":true,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"emailid","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Email Id","id":"21c590a3-3508-4974-b57e-ac572a21f388","displayName":null,"description":"Email Id","status":null}],"type":null,"displayName":null,"customerIds":["photon"],"used":false,"creationDate":1349685730343,"helpText":null,"system":true,"name":"Email","id":"config_Email","description":"Email configuration","status":null}}});
				}

			});
			
			editConfiguration.configurationlistener.addConfiguration("Email");
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/updateConfig?appDirName=wordpress-WordPress&envName=Production&customerId=photon&userId=admin",
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
				self.nonEnvEditConfigurationRender(editConfiguration);
			}, 1500);
		}); 
	},
	
	nonEnvEditConfigurationRender : function(editConfiguration) {
		var self=this;
		$("#content").html('');
		asyncTest("Test - Non Environment Edit Configuration Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=SAP&techId=tech-html5-jquery-mobile-widget",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":"confuguration Template Fetched successfully","exception":null,"responseCode":null,"data":{"downloadInfo":{},"settingsTemplate":{"envSpecific":false,"favourite":false,"appliesToTechs":[{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Php","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Iphone Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Iphone Hybrid","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Android Native","id":"tech-android-native","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Android Hybrid","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"possibleTypes":[],"customProp":false,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"searchHosts","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Search Hosts","id":"c42b96f7-0d91-4a39-bd3a-ea031aad1254","displayName":null,"description":"SearchHosts","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"sapSvcHost","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SapSvc Host","id":"0f90ccf2-ffb2-4633-9317-48062d3a22e5","displayName":null,"description":"SapSvcHost","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"sapSvcPort","type":"Number","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SapSvcPort","id":"bf0de51b-4391-4f49-be98-c1d11d4190b3","displayName":null,"description":"SapSvcPort","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"smtp-host","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SmtpHost","id":"11821bc4-eeb5-42b6-b8c4-3fd5ec8f4557","displayName":null,"description":"SmtpHost","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"useProductionConfig","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"UseProductionConfig","id":"90ff8a9d-dfb6-4058-a89d-a4437d0e84e7","displayName":null,"description":"UseProductionConfig","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"auth-token-cache-ttl","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"AuthTokenCache ttl","id":"0d62a584-f3da-4a7d-b58d-54d76076a85d","displayName":null,"description":"AuthToken Cache ttl","status":null}],"type":null,"displayName":null,"customerIds":["photon","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1349685730343,"helpText":null,"system":true,"name":"SAP","id":"config_SAP","description":"SAP Configuration","status":null}},"status":null});
				}

			});
			
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=wordpress-WordPress&configName=ttt&isEnvSpecific=false",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":"Configurations Listed","exception":null,"responseCode":null,"data":{"envName":null,"name":"ttt","properties":{"smtp-host":"bb","auth-token-cache-ttl":"v","searchHosts":"bb","sapSvcPort":"bb","useProductionConfig":"bvbvb","sapSvcHost":"bv"},"type":"SAP","desc":"bb"},"status":null});
				}
			});
			
			editConfiguration.configurationlistener.editConfiguration("ttt", "false");
			
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".row_bg").attr('type'), "SAP", "Non Environment Edit Configuration Test");
			}, 2500);
			self.nonEnvAddConfigurationRender(editConfiguration);
		}); 
	},
	
	nonEnvAddConfigurationRender : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Non Environment Add Configuration Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=wordpress-WordPress&customerId=photon&userId=admin&type=SAP&techId=tech-html5-jquery-mobile-widget",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":"confuguration Template Fetched successfully","exception":null,"responseCode":null,"data":{"downloadInfo":{},"settingsTemplate":{"envSpecific":false,"favourite":false,"appliesToTechs":[{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Php","id":"tech-php","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Iphone Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Iphone Hybrid","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Android Native","id":"tech-android-native","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Android Hybrid","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},{"creationDate":1349685730000,"helpText":null,"system":false,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"possibleTypes":[],"customProp":false,"properties":[{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"searchHosts","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"Search Hosts","id":"c42b96f7-0d91-4a39-bd3a-ea031aad1254","displayName":null,"description":"SearchHosts","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"sapSvcHost","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SapSvc Host","id":"0f90ccf2-ffb2-4633-9317-48062d3a22e5","displayName":null,"description":"SapSvcHost","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"sapSvcPort","type":"Number","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SapSvcPort","id":"bf0de51b-4391-4f49-be98-c1d11d4190b3","displayName":null,"description":"SapSvcPort","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"smtp-host","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"SmtpHost","id":"11821bc4-eeb5-42b6-b8c4-3fd5ec8f4557","displayName":null,"description":"SmtpHost","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"useProductionConfig","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"UseProductionConfig","id":"90ff8a9d-dfb6-4058-a89d-a4437d0e84e7","displayName":null,"description":"UseProductionConfig","status":null},{"required":false,"possibleValues":null,"propertyTemplates":null,"multiple":false,"key":"auth-token-cache-ttl","type":"String","defaultValue":null,"creationDate":1349685730343,"helpText":null,"system":false,"name":"AuthTokenCache ttl","id":"0d62a584-f3da-4a7d-b58d-54d76076a85d","displayName":null,"description":"AuthToken Cache ttl","status":null}],"type":null,"displayName":null,"customerIds":["photon","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"creationDate":1349685730343,"helpText":null,"system":true,"name":"SAP","id":"config_SAP","description":"SAP Configuration","status":null}},"status":null});
				}

			});
			
			editConfiguration.configurationlistener.nonEnvConfig("SAP", "false");
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".row_bg").attr('type'), "SAP", "Non Add Environment Configuration Test");
				self.cancleEventTest(editConfiguration);
			}, 2000);
		}); 
	},
	
	cancleEventTest : function(editConfiguration) {
		var self=this;
		asyncTest("Test - Cancel Event Configuration Test", function() {
			
			$("#cancelEditConfig").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#configurationPage").attr('id'), "configurationPage", "Cancel Event Configuration Test");
			}, 1500);
		}); 
	},
	
	};
});