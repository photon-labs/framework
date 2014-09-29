define([], function() {

	Clazz.createPackage("com.components.projectlist.js.listener");

	Clazz.com.components.projectlist.js.listener.ProjectsListListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		editproject : null,
		editAplnContent : null,
		projectRequestBody : {},
		flag1:null,
		flag2:null,
		flag3:null,
		act:null,
		iddynamic : [],
		delprojectname : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onEditProject : function(projectId, projectCode) {
			var self = this;
			commonVariables.projectId = projectId;
			self.projectRequestBody.projectId = projectCode;
			self.projectListAction(self.getActionHeader("", "continuousDeliveryType"), "", function(response) {
				ciType = response.data;
			});
			self.projectListAction(self.getActionHeader(self.projectRequestBody, "configTypes"), "", function(response) {	
				commonVariables.api.localVal.setJson('configTypes', response.data);
				commonVariables.navListener.getMyObj(commonVariables.dashboard, function(editprojectObject) {
					commonVariables.projectLevel = true;
					$('.hProjectId').val(projectId); 
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(editprojectObject, commonVariables.animation);
					if(ciType == "Bamboo"){
						$("#editprojectTab").css("display", "block");
						$("#continuousIntegration").css("display","none");
					}else{
						$("#editprojectTab").css("display", "block");
						$("#continuousIntegration").css("display","block");
					}
					$("#dashboard a").addClass('act');
					self.dynamicrenderlocales(commonVariables.contentPlaceholder);
				});
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
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
							//commonVariables.loadingScreen.removeLoading();
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}

		},


		projectListAction : function(header, loadingObj, callback) {
			var self = this;			
			try {
				if (!self.isBlank(loadingObj)) {
					self.showpopupLoad(loadingObj);
				}
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if(response !== null && response.status !== "error" && response.status !== "failure"){
							self.hidePopupLoad();
							if(response.responseCode !== 'PHR210051' && response.responseCode !== 'PHR200015' && response.responseCode !== 'PHR200021' && response.responseCode !== 'PHR600004' && response.responseCode !== null && response.responseCode !== undefined && response.responseCode !== 'PHR200027') {
								commonVariables.api.showError(response.responseCode ,"success", true);
								if(response.responseCode === 'PHR200010' || response.responseCode === 'PHR200026') {
									$('.msgdisplay').prepend(self.delprojectname+' ');
								}
							}	
							callback(response);		
						} else {
							if(response.responseCode === "PHR210035") {
								callback(response);
								self.hidePopupLoad();
							} else if(response.responseCode === "PHR210037") {
								callback(response);
								self.hidePopupLoad();
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true);
								self.hidePopupLoad();
							}
						}
					},					
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
						self.hidePopupLoad();
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
			}
		},
		
		projectListActionForScm : function(header, loadingObj, callback) {
			var self = this;			
			try {
				if (!self.isBlank(loadingObj)) {
					self.showpopupLoad(loadingObj);
				}
				commonVariables.api.ajaxRequestForScm(header,
					function(response) {
						if(response !== null && response.status !== "error" && response.status !== "failure"){
							self.hidePopupLoad();
							if(response.responseCode !== 'PHR200015' && response.responseCode !== 'PHR200021' && response.responseCode !== 'PHR600004' && response.responseCode !== null && response.responseCode !== undefined) {
								commonVariables.api.showError(response.responseCode ,"success", true);
								if(response.responseCode === 'PHR200010' || response.responseCode === 'PHR200026') {
									$('.msgdisplay').prepend(self.delprojectname+' ');
								}
							}	
							callback(response);		
						} else {
							if(response.responseCode === "PHR210035") {
								callback(response);
								self.hidePopupLoad();
							} else if(response.responseCode === "PHR210037") {
								callback(response);
								self.hidePopupLoad();
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true);
								self.hidePopupLoad();
							}
						}
					},					
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
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
			var self=this, header, data = {}, userId, projectId;
			var customerId = self.getCustomer();
			projectId = commonVariables.api.localVal.getSession('projectId');
			customerId = (customerId === "") ? "photon" : customerId;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			if (data !== undefined && data !== null && data !== "") {
				userId = data.id; 
			}
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			self.act = action;
			
			if(action === "delete") {
				header.requestMethod = "DELETE";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/delete"+"?actionType="+projectRequestBody.actionType;
			} else if(action === "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/list?customerId="+customerId;				
			} else if(action === "repoget") {
				header.requestMethod = "POST";
				var addrepo ={};
				header.requestPostBody = JSON.stringify(projectRequestBody.repoInfo);
				header.webserviceurl = commonVariables.webserviceurl + "repository/addProjectToRepo?appDirName="+projectRequestBody.appdirname+"&userId="+userId+"&appId="+projectRequestBody.appid+"&projectId="+projectRequestBody.projectid+"&displayName="+data.displayName;				
			} else if(action === "commitget") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(projectRequestBody.repoInfo);
				header.webserviceurl = commonVariables.webserviceurl + "repository/commitProjectToRepo?appDirName="+projectRequestBody.appdirname+"&displayName="+data.displayName;
			} else if(action === "updateget") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(projectRequestBody.repoInfo);
				header.webserviceurl = commonVariables.webserviceurl + "repository/updateImportedApplication?appDirName="+projectRequestBody.appdirname+"&displayName="+data.displayName;
			} else if (action === "configTypes") {
				if(projectId !== null){
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&projectId="+projectId;
				} else {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&techId="+projectRequestBody.techid;
				}
			} else if(action === "continuousDeliveryType"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/type?customerId="+ customerId + "&projectId=" + projectId;
			}
			if(action === "generateReport") {
				var moduleParam = self.isBlank(projectRequestBody.moduleName) ? "" : '&moduleName='+projectRequestBody.moduleName;
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + "app/printAsPdf?appDirName=" + projectRequestBody.appDirName + "&" + projectRequestBody.data + "&userId=" + userId + moduleParam;
			} 
			if(action === "getreport") {
				var moduleParam = self.isBlank(projectRequestBody.moduleName) ? "" : '&moduleName='+projectRequestBody.moduleName;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/showPopUp?appDirName="+projectRequestBody.appDir+"&fromPage="+projectRequestBody.fromPage + moduleParam;		
			} 
			if(action === "deleteReport") {
				var moduleParam = self.isBlank(projectRequestBody.moduleName) ? "" : '&moduleName='+projectRequestBody.moduleName;
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/deleteReport?appDirName="+projectRequestBody.appDir+"&reportFileName="+projectRequestBody.fileName+"&fromPage="+projectRequestBody.fromPage + moduleParam;		
			} 
			if(action === "downloadReport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/downloadReport?appDirName="+projectRequestBody.appDir+"&reportFileName="+projectRequestBody.fileName+"&fromPage="+projectRequestBody.fromPage;		
			} 
			if (action === "getCommitableFiles") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "repository/popupValues?appDirName="+projectRequestBody.appdirname+"&userId=" + userId + "&action=commit";
			} 
			if (action === "getUpdatableFiles") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "repository/popupValues?appDirName="+projectRequestBody.appdirname+"&userId=" + userId + "&action=update";
			}
			if(action === "searchlogmessage") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl +"repository/logMessages?appDirName="+projectRequestBody.appDirName;
			}
			if (action === "checkMachine") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'util/checkMachine';
			}
			if (action === "checkForDependents") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'project/dependents?moduleName='+projectRequestBody.subModuleName+'&rootModule='+projectRequestBody.rootModule;
			}
			if (action === "repoExists") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'repository/repoExists?appDirName='+projectRequestBody.appDirName;
			}
			
			return header;
		},
		
		editApplication : function(value, techid, module, appName) {
			var self = this, jsonVal = {};
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			jsonVal.techid = techid;
			self.projectRequestBody = jsonVal;
			self.projectListAction(self.getActionHeader(self.projectRequestBody, "configTypes"), "", function(response) {
				commonVariables.projectLevel = false;
				commonVariables.api.localVal.setJson('configTypes', response.data);
				commonVariables.navListener.configDropdown(response.data);
				if (self.isBlank(module)) {
					$('.moduleName').val('');
					$('.rootModule').val('');
				} else {
					$('.moduleName').val(module);
					$('.rootModule').val(value);
				}
				if(self.editAplnContent === null){
					commonVariables.navListener.getMyObj(commonVariables.editApplication, function(returnVal){
						self.editAplnContent = returnVal;
						self.loadAppInfo(value, techid, module, appName);
					});	
				} else {
					self.loadAppInfo(value, techid, module, appName);
				}					
			});
			
		},
		
		loadAppInfo : function(value, techid, module, appName, from, hasModules){
			var self = this;
			self.editAplnContent.appDirName = value;
			commonVariables.api.localVal.setSession('appDirName', value);
			commonVariables.api.localVal.setSession('techid', techid);
			if (self.isBlank(commonVariables.editAppFrom) && commonVariables.editAppHasModules) {
				try {
					var header = self.editAplnContent.editApplicationListener.getRequestHeader(value , "getappinfo");
					commonVariables.api.ajaxRequest(header,
						function(response) {
							if (response !== null && response.status !== "error" && response.status !== "failure") {
								commonVariables.api.localVal.setProjectInfo(response);
								commonVariables.navListener.getMyObj(commonVariables.featurelist, function(returnVal){
									Clazz.navigationController.push(returnVal, commonVariables.animation);
								});
								commonVariables.navListener.showHideControls(commonVariables.editApplication);
								$("li#featurelist").children().addClass("act");
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true);			
							}
						},

						function(textStatus) {
							commonVariables.api.showError("serviceerror" ,"error", true);
						}
					);
					setTimeout(function() {
						var header = self.editAplnContent.editApplicationListener.getRequestHeader(value , "getApplicableOptions" , techid);
						commonVariables.api.ajaxRequest(header,
							function(response) {
								if (response !== null && response.status !== "error" && response.status !== "failure") {
									commonVariables.api.localVal.setSession("applicableOptions", JSON.stringify(response.data));
									commonVariables.navListener.showHideParentOptions();									
							} else {
									commonVariables.api.showError(response.responseCode ,"error", true);			
								}
							},
							function(textStatus) {
								commonVariables.api.showError("serviceerror" ,"error", true);
							}
						);
					}, 500);
					
				} catch(exception) {
					
				}
			} else {
				Clazz.navigationController.push(self.editAplnContent, commonVariables.animation);
			}
			self.isBlank(module) ? $("#aplntitle").html("Edit - "+appName) : $("#aplntitle").html("Edit - "+module);
			// Code Added by balakumaran : App directory name conflict occurs in multiple tab
			$("#seltdAppDirName").val(value);

		},
		
		dynamicrenderlocales : function(contentPlaceholder) {
			var self = this;
			self.renderlocales(contentPlaceholder);
		},
		
		addRepoEvent : function(obj, dynid){
			var self = this;
			var srcRepoDetail = {}, phrescoRepoDetail = {}, testRepoDetail = {}, actionBody = {}, action, repoInfo = {};
			if(!self.validateAddToRepoData(dynid)) {
				self.showBtnLoading("button[name='addrepobtn'][id='"+dynid+"']");
				if($("#repomessage_"+dynid).val() === '') {
					var drop = obj.parent().prev().prev().find('tbody').find('.searchdropdown option:first-child').text();
					$("#repomessage_"+dynid).val(drop);
				}
				srcRepoDetail.type = $("#type_"+dynid).val();
				srcRepoDetail.repoUrl = $("#repourl_"+dynid).val();
				srcRepoDetail.userName = $("#uname_"+dynid).val();
				srcRepoDetail.password = $("#pwd_"+dynid).val();
				srcRepoDetail.commitMessage = $("#repomessage_"+dynid).val();
				srcRepoDetail.passPhrase = $("#repoPhrase_"+dynid).val();
				srcRepoDetail.serverPath = $("#addTfsServerPath_"+dynid).val();
				repoInfo.srcRepoDetail = srcRepoDetail;
				
				if ($("#splitDotPhresco_"+dynid).is(":checked")) {
					splitDotPhrescoVal = 'true';
					phrescoRepoDetail.type = $("#phrescotype_"+dynid).val();
					phrescoRepoDetail.repoUrl = $("#phrescorepourl_"+dynid).val();
					phrescoRepoDetail.userName = $("#phrescouname_"+dynid).val();
					phrescoRepoDetail.password = $("#phrescopwd_"+dynid).val();
					phrescoRepoDetail.commitMessage = $("#phrescorepomessage_"+dynid).val();
					phrescoRepoDetail.passPhrase = $("#phrescorepoPhrase_"+dynid).val();
					phrescoRepoDetail.serverPath = $("#addPhrTfsServerPath_"+dynid).val();
					repoInfo.phrescoRepoDetail = phrescoRepoDetail;
				}
				
				if ($("#splitTest_"+dynid).is(":checked")) {
					testRepoDetail.type = $("#testtype_"+dynid).val();
					testRepoDetail.repoUrl = $("#testrepourl_"+dynid).val();
					testRepoDetail.userName = $("#testuname_"+dynid).val();
					testRepoDetail.password = $("#testpwd_"+dynid).val();
					testRepoDetail.commitMessage = $("#testrepomessage_"+dynid).val();
					testRepoDetail.passPhrase = $("#testrepoPhrase_"+dynid).val();
					testRepoDetail.serverPath = $("#addTestTfsServerPath_"+dynid).val();
					repoInfo.testRepoDetail = testRepoDetail;
				}
				repoInfo.splitPhresco = $("#splitDotPhresco_"+dynid).is(":checked");
				repoInfo.splitTest = $("#splitTest_"+dynid).is(":checked");
				actionBody.repoInfo = repoInfo;
				actionBody.appdirname = obj.parent("div").attr("appDirName");
				actionBody.appid = obj.parent("div").attr("appId");
				actionBody.projectid = obj.parent("div").attr("projectId");
				
				action = "repoget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(actionBody, action), '', function(response){
					if (response.exception === null) {
						$("#addRepo_"+dynid).hide();
						self.addZindex();
					}
					commonVariables.hideloading = false;
				});
			}
		},
		
		addCommitEvent : function(obj, dynid){
			var self = this;
			var commitdata = {}, action;
			if (!self.validateCommitData(dynid)) {
				self.showBtnLoading("button[name='commitbtn'][id='"+dynid+"']");
				var repoInfo = {};
				var srcRepoDetail = {};
				srcRepoDetail.type = $("#commitType_"+dynid).val();
				srcRepoDetail.repoUrl = $("#commitRepourl"+dynid).val();
				srcRepoDetail.userName = $("#commitUserName"+dynid).val();
				srcRepoDetail.password = $("#commitPassword"+dynid).val();
				srcRepoDetail.commitMessage = $("#commitMessage_"+dynid).val();
				srcRepoDetail.passPhrase = $(".commitPassPhraseval"+dynid).val();
				var arrayCommitableFiles = [], srcTfsAddedFiles = [], srcTfsEditedFiles = [];
				$.each($('.commitChildChk[dynamicId='+dynid+'][from=src]') , function(index, value) {
					if ("tfs" === srcRepoDetail.type && $(this).is(':checked')) {
						self.getTFSCommitableFiles($(this), srcTfsAddedFiles, srcTfsEditedFiles);
					} else if ($(this).is(':checked')) {
						arrayCommitableFiles.push($(this).val());
					}
				});
				srcRepoDetail.commitableFiles = arrayCommitableFiles;
				srcRepoDetail.tfsAddedFiles = srcTfsAddedFiles;
				srcRepoDetail.tfsEditedFiles = srcTfsEditedFiles;
				repoInfo.srcRepoDetail = srcRepoDetail;
				
				var phrescoRepoDetail = {};
				if ($("#commitDotPhresco_"+dynid).is(":checked")) {
					phrescoRepoDetail.type = $("#phrCommitType_"+dynid).val();
					phrescoRepoDetail.repoUrl = $("#phrCommitRepourl"+dynid).val();
					phrescoRepoDetail.userName = $("#phrCommitUserName"+dynid).val();
					phrescoRepoDetail.password = $("#phrCommitPassword"+dynid).val();
					phrescoRepoDetail.commitMessage = $("#phrCommitMessage_"+dynid).val();
					phrescoRepoDetail.passPhrase = $(".phrCommitPassPhraseval"+dynid).val();
					var phrCommitableFiles = [], phrTfsAddedFiles = [], phrTfsEditedFiles = [];
					$.each($('.commitChildChk[dynamicId='+dynid+'][from=phr]') , function(index, value) {
						if ("tfs" === phrescoRepoDetail.type && $(this).is(':checked')) {
							self.getTFSCommitableFiles($(this), phrTfsAddedFiles, phrTfsEditedFiles);
						} else if ($(this).is(':checked')) {
							phrCommitableFiles.push($(this).val());
						}
					});
					phrescoRepoDetail.commitableFiles = phrCommitableFiles;
					phrescoRepoDetail.tfsAddedFiles = phrTfsAddedFiles;
					phrescoRepoDetail.tfsEditedFiles = phrTfsEditedFiles;
					repoInfo.phrescoRepoDetail = phrescoRepoDetail;
				}
				
				var testRepoDetail = {};
				if ($("#commitTest_"+dynid).is(":checked")) {
					testRepoDetail.type = $("#testCommitType_"+dynid).val();
					testRepoDetail.repoUrl = $("#testCommitRepourl"+dynid).val();
					testRepoDetail.userName = $("#testCommitUserName"+dynid).val();
					testRepoDetail.password = $("#testCommitPassword"+dynid).val();
					testRepoDetail.commitMessage = $("#testCommitMessage_"+dynid).val();
					testRepoDetail.passPhrase = $(".testCommitPassPhraseval"+dynid).val();
					var testCommitableFiles = [], testTfsAddedFiles = [], testTfsEditedFiles = [];
					$.each($('.commitChildChk[dynamicId='+dynid+'][from=test]') , function(index, value) {
						if ("tfs" === testRepoDetail.type && $(this).is(':checked')) {
							self.getTFSCommitableFiles($(this), testTfsAddedFiles, testTfsEditedFiles);
						} else if ($(this).is(':checked')) {
							testCommitableFiles.push($(this).val());
						}
					});
					testRepoDetail.commitableFiles = testCommitableFiles;
					testRepoDetail.tfsAddedFiles = testTfsAddedFiles;
					testRepoDetail.tfsEditedFiles = testTfsEditedFiles;
					repoInfo.testRepoDetail = testRepoDetail;
				}
				repoInfo.splitPhresco = $("#commitDotPhresco_"+dynid).is(":checked");
				repoInfo.splitTest = $("#commitTest_"+dynid).is(":checked");
				commitdata.repoInfo = repoInfo;
				
				commitdata.appdirname = obj.parent("div").attr("appDirName");
				action = "commitget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(commitdata, action), $('#commitLoading_'+dynid), function(response){
					if (response.exception === null) {
						$("#commit"+dynid).hide();
						self.addZindex();
					}
					commonVariables.hideloading = false;
				});
			}
		},
		
		getTFSCommitableFiles : function(checkboxObj, tfsAddedFiles, tfsEditedFiles) {
			if ("add" === $(checkboxObj).attr("tfs")) {
				tfsAddedFiles.push($(checkboxObj).val());
				tfsEditedFiles.push($(checkboxObj).val());
			} else if ("edit" === $(checkboxObj).attr("tfs")) {
				tfsEditedFiles.push($(checkboxObj).val());
			}
		},

		checkForDependents : function(subModuleName, rootModule, callback) {
			var self = this, moduleData = {};
			moduleData.subModuleName = subModuleName;
			moduleData.rootModule = rootModule;
			self.projectListAction(self.getActionHeader(moduleData, 'checkForDependents'), "", function(response) {
				if (response !== null && response.data !== null)  {
					callback(response.data);
				}
			});
		},

		generateReportEvent : function(obj){
			var self = this, modulename = '';
			var reportdata = {}, actionBody, action;		
			var appDir =  obj.attr("appDirName");
			var dynamicId = obj.attr("appId");
			reportdata.data = obj.closest("form").serialize();
			
			modulename = self.isBlank(obj.attr("moduleName")) ? "" : obj.attr("moduleName");
			
			reportdata.moduleName = modulename;
			reportdata.appDirName = appDir;
			actionBody = reportdata;
			action = "generateReport";
			commonVariables.hideloading = true;
			self.projectListAction(self.getActionHeader(actionBody, action), $('#pdfReportLoading_'+dynamicId), function(response){
				if (response.service_exception !== null) {
				} else {
					self.getReportEvent(response, appDir, "All", dynamicId, modulename);
				}
				commonVariables.hideloading = false;
			});
		},
		
		getCommitableFiles : function(data, obj) {
			var self = this;
			var dynamicId = data.dynamicId;
			self.openccpl(obj, $(obj).attr('name'), '');
			
			$('#commitLoading_'+dynamicId).show();
			//fix the scroll
			$('.fixedscroll').css("height", "250px");
			$('.fixedscroll').css("overflow-y", "auto");
			//end of the scroll
			commonVariables.hideloading = true;
	      	self.projectListActionForScm(self.getActionHeader(data, "getCommitableFiles"), $('#commitLoading_'+dynamicId), function(response) {
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				if ($(obj).offset().top > halfheight && $(obj).offset().left > halfwidth){
					var temp = $(obj).attr('name');
					if(response.data.srcRepoDetail) {
						var temp2 = $(obj).offset().top - 460 ;
						$("#"+temp).css('top',temp2);
					} else {
						var temp2 = $(obj).offset().top - 225;
						$("#"+temp).css('top',temp2);
					}	
				}
	      		$("#dummyCommit_"+dynamicId).css("height","0");
	      		
	      		$('#commit'+dynamicId).find(".repository_tabdiv").hide();
				$('#commit'+dynamicId).find("#myTabContent").hide();
				$('.commitErr_'+dynamicId).hide();
				
				var responseData = response.data;
				var srcRepoDetail = responseData.srcRepoDetail;
				
				if (srcRepoDetail === undefined || srcRepoDetail === null || !srcRepoDetail.repoExist) {
					$('.commitErr_'+dynamicId).show();
				} else {
					$('#commit'+dynamicId).find(".repository_tabdiv").show();
					$('#commit'+dynamicId).find("#myTabContent").show();
					
					self.showHideCommitAppCtrls(srcRepoDetail.type, dynamicId);
					
					$('#commitRepourl'+dynamicId).val(srcRepoDetail.repoUrl);
					$("#commitType_"+dynamicId).find('option').each(function(index, value){
						if(srcRepoDetail.type === $(value).val()){
							$(value).attr('selected', 'selected');
						}
					});
	
				if($("#commitType_" + dynamicId).val() !== 'svn'){
					$("#commitUserName"+dynamicId).val("").removeAttr('readonly');
					$("#commitPassword"+dynamicId).val("").removeAttr('readonly');
					$("#phrCommitUserName"+dynamicId).val("").removeAttr('readonly');
					$("#phrCommitPassword"+dynamicId).val("").removeAttr('readonly');
					$("#testCommitUserName"+dynamicId).val("").removeAttr('readonly');
					$("#testCommitPassword"+dynamicId).val("").removeAttr('readonly');
				}
				if ((srcRepoDetail.repoInfoFile !== undefined && srcRepoDetail.repoInfoFile !== null && srcRepoDetail.repoInfoFile.length > 0) || "tfs" === srcRepoDetail.type) {
						$('.srcCommitableFiles').show();
						self.constructCommitableFiles(dynamicId, srcRepoDetail, $('.commitable_files_'+dynamicId), "src");
						self.checkBoxEvent($('.commitParentChk[dynamicId='+dynamicId+']'), 'commitChildChk[dynamicId='+dynamicId+'][from=src]', $('input[name=commitbtn][id='+dynamicId+']'));
						self.checkAllEvent('.commitParentChk[dynamicId='+dynamicId+']', 'commitChildChk[dynamicId='+dynamicId+'][from=src]', $('input[name=commitbtn][id='+dynamicId+']'));
					}
					
					var phrescoRepoDetail = responseData.phrescoRepoDetail;
					if (phrescoRepoDetail !== null && phrescoRepoDetail !== undefined && responseData.splitPhresco) {
						$('#commitDotPhresco_'+dynamicId).attr("checked", true);
						$("#commitDotPhresco_"+dynamicId).attr("disabled", false);
						$("#commit" + dynamicId).find(".commitDotPhrescoA").attr("data-toggle", "tab").attr("href", "#commitDotphresco"+dynamicId);
						$('#phrCommitRepourl'+dynamicId).val(phrescoRepoDetail.repoUrl);
						$("#phrCommitType_"+dynamicId).find('option').each(function(index, value){
							if(phrescoRepoDetail.type === $(value).val()){
								$(value).attr('selected', 'selected');
							}
						});
						
						if ((phrescoRepoDetail.repoInfoFile !== undefined && phrescoRepoDetail.repoInfoFile !== null && phrescoRepoDetail.repoInfoFile.length > 0) || "tfs" === phrescoRepoDetail.type) {
							$('.phrCommitableFiles').show();
							self.constructCommitableFiles(dynamicId, phrescoRepoDetail, $('.phrCommitable_files_'+dynamicId), "phr");
							self.checkBoxEvent($('.phrCommitParentChk[dynamicId='+dynamicId+']'), 'commitChildChk[dynamicId='+dynamicId+'][from=phr]', $('input[name=commitbtn][id='+dynamicId+']'));
							self.checkAllEvent('.phrCommitParentChk[dynamicId='+dynamicId+']', 'commitChildChk[dynamicId='+dynamicId+'][from=phr]', $('input[name=commitbtn][id='+dynamicId+']'));
						}
					}
					else{						
						$('#commitDotPhresco_'+dynamicId).attr("checked", false);
						$("#commit" + dynamicId).find(".commitDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					}
					var testRepoDetail = responseData.testRepoDetail;
					if (testRepoDetail !== null && testRepoDetail !== undefined && responseData.splitTest) {
						$('#commitTest_'+dynamicId).attr("checked", true);
						$("#commitTest_"+dynamicId).attr("disabled", false);
						$("#commit" + dynamicId).find(".commitTestA").attr("data-toggle", "tab").attr("href", "#commitTest"+dynamicId);
						$('#testCommitRepourl'+dynamicId).val(testRepoDetail.repoUrl);
						$("#testCommitType_"+dynamicId).find('option').each(function(index, value){
							if(testRepoDetail.type === $(value).val()){
								$(value).attr('selected', 'selected');
							}
						});
						
						if ((testRepoDetail.repoInfoFile !== undefined && testRepoDetail.repoInfoFile !== null && testRepoDetail.repoInfoFile.length > 0) || "tfs" === testRepoDetail.type) {
							$('.testCommitableFiles').show();
							self.constructCommitableFiles(dynamicId, testRepoDetail, $('.testCommitable_files_'+dynamicId), "test");
							self.checkBoxEvent($('.tesCommitParentChk[dynamicId='+dynamicId+']'), 'commitChildChk[dynamicId='+dynamicId+'][from=test]', $('input[name=commitbtn][id='+dynamicId+']'));
							self.checkAllEvent('.tesCommitParentChk[dynamicId='+dynamicId+']', 'commitChildChk[dynamicId='+dynamicId+'][from=test]', $('input[name=commitbtn][id='+dynamicId+']'));
						}
					}
					else{
						$('#commitTest_'+dynamicId).attr("checked", false);
						$("#commit" + dynamicId).find(".commitTestA").removeAttr("data-toggle").removeAttr("href");
					}
				}
				commonVariables.hideloading = false;
			});
		},
		
		constructCommitableFiles : function(dynamicId, repoDetail, tbodyObj, from) {
			var self = this;
			var commitableFiles = '';
			if ("tfs" === repoDetail.type) { 
				self.constructTFSCommitableFiles(dynamicId, repoDetail, tbodyObj, from);
			}  else {
				$.each(repoDetail.repoInfoFile, function(index, value) {
					commitableFiles += '<tr><td style="vertical-align: top;"><input checkVal="check" dynamicId="'+ dynamicId +'" class="commitChildChk" from="'+from+'" type="checkbox" value="' + value.commitFilePath + '"></td>';
					commitableFiles += '<td style="width:280px !important;" title="'+ value.commitFilePath +'">"' + value.commitFilePath + '"</td>';
					commitableFiles += '<td style="vertical-align: top;text-align: center;">"' + value.status + '"</td></tr>';
				});
				tbodyObj.html(commitableFiles);
			}
		},

		constructTFSCommitableFiles : function (dynamicId, repoDetail, tbodyObj, from) {
			var self = this;
			var commitableFiles = '';
			if (repoDetail.tfsAddedFiles !== null && repoDetail.tfsAddedFiles !== undefined && repoDetail.tfsAddedFiles.length > 0) {
				$.each(repoDetail.tfsAddedFiles, function(index, addedFile) {
					commitableFiles += '<tr><td style="vertical-align: top;"><input checkVal="check" tfs="add" dynamicId="'+ dynamicId +'" class="commitChildChk" from="'+from+'" type="checkbox" value="' + addedFile + '"></td>';
					commitableFiles += '<td style="width:280px !important;" title="'+ addedFile +'">"' + addedFile + '"</td>';
					commitableFiles += '<td style="vertical-align: top;text-align: center;"> ? </td></tr>';
				});
			} 
			if (repoDetail.tfsEditedFiles !== null && repoDetail.tfsEditedFiles !== undefined && repoDetail.tfsEditedFiles.length > 0) {
				$.each(repoDetail.tfsEditedFiles, function(index, editedFile) {
					commitableFiles += '<tr><td style="vertical-align: top;"><input checkVal="check" tfs="edit" dynamicId="'+ dynamicId +'" class="commitChildChk" from="'+from+'" type="checkbox" value="' + editedFile + '"></td>';
					commitableFiles += '<td style="width:280px !important;" title="'+ editedFile +'">"' + editedFile + '"</td>';
					commitableFiles += '<td style="vertical-align: top;text-align: center;"> M </td></tr>';
				});
			} 
			tbodyObj.html(commitableFiles);
		}, 

		getUpdatableFiles : function(data, obj) {
			var self = this;
			var dynamicId = data.dynamicId;
			self.openccpl(obj, $(obj).attr('name'), '');
			//fix the scroll
			$('.fixedscroll').css("height", "250px");
			$('.fixedscroll').css("overflow-y", "auto");
			//end of the scroll
			$('#updateLoading_'+dynamicId).show();
			commonVariables.hideloading = true;
	      	self.projectListActionForScm(self.getActionHeader(data, "getUpdatableFiles"), $('#updateLoading_'+dynamicId), function(response) {
				// End of Type Selection
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				// if ($(obj).offset().top > halfheight && $(obj).offset().left > halfwidth){
				// 	var temp = $(obj).attr('name');
				// 	if(response.responseCode === 'PHR200022') {
				// 		var temp2 = $(obj).offset().top - 389;
				// 		$("#"+temp).css('top',temp2);
				// 	} else {
				// 		var temp2 = $(obj).offset().top - 218;
				// 		$("#"+temp).css('top',temp2);
				// 	}	
				// } 
	      		$("#dummyUpdate_"+dynamicId).css("height","0");
	      		
				$('#svn_update'+dynamicId).find(".repository_tabdiv").hide();
				$('#svn_update'+dynamicId).find("#myTabContent").hide();
				$('.updateErr_'+dynamicId).hide();
				
				var responseData = response.data;
				var srcRepoDetail = responseData.srcRepoDetail;
				if (srcRepoDetail === undefined || srcRepoDetail === null || !srcRepoDetail.repoExist) {
					$('.updateErr_'+dynamicId).show();
				} else {
					self.showHideUpdateAppCtrls(srcRepoDetail.type, dynamicId);
					$('#updateRepourl'+dynamicId).val(srcRepoDetail.repoUrl);
					// Type Selection based on response
					$("#updateType"+dynamicId).find('option').each(function(index, value) {
						if (srcRepoDetail.type === $(value).val()){
							$(value).attr('selected', 'selected');
						}
					});
					
					$('#svn_update'+dynamicId).find(".repository_tabdiv").show();
					$('#svn_update'+dynamicId).find("#myTabContent").show();
					
					var phrescoRepoDetail = responseData.phrescoRepoDetail;
					    
					if (responseData.splitPhresco && phrescoRepoDetail !== null && phrescoRepoDetail !== undefined) {
					    
					    $('#updateDotPhresco_'+dynamicId).attr("checked", true);						
						$("#updateDotPhresco_"+dynamicId).attr("disabled", false);
						$("#svn_update" + dynamicId).find(".updateDotPhrescoA").attr("data-toggle", "tab").attr("href", "#updateDotphresco"+dynamicId);
						$('#updatePhrescoRepourl'+dynamicId).val(phrescoRepoDetail.repoUrl);
						$("#updatePhrescoType"+dynamicId).find('option').each(function(index, value){
							if(phrescoRepoDetail.type === $(value).val()){
								$(value).attr('selected', 'selected');
							}
						});
					}
					else{						
						$('#updateDotPhresco_'+dynamicId).attr("checked", false);
				        $("#svn_update" + dynamicId).find(".updateDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					}
					var testRepoDetail = responseData.testRepoDetail;
					if (responseData.splitTest && testRepoDetail !== null && testRepoDetail !== undefined) {
						
						$('#updateTest_'+dynamicId).attr("checked", true);
						$("#updateTest_"+dynamicId).attr("disabled", false);
						$("#svn_update" + dynamicId).find(".updateTestA").attr("data-toggle", "tab").attr("href", "#updateTest"+dynamicId);
						$('#updateTestRepourl'+dynamicId).val(testRepoDetail.repoUrl);
						$("#testUpdateType"+dynamicId).find('option').each(function(index, value){
							if(testRepoDetail.type === $(value).val()){
								$(value).attr('selected', 'selected');
							}
						});
					}
					else{						
						$('#updateDotPhresco_'+dynamicId).attr("checked", false);
						$("#svn_update" + dynamicId).find(".updateTestA").removeAttr("data-toggle").removeAttr("href");
					}
				}
				commonVariables.hideloading = false;
			});
		},
		
		getReportEvent : function(obj, appDir, fromPage, dynamicId, modName){
			var self = this;
			$(".pdfheight").hide();
			var getreportdata = {}, actionBody, action, temp, modulename = '';	
			if (fromPage !== undefined && fromPage !== null) {
				getreportdata.fromPage = fromPage;
			} else {
				getreportdata.fromPage = obj.attr("fromPage");
			}
			
			if (appDir !== undefined && appDir !== null) {
				getreportdata.appDir = appDir;
				temp = appDir;
			} else {
				temp  = obj.closest("tr").attr("class");
				getreportdata.appDir = temp;
			}
			
			// if module is empty then it might be root app or called direclyt for list
			if (obj instanceof jQuery) {
				modulename = self.isBlank(obj.closest("tr").attr("moduleName")) ? "" : obj.closest("tr").attr("moduleName");
			} else {
				modulename = modName;
			}
			
			getreportdata.moduleName = modulename;
			actionBody = getreportdata;
			action = "getreport";
			if (dynamicId === undefined || dynamicId === null) {
				dynamicId = obj.attr("dynamicId");
			}
			
			commonVariables.hideloading = true;
			self.projectListAction(self.getActionHeader(actionBody, action), $("#pdfReportLoading_"+dynamicId), function(response) {
				 if(fromPage !== 'All') {
						var halfheight= window.innerHeight/2;
						var halfwidth= window.innerWidth/2;
						if ($(obj).offset().top > halfheight && $(obj).offset().left > halfwidth){
							var nameval = $(obj).attr('name');
							if(response.data.json.length === 0) {
								var temp2 = $(obj).position().top - 196;
								$("#"+nameval).css('top',temp2);
							} else {			
								var temp2 = $(obj).position().top - 262;
								$("#"+nameval).css('top',temp2);
							}	
						} 
						$.each(response.data,function(index,value) {
							if(index === 'value') {
								if(value === false) {
										$('input[name="generate"]').attr('disabled','disabled');
								}
							}
						});	
				}
				self.listPdfReports(response, temp, dynamicId, modulename);
				self.clickFunction(dynamicId);
				commonVariables.hideloading = false;
				$(".tooltiptop").tooltip();
			});
		},
		
		//To list the generated PDF reports
		listPdfReports : function(response, temp, dynamicId, modulename) {
			var self = this;
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			var content = "";
			$("tbody[name=generatedPdfs_"+dynamicId+"]").empty();
			if (response !== undefined && response !== null && response.data.json.length > 0) {
				$("#noReport_"+dynamicId).addClass("hideContent");
				$("#noReport_"+dynamicId).hide();
				$("table[name=pdfHeader_"+dynamicId+"]").removeClass("hideContent");
				$(".pdfheight").show();
				$("table[name=pdfHeader_"+dynamicId+"]").show();
				for(var i =0; i < response.data.json.length; i++) {
					var idgenerate = Date.now();
					var repname = response.data.json[i].fileName;
					var tooltipname = self.tooltip(repname);
					var headerTr = '<tr class="generatedRow" fileName="'+response.data.json[i].fileName+'" appdirname = "'+temp+'" moduleName="'+ modulename +'"><td><a class="tooltiptop" title="" data-placement="bottom" data-toggle="tooltip" href="javascript:void(0)" data-original-title="'+response.data.json[i].fileName+'" style="width:41% !important;">' + tooltipname +'</a></td><td>'+response.data.json[i].time + '</td><td>'+'</td>';
					content = content.concat(headerTr);
					headerTr = '<td class="list_img"><a class="tooltiptop" fileName="'+response.data.json[i].fileName+'" fromPage="All" href="javascript:void(0)" data-toggle="tooltip" data-placement="top" name="downLoad" data-original-title="Download Pdf" ><img src="themes/default/images/Phresco/download_icon.png" class="imgalign" ></a></td>';
					content = content.concat(headerTr);
					if(userPermissions.managePdfReports) {
						headerTr = '<td class="list_img"><a class="tooltiptop" name="deletepdf_'+idgenerate+i+'" fileName="'+response.data.json[i].fileName+'" fromPage="All" href="javascript:void(0)" data-toggle="tooltip" data-placement="top" namedel="delete" data-original-title="Delete Pdf" title=""><img src="themes/default/images/Phresco/delete_row.png" class="imgaligndel" width="14" height="18" border="0" alt="0"></a><div style="display:none;" id="deletepdf_'+idgenerate+i+'" class="delete_msg tohide">Are you sure to delete ?<div><input type="button" value="Yes" data-i18n="[value]common.btn.yes" class="btn btn_style dlt" name="delpdf"><input type="button" value="No" data-i18n="[value]common.btn.no" class="btn btn_style dyn_popup_close"></div></div></td></tr>';
					} else {
						headerTr = '<td class="list_img"><a class="tooltiptop" fileName="'+response.data.json[i].fileName+'" fromPage="All" href="javascript:void(0)" data-toggle="tooltip" data-placement="top" data-original-title="Delete Pdf" title=""><img src="themes/default/images/Phresco/delete_row_off.png" width="14" height="18" border="0" alt="0"></a></td></tr>';
					}
					content = content.concat(headerTr);
				}
				$("tbody[name=generatedPdfs_"+dynamicId+"]").append(content);
			} else {
				$(".pdfheight").hide();
				$("table[name=pdfHeader_"+dynamicId+"]").hide();
				$("#noReport_"+dynamicId).removeClass("hideContent");
				$("#noReport_"+dynamicId).show();
				$("#noReport_"+dynamicId).html("No Reports are Available");
			}
		},
		
		clickFunction : function(dynamicId){
			var self = this;
			$("a[namedel=delete]").click(function() {
				var temp = $(this).attr('name');
				self.openccpl(this, $(this).attr('name'));
				//fix the scroll
				$('.fixedscroll').css("height", "250px");
				$('.fixedscroll').css("overflow-y", "auto");
				//end of the scroll
				$('#'+temp).show();
				var deletedata = {}, actionBody = {}, action;
				deletedata.fileName = $(this).attr("fileName");
				deletedata.fromPage = $(this).attr("frompage");
				deletedata.appDir = $(this).closest('tr').attr("appdirname");
				deletedata.moduleName = self.isBlank($(this).closest("tr").attr("moduleName")) ? "" : $(this).closest("tr").attr("moduleName");
				$('input[name="delpdf"]').unbind();
				$('input[name="delpdf"]').click(function() {
					
					actionBody = deletedata;  
					self.projectListAction(self.getActionHeader(actionBody, "deleteReport"), "", function(response){
						$(".generatedRow").each(function() {
							var pdfFileName = $(this).attr("fileName");
							if(pdfFileName === deletedata.fileName){
								$("tr[fileName='"+pdfFileName+"']").remove();
							}
						});
						
						var size = $(".generatedRow").size();
						if(size === 0) {
							$("table[name=pdfHeader_"+dynamicId+"]").hide();
							$("#noReport_"+dynamicId).removeClass("hideContent");
							$("#noReport_"+dynamicId).show();
							$("#noReport_"+dynamicId).html("No Reports are Available");
						}
					});  
				});	
			});
			
			$("a[name=downLoad]").click(function() {
				var actionBody = {};
				actionBody.fileName = $(this).attr("fileName");
				actionBody.fromPage = $(this).attr("frompage");
				actionBody.appDir = $(this).closest('tr').attr("appdirname");
				
				actionBody.moduleName = self.isBlank($(this).closest("tr").attr("moduleName")) ? "" : $(this).closest("tr").attr("moduleName");
				var moduleParam = self.isBlank(actionBody.moduleName) ? "" : '&moduleName='+actionBody.moduleName;
				
				var pdfDownloadUrl = commonVariables.webserviceurl + "pdf/downloadReport?appDirName="+actionBody.appDir+"&reportFileName="+actionBody.fileName+"&fromPage="+actionBody.fromPage + moduleParam;
				if(commonVariables.animation)
				window.open(pdfDownloadUrl, '_self');
			});
			
			$("input[name=pdfName]").unbind('input');
			$("input[name=pdfName]").bind('input', function(){
				$(this).val(self.specialCharValidation($(this).val().replace(/\s/g, "")));
			});
		},
		
		addUpdateEvent : function(obj, dynid) {
			var self = this;
			if (!self.validateUpdateData(dynid)) {
				self.showBtnLoading("button[name='updatebtn'][id='"+dynid+"']");
				var repoInfo = {}, actionBody = {}, action;
				repoInfo.srcRepoDetail = self.getUpdateSrcRepoDetail(dynid);
				if ($('#updateDotPhresco_'+dynid).is(":checked")) {
					repoInfo.phrescoRepoDetail = self.getUpdatePhrescoRepoDetail(dynid);
					repoInfo.splitPhresco = true;
				}
				if ($('#updateTest_'+dynid).is(":checked")) {
					repoInfo.testRepoDetail = self.getUpdateTestRepoDetail(dynid);
					repoInfo.splitTest = true;
				}
				
				actionBody.appdirname = obj.parent("div").attr("appDirName");
				actionBody.repoInfo = repoInfo;
				action = "updateget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(actionBody, action), $("#updateRepoLoading_"+dynid), function(response){
					self.hideBtnLoading("button[name='updatebtn'][id='"+dynid+"']");
					if (response.exception === null) {
						$("#svn_update"+dynid).hide();
						self.addZindex();
					}
					commonVariables.hideloading = false;
				});
			}
		},
		
		getUpdateSrcRepoDetail : function(dynid) {
			var repoDetail = {};
			var headOptionName = "updatePhrescoHeadoption"+dynid;
			var revision = $("input[name='updateHeadoption"+dynid+"']:checked").val();
			if (revision === "revision") {
				revision = $("#updateRevision"+dynid).val();
			} else{
				revision = revision;
			}
			var repoType = $("#updateType"+dynid).val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#updateRepourl"+dynid).val();
			
			if ('svn' === repoType || 'tfs' === repoType) {
				repoDetail.userName = $("#updateUserName"+dynid).val();
				repoDetail.password = $("#updatePassword"+dynid).val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".updateBranchval"+dynid).val();
				repoDetail.userName = $("#updateGitUserName"+dynid).val();
				repoDetail.password = $("#updateGitPassword"+dynid).val();
				repoDetail.passPhrase = $(".updatePassPhraseval"+dynid).val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.updateStream'+dynid).val();
			}
			return repoDetail;
		},
		
		getUpdatePhrescoRepoDetail : function(dynid) {
			var repoDetail = {};
			var headOptionName = "updatePhrescoHeadoption"+dynid;
			var revision = $("input[name='"+headOptionName+"']:checked").val();
			if (revision === "revision") {
				revision = $("#updatePhrescoRevision"+dynid).val();
			} else{
				revision = revision;
			}
			var repoType = $("#updatePhrescoType"+dynid).val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#updatePhrescoRepourl"+dynid).val();
			
			if ('svn' === repoType || 'tfs' === repoType) {
				repoDetail.userName = $("#updatePhrescoUserName"+dynid).val();
				repoDetail.password = $("#updatePhrescoPassword"+dynid).val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".updatePhrescoBranchval"+dynid).val();
				repoDetail.userName = $("#updatePhrescoGitUserName"+dynid).val();
				repoDetail.password = $("#updatePhrescoGitPassword"+dynid).val();
				repoDetail.passPhrase = $(".updatePhrescoPassPhraseval"+dynid).val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.updateStream'+dynid).val();
			}
			return repoDetail;
		},
		
		getUpdateTestRepoDetail : function(dynid) {
			var repoDetail = {};
			var headOptionName = "testUpdateHeadoption"+dynid;
			var revision = $("input[name='"+headOptionName+"']:checked").val();
			if (revision === "revision") {
				revision = $("#testUpdateRevision"+dynid).val();
			} else{
				revision = revision;
			}
			var repoType = $("#testUpdateType"+dynid).val()
			repoDetail.type = repoType;
			repoDetail.repoUrl = $("#updateTestRepourl"+dynid).val();
			
			if ('svn' === repoType || 'tfs' === repoType) {
				repoDetail.userName = $("#updateTestUserName"+dynid).val();
				repoDetail.password = $("#updateTestPassword"+dynid).val();
				repoDetail.revision = revision;
			} else {
				repoDetail.branch = $(".testUpdateBranchval"+dynid).val();
				repoDetail.userName = $("#testUpdateGitUserName"+dynid).val();
				repoDetail.password = $("#testUpdateGitPassword"+dynid).val();
				repoDetail.passPhrase = $(".testUpdatePassPhraseval"+dynid).val();
			}
			
			if ('perforce' === repoType) {
				repoDetail.stream = $('.testUpdateStream'+dynid).val();
			}
			return repoDetail;
		},
		
		hideShowCredentials : function(val, usrObj, pwdObj, checkObj){
			if(val === 'svn') {
				$(".seperatetdSvn").parent().show();
				$(".seperatetdSvn").show();
				if(!checkObj.is(':checked')) {
					usrObj.attr('readonly','true');
					pwdObj.attr('readonly','true');
				}
			} else {
				$(".seperatetdSvn").parent().hide();
				$(".seperatetdSvn").hide();
				usrObj.removeAttr('readonly');
				pwdObj.removeAttr('readonly');
			}			
		},
		
		validateCommitData : function(dynamicId) {
			var self = this;
			var hasError = false;
			
			hasError = commonVariables.navListener.validateSvnData($("#commitRepourl"+dynamicId), $("#commitUserName"+dynamicId), $('#commitPassword'+dynamicId),$("#commitType_"+dynamicId));
			if (hasError) {
				commonVariables.navListener.showSrcTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
			}
			if ($('#commitDotPhresco_'+dynamicId).is(":checked") && !hasError) {
				hasError = commonVariables.navListener.validateSvnData($('#phrCommitRepourl'+dynamicId), $('#phrCommitUserName'+dynamicId), $('#phrCommitPassword'+dynamicId),$("#phrCommitType_"+dynamicId));
				if (hasError) {
					commonVariables.navListener.showDotPhrescoTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
				}
			}
			if ($('#commitTest_'+dynamicId).is(":checked") && !hasError) {
				hasError = commonVariables.navListener.validateSvnData($('#testCommitRepourl'+dynamicId), $('#testCommitUserName'+dynamicId), $('#testCommitPassword'+dynamicId),$("#testCommitType"+dynamicId));
				if (hasError) {
					commonVariables.navListener.showTestTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
				}
			}
			return hasError;
		},
		
		validatedotPhrescoAndTest : function(dynamicId, repourl, uname, pwd,type){
			var self = this;
			var hasError = false;
			hasError = commonVariables.navListener.validateSvnData($("#"+repourl+"_"+dynamicId), $("#"+uname+"_"+dynamicId), $("#"+pwd+"_"+dynamicId),$("#"+type+"_"+dynamicId));
			return hasError;
		
		},
		
		validateAddToRepoData : function(dynamicId) {
			var self = this;
			var hasError = false;
			
			hasError = commonVariables.navListener.validateSvnData($("#repourl_"+dynamicId), $("#uname_"+dynamicId), $('#pwd_'+dynamicId),$("#type_"+dynamicId));
			if (hasError) {
				commonVariables.navListener.showSrcTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
			}
			if ($('#splitDotPhresco_'+dynamicId).is(":checked") && !hasError) {
				hasError = commonVariables.navListener.validateSvnData($('#phrescorepourl_'+dynamicId), $('#phrescouname_'+dynamicId), $('#phrescopwd_'+dynamicId),$("#phrescotype_"+dynamicId));
				if (hasError) {
					commonVariables.navListener.showDotPhrescoTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
				}
			}
			if ($('#splitTest_'+dynamicId).is(":checked") && !hasError) {
				hasError = commonVariables.navListener.validateSvnData($('#testrepourl_'+dynamicId), $('#testuname_'+dynamicId), $('#testpwd_'+dynamicId),$("#testtype_"+dynamicId));
				if (hasError) {
					commonVariables.navListener.showTestTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
				}
			}
			return hasError;
		},
		
		validateUpdateData : function(dynamicId) {
			var self = this;
			var hasError = false;
			var updateType = $("#updateType"+dynamicId).val();
			if (updateType === "git") {
				hasError = commonVariables.navListener.validateGitData($('#updateRepourl'+dynamicId));
				if (hasError) {
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				}
				if ($('#updateDotPhresco_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateGitData($('#updatePhrescoRepourl'+dynamicId), $("#updateTest"+dynamicId));
					if (hasError) {
						commonVariables.navListener.showDotPhrescoTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
				if ($('#updateTest_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateGitData($('#updateTestRepourl'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showTestTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			}
			if (!hasError && updateType === "svn") {
				hasError = commonVariables.navListener.validateSvnData($('#updateRepourl'+dynamicId), $('#updateUserName'+dynamicId), $('#updatePassword'+dynamicId),$('#updateType'+dynamicId), $('input[name=updateHeadoption'+dynamicId+']:checked'), $('#updateRevision'+dynamicId));
				if (hasError) {
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				}
				if ($('#updateDotPhresco_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateSvnData($('#updatePhrescoRepourl'+dynamicId), $('#updatePhrescoUserName'+dynamicId), $('#updatePhrescoPassword'+dynamicId),$('#updatePhrescoType'+dynamicId), $('input[name=updatePhrescoHeadoption'+dynamicId+']:checked'), $('#updatePhrescoRevision'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showDotPhrescoTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
				if ($('#updateTest_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateSvnData($('#updateTestRepourl'+dynamicId), $('#updateTestUserName'+dynamicId), $('#updateTestPassword'+dynamicId),$('#testUpdateType'+dynamicId),$('input[name=testUpdateHeadoption'+dynamicId+']:checked'), $('#testUpdateRevision'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showTestTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			}
			if (!hasError && updateType === "perforce") {
				hasError = commonVariables.navListener.validatePerforceData($('#updateRepourl'+dynamicId), $('.updateStream'+dynamicId));
				if (hasError) {
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				}
				if ($('#updateDotPhresco_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validatePerforceData($('#updatePhrescoRepourl'+dynamicId), $('.updatePhrescoStream'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showDotPhrescoTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
				if ($('#updateTest_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validatePerforceData($('#updateTestRepourl'+dynamicId), $('.testUpdateStream'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showTestTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			}
			if (!hasError && updateType === "bitkeeper") {
				hasError = commonVariables.navListener.validateBitkeeperData($('#updateRepourl'+dynamicId));
				if (hasError) {
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				}
				if ($('#updateDotPhresco_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateBitkeeperData($('#updatePhrescoRepourl'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showDotPhrescoTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
				if ($('#updateTest_'+dynamicId).is(":checked") && !hasError) {
					hasError = commonVariables.navListener.validateBitkeeperData($('#updateTestRepourl'+dynamicId));
					if (hasError) {
						commonVariables.navListener.showTestTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			}
			return hasError;
		},
		
		trimValue: function (value) {
			var len = value.length;
			if(len > 50) {
			value = value.substr(0, 50) + "...";
			return value;
			}
			return value;
		},
		
		showHideUpdateAppCtrls : function(repoType, dynamicId) {
			if (repoType === "git") {
				$(".updateSvndata"+dynamicId).hide();
				$(".updatePerforcedata"+dynamicId).hide();
				$(".updateImportCredential"+dynamicId).hide();
				$(".updateGitdata"+dynamicId).show();
			}
			if (repoType === "svn") {
				$(".updateSvndata"+dynamicId).show();
				$(".updateImportCredential"+dynamicId).show();
				$(".updateGitdata"+dynamicId).hide();
				$(".updatePerforcedata"+dynamicId).hide();
			}
			if (repoType === "perforce") {
				$(".updatePerforcedata"+dynamicId).show();
				$(".updateSvndata"+dynamicId).hide();
				$(".updateGitdata"+dynamicId).hide();
				$(".updateImportCredential"+dynamicId).hide();
				$(".updateBitkeeperdata"+dynamicId).show();
			}
			if (repoType === "bitkeeper") {
				$(".updateSvndata"+dynamicId).hide();
				$(".updateGitdata"+dynamicId).hide();
				$(".updatePerforcedata"+dynamicId).hide();
				$(".updateImportCredential"+dynamicId).hide();
				$(".updateBitkeeperdata"+dynamicId).show();
			}
			if (repoType === "tfs") {
				$(".updateSvndata"+dynamicId).hide();
				$(".updateGitdata"+dynamicId).hide();
				$(".updatePerforcedata"+dynamicId).hide();
				$(".updateImportCredential"+dynamicId).hide();
				$(".updateBitkeeperdata"+dynamicId).hide();
				$(".updateImportCredential").hide();
				$(".updateTfsdata"+dynamicId).show();
				$("#updateUserName"+dynamicId).removeAttr("readonly").val("");
				$("#updatePassword"+dynamicId).removeAttr("readonly").val("");
				$("#updatePhrescoUserName"+dynamicId).removeAttr("readonly").val("");
				$("#updatePhrescoPassword"+dynamicId).removeAttr("readonly").val("");
				$("#updateTestUserName"+dynamicId).removeAttr("readonly").val("");
				$("#updateTestPassword"+dynamicId).removeAttr("readonly").val("");
			}
		},
		
		showHideCommitAppCtrls : function(repoType, dynamicId) {
			$(".commitTfsdata"+dynamicId).hide();
			$(".search").show();
			if (repoType === "git") {
				$(".commitGitdata"+dynamicId).show();
			}
			if (repoType === "tfs") {
				$(".commitTfsdata"+dynamicId).show();
				$(".search").hide();
			}
			if (repoType === "svn" || repoType === "bitkeeper" || repoType === "tfs") {
				$(".commitGitdata"+dynamicId).hide();
			}
		},
		
		addZindex : function () {
			$('.content_title').css('z-index', '6');
			$('.header_section').css('z-index', '7');
		}
	});

	return Clazz.com.components.projectlist.js.listener.ProjectsListListener;
});
