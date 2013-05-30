define(["framework/widgetWithTemplate", "dynamicPage/listener/dynamicPageListener"], function() {
	Clazz.createPackage("com.components.dynamicPage.js");

	Clazz.com.components.dynamicPage.js.DynamicPage = Clazz.extend(Clazz.WidgetWithTemplate, {
		ondynamicPageEvent : null,
		dynamicPageListener : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/dynamicPage/template/dynamicPage.tmp",
		configUrl: "components/dynamicPage/config/config.json",
		name : commonVariables.dynamicPage,
		localConfig: null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			self.ondynamicPageEvent = new signals.Signal();
			self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
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
		
		
		getHtml : function(callback){
			var self = this;
			self.dynamicPageListener.getServiceContent(callback);
		},
		
		showParameters : function() {
			var self = this; 
			self.dynamicPageListener.showParameters();
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
		}
	});

	return Clazz.com.components.dynamicPage.js.DynamicPage;
});