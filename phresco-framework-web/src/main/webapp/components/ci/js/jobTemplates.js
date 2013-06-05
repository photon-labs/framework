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

			if (self.updateEvent === null) {
				self.updateEvent = new signals.Signal();
			}

			if (self.deleteEvent === null) {
				self.deleteEvent = new signals.Signal();
			}

			// Trigger registered events
			self.addEvent.add(ciListener.addJobTemplate, ciListener);
			self.listEvent.add(ciListener.listJobTemplate, ciListener);
			self.updateEvent.add(ciListener.updateJobTemplate, ciListener);
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
				self.pageRefresh();
				// console.log("Sathya selvan ......."  + JSON.stringify(response.data));
				//self.renderTemplate(response.data, commonVariables.contentPlaceholder);
			});	
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
   				self.opencc(this, $(this).attr('name'));
   			});

   			// Save job template
   			$("input[name=save]").unbind("click");
   			$("input[name=save]").click(function() {
   				console.log("Save");
   				self.addEvent.dispatch(function(response) {
   					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'add', '');
				});
   			});

   			// Edit job template
   			$("input[name=edit]").unbind("click");
			$("input[name=edit]").click(function() {
				console.log("edit");
				self.editEvent.dispatch(function(response){
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'edit', '');
				});
   			});

   			// Update job template
   			$("input[name=update]").unbind("click");
   			$("input[name=update]").click(function() {
   				console.log("update");
   				self.updateEvent.dispatch(function(response){
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'update', '');
				});
   			});

   			// Delete job template
   			$("input[name=delete]").unbind("click");
   			$("input[name=delete]").click(function() {
   				console.log("delete " + $(this).parent().parent().attr('id'));
   				var jobTemplateName = {};
				jobTemplateName.name = $(this).parent().parent().attr('id');
				jobTemplateName = $.param(jobTemplateName);
				self.configRequestBody = {};
				self.getAction(self.configRequestBody, 'delete', jobTemplateName);
   			});

   			// job template delete poppup
   			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				console.log("Deletion purpose ");
				self.opencc(this, $(this).attr('name'));
			});
		}
	});

	return Clazz.com.components.ci.js.JobTemplates;
});