define(["framework/base", "api/api"], function() {

	Clazz.createPackage("com.components.dynamicPage.js.api");

	Clazz.com.components.dynamicPage.js.api.DynamicPageAPI = Clazz.extend(Clazz.com.js.api.API, {
        /***
         * Called in for getContent ajax call 
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
		getContent : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.dynamicPage.js.api.DynamicPageAPI;
});