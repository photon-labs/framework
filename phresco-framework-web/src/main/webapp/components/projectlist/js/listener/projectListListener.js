define(["framework/widget", "framework/widgetWithTemplate", "projectlist/api/projectListAPI"], function() {

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
			self.editproject = commonVariables.navListener.getMyObj(commonVariables.editproject);
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
		 * @projectRequestBody: request body of project
		 * @return: returns the contructed header
		 */
		getActionHeader : function(projectRequestBody, action) {
			var self=this, header, data = {}, userId;
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			data = JSON.parse(self.projectListAPI.localVal.getSession('userInfo'));
			userId = data.id;
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
					
			if(action == "delete") {
				header.requestMethod = "DELETE";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/delete";
			}
			if(action == "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/list?customerId="+customerId;				
			}
			if(action == "repoget") {
				header.requestMethod = "POST";
				console.info("projectRequestBody", projectRequestBody);
				var addrepo ={};
				addrepo.type = projectRequestBody.type;
				addrepo.repoUrl = projectRequestBody.repoUrl;
				//addrepo.userName = projectRequestBody.userName;
				addrepo.userName = "santhosh_ja";
				//addrepo.password = projectRequestBody.password;
				addrepo.password = "santJ!23";
				addrepo.commitMessage = projectRequestBody.commitMessage;
				header.requestPostBody = JSON.stringify(addrepo);
				userId = "santhosh_ja";				
				header.webserviceurl = commonVariables.webserviceurl + "repository/addProjectToRepo?appDirName="+projectRequestBody.appdirname+"&userId="+userId+"&appId="+projectRequestBody.appid+"&projectId="+projectRequestBody.projectid;				
			}
			if(action == "commitget") {
				header.requestMethod = "POST";
				console.info("projectRequestBody", projectRequestBody);
				var addcommit ={};
				addcommit.type = projectRequestBody.type;
				addcommit.repoUrl = projectRequestBody.repoUrl;
				//addrepo.userName = projectRequestBody.userName;
				addcommit.userName = "santhosh_ja";
				//addrepo.password = projectRequestBody.password;
				addcommit.password = "santJ!23";
				addcommit.commitMessage = projectRequestBody.commitMessage;
				addcommit.commitableFiles = projectRequestBody.commitableFiles;
				header.requestPostBody = JSON.stringify(addcommit);
				userId = "santhosh_ja";
				console.info("addcommit", addcommit);
				header.webserviceurl = commonVariables.webserviceurl + "repository/commitProjectToRepo?appDirName="+projectRequestBody.appdirname;
			}
			if(action == "updateget") {
				console.info("projectRequestBody", projectRequestBody);
				var addupdate ={};
				addupdate.type = projectRequestBody.type;
				addupdate.repoUrl = projectRequestBody.repoUrl;
				//addrepo.userName = projectRequestBody.userName;
				addupdate.userName = "santhosh_ja";
				//addrepo.password = projectRequestBody.password;
				addupdate.password = "santJ!23";
				addupdate.version = projectRequestBody.revision;
				header.requestPostBody = JSON.stringify(addupdate);
				userId = "santhosh_ja";
				console.info("addupdate", addupdate);
				header.webserviceurl = commonVariables.webserviceurl + "repository/updateImportedApplication?appDirName="+projectRequestBody.appdirname;
			}
			if(action == "reportget") {
				console.info("update");
				header.requestMethod = "POST";
				//header.webserviceurl = commonVariables.webserviceurl + "/app/printAsPdf/username="+data.name+"&appId="+appId+"&customerId="+customerId+"&fromPage="+fromPage+"&isReportAvailable=true&projectId="+projectId+"&reportDataType=detail&sonarUrl="+fromPage;		
			} 
			return header;
		},
		
		editApplication : function(value, techid) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.editAplnContent = commonVariables.navListener.getMyObj(commonVariables.editApplication);
			self.editAplnContent.appDirName = value;
            self.projectListAPI.localVal.setSession('appDirName', value);
            self.projectListAPI.localVal.setSession('techid', techid);	
			Clazz.navigationController.push(self.editAplnContent, true);
			$("#aplntitle").html("Edit - "+value);
		},
		
		dynamicrenderlocales : function(contentPlaceholder) {
			var self = this;
			self.renderlocales(contentPlaceholder);
		},
		addRepoEvent : function(obj, dynid){
			var self = this;
			var repodata = {}, actionBody, action;
			//if(!self.validation()) {
				console.info("test");
				repodata.type = $("#type_"+dynid).val();
				repodata.repoUrl = $("#repourl_"+dynid).val();
				repodata.userName = $("#uname_"+dynid).val();
				repodata.password = $("#pwd_"+dynid).val();
				repodata.commitMessage = $("#repomessage_"+dynid).val();
				repodata.appdirname = obj.parent("div").attr("appDirName");
				repodata.appid = obj.parent("div").attr("appId");
				repodata.projectid = obj.parent("div").attr("projectId");
				actionBody = repodata;
				action = "repoget";
				self.projectListAction(self.getActionHeader(actionBody, action), function(response){
				});
			//}
		},
		
		addCommitEvent : function(obj, dynid){
			var self = this;
			var commitdata = {}, actionBody, action, arrayCommitableFiles = ["C:/Documents and Settings/arunkumar_ve/workspace/projects/wordpress-WordPress/pom.xml","C:/Documents and Settings/arunkumar_ve/workspace/projects/wordpress-WordPress/source/wordpress/config/phresco-env-config.xml"];
			//if(!self.validation()) {
				commitdata.type = $("#commitType_"+dynid).val();
				commitdata.repoUrl = $("#commitRepourl_"+dynid).val();
				commitdata.userName = $("#commitUsername_"+dynid).val();
				commitdata.password = $("#commitPassword_"+dynid).val();
				commitdata.commitMessage = $("#commitMessage_"+dynid).val();
				commitdata.commitableFiles = arrayCommitableFiles;
				commitdata.appDirName = obj.parent("div").attr("appDirName");
				console.info("commitdata", commitdata);
				actionBody = commitdata;
				action = "commitget";
				self.projectListAction(self.getActionHeader(actionBody, action), function(response){
				});
			//}
		},
		
		addReportEvent : function(){
			var self = this;
			var reportdata = {}, actionBody, action;		
				reportdata.type = $("#reportType").val();
				actionBody = reportdata;
				action = "reportget";
				self.projectListAction(self.getActionHeader(actionBody, action));
		},
		
		addUpdateEvent : function(obj, dynid, revision){
			var self = this;
			var updatedata = {}, actionBody, action;
			//if(!self.validation()) {
				updatedata.type = $("#updateType_"+dynid).val();
				updatedata.repoUrl = $("#updateRepourl_"+dynid).val();
				updatedata.userName = $("#updateUsername_"+dynid).val();
				updatedata.password = $("#updatePassword_"+dynid).val();
				updatedata.revision = revision;
				updatedata.appdirname = obj.parent("div").attr("appDirName");
				console.info("updatedata", updatedata);
				actionBody = updatedata;
				action = "updateget";
				self.projectListAction(self.getActionHeader(actionBody, action), function(response){
				});
			//}
		},
		
		validation : function() {		
				var flag1=0,flag2=0,flag3=0,repourl, uname, pwd, commitRepourl, commitUsername, commitPassword, updateRepourl, updateUsername, updatePassword, revision;
				repourl = $("#repourl").val();
				uname = $("#uname").val();
				pwd = $("#pwd").val();
				
				commitRepourl = $("#commitRepourl").val();
				commitUsername = $("#commitUsername").val();
				commitPassword = $("#commitPassword").val();
				
				updateRepourl = $("#updateRepourl").val();
				updateUsername= $("#updateUsername").val();
				updatePassword = $("#updatePassword").val();
				revision = $("#revision").val();
				
				if(repourl == ""){
					$("#repourl").focus();
					$("#repourl").attr('placeholder','Enter Repourl');
					$("#repourl").addClass("errormessage");
					self.hasError = true;
				} else if(uname == ""){
					$("#uname").focus();
					$("#uname").attr('placeholder','Enter UserName');
					$("#uname").addClass("errormessage");
					self.hasError = true;
				} else if(pwd == ""){
					$("#pwd").focus();
					$("#pwd").attr('placeholder','Enter Password');
					$("#pwd").addClass("errormessage");
					self.hasError = true;
				}
				if(commitRepourl == ""){
					$("#commitRepourl").focus();
					$("#commitRepourl").attr('placeholder','Enter Repourl');
					$("#commitRepourl").addClass("errormessage");
					self.hasError = true;
				} else if(commitUsername == ""){
					$("#commitUsername").focus();
					$("#commitUsername").attr('placeholder','Enter UserName');
					$("#commitUsername").addClass("errormessage");
					self.hasError = true;
				} else if(commitPassword == ""){
					$("#commitPassword").focus();
					$("#commitPassword").attr('placeholder','Enter Password');
					$("#commitPassword").addClass("errormessage");
					self.hasError = true;
				}
				if(updateRepourl == ""){
					$("#updateRepourl").focus();
					$("#updateRepourl").attr('placeholder','Enter Repourl');
					$("#updateRepourl").addClass("errormessage");
					self.hasError = true;
				} else if(updateUsername == ""){
					$("#updateUsername").focus();
					$("#updateUsername").attr('placeholder','Enter UserName');
					$("#updateUsername").addClass("errormessage");
					self.hasError = true;
				} else if(updatePassword == ""){
					$("#updatePassword").focus();
					$("#updatePassword").attr('placeholder','Enter Password');
					$("#updatePassword").addClass("errormessage");
					self.hasError = true;
				}				
				return self.hasError;	
		},

	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});