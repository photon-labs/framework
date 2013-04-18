define(["jquery", "application/application", "framework/navigationController", "framework/widgetWithTemplate"], function($, Application) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("Application.js;Application");
		asyncTest("Application Test", function() {
		
			var application, output;
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			application = new Application();
			Clazz.navigationController.jQueryContainer = $("<div id='applicationTest'></div>");
			Clazz.navigationController.push(application, false);
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#appinfo").attr('id');
				equal("appinfo", output, "Application Rendered Successfully");
				start();
			}, 1500);
			
		});
	}};
	
});
