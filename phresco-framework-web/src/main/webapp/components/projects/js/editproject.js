define(["projects/listener/projectsListener"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.EditProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "components/projects/template/editProject.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.editproject,
		projectsListener : null,
		onProjectsEvent : null,
		projectRequestBody : {},
		templateData : {},
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		getData : null,
		onUpdateProjectsEvent : null,
		onCancelUpdateEvent : null,
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.projectsListener === null)
				self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			self.registerEvents(self.projectsListener);
		},
		
		registerEvents : function (projectsListener) {
			var self = this;
			if(self.onUpdateProjectsEvent === null)
				self.onUpdateProjectsEvent = new signals.Signal();
			if(self.onCancelUpdateEvent === null)	
				self.onCancelUpdateEvent = new signals.Signal();
			self.onUpdateProjectsEvent.add(projectsListener.createproject, projectsListener);
			self.onCancelUpdateEvent.add(projectsListener.cancelCreateproject, projectsListener);
		},
		
		
		/***
		 * Called once to register all the events 
		 *
		 * @projectsListener: projectsListener methods getting registered
		 */
		/***
		 *
		 *	Called once to create the projects listener
		 *
		 */
		loadPage :function(){
			Clazz.navigationController.push(this);
		},
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		 
		preRender: function(whereToRender, renderFunction){
			var self = this;
				self.applicationlayerData = self.projectsListener.projectAPI.localVal.getJson("Application Layer");
				self.weblayerData = self.projectsListener.projectAPI.localVal.getJson("Web Layer");
				self.mobilelayerData = self.projectsListener.projectAPI.localVal.getJson("Mobile Layer");
			if (self.applicationlayerData != null && self.weblayerData != null && self.mobilelayerData != null) {
				self.templateData.applicationlayerData = self.applicationlayerData;
				self.templateData.weblayerData = self.weblayerData;
				self.templateData.mobilelayerData = self.mobilelayerData;
				var userPermissions = JSON.parse(self.projectsListener.projectAPI.localVal.getSession('userPermissions'));
				self.templateData.userPermissions = userPermissions;
				self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, commonVariables.projectId), function(response) {
					self.templateData.editProject = response.data;
					// Setting project id in local storage for future use in job templates
					self.projectsListener.projectAPI.localVal.setSession("projectId", response.data.id);
					self.getData = self.templateData.editProject.appInfos;	
					renderFunction(self.templateData, whereToRender);
				});
			} else {
				self.setTechnologyData(function(bCheck){
				if(bCheck){
					self.applicationlayerData = self.projectsListener.projectAPI.localVal.getJson("Application Layer");
					self.weblayerData = self.projectsListener.projectAPI.localVal.getJson("Web Layer");
					self.mobilelayerData = self.projectsListener.projectAPI.localVal.getJson("Mobile Layer");
					self.templateData.applicationlayerData = self.applicationlayerData;
					self.templateData.weblayerData = self.weblayerData;
					self.templateData.mobilelayerData = self.mobilelayerData;
					var userPermissions = JSON.parse(self.projectsListener.projectAPI.localVal.getSession('userPermissions'));
					self.templateData.userPermissions = userPermissions;
					self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, commonVariables.projectId), function(response) {
						self.templateData.editProject = response.data;
						// Setting project id in local storage for future use in job templates
						self.projectsListener.projectAPI.localVal.setSession("projectId", response.data.id);
						self.getData = self.templateData.editProject.appInfos;	
						renderFunction(self.templateData, whereToRender);						
					});
				}
			});
			}
		}, 
		
		setTechnologyData : function(callback) {
			var self=this;
			self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, '', 'apptypes'), function(response) {
				$.each(response.data, function(index, value){
					//console.info("index",index,"value",value);
					self.projectsListener.projectAPI.localVal.setJson(value.name, value);
					
					if(response.data.length == (index + 1)){
						callback(true);
					}
				});
			});
		},
		
		postRender : function(element) {
			var self=this;
			self.projectsListener.editSeriveTechnolyEvent(self.getData);
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
		
			var self=this;
			self.setDateTimePicker();
			self.projectsListener.addLayersEvent();
			self.projectsListener.removeLayersEvent();
			self.projectsListener.technologyAndVersionChangeEvent();
			
			$("#updateProject").click(function() {
				self.onUpdateProjectsEvent.dispatch(commonVariables.projectId, "update");
			});
			
			$("#cancelUpdate").click(function() {
				self.onCancelUpdateEvent.dispatch();
			});
			
		}
	});

	return Clazz.com.components.projects.js.EditProject;
});