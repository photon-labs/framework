define(["performanceLoadListener/listener/performanceLoadListener"], function() {
	Clazz.createPackage("com.components.loadTest.js");

	Clazz.com.components.loadTest.js.LoadTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/loadTest/template/loadTest.tmp",
		configUrl: "components/loadTest/config/config.json",
		name : commonVariables.loadTest,
		loadTestListener : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onShowHideConsoleEvent : null,
		onShowPdfPopupEvent : null,
		getResultEvent : null,
		getResultFilesEvent : null,
		whereToRender : null,
		validation : null,
		dynamicpage : null,
		dynamicPageListener : null,
		preTriggerloadTest : null,
		onGeneratePdfEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.load;
			if (self.loadTestListener === null ) {
				self.loadTestListener = new Clazz.com.components.performanceLoadListener.js.listener.PerformanceLoadListener();
			}
			self.getDynamicPageObject();
			self.resultViewSignals();
			self.getResultEventSignals();
			self.pdfReportEventSignals();
			self.testTriggerSignals();
			self.registerEvents(self.loadTestListener);
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
			self.onTabularViewEvent.add(self.loadTestListener.onTabularView, self.loadTestListener);
			
			if (self.onGraphicalViewEvent === null) {
				self.onGraphicalViewEvent = new signals.Signal();
			}
			self.onGraphicalViewEvent.add(self.loadTestListener.onGraphicalView, self.loadTestListener);
		},

		getResultEventSignals : function () {
			var self = this;
			if (self.onShowHideConsoleEvent === null) {
				self.onShowHideConsoleEvent = new signals.Signal();
			}
			self.onShowHideConsoleEvent.add(self.loadTestListener.showHideConsole, self.loadTestListener);
						
			if (self.getResultEvent === null) {
				self.getResultEvent = new signals.Signal();
			}
			self.getResultEvent.add(self.loadTestListener.getResultOnChangeEvent, self.loadTestListener);
									
			if (self.getResultFilesEvent === null) {
				self.getResultFilesEvent = new signals.Signal();
			}
			self.getResultFilesEvent.add(self.loadTestListener.getResultFiles, self.loadTestListener);
		},

		pdfReportEventSignals : function () {
			var self = this;
			if (self.onShowPdfPopupEvent === null) {
				self.onShowPdfPopupEvent = new signals.Signal();
			}
			self.onShowPdfPopupEvent.add(self.loadTestListener.getPdfReports, self.loadTestListener);


			if (self.onGeneratePdfEvent === null) {
				self.onGeneratePdfEvent = new signals.Signal();
			}
			self.onGeneratePdfEvent.add(self.loadTestListener.generatePdfReport, self.loadTestListener);
		},

		testTriggerSignals : function () {
			var self = this;
			if (self.validation === null) {
				self.validation = new signals.Signal();
			}
			self.validation.add(self.loadTestListener.mandatoryValidation, self.loadTestListener);

			if (self.preTriggerloadTest === null) {
				self.preTriggerloadTest = new signals.Signal();
			}
			self.preTriggerloadTest.add(self.loadTestListener.preTriggerloadTest, self.loadTestListener);
		},

		registerEvents : function(loadTestListener) {
			var self = this;
			
			//To show performance not yet executed for server/ws/db msg
			Handlebars.registerHelper('showErrMsg', function(resultAvailable, testResultFiles, options) {
				if (resultAvailable && testResultFiles.length === 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});
			
			//To return server/ws
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
						returnVal += '<li class="testAgainstOption"><a href="javascript:void(0)" name="testAgainst" value="'+value+'">'+ value +'</a></li>';
					});
				}
				return returnVal;
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
			var self = this, loadLog = "", consoleLog = commonVariables.api.localVal.getSession('loadConsole');
			self.loadTestListener.resizeConsoleWindow();
			self.loadTestListener.resultBodyResize();
			
			if (!self.isBlank(consoleLog)) {
				loadLog = consoleLog;
			}

			//To show the log after reloading the test result once the test execution is completed
			$('#testConsole').html(loadLog);

			
			commonVariables.api.localVal.setSession('loadConsole', '');
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.whereToRender = whereToRender;
			self.loadTestListener.getTestReportOptions(self.loadTestListener.getActionHeader(self.projectRequestBody, "loadResultAvailable"), whereToRender, self.handleResponse);
		},

		handleResponse : function(response, whereToRender) {
			var self = this, loadTest;
			commonVariables.navListener.getMyObj('loadTest', function(obj){
				loadTest = obj;
			});
			loadTest.loadTestListener.renderLoadTemplate(response, loadTest.renderFnc,  whereToRender);
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

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			//To show performance popup
			$("input[name=loadPopup]").unbind("click");
			$("input[name=loadPopup]").click(function() {
				var openccObj = this, openccObjName = $(this).attr('name');
				self.checkForLock("load", '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						self.closeConsole();
		                var whereToRender = $('#loadPopup ul');
		                commonVariables.goal = "load-test";
		                commonVariables.phase = "load-test";
		                if (whereToRender.children().length < 1) {
		                	self.dynamicpage.getHtml(whereToRender, openccObj, openccObjName, function() {
		                		var totalControls = whereToRender.find('li.ctrl').length;
		                		if (totalControls > 3) {
				                	var sectionHeight = $('.performanceTestResults').height();
									$('#loadPopup').css("max-height", sectionHeight - 40 + 'px');
									$('#loadForm').css("max-height", sectionHeight - 92 + 'px');
				                	$("#loadForm").mCustomScrollbar({
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
			$('#openLoadFolder').unbind('click');
			$("#openLoadFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeLoadTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of performance test directory
			$('#copyLoadPath').unbind('click');
			$("#copyLoadPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeLoadTest;
				commonVariables.navListener.copyPath(paramJson);
			});

			//To copy the console log content to the clip-board
			$('#copyLog').unbind("click");
			$('#copyLog').click(function() {
				commonVariables.navListener.copyToClipboard($('#testConsole'));
			});

			//To show the print as pdf popup
			$('.loadPDF').unbind("click");
			$('.loadPDF').click(function() {
				self.onShowPdfPopupEvent.dispatch('load');
				$('#pdfReportLoading').show();
				self.opencc(this, 'pdf_report');
			});
			
			//To generate the pdf report
			$('#generatePdf').unbind("click");
			$('#generatePdf').click(function() {
				var from = $(this).parent().find('input[name=fromPage]').val();
				self.onGeneratePdfEvent.dispatch(from);
			});

			//Shows the tabular view of the test result
			$(".table2").unbind("click");
			$(".table2").click(function() {
				self.onTabularViewEvent.dispatch($(this));
			});
			
			//Shows the graphical view of the test result
			$(".graph1").unbind("click");
			$(".graph1").click(function() {
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

			//To select testAgainst value
			$('li a[name="testAgainst"]').unbind("click");
			$('li a[name="testAgainst"]').click(function() {
				self.testAgainstChangeEvent($(this));
			});
			
			self.customScroll($(".consolescrolldiv")); 
			
			$("#loadRun").click(function() {
				self.validation.dispatch("load-test", $('#loadForm').serialize(), self.dynamicpage);
			});
			
			//To copy the console log content to the clip-board
			$('#copyLog').unbind("click");
			$('#copyLog').click(function() {
				commonVariables.hideloading = true;
				commonVariables.navListener.copyToClipboard($('#testConsole'));
			});

			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.loadTest.js.LoadTest;
});