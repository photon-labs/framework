define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.ci.js.api");

	Clazz.com.components.ci.js.api.CIAPI = Clazz.extend(Clazz.com.js.api.API, {

		ci : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.ci.js.api.CIAPI;
});