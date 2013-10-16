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
		
		/*getDynamicParams : function(thisObj, whereToRender, popupId, goal) {
			var self = this;
			commonVariables.goal = goal;
			commonVariables.phase = goal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(whereToRender, thisObj, popupId, function(response) {
					var height = $(".testSuiteTable").height();
					$(popupId).css("max-height", height - 40 + 'px');
					var formObj;
					if (goal === commonVariables.integrationTestGoal) {
						formObj = $('#integrationTestForm');
					}
					formObj.css("max-height", height - 92 + 'px');
					if (formObj.find('li.ctrl').length > 5) {
						formObj.mCustomScrollbar({
							autoHideScrollbar:true,
							theme:"light-thin",
							advanced:{updateOnContentResize: true}
						});
					}
				});
			});
		},*/
		
		runIntegrationTest : function(callback) {
			var self = this;
		}
	});

	return Clazz.com.components.integrationTest.js.listener.IntegrationTestListener;
});