define(["codequality/listener/codequalityListener"], function() {
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
		onProgressEvent : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			
			if(self.codequalityListener === null)
				self.codequalityListener = new Clazz.com.components.codequality.js.listener.CodequalityListener();

				if(self.dynamicpage === null){
					commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
					self.dynamicpage = retVal;
					self.dynamicPageListener = self.dynamicpage.dynamicPageListener;
					self.registerEvents();
				});
			}else{
				self.registerEvents();
			}
		},
		
		registerEvents : function() {
			var self = this;
			if(self.onProgressEvent === null)
				self.onProgressEvent = new signals.Signal();
				
			self.readLogEvent = new signals.Signal();
			self.readLogEvent.add(self.codequalityListener.codeValidate, self.codequalityListener);
			self.onProgressEvent.add(self.codequalityListener.onPrgoress, self.codequalityListener);
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.push(this);
		},

			/*preRender: function(whereToRender, renderFunction){
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
						console.info('response = ' ,response);
							$("#dynamicContent").html(response);
							self.multiselect();
							self.dynamicpage.showParameters();
							self.dynamicPageListener.controlEvent();
						});
						renderFunction(projectlist, whereToRender);
					}else{
						 renderFunction(projectlist, whereToRender); 
						$('#iframePart').html('');
						$('#iframePart').append(response.message);
					}
				});
			}, 200);	 
		}, */

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		 
		postRender : function(element) {
			var self = this; 
			var appDirName = self.codequalityListener.codequalityAPI.localVal.getSession('appDirName');
			var goal = "validate-code";
			commonVariables.goal = goal;
			
			setTimeout(function() {
				self.codequalityListener.getReportTypes(self.codequalityListener.getRequestHeader(self.appDirName , "reporttypes"), function(response) {
					var projectlist = {};
					projectlist.projectlist = response;	
					self.renderedData = response;
					//console.info('response = ' , JSON.stringify(response));
					self.codequalityListener.constructHtml(self.renderedData);
					if(response.message == "Dependency returned successfully"){
						//renderFunction(projectlist, whereToRender);
					}else{
						// renderFunction(projectlist, whereToRender); 
						$('#iframePart').html('');
						$('#iframePart').append(response.message);
					}
				});
			}, 200);				
			
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
				self.dynamicpage.getHtml(false, function(response){
					$("#dynamicContent").html(response);
					self.multiselect();
					self.dynamicpage.showParameters();
					self.dynamicPageListener.controlEvent();
				});			
				self.opencc(this,'code_popup');
			});
			
			$("#validate").click(function() {
				self.readLogEvent.dispatch();
				$(".dyn_popup").hide();
			});
			
			$("#codeValidateConsole").click(function() {
				self.onProgressEvent.dispatch(this);
			});
			
			$(window).resize(function() {
			
				$(".dyn_popup").hide();

				var height = $(this).height();
				var twowidth = window.innerWidth/1.6;
				var onewidth = window.innerWidth - (twowidth+70);
				
				$('.features_content_main').height(height - 230);
				$('.build_progress').height(height - 230);
				
				$('.build_info').css("width",twowidth);
				$('.build_progress').css("width",onewidth);
				$('.build_close').css("right",onewidth+10);
				
			});
			
			$(window).resize();
		
			$("#content_div").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin"
			});			
			
			$(".scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{ updateOnContentResize: true}
			});
						
		}
	});

	return Clazz.com.components.codequality.js.CodeQuality;
});