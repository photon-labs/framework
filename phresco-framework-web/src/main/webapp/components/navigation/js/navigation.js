define(["framework/widgetWithTemplate", "navigation/listener/navigationListener"], function() {
	
	Clazz.createPackage("com.components.navigation.js");

	Clazz.com.components.navigation.js.navigation = Clazz.extend(Clazz.WidgetWithTemplate, {
		navigationEvent : null,
		navigationHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/navigation/template/navigation.tmp",
		configUrl: "../components/navigation/config/config.json",
		name : window.commonVariables.navigation,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			/* this.onnavigationEvent = new signals.Signal(); */
		},

		/***
		 * Called in once the navigation is success
		 *
		 */
		loadPage : function(){
			var navigationListener = new Clazz.com.components.navigation.js.listener.navigationListener();
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			/* $('#navigation').click(function(){
				self.onnavigationEvent.dispatch();
			}); */
		
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
		}
	});

	return Clazz.com.components.navigation.js.navigation;
});