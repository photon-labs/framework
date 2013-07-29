define([], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		headerContent : null,
		footerContent : null,
		navigationContent : null,
		//loginAPI : null,
		enterKeyDisable : false,
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
		},
		
		/***
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		doLogin : function() {
			try{
				
				var self = this, header = self.getRequestHeader();
				self.enterKeyDisable = true;
				if(self.loginValidation()){
					//commonVariables.loadingScreen.showLoading();
					//TODO: call login service here and call appendPlaceholder in the success function
					commonVariables.api.ajaxRequest(header, 
						function(response){
							if(response !== undefined && response !== null && response.status !== "error" && response.status !== "failure"){
								self.enterKeyDisable = false;
								self.setUserInfo(response.data);
								self.appendPlaceholder();
								self.renderNavigation();
							} else {
								//authentication failed
								//commonVariables.loadingScreen.removeLoading();
								if(response.responseCode === "PHR110001") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.isempty');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110002") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.unauthorizeduser');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110003") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.invalidcredential');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110004") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.authservicedown');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110005") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.jsonnotfound');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110006") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.parsingfailed');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR110007") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'login.errormessage.invalidpermissions');
									self.renderlocales(commonVariables.basePlaceholder);
								} else if(response.responseCode === "PHR000000") {
									self.enterKeyDisable = false;
									$(".login_error_msg").attr('data-i18n', 'commonlabel.errormessage.unexpectedfailure');
									self.renderlocales(commonVariables.basePlaceholder);
								}
							}
						}, 
						function(serviceError){
							//service access failed
							//commonVariables.loadingScreen.removeLoading();
							$(".login_error_msg").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
							self.renderlocales(commonVariables.basePlaceholder);
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
			commonVariables.api.localVal.setSession("username",$("#username").val());
			commonVariables.api.localVal.setSession("password",$("#password").val());
			commonVariables.api.localVal.setSession("rememberMe",$("#rememberMe").is(":checked"));
			commonVariables.api.localVal.setSession("userInfo",userInfo);
			commonVariables.api.localVal.setSession("userPermissions", JSON.stringify(data.permissions));
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