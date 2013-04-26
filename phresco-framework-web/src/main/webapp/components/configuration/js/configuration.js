define(["framework/widgetWithTemplate", "configuration/listener/configurationListener"], function() {
	Clazz.createPackage("com.components.configuration.js");

	Clazz.com.components.configuration.js.Configuration = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/configuration/template/configuration.tmp",
		configUrl: "../components/configuration/config/config.json",
		name : commonVariables.configuration,
		configurationlistener : null,
		configuationlistEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.configurationlistener = new Clazz.com.components.configuration.js.listener.ConfigurationListener();
			self.registerEvents(self.configurationlistener);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
			self.configuationlistEvent = new signals.Signal();
			self.configuationlistEvent.add(configurationlistener.listConfiguration, configurationlistener);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			$("a[name=clone_pop]").unbind("click");
			$("a[name=clone_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=env_pop]").unbind("click");
			$("input[name=env_pop]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.configuration.js.Configuration;
});