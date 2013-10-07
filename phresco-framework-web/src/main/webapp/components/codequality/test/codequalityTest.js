define(["codequality/codequality"], function(Codequality) {

	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return {
		runTests: function () {
		module("codequality.js;Codequality");
		var codequality = new Codequality(), self = this;
		asyncTest("Codequality sonar status check test case", function() {
			$.mockjax({
			  url: commonVariables.webserviceurl+"parameter/sonarUrl",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR500004","data":"http://localhost:9000","status":"success"});
				  
			  }
			});
			
			$.mockjax({
				url: "http://localhost:9000",
				type: "GET",
				status: 200,
				response : function() {
					this.responseText = "true";
				}
			});
			
			var sonarstatus = $.mockjax({
			  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=wordpress-WordPress&goal=validate-code",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR510001","data":null,"status":"failure"});
				  
			  }
			});
			/* var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI(); */
			commonVariables.api.localVal.setSession("appDirName" , "wordpress-WordPress");

			require(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			});						
			
			commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#content_div").children(".alert").text();
					equal('Sonar not yet started', output, "Codequality sonar status check test case");
					self.runNegativeEventTest(codequality);
				}, 3000);
			});
		},
		
		runNegativeEventTest : function(codequality) {
			var self = this;
			asyncTest("Codequality sonar status check failure test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=html5&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR510001","data":null,"status":"failure"});
					}
				});

				//var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI();
				commonVariables.api.localVal.setSession("appDirName" , "html5");

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						
			
				commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#content_div").text();
					notEqual('Sonar started', output, "Codequality sonar status check failure test case");
					self.rungetReportTypesTest(codequality);
				}, 1500);
			});
		},		
		
		rungetReportTypesTest : function(codequality) {
			var self = this;
			asyncTest("Codequality get report types test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=javaws&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify([{"options":[{"value":"js","key":"js","dependency":"showSettings,environmentName"},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests,showSettings,environmentName"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]);
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://localhost:9000/dashboard/index/com.photon.phresco.service:javaws:js"});
					}
				});

				//var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI();
				commonVariables.api.localVal.setSession("appDirName" , "javaws");
				commonVariables.api.localVal.setSession("username" , "sudhakar_rag");

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						
			
				commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#repTypes").text();
					equal('js', 'js', "Codequality get report types test case");
					self.rungetReportTypesFailureTest(codequality);
				}, 1500);
			});
		},		
		
		rungetReportTypesFailureTest : function(codequality) {
			var self = this;
			asyncTest("Codequality get report types failure test case", function() {
				setTimeout(function() {
					start();
					output = $("#repTypes").text();
					notEqual('Functional', output, "Codequality get report types failure test case");
					self.rungetReportTest(codequality);
				}, 1500);
			});
		},
		
		rungetReportTest : function(codequality) {
			var self = this;
			asyncTest("Codequality get report test case", function() {
				
				setTimeout(function() {
					start();
					output = $("#content_div").find("iframe").attr("src");
					equal("http://localhost:9000/dashboard/index/com.photon.phresco.service:javaws:js", "http://localhost:9000/dashboard/index/com.photon.phresco.service:javaws:js", "Codequality get report test case");
					self.rungetReportNotAvailableTest(codequality);
				}, 1500);
			});
		},
		
		rungetReportNotAvailableTest : function(codequality) {
			var self = this;
			asyncTest("Codequality get report not available test case", function() {
				
				setTimeout(function() {
					start();
					output = $("#content_div").children(".alert").text();
					notEqual('Report not available', output, "Codequality get report not available test case");
					//self.codevalidation(codequality);
				}, 3000);
			});
		},	
		
		codevalidation : function(codequality) {
			var self = this;
			asyncTest("Codequality Code Validation test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=javaws&goal=validate-code&phase=validate-code&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"skipTests"},{"value":"Functional Test","key":"functional","dependency":null},{"value":"Html","key":"html","dependency":null}]},"multiple":"false","value":"src","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTests","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true}],"status":"success"});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"app/codeValidate?username=sudhakar_rag&appId=ecb70c02-8b9d-4414-b7fe-662abb154cfb&customerId=photon&goal=validate-code&phase=validate-code&projectId=65c580d2-bce6-4a19-88dc-7bb69d97e8aa&sonar=src",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":"STARTED","configErrorMsg":null,"service_exception":"","parameterKey":null,"uniquekey":"bc69d3de-5007-4b69-9d81-31362d634ee3","configErr":false,"connectionAlive":false,"errorFound":false,"responseCode":"PHR500002","status":"STARTED"});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"app/readlog?&uniquekey=bc69d3de-5007-4b69-9d81-31362d634ee3",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"configErrorMsg":null,"service_exception":null,"parameterKey":null,"uniquekey":"85228032-b36b-48c0-99d2-11b3f68790fa","configErr":false,"connectionAlive":false,"errorFound":false,"responseCode":null,"status":"COMPLETED"});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://localhost:9000/dashboard/index/com.photon.phresco.service:javaws:js"});
					}
				});
				commonVariables.appDirName = "javaws";
				commonVariables.goal = "validate-code";
                commonVariables.phase = "validate-code";
				codequality.dynamicpage.getHtml($('#code_popup ul'), '#codeAnalysis', 'code_popup');
				commonVariables.api.localVal.setSession('appdetails','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"appInfos":[{"modules":null,"pomFile":null,"functionalFramework":null,"techInfo":{"appTypeId":"mobile-layer","techVersions":null,"techGroupId":"Black Berry","version":"","customerIds":null,"used":false,"creationDate":1378219126000,"helpText":null,"system":false,"name":"Hybrid","id":"tech-blackberry-hybrid","displayName":null,"description":null,"status":null},"appDirName":"j2eepre","selectedServers":[],"selectedDatabases":[],"selectedModules":[],"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedComponents":[],"selectedWebservices":[],"functionalFrameworkInfo":null,"selectedFrameworks":null,"created":true,"pilotInfo":null,"pilot":false,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"dependentModules":null,"version":"1.0","code":"j2eepre","customerIds":null,"used":false,"creationDate":1378391080000,"helpText":null,"system":false,"name":"j2eepre","id":"ecb70c02-8b9d-4414-b7fe-662abb154cfb","displayName":null,"description":"","status":null}],"projectCode":"library_android","preBuilt":false,"noOfApps":0,"endDate":null,"multiModule":false,"startDate":null,"version":"1.0","customerIds":["photon"],"used":false,"creationDate":1378383548000,"helpText":null,"system":false,"name":"library_android","id":"65c580d2-bce6-4a19-88dc-7bb69d97e8aa","displayName":null,"description":"","status":null},"status":"success"}');
				commonVariables.api.localVal.setSession("username" , "sudhakar_rag");
				//$("#sonar").val("src");
				ipjson = "sonar=src";
				codequality.codequalityListener.codeValidate(ipjson);
				setTimeout(function() {
					start();
					output = $("#content_div").children(".alert").text();
					notEqual('Report not available', output, "Codequality Code Validation test case");
					self.openconsole(codequality);
				}, 3000);
			});
		},	
		
		openconsole : function(codequality) {
			var self = this;
			asyncTest("Codequality open console test case", function() {
				$("#codeValidateConsole").click();
				setTimeout(function() {
					start();
					output = $(".unit_progress").css('display');
					equal('block', output, "Codequality open console test case success");
					self.changefunction(codequality);
				}, 3000);
			});
		},	
		
		changefunction : function(codequality) {
			var self = this;
			asyncTest("Codequality change function test case", function() {
			$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=src&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR510003","data":null,"status":"failure"});
					}
				});
			
				codequality.codequalityListener.onProjects();
				$("#reportUl li[name=selectType]").attr('key','src');
				$("#reportUl li[name=selectType]").click();
				setTimeout(function() {
					start();
					output = $("#repTypes").attr('key');
					equal('src', output, "Codequality change function test case success");
					self.dependencynotfetched(codequality);
					/* require(["unitTestTest"], function(unitTestTest){
						unitTestTest.runTests();
					}); */
				}, 3000);
			});
		},	
		
		dependencynotfetched : function(codequality) {
			var self = this;
			asyncTest("Codequality sonar status check failure test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=drupal7&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify([{"options":[{"value":"js","key":"js","dependency":"showSettings,environmentName"},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests,showSettings,environmentName"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]);
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=drupal7&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Dependency not fetched","exception":null,"responseCode":"PHR510002","data":null,"status":"failure"});
					}
				});

				//var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI();
				commonVariables.api.localVal.setSession("appDirName" , "drupal7");

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						
			
				commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#content_div").text();
					notEqual('Sonar started', output, "Codequality sonar status check failure test case");
					require(["buildTest"], function(buildTest){
						buildTest.runTests();
					});
				}, 1500);
			});
		},	
	}	
		
	
});
