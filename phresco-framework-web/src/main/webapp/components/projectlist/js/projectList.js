define(["framework/widgetWithTemplate", "projectlist/listener/projectListListener"], function() { 
	
	Clazz.createPackage("com.components.projectlist.js");

	Clazz.com.components.projectlist.js.ProjectList = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		projectsHeader : null,
		templateUrl: commonVariables.contexturl + "/components/projectlist/template/projectList.tmp",
		configUrl: "../components/projectlist/config/config.json",
		name : commonVariables.projectlist,
		contentContainer : commonVariables.contentPlaceholder,
		projectslistListener : null,
		onProjectsEvent : null,
		projectRequestBody: {},
		data : null,
		onProjectEditEvent : null,
		registerEvents : null,
		repositoryEvent : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.projectslistListener = new Clazz.com.components.projectlist.js.listener.ProjectsListListener;
			//self.registerEvents(self.projectslistListener, self.repositoryListener);

			//self.repositoryListener = new Clazz.com.components.repository.js.listener.repositoryListener;
			self.registerEvents(self.projectslistListener);
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			var self = this;
			Clazz.navigationController.push(this);
		},
		
		registerEvents : function(projectslistListener,repositoryListener) {
			var self = this;
			self.onProjectsEvent = new signals.Signal();
			self.onProjectEditEvent = new signals.Signal();
			self.onProjectEditEvent.add(projectslistListener.onEditProject, projectslistListener);			
			self.onProjectsEvent.add(projectslistListener.editApplication, projectslistListener); 

		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			
		},

		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.projectslistListener.getProjectList(self.projectslistListener.getRequestHeader(self.projectRequestBody), function(response) {
				var projectlist = {};
				projectlist.projectlist = response;				
				renderFunction(projectlist, whereToRender);
			});
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$(".dyn_popup").hide();
			$("#applicationedit").css("display", "none");
			$("#editproject").click(function(){
				self.onProjectEditEvent.dispatch();
			});	

			$('a[name=editApplication]').click(function(){
				var value = $(this).text();
				self.onProjectsEvent.dispatch(value);
			});

			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("a[name = 'updatesvn']").unbind("click");
			$("a[name = 'updatesvn']").bind("click",function(){
				$("#svn_update").show();
			});
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});