define(["framework/widgetWithTemplate", "configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.EditConfiguration = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/editConfiguration.tmp",
		configUrl: "components/configuration/config/config.json",
		name : commonVariables.editConfiguration,
		configurationlistener : null,
		cancelEditConfiguationEvent : null,
		addConfiguationEvent : null,
		configRequestBody : null,
		templateData : {},
		removeConfiguationEvent : null,
		configType : null,
		
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
			var self = this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "edit"), function(response) {
				$.each(response.data.configurations, function(index, value){
					if (value.type !== "Other") {
						self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "template", value.type), function(response) {
							self.configurationlistener.constructHtml(response, value);
						});
					} else {
						setTimeout(function(){
							self.configurationlistener.htmlForOhter(value);
						},800);
					}
				});
				self.templateData.configurations = response.data;
				self.templateData.configType = self.configType;
				var userPermissions = JSON.parse(self.configurationlistener.configurationAPI.localVal.getSession('userPermissions'));
				self.templateData.userPermissions = userPermissions;
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
			commonVariables.navListener.currentTab = self.name;
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.cancelEditConfiguationEvent = new signals.Signal();
			self.addConfiguationEvent = new signals.Signal();
			self.UpdateConfigEvent = new signals.Signal();
			self.cancelEditConfiguationEvent.add(configurationlistener.cancelEditConfiguation, configurationlistener);
			self.addConfiguationEvent.add(configurationlistener.addConfiguation, configurationlistener);
			self.UpdateConfigEvent.add(configurationlistener.UpdateConfig, configurationlistener);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			
			$("#cancelEditConfig").click(function() {
				self.cancelEditConfiguationEvent.dispatch();
			});
			
			$("ul[name=configurations] li").click(function() {
				self.addConfiguationEvent.dispatch($(this).attr('name'));
			});
			
			$("input[name=UpdateConfiguration]").unbind('click');
			$("input[name=UpdateConfiguration]").click(function() {
				self.UpdateConfigEvent.dispatch();
			});
		}
	});

	return Clazz.com.components.configuration.js.EditConfiguration;
});