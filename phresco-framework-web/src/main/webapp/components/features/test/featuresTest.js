define(["features/features"], function(Features) {
	return {
	/* setuserInfo : function(){  
	commonVariables.api.localVal.setProjectInfo({"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"appInfos":[{"pomFile":null,"modules":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"version":"3.4.2","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"php-Wordpress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":[],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"version":"1.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"e5fe85d7-f01b-4d3d-8a58-3c3ae75cc0d2","displayName":null,"description":null,"status":null}],"projectCode":"test","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1379325938000,"helpText":null,"system":false,"name":"test","id":"a20e25b3-61be-499f-8108-ae170a94b4c0","displayName":null,"description":"","status":null}},"status":"success"});	
	}, */
		
		runTests: function () {
			var self = this;
			var feature = new Features();
			module("Features.js;Features");
			commonVariables.techId = "tech-html5-jquery-mobile-widget";
			asyncTest("Test - Feature list Module service Tested", function() {
			$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5&type=FEATURE&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Application Features listed successfully","exception":null,"responseCode":null,"data":[{"artifactId":"jdom","classifier":null,"packaging":"jar","groupId":"jdom","versions":[{"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//jdom/jdom/1.0/jdom-1.0.jar","scope":null,"appliesTo":[{"required":false,"techId":"tech-java-webservice"}],"artifactGroupId":"31ec87e7-d8f2-43fe-aec2-ac24af57b3aa","dependencyIds":["b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","ad9508da-b484-427d-a7f9-16cc0a0b0494","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1"],"used":false,"fileSize":0,"version":"1.0","helpText":null,"system":false,"creationDate":1348896243312,"name":"jdom","id":"e75b7088-8d9f-4b00-8077-9e76b5c478fe","displayName":null,"description":null,"status":null}],"appliesTo":[{"techId":"tech-java-standalone","core":false},{"techId":"tech-java-webservice","core":false},{"techId":"tech-html5","core":false},{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-widget","core":false}],"imageURL":null,"licenseId":null,"type":"FEATURE","used":false,"customerIds":["photon"],"helpText":null,"system":false,"creationDate":1374762921526,"name":"jdom","id":"31ec87e7-d8f2-43fe-aec2-ac24af57b3aa","displayName":"jdom","description":null,"status":null},{"artifactId":"spring-xml","classifier":null,"packaging":"jar","groupId":"org.springframework.ws ","versions":[{"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//org/springframework/ws /spring-xml/1.5.9/spring-xml-1.5.9.jar","scope":null,"appliesTo":[{"required":false,"techId":"tech-java-webservice"}],"artifactGroupId":"7077da20-a2cc-41bc-b822-84c66ee1fe4e","dependencyIds":null,"used":false,"fileSize":0,"version":"1.5.9","helpText":null,"system":false,"creationDate":1348896243296,"name":"Spring-xml","id":"b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","displayName":null,"description":null,"status":null}],"appliesTo":[{"techId":"tech-java-standalone","core":false},{"techId":"tech-java-webservice","core":false},{"techId":"tech-html5","core":false},{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-widget","core":false}],"imageURL":null,"licenseId":null,"type":"FEATURE","used":false,"customerIds":["photon"],"helpText":null,"system":false,"creationDate":1374762921526,"name":"Spring-xml","id":"7077da20-a2cc-41bc-b822-84c66ee1fe4e","displayName":"Spring-xml","description":null,"status":null}]});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5&type=JAVASCRIPT&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
							this.responseText = JSON.stringify({"response":null,"message":"Application Features listed successfully","exception":null,"data":[{"artifactId":"jslib_jquery_ui","appliesTo":[{"techId":"tech-php","core":false},{"techId":"tech-android-hybrid","core":false},{"techId":"tech-iphone-native","core":false},{"techId":"tech-iphone-hybrid","core":false},{"techId":"tech-html5","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false}],"classifier":null,"packaging":"js","groupId":"jslibraries.files","versions":[{"scope":null,"dependencyIds":null,"appliesTo":[{"required":false,"techId":"tech-php"},{"required":false,"techId":"tech-android-hybrid"},{"required":false,"techId":"tech-iphone-native"},{"required":false,"techId":"tech-iphone-hybrid"}],"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//jslibraries/files/jslib_jquery_ui/1.8.16-alpha-1/jslib_jquery_ui-1.8.16-alpha-1.js","used":false,"artifactGroupId":"jslib_jquery_ui","fileSize":0,"version":"1.8.16-alpha-1","creationDate":1348931139109,"helpText":null,"system":false,"name":"jQuery_UI","id":"jslib_jquery_ui","displayName":null,"description":null,"status":null}],"licenseId":"3ae2a528-5f4c-4686-a6e7-e60968585537","imageURL":null,"type":"JAVASCRIPT","used":false,"customerIds":["photon"],"creationDate":1348931139203,"helpText":"jQuery UI is a JavaScript library that provides abstractions for low-level interaction and animation, advanced effects and high-level, themeable widgets.","system":true,"name":"jQuery_UI","id":"jslib_jquery_ui","displayName":"jQuery_UI","description":"jQuery UI is a JavaScript library that provides abstractions for low-level interaction and animation, advanced effects and high-level, themeable widgets.","status":null},{"artifactId":"jslib_xml2json","appliesTo":[{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-widget","core":false},{"techId":"tech-html5","core":false}],"classifier":null,"packaging":"zip","groupId":"jslibraries.files","versions":[{"scope":null,"dependencyIds":null,"appliesTo":[{"required":false,"techId":"tech-html5-jquery-mobile-widget"}],"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//jslibraries/files/jslib_xml2json/0.2.4/jslib_xml2json-0.2.4.zip","used":false,"artifactGroupId":"c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de","fileSize":0,"version":"0.2.4","creationDate":1355312572937,"helpText":null,"system":false,"name":"xml2json","id":"c4a8d772-305e-441a-993e-703e63795aac","displayName":null,"description":null,"status":null}],"licenseId":"3ae2a528-5f4c-4686-a6e7-e60968585537","imageURL":null,"type":"JAVASCRIPT","used":false,"customerIds":["photon"],"creationDate":1355312572937,"helpText":"A simple tool for taking XML data as an XML object or string and transforming it to a JSON","system":true,"name":"xml2json","id":"c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de","displayName":"xml2json","description":"A simple tool for taking XML data as an XML object or string and transforming it to a JSON","status":null}]});
						}
				}); 
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5&type=COMPONENT&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Application Features listed successfully","exception":null,"data":[]});
					}
				});
				
				//self.setuserInfo();
				(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.navListener.onMytabEvent("featurelist");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "jdom", "Feature list Module service Tested");
					self.FeatureModulelistfailureVerification(feature);
				}, 2000);
			});
		},
		
		FeatureModulelistfailureVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature javascript list Failure service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "Page", "Feature list Module failure service Tested");
					self.FeatureJavascriptlistVerification(feature);
				}, 2000);
			});
		},
		
		
		FeatureJavascriptlistVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature javascript list service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery_UI", "Feature javascript list service Tested");
					self.FeatureJavascriptlistfailureVerification(feature);
				}, 2000);
			});
		},
		
		FeatureJavascriptlistfailureVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature javascript list Failure service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery", "Feature javascript list Failure service Tested");
					self.FeatureModuleSearchVerification(feature);
				}, 2000);
			});
		},
		FeatureModuleSearchVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature Module Search Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#module').val("jQuery_UI");
				$('#module').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "jdom", "Feature Module Search Verification Tested");
					self.FeatureSearch(feature);
				}, 2000);
			});
		},
		FeatureSearch: function (feature){
			var self = this;
			asyncTest("Test - Feature Module Search function Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				feature.featuresListener.search("jQuery_UI", "module");
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "jdom", "Feature Module Search function Tested");
					self.FeatureSelected(feature);
				}, 2000);
			});
		},
		
		FeatureSelected: function (feature){
			var self = this;
			asyncTest("Test - Feature Module Selected function Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				feature.featuresListener.showSelected("");
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "jdom", "Feature Module Selected function Tested");
					self.FeatureModuleSearchFailurVerification(feature);
				}, 2000);
			});
		},
		
		
		FeatureModuleSearchFailurVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature Module Search failure Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#module').val("aaaaaa");
				$('#module').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#norecord1").text(), "No records", "Feature Module Search Failure Verification Tested");
					self.FeatureJavascriptSearchVerification(feature);
				}, 2000);
			});
		},
		FeatureJavascriptSearchVerification : function (feature){
			var self = this;
			asyncTest("Test - Feature Javascript Libraries Search Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#jsibraries').val("jQuery_UI");
				$('#jsibraries').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery_UI", "Javascript Libraries Search Verification Tested");
					self.FeatureModuleJavascriptFailurVerification(feature);
				}, 2000);
			});
		},
		FeatureModuleJavascriptFailurVerification : function (feature){
			var self = this;
			asyncTest("Test - Javascript Libraries Search failure Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#jsibraries').val("aaaaaaffd");
				$('#jsibraries').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#norecord2").text(), "No records", " Javascript Libraries Search Failure Verification Tested");
					self.FeatureModuleDescriptionVerification(feature);
				}, 2000);
			});
		},
		FeatureModuleDescriptionVerification : function (feature){
			var self = this;
			asyncTest("Test - Module Description Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?artifactGroupId=31ec87e7-d8f2-43fe-aec2-ac24af57b3aa&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "JDOM is a way to represent an XML document for easy and efficient reading, manipulation, and writing."});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?artifactGroupId=7077da20-a2cc-41bc-b822-84c66ee1fe4e&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "As your database grows, showing all the results of a query on a single page is no longer practical. This is where pagination comes in handy. You can display your results over a number of pages, each linked to the next, to allow your users to browse your content in bite sized pieces."});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?artifactGroupId=c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "As your database grows, showing all the results of a query on a single page is no longer practical. This is where pagination comes in handy. You can display your results over a number of pages, each linked to the next, to allow your users to browse your content in bite sized pieces."});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?artifactGroupId=jslib_jquery_ui&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "jQuery UI is a JavaScript library that provides abstractions for low-level interaction and animation, advanced effects and high-level, themeable widgets."});
					}
				});
				commonVariables.navListener.onMytabEvent("featurelist");
				$('.featureinfo_img').click();						
				setTimeout(function() { 
					start();					
					var desc = $(commonVariables.contentPlaceholder).find(".features_desc_content").text();
					equal(desc, "As your database grows, showing all the results of a query on a single page is no longer practical. This is where pagination comes in handy. You can display your results over a number of pages, each linked to the next, to allow your users to browse your content in bite sized pieces.", " Module Description Service Verification Tested");
					self.FeatureUpdateVerification(feature);
				}, 2000);
			});
		},
		FeatureUpdateVerification : function (feature){
			var self = this;
			asyncTest("Test - Module Update Service Verification Tested", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=featureUpdate&appId=2159a829-9316-4351-ada1-0098bc6712a1",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});

				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.projectlistContext + "/updateFeature?customerId=photon&userId=admin&appDirName=html5webyui&displayName=Admin",
					type:'PUT',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Update Features listed successfully","exception": null,"data": null});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?userId=admin&appDirName=html5webyui",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400004","data":[{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","moduleId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","versionID":"63e031f4-808f-4a5a-9354-f9aedc70bd0b","dispName":"options","dispValue":"7.0","canConfigure":false,"name":"options","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"2746d26d-9c25-42f3-97af-bc353235f499","moduleId":"2746d26d-9c25-42f3-97af-bc353235f499","versionID":"2e35e54c-894c-4a8f-adfa-adc92671648b","dispName":"field_sql_storage","dispValue":"7.0","canConfigure":false,"name":"field_sql_storage","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"82abf692-8389-4d26-8f0f-fa4c80050217","moduleId":"82abf692-8389-4d26-8f0f-fa4c80050217","versionID":"9a54f56b-5161-490f-8985-514f7946632a","dispName":"field","dispValue":"7.0","canConfigure":false,"name":"field","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","moduleId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","versionID":"ea7cb09e-6dc1-49e1-b5ee-d148362dec11","dispName":"file","dispValue":"7.0","canConfigure":false,"name":"file","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","moduleId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","versionID":"55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","dispName":"text","dispValue":"7.0","canConfigure":false,"name":"text","type":"FEATURE"}],"status":"success"});
					}
				});
				//var featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
				commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");
				$(".headerAppId").attr("value","2159a829-9316-4351-ada1-0098bc6712a1");	
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#featureUpdate').click();		
				setTimeout(function() { 
					start();
					//var successmsg = $(".popsuccess").text();
					var selectoption = $(commonVariables.contentPlaceholder).find("#feature_c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de").attr("class");
					equal(selectoption, "switch switchOff", " Select Service Verification Tested");
					self.FeatureSelectVerification(feature);
				}, 2000);
			});
		},
		
		FeatureSelectVerification : function (feature){
			var self = this;
			asyncTest("Test - Select Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?userId=admin&appDirName=html5webyui",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400004","data":[{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","moduleId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","versionID":"63e031f4-808f-4a5a-9354-f9aedc70bd0b","dispName":"options","dispValue":"7.0","canConfigure":false,"name":"options","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"2746d26d-9c25-42f3-97af-bc353235f499","moduleId":"2746d26d-9c25-42f3-97af-bc353235f499","versionID":"2e35e54c-894c-4a8f-adfa-adc92671648b","dispName":"field_sql_storage","dispValue":"7.0","canConfigure":false,"name":"field_sql_storage","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"82abf692-8389-4d26-8f0f-fa4c80050217","moduleId":"82abf692-8389-4d26-8f0f-fa4c80050217","versionID":"9a54f56b-5161-490f-8985-514f7946632a","dispName":"field","dispValue":"7.0","canConfigure":false,"name":"field","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","moduleId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","versionID":"ea7cb09e-6dc1-49e1-b5ee-d148362dec11","dispName":"file","dispValue":"7.0","canConfigure":false,"name":"file","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","moduleId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","versionID":"55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","dispName":"text","dispValue":"7.0","canConfigure":false,"name":"text","type":"FEATURE"}],"status":"success"});
					}
				});
				//var featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
				commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");				
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#featureUpdate').click();		
				setTimeout(function() { 
					start();					
					var selectoption = $(commonVariables.contentPlaceholder).find("#feature_c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de").attr("class");
					equal(selectoption, "switch switchOff", " Select Service Verification Tested");
					self.FeatureSelectFailureVerification(feature);
				}, 2000);
			});
		},
		FeatureSelectFailureVerification : function (feature){
			var self = this;
			asyncTest("Test - UnSelect Service option Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?userId=admin&appDirName=html5webyui",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400004","data":[{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","moduleId":"9cd750b0-fcfb-4210-829c-a03cd970fca8","versionID":"63e031f4-808f-4a5a-9354-f9aedc70bd0b","dispName":"options","dispValue":"7.0","canConfigure":false,"name":"options","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"2746d26d-9c25-42f3-97af-bc353235f499","moduleId":"2746d26d-9c25-42f3-97af-bc353235f499","versionID":"2e35e54c-894c-4a8f-adfa-adc92671648b","dispName":"field_sql_storage","dispValue":"7.0","canConfigure":false,"name":"field_sql_storage","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"82abf692-8389-4d26-8f0f-fa4c80050217","moduleId":"82abf692-8389-4d26-8f0f-fa4c80050217","versionID":"9a54f56b-5161-490f-8985-514f7946632a","dispName":"field","dispValue":"7.0","canConfigure":false,"name":"field","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","moduleId":"836a956f-af5e-48d9-8cff-d1bd5f2a1e68","versionID":"ea7cb09e-6dc1-49e1-b5ee-d148362dec11","dispName":"file","dispValue":"7.0","canConfigure":false,"name":"file","type":"FEATURE"},{"scope":null,"packaging":"zip","defaultModule":true,"artifactGroupId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","moduleId":"bb88894f-2c31-4f09-9054-ea84a809e6fe","versionID":"55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","dispName":"text","dispValue":"7.0","canConfigure":false,"name":"text","type":"FEATURE"}],"status":"success"});
					}
				});
				//var featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
				commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");				
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#featureUpdate').click();		
				setTimeout(function() { 
					start();	
					var unselectoption = $(commonVariables.contentPlaceholder).find("#feature_jslib_jquery_ui").attr("class");
					equal(unselectoption, "switch switchOff", "UnSelect Service option Verification Tested");
					self.settingsiconclickfunction(feature);					
				}, 2000);
			});
		},
		
		settingsiconclickfunction : function (feature){
			var self = this;
			asyncTest("Test - Settings Icon Click", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/populate?userId=admin&customerId=photon&featureName=jdom&appDirName=html5webyui",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400005","data":{"properties":{"DashBoard.titleLabel":"[Browse, SpecialOffer, Login, Register]"},"propertyTemplates":[{"key":"DashBoard.titleLabel","type":null,"defaultValue":null,"required":false,"possibleValues":null,"appliesTo":null,"propertyTemplates":null,"multiple":false,"settingsTemplateId":null,"name":"DashBoard titleLabel","id":"8d411d26-82e2-4676-b8d4-2fe0e08399a9","displayName":null,"status":null,"description":null,"creationDate":1378795060421,"helpText":null,"system":false}],"hasCustomProperty":true},"status":"success"});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/configureFeature?userId=admin&customerId=photon&featureName=jdom&appDirName=html5webyui&DashBoard.titleLabel=%5BBrowse%2C",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message": null,"exception": null,"responseCode": "PHR400006","data": null,"status": "success"});
					}
				});
				commonVariables.navListener.onMytabEvent("featurelist");
				$('.settings_icon').attr('settingsid','s_31ec87e7-d8f2-43fe-aec2-ac24af57b3aa');
				$('.settings_icon').parent().attr('name','jdom');
				$('.settings_icon').click();
				setTimeout(function() { 
					start();
					$("#configure_settings").click();
					var labelval = $("#settingspopup").find('li:eq(0)').find('label').text();
					equal("DashBoard.titleLabel", labelval, "Settings Icon Click Tested");
					self.settingsfailure(feature);	
				}, 2000);
			});
		},
		
		settingsfailure : function (feature){
			var self = this;
			asyncTest("Test - Settings Icon Failure", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/populate?userId=admin&customerId=photon&featureName=xml2json&appDirName=html5webyui",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400005","data":{"propertyTemplates":[],"hasCustomProperty":false,"properties":null},"status":"success"});
					}
				});
				commonVariables.navListener.onMytabEvent("featurelist");
				$('.settings_icon').attr('settingsid','s_c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de');
				$('.settings_icon').parent().attr('name','xml2json');
				$('.settings_icon').click();
				setTimeout(function() { 
					start();
					equal("", "", "Settings Icon Failure Tested");
					self.FeaturedepententVerification(feature);	
				}, 2000);
			});
		},
		
		FeaturedepententVerification : function (feature){
			var self = this;
			var descid = "e75b7088-8d9f-4b00-8077-9e76b5c478fe";
			asyncTest("Test - Defentent Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependencyFeature?userId=admin&versionId=e75b7088-8d9f-4b00-8077-9e76b5c478fe",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message": " Dependency Features listed successfully","exception": null,"responseCode": null,"data": {"e75b7088-8d9f-4b00-8077-9e76b5c478fe": ["b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","7077da20-a2cc-41bc-b822-84c66ee1fe4e"]},"status": "success"});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependencyFeature?userId=admin&versionId=b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR410004","data":null,"status":"failure"});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependentToFeatures?userId=admin&versionId=e75b7088-8d9f-4b00-8077-9e76b5c478fe&techId=tech-html5",
					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400007","data":[],"status":"success"});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/dependentToFeatures?userId=admin&versionId=b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1&techId=tech-html5",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR400007","data":["e75b7088-8d9f-4b00-8077-9e76b5c478fe"],"status":"success"});
					}
				});
				commonVariables.navListener.onMytabEvent("featurelist");
				//$("label[name=on_off]").click();
				feature.featuresListener.defendentmodule("e75b7088-8d9f-4b00-8077-9e76b5c478fe", "true");
				feature.featuresListener.defendentmodule("e75b7088-8d9f-4b00-8077-9e76b5c478fe", "false");
				feature.featuresListener.defendentmodule("b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1", "false");
				setTimeout(function() { 
					start();
					//var selectoption = $(commonVariables.contentPlaceholder).find("#feature_7077da20-a2cc-41bc-b822-84c66ee1fe4e").attr("class"); 
					equal("", "", "Defentent Service Verification Tested");
					self.clickfunctions(feature);	
				}, 2000);
			});
		},
		
		clickfunctions : function (feature){
			var self = this;
			var descid = "e75b7088-8d9f-4b00-8077-9e76b5c478fe";
			asyncTest("Test - Click functions", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				feature.loadPage();
				$("label[name=on_off]").attr('id','abc');
				$("label[name=on_off]").attr('value','false');
				$("label[name=on_off]").click();
				$("label[name=on_off]").attr('id','def');
				$("label[name=on_off]").attr('value','true');
				$("label[name=on_off]").click();
				$("input[name=on_off]").val('on');
				$("input[name=on_off]").click();
				$("#search").attr('class','switch switchOff');
				$('#module').val('a');
				$('#module').keyup();
				$('#jsibraries').val('a');
				$('#jsibraries').keyup();
				$('#components').val('a');
				$('#components').keyup();
				$('#module').val('');
				$('#module').keyup();
				$("#search").attr('class','switch switchOn default');
				$('#module').val('w');
				$('#module').keyup();
				$('.cleartext').attr('name','module');
				$('.cleartext').click();
				$('.cleartext').attr('name','jsibraries');
				$('.cleartext').click();
				$('.cleartext').attr('name','components');
				$('.cleartext').click();
				$('#cancelUpdateFeature').click();
				setTimeout(function() { 
					start();
					equal('', '', "Click functions Tested");
					require(["unitTestTest"], function(unitTestTest){
						unitTestTest.runTests();
					});
				}, 2000);
			});
		}
	};
});