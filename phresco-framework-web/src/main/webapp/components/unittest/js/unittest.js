define(["framework/widgetWithTemplate", "unittest/listener/unittestListener"], function() {
	Clazz.createPackage("com.components.unittest.js");

	Clazz.com.components.unittest.js.UnitTest = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/unittest/template/unittest.tmp",
		configUrl: "components/unittest/config/config.json",
		name : commonVariables.unittest,
		unittestlistener : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.unittestlistener = new Clazz.com.components.unittest.js.listener.UnitTestlistener();
			self.registerEvents(self.unittestlistener);
		},
		
		registerEvents : function(unittestlistener) {
			var self = this;
			self.onUnitTestResultEvent = new signals.Signal();
			self.onUnitTestDescEvent = new signals.Signal();
			self.onUnitTestGraphEvent = new signals.Signal();
			self.onUnitTestResultEvent.add(unittestlistener.onUnitTestResult, unittestlistener); 
			self.onUnitTestDescEvent.add(unittestlistener.onUnitTestDesc, unittestlistener); 
			self.onUnitTestGraphEvent.add(unittestlistener.onUnitTestGraph, unittestlistener); 
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{
							updateOnContentResize: true
						}
			});
			
			var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
			var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
			var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
			var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
			var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
			var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
			
			$(".fixedHeader tr th:first-child").css("width",w1);
			$(".fixedHeader tr th:nth-child(2)").css("width",w2);
			$(".fixedHeader tr th:nth-child(3)").css("width",w3);
			$(".fixedHeader tr th:nth-child(4)").css("width",w4);
			$(".fixedHeader tr th:nth-child(5)").css("width",w5);
			$(".fixedHeader tr th:nth-child(6)").css("width",w6);
			
			$(window).resize(function() {
				var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				
				var height = $(this).height();
				
				var resultvalue = 0;
				$('.mainContent').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv; 
				});
					var footervalue = $('.footer_section').height();
					resultvalue = resultvalue + footervalue + 200;

				$('.mainContent .scrollContent').height(height - resultvalue);

			});
			
			$("input[name=unitTest]").unbind("click");
			$("input[name=unitTest]").click(function() {
				self.opencc(this,'code_popup');
			});
			
			
			$("#uniTestResultTab").css("display", "none");
			$("#uniTestDesc").css("display", "none");
			$("#unitTestTab").css("display", "block");
			$(".unit_view").css("display", "none");
			$("#graphView").css("display", "none");
			
			
			$("a[name=unittestResult]").unbind("click");
			$("a[name=unittestResult]").click(function() {
				self.onUnitTestResultEvent.dispatch();
			});
			
			$("a[name=unitTestDescription]").unbind("click");
			$("a[name=unitTestDescription]").click(function() {
				self.onUnitTestDescEvent.dispatch();
			});
			
			$("a[name=unitTestGraph]").unbind("click");
			$("a[name=unitTestGraph]").click(function() {
				self.onUnitTestGraphEvent.dispatch();
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.unittest.js.UnitTest;
});