define(["ci/listener/ciListener", "lib/jquery-tojson-1.0"], function() {
	Clazz.createPackage("com.components.ci.js");

	Clazz.com.components.ci.js.JobTemplates = Clazz.extend(Clazz.WidgetWithTemplate, {
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/ci/template/jobTemplates.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.jobTemplates,
		ciListener: null,
		dynamicpage : null,
		addEvent : null,
		listEvent : null,
		updateEvent : null,
		editEvent : null,
		deleteEvent : null,
		ciRequestBody : {},
		templateData : {},
		validateName : null,
		preOpenEvent : null,
		preEditEvent : null,
		changeOperationEvent : null,
		changeFeaturesEvent : null,
		removeDangerEvent : null,
	
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
			var self=this;
			// Register events
			if (self.addEvent === null) {
				self.addEvent = new signals.Signal();
			}

			if (self.listEvent === null) {
				self.listEvent = new signals.Signal();
			}

			if (self.editEvent === null) {
				self.editEvent = new signals.Signal();
			}
			if (self.updateEvent === null) {
				self.updateEvent = new signals.Signal();
			}

			if (self.deleteEvent === null) {
				self.deleteEvent = new signals.Signal();
			}
			
			if (self.validateName === null) {
				self.validateName = new signals.Signal();
			}

			if (self.preOpenEvent === null) {
				self.preOpenEvent = new signals.Signal();
			}

			if (self.preEditEvent === null) {
				self.preEditEvent = new signals.Signal();
			}

			if (self.changeOperationEvent === null) {
				self.changeOperationEvent = new signals.Signal();
			}
			
			if (self.changeFeaturesEvent === null) {
				self.changeFeaturesEvent = new signals.Signal();
			}

			if (self.removeDangerEvent === null) {
				self.removeDangerEvent = new signals.Signal();
			}

			// Trigger registered events
			self.addEvent.add(ciListener.addJobTemplate, ciListener);
			self.listEvent.add(ciListener.listJobTemplate, ciListener);
			self.updateEvent.add(ciListener.updateJobTemplate, ciListener);
			self.editEvent.add(ciListener.editJobTemplate, ciListener);
			self.deleteEvent.add(ciListener.deleteJobTemplate, ciListener);
			self.validateName.add(ciListener.validateName, ciListener);
			self.preOpenEvent.add(ciListener.preOpen, ciListener);
			self.preEditEvent.add(ciListener.preEdit, ciListener);
			self.changeOperationEvent.add(ciListener.changeOperation, ciListener);
			self.changeFeaturesEvent.add(ciListener.changeFeatures, ciListener);
			self.removeDangerEvent.add(ciListener.removeDangerClass, ciListener);
		
			Handlebars.registerHelper('appendMods', function(appIds, module) {
				var returnVal = appIds;
				if (!self.isBlank(module)) {
					returnVal = appIds+" ("+module+")";
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
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, "list"), function(response) {
				self.templateData.jobTemplates = response.data;
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
			self.multiselect();
		},

		pageRefresh : function(response) {
			var self = this;
			commonVariables.navListener.getMyObj(commonVariables.jobTemplates, function(jobTemplatesObj){
				jobTemplatesObj.loadPage(true);
				if(response !== undefined) {
					commonVariables.api.showError(response.responseCode ,"success", true, false, true);
				}
			});
		},
		
		getAction : function(ciRequestBody, action, param, callback, modules) {
			var self = this;
			// Content place holder for the Job template
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, action, param), function(response) {	
				if (action === "edit") {				
					// only for edit popup value population
					self.editEvent.dispatch(response.data, modules);
				} else if (action === "getAppInfos") {
					callback(response);
				} else {
					// For add, update and delete
					if(response.data !== false) {
						self.pageRefresh(response);
					} else if(response.data === false) {
						commonVariables.api.showError(commonVariables.api.error[response.responseCode] ,"error", true, true, true);
					}
				}
			});	
		},
	
		resetForm: function($form) {
			$form.find('input:text, input:password, input:file, select, textarea').val('');
			$form.find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
		},
		
		constructApplicationsHtml : function(jobTemplateName, action, callback) {
				var self = this;
				var modules;
				// show applications names in popup
				self.getAction(self.ciRequestBody, 'getAppInfos', '', function(appInfos) {
					// empty the applist element
					$('select[name=appIds]').html('');
					var obj = $('select[name=appIds]');
					if (!self.isBlank(appInfos.data)) {
						$.each(appInfos.data, function(key, value) {
							var mods = value.modules;
							if (mods === undefined || mods === null || mods.length === 0) {
								var opt = document.createElement("option");
								opt.setAttribute('class',value.name);
								opt.value = value.name;
								opt.appendChild(document.createTextNode(value.name));
								obj.append(opt);
							} else {
								optGroup = document.createElement('optgroup');
								optGroup.label = value.name;
								/*//appendin option for root module
								rootModule = document.createElement("option") ;
								rootModule.innerHTML = value.name;
								rootModule.value = value.name;
								rootModule.appName = value.name;
								optGroup.appendChild(rootModule);*/
								modules = value.modules;
								$.each(value.modules, function(moduleIndex, module) {
									objOption=document.createElement("option");
									objOption.innerHTML = module.code;
									objOption.value = value.name + "#SEP#" + module.code;
									objOption.appName = value.name;
									optGroup.appendChild(objOption);
								});
								obj.append(optGroup);
							}
						});
					}

					//to refresh all bootstrap-select
					$('.selectpicker').selectpicker('refresh');
					if (jobTemplateName !== '') {
						// Restore values
						self.getAction(self.configRequestBody, 'edit', jobTemplateName,'', modules);
					}
				});
				callback();
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function() {
			var self = this;

			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();

			// Open job template popup
			$("input[name=jobTemplatePopup]").unbind("click");
			$("input[name=jobTemplatePopup]").click(function() {
				$("#errMsg").removeClass("errormessage");
				$('input[name=name]').removeClass("errormessage");	
				$('#errMsg').html('');
				//reset the form
				self.resetForm($('#jobTemplate'));
				// button name change
				$('input[name=update]').prop("value", "Create");
				$('input[name=update]').prop("name", "save");			
				// show applications names in popup
				self.constructApplicationsHtml('','add', function() {
					self.preOpenEvent.dispatch( function() {});
				});
				self.opencc(this, $(this).attr('name'));				
   			});
			
			// Save job template
   			$('#jobTemplate').on('click', '[name=save]', function(e) {	
				self.validateName.dispatch('save', self, function(response) {
					self.pageRefresh(response);
				});
			});

   			// Edit job template
   			$("a[name=editpopup]").unbind("click");
			$("a[name=editpopup]").click(function() {
				var thisElem = this;
				//reset the form
				self.resetForm($('#jobTemplate'));
				var jobTemplateName = {};
				var name = $(this).attr('value');
				jobTemplateName.name = name;
				// Construct applications names
				self.constructApplicationsHtml(jobTemplateName, 'edit', function() {
					self.preEditEvent.dispatch( function() {	
						// show edit popup
						setTimeout(function() {
							self.openccci(thisElem, "jobTemplatePopup", "jobTemplates");
						},1500);
						
					});
				});
   			});

   			// Update job template
   			$('#jobTemplate').on('click', '[name=update]', function(e) {				
				self.validateName.dispatch('update', self, function(response) {
					if(response.data !== false) {
						self.pageRefresh(response);
					} else if(response.data === false) {
						commonVariables.api.showError(response.responseCode ,"error", true, false, true);
					}
				});
			});

   			// Delete job template
   			$("input[name=delete]").unbind("click");
   			$("input[name=delete]").click(function() {
   				var jobTemplateName = {};
				jobTemplateName.name = $(this).parent().parent().attr('name');
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', jobTemplateName);
   			});

   			// job template delete poppup
   			$("a[name=deleteconfirm]").unbind("click");
			$("a[name=deleteconfirm]").click(function() {
				self.openccci(this, "yesnopopup_" + $(this).attr('value'),"deleteJobTemplate", true);
			});
			
			$("input[name=name]").on("keypress keyup paste", function(e) {
				this.value = this.value.replace(/[^-_a-zA-Z0-9]/g, '');
			});

			//enable/disable uploads on operation change
			$("select[name=type]").unbind("change");
			$("select[name=type]").on("change", function(){					
				self.changeOperationEvent.dispatch();
			});

			//remove danger class for uploads
			$("select[name=uploadTypes]").unbind("change");
			$("select[name=uploadTypes]").on("change", function(){				
				self.removeDangerEvent.dispatch($("select[name=uploadTypes]"));			
			});
			
			$("select[name=features]").unbind("change");
			$("select[name=features]").on("change", function() {		
				self.changeFeaturesEvent.dispatch();
			});

			//remove danger class for appIds
			$("select[name=appIds]").unbind("change");
			$("select[name=appIds]").on("change", function(){				
				self.removeDangerEvent.dispatch($("select[name=appIds]"));			
			});
			self.tableScrollbar();
		}
	});

	return Clazz.com.components.ci.js.JobTemplates;
});