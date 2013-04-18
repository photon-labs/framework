define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.projects.js.api");

	Clazz.com.components.projects.js.api.ProjectsAPI = Clazz.extend(Clazz.com.js.api.API, {

		projects : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.projects.js.api.ProjectsAPI;
});