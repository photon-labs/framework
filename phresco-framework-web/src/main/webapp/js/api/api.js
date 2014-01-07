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
					self.successResponse = null;
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
						self.successResponse = response;
					} else {self.errorpopupshow(response);}
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
					if((self.successResponse !== undefined && self.successResponse !== null && self.successResponse.status !== "error") || self.bCheck){
						callbackFunction(self.successResponse);
					}else if(self.successResponse === null || self.successResponse.status === "error"){self.errorpopupshow(response);}
					self.bCheck = false;
				}
			});
		},
		
		ajaxRequestDashboard : function(header, callbackFunction, errorHandler){
			var self = this;
			$.support.cors = true;
			
			//cancel/abort existing ajax call
			/*if(commonVariables.dashboardAjaxXhr && commonVariables.dashboardAjaxXhr.readyState != 4){
				commonVariables.dashboardAjaxXhr.abort();
			}*/

			commonVariables.dashboardAjaxXhr = $.ajax({
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
				
				beforeSend : function(){
					self.successResponse = null;
					$('section#serviceError').remove();
				},
				
				success : function(response, e ,xhr){
					if(response === undefined || response === null){
						self.showError("PHR000000", 'error', false);
					}else if((response.status === "error" || response.status === "failure")){
						self.showError(response.responseCode, 'error', false);
					}
					if ((response !== undefined && response !== null && response.status !== "error")) {
						self.successResponse = response;
					} else {self.errorpopupshow(response);}
				},
				
				error : function(jqXHR, textStatus, errorThrown){
					self.successResponse = null;
					self.showError("PHR000000", 'error', false);
					if (errorHandler){
						errorHandler(jqXHR.responseText);
					}
				},
				
				complete : function(response, e ,xhr){
					if(self.successResponse !== undefined && self.successResponse !== null && self.successResponse.status !== "error"){
						callbackFunction(self.successResponse);
					}else if(self.successResponse === null || self.successResponse.status === "error"){self.errorpopupshow(response);}
				}
			});
		},
		
		ajaxRequestForScm : function(header, callbackFunction, errorHandler){
			var self = this;
			$.support.cors = true;
			
			$.ajax({
				url: header.webserviceurl,
				type : header.requestMethod,
				dataType : header.dataType,
				header : "Access-Control-Allow-Headers: x-requested-with",
				contentType : header.contentType,
				data : header.requestPostBody,
				crossDomain : true,
				cache : false,
				async : true,
				
				beforeSend : function(){
					self.successResponse = null;
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
						self.successResponse = response;
					} else if (response.exception.message === "import.project.already") {
						callbackFunction(response);
					} else {self.errorpopupshow(response);}
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
					if (commonVariables.callLadda) {
						Ladda.stopAll();
					}
					if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading){
						commonVariables.hideloading = false;
						commonVariables.loadingScreen.removeLoading();
					}
					if((self.successResponse !== undefined && self.successResponse !== null && self.successResponse.status !== "error") || self.bCheck){
						callbackFunction(self.successResponse);
					}  else if(self.successResponse === null || self.successResponse.status === "error"){self.errorpopupshow(response);}
					self.bCheck = false;
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
				async : true,
				
				beforeSend : function(){
					self.successResponse = null;
					$('section#serviceError').remove();
					if(!Clazz.navigationController.loadingActive && !commonVariables.continueloading && !commonVariables.hideloading){
						if($(commonVariables.basePlaceholder).find(commonVariables.contentPlaceholder).length > 0){
							commonVariables.loadingScreen.showLoading($(commonVariables.contentPlaceholder));
						}else {
							commonVariables.loadingScreen.showLoading();
						}
					}
				},
				
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
					}else if(response === null || response.status === "error"){self.errorpopupshow(response);}
					
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
				}
			});
		},

		hideLoading : function() {
			var self = this;
			commonVariables.loadingScreen.removeLoading();
		},
		
		errorpopupshow : function(response) {
			var self = this;
			$("#project_list_import").find('input').removeAttr('disabled');	
			$("#project_list_import").find('select').removeAttr('disabled');
			if(response.responseCode !== undefined) {
				$('.errpopup, .modal-backdrop').remove();	
				if(response.status !== 0 && $(".errpopup").size() < 1 && $(".errpopup").css('display') !== 'display') {
					$(commonVariables.basePlaceholder).append('<div class="modal fade errpopup" tabindex="-1" style="display: none;"><div class="modal-body temp"></div><div class="modal-footer"><div><a href="javascript:void(0)" title="" id="copytoclip" class="flt_left padding_img" href="javascript:void(0)"><img class="padding_img" src="themes/default/images/Phresco/buildreport_icon.png" width="15" height="18" border="0" alt=""></a><div class="errorpopuploading" id="copyloadicon" style="display:none;">&nbsp</div></div><button type="button" data-dismiss="modal" class="btn btn_style">Close</button></div></div>');
					if(response.service_exception !== null && response.service_exception !== undefined) { 
						$(".modal-body").append(response.service_exception);
					}else if(response.exception !== null && response.exception !== undefined) {
						if(response.exception.errorMessage !== null && response.exception.errorMessage !== undefined) {
							$(".modal-body").append(response.exception.errorMessage+'<br>');
						} else if(response.exception.message !== null && response.exception.message !== undefined) {
							$(".modal-body").append(response.exception.message+'<br>');
						}
						if(response.exception.stackTrace !== undefined) {
							$.each(response.exception.stackTrace, function(index, value){
								$(".modal-body").append(' '+value.className+' '+value.fileName+' '+' '+value.lineNumber+' '+value.methodName);
							}); 
						} else {
							$(".modal-body").append(response.exception);
						}
					}
					$(".errpopup").modal();	
					setTimeout(function() {
						$("#copytoclip").zclip({
							path: "lib/ZeroClipboard.swf",
							copy: function(){
								return $(".temp").text();
							}
						});
					},700);	
					$(".popuploading").hide();
				}
			}	
		},
		
		urlExists : function(url, callbackfunction, errorHandler){
			
			commonVariables.ajaxXhr = $.ajax({
				type: 'GET',
				url: url,
				cache: true,
				header : "Access-Control-Allow-Headers: x-requested-with",
				dataType: "script",
				crossDomain: true,
				timeout : 10000,
				async : true,
				beforeSend : function(jqXHR, settings) {
					commonVariables.loadingScreen.showLoading($(commonVariables.contentPlaceholder));
				},
				success: function(data, textStatus, jqXHR){
					commonVariables.loadingScreen.removeLoading();
					callbackfunction(true);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					commonVariables.loadingScreen.removeLoading();
					callbackfunction(false);
				}	
			});	
		},

		showError : function(errorCode, msgType, timeOut, noCode, hideErrorOnly){
			var self = this, errorMsg = '';
			//console.info('errorCode ' , errorCode + 'errorMsg =' + errorMsg + ' noCode = ' +noCode);
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
			if ($(".msgdisplay").attr("class") === "msgdisplay error") {
				$(".msgdisplay").removeClass("error");
			}
			
			if(errorCode === "PHR210003" || errorCode === "PHR310001" || errorCode === "PHR410001" ||  errorCode === "PHR610006" ||  errorCode === "PHR110002"){
				//console.info('if' ,  errorCode)
				var url = location.href;
				if (commonVariables.customerContext == undefined) {
					commonVariables.customerContext = '#';
				}
				url = url.substr(0, url.lastIndexOf('/')) + "/" + commonVariables.customerContext;
				//self.clearSession();
				Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
				//self.removePlaceholder();
				//self.headerAPI.localVal.setSession('loggedout', 'true');
				location.hash = '';
				commonVariables.customerContext = '';
				location.href = url;
			} else {
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
				}, 7000);
			}
			
			
		}
	});

	return Clazz.com.js.api.API;
});