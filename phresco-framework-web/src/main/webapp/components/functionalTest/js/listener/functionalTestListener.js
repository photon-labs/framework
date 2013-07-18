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
			} else if (action === "status") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/connectionAliveCheck?appDirName="+appDirName+"&fromPage="+requestBody.from;				
			}
			return header;
		},

		getDynamicParams : function(thisObj, whereToRender, popupId, goal) {
			var self = this;
			commonVariables.goal = goal;
			commonVariables.phase = goal;
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml(whereToRender, thisObj, popupId, function(response) {
					var height = $(".testSuiteTable").height();
					$(popupId).css("max-height", height - 40 + 'px');
					var formObj;
					if (goal === commonVariables.functionalTestGoal) {
						formObj = $('#functionalTestForm');
					} else if (goal === commonVariables.startHubGoal) {
						formObj = $('#startHubForm');
					} else if (goal === commonVariables.startNodeGoal) {
						formObj = $('#startNodeForm');
					}

					formObj.css("max-height", height - 92 + 'px');
					formObj.mCustomScrollbar({
						autoHideScrollbar:true,
						theme:"light-thin",
						advanced:{updateOnContentResize: true}
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

		performAction : function(from) {
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
			self.testResultListener.openConsoleDiv();//To open the console
			self.testResultListener.setConsoleScrollbar(true);//To apply for auto scroll for console window
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
					self.mavenServiceListener = retVal;
					if (from === "startHub") {
						self.mavenServiceListener.mvnStartHub(queryString, '#testConsole', function(response) {
							self.postStartHub();
							self.testResultListener.setConsoleScrollbar(false);
						});
					} else if (from === "startNode") {
						self.mavenServiceListener.mvnStartNode(queryString, '#testConsole', function(response) {
							self.postStartNode();
							self.testResultListener.setConsoleScrollbar(false);
						});
					} else if (from === "runFunctionalTest") {
						self.mavenServiceListener.mvnFunctionalTest(queryString, '#testConsole', function(response) {
							self.testResultListener.closeConsole();
							self.testResultListener.setConsoleScrollbar(false);
						});
					} else if (from === "stopHub") {
						self.mavenServiceListener.mvnStopHub(queryString, '#testConsole', function(response) {
							self.postStopHub(response);
							self.testResultListener.setConsoleScrollbar(false);
						});
					} else if (from === "stopNode") {
						self.mavenServiceListener.mvnStopNode(queryString, '#testConsole', function(response) {
							self.postStopNode();
							self.testResultListener.setConsoleScrollbar(false);
						});
					}
				});
			} else {
				if (from === "startHub") {
					self.mavenServiceListener.mvnStartHub(queryString, '#testConsole', function(response) {
						self.postStartHub();
						self.testResultListener.setConsoleScrollbar(false);
					});
				} else if (from === "startNode") {
					self.mavenServiceListener.mvnStartNode(queryString, '#testConsole', function(response) {
						self.postStartNode();
						self.testResultListener.setConsoleScrollbar(false);
					});
				} else if (from === "runFunctionalTest") {
					self.mavenServiceListener.mvnFunctionalTest(queryString, '#testConsole', function(response) {
						self.testResultListener.closeConsole();
						self.testResultListener.setConsoleScrollbar(false);
					});
				} else if (from === "stopHub") {
					self.mavenServiceListener.mvnStopHub(queryString, '#testConsole', function(response) {
						self.postStopHub();
						self.testResultListener.setConsoleScrollbar(false);
					});
				} else if (from === "stopNode") {
					self.mavenServiceListener.mvnStopNode(queryString, '#testConsole', function(response) {
						self.postStopNode();
						self.testResultListener.setConsoleScrollbar(false);
					});
				}
			}
		},

		postStartHub : function() {
			var self = this;
			self.closeConsole();
			var requestBody = {};
			requestBody.from = "hubStatus";
			self.getFunctionalTestOptions(self.getActionHeader(requestBody, "status"), function(response) {
				if (response) {
					$('#startHub').attr('value', 'Stop Hub').attr('id', "stopHub");
					$('#functionalTestBtn').attr('disabled', false);

					$("#stopHub").unbind("click");
					$("#stopHub").click(function() {
						self.performAction("stopHub");
					});
				}
			});
		},

		postStartNode : function() {
			var self = this;
			self.closeConsole();
			var requestBody = {};
			requestBody.from = "nodeStatus";
			self.getFunctionalTestOptions(self.getActionHeader(requestBody, "status"), function(response) {
				if (response) {
					$('#startNode').attr('value', 'Stop Node').attr('id', "stopNode");

					$("#stopNode").unbind("click");
					$("#stopNode").click(function() {
						self.performAction("stopNode");
					});
				}
			});
		},

		postStopHub : function() {
			var self = this;
			self.closeConsole();
			var requestBody = {};
			requestBody.from = "hubStatus";
			self.getFunctionalTestOptions(self.getActionHeader(requestBody, "status"), function(response) {
				if (!response) {
					$('#stopHub').attr('value', 'Start Hub').attr('id', "startHub");
					$('#functionalTestBtn').attr('disabled', true);	

					$("#startHub").unbind("click");
					$("#startHub").click(function() {
						self.getDynamicParams(this, $('#startHubDynCtrls'), 'startHub_popup', commonVariables.startHubGoal);
					});
				}
			});
		},

		postStopNode : function() {
			var self = this;
			self.closeConsole();
			var requestBody = {};
			requestBody.from = "nodeStatus";
			self.getFunctionalTestOptions(self.getActionHeader(requestBody, "status"), function(response) {
				if (!response) {
					$('#stopNode').attr('value', 'Start Node').attr('id', "startNode");

					$("#startNode").unbind("click");
					$("#startNode").click(function() {
						self.getDynamicParams(this, $('#startNodeDynCtrls'), 'startNode_popup', commonVariables.startNodeGoal);
					});
				}
			});
		},
	});

	return Clazz.com.components.functionalTest.js.listener.FunctionalTestlistener;
});