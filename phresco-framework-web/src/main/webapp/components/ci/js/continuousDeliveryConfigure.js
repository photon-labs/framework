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
		onSaveEvent : null, // save continuous delivery
	
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
				
			 // Trigger registered events
			 self.onLoadEnvironmentEvent.add(ciListener.loadEnvironmentEvent, ciListener);
			 self.onConfigureJobPopupEvent.add(self.ciListener.showConfigureJob, self.ciListener);
			 self.onConfigureJobEvent.add(self.ciListener.configureJob, self.ciListener);
			 self.onSaveEvent.add(self.ciListener.saveContinuousDelivery, self.ciListener);

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
		 * This method automatically manipulates upstream and downstream as well as validation
		 *
		 */
		streamConfig : function(thisObj) {
	  		// third Construct upstream and downstream validations
	  		// all li elemnts of this
	  		console.log("upstream and sownstream construction ");
			$($(thisObj).find('li').get().reverse()).each(function() {
				console.log("elem span" + $(thisObj).find('span').text());
				    var anchorElem = $(thisObj).find('a');
				    var appName = $(anchorElem).attr("appname");
				    var templateJsonData = $(anchorElem).data("templateJson");
				    console.log("templateJsonData => " + JSON.stringify(templateJsonData));
				    var jobJsonData = $(anchorElem).data("jobJson");
				    console.log("jobJsonData => " + JSON.stringify(jobJsonData));
				    // upstream and downstream and clone workspace except last job
				    
				    var preli = $(thisObj).prev('li')
				    var preAnchorElem = $(preli).find('a');
				    var preTemplateJsonData = $(preAnchorElem).data("templateJson");
				    var preJobJsonData = $(preAnchorElem).data("jobJson");
				    console.log("preJobJsonData > " + preJobJsonData);
				    
				    var nextli = $(thisObj).next('li');
				    var nextAnchorElem = $(nextli).find('a');
				    var nextTemplateJsonData = $(nextAnchorElem).data("templateJson");
				    var nextJobJsonData = $(nextAnchorElem).data("jobJson");
				    console.log("nextJobJsonData > " + nextJobJsonData);


				    if (jobJsonData != undefined && jobJsonData != null) {
				        jobJsonData = {};
				    }

				    // Downstream
				    if (nextJobJsonData != undefined && nextJobJsonData != null) {
				        jobJsonData.downstreamApplication = nextJobJsonData.name;
				    }

				    // No use parent job
				    if (preJobJsonData != undefined && preJobJsonData != null) {
				        jobJsonData.upstreamApplication = preJobJsonData.name;
				    }

				    console.log("is previous App has repo check");
				    // Is parent app available for this app job
				    var parentAppFound = false;
				    var workspaceAppFound = false;
			  		$(thisObj).prevAll('li').each(function(index) {
			  			// Corresponding element access
					    var thisAnchorElem = $(thisObj).find('a');
					    console.log("elem span" + $(thisObj).find('span').text());
			  			var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
			  			var thisJobJsonData = $(thisAnchorElem).data("jobJson");
			  			var thisAppName = $(thisAnchorElem).attr("appname");

			  			if (thisAppName === appName && thisTemplateJsonData.enableRepo) {
			  				parentAppFound = true;
			  				console.log("Parent project found ");
			  				return false;
			  			}

			  			if (thisAppName === appName && !workspaceAppFound) {
			  				workspaceAppFound = true;
			  				console.log("Applications workspaceApp job found ");
			  				// Upstream app
			  				if (thisJobJsonData != undefined && thisJobJsonData != null) {
				        		jobJsonData.workspaceApp = thisJobJsonData.name;
				        		thisJobJsonData.clonetheWrokspace = true;
				        		$(thisAnchorElem).data("jobJson", thisJobJsonData);
				    		}
			  			}
					});
					
					if (!parentAppFound) {
						alert("Not able to find its parent source app ");
					}

				    // Store value in data
				    $(anchorElem).data("jobJson", jobJsonData);

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
				// sortable1 functionality
				$( "#sortable1" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
						console.log("start 1");
						// console.log("New position: " + ui.item.index());
						//console.log("[" + this.id + "] received [" + ui.item.html() + "] from [" + ui.sender + "]");
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
						console.log("ui1 stop => " , ui);
						//console.log("[" + this.id + "] received [" + ui.item.html() + "] from [" + ui.sender + "]");
					},

					receive: function( event, ui ) {
						console.log("receive 1 => " , ui);
						// For gear icons alone
						$("#sortable2 li.ui-state-default a").show();
						$("#sortable1 li.ui-state-default a").hide();	
						$(".dyn_popup").hide();

						// Remove application name text
						var itemText = $(ui.item).find('span').text();
						var anchorElem = $(ui.item).find('a');
						var appName = $(anchorElem).attr("appname");
						$(ui.item).find('span').text($(ui.item).find('span').text().replace(appName + " - ", ""));
						//console.log("[" + this.id + "] received [" + ui.item.html() + "] from [" + ui.sender + "]");
					},

					change: function( event, ui ) {
						console.log("change1 => " , ui);
						//console.log("[" + this.id + "] received [" + ui.item.html() + "] from [" + ui.sender + "]");
					}
				});

				// sortable2 functionality
			    $( "#sortable2" ).sortable({
					connectWith: ".connectedSortable",
					items: "> li",
					start: function( event, ui ) {
						$(".dyn_popup").hide();
						console.log("UI start 1");
					},

					stop: function( event, ui ) {
						$(".dyn_popup").hide();
						console.log("ui stop 2 => " , ui);
					},

					change: function( event, ui ) {
						console.log("change2 => " , ui);

						//console.log("[" + this.id + "] received [" + ui.item.html() + "] from [" + ui.sender + "]");
						var itemText = $(ui.item).find('span').text();
						var anchorElem = $(ui.item).find('a');
						var templateJsonData = $(anchorElem).data("templateJson");
						var appName = $(anchorElem).attr("appname");

						var sortable2Len = $('#sortable2 > li').length;
						console.log("sortable2Len > " + sortable2Len);
						// Initial validation
						if (sortable2Len === 1 && !templateJsonData.enableRepo) {
							console.log("atleast one job on right side should be with url 1 " + ui.sender);
							$(ui.sender).sortable('cancel');
						}
					},

					receive: function( event, ui ) {
						console.log("receive2 => " , ui);

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
						console.log("Template json data1 => " + JSON.stringify(templateJsonData));


						var sortable2Len = $('#sortable2 > li').length;
						console.log("calc " + $(this).find('li').size());

						console.log("New position: " + ui.item.index());

						// Second level validation
						// when the repo is not available check for parent project in sortable2
						if (!templateJsonData.enableRepo) {
							var parentAppFound = false;

							// Previous elemets
							console.log("is previous elemnt has repo check");
							$(ui.item).prevAll('li').each(function(index) {
								// Corresponding element access
								var thisAnchorElem = $(this).find('a');
								console.log("elem span" + $(this).find('span').text());
								var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
								var thisAppName = $(thisAnchorElem).attr("appname");

								if (thisAppName === appName && thisTemplateJsonData.enableRepo) {
									parentAppFound = true;
									console.log("Parent project found ");
									return false;
								} else {
									console.log("Parent object not found for this clonned workspace");
									$(ui.item).find('span').text(itemText);
									$(ui.sender).sortable('cancel');
								}
							});

							//Third  check
							//self.streamConfig(this); // jobJson is mandatory for this json job construct

							// Fourth on click save validation on save event 
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

   			// On save event of continuous delivery
   			$("input[type=submit][value=Add]").click(function() {
   				alert("Continuos delivery add ");
   				$(".dyn_popup").hide();
   				self.onSaveEvent.dispatch(this, function(response) {
   					console.log("continuous delivery obj " + JSON.stringify(response));
   				});
   			});

		}
	});

	return Clazz.com.components.ci.js.ContinuousDeliveryConfigure;
});