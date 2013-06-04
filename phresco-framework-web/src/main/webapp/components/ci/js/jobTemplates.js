define(["framework/widgetWithTemplate", "ci/listener/ciListener"], function() {
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
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.dynamicpage = commonVariables.navListener.getMyObj(commonVariables.dynamicPage);
			self.ciListener = new Clazz.com.components.ci.js.listener.CIListener(globalConfig);
			self.registerEvents(self.ciListener);
		},
		
		
		registerEvents : function (ciListener) {
			var self=this;
			// Register events
			self.addEvent = new signals.Signal();
			self.listEvent = new signals.Signal();
			self.updateEvent = new signals.Signal();
			self.deleteEvent = new signals.Signal();
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
			Clazz.navigationController.push(this);
		},
		
		// preRender: function(whereToRender, renderFunction){
		// 	var self = this;
		// 	alert("CI pre render ... ");		
		// },

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
		},
		
		/* dynamicEvent : function() {
			var self = this; 
			var dependency = '';
			dependency = $("select[name='sonar']").find(':selected').attr('dependency');
				
		}, */

		getAction : function(ciRequestBody, action, param) {
			var self=this;
			self.ciListener.listJobTemplate(self.ciListener.getRequestHeader(self.ciRequestBody, action, param), function(response) {
				self.loadPage();
			});	
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$(".dyn_popup").hide();
			// Open job template popup
			$("input[name=jobTemplatePopup]").click(function() {
   				self.opencc(this, $(this).attr('name'));
   			});

   			// Save job template
   			$("input[name=save]").unbind("click");
   			$("input[name=save]").click(function() {
   				alert("Save");
   				self.addEvent.dispatch(function(response) {
   					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'add', '');
				});
   			});

   			// Edit job template
			$("input[name=edit]").click(function() {
				alert("edit");
				self.editEvent.dispatch(function(response){
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'edit', '');
				});
   			});

   			// Update job template
   			$("input[name=update]").click(function() {
   				alert("update");
   				self.updateEvent.dispatch(function(response){
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'update', '');
				});
   			});

   			// Delete job template
   			$("input[name=delete]").click(function() {
   				alert("delete");
   				self.deleteEvent.dispatch(function(response){
					self.ciRequestBody = response;
					self.getAction(self.ciRequestBody, 'delete', '');
				});
   			});
		}
	});

	return Clazz.com.components.ci.js.JobTemplates;
});