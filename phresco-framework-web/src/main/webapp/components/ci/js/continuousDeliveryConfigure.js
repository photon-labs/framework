define(["framework/widgetWithTemplate", "ci/listener/ciListener", "lib/jquery-tojson-1.0"], function() {
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
		onConfigureJobEvent : null,	
		onConfigureJobPopupEvent : null, 
		onSaveEvent : null, 
		editContinuousViewTable : null,  
		name : null,
		downStreamCriteria : null,
		lastChild : null,
		sortableOneReceive : null,
		sortableTwoReceive : null,
		sortableOneChange : null,
		sortableTwoChange : null,
		sortableTwoHold : null,

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

			 if (self.onSaveEvent === null) {
			 	self.onSaveEvent = new signals.Signal();
			 }

			 if (self.editContinuousViewTable === null) {
			 	self.editContinuousViewTable = new signals.Signal();
			 }
			 
			 if (self.downStreamCriteria === null) {
				 	self.downStreamCriteria = new signals.Signal();
			 }
			 
			 if (self.lastChild === null) {
				 	self.lastChild = new signals.Signal();
			 }
			 
			 if (self.sortableOneReceive === null) {
				 	self.sortableOneReceive = new signals.Signal();
			 }
			 
			 if (self.sortableTwoReceive === null) {
				 	self.sortableTwoReceive = new signals.Signal();
			 }
			 
			 if (self.sortableOneChange === null) {
				 	self.sortableOneChange = new signals.Signal();
			 }
			 
			 if (self.sortableTwoChange === null) {
				 	self.sortableTwoChange = new signals.Signal();
			 }
				
			 if (self.sortableTwoHold === null) {
				 	self.sortableTwoHold = new signals.Signal();
			 }
			 
			 // Trigger registered events
			 self.onLoadEnvironmentEvent.add(ciListener.loadEnvironmentEvent, ciListener);
			 self.onConfigureJobPopupEvent.add(self.ciListener.showConfigureJob, self.ciListener);
			 self.onConfigureJobEvent.add(self.ciListener.configureJob, self.ciListener);
			 self.onSaveEvent.add(self.ciListener.saveContinuousDelivery, self.ciListener);
		  	 self.editContinuousViewTable.add(self.ciListener.editContinuousViewTable, self.ciListener);
		  	 self.downStreamCriteria.add(self.ciListener.downStreamCriteria, self.ciListener);
		  	 self.lastChild.add(self.ciListener.lastChild, self.ciListener);
		  	 self.sortableOneReceive.add(self.ciListener.sortableOneReceive, self.ciListener);
		  	 self.sortableTwoReceive.add(self.ciListener.sortableTwoReceive, self.ciListener);
		  	 self.sortableOneChange.add(self.ciListener.sortableOneChange, self.ciListener);
		  	 self.sortableTwoChange.add(self.ciListener.sortableTwoChange, self.ciListener);
		  	 self.sortableTwoHold.add(self.ciListener.sortableTwohold, self.ciListener);

			 // Handle bars
			Handlebars.registerHelper('environment', function(data, flag) {
				var returnVal = "";
				if (data !== undefined) {
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
			Clazz.navigationController.push(this, commonVariables.animation);
		},

		loadPageTest :function(){
			Clazz.navigationController.push(this);
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
				if(!self.isBlank(self.envName)) {
					params.envName = self.envName;
					self.envName = "";
				}
				self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironment', params, function(response) {
					self.ciListener.constructJobTemplateViewByEnvironment(response, function() {
						if(self.name !== null) {
							self.ciListener.getHeaderResponse(self.ciListener.getRequestHeader(self.ciRequestBody, 'editContinuousView', self.name), function (response) {
								self.editContinuousViewTable.dispatch(response);
							});
						} 
					});
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
				$(".dyn_popup").hide();
	  		});
	  		$(".first_list").find("span").hide();

	  		$("input[name=continuousDeliveryName]").on("keypress keyup paste", function(e) {
				this.value = this.value.replace(/[^-_a-zA-Z0-9]/g, '');
			});
	  		
	  		$("input[name=jobName]").on("keypress keyup paste", function(e) {
				this.value = this.value.replace(/[^-_a-zA-Z0-9 ]/g, '');
			});
	  		
	  		$( "#jobConfigure" ).delegate( "#Publish", "click", function() {
	  			if ($("#Publish").is(':checked')) {
	  				$("#Publish").val("true");
	  			} else {
	  				$("#Publish").val("false");
	  			}
	  				
	  			
			});
	  		
	  		$( "#jobConfigure" ).delegate( "#archive", "click", function() {
  				if ($("#archive").is(':checked')) {
  					$("#archive").val("true");
	  			} else {
	  				$("#archive").val("false");
	  			}
			});
	  		
	  		
			$(function() {
				// sortable1 functionality
				$( "#sortable1" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
						$('#header').css('z-index','7');
						$('.content_title').css('z-index','6');
						$('.qual_unit').css('z-index','6');
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
						$('#header').css('z-index','7');
						$('.content_title').css('z-index','6');
						$('.qual_unit').css('z-index','6');
						self.downStreamCriteria.dispatch();
						self.lastChild.dispatch();
					},

					receive: function( event, ui ) {
						self.sortableOneReceive.dispatch(ui);
					},

					change: function( event, ui ) {	
						self.sortableOneChange.dispatch(ui);
					}
				});

				// sortable2 functionality
			    $( "#sortable2" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
					},

					change: function( event, ui ) {
						self.sortableTwoChange.dispatch(ui);
					},

					receive: function( event, ui ) {
						self.sortableTwoReceive.dispatch(ui);
					},
					
					update: function() {
						self.sortableTwoHold.dispatch();
					}
				}); 
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
				$('#header').css('z-index','0');
				$('.content_title').css('z-index','0');
				$('.qual_unit').css('z-index','0');
   				var envName = $("[name=environments]").val();
   				$(this).attr("envName",envName);
   				self.onConfigureJobPopupEvent.dispatch(this);
   			});

   			// on change of environemnt change function
   			$("[name=environments]").change(function() {
   				$("#sortable2").empty();
				self.onLoadEnvironmentEvent.dispatch(function(params) {
						self.getAction(self.ciRequestBody, 'getJobTemplatesByEnvironment', params, function(response) {
							self.ciListener.constructJobTemplateViewByEnvironment(response, function() {});
						});
				});
   			});

   			// on clicking configure button from job configuration
   			$("[name=configure]").click(function() {
   				self.onConfigureJobEvent.dispatch(this);
   			});
   			
   			$("input[value=Cancel]").click(function() {		
   				self.ciListener.loadContinuousDeliveryView();
   			});
   			
   			// On save event of continuous delivery
			$("input[type=submit]").click(function() {				
				$(".dyn_popup").hide();

				if ( $(this).val() === 'Add') {
   					self.onSaveEvent.dispatch(this, function(addJobsParams) {
	   					self.ciRequestBody = addJobsParams;
	   					commonVariables.showloading = true;
	   					self.getAction(self.ciRequestBody, 'saveContinuousDelivery', '', function(response) {	   						
							self.ciListener.loadContinuousDeliveryView(response);
	   					});
	   				});
   				}
   			
   				if ( $(this).val() === 'Update') {
	   				self.onSaveEvent.dispatch(this, function(editJobParams) {
	   					self.ciRequestBody = editJobParams;
	   					commonVariables.showloading = true;
	   					self.getAction(self.ciRequestBody, 'updateContinuousDelivery', '', function(response) {
	   						if(response.responseCode === "PHR810005") {
	   							commonVariables.api.showError(response.responseCode ,"error", true, true, true);
	   						} else if(response.responseCode === "PHR810031") {
	   							var msg = response.data + commonVariables.api.error[response.responseCode];
	   							commonVariables.api.showError(msg ,"error", true, true, true);
	   						} else {
	   							self.ciListener.loadContinuousDeliveryView(response);
	   						}
	   						
	   					});
					});
	   			}

   			});
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			//this.customScroll($(".scroll_cont"));
		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryConfigure;
});