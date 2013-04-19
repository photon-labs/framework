define(["framework/widget", "features/api/featuresAPI", "application/application"], function() {

	Clazz.createPackage("com.components.features.js.listener");

	Clazz.com.components.features.js.listener.FeaturesListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		featuresAPI : null,
		appinfoContent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			self = this;	
			self.featuresAPI = new Clazz.com.components.features.js.api.FeaturesAPI();
		},
		
		onPrevious : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.appinfoContent = new Clazz.com.components.application.js.Application();
			Clazz.navigationController.push(self.appinfoContent, true);
		}
		
	});

	return Clazz.com.components.features.js.listener.FeaturesListener;
});