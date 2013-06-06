define(["api/api"], function() {

	Clazz.createPackage("com.components.navigation.js.api");

	Clazz.com.components.navigation.js.api.navigationAPI = Clazz.extend(Clazz.com.js.api.API, {

        /***
         * Called in for donavigation ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		donavigation : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.navigation.js.api.navigationAPI;
});