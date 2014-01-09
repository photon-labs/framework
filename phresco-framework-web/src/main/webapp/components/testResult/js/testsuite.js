define(["testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.testsuite.js");

	Clazz.com.components.testResult.js.Testsuite = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/testResult/template/testsuite.tmp",
		configUrl: "components/testResult/config/config.json",
		name : commonVariables.testResult,
		testResultListener : null,
		manualTestListener : null,
		requestBody : {},
		onTabularViewEvent : null,
		onShowHideConsoleEvent : null,
		onPrintPdfEvent : null,
		onGeneratePdfEvent : null,
		deleteTestSuiteEvent : null,
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
			
			if (self.deleteTestSuiteEvent === null) {
				self.deleteTestSuiteEvent = new signals.Signal();
			}
			self.deleteTestSuiteEvent.add(self.testResultListener.deleteTestSuite, self.testResultListener);
			
			self.registerEvents();
		},
		
		registerEvents : function() {
			var self = this;
			Handlebars.registerHelper('uniqueName', function(name) {
				var str = name;
				str = self.specialCharValidation(str);
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
				str = str.replace(/\s+/g, '');
				
				return str;
				
			});
		},
		
		loadPage : function() {
			Clazz.navigationController.push(this, false);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			commonVariables.from = "all";
			var currentTab = commonVariables.navListener.currentTab;
			
			if (currentTab === 'unitTest') {
				commonVariables.runType = 'unit';
			} else if (currentTab === 'componentTest') {
				commonVariables.runType = 'component';
			} else if (currentTab === 'functionalTest') {
				commonVariables.runType = 'Functional';

			} 
			//To get the testsuites
			self.testResultListener.getTestsuites(function(response) {
				var data = {};
				if ("manualTest" === currentTab) {
					data.testSuites = response.data.testSuites;
				} else {
					data.testSuites = response.data;
					var checkval= $("#iframeExistsCheck").val();
					if(checkval === 'true'){
						$("#testResult").hide();
						$("#tabularView").hide();
						$("#iframeContent").show();
					}else{
						$("#iframeContent").hide();
						$("#testResult").show();						
					}
				}
				data.message = response.message;
				commonVariables.testSuites = response.data;
				renderFunction(data, $('#testResult div.widget-maincontent-div'));
				
				if(response.responseCode === "PHRQ000003") {
					setTimeout(function() {
						$("#messagedisp").attr('data-i18n', 'errorCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);
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
			var currentTab = commonVariables.navListener.currentTab;
			var testsuites = commonVariables.testSuites;
			self.showHideSysSpecCtrls();
			if (testsuites !== undefined && testsuites !== null) {
				if ("manualTest" === currentTab) {
					self.testResultListener.getManualBarChartGraphData(testsuites, function(graphData) {
						self.testResultListener.createManualBarChart(graphData);
					});
				} else {
					self.testResultListener.getBarChartGraphData(testsuites, function(graphData, testSuiteLabels) {
						self.testResultListener.createBarChart(graphData, testSuiteLabels);
					});
				}
				$('.unit_view').show();
			}
			
			if ("manualTest" === currentTab) {
				$('.manual').show();
				$('.errorClm').hide();
				$('.testsuiteClm').hide();
				$('.log').hide();
			}
			
			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(commonVariables.logContent);
			commonVariables.logContent = '';
			
			self.testResultListener.resizeTestResultDiv();
			self.resizeConsoleWindow();
			setTimeout(function() {
				if ('functionalTest' === currentTab && commonVariables.consoleError !== undefined && commonVariables.consoleError) {
					self.testResultListener.openConsoleDiv();
					commonVariables.consoleError = false;
				}
			}, 100);	
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.killProcess();
			var currentTab = commonVariables.navListener.currentTab;
			if ("manualTest" === currentTab) {
				$('.unit_close').hide();
				$('#addTestSuite').show();
				$('#addTestCase').hide();
			}
			
			$(".tooltiptop").tooltip();
			
			//To show the testcases of the selected testsuite
			$("a[name=testDescription]").unbind("click");
			$("a[name=testDescription]").click(function() {
				commonVariables.from = "testsuite";
				commonVariables.testSuiteName = $(this).text();
				commonVariables.navListener.getMyObj(commonVariables.testcaseResult, function(retVal) {
					self.testcaseResult = retVal;
					Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
					Clazz.navigationController.push(self.testcaseResult, false);
				});
			});
			
			$("a[namedel=delete]").click(function() {
				var temp = $(this).attr('name');
				self.openccpl(this, $(this).attr('name'));
				$('#'+temp).show();
				$('input[name="delTestSuite"]').unbind();
				$('input[name="delTestSuite"]').click(function() {
					var testsuitename = $(this).closest("div").parent("div").attr("testsuitename");
					$('input[name="delTestSuite"]').closest("div");
					self.deleteTestSuiteEvent.dispatch(testsuitename);
				});	
			});
			
			//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			//To show the print as pdf popup
			$('#pdfIcon').unbind("click");
			$('#pdfIcon').click(function() {
				if ($('#topHidden').val() !== undefined || $('#topHidden').val() !== 0) {
					$('.th-inner').css('top', $('#topHidden').val()+'px');
				}
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
			/* $("#buildCopyLog").unbind("click");
			$("#buildCopyLog").click(function() {
				commonVariables.hideloading = true;
				commonVariables.navListener.copyToClipboard($("#testConsole"));
			}); */
			
			//Shows the tabular view of the test result
			$(".table2").unbind("click");
			$(".table2").click(function() {
				$("#graphicalView").hide();
				$("#tabularView").show();
				$("#graphView").hide();
				$("#testSuites").show();
			});
			
			//Shows the graphical view of the test result
			$(".graph1").unbind("click");
			$(".graph1").click(function() {
				var top = $('.header-background').offset().top;
				$("#testSuites").hide();
				$("#testSuites").append('<input type="hidden" id="topHidden" value="'+top+'">');
				$("#graphView").show();
				$("#tabularView").hide();
				$("#graphicalView").show();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			self.tableScrollbar();
			self.customScroll($(".consolescrolldiv"));
		},
	});

	return Clazz.com.components.testResult.js.Testsuite;
});