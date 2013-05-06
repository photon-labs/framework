define(["framework/widget", "framework/widgetWithTemplate", "projectlist/api/projectListAPI", "projects/editproject", "application/application"], function() {

	Clazz.createPackage("com.components.projectlist.js.listener");

	Clazz.com.components.projectlist.js.listener.ProjectsListListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		projectListAPI : null,
		editproject : null,
		editAplnContent : null,
		projectRequestBody : {},
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			self.projectListAPI = new Clazz.com.components.projectlist.js.api.ProjectsListAPI();
			self.editproject = new Clazz.com.components.projects.js.EditProject();
		},
		
		onEditProject : function(projectId) {
			var self = this;
			self.editproject.projectId = projectId;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.editproject, true);
			$("#editprojectTab").css("display", "block");
			self.dynamicrenderlocales(commonVariables.contentPlaceholder);
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
			var self=this, header;
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: commonVariables.webserviceurl+commonVariables.projectlistContext+"/list?customerId=photon"
			}
			return header;
		},
		
		editApplication : function(value) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editAplnContent = new Clazz.com.components.application.js.Application();
			Clazz.navigationController.push(self.editAplnContent, true);
			$("#applicationedit").css("display", "block");
			$("#aplntitle").html("Edit ("+value+")");
		},
		
		dynamicrenderlocales : function(contentPlaceholder) {
			var self = this;
			self.renderlocales(contentPlaceholder);
		}

	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});