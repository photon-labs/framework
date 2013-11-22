define(["lib/fileuploader-2.4"], function() {

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
	
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, header, data = {}, userId;
			data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			userId = data.id, appDirName = '';
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};

			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			if (!self.isBlank(moduleParam)) {
				appDirName = $('.rootModule').val()
			} else if(commonVariables.api.localVal.getProjectInfo() !== null) {
				var projectInfo = commonVariables.api.localVal.getProjectInfo();
				appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			}

			if (action === "addTestSuite") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testsuites?testSuiteName=' + requestBody.testSuiteName + '&appDirName=' + appDirName + moduleParam;
			} else if (action === "addTestcase") {
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testcases?testSuiteName=' + requestBody.testSuiteName + '&appDirName=' + appDirName + moduleParam;
			} else if (action === "validateTestCase") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + '/testcaseValidation?testSuiteName=' + requestBody.testSuiteName + '&appDirName=' + appDirName + '&testCaseId=' + requestBody.testCaseId + moduleParam;
			} else if (action === "downloadTemplate") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "manual/manualTemplate?fileType="+requestBody.format  + moduleParam;
			}
			return header;
		},
		
		getManualTestReport : function(header, callback) {
			var self = this;
			try {
				commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && (response.status !== "error" || response.status !== "failure")) {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							self.loadingScreen.removeLoading();
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
						commonVariables.loadingScreen.removeLoading();
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
				commonVariables.loadingScreen.removeLoading();
			}
		},
		
		addManualTestSuite : function(testSuiteVal) {
			var self = this, data = {};
			if(!self.validationTestSuite()) {
				data.testSuiteName = testSuiteVal;
				self.manualRequestBody = data;
				self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestSuite"), function(response) {
					commonVariables.navListener.onMytabEvent("manualTest");
				});
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
				self.manualRequestBody.testSuiteName = testsuiteName;
				
				self.getManualTestReport(self.getActionHeader(self.manualRequestBody, 'validateTestCase'), function (response) {
					//update operation
					if (!self.isBlank(response) && response.data) {	
						$("#show_manualTestCase_popup").toggle();
						self.getManualTestReport(self.getActionHeader(self.manualRequestBody, "addTestcase"), function(response) {
							commonVariables.navListener.getMyObj(commonVariables.testcaseResult, function(retVal) {
								self.testcaseResult = retVal;
								Clazz.navigationController.jQueryContainer = $(commonVariables.contentPlaceholder).find('#testResult');
								Clazz.navigationController.push(self.testcaseResult, false);
							});
						});
					} else {
						$("input[name='testCaseId']").focus();
						$("input[name='testCaseId']").val('');
						$("input[name='testCaseId']").attr('placeholder','TestCaseId Already Exists');
						$("input[name='testCaseId']").addClass("errormessage");
						$("input[name='testCaseId']").bind('keypress', function() {
							$(this).removeClass("errormessage");
						});
					}
				});
			}
		},
		
		createUploader : function() {  
			var self = this;
			var appDirName = '';
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
            if (!self.isBlank(moduleParam)) {
                appDirName = $('.rootModule').val()
            } else if(commonVariables.api.localVal.getProjectInfo() !== null) {
                var projectInfo = commonVariables.api.localVal.getProjectInfo();
                appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
            }

			var uploadFileUrl =commonVariables.webserviceurl + commonVariables.manual + '/uploadTemplate?appDirName=' + appDirName;
            var uploader = new qq.FileUploader({
                element: document.getElementById('manual_temp_upload'),
                action: uploadFileUrl,
				actionType : "manualTest",
				appDirName : appDirName,
				moduleName : $('.moduleName').val(),
				allowedExtensions : ['xls','xlsx','ods'],
				buttonLabel: "Upload",
				testListener : self.testResultListener,
				multiple: false,
				selfObj : self,
				debug: true
            });           
        },
        
        uploadCallBack : function () {
			commonVariables.navListener.onMytabEvent("manualTest");
			setTimeout(function() {
				$(".content_end").show();
				$(".msgdisplay").addClass("success").removeClass("error");
				$(".success").html('File Successfully uploaded');
				$(".success").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
			},2500);
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