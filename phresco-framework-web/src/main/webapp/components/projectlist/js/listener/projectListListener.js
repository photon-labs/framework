define(["framework/widget", "framework/widgetWithTemplate", "projectlist/api/projectListAPI"], function() {

	Clazz.createPackage("com.components.projectlist.js.listener");

	Clazz.com.components.projectlist.js.listener.ProjectsListListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		projectListAPI : null,
		editproject : null,
		editAplnContent : null,
		projectRequestBody : {},
		flag1:null,
		flag2:null,
		flag3:null,
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
				var addrepo ={};
				addrepo.type = projectRequestBody.type;
				addrepo.repoUrl = projectRequestBody.repoUrl;
				addrepo.userName = projectRequestBody.userName;
				addrepo.password = projectRequestBody.password;
				addrepo.commitMessage = projectRequestBody.commitMessage;
				header.requestPostBody = JSON.stringify(addrepo);			
				header.webserviceurl = commonVariables.webserviceurl + "repository/addProjectToRepo?appDirName="+projectRequestBody.appdirname+"&userId="+userId+"&appId="+projectRequestBody.appid+"&projectId="+projectRequestBody.projectid;				
			}
			if(action == "commitget") {
				header.requestMethod = "POST";
				var addcommit ={};
				addcommit.type = projectRequestBody.type;
				addcommit.repoUrl = projectRequestBody.repoUrl;
				addcommit.userName = projectRequestBody.userName;
				addcommit.password = projectRequestBody.password;
				addcommit.commitMessage = projectRequestBody.commitMessage;
				addcommit.commitableFiles = projectRequestBody.commitableFiles;
				header.requestPostBody = JSON.stringify(addcommit);
				header.webserviceurl = commonVariables.webserviceurl + "repository/commitProjectToRepo?appDirName="+projectRequestBody.appdirname;
			}
			if(action == "updateget") {
				var addupdate ={};
				header.requestMethod = "POST";
				addupdate.type = projectRequestBody.type;
				addupdate.repoUrl = projectRequestBody.repoUrl;
				addupdate.userName = projectRequestBody.userName;
				addupdate.password = projectRequestBody.password;
				addupdate.version = projectRequestBody.revision;
				header.requestPostBody = JSON.stringify(addupdate);
				header.webserviceurl = commonVariables.webserviceurl + "repository/updateImportedApplication?appDirName="+projectRequestBody.appdirname;
			}
			if(action == "reportget") {
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
			self.flag1=1;
			if(!self.validation(dynid)) {
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
			}
		},
		
		addCommitEvent : function(obj, dynid){
			var self = this;
			var commitdata = {}, actionBody, action, arrayCommitableFiles = ["C:/Documents and Settings/arunkumar_ve/workspace/projects/new-HTML5 Multichannel YUI Widget/pom.xml","C:/Documents and Settings/arunkumar_ve/workspace/projects/new-HTML5 Multichannel YUI Widget/docs/README.txt"];
			self.flag2=1;
			if(!self.validation(dynid)) {
				commitdata.type = $("#commitType_"+dynid).val();
				commitdata.repoUrl = $("#commitRepourl_"+dynid).val();
				commitdata.userName = $("#commitUsername_"+dynid).val();
				commitdata.password = $("#commitPassword_"+dynid).val();
				commitdata.commitMessage = $("#commitMessage_"+dynid).val();
				commitdata.commitableFiles = arrayCommitableFiles;
				commitdata.appdirname = obj.parent("div").attr("appDirName");
				actionBody = commitdata;
				action = "commitget";
				self.projectListAction(self.getActionHeader(actionBody, action), function(response){
				});
			}
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
			self.flag3=1;
			if(!self.validation(dynid)) {
				updatedata.type = $("#updateType_"+dynid).val();
				updatedata.repoUrl = $("#updateRepourl_"+dynid).val();
				updatedata.userName = $("#updateUsername_"+dynid).val();
				updatedata.password = $("#updatePassword_"+dynid).val();
				updatedata.revision = revision;
				updatedata.appdirname = obj.parent("div").attr("appDirName");
				actionBody = updatedata;
				action = "updateget";
				self.projectListAction(self.getActionHeader(actionBody, action), function(response){
				});
			}
		},
		
		validation : function(dynid) {	
				var self=this;	
				var repourl, uname, pwd, commitRepourl, commitUsername, commitPassword, updateRepourl, updateUsername, updatePassword, revision;
				repourl = $("#repourl_"+dynid).val();
				uname = $("#uname_"+dynid).val();
				pwd = $("#pwd_"+dynid).val();
				
				commitRepourl = $("#commitRepourl_"+dynid).val();
				commitUsername = $("#commitUsername_"+dynid).val();
				commitPassword = $("#commitPassword_"+dynid).val();
				
				updateRepourl = $("#updateRepourl_"+dynid).val();
				updateUsername= $("#updateUsername_"+dynid).val();
				updatePassword = $("#updatePassword_"+dynid).val();
				revision = $("#revision_"+dynid).val();
				
				if(self.flag1==1)
				{	
					if(repourl == ""){
						$("#repourl_"+dynid).focus();
						$("#repourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#repourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(uname == ""){
						$("#uname_"+dynid).focus();
						$("#uname_"+dynid).attr('placeholder','Enter UserName');
						$("#uname_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(pwd == ""){
						$("#pwd_"+dynid).focus();
						$("#pwd_"+dynid).attr('placeholder','Enter Password');
						$("#pwd_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else
						self.hasError=false;
					self.flag1=0;
					return self.hasError;
				}

				else if(self.flag2==1)
				{
					if(commitRepourl == ""){
						$("#commitRepourl_"+dynid).focus();
						$("#commitRepourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#commitRepourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(commitUsername == ""){
						$("#commitUsername_"+dynid).focus();
						$("#commitUsername_"+dynid).attr('placeholder','Enter UserName');
						$("#commitUsername_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(commitPassword == ""){
						$("#commitPassword_"+dynid).focus();
						$("#commitPassword_"+dynid).attr('placeholder','Enter Password');
						$("#commitPassword_"+dynid).addClass("errormessage");
						self.hasError = true;
					}else
						self.hasError=false;
					self.flag2=0;
					return self.hasError;
				}
				
				else if(self.flag3==1)
				{	
					if(updateRepourl == ""){
						$("#updateRepourl_"+dynid).focus();
						$("#updateRepourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#updateRepourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(updateUsername == ""){
						$("#updateUsername_"+dynid).focus();
						$("#updateUsername_"+dynid).attr('placeholder','Enter UserName');
						$("#updateUsername_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(updatePassword == ""){
						$("#updatePassword_"+dynid).focus();
						$("#updatePassword_"+dynid).attr('placeholder','Enter Password');
						$("#updatePassword_"+dynid).addClass("errormessage");
						self.hasError = true;
					}else
						self.hasError=false;
					self.flag3=0;
					return self.hasError;
				}
		},
	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});