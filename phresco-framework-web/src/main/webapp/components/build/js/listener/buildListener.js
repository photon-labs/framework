define(["framework/widget", "build/api/buildAPI"], function() {

	Clazz.createPackage("com.components.build.js.listener");

	Clazz.com.components.build.js.listener.BuildListener = Clazz.extend(Clazz.Widget, {
		buildAPI : null,		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.buildAPI = new Clazz.com.components.build.js.api.BuildAPI();
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
				$('.build_info').animate({width: window.innerWidth/2.5},500);
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
							callback(response.data);
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
		getRequestHeader : function(projectRequestBody) {
			var self=this, header, appdirName = '';
			
			if(self.buildAPI.localVal.getSession('appDirName') != null){
				appdirName = self.buildAPI.localVal.getSession('appDirName');
			}
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: 'buildinfo/list?appDirName=' + appdirName
			}
			console.info('header',header);
			return header;
		}
	});

	return Clazz.com.components.build.js.listener.BuildListener;
});