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
			self.projectListAction(self.getActionHeader(self.projectRequestBody, "configTypes"), "", function(response) {	
				commonVariables.api.localVal.setJson('configTypes', response.data);
				commonVariables.navListener.getMyObj(commonVariables.dashboard, function(editprojectObject) {
					commonVariables.projectLevel = true;
					$('.hProjectId').val(projectId); 
					Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
					Clazz.navigationController.push(editprojectObject, commonVariables.animation);
					$("#editprojectTab").css("display", "block");
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
							if(response.responseCode !== 'PHR200015' && response.responseCode !== 'PHR200021' && response.responseCode !== 'PHR600004' && response.responseCode !== null && response.responseCode !== undefined && response.responseCode !== 'PHR200027') {
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
			if(data !== undefined && data !== null && data !== "") { userId = data.id; }
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
				addrepo.type = projectRequestBody.type;
				addrepo.repoUrl = projectRequestBody.repoUrl;
				addrepo.userName = projectRequestBody.userName;
				addrepo.password = projectRequestBody.password;
				addrepo.commitMessage = projectRequestBody.commitMessage;
				header.requestPostBody = JSON.stringify(addrepo);			
				header.webserviceurl = commonVariables.webserviceurl + "repository/addProjectToRepo?appDirName="+projectRequestBody.appdirname+"&userId="+userId+"&appId="+projectRequestBody.appid+"&projectId="+projectRequestBody.projectid+"&displayName="+data.displayName;				
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
				header.webserviceurl = commonVariables.webserviceurl + "repository/commitProjectToRepo?appDirName="+projectRequestBody.appdirname+"&displayName="+data.displayName;
			} else if(action === "updateget") {
				var addupdate ={};
				header.requestMethod = "POST";
				addupdate.type = projectRequestBody.type;
				addupdate.repoUrl = projectRequestBody.repoUrl;
				addupdate.userName = projectRequestBody.userName;
				addupdate.password = projectRequestBody.password;
				addupdate.version = projectRequestBody.revision;
				if('perforce' === projectRequestBody.type) {
					addupdate.stream = projectRequestBody.stream;
				}
				header.requestPostBody = JSON.stringify(addupdate);
				header.webserviceurl = commonVariables.webserviceurl + "repository/updateImportedApplication?appDirName="+projectRequestBody.appdirname+"&displayName="+data.displayName;
			} else if (action === "configTypes") {
				if(projectId !== null){
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&projectId="+projectId;
				} else {
					self.bcheck = true;
					header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId="+customerId+"&userId="+userId+"&techId="+projectRequestBody.techid;
				}
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
				header.webserviceurl = commonVariables.webserviceurl +"repository/logMessages";
			}
			if (action === "checkMachine") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + 'util/checkMachine';
			}
			return header;
		},
		
		editApplication : function(value, techid, module) {
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
						self.loadAppInfo(value, techid, module);
					});	
				} else {
					self.loadAppInfo(value, techid, module);
				}					
			});
			
		},
		
		loadAppInfo : function(value, techid, module){
			var self = this;
			self.editAplnContent.appDirName = value;
			commonVariables.api.localVal.setSession('appDirName', value);
			commonVariables.api.localVal.setSession('techid', techid);
			Clazz.navigationController.push(self.editAplnContent, commonVariables.animation);
			self.isBlank(module) ? $("#aplntitle").html("Edit - "+value) : $("#aplntitle").html("Edit - "+module);
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
				if($("#repomessage_"+dynid).val() === '') {
					var drop = obj.parent().prev().prev().find('tbody').find('.searchdropdown option:first-child').text();
					$("#repomessage_"+dynid).val(drop);
				}
				
				repodata.type = $("#type_"+dynid).val();
				repodata.repoUrl = $("#repourl_"+dynid).val();
				repodata.userName = $("#uname_"+dynid).val();
				repodata.password = $("#pwd_"+dynid).val();
				repodata.commitMessage = $("#repomessage_"+dynid).val();
				repodata.appdirname = obj.parent("div").attr("appDirName");
				repodata.appid = obj.parent("div").attr("appId");
				repodata.projectid = obj.parent("div").attr("projectId");
				repodata.passPhrase = $("#repoPhrase_"+dynid).val();
				actionBody = repodata;
				action = "repoget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(actionBody, action), $("#addRepoLoading_"+dynid), function(response){
					if (response.exception === null) {
						$("#addRepo_"+dynid).hide();
					}
				commonVariables.hideloading = false;
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
				commitdata.passPhrase = $("#commitPhrase_"+dynid).val();
				$.each($('.commitChildChk_'+ dynid) , function(index, value){
					if ($(this).is(':checked')) {
						arrayCommitableFiles.push($(this).val());
					}
				});
				
				commitdata.commitableFiles = arrayCommitableFiles;
				commitdata.appdirname = obj.parent("div").attr("appDirName");
				actionBody = commitdata;
				action = "commitget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(actionBody, action), $('#commitLoading_'+dynid), function(response){
					if (response.exception === null) {
						$("#commit"+dynid).hide();
					}
				commonVariables.hideloading = false;
				});
			}
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
		
		getCommitableFiles : function(data, obj, usrObj, pwdObj, checkObj) {
			var self = this;
			var dynamicId = data.dynamicId;
			self.openccpl(obj, $(obj).attr('name'), '');
			$('#commitLoading_'+dynamicId).show();
			commonVariables.hideloading = true;
	      	self.projectListActionForScm(self.getActionHeader(data, "getCommitableFiles"), $('#commitLoading_'+dynamicId), function(response) {
				$('#commitRepourl_'+dynamicId).val(response.data.repoUrl);
				// For Type Selection based on response
				$("#commitType_"+dynamicId).find('option').each(function(index, value){
					if(response.data.type === $(value).val()){
						$(value).attr('selected', 'selected');
					}
				});	
				// End of Type Selection
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				if ($(obj).offset().top > halfheight && $(obj).offset().left > halfwidth){
					var temp = $(obj).attr('name');
					if(response.responseCode === 'PHR200021') {
						var temp2 = $(obj).offset().top - 520;
						$("#"+temp).css('top',temp2);
					} else {
						var temp2 = $(obj).offset().top - 224;
						$("#"+temp).css('top',temp2);
					}	
				}
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
					commitableFiles += '<thead class="fixedHeader"><tr><th style="width: 5%;"><input checkVal="check" dynamicId="'+ dynamicId +'" class="commitParentChk_'+ dynamicId +'"  type="checkbox"></th><th style="width: 71%;">File</th><th>Status</th></tr></thead><tbody class="commitFixed">';
					$.each(response.data.repoInfoFile, function(index, value) {
						commitableFiles += '<tr><td><input checkVal="check" dynamicId="'+ dynamicId +'" class="commitChildChk_' + dynamicId + '" type="checkbox" value="' + value.commitFilePath + '"></td>';
						commitableFiles += '<td style="width:300px !important;" title="'+ value.commitFilePath +'">"' + value.commitFilePath + '"</td>';
						commitableFiles += '<td>"' + value.status + '"</td></tr>';
					});
					$('.commitable_files_'+dynamicId).html(commitableFiles); 
					$.each(response.data.repoInfoFile, function(index, value) {
						self.checkBoxEvent($('.commitParentChk_'+dynamicId), 'commitChildChk_'+dynamicId, $('input[name=commitbtn]'));
					});
					self.checkAllEvent($('.commitParentChk_'+dynamicId), $('.commitChildChk_'+dynamicId), $('input[name=commitbtn]'));
					setTimeout(function() {
						$('.commitable_files_'+dynamicId+" tbody").mCustomScrollbar({
							autoHideScrollbar:true,
							theme:"light-thin",
							advanced: {
								autoScrollOnFocus: false,
								updateOnContentResize: true
							}
						});
					}, 5);
				}
				
				if (!self.isBlank(response.data.repoInfoFile) && response.data.repoInfoFile.length === 0) {
					$('.commit_data_'+dynamicId).show();
				}
				commonVariables.hideloading = false;
				self.hideShowCredentials(response.data.type, usrObj, pwdObj, checkObj);
				self.typeChangeEvent($("#commitType_"+dynamicId), response.data.type, dynamicId);
			});
		},
		
		getUpdatableFiles : function(data, obj, usrObj, pwdObj, checkObj) {
			var self = this;
			var dynamicId = data.dynamicId;
			self.openccpl(obj, $(obj).attr('name'), '');
			$('#updateLoading_'+dynamicId).show();
			commonVariables.hideloading = true;
	      	self.projectListActionForScm(self.getActionHeader(data, "getUpdatableFiles"), $('#updateLoading_'+dynamicId), function(response) {
				$('#updateRepourl_'+dynamicId).val(response.data.repoUrl);
				// Type Selection based on response
				$("#updateType_"+dynamicId).find('option').each(function(index, value){
					if(response.data.type === $(value).val()){
						$(value).attr('selected', 'selected');
					}
				});
				// End of Type Selection
				var halfheight= window.innerHeight/2;
				var halfwidth= window.innerWidth/2;
				if ($(obj).offset().top > halfheight && $(obj).offset().left > halfwidth){
					var temp = $(obj).attr('name');
					if(response.responseCode === 'PHR200022') {
						var temp2 = $(obj).offset().top - 389;
						$("#"+temp).css('top',temp2);
					} else {
						var temp2 = $(obj).offset().top - 218;
						$("#"+temp).css('top',temp2);
					}	
				} 
	      		$("#dummyUpdate_"+dynamicId).css("height","0");
				var updatableFiles = "";
				$('.update_data_'+dynamicId).hide();
				$('.updateErr_'+dynamicId).hide();      
				if (response.data !== undefined && !response.data.repoExist) {
					$('.updateErr_'+dynamicId).show();
				} else if (response.data !== undefined) {
					$('.update_data_'+dynamicId).show();
				}
				if (!self.isBlank(response.data.repoInfoFile)) {
					$('.update_data_'+dynamicId).show();
				}
				commonVariables.hideloading = false;
				self.hideShowCredentials(response.data.type, usrObj, pwdObj, checkObj);
				self.typeChangeEvent($("#updateType_"+dynamicId), response.data.type, dynamicId);
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
					var headerTr = '<tr class="generatedRow" fileName="'+response.data.json[i].fileName+'" appdirname = "'+temp+'" moduleName="'+ modulename +'"><td>' + response.data.json[i].time + '</td><td>'+response.data.json[i].type+'</td>';
					content = content.concat(headerTr);
					headerTr = '<td class="list_img"><a class="tooltiptop" fileName="'+response.data.json[i].fileName+'" fromPage="All" href="javascript:void(0)" data-toggle="tooltip" data-placement="top" name="downLoad" data-original-title="Download Pdf" title=""><img src="themes/default/images/Phresco/download_icon.png" width="15" height="18" border="0" alt="0"></a></td>';
					content = content.concat(headerTr);
					if(userPermissions.managePdfReports) {
						headerTr = '<td class="list_img"><a class="tooltiptop" name="deletepdf_'+idgenerate+i+'" fileName="'+response.data.json[i].fileName+'" fromPage="All" href="javascript:void(0)" data-toggle="tooltip" data-placement="top" namedel="delete" data-original-title="Delete Pdf" title=""><img src="themes/default/images/Phresco/delete_row.png" width="14" height="18" border="0" alt="0"></a><div style="display:none;" id="deletepdf_'+idgenerate+i+'" class="delete_msg tohide">Are you sure to delete ?<div><input type="button" value="Yes" data-i18n="[value]common.btn.yes" class="btn btn_style dlt" name="delpdf"><input type="button" value="No" data-i18n="[value]common.btn.no" class="btn btn_style dyn_popup_close"></div></div></td></tr>';
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
				$('#'+temp).show();
				var deletedata = {}, actionBody = {}, action;
				deletedata.fileName = $(this).attr("fileName");
				deletedata.fromPage = $(this).attr("frompage");
				deletedata.appDir = $(this).closest('tr').attr("appdirname");
				deletedata.moduleName = self.isBlank($(this).closest("tr").attr("moduleName")) ? "" : $(this).closest("tr").attr("moduleName");
				$('input[name="delpdf"]').unbind();
				$('input[name="delpdf"]').click(function() {
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
					actionBody = deletedata;  
					self.projectListAction(self.getActionHeader(actionBody, "deleteReport"), "", function(response){
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
		
		addUpdateEvent : function(obj, dynid, revision){
			var self = this;
			var updatedata = {}, actionBody, action;
			self.flag3=1;
			if(!self.validation(dynid)) {
				updatedata.type = $("#updateType_"+dynid).val();
				updatedata.repoUrl = $("#updateRepourl_"+dynid).val();
				updatedata.userName = $("#updateUsername_"+dynid).val();
				updatedata.password = $("#updatePassword_"+dynid).val();
				updatedata.passPhrase = $("#updatePhrase_"+dynid).val();
				updatedata.revision = revision;
				updatedata.appdirname = obj.parent("div").attr("appDirName");
				if('perforce' === updatedata.type) {
				updatedata.stream = $("#stream_"+dynid).val();
			}
			
				actionBody = updatedata;
				action = "updateget";
				commonVariables.hideloading = true;
				self.projectListActionForScm(self.getActionHeader(actionBody, action), $("#updateRepoLoading_"+dynid), function(response){
					if (response.exception === null) {
						$("#svn_update"+dynid).hide();
					}
				commonVariables.hideloading = false;
				});
			}
		},
		
		
		hideShowCredentials : function(val, usrObj, pwdObj, checkObj){
			if(val === 'svn') {
				$(".seperatetd").parent().show();
				$(".seperatetd").show();
				if(!checkObj.is(':checked')) {
					usrObj.attr('readonly','true');
					pwdObj.attr('readonly','true');
				}
			} else {
				$(".seperatetd").parent().hide();
				$(".seperatetd").hide();
				usrObj.removeAttr('readonly');
				pwdObj.removeAttr('readonly');
			}			
		},
		
		typeChangeEvent : function(selectObj, selectedType, dynamicId) {
			if($(selectObj).attr('id') === 'updateType_'+dynamicId) {
				$('#temporary_'+dynamicId).remove();
				$('.perforcedata').hide();
				if(selectedType !== 'svn') {
					$("#updatePassword_"+dynamicId).parent().parent().next('tr').hide();
					$("#updatePassword_"+dynamicId).parent().parent().next('tr').next('tr').hide();
					$("#updatePassword_"+dynamicId).parent().parent().append('<td id="temporary_'+dynamicId+'" style="height: 115px;"></td>');								
				} else {
					$("#updatePassword_"+dynamicId).parent().parent().next('tr').show();
					$("#updatePassword_"+dynamicId).parent().parent().next('tr').next('tr').show();
				}
				if(selectedType === 'perforce') {
					$('.perforcedata').show();
					$('#temporary_'+dynamicId).hide();
					$('#updateRepourl_'+dynamicId).attr('placeholder','host:portnumber');
				} else {
					$('#updateRepourl_'+dynamicId).attr('placeholder','Repo Url');
				}		
			}
			
			if(selectedType === 'git') {
				$("input[checkVal=check]").prop('checked', true);
				$("input[checkVal=check]").attr("disabled", true);
				$('input[name=commitbtn]').addClass("btn_style");
				$('input[name=commitbtn]').prop("disabled", false);
				$(".passPhrase").show();
				$(".uname").attr("mandatory", "false");
				$(".pwd").attr("mandatory", "false");
				$("span[name=username]").next().html("");
				$("span[name=password]").next().html("");
			} else {
				$("input[checkVal=check]").attr("disabled", false);
				$("input[checkVal=check]").prop('checked', false);
				$('input[name=commitbtn]').removeClass("btn_style");
				$('input[name=commitbtn]').prop("disabled", true);
				$(".passPhrase").hide();
				$(".uname").attr("mandatory", "true");
				$(".pwd").attr("mandatory", "true");
				$("span[name=username]").next().html("<sup>*</sup>");
				$("span[name=password]").next().html("<sup>*</sup>");
			}
		},
		
		validation : function(dynid) {	
				var self=this;	
				var repourl, uname, pwd, commitRepourl, commitUsername, commitPassword, updateRepourl, updateUsername, updatePassword, revision, mandatoryUser, mandatoryPwd, mandatoryCommitUser, mandatoryCommitPwd, mandatoryUpdateUser, mandatoryUpdatePwd;
				repourl = $("#repourl_"+dynid).val();
				uname = $("#uname_"+dynid).val();
				pwd = $("#pwd_"+dynid).val();
				mandatoryUser = $("#uname_"+dynid).attr("mandatory");
				mandatoryPwd = $("#pwd_"+dynid).attr("mandatory");
				
				commitRepourl = $("#commitRepourl_"+dynid).val();
				commitUsername = $("#commitUsername_"+dynid).val();
				commitPassword = $("#commitPassword_"+dynid).val();
				mandatoryCommitUser = $("#commitUsername_"+dynid).attr("mandatory");
				mandatoryCommitPwd = $("#commitPassword_"+dynid).attr("mandatory");
				
				updateRepourl = $("#updateRepourl_"+dynid).val();
				updateUsername= $("#updateUsername_"+dynid).val();
				updatePassword = $("#updatePassword_"+dynid).val();
				mandatoryUpdateUser = $("#updateUsername_"+dynid).attr("mandatory");
				mandatoryUpdatePwd = $("#updatePassword_"+dynid).attr("mandatory");
				revision = $("#revision_"+dynid).val();
				if(self.flag1 === 1)
				{	
					if(repourl === ''){
						$("#repourl_"+dynid).focus();
						$("#repourl_"+dynid).val('');
						$("#repourl_"+dynid).attr('placeholder','Enter URL');
						$("#repourl_"+dynid).addClass("errormessage");
						setTimeout(function() { 
							$("#repourl_"+dynid).val(repourl); 
						}, 4000);
						self.hasError = true;
					} else if (mandatoryUser === "true") { 
						if(uname === ""){
							$("#uname_"+dynid).focus();
							$("#uname_"+dynid).attr('placeholder','Enter UserName');
							$("#uname_"+dynid).addClass("errormessage");
							self.hasError = true;
						} else if(pwd === ""){
							$("#pwd_"+dynid).focus();
							$("#pwd_"+dynid).attr('placeholder','Enter Password');
							$("#pwd_"+dynid).addClass("errormessage");
							self.hasError = true;
						} else {
							self.hasError=false;
						}
					} else {
						self.hasError=false;
					}
					self.flag1=0;
					return self.hasError;
				}

				else if(self.flag2 === 1)
				{
					if(commitRepourl === ""){
						$("#commitRepourl_"+dynid).focus();
						$("#commitRepourl_"+dynid).val('');
						$("#commitRepourl_"+dynid).attr('placeholder','Enter Repourl');
						$("#commitRepourl_"+dynid).addClass("errormessage");
						setTimeout(function() { 
							$("#commitRepourl_"+dynid).val(repourl); 
						}, 4000);
						self.hasError = true;
					} else if (mandatoryUser === "true") {
						if(commitUsername === ""){
							$("#commitUsername_"+dynid).focus();
							$("#commitUsername_"+dynid).attr('placeholder','Enter UserName');
							$("#commitUsername_"+dynid).addClass("errormessage");
							self.hasError = true;
						} else if(commitPassword === ""){
							$("#commitPassword_"+dynid).focus();
							$("#commitPassword_"+dynid).attr('placeholder','Enter Password');
							$("#commitPassword_"+dynid).addClass("errormessage");
							self.hasError = true;
						} else {
							self.hasError=false;
						}
					} else {
						self.hasError=false;
					}
					self.flag2=0;
					return self.hasError;
				}
				
				else if(self.flag3 === 1)
				{	
				if($("#updateType_"+dynid).val() !== 'perforce') {
						if(updateRepourl === ""){
								$("#updateRepourl_"+dynid).focus();
								$("#updateRepourl_"+dynid).val('');
								$("#updateRepourl_"+dynid).attr('placeholder','Enter Repourl');
								$("#updateRepourl_"+dynid).addClass("errormessage");
								setTimeout(function() { 
									$("#updateRepourl_"+dynid).val(updateRepourl); 
								}, 4000);
								self.hasError = true;
			
						} else if (mandatoryUser === "true") {
							if(updateUsername === ""){
								$("#updateUsername_"+dynid).focus();
								$("#updateUsername_"+dynid).attr('placeholder','Enter UserName');
								$("#updateUsername_"+dynid).addClass("errormessage");
								self.hasError = true;
							} else if(updatePassword === ""){
								$("#updatePassword_"+dynid).focus();
								$("#updatePassword_"+dynid).attr('placeholder','Enter Password');
								$("#updatePassword_"+dynid).addClass("errormessage");
								self.hasError = true;
							} else {
								self.hasError=false;
							}
						} else {
							self.hasError=false;
						}

					}
					else {
						stream = $("#stream_"+dynid).val();
						if($(".stream").hasClass("errormessage")) 
								$(".stream").removeClass("errormessage");

						if(updateRepourl === ""){
								$("#updateRepourl_"+dynid).focus();
								$("#updateRepourl_"+dynid).addClass("errormessage");
								$("#updateRepourl_"+dynid).attr('placeholder','Enter Url');
								setTimeout(function() { 
									$("#updateRepourl_"+dynid).val(updateRepourl); 
								}, 4000);
								self.hasError = true;
			
						} else if(!self.validateperforce(updateRepourl)) {
								$("#updateRepourl_"+dynid).focus();
								$("#updateRepourl_"+dynid).addClass("errormessage");
								$("#updateRepourl_"+dynid).attr('placeholder','Enter Url');
								self.hasError = true;
			
						} else if(updateUsername === ""){
							if($("#updateRepourl_"+dynid).hasClass("errormessage")) 
							$("#updateRepourl_"+dynid).removeClass("errormessage");
							$("#updateUsername_"+dynid).focus();
							$("#updateUsername_"+dynid).attr('placeholder','Enter UserName');
							$("#updateUsername_"+dynid).addClass("errormessage");
							self.hasError = true;
						} else if(stream === ""){
							if($("#updateRepourl_"+dynid).hasClass("errormessage")) 
								$("#updateRepourl_"+dynid).removeClass("errormessage");
								$("#stream_"+dynid).focus();
								$("#stream_"+dynid).attr('placeholder','Enter Stream');
								$("#stream_"+dynid).addClass("errormessage");
								self.hasError = true;
						} else {
							self.hasError=false;
						}

					}	
					self.flag3=0;
					return self.hasError;
				}
		},
		
		validateperforce : function(updateRepourl) {
			if( updateRepourl.indexOf(':') > 0 ){
				var arr=new Array();
				var arr=updateRepourl.split(":"); 
				if(/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/i.test(arr[0]) === true || arr[0] === "localhost" ){
					if(!isNaN(arr[1]) && isFinite(arr[1])){
					return true;
					}
				}	

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