
define(["configuration/configuration"], function(Configuration) {

	return { runTests: function (configData) {
		
		module("configuration.js;Configuration");
		
		var configuration = new Configuration(), self=this, configList;
		asyncTest("Test - Configuration Page render", function() {

			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=aap1",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]}]});
				}
			});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});

				var configurationAPI = new Clazz.com.components.configuration.js.api.ConfigurationAPI();
				configurationAPI.localVal.setSession("appDirName" , "aap1");

				commonVariables.navListener.onMytabEvent("configuration");
			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find(".envlistname").text(), "Production", "Configuration Page render Tested");
					equal($(commonVariables.contentPlaceholder).find("#content_Env li").length, 1, "Add Configuration Page render Tested");
					self.addEnvironment(configuration);
				}, 1500);
		});
			
	},
	
	addEnvironment : function(configuration) {
		var self =this;
		asyncTest("Test - Add Environment Test", function() {
			$("input[name=envName]").val('test');
			$("input[name=envDesc]").val('test');
			$("input[name=addEnv]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#content_Env li").length, 2, "Add Environment Tested");
				self.saveEnvironment(configuration);
			}, 1500);
		});
		
	},
	
	saveEnvironment : function(configuration) {
		var self =this;
		asyncTest("Test - Save Environment Test", function() {
			$.mockjaxClear(self.configList);
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=aap1",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments added Successfully","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":false,"name":"test","desc":"test","configurations":[]}]});
				}
			});
			
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=aap1",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]}]});
				}
			});
			
			require(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			});

			var configurationAPI = new Clazz.com.components.configuration.js.api.ConfigurationAPI();
			configurationAPI.localVal.setSession("appDirName" , "aap1");

			commonVariables.navListener.onMytabEvent("configuration");
			
			$("input[name=saveEnvironment]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".envlistname").text(),"Productiontest", "Save Environment Tested");
				self.cloneEnvironment(configuration);
			}, 1500);
		});
	},
	
	cloneEnvironment : function(configuration) {
		var self=this;
		asyncTest("Test - Clone Environment Test", function() {
			$.mockjaxClear(self.configList);
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName=aap1&envName=Production",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName=aap1&envName=test",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
				}
			});
			
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=aap1",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}]});
				}
			});
			
			require(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			});

			var configurationAPI = new Clazz.com.components.configuration.js.api.ConfigurationAPI();
			configurationAPI.localVal.setSession("appDirName" , "aap1");

			commonVariables.navListener.onMytabEvent("configuration");
			
			$("input[name=envrName]").val('sample');
			$("input[name=envrDesc]").val('sample');
			$("input[name=cloneEnvr]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".envlistname").text(),"Productiontestsample", "Clone Environment Tested");
				self.deleteEnvironment(configuration);
			}, 1500);
		});
	},
	
	deleteEnvironment : function(configuration) {
		var self =this;
		asyncTest("Test - Delete Environment Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=aap1&envName=Production",
				type:'DELETE',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=aap1&envName=test",
				type:'DELETE',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=aap1&envName=sample",
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
			}, 2500);
		});
		
	}
	
	};
});