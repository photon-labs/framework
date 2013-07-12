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
		onPrintPdfEvent : null,
		onGeneratePdfEvent : null,
		
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
			
			if (self.onPrintPdfEvent === null) {
				self.onPrintPdfEvent = new signals.Signal();
			}
			self.onPrintPdfEvent.add(self.testResultListener.getPdfReports, self.testResultListener);
			
			if (self.onGeneratePdfEvent === null) {
				self.onGeneratePdfEvent = new signals.Signal();
			}
			self.onGeneratePdfEvent.add(self.testResultListener.generatePdfReport, self.testResultListener);
			
			if (self.onGeneratePdfEvent === null) {
				self.onGeneratePdfEvent = new signals.Signal();
			}
			self.onGeneratePdfEvent.add(self.testResultListener.generatePdfReport, self.testResultListener);
			
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
			//commonVariables.loadingScreen.showLoading();
			//To get the testsuites
			self.testResultListener.performAction(self.testResultListener.getActionHeader(self.requestBody, "getTestSuite"), function(response) {
				var data = {};
				data.testSuites = response.data;
				data.message = response.message;
				commonVariables.testSuites = response.data;
				renderFunction(data, $('#testResult div.widget-maincontent-div'));
				//commonVariables.loadingScreen.removeLoading();
				if (response.data !== undefined && response.data !== null) {
					setTimeout(function() {
						self.testResultListener.getBarChartGraphData(response.data, function(graphData, testSuiteLabels) {
							self.testResultListener.createBarChart(graphData, testSuiteLabels);
						});
					}, 400);
					$('#pdfDiv').show();
				} else {
					setTimeout(function() {
						var noReportContent = '<div class="alert alert-block" style="text-align: center; margin: auto 0;">' + response.message + '</div>';
						$('#graphView').html(noReportContent);
					}, 400);
					$('#pdfDiv').hide();
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
			self.resizeConsoleWindow();
			self.testResultListener.resizeTestResultColumn("testSuites");
			
			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(self.logContent);
			self.logContent = '';
			
			self.testResultListener.showTabularView();
			self.testResultListener.resizeTestResultDiv();
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
				self.testResultListener.resizeTestResultColumn();
				
				var height = $(this).height();
				var resultvalue = 0;
				$('.mainContent').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv; 
				});
				var footervalue = $('.footer_section').height();
				resultvalue = resultvalue + footervalue + 200;
				
				var testSuiteTableHeight = $('.testSuiteTable').height();
				//$('.scrollContent').height(testSuiteTableHeight);
				self.resizeConsoleWindow();
			});
			
			//To show the testcases of the selected testsuite
			$("a[name=testDescription]").unbind("click");
			$("a[name=testDescription]").click(function() {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.from = "testsuite";
				commonVariables.testSuiteName = $(this).text();
				self.onTestResultDescEvent.dispatch();
			});
			
			//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			//To show the print as pdf popup
			$('#pdfIcon').unbind("click");
			$('#pdfIcon').click(function() {
				self.onPrintPdfEvent.dispatch();
				self.testResultListener.showPopupLoading($('#pdfReportLoading'));
				self.opencc(this, 'pdf_report');
			});
			
			//To generate the pdf report
			$('#generatePdf').unbind("click");
			$('#generatePdf').click(function() {
				self.onGeneratePdfEvent.dispatch();
				self.testResultListener.showPopupLoading($('#pdfReportLoading'));
			});
			
			//To copy the console log content to the clip-board
			$('#copyLog').unbind("click");
			$('#copyLog').click(function() {
				commonVariables.navListener.copyToClipboard($('#testConsole'));
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.testResult.js.TestResult;
});