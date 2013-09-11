define([], function() {

	Clazz.createPackage("com.components.settings.js.listener");

	Clazz.com.components.settings.js.listener.SettingsListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		jobTemplates : null,
		mavenServiceListener : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			
			if (self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}
			
		},
		
		getHeaderResponse : function (header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
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
		
		getRequestHeader : function(ciRequestBody, action, params) {
			var self = this, header;
	
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "",
				webserviceurl : '',
			};
			if (action === "jenkinsUrl") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/jenkinsUrl";
			} else if (action === "getMail") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/mail";
			} else if (action === "getConfluence") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/confluence";
			} else if (action === "setup") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.mvnCiSetup;
			} else if (action === "start") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.mvnCiStart;
			} else if (action === "stop") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.mvnCiStop;
			} else if (action === "save") {
				header.requestMethod = "POST";
				var user = $('input[name=username][temp=email]').val();
				var pass = $('input[name=password][temp=email]').val();
				header.requestPostBody = JSON.stringify(ciRequestBody);
//				header.requestPostBody = ciRequestBody;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/global?emailAddress=" + user + "&emailPassword=" + pass;
			} 
			return header;
		},
		
		getSettings : function() {
			var self = this;
			
			self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'jenkinsUrl'), function(response) {
				$('input[name=jenkinsUrl]').val(response.data);
			});
			
			self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'getMail'), function(response) {
				$('input[name=username][temp=email]').val(response.data[0]);
				$('input[name=password][temp=email]').val(response.data[1]);
			});
			
			self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'getConfluence'), function(response) {
				for (i in response.data) {
					var data = response.data[i];
					var tds = $('#prototype').html();
					var tr = $('<tr class=confluence>');
					tr.append(tds);
					tr.find('[name=url]').val(data.repoUrl);
					tr.find('[name=username]').val(data.userName);
					tr.find('[name=password]').val(data.password);
					
					tr.insertBefore($('#save'));
				}
				self.switchStatus();
//				self.iconsmanipulator();
				
				setTimeout(function() {
					
					
					$('#conflTable').on('click', 'a.delete', function() {
						$(this).closest('.confluence').remove();
					});
					
				}, 500);
			});
				
		},
		
		switchStatus : function() {
			commonVariables.navListener.getMyObj(commonVariables.jobTemplates, function(jobTemplatesObject) {
				jobTemplatesObject.ciListener.jenkinsStatus(function(response) {
					if (response.data === "200") {
						$('input[name=switch]').attr('value', "Stop");
					} else if (response.data === "503") {
						$('input[name=switch]').attr('value', "Starting...");
						$('input[name=start]').attr('disabled', true);
					} else {
						$('input[name=switch]').attr('value', "Start");
					}
				});
			});
		}, 
		
		iconsmanipulator : function() {
			var confluence = $('.confluence');
			if (confluence.length < 2) {
				$('.delete').hide();
			} else {
				$('.delete').show();
			}
		},
		
		setup : function() {
			var self = this;
			var queryString = '';
						
			$('#testConsole').html('');
			self.openConsole();//To open the console
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnCiSetup(queryString, '#testConsole', function(response) {
						self.closeConsole();
					});
				});
			} else {
				self.mavenServiceListener.mvnCiSetup(queryString, '#testConsole', function(response) {
					self.closeConsole();
				});
			}
		},

		start : function() {
			var self = this;
			var queryString = '';
						
			$('#testConsole').html('');
			self.openConsole();//To open the console
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnCiStart(queryString, '#testConsole', function(response) {
						self.closeConsole();
					});
				});
			} else {
				self.mavenServiceListener.mvnCiStart(queryString, '#testConsole', function(response) {
					self.closeConsole();
				});
			}
		},
		
		stop : function() {
			var self = this;
			var queryString = '';
						
			$('#testConsole').html('');
			self.openConsole();//To open the console
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnCiStop(queryString, '#testConsole', function(response) {
						self.closeConsole();
					});
				});
			} else {
				self.mavenServiceListener.mvnCiStop(queryString, '#testConsole', function(response) {
					self.closeConsole();
				});
			}
		},
		
		validate : function (callback) {
			var self=this;
			var status = true;
			var username = $('.confluence').find("input[name=username]");	
			username.each(function(i, selected) {
				if($(this).val() === "") {
					$(this).focus();
					$(this).attr('placeholder','Enter Username');
					$(this).addClass("errormessage");
					$(this).bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					status = false;				
				}
			});
			var password = $('.confluence').find("input[name=password]");	
			password.each(function(i, selected) {
				if($(this).val() === "") {
					$(this).focus();
					$(this).attr('placeholder','Enter Password');
					$(this).addClass("errormessage");
					$(this).bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					status = false;				
				}
			});
			var url = $('.confluence').find("input[name=url]");	
			url.each(function(i, selected) {
				if(!self.isValidUrl($(this).val())) {	
					$(this).focus();
					$(this).val('');
					$(this).attr('placeholder','Enter ValidUrl');
					$(this).addClass("errormessage");
					$(this).bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					status = false;				
				}
			});
			
			var username = $("input[name=username][temp=email]");	
			username.each(function(i, selected) {
				if($(this).val() === "") {
					$(this).focus();
					$(this).attr('placeholder','Enter EmailId');
					$(this).addClass("errormessage");
					$(this).bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					status = false;				
				}
			});
			var password = $("input[name=password][temp=email]");	
			password.each(function(i, selected) {
				if($(this).val() === "") {
					$(this).focus();
					$(this).attr('placeholder','Enter Password');
					$(this).addClass("errormessage");
					$(this).bind('keypress', function() {
						$(this).removeClass("errormessage");
					});
					status = false;				
				}
			});
			
			return status
		},
		
		save : function() { 
			var self = this;
			var status = self.validate();
			if (status === true) {
				var ciRequestBody = [];
				var confluence = $('.confluence');
				
				confluence.each(function(i, selected) {
					var confluenceObj = {};
					confluenceObj.repoUrl = $(this).find('input[name=url]').val();
					confluenceObj.userName = $(this).find('input[name=username]').val();
					confluenceObj.password = $(this).find('input[name=password]').val();
					ciRequestBody.push(confluenceObj);
				});
				
				self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'save'), function(response) {});
			}
			
		},
		
		showHideConsole : function() {
			var self = this;
			var check = $('#consoleImg').attr('data-flag');
			if (check === "true") {
				self.openConsole();
			} else {
				self.closeConsole();
				$('.progress_loading').hide();
			}
		},
				
	});

	
	return Clazz.com.components.settings.js.listener.SettingsListener;
});