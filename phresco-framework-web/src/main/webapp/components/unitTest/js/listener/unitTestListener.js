define(["unitTest/api/unitTestAPI"], function() {

	Clazz.createPackage("com.components.unitTest.js.listener");

	Clazz.com.components.unitTest.js.listener.UnitTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		unitTestAPI : null,
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
			if (self.unitTestAPI === null) {
				self.unitTestAPI =  new Clazz.com.components.unitTest.js.api.UnitTestAPI();
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
			data = JSON.parse(self.unitTestAPI.localVal.getSession('userInfo'));
			userId = data.id;
			appDirName = self.unitTestAPI.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
					
			if(action === "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.unit + "?userId="+userId+"&appDirName="+appDirName;				
			}
			return header;
		},
		
		getUnitTestReportOptions : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				self.unitTestAPI.unitTest(header,
					function(response) {
						if (response !== null) {
							//commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							//self.loadingScreen.removeLoading();
							callback({"status" : "service failure"});
						}
					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},
		
		getDynamicParams : function(thisObj, callback) {
			var self = this;
			commonVariables.goal = commonVariables.unitTestGoal;
			commonVariables.phase = commonVariables.unitTestGoal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml($('.dynamicControls'), thisObj, 'unit_popup', function(response) {
					if ("No parameters available" === response) {
						self.runUnitTest(function(responseData) {
							callback(responseData);
						});
					}
				});
			});
		},
		
		runUnitTest : function(callback) {
			var self = this;
			var testData = $("#unitTestForm").serialize();
			var appdetails = self.unitTestAPI.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = self.unitTestAPI.localVal.getSession('username');
						
			if (appdetails !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=unit-test&phase=unit-test&projectId="+projectId+"&"+testData;
			}
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnUnitTest(queryString, '#testConsole', function(response) {
						self.testResultListener.closeConsole();
						callback(response);
					});
				});
			} else {
				self.mavenServiceListener.mvnUnitTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					callback(response);
				});
			}
		}
	});

	return Clazz.com.components.unitTest.js.listener.UnitTestListener;
});