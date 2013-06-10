define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.functionalTest.js.api");

	Clazz.com.components.functionalTest.js.api.FunctionalTestAPI = Clazz.extend(Clazz.com.js.api.API, {

        /***
         * Called in for doLogin ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		functionalTest : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
		
	});

	return Clazz.com.components.functionalTest.js.api.FunctionalTestAPI;
});