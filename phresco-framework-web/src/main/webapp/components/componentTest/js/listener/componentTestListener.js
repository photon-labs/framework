define([], function() {

	Clazz.createPackage("com.components.componentTest.js.listener");

	Clazz.com.components.componentTest.js.listener.ComponentTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		testResultListener : null,
		dynamicpage : null,
		
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
		
		getDynamicParams : function(thisObj, callback) {
			var self = this;
			commonVariables.goal = commonVariables.componentTestGoal;
			commonVariables.phase = commonVariables.componentTestGoal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml($('.dynamicControls'), thisObj, 'componentTest_popup', function(response) {;
					if ("No parameters available" === response) {
						self.runComponentTest(function(responseData) {
							callback(responseData);
						});
					}
				});
			});
		},
		
		runComponentTest : function(callback) {
			var self = this;
			var testData = $("#componentTestForm").serialize();
			var appdetails = commonVariables.api.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = commonVariables.api.localVal.getSession('username');
						
			if (appdetails !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=component-test&phase=component-test&projectId="+projectId+"&"+testData;
			}
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
				retVal.mvnComponentTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					callback(response);
				});
			});
		}
	});

	return Clazz.com.components.componentTest.js.listener.ComponentTestListener;
});