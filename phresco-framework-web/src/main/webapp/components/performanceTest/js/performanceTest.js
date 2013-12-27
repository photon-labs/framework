define(["performanceLoadListener/listener/performanceLoadListener"], function() {
	Clazz.createPackage("com.components.performanceTest.js");

	Clazz.com.components.performanceTest.js.PerformanceTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/performanceTest/template/performanceTest.tmp",
		configUrl: "components/performanceTest/config/config.json",
		name : commonVariables.performanceTest,
		performanceTestListener : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onShowHideConsoleEvent : null,
		onShowPdfPopupEvent : null,
		getResultEvent : null,
		getDeviceEvent : null,
		onDeviceChangeEvent : null,
		getResultFilesEvent : null,
		whereToRender : null,
		dynamicpage : null,
		validation : null,
		dynamicPageListener : null,
		preTriggerPerformanceTest : null,
		onGeneratePdfEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.runType = "performance";
			commonVariables.testType = commonVariables.performance;
			if (self.performanceTestListener === null ) {
				self.performanceTestListener = new Clazz.com.components.performanceLoadListener.js.listener.PerformanceLoadListener();
			}
			self.getDynamicPageObject();
			self.resultViewSignals();
			self.getResultEventSignals();
			self.deviceEventSignals();
			self.pdfReportEventSignals();
			self.testTriggerSignals();
			self.registerEvents(self.performanceTestListener);
		},
		
		getDynamicPageObject : function () {
			var self = this;
			if (self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
				});
			}
		},

		resultViewSignals : function () {
			var self = this;
			if (self.onTabularViewEvent === null) {
				self.onTabularViewEvent = new signals.Signal();
			}
			self.onTabularViewEvent.add(self.performanceTestListener.onTabularView, self.performanceTestListener);
			
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.performanceTestListener.onGraphicalView, self.performanceTestListener);
		},

		getResultEventSignals : function () {
			var self = this;
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			self.onShowHideConsoleEvent.add(self.performanceTestListener.showHideConsole, self.performanceTestListener);
						
			if (self.getResultEvent === null) {
				self.getResultEvent = new signals.Signal();
			}
			self.getResultEvent.add(self.performanceTestListener.getResultOnChangeEvent, self.performanceTestListener);
									
			if (self.getResultFilesEvent === null) {
				self.getResultFilesEvent = new signals.Signal();
			}
			self.getResultFilesEvent.add(self.performanceTestListener.getResultFiles, self.performanceTestListener);
		},

		deviceEventSignals : function () {
			var self = this;
			if (self.getDeviceEvent === null) {
				self.getDeviceEvent = new signals.Signal();
			}
			self.getDeviceEvent.add(self.performanceTestListener.getDevices, self.performanceTestListener);

			if (self.onDeviceChangeEvent === null) {
				self.onDeviceChangeEvent = new signals.Signal();
			}
			self.onDeviceChangeEvent.add(self.performanceTestListener.getResultOnChangeEvent, self.performanceTestListener);
		},

		pdfReportEventSignals : function () {
			var self = this;
			if (self.onShowPdfPopupEvent === null) {
				self.onShowPdfPopupEvent = new signals.Signal();
			}
			self.onShowPdfPopupEvent.add(self.performanceTestListener.getPdfReports, self.performanceTestListener);


			if (self.onGeneratePdfEvent === null) {
				self.onGeneratePdfEvent = new signals.Signal();
			}
			self.onGeneratePdfEvent.add(self.performanceTestListener.generatePdfReport, self.performanceTestListener);
		},

		testTriggerSignals : function () {
			var self = this;

			if (self.validation === null) {
				self.validation = new signals.Signal();
			}
			self.validation.add(self.performanceTestListener.mandatoryValidation, self.performanceTestListener);

			if (self.preTriggerPerformanceTest === null) {
				self.preTriggerPerformanceTest = new signals.Signal();
			}
			self.preTriggerPerformanceTest.add(self.performanceTestListener.preTriggerPerformanceTest, self.performanceTestListener);
		},

		registerEvents : function(performanceTestListener) {
			var self = this;
			
			//To show performance not yet executed for server/ws/db msg
			Handlebars.registerHelper('showErrMsg', function(resultAvailable, testResultFiles, options) {
				if (resultAvailable && testResultFiles.length === 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});
			
			//To return server/ws/db
			Handlebars.registerHelper('against', function(testAgainsts) {
				if (testAgainsts !== undefined && testAgainsts.length > 0) {
					return testAgainsts[0];
				}
			});
			
			//To hide table view, graph view based on test results
			Handlebars.registerHelper('showIcons', function(resultAvailable, testResultFiles, options) {
				if (resultAvailable && testResultFiles.length > 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}
			});
			
			//To hide pdf icons based if no results available
			Handlebars.registerHelper('showPdfIcon', function(resultAvailable, options) {
				if (resultAvailable) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}
			});

			//To show/hide test against drop down
			Handlebars.registerHelper('showTestAgainst', function(showDevice, resultAvailable, options) {
				if (!showDevice && resultAvailable) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});
			
			//To show/hide result file drop down
			Handlebars.registerHelper('showResultFiles', function(testResultFiles, options) {
				if (testResultFiles !== null && testResultFiles !== undefined && testResultFiles.length > 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});
			
			//To load test againsts drop down
			Handlebars.registerHelper('testAgainsts', function(data, firstVal) {
				var returnVal = "";
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, value){
						returnVal += '<li class="testAgainstOption"><a href="javascript:void(0)" name="testAgainst" value="'+value+'">'+ value +'</a></li>';
					});
				}
				return returnVal;
			});
			
			//To load devices drop down
			Handlebars.registerHelper('devices', function(showDevice, devices, testResultFiles, firstValue, id) {
				var returnVal = "";
				if (showDevice && testResultFiles !== undefined && testResultFiles.length > 0 && !$.isEmptyObject(devices)) {
					if (firstValue && id) {
						returnVal = devices[0].split("#SEP#")[0];
					} else if (firstValue && !id) {
						returnVal = devices[0].split("#SEP#")[1];
					} else {
						$.each(devices, function(i, value){
							returnVal += '<li class="devicesOption"><a href="javascript:void(0)" name="devices" deviceId="'+ value.split("#SEP#")[0] +'">'+ value.split("#SEP#")[1] +'</a></li>';
						});
						returnVal += '<li class="devicesOption"><a href="javascript:void(0)" name="devices" deviceId="">All</a></li>';
					} 
				} 
				
				return returnVal;
			});
			
			//To show/hide device dropdown
			Handlebars.registerHelper('showDeviceDropDown', function(showDevice, devices, options) {
				var returnVal = "";
				if (showDevice && devices.length > 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				} 
			});
			
			//To load result file in results drop down
			Handlebars.registerHelper('testResultFiles', function(data, firstVal) {
				var returnVal = "";
				if (firstVal && data !== null) {
					returnVal = data[0];
				} else if ( data !== null) {
					$.each(data, function(index, value){
						returnVal += '<li class="testResultFilesOption"><a href="javascript:void(0)" name="resultFileName">'+ value +'</a></li>';
					});
				}
				return returnVal;
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this, performanceLog = "", consoleLog = commonVariables.api.localVal.getSession('performanceConsole');
			self.performanceTestListener.resizeConsoleWindow();
			self.showHideSysSpecCtrls();
			self.performanceTestListener.resultBodyResize();
			
			if (!self.isBlank(consoleLog)) {
				performanceLog = consoleLog;
			}

			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(performanceLog);
			
			commonVariables.api.localVal.setSession('performanceConsole', '');
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.whereToRender = whereToRender;
			self.performanceTestListener.getTestReportOptions(self.performanceTestListener.getActionHeader(self.projectRequestBody, "resultAvailable"), whereToRender, self.handleResponse);
		},

		handleResponse : function(response, whereToRender) {
			var self = this, performanceTest;
			commonVariables.navListener.getMyObj('performanceTest', function(obj){
				performanceTest = obj;
			});
			performanceTest.performanceTestListener.renderPerformanceTemplate(response, performanceTest.renderFnc,  whereToRender);
		},

		graphDropDownChangeEvent : function (obj) {
			var self = this;
			var previousOption = $("#graphForDrop").attr("value");
			var currentOption = obj.attr("value");
			if (previousOption !== currentOption) {
				self.getResultEvent.dispatch($("#testAgainstsDrop").attr("value"),$("#testResultFileDrop").attr("value"), currentOption, $("#deviceDropDown").attr("value"), commonVariables.contentPlaceholder, function(response) {
					if(response.status === "success") {
						$("#graphForDrop").html(obj.text()  + '<b class="caret"></b>');
						$("#graphForDrop").attr("value", obj.attr("value"));
					}
				});
				if (currentOption !== "all") {
					$("#allData").hide();
				} else {
					$("#allData").show();
				}
			}
		},

		testAgainstChangeEvent : function (obj) {
			var self = this, currentOption = obj.text();
			$("#testAgainstsDrop").html(currentOption + '<b class="caret"></b>');
			$("#testAgainstsDrop").attr("value", currentOption);
			$(".perfResultInfo").html('');
			self.getResultFilesEvent.dispatch(currentOption, commonVariables.contentPlaceholder);
		},

		deviceChangeEvent : function (obj) {
			var self = this, previousDevice = $("#deviceDropDown").attr("value");
			var currentDevice = obj.attr("deviceid");
			if (previousDevice !== currentDevice) {
				$("#deviceDropDown").html(obj.text()  + '<b class="caret"></b>');
				$("#deviceDropDown").attr("value", currentDevice);
				self.onDeviceChangeEvent.dispatch('',$("#testResultFileDrop").text(), '', currentDevice, commonVariables.contentPlaceholder, function(response) {
					self.performanceTestListener.setResponseTime();	
				});
			}
		},
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.killProcess();
			$(".tooltiptop").tooltip();

			//To show performance popup
			$("input[name=performancePopup]").unbind("click");
			$("input[name=performancePopup]").click(function() {
				var openccObj = this, openccObjName = $(this).attr('name');
				self.checkForLock("performance", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						self.closeConsole();
		                var whereToRender = $('#performancePopup ul');
		                commonVariables.goal = "performance-test";
		                commonVariables.phase = "performance-test";

		                if (whereToRender.children().length < 1) {
		                	self.dynamicpage.getHtml(whereToRender, openccObj, openccObjName, function() {
		                		var totalControls = whereToRender.find('li.ctrl').length;
		                		if (totalControls > 3) {
		        					var sectionHeight = $('.performanceTestResults').height();
									$('#performancePopup').css("max-height", sectionHeight - 40 + 'px');
									$('#performanceForm').css("max-height", sectionHeight - 92 + 'px');
				                	$("#performanceForm").mCustomScrollbar({
										autoHideScrollbar:true,
										theme:"light-thin",
										advanced:{ updateOnContentResize: true}
									});
		                		}
		                	});
		                } else {
		                	commonVariables.api.hideLoading();
		                	self.opencc(openccObj, openccObjName);
		                }
                	} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}	
				});	
			});
			
			//To open the performance test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typePerformanceTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of performance test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typePerformanceTest;
				commonVariables.navListener.copyPath(paramJson);
			});

			//To show the print as pdf popup
			$('.performancePDF').unbind("click");
			$('.performancePDF').click(function() {
				self.onShowPdfPopupEvent.dispatch('performance');
				$('#pdfReportLoading').show();
				self.opencc(this, 'pdf_report');
			});
			
			//To generate the pdf report
			$('#generatePdf').unbind("click");
			$('#generatePdf').click(function() {
				self.onGeneratePdfEvent.dispatch('performance');
			});

			//Shows the tabular view of the test result
			$(".table2").unbind("click");
			$(".table2").click(function() {
				self.onTabularViewEvent.dispatch(this);
			});
			
			//Shows the graphical view of the test result
			$(".graph1").unbind("click");
			$(".graph1").click(function() {
				self.onGraphicalViewEvent.dispatch(this);
			});
			
			//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});	
			
			//To select the test result file
			$('li a[name="resultFileName"]').unbind("click");
			$('li a[name="resultFileName"]').click(function() {
				var currentOption = $(this).text();
				$("#testResultFileDrop").html(currentOption  + '<b class="caret"></b>');
				$("#testResultFileDrop").attr("value", currentOption);

				if (!self.isBlank($("#testAgainstsDrop").attr("value"))) {
					self.getResultEvent.dispatch($("#testAgainstsDrop").attr("value"), $(this).text(), $("#graphForDrop").attr("value"), '', self.whereToRender);
				} else {
					self.getDeviceEvent.dispatch($("#testResultFileDrop").attr("value"), $("#graphForDrop").attr("value"), self.whereToRender);
				}
			});

			//To select the show graph based on
			$('li a[name="graphForDrop"]').unbind("click");
			$('li a[name="graphForDrop"]').click(function() {
				self.graphDropDownChangeEvent($(this));
			});

			$('li a[name="devices"]').unbind("click");
			$('li a[name="devices"]').click(function() {
				self.deviceChangeEvent($(this));
			});

			//To select testAgainst value
			$('li a[name="testAgainst"]').unbind("click");
			$('li a[name="testAgainst"]').click(function() {
				self.testAgainstChangeEvent($(this));
			});
			
			self.customScroll($(".consolescrolldiv")); 
			
			$("#performanceRun").click(function() {
				//To disable template contents
				$('.templates').find('input ,select,textarea').prop('disabled', true);
				self.validation.dispatch("performance-test", $('#performanceForm :input[name!=parameterValue]').serialize(), self.dynamicpage);
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.performanceTest.js.PerformanceTest;
});