define(["framework/base", "api/localStorageAPI"], function(){	
	
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.API = Clazz.extend(Clazz.Base, {
		localVal : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(){
			this.localVal = new Clazz.com.js.api.LocalStorageAPI();
		},

		//to solve caching issue for iOS 6
		//always send current time to differ every request.
		getTimeStamp : function(){
			var date = new Date();
			return date.getTime(); 
		},

		// This method is used to send the request to web service.
		// module is the identical key that is used to be inserted in the apiHost.
		// requestMethod is the method to send web service. It can be "POST" or "GET".
		// requestPostBody is the request parameter and value to be sent.
		// callbackFunction is the method sent to handle the response.
		// errorHandler is used to handle the error happened.
		ajaxRequest : function(header, callbackFunction, errorHandler){
			$.support.cors = true;
			$.ajax({
				url: header.webserviceurl,
				type : header.requestMethod,
				dataType : header.dataType,
				header : "Access-Control-Allow-Headers: x-requested-with",
				contentType : header.contentType,
				data : header.requestPostBody,
				timeout: 1000000,
				crossDomain: true,
				cache: true,
				async: true,
				
				success : function(response, e ,xhr) {
					if (callbackFunction) {
						callbackFunction(response);
					}
				},
				
				error : function(jqXHR, textStatus, errorThrown) {
					if (errorHandler) {
						errorHandler(textStatus);
					}
				}
			});
		}
	});

	return Clazz.com.js.api.API;
});