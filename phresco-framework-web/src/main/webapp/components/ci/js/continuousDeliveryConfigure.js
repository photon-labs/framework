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
		onConfigureEvent : null,
		onLoadDynamicPageEvent : null,
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

			 // Trigger registered events
			 self.onLoadEnvironmentEvent.add(ciListener.loadEnvironmentEvent, ciListener);
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
			console.log("Pre render .... ");
			self.getAction(self.ciRequestBody, 'getEnvironemntsByProjId', '', function(response) {
				console.log("get environement response =>  " + JSON.stringify(response.data));
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
			console.log("Post render .... ");

			// List job templates by environment from all applications
			self.onLoadEnvironmentEvent.dispatch(function(params) {
					self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironemnt', params, function(response) {
						console.log("Populates job templates by environment ");
					});
			});
		},
		
		getAction : function(ciRequestBody, action, params, callback) {
			var self = this;
			console.log("Get action .... ");
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
				$(".dyn_popup").hide();
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
			    }).disableSelection();
			 }); 

	  		$(function () {
				$(".tooltiptop").tooltip();
			});
		
			$(".code_content .scrollContent").mCustomScrollbar({
				autoHideScrollbar:true,
				theme:"light-thin",
				advanced:{
						updateOnContentResize: true
				}
			});
		
			$("#sortable1 li.ui-state-default a").hide();
			
			$("a[name=cont_delivery], a[name=code_build]").click(function() {
   				commonVariables.openccmini(this, $(this).attr("name"));
   			});
		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryConfigure;
});