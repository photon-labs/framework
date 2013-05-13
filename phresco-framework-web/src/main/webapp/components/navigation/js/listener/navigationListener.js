define(["framework/widget", "navigation/api/navigationAPI", "projects/project", "projects/editproject", "application/application", "features/features", "codequality/codequality", "configuration/configuration", "build/build"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		navAPI : null,
		localStorageAPI : null,
		loadingScreen : null,
		header : null,
		footer : null,
		projectlist : null,
		project : null,
		editApplication : null,
		featurelist : null,
		codequality : null,
		configuration : null, 
		build : null,
		currentTab : null,
		editproject : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.navAPI = new Clazz.com.components.navigation.js.api.navigationAPI();
		},
		
		onAddProject : function() {
			var self = this;
			self.getMyObj(commonVariables.project);
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.project, true);
		},
		
		landingPage : function(){
			var self = this;
			self.renderHeader();
			self.renderContent();
			self.renderFooter();
		},
		
		getMyObj : function(keyword){
			var self=this, retuenObj;

				switch(keyword){
				
					case commonVariables.header :
						
						if(self.header === null)
							self.header = new Clazz.com.commonComponents.modules.header.js.Header();
							
						retuenObj = self.header;
						break;
						
					case commonVariables.footer :
						
						if(self.footer === null)
							self.footer = new Clazz.com.commonComponents.modules.footer.js.Footer();
							
						retuenObj = self.footer;
						break;
						
					case commonVariables.projectlist :
						
						if(self.projectlist === null)
							self.projectlist = new Clazz.com.components.projectlist.js.ProjectList();
							
						retuenObj = self.projectlist;
						break;
						
					case commonVariables.editApplication :
						
						if(self.editApplication === null)
							self.editApplication = new Clazz.com.components.application.js.Application();
							
						retuenObj = self.editApplication;
						break;
						
					case commonVariables.featurelist :
						
						if(self.featurelist === null)
							self.featurelist = new Clazz.com.components.features.js.Features();
							
						retuenObj = self.featurelist;
						break;
						
					case commonVariables.codequality :
						
						if(self.codequality === null)
							self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
							
						retuenObj = self.codequality;
						break;
						
					case commonVariables.configuration :
						
						if(self.configuration === null)
							self.configuration = new Clazz.com.components.configuration.js.Configuration();
							
						retuenObj = self.configuration;
						break;
						
					case commonVariables.build :
						
						if(self.build === null)
							self.build = new Clazz.com.components.build.js.Build();
							
						retuenObj = self.build;
						break;
						
					case commonVariables.project :
						
						if(self.project === null)
							self.project = new Clazz.com.components.projects.js.Project();
							
						retuenObj = self.project;
						break;
					
					case commonVariables.editproject :
						
						if(self.editproject === null)
							self.editproject = new Clazz.com.components.projects.js.EditProject();
							
						retuenObj = self.editproject;
						break;
				
				}
			
			return retuenObj;
		},
		
		renderHeader : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.headerPlaceholder;
			self.getMyObj(commonVariables.header);
			self.header.data = JSON.parse(self.navAPI.localVal.getSession('userInfo'));
			Clazz.navigationController.push(self.header, false);
		},
		
		renderContent : function(){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.getMyObj(commonVariables.projectlist);
			Clazz.navigationController.push(self.projectlist, true);
		},
		
		renderFooter : function(){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.footerPlaceholder;
			self.getMyObj(commonVariables.footer);
			Clazz.navigationController.push(self.footer, false);
		},
		
		onMytabEvent : function(keyword) {
			var self=this, currentObj;
			
			if (self.currentTab !== commonVariables.editApplication && keyword === commonVariables.editApplication){
				currentObj = self.getMyObj(commonVariables.editApplication);
			}else if (self.currentTab !== commonVariables.featurelist && keyword === commonVariables.featurelist){
				currentObj = self.getMyObj(commonVariables.featurelist);
			}else if (self.currentTab !== commonVariables.codequality && keyword === commonVariables.codequality){
				currentObj = self.getMyObj(commonVariables.codequality);
			}else if (self.currentTab !== commonVariables.configuration && keyword === commonVariables.configuration){
				currentObj = self.getMyObj(commonVariables.configuration);
			}else if (self.currentTab !== commonVariables.build && keyword === commonVariables.build){
				currentObj = self.getMyObj(commonVariables.build);
			}

			if(currentObj != undefined && currentObj != null){
				self.currentTab = keyword;
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(currentObj, true);
			}
		}
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});