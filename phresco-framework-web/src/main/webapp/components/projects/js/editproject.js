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
		getData : null,
		onUpdateProjectsEvent : null,
		onCancelUpdateEvent : null,
		removeLayerEvent : null,
		addLayerEvent : null,
			
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
		
		registerEvents : function (projectsListener) {
			var self = this;
			if(self.onUpdateProjectsEvent === null){
				self.onUpdateProjectsEvent = new signals.Signal();
			}	
			if(self.onCancelUpdateEvent === null){	
				self.onCancelUpdateEvent = new signals.Signal();
			}
			
			if(self.removeLayerEvent === null) {
				self.removeLayerEvent = new signals.Signal();
			}
			
			if(self.addLayerEvent === null) {
				self.addLayerEvent = new signals.Signal();
			}
			
			self.removeLayerEvent.add(projectsListener.removelayeredit, projectsListener);
			self.addLayerEvent.add(projectsListener.addlayeredit, projectsListener);
			self.onUpdateProjectsEvent.add(projectsListener.createproject, projectsListener);
			self.onCancelUpdateEvent.add(projectsListener.cancelCreateproject, projectsListener);
		},
		
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
				self.applicationlayerData = commonVariables.api.localVal.getJson("Application Layer");
				self.weblayerData = commonVariables.api.localVal.getJson("Web Layer");
				self.mobilelayerData = commonVariables.api.localVal.getJson("Mobile Layer");
			if (self.applicationlayerData !== null && self.weblayerData !== null && self.mobilelayerData !== null) {
				self.templateData.applicationlayerData = self.applicationlayerData;
				self.templateData.weblayerData = self.weblayerData;
				self.templateData.mobilelayerData = self.mobilelayerData;
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				self.templateData.userPermissions = userPermissions;
				self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, commonVariables.projectId), function(response) {
					var responseData = response.data;
					//To change start date into the format of (dd/mm/yyyy)
					var startdate = responseData.startDate;
					if (startdate !== null) {
						var sd = new Date(startdate);
						var startDateformat = [ sd.getMonth()+1, sd.getDate(), sd.getFullYear()].join('/');
						responseData.startDate = startDateformat;
					} else {
						responseData.startDate = "";
					}
					
					//To change end date into the format of (dd/mm/yyyy)
					var enddate = responseData.endDate;
					if (enddate !== null) {
						var ed = new Date(enddate);
						var endDateformat = [ ed.getMonth()+1, ed.getDate(), ed.getFullYear()].join('/');
						responseData.endDate = endDateformat;
					} else {
						responseData.endDate = "";
					}
					
					self.templateData.editProject = responseData;
					// Setting project id in local storage for future use in job templates
					commonVariables.api.localVal.setSession("projectId", responseData.id);
					self.getData = self.templateData.editProject.appInfos;	
					renderFunction(self.templateData, whereToRender);
				});
			} else {
				self.setTechnologyData(function(bCheck){
				if(bCheck){
					self.applicationlayerData = commonVariables.api.localVal.getJson("Application Layer");
					self.weblayerData = commonVariables.api.localVal.getJson("Web Layer");
					self.mobilelayerData = commonVariables.api.localVal.getJson("Mobile Layer");
					self.templateData.applicationlayerData = self.applicationlayerData;
					self.templateData.weblayerData = self.weblayerData;
					self.templateData.mobilelayerData = self.mobilelayerData;
					var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
					self.templateData.userPermissions = userPermissions;
					self.projectsListener.getEditProject(self.projectsListener.getRequestHeader(self.projectRequestBody, commonVariables.projectId), function(response) {
						var responseData = response.data;
						//To change start date into the format of (dd/mm/yyyy)
						var startdate = responseData.startDate;
						if (startdate !== null) {
							var sd = new Date(startdate);
							var startDateformat = [ sd.getMonth()+1, sd.getDate(), sd.getFullYear()].join('/');
							responseData.startDate = startDateformat;
						} else {
							responseData.startDate = "";
						}
						
						//To change end date into the format of (dd/mm/yyyy)
						var enddate = responseData.endDate;
						if (enddate !== null) {
							var ed = new Date(enddate);
							var endDateformat = [ ed.getMonth()+1, ed.getDate(), ed.getFullYear()].join('/');
							responseData.endDate = endDateformat;
						} else {
							responseData.endDate = "";
						}
						
						self.templateData.editProject = responseData;
						// Setting project id in local storage for future use in job templates
						commonVariables.api.localVal.setSession("projectId", responseData.id);
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
					commonVariables.api.localVal.setJson(value.name, value);
					if(response.data.length === (index + 1)){
						callback(true);
					}
				});
			});
		},
		
		postRender : function(element) {
			var self=this;
			self.multiselect();
			commonVariables.navListener.currentTab = commonVariables.editproject;
			self.projectsListener.editSeriveTechnolyEvent(self.getData);
			self.projectsListener.enablebuttonEdit();
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
			self.projectsListener.multiModuleEvent("false");
			self.windowResize();
			
			$("#updateProject").unbind('click');
			$("#updateProject").bind('click', function() {
				self.onUpdateProjectsEvent.dispatch(commonVariables.projectId, "update");
			});
			
			$("#cancelUpdate").unbind('click');
			$("#cancelUpdate").bind('click', function() {
				self.onCancelUpdateEvent.dispatch();
			});
			
			$("img[name='close']").unbind('click');
			$("img[name='close']").bind('click', function(){
				self.removeLayerEvent.dispatch($(this));
			});
			
			$(".flt_left input").unbind('click');
			$(".flt_left input").bind('click', function(){
				self.addLayerEvent.dispatch($(this));
			});
			
			$("#startDate,#endDate").bind('keydown', function(e) {
				var keyCode = e.keyCode || e.which;
				if ((e.which >= 48 && e.which <= 57 && !e.shiftKey) || (keyCode === 191 && !e.shiftKey) || (keyCode === 9) || (keyCode === 8)){
					return true;
				} else {
					e.preventDefault();
				}
			});
			
			$("#strdt").click(function() {
				$("#startDate").focus();
			});

			$("#enddt").click(function() {
				$("#endDate").focus();
			});
			
			$("input[name='multimodule']").click(function() {
				$(this).is(':checked')?$(this).val(true):$(this).val(false);
				var multimodule = $("input[name=multimodule]").val();
				self.projectsListener.multiModuleEvent(multimodule);
			});
			this.customScroll($(".scrolldiv"));
		}
	});

	return Clazz.com.components.projects.js.EditProject;
});