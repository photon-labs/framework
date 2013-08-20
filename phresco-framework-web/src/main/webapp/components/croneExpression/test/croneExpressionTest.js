define(["jquery", "croneExpression/croneExpression", "configuration/editConfiguration"], function($, croneExpression, EditConfiguration) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("croneExpression.js;croneExpression");
		var editConfiguration = new EditConfiguration();
		var croneExp = new croneExpression();
		var self = this;
		asyncTest("Test for popup render", function() {
		
			$.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId=photon&userId=admin&techId=tech-html5-jquery-mobile-widget",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":["Scheduler","Server","Database","Email","SAP","WebService","LDAP"]});
				}
			});
			
			self.editConfig = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"?appDirName=aap1&envName=Production",
				type:'GET',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Environments Listed","exception":null,"data":{"appliesTo":[""],"defaultEnv":true,"delete":false,"name":"Production","desc":"Production Environment is used for Development purpose only","configurations":[{"envName":"Production","name":"ww","properties":{"scheduler":"* * * * *"},"type":"Scheduler","desc":"2"}]}});
				}
			});
			
			commonVariables.api.localVal.setSession("appDirName" , "aap1");
			
			editConfiguration.configurationlistener.editConfiguration("Production");
			var config = $.mockjax({
					url: commonVariables.webserviceurl+commonVariables.configuration+"/settingsTemplate?appDirName=aap1&customerId=photon&userId=admin&type=Scheduler&techId=tech-html5-jquery-mobile-widget",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"response":null,"message":"confuguration Template Fetched successfully","exception":null,"data":{"downloadInfo":{},"settingsTemplate":{"appliesToTechs":[{"helpText":null,"system":false,"creationDate":1357659881000,"name":"Android Hybrid","id":"tech-android-hybrid","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659882000,"name":"Android Library","id":"tech-android-library","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659882000,"name":"Android Native","id":"tech-android-native","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659882000,"name":"ASP.NET","id":"tech-dotnet","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659883000,"name":"Blackberry Hybrid","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659883000,"name":"Drupal6","id":"tech-phpdru6","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659883000,"name":"Drupal7","id":"tech-phpdru7","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659884000,"name":"HTML5 JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659884000,"name":"HTML5 Multichannel JQuery Widget","id":"tech-html5-jquery-widget","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659884000,"name":"HTML5 Multichannel YUI Widget","id":"tech-html5","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659885000,"name":"HTML5 YUI Mobile Widget","id":"tech-html5-mobile-widget","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659885000,"name":"iPhone Hybrid","id":"tech-iphone-hybrid","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659885000,"name":"Iphone Library","id":"tech-iphone-library","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659886000,"name":"iPhone Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659886000,"name":"Iphone Workspace","id":"tech-iphone-workspace","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659886000,"name":"Java Standalone","id":"tech-java-standalone","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659887000,"name":"Java WebService","id":"tech-java-webservice","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659887000,"name":"Node JS Web Service","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659887000,"name":"PHP","id":"tech-php","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659888000,"name":"Sharepoint","id":"tech-sharepoint","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659888000,"name":"Site Core","id":"tech-sitecore","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659888000,"name":"Windows Metro","id":"tech-win-metro","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659889000,"name":"Windows Phone","id":"tech-win-phone","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1357659889000,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},{"helpText":null,"system":false,"creationDate":1349685730000,"name":"Metlife html5 jquery archetype","id":"3bfc67a1-588d-41f0-9b8a-2807317d5c70","displayName":null,"description":null,"status":null}],"customProp":false,"favourite":false,"possibleTypes":null,"envSpecific":true,"properties":[{"required":true,"possibleValues":[],"multiple":false,"propertyTemplates":null,"key":"scheduler","type":"Scheduler","defaultValue":null,"helpText":"","system":false,"creationDate":1357659889000,"name":"Cron Expression","id":"cabe362d-146c-46bd-afe6-f675cd4312e8","displayName":null,"description":null,"status":null}],"type":null,"displayName":null,"customerIds":["photon","05c80933-95d4-46c8-a58d-ceceb4bcce48"],"used":false,"helpText":null,"system":true,"creationDate":1357659881000,"name":"Scheduler","id":"087d817f-6b87-4a3b-ad3e-0d854d7ea1f6","description":"CI Configuration","status":null}}});
					}
					
				});	

			var con = $.mockjax({
				url:  commonVariables.webserviceurl+commonVariables.configuration+"/cronExpression",
				type:'POST',
				contentType: 'application/json',
				status: 200,
				response: function() {
					this.responseText = JSON.stringify({"response":null,"message":"Cron Expression calculated successfully","exception":null,"data":{"cronBy":null,"every":null,"week":null,"dates":null,"cronExpression":"","hours":null,"minutes":null,"month":null,"day":null}});
				}
			});
		
				setTimeout(function() {
					start();
					$('a[name=cron_expression]').click();	
					var temp = $(commonVariables.contentPlaceholder).find('#cron_expression').children().last().attr('id');
					equal(temp,"crone_triggered","Popup render tested successfully");	
					self.weekly(croneExp);
				}, 2000);
			});
	    },			
			
		weekly : function(croneExp) {
			var self = this;
			asyncTest("Test for Weekly schedule event",function() {
				croneExp.croneExpressionlistener.currentEvent('Weekly');
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#scheduleExpression').next('tr').attr('id');
					logs("weekly-val"+temp);
					equal(temp,"schedule_weekly","Weekly schedule event tested successfully");
					self.weeklyfailure(croneExp);
					self.monthly(croneExp);
				},2000);
			});
		},
		
		weeklyfailure : function(croneExp) {
			asyncTest("Test for Weekly schedule failure event",function() {
				croneExp.croneExpressionlistener.currentEvent('Weekly');
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#scheduleExpression').next('tr').attr('id');
					logs("weekly-val"+temp);
					notEqual(temp,"schedule_monthly","Weekly schedule event tested successfully");
				},1000);
			});
		},
		
		monthly : function(croneExp) {
			var self = this;
			asyncTest("Test for Monthly schedule event",function() {
				croneExp.croneExpressionlistener.currentEvent('Monthly');
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#scheduleExpression').next('tr').attr('id');
					logs("monthly-val"+temp);
					equal(temp,"schedule_monthly","Monthly schedule event tested successfully");
					self.monthlyfailure(croneExp);
					self.croneTriggered(croneExp);
				},1000);
			});
		},
		
		monthlyfailure : function(croneExp) {
			asyncTest("Test for Monthly schedule failure event",function() {
				croneExp.croneExpressionlistener.currentEvent('Weekly');
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#scheduleExpression').next('tr').attr('id');
					logs("monthly-val"+temp);
					notEqual(temp,"schedule_daily","Monthly schedule failure event tested successfully");
				},1000);
			});
		},
		
		croneTriggered : function(croneExp) {
			var self = this;
			asyncTest("Test for CroneTriggered popup event",function() {
				$("#cronepassword").click();
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#crone_triggered').css('display');
					logs("display-value"+temp);
					equal(temp,'block',"CroneTriggered popup event tested successfully");
					self.okclicked(croneExp);
				},2000);
			});
		},
		
		/* croneTriggeredfailure : function(croneExp) {
			var self = this;
			asyncTest("Test for CroneTriggered popup failure event",function() {
				$("#cronepassword").click();
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find('#crone_triggered').css('display');
					logs("display-value"+temp);
					notEqual(temp,'none',"CroneTriggered popup failure event tested successfully");
					self.okclicked(croneExp);
				},2000);
			});
		}, */
		
		okclicked : function(croneExp) {
			var self = this;
			asyncTest("Test for OK click event",function() {
				$("#CroneExpressionValue").val('2 1 * * *');
				$("input[name=croneOk]").click();
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find("input[name=scheduler]").val();
					logs("scheduler-val"+temp);
					equal(temp,"2 1 * * *","OK click event tested successfully");
					self.okclickedfailure(croneExp);
				},2000);
			});
		},
		
		okclickedfailure : function(croneExp) {
			asyncTest("Test for OK click failure event",function() {
				$("#CroneExpressionValue").val('2 1 * * *');
				$("input[name=croneOk]").click();
				setTimeout(function() {
					start();
					var temp = $(commonVariables.contentPlaceholder).find("input[name=scheduler]").val();
					notEqual(temp,"2 * * * *","OK click failure event tested successfully");
				},1000);
			});
		}
  };
  
});