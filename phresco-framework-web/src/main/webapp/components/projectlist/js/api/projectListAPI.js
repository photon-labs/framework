define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.projectlist.js.api");

	Clazz.com.components.projectlist.js.api.ProjectsListAPI = Clazz.extend(Clazz.com.js.api.API, {

		projectslist : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.projectlist.js.api.ProjectsListAPI;
});