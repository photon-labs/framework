define(["componentTest/api/componentTestAPI"], function() {

	Clazz.createPackage("com.components.componentTest.js.listener");

	Clazz.com.components.componentTest.js.listener.ComponentTestListener = Clazz.extend(Clazz.Widget, {
		
		componentTestAPI : null,
		testResultListener : null,
		dynamicpage : null,
		dynamicPageListener : null,
		mavenServiceListener : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.componentTestAPI === null) {
				self.componentTestAPI =  new Clazz.com.components.componentTest.js.api.ComponentTestAPI();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}
		},
		
		onGraphicalView : function() {
			var self = this;
			self.testResultListener.showGraphicalView();
		},
		
		onTabularView : function() {
			var self = this;
			self.testResultListener.showTabularView();
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, header, data = {}, userId;
			data = JSON.parse(self.componentTestAPI.localVal.getSession('userInfo'));
			userId = data.id;
			appDirName = self.componentTestAPI.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
					
			if (action == "getFunctionalTestOptions") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/functionalFramework?appDirName="+appDirName;				
			}
			return header;
		},
		
		getDynamicParams : function(goal) {
			var self = this;
			commonVariables.goal = goal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(false, function(response) {
					$("#dynamicContent").html(response);
//					self.multiselect();
					dynamicPageObject.showParameters();
					self.dynamicPageListener.controlEvent();
				});
			});
		},
		
		getFunctionalTestOptions : function(header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				self.componentTestAPI.componentTest(header,
					function(response) {
						if (response !== null) {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							self.loadingScreen.removeLoading();
							callback({"status" : "service failure"});
						}
					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
			}
		},
		
		runComponentTest : function() {
			var self = this;
			var testData = $("#componentTestForm").serialize();
			var appdetails = self.componentTestAPI.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = self.componentTestAPI.localVal.getSession('username');
						
			if (appdetails != null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+testData;
			}
			$('#componentTestConsole').html('');
				
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					
					self.mavenServiceListener.mvnUnitTest(queryString, '#unitTestConsole', function(returnVal) {
						callback(returnVal);
					});
				});
			} else {
				self.mavenServiceListener.mvnComponentTest(queryString, '#componentTestConsole', function(returnVal) {
					
				});
			}			
		}
	});

	return Clazz.com.components.componentTest.js.listener.ComponentTestlistener;
});