define(["croneExpression/listener/croneExpressionListener"], function() {
	Clazz.createPackage("com.components.croneExpression.js");

	Clazz.com.components.croneExpression.js.croneExpression = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/croneExpression/template/croneExpression.tmp",
		name : commonVariables.croneExpression,
		croneExpressionlistener : null,
		configRequestBody : {},
		templateData : {},
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.croneExpressionlistener = new Clazz.com.components.croneExpression.js.listener.croneExpressionListener(globalConfig);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
		},

		postRender : function(element) {
	
		},
		
		bindUI : function() {
			var self = this;
			
		}
		
	 });
	return Clazz.com.components.croneExpression.js.croneExpression;
});	
	
		