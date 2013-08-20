
define(["configuration/configuration",  "framework/navigationController", "framework/widgetWithTemplate"], function(Configuration, navigation, WidgetWithTemplate) {

	return { 
		runTests: function (configData) {
		module("configuration.js;Configuration");
		asyncTest("Test - Configuration Page design", function() {
			var configuration, navigationController, widgetWithTemplate, configurationId;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			configuration = new Configuration();
			Clazz.navigationController.jQueryContainer = $("<div id='configurationTest' style='display:none;'></div>");
			Clazz.navigationController.push(configuration, false);
			
			setTimeout(function() {
				var configurationId = $(Clazz.navigationController.jQueryContainer).find("#configurationPage").attr('id');
				equal(configurationId, "configurationPage", "Configuration Page Successfully Rendered");
				start();
			}, 1500);
			
		});
		
	}

	
	}
	
});