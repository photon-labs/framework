define([], function() {

	Clazz.createPackage("com.components.manualTest.js.listener");

	Clazz.com.components.manualTest.js.listener.manualTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		testResultListener : null,
		mavenServiceListener : null,
		manualRequestBody : null,
		
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
			} /*else if (action === "updateTestcase") {
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
			data.testSuiteName = testSuiteVal;
			self.manualRequestBody = data;
			self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestSuite"), function(response) {
			});
			commonVariables.navListener.onMytabEvent("manualTest");
		},
		
		addManualTestcase : function(testsuiteName) {
			var self = this; data = {}, response = {};
			data = $("#manualTestTestCaseForm").serializeObject();
			self.manualRequestBody = data;
			$("#show_manualTestCase_popup").toggle();
			self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestcase"), function(response) {
				self.testResultListener.onTestResultDesc(response);
			});
		},
		
		updateManualTestcase : function(testcaseVal) {
			var self = this, data = {};
			data.testSuiteName = testcaseVal;
			self.manualRequestBody = data.testSuiteName;
			self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "updateTestcase"), function(response) {
			});
		},
		
		openPopUpToEdit : function(obj) {
			self.openccpl(obj, 'show_manualTestCase_popup','');
		}

	});

	return Clazz.com.components.manualTest.js.listener.ManualTestListener;
});