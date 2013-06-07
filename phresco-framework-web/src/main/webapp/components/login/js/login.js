define(["login/listener/loginListener"], function() {
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
			
			if(self.onLoginEvent == null)
				self.onLoginEvent = new signals.Signal();
			
			if(self.loginListener == null)
				self.loginListener = new Clazz.com.components.login.js.listener.LoginListener();
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
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
			
			if(self.loginListener.loginAPI.localVal.getSession('customerlogo') != null &&
				self.loginListener.loginAPI.localVal.getSession('customerlogo') != ""){
				$('#loginlogo').attr("src", "data:image/png;base64," + self.loginListener.loginAPI.localVal.getSession('customerlogo'));
			} else {
				$('#loginlogo').attr("src", "themes/default/images/helios/logo_login.png");
			}
			
			if(self.loginListener.loginAPI.localVal.getSession('loggedout') == "true"){
				$(".login_error_msg").text('Logged out');
			}
			self.loginListener.loginAPI.localVal.deleteSession('loggedout');
			
			if(self.loginListener.loginAPI.localVal.getSession('statusmsg') != null){
				if(self.loginListener.loginAPI.localVal.getSession('statusmsg') == "Customer Context Required"){
					$("#contex_er").show();
				}else{
					$(".login_error_msg").text(self.loginListener.loginAPI.localVal.getSession('statusmsg'));
				}
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
				self.onLoginEvent.dispatch();
			});
			
			//Enter Key Press Event
			document.onkeydown = function(evt) {
				evt = evt || window.event;
				if (evt.keyCode == 13) {
					self.onLoginEvent.dispatch();
				}
			};
			
			//Key press Event
			$('#login, #rememberMe').keypress(function(e){
				if(e.keyCode == 13)
					self.onLoginEvent.dispatch();
			});
			
			// Control validation Event
			$('#username').focusout(function(){
				self.loginListener.userNameValidation();
			});
			
			// Control validation Event
			$('#password').focusout(function(){
				self.loginListener.passwordValidation();
			});
			
			//Set rememberMe chk box val
			if(self.loginListener.loginAPI.localVal.getSession('rememberMe') == "true"){
				$('#rememberMe').prop('checked', true);
				$('#username').val(self.loginListener.loginAPI.localVal.getSession('username'));
				$('#password').val(self.loginListener.loginAPI.localVal.getSession('password'));
			}
			
			$('#username').focus();
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.login.js.Login;
});