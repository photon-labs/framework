
define(["MockProjectList"], function(MockProjectList) {

	return { runTests: function (configData) {
		/**
		 * Test that the setMainContent method sets the text in the MyCart-widget
		 */
		module("projectlist.js;projectlist");
		
		var mockprojectlist, mockFunc;
		mockprojectlist = new MockProjectList();
		
		mockFunc = mockFunction();
		when(mockFunc)(anything()).then(function(arg) {
			var projectlistReturn = "projectlist successfully rendered"; 		
			return projectlistReturn;
		});

	   mockprojectlist.projectlist = mockFunc
	   
	   test("ProjectList Sample Test", function() {
		   equal(mockprojectlist.projectlist(configData), "projectlist successfully rendered", "projectlist Test");
	   });
		  
	}};
});