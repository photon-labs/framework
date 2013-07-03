define(["functionalTest/api/functionalTestAPI"], function() {

	Clazz.createPackage("com.components.functionalTest.js.listener");

	Clazz.com.components.functionalTest.js.listener.FunctionalTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
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
			};
					
			if (action === "getFunctionalTestOptions") {
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
					var height = $(".testSuiteTable").height();
					if (goal === commonVariables.functionalTestGoal) {
						$("#dynamicContent").html(response);
						$('#functionalTest_popup').css("max-height", height - 40 + 'px');
						$('#dynamicContent').css("max-height", height - 92 + 'px');
						self.opencc($("#functionalTestBtn"), 'functionalTest_popup');
					} else if (goal === commonVariables.startHubGoal) {
						$("#startHubDynamicContent").html(response);
						$('#startHub_popup').css("max-height", height - 40 + 'px');
						$('#startHubDynamicContent').height("auto");
						$('#startHubDynamicContent').css("max-height", height - 92 + 'px');
						self.opencc($("#startHub"), 'startHub_popup');
					} else if (goal === commonVariables.startNodeGoal) {
						$("#startNodeDynamicContent").html(response);
						$('#startNode_popup').css("max-height", height - 40 + 'px');
						$('#startNodeDynamicContent').css("max-height", height - 92 + 'px');
						$('#startNodeDynamicContent').height("auto");
						self.opencc($("#startNode"), 'startNode_popup');
					}
					
					dynamicPageObject.showParameters();
					self.dynamicPageListener.controlEvent();
					$(".scrollContent").mCustomScrollbar({
						autoHideScrollbar:true,
						theme:"light-thin",
						advanced:{ updateOnContentResize: true}
					});
				});
			});
		},
		
		getFunctionalTestOptions : function(header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				self.functionalTestAPI.functionalTest(header,
					function(response) {
						if (response !== null) {
							//commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							//self.loadingScreen.removeLoading();
							callback({"status" : "service failure"});
						}
					},

					function(textStatus) {
						//commonVariables.loadingScreen.removeLoading();
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},
		
		runFunctionalTest : function(from) {
			var self = this;
			var testData;
			if (from === "startHub") {
				$("#startHub_popup").toggle();
				testData = $('#startHubForm').serialize();
			} else if (from === "startNode") {
				$("#startNode_popup").toggle();
				testData = $('#startNodeForm').serialize();
			} else if (from === "runFunctionalTest") {
				$("#functionalTest_popup").toggle();
				testData = $('#functionalTestForm').serialize();
			}
			var appdetails = self.functionalTestAPI.localVal.getJson('appdetails');
			var queryString = '';
			appId = appdetails.data.appInfos[0].id;
			projectId = appdetails.data.id;
			customerId = appdetails.data.customerIds[0];
			username = self.functionalTestAPI.localVal.getSession('username');
						
			if (appdetails !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=functional-test&phase=functional-test&projectId="+projectId+"&"+testData;
			}
			
			$('#testConsole').html('');
			self.testResultListener.openConsole();//To open the console
				
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					if (from === "startHub") {
						self.mavenServiceListener.mvnStartHub(queryString, '#testConsole');
					} else if (from === "startNode") {
						self.mavenServiceListener.mvnStartNode(queryString, '#testConsole');
					} else if (from === "runFunctionalTest") {
						self.mavenServiceListener.mvnFunctionalTest(queryString, '#testConsole');
					}
				});
			} else {
				if (from === "startHub") {
					self.mavenServiceListener.mvnStartHub(queryString, '#testConsole');
				} else if (from === "startNode") {
					self.mavenServiceListener.mvnStartNode(queryString, '#testConsole');
				} else if (from === "runFunctionalTest") {
					self.mavenServiceListener.mvnFunctionalTest(queryString, '#testConsole');
				}
			}			
		}
	});

	return Clazz.com.components.functionalTest.js.listener.FunctionalTestlistener;
});