define(["framework/widget", "codequality/api/codequalityAPI"], function() {

	Clazz.createPackage("com.components.codequality.js.listener");

	Clazz.com.components.codequality.js.listener.CodequalityListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		codequalityAPI : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.codequalityAPI = new Clazz.com.components.codequality.js.api.CodeQualityAPI();
		},
		
		onProjects : function() {
		
		}
		
	});

	return Clazz.com.components.codequality.js.listener.CodequalityListener;
});