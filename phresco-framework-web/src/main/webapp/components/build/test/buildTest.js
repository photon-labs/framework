
define(["build/build",  "framework/navigationController", "framework/widgetWithTemplate"], function(Build, navigation, WidgetWithTemplate) {

	return { runTests: function (configData) {
		
		module("build.js;Build");
		
		asyncTest("Test - Build Page design", function() {
		
			var build, navigationController, widgetWithTemplate, buildId;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			build = new Build();
			Clazz.navigationController.jQueryContainer = $("<div id='buildTest' style='display:none;'></div>");
			Clazz.navigationController.push(build, false);
			
			setTimeout(function() {
				var buildId = $(Clazz.navigationController.jQueryContainer).find("#buildPage").attr('id');
				equal(buildId, "buildPage", "BuildPage Successfully Rendered");
				start();
			}, 1500);
			
		});
		
	}};
});