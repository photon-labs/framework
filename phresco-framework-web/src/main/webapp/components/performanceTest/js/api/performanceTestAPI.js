define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.performanceTest.js.api");

	Clazz.com.components.performanceTest.js.api.PerformanceTestAPI = Clazz.extend(Clazz.com.js.api.API, {

        /***
         * Called in for doLogin ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		performanceTest : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.performanceTest.js.api.performanceTestAPI;
});