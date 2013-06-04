define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.ci.js.api");

	Clazz.com.components.ci.js.api.CIAPI = Clazz.extend(Clazz.com.js.api.API, {

        /***
         * Called in for ci call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		ci : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.ci.js.api.CIAPI;
});