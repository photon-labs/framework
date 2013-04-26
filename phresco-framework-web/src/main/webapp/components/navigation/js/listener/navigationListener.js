define(["framework/widget", "navigation/api/navigationAPI", "projects/project", "application/application", "features/features", "codequality/codequality", "configuration/configuration", "build/build"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,
		project : null,
		applications : null,
		featurelist : null,
		configuration : null, 
		build : null,
		currentTab : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.project = new Clazz.com.components.projects.js.Project();
			self.applications = Clazz.com.components.application.js.Application();
			self.featurelist = new Clazz.com.components.features.js.Features();
			self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
			self.configuration = new Clazz.com.components.configuration.js.Configuration();
			self.build = new Clazz.com.components.build.js.Build();
		},
		
		onAddProject : function() {
			var self = this;
			if(self.project === null) {
				self.project = new Clazz.com.components.projects.js.Project();
			}
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.project, true);
		},
		
		onMytabEvent : function(keyword) {
			var self=this;
			
			if (self.currentTab !== commonVariables.editApplication) {
				if (keyword === commonVariables.editApplication) {
					self.currentTab = keyword;
					if (self.applications === null || self.applications === undefined) {
						self.applications = new Clazz.com.components.application.js.Application();
					}
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(self.applications, true);
				}
			}
			
			if (self.currentTab !== commonVariables.featurelist) {
				if (keyword === commonVariables.featurelist) {
					self.currentTab = keyword;
					if (self.featurelist === null || self.featurelist === undefined) {
						self.featurelist = new Clazz.com.components.features.js.Features();
					}
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(self.featurelist, true);
				}
			}
			
			if (self.currentTab !== commonVariables.codequality) {
				if (keyword === commonVariables.codequality) {
					self.currentTab = keyword;
					if (self.codequality === null || self.codequality === undefined) {
						self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
					}
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(self.codequality, true);
				}
			}
			
			if (self.currentTab !== commonVariables.configuration) {
				if (keyword === commonVariables.configuration) {
					self.currentTab = keyword;
					if (self.configuration === null || self.configuration === undefined) {
						self.configuration = new Clazz.com.components.configuration.js.Configuration();
					}
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(self.configuration, true);
				}
			}
			
			if (self.currentTab !== commonVariables.build) {
				if (keyword === commonVariables.build) {
					self.currentTab = keyword;
					if (self.build === null || self.build === undefined) {
						self.build = new Clazz.com.components.build.js.Build();
					}
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(self.build, true);
				}
			}
		}
		
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});