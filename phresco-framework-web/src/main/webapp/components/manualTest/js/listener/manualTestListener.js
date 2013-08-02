define([], function() {

	Clazz.createPackage("com.components.manualTest.js.listener");

	Clazz.com.components.manualTest.js.listener.manualTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		testResultListener : null,
		mavenServiceListener : null,
		manualRequestBody : null,
		hasError: false,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.testResultListener === null) {
				self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			}
			if (self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}
		},
		
		onGraphicalView : function() {
			var self = this;
			self.testResultListener.showGraphicalView();
		},
		
		onTabularView : function() {
			var self = this;
			self.testResultListener.showTabularView();
		},
	
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, header, data = {}, userId;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			userId = data.id;
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}

			if(action === "get") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testsuites?appDirName=' + appDirName;
			} else if (action === "addTestSuite") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=' + requestBody.testSuiteName + '&appDirName=' + appDirName;
			} else if (action === "addTestcase") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=' + requestBody.testSuiteName + '&appDirName=' + appDirName;
			} else if (action === "downloadTemplate") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "manual/manualTemplate?fileType="+requestBody.format;
			} 
			
			/*else if (action === "updateTestcase") {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=aaaaaaa&appDirName=' + appDirName;
				console.info("header.webserviceurl ... " + header.webserviceurl);
			}*/
			return header;
		},
		
		getManualTestReport : function(header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null) {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							self.loadingScreen.removeLoading();
							callback({"status" : "service failure"});
						}
					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
			}
		},
		
		addManualTestSuite : function(testSuiteVal) {
			var self = this, data = {};
			if(!self.validationTestSuite()) {
				data.testSuiteName = testSuiteVal;
				self.manualRequestBody = data;
				self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestSuite"), function(response) {
				});
				commonVariables.navListener.onMytabEvent("manualTest");
			}
		},
		
		validationTestSuite : function() {
			var self = this;
			
		    if($('#testSuiteId').val() === ""){
				self.valid("#testSuiteId", "Testsuite Name");
				self.hasError = true;
		    } else {
		    	self.hasError = false;
		    } 
	    	return self.hasError;
		    	
		},
		
		validationTestcase : function() {
			var self = this;
			
			if ($('#featureId').val() === "") {
		    	self.valid("#featureId", "Feature Name");
		    	self.hasError = true;
		    } else if ($('#testCaseId').val() === "") {
		    	self.valid("#testCaseId", "Feature Name");
		    	self.hasError = true;
		    } else {
		    	self.hasError = false;
		    } 
	    	return self.hasError;
		    	
		},
		
		
		valid : function(item, msg) {
			$(item).focus();
			$(item).attr('placeholder', msg);
			$(item).addClass("errormessage");
			$(item).bind('keypress', function() {
				$(this).removeClass("errormessage");
			});
		},
		
		addManualTestcase : function(testsuiteName) {
			var self = this; data = {}, response = {};
			if(!self.validationTestcase()) {
				data = $("#manualTestTestCaseForm").serializeObject();
				self.manualRequestBody = data;
				$("#show_manualTestCase_popup").toggle();
				self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestcase"), function(response) {
					self.testResultListener.onTestResultDesc(response);
				});
			}
		},
		
		updateManualTestcase : function(testcaseVal) {
			var self = this, data = {};
			data.testSuiteName = testcaseVal;
			self.manualRequestBody = data.testSuiteName;
			self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "updateTestcase"), function(response) {
			});
		},
		
		createUploader : function() {     
			var appDirName = commonVariables.api.localVal.getSession("appDirName");
			var uploadFileUrl =commonVariables.webserviceurl + commonVariables.manual + '/uploadTemplate?appDirName=' + appDirName;
            var uploader = new qq.FileUploader({
                element: document.getElementById('manual_temp_upload'),
                action: uploadFileUrl,
				actionType : "manualTest",
				appDirName : appDirName,
				allowedExtensions : ['xls','xlsx'],
				buttonLabel: "Upload File",
				multiple: false,
				debug: true
            });           
        },
        
		openPopUpToEdit : function(obj) {
			self.openccpl(obj, 'show_manualTestCase_popup','');
		},
		
		downloadTemplate : function(format) {
			var downloadTemplateUrl = commonVariables.webserviceurl + commonVariables.manual + "/manualTemplate?fileType=" + format;
			window.open(downloadTemplateUrl, '_self');
		}
	});

	return Clazz.com.components.manualTest.js.listener.ManualTestListener;
});