define(["framework/widgetWithTemplate", "projectlist/listener/projectListListener", "repository/listener/repositoryListener"], function() {
	
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
			self.repositoryListener = new Clazz.com.components.repository.js.listener.RepositoryListener;
			self.registerEvents(self.projectslistListener, self.repositoryListener);

			//self.repositoryListener = new Clazz.com.components.repository.js.listener.repositoryListener;
			//self.registerEvents(self.repositoryListener);
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

			self.repositoryEvent = new signals.Signal();
			self.repositoryEvent.add(repositoryListener.getRepository, repositoryListener);
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
				renderFunction(response, whereToRender);
			});
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			console.info("test response1");
			$(".dyn_popup").hide();
			$("#editproject").click(function(){
				self.onProjectEditEvent.dispatch();
			});	

			$('td[name=editApplication]').click(function(){
				var value = $(this).text();
				self.onProjectsEvent.dispatch(value);
			});


			$("a[name = 'updatesvn']").unbind("click");
			$("a[name = 'updatesvn']").bind("click",function(){
				console.info("test response2");
				//self.repositoryEvent.dispatch();
				//$(this).attr("href", "#svn_update");
				$("#svn_update").show();

			});
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});