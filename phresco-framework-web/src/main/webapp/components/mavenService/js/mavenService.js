define(["mavenService/listener/mavenServiceListener"], function() {
	Clazz.createPackage("com.components.mavenService.js");

	Clazz.com.components.mavenService.js.MavenService = Clazz.extend(Clazz.WidgetWithTemplate, {
		mavenServiceEvent : null,
		mavenServiceListener : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/mavenService/template/mavenService.tmp",
		configUrl: "components/mavenService/config/config.json",
		name : commonVariables.mavenService,
		localConfig: null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			self.mavenServiceEvent = new signals.Signal();
			
			if(self.mavenServiceListener === null)
				self.mavenServiceListener = new Clazz.com.components.mavenService.js.listener.MavenServiceListener();
		},

		/***
		 * Called in once success
		 *
		 */
		loadPage : function(){
			this.mavenServiceEvent.add(this.mavenServiceListener.mvnServiceCall, this.mavenServiceListener);
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

	return Clazz.com.components.mavenService.js.MavenService;
});