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
		flagged: null,
		flag:null,
		
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
		
		deleteProjectfn : function(deletearray, imgname1, imgname2, deleteproject){
			var self = this;
			self.getAction(deletearray,"delete",function(response) {
				//commonVariables.loadingScreen.removeLoading();
				self.deleterow(imgname1, imgname2, deleteproject);
			});
		},
		
		deleterow : function(imgname1, imgname2, deleteproject){
			var self = this;
			if(imgname1 === 'delete'|| imgname2 === 'delete') {	
				$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").remove();
			} else {
				$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").prev('tr').remove();
				$(commonVariables.contentPlaceholder).find("tr[class="+deleteproject+"]").remove();
			}
			if(!($(commonVariables.contentPlaceholder).find('tr.proj_title').length)){
				self.flagged=0;	
			}
		},
		
		makeCredReadOnly : function (checkObj, usrObj, pwdObj) {
			if(!checkObj.is(':checked')) {
				usrObj.attr('readonly','true');
				pwdObj.attr('readonly','true');
			} else {
				usrObj.removeAttr('readonly');
				pwdObj.removeAttr('readonly');
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
				var thisObj = this; appid = $(this).attr("appid");
				self.checkForLock("editAppln", appid, function(response){
					if (response.status === "success" && response.responseCode === "PHR10C00002") {
						$(".dyn_popup").hide();
						commonVariables.appDirName = $(thisObj).closest("tr").attr("class");
						var value = $(thisObj).closest("tr").attr("class");
						var techid = $(thisObj).closest("tr").attr("techid");
						commonVariables.techId = techid;
						$("#myTab li#appinfo a").addClass("act");
						var module = "multimodule" === $(thisObj).attr("from") ?  $(thisObj).text() : "";
						self.onProjectsEvent.dispatch(value , techid, module);
	
						//To show/hide openfolder, copy path icon and copy to clipboard
						var actionBody = {};
						self.projectslistListener.getProjectList(self.projectslistListener.getActionHeader(actionBody, "checkMachine"), function(response) {
							commonVariables.api.localVal.setSession("checkMachine", JSON.stringify(response));
						});
						if (!self.isBlank(module)) {
							$('li[id=continuousDeliveryView]').hide();
						} else {
							$('li[id=continuousDeliveryView]').show();
						}
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
				/* $("#uname_"+dynamicId).keypress(function(e) {
						if($("#type_"+dynamicId).val() === 'git') {
							if (e.which !== 95) {
								return true;
							} else {
								e.preventDefault();
							}
						}	
					}); */
				
				if (action === "Add Repo") {   0
					selectObj = $("#type_"+dynamicId);
					checkObj = $("#repocredential_"+dynamicId);
					usrObj = $("#uname_"+dynamicId);
					pwdObj = $("#pwd_"+dynamicId);
					$("input[name='repoUrl']").val('');
					$("textarea[name='commitMsg']").val('');
					if($("#repourl_"+dynamicId).hasClass('errormessage')) {
						$("#repourl_"+dynamicId).removeClass('errormessage');
						$("#repourl_"+dynamicId).removeAttr('placeholder');
						$("#repourl_"+dynamicId).attr('placeholder','Repo Url');
					}
					if($("#uname_"+dynamicId).hasClass('errormessage')) {
						$("#uname_"+dynamicId).removeClass('errormessage');
						$("#uname_"+dynamicId).removeAttr('placeholder');
					}
					if($("#pwd_"+dynamicId).hasClass('errormessage')) {
						$("#pwd_"+dynamicId).removeClass('errormessage');
						$("#pwd_"+dynamicId).removeAttr('placeholder');
						$("#pwd_"+dynamicId).attr('placeholder','Password');
					}
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
					selectObj = $("#updateType_"+dynamicId);
					checkObj = $("#updateCredential_"+dynamicId);
					usrObj = $("#updateUsername_"+dynamicId);
					pwdObj = $("#updatePassword_"+dynamicId);
					$("input[name='repoUrl']").val('');
					$(".revision").val('');
					if($("#updateRepourl_"+dynamicId).hasClass('errormessage')) {
						$("#updateRepourl_"+dynamicId).removeClass('errormessage');
						$("#updateRepourl_"+dynamicId).removeAttr('placeholder');
						$("#updateRepourl_"+dynamicId).attr('placeholder','Repo Url');
					}
					if($("#updateUsername_"+dynamicId).hasClass('errormessage')) {
						$("#updateUsername_"+dynamicId).removeClass('errormessage');
						$("#updateUsername_"+dynamicId).removeAttr('placeholder');
					}
					if($("#updatePassword_"+dynamicId).hasClass('errormessage')) {
						$("#updatePassword_"+dynamicId).removeClass('errormessage');
						$("#updatePassword_"+dynamicId).removeAttr('placeholder');
						$("#updatePassword_"+dynamicId).attr('placeholder','Password');
					}
				} 
				
				if(checkObj !== null  && checkObj !== undefined && checkObj !== '') {
					self.makeCredReadOnly(checkObj, usrObj, pwdObj);
				}	

				if(checkObj !== null  && checkObj !== undefined && checkObj !== '') {
					checkObj.unbind("change");
					checkObj.on("change", function(){				
						self.makeCredReadOnly(checkObj, usrObj, pwdObj);
					});
				}
				
				if(selectObj !== null  && selectObj !== undefined && selectObj !== '') {
					selectObj.unbind("change");
					selectObj.on("change", function(){				
						self.projectslistListener.hideShowCredentials($(this).find(':selected').val(), usrObj, pwdObj, checkObj);
						self.projectslistListener.typeChangeEvent(selectObj, $(this).find(':selected').val(), dynamicId);
					});
				}
				
				var data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				userId = data.id;
				$('.uname').val(data.id);
				$('.pwd').val('');
				$('.commit').hide();
				$('.add_repo').hide();
				$('.svn_update').hide();
				$("#addRepoLoading_"+dynamicId).hide();
				var action = $(this).attr("data-original-title"), openccObj = this;
				if (action === "Commit") {
					self.checkForLock("commit", dynamicId, function(response){
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
					self.checkForLock("update", dynamicId, function(response){
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
					var openccName = $(this).attr('name'), lockAction = action ===  "Add Repo" ? "addToRepo" : ($(this).hasClass('del_appln') ? "deleteAppln" : "deleteProj");
					var applnids = $(this).hasClass('del_project') ? $(this).attr('appids') : dynamicId;
					self.checkForLock(lockAction, applnids, function(response){
						if (response.status === "success" && response.responseCode === "PHR10C00002") {
							self.openccpl(openccObj, openccName, currentPrjName);
							var target = $('#' + openccName);
							$('.content_main').prepend(target);
							$('.content_title').css('z-index', '0');
							$('.header_section').css('z-index', '0');
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
			
			$("a[name = 'updatesvn']").unbind("click");
			$("a[name = 'updatesvn']").bind("click",function(){
				$("#svn_update").show();
			});
			$("input[name='deleteConfirm']").unbind('click');
			$("input[name='deleteConfirm']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).attr('deleteAppname');
				var deletearray = [], deleteproject, imgname1, imgname2;
				deleteproject = $(this).parent().parent().attr('currentPrjName');
				deletearray.push(deleteproject);
				imgname1 = $("tr[class="+deleteproject+"]").next('tr').children('td:eq(5)').children('a').children('img').attr('name');
				imgname2 = $("tr[class="+deleteproject+"]").prev('tr').children('td:eq(5)').children('a').children('img').attr('name');
				self.flagged=1;
				//commonVariables.loadingScreen.showLoading();		
				deletearray.actionType = "application";				
				self.deleteProjectfn(deletearray, imgname1, imgname2, deleteproject);
				self.closeAll($(this).attr('name'));
				
			});

			$("input[name='holeDelete']").unbind('click');
			$("input[name='holeDelete']").click(function(e) {
				self.projectslistListener.delprojectname = $(this).attr('deleteAppname');
				var projectnameArray = [], cls, temp, temp1, temp2, classname, curr, currentRow;
				temp = $(this).parents().parent("td.delimages").parent('tr');
				curr =  $(this).parents().parent("td.delimages").parent().next('tr');
				currentRow =  $(this).parents().parent("td.delimages").parent().next();
				while(currentRow !== null && currentRow.length > 0) {
				   classname = currentRow.attr("class");
				   if(classname !== "proj_title") {
				        currentRow = currentRow.next('tr');
				        projectnameArray.push(classname);
				   }else {currentRow = null;}
				}
				self.flagged=1;
				projectnameArray.actionType = "project";
				//commonVariables.loadingScreen.showLoading();
				self.getAction(projectnameArray,"delete",function(response) {
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
				});					
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
					
			$("input[name='addrepobtn']").unbind("click");
			$("input[name='addrepobtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddRepoEvent.dispatch($(this), dynid);				
			});				
			
			$("input[name='commitbtn']").unbind("click");
			$("input[name='commitbtn']").click(function() {
				var dynid = $(this).attr('id');
				self.onAddCommitEvent.dispatch($(this), dynid);				
			});
						
			$("input[name='updatebtn']").unbind("click");
			$("input[name='updatebtn']").click(function() {
				var dynid, revision;
				dynid = $(this).attr('id');
				var revision = $("input[name='revision']:checked").val();
				if(revision !== ""){
					revision = revision;
				} else{
					revision = $("#revision_"+dynid).val();
				}				
				self.onAddUpdateEvent.dispatch($(this), dynid, revision);				
			});

			$("input[name='generate']").unbind("click");
			$("input[name='generate']").click(function() {
				self.onAddReportEvent.dispatch($(this));	
			});
			
			$("a[temp=pdf_report]").click(function() {
				self.onGetReportEvent.dispatch($(this));
			});
			
			$("input[name='revision']").unbind("click");
			$("input[name='revision']").click(function() {
				if($(this).is(':checked')) {
					var value = $(this).val();
				}
			});
			self.windowResize();
			self.tableScrollbar();
			this.customScroll($(".customer_names"));
			//this.customScroll($(".pdfheight"));
		}
	});

	return Clazz.com.components.projectlist.js.ProjectList;
});