define(["api/api"], function() {

	Clazz.createPackage("com.components.mavenService.js.api");

	Clazz.com.components.mavenService.js.api.MavenServiceAPI = Clazz.extend(Clazz.com.js.api.API, {
        /***
         * Called in for mvnSer ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		mvnSer : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.mavenService.js.api.MavenServiceAPI;
});