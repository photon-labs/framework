define(["framework/widget", "navigation/api/navigationAPI", "common/loading", "header/header"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		/***
		 * Verify the navigation and navigate to dashboard page if the navigation is successful
		 * 
		 * @header: constructed header for each call
		 */
		
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});