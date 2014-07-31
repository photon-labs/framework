define(["unitTest/listener/unitTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.unitTest.js");

	Clazz.com.components.unitTest.js.UnitTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/unitTest/template/unitTest.tmp",
		configUrl: "components/unitTest/config/config.json",
		name : commonVariables.unitTest,
		unitTestListener : null,
		testsuiteResult : null,
		testResultListener : null,
		onDynamicPageEvent : null,
		onRunUnitTestEvent : null,
		validation : null,

		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.unit;
			if (self.unitTestListener === null ) {
				self.unitTestListener = new Clazz.com.components.unitTest.js.listener.UnitTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.unitTestListener.getDynamicParams, self.unitTestListener);
			
			if (self.onRunUnitTestEvent === null) {
				self.onRunUnitTestEvent = new signals.Signal();
			}
			self.onRunUnitTestEvent.add(self.unitTestListener.runUnitTest, self.unitTestListener);
			
			if (self.validation === null) {
				self.validation = new signals.Signal();
			}
			self.validation.add(self.testResultListener.mandatoryValidation, self.testResultListener);
			
			self.registerEvents(self.unitTestListener);
		},
		
		registerEvents : function(unitTestListener) {
			var self = this;
			Handlebars.registerHelper('report', function(data, firstVal) {
				var returnVal = "";
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, current){
						returnVal += '<li class="reportOption"><a href="javascript:void(0)">'+ current +'</a></li>';
					});
				}
				return returnVal;
			});
			
			Handlebars.registerHelper('modules', function(data, firstVal) {
				var returnVal = "";
				if (firstVal) {
					returnVal = data[0];
				} else {
					$.each(data, function(index, current){
						returnVal += '<li class="projectModule"><a href="javascript:void(0)">'+ current +'</a></li>';
					});
				}
				return returnVal;
			});
			
			Handlebars.registerHelper('testResult', function(showDevice, testResult, firstValue, id) {
				var returnVal = "";
				if (showDevice!== undefined && testResult.length > 0 && !$.isEmptyObject(testResult)) {
					if (firstValue && id) {
						returnVal = testResult[0].split("#SEP#")[0];
					} else if (firstValue && !id) {
						returnVal = testResult[0].split("#SEP#")[1];
					} else {
						$.each(testResult, function(i, value){
							returnVal += '<li class="devicesOption"><a href="javascript:void(0)" name="testResult" deviceId="'+ value.split("#SEP#")[0] +'">'+ value.split("#SEP#")[0] +'</a></li>';
						});
						 returnVal += '<li class="devicesOption"><a href="javascript:void(0)" name="testResult" deviceId="">All</a></li>';
						
					} 
				} 
				
				return returnVal;
			});
			
			//To show/hide device dropdown
			Handlebars.registerHelper('showDeviceDropDown', function(showDevice, testResult, options) {
				var returnVal = "";
				if (showDevice && testResult.length > 0) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				} 
			});

		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(needAnimation) {
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
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
			commonVariables.navListener.getMyObj(commonVariables.testsuiteResult, function(retVal) {
				self.testsuiteResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			
			//commonVariables.currentDevice = $('li a[name="testResult"]').attr("deviceid");
			
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.unitTestListener.getUnitTestReportOptions(self.unitTestListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var responseData = response.data;
				var unitTestOptions = {};
				unitTestOptions.reportOptions = responseData.reportOptions;
				unitTestOptions.projectModules = responseData.projectModules;
				unitTestOptions.testAgainsts = responseData.testAgainsts;
			    unitTestOptions.resultAvailable = responseData.resultAvailable;
			    unitTestOptions.showDevice = responseData.showDevice;
			    unitTestOptions.testResultFiles = responseData.testResultFiles;
				unitTestOptions.testResult = responseData.testResult;
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				unitTestOptions.userPermissions = userPermissions;
				renderFunction(unitTestOptions, whereToRender);
				self.handleDevice(unitTestOptions);
			});
		},
		
		handleDevice : function(unitTestOptions){
		 if(unitTestOptions.showDevice !== null && unitTestOptions.showDevice !== ""){
		  console.info("enetring if");
		  commonVariables.currentDevice = $('li a[name="testResult"]').attr("deviceid");
		 }
		 else{
		  console.info("enetring else");
		  commonVariables.currentDevice = "";
		 }
		},
		
		reportModifier : function(data) {
			var returnVal = "";
			$.each(data, function(index, current){
				returnVal += '<li class="reportOption"><a href="javascript:void(0)">'+ current +'</a></li>';
			});
			$(".reportTechOptions").html(returnVal);
			
			$('#reportOptionsDrop').attr("value", data[0]);
			$('#reportOptionsDrop').html(data[0] + '<b class="caret"></b>');
			
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			$("#unitTestBtn").unbind("click");
			$("#unitTestBtn").click(function() {
				var btnObj = this;
				self.checkForLock("unit", '', '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						self.onDynamicPageEvent.dispatch(btnObj, function() {
							commonVariables.logContent = $('#testConsole').html();
							$('#testResult').empty();
							Clazz.navigationController.jQueryContainer = '#testResult';
							Clazz.navigationController.push(self.testsuiteResult, false);
						});
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}	
				});	
				self.copyLog();
			});
			
			//Change event of the report type to get the report
			$('.projectModule').click(function() {
				$('#modulesDrop').attr("value", $(this).children().text());
				$('#modulesDrop').html($(this).children().text() + '<b class="caret"></b>');
				self.projectRequestBody = $(this).children().text();
				self.unitTestListener.getUnitTestReportOptions(self.unitTestListener.getActionHeader(self.projectRequestBody, "getTechOptions"), function(response) {
					$(".reportTechOptions").empty();
					self.reportModifier(response.data);
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
				
			});
			
			//Change event of the report type to get the report
			$('.reportTechOptions').on("click", ".reportOption", function() {
				$('#reportOptionsDrop').attr("value", $(this).children().text());
				$('#reportOptionsDrop').html($(this).children().text() + '<b class="caret"></b>');
				
				$('#testResult').empty();
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			
			//To open the unit test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of unit test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				commonVariables.hideloading = true;
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			$('li a[name="testResult"]').unbind("click");
			$('li a[name="testResult"]').click(function() {
			   var previousDevice =  $("#deviceDropDown").attr("value");
			   commonVariables.currentDevice = $(this).attr("deviceid");
			if (previousDevice !== commonVariables.currentDevice) {
				$("#deviceDropDown").html($(this).text()  + '<b class="caret"></b>');
				$("#deviceDropDown").attr("value", commonVariables.currentDevice);
			  }
			  commonVariables.navListener.getMyObj(commonVariables.testsuiteResult, function(retVal) {
				self.testsuiteResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			});
			
			//To run the unit test
			$("#runUnitTest").unbind("click");
			$("#runUnitTest").click(function() {
				self.validation.dispatch("unit-test", "unit-test", $("#unitTestForm").serialize(), function (status) {
					if (status) {
						self.onRunUnitTestEvent.dispatch(function() {
							commonVariables.logContent = $('#testConsole').html();
							$('#testResult').empty();
							Clazz.navigationController.jQueryContainer = '#testResult';
							Clazz.navigationController.push(self.testsuiteResult, false);
						});
						$("#unit_popup").toggle();
					}
				});
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.unitTest.js.UnitTest;
});