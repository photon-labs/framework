define(["framework/widget", "mavenService/api/mavenServiceAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.mavenService.js.listener");

	Clazz.com.components.mavenService.js.listener.MavenServiceListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		mavenServiceAPI : null,
		//self : this,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.mavenServiceAPI = new Clazz.com.components.mavenService.js.api.MavenServiceAPI();
		},

		mvnBuild : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnBuild, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnDeploy : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnDeploy, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnUnitTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnUnitTest, paramData);
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
		
		mvnPerformanceTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnPerformanceTest, paramData);
			self.mvnService(header, divId, callback);
		},
		
		mvnLoadTest : function(paramData, divId, callback){
			var self = this, header = self.getRequestHeader("POST", "", commonVariables.mvnLoadTest, paramData);
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
				if(response == false){
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
			self.mvnCheckNode(paramData, divId, function(response){
				if(response == false){
					self.mvnService(header, divId, callback);
				}
			});
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
				var self = this, callbackData = null;
				self.loadingScreen.showLoading();
				self.mavenServiceAPI.mvnSer(header, 
					function(response){
						if(response != undefined && response != null){
							if(response.status == 'STARTED'){
								self.mvnlogService(response.uniquekey, divId);
								callbackData = true;
							}else if(response.status == 'INPROGRESS'){
								callbackData = true;
							}else if(response.status == 'COMPLETED'){
								callbackData = true;
							}else if(response.status == 'ERROR'){
								$(divId).append(response.service_exception);
								callbackData = false;
							}else if(response.status == 'SUCCESS'){
								callbackData = response.connectionAlive;
							}
							self.loadingScreen.removeLoading();
							callback(callbackData);
						}else {
							self.loadingScreen.removeLoading();
							callback(callbackData);
						}
					}, 
					function(serviceerror){
						self.loadingScreen.removeLoading();
						callback('Service Connection Exception');
					});
			}catch(exception){
				//Exception
				self.loadingScreen.removeLoading();
				callback('Service Exception');
			}
		},
		
		mvnlogService : function(key, divId){
			try{
				var self = this, header = self.getRequestHeader("GET", '&uniquekey=' + key, commonVariables.mvnlogService, "");
				self.mavenServiceAPI.mvnSer(header, 
					function(response){
						if(response != undefined && response != null){

							$(divId).append(response.log);
						
							if(response.status == 'STARTED'){
							}else if(response.status == 'INPROGRESS'){
								self.mvnlogService(response.uniquekey, divId);
							}else if(response.status == 'COMPLETED'){
							}else if(response.status == 'ERROR'){
								$(divId).append(data.service_exception);
							}else if(response.status == 'SUCCESS'){
							}
						}else {
						}
					}, 
					function(serviceerror){
					});
			}catch(exception){
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
			}

			return header;
		}
	});

	return Clazz.com.components.mavenService.js.listener.MavenServiceListener;
});