define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.application.js.api");

	Clazz.com.components.application.js.api.ApplicationAPI = Clazz.extend(Clazz.com.js.api.API, {

		appinfo : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.application.js.api.ApplicationAPI;
});