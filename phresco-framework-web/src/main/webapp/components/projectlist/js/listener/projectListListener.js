define(["framework/widget", "projectlist/api/projectListAPI", "projects/project", "application/application"], function() {

	Clazz.createPackage("com.components.projectlist.js.listener");

	Clazz.com.components.projectlist.js.listener.ProjectsListListener = Clazz.extend(Clazz.Widget, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		projectListAPI : null,
		editproject : null,
		editAplnContent : null,
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.projectListAPI = new Clazz.com.components.projectlist.js.api.ProjectsListAPI();
			self.editproject = new Clazz.com.components.projects.js.Project();
		},
		
		onEditProject : function() {
			var self = this;
			if(self.editproject === null) {
				self.editproject = new Clazz.com.components.projects.js.Project();
			}
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.editproject, true);
		},

		getProjectList : function(header, callback) {
			var self = this;
			try {
				self.projectListAPI.projectslist(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						
					}
				);
			} catch(exception) {
				
			}

		},
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(projectRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl+commonVariables.projectlistContext+"/list?customerId=photon"
			};

			return header;
		},
		
		editApplication : function(value) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editAplnContent = new Clazz.com.components.application.js.Application();
			Clazz.navigationController.push(self.editAplnContent, true);
			$("#applicationedit").css("display", "block");
			$("#aplntitle").html("Edit - "+value);
		}

	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});