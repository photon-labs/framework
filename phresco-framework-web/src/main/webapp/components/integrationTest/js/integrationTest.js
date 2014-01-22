define(["integrationTest/listener/integrationTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.integrationTest.js");

	Clazz.com.components.integrationTest.js.IntegrationTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/integrationTest/template/integrationTest.tmp",
		configUrl: "components/integrationTest/config/config.json",
		name : commonVariables.integrationTest,
		integrationTestListener : null,
		testsuiteResult : null,
		testResultListener : null,
		// onTabularViewEvent : null,
		// onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onRunIntegrationTestEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.integration;
			if (self.integrationTestListener === null ) {
				self.integrationTestListener = new Clazz.com.components.integrationTest.js.listener.IntegrationTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.integrationTestListener.getDynamicParams, self.integrationTestListener);

			if (self.onRunIntegrationTestEvent === null) {
				self.onRunIntegrationTestEvent = new signals.Signal();
			}
			self.onRunIntegrationTestEvent.add(self.integrationTestListener.runIntegrationTest, self.integrationTestListener);
			
			self.registerEvents(self.integrationTestListener);
		},
		
		registerEvents : function(integrationTestListener) {
			var self = this;
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.getMyObj(commonVariables.testsuiteResult, function(retVal) {
				self.testsuiteResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
				var data = {};
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				data.userPermissions = userPermissions;
				renderFunction(data, whereToRender);
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			self.windowResize();
			
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
			
			$("#integrationTestBtn").unbind("click");
			$("#integrationTestBtn").click(function() {
				self.onDynamicPageEvent.dispatch(this, function() {
					self.testResult.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testResult, false);
				});
				self.copyLog();
			});
			
			$("#testSuites").css("display", "none");
			$("#testCases").css("display", "none");
			$("#unitTestTab").css("display", "block");
			$(".unit_view").css("display", "none");
			$("#graphView").css("display", "none");

			//To open the unit test directory
			$('#OpenFolder').unbind('click');
			$("#OpenFolder").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typeIntegrationTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of unit test directory
			$('#CopyPath').unbind('click');
			$("#CopyPath").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typeIntegrationTest;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			//To run the Integration test
			$("#runIntegrationTest").unbind("click");
			$("#runIntegrationTest").click(function() {
				commonVariables.runType = 'integration';
				$('input[name=kill]').attr('disabled', true);
				self.onRunIntegrationTestEvent.dispatch(function() {
					commonVariables.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
				$("#integration_popup").toggle();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.integrationTest.js.IntegrationTest;
});