define(["jquery", "features/features", "framework/navigationController", "framework/widgetWithTemplate", , "features/listener/featuresListener"], function($, Features) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("Features.js;Features");
		asyncTest("Features Test", function() {
		
			var features, output;
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});

			features = new Features();
			Clazz.navigationController.jQueryContainer = $("<div id='featuresTest'></div>");
			Clazz.navigationController.push(features, false);
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#featureContent").attr('id');
				equal("featureContent", output, "Features Rendered Successfully");
				start();
			}, 2000);
			
		});

		asyncTest("Features Test", function() {
		
			var features, output;
			Clazz.config = configData;
			var featuresListener = new Clazz.com.components.features.js.listener.FeaturesListener();
			console.info("test", featuresListener.search("Weather", "module"));
			
			setTimeout(function() {
				output = $(Clazz.navigationController.jQueryContainer).find("#featureContent").attr('id');
				equal("featureContent", output, "Features Rendered Successfully");
				start();
			}, 2000);
			
		});
	}};
	
});
