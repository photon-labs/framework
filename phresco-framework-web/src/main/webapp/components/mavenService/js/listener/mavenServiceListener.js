define([], function() {

	Clazz.createPackage("com.components.mavenService.js.listener");

	Clazz.com.components.mavenService.js.listener.MavenServiceListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		//self : this,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
		},

		mvnBuild : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnBuild, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnDeploy : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnDeploy, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnProcessBuild : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnProcessBuild, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnUnitTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnUnitTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnComponentTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnComponentTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnCodeValidation : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnCodeValidation, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnRunagainstSource : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnRunagainstSource, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnStopServer : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnStopServer, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnRestartServer : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnRestartServer, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnPerformanceTest : function(paramData, divId, json, callback){
			var self = this, header = self.getRequestHeader("POST", JSON.stringify(json), commonVariables.mvnPerformanceTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnLoadTest : function(paramData, divId, json, callback){
			var self = this, header = self.getRequestHeader("POST", JSON.stringify(json), commonVariables.mvnLoadTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnFunctionalTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnFunctionalTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnMinification : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnMinification, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnStartHub : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnStartHub, paramData);
			self.mvnCheckHub(paramData, divId, function(response){
				if(response === false){
					self.mvnService(header, divId, callback);
				}
			});
		},
		
		mvnStopHub : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnStopHub, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnCheckHub : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnCheckHub, paramData);
			callback(self.mvnService(header, divId, callback));
		},
		
		mvnShowStartedHub : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnShowStartedHub, paramData);
			self.mvnService(header, divId, callback);
		},

		mvnStartNode : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnStartNode, paramData);
//			self.mvnCheckNode(paramData, divId, function(response){
//				if(response === false){
					self.mvnService(header, divId, callback);
//				}
//			});
		},
		
		mvnStopNode : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnStopNode, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnCheckNode : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnCheckNode, paramData);
			callback(self.mvnService(header, divId, callback));
		},
		
		mvnShowStartedNode : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnShowStartedNode, paramData);
			self.mvnService(header, divId, callback);
		},

		mvnService : function(header, divId, callback){
			try{
				var self = this;
				$(divId).html('');
				commonVariables.loadingScreen.showLoading();
				
				commonVariables.api.ajaxRequest(header, 
					function(response){
						if(response !==  undefined && response !==  null){
							commonVariables.loadingScreen.removeLoading();
							
							if(response.status === 'STARTED'){
								$(divId).append(response.status + '<br>');
								self.mvnlogService(response.uniquekey, divId, function(retVal){
									callback(retVal);
								});
							}else if(response.status === 'INPROGRESS'){
								callback(response);
							}else if(response.status === 'COMPLETED'){
								callback(response);
							}else if(response.status === 'error'){
								$(divId).append('<font style = "color:red">' + response.service_exception + '</font><br>');
								callback(response.service_exception);
							}else if(response.status === 'success'){
								callback(response.connectionAlive);
							}else if(response.status === 'failure'){
								$(".content_end").show();
								$(".msgdisplay").removeClass("success").addClass("error");
								$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);	
								$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
								setTimeout(function() {
									$(".content_end").hide();
								},2500); 
				
							}else if(response.status === null){
								callback(response);
							}
							
							//$(divId).mCustomScrollbar("update");
						}else {
							commonVariables.loadingScreen.removeLoading();
							$(".content_end").show();
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".content_end").hide();
							},2500);
							callback(false);
						}
					}, 
					function(serviceerror){
						commonVariables.loadingScreen.removeLoading();
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					});
			}catch(exception){
				//Exception
				commonVariables.loadingScreen.removeLoading();
				callback('Service Exception');
			}
		},
		
		mvnlogService : function(key, divId, callback){
			try{
				var self = this, header = self.getRequestHeader("GET", '&uniquekey=' + key, commonVariables.mvnlogService, "");
				$('.progress_loading').css('display','block');
				
				commonVariables.api.ajaxRequest(header, 
					function(response){
						if(response !==  undefined && response !== null){
							if(response.log !== undefined && response.log !== null){
								var logMsg = response.log;
								var nl = /\n|\r|\r\n/g;
								// split each line for assigning color
								logMsg = logMsg.replace(nl, '#LINESEP#');
								var logMsgs = logMsg.split('#LINESEP#');
								$.each(logMsgs, function (index, logMsgPart) {
									if (logMsgPart != "") {
										$(divId).append('<font style = "color:' + self.logColor(logMsgPart) + '">' + logMsgPart + '</font><br>');
									}
								});
							}
							if(response.status === 'STARTED'){
								callback(response);
							}else if(response.status === 'INPROGRESS'){
								if (response.log.indexOf("SocketConnector@0.0.0.0:") != -1) {//For start hub
									callback(response);
								} else if (response.log.indexOf("Done: /status") != -1) {//For start node
									callback(response);
								} else {
									self.mvnlogService(response.uniquekey, divId, callback);									
								}
							}else if(response.status === 'COMPLETED'){
								callback(response);
							}else if(response.status === 'error'){
								$(divId).append('<font style = "color:red">' + data.service_exception + '</font><br>');
								$('.progress_loading').css('display','none');
								callback(response);
							}else if(response.status === 'success'){
								$('.progress_loading').css('display','none');
								callback(response);
							}else if(response.status === null){
								$('.progress_loading').css('display','none');
								callback(response);
							}
							
							//$(divId).mCustomScrollbar("update");
						}else {
							$('.progress_loading').css('display','none');
							$(".content_end").show();
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".content_end").hide();
							},2500);
							callback(response);
						}
					}, 
					function(serviceerror){
						$('.progress_loading').css('display','none');
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					});
			}catch(exception){
				$('.progress_loading').css('display','none');
				//Exception
			}
		},

		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(type, body, urlContext, paramData) {
			var header = {
				contentType: "application/json",
				requestMethod: type,
				dataType: "json",
				requestPostBody: body,
				webserviceurl: commonVariables.webserviceurl + urlContext + "?" + paramData
			};

			return header;
		},
		
		logColor : function(logStr){
		
			if(logStr !== null){
				if(logStr.match("ERROR") || logStr.match("FAILURE")){
					return "#B40404";
				}else if(logStr.match("WARNING")){
					return "#FFBF00";
				}else if(logStr.match("SUCCESS")){
					return "green";
				}else{
					return "block";
				}
			}else{
				return "block";
			}
		}
	});

	return Clazz.com.components.mavenService.js.listener.MavenServiceListener;
});