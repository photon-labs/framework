define(["api/api"], function() {

	Clazz.createPackage("com.components.codequality.js.api");

	Clazz.com.components.codequality.js.api.CodeQualityAPI = Clazz.extend(Clazz.com.js.api.API, {

		codequality : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.codequality.js.api.CodeQualityAPI;
});