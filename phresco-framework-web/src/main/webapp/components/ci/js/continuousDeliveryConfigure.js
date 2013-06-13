define(["framework/widgetWithTemplate", "ci/listener/ciListener"], function() {
	Clazz.createPackage("com.components.ci.js");

	Clazz.com.components.ci.js.ContinuousDeliveryConfigure = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/ci/template/continuousDeliveryConfigure.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.continuousDeliveryConfigure,
		ciListener: null,
		ciRequestBody : {},
		templateData : {},
		dynamicpage : null,
		onLoadEnvironmentEvent : null,
		onLoadDynamicPageEvent : null,
		onConfigureJobEvent : null,	// saving job template
		onConfigureJobPopupEvent : null, // show configure popup from gear icon
		onSaveEvent : null,
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if (self.dynamicpage === null) {
				commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal) {
					self.dynamicpage = retVal;
				});
			}

			if (self.ciListener === null) {
				self.ciListener = new Clazz.com.components.ci.js.listener.CIListener(globalConfig);
			}

			self.registerEvents(self.ciListener);
		},
		
		
		registerEvents : function (ciListener) {
			var self = this;
			// Register events
			 if (self.onLoadEnvironmentEvent === null) {
			 	self.onLoadEnvironmentEvent = new signals.Signal();
			 }

			 if (self.onConfigureJobPopupEvent === null) {
				self.onConfigureJobPopupEvent = new signals.Signal();
			 }

			 if (self.onConfigureJobEvent === null) {
			 	self.onConfigureJobEvent = new signals.Signal();
			 }
				
			 // Trigger registered events
			 self.onLoadEnvironmentEvent.add(ciListener.loadEnvironmentEvent, ciListener);
			 self.onConfigureJobPopupEvent.add(self.ciListener.showConfigureJob, self.ciListener);
			 self.onConfigureJobEvent.add(self.ciListener.configureJob, self.ciListener);

			 // Handle bars
			Handlebars.registerHelper('environment', function(data, flag) {
				var returnVal = "";
				if (data != undefined) {
					$.each(data, function(key, value) {
						returnVal +=  '<option value="'+ value +'">'+ value +'</option>';
					});
				}
				return returnVal;
			});
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, true);
		},
		
		preRender: function(whereToRender, renderFunction) {
			var self = this;
			self.getAction(self.ciRequestBody, 'getEnvironemntsByProjId', '', function(response) {
			 	self.templateData.environments = response.data;
				renderFunction(self.templateData, whereToRender);
			});		
		},

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;

			// List job templates by environment from all applications
			self.onLoadEnvironmentEvent.dispatch(function(params) {
					self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironment', params, function(response) {
						self.ciListener.constructJobTemplateViewByEnvironment(response);
					});
			});
		},
		
		getAction : function(ciRequestBody, action, params, callback) {
			var self = this;
			self.ciListener.getHeaderResponse(self.ciListener.getRequestHeader(self.ciRequestBody, action, params), function(response) {
				callback(response);
			}); 
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;
   			$(".dyn_popup").hide();
	  		$(window).resize(function() {
				//$(".dyn_popup").hide();
	  		});
	  		$(".first_list").find("span").hide();

			$(function() {
			    $( "#sortable1, #sortable2" ).sortable({
			      connectWith: ".connectedSortable",
				  items: "> li",
				  start: function( event, ui ) {
					  $("#sortable1 li.ui-state-default a").hide();
					  $("#sortable2 li.ui-state-default a").show();	
					  $(".dyn_popup").hide();
					  },
				  stop: function( event, ui ) {
					  $("#sortable2 li.ui-state-default a").show();
					  $("#sortable1 li.ui-state-default a").hide();	
					  $(".dyn_popup").hide();
					  }	  
			    })
			 }); 

	  		$(function () {
				$(".tooltiptop").tooltip();
			});
		
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced: {
					updateOnContentResize: true
				}
			});
		
			// By Default gear icon should not be displayed
			$("#sortable1 li.ui-state-default a").hide();
			
   			$('#sortable2').on('click', 'a[name=jobConfigurePopup]', function() {
   				// Show popup as well as dynamic popup
   				self.onConfigureJobPopupEvent.dispatch(this);
   			});

   			// on change of environemnt change function
   			$("[name=environments]").change(function() {
   				//Clear existing job templates of a environemnt, when the environment is changed
   				$("#sortable2").empty();
   				// List job templates by environment from all applications
				self.onLoadEnvironmentEvent.dispatch(function(params) {
						self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironment', params, function(response) {
							self.ciListener.constructJobTemplateViewByEnvironment(response);
						});
				});
   			});

   			// on clicking configure button from job configuration
   			$("[name=configure]").click(function() {
   				// we can get the this element over here
   				self.onConfigureJobEvent.dispatch(this);
   			});
		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryConfigure;
});