define(["projectlist/api/projectListAPI"], function() {

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
		act:null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if(self.projectListAPI === null){
				self.projectListAPI = new Clazz.com.components.projectlist.js.api.ProjectsListAPI();
			}
		},
		
		onEditProject : function(projectId) {
			var self = this;
			commonVariables.projectId = projectId;
			commonVariables.navListener.getMyObj(commonVariables.editproject, function(editprojectObject) {
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(editprojectObject, true);
				$("#editprojectTab").css("display", "block");
				self.dynamicrenderlocales(commonVariables.contentPlaceholder);
			});
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
				commonVariables.loadingScreen.showLoading();
				self.projectListAPI.projectslist(header,
					function(response) {
						if (response !== null) {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							commonVariables.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					},
					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
			}

		},


		projectListAction : function(header, loadingObj, callback) {
			var self = this;			
			try {
				if (!self.isBlank(loadingObj)) {
					self.showpopupLoad(loadingObj);
				}
				self.projectListAPI.projectslist(header,
					function(response) {
						if(response !== null ){
							self.hidePopupLoad();
							if(self.act=='delete')
								self.successMsgPopUp(response.message);		
							else if(self.act=='updateget')
								self.successMsgPopUp(response.message);
							else if(self.act=='repoget')
								self.successMsgPopUp(response.message);
							else if(self.act=='commitget')
								self.successMsgPopUp(response.message);
							self.hidePopupLoad();
							callback(response);		
						} else {
							self.hidePopupLoad();
							callback({ "status" : "service failure"});
							self.failureMsgPopUp(response.message);
						}
					},					
					function(textStatus) {
						if(self.act=='updateget')
							self.failureMsgPopUp("Project Update Failed");
						else if(self.act=='repoget')
							self.failureMsgPopUp("Add To Repo Failed");
						else if(self.act=='commitget')
							self.failureMsgPopUp("Commit Failed");
						self.hidePopupLoad();
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
			}
		},
		showpopupLoad : function(loadingObj){
			loadingObj.show();
		},
		
		hidePopupLoad : function(){
			$('.popuploading').hide();
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
			customerId = (customerId === "") ? "photon" : customerId;
			data = JSON.parse(self.projectListAPI.localVal.getSession('userInfo'));
			if(data !== "") { userId = data.id; }
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
			self.act = action;
					
			if(action === "delete") {
				header.requestMethod = "DELETE";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/delete";
			} else if(action === "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/list?customerId="+customerId;				
			} else if(action === "repoget") {
				header.requestMethod = "POST";
				var addrepo ={};
				addrepo.type = projectRequestBody.type;
				addrepo.repoUrl = projectRequestBody.repoUrl;
				addrepo.userName = projectRequestBody.userName;
				addrepo.password = projectRequestBody.password;
				addrepo.commitMessage = projectRequestBody.commitMessage;
				header.requestPostBody = JSON.stringify(addrepo);			
				header.webserviceurl = commonVariables.webserviceurl + "repository/addProjectToRepo?appDirName="+projectRequestBody.appdirname+"&userId="+userId+"&appId="+projectRequestBody.appid+"&projectId="+projectRequestBody.projectid;				
			} else if(action === "commitget") {
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
			} else if(action === "updateget") {
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
			if(action === "reportget") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + "app/printAsPdf?username="+data.name+"&appId="+projectRequestBody.appid+"&customerId="+customerId+"&fromPage="+projectRequestBody.fromPage+"&pdfName="+projectRequestBody.pdfName+"&isReportAvailable=true&projectId="+projectRequestBody.projectId+"&reportDataType=detail&sonarUrl="+projectRequestBody.sonarUrl;		
			} 
			if(action === "getreport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/showPopUp?appDirName="+projectRequestBody.appDir+"&fromPage="+projectRequestBody.fromPage;		
			} 
			if(action === "deleteReport") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/deleteReport?appDirName="+projectRequestBody.appDir+"&reportFileName="+projectRequestBody.fileName+"&fromPage="+projectRequestBody.fromPage;		
			} 
			if(action === "downloadReport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/downloadReport?appDirName="+projectRequestBody.appDir+"&reportFileName="+projectRequestBody.fileName+"&fromPage="+projectRequestBody.fromPage;		
			} 
			if (action === "getCommitableFiles") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "repository/popupValues?appDirName="+projectRequestBody.appdirname+"&userId=" + userId + "&action=commit";
			} 
			return header;
		},
		
		editApplication : function(value, techid) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			if(self.editAplnContent === null){
				commonVariables.navListener.getMyObj(commonVariables.editApplication, function(returnVal){
					self.editAplnContent = returnVal;
					self.loadAppInfo(value, techid);
				});	
			} else {
				self.loadAppInfo(value, techid);
			}	
		},
		
		loadAppInfo : function(value, techid){
			var self = this;
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
				self.projectListAction(self.getActionHeader(actionBody, action), $("#addRepoLoading_"+dynid), function(response){
					if (response.exception === null) {
						$("#addRepo_"+dynid).hide();
					}
				});
			}
		},
		
		addCommitEvent : function(obj, dynid){
			var self = this;
			var commitdata = {}, actionBody, action, arrayCommitableFiles = [];
			self.flag2=1;
			if(!self.validation(dynid)) {
				commitdata.type = $("#commitType_"+dynid).val();
				commitdata.repoUrl = $("#commitRepourl_"+dynid).val();
				commitdata.userName = $("#commitUsername_"+dynid).val();
				commitdata.password = $("#commitPassword_"+dynid).val();
				commitdata.commitMessage = $("#commitMessage_"+dynid).val();
				$.each($('.commitChildChk_'+ dynid) , function(index, value){
					if ($(this).is(':checked')) {
						arrayCommitableFiles.push($(this).val());
					}
				});
				
				commitdata.commitableFiles = arrayCommitableFiles;
				commitdata.appdirname = obj.parent("div").attr("appDirName");
				actionBody = commitdata;
				action = "commitget";
				self.projectListAction(self.getActionHeader(actionBody, action), $('#commitLoading_'+dynid), function(response){
					if (response.exception === null) {
						$("#commit"+dynid).hide();
					}
				});
			}
		},
		
		addReportEvent : function(obj){
			var self = this;
			var reportdata = {}, actionBody, action;		
				reportdata.type = $("#reportType").val();
				reportdata.fromPage = obj.attr("fromPage");
				reportdata.appid = obj.attr("appId");
				reportdata.pdfName =  $('input[name=pdfName]').val();
				reportdata.projectId = obj.attr("projectId");
				reportdata.sonarUrl = obj.attr("sonarUrl");
				actionBody = reportdata;
				action = "reportget";
				self.projectListAction(self.getActionHeader(actionBody, action), $('#pdfReportLoading_'+reportdata.appid), function(response){
				});
		},
		
		getCommitableFiles : function(data, obj) {
			var self = this;
			var dynamicId = data.dynamicId;
			self.openccpl(obj, $(obj).attr('name'), '');
			$('#commitLoading_'+dynamicId).show();
	      	self.projectListAction(self.getActionHeader(data, "getCommitableFiles"), $('#commitLoading_'+dynamicId), function(response) {
	      		$("#dummyCommit_"+dynamicId).css("height","0");
				var commitableFiles = "";
				$('.commit_data_'+dynamicId).hide();
				$('.commitErr_'+dynamicId).hide();      
				if (response.data !== undefined && !response.data.repoExist) {
					$('.commitErr_'+dynamicId).show();
				} else if (response.data !== undefined && response.data.repoInfoFile.length !== 0) {
					$('input[name=commitbtn]').prop("disabled", true);
					$('input[name=commitbtn]').removeClass("btn_style");		
					$('.commit_data_'+dynamicId).show();
					$('#commitRepourl_'+dynamicId).val(response.data.repoUrl);
					commitableFiles += '<thead><tr><th><input dynamicId="'+ dynamicId +'" class="commitParentChk_'+ dynamicId +'"  type="checkbox"></th><th>File</th><th>Status</th></tr></thead><tbody>';
					$.each(response.data.repoInfoFile, function(index, value) {
						commitableFiles += '<tr><td><input dynamicId="'+ dynamicId +'" class="commitChildChk_' + dynamicId + '" type="checkbox" value="' + value.commitFilePath + '"></td>';
						commitableFiles += '<td style="width:150px;" title="'+ value.commitFilePath +'">"' + self.trimValue(value.commitFilePath) + '"</td>';
						commitableFiles += '<td>"' + value.status + '"</td></tr>';
					});
					$('.commitable_files_'+dynamicId).html(commitableFiles); 
					$.each(response.data.repoInfoFile, function(index, value) {
						self.checkBoxEvent($('.commitParentChk_'+dynamicId), 'commitChildChk_'+dynamicId, $('input[name=commitbtn]'));
					});
					self.checkAllEvent($('.commitParentChk_'+dynamicId), $('.commitChildChk_'+dynamicId), $('input[name=commitbtn]'));
				}
				
				if (!self.isBlank(response.data.repoInfoFile) && response.data.repoInfoFile.length === 0) {
					$('.commit_data_'+dynamicId).show();
				}
			});
		},
		
		getReportEvent : function(obj){
			var self = this;
			var getreportdata = {}, actionBody, action;		
				getreportdata.fromPage = obj.attr("fromPage");
				getreportdata.appDir = obj.closest("tr").attr("class");
				var temp  = obj.closest("tr").attr("class");
				actionBody = getreportdata;
				action = "getreport";
				var dynamicId = obj.attr("dynamicId");
				self.projectListAction(self.getActionHeader(actionBody, action), $("#pdfReportLoading_"+dynamicId), function(response) {
					var content = "";
					$("tbody[name=generatedPdfs]").empty();
					if(response.length !== 0 && response.length !== undefined) {
						$("#noReport").hide();
						$("thead[name=pdfHeader]").show();
						for(var i =0; i < response.length; i++) {
							var headerTr = '<tr class="generatedRow" fileName="'+response[i].fileName+'" appdirname = "'+temp+'"><td>' + response[i].time + '</td><td>'+response[i].type+'</td>';
							content = content.concat(headerTr);
							headerTr = '<td><a class="tooltiptop" fileName="'+response[i].fileName+'" fromPage="All" href="#" data-toggle="tooltip" data-placement="top" name="downLoad" data-original-title="Download Pdf" title=""><img src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt="0"></a></td>';
							content = content.concat(headerTr);
							headerTr = '<td><a class="tooltiptop" fileName="'+response[i].fileName+'" fromPage="All" href="#" data-toggle="tooltip" data-placement="top" name="delete" data-original-title="Delete Pdf" title=""><img src="themes/default/images/helios/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>';
							content = content.concat(headerTr);
						}
						$("tbody[name=generatedPdfs]").append(content);
						self.clickFunction();
					} else {
						$("thead[name=pdfHeader]").hide();
						$("#noReport").show();
						$("#noReport").html("No Report are Available");
					}
					
				});
		},
		
		clickFunction : function(){
			var self = this;
			$("a[name=delete]").click(function() {
				var deletedata = {}, actionBody = {}, action;
				deletedata.fileName = $(this).attr("fileName");
				deletedata.fromPage = $(this).attr("frompage");
				deletedata.appDir = $(this).closest('tr').attr("appdirname");
				$(".generatedRow").each(function() {
					var pdfFileName = $(this).attr("fileName");
					if(pdfFileName === deletedata.fileName){
						$("tr[fileName='"+pdfFileName+"']").remove();
					}
				});
				var size = $(".generatedRow").size();
				if(size === 0) {
					$("thead[name=pdfHeader]").hide();
					$("#noReport").show();
					$("#noReport").html("No Report are Available");
				}
				actionBody = deletedata;
				self.projectListAction(self.getActionHeader(actionBody, "deleteReport"), "", function(response){
				});
			});
			
			$("a[name=downLoad]").click(function() {
				var downloaddata = {}, actionBody = {}, action;
				downloaddata.fileName = $(this).attr("fileName");
				downloaddata.fromPage = $(this).attr("frompage");
				downloaddata.appDir = $(this).closest('tr').attr("appdirname");
				actionBody = downloaddata;
				self.projectListAction(self.getActionHeader(actionBody, "downloadReport"), "", function(response){
				});
			});
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
				self.projectListAction(self.getActionHeader(actionBody, action), $("#updateRepoLoading_"+dynid), function(response){
					if (response.exception === null) {
						$("#svn_update"+dynid).hide();
					}
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
				
				if(self.flag1 === 1)
				{	
					if(repourl === ""){
						$("#repourl_"+dynid).focus();
						$("#repourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#repourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(uname === ""){
						$("#uname_"+dynid).focus();
						$("#uname_"+dynid).attr('placeholder','Enter UserName');
						$("#uname_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(pwd === ""){
						$("#pwd_"+dynid).focus();
						$("#pwd_"+dynid).attr('placeholder','Enter Password');
						$("#pwd_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else
						self.hasError=false;
					self.flag1=0;
					return self.hasError;
				}

				else if(self.flag2 === 1)
				{
					if(commitRepourl === ""){
						$("#commitRepourl_"+dynid).focus();
						$("#commitRepourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#commitRepourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(commitUsername === ""){
						$("#commitUsername_"+dynid).focus();
						$("#commitUsername_"+dynid).attr('placeholder','Enter UserName');
						$("#commitUsername_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(commitPassword === ""){
						$("#commitPassword_"+dynid).focus();
						$("#commitPassword_"+dynid).attr('placeholder','Enter Password');
						$("#commitPassword_"+dynid).addClass("errormessage");
						self.hasError = true;
					}else
						self.hasError=false;
					self.flag2=0;
					return self.hasError;
				}
				
				else if(self.flag3 === 1)
				{	
					if(updateRepourl === ""){
						$("#updateRepourl_"+dynid).focus();
						$("#updateRepourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#updateRepourl_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(updateUsername === ""){
						$("#updateUsername_"+dynid).focus();
						$("#updateUsername_"+dynid).attr('placeholder','Enter UserName');
						$("#updateUsername_"+dynid).addClass("errormessage");
						self.hasError = true;
					} else if(updatePassword === ""){
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
		
		trimValue: function (value) {
			var len = value.length;
			if(len > 50) {
			value = value.substr(0, 50) + "...";
			return value;
			}
			return value;
		}	
	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});