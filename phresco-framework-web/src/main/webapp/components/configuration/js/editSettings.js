define(["configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.EditSettings = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/editSettings.tmp",
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

		renderValues : function() {
			var self=this;
			self.configType = commonVariables.api.localVal.getJson('configTypes');
			self.templateData.configType = self.configType;
			self.templateData.favourite = self.favourite;
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			self.templateData.userPermissions = userPermissions;
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this, res = {};
			self.configName = ""; 
			self.configurationlistener.favourite = self.favourite;
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
									self.configurationlistener.constructHtml(response, value, response.data.settingsTemplate.name, false);
								});
							}
						} else {
							setTimeout(function(){
								self.configurationlistener.htmlForOther('', value, '');
							},800);
						}
					});
				}
				self.templateData.configurations = response.data;
				self.renderValues();
				renderFunction(self.templateData, whereToRender);
			});	
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.cancelEditConfigurationEvent = new signals.Signal();
			self.addConfigurationEvent = new signals.Signal();
			self.updateConfigEvent = new signals.Signal();
			self.cancelEditConfigurationEvent.add(configurationlistener.cancelEditConfiguration, configurationlistener);
			self.addConfigurationEvent.add(configurationlistener.addConfiguration, configurationlistener);
			self.updateConfigEvent.add(configurationlistener.updateConfig, configurationlistener);
		},
		
		validationForEnv : function() {
			var self=this;
			$('input[name=EnvName]').focus();
			$("input[name='EnvName']").attr('placeholder','Enter Environment Name');
			$("input[name='EnvName']").addClass("errormessage");
			$("input[name='EnvName']").bind('keypress', function() {
				$("input[name='EnvName']").removeClass("errormessage");
			});
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			self.configurationlistener.windowResize();
			$(".tooltiptop").tooltip();
			
			self.customScroll($(".scrolldiv"));
			self.customScroll($(".popup_scroll"));
			
			$("ul[name=configurations] li").click(function() {
				self.addConfigurationEvent.dispatch($(this).attr('name'));
			});
			
			$("input[name=UpdateConfiguration]").unbind('click');
			$("input[name=UpdateConfiguration]").click(function() {
				self.checkForLock("configUpdate", '', function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						if ($('input[name=EnvName]').val() !== '') {
							self.updateConfigEvent.dispatch();
						} else {
							self.validationForEnv();
						}
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true, true);
					}	
				});		
			});
			
			$("#cancelEditConfig").click(function() {
				self.cancelEditConfigurationEvent.dispatch();
			});
		}
	});

	return Clazz.com.components.configuration.js.EditSettings;
});