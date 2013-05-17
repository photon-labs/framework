define(["framework/widget","configuration/api/configurationAPI"], function() {

	Clazz.createPackage("com.components.configuration.js.listener");

	Clazz.com.components.configuration.js.listener.ConfigurationListener = Clazz.extend(Clazz.Widget, {
		configurationAPI : null,
		editConfiguration : null,
		cancelEditConfiguration : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.configurationAPI = new Clazz.com.components.configuration.js.api.ConfigurationAPI();
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		editConfiguration : function(envName) {
			var self=this;
			commonVariables.environmentName = envName;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editConfiguration = commonVariables.navListener.getMyObj(commonVariables.editConfiguration);
			Clazz.navigationController.push(self.editConfiguration, true);
		},
		
		cancelEditConfiguation : function() {
			var self=this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.cancelEditConfiguration = commonVariables.navListener.getMyObj(commonVariables.configuration);
			Clazz.navigationController.push(self.cancelEditConfiguration, true);
		},
		
		getConfigurationList : function(header, callback) {
			var self = this;
			try {
				this.loadingScreen.showLoading();
				self.configurationAPI.configuration(header,
					function(response) {
						if (response !== null) {
							callback(response);
							self.loadingScreen.removeLoading();
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}

		},
		
		getRequestHeader : function(configRequestBody, action) {
			var self=this, header, appDirName;
			appDirName = self.configurationAPI.localVal.getJson("appDirName");
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName;
			} else if (action === "edit") {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"?appDirName="+appDirName+"&envName="+commonVariables.environmentName;
			}
			return header;
		}
		
	});

	return Clazz.com.components.configuration.js.listener.ConfigurationListener;
});