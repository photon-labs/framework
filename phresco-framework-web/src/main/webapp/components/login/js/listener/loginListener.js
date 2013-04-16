define(["framework/widget", "login/api/loginAPI", "common/loading", "footer/footer", "header/header", "projectlist/projectList", "navigation/navigation"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,
		footerContent : null,
		navigationContent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
		},
		
		/***
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		doLogin : function() {
			var self = this;
			//TODO: call login service here and call appendPlaceholder in the success function
			self.appendPlaceholder();
			self.renderHeader();
			self.renderFooter();
			self.renderContent();
			self.renderNavigation();
		},
		
		appendPlaceholder : function() {
			$(commonVariables.basePlaceholder).empty();
			$(commonVariables.basePlaceholder).append(commonVariables.headerPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.navigationPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.contentPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.footerPlaceholder);
		},
		
		renderHeader : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.headerPlaceholder;
			self.headerContent = new Clazz.com.commonComponents.modules.header.js.Header();
			Clazz.navigationController.push(self.headerContent, false);
		},
		
		renderNavigation : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.navigationPlaceholder;
			self.navigationContent = new Clazz.com.components.navigation.js.navigation();
			Clazz.navigationController.push(self.navigationContent, false);
		},
		
		renderContent : function(){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectlistContent = new Clazz.com.components.projectlist.js.ProjectList();
			Clazz.navigationController.push(self.projectlistContent, true);
		},
		
		renderFooter : function(){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.footerPlaceholder;
			self.footerContent = new Clazz.com.commonComponents.modules.footer.js.Footer();
			Clazz.navigationController.push(self.footerContent, false);
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});