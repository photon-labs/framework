define(["framework/widget", "common/loading", "header/api/headerAPI",], function() {

	Clazz.createPackage("com.common_components.modules.header.js.listener");

	Clazz.com.common_components.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			 this.headerAPI = new Clazz.com.common_components.modules.header.js.api.HeaderAPI;
		},
		onButtonClick : function(callback) {
			var self = this;
			try {
				
			} catch(exception) {
				callback({ "status" : exception});
			}
		}
	});

	return Clazz.com.common_components.modules.header.js.listener.HeaderListener;
});