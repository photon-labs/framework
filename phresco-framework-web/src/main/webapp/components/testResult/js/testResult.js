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
			commonVariables.from = "all";
			self.registerEvents();
		},
		
		registerEvents : function() {
			var self = this;
			if (self.onTestResultDescEvent === null) {
				self.onTestResultDescEvent = new signals.Signal();
			}
			self.onTestResultDescEvent.add(self.testResultListener.onTestResultDesc, self.testResultListener);
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function() {
			Clazz.navigationController.push(this, false);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
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
			
			var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
			var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
			var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
			var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
			var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
			var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
			
			$(".fixedHeader tr th:first-child").css("width",w1);
			$(".fixedHeader tr th:nth-child(2)").css("width",w2);
			$(".fixedHeader tr th:nth-child(3)").css("width",w3);
			$(".fixedHeader tr th:nth-child(4)").css("width",w4);
			$(".fixedHeader tr th:nth-child(5)").css("width",w5);
			$(".fixedHeader tr th:nth-child(6)").css("width",w6);
			
			$(window).resize(function() {
				var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				
				var height = $(this).height();
				
				var resultvalue = 0;
				$('.mainContent').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv; 
				});
				var footervalue = $('.footer_section').height();
				resultvalue = resultvalue + footervalue + 200;

				$('.mainContent .scrollContent').height(height - resultvalue);

			});
			
			$("a[name=unittestResult]").unbind("click");
			$("a[name=unittestResult]").click(function() {
				self.onTestResultEvent.dispatch();
			});
			
			$("a[name=testDescription]").unbind("click");
			$("a[name=testDescription]").click(function() {
				commonVariables.loadingScreen.showLoading();
				commonVariables.from = "testsuite";
				commonVariables.testSuiteName = $(this).text();
				self.onTestResultDescEvent.dispatch();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.testResult.js.TestResult;
});