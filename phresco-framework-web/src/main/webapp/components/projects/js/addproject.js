define(["projects/listener/projectsListener"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.addProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "components/projects/template/addProject.tmp",
		configUrl: "components/projects/config/config.json",
		name : commonVariables.addproject,
		projectsListener : null,
		applicationlayerData : null,
		weblayerData : null,
		mobilelayerData : null,
		templateData : {},
		onProjectsEvent : null,
		onRemoveLayerEvent : null,
		onAddLayerEvent : null,
		onCreateEvent : null,
		onCancelCreateEvent : null,
		
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.projectsListener === null){
				self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
			}
			self.registerEvents(self.projectsListener);
		},

		/***
		 * Called once to register all the events 
		 *
		 * @projectsListener: projectsListener methods getting registered
		 */
		registerEvents : function (projectsListener) {
			var self = this;
			if(self.onProjectsEvent === null) {	
				self.onProjectsEvent = new signals.Signal();
			}	
			if(self.onRemoveLayerEvent === null) {	
				self.onRemoveLayerEvent = new signals.Signal();
			}
			
			if(self.onAddLayerEvent === null) {
				self.onAddLayerEvent = new signals.Signal();
			}
			
			if(self.onCreateEvent === null) {
				self.onCreateEvent = new signals.Signal();
			}
			
			if(self.onCancelCreateEvent === null) {
				self.onCancelCreateEvent = new signals.Signal();
			}
			
			self.onRemoveLayerEvent.add(projectsListener.removelayer, projectsListener);
			self.onAddLayerEvent.add(projectsListener.addlayer, projectsListener);
			self.onCreateEvent.add(projectsListener.createproject, projectsListener);
			self.onCancelCreateEvent.add(projectsListener.cancelCreateproject, projectsListener);
		},
		
		/***
		 *
		 *	Called once to create the projects listener
		 *
		 */
		loadPage:function(){
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, true);
		},
		
		preRender : function(whereToRender, renderFunction) {
			$("#projectList").hide();
			$("#createProject").show();
			var self=this;
			self.projectsListener.counter = null;
			self.applicationlayerData = self.projectsListener.projectAPI.localVal.getJson("Application Layer");
			self.weblayerData = self.projectsListener.projectAPI.localVal.getJson("Web Layer");
			self.mobilelayerData = self.projectsListener.projectAPI.localVal.getJson("Mobile Layer");
			if(self.applicationlayerData !== null &&  self.weblayerData !== null && self.mobilelayerData !== null) {
				self.templateData.applicationlayerData = self.applicationlayerData;
				self.templateData.weblayerData = self.weblayerData;
				self.templateData.mobilelayerData = self.mobilelayerData;
				renderFunction(self.templateData, whereToRender);
			} else {
				self.setTechnologyData(function(bCheck){
					if(bCheck){
						self.applicationlayerData = self.projectsListener.projectAPI.localVal.getJson("Application Layer");
						self.weblayerData = self.projectsListener.projectAPI.localVal.getJson("Web Layer");
						self.mobilelayerData = self.projectsListener.projectAPI.localVal.getJson("Mobile Layer");
						self.templateData.applicationlayerData = self.applicationlayerData;
						self.templateData.weblayerData = self.weblayerData;
						self.templateData.mobilelayerData = self.mobilelayerData;
						renderFunction(self.templateData, whereToRender);
					}
				});
			}	
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self=this;
			self.multiselect();
			self.projectsListener.multiModuleEvent("false");
		},
		
		setTechnologyData : function(callback) {
			var self=this;
			self.userInfo = JSON.parse(self.projectsListener.projectAPI.localVal.getSession('userInfo'));
			self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, '', 'apptypes'), function(response) {
				$.each(response.data, function(index, value){
					self.projectsListener.projectAPI.localVal.setJson(value.name, value);
					if(response.data.length === (index + 1)){
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
			self.projectsListener.multiModuleEvent("false");
			self.setDateTimePicker();
			
			self.windowResize();
			
			$("img[name='close']").unbind('click');
			$("img[name='close']").bind('click', function(){
				self.onRemoveLayerEvent.dispatch($(this));
			});
			
			$(".flt_left input").unbind('click');
			$(".flt_left input").bind('click', function(){
				self.onAddLayerEvent.dispatch($(this));
			});
			
			$("#appcode, #webappcode, #mobileappcode").bind('input', function(){
				var str = $(this).val();
				str = str.replace(/[^a-zA-Z 0-9\-\_]+/g, "");
				str = str.replace(/\s/g, "");
				$(this).val(str);
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
				if($('.applnLayer').attr('key') === 'displayed') {
					$("#appcode").focus();	
				} else if($('.webLayer').attr('key') === 'displayed') {
					$("#webappcode").focus();
				} else {
					$("#mobileappcode").focus();
				}
			});

			$("input[name='projectname']").on('keyup',function() {
				$("input[name='projectcode']").val($(this).val());
			});
			
			$("input[name='multimodule']").click(function() {
				$(this).is(':checked')?$(this).val(true):$(this).val(false);
				var multimodule = $("input[name=multimodule]").val();
				self.projectsListener.multiModuleEvent(multimodule);
			});

			$("#strdt").click(function() {
				$("#startDate").focus();
			});

			$("#enddt").click(function() {
				$("#endDate").focus();
			});
			
			if(commonVariables.animation) {
				$(".create_proj .scrollContent").mCustomScrollbar({
					autoHideScrollbar:true,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true}
				});	
			}
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
		}
	});

	return Clazz.com.components.projects.js.addProject;
});