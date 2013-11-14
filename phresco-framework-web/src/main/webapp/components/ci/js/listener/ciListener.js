define([], function() {

	Clazz.createPackage("com.components.ci.js.listener");

	Clazz.com.components.ci.js.listener.CIListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		continuousDeliveryConfigure : null,
		addJobTemplate : null,
		listJobTemplate : null,
		updateJobTemplate : null,
		deleteJobTemplate : null,
		configureJob : null, // configuring the CI job
		ciRequestBody : {},

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.loadingScreen === null) {
				self.loadingScreen = new Clazz.com.js.widget.common.Loading();
			}
		},
		
		loadContinuousDeliveryConfigure : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryConfigure, function(retVal){
				self.continuousDeliveryConfigure = 	retVal;
				retVal.name = null;
				Clazz.navigationController.push(retVal, commonVariables.animation);
			}); 
		},

		loadContinuousDeliveryView : function(response) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryView, function(retVal){
				self.continuousDeliveryView = 	retVal;		
				if(response !== undefined) {
					commonVariables.api.showError(response.responseCode ,"success", true, false, true);
				}
				setTimeout(function() {
					Clazz.navigationController.push(retVal, commonVariables.animation);
				},1200);
				
			}); 
		},

		editContinuousDeliveryConfigure : function(contDeliveryName, envName) {
			var self = this;			
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryConfigure, function(retVal){
				self.edit = retVal;
				self.edit.name = contDeliveryName;
				self.edit.envName = envName;
				Clazz.navigationController.push(self.edit, commonVariables.animation);
			}); 
		},

		editContinuousViewTable : function (response) {		
			var self = this;			
			var data = response.data;
			$("input[type=submit][value=Add]").attr("data-i18n","[value]common.btn.update");	
			$("input[type=submit][value=Add]").attr("value","Update");	
			
			if (!self.isBlank(data)) {
				var sort = $(commonVariables.contentPlaceholder).find('#sortable2');
				sort.empty();
				$('input[name=continuousDeliveryName]').val(data.name);
				$('input[name=continuousDeliveryName]').prop("readonly",true);
				$('select[name=environments]').val(data.envName);
				$.each(data.jobs, function(key, value) {
					var id = value.appName + value.templateName;
					var parentli = $('#'+id).parent();
					var itemText = parentli.find('span').text();
					
					
					var fullName = value.appName  + " - " + itemText;
					var finalText;
					if (fullName.length > 40) {
						finalText = fullName.substring(0,40) + "...";
					} else {
						finalText = fullName;
					}
					
					// Application name construct
					parentli.find('span').text(finalText);
					parentli.attr('title', fullName);
					
					sort.append(parentli);
					// For gear icons alone
					$("#sortable2 li.ui-state-default a").show();
					$("#sortable1 li.ui-state-default a").hide();
					$('#'+id).data("jobJson", value);
					self.downStreamCriteria();
				});	
				self.lastChild();
			}
		},

		getRequestHeader : function(ciRequestBody, action, params) {
			var self = this, appDir = '', projectId='', header;
			// basic params for job templates
			var customerId = self.getCustomer();
			customerId = (customerId === "") ? "photon" : customerId;
			var data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			if(data !== "") { 
				userId = data.id; 
			}
			
			// application level
			if (commonVariables.api.localVal.getProjectInfo() !== null && !commonVariables.projectLevel){
				var projectInfo = commonVariables.api.localVal.getProjectInfo();
				appDir = projectInfo.data.projectInfo.appInfos[0].appDirName;
			// project level
			} else if(commonVariables.projectLevel){
				projectId = $('.hProjectId').val();
			}
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "?" + params;
				}
			} else if (action === "add") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates+ "?customerId="+ customerId + "&projectId=" + projectId;
			} else if (action === "continuousDeliveryList") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci+"/list?projectId="+projectId+"&appDirName="+appDir;
			} else if (action === "update") {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				var oldname = $('[name="oldname"]').val();				
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=" + oldname + "&customerId="+ customerId+"&projectId=" + projectId;
			} else if(action === "validateApp") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates+"/validateApp?projectId="+projectId+"&customerId="+customerId+"&name="+ciRequestBody.name+"&appName="+ciRequestBody.appName;
			} else if (action === "edit") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
				if (params !== null && params !== undefined && params !== '') {
					header.webserviceurl = header.webserviceurl + "/" + params.name + "?customerId="+ customerId + "&projectId=" + projectId;
				}
			} else if (action === "delete") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "getAppInfos") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.projectlistContext + "/appinfos" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			}  else if (action === "validate") {				
				var name = $('input[name="name"]').val();
				var oldname = $('[name="oldname"]').val();
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate" + "?customerId="+ customerId + "&oldname=" + oldname + "&projectId=" + projectId + "&name=" + name;				
			} else if (action === "getEnvironemntsByProjId") {
				header.requestMethod = "GET";
				if(appDir !== null & appDir !== undefined && appDir !== '') {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.configuration + "/environmentList?appDirName="+appDir;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId" + "?customerId="+ customerId + "&projectId=" + projectId;
				}
			}  else if (action === "getJobTemplatesByEnvironment") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;	
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			}  else if (action === "saveContinuousDelivery") {
				// Save the continuos delivery with all the drag and dropped job templates
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/create" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+ appDir + "&userId=" +userId;
			} else if (action === "updateContinuousDelivery") {
				// update the continuos delivery with all the drag and dropped job templates
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/update" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir + "&userId=" +userId;
			}  else if (action === "getBuilds") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/builds?projectId="+projectId+"&name="+ciRequestBody.jobName+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "generateBuild") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/build?name="+ciRequestBody.jobName+"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "lastBuildStatus") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/lastBuildStatus?name="+ciRequestBody.jobName+"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "deleteBuild") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/deletebuilds?buildNumber="+ciRequestBody.buildNumber+"&name="+ciRequestBody.jobName+"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if(action === "download"){
				header.requestMethod = "GET";
				header.webserviceurl  =commonVariables.webserviceurl + commonVariables.ci +"/downloadBuild?buildDownloadUrl=" +ciRequestBody.buildDownloadUrl+"&downloadJobName="+ciRequestBody.jobName+ "&customerId=" + customerId +"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "jobStatus") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/jobStatus?name="+ciRequestBody.jobName+"&continuousName="+ciRequestBody.cdName+"&projectId="+projectId+"&appDirName="+appDir;
			} else if (action === "deleteContinuousDelivery") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/delete?continuousName="+ciRequestBody.cdName+"&customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;
			} else if (action === "cronExpression") {
				self.bcheck = true;
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cronExpression";
			} else if (action === "createClone") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.ci+"/clone?customerId="+ customerId + "&projectId="+projectId+"&appDirName=" + appDir + "&userId=" + userId +"&"+ciRequestBody.data;
			} else if(action === "editContinuousView") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/editContinuousView?projectId="+projectId+"&appDirName="+appDir+"&name=" + params;
			} else if(action === "jenkinsStatus") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/isAlive";
			} else if(action === "writeJson") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl+"app/ciPerformanceTest?"+ciRequestBody.data;
				header.requestPostBody = ciRequestBody.jsondata;
			} else if(action === "jobValidation") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/validation?name="+ciRequestBody.jobName+"&flag="+ciRequestBody.flag+"&userId=" + userId; 
			}

			return header;
		},

		deleteContinuousDelivery: function(obj) {
			var self = this;
			var ciRequestBody = {},name;
			ciRequestBody.cdName = obj.prev().attr("value");
			
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'deleteContinuousDelivery'), function (response) {
				self.loadContinuousDeliveryView(response);
			});
		},
		
		ciStatus:function(obj) {
			var self = this;
			var ciRequestBody = {},name;
			ciRequestBody.jobName=obj.attr("id");
			ciRequestBody.cdName = obj.parent().parent("div").attr("name");
			commonVariables.hideloading = true;
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'jobStatus'), function (response) {
				var parseJson = $.parseJSON(response.data);
				if(parseJson === "red") {
					obj.find('.img_process').attr('src',"themes/default/images/Phresco/cross_red.png");
				} else if (parseJson === "red_anime" || parseJson === "blue_anime" || parseJson ==="grey_anime" || parseJson === "notbuilt_anime") {
					obj.find('.img_process').attr('src',"themes/default/images/Phresco/processing.gif");
				} else if (parseJson === "blue") {
					obj.find('.img_process').attr('src',"themes/default/images/Phresco/tick_green.png");
				}
			});
		},
		
		jenkinsStatus:function(callback) {
			var self = this;
			var ciRequestBody = {};
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'jenkinsStatus'), function (response) {
				var enableOpts, disableOpts;
				if(response.data === "200") {
					disableOpts = document.getElementsByClassName('deadOpts');
					enableOpts = document.getElementsByClassName('aliveOpts');
				} else {
					disableOpts = document.getElementsByClassName('aliveOpts');
					enableOpts = document.getElementsByClassName('deadOpts');
				}
				 for(var i = 0, length = enableOpts.length; i < length; i++) {
					 enableOpts[i].style.display = 'block';
					 disableOpts[i].style.display = 'none';
				 } 
				 callback(response);
			});
		},
		
		getBuilds: function(obj,job, name, operation) {
			var self = this;
			var ciRequestBody = {},jobName,continuousName;
			if (!self.isBlank(job)) {
				jobName = job;
				continuousName = name;
				ciRequestBody.jobName =job;
				ciRequestBody.continuousName = name;
			} else {
				ciRequestBody.jobName = obj.attr("jobName");
				jobName = ciRequestBody.jobName;
				ciRequestBody.continuousName = obj.closest('div[class=widget_testing]').attr('name');
				continuousName = ciRequestBody.continuousName;
			}
 			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'getBuilds'), function (response) {
				$("tbody[name=buildList]").empty();
				var content = "";
				if(response.data !== undefined && response.data !== null && response.data !== "" && response.data.length > 0) {
					for(var i =0; i < response.data.length; i++) {
						var headerTr = '<tr><td>'+response.data[i].timeStamp;
						content = content.concat(headerTr);
						if(response.data[i].status === "FAILURE") {
							headerTr = '</td><td><img src="themes/default/images/Phresco/red_deactive.png"></td>';
						} else {
							headerTr = '</td><td><img src="themes/default/images/Phresco/green_active.png"></td>';
						}
						content = content.concat(headerTr);
						if(response.data[i].status === "SUCCESS" && (operation === 'build' || operation === 'pdfReport')) {
							var url = (response.data[i].download).replace(/\"/g, ""); 
							headerTr = '<td><center><a href="javascript:void(0)" data-placement="top" continuousName="'+continuousName+'" jobName="'+jobName+'"buildNumber="'+response.data[i].number+'"buildDownloadUrl="'+ url +'"temp="downloadBuild"><img src="themes/default/images/Phresco/download_icon.png" width="14" height="18" border="0" alt="0"></a><center></td>';
						} else {
							headerTr = '<td><center><img src="themes/default/images/Phresco/cross_red.png" width="14" height="18" border="0" alt="0"><center></td>';
						}
						content = content.concat(headerTr);
						headerTr = '<td><a href="javascript:void(0)" data-placement="top" continuousName="'+continuousName+'" jobName="'+jobName+'"buildNumber="'+response.data[i].number+'"temp="deleteBuild"><img src="themes/default/images/Phresco/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>';
						content = content.concat(headerTr);
					}
					
					$("tbody[name=buildList]").append(content);
				} else {
					$("tbody[name=buildList]").html("No Builds are Available");
				}
				self.BuildsOnClick(response);
				
			});
		},
		
		BuildsOnClick: function(obj) {
			var self = this;
			$("a[temp=deleteBuild]").click(function() {
				self.deleteBuild($(this));
			});
			
			$("a[temp=downloadBuild]").click(function() {
				self.downloadBuild($(this));
			});
		},
		
		deleteBuild: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName,continuousName;
			ciRequestBody.jobName = obj.attr("jobName");
			ciRequestBody.buildNumber = obj.attr("buildNumber");
			ciRequestBody.continuousName = obj.attr('continuousName');
			continuousName = ciRequestBody.continuousName;
			jobName = ciRequestBody.jobName;
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'deleteBuild'), function (response) {
				self.getBuilds(response,jobName, continuousName);
			});
		},
		
		downloadBuild: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName,continuousName;
			ciRequestBody.jobName = obj.attr("jobName");
			ciRequestBody.buildNumber = obj.attr("buildNumber");
			ciRequestBody.continuousName = obj.attr('continuousName');
			ciRequestBody.buildDownloadUrl = obj.attr('buildDownloadUrl');
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'download'), function (response) {
				window.open(response.data);
			});
		},
		
		listEnvironment: function(obj) {
			var self = this;
			var ciRequestBody = {};
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'getEnvironemntsByProjId'), function (response) {
				var content = "";
				$("select[name=envName]").empty();
				if(response.data !== undefined && response.data !== null && response.data !== "" && response.data.length > 0) {
					for(var i =0; i < response.data.length; i++) {
						var headerTr = '<option value="'+response.data[i]+'">'+response.data[i]+'</option>';
						content = content.concat(headerTr);
					}
					$("select[name=envName]").append(content);
				}
			});
		},
		
		generateBuild: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName;
			ciRequestBody.jobName = obj.attr("jobName");
			jobName = ciRequestBody.jobName;
			ciRequestBody.continuousName = obj.closest('div[class=widget_testing]').attr('name');
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'generateBuild'), function (response) {
			});
		},
		
		lastBuildStatus: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName;
			ciRequestBody.jobName = obj.attr("jobName");
			jobName = ciRequestBody.jobName;
			ciRequestBody.continuousName = obj.closest('div[class=widget_testing]').attr('name');
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'lastBuildStatus'), function (response) {
				$("#buildStatus").empty();
				var content = "";
				var timeStamp = "Nil";
				var number = "Nil";
				var status = "Nil";
				if(response.data !== undefined && response.data !=="" && response.data !==null) {
					timeStamp = response.data.timeStamp;
					number = response.data.number;
					status = response.data.status;
				}
				
				var headerTr = '<h1>Last Build Status</h1><div><b>Executed:</b>'+timeStamp+'<br><b>BuildNumber:</b>'+number+'<br>';
				content = content.concat(headerTr);
				if(status === "SUCCESS") {
					headerTr = '<b>Status:</b><span class=completedinfo>'+status+"<span></div>";
				} else if(status === "FAILURE"){
					headerTr = '<b>Status:</b><span class=failureinfo>'+status+"<span></div>";
				} else {
					headerTr = '<b>Status:</b><span>'+status+"<span></div>";
				}
				content = content.concat(headerTr);
				$("#buildStatus").append(content);
				
			});
		},
		
		cloneCi: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName;
			ciRequestBody.data = obj.closest("form").serialize();
			if(self.cloneValidation()) {
				self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'createClone'), function (response) {
					self.loadContinuousDeliveryView(response);
				});
			}
		},
		
		cloneValidation: function(obj) {
			$('#errMsg').html('');
			var self=this;
			var status = true;
			var name = $("input[name=cloneName]").val();
			if(name === "" || name === undefined) {				
				$("input[name='cloneName']").focus();
				$("input[name='cloneName']").attr('placeholder','Enter Name');
				$("input[name='cloneName']").addClass("errormessage");
				$("input[name='ncloneNameame']").bind('keypress', function() {
					$(this).removeClass("errormessage");
				});
				status = false;				
			}
			
			return status;
		},
		
		validate : function (callback) {
			$('#errMsg').html('');
			var self=this;
			var status = true;
			$("#errMsg").removeClass("errormessage");
			$('input[name=name]').removeClass("errormessage");	
			$('#errMsg').html('');
			var features = $("select[name=features]").val();
			if (features !== null) {
				if (features.indexOf('enableUploadSettings') > -1) {
					var uploads = $('select[name=uploadTypes] :selected').length;
					if (uploads === 0) {
						$('#errMsg').html('Select atleast one Uploader');
						$("select[name='uploadTypes']").focus();
						$('#errMsg').addClass("errormessage");
						$('select[name=uploadTypes]').next().find('button.dropdown-toggle').addClass("btn-danger");
						status = false;	 
					}
				} 
			} 
			
			var appIds = $('select[name=appIds] :selected').length;
			if(appIds === 0) {
				$('#errMsg').html('Select atleast one application');
				$("select[name='appIds']").focus();
				$('#errMsg').addClass("errormessage");
				$('select[name=appIds]').next().find('button.dropdown-toggle').addClass("btn-danger");
				status = false;	 											
			}	
		
			var name = $("[name=name]").val();			
			if(name === "") {				
				$("input[name='name']").focus();
				$("input[name='name']").attr('placeholder','Enter Name');
				$("input[name='name']").addClass("errormessage");
				$("input[name='name']").bind('keypress', function() {
					$(this).removeClass("errormessage");
				});
				status = false;				
			}

			return status;
		},

		
		validateName : function(operation, thisObj, callback) {
			var self = this;
			// basci ui validation
			var status = self.validate();
			if (status) {
				var self = this;
				self.ciRequestBody = {};
					// save or update operation
				 if (operation === 'save') {
					self.addJobTemplate(function(response) {
						self.ciRequestBody = response;
						self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'add'), function(response){
							if(!self.isBlank(response.data)) {
								callback(response); 
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true, false, true);
							}
						});
					});	
				} else if (operation === 'update') {
					self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'validate'), function (response) {
						//update operation
						if (!self.isBlank(response) && response.data) {	
							self.updateJobTemplate(function(response) {
								self.ciRequestBody = response;
								self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'update'), function(response) {
									callback(response);
								});								
							});
						} else {
							$("input[name='name']").focus();
							$("input[name='name']").val('');
							$("input[name='name']").attr('placeholder','Name Already Exists');
							$("input[name='name']").addClass("errormessage");
							$("input[name='name']").bind('keypress', function() {
								$(this).removeClass("errormessage");
							});
						}
					});
				} 
			}
		}, 

		addJobTemplate : function (callback) {
			var self = this;
			var jobTemplate = self.constructJobTemplate();

			callback(jobTemplate);
		},

		listJobTemplate : function (header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header, function(response) {
						if (response !== null && (response.status !== "error" || response.status !== "failure")) {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							commonVariables.loadingScreen.removeLoading();
							$(".content_end").show();
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".content_end").hide();
							},2500);
						}

					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
						self.renderlocales(commonVariables.contentPlaceholder);		
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
				callback({ "status" : "service exception"});
			}
		},

		getHeaderResponse : function (header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
						if (response !== null && (response.status !== "error" || response.status !== "failure")) {
							callback(response);
						} else {
							$(".content_end").show();
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".content_end").hide();
							},2500);
						}

					},

					function(textStatus) {						
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
						self.renderlocales(commonVariables.contentPlaceholder);		
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					}
				);
			} catch(exception) {
				callback({ "status" : "service exception"});
			}
		},

		openJobTemplate : function () {
			var self = this;
			var jobTemplate = self.constructJobTemplate();
		},

		updateJobTemplate : function (callback) {
			var self = this;
			var jobTemplate = self.constructJobTemplate();
			callback(jobTemplate);
		},

		constructJobTemplate : function () {
			var self=this;
			var formObj = $("#jobTemplate"); 

			var jobTemplate = $('#jobTemplate :input[name!=oldname][name!=features][name!=repoTypes]').serializeObject();

			jobTemplate.enableRepo = false;
			jobTemplate.enableSheduler = false;
			jobTemplate.enableEmailSettings = false;
			jobTemplate.enableUploadSettings = false;
			// Convert appIds to array
			jobTemplate.appIds = [];
			$('select[name=appIds] :selected').each(function(i, selected) {
				jobTemplate.appIds.push(this.value);
			});
			if(!self.isBlank($('input[name=name]').attr('module'))) {
				jobTemplate.module = $('input[name=name]').attr('module');
				var option = $('select[name=appIds]').find('optgroup').attr('label');
				jobTemplate.appIds.push(option);
			}

			var features = $("select[name=features]").val();
			if (features !== null) {
				if (features.indexOf('enableRepo') > -1) {
					jobTemplate.enableRepo = true;
					jobTemplate.repoTypes = $('select[name=repoTypes]').val();
				} 
				
				if (features.indexOf('enableSheduler') > -1) {
					jobTemplate.enableSheduler = true;
				} 
				
				if (features.indexOf('enableEmailSettings') > -1) {
					jobTemplate.enableEmailSettings = true;
				} 
				
				if (features.indexOf('enableUploadSettings') > -1) {
					jobTemplate.enableUploadSettings = true;
				} 
			} 
			
			jobTemplate.uploadTypes = [];
			if(jobTemplate.enableUploadSettings) {
				$('select[name=uploadTypes] :selected').each(function(i, selected){ 
					jobTemplate.uploadTypes.push(this.value);				
				});
			}
			
			return jobTemplate;
		},

		preOpen : function (callback) {
			var self = this;
			$(".repoDiv").hide();
			$(".uploadDiv").hide();
			$('select[name=features]').selectpicker('deselectAll');
			$('select[name=appIds]').selectpicker('deselectAll');
			$('select[name=uploadTypes]').selectpicker('deselectAll');
//			$('.selectpicker').selectpicker('render');
			self.removeDangerClass($('select[name=appIds]'));
			self.removeDangerClass($('select[name=uploadTypes]'));
			$("input[name=enableUploadSettings]").attr("disabled","disabled");
			callback();
		},

		preEdit : function (callback) {
			
			var self = this;
			$("#errMsg").removeClass("errormessage");
			$('input[name=name]').removeClass("errormessage");	
			$('#errMsg').html('');
			$('select[name=appIds]').selectpicker('deselectAll');
			$('select[name=uploadTypes]').selectpicker('deselectAll');
			$('select[name=features]').selectpicker('deselectAll');
//			$('.selectpicker').selectpicker('render');
			self.removeDangerClass($('select[name=appIds]'));
			self.removeDangerClass($('select[name=uploadTypes]'));
			callback();
		},

		changeOperation : function() {
			var self = this;
			var operation = $("select[name=type]").val();
			$("select[name=features]").selectpicker('deselectAll');
			if (operation === 'build' || operation === 'pdfReport') {
				$("#upload").removeAttr("disabled");
			} else {
				$("#upload").attr("disabled", "disabled");
			}
			self.changeFeatures();
		},
		
		changeFeatures : function() {
			var features = $("select[name=features]").val();
			if (features !== null) {
				
				if (features.indexOf('enableRepo') > -1) {
					$('.repoDiv').show();
				} else {
					$('.repoDiv').hide();
				}
				
				if (features.indexOf('enableUploadSettings') > -1) {
					$('.uploadDiv').show();
				} else {
					$('.uploadDiv').hide();
				}
			} else {
				$('.repoDiv').hide();
				$('.uploadDiv').hide();
			}
		},

		editJobTemplate : function (data) {
			var self = this;
			$("[name=name]").val(data.name);
			$("[name=oldname]").val(data.name);
			$("[name=type]").selectpicker('val', data.type);
			//AppIds
			var obj = $('select[name=appIds]');
			if (self.isBlank(data.module)) {
				$("select[name=appIds]").selectpicker('val', data.appIds);
			} else {
				obj.html('');
				optGroup = document.createElement('optgroup');
				optGroup.label = data.appIds;
				
				objOption=document.createElement("option");
				objOption.innerHTML = data.module;
				objOption.value = data.module;
				objOption.appName = data.appIds;
				objOption.setAttribute('selected', 'selected');
				objOption.setAttribute('disabled', 'disabled');
				optGroup.appendChild(objOption);
				
				obj.append(optGroup);
			}
			$("select[name=appIds]").selectpicker('refresh');
			$("[name=repoTypes]").val(data.repoTypes);
			
			var features = [];
			if(data.enableRepo) {
				features.push('enableRepo');
				$(".repoDiv").show();
			}  else {
				$(".repoDiv").hide();
			}
			
			if(data.enableSheduler) {
				features.push('enableSheduler');
			} 
			
			if(data.enableEmailSettings) {
				features.push('enableEmailSettings');
			} 
			
			if (!self.isBlank(data.module)) {
				$("[name=name]").attr('module', data.module);
			} else {
				$("[name=name]").attr('module', '');
			}
			
			//Uploaders
			if(data.enableUploadSettings) {
				features.push('enableUploadSettings');
				$("select[name=uploadTypes]").selectpicker('val', data.uploadTypes);
				$(".uploadDiv").show();
			}  else {
				$(".uploadDiv").hide();
			}
			
			$("select[name=features]").selectpicker('val', features);
			
			// button name change
			$('input[name=save]').prop("value", "Update");
			$('input[name=save]').prop("name", "update");
			for (var i=0; i < data.appIds.length; i++) {
				
				var ciRequestBody = {};
				ciRequestBody.appName = data.appIds[i];
				ciRequestBody.name = data.name;
				self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'validateApp'), function (response) {
					var str = [];
					str = response.data.split("#SEP#");
					if(str[1] === "false") {
						$("select[name=appIds]").find("option[class='"+str[0]+"']").attr('disabled', 'disabled');
					}
				});
				
				
				
			}
		},
				
		//remove error class for multiselect
		removeDangerClass : function(obj) {			
			obj.next().find('button.dropdown-toggle').removeClass("btn btn-danger"); 
			obj.next().find('button.dropdown-toggle').addClass("btn dropdown-toggle");			
		},
		
		deleteJobTemplate : function () {
			var self = this;
		},

		loadEnvironmentEvent : function (callback) {
			var dataObj = {};
			var environment = $("select[name=environments]").val();
			dataObj.envName = environment;
			callback(dataObj);
		},

		cronReverser : function(Obj) {
			var self = this;
			var schedule = Obj.scheduleType;
			self.currentEvent(schedule, '');
			
			var CronExpre = Obj.scheduleExpression;
			if(CronExpre !== null && CronExpre !== undefined) {
				var cronSplit = [];
				cronSplit = CronExpre.split(" ");
				if(schedule === "Daily") {
					$('input[name=scheduleType][value=Daily]').attr('checked',true);
					if(CronExpre.indexOf("/") != -1) {
						var every = $('#schedule_daily').find('input[name=everyAt]');
						every.prop('checked', true);
					}
					
		            if (cronSplit[1].indexOf("/") != -1) {
		            	var hours = $('#schedule_daily').find('select[class=selectpicker][name=hours]');
		            	hours.selectpicker('val', cronSplit[1].substring(2) + "");
		            } else {
		            	var hours = $('#schedule_daily').find('select[class=selectpicker][name=hours]');
		            	hours.selectpicker('val', cronSplit[1]);
		            }
		            
					if (cronSplit[0].indexOf("/") != -1) {
						var minutes = $('#schedule_daily').find('select[class=selectpicker][name=minutes]');
		            	minutes.selectpicker('val', cronSplit[0].substring(2) + "");
		            } else {
		            	var minutes = $('#schedule_daily').find('select[class=selectpicker][name=minutes]');
		            	minutes.selectpicker('val', cronSplit[0]);
		            }
				} else if(schedule === "Weekly") {
					$('input[name=scheduleType][value=Weekly]').attr('checked',true);
					var weeks = $('#schedule_weekly').find('select[class=selectpicker][name=weeks]');
					var hours = $('#schedule_weekly').find('select[class=selectpicker][name=hours]');
					var minutes = $('#schedule_weekly').find('select[class=selectpicker][name=minutes]');
					weeks.selectpicker('val', cronSplit[4]);
					hours.selectpicker('val', cronSplit[1]);
					minutes.selectpicker('val', cronSplit[0]);
				} else if(schedule === "Monthly") {
					$('input[name=scheduleType][value=Monthly]').attr('checked',true);
					var day = $('#schedule_monthly').find('select[class=selectpicker][name=days]');
					var month = $('#schedule_monthly').find('select[class=selectpicker][name=months]');
					var hour = $('#schedule_monthly').find('select[class=selectpicker][name=hours]');
					var minute = $('#schedule_monthly').find('select[class=selectpicker][name=minutes]');
					day.selectpicker('val', cronSplit[2]);
					month.selectpicker('val', cronSplit[3]);
					hour.selectpicker('val', cronSplit[1]);
					minute.selectpicker('val', cronSplit[0]);
				}
			}
			
		},
		
		showConfigureJob : function(thisObj) {
			$('input[name=jobName]').removeClass('errormessage');
			$("input[name=jobName]").attr('placeholder','Enter Name');
			$('input[name=jobName]').val('');
			$('#errMsg').html('');
			$('#errMsg').removeClass('errormessage');
			//enable scheduler & downStreamCriteria options 
			$("select[name=downStreamCriteria]").attr('disabled', false);
			$('[name=scheduleExpression]').attr('disabled', false);
//			$("input[name=scheduleType]").attr('disabled', false);
			$("input[name=everyAt]").attr('disabled', false);
			$("select[name=minutes]").attr('disabled', false);
			$("select[name=hours]").attr('disabled', false);	
			$("select[name=days]").attr('disabled', false);			
			$("select[name=weeks]").attr('disabled', false);
			$("select[name=months]").attr('disabled', false);
			$("input[name=triggers]").attr('disabled', false);
			var self = this;
			var templateJsonData = $(thisObj).data("templateJson");
			var jobJsonData = $(thisObj).data("jobJson");			
			// elements
			var repoTypeElemUrl = $("#repoType tbody tr[id='url']");
			var repoTypeElemCred = $("#repoType tbody tr[id='cred']");
			var jobNameTrElem = $("#jobName tbody tr td");
			var repoTypeTitleElem = $("#repoType thead tr th");

			// Repo types			
			if (templateJsonData.enableRepo && templateJsonData.repoTypes === "svn") {
				// For svn
				$(repoTypeElemUrl).html('<td colspan="2"><input type="text" placeholder="SVN Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>');
				
				$(repoTypeElemCred).html('<td><input type="text" placeholder="Username" name="username"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else if (templateJsonData.enableRepo && templateJsonData.repoTypes === "git") {
				// For GIT
				$(repoTypeElemUrl).html('<td colspan="3"><input type="text" placeholder="GIT Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>');
				
				$(repoTypeElemCred).html('<td><input type="text" placeholder="Username" name="username"></td>'+
                      '<td><input type="text" placeholder="Branch" name="branch"></td>'+
                      '<td><input type="password" placeholder="Password" name="password"></td>');
			} else {				
				//For clonned workspace
				$(repoTypeElemUrl).html('<input name="repoType" type="hidden" value="clonedWorkspace">');
				$(repoTypeElemCred).html('');
				//set label value
				$(repoTypeTitleElem).html("Clonned workspace");
			}
			
			if(!templateJsonData.enableVersion) {
				$("#jonConfiguration").append('<label>Version</label><select><option>1</option></select>');
			}
			
			if(!templateJsonData.enableParameters) {
				$("#jonConfiguration").append('<label>Parameters</label><input type="text">');
			}
			
			if(!templateJsonData.enableCriteria) {
				$('#criteria').hide();
			} else {
				$('#criteria').show();
			}

			// Upload configurations
			$("#uploadSettings").empty();
			if (templateJsonData.enableUploadSettings) {

				//elements
				var uploadSettingsElem = $("#uploadSettings");
				uploadSettingsElem.html();
				// uploadType
				var upload = templateJsonData.uploadTypes;
				for (var i=0; i < upload.length; i++) {
   				 // Iterates over numeric indexes from 0 to 5, as everyone expects
					if (upload[i] === "Collabnet") {
						var uploadSettingsHtml = '<table id="collabnetUploadSettings" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
	                    '<thead><tr><th colspan="3">CollabNet Upload Settings</th></tr></thead>'+
	                    '<tbody>'+
	                    '<tr><td colspan="3">Overwrite Files ?<input type="radio" value="true" name="collabNetoverWriteFiles" checked>Yes<input type="radio" value="false" name="collabNetoverWriteFiles">No</td></tr>'+
	                    '<tr><td><input name="collabNetURL" type="text" placeholder="Url"></td><td><input type="text" name="collabNetusername" placeholder="Username"></td><td><input type="password" name="collabNetpassword" placeholder="Password"></td></tr>'+
	                    '<tr><td><input name="collabNetProject" type="text" placeholder="Project"></td><td><input name="collabNetPackage" type="text" placeholder="Package"></td><td><input name="collabNetRelease" type="text" placeholder="Release"></td></tr>'+
	                  	'<input name="enableBuildRelease" type="hidden" value="true">'+
	                    '</tbody>'+
	                	'</table>';

						$(uploadSettingsElem).append(uploadSettingsHtml);
					} 

					if (upload[i] === "Confluence") {
						var uploadSettingsHtml = '<table id="confluenceUploadSettings" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
	                    '<thead><tr><th colspan="3">Confluence Upload Settings</th></tr></thead>'+
	                    '<tbody>'+
	                    '<tr>'+  
                    	'<tr><td>Confluence Site<br><input name="confluenceSite" type="text" placeholder="Ip"></td></tr>'+
	                    '<tr><td><input name="confluenceSpace" type="text" placeholder="Space"></td><td><input name="confluencePage" type="text" placeholder="Page"></td></tr>'+
	                    '<tr><td><input name="confluenceOther" type="text" placeholder="Other files to attach"></td></tr>'+
                    	'<td colspan="3">'+                                       
                                        '<input id="Publish" name="confluencePublish" type="checkbox" value="">'+
                                        '<span> Publish even if build is unstable</span>'+
                                        '<input id="archive" name="confluenceArtifacts" type="checkbox" value="">Attach archived artifacts to page</td></tr>'+
                       	'<input name="enableConfluence" type="hidden" value="true">'+
                        '</tbody>'+
	                	'</table>';
						$(uploadSettingsElem).append(uploadSettingsHtml);
					}

					if (upload[i] === "Cobertura") {
						var uploadSettingsHtml = '<table id="coberturaUploadSettings" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
	                 	'<thead><tr><th colspan="3">Cobertura Upload Settings</th></tr></thead>'+
	                    '<tbody>'+
						'<input name="coberturaPlugin" type="hidden" value="true">'+
                     	'</tbody>'+
	                	'</table>';
						$(uploadSettingsElem).append(uploadSettingsHtml);
					}
				}
			}

			//scheduler
			if (!templateJsonData.enableSheduler) {
				$("#scheduler").hide();
				$("#cronPreview").hide();
			} else {
				$("#scheduler").show();
				$("#cronPreview").show();
			}

			//mail
			if (!templateJsonData.enableEmailSettings) {
				$("#mailSettings").remove();
			}

			//upstream config
			$("#downstreamConfig").remove();

			// Get app name
			var appName = $(thisObj).attr("appname");
			var appDirName = $(thisObj).attr("appDirName");
			var jobtemplatename = $(thisObj).attr("jobtemplatename");
			var templateJsonModule = templateJsonData.module;
			var moduleName = self.isBlank(templateJsonModule) ? "" : templateJsonModule;
			
			// set corresponding job template name and their app name in configure button
			$("[name=configure]").attr("appname", appName);
			$("[name=configure]").attr("appDirName", appDirName);
			$("[name=configure]").attr("jobtemplatename", jobtemplatename);
			$("[name=configure]").attr("module", moduleName);


			// Dynamic param
			var operation = templateJsonData.type;

			if ("codeValidation" === operation) {
				commonVariables.goal = commonVariables.codeValidateGoal;
			} else if ("build" === operation) {
				commonVariables.goal = commonVariables.packageGoal;
			} else if ("deploy" === operation) {
				commonVariables.goal = commonVariables.deployGoal;
			} else if ("unittest" === operation) {
				commonVariables.goal = commonVariables.unitTestGoal;
			} else if ("componentTest" === operation) {
				commonVariables.goal = commonVariables.componentTestGoal;
			} else if ("functionalTest" === operation) {
				commonVariables.goal = commonVariables.functionalTestGoal;
			} else if ("performanceTest" === operation) {
				commonVariables.goal = commonVariables.performanceTestGoal;
			} else if ("loadTest" === operation) {
				commonVariables.goal = commonVariables.loadTestGoal;
			} else if ("pdfReport" === operation) {
				commonVariables.goal = commonVariables.pdfReportGoal;
			}

			commonVariables.phase = commonVariables.ciPhase + commonVariables.goal; // ci phase
			commonVariables.appDirName = appDirName;
			commonVariables.moduleName = moduleName;
			
			self.CroneExpression();
			if (!self.isBlank(jobJsonData) && templateJsonData.enableSheduler) {
				self.cronReverser(jobJsonData);
			}
			$('#dynamicContent ul').empty();
			$('#dynamicContent div[class=hiddenControls]').empty();
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();

				// Restore existing values
				// get all the elemenst of the form and iterate through that, check for type and populate the value
                var whereToRender = $('#dynamicContent ul');
				dynamicPageObject.getHtml(whereToRender, thisObj, 'jobConfigure', function(response) {
					// Restore existing values
					// get all the elemenst of the form and iterate through that, check for type and populate the value	
					var EnvName = $(thisObj).attr("envname");
					$("#environmentName").val(EnvName);
					$("#environmentName").selectpicker('refresh');
					if (!self.isBlank(jobJsonData)) {
						$('input[name=jobName]').val(jobJsonData.jobName);
						self.restoreFormValues($("#jonConfiguration"), jobJsonData);
						if (jobJsonData.confluencePublish === true || jobJsonData.confluencePublish === "true") {
							$("#Publish").val("true");
							$("#Publish").attr('checked', true);
						} 
						if (jobJsonData.confluenceArtifacts === true || jobJsonData.confluenceArtifacts === "true") {
							$("#archive").val("true");
							$("#archive").attr('checked', true);
						} 
						var fetchSql = true;
						if (jobJsonData.fetchSql === "{}") {
							fetchSql = false;
						} 	
					}
					if (templateJsonData.enableRepo && templateJsonData.repoTypes === "svn" || templateJsonData.enableRepo && templateJsonData.repoTypes === "git") {
						$("input[name=repoType]").val(templateJsonData.repoTypes);
					} else {
						$(repoTypeElemUrl).html('<input name="repoType" type="hidden" value="clonedWorkspace">');
					}
					
					if(templateJsonData.enableUploadSettings) {
						var upload = templateJsonData.uploadTypes;
						for (var i=0; i < upload.length; i++) {
							if (upload[i] === "Collabnet") {
								$("input[name=enableBuildRelease]").val("true");
							}
							if (upload[i] === "Confluence") {
								$("input[name=enableConfluence]").val("true");
							}
							if (upload[i] === "Cobertura") {
								$("input[name=coberturaPlugin]").val("true");
							}
						}
					}
					self.popForCi(thisObj, 'jobConfigure');
					if (commonVariables.goal === commonVariables.performanceTestGoal || commonVariables.goal === commonVariables.loadTestGoal) {
						var sectionHeight = $('.content_main').height();
						$('#jobConfigure').css("max-height", sectionHeight+ 'px');
						$('#jonConfiguration').css("max-height", sectionHeight -34+ 'px');
					} else {
						$('#jobConfigure').css("max-height", 'auto');
						$('#jonConfiguration').css("max-height", 'auto');
					}
					self.dragDrop();
					self.chkSQLCheck();
				});
			});
		},

		CroneExpression : function() {
			var self=this;
			$("#cronepassword").click(
				
				function openccvar() {
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $("#cronepassword");
				var target = $("#crone_triggered");
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
				}
				else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2;
					var l=clicked.offset().left - (target.width()+ 15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			
				}
				self.closeAll(target);
				
			});
			
			$("input[name=dyn_popupcon_close]").click(function() {
				$(this).parent().parent().hide();
			});
			
			self.currentEvent($('input[name=scheduleType]:checked').val(), '');
			$('input[name=scheduleType]').bind('click', function() {
				$(this).attr('checked', true);
				self.currentEvent($(this).val(), '');
			});
			
			$(".dyn_popupcon_close").click(
			function() {
				
				$(".dyn_popupcon_close").parent().parent().hide();
			});
		},
		
		closeAll : function(placeId) {
			
			$(document).keyup(function(e) {
				if(e.which === 27){
					$(placeId).hide();
					$('#header').css('z-index','7');
					$('.content_title').css('z-index','6');
				}
			});
			
			$('.dyn_popup_close').click( function() {
				$(placeId).hide();
				$("#cron_expression").hide();
				$('#header').css('z-index','7');
				$('.content_title').css('z-index','6');
			});
				
		},
		
		
		
		currentEvent : function(value, WhereToAppend) {
			var self=this, dailySchedule, weeklySchedule, monthlySchedule, toAppend;
			dailySchedule = '<tr id="schedule_daily" class="schedule_date"><td>Every At<input type="checkbox" name="everyAt"></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			weeklySchedule = '<tr id="schedule_weekly" class="schedule_date"><td><select name="weeks" class="selectpicker" multiple data-selected-text-format="count>2">'+self.weeks()+'</select><span>Weeks</span> <span>at</span></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			monthlySchedule = '<tr id="schedule_monthly" class="schedule_date"><td><span>Every</span><select name="days" class="selectpicker">'+self.days()+'</select></td><td><span>of</span><select name="months" class="selectpicker" multiple data-selected-text-format="count>2">'+self.months()+'</select><span>Months</span></td><td><span>at</span><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			$('.schedule_date').remove();
			if (WhereToAppend === "") {
				toAppend = $('#scheduleExpression:last');
			} else {
				toAppend = WhereToAppend;
			}
			if (value === 'Daily') {
				$(dailySchedule).insertAfter(toAppend);
			} else if (value === 'Weekly') {
				$(weeklySchedule).insertAfter(toAppend);
			} else {
				$(monthlySchedule).insertAfter(toAppend);
			}
			self.multiselect();
			self.cronExpressionValues(value);
		},
		
		cronExpressionValues : function (value) {
			var self=this, croneJson = {};
			if (value === 'Daily') {
				croneJson.cronBy = value;
				croneJson.every = $('input[name=everyAt]').is(':checked');
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('input[name=everyAt], select[name=hours], select[name=minutes]').bind('change', function(){
					croneJson.every = $('input[name=everyAt]').is(':checked');
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});

			} else if (value === 'Weekly') {
				var weeks = [], val;
				croneJson.cronBy = value;
				if ($('select[name=weeks]').val() === null) {
					val = '*';
				}
				weeks.push(val);
				croneJson.week = weeks;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=weeks], select[name=hours], select[name=minutes]').bind('change', function(){
					var weeks = [];
					if ($('select[name=weeks]').val() === null) {
						val = '*';
						weeks.push(val);
						croneJson.week = weeks;
					} else {
						croneJson.week = $('select[name=weeks]').val();
					}
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			} else {
				var month = [], val;
				croneJson.cronBy = value;
				if ($('select[name=months]').val() === null) {
					val = '*';
				}
				croneJson.day = $('select[name=days]').val();
				month.push(val);
				croneJson.month = month;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=days], select[name=months], select[name=hours], select[name=minutes]').bind('change', function(){
					var months = [];
					if ($('select[name=months]').val() === null) {
						val = '*';
						months.push(val);
						croneJson.month = months;
					} else {
						croneJson.month = $('select[name=months]').val();
					}
					croneJson.day = $('select[name=days]').val();
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			}
		},
		
		cronExpressionLoad : function (croneJson) {
			var self=this, i, tr;
			self.ciRequestBody = croneJson;
			self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'cronExpression'), function (response) {
				$("input[name=scheduleExpression]").val(response.data.cronExpression);
				$('tbody[name=scheduleDates]').html('');
				if (response.data.dates !== null) {
					for (i=0; i<response.data.dates.length; i++) {
						tr += '<tr><td>'+response.data.dates[i]+'</td></tr>';
					}
				}
				$('tbody[name=scheduleDates]').append(tr);
			});
			
		},
		
		hours : function() {
			var self=this, option='', i;
				option = '<option>*</option>';
			for(i=0; i<24; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		minutes : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=0; i<60; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		days : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=1; i<32; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		weeks : function () {
			var self=this, option='', i, weeks = ['*', 'Sunday', 'Monday', 'Tuesday', 'Wendesday', 'Thursday', 'Friday', 'Saturday'];
			
			for(i=0; i<weeks.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+weeks[i]+'</option>';
			}
			return option;
		},
		
		months : function () {
			var self=this, option='', i, months = ["*","January","February","March","April","May","June","July","August","September","October","November","December"];
			
			for(i=0; i<months.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+months[i]+'</option>';
			}
			return option;
		},
		
		
		restoreFormValues : function (formObj, data) {
			var self = this;

		    if (!self.isBlank(formObj)) {
		    	for ( var i = 0; i < $(formObj)[0].elements.length; i++ ) {

			        var e = $(formObj)[0].elements[i];

			        var key = e.name;
			        var value = data[key];

			        if (self.isBlank(e.name)) continue; 

			       	switch (e.type) {
			            case 'text':
			            	$('[name="'+ key +'"]').val(value);
			            	break;
			            case 'textarea':
			            	$('[name="'+ key +'"]').text(value);
			            	break;
			            case 'password':
			            	$('[name="'+ key +'"]').val(value);
			            	break;
			            case 'hidden':
			            	$('[name="'+ key +'"]').val(value);
			                break;
			            case 'radio':
			            	if ($.type() === "string") {
			            		$('[name="'+ key +'"][value="'+ value +'"]').attr("checked", true);
			            	}
			            	break;
			            case 'checkbox':
			            	if ($.type(value) === "array") {
			            	 	$.each(value, function (arrayKey, arrayValue) {
			            	 		$('[name="'+ key +'"][value="'+ arrayValue +'"]').attr("checked", true);
			            	 	});
			            	} else if ($.type(value) === "string" && value === "true") {
			            		$('[name="'+ key +'"]').prop('checked', true);
			            		$('[name="'+ key +'"]').attr('value', "true");
			            	} else if ($.type(value) === "string" && value === "false") {
			            		$('[name="'+ key +'"]').prop('checked', false);
			            		$('[name="'+ key +'"]').attr('value', "false");
			            	} else if ($.type(value) === "string") { 
			            	 	$('[name="'+ key +'"][value="'+ value +'"]').attr("checked", true);
			            	 	$('[name="'+ key +'"]').attr('value', "true");
			            	}
			                break;
			            case 'select-one':
			            	var className = $('[name="'+ key +'"]').attr('class');
			            	if (className !== undefined) {
				            	if(className.indexOf("selectpicker") != -1) {
					                $('[name="'+ key +'"]').selectpicker('val', value);
				            	} else {
				            		$('[name="'+ key +'"]').val(value);
				            	}
			            	}
			                break;
			            case 'select-multiple':
			            	var className = $('[name="'+ key +'"]').attr('class');
			            	if(className.indexOf("selectpicker") != -1) {
								if ($.type(value) === "array") {
				            		$('[name="'+ key +'"]').selectpicker('val', value);
				            	} else if ($.type() === "string") {
				            		$('[name="'+ key +'"]').selectpicker('val', value);
				            	} 
							} else {
								if ($.type(value) === "array") {
				            		$('[name="'+ key +'"]').val(value);
				            	} else if ($.type() === "string") {
				            		$('[name="'+ key +'"]').val(value);
				            	} 
			            	}
			                break;
			        }
			    }
		    	if(data.operation === 'deploy') {
		       		self.consDragnDropcnt(data, $('.jobConfigure').find('.row'));
		       	}
		    }
		},
		
		consDragnDropcnt : function(parameter, whereToRender){
			var self = this;
			if(parameter != null && parameter != ""){
				var sortable1Val = "", sortable2Val = "", sort2 ={};
				$('table[name=fetchSql_table]').remove();
				if(parameter.fetchSql != null && parameter.fetchSql != ""){
					$.each(JSON.parse(parameter.fetchSql), function(key, currentDbList){
						sort2[key] = [];
						$.each(currentDbList, function(index, current){
							sortable2Val += '<li class="ui-state-default '+ ($("#dataBase").val() == key ? "" : "ui-state-disabled")+'" path="'+ current +'" dbName="' + key + '">'+ current.split('/').pop() +'</li>';
							sort2[key].push(current);
						});
					});
				} 
				whereToRender.append('<table name="fetchSql_table" class="table table-striped table_border table-bordered fetchSql_table border_div" cellpadding="0" cellspacing="0" border="0"><thead><tr><th colspan="2">DB Script Execution</th></tr></thead><tbody><tr><td><ul name="sortable1" class="sortable1 connectedSortable">' + sortable1Val + '</ul></td><td><ul name="sortable2" class="sortable2 connectedSortable">' + sortable2Val + '</ul></td></tr></tbody></table>');
			}
		},

		configureJob : function (thisObj) {
			var self = this;
			// Get app name
			var appName = $(thisObj).attr("appname");
			var appDirName = $(thisObj).attr("appDirName");
			var jobtemplatename = $(thisObj).attr("jobtemplatename");
			var templateJsonData = $('[appname="'+ appName +'"][jobtemplatename="'+ jobtemplatename +'"]').data("templateJson");

			// validation starts

			// when the repo is svn or git need validation
			var emptyFound = false;

			//repo url validation
			if (!self.isBlank(templateJsonData.repoTypes) && (templateJsonData.repoTypes === "svn" || templateJsonData.repoTypes === "git")) {

				var repos = $("#repoType :input").not("#repoType input[type=hidden]");
				repos.each(function() {
					if (self.isBlank(this.value)) {
						this.placeholder = "Enter "+ this.name;
						$("input[name="+this.name+"]").addClass("errormessage");
						$("input[name="+this.name+"]").bind('keypress', function() {
							$("input[name="+this.name+"]").removeClass("errormessage");
						});		
						emptyFound = true;			
					}
				});
			}

			var confluence = $("#confluenceUploadSettings input, #confluenceUploadSettings select").not("#confluenceUploadSettings input[type=checkbox], input[name=confluenceOther]");
			confluence.each(function() {
				if (self.isBlank(this.value) && (this.name === 'confluenceSite')) {
					$('#errMsg').html('Select atleast One Site');
					$('input[name=confluenceSite]').focus();
					$('#errMsg').addClass("errormessage");
					emptyFound = true;
				} else if (self.isBlank(this.value)) {
					this.placeholder = "Enter "+ this.name;
					$("input[name="+this.name+"]").addClass("errormessage");
					$("input[name="+this.name+"]").focus();
					$("input[name="+this.name+"]").bind('keypress', function() {
						$("input[name="+this.name+"]").removeClass("errormessage");
					});		
					emptyFound = true;
				}	
			});

			var collabnet = $("#collabnetUploadSettings input").not("#repoType input[type=radio]");
			collabnet.each(function() {
				if (self.isBlank(this.value)) {
					this.placeholder = "Enter "+ this.name;
					$("input[name="+this.name+"]").addClass("errormessage");
					$("input[name="+this.name+"]").focus();
					$("input[name="+this.name+"]").bind('keypress', function() {
						$("input[name="+this.name+"]").removeClass("errormessage");
					});		
					emptyFound = true;			
				}
			});

			if (self.isBlank($("input[name=jobName]").val())) {
				$("input[name=jobName]").focus();
				$("input[name=jobName]").attr('placeholder','Enter Name');
				$("input[name=jobName]").addClass("errormessage");
				$("input[name=jobName]").bind('keypress', function() {
					$(this).removeClass("errormessage");
				});		
				emptyFound = true;			
			}
			
			var selectedJobLiObj = $("#sortable2 li[temp=ci]");
			var add = $(".content_end").find("input[type=submit]").val();
//			if(add === "Add") {
			$(selectedJobLiObj).each(function(index) {
				var thisAnchorElem = $(this).find('a');
				var thisJobJsonData = $(thisAnchorElem).data("jobJson");
				if(thisJobJsonData !== undefined) {
					var appname = $(thisObj).attr("appname");
					var templateName = $(thisObj).attr("jobtemplatename");
					if(thisJobJsonData.jobName === $("input[name=jobName]").val() && $(thisAnchorElem).attr("id") !== (appname+templateName)) {
						$("input[name=jobName]").focus();
						$("input[name=jobName]").val("");
						$("input[name=jobName]").attr('placeholder','Job Already Exists');
						$("input[name=jobName]").addClass("errormessage");
						$("input[name=jobName]").bind('keypress', function() {
							$(this).removeClass("errormessage");
						});		
						emptyFound = true;		
					}
				}
			});
//			}

			if(templateJsonData.type === "performanceTest" || templateJsonData.type === "loadTest") {
				var ciRequestBody = {}, redirect = true, templJsonStr="",testAction;
				redirect = self.contextUrlsMandatoryVal();
				if(redirect) {
					emptyFound = false;
					templJsonStr = self.contextUrls() + "," + self.dbContextUrls();
					var json = '{' + templJsonStr + '}'	;
					if(templateJsonData.type === "loadTest") {
						testAction = "load";
					} else {
						testAction = "performance";
					}
					var moduleParam = self.isBlank(templateJsonData.module) ? "" : '&moduleName='+templateJsonData.module;
					$("#contextDivParent").remove();
					ciRequestBody.data = $('#jonConfiguration :input[name!=parameterValue]').serialize()+"&appDirName="+appDirName+"&testAction="+testAction+moduleParam;
					ciRequestBody.jsondata = json;
					
					self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'writeJson'), function (response) {
						
					});
				} else {
					emptyFound = true;
				}
			}
			
			//scheduler validation
			if (!templateJsonData.enableSheduler) {
				$('[name=scheduleExpression]').attr('disabled', true);
			} 

			//mail settings validation

			// validation ends
			if (!emptyFound) {
				var triggers = [];
				$.each($("input:checkbox[name=triggers]:checked"), function(key, value) {
					 triggers.push($(this).val());
				});
				if(!templateJsonData.enableCriteria) {
					$("select[name=downStreamCriteria]").attr('disabled', true);
				} 
				//disable scheduler options
//				$("input[name=scheduleType]").attr('disabled', true);
				$("input[name=everyAt]").attr('disabled', true);
				$("select[name=minutes]").attr('disabled', true);
				$("select[name=hours]").attr('disabled', true);	
				$("select[name=days]").attr('disabled', true);			
				$("select[name=weeks]").attr('disabled', true);
				$("select[name=months]").attr('disabled', true);
				$("input[name=triggers]").attr('disabled', true);
 
				// append the configureJob json (jobJson) in  job template name id
				var jobConfiguration = $('#jonConfiguration :input[name!=targetFolder][name!=selectedFileOrFolder]').serializeObject();
				if(!self.isBlank(templateJsonData.module)) {
					jobConfiguration.module = templateJsonData.module;
				}
				jobConfiguration.operation = templateJsonData.type;
				jobConfiguration.triggers = triggers;
				jobConfiguration.templateName = templateJsonData.name;
				jobConfiguration.appDirName=appDirName;
				jobConfiguration.appName = appName;
				
				
				self.sqlQueryParam($('form[id=jonConfiguration] #executeSql').is(':checked'), $('form[id=jonConfiguration] ul[name=sortable2] li'), function(sqlParam){
					jobConfiguration.fetchSql = sqlParam;
				});
				if($('form[id=jonConfiguration] #executeSql').is(':checked')) {
					jobConfiguration.executeSql = "true";
				} else {
					jobConfiguration.executeSql = "false";
				}
				if($('form[id=jonConfiguration] #showSettings').is(':checked')) {
					jobConfiguration.showSettings = "true";
				} else {
					jobConfiguration.showSettings = "false";
				}

				//Checking
				$('[appname="'+ appName +'"][jobtemplatename="'+ jobtemplatename +'"]').data("jobJson", jobConfiguration);
	            // Hide popup
	            $(".dyn_popup").hide();
				$('#header').css('z-index','7');
				$('.content_title').css('z-index','6');
			} 	
		},

		contextUrlsMandatoryVal : function () {
			var self = this, redirect = true;
			$('.contextDivClass').each(function() {
				if (self.isBlank($(this).find($('input[name=httpName]')).val())) {
					redirect = false;
					$(this).find($('input[name=httpName]')).val('');
					$(this).find($('input[name=httpName]')).focus();
					$(this).find($('input[name=httpName]')).attr('placeholder','Http name is missing');
					$(this).find($('input[name=httpName]')).addClass("errormessage");
				} 
			});
			
			return redirect;
		},
		
		dbContextUrls : function () {
			var dbContextUrls = [];
			$('.dbContextDivClass').each(function() {
				var jsonObject = {};
				jsonObject.name = $(this).find($("input[name=dbName]")).val();
				jsonObject.queryType = $(this).find($("select[name=queryType]")).val();
				jsonObject.query = $(this).find($("textarea[name=query]")).val(); 
				var dbContexts = JSON.stringify(jsonObject);
				dbContextUrls.push(dbContexts);
			});
			var jsonStrFromTemplate = '"dbContextUrls":[' + dbContextUrls + ']';
			return jsonStrFromTemplate;
		},
		
		contextUrls : function () {
			var contextUrls = [];
			var contexts = "";
			$('.contextDivClass').each(function() {
				var jsonObject = {};
				jsonObject.name = $(this).find($("input[name=httpName]")).val();
				jsonObject.context = $(this).find($("input[name=context]")).val();
				jsonObject.contextType = $(this).find($("select[name=contextType]")).val();
				jsonObject.encodingType = $(this).find($("select[name=encodingType]")).val();
				jsonObject.contextPostData = $(this).find($("textarea[name=contextPostData]")).val(); 

				jsonObject.redirectAutomatically = $(this).find($("input[name=redirectAutomatically]")).is(':checked'); 
				jsonObject.followRedirects = $(this).find($("input[name=followRedirects]")).is(':checked'); 
				jsonObject.keepAlive = $(this).find($("input[name=keepAlive]")).is(':checked'); 
				jsonObject.multipartData = $(this).find($("input[name=multipartData]")).is(':checked'); 
				jsonObject.compatibleHeaders = $(this).find($("input[name=compatibleHeaders]")).is(':checked');  			
				
				jsonObject.applyTo = $(this).find($("select[name=applyTo]")).val();
				jsonObject.regexExtractor = $(this).find($("input[name=regexExtractor]")).is(':checked'); 
				jsonObject.responseField = $(this).find($("select[name=responseField]")).val();
				jsonObject.referenceName = $(this).find($("input[name=referenceName]")).val();
				jsonObject.regex = $(this).find($("textarea[name=regex]")).val();
				jsonObject.template = $(this).find($("input[name=template]")).val();
				jsonObject.matchNo = $(this).find($("input[name=matchNo]")).val();
				jsonObject.defaultValue = $(this).find($("input[name=defaultValue]")).val();
				
				var headers = [];
				$(this).find($('.headers')).each(function() {
					var key = $(this).find($("input[name=headerKey]")).val();
					var value = $(this).find($("input[name=headerValue]")).val();
					var keyValueObj = {};
					keyValueObj.key=key;
					keyValueObj.value=value;
					headers.push(keyValueObj);
				});
				jsonObject.headers=headers;
				
				var parameters = [];
				$(this).find($('.parameterRow')).each(function() {
					var name = $(this).find($("input[name=parameterName]")).val();
					var value = $(this).find($("textarea[name=parameterValue]")).val();
					var encode = $(this).find($("input[name=parameterEncode]")).is(':checked');
					var nameValueObj = {};
					nameValueObj.name=name;
					nameValueObj.value=value;
					nameValueObj.encode=encode;
					parameters.push(nameValueObj);
				});
				jsonObject.parameters=parameters;

				contextUrls.push(JSON.stringify(jsonObject));
			});
			var jsonStrFromTemplate = '"contextUrls":[' + contextUrls + ']';
			return jsonStrFromTemplate;
		},
		
		constructJobTemplateViewByEnvironment : function (response, callback) {			
			var self = this;			
			var data = response.data;			
			if (!self.isBlank(data)) {
				// appinfo job templates
				var sort = $(commonVariables.contentPlaceholder).find('#sortable1');
				sort.empty();
				$.each(data, function(key, value) {
					var parseJson = $.parseJSON(key);
					var appName = parseJson.appName;
					var appDirName = parseJson.appDirName;
					var jobTemplateApplicationName = '<div class="sorthead">'+ appName +'</div>';
					sort.append(jobTemplateApplicationName);					
					// job tesmplate key and value
					$.each(value, function(jobTemplateKey, jobTemplateValue) {
						var jobTemplateGearHtml = '<a href="javascript:;" id="'+ appName + jobTemplateValue.name +'" class="validate_icon" jobTemplateName="'+ jobTemplateValue.name +'" appName="'+ appName +'" appDirName="'+ appDirName +'" name="jobConfigurePopup" style="display: none;"><img src="themes/default/images/Phresco/validate_image.png" width="19" height="19" border="0"></a>';
                		var jobTemplateHtml = '<li class="ui-state-default" title="" temp="ci"><span>' + jobTemplateValue.name + ' - ' + jobTemplateValue.type + '</span>' + jobTemplateGearHtml + '</li>';
                		sort.append(jobTemplateHtml);
                		// set json value on attribute
                		var jobTemplateJsonVal = jobTemplateValue;
                		$('#'+ appName + jobTemplateValue.name).data("templateJson", jobTemplateJsonVal);        
					});					
				});				
			}
			callback();
		},

		/***
		 * This method automatically manipulates upstream and downstream as well as validation
		 *
		 */
		streamConfig : function(sortableObj, callback) {
	  		// third Construct upstream and downstream validations
	  		// all li elemnts of this
			$($(sortableObj).find('li').get().reverse()).each(function() {
					var thisObj = this;
				    var anchorElem = $(thisObj).find('a');
				    var appName = $(anchorElem).attr("appname");
				    var templateJsonData = $(anchorElem).data("templateJson");
				    var jobJsonData = $(anchorElem).data("jobJson");
				    // upstream and downstream and clone workspace except last job
				    
				    var preli = $(thisObj).prev('li');
				    var preAnchorElem = $(preli).find('a');
				    var preTemplateJsonData = $(preAnchorElem).data("templateJson");
				    var preJobJsonData = $(preAnchorElem).data("jobJson");
				    
				    var nextli = $(thisObj).next('li');
				    var nextAnchorElem = $(nextli).find('a');
				    var nextTemplateJsonData = $(nextAnchorElem).data("templateJson");
				    var nextJobJsonData = $(nextAnchorElem).data("jobJson");

				    if (jobJsonData === undefined && jobJsonData === null) {
				        jobJsonData = {};
				    }

				    // Downstream
				    if (nextJobJsonData !== undefined && nextJobJsonData !== null) {
				    	// downstream specifies the next job which needs to be triggered after this job
				        jobJsonData.downstreamApplication = nextJobJsonData.jobName;
				        //jobJsonData.downStreamProject = nextJobJsonData.jobName;
//				        jobJsonData.downStreamCriteria = "SUCCESS";
				    } else {
				    	jobJsonData.downstreamApplication ="";
				    }

				    // No use parent job
				    if (preJobJsonData !== undefined && preJobJsonData !== null) {
				    	// upstream job specifies the from where this app will be triggered, this will be based on assumption, only for UI purpose
				        jobJsonData.upstreamApplication = preJobJsonData.jobName;
				    } else {
				    	jobJsonData.upstreamApplication = "";
				    }

				   // Is parent app available for this app job
				    var parentAppFound = false;
				    var workspaceAppFound = false;
				    // check for previous job to find its cloneJobName
			  		$(thisObj).prevAll('li').each(function(index) {
			  			// Corresponding element access
					    var thisAnchorElem = $(this).find('a');
			  			var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
			  			var thisJobJsonData = $(thisAnchorElem).data("jobJson");
			  			var thisAppName = $(thisAnchorElem).attr("appname");
			  			if (appName === thisAppName && thisTemplateJsonData.enableRepo) {
			  				parentAppFound = true;			  			
			  			}

			  			if (appName === thisAppName && !workspaceAppFound) {
			  				workspaceAppFound = true;
			  				// from where this jobs source code have to come from
			  				// Upstream app
			  				if (thisJobJsonData !== undefined && thisJobJsonData !== null) {
				        		jobJsonData.usedClonnedWorkspace = thisJobJsonData.jobName;
				        		thisJobJsonData.cloneWorkspace = true;
				        		$(thisAnchorElem).data("jobJson", thisJobJsonData);
				    		}
			  			}

			  			if (parentAppFound && workspaceAppFound) {
			  				return false;
			  			}
					});
								

				    // Store value in data
				    $(anchorElem).data("jobJson", jobJsonData);

			});
			callback();
		},

		/***
		 * This method automatically constructs the jobs from each job, that are all on sortable2
		 *
		 */
		constructJobsObj : function(thisObj) {
			var self = this;
		},

		saveContinuousDelivery : function (thisObj, callback) {
			var self = this;
			var contDeliveryName = $("[name=continuousDeliveryName]").val();
			var envName = $("select[name=environments]").val();
			var sortable2LiObj = $("#sortable2 li[temp=ci]");
			var sortable2Obj = $("#sortable2");
			var name = [];
			if(!self.isBlank(contDeliveryName) && $(sortable2LiObj).length !== 0) {				
				var hasError = false;
				$(sortable2LiObj).each(function(index) {
					var thisAnchorElem = $(this).find('a');
					var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
					var thisJobJsonData = $(thisAnchorElem).data("jobJson");
					var thisAppName = $(thisAnchorElem).attr("appname");
					// open the popup and ask the user to input the data, Once the popup is opened all the data will be validates
					if (self.isBlank(thisJobJsonData)) {
						self.showConfigureJob(thisAnchorElem);
						hasError = true;
						return false;
					} else {					
						// all the input text box should not be empty
						if (self.isBlank(thisJobJsonData.jobName)) {
							self.showConfigureJob(thisAnchorElem);
							hasError = true;
							return false;
						} 
					}
					name.push(thisJobJsonData.jobName);
				});
				var ciRequestBody = {};
				ciRequestBody.jobName =name;
				ciRequestBody.flag =$(thisObj).val();
				self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'jobValidation'), function (response) {
					if(response.responseCode === "PHR810031") {
						var msg = response.data + commonVariables.api.error[response.responseCode];
						commonVariables.api.showError(msg ,"error", true, true, true);
						hasError = true;
						return false;
					} 

					if(!hasError) {
						var jobs = [];
						var contDelivery = {};
						// Upstream and downstream config auto population goes here
						self.streamConfig(sortable2Obj, function() {
							// construct the job json data here once all the data are validated
							$(sortable2LiObj).each(function(index) {
								var thisAnchorElem = $(this).find('a');
								var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
								var thisJobJsonData = $(thisAnchorElem).data("jobJson");
								jobs.push(thisJobJsonData);
							});
						});
						
						contDelivery.name = contDeliveryName;
						contDelivery.envName = envName;
						contDelivery.jobs = jobs;

						callback(contDelivery);
					}
				});
				
			} else if(self.isBlank(contDeliveryName)){
				$("[name=continuousDeliveryName]").focus();
				$("[name=continuousDeliveryName]").attr('placeholder','Enter Name');
				$("[name=continuousDeliveryName]").addClass("errormessage");
				$("[name=continuousDeliveryName]").bind('keypress', function() {
					$(this).removeClass("errormessage");
				});
			} else if($(sortable2LiObj).length === 0) {
				$(".msgdisplay").removeClass("success").addClass("error");
				$(".error").text("Configure atleast one Job!");
				$(".error").show();
				$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
				setTimeout(function() {
					$(".error").hide();
				},2500);
			}
		},



		saveContinuousDeliveryValidation : function (thisObj, callback) {
			var self = this;
			callback();
		},
		
		downStreamCriteria : function() {
			var self = this;
			var sortable2LiObj = $("#sortable2 li");
			$(sortable2LiObj).each(function(index) {
				var thisAnchorElem = $(this).find('a');
				var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
				var thisJobJsonData = $(thisAnchorElem).data("jobJson");
				if (!self.isBlank(thisJobJsonData) && thisJobJsonData.downStreamCriteria === "") {
					thisJobJsonData.downStreamCriteria = "SUCCESS";
				}
				thisTemplateJsonData.enableCriteria = true;
			});
		},
		
		lastChild : function() {
			var self =this;
			var lastChild = $("#sortable2 li:last-child").find('a');
			if(!self.isBlank(lastChild)) {
				var lastTemplateJsonData = lastChild.data("templateJson");
				var lastJobJsonData = lastChild.data("jobJson");
				if (!self.isBlank(lastJobJsonData)) {
					lastJobJsonData.downStreamCriteria = "";
				}
				if(!self.isBlank(lastTemplateJsonData)) {
					lastTemplateJsonData.enableCriteria = false;
				}
			}
		},
		
		sortableOneReceive : function(ui) {
			// For gear icons alone
			$("#sortable2 li.ui-state-default a").show();
			$("#sortable1 li.ui-state-default a").hide();	
			$(".dyn_popup").hide();

			// Remove application name text
			var itemText = $(ui.item).find('span').text();
			var anchorElem = $(ui.item).find('a');
			var appName = $(anchorElem).attr("appname");						
			$(ui.item).find('span').text($(ui.item).find('span').text().replace(appName + " - ", ""));
			$( ".sorthead" ).each(function( index ) {
				if(appName === $(this).text()) {
					$(ui.item).insertAfter($(this));
				} 
			});
		},
		
		sortableTwoReceive : function(ui) {
			var tt = $("#sortable2 li.ui-state-default").length;
			if(tt === 2) {
				$("#sortable2 li.ui-state-default").each(function( index ) {
					$(ui.item).insertAfter($(this));
				});
			}
			// For gear icons alone
			$("#sortable2 li.ui-state-default a").show();
			$("#sortable1 li.ui-state-default a").hide();	

			// Append application name text
			var itemText = $(ui.item).find('span').text();
			var anchorElem = $(ui.item).find('a');
			var templateJsonData = $(anchorElem).data("templateJson");
			var appName = $(anchorElem).attr("appname");
			
			var fullName = appName  + " - " + itemText;
			var finalText;
			if (fullName.length > 40) {
				finalText = fullName.substring(0,40) + "...";
			} else {
				finalText = fullName;
			}
			
			// Application name construct
			$(ui.item).find('span').text(finalText);
			$(ui.item).attr('title', fullName);

			var sortable2Len = $('#sortable2 > li').length;

			// Second level validation
			// when the repo is not available check for parent project in sortable2
			if ( templateJsonData !== undefined && !templateJsonData.enableRepo) {
				var parentAppFound = false;

				// Previous elemets
				$(ui.item).prevAll('li').each(function(index) {
					var thisAnchorElem = $(this).find('a');
					var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
					var thisAppName = $(thisAnchorElem).attr("appname");
					if (thisAppName === appName && thisTemplateJsonData.enableRepo) {
						parentAppFound = true;
						return false;
					}
				});

				if (!parentAppFound) {
					$(ui.item).find('span').text(itemText);
					$(ui.sender).sortable('cancel');
					$(ui.item).find("a").hide();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").text("Parent object not found for "+templateJsonData.name+" template!");
					$(".error").show();
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".error").hide();
					},2500);
				}
			}
		},
		
		sortableOneChange : function(ui) {
			var itemText = $(ui.item).find('span').text();
			var anchorElem = $(ui.item).find('a');
			var templateJsonData = $(anchorElem).data("templateJson");
			var id = $(ui.item).parent("ul").attr("id");
			if(id === "sortable2") {
				var nextItem = $(ui.item).next();
				var nextAnchorElem = $(ui.item).next().find('a');
				var nextAnchorElem = $(ui.item).next().find('a');
				var downTemplateJsonData = $(nextAnchorElem).data("templateJson");
				
				var prevItem = $(ui.item).prev();
				var upTemplateJsonData;
				if(prevItem !== null && prevItem!== undefined) {
					var prevAnchorElem = $(ui.item).prev().find('a');
					var prevAnchorElem = $(ui.item).prev().find('a');
					upTemplateJsonData = $(prevAnchorElem).data("templateJson");
				}
				if (upTemplateJsonData === null) {
					if(downTemplateJsonData !== undefined && downTemplateJsonData!== null && !downTemplateJsonData.enableRepo) {
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").text("DownStream "+downTemplateJsonData.name+" job Doesn't have the Repo!");
						$(".error").show();
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".error").hide();
						},2500);
						$(ui.sender).sortable('cancel');
					}
				}
			}
		},
		
		sortableTwoChange : function(ui) {
			var itemText = $(ui.item).find('span').text();
			var anchorElem = $(ui.item).find('a');
			var templateJsonData = $(anchorElem).data("templateJson");
			var appName = $(anchorElem).attr("appname");
			var sortable2Len = $('#sortable2 > li').length;
			// Initial validation
			if (sortable2Len === 1 && !templateJsonData.enableRepo) {
				$(ui.sender).sortable('cancel');
				$(".msgdisplay").removeClass("success").addClass("error");
				$(".error").text(templateJsonData.name + " job Doesn't have the Repo!");
				$(".error").show();
				$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
				setTimeout(function() {
					$(".error").hide();
				},2500);
			}
		},


		dragDrop : function(){
			$('.connectedSortable').sortable({
				connectWith: '.connectedSortable',
				cancel: ".ui-state-disabled"
			});
		},

		sqlQueryParam : function(ststus, control, callback){
			var sqlParamVal = "";
			
			if(ststus){
				sqlParamVal = {};
				$.each(control, function(index, current){
					if(sqlParamVal.hasOwnProperty($(current).attr('dbName'))){
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}else{
						sqlParamVal[$(current).attr('dbName')] = []
						sqlParamVal[$(current).attr('dbName')].push($(current).attr('path'));
					}
				});			
				callback(JSON.stringify(sqlParamVal));
			} else {
				callback(sqlParamVal);
			}
		}, 

		chkSQLCheck : function(){
			if(!$('#executeSql').is(':checked')){
				$('table[name=fetchSql_table]').hide();
				$('#dataBaseLi').hide();
			}else{
				$('table[name=fetchSql_table]').show();
				$('#dataBaseLi').show();
			}
		},
		
				
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});