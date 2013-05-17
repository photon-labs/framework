define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.configuration.js.api");

	Clazz.com.components.configuration.js.api.ConfigurationAPI = Clazz.extend(Clazz.com.js.api.API, {

        /***
         * Called in for doLogin ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		configuration : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.configuration.js.api.ConfigurationAPI;
});