define(["framework/base", "api/localStorageAPI"], function(){	
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.API = Clazz.extend(Clazz.Base, {
		localVal : null,
		bCheck : false,
		/***
		 * Written by Kavinraj.M Date - 23/08/2013
		 *
		 * Called in initialization time of this class 
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
			var self = this;
			$.support.cors = true;
			
			//cancel/abort existing ajax call
			/* if(commonVariables.ajaxXhr && commonVariables.ajaxXhr.readyState != 4){
				commonVariables.ajaxXhr.abort();
			} */

			commonVariables.ajaxXhr = $.ajax({
				url: header.webserviceurl,
				type : header.requestMethod,
				dataType : header.dataType,
				header : "Access-Control-Allow-Headers: x-requested-with",
				contentType : header.contentType,
				data : header.requestPostBody,
				timeout : 1000000,
				crossDomain : true,
				cache : false,
				async : true,
				
				beforeSend : function(){
					$('section#serviceError').remove();
					if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading && !commonVariables.hideloading){
						if($(commonVariables.basePlaceholder).find(commonVariables.contentPlaceholder).length > 0){
							commonVariables.loadingScreen.showLoading($(commonVariables.contentPlaceholder));
						}else{
							commonVariables.loadingScreen.showLoading();
						}
					}
				},
				
				success : function(response, e ,xhr){
					if(response === undefined || response === null){
						self.showError("Unexpected Failure at server end");
					}else if((response.status === "error" || response.status === "failure") && (Clazz.navigationController.loadingActive || commonVariables.continueloading)){
						$.get(commonVariables.globalconfig.environments.locales, function(data){
							if(data !== undefined && data !== null){
								self.showError(data.errorCodes[response.responseCode]);
							}
						}, 'JSON');
					}
					
					if ((response !== undefined && response !== null && response.status !== "error") || self.bCheck) {
						callbackFunction(response);
					}
					
					self.bCheck = false;
				},
				
				error : function(jqXHR, textStatus, errorThrown){
					self.bCheck = false;
					self.showError("Unexpected Failure at server end");
					if (errorHandler){
						errorHandler(jqXHR.responseText);
					}
				},
				
				complete : function(response, e ,xhr){
					if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading){
						commonVariables.hideloading = false;
						commonVariables.loadingScreen.removeLoading();
					}
				}
			});
		},
		
		showError : function(errorMsg){
			Clazz.navigationController.loadingActive = false;
			commonVariables.continueloading = false;
			commonVariables.hideloading = false;
			commonVariables.loadingScreen.removeLoading();
			$(commonVariables.basePlaceholder).append('<section id="serviceError" class="content_end" style="display:block;"><div class="msgdisplay error">'+ errorMsg +'</div></section>');
		}
	});

	return Clazz.com.js.api.API;
});