define(["functionalTest/api/functionalTestAPI"], function() {

	Clazz.createPackage("com.components.functionalTest.js.listener");

	Clazz.com.components.functionalTest.js.listener.FunctionalTestListener = Clazz.extend(Clazz.Widget, {
		
		functionalTestAPI : null,
		testResultListener : null,
		dynamicpage : null,
		dynamicPageListener : null,
		mavenServiceListener : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.functionalTestAPI === null) {
				self.functionalTestAPI =  new Clazz.com.components.functionalTest.js.api.FunctionalTestAPI();
			}
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
			data = JSON.parse(self.functionalTestAPI.localVal.getSession('userInfo'));
			userId = data.id;
			appDirName = self.functionalTestAPI.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
					
			if (action == "getFunctionalTestOptions") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/functionalFramework?appDirName="+appDirName;				
			}
			return header;
		},
		
		getDynamicParams : function(goal) {
			var self = this;
			commonVariables.goal = goal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(false, function(response) {
					if (goal == commonVariables.functionalTestGoal) {
						$("#dynamicContent").html(response);
					} else if (goal == commonVariables.startHubGoal) {
						$("#startHubDynamicContent").html(response);
					} else if (goal == commonVariables.startNodeGoal) {
						$("#startNodeDynamicContent").html(response);
					}
//					self.multiselect();
					dynamicPageObject.showParameters();
					self.dynamicPageListener.controlEvent();
				});
			});
		},
		
		getFunctionalTestOptions : function(header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				self.functionalTestAPI.functionalTest(header,
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
		
		runFunctionalTest : function(from) {
			var self = this;
			var testData
			if (from === "startHub") {
				$("#startHub_popup").toggle();
				testData = $('#startHubForm').serialize();
			}
			if (from === "startNode") {
				$("#startNode_popup").toggle();
				testData = $('#startNodeForm').serialize();
			}
			if (from === "runFunctionalTest") {
				$("#functionalTest_popup").toggle();
				testData = $('#functionalTestForm').serialize();
			}
			var appdetails = self.functionalTestAPI.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = self.functionalTestAPI.localVal.getSession('username');
						
			if (appdetails != null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+testData;
			}
			$('#functionalTestConsole').html('');
				
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					if (from === "startHub") {
						self.mavenServiceListener.mvnStartHub(queryString, '#functionalTestConsole');
					} else if (from === "startNode") {
						self.mavenServiceListener.mvnStartNode(queryString, '#functionalTestConsole');
					} else if (from === "runFunctionalTest") {
						self.mavenServiceListener.mvnFunctionalTest(queryString, '#functionalTestConsole');
					}
				});
			} else {
				if (from === "startHub") {
					self.mavenServiceListener.mvnStartHub(queryString, '#functionalTestConsole');
				} else if (from === "startNode") {
					self.mavenServiceListener.mvnStartNode(queryString, '#functionalTestConsole');
				} else if (from === "runFunctionalTest") {
					self.mavenServiceListener.mvnFunctionalTest(queryString, '#functionalTestConsole');
				}
			}			
		}
	});

	return Clazz.com.components.functionalTest.js.listener.FunctionalTestlistener;
});