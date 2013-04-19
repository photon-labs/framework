define(["framework/widget", "navigation/api/navigationAPI", "projects/addproject", "application/application", "features/features", "codequality/codequality"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,
		addproject : null,
		applications : null,
		featurelist : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.addproject = new Clazz.com.components.projects.js.AddProject();
			self.applications = Clazz.com.components.application.js.Application();
			self.featurelist = new Clazz.com.components.features.js.Features();
			self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
		},
		
		onAddProject : function() {
			var self = this;
			if(self.addproject === null) {
				self.addproject = new Clazz.com.components.projects.js.AddProject();
			}
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.addproject, true);
		},
		
		onMytabEvent : function(keyword) {
			var self=this;

			if (keyword === commonVariables.editApplication) {
				if (self.applications === null || self.applications === undefined) {
					self.applications = new Clazz.com.components.application.js.Application();
				}
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(self.applications, true);
			}
			
			if (keyword === commonVariables.featurelist) {
				if (self.featurelist === null || self.featurelist === undefined) {
					self.featurelist = new Clazz.com.components.features.js.Features();
				}
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(self.featurelist, true);
			}
			
			if (keyword === commonVariables.codequality) {
				if (self.codequality === null || self.codequality === undefined) {
					self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
				}
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(self.codequality, true);
			}
		}
		
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});