define(["framework/widgetWithTemplate", "navigation/listener/navigationListener"], function() {
	
	Clazz.createPackage("com.components.navigation.js");

	Clazz.com.components.navigation.js.navigation = Clazz.extend(Clazz.WidgetWithTemplate, {
		navigationEvent : null,
		navigationHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/navigation/template/navigation.tmp",
		configUrl: "../components/navigation/config/config.json",
		name : window.commonVariables.navigation,
		navigationListener : null,
		onAddNewProjectEvent : null,
		onMytabEvent : null,
		currentContent : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			self.navigationListener = commonVariables.navListener;
			self.registerEvents(self.navigationListener);
		},

		/***
		 * Called in once the navigation is success
		 *
		 */
		loadPage : function(){
			commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
			self.navigationListener = commonVariables.navListener;
		},
		
		registerEvents : function(navigationListener) {
			var self = this;
			self.onAddNewProjectEvent = new signals.Signal();
			self.onMytabEvent = new signals.Signal();
			self.onAddNewProjectEvent.add(navigationListener.onAddProject, navigationListener); 
			self.onMytabEvent.add(navigationListener.onMytabEvent, navigationListener); 
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			self.navigationListener.landingPage(self.currentContent);
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
			$('#addproject').unbind('click');
			$('#addproject').click(function(){
				self.onAddNewProjectEvent.dispatch();
			}); 
			
			$("#myTab li").click(function() {
				$("#myTab li a").removeClass("act");
				$(this).children().addClass("act");
				self.onMytabEvent.dispatch(this.id);
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			
		}
	});

	return Clazz.com.components.navigation.js.navigation;
});