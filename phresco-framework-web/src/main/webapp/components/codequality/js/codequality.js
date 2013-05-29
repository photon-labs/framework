define(["framework/widgetWithTemplate", "codequality/listener/codequalityListener"], function() {
	Clazz.createPackage("com.components.codequality.js");

	Clazz.com.components.codequality.js.CodeQuality = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/codequality/template/codequality.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.codequality,
		codequalityListener: null,
		dynamicpage : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.dynamicpage = commonVariables.navListener.getMyObj(commonVariables.dynamicPage);
			self.codequalityListener = new Clazz.com.components.codequality.js.listener.CodequalityListener(globalConfig);
		},
		
		
		registerEvents : function () {
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this; 
			var appDirName = commonVariables.appDirName;
			var goal = "validate-code";
			self.dynamicpage.getHtml(appDirName, goal, function(response){
				$("#dynamicContent").html(response);
				//self.dynamicEvent();
			});
		},
		
		/* dynamicEvent : function() {
			var self = this; 
			var dependency = '';
			dependency = $("select[name='sonar']").find(':selected').attr('dependency');
				
		}, */

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			$("#codeAnalysis").click(function() {
				self.opencc(this,'code_popup');
			});
			
			
			
		}
	});

	return Clazz.com.components.codequality.js.CodeQuality;
});