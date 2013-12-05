define([], function() {

	Clazz.createPackage("com.components.codequality.js.listener");

	Clazz.com.components.codequality.js.listener.CodequalityListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		basePlaceholder :  window.commonVariables.basePlaceholder,
		mavenServiceListener : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
				});
			}				
		},
		
		codeValidate : function(ipjson , callback) {
			var self = this;
			//var ipjson = $("#codeValidateForm").serialize();
			var appdetails = commonVariables.api.localVal.getProjectInfo();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var queryString = '';
			appId = appdetails.data.projectInfo.appInfos[0].id;
			projectId = appdetails.data.projectInfo.id;
			customerId = appdetails.data.projectInfo.customerIds[0];
			username = commonVariables.api.localVal.getSession('username');
						
			if(appdetails !== null && userInfo !== null){
				queryString ="username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+ipjson+'&displayName='+userInfo.displayName;
			}
			queryString += self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			$('#iframePart').html('');
			self.openConsole();//To open the console
			
						
			if(self.mavenServiceListener === null)	{
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
						/* var validateAgainst = $("#src").find(':selected').val();
						if(validateAgainst === undefined){
							validateAgainst = $("#sonar").find(':selected').val();
						} */
						
						var validateAgainst = $("#repTypes").attr("key");
						self.getIframeReport(validateAgainst);
						callback(returnVal);
					});
				});
			}else{
				self.mavenServiceListener.mvnCodeValidation(queryString, '#iframePart', function(returnVal){
					/* var validateAgainst = $("#src").find(':selected').val();
					if(validateAgainst === undefined){
						validateAgainst = $("#sonar").find(':selected').val();
					} */
					var validateAgainst = $("#repTypes").attr("key");
					self.getIframeReport(validateAgainst);
					callback(returnVal);
				});
			}			
			
		},


		onPrgoress : function(clicked) {
			var self = this;
			var check = $(clicked).attr('data-flag');
			if(check === "true") {
				self.openConsole();
				$(clicked).attr('data-flag','false');
			} else {
				self.closeConsole();
				$(clicked).attr('data-flag','true');
				$(window).resize();
			}
		},
		
		getRequestHeader : function(inputData, action) {
			var self=this, header, username, appId, customerId, projectId;
			var customerId = self.getCustomer();
			customerId = (customerId === "") ? "photon" : customerId;
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				webserviceurl: '',
				data: ''
			};
			/* if(action === "validate-code"){
				var appdetails = commonVariables.api.localVal.getJson('appdetails');
				appId = appdetails.data.appInfos[0].id;
				projectId = appdetails.data.id;
				username = commonVariables.api.localVal.getSession('username');
				
				header.requestMethod ="POST";
				header.webserviceurl = commonVariables.webserviceurl+"app/codeValidate?username="+username+"&appId="+appId+"&customerId="+customerId+"&goal=validate-code&phase=validate-code&projectId="+projectId+"&"+inputData;
			} */
			if(action === "readlog"){
				var uniquekey = inputData.uniquekey;
				header.webserviceurl = commonVariables.webserviceurl+"app/readlog?uniquekey="+uniquekey;
			}
			
			if(action === "reporttypes"){
				var appDirName = commonVariables.api.localVal.getSession('appDirName');
				header.webserviceurl = commonVariables.webserviceurl+"parameter/codeValidationReportTypes?appDirName="+appDirName+"&goal=validate-code"+moduleParam;
			}
			
			if(action === "sonarurl"){
				var appDirName = commonVariables.api.localVal.getSession('appDirName');
				header.webserviceurl = commonVariables.webserviceurl+"parameter/sonarUrl?appDirName="+appDirName+moduleParam;
			}
			
			if(action === "iframereport"){
				var appDirName = commonVariables.api.localVal.getSession('appDirName');
				username = commonVariables.api.localVal.getSession('username');
				header.webserviceurl = commonVariables.webserviceurl+"parameter/iFrameReport?appDirName="+appDirName+"&validateAgainst="+inputData+"&customerId="+customerId+"&userId="+username+moduleParam;
			}

			return header;
		},
		
		getSonarStatus : function(url , callback){
			var self = this;
			try {
				commonVariables.api.urlExists(url, 
				function(response){
					if (response === false) {
						var sonarExternalUrl = url;
						var pathArray = sonarExternalUrl.split( '/' );
						var protocol = pathArray[0];
						var host = pathArray[2];
						sonarExternalUrl = sonarExternalUrl.replace(protocol, window.location.protocol);
						sonarExternalUrl = sonarExternalUrl.replace(host, window.location.host);
						
						commonVariables.api.urlExists(sonarExternalUrl, function(response){
							callback(response);
						}, function(response){
							callback(response);
						});
					} else {
						callback(response);
					}
				},
				function(response){
					callback(response);
				});
			} catch(exception) {
				console.info('exception');
			}
		},
		
		getSonarUrl : function(url , callback){
			var self = this;
			try {
				commonVariables.api.urlExists(url, 
				function(response){
					if (response === false) {
						var sonarExternalUrl = url;
						var pathArray = sonarExternalUrl.split( '/' );
						var protocol = pathArray[0];
						var host = pathArray[2];
						sonarExternalUrl = sonarExternalUrl.replace(protocol, window.location.protocol);
						sonarExternalUrl = sonarExternalUrl.replace(host, window.location.host);
						
						commonVariables.api.urlExists(sonarExternalUrl, function(response){
							callback(sonarExternalUrl);
						}, function(response){
							callback(sonarExternalUrl);
						});
					} else {
						callback(url);
					}
				},
				function(response){
					callback(url);
				});
			} catch(exception) {
				console.info('exception');
			}
		},
		
		getReportTypes : function(header, callback) {
			var self = this;
			self.closeConsole();
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							commonVariables.loadingScreen.removeLoading();
							callback(response);
						} else {
							commonVariables.loadingScreen.removeLoading();
							if(response.responseCode === "PHR510001" ) {
								$(".alert").show();
								$('#content_div').html('<div class="alert" style="text-align: center; width:98%"></div>');
								$(".alert").attr('data-i18n', 'errorCodes.' + response.responseCode);
								$('#codeAnalysis').hide();
								$(".code_report").hide();
								$(".code_report_icon").hide();
								$("#codereportTypes").hide();	
								self.renderlocales(commonVariables.contentPlaceholder);
							}else if(response.responseCode === "PHR510002" ) {
								$(".alert").show();
								$('#content_div').html('<div class="alert" style="text-align: center; width:98%"></div>');
								$(".alert").attr('data-i18n', 'errorCodes.' + response.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);
							} else {
								commonVariables.api.showError(response.responseCode ,"error", true);
							}
						}
					},

					function(textStatus) {
						commonVariables.loadingScreen.removeLoading();
						commonVariables.api.showError("serviceerror" ,"error", true);
						$('#codeAnalysis').hide();
						$(".code_report").hide();
						$(".code_report_icon").hide();
						$("#codereportTypes").hide();						
					}
				);
			} catch(exception) {
				commonVariables.loadingScreen.removeLoading();
				commonVariables.api.showError("serviceerror" ,"error", true);
				$('#codeAnalysis').hide();
			}
		},

		constructHtml : function(response, output){
			var self = this;
				if(response[0].validateAgainst !== null){
					var typeLi = '';
					var validateAgainst = response[0].validateAgainst.key;
					var repTypesData = response[0].validateAgainst.value;
					$('#codeAnalysis').show();
					$(".code_report").show();
					$(".code_report_icon").show();
					$("#codereportTypes").show();
					$.each(response, function(index, resdata) {
						var innerUl = '';
						if(resdata.options === null){
							typeLi += "<li class='dropdown-key' name='selectType' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+" style='padding-left:8px;cursor:pointer;'>"+resdata.validateAgainst.value+"</li>";
						}else{
							validateAgainst = resdata.options[0].key;
							 repTypesData = resdata.options[0].value;
							$.each(resdata.options, function(index, optvalue) {
								innerUl += "<li class='dropdown-key' name='selectType' key="+optvalue.key+" data="+optvalue.value+" style='padding-left:8px;cursor:pointer;'>"+optvalue.value+"</li>";
							});
							typeLi += "<li disabled='disabled' key="+resdata.validateAgainst.key+" data="+resdata.validateAgainst.value+" style='padding-left:4px;'>"+resdata.validateAgainst.value+'<ul>'+innerUl+"</ul></li>";
						}
					});
					var dropdownLi = '<ul class="nav"><li id="fat-menu" class="dropdown"><a href="javascript:void(0)" id="drop5" role="button" class="dropdown-toggle" data-toggle="dropdown"><b id="repTypes" key='+validateAgainst+'>'+repTypesData+'</b><b class="caret"></b></a> <div class="dropdown-menu cust_sel code_test_opt" role="menu" aria-labelledby="drop5"> <ul id="reportUl">'+typeLi+'</ul></div></li></ul>';
					var codeReport = $("#codereportTypes").attr("class");
					if(codeReport !== undefined) {
						$("#codereportTypes").append(dropdownLi);
					} else {
						$(output).append(dropdownLi);
					}
					self.onProjects();
					self.getIframeReport(validateAgainst);
				}
		},
		
		onProjects : function() {
			var self = this, validateAgainst ;
			$("#reportUl li[name=selectType]").click(function() {
				$('.dyn_popup').hide();
				validateAgainst = $(this).attr('key');
				$("#repTypes").html($(this).attr('data'));
				$("#repTypes").attr("key",validateAgainst);
				self.getIframeReport(validateAgainst);
			});
		},
		
		getIframeReport : function(validateAgainst){
			var self = this;
			$(".alert").hide();
			try {
				commonVariables.api.ajaxRequest(self.getRequestHeader(validateAgainst , "iframereport"), 
					function(iframereport) {
						if(iframereport.data !== null){
							self.getSonarUrl(iframereport.data, function(sonarExternalUrl) {
								self.closeConsole();
								var iframedata = "<iframe id='iframe' class='iframe' src="+sonarExternalUrl+ " ></iframe>";
								$('#content_div').html(iframedata);
								var dynHeight = $('#content_div').height();
								$('#iframe').css('height', dynHeight);
							});
						} else {
							if(iframereport.responseCode === 'PHR500005') {
								$(".alert").show();
								$('#content_div').html('<div class="alert" style="text-align: center; width:98%"></div>');
								$(".alert").attr('data-i18n', 'successCodes.' + iframereport.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);
							} else if(iframereport.responseCode === "PHR510003" ) {
								$(".alert").show();
								$('#content_div').html('<div class="alert" style="text-align: center; width:98%"></div>');
								$(".alert").attr('data-i18n', 'errorCodes.' + iframereport.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);
							} else if(iframereport.responseCode === "PHR510002" ) {
								$(".alert").show();
								$('#content_div').html('<div class="alert" style="text-align: center; width:98%"></div>');
								$(".alert").attr('data-i18n', 'errorCodes.' + iframereport.responseCode);
								self.renderlocales(commonVariables.contentPlaceholder);
							} else {
								commonVariables.api.showError(iframereport.responseCode ,"error", true);
							}	
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception) {
				commonVariables.api.showError("serviceerror" ,"error", true);
			}	
		}
		
	});

	return Clazz.com.components.codequality.js.listener.CodequalityListener;
});