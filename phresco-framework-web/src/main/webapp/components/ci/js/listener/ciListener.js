define(["framework/widget", "ci/api/ciAPI"], function() {

	Clazz.createPackage("com.components.ci.js.listener");

	Clazz.com.components.ci.js.listener.CIListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		ciAPI : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.ciAPI = new Clazz.com.components.ci.js.api.CIAPI();
		},
		
		onProjects : function() {
		
		}
		
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});