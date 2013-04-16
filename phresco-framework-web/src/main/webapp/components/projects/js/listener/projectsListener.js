define(["framework/widget", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js.listener");

	Clazz.com.components.projects.js.listener.projectsListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
		}
		
	});

	return Clazz.com.components.projects.js.listener.projectsListener;
});