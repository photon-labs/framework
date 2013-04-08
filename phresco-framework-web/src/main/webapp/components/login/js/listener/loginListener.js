define(["framework/widget", "login/api/loginAPI", "common/loading", "header/header"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.Widget, {
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
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		doLogin : function() {
			var self = this;
			//TODO: call login service here and call appendPlaceholder in the success function
			self.appendPlaceholder();
			self.renderHeader();
			
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
			Clazz.navigationController.push(self.headerContent);
		},
		
		renderNavigation : function() {
		},
		
		renderContent : function() {
		},
		
		renderFooter : function() {
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});