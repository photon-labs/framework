define(["features/features"], function(Features) {
	return {
		runTests: function () {
			var self = this;
			module("Features.js;Features");
			commonVariables.techId = "tech-html5-jquery-mobile-widget";
				
			asyncTest("Test - Feature list Module service Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5-jquery-mobile-widget&type=FEATURE&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Application Features listed successfully","exception":null,"data":[{"licenseId":null,"artifactId":"mod_pagination_1.0","classifier":null,"packaging":"zip","groupId":"modules.tech-php.files","versions":[{"scope":null,"dependencyIds":null,"appliesTo":[{"required":false,"techId":"tech-php"}],"artifactGroupId":"19574e90-0a4e-482a-934a-8360abff768c","used":false,"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//modules/tech-php/files/mod_pagination_1.0/1.0/mod_pagination_1.0-1.0.zip","fileSize":0,"version":"1.0","creationDate":1348896224750,"helpText":null,"system":false,"name":"Pagination","id":"59bdbeeb-0007-4b0f-8538-b3768e3c077d","displayName":null,"description":null,"status":null}],"appliesTo":[{"techId":"tech-php","core":false}],"imageURL":null,"type":"FEATURE","customerIds":["photon"],"used":false,"creationDate":1371627764188,"helpText":null,"system":false,"name":"Pagination","id":"19574e90-0a4e-482a-934a-8360abff768c","displayName":"Pagination","description":null,"status":null}]});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5-jquery-mobile-widget&type=JAVASCRIPT&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
							this.responseText = JSON.stringify({"response":null,"message":"Application Features listed successfully","exception":null,"data":[{"artifactId":"jslib_jquery_ui","appliesTo":[{"techId":"tech-php","core":false},{"techId":"tech-android-hybrid","core":false},{"techId":"tech-iphone-native","core":false},{"techId":"tech-iphone-hybrid","core":false},{"techId":"tech-html5","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false}],"classifier":null,"packaging":"js","groupId":"jslibraries.files","versions":[{"scope":null,"dependencyIds":null,"appliesTo":[{"required":false,"techId":"tech-php"},{"required":false,"techId":"tech-android-hybrid"},{"required":false,"techId":"tech-iphone-native"},{"required":false,"techId":"tech-iphone-hybrid"}],"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//jslibraries/files/jslib_jquery_ui/1.8.16-alpha-1/jslib_jquery_ui-1.8.16-alpha-1.js","used":false,"artifactGroupId":"jslib_jquery_ui","fileSize":0,"version":"1.8.16-alpha-1","creationDate":1348931139109,"helpText":null,"system":false,"name":"jQuery_UI","id":"jslib_jquery_ui","displayName":null,"description":null,"status":null}],"licenseId":"3ae2a528-5f4c-4686-a6e7-e60968585537","imageURL":null,"type":"JAVASCRIPT","used":false,"customerIds":["photon"],"creationDate":1348931139203,"helpText":"jQuery UI is a JavaScript library that provides abstractions for low-level interaction and animation, advanced effects and high-level, themeable widgets.","system":true,"name":"jQuery_UI","id":"jslib_jquery_ui","displayName":"jQuery_UI","description":"jQuery UI is a JavaScript library that provides abstractions for low-level interaction and animation, advanced effects and high-level, themeable widgets.","status":null},{"artifactId":"jslib_xml2json","appliesTo":[{"techId":"tech-html5-jquery-mobile-widget","core":false},{"techId":"tech-html5-mobile-widget","core":false},{"techId":"tech-html5-jquery-widget","core":false},{"techId":"tech-html5-widget","core":false},{"techId":"tech-html5","core":false}],"classifier":null,"packaging":"zip","groupId":"jslibraries.files","versions":[{"scope":null,"dependencyIds":null,"appliesTo":[{"required":true,"techId":"tech-html5-jquery-mobile-widget"}],"downloadURL":"http://172.16.17.226:8080/repository/content/groups/public//jslibraries/files/jslib_xml2json/0.2.4/jslib_xml2json-0.2.4.zip","used":false,"artifactGroupId":"c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de","fileSize":0,"version":"0.2.4","creationDate":1355312572937,"helpText":null,"system":false,"name":"xml2json","id":"c4a8d772-305e-441a-993e-703e63795aac","displayName":null,"description":null,"status":null}],"licenseId":"3ae2a528-5f4c-4686-a6e7-e60968585537","imageURL":null,"type":"JAVASCRIPT","used":false,"customerIds":["photon"],"creationDate":1355312572937,"helpText":"A simple tool for taking XML data as an XML object or string and transforming it to a JSON","system":true,"name":"xml2json","id":"c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de","displayName":"xml2json","description":"A simple tool for taking XML data as an XML object or string and transforming it to a JSON","status":null}]});
						}
				}); 
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/list?customerId=photon&techId=tech-html5-jquery-mobile-widget&type=COMPONENT&userId=admin',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Application Features listed successfully","exception":null,"data":[]});
					}
				});
				
				(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});

				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "Pagination", "Feature list Module service Tested");
					self.FeatureModulelistfailureVerification();
				}, 2000);
			});
		},
		
		FeatureModulelistfailureVerification : function (){
			var self = this;
			asyncTest("Test - Feature javascript list Failure service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "Page", "Feature list Module failure service Tested");
					self.FeatureJavascriptlistVerification();
				}, 2000);
			});
		},
		
		
		FeatureJavascriptlistVerification : function (){
			var self = this;
			asyncTest("Test - Feature javascript list service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery_UI", "Feature javascript list service Tested");
					self.FeatureJavascriptlistfailureVerification();
				}, 2000);
			});
		},
		
		FeatureJavascriptlistfailureVerification : function (){
			var self = this;
			asyncTest("Test - Feature javascript list Failure service Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");				
				setTimeout(function() {
					start();
					notEqual($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery", "Feature javascript list Failure service Tested");
					self.FeatureModuleSearchVerification();
				}, 2000);
			});
		},
		FeatureModuleSearchVerification : function (){
			var self = this;
			asyncTest("Test - Feature Module Search Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#module').val("jQuery_UI");
				$('#module').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = FEATURE]").attr("name"), "Pagination", "Feature Module Search Verification Tested");
					self.FeatureModuleSearchFailurVerification();
				}, 2000);
			});
		},
		FeatureModuleSearchFailurVerification : function (){
			var self = this;
			asyncTest("Test - Feature Module Search failure Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#module').val("aaaaaa");
				$('#module').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#norecord1").text(), "No records", "Feature Module Search Failure Verification Tested");
					self.FeatureJavascriptSearchVerification();
				}, 2000);
			});
		},
		FeatureJavascriptSearchVerification : function (){
			var self = this;
			asyncTest("Test - Feature Javascript Libraries Search Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#jsibraries').val("jQuery_UI");
				$('#jsibraries').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("li[type = JAVASCRIPT]").attr("name"), "jQuery_UI", "Javascript Libraries Search Verification Tested");
					self.FeatureModuleJavascriptFailurVerification();
				}, 2000);
			});
		},
		FeatureModuleJavascriptFailurVerification : function (){
			var self = this;
			asyncTest("Test - Javascript Libraries Search failure Verification Tested", function() {
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#jsibraries').val("aaaaaaffd");
				$('#jsibraries').bind("keyup");			
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#norecord2").text(), "No records", " Javascript Libraries Search Failure Verification Tested");
					self.FeatureModuleDescriptionVerification();
				}, 2000);
			});
		},
		FeatureModuleDescriptionVerification : function (){
			var self = this;
			asyncTest("Test - Module Description Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?&artifactGroupId=c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "JDOM is a way to represent an XML document for easy and efficient reading, manipulation, and writing."});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?&artifactGroupId=19574e90-0a4e-482a-934a-8360abff768c&userId=admin',					
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Application Features listed successfully","exception": null,"data": "As your database grows, showing all the results of a query on a single page is no longer practical. This is where pagination comes in handy. You can display your results over a number of pages, each linked to the next, to allow your users to browse your content in bite sized pieces."});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext+'/desc?&artifactGroupId=jslib_jquery_ui&userId=admin',					
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
					equal(desc, "JDOM is a way to represent an XML document for easy and efficient reading, manipulation, and writing.", " Module Description Service Verification Tested");
					self.FeatureUpdateVerification();
				}, 2000);
			});
		},
		FeatureUpdateVerification : function (){
			var self = this;
			asyncTest("Test - Module Update Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.projectlistContext + "/updateFeature?customerId=photon&userId=admin&appDirName=wordpress-WordPress",
					type:'PUT',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Update Features listed successfully","exception": null,"data": null});
					}
				});
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?&userId=admin&appDirName=wordpress-WordPress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Update Features listed successfully","exception": null,"data": null});
					}
				});
				//var featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
				commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");
				
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#featureUpdate').click();		
				setTimeout(function() { 
					start();
					//var successmsg = $(".popsuccess").text();
					var selectoption = $(commonVariables.contentPlaceholder).find("#feature_c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de").attr("class");
					equal(selectoption, "switch switchOn", " Select Service Verification Tested");
					self.FeatureSelectVerification();
				}, 2000);
			});
		},
		
		FeatureSelectVerification : function (){
			var self = this;
			asyncTest("Test - Select Service Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?&userId=admin&appDirName=wordpress-WordPress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Features updated successfully","exception":null,"data":null});
					}
				});
				//var featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
				commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");				
				commonVariables.navListener.onMytabEvent("featurelist");
				$('#featureUpdate').click();		
				setTimeout(function() { 
					start();					
					var selectoption = $(commonVariables.contentPlaceholder).find("#feature_c2c74b7d-a6b6-4e61-b0e0-a44db1dfb3de").attr("class");
					equal(selectoption, "switch switchOn", " Select Service Verification Tested");
					self.FeatureSelectFailureVerification();
				}, 2000);
			});
		},
		FeatureSelectFailureVerification : function (){
			var self = this;
			asyncTest("Test - UnSelect Service option Verification Tested", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.featurePageContext + "/selectedFeature?&userId=admin&appDirName=wordpress-WordPress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response": null,"message": "Update Features listed successfully","exception": null,"data": null});
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
					/* require(["codequalityTest"], function(codequalityTest){
						codequalityTest.runTests();
					}); */
				}, 2000);
			});
		}
	};
});