define(["framework/base", "api/localStorageAPI"], function(){	
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.API = Clazz.extend(Clazz.Base, {
		localVal : null,
		bCheck : false,
		error : null,
		success : null,
		successResponse: null,
		
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
						}else {
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
						// callbackFunction(response);
						self.successResponse = response;
					} else {
						self.errorpopupshow(response);
					}
					
					self.bCheck = false;
				},
				
				error : function(jqXHR, textStatus, errorThrown){
					self.bCheck = false;
					self.successResponse = null;
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
					if ((self.successResponse !== undefined && self.successResponse !== null && self.successResponse.status !== "error") || self.bCheck) {
						callbackFunction(self.successResponse);
						self.successResponse = null;
					}
				}
			});
		},
		
		ajaxForDynamicParam : function(header, whereToRender, callbackFunction, errorHandler) {
			var self = this, btnObj = '';
			if (whereToRender !== "") {
				btnObj = $(whereToRender).closest('form').parent().find('.alignright').find('input[value=Test]');
			}

			$.ajax({
				url: header.webserviceurl,
				type : header.requestMethod,
				dataType : header.dataType,
				header : "Access-Control-Allow-Headers: x-requested-with",
				contentType : header.contentType,
				data : header.requestPostBody,
				timeout : 1000000,
				crossDomain : true,
				cache : false,
				async : false,
				success : function(response) {
					if(response === undefined || response === null){
						self.showError("PHR000000", 'error', false);
					}else if((response.status === "error" || response.status === "failure") && (Clazz.navigationController.loadingActive || commonVariables.continueloading)){
						self.showError(response.responseCode, 'error', true);
						if (response.responseCode === "PHR7C10001" && btnObj !== "") {
							$(btnObj).prop("disabled", true);
						}
					}
					
					if ((response !== undefined && response !== null && response.status !== "error") || self.bCheck) {
						callbackFunction(response);
					}
					
					self.bCheck = false;
				}
			});
		},

		hideLoading : function() {
			var self = this;
			commonVariables.loadingScreen.removeLoading();
		},
		
		errorpopupshow : function(response) {
			var self = this;
			$('#errpopup').remove();
			$(commonVariables.basePlaceholder).append('<div id="errpopup" class="modal fade" tabindex="-1" style="display: none;"><div class="modal-body temp"></div><div class="modal-footer"><div><a href="javascript:void(0)" title="" id="copytoclip" class="flt_left padding_img" href="javascript:void(0)"><img class="padding_img" src="themes/default/images/helios/buildreport_icon.png" width="15" height="18" border="0" alt=""></a><div class="errorpopuploading" id="copyloadicon" style="display:none;">&nbsp</div></div><button type="button" data-dismiss="modal" class="btn btn_style">Close</button></div></div>');
			if(response.service_exception !== null && response.service_exception !== undefined) { 
				$(".modal-body").append(response.service_exception);
			}else if(response.exception !== null && response.exception !== undefined) {
				$.each(response.exception.stackTrace, function(index, value){
					$(".modal-body").append(' '+value.className+' '+value.fileName+' '+' '+value.lineNumber+' '+value.methodName);
				});
			}
			$("#errpopup").modal();	
			$("#copytoclip").click(function() {
				$("#copyloadicon").show();
				commonVariables.navListener.copyToClipboard($('.temp'));
			});		
			$(".popuploading").hide();
		},
		
		urlExists : function(url, callbackfunction, errorHandler){
			commonVariables.ajaxXhr = $.ajax({
				type: 'GET',
				url: url,
				header : "Access-Control-Allow-Headers: x-requested-with",
				dataType: "script",
				timeout : 2000,
				async : true,
				beforeSend : function(jqXHR, settings) {
					commonVariables.loadingScreen.showLoading($(commonVariables.contentPlaceholder));
				},
				success: function(data, textStatus, jqXHR){
					commonVariables.loadingScreen.removeLoading();
					//callbackfunction(jqXHR.status);
					callbackfunction(true);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					commonVariables.loadingScreen.removeLoading();
					//callbackfunction(jqXHR.status);
					callbackfunction(false);
				},					
				complete: function (jqXHR, textStatus) {
					commonVariables.loadingScreen.removeLoading();
					//callbackfunction(jqXHR.status);
					callbackfunction(false);
				},	
			});	
		},

		showError : function(errorCode, msgType, timeOut, noCode, hideErrorOnly){
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
			$("." + msgType).css("display", "block");
			if($(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').length > 0){	
				if($(commonVariables.contentPlaceholder).find('.content_end').css("display") === "none") {
					$(commonVariables.contentPlaceholder).find('.content_end').css("display","block");
				}
				$(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').addClass(msgType).html(errorMsg);
				$(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').show();
			}else{
				$(commonVariables.basePlaceholder).append('<section id="serviceError" class="content_end" style="display:block;"><div class="msgdisplay '+ msgType +'">'+   errorMsg +'</div></section>');
				msgType = 'content_end';
			}
			
			if(timeOut){
				setTimeout(function(){
					if(hideErrorOnly) {
						$("." + msgType).empty();
						$("." + msgType).css("display", "none");
					} else {
						$("." + msgType).parent().hide();
						$("." + msgType).css("display", "none");
					}
				}, 2000);
			}
		}
	});

	return Clazz.com.js.api.API;
});