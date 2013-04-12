define(["jquery", "header/header", "framework/navigationController", "framework/widgetWithTemplate"], function($, Header) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("Header.js;Header");
		asyncTest("Header Test", function() {
		
			var header, output;
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			header = new Header();
			Clazz.navigationController.jQueryContainer = $("<div id='headerTest'></div>");
			Clazz.navigationController.push(header, false);
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#header").attr('id');
				equal("header", output, "Header Rendered Successfully");
				start();
			}, 1500);
			
		});
	}};
	
});
