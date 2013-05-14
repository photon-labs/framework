define(["MockProject"], function(MockProject) {

	return { runTests: function (configData) {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("Project.js;Project");
		
		var mockprojectUpdate, mockFunc;
		mockprojectUpdate = new MockProject();
		
		mockFunc = mockFunction();
		when(mockFunc)(anything()).then(function(arg) {
			var projectlistReturn = "Project Updated Successfully"; 		
			return projectlistReturn;
		});

	   mockprojectUpdate.projectUpdate = mockFunc
	   
	   test("ProjectUpdate Test", function() {
		   equal(mockprojectUpdate.projectUpdate(configData), "Project Updated Successfully", "projectUpdate Test");
	   });
		  
	}};
});