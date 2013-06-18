define(["footer/listener/footerListener"] , function(template) {

	Clazz.createPackage("com.commonComponents.modules.footer");

	Clazz.com.commonComponents.modules.footer.js.Footer = Clazz.extend(Clazz.WidgetWithTemplate, {
		footerListener : null,
		
		// template URL, used to indicate where to get the template
		templateUrl:  commonVariables.contexturl + "js/commonComponents/modules/footer/template/footer.tmp",
		configUrl: "js/commonComponents/modules/footer/config/config.json",
		name : "footer",
		
		initialize : function(globalConfig){
			var self = this;
		},
		
		loadPage :function(){
			if(this.footerListener === null){
				this.footerListener = new Clazz.com.commonComponents.modules.footer.js.listener.FooterListener();
			}	
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

	return Clazz.com.commonComponents.modules.footer.js.Footer;
});