define(["ci/api/ciAPI"], function() {

	Clazz.createPackage("com.components.ci.js.listener");

	Clazz.com.components.ci.js.listener.CIListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		ciAPI : null,
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
			if (self.ciAPI === null) {
				self.ciAPI = new Clazz.com.components.ci.js.api.CIAPI();
			}
			
			if (self.loadingScreen === null) {
				self.loadingScreen = new Clazz.com.js.widget.common.Loading();
			}
		},
		
		loadContinuousDeliveryConfigure : function() {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryConfigure, function(retVal){
				self.continuousDeliveryConfigure = 	retVal;
				Clazz.navigationController.push(self.continuousDeliveryConfigure, true);
			}); 
		},

		getRequestHeader : function(ciRequestBody, action, params) {
			var self = this, header;
			// basic params for job templates
			var customerId = self.getCustomer();
			customerId = (customerId == "") ? "photon" : customerId;
			var projectId = self.ciAPI.localVal.getSession("projectId");

			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId="+ customerId + "&projectId=" + projectId;
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
				// get all the environments of a project, which lists all the application environments
				header.requestMethod = "GET";
				//header.webserviceurl = commonVariables.webserviceurl + commonVariables.configuration + "/allEnvironments" + "?customerId="+ customerId + "&projectId=" + projectId;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId" + "?customerId="+ customerId + "&projectId=" + projectId;
				// For Testing Purpose - Remove this when service calls get ready
				//header.webserviceurl = header.webserviceurl + "&appDirName=wwwww-Java WebService";		
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			}  else if (action === "getJobTemplatesByEnvironment") {
				// get job template info with appId
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment" + "?customerId="+ customerId + "&projectId=" + projectId;	
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
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/saveContinuousDelivery" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "updateContinuousDelivery") {
				// update the continuos delivery with all the drag and dropped job templates
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/updateContinuousDelivery" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "getContinuousDelivery") {
				// getContinuousDelivery
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/getContinuousDelivery" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
			} else if (action === "deleteContinuousDelivery") {
				// deleteContinuousDelivery
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "/deleteContinuousDelivery" + "?customerId="+ customerId + "&projectId=" + projectId;
				if (params !== null && params !== undefined && params !== '') {
					params = $.param(params);
					header.webserviceurl = header.webserviceurl + "&" + params;
				}
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
							self.addJobTemplate(function(response) { // request body construction
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

		// fieldValidate : function (obj) {
		// 	if (obj.val() == undefined || obj.val() == null || $.trim(obj.val()) == "") {
		// 		obj.attr('placeholder','Enter Username');
		// 		obj.addClass("loginuser_error");
		// 		return false;
		// 	}
		// 	return true;
		// },

		addJobTemplate : function (callback) {
			var self = this;
			var jobTemplate = self.constructJobTemplate();

			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		listJobTemplate : function (header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				self.ciAPI.ci(header, function(response) {
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
				self.ciAPI.ci(header, function(response) {
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
			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		constructJobTemplate : function () {
			var formObj = $("#jobTemplate"); // Object
			$('#jobTemplate #features :checkbox:not(:checked)').attr('value', false); // make unchecked checkbox value to false
			$('#jobTemplate #features :checkbox:checked').attr('value', true); // make checked checkbox value to true

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
			if (operation === 'Build' || operation === 'PDF Report') {
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
			//TODO: need to get downstream project as well as dynamic params from service
			var self = this;
			var templateJsonData = $(thisObj).data("templateJson");

			var jobJsonData = $(thisObj).data("jobJson");

			// elements
			var repoTypeElem = $("#repoType tbody tr");
			var repoTypeTitleElem = $("#repoType thead tr th");

			// Repo types
			if (!self.isBlank(templateJsonData.repoTypes) && templateJsonData.repoTypes === "svn") {
				// For svn
				$(repoTypeElem).html('<td><input type="text" placeholder="SVN Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>'+
                        '<td><input type="text" placeholder="Username" name="userName"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else if (!self.isBlank(templateJsonData.repoTypes) && templateJsonData.repoTypes === "git") {
				// For GIT
				$(repoTypeElem).html('<td><input type="text" placeholder="GIT Url" name="url"><input name="repoType" type="hidden" value="'+ templateJsonData.repoTypes +'"></td>'+
                        '<td><input type="text" placeholder="Username" name="userName"></td>'+
                        // '<td><input type="text" placeholder="Branch" name="branch"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else {
				// For clonned workspace
				$(repoTypeElem).html('<input name="repoType" type="hidden" value="clonedWorkspace"><select id="clonnedWorkspaceName" name="clonnedWorkspaceName"></select>');
				// set label value
				$(repoTypeTitleElem).html("Clonned workspace");
			}

			// Upload configurations
			$("#uploadSettings").empty();
			if (templateJsonData.enableUploadSettings) {
				//elements
				var uploadSettingsElem = $("#uploadSettings");

				// uploadType
				if (templateJsonData.uploadType === "collabnet") {
					var uploadSettingsHtml = '<table id="collabnetUploadSettings" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
                    '<thead><tr><th colspan="3">CollabNet Upload Settings</th></tr></thead>'+
                    '<tbody>'+
                    '<tr><td colspan="3">Overwrite Files ?<input type="radio" name="collabNetoverWriteFiles" checked>Yes<input type="radio" name="collabNetoverWriteFiles">No</td></tr>'+
                    '<tr><td><input type="text" placeholder="Url"></td><td><input type="text" placeholder="Username"></td><td><input type="password" placeholder="Password"></td></tr>'+
                    '<tr><td><input name="collabNetProject" type="text" placeholder="Project"></td><td><input name="collabNetPackage" type="text" placeholder="Package"></td><td><input name="collabNetRelease" type="text" placeholder="Release"></td></tr>'+
                    '</tbody>'+
                	'</table>';

					$(uploadSettingsElem).html(uploadSettingsHtml);
				} else if (templateJsonData.uploadType === "confluence") {
					var uploadSettingsHtml = '<table id="collabnetUploadSettings" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
                    '<thead><tr><th colspan="3">CollabNet Upload Settings</th></tr></thead>'+
                    '<tbody>'+
                    '<tr><td colspan="3">Overwrite Files ?<input type="radio" name="collabNetoverWriteFiles" checked>Yes<input type="radio" name="collabNetoverWriteFiles">No</td></tr>'+
                    '<tr><td><input type="text" placeholder="Url"></td><td><input type="text" placeholder="Username"></td><td><input type="password" placeholder="Password"></td></tr>'+
                    '<tr><td><input name="collabNetProject" type="text" placeholder="Project"></td><td><input name="collabNetPackage" type="text" placeholder="Package"></td><td><input name="collabNetRelease" type="text" placeholder="Release"></td></tr>'+
                    '</tbody>'+
                	'</table>';
					$(uploadSettingsElem).html();
				}
			}

			//scheduler
			if (!templateJsonData.enableSheduler) {
				$("#scheduler").remove();
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
			//console.log("operation > " + operation);

			if ("Code Validation" === operation) {
				commonVariables.goal = commonVariables.codeValidateGoal;
			} else if ("Build" === operation) {
				commonVariables.goal = commonVariables.packageGoal;
			} else if ("Deploy" === operation) {
				commonVariables.goal = commonVariables.deployGoal;
			} else if ("Unit Test" === operation) {
				commonVariables.goal = commonVariables.unitTestGoal;
			} else if ("Component Test" === operation) {
				commonVariables.goal = commonVariables.componentTestGoal;
			} else if ("Functional Test" === operation) {
				commonVariables.goal = commonVariables.functionalTestGoal;
			} else if ("Performance Test" === operation) {
				commonVariables.goal = commonVariables.performanceTestGoal;
			} else if ("Load Test" === operation) {
				commonVariables.goal = commonVariables.loadTestGoal;
			} else if ("PDF Report" === operation) {
				commonVariables.goal = commonVariables.pdfReportGoal;
			}

			commonVariables.phase = commonVariables.ciPhase;
			commonVariables.appDirName = appDirName;

			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(false, function(response) {

					if ("No parameters available" == response) {
						//console.log("No parameters available ");
						$("#dynamicContent").empty();
					} else {
						$("#dynamicContent").html(response);
						dynamicPageObject.showParameters();
						self.dynamicPageListener.controlEvent();
					}

					// Restore existing values
					// get all the elemenst of the form and iterate through that, check for type and populate the value
					if (!self.isBlank(jobJsonData)) {
						self.restoreFormValues($("#jonConfiguration"), jobJsonData);
					}

					// Open popup
					commonVariables.openccmini(thisObj, 'jobConfigure');
				});
			});
		},

		restoreFormValues : function (formObj, data) {
			var self = this;

		    if (!self.isBlank(formObj)) {
		    	for ( var i = 0; i < $(formObj)[0].elements.length; i++ ) {

			        var e = $(formObj)[0].elements[i];

			        var key = e.name;
			        var value = data[key];

			        if (self.isBlank(e.name)) continue; // Shortcut, may not be suitable for values = 0 (considered as false)

			        //console.log("Name => " + key + " :: Type => " + e.type  + " :: Value => " + value + " :: Value type => " + $.type(value));

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
			            	} else if ($.type(value) === "string") { // other case
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
			if (self.isBlank($("input[name=jobName]").val())) {
				//console.log("Name is empty ");
				return false;
			}

			// when the repo is svn or git need validation
			var emptyFound = false;

			//repo url validation
			if (!self.isBlank(templateJsonData.repoTypes) && (templateJsonData.repoTypes === "svn" || templateJsonData.repoTypes === "git")) {
				$('#repoType input').each(function() {
			    	if (self.isBlank(this.value)) {
			           //$("#error").show('slow');
			           //console.log("empty " + this.name);
			           emptyFound = true;
			           return false;
			       	} 
			    });
			}

			//scheduler validation

			//mail settings validation

			// validation ends

			if (!emptyFound) {
				// append the configureJob json (jobJson) in  job template name id
				var jobConfiguration = $('#jonConfiguration').serializeObject();

				//console.log("Storing " + JSON.stringify(jobConfiguration));
				//Checking
				$('[appname="'+ appName +'"][jobtemplatename="'+ jobtemplatename +'"]').data("jobJson", jobConfiguration);

	            // Hide popup
	            $(".dyn_popup").hide();
			}
		},

		constructJobTemplateViewByEnvironment : function (response) {
			var self = this;
			var data = response.data;
			if (!self.isBlank(data)) {
				// appinfo job templates
				//var continuousDeliveryJobTemplates = "";
				$("#sortable1").empty();
				$.each(data, function(key, value) {
					//console.log("Type key " + $.type(key));
					//console.log("Type value " + $.type(value));

					//console.log("key > " + JSON.stringify(key));
					var parseJson = $.parseJSON(key);
					//console.log("appName " + parseJson.appName);
					//console.log("appDirName " + parseJson.appDirName);
					var appName = parseJson.appName;
					var appDirName = parseJson.appDirName;

					var jobTemplateApplicationName = '<div class="sorthead">'+ appName +'</div>';
					$("#sortable1").append(jobTemplateApplicationName);

					if (!self.isBlank(data)) {
						// job tesmplate key and value
						$.each(value, function(jobTemplateKey, jobTemplateValue) {

							var jobTemplateGearHtml = '<a href="javascript:;" id="'+ jobTemplateValue.name +'" class="validate_icon" jobTemplateName="'+ jobTemplateValue.name +'" appName="'+ appName +'" appDirName="'+ appDirName +'" name="jobConfigurePopup" style="display: none;"><img src="themes/default/images/helios/validate_image.png" width="19" height="19" border="0"></a>';
                    		var jobTemplateHtml = '<li class="ui-state-default"><span>' + jobTemplateValue.name + ' - ' + jobTemplateValue.type + '</span>' + jobTemplateGearHtml + '</li>'
                    		$("#sortable1").append(jobTemplateHtml);

                    		// set json value on attribute
                    		var jobTemplateJsonVal = jobTemplateValue;
                    		$('#'+ jobTemplateValue.name).data("templateJson", jobTemplateJsonVal);
						});
					}

				});
			}
		},

		/***
		 * This method automatically manipulates upstream and downstream as well as validation
		 *
		 */
		streamConfig : function(thisObj, callback) {
	  		// third Construct upstream and downstream validations
	  		// all li elemnts of this
	  		//console.log("upstream and sownstream construction ");
			$($(thisObj).find('li').get().reverse()).each(function() {
				//console.log("elem span" + $(thisObj).find('span').text());
				    var anchorElem = $(thisObj).find('a');
				    var appName = $(anchorElem).attr("appname");
				    var templateJsonData = $(anchorElem).data("templateJson");
				    //console.log("templateJsonData => " + JSON.stringify(templateJsonData));
				    var jobJsonData = $(anchorElem).data("jobJson");
				    //console.log("jobJsonData => " + JSON.stringify(jobJsonData));
				    // upstream and downstream and clone workspace except last job
				    
				    var preli = $(thisObj).prev('li')
				    var preAnchorElem = $(preli).find('a');
				    var preTemplateJsonData = $(preAnchorElem).data("templateJson");
				    var preJobJsonData = $(preAnchorElem).data("jobJson");
				    //console.log("preJobJsonData > " + preJobJsonData);
				    
				    var nextli = $(thisObj).next('li');
				    var nextAnchorElem = $(nextli).find('a');
				    var nextTemplateJsonData = $(nextAnchorElem).data("templateJson");
				    var nextJobJsonData = $(nextAnchorElem).data("jobJson");
				    //console.log("nextJobJsonData > " + nextJobJsonData);


				    if (jobJsonData != undefined && jobJsonData != null) {
				        jobJsonData = {};
				        //console.log("Erorr ... ");
				    }

				    // Downstream
				    if (nextJobJsonData != undefined && nextJobJsonData != null) {
				    	// downstream specifies the next job which needs to be triggered after this job
				    	//console.log("Downstream job name => " + nextJobJsonData.name);
				        jobJsonData.downstreamApplication = nextJobJsonData.name;
				    } else {
				    	//console.log("next job data not found ... ");
				    }

				    // No use parent job
				    if (preJobJsonData != undefined && preJobJsonData != null) {
				    	// upstream job specifies the from where this app will be triggered, this will be based on assumption, only for UI purpose
				    	//console.log("Upstream job name => " + preJobJsonData.name);
				        jobJsonData.upstreamApplication = preJobJsonData.name;
				    } else {
				    	//console.log("previous job data not found ... ");
				    }

				    //console.log("is previous App have to repo check");
				    // Is parent app available for this app job
				    var parentAppFound = false;
				    var workspaceAppFound = false;
				    // check for previous job to find its cloneJobName
			  		$(thisObj).prevAll('li').each(function(index) {
			  			// Corresponding element access
					    var thisAnchorElem = $(thisObj).find('a');
					    //console.log("elem span" + $(thisObj).find('span').text());
			  			var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
			  			var thisJobJsonData = $(thisAnchorElem).data("jobJson");
			  			var thisAppName = $(thisAnchorElem).attr("appname");

			  			if (thisAppName === appName && thisTemplateJsonData.enableRepo) {
			  				parentAppFound = true;
			  				//console.log("Parent project found ");
			  			}

			  			if (thisAppName === appName && !workspaceAppFound) {
			  				workspaceAppFound = true;
			  				// from where this jobs source code have to come from
			  				//console.log("Applications clone job job found ");
			  				// Upstream app
			  				if (thisJobJsonData != undefined && thisJobJsonData != null) {
				        		jobJsonData.cloneJobName = thisJobJsonData.name;
				        		thisJobJsonData.clonetheWrokspace = true;
				        		$(thisAnchorElem).data("jobJson", thisJobJsonData);
				        		return false;
				    		}
			  			}
					});
					
					if (!parentAppFound) {
						//console.log("Not able to find its parent source app for this job");
					}

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
			var jobs = {};
			var sortable2LiObj = $("#sortable2 li");

			$(sortable2LiObj).each(function(index) {
				var thisAnchorElem = $(this).find('a');
				//console.log("elem span" + $(this).find('span').text());
				var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
				//console.log("thisTemplateJsonData => " + JSON.stringify(thisTemplateJsonData));
				var thisJobJsonData = $(thisAnchorElem).data("jobJson");
			    //console.log("jobJsonData => " + JSON.stringify(thisJobJsonData));
				var thisAppName = $(thisAnchorElem).attr("appname");

				// open the popup and ask the user to input the data, Once the popup is opened all the data will be validates
				if (self.isBlank(thisJobJsonData)) {
					self.showConfigureJob(thisAnchorElem);
					//console.log("Fille the job template");
					return false;
				} else {
					//console.log("save continuous delivery validation starts here");
					// all the input text box should not be empty
					if (isBlank(thisJobJsonData.name)) {
						self.showConfigureJob(thisAnchorElem);
						return false;
					}

					// more validation can be added here

				}
			});

			// Upstream and downstream config auto population goes here
			self.streamConfig(sortable2LiObj, function() {


				// construct the job json data here once all the data are validated
				callback(jobs);
			});
		},



		saveContinuousDeliveryValidation : function (thisObj, callback) {
			var self = this;
			callback();
		}
				
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});