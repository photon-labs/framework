define(["framework/widget"], function() {

	Clazz.createPackage("com.components.build.js.listener");

	Clazz.com.components.build.js.listener.BuildListener = Clazz.extend(Clazz.Widget, {
				
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
		},
		
		listConfiguration : function() {
		}
		
	});

	return Clazz.com.components.build.js.listener.BuildListener;
});