
define(["projectlist/projectList", "framework/navigationController", "framework/widgetWithTemplate","projectlist/listener/projectListListener"], function(projectList, navigation, WidgetWithTemplate, projectListListener) {

	return { runTests: function (configData) {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("projectlist.js;projectlist");
		
		asyncTest("Test - Load project data.", function() {
			var synonyms, output1, output2, output, navigationController, widgetWithTemplate, projectListListener, requestBody; 
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				
				projectlist = new projectList();
				Clazz.navigationController.jQueryContainer = $("<div id='projectsTest' style='display:none;'></div>");
				/* $("body").append(Clazz.navigationController.jQueryContainer); */
				Clazz.navigationController.push(projectlist, false);
					
				setTimeout(function() {
					var columnLength = $(Clazz.navigationController.jQueryContainer).find("#projectlist > thead > tr > th").length;
					equal(7, columnLength,	"Successfully Project Listted");
					start();
				}, 2000);				
				
		});
		
	}};
});