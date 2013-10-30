define(["configuration/settings"], function(Settings) {
	return {
		
		setUserInfo : function(){
			commonVariables.api.localVal.setSession('a20e25b3-61be-499f-8108-ae170a94b4c0', '{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"appInfos":[{"pomFile":null,"modules":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"version":"3.4.2","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"e5fe85d7-f01b-4d3d-8a58-3c3ae75cc0d2","displayName":null,"description":null,"status":null}],"projectCode":"test","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"test","id":"a20e25b3-61be-499f-8108-ae170a94b4c0","displayName":null,"description":"","status":null}},"status":"success"}');
			$('.hProjectId').val('a20e25b3-61be-499f-8108-ae170a94b4c0');
		},
		
		runTests: function () {
			
			module("settings.js;Settings");
			
			var settings = new Settings(), self=this, configList;
			
			$(commonVariables.contentPlaceholder).find("#configurationPage").empty();
			$(commonVariables.contentPlaceholder).find("section.add_config").empty();
			
			asyncTest("Test - Settings Page render", function() {
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
				
				$("img[name=editproject]").click();
				commonVariables.navListener.onMytabEvent("projectsettings");
				
				setTimeout(function() {
					start();
					
					equal($(commonVariables.contentPlaceholder).find(".envlistname").text(), "Production", "Configuration Page render Tested");
					equal($(commonVariables.contentPlaceholder).find("#content_Env li").length, 1, "Add Configuration Page render Tested");
					self.validationTest(settings);
				}, 1500);
			});
		},
		
		validationTest : function(settings) {
			var self =this;
			asyncTest("Test - Add Environment validation Test", function() {
				$("input[name=envName]").val('');
				$("input[name=envDesc]").val('');
				$("input[name=addEnv]").click();
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("input[name='envName']").attr('placeholder'), "Enter Environment Name", "Add Environment validation Tested");
					self.addEnvironment(settings);
				}, 1500);
			});
		},
		
		addEnvironment : function(settings) {
			var self =this;
			asyncTest("Test - Add Environment Test", function() {
				$("input[name=envName]").val('test');
				$("input[name=envDesc]").val('test');
				$("input[name=addEnv]").click();
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#content_Env li").length, 2, "Add Environment Tested");
					self.saveEnvironment(settings);
				}, 1500);
			});
		},
		
		saveEnvironment : function(settings) {
			var self =this;
			asyncTest("Test - Save Environment Test", function() {
				$.mockjaxClear(self.configList);
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"?projectCode=wordpress&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&customerId=photon",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environments added Successfully","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":false,"name":"test","desc":"test","configurations":[]}]});
					}
				});
				
				self.configList = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?projectCode=wordpress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}]});
					}
				});
				
				$("input[name=saveEnvironment]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find(".envlistname").text(),"Productiontest", "Save Environment Tested");
					self.cloneEnvironment(settings);
				}, 1500);
			});
		},
		
		cloneEnvironment : function(settings) {
			var self=this;
			$("#content").html('');
			asyncTest("Test - Clone Environment Test", function() {
				$.mockjaxClear(self.configList);
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?projectCode=wordpress&envName=Production",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
					}
				});
				
				self.configList = $.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?projectCode=wordpress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}]});
					}
				});
				
				$("img[name=editproject]").click();
				commonVariables.navListener.onMytabEvent("projectsettings");
				
				$("input[name=envrName]").val('sample');
				$("input[name=envrDesc]").val('sample');
				$("input[name=cloneEnvr]").click();
				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find(".envlistname").text(),"Productionsample", "Clone Environment Tested");
					self.cloneValidationEnvironment(settings);
				}, 1500);
			});
		},
		
		cloneValidationEnvironment : function(configuration) {
			var self=this, output;
			asyncTest("Test - Clone Validation Test", function() {
				$.mockjaxClear(self.configList);
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName=wordpress-WordPress&envName=Production",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
					}
				});
				
				configuration.configurationlistener.cloneEnv("cloneEnv", "", function(res){
					output = res.defaultEnv;
				});
				setTimeout(function() {
					start();
					equal(output,false, "Clone Validation Test");
					self.deleteEnvironment(configuration);
				}, 1500);
			});
		},
		
		deleteEnvironment : function(settings) {
			var self =this;
			asyncTest("Test - Delete Environment Test", function() {
			
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?projectCode=wordpress&envName=Production",
					type:'DELETE',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?projectCode=wordpress&envName=sample",
					type:'DELETE',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
					}
				});
				
				$("input[name=deleteEnv]").click();
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find('tbody[name=EnvLists] tr').html(), null, "Delete Environment Tested");
					require(["editSettingsTest"], function(editSettingsTest){
						editSettingsTest.runTests();
					});
				}, 2500);
			});
		}
	};
});