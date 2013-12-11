define([], function() {

	Clazz.createPackage("com.components.build.js.listener");

	Clazz.com.components.build.js.listener.BuildListener = Clazz.extend(Clazz.WidgetWithTemplate, {
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
		
		getInfo : function(header, callback){
			var self = this;

			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && (response.status !== "error" || response.status !== "failure")) {
							callback(response);
						} else {commonVariables.api.showError(response.responseCode ,"error", true);}
					},

					function(textStatus) {commonVariables.api.showError(response.responseCode ,"error", true);}
				);
			} catch(exception){callback({ "status" : "service exception"});}
		},
		
		downloadBuild : function(buildNo, type, callback){
			var self = this, header = self.getRequestHeader("", {'buildNo':buildNo}, type);
			$.fileDownload(header.webserviceurl).fail(function () { alert('File download failed!'); });
		},

		mavenServiceCall : function(functionName, queryString, minAll, bodyContent, callback){
			var self = this, appInfo = commonVariables.api.localVal.getProjectInfo();

			if(appInfo !== null){
				queryString += (queryString === "" ? "" : "&") + 'customerId='+ self.getCustomer() +'&appId='+ appInfo.data.projectInfo.appInfos[0].id +'&projectId=' + appInfo.data.projectInfo.id + '&username=' + commonVariables.api.localVal.getSession('username') + (minAll === ""? "" : '&minifyAll='+ minAll);
			}
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					
					self.mavenServiceListener[functionName](queryString, '#logContent', bodyContent, function(returnVal){
						callback(returnVal);
					});
				});
			}else{
				self.mavenServiceListener[functionName](queryString, '#logContent', bodyContent, function(returnVal){
					callback(returnVal);
				});
			}
		},

		mandatoryValidation : function (phase, queryString, callback){
			var self = this, appInfo = commonVariables.api.localVal.getProjectInfo(), header;
			
			try {
				header = self.getRequestHeader('','','validation');
				header.webserviceurl += '&phase='+ phase +'&' + queryString;
				
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response.errorFound || response.status === "error" || response.status === "failure"){
							commonVariables.api.showError(response.responseCode ,"error", true);
							self.showDynamicErrors(response);
						}
						callback(response);
					},

					function(textStatus) {commonVariables.api.showError(response.responseCode ,"error", true);}
				);
			} catch(exception) {callback({ "status" : "service exception"});}
		},
		
		getRequestHeader : function(BuildRequestBody, buildInfo, action) {
			var self=this, header, appdirName = '', url = '', method = "GET", contType = "application/json", conte;
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			if (!self.isBlank(moduleParam)) {
				appdirName = $('.rootModule').val()
			} else if(commonVariables.api.localVal.getProjectInfo() !== null){
				var projectInfo = commonVariables.api.localVal.getProjectInfo();
				appdirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			}

			if (action === "getList") {
				method = "GET";
				url = 'buildinfo/list?appDirName=' + appdirName+moduleParam;
			} else if(action === "download") {
				method = "GET";
				contType: "multipart/form-data";
				if (self.isBlank($('.moduleName').val())) {
					url = 'buildinfo/downloadBuild?appDirName=' + appdirName + '&buildNumber=' + buildInfo.buildNo;
				} else {
					url = 'buildinfo/downloadBuild?appDirName=' + appdirName + '/' + $('.moduleName').val() + '&buildNumber=' + buildInfo.buildNo;
				}
				
			} else if(action === "ipadownload") {
				method = "POST";
				contType: "multipart/form-data";
				url = 'Ipadownload?appDirName=' + appdirName + '&buildNumber=' + buildInfo.buildNo+moduleParam;
			} else if(action === "delete") {
				method = "DELETE";
				var appInfo = commonVariables.api.localVal.getProjectInfo();
				if(appInfo !== null){
					url = 'buildinfo/deletebuild?appDirName=' + appdirName+moduleParam;
				}
			} else if(action === "serverstatus") {
				method = "GET";
				url = 'buildinfo/checkstatus?appDirName=' + appdirName+moduleParam;
			} else if (action === "validation") {
				method = "GET";
				url ='util/validation?appDirName=' + appdirName + '&customerId=' + self.getCustomer()+moduleParam;
			} else if (action === "minifyList") {
				method = "GET";
				url ='buildinfo/minifer?appDirName=' + appdirName+moduleParam;
			} else if (action === "logContent") {
				method = "GET";
				url = 'buildinfo/logContent?status=' + buildInfo + '&appDirName=' + appdirName+moduleParam;
			}
			
			header = {
				contentType: contType,
				requestMethod: method,
				requestPostBody: BuildRequestBody,
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl + url
			};

			return header;
		}
	});

	return Clazz.com.components.build.js.listener.BuildListener;
});