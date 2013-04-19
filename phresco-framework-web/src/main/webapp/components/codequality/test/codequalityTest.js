define(["jquery", "codequality/codequality", "framework/navigationController", "framework/widgetWithTemplate"], function($, Codequality) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("Codequality.js;Codequality");
		asyncTest("Codequality Test", function() {
		
			var codequality, output;
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			codequality = new Codequality();
			Clazz.navigationController.jQueryContainer = $("<div id='applicationTest'></div>");
			Clazz.navigationController.push(codequality, false);
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#codequalityContent").attr('id');
				equal("codequalityContent", output, "Codequality Rendered Successfully");
				start();
			}, 1500);
			
		});
	}};
	
});
