define(["seo/listener/seoListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.seo.js");

	Clazz.com.components.seo.js.seo = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/seo/template/seoTest.tmp",
		configUrl: "components/seo/config/config.json",
		name : commonVariables.seo,
		seoListener : null,
		testsuiteResult : null,
		testResultListener : null,
		onDynamicPageEvent : null,
		onRunseoEvent : null,
		
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			commonVariables.testType = commonVariables.manual;
		
			if (self.seoListener === null ) {
				self.seoListener = new Clazz.com.components.seo.js.listener.seoListener();
				self.registerEvents(self.seoListener);
			}

			
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
		},

		registerEvents : function(seoListener) {
			var self=this;
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.seoListener.getDynamicParams, self.seoListener);

			if (self.onRunseoEvent === null) {
				self.onRunseoEvent = new signals.Signal();
			}
			self.onRunseoEvent.add(self.seoListener.runSeoTest, self.seoListener);			
		},
		
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(needAnimation) {
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, needAnimation);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			commonVariables.navListener.getMyObj(commonVariables.testsuiteResult, function(retVal) {
				self.testsuiteResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
			self.seoListener.createUploader();
			
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var data = {};
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			data.userPermissions = userPermissions;
			renderFunction(data, whereToRender);
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();
			self.windowResize();

			//show upload template popup
			$("#show_uploadTemplate_popup").unbind("click");			
			$('#show_uploadTemplate_popup').click(function() {
				var btnObj = this;
				self.onDynamicPageEvent.dispatch('test' , btnObj, function() {
					commonVariables.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
				self.copyLog();
			});				
			

			$("#seoTest").unbind('click');
			$("#seoTest").click(function(){
				self.onRunseoEvent.dispatch(function(response){
					console.info('response = ' , response);
					commonVariables.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
			});


			
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.seo.js.seo;
});