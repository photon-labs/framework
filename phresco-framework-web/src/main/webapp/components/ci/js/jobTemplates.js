define(["ci/listener/ciListener"], function() {
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
		openEvent : null,
		deleteEvent : null,
		ciRequestBody : {},
		templateData : {},
	
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

			if (self.openEvent === null) {
				self.openEvent = new signals.Signal();
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

			// Trigger registered events
			self.openEvent.add(ciListener.openJobTemplate, ciListener);
			self.addEvent.add(ciListener.addJobTemplate, ciListener);
			self.listEvent.add(ciListener.listJobTemplate, ciListener);
			self.updateEvent.add(ciListener.updateJobTemplate, ciListener);
			self.editEvent.add(ciListener.editJobTemplate, ciListener);
			self.deleteEvent.add(ciListener.deleteJobTemplate, ciListener);
		},
		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			console.log("loadpage ");
			Clazz.navigationController.push(this);
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			console.log("CI pre render ... " + whereToRender);
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, "list"), function(response) {
				console.log(JSON.stringify(response.data));
				self.templateData.jobTemplates = response.data;
				// var userPermissions = JSON.parse(self.configurationlistener.configurationAPI.localVal.getSession('userPermissions'));
				// self.templateData.userPermissions = userPermissions;
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
		},

		pageRefresh: function() {
			var self = this;
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, "list"), function(response) {
				console.log(JSON.stringify(response.data));
				self.templateData.jobTemplates = response.data;
				self.renderTemplate(self.templateData, commonVariables.contentPlaceholder);
			});
		},

		getAction : function(ciRequestBody, action, param) {
			var self=this;
			// Content place holder for the Job template
			// Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, action, param), function(response) {
				// self.loadPage();
				if (action === "edit") {
					self.editEvent.dispatch(response.data);
				} else {
					self.pageRefresh();
				}

				// console.log("Sathya selvan ......."  + JSON.stringify(response.data));
				//self.renderTemplate(response.data, commonVariables.contentPlaceholder);
			});	
		},
	
		resetForm: function($form) {
			$form.find('input:text, input:password, input:file, select, textarea').val('');
			$form.find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
		},
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;

			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			
			// Open job template popup
			$("input[name=jobTemplatePopup]").unbind("click");
			$("input[name=jobTemplatePopup]").click(function() {
				//reset the form
				self.resetForm($('#jobTemplate'));
				// button name change
				$('input[name=update]').prop("value", "Create");
				$('input[name=update]').prop("name", "save");
   				self.opencc(this, $(this).attr('name'));
   			});

   			// Save job template
   			//$("input[name=save]").unbind("click");
   			//$("input[name=save]").click(function() {
   			$('#jobTemplate').on('click', '[name=save]', function(e) {
   				console.log("Save");
   				self.addEvent.dispatch(function(response) {
   					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'add', '');
				});
   			});

   			// Edit job template
   			$("a[name=editpopup]").unbind("click");
			$("a[name=editpopup]").click(function() {
				//reset the form
				self.resetForm($('#jobTemplate'));
				console.log("edit " +  $(this).attr('value'));
				var jobTemplateName = {};
				var name = $(this).attr('value');
				jobTemplateName.name = name;
				// show edit popup
				self.opencc(this, "jobTemplatePopup");
				self.getAction(self.configRequestBody, 'edit', jobTemplateName);
   			});

   			// Update job template
   			//$("input[name=update]").unbind("click");
   			//$("input[name=update]").click(function() {
   			$('#jobTemplate').on('click', '[name=update]', function(e) {
   				console.log("update");
   				self.updateEvent.dispatch(function(response) {
   					console.log("req body content => " + response);
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'update', '');
				});
   			});

   			// Delete job template
   			$("input[name=delete]").unbind("click");
   			$("input[name=delete]").click(function() {
   				console.log("delete " + $(this).parent().parent().attr('name'));
   				var jobTemplateName = {};
				jobTemplateName.name = $(this).parent().parent().attr('name');
				jobTemplateName = $.param(jobTemplateName);
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', jobTemplateName);
   			});

   			// job template delete poppup
   			$("a[name=deleteconfirm]").unbind("click");
			$("a[name=deleteconfirm]").click(function() {
				console.log("Deletion purpose ");
				self.opencc(this, "yesnopopup_" + $(this).attr('value'));
			});
		}
	});

	return Clazz.com.components.ci.js.JobTemplates;
});