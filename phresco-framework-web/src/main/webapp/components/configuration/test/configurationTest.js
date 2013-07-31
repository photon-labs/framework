
define(["configuration/configuration"], function(Configuration) {

	return { runTests: function () {
		
		module("configuration.js;Configuration");
		
		var configuration = new Configuration(), self=this, configList;
		asyncTest("Test - Configuration Page render", function() {
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=wordpress-WordPress",
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

				commonVariables.navListener.onMytabEvent("configuration");
			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find(".envlistname").text(), "Production", "Configuration Page render Tested");
					equal($(commonVariables.contentPlaceholder).find("#content_Env li").length, 1, "Add Configuration Page render Tested");
					self.validationTest(configuration);
				}, 1500);
		});
			
	},
	
	validationTest : function(configuration) {
		var self =this;
		asyncTest("Test - Add Environment validation Test", function() {
			$("input[name=envName]").val('');
			$("input[name=envDesc]").val('');
			$("input[name=addEnv]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("input[name='envName']").attr('placeholder'), "Enter Environment Name", "Add Environment validation Tested");
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
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=wordpress-WordPress",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments added Successfully","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":false,"name":"test","desc":"test","configurations":[]}]});
				}
			});
			
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=wordpress-WordPress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]}]});
				}
			});
			
			configuration.configurationlistener.saveEnvEvent("", function(res){
			});
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
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName=wordpress-WordPress&envName=Production",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/cloneEnvironment?appDirName=wordpress-WordPress&envName=test",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Clone Environment Done Successfully","exception":null,"data":{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}});
				}
			});
			
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=wordpress-WordPress",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":[{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[]},{"defaultEnv":true,"appliesTo":[""],"delete":true,"name":"test","desc":"test","configurations":[]},{"defaultEnv":false,"appliesTo":[""],"delete":true,"name":"sample","desc":"sample","configurations":[]}]});
				}
			});
			
			$("input[name=envrName]").val('sample');
			$("input[name=envrDesc]").val('sample');
			$("input[name=cloneEnvr]").click();
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".envlistname").text(),"ProductiontestProductiontestsample", "Clone Environment Tested");
				self.deleteEnvironment(configuration);
			}, 1500);
		});
	},
	
	deleteEnvironment : function(configuration) {
		var self =this;
		asyncTest("Test - Delete Environment Test", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=wordpress-WordPress&envName=Production",
				type:'DELETE',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=wordpress-WordPress&envName=test",
				type:'DELETE',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environment Deleted successfully","exception":null,"data":null});
				}
			});
			
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/deleteEnv?appDirName=wordpress-WordPress&envName=sample",
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
				self.envConfigurationRender(configuration);
			}, 2500);
		});
		
	},
	
	envConfigurationRender : function(configuration) {
		var self=this;
		$("#content").html('');
		asyncTest("Test - Non Environmnet Configuration Page render", function() {
			self.configList = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/allEnvironments?appDirName=wordpress-WordPress&isEnvSpecific=false&configType=SAP",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"message":"Configurations Listed","exception":null,"responseCode":null,"data":[{"envName":null,"name":"ttt","properties":{"smtp-host":"bb","auth-token-cache-ttl":"v","searchHosts":"bb","sapSvcPort":"bb","useProductionConfig":"bvbvb","sapSvcHost":"bv"},"type":"SAP","desc":"bb"}],"status":null});
				}
			});
			
			$("ul[name=configurationList] li").click();
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("div[envspecificval=false]").attr("configname"), "SAP", "Non Environmnet Configuration Page Tested");
				require(["editConfigurationTest"], function(editConfigurationTest){
					editConfigurationTest.runTests();
				});
			}, 1500);
		});
	}
	};
});