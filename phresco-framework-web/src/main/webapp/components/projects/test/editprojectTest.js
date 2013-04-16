define(["projects/editproject",  "framework/navigationController", "framework/widgetWithTemplate", "projects/listener/projectsListener"], function(EditProject, navigation, WidgetWithTemplate, projectsListener) {

	return { runTests: function (configData) {
		
		module("editproject.js;EditProject");
		
		asyncTest("Test - EditProject design", function() {
		
			var editproject, navigationController, widgetWithTemplate, editprojectid;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			editproject = new EditProject();
			Clazz.navigationController.jQueryContainer = $("<div id='editprojectTest' style='display:none;'></div>");
			Clazz.navigationController.push(editproject, false);
			
			setTimeout(function() {
				editprojectid = $(Clazz.navigationController.jQueryContainer).find("#editproject").attr('id');
				equal(editprojectid, "editproject", "EditProject Page Successfully Rendered");
				start();
			}, 500);
			
		});
		
	}};
});