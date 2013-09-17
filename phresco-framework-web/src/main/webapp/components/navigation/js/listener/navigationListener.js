define([], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		header : null,
		footer : null,
		projectlist : null,
		addproject : null,
		editApplication : null,
		featurelist : null,
		codequality : null,
		configuration : null, 
		build : null,
		currentTab : null,
		manualTest : null,
		editproject : null,
		settings : null,
		downloads : null,
		unitTest : null,
		functionalTest : null,
		componentTest : null,
		integrationTest : null,
		performanceTest : null,
		loadTest : null,
		testsuiteResult : null,
		testcaseResult : null,
		dynamicpage : null,
		editConfiguration : null,
		jobTemplates : null,
		continuousDeliveryView : null,
		mavenService : null,
		continuousDeliveryConfigure : null,
		act:null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onAddProject : function() {
			var self = this;
			self.getMyObj(commonVariables.addproject, function(addProjectObj){
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(addProjectObj, commonVariables.animation);
			});
		},
		
		landingPage : function(currentContent){
			var self = this;
			commonVariables.continueloading = true;
			self.renderHeader(function(retVal){
				if(currentContent === undefined || currentContent === null){
					self.renderFooter(function(retVal){
						self.renderContent(function(retVal){
							commonVariables.continueloading = false;
						});
					});
				} else if(currentContent !== undefined && currentContent !== null && currentContent !== "") {
					self.renderFooter(function(retVal){
						self.dynamicContent(currentContent, function(retVal){
							commonVariables.continueloading = false;
						});
					});
				}
			});
		},

		getMyObj : function(keyword, callback) {
			var self=this, retuenObj, objInfo = "";
				switch(keyword){
				
					case commonVariables.header :

						if(self.header === null){
							require(["header/header"], function(){
								self.header = new Clazz.com.commonComponents.modules.header.js.Header();
								callback(self.header);	
							});
						}else{
							callback(self.header);
						}
						
						break;
						
					case commonVariables.footer :
					
						if(self.footer === null){
							require(["footer/footer"], function(){
								self.footer = new Clazz.com.commonComponents.modules.footer.js.Footer();
								callback(self.footer);	
							});
						}else{
							callback(self.footer);
						}
					
						break;
						
					case commonVariables.projectlist :
						
						if(self.projectlist === null){
							require(["projectlist/projectList"], function(){
								self.projectlist = new Clazz.com.components.projectlist.js.ProjectList();
								callback(self.projectlist);	
							});
						}else{
							callback(self.projectlist);
						}

						break;
						
					case commonVariables.editApplication :
						
						if(self.editApplication === null){
							require(["application/application"], function(){
								self.editApplication = new Clazz.com.components.application.js.Application();
								callback(self.editApplication);	
							});
						}else{
							callback(self.editApplication);
						}

						break;
						
					case commonVariables.featurelist :
					
						if(self.featurelist === null){
							require(["features/features"], function(){
								self.featurelist = new Clazz.com.components.features.js.Features();
								callback(self.featurelist);	
							});
						}else{
							callback(self.featurelist);
						}

						break;
						
					case commonVariables.codequality :
						
						if(self.codequality === null){
							require(["codequality/codequality"], function(){
								self.codequality = new Clazz.com.components.codequality.js.CodeQuality();
								callback(self.codequality);	
							});
						}else{
							callback(self.codequality);
						}

						break;
						
					case commonVariables.configuration :
						
						if(self.configuration === null){
							require(["configuration/configuration"], function(){
								self.configuration = new Clazz.com.components.configuration.js.Configuration();
								callback(self.configuration);	
							});
						}else{
							callback(self.configuration);
						}

						break;
						
					case commonVariables.build :
						
						if(self.build === null){
							require(["build/build"], function(){
								self.build = new Clazz.com.components.build.js.Build();
								callback(self.build);	
							});
						}else{
							callback(self.build);
						}

						break;
						
					case commonVariables.addproject :
						
						if(self.addproject === null){
							require(["projects/addproject"], function(){
								self.addproject = new Clazz.com.components.projects.js.addProject();
								callback(self.addproject);	
							});
						}else{
							callback(self.addproject);
						}
						
						break;
					
					case commonVariables.editproject :
						
						if(self.editproject === null){
							require(["projects/editproject"], function(){
								self.editproject = new Clazz.com.components.projects.js.EditProject();
								callback(self.editproject);	
							});
						}else{
							callback(self.editproject);
						}
						
						break;
						
					case commonVariables.unitTest :
						
						if(self.unitTest === null){
							require(["unitTest/unitTest"], function() {
								self.unitTest = new Clazz.com.components.unitTest.js.UnitTest();
								callback(self.unitTest);	
							});
						}else{
							callback(self.unitTest);
						}
						
						break;
						
					case commonVariables.componentTest :
						
						if (self.componentTest === null) {
							require(["componentTest/componentTest"], function() {
								self.componentTest = new Clazz.com.components.componentTest.js.ComponentTest();
								callback(self.componentTest);	
							});
						}else{
							callback(self.componentTest);
						}
						
						break;
						
					case commonVariables.functionalTest :
						
						if(self.functionalTest === null){
							require(["functionalTest/functionalTest"], function() {
								self.functionalTest = new Clazz.com.components.functionalTest.js.FunctionalTest();
								callback(self.functionalTest);	
							});
						}else{
							callback(self.functionalTest);
						}
						
						break;

					case commonVariables.integrationTest :
						if (self.integrationTest === null) {
							require(["integrationTest/integrationTest"], function() {
								self.integrationTest = new Clazz.com.components.integrationTest.js.IntegrationTest();
								callback(self.integrationTest);	
							});
						}else{
							callback(self.integrationTest);
						}
						
						break;
						
					case commonVariables.testsuiteResult :
						if (self.testsuiteResult === null) {
							require(["testResult/testsuite"], function() {
								self.testsuiteResult = new Clazz.com.components.testResult.js.Testsuite();
								callback(self.testsuiteResult);	
							});
						} else {
							callback(self.testsuiteResult);
						}
						
						break;
						
					case commonVariables.testcaseResult :
						
						if (self.testcaseResult === null) {
							require(["testResult/testcase"], function() {
								self.testcaseResult = new Clazz.com.components.testResult.js.Testcase();
								callback(self.testcaseResult);	
							});
						} else {
							callback(self.testcaseResult);
						}
						
						break;
						
					case commonVariables.performanceTest : 
						if (self.performanceTest === null) {
							require(["performanceTest/performanceTest"], function() {
								self.performanceTest = new Clazz.com.components.performanceTest.js.PerformanceTest();
								callback(self.performanceTest);	
							});
						} else {
							callback(self.performanceTest);	
						}
						break;
					
					case commonVariables.loadTest : 
						if (self.loadTest === null) {
							require(["loadTest/loadTest"], function() {
								self.loadTest = new Clazz.com.components.loadTest.js.LoadTest();
								callback(self.loadTest);	
							});
						} else {
							callback(self.loadTest);	
						}
						break;

					case commonVariables.editConfiguration :
						
						if(self.editConfiguration === null){
							require(["configuration/editConfiguration"], function(){
								self.editConfiguration = new Clazz.com.components.configuration.js.EditConfiguration();
								callback(self.editConfiguration);	
							});
						}else{
							callback(self.editConfiguration);
						}
						
						break;

					case commonVariables.dynamicPage :
						
						if(self.dynamicpage === null){
							require(["dynamicPage/dynamicPage"], function(){
								self.dynamicpage = new Clazz.com.components.dynamicPage.js.DynamicPage();
								callback(self.dynamicpage);	
							});
						}else{
							callback(self.dynamicpage);
						}
						
						break;

					case commonVariables.jobTemplates :
						
						if(self.jobTemplates === null){
							require(["ci/jobTemplates"], function(){
								self.jobTemplates = new Clazz.com.components.ci.js.JobTemplates();
								callback(self.jobTemplates);	
							});
						}else{
							callback(self.jobTemplates);
						}
						
						break;

					case commonVariables.continuousDeliveryConfigure :
						
						if(self.continuousDeliveryConfigure === null){
							require(["ci/continuousDeliveryConfigure"], function(){
								self.continuousDeliveryConfigure = new Clazz.com.components.ci.js.ContinuousDeliveryConfigure();
								callback(self.continuousDeliveryConfigure);	
							});
						}else{
							callback(self.continuousDeliveryConfigure);
						}
						
						break;

					case commonVariables.continuousDeliveryView :
						
						if(self.continuousDeliveryView === null){
							require(["ci/continuousDeliveryView"], function(){
								self.continuousDeliveryView = new Clazz.com.components.ci.js.ContinuousDeliveryView();
								callback(self.continuousDeliveryView);	
							});
						}else{
							callback(self.continuousDeliveryView);
						}
						
						break;
						
					case commonVariables.mavenService :
					
						if(self.mavenService === null){
							require(["mavenService/listener/mavenServiceListener"], function(){
								self.mavenService = new Clazz.com.components.mavenService.js.listener.MavenServiceListener();
								callback(self.mavenService);	
							});
						}else{
							callback(self.mavenService);
						}
					
						break;
						
					case commonVariables.manualTest :
						
						if (self.manualTest === null) {
							require(["manualTest/manualTest"], function() {
								self.manualTest = new Clazz.com.components.manualTest.js.ManualTest();
								callback(self.manualTest);	
							});
						}else{
							callback(self.manualTest);
						}
						
						break;
						
					case commonVariables.settings :
						
						if (self.settings === null) {
							require(["settings/settings"], function() {
								self.settings = new Clazz.com.components.settings.js.Settings();
								callback(self.settings);	
							});
						}else{
							callback(self.settings);
						}
						
						break;

					case commonVariables.downloads :
						if (self.downloads === null) {
							require(["downloads/downloads"], function() {
								self.downloads = new Clazz.com.components.downloads.js.Downloads();
								callback(self.downloads);	
							});
						}else{
							callback(self.downloads);
						}
						
						break;
				}
		},

		//To show/hide controls based on the component 
		showHideControls : function(keyword) {
			var self = this;
			switch(keyword) {
				case commonVariables.projectlist :
					$("#projectList").show();
					$("#createProject").hide();
					$("#settingsNav").hide();
					$("#downloadsNav").hide();
					self.applyRBAC(keyword);
					break;
					
				case commonVariables.editApplication :
					$("#applicationedit").show();
					$("#settingsNav").hide();
					$("#downloadsNav").hide();
					break;
					
				case commonVariables.settings :
					$("#settingsNav").show();
					$("#downloadsNav").hide();
					$("#projectList").hide();
					$("#createProject").hide();
					$("#applicationedit").hide();
					$('#editprojectTab').hide();

				case commonVariables.downloads :
					$("#downloadsNav").show();
					$("#settingsNav").hide();
					$("#projectList").hide();
					$("#createProject").hide();
					$("#applicationedit").hide();
					$('#editprojectTab').hide();
					self.applyRBAC(keyword);
					break;
				

			}
		},
		
		//To apply the RBAC to the users
		applyRBAC : function(keyword) {
			var self = this;
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			if(userPermissions){
				switch(keyword) {
					case commonVariables.projectlist :
						if (!userPermissions.importApplication) {
							$("#importApp").prop("disabled", true);
						}
						if (!userPermissions.manageApplication) {
							$("#addproject").prop("disabled", true);
						}
						break;
						
					case commonVariables.configuration :
						if (!userPermissions.manageConfiguration) {
							$("input[name=env_pop]").prop("disabled", true);
						}
						break;
				}
			}
		},
		
		//To show hide the options like build, deploy based on the applicable options for the technology
		showHideTechOptions : function() {
			var self = this;
			var applicableOptions = JSON.parse(commonVariables.api.localVal.getSession('applicableOptions'));
			if (jQuery.inArray(commonVariables.optionsCode, applicableOptions) === -1) {
				$("#codequality").hide();
			} else {
				$("#codequality").show();
			}
			if (jQuery.inArray(commonVariables.optionsReports, applicableOptions) === -1) {
				$("#mavenReport").hide();
			} else {
				$("#mavenReport").show();
			}
			if (jQuery.inArray(commonVariables.optionsUnitTest, applicableOptions) === -1) {
				$("#unitTest").hide();
			} else {
				$("#unitTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsComponentTest, applicableOptions) === -1) {
				$("#componentTest").hide();
			} else {
				$("#componentTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsFunctionalTest, applicableOptions) === -1) {
				$("#functionalTest").hide();
			} else {
				$("#functionalTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsPerformanceTest, applicableOptions) === -1) {
				$("#performanceTest").hide();
			} else {
				$("#performanceTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsLoadTest, applicableOptions) === -1) {
				$("#loadTest").hide();
			} else {
				$("#loadTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsManualTest, applicableOptions) === -1) {
				$("#manualTest").hide();
			} else {
				$("#manualTest").show();
			}
			if (jQuery.inArray(commonVariables.optionsCI, applicableOptions) === -1) {
				$("#continuousDeliveryView").hide();
			} else {
				$("#continuousDeliveryView").show();
			}
			if (jQuery.inArray(commonVariables.optionsRunAgainstSrc, applicableOptions) === -1) {
				$("input[name=build_runagsource]").hide();
				$("#stop").hide();
				$("#restart").hide();
			} else {
				$("input[name=build_runagsource]").show();
				$("#stop").show();
				$("#restart").show();
			}
			if (jQuery.inArray(commonVariables.optionsMinification, applicableOptions) === -1) {
				$("#minifier").hide();
			} else {
				$("#minifier").show();
			}
			if (jQuery.inArray(commonVariables.optionsBuild, applicableOptions) === -1) {
				$('input[name=build_genbuild]').hide();
			} else {
				$('input[name=build_genbuild]').show();
			}
			if (jQuery.inArray(commonVariables.optionsDeploy, applicableOptions) === -1) {
				$("table th[name=buildDep]").hide();
				$("table td[name=buildDep]").hide();
			} else {
				$("table th[name=buildDep]").show();
				$("table td[name=buildDep]").show();
			}
			if (jQuery.inArray(commonVariables.optionsExeDownload, applicableOptions) === -1) { 
				$('img[name=ipaDownload]').hide();
			} else {
				$('img[name=ipaDownload]').show();
			}
			if (jQuery.inArray(commonVariables.optionsFeatureConfig, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsComponentConfig, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsProcessBuild, applicableOptions) === -1) {
				//process build
				$("table th[name=prcBuild]").hide();
				$("table td[name=prcBuild]").hide();
			} else {
				$("table th[name=prcBuild]").show();
				$("table td[name=prcBuild]").show();

			}
			if (jQuery.inArray(commonVariables.optionsRemoteDeployment, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsEmbedApplication, applicableOptions) === -1) {

			} else {
				
			}
			if (jQuery.inArray(commonVariables.optionsThemeBuilder, applicableOptions) === -1) {

			} else {
				
			}
		},
		
		//Handles the open folder action
		openFolder : function(actionBody) {
			var self = this;
			self.navigationAction(self.getActionHeader(actionBody, "openFolder"), function(response) {});
		},
		
		//Handles the copy path action
		copyPath : function(actionBody) {
			var self = this;
			self.navigationAction(self.getActionHeader(actionBody, "copyPath"), function(response) {});
		},
		
		renderHeader : function(callback) {
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.headerPlaceholder;
			self.getMyObj(commonVariables.header, function(returnVal){
				self.header.data = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				Clazz.navigationController.push(self.header, false);
				callback(true);
			});
		},
		
		renderContent : function(callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			self.header.headerListener.currentTab = "Projects";
			self.getMyObj(commonVariables.projectlist, function(returnVal){
				self.currentTab = commonVariables.projectlist;
				Clazz.navigationController.push(self.projectlist, commonVariables.animation);
				callback(true);
			});
		},
		
		dynamicContent : function(contentObj, callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			self.getMyObj(contentObj, function(returnVal){
				self.currentTab = contentObj;
				Clazz.navigationController.push(returnVal, commonVariables.animation);
				callback(true);
			});
		},
		
		renderFooter : function(callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.footerPlaceholder;
			self.getMyObj(commonVariables.footer, function(returnVal){
				Clazz.navigationController.push(self.footer, false);
				callback(true);
			});
		},
		
		onMytabEvent : function(keyword) {
			var self = this, currentObj;
			if (self.currentTab !== commonVariables.editApplication && keyword === commonVariables.editApplication){
				self.getMyObj(commonVariables.editApplication, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.featurelist && keyword === commonVariables.featurelist) {
				self.getMyObj(commonVariables.featurelist, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.codequality && keyword === commonVariables.codequality) {
				self.getMyObj(commonVariables.codequality, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				}); 
			} else if (self.currentTab !== commonVariables.configuration && keyword === commonVariables.configuration) {
				self.getMyObj(commonVariables.configuration, function(returnVal){
					currentObj = returnVal;
					currentObj.envSpecific = true;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.build && keyword === commonVariables.build) {
				self.getMyObj(commonVariables.build, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.jobTemplates && keyword === commonVariables.jobTemplates) {
				self.getMyObj(commonVariables.jobTemplates, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.continuousDeliveryView && keyword === commonVariables.continuousDeliveryView) {
				self.getMyObj(commonVariables.continuousDeliveryView, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (self.currentTab !== commonVariables.continuousDeliveryConfigure && keyword === commonVariables.continuousDeliveryConfigure) {
				self.getMyObj(commonVariables.continuousDeliveryConfigure, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.unitTest) {
				self.getMyObj(commonVariables.unitTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.functionalTest) {
				self.getMyObj(commonVariables.functionalTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.componentTest) {
				self.getMyObj(commonVariables.componentTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.performanceTest) {
				self.getMyObj(commonVariables.performanceTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (keyword === commonVariables.loadTest) {
				self.getMyObj(commonVariables.loadTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.integrationTest) {
				self.getMyObj(commonVariables.integrationTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			} else if (keyword === commonVariables.manualTest) {
				self.getMyObj(commonVariables.manualTest, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}  else if (keyword === commonVariables.editproject) {
				self.getMyObj(commonVariables.editproject, function(returnVal){
					currentObj = returnVal;
					self.myTabRenderFunction(currentObj, keyword);
				});
			}

		},
		
		myTabRenderFunction : function(currentObj, keyword) {
			var self = this;
			if(currentObj !== undefined && currentObj !== null){
				self.currentTab = keyword;
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(currentObj, commonVariables.animation);
			}
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self=this, header, data = {}, userId;
			var type = requestBody.type;
			var appDirName = commonVariables.api.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			self.act=action;
			if (action === "openFolder") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.openFolderContext + "?type=" + type + "&appDirName=" + appDirName;				
			} else if (action === "copyPath") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyPathContext + "?type=" + type + "&appDirName=" + appDirName;
			} else if(action === "importpost") {
				var displayName="", userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
				if (userInfo !== null) {
					displayName = userInfo.displayName;
				}
				header.requestMethod = "POST";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + "repository/importApplication?displayName="+displayName;
			} else if (action === "copyToClipboard") {
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyToClipboardContext;
				header.requestPostBody = requestBody.log;
			}
			return header;
		},
		
		navigationAction : function(header, callback) {
			var self = this;			
			try {
				//commonVariables.loadingScreen.showLoading();
				commonVariables.api.ajaxRequest(header,
					function(response) {
						//commonVariables.loadingScreen.removeLoading();
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							if(response.responseCode === "PHR200017") {
								commonVariables.api.showError(response.responseCode ,"success", true);
							}
							callback(response);						
						} else {
							commonVariables.api.showError(response.responseCode ,"error", true);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);		
					}
				);
			} catch(exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		}, 
		
		validateImport : function (callback) {
			var self = this;
			var importType = $("#importType").val();
			var importRepourl = $("#importRepourl").val().replace(/\s/g, '');
			$("#importRepourl").removeClass("errormessage");
			var error = false;
			
			if(!self.isValidUrl(importRepourl)) {
				error = true;
				$("#importRepourl").val('');
				self.validateTextBox($("#importRepourl"), 'Invalid Application Url');	
				setTimeout(function() { 
				//$("#importRepourl").val(importRepourl); 
				}, 4000);				
			}
			
			if ('svn' === importType && !error) {
				$("#importUserName").removeClass("errormessage");
				$("#importPassword").removeClass("errormessage");
				$("#revision").removeClass("errormessage");
				var userName = $("#importUserName").val().replace(/\s/g, '');
				var pswd = $("#importPassword").val();
				var revision = $("input[name='headoption']:checked").val();
				if (userName === "") {
					error = true;
					self.validateTextBox($("#importUserName"), 'Enter user name');
				} else if (pswd === "") {
					error = true;
					self.validateTextBox($("#importPassword"), 'Enter password');
				} else if (revision === "revision" && $('#revision').val() === "") {
					error = true;
					self.validateTextBox($("#revision"), 'Enter revision');
				} else if ($(".testCheckout").is(":checked")) {
					$("#testRepoUrl").removeClass("errormessage");
					$("#testImportUserName").removeClass("errormessage");
					$("#testImportPassword").removeClass("errormessage");
					
					var testRepoUrl = $("#testRepoUrl").val().replace(/\s/g, '');
					var testImportUserName = $("#testImportUserName").val().replace(/\s/g, '');
					var testImportPassword = $("#testImportPassword").val();
					var testRevision = $("input[name='testHeadOption']:checked").val();
					if(!self.isValidUrl(testRepoUrl)) {
						error = true;
						$("#testRepoUrl").val('');
						self.validateTextBox($("#testRepoUrl"), 'Invalid Test Repourl');
						setTimeout(function() { 
							$("#testRepoUrl").val(testRepoUrl); 
						}, 4000);
					} else if (testImportUserName === "") {
						error = true;
						self.validateTextBox($("#testImportUserName"), 'Enter user name');
					} else if (testImportPassword === "") {
						error = true;
						self.validateTextBox($("#testImportPassword"), 'Enter password');
					} else if (testRevision === "revision" && $('#testRevision').val() === "") {
						error = true;
						self.validateTextBox($("#testRevision"), 'Enter revision');
					} 
				}
			} else if ('bitkeeper' === importType) {
				$("#gitUserName").removeClass("errormessage");
				$("#gitPassword").removeClass("errormessage");
				var bituserName = $("#gitUserName").val().replace(/\s/g, '');
				var bitpwd = $("#gitPassword").val();
				if (bituserName === "") {
					error = true;
					self.validateTextBox($("#gitUserName"), 'Enter user name');
				} else if (bitpwd === "") {
					error = true;
					self.validateTextBox($("#gitPassword"), 'Enter password');
				}
			} else if ('git' === importType) {
				$("#gitUserName").removeClass("errormessage");
				$("#gitPassword").removeClass("errormessage");
			}
			callback(error);
		},
		
		validateTextBox : function (textBoxObj, errormsg) {
			if(textBoxObj !== "" && errormsg !== "") {
				textBoxObj.focus();
				textBoxObj.attr('placeholder', errormsg);
				textBoxObj.addClass("errormessage");
			} else {
				textBoxObj.attr('placeholder', errormsg);
				textBoxObj.removeClass("errormessage");
			}
		}, 
		
		addImportEvent : function(){
			var self = this;
			var importdata = {}, actionBody, action;
			
			var revision = $("input[name='headoption']:checked").val();
			if(revision !== ""){
				revision = revision;
			} else{
				revision = $("#revision").val();
			}
			
			var testRevision = $("input[name='testHeadOption']:checked").val();
			if(testRevision !== ""){
				testRevision = testRevision;
			} else{
				testRevision = $("#testRevision").val();
			}
			
			importdata.type = $("#importType").val();
			importdata.repoUrl = $("#importRepourl").val();
			
			if('git' === importdata.type) {
				importdata.branch = $(".branchval").val();
				importdata.userName = $("#gitUserName").val();
				importdata.password = $("#gitPassword").val();
			} else {
				importdata.userName = $("#importUserName").val();
				importdata.password = $("#importPassword").val();
				importdata.revision = revision;
			}
			
			if ('svn' === importdata.type && $(".testCheckout").is(":checked")) {
				importdata.testCheckOut = true;
				importdata.testRepoUrl = $("#testRepoUrl").val();
				importdata.testUserName = $("#testImportUserName").val();
				importdata.testPassword = $("#testImportPassword").val();
				importdata.testRevision = testRevision;
			} else {
				importdata.testCheckOut = false;
			}
			
			actionBody = importdata;
			action = "importpost";
			self.navigationAction(self.getActionHeader(actionBody, action), function(response){
				if (response.exception === null) {
					$("#project_list_import").hide();	
					self.getMyObj(commonVariables.projectlist, function(returnVal){
						self.projectlist = returnVal;
						Clazz.navigationController.push(self.projectlist, commonVariables.animation);
					});
				}
			});
		},
		
		//To copy the console log content to the clip-board
		copyToClipboard : function(consoleObj) {
			var self = this;
			var logContent = consoleObj.text();
			var data = {};
			data.log = escape(logContent);
			self.navigationAction(self.getActionHeader(data, "copyToClipboard"), function(response) {});
		},
		
		configDropdown : function(val) {
			var self=this, favConfigList="", favConfig, flag = false;
			$("#configuration").html('');
			$.each(val, function(index, value){
				if (value.favourite === true || value.envSpecific === false) {
					flag = true;
					favConfigList += '<li name="configuration" configType="'+value.templateName+'" favourite='+value.favourite+' envSpecific='+value.envSpecific+'><a href="javascript:void(0)">'+value.templateName+'</a></li>';
				}
			});
			if (flag === true) {
				favConfig = '<a href="javascript:void(0)" id="drop4Config" role="quality" class="dropdown-toggle drop-qual" data-toggle="dropdown">Configuration<b class="caret"></b></a><div class="dropdown-menu cust_sel test_options" role="quality" aria-labelledby="drop4"><ul name="configurationList">'+favConfigList+'</ul></div>';
			} else {
				favConfig = '<a href="javascript:void(0)" id="drop4Config" role="quality" class="dropdown-toggle drop-qual" data-toggle="dropdown">Configuration</b></a>';
			}
			$("#configuration").append(favConfig);
			self.clickEvent();
		},
		
		clickEvent : function() {
			var self=this, envSpec, configType;
			$("ul[name=configurationList] li").unbind("click");
			$("ul[name=configurationList] li").click(function() {
				$("#myTab li a").removeClass("act");
				$("#configuration a#drop4Config").addClass("act");
				envSpec = $(this).attr('envSpecific');
				configType = $(this).attr('configType');
				envSpec = (envSpec === "true") ? true : false;
				commonVariables.subtabClicked = true;
				commonVariables.navListener.currentTab = '';
				self.getMyObj(commonVariables.configuration, function(returnVal){
					self.nonEnvConfigurations = returnVal;
					self.nonEnvConfigurations.envSpecific = envSpec;
					self.nonEnvConfigurations.configType = configType;
					Clazz.navigationController.push(self.nonEnvConfigurations, true);
				});
			});
		}
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});