define(["performanceTest/listener/performanceTestListener"], function() {
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
		onRunPerformanceTestEvent : null,
		onShowHideConsoleEvent : null,
		onShowPdfPopupEvent : null,
		getResultEvent : null,
		getDeviceEvent : null,
		onDeviceChangeEvent : null,
		getResultFilesEvent : null,
		whereToRender : null,
		dynamicpage : null,
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
			commonVariables.testType = commonVariables.performance;
			if (self.performanceTestListener === null ) {
				self.performanceTestListener = new Clazz.com.components.performanceTest.js.listener.PerformanceTestListener();
			}
			if (self.onTabularViewEvent === null) {
				self.onTabularViewEvent = new signals.Signal();
			}
			self.onTabularViewEvent.add(self.performanceTestListener.onTabularView, self.performanceTestListener);
			
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.performanceTestListener.onGraphicalView, self.performanceTestListener);
			
			if (self.onRunPerformanceTestEvent === null) {
				self.onRunPerformanceTestEvent = new signals.Signal();
			}
			
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
			
			if (self.getDeviceEvent === null) {
				self.getDeviceEvent = new signals.Signal();
			}
			self.getDeviceEvent.add(self.performanceTestListener.getDevices, self.performanceTestListener);

			if (self.onDeviceChangeEvent === null) {
				self.onDeviceChangeEvent = new signals.Signal();
			}
			self.onDeviceChangeEvent.add(self.performanceTestListener.getResultOnChangeEvent, self.performanceTestListener);

			if (self.dynamicpage === null){
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
				});
			}

			if (self.preTriggerPerformanceTest === null) {
				self.preTriggerPerformanceTest = new signals.Signal();
			}
			self.preTriggerPerformanceTest.add(self.performanceTestListener.preTriggerPerformanceTest, self.performanceTestListener);

			if (self.onShowPdfPopupEvent === null) {
				self.onShowPdfPopupEvent = new signals.Signal();
			}
			self.onShowPdfPopupEvent.add(self.performanceTestListener.getPdfReports, self.performanceTestListener);


			if (self.onGeneratePdfEvent === null) {
				self.onGeneratePdfEvent = new signals.Signal();
			}
			self.onGeneratePdfEvent.add(self.performanceTestListener.generatePdfReport, self.performanceTestListener);

			self.registerEvents(self.performanceTestListener);
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
			
			//To hide table view, graph view, pdf icons based on test results
			Handlebars.registerHelper('showIcons', function(resultAvailable, testResultFiles, options) {
				if (resultAvailable && testResultFiles.length > 0) {
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
						returnVal += '<li class="testAgainstOption"><a href="#" name="testAgainst" value="'+value+'">'+ value +'</a></li>';
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
							returnVal += '<li class="devicesOption"><a href="#" name="devices" deviceId="'+ value.split("#SEP#")[0] +'">'+ value.split("#SEP#")[1] +'</a></li>';
						});
						returnVal += '<li class="devicesOption"><a href="#" name="devices" deviceId="">All</a></li>';
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
						returnVal += '<li class="testResultFilesOption"><a href="#" name="resultFileName">'+ value +'</a></li>';
					});
				}
				return returnVal;
			});
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(needAnimation) {
			Clazz.navigationController.push(this, needAnimation);
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
			self.performanceTestListener.getPerformanceTestReportOptions(self.performanceTestListener.getActionHeader(self.projectRequestBody, "resultAvailable"), whereToRender, self.handleResponse);
		},

		handleResponse : function(response, whereToRender) {
			var self = this, performanceTest;
			commonVariables.navListener.getMyObj('performanceTest', function(obj){
				performanceTest = obj;
			});
			performanceTest.performanceTestListener.renderPerformanceTemplate(response, performanceTest.renderFnc,  whereToRender);
		},

		setConsoleScrollbar : function(bcheck){
			if(bcheck){
				$("#unit_progress .scrollContent").mCustomScrollbar("destroy");
				$("#unit_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar: false,
					scrollInertia: 1000,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true},
					callbacks:{
						onScrollStart:function(){
							$("#unit_progress .scrollContent").mCustomScrollbar("scrollTo","bottom");
						}
					}
				});
			}else{
				$("#unit_progress .scrollContent").mCustomScrollbar("destroy");
				$("#unit_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar:true,
					scrollInertia: 200,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true}
				});
			}
		},	

		graphDropDownChangeEvent : function (obj) {
			var self = this;
			var previousOption = $("#graphForDrop").attr("value");
			var currentOption = obj.attr("value");
			if (previousOption !== currentOption) {
				$("#graphForDrop").html(obj.text()  + '<b class="caret"></b>');
				$("#graphForDrop").attr("value", obj.attr("value"));
				self.getResultEvent.dispatch($("#testAgainstsDrop").attr("value"),$("#testResultFileDrop").attr("value"), currentOption, $("#deviceDropDown").attr("value"), commonVariables.contentPlaceholder);
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

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			//To show performance popup
			$("input[name=performancePopup]").unbind("click");
			$("input[name=performancePopup]").click(function() {
				self.closeConsole();
                var whereToRender = $('#performancePopup ul');
                commonVariables.goal = "performance-test";
                commonVariables.phase = "performance-test";

                if (whereToRender.children().length < 1) {
                	self.dynamicpage.getHtml(whereToRender, this, $(this).attr('name'), function() {
	                	var sectionHeight = $('.performanceTestResults').height();
						$('#performancePopup').css("max-height", sectionHeight - 40 + 'px');
						$('#performanceForm').css("max-height", sectionHeight - 92 + 'px');
	                	$("#performanceForm").mCustomScrollbar({
							autoHideScrollbar:true,
							theme:"light-thin",
							advanced:{ updateOnContentResize: true}
						});
                	});
                } else {
                	self.opencc(this, $(this).attr('name'));
                }
                
			});
			
			//To open the performance test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typePerformanceTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of performance test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typePerformanceTest;
				commonVariables.navListener.copyPath(paramJson);
			});

			//To show the print as pdf popup
			$('#performancePdf').unbind("click");
			$('#performancePdf').click(function() {
				self.onShowPdfPopupEvent.dispatch();
				$('#pdfReportLoading').show();
				self.opencc(this, 'pdf_report');
			});
			
			//To generate the pdf report
			$('#generatePdf').unbind("click");
			$('#generatePdf').click(function() {
				self.onGeneratePdfEvent.dispatch();
				$('#pdfReportLoading').show();
			});

			//Shows the tabular view of the test result
			$("#tabularView").unbind("click");
			$("#tabularView").click(function() {
				self.onTabularViewEvent.dispatch($(this));
			});
			
			//Shows the graphical view of the test result
			$("#graphicalView").unbind("click");
			$("#graphicalView").click(function() {
				self.onGraphicalViewEvent.dispatch($(this));
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
				$(".perfResultInfo").html('');

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
				var previousDevice = $("#deviceDropDown").attr("value");
				var currentDevice = $(this).attr("deviceid");
				if (previousDevice !== currentDevice) {
					$("#deviceDropDown").html($(this).text()  + '<b class="caret"></b>');
					$("#deviceDropDown").attr("value", currentDevice);
					self.onDeviceChangeEvent.dispatch('',$("#testResultFileDrop").text(), '', currentDevice, self.whereToRender, function(response) {
						self.performanceTestListener.setResponseTime();	
					});
				}
			});

			//To select testAgainst value
			$('li a[name="testAgainst"]').unbind("click");
			$('li a[name="testAgainst"]').click(function() {
				self.testAgainstChangeEvent($(this));
				
			});
			
			$("#performanceRun").click(function() {
				$('.progress_loading').show();
				self.setConsoleScrollbar(true);
				self.preTriggerPerformanceTest.dispatch();
			});

			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.performanceTest.js.PerformanceTest;
});