define(["framework/widget", "login/api/loginAPI", "common/loading", "footer/footer", "header/header", "projectlist/projectList", "navigation/navigation"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		headerContent : null,
		footerContent : null,
		navigationContent : null,
		loginAPI : null,
		//self : this,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.loginAPI = new Clazz.com.components.login.js.api.LoginAPI();
		},
		
		/***
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		doLogin : function() {
			try{
				
				var self = this, header = self.getRequestHeader();
			
				if(self.loginValidation()){
					self.loadingScreen.showLoading();
					//TODO: call login service here and call appendPlaceholder in the success function
					self.loginAPI.doLogin(header, 
						function(response){
							if(response != undefined && response != null && response.row.validLogin == true){
								self.setUserInfo(response.row);
								self.appendPlaceholder();
								self.renderHeader();
								self.renderFooter();
								self.renderContent();
								self.renderNavigation();
							} else {
								//authendication failed
								self.loadingScreen.removeLoading();
							}
						}, 
						function(serviceError){
							//service access failed
							self.loadingScreen.removeLoading();
							$(".login_error_msg").text('authendication failed');
						}
					);
				}
			}catch(error){
				//Exception
				self.loadingScreen.removeLoading();
			}
		},
		
		
		setUserInfo : function(data){
			var self = this, userInfo = JSON.stringify(data);
			
			self.loginAPI.localVal.setSession("username",$("#username").val());
			self.loginAPI.localVal.setSession("password",$("#password").val());
			self.loginAPI.localVal.setSession("rememberMe",$("#rememberMe").is(":checked"));
			self.loginAPI.localVal.setSession("userInfo",userInfo);
		},
		
		loginValidation : function(){
			var self = this, bCheck = false;
			
			if(self.userNameValidation())
				bCheck = self.passwordValidation();
			else
				bCheck = false;

			return bCheck;
		},
		
		userNameValidation : function(){
			if($("#username").val() == undefined || $("#username").val() == null || $.trim($("#username").val()) == ""){
				$("#usernameDiv").addClass("loginuser_error");
				return false;
			}
			else{
				$("#usernameDiv").removeClass("loginuser_error");
				return true;
			}
		},
		
		passwordValidation : function(){
			if($("#password").val() == undefined || $("#password").val() == null || $.trim($("#password").val()) == ""){
				$("#passwordDiv").addClass("loginuser_error");
				return false;
			}
			else{
				$("#passwordDiv").removeClass("loginuser_error");
				return true;
			}
		},
		
		/***
		 * provides the request header
		 * @return: returns the contructed header
		 */
		getRequestHeader : function() {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify({"username" : $("#username").val(), "password" : $("#password").val()}),
				webserviceurl: commonVariables.webserviceurl + commonVariables.loginContext
			}

			return header;
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