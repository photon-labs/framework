define(["lib/fileuploader-2.4"], function() {

	Clazz.createPackage("com.components.seo.js.listener");

	Clazz.com.components.seo.js.listener.seoListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
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
			userId = data.id;
			var appDirName = commonVariables.api.localVal.getSession("appDirName");

			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};

			if (action === "runseotest") {
				console.info('in' , requestBody);
				header.webserviceurl = commonVariables.webserviceurl + 'app/seoTest?customerId='+self.getCustomer() + '&appDirName=' + appDirName +'&'+requestBody;
			}
			return header;
		},
		
		getseoReport : function(header, callback) {
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
		
		runSeoTest : function(callback){
			var self = this;
			var testData =  $('#seoUploadForm').serialize();
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var queryString = '';

			customerId = appdetails.data.projectInfo.customerIds[0];
			appDirName = commonVariables.api.localVal.getSession("appDirName");
			
			queryString = testData+"&appDirName="+appDirName+"&customerId="+customerId;
			self.testResultListener = new Clazz.com.components.testResult.js.listener.TestResultListener();
			
			$('#testConsole').html('');
			self.testResultListener.openConsoleDiv();//To open the console
			$('.progress_loading').show();
			commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
				retVal.mvnSeoTest(queryString, '#testConsole', function(response) {
					self.testResultListener.closeConsole();
					commonVariables.consoleError = false;
					callback(response);
				});
			});
			
			/* 	self.getseoReport(self.getActionHeader(data, "runseotest"), function(response) {
				commonVariables.api.showError(response.responseCode ,"success", true, false, true);
				setTimeout(function() {
					commonVariables.navListener.onMytabEvent("manualTest");
				},1200);
				
			}); */
		},
				
		getDynamicParams : function(type, thisObj, callback) {
			var self = this;
			if(type === 'test'){
				commonVariables.goal = commonVariables.seoGoal;
				commonVariables.phase = commonVariables.seoGoal;
				var popupObj = 'template_upload';
				var dynamicControls = $('.dynamicControls')
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

			//var uploadFileUrl =commonVariables.webserviceurl + 'app/seoTest?appDirName=' + appDirName;
			var uploadFileUrl =commonVariables.webserviceurl + 'app/csvFileUpload?appDirName=' + appDirName;
			
            var uploader = new qq.FileUploader({
                element: document.getElementById('manual_temp_upload'),
                action: uploadFileUrl,
				actionType : "seo",
				appDirName : appDirName,
				moduleName : $('.moduleName').val(),
				allowedExtensions : ['xls','xlsx','csv'],
				buttonLabel: "Upload",
				//testListener : self.testResultListener,
				multiple: false,
				selfObj : self,
				debug: true
            });           
        },
        
        uploadCallBack : function () {
			commonVariables.navListener.onMytabEvent("seo");
			setTimeout(function() {
				$(".content_end").show();
				$(".msgdisplay").addClass("success").removeClass("error");
				$(".success").html('File Successfully uploaded');
				$(".success").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
			},2500);
        },
        
		
	});

	return Clazz.com.components.seo.js.listener.seoListener;
});