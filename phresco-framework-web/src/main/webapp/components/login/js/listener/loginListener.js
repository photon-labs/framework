define(["login/api/loginAPI"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
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
			var self = this;

			if(self.navigationContent === null){
				require(["navigation/navigation"], function(){
					self.navigationContent = new Clazz.com.components.navigation.js.navigation();	
				});
			}	
			if(self.loginAPI === null){
				self.loginAPI = new Clazz.com.components.login.js.api.LoginAPI();
			}	
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
					//commonVariables.loadingScreen.showLoading();
					//TODO: call login service here and call appendPlaceholder in the success function
					self.loginAPI.doLogin(header, 
						function(response){
							if(response !== undefined && response !== null && response.status !== "error" && response.status !== "failure"){
								self.setUserInfo(response.data);
								self.appendPlaceholder();
								self.renderNavigation();
							} else {
								//authentication failed
								//commonVariables.loadingScreen.removeLoading();
								if(response.errorCode === "PHR110001") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.isempty');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110002") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.unauthorizeduser');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110003") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.invalidcredential');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110004") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.authservicedown');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110005") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.jsonnotfound');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110006") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.parsingfailed');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.errorCode === "PHR110007") {
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.invalidpermissions');
									self.renderlocales(commonVariables.basePlaceholder);
								}
							}
						}, 
						function(serviceError){
							//service access failed
							//commonVariables.loadingScreen.removeLoading();
							$(".login_error_msg").text('Service down');
						}
					);
				}
			}catch(error){
				//Exception
				//commonVariables.loadingScreen.removeLoading();
			}
		},
		
		pageRefresh : function(contentObj){
			var self = this;
			self.appendPlaceholder();
			self.renderNavigation(contentObj);
		},
		
		setUserInfo : function(data){
			var self = this, userInfo = JSON.stringify(data);
			self.loginAPI.localVal.setSession("username",$("#username").val());
			self.loginAPI.localVal.setSession("password",$("#password").val());
			self.loginAPI.localVal.setSession("rememberMe",$("#rememberMe").is(":checked"));
			self.loginAPI.localVal.setSession("userInfo",userInfo);
			self.loginAPI.localVal.setSession("userPermissions", JSON.stringify(data.permissions));
		},
		
		loginValidation : function(){
			var self = this, bCheck = false;
			
			if(self.userNameValidation()){
				bCheck = self.passwordValidation();
			}
			else{
				bCheck = false;
			}
			return bCheck;
		},
		
		userNameValidation : function(){
			
			if ($("#username").val() === undefined || $("#username").val() === null || $.trim($("#username").val()) === ""){
				$("#username").attr('placeholder','Enter Username');
				$("#usernameDiv").addClass("loginuser_error");
				return false;
			} else { 
				$("#usernameDiv").removeClass("loginuser_error");
				return true;
			}
		},
		
		passwordValidation : function(){
			if($("#password").val() === undefined || $("#password").val() === null || $.trim($("#password").val()) === ""){
				$("#password").attr('placeholder','Enter Password');
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
			};

			return header;
		},
		
		appendPlaceholder : function() {
			$(commonVariables.basePlaceholder).empty();
			$(commonVariables.basePlaceholder).append(commonVariables.headerPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.navigationPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.contentPlaceholder);
			$(commonVariables.basePlaceholder).append(commonVariables.footerPlaceholder);
		},
		
		renderNavigation : function(contentObj) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.navigationPlaceholder;

			if(self.navigationContent === null){
				require(["navigation/navigation"], function(){
					self.navigationContent = new Clazz.com.components.navigation.js.navigation();
					self.loadNavigationPage(contentObj);
				});
			}else{ self.loadNavigationPage(contentObj); }
		},
		
		loadNavigationPage : function(contentObj){
			var self = this;
			self.navigationContent.currentContent = contentObj;
			Clazz.navigationController.push(self.navigationContent, false);	
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});