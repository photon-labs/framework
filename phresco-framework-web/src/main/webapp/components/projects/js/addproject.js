define(["framework/widgetWithTemplate", "projects/listener/projectsListener", "projects/api/projectsAPI"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.addProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "components/projects/template/addProject.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.addproject,
		projectsListener : null,
		projectAPI : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		templateData : {},
		onProjectsEvent : null,
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		onCreateEvent : null,
		
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			self.projectAPI = new Clazz.com.components.projects.js.api.ProjectsAPI();
			self.registerEvents(self.projectsListener);
		},

		/***
		 * Called once to register all the events 
		 *
		 * @projectsListener: projectsListener methods getting registered
		 */
		registerEvents : function (projectsListener) {
			var self = this;
			self.onProjectsEvent = new signals.Signal();
			self.onRemoveLayerEvent = new signals.Signal();
			self.onAddLayerEvent = new signals.Signal();
			self.onCreateEvent = new signals.Signal();
			self.onCancelCreateEvent = new signals.Signal();
			self.onRemoveLayerEvent.add(projectsListener.removelayer, projectsListener);
			self.onAddLayerEvent.add(projectsListener.addlayer, projectsListener);
			self.onCreateEvent.add(projectsListener.createproject, projectsListener)
			self.onCancelCreateEvent.add(projectsListener.cancelCreateproject, projectsListener)
		},
		
		/***
		 *
		 *	Called once to create the projects listener
		 *
		 */
		loadPage :function(){
			self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			Clazz.navigationController.push(this);
		},
		
		preRender : function(whereToRender, renderFunction) {
			$("#projectList").hide();
			$("#createProject").show();
			var self=this;
			self.setTechnologyData(function(bCheck){
				if(bCheck){
					self.applicationlayerData = self.projectAPI.localVal.getJson("Application Layer");
					self.weblayerData = self.projectAPI.localVal.getJson("Web Layer");
					self.mobilelayerData = self.projectAPI.localVal.getJson("Mobile Layer");
					self.templateData.applicationlayerData = self.applicationlayerData;
					self.templateData.weblayerData = self.weblayerData;
					self.templateData.mobilelayerData = self.mobilelayerData;
					renderFunction(self.templateData, whereToRender);
				}
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
		
		},
		
		setTechnologyData : function(callback) {
			var self=this;
			self.userInfo = JSON.parse(self.projectAPI.localVal.getSession('userInfo'));
			self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, '', 'apptypes'), function(response) {
				$.each(response.data, function(index, value){
					//console.info("index",index,"value",value);
					self.projectAPI.localVal.setJson(value.name, value);
					
					if(response.data.length == (index + 1)){
						callback(true);
					}
				});
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
			self.projectsListener.pilotprojectsEvent();
			self.setDateTimePicker();
			
			$("img[name='close']").unbind('click');
			$("img[name='close']").bind('click', function(){
				self.onRemoveLayerEvent.dispatch($(this));
			});
			
			$(".content_end input").unbind('click');
			$(".content_end input").bind('click', function(){
				self.onAddLayerEvent.dispatch($(this));
			});
			
			$("input[name='Create']").unbind('click');
			$("input[name='Create']").bind('click', function(){
				self.onCreateEvent.dispatch('', 'create');
			});
			
			$("input[name='Cancel']").unbind('click');
			$("input[name='Cancel']").bind('click', function(){
				self.onCancelCreateEvent.dispatch();
			});
			
			$("#endDate").blur(function(){
				if($('.applnLayer').attr('key')=='displayed')
					$("#appcode").focus();	
				else if($('.webLayer').attr('key')=='displayed')
					$("#webappcode").focus();
				else
					$("#mobileappcode").focus();
			});

			$("input[name='projectname']").on('keyup',function() {
				$("input[name='projectcode']").val($(this).val());
			});
			 
		}
	});

	return Clazz.com.components.projects.js.addProject;
});