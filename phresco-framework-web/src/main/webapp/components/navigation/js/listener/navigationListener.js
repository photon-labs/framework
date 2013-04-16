define(["framework/widget", "navigation/api/navigationAPI", "projects/addproject"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,
		addproject : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.addproject = new Clazz.com.components.projects.js.AddProject();
		},
		
		onAddProject : function() {
			var self = this;
			if(self.addproject === null) {
				self.addproject = new Clazz.com.components.projects.js.AddProject();
			}
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.addproject, true);
		}
		
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});