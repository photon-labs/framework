define([], function() {

	Clazz.createPackage("com.components.functionalTest.js.listener");

	Clazz.com.components.functionalTest.js.listener.FunctionalTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {

		testResultListener : null,
		dynamicpage : null,
		dynamicPageListener : null,
		
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
			var projectInfo = commonVariables.api.localVal.getProjectInfo();
			appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			header = {
					contentType: "application/json",				
					dataType: "json",
					webserviceurl: ''
			};

			if (action === "getFunctionalTestOptions") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/functionalFramework?appDirName="+appDirName;				
			}
			if (action === "status") {
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
					}
					if (goal === commonVariables.startHubGoal) {
						formObj = $('#startHubForm');
					}
					if (goal === commonVariables.startNodeGoal) {
						formObj = $('#startNodeForm');
					}
					
					formObj.css("max-height", height - 92 + 'px');
					if (formObj.find('li.ctrl').length > 5) {
						formObj.mCustomScrollbar({
							autoHideScrollbar:true,
							theme:"light-thin",
							advanced:{updateOnContentResize: true}
						});
					}
				});
			});
		},

		getFunctionalTestOptions : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
						function(response) {
					if (response !== null && (response.status !== "error" || response.status !== "failure")) {
						callback(response);
					} else {
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				},

				function(textStatus) {
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
					self.renderlocales(commonVariables.contentPlaceholder);		
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
				}
				);
			} catch(exception) {
			}
		},

		performAction : function(from, callback) {
			var self = this;
			var testData;
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
			var projectInfo = commonVariables.api.localVal.getProjectInfo();
			appId = projectInfo.data.projectInfo.appInfos[0].appDirName;
			appId = projectInfo.data.projectInfo.appInfos[0].id;
			projectId = projectInfo.data.projectInfo.id;
			customerId = projectInfo.data.projectInfo.customerIds[0];

			username = commonVariables.api.localVal.getSession('username');
			var queryString = '';
			if (projectInfo !== null) {
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=functional-test&phase=functional-test&projectId="+projectId;
			}

			if (testData !== undefined) {
				queryString = queryString+"&"+testData;
			}
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			$('.progress_loading').show();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal) {
				if (from === "startHub") {
					retVal.mvnStartHub(queryString, '#testConsole', function(response) {
						self.postStartHub();
					});
				} 
				if (from === "startNode") {
					retVal.mvnStartNode(queryString, '#testConsole', function(response) {
						self.postStartNode();
					});
				}
				if (from === "runFunctionalTest") {
					var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
					if (userInfo !== null) {
						queryString += "&displayName=" + userInfo.displayName;
					}
					retVal.mvnFunctionalTest(queryString, '#testConsole', function(response) {
						self.testResultListener.closeConsole();
						callback();
					});
				}
				if (from === "stopHub") {
					retVal.mvnStopHub(queryString, '#testConsole', function(response) {
						self.postStopHub(response);
					});
				}
				if (from === "stopNode") {
					retVal.mvnStopNode(queryString, '#testConsole', function(response) {
						self.postStopNode();
					});
				}
			});
		},

		postStartHub : function() {
			var self = this;
			self.closeConsole();
			$('.progress_loading').hide();
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
			$('.progress_loading').hide();
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
			$('.progress_loading').hide();
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
			$('.progress_loading').hide();
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
		}
	});

	return Clazz.com.components.functionalTest.js.listener.FunctionalTestlistener;
});