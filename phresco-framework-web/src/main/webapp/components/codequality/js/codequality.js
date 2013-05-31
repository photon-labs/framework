define(["framework/widgetWithTemplate", "codequality/listener/codequalityListener"], function() {
	Clazz.createPackage("com.components.codequality.js");

	Clazz.com.components.codequality.js.CodeQuality = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/codequality/template/codequality.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.codequality,
		codequalityListener: null,
		codequalityAPI : null,
		dynamicpage : null,
		dynamicPageListener : null,
		renderedData : {},
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.dynamicpage = commonVariables.navListener.getMyObj(commonVariables.dynamicPage);
			self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
			self.codequalityListener = new Clazz.com.components.codequality.js.listener.CodequalityListener(globalConfig);
			self.codequalityAPI = new Clazz.com.components.codequality.js.api.CodeQualityAPI();			
			self.registerEvents(self.codequalityListener);
		},
		
		registerEvents : function (codequalityListener) {
			var self = this;
			self.readLogEvent = new signals.Signal();
			self.readLogEvent.add(codequalityListener.codeValidate, codequalityListener);
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.push(this);
		},
		
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			var appDirName = self.codequalityListener.codequalityAPI.localVal.getSession('appDirName');
			var goal = "validate-code";
			commonVariables.goal = goal;
			
			setTimeout(function() {
				self.codequalityListener.getReportTypes(self.codequalityListener.getRequestHeader(self.appDirName , "reporttypes"), function(response) {
					var projectlist = {};
					projectlist.projectlist = response;	
					self.renderedData = response;
					if(response.message == "Dependency returned successfully"){
						self.dynamicpage.getHtml(function(response){
							$("#dynamicContent").html(response);
							self.dynamicpage.showParameters();
							self.dynamicPageListener.controlEvent();
						});
						renderFunction(projectlist, whereToRender);
					}else{
						 renderFunction(projectlist, whereToRender); 
					}
				});
			}, 200);	
		}, 

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		 
		postRender : function(element) {
			var self = this; 
			self.codequalityListener.constructHtml(self.renderedData);

			},

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
			
			$("#validate").click(function() {
				self.readLogEvent.dispatch();
				$(".dyn_popup").hide();
			});
			
			
			
			
		}
	});

	return Clazz.com.components.codequality.js.CodeQuality;
});