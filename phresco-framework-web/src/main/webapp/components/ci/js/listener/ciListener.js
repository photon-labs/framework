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
				//var jsonString = $.toJSON(myObj);
				//$('#formCustomers').toJSON(); for POST and PUT
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
			} else if (action === "update") {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(ciRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.jobTemplates;
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

			var formObj = $("#jobTemplate"); // Object
			$('#jobTemplate #features :checkbox:not(:checked)').attr('value', false); // make checked checkbox value to false
			$('#jobTemplate #features :checkbox:checked').attr('value', true); // make checked checkbox value to true

			var jobTemplate = $('#jobTemplate').serializeObject();

			// Convert appIds to array
			jobTemplate.appIds = [];
			$("input[name=appIds]:checked").each(function() {
			    jobTemplate.appIds.push(this.value);
			});

			// jobTemplate = $.makeArray(jobTemplate);
			callback(jobTemplate);
		},

		listJobTemplate : function (header, callback) {
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

		updateJobTemplate : function () {
			var self=this;
			alert("Add job template ");
		},

		deleteJobTemplate : function () {
			var self=this;
			alert("Add job template ");
		}
		
	});

	return Clazz.com.components.ci.js.listener.CIListener;
});