define(["header/api/headerAPI"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		headerAPI : null,
		projectListContent: null,
		currentTab : null,
		
		initialize : function(config){
			if(this.headerAPI === null)
				this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
		},
		
		doLogout : function(){
			this.clearSession();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			this.removePlaceholder();
			this.headerAPI.localVal.setSession('loggedout', 'true');
			location.hash = '';
			location.reload();
		},
		
		clearSession : function(){
			var username = this.headerAPI.localVal.getSession('username'), password = this.headerAPI.localVal.getSession('password'), rememberMe = this.headerAPI.localVal.getSession('rememberMe');
			
			this.headerAPI.localVal.clearSession();
			
			if(rememberMe == "true"){
				this.headerAPI.localVal.setSession('username', username);
				this.headerAPI.localVal.setSession('password', password);
				this.headerAPI.localVal.setSession('rememberMe', "true");
			}
		},
		
		loadTab : function(){
			var self = this, currentObj = null;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			if(self.currentTab == "Dashboard"){
			}else if(self.currentTab == "Projects"){
				if (currentObj === null) {
					commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal) {
						currentObj = retVal;
					});
				}
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
			var self=this,obj=null;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			$("#selectedCustomer").text(customerValue);
				if (obj === null) {
					commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal) {
						obj = retVal;
					});
			}
			$('.proj_list').children('table').each(function() {
				if(obj != null)
					Clazz.navigationController.push(obj, true);
			});
		}
	});

	return Clazz.com.commonComponents.modules.header.js.listener.HeaderListener;
});