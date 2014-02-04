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
		//deleteTestCaseEvent : null,
		
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
			
//			if (self.deleteTestCaseEvent === null) {
//				self.deleteTestCaseEvent = new signals.Signal();
//			}
			//self.deleteTestCaseEvent.add(self.testResultListener.deleteTestCase, self.testResultListener);
			
			self.registerEvents();
		},

		registerEvents : function() {
			var self = this;
			Handlebars.registerHelper('removeDot', function(string) {
				return string.replace(/\./g, '').replace(/\s/g, '');
			});
		
			Handlebars.registerHelper('statusUpdate', function(status) {
				var successStatus = "";
				var failStatus = "";
				var notAppStatus = "";
				var blockStatus = "";
				var returnVal= "";
				var sectedText = "";
				if(status === "success") {
					successStatus = "selected";
				} else if(status === "failure") {
					failStatus = "selected";
				} else if(status === "notApplicable") {
					notAppStatus = "selected";
				} else if(status === "blocked") {
					blockStatus = "selected";
				} else {
					sectedText  = "selected";
				}
				var returnVal = "<select name='status'> <option "+ sectedText +" disabled value=''>Select Status</option> <option value='success'"+ successStatus +">Success</option> <option value='failure'"+ failStatus +">Failure</option><option value='notApplicable'" +notAppStatus +">Not Applicable</option><option value='blocked'" + blockStatus+">Blocked</option>";
				
				return returnVal;
			});
			
			Handlebars.registerHelper('uniqueTestCaseId', function(testCaseId) {
				var str = testCaseId;
				if(!self.isBlank(str)) {
					str = self.specialCharValidation(str);
					str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
					str = str.replace(/\s+/g, '');
				}
				return str;
			});

			Handlebars.registerHelper('showArrowImage', function(testSteps, testFunctions) {
				var css = "display:none;";
				/*if (testFunctions !== undefined && testFunctions !== null && testFunctions.length > 0) {
					css = "display:inline; cursor:pointer;";
				} else*/

				if (testSteps !== undefined && testSteps !== null && testSteps.length > 0) {
					css = "display:inline; cursor:pointer;";
				}
				return css;
			});

			
			Handlebars.registerHelper('hasTestFunctionsSteps', function(testFunctionsStep) {
				var flag = "false";
				if (testFunctionsStep !== undefined && testFunctionsStep !== null && testFunctionsStep.length > 0) {
					flag = "true";
				} 
				return flag;
			});
		},
		
		loadPage : function() {
			Clazz.navigationController.push(this, false);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			commonVariables.from = "all";
			var requestBody = {};
			var data = {};
			requestBody.testSuite = commonVariables.testSuiteName;
			self.testResultListener.getTestsuites(function(response) {
				data.testSuites = response.data.testSuites;
				commonVariables.testSuites = response.data;
			});
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
			self.showHideSysSpecCtrls();
			var currentTab = commonVariables.navListener.currentTab;
			if ("manualTest" === currentTab) {
				$('.manual').show();
				$('.log').hide();
				$('.defaultHead').hide();
			} else if ("functionalTest" === currentTab) {
				$('.functional').show();
			}
			
			if (currentTab === 'unitTest') {
				commonVariables.runType = 'unit';
			} else if (currentTab === 'componentTest') {
				commonVariables.runType = 'component';
			} else if (currentTab === 'functionalTest') {
				commonVariables.runType = 'functional';
			} 
			
			var testcases = commonVariables.testcases;
			if (testcases !== undefined && testcases !== null) {
				if ("manualTest" === currentTab) {
					$('.graph_area').append('<canvas id="pie" width="620" height="400">[No canvas support]</canvas>');
					self.testResultListener.getManualPieChartGraphData(function(graphData) {
						self.testResultListener.createManualPieChart(graphData);
					});
				} else {

					var testStepsAvailable = false;
					$.each(testcases, function(index, testcase) {
						if (testcase.testSteps !== undefined && testcase.testSteps !== null && testcase.testSteps.length > 0) {
							testStepsAvailable = true;
							return false;
						}
					});
					if (testStepsAvailable) {
						$('.graph_area').append('<canvas id="bar" width="620" height="400">[No canvas support]</canvas>');
						self.testResultListener.getTestStepBarChartGraphData(testcases, function(graphDatas, labels) {
							self.testResultListener.createBarChart(graphDatas, labels);
						});
					} else {
						$('.graph_area').append('<canvas id="pie" width="620" height="400">[No canvas support]</canvas>');
						self.testResultListener.getPieChartGraphData(function(graphData) {
							self.testResultListener.createPieChart(graphData);
						});
					}
					if ("functionalTest" === currentTab) {
						self.showScreenShot();
					}
				}
			} else {
				$('.unit_view').hide();
			}
			
			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(commonVariables.logContent);
			commonVariables.logContent = '';
			
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
				if (testcase.testSteps != null && testcase.testSteps.length > 0) {
					$.each(testcase.testSteps, function(index, testStep) {
						if (testStep.testStepFailure !== null && testStep.testStepFailure.hasFailureImg) {
							var testStepClass = testStep.name.replace(/\./g, '').replace(/\s/g, '');
							var screenshotUrl = testStep.testStepFailure.screenshotUrl;
							$('.'+testStepClass).magnificPopup({
								items: [{
									src: $('<div class="text_center"><img src="'+screenshotUrl+'"><div class="fullscreen_desc">'+testStepClass+'</div></div>'), // Dynamically created element
									type: 'inline'
								}],
								gallery: {
									enabled: true
								},
								type: 'image'
							});
						}
					});
				} else {
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
				}
			}
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
			$(".table2").unbind("click");
			$(".table2").click(function() {
				$("#graphicalView").hide();
				$("#tabularView").show();
				$("#graphView").hide();
				$("#testcases").show();
			});
			
			//Shows the graphical view of the test result
			$(".graph1").unbind("click");
			$(".graph1").click(function() {
				$("#testcases").hide();
				$("#graphView").show();
				$("#tabularView").hide();
				$("#graphicalView").show();
			});
			
			//to toggle test steps inside test function
			$(".testFnArrowLeft").unbind("click");
			$(".testFnArrowLeft").click(function() {
				($(this).attr('src') === 'themes/default/images/Phresco/arrow_up_grey.png') ? $(this).attr('src', 'themes/default/images/Phresco/arrow_down_grey.png') 
					: $(this).attr('src', 'themes/default/images/Phresco/arrow_up_grey.png');
				var trElements = $(this).closest('tr').nextUntil($('.testFunctions'));
				$.each(trElements, function(index, trElement) {
					if ($(trElement).hasClass('functionTestSteps')) {
						$(trElement).fadeToggle(200, 'linear');
					} else {
						return false;
					}
				});
			});

			//to toggle test function/steps
			$(".arrowLeft").unbind("click");
			$(".arrowLeft").click(function() {
				var hasFunctions =$(this).attr('hasTestFunctions'), trElements = $(this).closest('tr').nextUntil($('.testCaseRow'));
				($(this).attr('src') === 'themes/default/images/Phresco/arrow_up_grey.png') ? $(this).attr('src', 'themes/default/images/Phresco/arrow_down_grey.png') 
					: $(this).attr('src', 'themes/default/images/Phresco/arrow_up_grey.png');
				$(this).closest('tr').nextUntil($('.testCaseRow')).fadeToggle(400, 'linear');

				if (hasFunctions === 'true') {
					$.each(trElements, function(index, trElement) {
					    ($(trElement).hasClass('functionTestSteps')) ? $(trElement).hide() : "";
					    ($(trElement).hasClass('testFunctions')) ? $(trElement).find('.testFnArrowLeft').attr('src', 'themes/default/images/Phresco/arrow_up_grey.png') : "";
					});
				}
			});

			$('a[name=updateManualTestCase_popup]').click(function() {
				var dynClass = $(this).attr('class');
				self.openccpl(this, dynClass, '');
				var target = $('#' + dynClass);
				$('.features_content_main').prepend(target);
				$('.content_title').css('z-index', '6');
				$('.header_section').css('z-index', '7');
				$('.footer_section').css('z-index', '4');
				$('.manualTemp').css('z-index', '1');
				var testsuiteName = commonVariables.testSuiteName;
				$('#testSuiteId').val(testsuiteName);
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
						
						var currentTab = commonVariables.navListener.currentTab;
						self.testResultListener.getTestsuites(function(response) {
							var data = {};
							if ("manualTest" === currentTab) {
								data.testSuites = response.data.testSuites;
							} 
							data.message = response.message;
							commonVariables.testSuites = response.data;
							
						});
						
					});
				});
			});
			
			$("a[namedel=deleteTestCase]").click(function() {
				var temp = $(this).attr('name');
				self.openccpl(this, $(this).attr('name'));
				$('#'+temp).show();
				$('input[name="delTestCase"]').unbind();
				$('input[name="delTestCase"]').click(function() {
					var testCaseName = $(this).closest("div").parent("div").attr("testCaseName");
					$('input[name="delTestCase"]').closest("div");
					var currentTestsuiteName = commonVariables.testSuiteName;
				//	self.deleteTestCaseEvent.dispatch(testCaseName, currentTestsuiteName);
					var requestBody = {};
					requestBody.testCaseName = testCaseName;
					requestBody.testsuitename = currentTestsuiteName;
					self.testResultListener.performAction(self.testResultListener.getActionHeader(requestBody, "deleteTestCase"), function(response) {
						if(response.data) {
							$("tr[testCaseId='"+testCaseName+"']").remove();
							commonVariables.api.showError(response.responseCode ,"success", true, false, true);
							commonVariables.navListener.getMyObj(commonVariables.testcaseResult, function(retVal) {
								self.testcaseResult = retVal;
								Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
								Clazz.navigationController.push(self.testcaseResult, false);
								
								var currentTab = commonVariables.navListener.currentTab;
								self.testResultListener.getTestsuites(function(response) {
									var data = {};
									if ("manualTest" === currentTab) {
										data.testSuites = response.data.testSuites;
									} 
									data.message = response.message;
									commonVariables.testSuites = response.data;
									
								});
								
							});
						} 
					});
					
				});	
			});
			
			$('.log').unbind("click");
			$('.log').bind("click", function() {
				var id = $(this).attr("resultname");
				if (!$('.testcaseLogDiv').hasClass('mCustomScrollbar _mCS_2')) {
					$('.testcaseLogDiv').mCustomScrollbar({
						autoHideScrollbar:true,
						theme:"light-thin",
						advanced:{updateOnContentResize: true}
					});	
				}
				
				self.opencc(this, id);
			});
			
			//To copy the console log content to the clip-board
			/* $("#buildCopyLog").unbind("click");
			$("#buildCopyLog").click(function() {
				commonVariables.hideloading = true;
				commonVariables.navListener.copyToClipboard($("#testConsole"));
			}); */
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			self.tableScrollbar();
			self.customScroll($(".consolescrolldiv"));
		},
	});

	return Clazz.com.components.testResult.js.Testcase;
});