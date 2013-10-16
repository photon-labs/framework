define(["integrationTest/listener/integrationTestListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.integrationTest.js");

	Clazz.com.components.integrationTest.js.IntegrationTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/integrationTest/template/integrationTest.tmp",
		configUrl: "components/integrationTest/config/config.json",
		name : commonVariables.integrationTest,
		integrationTestListener : null,
		testsuiteResult : null,
		testResultListener : null,
		// onTabularViewEvent : null,
		// onGraphicalViewEvent : null,
		onRunIntegrationTestEvent : null,
		onDynamicPageEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.integration;
			if (self.integrationTestListener === null ) {
				self.integrationTestListener = new Clazz.com.components.integrationTest.js.listener.IntegrationTestListener();
			}
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			//self.onDynamicPageEvent.add(self.integrationTestListener.getDynamicParams, self.integrationTestListener);

			if (self.onRunIntegrationTestEvent === null) {
				self.onRunIntegrationTestEvent = new signals.Signal();
			}
			self.onRunIntegrationTestEvent.add(self.integrationTestListener.runIntegrationTest, self.integrationTestListener);
			
			self.registerEvents(self.integrationTestListener);
		},
		
		registerEvents : function(integrationTestListener) {
			var self = this;
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
				var data = {};
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				data.userPermissions = userPermissions;
				renderFunction(data, whereToRender);
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			self.windowResize();
			
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
			
			$("#ntegrationTestBtn").unbind("click");
			$("#ntegrationTestBtn").click(function() {
				self.onDynamicPageEvent.dispatch(this, function() {
					self.testResult.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testResult, false);
				});
			});
			
			$("#testSuites").css("display", "none");
			$("#testCases").css("display", "none");
			$("#unitTestTab").css("display", "block");
			$(".unit_view").css("display", "none");
			$("#graphView").css("display", "none");
			
			//Change event of the report type to get the report
			$('.projectModule').click(function() {
				$('#modulesDrop').attr("value", $(this).children().text());
				$('#modulesDrop').html($(this).children().text() + '<b class="caret"></b>');
				
				$('#testResult').empty();
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testResult, false);
			});
			
			//Change event of the report type to get the report
			$('.reportOption').click(function() {
				$('#reportOptionsDrop').attr("value", $(this).children().text());
				$('#reportOptionsDrop').html($(this).children().text() + '<b class="caret"></b>');
				
				$('#testResult').empty();
				Clazz.navigationController.jQueryContainer = '#testResult';
				Clazz.navigationController.push(self.testResult, false);
			});

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
			
			//To open the Integration test directory
			$('#openFolder').unbind('click');
			$("#openFolder").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeIntegrationTest;
				commonVariables.navListener.openFolder(paramJson);
			});
			
			//To copy the path of Integration test directory
			$('#copyPath').unbind('click');
			$("#copyPath").click(function() {
				var paramJson = {};
				paramJson.type =  commonVariables.typeIntegrationTest;
				commonVariables.navListener.copyPath(paramJson);
			});

			/*$('#integrationTestBtn').unbind('click');
			$("#integrationTestBtn").click(function() {
				var openccObj = this;
				console.info("$('#integrationTestDynCtrls'):::::", $('#integrationTestDynCtrls'));
				console.info("commonVariables.intergrationTestGoal:::", commonVariables.integrationTestGoal);
				self.onDynamicPageEvent.dispatch(openccObj, $('#integrationTestDynCtrls'), 'integration_popup', commonVariables.intergrationTestGoal);
			});*/
			
			//To run the unit test
			$("#runIntegrationTest").unbind("click");
			$("#runIntegrationTest").click(function() {
				self.onRunUnitTestEvent.dispatch(function() {
					self.testResult.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testResult, false);
				});
				$("#unit_popup").toggle();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.integrationTest.js.IntegrationTest;
});