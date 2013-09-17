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
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.unitTestListener.getUnitTestReportOptions(self.unitTestListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var responseData = response.data;
				var unitTestOptions = {};
				unitTestOptions.reportOptions = responseData.reportOptions;
				unitTestOptions.projectModules = responseData.projectModules;
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				unitTestOptions.userPermissions = userPermissions;
				renderFunction(unitTestOptions, whereToRender);
			});
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
				self.checkForLock("unit", '', function(response){
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
			});
			
			//Change event of the report type to get the report
			$('.projectModule').click(function() {
				$('#modulesDrop').attr("value", $(this).children().text());
				$('#modulesDrop').html($(this).children().text() + '<b class="caret"></b>');
				
				$('#testResult').empty();
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			
			//Change event of the report type to get the report
			$('.reportOption').click(function() {
				$('#reportOptionsDrop').attr("value", $(this).children().text());
				$('#reportOptionsDrop').html($(this).children().text() + '<b class="caret"></b>');
				
				$('#testResult').empty();
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			
			//To open the unit test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of unit test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeUnitTest;
				commonVariables.navListener.copyPath(paramJson);
			});
			
			//To run the unit test
			$("#runUnitTest").unbind("click");
			$("#runUnitTest").click(function() {
				self.onRunUnitTestEvent.dispatch(function() {
					commonVariables.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
				$("#unit_popup").toggle();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.unitTest.js.UnitTest;
});