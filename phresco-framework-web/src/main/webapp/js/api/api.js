define(["framework/base", "api/localStorageAPI"], function(){	
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.API = Clazz.extend(Clazz.Base, {
		localVal : null,
		bCheck : false,
		error : null,
		success : null,
		
		/***
		 * Written by Kavinraj.M Date - 23/08/2013
		 *
		 * Called in initialization time of this class 
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(){
			var self = this;
			this.localVal = new Clazz.com.js.api.LocalStorageAPI();
			
			$.get(commonVariables.globalconfig.environments.locales, function(data){
				if(data !== undefined && data !== null){
					self.error = data.errorCodes; 
					self.success = data.successCodes; 
				}
			}, 'JSON');
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
						self.showError("PHR000000", 'error', false);
					}else if((response.status === "error" || response.status === "failure") && (Clazz.navigationController.loadingActive || commonVariables.continueloading)){
						self.showError(response.responseCode, 'error', false);
					}
					
					if ((response !== undefined && response !== null && response.status !== "error") || self.bCheck) {
						callbackFunction(response);
					}
					
					self.bCheck = false;
				},
				
				error : function(jqXHR, textStatus, errorThrown){
					self.bCheck = false;
					self.showError("PHR000000", 'error', false);
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
		
		showError : function(errorCode, msgType, timeOut, noCode){
			var self = this, errorMsg = '';
			
			try {
				if(noCode){ errorMsg = errorCode; }else{errorMsg = self[msgType][errorCode];}
			} catch(ex) {
				errorMsg = '';
			}
			$('section#serviceError').remove();
			Clazz.navigationController.loadingActive = false;
			commonVariables.continueloading = false;
			commonVariables.hideloading = false;
			commonVariables.loadingScreen.removeLoading();
			
			if($(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').length > 0){	
				$(commonVariables.contentPlaceholder).find('.content_end').show();
				$(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').addClass(msgType).html(errorMsg);
				$(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').show();
			}else{
				$(commonVariables.basePlaceholder).append('<section id="serviceError" class="content_end" style="display:block;"><div class="msgdisplay '+ msgType +'">'+   errorMsg +'</div></section>');
				msgType = 'content_end';
			}
			
			if(timeOut){
				setTimeout(function(){
					$("." + msgType).parent().hide();
					$("." + msgType).css("display", "none");
				}, 2000);
			}
		}
	});

	return Clazz.com.js.api.API;
});