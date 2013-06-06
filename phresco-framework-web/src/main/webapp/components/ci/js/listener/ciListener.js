define(["ci/api/ciAPI"], function() {

	Clazz.createPackage("com.components.ci.js.listener");

	Clazz.com.components.ci.js.listener.CIListener = Clazz.extend(Clazz.Widget, {
		
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
			var self=this;
			commonVariables.navListener.getMyObj(commonVariables.continuousDeliveryConfigure, function(retVal){
				self.continuousDeliveryConfigure = 	retVal;
			}); 
			Clazz.navigationController.push(self.continuousDeliveryConfigure, true);
		},

		getRequestHeader : function(ciRequestBody, action, params) {
			// var self = this, header, appDirName;
			// var customerId = self.getCustomer();
			// customerId = (customerId == "") ? "photon" : customerId;
			// appDirName = self.configurationAPI.localVal.getSession("appDirName");
			// data = JSON.parse(self.configurationAPI.localVal.getSession('userInfo'));
			// var userId = data.id;
			// var techId = commonVariables.techId;
			// self.bcheck = false;
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			if (action === "list") {
				header.requestMethod = "GET";
				if (ciRequestBody !== null && ciRequestBody !== undefined) { 
					console.log("Request body is undefined... ");
				}
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
				if (params !== null && params !== undefined && params !== '') {
					header.webserviceurl = header.webserviceurl + "?" + params;
				}
			} else if (action === "add") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
			} else if (action === "update") {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				console.log("log => " + JSON.stringify(ciRequestBody));
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
			} else if (action === "edit") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
				if (params !== null && params !== undefined && params !== '') {
					header.webserviceurl = header.webserviceurl + "/" + params.name;
				}
			} else if (action === "delete") {
				header.requestMethod = "DELETE";
				if (ciRequestBody !== null && ciRequestBody !== undefined) { 
					console.log("Request body is undefined... ");
				}
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
				if (params !== null && params !== undefined && params !== '') {
					header.webserviceurl = header.webserviceurl + "?" + params;
				}
			} 

			return header;
		},

		addJobTemplate : function (callback) {
			var self=this;
			console.log("add job template ");
			var jobTemplate = self.constructJobTemplate();

			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		listJobTemplate : function (header, callback) {
			console.log("list templete");
			var self=this;
			try {
				self.ciAPI.ci(header,
					function(response) {
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

		updateJobTemplate : function (callback) {
			var self = this;
			console.log("update job template ");
			var jobTemplate = self.constructJobTemplate();
			console.log("update job template " + JSON.stringify(jobTemplate));
			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		constructJobTemplate : function () {
			var formObj = $("#jobTemplate"); // Object
			$('#jobTemplate #features :checkbox:not(:checked)').attr('value', false); // make checked checkbox value to false
			$('#jobTemplate #features :checkbox:checked').attr('value', true); // make checked checkbox value to true

			var jobTemplate = $('#jobTemplate').serializeObject();

			// Convert appIds to array
			jobTemplate.appIds = [];
			$("input[name=appIds]:checked").each(function() {
			    jobTemplate.appIds.push(this.value);
			});
			return jobTemplate;
		},

		editJobTemplate : function (data) {
			var self=this;
			console.log("edit job template " + JSON.stringify(data));
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
			$("[name=type]").val(data.type);
			$.each(data.appIds, function(index, value) {
				//alert(index + ': ' + value);
				$('[name=appIds][value='+ value +']').prop('checked', true);
			});

			$("[name=repoTypes]").val(data.repoTypes);

			$('[name=enableRepo]').prop('checked', data.enableRepo);
			$('[name=enableSheduler]').prop('checked', data.enableSheduler);
			$('[name=enableEmailSettings]').prop('checked', data.enableEmailSettings);
			$('[name=enableUploadSettings]').prop('checked', data.enableUploadSettings);
			// button name change
			$('input[name=save]').prop("value", "Update");
			$('input[name=save]').prop("name", "update");
			// bind the update event
			//$("input[name=save]").unbind("click");

		},

		deleteJobTemplate : function () {
			var self=this;
			console.log("delete job template ");
		}
		
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});