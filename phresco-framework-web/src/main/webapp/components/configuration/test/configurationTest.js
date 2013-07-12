
define(["configuration/configuration"], function(Configuration) {

	return { runTests: function (configData) {
		
		module("configuration.js;Configuration");
		
		var configuration = new Configuration(), self=this;
		asyncTest("Test - Configuration Page render", function() {

			$.mockjax({
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
			}, 1500);
		});
		
	}
	
	};
});