define(["framework/widget", "login/api/loginAPI", "common/loading"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		basePlaceholder : commonVariables.basePlaceholder,

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
		doLogin : function(header) {
			var self = this;
			//TODO: call login service here and call appendPlaceholder in the success function
			self.appendPlaceholder();
			
		},
		
		appendPlaceholder : function(){
			$(this.basePlaceholder).empty();
			$(this.basePlaceholder).append(commonVariables.headerPlaceholder);
			$(this.basePlaceholder).append(commonVariables.navigationPlaceholder);
			$(this.basePlaceholder).append(commonVariables.contentPlaceholder);
			$(this.basePlaceholder).append(commonVariables.footerPlaceholder);
		},
		
		renderHeader : function(){
		},
		
		renderNavigation : function(){
		},
		
		renderContent : function(){
		},
		
		renderFooter : function(){
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});