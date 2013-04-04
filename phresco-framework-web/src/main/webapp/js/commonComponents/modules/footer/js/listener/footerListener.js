define(["framework/widget", "footer/api/footerAPI", "common/loading"], function() {

	Clazz.createPackage("com.common_components.modules.footer.js.listener");

	Clazz.com.common_components.modules.footer.js.listener.FooterListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.footerAPI = new Clazz.com.components.footer.js.api.FooterAPI();
		}
	});

	return Clazz.com.common_components.modules.footer.js.listener.FooterListener;
});