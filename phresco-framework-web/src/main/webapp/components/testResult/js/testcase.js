define(["testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.testcase.js");

	Clazz.com.components.testResult.js.Testcase = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/testResult/template/testcase.tmp",
		configUrl: "components/testResult/config/config.json",
		name : commonVariables.testResult,
		testResultListener : null,
		requestBody : {},
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
			
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			self.onShowHideConsoleEvent.add(self.testResultListener.showHideConsole, self.testResultListener);
		},
		
		loadPage : function() {
			Clazz.navigationController.push(this, false);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			commonVariables.from = "all";
			var requestBody = {};
			requestBody.testSuite = commonVariables.testSuiteName;
			self.testResultListener.performAction(self.testResultListener.getActionHeader(requestBody, "getTestReport"), function(response) {
				var data = {};
				data.testcases = response.data;
				data.message = response.message;
				commonVariables.testcases = response.data;
				renderFunction(data, $('#testResult div.widget-maincontent-div'));
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
			$('#pdfDiv').hide();
			var currentTab = commonVariables.navListener.currentTab;
			if ("manualTest" === currentTab) {
				$('.manual').show();
				$('.log').hide();
				$('.defaultHead').hide();
			} else if ("functionalTest" === currentTab) {
				$('.functional').show();
			}
			
			var testcases = commonVariables.testcases;
			if (testcases !== undefined && testcases !== null) {
				if ("manualTest" === currentTab) {
					self.testResultListener.getManualPieChartGraphData(function(graphData) {
						self.testResultListener.createManualPieChart(graphData);
					});
				} else {
					self.testResultListener.getPieChartGraphData(function(graphData) {
						self.testResultListener.createPieChart(graphData);
					});
					self.showScreenShot();
				}
			} else {
				$('.unit_view').hide();
			}
			
			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(self.logContent);
			self.logContent = '';
			
			self.testResultListener.resizeTestResultDiv();
			self.resizeConsoleWindow();
		},
		
		showScreenShot : function() {
			var testcases = commonVariables.testcases;
			for (i in testcases) {
				var testcase = testcases[i];
				var eventClass = testcase.name;
				var filePath = "";
				if (testcase.testCaseFailure !== null && testcase.testCaseFailure.hasFailureImg) {
					filePath = "data:image/png;base64," + testcase.testCaseFailure.screenshotPath;
				} else if (testcase.testCaseError !== null && testcase.testCaseError.hasErrorImg) {
					filePath = "data:image/png;base64," + testcase.testCaseFailure.screenshotPath;
				}
				$('.'+eventClass).magnificPopup({
					items: [{
						src: $('<div class="text_center"><img src="'+filePath+'"><div class="fullscreen_desc">'+eventClass+'</div></div>'), // Dynamically created element
						type: 'inline'
					}],
					gallery: {
						enabled: true
					},
					type: 'image'
				});
				
				/*$('.'+eventClass).click(function(e){
					$('.mfp-container').fullScreen();
					e.preventDefault();
				});*/
			}
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			var currentTab = commonVariables.navListener.currentTab;
			if ("manualTest" === currentTab) {
				$('.unit_close').hide();
				$('#addTestSuite').hide();
				$('#addTestCase').show();
			}
			
			$(".tooltiptop").tooltip();
			
			//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			//Shows the tabular view of the test result
			$("#tabularView").unbind("click");
			$("#tabularView").click(function() {
				self.showTabularView();
			});
			
			//Shows the graphical view of the test result
			$("#graphicalView").unbind("click");
			$("#graphicalView").click(function() {
				self.showGraphicalView();
			});
			
			$('a[name=updateManualTestCase_popup]').click(function() {
				var dynClass = $(this).attr('class');
				self.openccpl(this, dynClass, '');
				var testsuiteName = commonVariables.testSuiteName;
				$('#testSuiteId').val(testsuiteName);
			});
			
			//To copy the console log content to the clip-board
			$('#copyLog').unbind("click");
			$('#copyLog').click(function() {
				commonVariables.navListener.copyToClipboard($('#testConsole'));
			});
			
			$("input[name=updateTestCase]").unbind("click");
			$('input[name=updateTestCase]').click(function() {
				var data = {};
				data = $(this).parent().parent().serializeObject();
				var currentTestsuiteName = commonVariables.testSuiteName;
				data.testSuite = currentTestsuiteName;
				self.manualRequestBody = data;
				self.testResultListener.performAction(self.testResultListener.getActionHeader(self.manualRequestBody, "updateTestcase"), function(response) {
					commonVariables.navListener.getMyObj(commonVariables.testcaseResult, function(retVal) {
						self.testcaseResult = retVal;
						Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
						Clazz.navigationController.push(self.testcaseResult, false);
					});
				});
			});
			
			$('.log').unbind("click");
			$('.log').bind("click", function() {
				var id = $(this).attr("resultname");
				self.opencc(this, id);
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			self.tableScrollbar();
			self.customScroll($(".consolescrolldiv"));
		},
		
		showGraphicalView : function() {
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_off.png" width="25" height="25" border="0" alt="">Table View');
			$("#testcases").hide();
			$("#graphView").show();
		},
		
		showTabularView : function() {
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_off.png" width="25" height="25" border="0" alt="">Graph View');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_on.png" width="25" height="25" border="0" alt=""><b>Table View</b>');
			$("#graphView").hide();
			$("#testcases").show();
		},
	});

	return Clazz.com.components.testResult.js.Testcase;
});