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
				console.log("log => " + JSON.stringify(ciRequestBody));
				var oldname = $('[name="oldname"]').val();
				console.log("oldname => " + oldname);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=" + oldname;
			} else if (action === "edit") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
				if (params !== null && params !== undefined && params !== '') {
					header.webserviceurl = header.webserviceurl + "/" + params.name + "?customerId="+ customerId + "&projectId=" + projectId;
				}
			} else if (action === "delete") {
				console.log("Deleet params " + params);
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
			var self=this;
			var jobTemplate = self.constructJobTemplate();

			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		listJobTemplate : function (header, callback) {
			var self=this;
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
			var self=this;
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
			var self=this;
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
			var self=this;
		},

		loadEnvironmentEvent : function (callback) {
			var dataObj = {};
			var environment = $("select[name=environments]").val();
			dataObj.envName = environment;
			callback(dataObj);
		},


		getDynamicParams : function(thisObj) {
			var self = this;
			//commonVariables.goal = commonVariables.ciPhase;
			commonVariables.goal = commonVariables.unitTestGoal;
			commonVariables.appDirName = "wwwww-Java WebService";

			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(false, function(response) {
					if ("No parameters available" == response) {
						console.log("No parameters available ");
					} else {
						console.log("Parameters available ");
						$("#dynamicContent").html(response);
						dynamicPageObject.showParameters();
						self.dynamicPageListener.controlEvent();
						commonVariables.openccmini(thisObj, $(thisObj).attr("name"));
					}
				});
			});
		},

		constructJobTemplateViewByEnvironment : function (response) {
			var self = this;
			console.log("constructJobTemplateViewByEnvironment = > " + JSON.stringify(response.data));
			var data = response.data;
			console.log(self.isBlank(data));
			if (!self.isBlank(data)) {
				// appinfo job templates
				var continuousDeliveryJobTemplates = "";
				$.each(data, function(key, value) {
					console.log("key > " + JSON.stringify(key));
					console.log("value > " + JSON.stringify(value));
					continuousDeliveryJobTemplates = continuousDeliveryJobTemplates.concat('<div class="sorthead">'+ key +'</div>');
					if (!self.isBlank(data)) {
						// job tesmplate key and value
						$.each(value, function(jobTemplateKey, jobTemplateValue) {
							console.log("jobTemplateValue > " + JSON.stringify(jobTemplateValue.name));
							console.log("jobTemplateValue > " + JSON.stringify(jobTemplateValue.type));
							var jobTemplateGearHtml = '<a href="javascript:;" class="validate_icon" jobTemplateName="'+ jobTemplateValue.name +'" correspondingApp="'+ key +'" name="jobConfigure" style="display: none;"><img src="themes/default/images/helios/validate_image.png" width="19" height="19" border="0"></a>';
                    		var jobTemplateHtml = '<li class="ui-state-default">' + jobTemplateValue.name + ' - ' + jobTemplateValue.type + jobTemplateGearHtml + '</li>'
							continuousDeliveryJobTemplates = continuousDeliveryJobTemplates.concat(jobTemplateHtml);
						});
					}

				});
				$("#sortable1").html(continuousDeliveryJobTemplates);
			}
		}
		
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});