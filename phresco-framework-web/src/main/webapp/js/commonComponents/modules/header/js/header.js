define(["header/listener/headerListener"] , function(template) {

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
			if(self.headerListener.headerAPI.localVal.getSession('customerlogo') !== null &&
				self.headerListener.headerAPI.localVal.getSession('customerlogo') !== ""){
				$('#bannerlogo').attr("src", "data:image/png;base64," + self.headerListener.headerAPI.localVal.getSession('customerlogo'));
			} else {
				$('#bannerlogo').attr("src", "themes/default/images/Phresco/helios_logo.png");
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
			$(".scrollable, .dropdown-menu, .cust_sel, .cus_themes").css("height","200px");
			
			//Logout event
			$('#logout').click(function(){
				self.onLogoutEvent.dispatch();
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
		}
	});

	return Clazz.com.commonComponents.modules.header.js.Header;
});