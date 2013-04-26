define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.features.js.api");

	Clazz.com.components.features.js.api.FeaturesAPI = Clazz.extend(Clazz.com.js.api.API, {

		features : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}	
		
	});

	return Clazz.com.components.features.js.api.FeaturesAPI;
});