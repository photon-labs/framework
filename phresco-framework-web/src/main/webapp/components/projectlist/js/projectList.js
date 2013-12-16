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
				if ("delete" === action) {
					callback();
				}				
				if(self.flagged!=1) {
					self.loadPage(commonVariables.animation);
				} else {
					self.flagged=1;
				}
			});
		},
		
		deleteProjectfn : function(deletObj, imgname1, imgname2, deleteproject){
			var self = this;
			self.getAction(deletObj,"delete",function(response) {
				//commonVariables.loadingScreen.removeLoading();
				self.deleterow(imgname1, imgname2, deleteproject);
			});
		},
		
		deleterow : function(imgname1, imgname2, deleteproject){
			var self = this;
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
						commonVariables.techId = techid;
						$("#myTab li#appinfo a").addClass("act");
						var from = $(thisObj).attr("from");
						var hasModules = Boolean($(thisObj).attr("modules"));
						commonVariables.editAppFrom = from;
						commonVariables.editAppHasModules = hasModules;
						var module = "multimodule" === from ?  $(thisObj).text() : "";
						self.onProjectsEvent.dispatch(value , techid, module);
	
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
				var selectObj;
				var checkObj;
				var usrObj;
				var pwdObj;		
				var counter;				
				var obj_del = this;
				var action = $(this).attr("data-original-title");
				var currentPrjName = $(this).closest("tr").attr("class");
				var dynamicId = $(this).attr("dynamicId");
				$('input[name=generate]').addClass('btn_style').removeClass('disabled');
				$('input[name=generate]').removeAttr('disabled');
				//for hiding and clearing the dropdown values
				$('.searchdropdown').hide();
				$('.searchdropdown').html('');
				if ($("#updateType_"+dynamicId).val() !== "perforce") {
					$('.perforcedata').hide();
			    } else {
				 	$('.perforcedata').show();
				 	$('#updateRepourl_'+dynamicId).attr('placeholder','host:portnumber');
			    }
				//end for hiding and clearing dropdown value
				if (action === "Add Repo") {
					self.hideBtnLoading("button[name='addrepobtn'][id='"+dynamicId+"']");
					self.projectslistListener.showSrcUpdateTab(dynamicId);
					
					$('#splitDotPhresco_'+dynamicId).attr("checked", false);
					$('#splitTest_'+dynamicId).attr("checked", false);
					
					$("#repourl_"+dynamicId).val("");
					$("#phrescorepourl_"+dynamicId).val("");
					$("#testrepourl_"+dynamicId).val("");
					
					$('.addToRepocredential').attr("checked", false);
					$('.addToRepoPhrescocredential').attr("checked", false);
					$('.addToRepoTestCredential').attr("checked", false);
					
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
					selectObj = $("#commitType_"+dynamicId);
					checkObj = $("#commitCredential_"+dynamicId);
					usrObj = $("#commitUsername_"+dynamicId);
					pwdObj = $("#commitPassword_"+dynamicId);
					$("textarea[name='commitMsg']").val('');
					if($("#commitRepourl_"+dynamicId).hasClass('errormessage')) {
						$("#commitRepourl_"+dynamicId).removeClass('errormessage');
						$("#commitRepourl_"+dynamicId).removeAttr('placeholder');
						$("#commitRepourl_"+dynamicId).attr('placeholder','Repo Url');
					}
					if($("#commitUsername_"+dynamicId).hasClass('errormessage')) {
						$("#commitUsername_"+dynamicId).removeClass('errormessage');
						$("#commitUsername_"+dynamicId).removeAttr('placeholder');
					}
					if($("#commitPassword_"+dynamicId).hasClass('errormessage')) {
						$("#commitPassword_"+dynamicId).removeClass('errormessage');
						$("#commitPassword_"+dynamicId).removeAttr('placeholder');
						$("#commitPassword_"+dynamicId).attr('placeholder','Password');
					}
				} else if (action === "Update") {
					self.hideBtnLoading("button[name='updatebtn'][id='"+dynamicId+"']");
					self.projectslistListener.showSrcUpdateTab(dynamicId);
					
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
					
					selectObj = $("#updateType_"+dynamicId);
					checkObj = $("#updateCredential_"+dynamicId);
					usrObj = $("#updateUserName"+dynamicId);
					pwdObj = $("#updatePassword"+dynamicId);
				}
				
				if(selectObj !== null  && selectObj !== undefined && selectObj !== '') {
					selectObj.unbind("change");
					selectObj.on("change", function(){				
						self.projectslistListener.hideShowCredentials($(this).find(':selected').val(), usrObj, pwdObj, checkObj);
						self.projectslistListener.typeChangeEvent(selectObj, $(this).find(':selected').val(), dynamicId);
					});
				}
				
				$('.uname').val(userInfo.id);
				$('.pwd').val(password);
				$('#phrescouname_'+dynamicId).val(userInfo.id);
				$('#phrescopwd_'+dynamicId).val(password);
				$('#testuname_'+dynamicId).val(userInfo.id);
				$('#testpwd_'+dynamicId).val(password);
				
				$('.commit').hide();
				$('.add_repo').hide();
				$('.svn_update').hide();
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
							self.projectslistListener.getCommitableFiles(data, openccObj, usrObj,pwdObj,checkObj);

							// Popup Fix
							var targetName = $(openccObj).attr('name');
							var target = $('#' + targetName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
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
							self.projectslistListener.getUpdatableFiles(data, openccObj, usrObj, pwdObj, checkObj);

							// Popup Fix
							var targetName = $(openccObj).attr('name');
							var target = $('#' + targetName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
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
							if($(obj_del).hasClass('del_project')) {
								self.delobj = $(obj_del).parent('td.delimages');
							}
							self.openccpl(openccObj, openccName, currentPrjName);
							var target = $('#' + openccName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
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
					if($(this).val() !== 'svn') {
						$(".search").hide();
						$('.searchdropdown').hide();
					} else
						$(".search").show();
				});
				
				if($("#type_"+dynamicId).val() === 'svn')
					$('.search').show();
				else
					$(".search").hide();
				
				$('.search').unbind("click");
				$('.search').bind("click", function() {
					self.projectslistListener.flag1 = 1;
					var idval = $(this).parent().parent().parent().parent().next('div').next().children('input').attr('id');
					if(!(self.projectslistListener.validation(idval))) {
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
						});
						$('.searchdropdown').show();
					}
				});
				counter = 0;
				$('.searchdropdown').click(function() {
					counter++;
					if(counter == 2) {
						$('.searchdropdown').hide();
						var temp = $(this).find(':selected').text();
						$("#repomessage_"+dynamicId).val(temp);
						counter = 0;
					}	
				});	
				
				$('.searchdropdown').focusout(function() {
					counter = 0;
				});
				
				//end of functionality for search log messages
			});
			
			$("input[name='deleteConfirm']").unbind('click');
			$("input[name='deleteConfirm']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).attr('deleteAppname'), deleteObj = {};
				var deletearray = [], deleteproject, imgname1, imgname2;
				deleteproject = $(this).parent().parent().attr('currentPrjName');
				deletearray.push(deleteproject);
				imgname1 = $("tr[class="+deleteproject+"]").next('tr').children('td:eq(5)').children('a').children('img').attr('name');
				imgname2 = $("tr[class="+deleteproject+"]").prev('tr').children('td:eq(5)').children('a').children('img').attr('name');
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
					self.deleteProjectfn(deleteObj, "module_delete", "", deleteproject);
				} else {
					self.deleteProjectfn(deleteObj, imgname1, imgname2, deleteproject);
				}

				
				self.closeAll($(this).attr('name'));
				$('.content_title').css('z-index', '6');
				$('.header_section').css('z-index', '7');
				
			});

			$("input[name='holeDelete']").unbind('click');
			$("input[name='holeDelete']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).attr('deleteAppname');
				var deleteObj = {}, projectnameArray = [], cls, temp, temp1, temp2, classname, curr, currentRow, deleteappname;
				deleteappname = $(this).attr('deleteappname');
				temp = self.delobj.parent('tr');
				curr =  self.delobj.parent().next('tr');
				currentRow =  self.delobj.parent().next();
				while(currentRow !== null && currentRow.length > 0) {
				   classname = currentRow.attr("class");
				   if(classname !== "proj_title" && classname !== "") {
				        currentRow = currentRow.next('tr');
				        projectnameArray.push(classname);
				   }else {currentRow = null;}
				}
				self.flagged=1;
				deleteObj.actionType = "project";
				deleteObj.appDirNames = projectnameArray;
				//commonVariables.loadingScreen.showLoading();
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
			
			$(".credential").unbind("click");
			$(".credential").click(function() {
				if ($(".credential").is(':checked')) {
					$(".uname").val('');
					$(".pwd").val('');
				} else {
					var data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
					$(".uname").val(data.id);
					$(".pwd").val('');
				}
			});
			
			$(".addToRepocredential").unbind("click");
			$(".addToRepocredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				if ($(this).is(':checked')) {
					$('#uname_'+dynamicId).removeAttr('readonly');
					$('#pwd_'+dynamicId).removeAttr('readonly');
					$("#uname_"+dynamicId).val('');
					$("#pwd_"+dynamicId).val('');
				} else {
					$('#uname_'+dynamicId).val(userInfo.id);
					$('#pwd_'+dynamicId).val(password);
					$('#uname_'+dynamicId).attr('readonly','readonly');
					$('#pwd_'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$(".addToRepoPhrescocredential").unbind("click");
			$(".addToRepoPhrescocredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				if ($(this).is(':checked')) {
					$('#phrescouname_'+dynamicId).removeAttr('readonly');
					$('#phrescopwd_'+dynamicId).removeAttr('readonly');
					$("#phrescouname_"+dynamicId).val('');
					$("#phrescopwd_"+dynamicId).val('');
				} else {
					$('#phrescouname_'+dynamicId).val(userInfo.id);
					$('#phrescopwd_'+dynamicId).val(password);
					$('#phrescouname_'+dynamicId).attr('readonly','readonly');
					$('#phrescopwd_'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$(".addToRepoTestCredential").unbind("click");
			$(".addToRepoTestCredential").bind("click", function() {
				var dynamicId = $(this).attr("repoDynid");
				if ($(this).is(':checked')) {
					$('#testuname_'+dynamicId).removeAttr('readonly');
					$('#testpwd_'+dynamicId).removeAttr('readonly');
					$("#testuname_"+dynamicId).val('');
					$("#testpwd_"+dynamicId).val('');
				} else {
					$('#testuname_'+dynamicId).val(userInfo.id);
					$('#testpwd_'+dynamicId).val(password);
					$('#testuname_'+dynamicId).attr('readonly','readonly');
					$('#testpwd_'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$(".updSrcOtherCredential").unbind("click");
			$(".updSrcOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(':checked')) {
					$('#updateUserName'+dynamicId).removeAttr('readonly');
					$('#updatePassword'+dynamicId).removeAttr('readonly');
					$("#updateUserName"+dynamicId).val('');
					$("#updatePassword"+dynamicId).val('');
				} else {
					$('#updateUserName'+dynamicId).val(userInfo.id);
					$('#updatePassword'+dynamicId).val(password);
					$('#updateUserName'+dynamicId).attr('readonly','readonly');
					$('#updatePassword'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$(".updPhrOtherCredential").unbind("click");
			$(".updPhrOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(':checked')) {
					$('#updatePhrescoUserName'+dynamicId).removeAttr('readonly');
					$('#updatePhrescoPassword'+dynamicId).removeAttr('readonly');
					$("#updatePhrescoUserName"+dynamicId).val('');
					$("#updatePhrescoPassword"+dynamicId).val('');
				} else {
					$('#updatePhrescoUserName'+dynamicId).val(userInfo.id);
					$('#updatePhrescoPassword'+dynamicId).val(password);
					$('#updatePhrescoUserName'+dynamicId).attr('readonly','readonly');
					$('#updatePhrescoPassword'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$(".updTestOtherCredential").unbind("click");
			$(".updTestOtherCredential").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(':checked')) {
					$('#updateTestUserName'+dynamicId).removeAttr('readonly');
					$('#updateTestPassword'+dynamicId).removeAttr('readonly');
					$("#updateTestUserName"+dynamicId).val('');
					$("#updateTestPassword"+dynamicId).val('');
				} else {
					$('#updateTestUserName'+dynamicId).val(userInfo.id);
					$('#updateTestPassword'+dynamicId).val(password);
					$('#updateTestUserName'+dynamicId).attr('readonly','readonly');
					$('#updateTestPassword'+dynamicId).attr('readonly','readonly');
				}
			});
			
			$("button[name='addrepobtn']").unbind("click");
			$("button[name='addrepobtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddRepoEvent.dispatch($(this), dynid);				
			});				
			
			$("input[name='commitbtn']").unbind("click");
			$("input[name='commitbtn']").click(function() {
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
					$("#addRepo_" + dynamicId).find(".dotPhrescoA").attr("data-toggle", "tab").attr("href", "#dotphresco"+dynamicId);
					self.projectslistListener.showDotPhrescoTab(dynamicId);
				} else {
					$("#addRepo_" + dynamicId).find(".dotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.projectslistListener.showSrcTab(dynamicId);
					}
				}
			});
			
			$(".splitTest").unbind("click");
			$(".splitTest").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#addRepo_" + dynamicId).find(".testA").attr("data-toggle", "tab").attr("href", "#test"+dynamicId);
					self.projectslistListener.showTestTab(dynamicId);
				} else {
					$("#addRepo_" + dynamicId).find(".testA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.projectslistListener.showSrcTab(dynamicId);
					}
				}
			});
			
			$(".dotPhrescoA, .testA").unbind("click");
			$(".dotPhrescoA, .testA").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				var selectedType = $("#type_" + dynamicId).val();
				$("#phrescotype_" + dynamicId).val(selectedType);
				$("#testtype_" + dynamicId).val(selectedType);
			});
			
			$(".updateDotPhresco").unbind("click");
			$(".updateDotPhresco").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#svn_update" + dynamicId).find(".updateDotPhrescoA").attr("data-toggle", "tab").attr("href", "#updateDotphresco"+dynamicId);
					self.projectslistListener.showDotPhrescoUpdateTab(dynamicId);
				} else {
					$("#svn_update" + dynamicId).find(".updateDotPhrescoA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.projectslistListener.showSrcUpdateTab(dynamicId);
					}
				}
			});
			
			$(".updateTest").unbind("click");
			$(".updateTest").bind("click", function() {
				var dynamicId = $(this).attr("dynamicId");
				if ($(this).is(":checked")) {
					$("#svn_update" + dynamicId).find(".updateTestA").attr("data-toggle", "tab").attr("href", "#updateTest"+dynamicId);
					self.projectslistListener.showTestUpdateTab(dynamicId);
				} else {
					$("#svn_update" + dynamicId).find(".updateTestA").removeAttr("data-toggle").removeAttr("href");
					if ($(this).parent().hasClass("active")) {
						self.projectslistListener.showSrcUpdateTab(dynamicId);
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
			
			self.windowResize();
			self.tableScrollbar();
			this.customScroll($(".customer_names"));
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});