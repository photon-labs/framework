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
				
			 // Trigger registered events
			 self.onLoadEnvironmentEvent.add(ciListener.loadEnvironmentEvent, ciListener);
			 self.onConfigureJobPopupEvent.add(self.ciListener.showConfigureJob, self.ciListener);
			 self.onConfigureJobEvent.add(self.ciListener.configureJob, self.ciListener);
			 self.onSaveEvent.add(self.ciListener.saveContinuousDelivery, self.ciListener);
		  	 self.editContinuousViewTable.add(self.ciListener.editContinuousViewTable, self.ciListener);
		  	 self.downStreamCriteria.add(self.ciListener.downStreamCriteria, self.ciListener);
		  	 self.lastChild.add(self.ciListener.lastChild, self.ciListener);


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
						// For gear icons alone
						$("#sortable2 li.ui-state-default a").show();
						$("#sortable1 li.ui-state-default a").hide();	
						$(".dyn_popup").hide();

						// Remove application name text
						var itemText = $(ui.item).find('span').text();
						var anchorElem = $(ui.item).find('a');
						var appName = $(anchorElem).attr("appname");						
						$(ui.item).find('span').text($(ui.item).find('span').text().replace(appName + " - ", ""));
						$( ".sorthead" ).each(function( index ) {
							if(appName === $(this).text()) {
								$(ui.item).insertAfter($(this));
							} 
						});
					},

					change: function( event, ui ) {		
						var itemText = $(ui.item).find('span').text();
						var anchorElem = $(ui.item).find('a');
						var templateJsonData = $(anchorElem).data("templateJson");
						var id = $(ui.item).parent("ul").attr("id");
						if(id === "sortable2") {
							var nextItem = $(ui.item).next();
							var nextAnchorElem = $(ui.item).next().find('a');
							var nextAnchorElem = $(ui.item).next().find('a');
							var downTemplateJsonData = $(nextAnchorElem).data("templateJson");
							
							var prevItem = $(ui.item).prev();
							var upTemplateJsonData;
							if(prevItem !== null && prevItem!== undefined) {
								var prevAnchorElem = $(ui.item).prev().find('a');
								var prevAnchorElem = $(ui.item).prev().find('a');
								upTemplateJsonData = $(prevAnchorElem).data("templateJson");
							}
							if (upTemplateJsonData === null) {
								if(downTemplateJsonData !== undefined && downTemplateJsonData!== null && !downTemplateJsonData.enableRepo) {
									$(".msgdisplay").removeClass("success").addClass("error");
									$(".error").text("DownStream "+downTemplateJsonData.name+" job Doesn't have the Repo!");
									$(".error").show();
									$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
									setTimeout(function() {
										$(".error").hide();
									},2500);
									$(ui.sender).sortable('cancel');
								}
							}
						}
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
						var itemText = $(ui.item).find('span').text();
						
						var anchorElem = $(ui.item).find('a');
						var templateJsonData = $(anchorElem).data("templateJson");
						var appName = $(anchorElem).attr("appname");
						var sortable2Len = $('#sortable2 > li').length;
						// Initial validation
						if (sortable2Len === 1 && !templateJsonData.enableRepo) {
							$(ui.sender).sortable('cancel');
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").text(templateJsonData.name + " job Doesn't have the Repo!");
							$(".error").show();
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".error").hide();
							},2500);
						}
						
					},

					receive: function( event, ui ) {
						var tt = $("#sortable2 li.ui-state-default").length;
						if(tt === 2) {
							$("#sortable2 li.ui-state-default").each(function( index ) {
								$(ui.item).insertAfter($(this));
							});
						}
						// For gear icons alone
						$("#sortable2 li.ui-state-default a").show();
						$("#sortable1 li.ui-state-default a").hide();	

						// Append application name text
						var itemText = $(ui.item).find('span').text();
						var anchorElem = $(ui.item).find('a');
						var templateJsonData = $(anchorElem).data("templateJson");
						var appName = $(anchorElem).attr("appname");

						// Application name construct
						$(ui.item).find('span').text(appName  + " - " + itemText);


						var sortable2Len = $('#sortable2 > li').length;

						// Second level validation
						// when the repo is not available check for parent project in sortable2
						if ( templateJsonData !== undefined && !templateJsonData.enableRepo) {
							var parentAppFound = false;

							// Previous elemets
							$(ui.item).prevAll('li').each(function(index) {
								var thisAnchorElem = $(this).find('a');
								var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
								var thisAppName = $(thisAnchorElem).attr("appname");

								if (thisAppName === appName && thisTemplateJsonData.enableRepo) {
									parentAppFound = true;
									return false;
								}
							});

							if (!parentAppFound) {
								$(ui.item).find('span').text(itemText);
								$(ui.sender).sortable('cancel');
								$(".msgdisplay").removeClass("success").addClass("error");
								$(".error").text("Parent object not found for "+templateJsonData.name+" template!");
								$(".error").show();
								$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
								setTimeout(function() {
									$(".error").hide();
								},2500);
							}

						}
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
	   					self.getAction(self.ciRequestBody, 'saveContinuousDelivery', '', function(response) {	   						
							self.ciListener.loadContinuousDeliveryView();
	   					});
	   				});
   				}
   			
   				if ( $(this).val() === 'Update') {
	   				self.onSaveEvent.dispatch(this, function(editJobParams) {
	   					self.ciRequestBody = editJobParams;
	   					self.getAction(self.ciRequestBody, 'updateContinuousDelivery', '', function(response) {
	   						self.ciListener.loadContinuousDeliveryView();
	   					});
					});
	   			}

   			});

		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryConfigure;
});