define(["footer/footer",  "framework/navigationController", "framework/widgetWithTemplate", "footer/listener/footerListener"], function(Footer, navigation, WidgetWithTemplate, footerListener) {

	return { runTests: function (configData) {
		
		module("footer.js;Footer");
		
		asyncTest("Test - Footer design", function() {
		
			var footer, actualdata, expecteddata, navigationController, widgetWithTemplate, footerlistener;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			footer = new Footer();
			Clazz.navigationController.jQueryContainer = $("<div id='footerTest'style='display:none;'></div>");
			Clazz.navigationController.push(footer, false);
			
			setTimeout(function() {
				var copyrightlength = $(Clazz.navigationController.jQueryContainer).text().length;
				equal(copyrightlength, 47, "Footer Successfully Rendered");
				start();
			}, 500);
			
		});
		
	}};
});
