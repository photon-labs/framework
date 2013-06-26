define(["componentTest/listener/componentTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.componentTest.js");

	Clazz.com.components.componentTest.js.ComponentTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/componentTest/template/componentTest.tmp",
		configUrl: "components/componentTest/config/config.json",
		name : commonVariables.componentTest,
		componentTestListener : null,
		componentTestAPI : null,
		testResult : null,
		testResultListener : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onRunComponentTestEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.component;
			if (self.componentTestAPI === null) {
				self.componentTestAPI =  new Clazz.com.components.componentTest.js.api.ComponentTestAPI();
			}
			if (self.componentTestListener === null ) {
				self.componentTestListener = new Clazz.com.components.componentTest.js.listener.ComponentTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.onTabularViewEvent === null) {
				self.onTabularViewEvent = new signals.Signal();
			}
			self.onTabularViewEvent.add(self.componentTestListener.onTabularView, self.componentTestListener);
			
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.componentTestListener.onGraphicalView, self.componentTestListener);
			
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.componentTestListener.getDynamicParams, self.componentTestListener);
			
			if (self.onRunComponentTestEvent === null) {
				self.onRunComponentTestEvent = new signals.Signal();
			}
			self.onRunComponentTestEvent.add(self.componentTestListener.runComponentTest, self.componentTestListener);
			
			self.registerEvents();
		},
		
		registerEvents : function() {
			var self = this;
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(needAnimation) {
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, needAnimation);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal) {
				self.testResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testResult, false);
			});
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var data = {};
			var userPermissions = JSON.parse(self.componentTestListener.componentTestAPI.localVal.getSession('userPermissions'));
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
			
			$("#componentTestBtn").unbind("click");
			$("#componentTestBtn").click(function() {
				self.onDynamicPageEvent.dispatch(this, function() {
					self.testResult.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testResult, false);
				});
			});
			
			$("#testSuites").css("display", "none");
			$("#testCases").css("display", "none");
			$("#unitTestTab").css("display", "block");
			$(".unit_view").css("display", "none");
			$("#graphView").css("display", "none");
			
			//To open the component test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeComponentTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of component test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeComponentTest;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			//Shows the tabular view of the test result
			$("#tabularView").unbind("click");
			$("#tabularView").click(function() {
				self.onTabularViewEvent.dispatch();
			});
			
			//Shows the graphical view of the test result
			$("#graphicalView").unbind("click");
			$("#graphicalView").click(function() {
				self.onGraphicalViewEvent.dispatch();
			});
			
			//To run the component test
			$("#runComponentTest").unbind("click");
			$("#runComponentTest").click(function() {
				self.onRunComponentTestEvent.dispatch(function() {
					self.testResult.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testResult, false);
				});
				$("#componentTest_popup").toggle();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.componentTest.js.ComponentTest;
});