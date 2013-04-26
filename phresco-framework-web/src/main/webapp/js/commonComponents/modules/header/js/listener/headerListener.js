define(["framework/widget", "common/loading", "header/api/headerAPI", "login/login", "projectlist/projectList"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		headerAPI : null,
		projectListContent: null,
		
		initialize : function(config){
			 this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
		},
		
		doLogout : function(){
			this.clearSession();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			
			//$(Clazz.navigationController.jQueryContainer).children().remove();
			this.removePlaceholder();
			
			var loginView = new Clazz.com.components.login.js.Login();
			loginView.loadPage();
		},
		
		clearSession : function(){
			if(this.headerAPI.localVal.getSession('rememberMe') == "true"){
				this.headerAPI.localVal.deleteSession('userInfo');
			}else{
				this.headerAPI.localVal.clearSession();
			}
		},
		
		projectList : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.projectListContent = new Clazz.com.components.projectlist.js.ProjectList();
			Clazz.navigationController.push(self.projectListContent, true);
		},
		
		removePlaceholder : function() {
			$(commonVariables.headerPlaceholder).remove();
			$(commonVariables.headerPlaceholder).empty();
			
			$(commonVariables.navigationPlaceholder).remove();
			$(commonVariables.navigationPlaceholder).empty();
			
			$(commonVariables.contentPlaceholder).remove();
			$(commonVariables.contentPlaceholder).empty();
			
			$(commonVariables.footerPlaceholder).remove();
			$(commonVariables.footerPlaceholder).empty();
		},
		
		selectCoustomer : function(customerValue) {
			$("#selectedCustomer").text(customerValue);
		}
	});

	return Clazz.com.commonComponents.modules.header.js.listener.HeaderListener;
});