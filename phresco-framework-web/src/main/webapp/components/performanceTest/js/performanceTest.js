define(["performanceTest/listener/performanceTestListener"], function() {
	Clazz.createPackage("com.components.performanceTest.js");

	Clazz.com.components.performanceTest.js.PerformanceTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/performanceTest/template/performanceTest.tmp",
		configUrl: "components/performanceTest/config/config.json",
		name : commonVariables.performanceTest,
		performanceTestListener : null,
		performanceTestAPI : null,
		onTabularViewEvent : null,
		onGraphicalViewEvent : null,
		onDynamicPageEvent : null,
		onRunPerformanceTestEvent : null,
		onShowHideConsoleEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.performance;
			if (self.performanceTestAPI === null) {
				self.performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
			}
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
			
			self.registerEvents(self.performanceTestListener);
		},
		
		registerEvents : function(performanceTestListener) {
			var self = this;
			
			//To show performance not yet executed for server/ws/db msg
			Handlebars.registerHelper('showErrMsg', function(resultAvailable, testResultFiles, options) {
				if (resultAvailable && testResultFiles.length == 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}	
			});
			
			//To return server/ws/db
			Handlebars.registerHelper('against', function(testAgainsts) {
				if (testAgainsts != undefined && testAgainsts.length > 0) {
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
				if (testResultFiles != undefined && testResultFiles.length > 0) {
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
						returnVal += '<li class="testAgainstOption"><a href="#">'+ value +'</a></li>';
					});
				}
				return returnVal;
			});
			
			//To load devices drop down
			Handlebars.registerHelper('devices', function(showDevice, devices, testResultFiles, firstValue, id) {
				var returnVal = "";
				if (showDevice && testResultFiles != undefined && testResultFiles.length > 0 && !$.isEmptyObject(devices)) {
					if (firstValue && id) {
						returnVal = devices[0].split("#SEP#")[0];
					} else if (firstValue && !id) {
						returnVal = devices[0].split("#SEP#")[1];
					} else {
						$.each(devices, function(i, value){
							returnVal += '<li class="devicesOption"><a href="#" deviceId="'+ value.split("#SEP#")[0] +'">'+ value.split("#SEP#")[1] +'</a></li>';
						});
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
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, value){
						returnVal += '<li class="testResultFilesOption"><a href="#">'+ value +'</a></li>';
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
			var self = this;
			self.performanceTestListener.resizeConsoleWindow();
			self.performanceTestListener.resultBodyResize();
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.performanceTestListener.getPerformanceTestReportOptions(self.performanceTestListener.getActionHeader(self.projectRequestBody, "resultAvailable"), whereToRender, self.handleResponse);
		},

		handleResponse : function(response, whereToRender) {
			var performanceTest;
			commonVariables.navListener.getMyObj('performanceTest', function(obj){
				performanceTest = obj;
			});
			performanceTest.performanceTestListener.renderPerformanceTemplate(response, performanceTest.renderFnc,  whereToRender);
		},
	
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
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
			
			//To show hide the console content when the console is clicked
			$('#consoleImg').unbind("click");
			$('#consoleImg').click(function() {
				self.onShowHideConsoleEvent.dispatch();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.performanceTest.js.PerformanceTest;
});