define(["navigation/api/navigationAPI"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.navigationListener = Clazz.extend(Clazz.Widget, {
		navAPI : null,
		localStorageAPI : null,
		loadingScreen : null,
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
		editproject : null,
		unittest : null,
		dynamicpage : null,
		editConfiguration : null,
		jobTemplates : null,
		continuousDeliveryView : null,
		mavenService : null,
		continuousDeliveryConfigure : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			
			if(self.navAPI == null)
				self.navAPI = new Clazz.com.components.navigation.js.api.navigationAPI();
		},
		
		onAddProject : function() {
			var self = this;
			self.getMyObj(commonVariables.addproject);
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			Clazz.navigationController.push(self.addproject, true);
		},
		
		landingPage : function(currentContent){
			var self = this;
			self.renderHeader(function(retVal){
				if(currentContent == undefined || currentContent == null){
					self.renderContent(function(retVal){
						self.renderFooter(function(retVal){});
					});
				} else if(currentContent != undefined && currentContent != null && currentContent != "") {
					self.dynamicContent(currentContent, function(retVal){
						self.renderFooter(function(retVal){});
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
						
					case commonVariables.unittest :
						
						if(self.unittest === null){
							require(["unittest/unittest"], function(){
								self.unittest = new Clazz.com.components.unittest.js.UnitTest();
								callback(self.unittest);	
							});
						}else{
							callback(self.unittest);
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
				}
			//return retuenObj;
		},

		//To show/hide controls based on the component 
		showHideControls : function(keyword) {
			var self = this;
			switch(keyword) {
				case commonVariables.projectlist :
					$("#projectList").show();
					$("#createProject").hide();
					self.applyRBAC(keyword);
					break;
					
				case commonVariables.editApplication :
					$("#applicationedit").show();
			}
		},
		
		//To apply the RBAC to the users
		applyRBAC : function(keyword) {
			var self = this;
			var userPermissions = JSON.parse(self.navAPI.localVal.getSession('userPermissions'));
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
		},
		
		//To show hide the options like build, deploy based on the applicable options for the technology
		showHideTechOptions : function() {
			var self = this;
			var applicableOptions = JSON.parse(self.navAPI.localVal.getSession('applicableOptions'));
			if (jQuery.inArray(commonVariables.optionsCode, applicableOptions) == -1) {
				$("#codequality").hide();
			}
			if (jQuery.inArray(commonVariables.optionsReports, applicableOptions) == -1) {
				$("#mavenReport").hide();
			}
			if (jQuery.inArray(commonVariables.optionsUnitTest, applicableOptions) == -1) {
				$("#unittest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsComponentTest, applicableOptions) == -1) {
				$("#componenttest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsFunctionalTest, applicableOptions) == -1) {
				$("#functionaltest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsPerformanceTest, applicableOptions) == -1) {
				$("#performancetest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsLoadTest, applicableOptions) == -1) {
				$("#loadtest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsManualTest, applicableOptions) == -1) {
				$("#manualtest").hide();
			}
			if (jQuery.inArray(commonVariables.optionsCI, applicableOptions) == -1) {
				$("#continuousDeliveryView").hide();
			}
			if (jQuery.inArray(commonVariables.optionsRunAgainstSrc, applicableOptions) == -1) {
				$("#runAgainstSrc").hide();
				$("#stop").hide();
				$("#restart").hide();
			}
			if (jQuery.inArray(commonVariables.optionsMinification, applicableOptions) == -1) {
				$("#minifier").hide();
			}
			if (jQuery.inArray(commonVariables.optionsBuild, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsDeploy, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsExeDownload, applicableOptions) == -1) { 

			}
			if (jQuery.inArray(commonVariables.optionsFeatureConfig, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsComponentConfig, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsProcessBuild, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsRemoteDeployment, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsEmbedApplication, applicableOptions) == -1) {

			}
			if (jQuery.inArray(commonVariables.optionsThemeBuilder, applicableOptions) == -1) {

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
				self.header.data = JSON.parse(self.navAPI.localVal.getSession('userInfo'));
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
				Clazz.navigationController.push(self.projectlist, true);
				callback(true);
			});
		},
		
		dynamicContent : function(contentObj, callback){
			var self = this;
			Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
			
			self.getMyObj(contentObj, function(returnVal){
				self.currentTab = contentObj;
				Clazz.navigationController.push(returnVal, true);
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
			var self=this, currentObj;
			if (self.currentTab !== commonVariables.editApplication && keyword === commonVariables.editApplication){
				currentObj = self.getMyObj(commonVariables.editApplication);
			} else if (self.currentTab !== commonVariables.featurelist && keyword === commonVariables.featurelist) {
				currentObj = self.getMyObj(commonVariables.featurelist);
			} else if (self.currentTab !== commonVariables.codequality && keyword === commonVariables.codequality) {
				currentObj = self.getMyObj(commonVariables.codequality);
			} else if (self.currentTab !== commonVariables.configuration && keyword === commonVariables.configuration) {
				currentObj = self.getMyObj(commonVariables.configuration);
			} else if (self.currentTab !== commonVariables.build && keyword === commonVariables.build) {
				currentObj = self.getMyObj(commonVariables.build);
			} else if (self.currentTab !== commonVariables.jobTemplates && keyword === commonVariables.jobTemplates) {
				currentObj = self.getMyObj(commonVariables.jobTemplates);
			} else if (self.currentTab !== commonVariables.continuousDeliveryView && keyword === commonVariables.continuousDeliveryView) {
				currentObj = self.getMyObj(commonVariables.continuousDeliveryView);
			} else if (self.currentTab !== commonVariables.continuousDeliveryConfigure && keyword === commonVariables.continuousDeliveryConfigure) {
				currentObj = self.getMyObj(commonVariables.continuousDeliveryConfigure);
			}

			if(currentObj != undefined && currentObj != null){
				self.currentTab = keyword;
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(currentObj, true);
			}
		},
		
		onQualitytab : function(keyword) {
			var self=this, currentObj;
			if (keyword === commonVariables.unittest){	
				currentObj = self.getMyObj(commonVariables.unittest);
			}
			
			if(currentObj != undefined && currentObj != null){
				self.currentTab = keyword;
				Clazz.navigationController.jQueryContainer = commonVariables.contentPlaceholder;
				Clazz.navigationController.push(currentObj, true);
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
			var appDirName = self.navAPI.localVal.getSession("appDirName");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
			if (action == "openFolder") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.openFolderContext + "?type=" + type + "&appDirName=" + appDirName;				
			} else if (action == "copyPath") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.copyPathContext + "?type=" + type + "&appDirName=" + appDirName;
			} else if(action == "importpost") {
				header.requestMethod = "POST";				
				header.requestPostBody = JSON.stringify(requestBody);		
				header.webserviceurl = commonVariables.webserviceurl + "repository/importApplication";				
			}
			return header;
		},
		
		navigationAction : function(header, callback) {
			var self = this;			
			try {
				self.navAPI.donavigation(header,
					function(response) {
						if (response != null ) {
							callback(response);						
						} else {
							callback({ "status" : "service failure"});
						}
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}
		},
		addImportEvent : function(revision){
			var self = this;
			var importdata = {}, actionBody, action;
			//if(!self.validation()) {
				importdata.type = $("#importType").val();
				importdata.repoUrl = $("#importRepourl").val();
				importdata.userName = $("#importUserName").val();
				importdata.password = $("#importPassword").val();
				importdata.revision = revision;
				console.info("importdata", importdata);
				actionBody = importdata;
				action = "importpost";
				self.navigationAction(self.getActionHeader(actionBody, action), function(response){
					console.info("response", response);
				});
			//}
		}	
	});

	return Clazz.com.components.navigation.js.listener.navigationListener;
});