define(["unitTest/listener/unitTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.unitTest.js");

	Clazz.com.components.unitTest.js.UnitTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/unitTest/template/unitTest.tmp",
		configUrl: "components/unitTest/config/config.json",
		name : commonVariables.unitTest,
		unitTestListener : null,
		unitTestAPI : null,
		testResult : null,
		testResultListener : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onRunUnitTestEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.unit;
			if (self.unitTestAPI === null) {
				self.unitTestAPI =  new Clazz.com.components.unitTest.js.api.UnitTestAPI();
			}
			if (self.unitTestListener === null ) {
				self.unitTestListener = new Clazz.com.components.unitTest.js.listener.UnitTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.onTabularViewEvent === null) {
				self.onTabularViewEvent = new signals.Signal();
			}
			self.onTabularViewEvent.add(self.unitTestListener.onTabularView, self.unitTestListener);
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.unitTestListener.onGraphicalView, self.unitTestListener);
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.unitTestListener.getDynamicParams, self.unitTestListener);
			if (self.onRunUnitTestEvent === null) {
				self.onRunUnitTestEvent = new signals.Signal();
			}
			self.onRunUnitTestEvent.add(self.unitTestListener.runUnitTest, self.unitTestListener);
			
			self.registerEvents(self.unitTestListener);
		},
		
		registerEvents : function(unitTestListener) {
			var self = this;
			Handlebars.registerHelper('report', function(data, firstVal) {
				var returnVal = "";
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, current){
						returnVal += '<li class="reportOption"><a href="#">'+ current +'</a></li>';
					});
				}
				return returnVal;
			});
			
			Handlebars.registerHelper('modules', function(data, firstVal) {
				var returnVal = "";
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, current){
						returnVal += '<li class="projectModule"><a href="#">'+ current +'</a></li>';
					});
				}
				return returnVal;
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
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.unitTestListener.getUnitTestReportOptions(self.unitTestListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var responseData = response.data;
				var unitTestOptions = {};
				unitTestOptions.reportOptions = responseData.reportOptions;
				unitTestOptions.projectModules = responseData.projectModules;
				var userPermissions = JSON.parse(self.unitTestListener.unitTestAPI.localVal.getSession('userPermissions'));
				unitTestOptions.userPermissions = userPermissions;
				renderFunction(unitTestOptions, whereToRender);
			});
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			
			$("#unitTestBtn").unbind("click");
			$("#unitTestBtn").click(function() {
				self.onDynamicPageEvent.dispatch(this);
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
			
			//Change event of the report type to get the report
			$('.projectModule').click(function() {
				$('#modulesDrop').attr("value", $(this).children().text());
				$('#modulesDrop').html($(this).children().text() + '<b class="caret"></b>');
			});
			
			//Change event of the report type to get the report
			$('.reportOption').click(function() {
				$('#reportOptionsDrop').attr("value", $(this).children().text());
				$('#reportOptionsDrop').html($(this).children().text() + '<b class="caret"></b>');
			});
			
			//To open the unit test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of unit test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
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
			
			//To run the unit test
			$("#runUnitTest").unbind("click");
			$("#runUnitTest").click(function() {
				self.onRunUnitTestEvent.dispatch();
			});
		}
	});

	return Clazz.com.components.unitTest.js.UnitTest;
});