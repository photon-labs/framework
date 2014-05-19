define([], function() {

	Clazz.createPackage("com.components.zapmenu.js.listener");

	Clazz.com.components.zapmenu.js.listener.zapmenuListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,	
		dynamicpage : null,
		dynamicPageListener : null,
		testResultListener : null,
		zapmenuListener : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		getRequestHeader : function(data , action) {
			var self=this, header, userId, appDirName;
			userId = commonVariables.api.localVal.getSession('username');
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			};
			if(action === 'parameter'){
				header.webserviceurl = commonVariables.webserviceurl+"parameter/dynamic?appDirName="+appDirName+"&goal=zap&phase=zap&customerId=photon&userId="+userId;
			}				
			
			if(action === "zap-status"){
				header.webserviceurl = commonVariables.webserviceurl+"quality/zapstatus?appDirName="+appDirName;
			}
			
			return header;
		},
		
				
		getData : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true, false, true);
						}
					},

					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
			}
		},

				
		getDynamicParams : function(type, thisObj, callback) {
			var self = this;
			if(type === 'test'){
				commonVariables.goal = commonVariables.zapGoal;
				commonVariables.phase = commonVariables.zapGoal;
				var popupObj = 'unit_popup';
				var dynamicControls = $('.dynamicControls')
			} else 	if(type === 'start'){
				commonVariables.goal = commonVariables.zapStart;
				commonVariables.phase = commonVariables.zapStart;
				var popupObj = 'zapstart_popup';
				var dynamicControls = $('.dynamicControls1');
			} 	
			commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(dynamicPageObject) {
				self.dynamicPageListener = new Clazz.com.components.dynamicPage.js.listener.DynamicPageListener();
				dynamicPageObject.getHtml( dynamicControls, thisObj, popupObj , function(response) {
					if ("No parameters available" === response) {
						self.runUnitTest(function(responseData) {
							callback(responseData);
						});
					}
				});
			});
		},
		
		
		runZapTest : function(callback) {
			var self = this;
			var testData = $("#unitTestForm").serialize();
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var queryString = '';
			appId = appdetails.data.projectInfo.appInfos[0].id;
			projectId = appdetails.data.projectInfo.id;
			customerId = appdetails.data.projectInfo.customerIds[0];
			username = commonVariables.api.localVal.getSession('username');
			appDirName = commonVariables.api.localVal.getSession("appDirName");

			if (appdetails !== null && userInfo !== null) {
				queryString = testData+"&appDirName="+appDirName+"&customerId="+customerId;
			}
			self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			$('.progress_loading').show();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnZapTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					commonVariables.consoleError = false;
					callback(response);
				});
			});
		},
		
		
		zapStartStop : function(type) {
			var self = this;
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var queryString = '';
			customerId = appdetails.data.projectInfo.customerIds[0];
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			var testData = "";

			if (appdetails !== null && userInfo !== null) {
				queryString = "customerId="+customerId+"&type="+type;
			}
			if(type === "zapStart"){
				testData = $("#zapStartForm").serialize();
				queryString += "&"+ testData; 
			}
			self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			$('.progress_loading').show();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnZapAction(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					commonVariables.consoleError = false;
					if(response !== null){
						$('.progress_loading').css('display','none');
						var zapmenuListener = new Clazz.com.components.zapmenu.js.listener.zapmenuListener();
						zapmenuListener.getZapStatus(zapmenuListener , "zap-status");
					}
				});
			});
		},
		
		getZapStatus : function (zapmenuListener , action){
			//var testadsf = new Clazz.com.components.zapmenu.js.listener.zapmenuListener();
			zapmenuListener.getData(zapmenuListener.getRequestHeader('', action), function(response) {
				if(response.responseCode === "PHRQ110005"){
					$("#zapStart").removeClass("btn_style");
					$("#zapStart").addClass("btn_style_off");
					$("#zapStop").removeClass("btn_style_off");
					$("#zapStop").addClass("btn_style");					
					$("#unitTestBtn").removeClass("btn_style_off");
					$("#unitTestBtn").addClass("btn_style");
				}else {
					$("#zapStart").removeClass("btn_style_off");
					$("#zapStart").addClass("btn_style");
					$("#zapStop").removeClass("btn_style");
					$("#zapStop").addClass("btn_style_off");					
					$("#unitTestBtn").removeClass("btn_style");
					$("#unitTestBtn").addClass("btn_style_off");
				}
			});		
		}
		
	});
	return Clazz.com.components.zapmenu.js.listener.zapmenuListener;
});