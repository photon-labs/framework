define(["framework/widget", "common/loading", "header/api/headerAPI"], function() {

	Clazz.createPackage("com.commonComponents.modules.header.js.listener");

	Clazz.com.commonComponents.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			 this.headerAPI = new Clazz.com.commonComponents.modules.header.js.api.HeaderAPI;
		},
	});

	return Clazz.com.commonComponents.modules.header.js.listener.HeaderListener;
});