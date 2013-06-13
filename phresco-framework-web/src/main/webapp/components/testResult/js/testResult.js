define(["testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.testResult.js");

	Clazz.com.components.testResult.js.TestResult = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/testResult/template/testResult.tmp",
		configUrl: "components/testResult/config/config.json",
		name : commonVariables.testResult,
		testResultListener : null,
		testResultAPI : null,
		requestBody : {},
		onTestResultDescEvent : null,
		onShowHideConsoleEvent : null,
		logContent : "",
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.testResultAPI === null) {
				self.testResultAPI =  new Clazz.com.components.testResult.js.api.TestResultAPI();
			}
			
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			self.onShowHideConsoleEvent.add(self.testResultListener.showHideConsole, self.testResultListener);
			
			self.registerEvents();
		},
		
		registerEvents : function() {
			var self = this;
			if (self.onTestResultDescEvent === null) {
				self.onTestResultDescEvent = new signals.Signal();
			}
			self.onTestResultDescEvent.add(self.testResultListener.onTestResultDesc, self.testResultListener);
		},
		
		loadPage : function() {
			Clazz.navigationController.push(this, false);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			commonVariables.from = "all";
			commonVariables.loadingScreen.showLoading();
			self.testResultListener.getTestResult(self.testResultListener.getActionHeader(self.requestBody, "getTestSuite"), function(response) {
				var data = {};
				data.testSuites = response.data;
				data.message = response.message
				commonVariables.testSuites = response.data;
				renderFunction(data, $('#testResult div.widget-maincontent-div'));
				commonVariables.loadingScreen.removeLoading();
				if (response.data != undefined && response.data != null) {
					setTimeout(function() {
						self.testResultListener.getBarChartGraphData(response.data, function(graphData, testSuiteLabels) {
							self.testResultListener.createBarChart(graphData, testSuiteLabels);
						});
					}, 400);
				}
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			self.testResultListener.onTestResult();
			self.testResultListener.resizeConsoleWindow();
			self.testResultListener.resizeTestResultTable("testSuites");
			
			$('#testConsole').html(self.logContent);
			self.logContent = '';
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{
							updateOnContentResize: true
						}
			});
			
			
			
			$(window).resize(function() {
				self.testResultListener.resizeTestResultTable();
				
				var height = $(this).height();
				var resultvalue = 0;
				$('.mainContent').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv; 
				});
				var footervalue = $('.footer_section').height();
				resultvalue = resultvalue + footervalue + 200;
				
				$('.mainContent .scrollContent').height(height - resultvalue);
				self.testResultListener.resizeConsoleWindow();
			});
			
//			$("a[name=unittestResult]").unbind("click");
//			$("a[name=unittestResult]").click(function() {
//				self.onTestResultEvent.dispatch();
//			});
			
			$("a[name=testDescription]").unbind("click");
			$("a[name=testDescription]").click(function() {
				commonVariables.loadingScreen.showLoading();
				commonVariables.from = "testsuite";
				commonVariables.testSuiteName = $(this).text();
				self.onTestResultDescEvent.dispatch();
			});
			
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.testResult.js.TestResult;
});