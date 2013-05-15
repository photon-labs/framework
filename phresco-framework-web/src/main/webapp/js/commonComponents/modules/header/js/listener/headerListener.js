define(["framework/widget", "common/loading", "header/api/headerAPI", "login/login", "projectlist/projectList"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		headerAPI : null,
		projectListContent: null,
		currentTab : null,
		
		initialize : function(config){
			 this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
		},
		
		doLogout : function(){
			this.clearSession();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			this.removePlaceholder();
			location.hash = '';
			location.reload();
		},
		
		clearSession : function(){
			if(this.headerAPI.localVal.getSession('rememberMe') == "true"){
				this.headerAPI.localVal.deleteSession('userInfo');
			}else{
				this.headerAPI.localVal.clearSession();
			}
		},
		
		loadTab : function(){
			var self = this, currentObj = null;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			if(self.currentTab == "Dashboard"){
			}else if(self.currentTab == "Projects"){
				currentObj = commonVariables.navListener.getMyObj(commonVariables.projectlist);
				commonVariables.navListener.currentTab = commonVariables.projectlist;
			}else if(self.currentTab == "Settings"){
			}else if(self.currentTab == "Downloads"){
			}else if(self.currentTab == "Admin"){
			}	

			if(currentObj != null){
				Clazz.navigationController.push(currentObj, true);
			}
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