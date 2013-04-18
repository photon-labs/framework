define(["framework/widgetWithTemplate", "projects/listener/projectsListener"], function() {

	Clazz.createPackage("com.components.projects.js");

	Clazz.com.components.projects.js.AddProject = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		templateUrl: commonVariables.contexturl + "/components/projects/template/addproject.tmp",
		configUrl: "../components/projects/config/config.json",
		name : commonVariables.addproject,
		projectsListener : null,
		onProjectsEvent : null,
			
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			self.projectsListener = new Clazz.com.components.projects.js.listener.projectsListener();
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
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
		}
	});

	return Clazz.com.components.projects.js.AddProject;
});