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
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=" + oldname;
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
				self.ciAPI.ci(header, function(response) {
						if (response !== null) {
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						console.info('textStatus',textStatus);
						callback({ "status" : "Connection failure"});
					}
				);
			} catch(exception) {
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
						console.info('textStatus',textStatus);
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
			$('#jobTemplate #features :checkbox:not(:checked)').attr('value', false); // make checked checkbox value to false
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
				//alert(index + ': ' + value);
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
				console.log("Upload settings design chnages and type need to get from UI");
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
			console.log("operation > " + operation);

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
						console.log("No parameters available ");
						$("#dynamicContent").empty();
					} else {
						console.log("Parameters available ");
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
				alert("Name is empty ");
				return false;
			}

			// when the repo is svn or git need validation
			var emptyFound = false;
			if (!self.isBlank(templateJsonData.repoTypes) && (templateJsonData.repoTypes === "svn" || templateJsonData.repoTypes === "git")) {
				$('#repoType input').each(function() {
			    	if (this.value == "") {
			           //$("#error").show('slow');
			           console.log("empty " + this.name);
			           emptyFound = true;
			           return false;
			       	} 
			    });
			}
			// validation ends

			if (!emptyFound) {
				// append the configureJob json (jobJson) in  job template name id
				var jobConfiguration = $('#jonConfiguration').serializeObject();

				console.log("Storing " + JSON.stringify(jobConfiguration));
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
					console.log("Type key " + $.type(key));
					console.log("Type value " + $.type(value));

					console.log("key > " + JSON.stringify(key));
					var parseJson = $.parseJSON(key);
					console.log("appName " + parseJson.appName);
					console.log("appDirName " + parseJson.appDirName);
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

		saveContinuousDelivery : function (thisObj, callback) {
			var self = this;
			var jobs = {};

			$("#sortable2 li").each(function(index) {
				var thisAnchorElem = $(this).find('a');
				console.log("elem span" + $(this).find('span').text());
				var thisTemplateJsonData = $(thisAnchorElem).data("templateJson");
				console.log("thisTemplateJsonData => " + JSON.stringify(thisTemplateJsonData));
				var thisJobJsonData = $(thisAnchorElem).data("jobJson");
			    console.log("jobJsonData => " + JSON.stringify(thisJobJsonData));
				var thisAppName = $(thisAnchorElem).attr("appname");

				// open the popup and ask the user to input the data, Once the popup is opened all the data will be validates
				if (self.isBlank(thisJobJsonData)) {
					self.showConfigureJob(thisAnchorElem);
					alert("Fille the job template");
					return false;
				} else {
					console.log("Validation starts here ");
					// all the input text box should not be empty
					if (isBlank(thisJobJsonData.name)) {
						self.showConfigureJob(thisAnchorElem);
					}
					return false;
				}

			});

			// construct the job json data here once all the data are validated
			callback(jobs);
		},

		saveContinuousDeliveryValidation : function (thisObj, callback) {
			var self = this;
			callback();
		}
				
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});