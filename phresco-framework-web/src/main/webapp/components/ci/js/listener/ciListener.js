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

		validation : function () {
			try {
				var self = this, bCheck = false;
				if ($('input[name="name"]').val() == undefined || $('input[name="name"]').val() == null || $.trim($('input[name="name"]').val()) == "") {
					$('input[name="name"]').attr('placeholder','Enter Username');
					$('input[name="name"]').addClass("loginuser_error");
					bCheck = false;
				}

				if($('#appIdsList').find('input[type=checkbox]:checked').length == 0) {
        				alert('Please select atleast one application');
    			}

    			if($('#features').find('input[type=checkbox]:checked').length == 0) {
        				alert('Please select atleast one feature');
    			}

			} catch (error) {
				//Exception
				console.log("Exception ");
			}
		},

		isNameExists : function () {

		},
		
		validate : function (callback) {
			$('#errMsg').html('');
			var self=this;
			var status = true;
			$("#errMsg").removeClass("errormessage");
			$('input[name=name]').removeClass("errormessage");	
			$('#errMsg').html('');
			var appIds = $('[name=appIds]:checked').length;
			if(appIds === 0) {
				$('#errMsg').html('Select atleast one application');
				$("input[name='appIds']").focus();
				$('#errMsg').addClass("errormessage");
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
			$("input[name=appIds]:checked").each(function() {
			    jobTemplate.appIds.push(this.value);
			});
			return jobTemplate;
		},

		editJobTemplate : function (data) {
			var self = this;
			//$.each(data, function(key, value) {
    			//display the key and value pair
    			//console.log(key + ' is ' + value);
    			//$("#elementId").is("input")
    			// if ($.isArray(value)) {

    			// } else {
    			// 	$("input[name="+key+"]").val(value);
    			// }
			//});

			$("[name=name]").val(data.name);
			$("[name=oldname]").val(data.name);
			$("[name=type]").val(data.type);
			$.each(data.appIds, function(index, value) {			
				$('[name=appIds][value="'+ value +'"]').prop('checked', true);				
			});

			$("[name=repoTypes]").val(data.repoTypes);

			$('[name=enableRepo]').prop('checked', data.enableRepo);
			$('[name=enableSheduler]').prop('checked', data.enableSheduler);
			$('[name=enableEmailSettings]').prop('checked', data.enableEmailSettings);
			$('[name=enableUploadSettings]').prop('checked', data.enableUploadSettings);
			// button name change
			$('input[name=save]').prop("value", "Update");
			$('input[name=save]').prop("name", "update");
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

			// elements
			var repoTypeElem = $("#repoType tbody tr");
			var repoTypeTitleElem = $("#repoType thead tr th");

			// Repo types
			if (!self.isBlank(templateJsonData.repoTypes) && templateJsonData.repoTypes === "svn") {
				// For svn
				$(repoTypeElem).html('<td><input type="text" placeholder="SVN Url" name="url"></td>'+
                        '<td><input type="text" placeholder="Username" name="userName"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else if (!self.isBlank(templateJsonData.repoTypes) && templateJsonData.repoTypes === "git") {
				// For GIT
				$(repoTypeElem).html('<td><input type="text" placeholder="GIT Url" name="url"></td>'+
                        '<td><input type="text" placeholder="Username" name="userName"></td>'+
                        // '<td><input type="text" placeholder="Branch" name="branch"></td>'+
                        '<td><input type="password" placeholder="Password" name="password"></td>');
			} else {
				// For clonned workspace
				$(repoTypeElem).html('<select id="clonnedworkspaces" name="clonnedworkspaces"></select>');
				// set label value
				$(repoTypeTitleElem).html("Clonned workspace");
			}

			// Upload configurations
			if (templateJsonData.enableUploadSettings) {
				//elements
				var uploadSettingsElem = $("#uploadSettings");

				$(uploadSettingsElem).empty();
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

			// templateJsonData.type
			// TODO: Based on type dynamic page have to render
			var operation = templateJsonData.type;

			// Get app name
			var appName = $(thisObj).attr("appname");
			var jobtemplatename = $(thisObj).attr("jobtemplatename");

			// set corresponding job template name and their app name in configure button
			$("[name=configure]").attr("appname", appName);
			$("[name=configure]").attr("jobtemplatename", jobtemplatename);

			// Dynamic param
			//commonVariables.goal = commonVariables.ciPhase;
			commonVariables.goal = commonVariables.unitTestGoal;
			commonVariables.appDirName = "wwwww-Java WebService";

			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(false, function(response) {
					if ("No parameters available" == response) {
						console.log("No parameters available ");
					} else {
						$("#dynamicContent").html(response);
						dynamicPageObject.showParameters();
						self.dynamicPageListener.controlEvent();
						// OPen popup
						commonVariables.openccmini(thisObj, 'jobConfigure');
					}
				});
			});
		},

		configureJob : function (thisObj) {
			// Get app name
			var appName = $(thisObj).attr("appname");
			var jobtemplatename = $(thisObj).attr("jobtemplatename");

			// append the configureJob json (jobJson) in  job template name id
			var jobConfiguration = $('#jonConfiguration').serializeObject();

			//Checking
			$('[appname="'+ appName +'"][jobtemplatename="'+ jobtemplatename +'"]').data("jobJson", jobConfiguration);

            // Hide popup
            $(".dyn_popup").hide();
		},

		constructJobTemplateViewByEnvironment : function (response) {
			var self = this;
			var data = response.data;
			if (!self.isBlank(data)) {
				// appinfo job templates
				//var continuousDeliveryJobTemplates = "";
				$("#sortable1").empty();
				$.each(data, function(key, value) {
					var jobTemplateApplicationName = '<div class="sorthead">'+ key +'</div>';
					$("#sortable1").append(jobTemplateApplicationName);

					if (!self.isBlank(data)) {
						// job tesmplate key and value
						$.each(value, function(jobTemplateKey, jobTemplateValue) {

							var jobTemplateGearHtml = '<a href="javascript:;" id="'+ jobTemplateValue.name +'" class="validate_icon" jobTemplateName="'+ jobTemplateValue.name +'" appName="'+ key +'" name="jobConfigurePopup" style="display: none;"><img src="themes/default/images/helios/validate_image.png" width="19" height="19" border="0"></a>';
                    		var jobTemplateHtml = '<li class="ui-state-default">' + jobTemplateValue.name + ' - ' + jobTemplateValue.type + jobTemplateGearHtml + '</li>'
                    		$("#sortable1").append(jobTemplateHtml);

                    		// set json value on attribute
                    		var jobTemplateJsonVal = jobTemplateValue;
                    		$('#'+ jobTemplateValue.name).data("templateJson", jobTemplateJsonVal);
						});
					}

				});
			}
		}
		
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});