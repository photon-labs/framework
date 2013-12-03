define(["framework/widgetWithTemplate", "repository/listener/repositoryListener"], function() {
	Clazz.createPackage("com.components.repository.js");

	Clazz.com.components.repository.js.SourceRepository = Clazz.extend(Clazz.WidgetWithTemplate, {

		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/repository/template/sourceRepository.tmp",
		configUrl: "components/repository/config/config.json",
		repositoryListener : null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			
			if(self.repositoryListener === null){
				self.repositoryListener = new Clazz.com.components.repository.js.listener.RepositoryListener();
			}	
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

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			$(".dyn_popup").hide();
			
			$("input[name=rep_create]").unbind("click");
			$("input[name=rep_create]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
			
			$("input[name=rep_release]").unbind("click");
			$("input[name=rep_release]").click(function() {
				self.opencc(this, $(this).attr('name'));
			});
		}
	});

	return Clazz.com.components.repository.js.SourceRepository;
});