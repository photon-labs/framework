define(["framework/widgetWithTemplate", "header/listener/headerListener"] , function(template) {

	Clazz.createPackage("com.commonComponents.modules.header");

	Clazz.com.commonComponents.modules.header.js.Header = Clazz.extend(Clazz.WidgetWithTemplate, {
		headerEvent : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/js/commonComponents/modules/header/template/header.tmp",
		configUrl: "../../js/commonComponents/modules/header/config/config.json",
		name : "header",
		//Events, to fire a function
		onButtonClick: null,
		onProjectlistEvent: null,
		onSelectCustomerEvent: null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
			self.headerListener = new Clazz.com.commonComponents.modules.header.js.listener.HeaderListener(); 
			self.registerEvents(self.headerListener);
		},
		
		loadPage :function(){
			var self = this;
		},
		
		/***
		 * 
		 */
		postRender : function(element) {
		},
		
		/***
         * Called once to register all the events 
         *
         * @facetsListener: HeaderListener methods getting registered
         */
        registerEvents : function (headerListener) {
            var self = this;
			self.onLogoutEvent = new signals.Signal();
			self.onTabChangeEvent = new signals.Signal();
			self.onSelectCustomerEvent = new signals.Signal();
			
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

				//if(("#" + $(this).text()) != location.hash){
					self.headerListener.currentTab = $(this).text();
					self.onTabChangeEvent.dispatch();
				//}
			});
			
			$('a[name=customers]').click(function(){
				self.onSelectCustomerEvent.dispatch($(this).text());
			});
		}
	});

	return Clazz.com.commonComponents.modules.header.js.Header;
});