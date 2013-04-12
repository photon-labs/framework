
define(["navigation/navigation",  "framework/navigationController", "framework/widgetWithTemplate", "navigation/listener/navigationListener"], function(Navigation, navigation, WidgetWithTemplate, navigationListener) {

	return { runTests: function (configData) {
		
		module("navigation.js;Navigation");
		
		asyncTest("Test - Navigation design", function() {
		
			var navigation, navigationController, widgetWithTemplate;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			navigation = new Navigation();
			Clazz.navigationController.jQueryContainer = $("<div id='navigationTest' style='display:none;'></div>");
			Clazz.navigationController.push(navigation, false);
			
			setTimeout(function() {
				var navigation = $(Clazz.navigationController.jQueryContainer).find("#navigation").attr('id');
				equal(navigation, "navigation", "Navigation Successfully Rendered");
				start();
			}, 1500);
			
		});
		
	}};
});
