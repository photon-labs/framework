define(["framework/widgetWithTemplate", "login/listener/loginListener"], function() {
	Clazz.createPackage("com.components.login.js");

	Clazz.com.components.login.js.Login = Clazz.extend(Clazz.WidgetWithTemplate, {
		onLoginEvent : null,
		loginListener : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/login/template/login.tmp",
		configUrl: "components/login/config/config.json",
		name : commonVariables.login,
		localConfig: null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			
			if(self.onLoginEvent === null){
				self.onLoginEvent = new signals.Signal();
			}
			if(self.loginListener === null){
				self.loginListener = new Clazz.com.components.login.js.listener.LoginListener();
			}	
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			$(commonVariables.basePlaceholder).empty();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			this.onLoginEvent.add(this.loginListener.doLogin, this.loginListener);
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this;
			if(commonVariables.api.localVal.getSession('favicon') !== null){
				$("#favicon").attr("href", "data:image/png;base64," + commonVariables.api.localVal.getSession('favicon'));
			} else {
				$("#favicon").attr("href", "themes/default/images/Phresco/favicon.png");
			}
			
			if(commonVariables.api.localVal.getSession('customerlogo') !== null &&
				commonVariables.api.localVal.getSession('customerlogo') !== ""){
				$('#loginlogo').attr("src", "data:image/png;base64," + commonVariables.api.localVal.getSession('customerlogo'));
			} else {
				$('#loginlogo').attr("src", "themes/default/images/Phresco/logo_login.png");
			}
			
			if(commonVariables.api.localVal.getSession('loggedout') === "true" || commonVariables.api.localVal.getSession('multiplelogout') === "true"){
				$(".login_error_msg").text('Logged out');
				$(".login_error_msg").css('color','green');
			}
			commonVariables.api.localVal.deleteSession('loggedout');
			
			if(commonVariables.api.localVal.getSession('statusmsg') !== null){
				if(commonVariables.api.localVal.getSession('statusmsg') === "Customer Context Required"){
					$("#contex_er").show();
				}else if(commonVariables.api.localVal.getSession('statusmsg') === "Invalid Customer"){
					var finalUrl, oldUrl = location.href.split('/');
					finalUrl = location.href.replace(oldUrl[oldUrl.length -1], '#');
					history.pushState('', 'Phresco', finalUrl);
					$(".login_error_msg").css('color','red');
					$(".login_error_msg").text(commonVariables.api.localVal.getSession('statusmsg'));
				}else{
					$(".login_error_msg").css('color','red');
					$(".login_error_msg").text(commonVariables.api.localVal.getSession('statusmsg'));
				}
			}
			
			var themeValue = $.parseJSON(commonVariables.api.localVal.getSession('customertheme'));
			if (themeValue !== null && themeValue !== undefined && themeValue !== '') {
				commonVariables.customerContext = themeValue.context;
			}
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;

			//Login btn click Event
			$('#login').click(function(){
				$('#login').focus();
				$('#login').attr('disabled', '');
				self.onLoginEvent.dispatch();
				$(".login_error_msg").text('');
			});
			
			//Enter Key Press Event
			document.onkeydown = function(evt) {
				evt = evt || window.event;
				if (evt.keyCode === 13) {
					$('#login').focus();
					$('#login').attr('disabled', '');
					self.onLoginEvent.dispatch();
					$(".login_error_msg").text('');
				}
			};
			
			//Key press Event
			$('#login, #rememberMe').keypress(function(e){
				if(e.keyCode === 13){
					$('#login').focus();
					$('#login').attr('disabled', '');
					self.onLoginEvent.dispatch();
					$(".login_error_msg").text('');
				}	
			});
			
			// Control validation Event
			/* $('#username').focusout(function(){
				self.loginListener.userNameValidation();
			}); */
			
			// Control validation Event
			/* $('#password').focusout(function(){
				self.loginListener.passwordValidation();
			}); */
			
			//Set rememberMe chk box val
			if(commonVariables.api.localVal.getSession('rememberMe') === "true"){
				$('#rememberMe').prop('checked', true);
				$('#username').val(commonVariables.api.localVal.getSession('username'));
				$('#password').val(commonVariables.api.localVal.getSession('password'));
			}
			
			$('#username').focus();
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			$('#username, #password').keypress(function(e) {
				if (e.which === 32) {
					e.preventDefault();
				} 
			});
			
			$("#forgotpassword").click(function() {
				self.openccdashboardsettings(this,'forgot_password');
				$("#userid").removeClass('errormessage');
				
			});
			
			$("#confirm_forgot_password").click(function() {
				var userid = $("#userid");
				if(userid.val() === '') {
					userid.addClass('errormessage');
					userid.focus();
					userid.attr('placeholder','Enter user');
				} else {
					 self.loginListener.forgotpassword(self.loginListener.getRequestHeader("forgotpassword"), function(response) {
					});
				}	
			});
			
		}
	});

	return Clazz.com.components.login.js.Login;
});