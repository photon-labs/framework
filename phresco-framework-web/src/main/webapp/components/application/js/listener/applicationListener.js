define(["framework/widget", "application/api/applicationAPI", "features/features"], function() {

	Clazz.createPackage("com.components.application.js.listener");

	Clazz.com.components.application.js.listener.ApplicationListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		applicationAPI : null,
		featureContent : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
				this.applicationAPI = new Clazz.com.components.application.js.api.ApplicationAPI();
				this.featureContent = new Clazz.com.components.features.js.Features();
		},
		
		onFeature : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.featureContent, true);
		}
		
	});

	return Clazz.com.components.application.js.listener.ApplicationListener;
});