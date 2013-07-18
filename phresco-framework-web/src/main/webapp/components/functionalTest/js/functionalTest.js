define(["functionalTest/listener/functionalTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.functionalTest.js");

	Clazz.com.components.functionalTest.js.FunctionalTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/functionalTest/template/functionalTest.tmp",
		configUrl: "components/functionalTest/config/config.json",
		name : commonVariables.functionalTest,
		functionalTestListener : null,
		functionalTestAPI : null,
		testResult : null,
		testResultListener : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onPerformActionEvent : null,
		onStopHubEvent : null,
		onStopNodeEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.functional;
			if (self.functionalTestAPI === null) {
				self.functionalTestAPI =  new Clazz.com.components.functionalTest.js.api.FunctionalTestAPI();
			}
			if (self.functionalTestListener === null ) {
				self.functionalTestListener = new Clazz.com.components.functionalTest.js.listener.FunctionalTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.onTabularViewEvent === null) {
				self.onTabularViewEvent = new signals.Signal();
			}
			self.onTabularViewEvent.add(self.functionalTestListener.onTabularView, self.functionalTestListener);
			
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.functionalTestListener.onGraphicalView, self.functionalTestListener);
			
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.functionalTestListener.getDynamicParams, self.functionalTestListener);
			
			if (self.onPerformActionEvent === null) {
				self.onPerformActionEvent = new signals.Signal();
			}
			self.onPerformActionEvent.add(self.functionalTestListener.performAction, self.functionalTestListener);
			
			self.registerEvents();
		},
		
		registerEvents : function() {
			var self = this;
			// To enable/disable the test button when the functional framework is grid and based on the hub status
			Handlebars.registerHelper('enableDisable', function(functionalFramework, hubStatus) {
				if (functionalFramework === "grid") {
					if (hubStatus) {
						return "";
					} else {
						return "disabled";
					}
				} else {
					return "";
				}
			});
			
			//To show the start node and stat hub button only when the functional framework is grid
			Handlebars.registerHelper('showHide', function(functionalFramework) {
				if (functionalFramework === "grid") {
					return "";
				} else {
					return "hideContent";
				}
			});
			
			//To show the start node and stat hub button only when the functional framework is grid
			Handlebars.registerHelper('hubButton', function(hubStatus) {
				if (hubStatus) {
					return 'value="Stop Hub" id="stopHub"';
				} else {
					return 'value="Start Hub" id="startHub"';
				}
			});
			
			//To show the start node and stat hub button only when the functional framework is grid
			Handlebars.registerHelper('nodeButton', function(nodeStatus) {
				if (nodeStatus) {
					return 'value="Stop Node" id="stopNode"';
				} else {
					return 'value="Start Node" id="startNode"';
				}
			});
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.getMyObj(commonVariables.testResult, function(retVal){
				self.testResult = retVal;
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testResult, false);
			});
			
			$("#testResult .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.functionalTestListener.getFunctionalTestOptions(self.functionalTestListener.getActionHeader(self.projectRequestBody, "getFunctionalTestOptions"), function(response) {
				var responseData = response.data;
				var functionalTestOptions = {};
				functionalTestOptions.functionalFramework = responseData.functionalFramework;
				functionalTestOptions.hubStatus = responseData.hubStatus;
				functionalTestOptions.nodeStatus = responseData.nodeStatus;
				var userPermissions = JSON.parse(self.functionalTestListener.functionalTestAPI.localVal.getSession('userPermissions'));
				functionalTestOptions.userPermissions = userPermissions;
				renderFunction(functionalTestOptions, whereToRender);
			});
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			self.testResultListener.resizeTestResultDiv();
			
			$("#functionalTestBtn").unbind("click");
			$("#functionalTestBtn").click(function() {
				self.onDynamicPageEvent.dispatch(this, $('#functionalTestDynCtrls'), 'functionalTest_popup', commonVariables.functionalTestGoal);
			});
			
			$("#startHub").unbind("click");
			$("#startHub").click(function() {
				self.onDynamicPageEvent.dispatch(this, $('#startHubDynCtrls'), 'startHub_popup', commonVariables.startHubGoal);
			});
			
			$("#stopHub").unbind("click");
			$("#stopHub").click(function() {
				self.onPerformActionEvent.dispatch("stopHub");
			});
			
			$("#startNode").unbind("click");
			$("#startNode").click(function() {
				self.onDynamicPageEvent.dispatch(this, $('#startNodeDynCtrls'), 'startNode_popup', commonVariables.startNodeGoal);
			});
			
			$("#stopNode").unbind("click");
			$("#stopNode").click(function() {
				self.onPerformActionEvent.dispatch("stopNode");
			});
			
			$("#testSuites").css("display", "none");
			$("#testCases").css("display", "none");
			$("#unitTestTab").css("display", "block");
			$(".unit_view").css("display", "none");
			$("#graphView").css("display", "none");
			
			
			$("a[name=unittestResult]").unbind("click");
			$("a[name=unittestResult]").click(function() {
				self.onUnitTestResultEvent.dispatch();
			});
			
			$("a[name=unitTestDescription]").unbind("click");
			$("a[name=unitTestDescription]").click(function() {
				self.onUnitTestDescEvent.dispatch();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
			//To open the functional test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeFunctionalTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of functional test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeFunctionalTest;
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
			
			//To run the Functional test
			$("#runFunctionalTest").unbind("click");
			$("#runFunctionalTest").click(function() {
				self.onPerformActionEvent.dispatch("runFunctionalTest");
			});
			
			//To start the Hub
			$("#executeStartHub").unbind("click");
			$("#executeStartHub").click(function() {
				self.onPerformActionEvent.dispatch("startHub");
			});
			
			//To start the Node
			$("#executeStartNode").unbind("click");
			$("#executeStartNode").click(function() {
				self.onPerformActionEvent.dispatch("startNode");
			});
		}
	});

	return Clazz.com.components.functionalTest.js.FunctionalTest;
});