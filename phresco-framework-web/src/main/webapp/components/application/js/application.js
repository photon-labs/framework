define(["framework/widgetWithTemplate", "application/listener/applicationListener"], function() {
	Clazz.createPackage("com.components.application.js");

	Clazz.com.components.application.js.Application = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/application/template/application.tmp",
		configUrl: "../components/projects/config/config.json",
		editApplicationListener: null,
		name : commonVariables.editApplication,
		addServerEvent : null,
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.editApplicationListener = new Clazz.com.components.application.js.listener.ApplicationListener(globalConfig);
			self.registerEvents(self.editApplicationListener);
		},
		
		registerEvents : function(editApplicationListener) {
			var self = this;
			self.onCancelEvent = new signals.Signal();
			self.onCancelEvent.add(editApplicationListener.onCancelUpdate, editApplicationListener); 
		},
		
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			
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
			
			$('#cancelbutton').unbind('click');
			$('#cancelbutton').click(function(){
				self.onCancelEvent.dispatch();
			}); 
			
			self.editApplicationListener.addServerDatabaseEvent();
			self.editApplicationListener.removeServerDatabaseEvent();
		}
	});

	return Clazz.com.components.application.js.Application;
});