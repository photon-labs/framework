
define(["projectlist/projectList"], function(ProjectList) {

	return { runTests: function (configData) {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("projectlist.js;projectlist");
		
		var mockFunc, projectList = new ProjectList();
		
		mockFunc = mockFunction();
		when(mockFunc)(anything()).then(function(arg) {
			console.info('mocke....');
			var projectlistReturn = "projectlist successfully rendered"; 		
			return projectlistReturn;
		});

	   projectList.projectslistListener.getProjectList = mockFunc
	   
	   test("ProjectList Sample Test", function() {
		   projectList.loadPage();
		   console.info(projectList.contentContainer);
		   equal(projectList.contentContainer, "projectlist successfully rendered", "projectlist Test");
	   });
		  
	}};
});