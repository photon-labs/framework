define(["framework/widgetWithTemplate", "header/listener/headerListener"] , function(template) {

	Clazz.createPackage("com.common_components.modules.header");

	Clazz.com.common_components.modules.header.js.Header = Clazz.extend(Clazz.WidgetWithTemplate, {
		headerEvent : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: "../js/common_components/modules/header/template/header.tmp",
		configUrl: "../../js/common_components/modules/header/config/config.json",
		name : "header",
		localConfig: null,
		globalConfig: null,
		maincontent: "basepage\\:widget",
		contentContainer : commonVariables.contentPlaceholder,
		currentObj : null,
		//Events, to fire a function
		onButtonClick: null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
			self.headerListener = new Clazz.com.common_components.modules.header.js.listener.HeaderListener(); 
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
            self.onButtonClick = new signals.Signal();
            self.onButtonClick.add(headerListener.onButtonClick, headerListener);
        },
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
		}
	});

	return Clazz.com.common_components.modules.header.js.Header;
});