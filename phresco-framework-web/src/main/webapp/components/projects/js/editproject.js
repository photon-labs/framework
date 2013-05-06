define(["framework/widgetWithTemplate", "projects/listener/projectsListener", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.EditProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "/components/projects/template/editproject.tmp",
		configUrl: "../components/projects/config/config.json",
		name : commonVariables.project,
		projectsListener : null,
		onProjectsEvent : null,
		projectRequestBody : {},
		templateData : {},
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		projectAPI : null,
		getData : null,
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			self.projectAPI = new Clazz.com.components.projects.js.api.ProjectsAPI();
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
			self.setTechnologyData("photon");
			self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
			self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
			self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
			self.templateData.applicationlayerData = self.applicationlayerData;
			self.templateData.weblayerData = self.weblayerData;
			self.templateData.mobilelayerData = self.mobilelayerData;
			self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, self.projectId), function(response) {
				self.templateData.editProject = response.data;				
			});
			renderFunction(self.templateData, whereToRender);
		}, 
		
		postRender : function(element) {
		},
		
		setTechnologyData : function(customerId) {
			var self=this;
			self.userInfo = JSON.parse(self.projectAPI.localVal.getSession('userInfo'));
			$.each(self.userInfo.customers, function(index, value){
				if(value.id === customerId){
					$.each(value.applicableAppTypes, function(index, value){
						self.projectAPI.localVal.setJson(value.name, value);
					});
				}
			});
		},
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
		
			var self=this;
			
			self.projectsListener.addLayersEvent();
			self.projectsListener.removeLayersEvent();
			self.projectsListener.technologyAndVersionChangeEvent();
			
			self.getData = self.templateData.editProject.appInfos;
			self.projectsListener.editSeriveTechnolyEvent(self.getData);
			
		}
	});

	return Clazz.com.components.projects.js.EditProject;
});