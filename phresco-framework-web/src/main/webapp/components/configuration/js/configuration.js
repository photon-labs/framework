define(["framework/widgetWithTemplate", "configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.Configuration = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/configuration/template/configuration.tmp",
		configUrl: "components/configuration/config/config.json",
		name : commonVariables.configuration,
		configurationAPI : null,
		configurationlistener : null,
		editConfigurationEvent : null,
		configRequestBody : null,
		templateData : {},
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.configurationlistener = new Clazz.com.components.configuration.js.listener.ConfigurationListener(globalConfig);
			self.configurationAPI = 
			self.registerEvents(self.configurationlistener);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this, true);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, "list"), function(response) {
				self.templateData.configurationList = response.data;
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
			commonVariables.navListener.applyRBAC(commonVariables.configuration);
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.editConfigurationEvent = new signals.Signal();
			self.editConfigurationEvent.add(configurationlistener.editConfiguration, configurationlistener);
		},
		
		getAction : function(configRequestBody, action, deleteEnvironment) {
			var self=this;
			self.configurationlistener.getConfigurationList(self.configurationlistener.getRequestHeader(self.configRequestBody, action, deleteEnvironment), function(response) {
				self.loadPage();
			});	
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			$("a[name=clone_pop]").unbind("click");
			$("a[name=clone_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=env_pop]").unbind("click");
			$("input[name=env_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name='deleteEnv']").unbind('click');
			$("input[name='deleteEnv']").click(function(e) {
				deleteEnvironment = $(this).parent().parent().attr('id');
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', deleteEnvironment);
			});
			
			$("a[name=editConfiguration]").unbind("click");
			$("a[name=editConfiguration]").click(function() {
				self.editConfigurationEvent.dispatch($(this).attr('key'));
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.configuration.js.Configuration;
});