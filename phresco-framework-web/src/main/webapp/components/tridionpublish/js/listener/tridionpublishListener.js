define([], function() {

	Clazz.createPackage("com.components.tridionpublish.js.listener");

	Clazz.com.components.tridionpublish.js.listener.tridionpublishListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		mavenServiceListener : null,		

		initialize : function(config) {
			var self = this;
			
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){});
			}
		},
		
		onPrgoress : function(clicked) {
			var check = $(clicked).attr('data-flag');
			var self = this;
			if(check === "true") {
				self.openConsole();
				$(clicked).attr('data-flag','false');
			} else {
				self.closeConsole();
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		},
		
		getRequestHeader : function(data , action) {
			var self=this, header, userId, appDirName;
			userId = commonVariables.api.localVal.getSession('username');
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			var environment = $("#envList").val();
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			};
			if(action === 'getEnv'){
				header.webserviceurl = commonVariables.webserviceurl+"configuration/allEnvironments?appDirName="+appDirName;
			}				
			
			if(action === 'getTargets'){
				header.webserviceurl = commonVariables.webserviceurl+"tridion/getPublicationsList?appDirName="+appDirName+"&type=publicationTarget&envName="+data;
			}				
						
			if(action === 'publishPages'){
				header.requestMethod ="POST";
				header.requestPostBody = data;
				header.webserviceurl = commonVariables.webserviceurl+"tridion/publishPages?appDirName="+appDirName+"&environment="+environment+"&type=PublishPages";
			}				
									
			if(action === 'UnPublishPages'){
				header.requestMethod ="POST";
				header.requestPostBody = data;
				header.webserviceurl = commonVariables.webserviceurl+"tridion/publishPages?appDirName="+appDirName+"&environment="+environment+"&type=UnPublishPages";
			}				
	
			if(action === 'publishQueue'){
				header.webserviceurl = commonVariables.webserviceurl+"tridion/publishQueue?appDirName="+appDirName+"&environment="+data;
			}						
	
	
			return header;
		},
		
				
		getData : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
							//commonVariables.api.showError(response.responseCode ,"success", true);
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true, false, true);
						}
					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},
		
		getTargets : function(environmentSelected , action){
			var self = this;
			self.getData(self.getRequestHeader(environmentSelected, action), function(response) {
				if(response.data !== null && response.data.length !== 0) {
 					if(response.responseCode === "PHRTR0004"){
						var availableParents = "";
						$.each(response.data, function(index, value){
							 var allparent = $.trim(value.item);
								availableParents += '<option value='+value.id+'>'+ allparent +'</option>';
						});
						$("#envTarget").append(availableParents);
					} 
				}
			});
		},
				
		getPublishingQueue : function(action){
			var self = this;
			var environmentSelected = $("#envList").val();
			self.getData(self.getRequestHeader(environmentSelected, action), function(response) {
				if(response.data !== null && response.data.length !== 0) {
 					 if(response.responseCode === "PHRTR0008"){
						$("#publishQueueList").empty();
						var pubQueue = '<td>'+$.trim(response.data.Name)+'</td><td>'+$.trim(response.data.target)+'</td><td>'+$.trim(response.data.Publication)+'</td><td>'+$.trim(response.data.Path)+'</td><td>'+$.trim(response.data.Action)+'</td><td>'+$.trim(response.data.state)+'</td><td>'+$.trim(response.data.Priority)+'</td><td>'+$.trim(response.data.Time)+'</td><td>'+$.trim(response.data.User)+'</td>';
						$("#publishQueueList").append(pubQueue);
					}  
				}
			});
		},
		
		publishWebsite : function(){
			var self = this;
			if(!self.validation()) {
			var publicationInfo = {};
			var envList = $("#envList").val();
			var envTarget = $("#envTarget").val();
 				self.getData(self.getRequestHeader(JSON.stringify(envTarget), "publishPages"), function(response) {
					 if(response.data !== null && response.data.length !== 0) {
						if(response.responseCode === "PHRTR0007"){
							commonVariables.api.showError("PHRTR0007" ,"success", true, false, true);
						} else {
							commonVariables.api.showError("PHRTR1008" ,"error", true, false, true);
						}
					} 
				}); 
 			}
		},	
		
		unPublishWebsite : function(){
			var self = this;
			if(!self.validation()) {
			var publicationInfo = {};
			var envList = $("#envList").val();
			var envTarget = $("#envTarget").val();
 				self.getData(self.getRequestHeader(JSON.stringify(envTarget), "UnPublishPages"), function(response) {
					 if(response.data !== null && response.data.length !== 0) {
						if(response.responseCode === "PHRTR0010"){
							commonVariables.api.showError("PHRTR0010" ,"success", true, false, true);
						} else {
							commonVariables.api.showError("PHRTR1011" ,"error", true, false, true);
						}
					} 
				}); 
 			}
		},
		
		validation : function() {
			self.hasError = false;
			var envList = $("#envList").val();
			var envTarget = $("#envTarget").val();
				if(envList === "0"){
					commonVariables.api.showError("PHRTRS1020" ,"error", true, false, true);
					self.hasError = true;
			   } else if(!envTarget){
					commonVariables.api.showError("PHRTRS1030" ,"error", true, false, true);
					self.hasError = true;
			   } 
			  return self.hasError;
		}		
				
		
	});

	return Clazz.com.components.tridionpublish.js.listener.tridionpublishListener;
});