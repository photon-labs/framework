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

		loadContinuousDeliveryView : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryView, function(retVal){
				self.continuousDeliveryView = 	retVal;			
				Clazz.navigationController.push(retVal, commonVariables.animation);
			}); 
		},

		editContinuousDeliveryConfigure : function(contDeliveryName) {
			var self = this;			
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryConfigure, function(retVal){
				retVal.name = contDeliveryName;
				Clazz.navigationController.push(retVal, commonVariables.animation);
			}); 
		},

		editContinuousViewTable : function (response) {			
			var self = this;			
			var data = response.data;
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
					parentli.find('span').text(value.appName  + " - " + itemText);
					sort.append(parentli);
					// For gear icons alone
					$("#sortable2 li.ui-state-default a").show();
					$("#sortable1 li.ui-state-default a").hide();
					$('#'+id).data("jobJson", value);
				});				
			}
		},

		getRequestHeader : function(ciRequestBody, action, params) {
			var self = this, header;
			// basic params for job templates
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			var projectId = commonVariables.api.localVal.getSession("projectId");
			var appDir = commonVariables.api.localVal.getSession('appDirName');

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
				ciRequestBody.customerId = customerId;
				ciRequestBody.projectId = projectId;
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
			} else if (action === "continuousDeliveryList") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci+"/list?projectId="+projectId+"&appDirName="+appDir;
			} else if (action === "update") {
				header.requestMethod = "PUT";
				ciRequestBody.customerId = customerId;
				ciRequestBody.projectId = projectId;
				header.requestPostBody = JSON.stringify(ciRequestBody);
				var oldname = $('[name="oldname"]').val();				
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=" + oldname + "&projectId=" + projectId;
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
				// For Testing Purpose - Remove this when service calls get ready
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			}  else if (action === "getJobTemplatesByEnvironment") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;	
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "getDynamicPopupByAppId") {
				// need to pass appId, operation and appId
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/getDynamicPopupByAppId" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "saveContinuousDelivery") {
				// Save the continuos delivery with all the drag and dropped job templates
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/create" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "updateContinuousDelivery") {
				// update the continuos delivery with all the drag and dropped job templates
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/update" + "?customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;					
				}				
			} else if (action === "getContinuousDelivery") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/getContinuousDelivery" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "getBuilds") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/builds?projectId="+projectId+"&name="+ciRequestBody.jobName+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "generateBuild") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/build?name="+ciRequestBody.jobName+"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "deleteBuild") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/deletebuilds?buildNumber="+ciRequestBody.buildNumber+"&name="+ciRequestBody.jobName+"&projectId="+projectId+"&appDirName="+appDir+"&continuousName="+ciRequestBody.continuousName;
			} else if (action === "jobStatus") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/jobStatus?name="+ciRequestBody.jobName+"&continuousName="+ciRequestBody.cdName+"&projectId="+projectId+"&appDirName="+appDir;
			} else if (action === "deleteContinuousDelivery") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/delete?continuousName="+ciRequestBody.cdName+"&customerId="+ customerId + "&projectId=" + projectId+"&appDirName="+appDir;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "cronExpression") {
				self.bcheck = true;
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cronExpression";
			} else if (action === "createClone") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl+commonVariables.ci+"/clone?projectId="+projectId+"&appDirName="+appDir+"&"+ciRequestBody.data;
			} else if(action === "editContinuousView") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/editContinuousView?projectId="+projectId+"&appDirName="+appDir+"&name=" + params;
			} else if(action === "jenkinsStatus") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.ci + "/isAlive";
			}

			return header;
		},

		showHideRepo : function(callback) {
			if ($("input[name=enableRepo]").is(':checked')) {
				$("select[name=repoTypes]").next().show();
			} else {
				$("select[name=repoTypes]").next().hide();
			}
		},
		
		showHideUpload : function(callback) {
			if ($("input[name=enableUploadSettings]").is(':checked')) {
				$("select[name=uploadTypes]").next().show();
			} else {
				$("select[name=uploadTypes]").next().hide();
			}
		},
		
		deleteContinuousDelivery: function(obj) {
			var self = this;
			var ciRequestBody = {},name;
			ciRequestBody.cdName = obj.prev().attr("value");
			
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'deleteContinuousDelivery'), function (response) {
				self.loadContinuousDeliveryView();
			});
		},
		
		ciStatus:function(obj) {
			var self = this;
			var ciRequestBody = {},name;
			ciRequestBody.jobName=obj.attr("id");
			ciRequestBody.cdName = obj.parent().parent("div").attr("name");
			commonVariables.loadingScreen.removeLoading();
			self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'jobStatus'), function (response) {
				if(response.data === "FAILURE") {
					obj.find('.img_process').attr('src',"themes/default/images/helios/cross_red.png");
				} else if (response.data === "SUCCESS") {
					obj.find('.img_process').attr('src',"themes/default/images/helios/tick_green.png");
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
		
		getBuilds: function(obj,job, name) {
			var self = this;
			var ciRequestBody = {},jobName,continuousName;
			if (job !== undefined && job !== null) {
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
							headerTr = '</td><td><img src="themes/default/images/helios/red_deactive.png"></td>';
						} else {
							headerTr = '</td><td><img src="themes/default/images/helios/green_active.png"></td>';
						}
						content = content.concat(headerTr);
						headerTr = '<td><a href="#" data-placement="top" continuousName="'+continuousName+'" jobName="'+jobName+'"buildNumber="'+response.data[i].number+'"temp="deleteBuild"><img src="themes/default/images/helios/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>';
						content = content.concat(headerTr);
					}
					
					$("tbody[name=buildList]").append(content);
				} else {
					$("tbody[name=buildList]").html("No Builds are Available");
				}
				self.deleteOnClick(response);
			});
		},
		
		deleteOnClick: function(obj) {
			var self = this;
			$("a[temp=deleteBuild]").click(function() {
				self.deleteBuild($(this));
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
						var headerTr = '<option value="'+response.data[i]+'">'+response.data[i]+'</option>'
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
		
		cloneCi: function(obj) {
			var self = this;
			var ciRequestBody = {},jobName;
			ciRequestBody.data = obj.closest("form").serialize();
			if(self.cloneValidation()) {
				self.getHeaderResponse(self.getRequestHeader(ciRequestBody, 'createClone'), function (response) {
					self.loadContinuousDeliveryView();
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
		
		validate : function (callback) {
			$('#errMsg').html('');
			var self=this;
			var status = true;
			$("#errMsg").removeClass("errormessage");
			$('input[name=name]').removeClass("errormessage");	
			$('#errMsg').html('');
			
			var enableUpload = $('input[name=enableUploadSettings]').is(':checked');
			if (enableUpload) {
				var uploads = $('select[name=uploadTypes] :selected').length;
				if (uploads == 0) {
					$('#errMsg').html('Select atleast one Uploader');
					$("select[name='uploadTypes']").focus();
					$('#errMsg').addClass("errormessage");
					$('select[name=uploadTypes]').next().find('button.dropdown-toggle').addClass("btn-danger");
					status = false;	 
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
				self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'validate'), function (response) {
					
					// save or update operation
					if (!self.isBlank(response) && response.data) {						
						 if (operation === 'save') {
							self.addJobTemplate(function(response) {
								self.ciRequestBody = response;
								self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'add'), function(response){
									callback(response); 
								});
							});	
						} else if (operation === 'update') {
							self.updateJobTemplate(function(response) {
								self.ciRequestBody = response;
								self.getHeaderResponse(self.getRequestHeader(self.ciRequestBody, 'update'), function(response) {
									callback(response);
								});								
							});
						} 
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
						callback({ "status" : "Connection failure"});
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
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {						
						callback({ "status" : "Connection failure"});
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
			var formObj = $("#jobTemplate"); 
			$('#jobTemplate #features :checkbox:not(:checked)').attr('value', false); 
			$('#jobTemplate #features :checkbox:checked').attr('value', true); 

			var jobTemplate = $('#jobTemplate :input[name!=oldname]').serializeObject();

			// Convert appIds to array
			jobTemplate.appIds = [];
			$('select[name=appIds] :selected').each(function(i, selected) {
				jobTemplate.appIds.push(this.value);
			});
			
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
			
			$("select[name=repoTypes]").next().hide();
			$("select[name=uploadTypes]").next().hide();
			$('select[name=appIds]').selectpicker('deselectAll');
			$('select[name=uploadTypes]').selectpicker('deselectAll');
			$('.selectpicker').selectpicker('render');
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
			$('.selectpicker').selectpicker('render');
			self.removeDangerClass($('select[name=appIds]'));
			self.removeDangerClass($('select[name=uploadTypes]'));
			callback();
		},

		changeUpload : function() {
			var operation = $("select[name=type]").val();			
			if (operation === 'build' || operation === 'pdfReport') {
				$("input[name=enableUploadSettings]").removeAttr("disabled");
			} else {
				$("input[name=enableUploadSettings]").attr("checked", false);
				$("input[name=enableUploadSettings]").attr("disabled","disabled");
				$('select[name=uploadTypes]').selectpicker('deselectAll');
				$('.selectpicker').selectpicker('render');
				$("select[name=uploadTypes]").next().hide();
			}
		},

		editJobTemplate : function (data) {
			var self = this;
			$("[name=name]").val(data.name);
			$("[name=oldname]").val(data.name);
			$("[name=type]").selectpicker('val', data.type);
			//AppIds
			$("select[name=appIds]").selectpicker('val', data.appIds);
			
			$("[name=repoTypes]").val(data.repoTypes);

			$('[name=enableRepo]').prop('checked', data.enableRepo);
			$('[name=enableSheduler]').prop('checked', data.enableSheduler);
			$('[name=enableEmailSettings]').prop('checked', data.enableEmailSettings);
			$('[name=enableUploadSettings]').prop('checked', data.enableUploadSettings);
			//Uploaders
			$("select[name=uploadTypes]").selectpicker('val', data.uploadTypes);
			$('.selectpicker').selectpicker('render');
			
			self.showHideRepo();
			self.showHideUpload();
			// button name change
			$('input[name=save]').prop("value", "Update");
			$('input[name=save]').prop("name", "update");
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


		showConfigureJob : function(thisObj) {
			$('input[name=jobName]').removeClass('errormessage');
			$('input[name=jobName]').val('');
			$('#errMsg').html('');
			$('#errMsg').removeClass('errormessage');
			//enable scheduler options 
			$("input[name=scheduleOption]").attr('disabled', false);
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
			var repoTypeElem = $("#repoType tbody tr");
			var jobNameTrElem = $("#jobName tbody tr td");
			var repoTypeTitleElem = $("#repoType thead tr th");

			// Repo types			
			if (templateJsonData.enableRepo && templateJsonData.repoTypes === "svn") {
				// For svn
				$(repoTypeElem).html('<td><input type="text" placeholder="SVN Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>'+
                        '<td><input type="text" placeholder="Username" name="username"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else if (templateJsonData.enableRepo && templateJsonData.repoTypes === "git") {
				// For GIT
				$(repoTypeElem).html('<td><input type="text" placeholder="GIT Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>'+
                        '<td><input type="text" placeholder="Username" name="username"></td>'+
                        '<td><input type="text" placeholder="Branch" name="branch"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else {				
				//For clonned workspace
				$(repoTypeElem).html('<input name="repoType" type="hidden" value="clonedWorkspace">');
				//set label value
				$(repoTypeTitleElem).html("Clonned workspace");
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
                    	'<tr><td>Confluence Site<br><select name="confluenceSite"><option>172.16.25.44</option></select></td></tr>'+
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
				$("#scheduler").remove();
				$("#cronPreview").remove();
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

			// set corresponding job template name and their app name in configure button
			$("[name=configure]").attr("appname", appName);
			$("[name=configure]").attr("appDirName", appDirName);
			$("[name=configure]").attr("jobtemplatename", jobtemplatename);


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
			
			self.CroneExpression();
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
					}
					self.popForCi(thisObj, 'jobConfigure');
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
			
			self.currentEvent($('input[name=scheduleOption]:checked').val(), '');
			$('input[name=scheduleOption]').bind('click', function() {
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
					croneJson.week = $('select[name=weeks]').val();
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
			            	} else if ($.type(value) === "string" && value === "false") {
			            		$('[name="'+ key +'"]').prop('checked', false);
			            	} else if ($.type(value) === "string") { 
			            	 	$('[name="'+ key +'"][value="'+ value +'"]').attr("checked", true);
			            	}
			                break;
			            case 'select-one':
			            	$('[name="'+ key +'"]').val(value);
			                break;
			            case 'select-multiple':
							if ($.type(value) === "array") {
			            		$('[name="'+ key +'"]').val(value);
			            	} else if ($.type() === "string") {
			            		$('[name="'+ key +'"]').val(value);
			            	}
			                break;
			        }
			    }
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
				if (self.isBlank(this.value) && (this.name == 'confluenceSite')) {
					$('#errMsg').html('Select atleast One Site');
					$('select[name=confluenceSite]').focus();
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

			//scheduler validation

			//mail settings validation

			// validation ends
			if (!emptyFound) {
				var triggers = [];
				$.each($("input:checkbox[name=triggers]:checked"), function(key, value) {
					 triggers.push($(this).val());
				});
				
				//disable scheduler options
				$("input[name=scheduleOption]").attr('disabled', true);
				$("input[name=everyAt]").attr('disabled', true);
				$("select[name=minutes]").attr('disabled', true);
				$("select[name=hours]").attr('disabled', true);	
				$("select[name=days]").attr('disabled', true);			
				$("select[name=weeks]").attr('disabled', true);
				$("select[name=months]").attr('disabled', true);
				$("input[name=triggers]").attr('disabled', true);
 
				// append the configureJob json (jobJson) in  job template name id
				var jobConfiguration = $('#jonConfiguration').serializeObject();
				jobConfiguration.operation = templateJsonData.type;
				jobConfiguration.triggers = triggers;
				jobConfiguration.templateName = templateJsonData.name;
				jobConfiguration.appDirName=appDirName;
				jobConfiguration.appName = appName;
				
				//Checking
				$('[appname="'+ appName +'"][jobtemplatename="'+ jobtemplatename +'"]').data("jobJson", jobConfiguration);
	            // Hide popup
	            $(".dyn_popup").hide();
				$('#header').css('z-index','7');
				$('.content_title').css('z-index','6');
			} 			
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
						var jobTemplateGearHtml = '<a href="javascript:;" id="'+ appName + jobTemplateValue.name +'" class="validate_icon" jobTemplateName="'+ jobTemplateValue.name +'" appName="'+ appName +'" appDirName="'+ appDirName +'" name="jobConfigurePopup" style="display: none;"><img src="themes/default/images/helios/validate_image.png" width="19" height="19" border="0"></a>';
                		var jobTemplateHtml = '<li class="ui-state-default" temp="ci"><span>' + jobTemplateValue.name + ' - ' + jobTemplateValue.type + '</span>' + jobTemplateGearHtml + '</li>'
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
				    
				    var preli = $(thisObj).prev('li')
				    var preAnchorElem = $(preli).find('a');
				    var preTemplateJsonData = $(preAnchorElem).data("templateJson");
				    var preJobJsonData = $(preAnchorElem).data("jobJson");
				    
				    var nextli = $(thisObj).next('li');
				    var nextAnchorElem = $(nextli).find('a');
				    var nextTemplateJsonData = $(nextAnchorElem).data("templateJson");
				    var nextJobJsonData = $(nextAnchorElem).data("jobJson");

				    if (jobJsonData == undefined && jobJsonData == null) {
				        jobJsonData = {};
				    }

				    // Downstream
				    if (nextJobJsonData != undefined && nextJobJsonData != null) {
				    	// downstream specifies the next job which needs to be triggered after this job
				        jobJsonData.downstreamApplication = nextJobJsonData.jobName;
				        //jobJsonData.downStreamProject = nextJobJsonData.jobName;
				        jobJsonData.downStreamCriteria = "SUCCESS";
				    } else {
				    	jobJsonData.downstreamApplication ="";
				    }

				    // No use parent job
				    if (preJobJsonData != undefined && preJobJsonData != null) {
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
			  				if (thisJobJsonData != undefined && thisJobJsonData != null) {
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
				});

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
			} else if(self.isBlank(contDeliveryName)){
				$("[name=continuousDeliveryName]").focus();
				$("[name=continuousDeliveryName]").attr('placeholder','Enter Name');
				$("[name=continuousDeliveryName]").addClass("errormessage");
				$("[name=continuousDeliveryName]").bind('keypress', function() {
					$(this).removeClass("errormessage");
				});
			} else if($(sortable2LiObj).length === 0) {
				$(".blinkmsg").removeClass("popsuccess").addClass("poperror");
				self.effectFadeOut('poperror', (''));
				$(".poperror").text("Configure atleast one Job!");
			}
		},



		saveContinuousDeliveryValidation : function (thisObj, callback) {
			var self = this;
			callback();
		}
				
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});