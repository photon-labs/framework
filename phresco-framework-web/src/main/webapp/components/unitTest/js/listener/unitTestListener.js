define([], function() {

	Clazz.createPackage("com.components.unitTest.js.listener");

	Clazz.com.components.unitTest.js.listener.UnitTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		testResultListener : null,
		dynamicpage : null,
		dynamicPageListener : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, header, data = {}, userId;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			userId = data.id;
			var projectInfo = commonVariables.api.localVal.getProjectInfo();
			appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
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
				commonVariables.api.ajaxRequest(header,
					function(response) {
						callback(response);
					},

					function(textStatus) {
					}
				);
			} catch(exception) {
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
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var queryString = '';
			appId = appdetails.data.projectInfo.appInfos[0].id;
			projectId = appdetails.data.projectInfo.id;
			customerId = appdetails.data.projectInfo.customerIds[0];
			username = commonVariables.api.localVal.getSession('username');
						
			if (appdetails !== null && userInfo !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=unit-test&phase=unit-test&projectId="+projectId+"&"+testData+'&displayName='+userInfo.displayName;
			}
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnUnitTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					callback(response);
				});
			});
		}
	});

	return Clazz.com.components.unitTest.js.listener.UnitTestListener;
});