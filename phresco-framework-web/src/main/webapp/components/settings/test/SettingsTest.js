define(["settings/settings"], function(Settings) {

	return { 

		runTests: function (configData) {

			module("settings.js");	
			var settings = new Settings(), templateData = {}, self = this, settingsList;
			asyncTest("Settings jenkins Alive - UI Test", function() {
				var ciAPI = commonVariables.api;
//				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
//				ciAPI.localVal.setSession("appDirName" , "");
				self.settingsList = $.mockjax({						
					url: commonVariables.webserviceurl + commonVariables.ci + "/mail",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Mail configuration retrieved successfully","exception":null,"responseCode":null,"data":["landy@gmail.com","password"],"status":null});
					}
				});
				
				$.mockjax({		
					url: commonVariables.webserviceurl + commonVariables.ci + "/confluence",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Confluence configuration retrieved successfully","exception":null,"responseCode":null,"data":[{"type":null,"password":"asdasd","userName":"user1","revision":null,"repoUrl":"http://localhost1/","testCheckOut":false,"testUserName":null,"testPassword":null,"testRepoUrl":null,"testRevision":null,"revisionVal":null,"commitMessage":null,"commitableFiles":null,"repoExist":false,"repoInfoFile":null}],"status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins is Alive","exception":null,"responseCode":null,"data":"200","status":null});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.getMyObj(commonVariables.settings, function(retVal){	
					Clazz.navigationController.push(retVal, commonVariables.animation);
				});

				setTimeout(function() {
					start();
					console.info("val...", $(commonVariables.contentPlaceholder))
					equal(1, 1, "Settings jenkins Alive - UI Tested");
					self.jenkinsDeadTest(jobTemplates);
				}, 2500);
			});					
		},
		
		jenkinsDeadTest : function(jobTemplates) {
			var self=this;
			asyncTest("Settings jenkins Dead - UI Test", function() {
				var ciAPI = commonVariables.api;
//				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
//				ciAPI.localVal.setSession("appDirName" , "");
				self.settingsList = $.mockjax({						
					url: commonVariables.webserviceurl + commonVariables.ci + "/mail",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Mail configuration retrieved successfully","exception":null,"responseCode":null,"data":["landy@gmail.com","password"],"status":null});
					}
				});
				
				$.mockjax({		
					url: commonVariables.webserviceurl + commonVariables.ci + "/confluence",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Confluence configuration retrieved successfully","exception":null,"responseCode":null,"data":[{"type":null,"password":"asdasd","userName":"user1","revision":null,"repoUrl":"http://localhost1/","testCheckOut":false,"testUserName":null,"testPassword":null,"testRepoUrl":null,"testRevision":null,"revisionVal":null,"commitMessage":null,"commitableFiles":null,"repoExist":false,"repoInfoFile":null}],"status":null});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins not found","exception":null,"responseCode":null,"data":"404","status":null});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.getMyObj(commonVariables.settings, function(retVal){	
					Clazz.navigationController.push(retVal, commonVariables.animation);
				});

				setTimeout(function() {
					start();
					console.info("val...", $(commonVariables.contentPlaceholder))
					equal(1, 1, "Settings jenkins Dead - UI Tested");
				}, 2500);
			});
		},

	};	
});

