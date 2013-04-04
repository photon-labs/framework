define(["framework/base", "api/api"], function() {
	Clazz.createPackage("com.common_components.modules.header.js.api");
	Clazz.com.common_components.modules.header.js.api.HeaderAPI = Clazz.extend(Clazz.com.js.api.API, {
	
		callCategories : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}

	});
	
	return Clazz.com.common_components.modules.header.js.api.HeaderAPI; 	
});