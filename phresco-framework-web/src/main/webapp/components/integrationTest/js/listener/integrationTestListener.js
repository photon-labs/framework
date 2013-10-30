define([], function() {

	Clazz.createPackage("com.components.integrationTest.js.listener");

	Clazz.com.components.integrationTest.js.listener.IntegrationTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		testResultListener : null,
		mavenServiceListener : null,
		
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
			if (self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}
		},
		
		getDynamicParams : function(thisObj, callback) {
			var self = this;
			projectId = commonVariables.api.localVal.getSession('projectId');
			commonVariables.goal = commonVariables.integrationTestGoal;
			commonVariables.phase = commonVariables.integrationTestGoal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml($('.dynamicControls'), thisObj, 'integration_popup', function(response) {
					if ("No parameters available" === response) {
						self.runIntegrationTest(function(responseData) {
							callback(responseData);
						});
					}
				});
			});
		},
		
		runIntegrationTest : function(callback) {
			var self = this;
			var testData = $("#integrationTestForm").serialize();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var queryString = '';
			var customerId = self.getCustomer();
			customerId = (customerId === "") ? "photon" : customerId;
			username = commonVariables.api.localVal.getSession('username');
			queryString ="username="+username+"&customerId="+customerId+"&goal=integration-test&phase=integration-test&"+testData+'&displayName='+userInfo.displayName+"&projectCode="+commonVariables.projectCode;
			queryString += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			$('.progress_loading').show();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnIntegrationTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					callback(response);
				});
			});
		}
	});

	return Clazz.com.components.integrationTest.js.listener.IntegrationTestListener;
});