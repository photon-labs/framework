define(["framework/widgetWithTemplate", "footer/listener/footerListener"] , function(template) {

	Clazz.createPackage("com.common_components.modules.footer");

	Clazz.com.common_components.modules.footer.js.Footer = Clazz.extend(Clazz.WidgetWithTemplate, {
		headerEvent : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: "../js/common_components/modules/footer/template/footer.tmp",
		configUrl: "../../js/common_components/modules/footer/config/config.json",
		name : "footer",
		localConfig: null,
		globalConfig: null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
		},
		
		loadPage :function(){
			var footerListener = new Clazz.com.common_components.modules.footer.js.listener.FooterListener();
		},
		
		/***
		 * 
		 */
		postRender : function(element) {
				
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			
		}
	});

	return Clazz.com.common_components.modules.footer.js.Footer;
});