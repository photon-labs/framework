define(["framework/widgetWithTemplate", "repository/listener/repositoryListener"], function() {
	Clazz.createPackage("com.components.repository.js");

	Clazz.com.components.repository.js.BuildRepository = Clazz.extend(Clazz.WidgetWithTemplate, {

		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/repository/template/buildRepository.tmp",
		configUrl: "components/repository/config/config.json",
		name : commonVariables.buildRepo,
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
			var self = this;
			var requestBody = {};
			self.repositoryListener.repository(self.repositoryListener.getActionHeader(self.requestBody, "browseBuildRepo"), function(response) {
				var responseData = response.data;
				if (responseData !== undefined && responseData !== null && responseData.length > 0) {
					$.each(responseData, function(index, value) {
						self.repositoryListener.constructTree(value)
					});
				} else {
					$('.tree_view').hide();
					$('.file_view').hide();
					$('#messagedisp').show();
				}
			});
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
			$("input[name=downloadArtifact]").unbind("click");
			$("input[name=downloadArtifact]").bind("click", function() {
				var appDirName = $(this).attr('appdirname');
				var nature = $(this).attr('nature');
				var version = $(this).attr("version");
				var moduleName = $(this).attr("moduleName");
				var requestBody = {};
				requestBody.appDirName = appDirName;
				requestBody.nature = nature;
				requestBody.version = version;
				requestBody.moduleName = moduleName;
				var header = self.repositoryListener.getActionHeader(requestBody, "downloadBuild");
				$.fileDownload(header.webserviceurl);
			});
			
			self.customScroll($(".file_view"));
		}
	});

	return Clazz.com.components.repository.js.BuildRepository;
});