define(["framework/widget", "application/api/applicationAPI"], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		applicationAPI : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.applicationAPI = new Clazz.com.components.application.js.api.ApplicationAPI();
		},
		
		onProjects : function() {
		
		}
		
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});