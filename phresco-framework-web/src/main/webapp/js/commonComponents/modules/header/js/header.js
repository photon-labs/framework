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
				$('#bannerlogo').attr("src", "themes/default/images/helios/helios_logo.png");
			}
			var customername = self.headerListener.headerAPI.localVal.getSession('customername');
			if (customername !== null && customername !== undefined && customername !== "") {
				$("#selectedCustomer").text(customername);
				$('.customerDropdown').hide();
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
			self.onLogoutEvent.add(headerListener.doLogout, headerListener);
			self.onTabChangeEvent.add(headerListener.loadTab, headerListener);
			self.onSelectCustomerEvent.add(headerListener.selectCoustomer, headerListener);
        },
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			var self = this;
			
			//Logout event
			$('#logout').click(function(){
				self.onLogoutEvent.dispatch();
			});
			
			$(".header_left ul li").click(function(){
				$(".header_left ul li a").removeClass('nav_active');
				$(this).children().addClass('nav_active');
				self.headerListener.currentTab = $(this).text();
				self.onTabChangeEvent.dispatch();
			});
			
			$('a[name=customers]').click(function(){
				self.onSelectCustomerEvent.dispatch($(this).text());
			});
			
			this.customScroll($(".scrolldiv"));
		}
	});

	return Clazz.com.commonComponents.modules.header.js.Header;
});