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
			  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=html5&goal=validate-code",
			  type: "GET",
			  dataType: "json",
			  contentType: "application/json",
			  status: 200,
			  response : function() {
				  this.responseText = JSON.stringify({"response":null,"message":"Sonar not yet Started","exception":null,"data":null});
			  }
			});

		

			/* var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI(); */
			commonVariables.api.localVal.setSession("appDirName" , "html5");

			require(["navigation/navigation"], function(){
				commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			});						
			
			commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#content_div").text();
					equal('Sonar not yet Started', output, "Codequality sonar status check test case");
					self.runNegativeEventTest();
				}, 1500);
			});
		},
		
		runNegativeEventTest : function() {
			var self = this;
			asyncTest("Codequality sonar status check failure test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=html5&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Sonar not yet Started","exception":null,"data":null});
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
					self.rungetReportTypesTest();
				}, 1500);
			});
		},		
		
		rungetReportTypesTest : function() {
			var self = this;
			asyncTest("Codequality get report types test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=javaws&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":[{"value":"js","key":"js","dependency":null},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://172.16.23.72:9000/dashboard/index/com.photon.phresco.service:javaws:js"});
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
					equal('js', output, "Codequality get report types test case");
					self.rungetReportTypesFailureTest();
				}, 1500);
			});
		},		
		
		rungetReportTypesFailureTest : function() {
			var self = this;
			asyncTest("Codequality get report types failure test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=javaws&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":[{"value":"js","key":"js","dependency":null},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://172.16.23.72:9000/dashboard/index/com.photon.phresco.service:javaws:js"});
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
					notEqual('Functional', output, "Codequality get report types failure test case");
					self.rungetReportTest();
				}, 1500);
			});
		},
		
		rungetReportTest : function() {
			var self = this;
			asyncTest("Codequality get report test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=javaws&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":[{"value":"js","key":"js","dependency":null},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}],"validateAgainst":{"value":"Source","key":"src","dependency":"src,skipTests"}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=javaws&validateAgainst=js&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":"http://172.16.23.72:9000/dashboard/index/com.photon.phresco.service:javaws:js"});
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
					output = $("#content_div").find("iframe").attr("src");
					equal('http://172.16.23.72:9000/dashboard/index/com.photon.phresco.service:javaws:js', output, "Codequality get report test case");
					self.rungetReportNotAvailableTest();
				}, 1500);
			});
		},
		
		rungetReportNotAvailableTest : function() {
			var self = this;
			asyncTest("Codequality get report not available test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=drupal&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":null}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=drupal&validateAgainst=src&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Report not available","exception":null,"data":null});
					}
				});

				//var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI();
				commonVariables.api.localVal.setSession("appDirName" , "drupal");
				commonVariables.api.localVal.setSession("username" , "sudhakar_rag");

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						
			
				commonVariables.navListener.onMytabEvent("codequality");
			
				setTimeout(function() {
					start();
					output = $("#content_div").find(".alert").text();
					equal('Report not available', output, "Codequality get report not available test case");
					require(["configurationTest"], function(configurationTest){
						configurationTest.runTests();
					});
				}, 1500);
			});
		},	
		
		/* rundynamicParameterTest : function(codequality) {
			var self = this;
			asyncTest("Codequality dynamice parameter test case", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName=drupal&goal=validate-code",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Dependency returned successfully","exception":null,"data":[{"options":null,"validateAgainst":{"value":"Source","key":"src","dependency":null}},{"options":null,"validateAgainst":{"value":"Functional Test","key":"functional","dependency":null}}]});
					}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/iFrameReport?appDirName=drupal&validateAgainst=src&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function(){ this.responseText = JSON.stringify({"response":null,"message":"Report not available","exception":null,"data":null});}
				});
				
				$.mockjax({
				  url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=drupal&goal=validate-code&phase=validate-code&customerId=photon&userId=sudhakar_rag",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function(){this.responseText = JSON.stringify({"response":null,"message":"Report not available","exception":null,"data":null});}
				});

				var codequalityAPI =  new Clazz.com.components.codequality.js.api.CodeQualityAPI();
				codequalityAPI.localVal.setSession("appDirName" , "drupal");
				codequalityAPI.localVal.setSession("username" , "sudhakar_rag");
				commonVariables.appDirName = "drupal";
				commonVariables.goal = "validate-code";
				commonVariables.phase = "validate-code";
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});						
				
				$("#codeAnalysis").click();
			
				setTimeout(function() {
					start();
					output = $("#content_div").find(".alert").text();
					equal('Report not available', output, "Codequality dynamice parameter test case");
					
				}, 1500);
			});
		},			 */
	}	
		
	
});
