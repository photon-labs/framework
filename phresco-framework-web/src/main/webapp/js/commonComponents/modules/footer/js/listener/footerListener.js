define(["footer/api/footerAPI"], function() {

	Clazz.createPackage("com.commonComponents.modules.footer.js.listener");

	Clazz.com.commonComponents.modules.footer.js.listener.FooterListener = Clazz.extend(Clazz.Widget, {
		footerAPI : null,

		initialize : function(config){
			if(this.footerAPI === null){
				this.footerAPI = new Clazz.com.components.footer.js.api.FooterAPI();
			}	
		}
	});

	return Clazz.com.commonComponents.modules.footer.js.listener.FooterListener;
});