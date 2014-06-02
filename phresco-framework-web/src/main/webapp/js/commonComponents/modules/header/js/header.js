define(["header/listener/headerListener", "framework/widgetWithTemplate"] , function(template) {

	Clazz.createPackage("com.commonComponents.modules.header");

	Clazz.com.commonComponents.modules.header.js.Header = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "js/commonComponents/modules/header/template/header.tmp",
		configUrl: "js/commonComponents/modules/header/config/config.json",
		name : "header",
		
		//Events, to fire a function
		headerListener : null,
		headerEvent : null,
		onLogoutEvent: null,
		onTabChangeEvent: null,
		onSelectCustomerEvent: null,
		onAboutClickEvent: null,
		onUpgradeEvent: null,
		downloads: null,
		widgetTemp:null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
			if(self.headerListener === null){
				self.headerListener = new Clazz.com.commonComponents.modules.header.js.listener.HeaderListener(); 
			}	
			self.registerEvents(self.headerListener);
		},
		
		loadPage :function(){
			var self = this;
		},
		
		/***
		 * 
		 */
		postRender : function(element) {
			var self = this;
			if(self.headerListener.headerAPI.localVal.getSession('loginIcon') !== null &&
				self.headerListener.headerAPI.localVal.getSession('loginIcon') !== ""){
				$('#bannerlogo').attr("src", "data:image/png;base64," + self.headerListener.headerAPI.localVal.getSession('loginIcon'));
				$('#aboutimge').attr("src", "data:image/png;base64," + self.headerListener.headerAPI.localVal.getSession('loginIcon'));
			} else {
				$('#bannerlogo').attr("src", "themes/default/images/Phresco/helios_logo.png");
				$('#aboutimge').attr("src", "themes/default/images/Phresco/helios_logo.png");
			}
			if(self.headerListener.headerAPI.localVal.getSession('favicon') !== null){
			$("#favicon").attr("href", "data:image/png;base64," + commonVariables.api.localVal.getSession('favicon'));
				$("#favicon1").attr("href", "data:image/png;base64," + commonVariables.api.localVal.getSession('favicon'));
				
			} else {
			$("#favicon").attr("href", "themes/default/images/Phresco/favicon.png");
				$("#favicon1").attr("href", "themes/default/images/Phresco/favicon.png");
				
			}
			var customername = self.headerListener.headerAPI.localVal.getSession('customername');
			if (customername !== null && customername !== undefined && customername !== "") {
				$("#selectedCustomer").text(customername);
				$('.customerDropdown').hide();
			}
			self.headerListener.showHideMainMenu($("#selectedCustomer").text());
			
			var customerId = self.getCustomer();
			var data = {};
			data.customerId = customerId; 
			self.headerListener.performAction(self.headerListener.getActionHeader("getCustomerTheme", data), function(response) {
				if (response.data.theme !== null) {
					self.headerListener.changeCustomerTitle(response.data.theme);
				}
			});
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			
			if (userInfo.authType == "LOCAL") {
				$("#changepassword").show();
			}
		},
		
		/***
         * Called once to register all the events 
         *
         * @facetsListener: HeaderListener methods getting registered
         */
        registerEvents : function (headerListener) {
            var self = this;
			
			if(self.onLogoutEvent === null){
				self.onLogoutEvent = new signals.Signal();
			}	
			if(self.onTabChangeEvent === null){
				self.onTabChangeEvent = new signals.Signal();
			}	
			if(self.onSelectCustomerEvent === null){
				self.onSelectCustomerEvent = new signals.Signal();
			}
			if (self.onAboutClickEvent === null) {
				self.onAboutClickEvent =new signals.Signal();
			}
			if (self.onUpgradeEvent === null) {
				self.onUpgradeEvent =new signals.Signal();
			}
			self.onLogoutEvent.add(headerListener.doLogout, headerListener);
			self.onTabChangeEvent.add(headerListener.loadTab, headerListener);
			self.onSelectCustomerEvent.add(headerListener.selectCoustomer, headerListener);
			self.onAboutClickEvent.add(headerListener.aboutPhresco, headerListener);
			self.onUpgradeEvent.add(headerListener.upgradePhresco, headerListener);
        },
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			var self = this;
			$(".scrollable, .cus_themes").css("height","200px");			
			//Logout event
			$('#logout').click(function(){
				self.doLogout();
			});
			
			$(".header_left ul li").click(function(){
				$(".header_left ul li a").removeClass('nav_active');
				$("#editprojectTab li a").removeClass("act");
				$(this).children().addClass('nav_active');
				self.headerListener.currentTab = $(this).text();
				self.onTabChangeEvent.dispatch();
				$("#aboutPopup").hide();
			});
			
			$(".header_right ul li").click(function(){
				var currentTab = $(this).text();
				if ("About" === currentTab) {
					self.onAboutClickEvent.dispatch(this);
				}
			});

			$("#upgrade").click(function() {
				self.onUpgradeEvent.dispatch();
			});

			$('a[name=customers]').click(function(){
				self.onSelectCustomerEvent.dispatch($(this).text(), $(this).attr("id"));
			});
			
			$("#changepassword").click(function() {
				self.openccdashboardsettings(this,'change_password');
				$("#old_password").val('');
				$("#new_password").val('');
				$("#new_password_reenter").val('');
				
			});
			
			$("#confirm_change_password").click(function() {
				var oldpass = $("#old_password");
				var newpass = $("#new_password");
				var confirmnewpass = $("#new_password_reenter");
				if(oldpass.val() === '') {
					oldpass.addClass('errormessage');
					oldpass.focus();
					oldpass.attr('placeholder','Enter Old Password');
					oldpass.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(newpass.val() === '') {
					newpass.addClass('errormessage');
					newpass.focus();
					newpass.attr('placeholder','Enter New Password');
					newpass.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(confirmnewpass.val() === '') {
					confirmnewpass.addClass('errormessage');
					confirmnewpass.focus();
					confirmnewpass.attr('placeholder','Enter Confirm New Password');
					confirmnewpass.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(newpass.val() !== confirmnewpass.val()) {
					confirmnewpass.val('');
					confirmnewpass.addClass('errormessage');
					confirmnewpass.focus();
					confirmnewpass.attr('placeholder','Re-enter password');
					confirmnewpass.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else {
					self.headerListener.performAction(self.headerListener.getActionHeader("changepassword"), function(response) {
						setTimeout(function(){
							self.onLogoutEvent.dispatch();
						},3000);
					});
				}	
			});
		}
	});

	return Clazz.com.commonComponents.modules.header.js.Header;
});