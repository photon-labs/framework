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
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			self.projectListAPI = new Clazz.com.components.projectlist.js.api.ProjectsListAPI();
			self.editproject = new Clazz.com.components.projects.js.Project();
		},
		
		onEditProject : function(projectId) {
			var self = this;
			commonVariables.projectId = projectId;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.editproject, true);
			$("#editprojectTab").css("display", "block");
			self.dynamicrenderlocales(commonVariables.contentPlaceholder);
		},
		
		getCustomer : function() {
			var selectedcustomer = $("#selectedCustomer").text();
			var customerId = "";
			$.each($("#customer").children(), function(index, value){
				var customerText = $(value).children().text();
				if(customerText === selectedcustomer){
					customerId = $(value).children().attr('id');
				}
			});
			
			return customerId;
		},


		getProjectList : function(header, callback) {
			var self = this;
			try {
				self.loadingScreen.showLoading();
				self.projectListAPI.projectslist(header,
					function(response) {
						if (response !== null) {
							self.loadingScreen.removeLoading();
							callback(response);
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}

		},


		projectListAction : function(header, callback) {
			var self = this;			
			try {
				self.loadingScreen.showLoading();
				self.projectListAPI.projectslist(header,
					function(response) {
						if(response != null ){
							self.loadingScreen.removeLoading();
							callback(response);						
						} else {
							self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					},
					
					function(textStatus) {
						self.loadingScreen.removeLoading();	
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
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
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			data = JSON.parse(self.projectListAPI.localVal.getSession('userInfo'));
			userId = data.id;
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				requestPostBody: JSON.stringify(projectRequestBody),
				webserviceurl: ''
			}
			if (id == '') {
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/list?customerId="+customerId;
			} else {
				header.webserviceurl = commonVariables.webserviceurl+"project/edit?userId="+userId+"&customerId="+ customerId +"&projectId="+id;
			}
			return header;
		},

		getActionHeader : function(projectRequestBody, action) {
			var self = this, header = {
				contentType: "application/json",
				requestMethod: "DELETE",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}

			if(projectRequestBody != "") {
				header.requestPostBody = JSON.stringify(projectRequestBody);
			}
					
			if(action == "delete") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/delete";
			} 
			return header;
		},

		
		editApplication : function(value) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editAplnContent = new Clazz.com.components.application.js.Application();
			self.editAplnContent.appDirName = value;
            self.projectListAPI.localVal.setJson('appDirName', value);			
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