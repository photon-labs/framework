define(["framework/widgetWithTemplate", "mevanService/listener/mevanServiceListener"], function() {
	Clazz.createPackage("com.components.login.js");

	Clazz.com.components.mevanService.js.MevanService = Clazz.extend(Clazz.WidgetWithTemplate, {
		mevanServiceEvent : null,
		mevanServiceListener : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/mevanService/template/mevanService.tmp",
		configUrl: "components/mevanService/config/config.json",
		name : commonVariables.mevanService,
		localConfig: null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			self.mevanServiceEvent = new signals.Signal();
			self.mevanServiceListener = new Clazz.com.components.mevanService.js.listener.MevanServiceListener();
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			this.mevanServiceEvent.add(this.mevanServiceListener.mvnServiceCall, this.mevanServiceListener);
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

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
		}
	});

	return Clazz.com.components.mevanService.js.MevanService;
});