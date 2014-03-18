define(["projectlist/listener/projectListListener"], function() {
	
	Clazz.createPackage("com.components.projectlist.js");

	Clazz.com.components.projectlist.js.ProjectList = Clazz.extend(Clazz.WidgetWithTemplate, {
		projectsEvent : null,
		projectsHeader : null,
		templateUrl: commonVariables.contexturl + "components/projectlist/template/projectList.tmp",
		configUrl: "components/projectlist/config/config.json",
		name : commonVariables.projectlist,
		contentContainer : commonVariables.contentPlaceholder,
		projectslistListener : null,
		onProjectsEvent : null,
		projectRequestBody: {},
		data : null,
		onProjectEditEvent : null,
		registerEvents : null,
		repositoryEvent : null,
		onAddRepoEvent : null,
		onAddCommitEvent : null,
		onAddUpdateEvent : null,
		onAddReportEvent : null,
		onGetReportEvent : null,
		onDeleteSubModuleEvent : null,
		flagged: null,
		flag:null,
		delobj:null,
		deleteObj : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			if(self.projectslistListener === null){
				self.projectslistListener = new Clazz.com.components.projectlist.js.listener.ProjectsListListener;
			}
			self.registerHandlebars();
			self.registerEvents(self.projectslistListener);
		},

		/*** 
		 * Called in once the login is success
		 *
		 */
		loadPage :function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(this, commonVariables.animation);
		},
		
		
		registerEvents : function(projectslistListener,repositoryListener) {
			var self = this;
			if(self.onProjectsEvent === null) {
				self.onProjectsEvent = new signals.Signal();
			}
			if(self.onProjectEditEvent === null) {
				self.onProjectEditEvent = new signals.Signal();
			}
			self.onProjectEditEvent.add(projectslistListener.onEditProject, projectslistListener);			
			self.onProjectsEvent.add(projectslistListener.editApplication, projectslistListener);
			
			if(self.onAddRepoEvent === null) {
				self.onAddRepoEvent = new signals.Signal();
			}
			self.onAddRepoEvent.add(projectslistListener.addRepoEvent, projectslistListener);
			
			if(self.onAddCommitEvent === null) {
				self.onAddCommitEvent = new signals.Signal();
			}
			self.onAddCommitEvent.add(projectslistListener.addCommitEvent, projectslistListener);
			
			if(self.onAddUpdateEvent === null) {
				self.onAddUpdateEvent = new signals.Signal();
			}
			self.onAddUpdateEvent.add(projectslistListener.addUpdateEvent, projectslistListener);
			
			if(self.onAddReportEvent === null) {
				self.onAddReportEvent = new signals.Signal();
			}
			self.onAddReportEvent.add(projectslistListener.generateReportEvent, projectslistListener);
			
			if(self.onGetReportEvent === null) {
				self.onGetReportEvent = new signals.Signal();
			}
			self.onGetReportEvent.add(projectslistListener.getReportEvent, projectslistListener);
			
			if(self.onDeleteSubModuleEvent === null) {
				self.onDeleteSubModuleEvent = new signals.Signal();
			}
			self.onDeleteSubModuleEvent.add(projectslistListener.checkForDependents, projectslistListener);
		},
		registerHandlebars : function () {
			var self = this;
			Handlebars.registerHelper('descriptionshow', function(description) {
				var descri;
				if(description !== null){
					descri = description;
				}else{							
					descri = '&nbsp';
				}		
				return descri;
			});

			Handlebars.registerHelper('csvAppIds', function(appinfos) {
				var appIds = "";
				if (appinfos !== null && appinfos !== undefined && appinfos.length !== 0) {
					$.each(appinfos, function(index, appinfo){
						appIds += appinfo.id;
						if (index < appinfos.length - 1) {
							appIds += ",";
						}
					});
				}
				return appIds;
			});
			
			Handlebars.registerHelper('hasModules', function(modules) {
				if (modules !== undefined && modules !== null && modules.length > 0) {
					return true;
				} else {							
					return false;
				}		
			});

			Handlebars.registerHelper('subModules', function(modules) {
				if (modules !== undefined && modules !== null && modules.length > 0) {
					var moduleIds = [];
					$.each(modules, function(index, module) {
						moduleIds.push(module.id);
					});
					return moduleIds.join(',');
				} else {							
					return "";
				}		
			});

			Handlebars.registerHelper('allSubModuleIds', function(appInfos) {
				var moduleIds = [];
				if (appInfos !== undefined && appInfos !== null && appInfos.length > 0) {
					$.each(appInfos, function(index, appInfo) {
						if (appInfo.modules !== undefined && appInfo.modules !== null && appInfo.modules.length > 0) {
							$.each(appInfo.modules, function(index, module) {
								moduleIds.push(module.id);
							});
						}
					});
					return moduleIds.join(',');
				} else {							
					return "";
				}		
			});
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			$("#editprojecttitle").html("");
			commonVariables.navListener.currentTab = commonVariables.projectlist;
			commonVariables.navListener.showHideControls(commonVariables.projectlist);
		},

		preRender: function(whereToRender, renderFunction){
		 	var self = this;
			self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(self.projectRequestBody, "get"), function(response) {
				var projectlist = {};
				projectlist.projectlist = response.data;
				var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
				projectlist.userPermissions = userPermissions;
				renderFunction(projectlist, whereToRender);
			});
		},

		getAction : function(actionBody, action, callback) {
			var self = this;
			self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, action), "" , function(response) {
				console.info("after projectListAction........");
				if (response.responseCode === "PHRSR10007") {
					self.showCredentialsPopupForTfs();
				} else {
					if ("delete" === action) {
						callback();
					}				
					if(self.flagged!=1) {
						self.loadPage(commonVariables.animation);
					} else {
						self.flagged=1;
					}
				}
			});
		},
		
		//To show the credentials popup to delete the TFS workspace during deletion of the tfs application
		showCredentialsPopupForTfs : function() {
			var self = this;
			$('.modal-backdrop, #tfsRepoCredentials').remove();
			$(commonVariables.basePlaceholder).append('<div id="tfsRepoCredentials" class="modal fade errpopup hideContent" tabindex="-1"><div class="modal-body temp"><table><tr><td colspan="2"><span>Credentials to deletet the TFS workspace : </span></td></tr><tr><td><span>User Name</span><sup>*</sup></td><td><span>Password</span><sup>*</sup></td></tr><tr><td><input type="text" id="repoUsername" style="margin-right:10px"></td><td><input type="password" id="repoPassword"></td></tr></table></div><div class="modal-footer"><input type="button" id="submitCredentials" class="btn btn_style" value="Ok"><input type="button" data-dismiss="modal" value="Cancel" class="btn btn_style" id="closeCredPopup"></div></div>');
			$("#tfsRepoCredentials").modal("show");
			self.submitCredentials();
		},
		
		submitCredentials : function() {
			var self = this;
			$('#submitCredentials').unbind("click");
			$('#submitCredentials').bind("click", function() {
				$("#tfsRepoCredentials").modal("hide");
				self.deleteObj.userName = $("#repoUsername").val();
				self.deleteObj.password = $("#repoPassword").val();
				self.getAction(self.deleteObj, "delete", function(response) {
					commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal) {
						Clazz.navigationController.push(retVal, commonVariables.animation);
					});
				});
			});
		},
		
		deleteProjectfn : function(deletObj, imgname1, imgname2, deleteproject, flag_multi){
			var self = this;
			self.deleteObj = deleteObj;
			self.getAction(deletObj,"delete",function(response) {
				//commonVariables.loadingScreen.removeLoading();
				self.deleterow(imgname1, imgname2, deleteproject, flag_multi);
			});
		},
		
		deleterow : function(imgname1, imgname2, deleteproject, flag_multi){
			var self = this;
			if(flag_multi !== 1){
				if(imgname1 === 'delete'|| imgname2 === 'delete') {	
					$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").remove();
				} else if (imgname1 === 'module_delete') {
					deleteproject.remove();
				} else {
					$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").prev('tr').remove();
					$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").remove();
				}
				if(!($(commonVariables.contentPlaceholder).find('tr.proj_title').length)){
					self.flagged=0;	
				}
			} else {
				if(imgname1 === 'delete'|| imgname2 === 'delete') {	
					$(commonVariables.contentPlaceholder).find("tr[modulename="+deleteproject+"]").remove();
				} else if (imgname1 === 'module_delete') {
					deleteproject.remove();
				} else {
					$(commonVariables.contentPlaceholder).find("tr[modulename="+deleteproject+"]").prev('tr').remove();
					$(commonVariables.contentPlaceholder).find("tr[modulename="+deleteproject+"]").remove();
				}
				if(!($(commonVariables.contentPlaceholder).find('tr.proj_title').length)){
					self.flagged=0;	
				}
			}			
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){   
			var self = this;
			$(".tooltiptop").tooltip();
			$(".dyn_popup").hide();
			$(".pdf_popup").hide();
			
			$(".addToRepoHelp").unbind("click");
			$(".addToRepoHelp").click(function() {
				var dynamicId = $(this).attr("dynamicId");
				self.openAddToRepoHelp($(this), dynamicId);
			});
			
			$(document).keyup(function(e) {
				  if (e.keyCode == 27) { 
					 self.addZindex(); 
				  }   
			});
			
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var password = commonVariables.api.localVal.getSession('password');
			
			var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
			var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
			var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
			var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
			var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
			var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
			var w7 = $(".scrollContent tr:nth-child(2) td:nth-child(7)").width();
			
			$(".fixedHeader tr th:first-child").css("width",w1);
			$(".fixedHeader tr th:nth-child(2)").css("width",w2);
			$(".fixedHeader tr th:nth-child(3)").css("width",w3);
			$(".fixedHeader tr th:nth-child(4)").css("width",w4 + 13);
			$(".fixedHeader tr th:nth-child(5)").css("width",w5 - 10);
			$(".fixedHeader tr th:nth-child(6)").css("width",w6);
			$(".fixedHeader tr th:nth-child(7)").css("width",w7);
			
			$(window).resize(function() {
				var w1 = $(".scrollContent tr:nth-child(2) td:first-child").width();
				var w2 = $(".scrollContent tr:nth-child(2) td:nth-child(2)").width();
				var w3 = $(".scrollContent tr:nth-child(2) td:nth-child(3)").width();
				var w4 = $(".scrollContent tr:nth-child(2) td:nth-child(4)").width();
				var w5 = $(".scrollContent tr:nth-child(2) td:nth-child(5)").width();
				var w6 = $(".scrollContent tr:nth-child(2) td:nth-child(6)").width();
				var w7 = $(".scrollContent tr:nth-child(2) td:nth-child(7)").width();
				
				$(".fixedHeader tr th:first-child").css("width",w1);
				$(".fixedHeader tr th:nth-child(2)").css("width",w2);
				$(".fixedHeader tr th:nth-child(3)").css("width",w3);
				$(".fixedHeader tr th:nth-child(4)").css("width",w4);
				$(".fixedHeader tr th:nth-child(5)").css("width",w5);
				$(".fixedHeader tr th:nth-child(6)").css("width",w6);
				$(".fixedHeader tr th:nth-child(7)").css("width",w7);
				self.windowResize();
			});			
			
			$("#applicationedit").css("display", "none");
			$("#editprojectTab").css("display", "none");
			$("#editprojectTab li a").removeClass("act");
			
			$("img[name=editproject]").unbind("click");
			$("img[name=editproject]").click(function(){
				commonVariables.api.localVal.setSession('projectCode', $(this).attr('projectcode'));
				commonVariables.api.localVal.setSession('projectId', $(this).attr('projectId'));
				self.onProjectEditEvent.dispatch($(this).attr('key'), $(this).attr('projectId'));
				commonVariables.projectCode = commonVariables.api.localVal.getSession('projectCode');
				var actionBody = {};
				self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(actionBody, "checkMachine"), function(response) {
					commonVariables.api.localVal.setSession("checkMachine", JSON.stringify(response));
				});
			});	
			
			$("#myTab li a").removeClass("act");
			$('a[name=editApplication]').click(function(){
				var thisObj = this, appid = $(this).attr("appid");
				self.checkForLock("editAppln", appid, "", function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						$(".dyn_popup").hide();
						commonVariables.appDirName = $(thisObj).closest("tr").attr("class");
						var value = $(thisObj).closest("tr").attr("class");
						var techid = $(thisObj).closest("tr").attr("techid");
						var appName = $(thisObj).text();
						commonVariables.techId = techid;
						$("#myTab li#appinfo a").addClass("act");
						var from = $(thisObj).attr("from");
						var hasModules = Boolean($(thisObj).attr("modules"));
						commonVariables.editAppFrom = from;
						commonVariables.editAppHasModules = hasModules;
						var module = "multimodule" === from ?  $(thisObj).text() : "";
						self.onProjectsEvent.dispatch(value , techid, module, appName);
	
						//To show/hide openfolder, copy path icon and copy to clipboard
						var actionBody = {};
						self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(actionBody, "checkMachine"), function(response) {
							commonVariables.api.localVal.setSession("checkMachine", JSON.stringify(response));
						});
					} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
						commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
					}	
				});	
				commonVariables.projectCode = "";
			});

			$(".tooltiptop").unbind("click");
			$(".tooltiptop").click(function() {
				var counter;				
				var dotphresco;				
				var testsearch;				
				var obj_del = this;
				var action = $(this).attr("data-original-title");
				var currentPrjName = $(this).closest("tr").attr("class");
				var dynamicId = $(this).attr("dynamicId");
				$('input[name=generate]').addClass('btn_style').removeClass('disabled');
				$('input[name=generate]').removeAttr('disabled');
				//for hiding and clearing the dropdown values
				$('.searchdropdown').hide();
				$('.searchdropdown').html('');
				$('.dotphrescosearchdropdown').hide();
				$('.dotphrescosearchdropdown').html('');
				$('.testsearchdropdown').hide();
				$('.testsearchdropdown').html('');
				//end for hiding and clearing dropdown value
				if (action === "Add Repo") {
					self.hideBtnLoading("button[name='addrepobtn'][id='"+dynamicId+"']");
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					
					$('#splitDotPhresco_'+dynamicId).attr("checked", false);
					$('#splitTest_'+dynamicId).attr("checked", false);
					
					$("#repourl_"+dynamicId).val("");
					$("#phrescorepourl_"+dynamicId).val("");
					$("#testrepourl_"+dynamicId).val("");
					
					$('.addToRepocredential').attr("checked", false);
					$('.addToRepoPhrescocredential').attr("checked", false);
					$('.addToRepoTestCredential').attr("checked", false);
					
					if($("#repourl_"+dynamicId).hasClass('errormessage'))
						$("#repourl_"+dynamicId).removeClass('errormessage');
					
					$('#uname_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#pwd_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#phrescouname_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#phrescopwd_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#testuname_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#testpwd_'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					
					$("#addRepo_" + dynamicId).find(".dotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					$("#addRepo_" + dynamicId).find(".testA").removeAttr("data-toggle").removeAttr("href");
					
					$("#repoPhrase_"+dynamicId).val("");
					$("#phrescorepoPhrase_"+dynamicId).val("");
					$("#testrepoPhrase_"+dynamicId).val("");
					
					$('#uname_'+dynamicId).val(userInfo.id);
					$('#pwd_'+dynamicId).val(password);
					$('#phrescouname_'+dynamicId).val(userInfo.id);
					$('#phrescopwd_'+dynamicId).val(password);
					$('#testuname_'+dynamicId).val(userInfo.id);
					$('#testpwd_'+dynamicId).val(password);
				} else if (action === "Commit") {
					self.hideBtnLoading("button[name='commitbtn'][id='"+dynamicId+"']");
					commonVariables.navListener.showSrcTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
					
					$('#commitDotPhresco_'+dynamicId).attr("checked", false);
					$('#commitTest_'+dynamicId).attr("checked", false);
					
					$('.cmtSrcOtherCredential').attr("checked", false);
					$('.phrCmtSrcOtherCredential').attr("checked", false);
					$('.testCmtSrcOtherCredential').attr("checked", false);
					
					$('#commitUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#commitPassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#phrCommitUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#phrCommitPassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#testCommitUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#testCommitPassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					
					$("#commit" + dynamicId).find(".updateDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					$("#commit" + dynamicId).find(".updateTestA").removeAttr("data-toggle").removeAttr("href");
					
					$(".commitPassPhraseval"+dynamicId).val("");
					$(".phrCommitPassPhraseval"+dynamicId).val("");
					$(".testCommitPassPhraseval"+dynamicId).val("");
					
					$('#commitUserName'+dynamicId).val(userInfo.id);
					$('#commitPassword'+dynamicId).val(password);
					$('#phrCommitUserName'+dynamicId).val(userInfo.id);
					$('#phrCommitPassword'+dynamicId).val(password);
					$('#testCommitUserName'+dynamicId).val(userInfo.id);
					$('#testCommitPassword'+dynamicId).val(password);
					
					$('.srcCommitableFiles').hide();
					$('.phrCommitableFiles').hide();
					$('.testCommitableFiles').hide();
				} else if (action === "Update") {
					self.hideBtnLoading("button[name='updatebtn'][id='"+dynamicId+"']");
					commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					
					$('#updateDotPhresco_'+dynamicId).attr("checked", false);
					$('#updateTest_'+dynamicId).attr("checked", false);
					
					$('.updSrcOtherCredential').attr("checked", false);
					$('.updPhrOtherCredential').attr("checked", false);
					$('.updTestOtherCredential').attr("checked", false);
					
					$('#updateUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#updatePassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#updatePhrescoUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#updatePhrescoPassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#updateTestUserName'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					$('#updateTestPassword'+dynamicId).attr('readonly','readonly').removeClass('errormessage').removeAttr('placeholder');
					
					$("#svn_update" + dynamicId).find(".updateDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					$("#svn_update" + dynamicId).find(".updateTestA").removeAttr("data-toggle").removeAttr("href");
					
					$("input[name='updateHeadoption"+dynamicId+"']").last().attr("checked", false);
					$("input[name='updatePhrescoHeadoption"+dynamicId+"']").last().attr("checked", false);
					$("input[name='testUpdateHeadoption"+dynamicId+"']").last().attr("checked", false);
					
					$("#updateRevision"+dynamicId).val("").attr("readonly", "readonly");
					$("#updatePhrescoRevision"+dynamicId).val("").attr("readonly", "readonly");
					$("#testUpdateRevision"+dynamicId).val("").attr("readonly", "readonly");
					
					$("#updateGitUserName"+dynamicId).val("");
					$("#updateGitPassword"+dynamicId).val("");
					
					$("#updatePhrescoGitUserName"+dynamicId).val("");
					$("#updatePhrescoGitPassword"+dynamicId).val("");
					
					$("#testUpdateGitUserName"+dynamicId).val("");
					$("#testUpdateGitPassword"+dynamicId).val("");
					
					$(".updateBranchval"+dynamicId).val("");
					$(".updatePassPhraseval"+dynamicId).val("");
					
					$(".updatePhrescoBranchval"+dynamicId).val("");
					$(".updatePhrescoPassPhraseval"+dynamicId).val("");
					
					$(".testUpdateBranchval"+dynamicId).val("");
					$(".testUpdatePassPhraseval"+dynamicId).val("");
					
					$(".updateStream"+dynamicId).val("");
					$(".updatePhrescoStream"+dynamicId).val("");
					$(".testUpdateStream"+dynamicId).val("");
					
					$('#updateUserName'+dynamicId).val(userInfo.id);
					$('#updatePassword'+dynamicId).val(password);
					$('#updatePhrescoUserName'+dynamicId).val(userInfo.id);
					$('#updatePhrescoPassword'+dynamicId).val(password);
					$('#updateTestUserName'+dynamicId).val(userInfo.id);
					$('#updateTestPassword'+dynamicId).val(password);
				}
				
				var action = $(this).attr("data-original-title"), openccObj = this;
				if (action === "Commit") {
					commonVariables.hideloading = true;
					self.checkForLock("commit", dynamicId, '', function(response){
						if (response.status === "success" && response.responseCode === "PHR10C00002") {
							var appDirName = $(openccObj).parent().parent().attr("class");
							var data = {};
							data.appdirname = appDirName;
							data.dynamicId = dynamicId;
							$("#dummyCommit_"+dynamicId).css("height","10px");
							commonVariables.loadingScreen.removeLoading();
							self.projectslistListener.getCommitableFiles(data, openccObj);

							// Popup Fix
							var targetName = $(openccObj).attr('name');
							var target = $('#' + targetName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
							$('.footer_section').css('z-index', '0');
							// End Popup Fix
						} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
							commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
						}	
					});		
				} else if (action === "Update") {
					var openccName = $(this).attr('name');
					commonVariables.hideloading = true;
					self.checkForLock("update", dynamicId, '', function(response){
						if (response.status === "success" && response.responseCode === "PHR10C00002") {
							var appDirName = $(openccObj).parent().parent().attr("class");
							var data = {};
							data.appdirname = appDirName;
							data.dynamicId = dynamicId;
							$("#dummyUpdate_"+dynamicId).css("height","10px");
							self.projectslistListener.getUpdatableFiles(data, openccObj);

							// Popup Fix
							var targetName = $(openccObj).attr('name');
							var target = $('#' + targetName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
							$('.footer_section').css('z-index', '0');
							// End Popup Fix
						} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
							commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
						}	
					});		
				} else if (action === "Add Repo" || $(this).hasClass('del_appln') || $(this).hasClass('del_project')){
					var openccName = $(this).attr('name'), subModuleIds = $(this).attr('subModuleIds'),lockAction = action ===  "Add Repo" ? "addToRepo" : ($(this).hasClass('del_appln') ? "deleteAppln" : "deleteProj");
					var applnids = $(this).hasClass('del_project') ? $(this).attr('appids') : dynamicId;
					if(lockAction === "addToRepo") {
						commonVariables.hideloading = true;
					}
					self.checkForLock(lockAction, applnids, subModuleIds, function(response){
						if (response.status === "success" && response.responseCode === "PHR10C00002") {
							if (action === "Add Repo") {
								$('#addToRepoHelpDiv'+dynamicId).hide();
								$('#addToRepoTabDiv'+dynamicId).hide();
								$('#addToRepoMyTabContent'+dynamicId).hide();
								$('.addToRepoErr_'+dynamicId).hide();
								var appDirName = $(openccObj).parent().parent().attr("class");
								var data = {};
								data.appDirName = appDirName;
								self.projectslistListener.projectListActionForScm(self.projectslistListener.getActionHeader(data, "repoExists"), "", function(response) {
									if (response.data) {
										$('.addToRepoErr_'+dynamicId).show();
									} else {
										$('#addToRepoHelpDiv'+dynamicId).show();
										$('#addToRepoTabDiv'+dynamicId).show();
										$('#addToRepoMyTabContent'+dynamicId).show();
									}
								});
							}
							
							if($(obj_del).hasClass('del_project')) {
								self.delobj = $(obj_del).parent('td.delimages');
							}
							self.openccpl(openccObj, openccName, currentPrjName);
							var target = $('#' + openccName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
							$('.footer_section').css('z-index', '0');
						} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
							commonVariables.api.showError(self.getLockErrorMsg(response, lockAction), 'error', true, true);
						}
					});			
				} else if ($(this).hasClass('del_submodule')){
					var openccName = $(this).attr('name'), rootModule = $(this).closest('tr').attr('rootModule');
					self.checkForLock('deleteAppln', dynamicId, '', function(response) {
						if (response.status === "success" && response.responseCode === "PHR10C00002") {
							self.onDeleteSubModuleEvent.dispatch(openccName, rootModule, function(dependents) {
							var deleteMsg = "Are you sure to delete?";
							if (dependents.length > 0) {
								deleteMsg =openccName + ' is dependent to ' + dependents.join(',') + '. <br/>Are you sure to delete?'
								$('.'+openccName+'_module_dependents').attr("dependents", dependents.join(','));
							}
							self.openccpl(openccObj, openccName, rootModule);
							$('.'+openccName+'_module_delete_msg').html(deleteMsg);
						});
						} else if (response.status === "success" && response.responseCode === "PHR10C00001") {
							commonVariables.api.showError(self.getLockErrorMsg(response), 'error', true, true);
						}
					});
				} else {
					self.openccpl(this, $(this).attr('name'), currentPrjName);
				}
				//functionality for search log messages
				$("#type_"+dynamicId).bind('change',function() {

					if($("#type_"+dynamicId).val() === 'tfs'){
						$('.search, .phrescosearch, .testsearch').hide();
						$('.tfsAddServerPath').show();
					} else if($(this).val() !== 'svn') {
						$(".search, .tfsAddServerPath").hide();
						$('.searchdropdown').hide();
						$(".phrescosearch").hide();
						$('.dotphrescosearchdropdown').hide();
						$(".testsearch").hide();
						$('.testsearchdropdown').hide();
					} else{
						$(".search").show();
						$(".phrescosearch").show();
						$(".testsearch").show();
					}	
				});
				
				if($("#type_"+dynamicId).val() === 'svn'){
					$('.search').show();
					$('.phrescosearch').show();
					$('.testsearch').show();
					$('.tfsAddServerPath').hide();
				} else {
					$(".search").hide();
					$(".phrescosearch").hide();
					$(".testsearch").hide();
					$('.tfsAddServerPath').hide();
				}	
				
				$('.search').unbind("click");
				$('.search').bind("click", function() {
					self.projectslistListener.flag1 = 1;
					var idval = $(this).attr('dynId');
					var repoType = $(this).attr('typeOfRepo');
					if(repoType === 'add') {
						if(!(self.projectslistListener.validateAddToRepoData(idval))) {
							$('.searchdropdown').empty();
							counter = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#repourl_"+dynamicId).val();
							actionBody.userName = $("#uname_"+dynamicId).val();
							actionBody.password = $("#pwd_"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.searchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();
								// Loading latest message by default
								var repoMsg = $('.searchdropdown').eq(0).find('option:first').text();
								$("#repomessage_"+dynamicId).val(repoMsg);								
							});
							$('.searchdropdown').show();
						}
					} else if(repoType === 'commit') {
						$('.searchdropdown').empty();
							counter = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#commitRepourl"+dynamicId).val();
							actionBody.userName = $("#commitUserName"+dynamicId).val();
							actionBody.password = $("#commitPassword"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.searchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();

								// Loading latest message by default
								var repoMsg = $('.searchdropdown').eq(0).find('option:first').text();
								$("#commitMessage_"+dynamicId).val(repoMsg);
							});
							$('.searchdropdown').show();
					}
				});	
				
				$('.phrescosearch').unbind("click");
				$('.phrescosearch').bind("click", function() {
					var idval = $(this).attr('dynId');
					var typeOfRepo = $(this).attr('typeOfRepo');
					if(typeOfRepo === "add") {
						if(!(self.projectslistListener.validatedotPhrescoAndTest(idval, 'phrescorepourl' , 'phrescouname', 'phrescopwd'))) {
							$('.dotphrescosearchdropdown').empty();
							dotphresco = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#phrescorepourl_"+dynamicId).val();
							actionBody.userName = $("#phrescouname_"+dynamicId).val();
							actionBody.password = $("#phrescopwd_"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.dotphrescosearchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();

								// Loading latest message by default
								var repoMsg = $('.dotphrescosearchdropdown').eq(0).find('option:first').text();
								$("#phrescorepomessage_"+dynamicId).val(repoMsg);
							});
							$('.dotphrescosearchdropdown').show();
						}
					} else if(typeOfRepo === "commit") {
							$('.dotphrescosearchdropdown').empty();
							dotphresco = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#phrCommitRepourl"+dynamicId).val();
							actionBody.userName = $("#phrCommitUserName"+dynamicId).val();
							actionBody.password = $("#phrCommitPassword"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.dotphrescosearchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();

								// Loading latest message by default
								var repoMsg = $('.dotphrescosearchdropdown').eq(0).find('option:first').text();
								$("#phrCommitMessage_"+dynamicId).val(repoMsg);
							});
							$('.dotphrescosearchdropdown').show();
					}
				});
				
				$('.testsearch').unbind("click");
				$('.testsearch').bind("click", function() {
					var idval = $(this).attr('dynId');
					var typeOfRepo = $(this).attr('typeOfRepo');					
					if(typeOfRepo === 'add') {	
						if(!(self.projectslistListener.validatedotPhrescoAndTest(idval, 'testrepourl' , 'testuname', 'testpwd'))) {
							$('.testsearchdropdown').empty();
							testsearch = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#testrepourl_"+dynamicId).val();
							actionBody.userName = $("#testuname_"+dynamicId).val();
							actionBody.password = $("#testpwd_"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.testsearchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();

								// Loading latest message by default
								var repoMsg = $('.testsearchdropdown').eq(0).find('option:first').text();
								$("#testrepomessage_"+dynamicId).val(repoMsg);
							});
							$('.testsearchdropdown').show();
						}
					} else if(typeOfRepo === 'commit') {
						$('.testsearchdropdown').empty();
							testsearch = 0;
							var actionBody = {};
							actionBody.repoUrl = $("#testCommitRepourl"+dynamicId).val();
							actionBody.userName = $("#testCommitUserName"+dynamicId).val();
							actionBody.password = $("#testCommitPassword"+dynamicId).val();
							commonVariables.hideloading = true;
							self.projectslistListener.showpopupLoad($("#addRepoLoading_"+dynamicId));
							self.projectslistListener.projectListAction(self.projectslistListener.getActionHeader(actionBody, "searchlogmessage"), "" , function(response) {
								 $.each(response.data, function(index, value) {
									$('.testsearchdropdown').append('<option value='+value+'>'+value+'</option>');
								});
								commonVariables.hideloading = false;
								self.projectslistListener.hidePopupLoad();

								// Loading latest message by default
								var repoMsg = $('.testsearchdropdown').eq(0).find('option:first').text();
								$("#testCommitMessage_"+dynamicId).val(repoMsg);
							});
							$('.testsearchdropdown').show();
					}
				});
				
				$('.searchdropdown').on('change', function() {
					$('.searchdropdown').hide();
					var temp = $(this).find(':selected').text();
					if($(this).attr('typeOfRepo') === 'add')
						$("#repomessage_"+dynamicId).val(temp);
					else if($(this).attr('typeofRepo') === 'commit')
						$("#commitMessage_"+dynamicId).val(temp);
				});	


				$('.dotphrescosearchdropdown').on("change", function() {
					$('.dotphrescosearchdropdown').hide();
					var temp = $(this).find(':selected').text();
					if($(this).attr('typeOfRepo') === 'add')
						$("#phrescorepomessage_"+dynamicId).val(temp);
					else if($(this).attr('typeofRepo') === 'commit')
						$("#phrCommitMessage_"+dynamicId).val(temp);
			
				});	

				$('.testsearchdropdown').click(function() {
					$('.testsearchdropdown').hide();
					var temp = $(this).find(':selected').text();
					if($(this).attr('typeOfRepo') === 'add')
						$("#testrepomessage_"+dynamicId).val(temp);
					else if($(this).attr('typeofRepo') === 'commit')
						$("#testCommitMessage_"+dynamicId).val(temp);

				});	
				//end of functionality for search log messages
			});
			
			$("input[name='deleteConfirm']").unbind('click');
			$("input[name='deleteConfirm']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).parent().parent().attr("currentprjname"), deleteObj = {};
				var deletearray = [], deleteproject, imgname1, imgname2, deletemulti, flag_multi = 0;
				deleteproject = $(this).parent().parent().attr('currentPrjName');
				deletemulti = $(this).parent().parent().attr('currentmodulename');
				if(deletemulti !== undefined) {
					deletearray.push(deletemulti);
					imgname1 = $("tr[multimodule="+deletemulti+"]").next('tr').children('td:eq(5)').children('a').children('img').attr('name');
					imgname2 = $("tr[multimodule="+deletemulti+"]").prev('tr').children('td:eq(5)').children('a').children('img').attr('name');
					flag_multi = 1;
				} else { 
					deletearray.push(deleteproject);
					imgname1 = $("tr[class="+deleteproject+"]").next('tr').children('td:eq(5)').children('a').children('img').attr('name');
					imgname2 = $("tr[class="+deleteproject+"]").prev('tr').children('td:eq(5)').children('a').children('img').attr('name');
				}	
				self.flagged=1;
				//commonVariables.loadingScreen.showLoading();		
				deleteObj.actionType = "application";		
				deleteObj.appDirNames = deletearray;
				if ($(this).hasClass('module_delete')) {
					var array = [];
					array.push($(this).attr('moduleName'));
					deleteObj.actionType = 'module';
					deleteObj.appDirNames = array;
					deleteObj.rootModule = $(this).attr('appdirname');
					deleteObj.dependents = $(this).attr('dependents').split(',');
					self.projectslistListener.delprojectname = $(this).attr("moduleName");
					deleteproject = $(this).closest("tr");
					self.deleteProjectfn(deleteObj, "module_delete", "", deleteproject, flag_multi);
				} else {
					self.deleteProjectfn(deleteObj, imgname1, imgname2, deleteproject, flag_multi);
				}

				
				self.closeAll($(this).attr('name'));
				$('.content_title').css('z-index', '6');
				$('.header_section').css('z-index', '7');
				
			});

			$("input[name='holeDelete']").unbind('click');
			$("input[name='holeDelete']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).attr('deleteAppname');
				var deleteObj = {}, projectnameArray = [], multiArray = [],  cls, temp, temp1, temp2, classname, curr, currentRow, deleteappname, multimod, multiflag = 0;
				deleteappname = $(this).attr('deleteappname');
				temp = self.delobj.parent('tr');
				curr =  self.delobj.parent().next('tr');
				currentRow =  self.delobj.parent().next();
				while(currentRow !== null && currentRow.length > 0) {
				   classname = currentRow.attr("class");
				   if(currentRow.attr('ismultimodule') === 'true') {
						multimod = currentRow.attr('modulename');
						multiflag = 1;
						currentRow = currentRow.next('tr');
						if($.inArray(classname.toString(), projectnameArray) === -1)
							projectnameArray.push(classname);
						multiArray.push(multimod);
				   } else if(classname !== "proj_title" && classname !== "") {
				        currentRow = currentRow.next('tr');
						if($.inArray(classname.toString(), projectnameArray) === -1)
							projectnameArray.push(classname);
				   }else {currentRow = null;}
				}
				self.flagged=1;
				deleteObj.actionType = "project";
				deleteObj.appDirNames = projectnameArray;
				//commonVariables.loadingScreen.showLoading();
				self.deleteObj = deleteObj;
				self.getAction(deleteObj,"delete",function(response) {
					//commonVariables.loadingScreen.removeLoading();
						while(curr !== null && curr.length !== 0) {
							cls = curr.attr("class");
							if(cls!="proj_title") {
								temp1 = curr;
								temp2 = curr.next('tr');
								$(curr).remove();
								curr = temp2;
							}
							else {
								curr = null;
							}
						}	
						$(temp).remove();
						if(!($('tr.proj_title').length)) {
							self.flagged=0;
						}	
						$(".deleteproj_msg").hide();
				});		
				$('.content_title').css('z-index', '6');
				$('.header_section').css('z-index', '7');
			});			
			
			$(".addToRepocredential").unbind("click");
			$(".addToRepocredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				self.otherCredentialClickEvent($(this), $('#uname_'+dynamicId), $('#pwd_'+dynamicId));
			});
			
			$(".addToRepoPhrescocredential").unbind("click");
			$(".addToRepoPhrescocredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				self.otherCredentialClickEvent($(this), $('#phrescouname_'+dynamicId), $('#phrescopwd_'+dynamicId));
			});
			
			$(".addToRepoTestCredential").unbind("click");
			$(".addToRepoTestCredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				self.otherCredentialClickEvent($(this), $('#testuname_'+dynamicId), $('#testpwd_'+dynamicId));
			});
			
			$(".cmtSrcOtherCredential").unbind("click");
			$(".cmtSrcOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#commitUserName'+dynamicId), $('#commitPassword'+dynamicId));
			});
			
			$(".phrCmtSrcOtherCredential").unbind("click");
			$(".phrCmtSrcOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#phrCommitUserName'+dynamicId), $('#phrCommitPassword'+dynamicId));
			});
			
			$(".testCmtSrcOtherCredential").unbind("click");
			$(".testCmtSrcOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#testCommitUserName'+dynamicId), $('#testCommitPassword'+dynamicId));
			});
			
			$(".updSrcOtherCredential").unbind("click");
			$(".updSrcOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#updateUserName'+dynamicId), $('#updatePassword'+dynamicId));
			});
			
			$(".updPhrOtherCredential").unbind("click");
			$(".updPhrOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#updatePhrescoUserName'+dynamicId), $('#updatePhrescoPassword'+dynamicId));
			});
			
			$(".updTestOtherCredential").unbind("click");
			$(".updTestOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.otherCredentialClickEvent($(this), $('#updateTestUserName'+dynamicId), $('#updateTestPassword'+dynamicId));
			});
			
			$("button[name='addrepobtn']").unbind("click");
			$("button[name='addrepobtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddRepoEvent.dispatch($(this), dynid);				
			});				
			
			$("button[name='commitbtn']").unbind("click");
			$("button[name='commitbtn']").bind("click", function() {
				var dynid = $(this).attr('id');
				self.onAddCommitEvent.dispatch($(this), dynid);				
			});
						
			$("button[name='updatebtn']").unbind("click");
			$("button[name='updatebtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddUpdateEvent.dispatch($(this), dynid);				
			});

			$("input[name='generate']").unbind("click");
			$("input[name='generate']").click(function() {
				self.onAddReportEvent.dispatch($(this));	
			});
			
			$("a[temp=pdf_report]").click(function() {
				self.onGetReportEvent.dispatch($(this));
			});
			
			$(".splitDotPhresco").unbind("click");
			$(".splitDotPhresco").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					self.makeRepoTypeSelected(dynamicId);
					$("#addRepo_" + dynamicId).find(".dotPhrescoA").attr("data-toggle", "tab").attr("href", "#dotphresco"+dynamicId);
					commonVariables.navListener.showDotPhrescoTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
				} else {
					$("#addRepo_" + dynamicId).find(".dotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
					}
				}
			});
			
			$(".splitTest").unbind("click");
			$(".splitTest").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					self.makeRepoTypeSelected(dynamicId);
					$("#addRepo_" + dynamicId).find(".testA").attr("data-toggle", "tab").attr("href", "#test"+dynamicId);
					commonVariables.navListener.showTestTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
				} else {
					$("#addRepo_" + dynamicId).find(".testA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#dotphresco"+dynamicId), $("#source"+dynamicId), $("#test"+dynamicId), $("#splitDotPhresco_"+dynamicId), $("#splitTest_"+dynamicId));
					}
				}
			});
			
			$(".commitDotPhresco").unbind("click");
			$(".commitDotPhresco").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#commit" + dynamicId).find(".commitDotPhrescoA").attr("data-toggle", "tab").attr("href", "#commitDotphresco"+dynamicId);
					commonVariables.navListener.showDotPhrescoTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
				} else {
					$("#commit" + dynamicId).find(".commitDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
					}
				}
			});
			
			$(".commitTest").unbind("click");
			$(".commitTest").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#commit" + dynamicId).find(".commitTestA").attr("data-toggle", "tab").attr("href", "#commitTest"+dynamicId);
					commonVariables.navListener.showTestTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
				} else {
					$("#commit" + dynamicId).find(".commitTestA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#commitDotphresco"+dynamicId), $("#commitSource"+dynamicId), $("#commitTest"+dynamicId), $("#commitDotPhresco_"+dynamicId), $("#commitTest_"+dynamicId));
					}
				}
			});
			
			$(".dotPhrescoA, .testA").unbind("click");
			$(".dotPhrescoA, .testA").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				self.makeRepoTypeSelected(dynamicId);
			});
			
			$(".updateDotPhresco").unbind("click");
			$(".updateDotPhresco").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#svn_update" + dynamicId).find(".updateDotPhrescoA").attr("data-toggle", "tab").attr("href", "#updateDotphresco"+dynamicId);
					commonVariables.navListener.showDotPhrescoTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				} else {
					$("#svn_update" + dynamicId).find(".updateDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			});
			
			$(".updateTest").unbind("click");
			$(".updateTest").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#svn_update" + dynamicId).find(".updateTestA").attr("data-toggle", "tab").attr("href", "#updateTest"+dynamicId);
					commonVariables.navListener.showTestTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
				} else {
					$("#svn_update" + dynamicId).find(".updateTestA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						commonVariables.navListener.showSrcTab($("#updateDotphresco"+dynamicId), $("#updateSource"+dynamicId), $("#updateTest"+dynamicId), $("#updateDotPhresco_"+dynamicId), $("#updateTest_"+dynamicId));
					}
				}
			});
			
			$('.updateHeadoption').unbind("click");
			$('.updateHeadoption').bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).val() === "revision") {
					$("#updateRevision"+dynamicId).removeAttr("readonly");
				} else {
					$("#updateRevision"+dynamicId).attr("readonly", "readonly");
				}
			});
			
			$('.updatePhrescoHeadoption').unbind("click");
			$('.updatePhrescoHeadoption').bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).val() === "revision") {
					$("#updatePhrescoRevision"+dynamicId).removeAttr("readonly");
				} else {
					$("#updatePhrescoRevision"+dynamicId).attr("readonly", "readonly");
				}
			});
			
			$('.testUpdateHeadoption').unbind("click");
			$('.testUpdateHeadoption').bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).val() === "revision") {
					$("#testUpdateRevision"+dynamicId).removeAttr("readonly");
				} else {
					$("#testUpdateRevision"+dynamicId).attr("readonly", "readonly");
				}
			});
			
			$('.closeAddToRepoHelp').unbind("click");
			$('.closeAddToRepoHelp').bind("click", function() {
				$(this).parent().parent().hide();
			});
			
			self.windowResize();
			self.tableScrollbar();
			this.customScroll($(".customer_names"));
		},
		
		openAddToRepoHelp : function(thisObj, dynamicId) {
			var self = this;
			$('.content_main').addClass('z_index_ci');
			var clicked = thisObj;
			var target = $("#addToRepoHelp"+dynamicId);
			var twowidth = window.innerWidth/1.5;
		
			if (clicked.offset().left < twowidth) {
				$(target).toggle();
				var a = target.height()/2;
				var b = clicked.height()/2;
				var t=clicked.offset().top + (b+12) - (a+12) ;
				var l=clicked.offset().left + clicked.width()+ 4;
				$(target).offset({
					top: t,
					left: l
				});
				$(target).addClass('speakstyleleft').removeClass('speakstyleright');
			} else {
				$(target).toggle();
				var t=clicked.offset().top - target.height()/2;
				var l=clicked.offset().left - (target.width()+ 15);
				$(target).offset({
					top: t,
					left: l
				});
				$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			}
		},
		
		otherCredentialClickEvent : function(thisObj, userNameObj, pwdObj) {
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var password = commonVariables.api.localVal.getSession('password');
			if (thisObj.is(':checked')) {
				userNameObj.removeAttr('readonly');
				pwdObj.removeAttr('readonly');
				userNameObj.val('');
				pwdObj.val('');
			} else {
				userNameObj.val(userInfo.id);
				pwdObj.val(password);
				userNameObj.attr('readonly','readonly');
				pwdObj.attr('readonly','readonly');
			}
		},
		
		addZindex : function () {
			$('.content_title').css('z-index', '6');
			$('.header_section').css('z-index', '7');
		},
		
		makeRepoTypeSelected : function(dynamicId) {
			var selectedType = $("#type_" + dynamicId).val();
			$("#phrescotype_" + dynamicId).val(selectedType);
			$("#testtype_" + dynamicId).val(selectedType);
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});
