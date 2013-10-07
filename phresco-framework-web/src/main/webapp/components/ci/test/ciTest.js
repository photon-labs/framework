define(["jquery", "ci/ci", "framework/navigationController", "framework/widgetWithTemplate"], function($, ci) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("ci.js;ci");
		asyncTest("ci Test", function() {
		
			var codequality, output;
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			codequality = new ci();
			Clazz.navigationController.jQueryContainer = $("<div id='applicationTest'></div>");
			Clazz.navigationController.push(codequality, false);
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#ciContent").attr('id');
				equal("codequalityContent", output, "ci Rendered Successfully");
				start();
				require(["continuousDeliveryConfigureTest"], function(continuousDeliveryConfigureTest){
					continuousDeliveryConfigureTest.runTests();
				});
			}, 1500);
			
		});
	}};
	
});
