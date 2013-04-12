define(["framework/widget", "footer/api/footerAPI"], function() {

	Clazz.createPackage("com.commonComponents.modules.footer.js.listener");

	Clazz.com.commonComponents.modules.footer.js.listener.FooterListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			this.footerAPI = new Clazz.com.components.footer.js.api.FooterAPI();
		}
	});

	return Clazz.com.commonComponents.modules.footer.js.listener.FooterListener;
});