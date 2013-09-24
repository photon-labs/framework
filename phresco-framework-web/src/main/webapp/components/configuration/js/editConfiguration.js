define(["configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.EditConfiguration = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/editConfiguration.tmp",
		configUrl: "components/configuration/config/config.json",
		name : commonVariables.editConfiguration,
		configurationlistener : null,
		cancelEditConfigurationEvent : null,
		addConfigurationEvent : null,
		configRequestBody : {},
		templateData : {},
		removeConfiguationEvent : null,
		configType : null,
		updateConfigEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.configurationlistener = new Clazz.com.components.configuration.js.listener.ConfigurationListener(globalConfig);
			self.registerEvents(self.configurationlistener);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this, res = {};
			if (self.configClick === "EditConfiguration") {
				self.configName = ""; 
				self.configRequestBody.envSpecific = self.envSpecificVal;
				commonVariables.envSpecifig = self.envSpecificVal;
				self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "edit"), function(response) {
					self.configurationlistener.defaultEnv = response.data.defaultEnv;
					self.configurationlistener.oldEnvName = response.data.name;
					if(response.data.configurations !== undefined) {
					$.each(response.data.configurations, function(index, value){
						if (value.type !== "Other") {
							if(value.type === "Components" || value.type === "Features") {
								self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "configType", value.type), function(response) {
									self.configurationlistener.htmlForOther(response.data, value, value.type);
								});
							} else {
								self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "template", value.type), function(response) {
									self.configurationlistener.constructHtml(response, value, response.data.settingsTemplate.name, self.envSpecificVal);
								});
							}
						} else {
							setTimeout(function(){
								self.configurationlistener.htmlForOther('', value, '');
							},800);
						}
					});
					} else {
						res = response.data;
						self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "template", res.type), function(response) {
							commonVariables.updateConfigName = res.name;
							self.configurationlistener.constructHtml(response, res, response.data.settingsTemplate.name, self.envSpecificVal);
						});
					}
					self.templateData.configurations = response.data;
					self.renderValues();
					renderFunction(self.templateData, whereToRender);
				});	
			} else {
				self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "template", self.configName), function(response) {
					commonVariables.updateConfigName = '';
					self.configurationlistener.constructHtml(response, "", self.configName, self.envSpecificVal);
				});
				self.renderValues();
				renderFunction(self.templateData, whereToRender);
			}	
		},
		
		renderValues : function() {
			var self=this;
			self.configType = commonVariables.api.localVal.getJson('configTypes');
			self.templateData.configType = self.configType;
			self.templateData.envSpecificVal = self.envSpecificVal;
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			self.templateData.userPermissions = userPermissions;
			
		},
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			self.resizeConsoleWindow();
			commonVariables.navListener.currentTab = self.name;
			if(self.configName === "Content" || self.configName === "Theme") {
				$("#validationConsole").css("display", "block");
			}
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.cancelEditConfigurationEvent = new signals.Signal();
			self.addConfigurationEvent = new signals.Signal();
			self.updateConfigEvent = new signals.Signal();
			self.addNonEnvirinmentConfigEvent = new signals.Signal();
			self.validationConsoleEvent = new signals.Signal();
			self.cancelEditConfigurationEvent.add(configurationlistener.cancelEditConfiguration, configurationlistener);
			self.addNonEnvirinmentConfigEvent.add(configurationlistener.addConfiguration, configurationlistener);
			self.addConfigurationEvent.add(configurationlistener.addConfiguration, configurationlistener);
			self.updateConfigEvent.add(configurationlistener.updateConfig, configurationlistener);
			self.validationConsoleEvent.add(configurationlistener.validationConsole, configurationlistener);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.configurationlistener.windowResize();
			$(".tooltiptop").tooltip();
			
			$("#cancelEditConfig").click(function() {
				self.cancelEditConfigurationEvent.dispatch();
			});
			
			$("ul[name=configurations] li").click(function() {
				self.addConfigurationEvent.dispatch($(this).attr('name'));
			});
			
			$("#themeValidation").click(function() {
				self.validationConsoleEvent.dispatch($(this));
			});
			
			$("input[name=UpdateConfiguration]").unbind('click');
			$("input[name=UpdateConfiguration]").click(function() {
				self.checkForLock("configUpdate", '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						self.updateConfigEvent.dispatch();
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true, true);
					}	
				});		
			});

			self.customScroll($(".scrolldiv"));
			self.customScroll($(".popup_scroll"));
		}
	});

	return Clazz.com.components.configuration.js.EditConfiguration;
});