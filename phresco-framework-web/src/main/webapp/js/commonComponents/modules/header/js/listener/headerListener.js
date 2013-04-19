define(["framework/widget", "common/loading", "header/api/headerAPI", "login/login"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		headerAPI : null,

		initialize : function(config){
			 this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
		},
		
		doLogout : function(){
			this.headerAPI.localVal.clearSession();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			
			var loginView = new Clazz.com.components.login.js.Login();
			loginView.loadPage();
		},
		
		
		
	});

	return Clazz.com.commonComponents.modules.header.js.listener.HeaderListener;
});