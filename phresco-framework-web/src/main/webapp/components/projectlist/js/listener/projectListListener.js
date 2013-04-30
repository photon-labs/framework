define(["framework/widget", "framework/widgetWithTemplate", "projectlist/api/projectListAPI", "projects/project", "application/application"], function() {

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
			self.editproject = new Clazz.com.components.projects.js.Project();
		},
		
		onEditProject : function(projectId) {
			var self = this;

			if(self.editproject === null) {
				self.editproject = new Clazz.com.components.projects.js.Project();
			}
			self.getProjectList(self.getRequestHeader(self.projectRequestBody, projectId), function(response) {
				self.editproject.projectEdit = response.data;
			});
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.editproject, true);
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
		getRequestHeader : function(projectRequestBody, id) {
			var self=this, header, data = {}, userId;
			
			data = JSON.parse(self.projectListAPI.localVal.getSession('userInfo'));
			userId = data.id;
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: ''
			}
			if (id == '') {
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.projectlistContext+"/list?customerId=photon";
			} else {
				header.webserviceurl = commonVariables.webserviceurl+"project/edit?userId="+userId+"&customerId=photon&projectId="+id;
			}
			return header;
		},
		
		editApplication : function(value) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editAplnContent = new Clazz.com.components.application.js.Application();
			Clazz.navigationController.push(self.editAplnContent, true);
			$("#applicationedit").css("display", "block");
			$("#aplntitle").html("Edit - "+value);
		},
		
		dynamicrenderlocales : function(contentPlaceholder) {
			var self = this;
			self.renderlocales(contentPlaceholder);
		}

	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});