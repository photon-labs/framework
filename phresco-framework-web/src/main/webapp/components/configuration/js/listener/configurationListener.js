define(["framework/widget"], function() {

	Clazz.createPackage("com.components.configuration.js.listener");

	Clazz.com.components.configuration.js.listener.ConfigurationListener = Clazz.extend(Clazz.Widget, {
				
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

	return Clazz.com.components.configuration.js.listener.ConfigurationListener;
});