define(["zapmenu/listener/zapmenuListener", "testResult/listener/testResultListener"], function() {
	Clazz.createPackage("com.components.zapmenu.js");

	Clazz.com.components.zapmenu.js.zapmenu = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/zapmenu/template/zapmenu.tmp",
		configUrl: "components/zapmenu/config/config.json",
		name : commonVariables.zapmenu,
		zapmenuListener : null,
		onDynamicPageEvent : null,
		onZapTestEvent : null,
		validation : null,
		testResultListener : null,
		onZapStartStopEvent : null,
		onZapStatusEvent : null,
		


		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(){
			var self = this;
			if (self.zapmenuListener === null) {
				self.zapmenuListener = new Clazz.com.components.zapmenu.js.listener.zapmenuListener();
			}
			self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
						
			self.registerEvents();
		},
		
		registerEvents : function () {
			var self=this;
						
			if (self.onDynamicPageEvent === null) {
				self.onDynamicPageEvent = new signals.Signal();
			}
			self.onDynamicPageEvent.add(self.zapmenuListener.getDynamicParams, self.zapmenuListener);
			
			if (self.onZapTestEvent === null) {
				self.onZapTestEvent = new signals.Signal();
			}
			self.onZapTestEvent.add(self.zapmenuListener.runZapTest, self.unitTestListener);
									
			if (self.onZapStartStopEvent === null) {
				self.onZapStartStopEvent = new signals.Signal();
			}
			self.onZapStartStopEvent.add(self.zapmenuListener.zapStartStop, self.unitTestListener);
															
			if (self.onZapStatusEvent === null) {
				self.onZapStatusEvent = new signals.Signal();
			}
			self.onZapStatusEvent.add(self.zapmenuListener.getZapStatus, self.unitTestListener);
						
			if (self.validation === null) {
				self.validation = new signals.Signal();
			}
			//self.validation.add(self.testResultListener.mandatoryValidation, self.testResultListener);			
			
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(needAnimation) {
			Clazz.navigationController.mainContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, needAnimation);
		},
		
 		preRender: function(whereToRender, renderFunction) {
			var self = this;
			var publicationInfo = {};
			 setTimeout(function() {
				self.zapmenuListener.getData(self.zapmenuListener.getRequestHeader('' , "parameter"), function(response) {
					commonVariables.api.localVal.setJson('readConfigData', response.data);
					publicationInfo.publicationInfo = response;
					renderFunction(publicationInfo, whereToRender);
				});
			}, 200); 
		}, 
		
		postRender : function(element) {
			var self = this;
			self.onZapStatusEvent.dispatch(self.zapmenuListener , "zap-status");
			commonVariables.navListener.getMyObj(commonVariables.testsuiteResult, function(retVal) {
				self.testsuiteResult = retVal;
				Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
				Clazz.navigationController.push(self.testsuiteResult, false);
			});
		},

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */

					
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
			$(".tooltiptop").tooltip();

			$("#unitTestBtn").unbind("click");
			$("#unitTestBtn").click(function() {
				var btnObj = this;
				//self.checkForLock("unit", '', '', function(response){
					//if (response.status === "success" && response.responseCode === "PHR10C00002") {
					if($(this).attr("class") === "btn btn_style"){
						self.onDynamicPageEvent.dispatch('test' , btnObj, function() {
							commonVariables.logContent = $('#testConsole').html();
							$('#testResult').empty();
							Clazz.navigationController.jQueryContainer = '#testResult';
							Clazz.navigationController.push(self.testsuiteResult, false);
						});
					}	
					//} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
					//	commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					//}	
				//});	
				self.copyLog();
			});
		
			
			//To run the zap test
			$("#zapAttack").unbind("click");
			$("#zapAttack").click(function() {
				self.onZapTestEvent.dispatch(function() {
					commonVariables.logContent = $('#testConsole').html();
					$('#testResult').empty();
					Clazz.navigationController.jQueryContainer = '#testResult';
					Clazz.navigationController.push(self.testsuiteResult, false);
				});
				$("#unit_popup").toggle();
			});	

			
			//To start the zap
			$("#zapStart").unbind("click");
			$("#zapStart").click(function() {
				var btnObj = this;
				if($(this).attr("class") === "btn btn_style"){
					self.onDynamicPageEvent.dispatch('start',btnObj, function() {
						commonVariables.logContent = $('#testConsole').html();
						$('#testResult').empty();
						Clazz.navigationController.jQueryContainer = '#testResult';
						Clazz.navigationController.push(self.testsuiteResult, false);
					});
				}	
				self.copyLog();
			});
						
			//To stop the unit test
			$("#runZapStart").unbind("click");
			$("#runZapStart").click(function() {
				self.onZapStartStopEvent.dispatch("zapStart");
				$("#zapstart_popup").toggle();
			});
									
			//To stop the unit test
			$("#zapStop").unbind("click");
			$("#zapStop").click(function() {
				var btnObj = this;
				if($(this).attr("class") === "btn btn_style"){
					self.onZapStartStopEvent.dispatch("zapStop");
				}	
			});
	
		}
	});

	return Clazz.com.components.zapmenu.js.zapmenu;
});