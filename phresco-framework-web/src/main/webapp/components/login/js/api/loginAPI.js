define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.login.js.api");

	Clazz.com.components.login.js.api.LoginAPI = Clazz.extend(Clazz.com.js.api.API, {
        /***
         * Called in for doLogin ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		doLogin : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.login.js.api.LoginAPI;
});