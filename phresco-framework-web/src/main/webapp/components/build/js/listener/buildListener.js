define(["framework/widgetWithTemplate", "mavenService/mavenService", "build/api/buildAPI"], function() {

	Clazz.createPackage("com.components.build.js.listener");

	Clazz.com.components.build.js.listener.BuildListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		buildAPI : null,		
		mavenServiceListener : null,		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.buildAPI = new Clazz.com.components.build.js.api.BuildAPI();
			var mvnService = commonVariables.navListener.getMyObj(commonVariables.mavenService);
			this.mavenServiceListener = mvnService.mavenServiceListener;
		},
		
		onPrgoress : function(clicked) {
			var check = $(clicked).attr('data-flag');
			var value = $('.build_info').width();
			var value1 = $('.build_progress').width();
			if(check == "true") {
				$('.build_info').animate({width: '97%'},500);
				$('.build_progress').animate({right: -value1},500);
				$('.build_close').animate({right: '0px'},500);
				$(clicked).attr('data-flag','false');
			} else {
				$('.build_info').animate({width: window.innerWidth/1.6},500);
				$('.build_progress').animate({right: '10px'},500);
				$('.build_close').animate({right: value1+10},500);
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		},
		
		getBuildInfo : function(header, callback){
			var self = this;
			try {
				self.buildAPI.build(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}
					},

					function(textStatus) {
						callback({ "status" : "Connection failure"});
					}
				);
			} catch(exception) {
				callback({ "status" : "service exception"});
			}
		},
		
		downloadBuild : function(buildNo, callback){
			var self = this, header = self.getRequestHeader("", {'buildNo':buildNo}, 'download');
			try {
				self.buildAPI.build(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}
					},

					function(textStatus) {
						console.info('textStatus',textStatus);
						callback({ "status" : "Connection failure"});
					}
				);
			} catch(exception) {
				callback({ "status" : "service exception"});
			}
		},
		
		deployBuild : function(buildNo, callback){},
		
		buildProject : function(queryString, callback){
			var self = this;
			$('#logContent').html();
			self.mavenServiceListener.mvnBuild(queryString, '#logContent', function(returnVal){
				callback(returnVal);
			});
		},

		deleteBuild : function(buildNo, callback){
			var self = this, header = self.getRequestHeader(JSON.stringify([buildNo]), '', 'delete');
			try {
				self.buildAPI.build(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}
					},

					function(textStatus) {
						callback({ "status" : "Connection failure"});
					}
				);
			} catch(exception) {
				callback({ "status" : "service exception"});
			}
		},
		
		
		/***
		 * provides the request header
		 *
		 * @BuildRequestBody: request body of Build
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(BuildRequestBody, buildInfo, action) {
			var self=this, header, appdirName = '', url = '', method = "GET", conte;
			
			if(self.buildAPI.localVal.getSession('appDirName') != null){
				appdirName = self.buildAPI.localVal.getSession('appDirName');
			}
			
			if(action == "getList"){
				method = "GET"
				url = 'buildinfo/list?appDirName=' + appdirName;
			}else if(action == "download"){
				method = "POST";
				url = 'buildinfo/buildfile?appDirName=' + appdirName + '&buildNumber=' + buildInfo.buildNo;
			}else if(action == "delete"){
				method = "DELETE";
				var appInfo = self.buildAPI.localVal.getJson('appdetails');
				if(appInfo != null){
					url = 'buildinfo/deletebuild?customerId='+ self.getCustomer() +'&appId='+ appInfo.data.appInfos[0].id +'&projectId=' + appInfo.data.id;
				}
			}
			
			header = {
				contentType: "application/json",
				requestMethod: method,
				requestPostBody: BuildRequestBody,
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl + url
			}
			return header;
		}
	});

	return Clazz.com.components.build.js.listener.BuildListener;
});