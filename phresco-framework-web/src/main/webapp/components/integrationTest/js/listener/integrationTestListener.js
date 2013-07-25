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
		
		onGraphicalView : function() {
			var self = this;
			self.testResultListener.showGraphicalView();
		},
		
		onTabularView : function() {
			var self = this;
			self.testResultListener.showTabularView();
		},
		
		runIntegrationTest : function(callback) {
			var self = this;
		}
	});

	return Clazz.com.components.integrationTest.js.listener.IntegrationTestListener;
});