define(["framework/widgetWithTemplate", "build/listener/buildListener"], function() {
	Clazz.createPackage("com.components.build.js");

	Clazz.com.components.build.js.Build = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/build/template/build.tmp",
		configUrl: "../components/build/config/config.json",
		name : commonVariables.build,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},
		
		registerEvents : function(configurationlistener) {
			var self=this;
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			
			$("input[name=build_runagsource]").unbind("click");
			$("input[name=build_runagsource]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=build_genbuild]").unbind("click");
			$("input[name=build_genbuild]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=build_minifier]").unbind("click");
			$("input[name=build_minifier]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.build.js.Build;
});