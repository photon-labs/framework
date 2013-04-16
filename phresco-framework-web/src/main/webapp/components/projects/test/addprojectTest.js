define(["projects/addproject",  "framework/navigationController", "framework/widgetWithTemplate", "projects/listener/projectsListener"], function(AddProject, navigation, WidgetWithTemplate, projectsListener) {

	return { runTests: function (configData) {
		
		module("addproject.js;AddProject");
		
		asyncTest("Test - AddProject design", function() {
		
			var addproject, navigationController, widgetWithTemplate, projectslistener, addprojectid;
			 
			Clazz.config = configData;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			addproject = new AddProject();
			Clazz.navigationController.jQueryContainer = $("<div id='addprojectTest' style='display:none;'></div>");
			Clazz.navigationController.push(addproject, false);
			
			setTimeout(function() {
				addprojectid = $(Clazz.navigationController.jQueryContainer).find("#addproject").attr('id');
				//console.info("addprojectid",addprojectid);
				equal(addprojectid, "addproject", "AddProject Page Successfully Rendered");
				start();
			}, 500);
			
		});
		
	}};
});