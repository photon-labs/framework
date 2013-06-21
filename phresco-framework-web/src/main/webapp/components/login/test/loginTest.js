define(["jquery", "login/login"], function($, Login) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests : function (configData, runOtherTests) {
			module("login.js;login");
			var login = new Login();
			
			asyncTest("Login Service Test", function() {
				mockDoLogin = mockFunction();
				when(mockDoLogin)(anything()).then(function(arg) {
					var loginresponse = {"response":null,"message":"Login Success","exception":null,"data":{"email":null,"token":"1ibnf3ashmk0s14qv793nst71bo","authType":"LOCAL","customers":[{"options":["Home","Help","Settings","Download"],"frameworkTheme":null,"repoInfo":null,"emailId":null,"zipcode":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableAppTypes":null,"applicableTechnologies":null,"address":null,"context":null,"state":null,"type":"TYPE_BRONZE","country":null,"icon":null,"creationDate":1370260351850,"helpText":null,"system":false,"name":"Photon","id":"photon","displayName":null,"description":"photon","status":null}],"phrescoEnabled":true,"loginId":null,"firstName":null,"lastName":null,"roleIds":["4e8c0bd7-fb39-4aea-ae73-2d1286ae4ad0","4e8c0bd7-fb39-4aea-ae73-2d1286ae4ae0"],"validLogin":true,"permissions":{"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false},"password":"462d1b8c3ec910626e1433647b1675e","creationDate":1357304255000,"helpText":null,"system":false,"name":"admin","id":"admin","displayName":"Admin","description":null,"status":null}};
					login.loginListener.setUserInfo(loginresponse.data);
					login.loginListener.appendPlaceholder();
					login.loginListener.renderNavigation();
				});
				
				login.loginListener.loginAPI.doLogin = mockDoLogin;
				
				mockValidation = mockFunction();
				when(mockValidation)().thenReturn(true);
				login.loginListener.loginValidation = mockValidation;
				
				mockProjectList = mockFunction();
				when(mockProjectList)(anything()).then(function(arg) {
					var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
					
					var projectlist = {};
					projectlist.projectlist = projectListresponse.data;
					login.loginListener.navigationContent.navigationListener.projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
				}); 
				require(["projectlist/projectList"], function(){
					login.loginListener.navigationContent.navigationListener.projectlist = new Clazz.com.components.projectlist.js.ProjectList();
				});
				login.loginListener.navigationContent.navigationListener.projectlist.projectslistListener.projectListAPI.projectslist = mockProjectList;
				
				login.loginListener.doLogin();
				setTimeout(function() {
					start();
					equal($(commonVariables.footerPlaceholder).find("#footer").attr('id'), "footer", "Footer Rendering Tested");
					equal($(commonVariables.headerPlaceholder).find("font:first").text(), "Admin", "Login Service Tested");
				}, 1500);
			});
			
			asyncTest("Login UI Test", function() {
				var output;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});

				Clazz.navigationController.jQueryContainer = $("<div id='loginTest'></div>");
				Clazz.navigationController.push(login, false);
				
				setTimeout(function() {
					login.loadPage();
					start();
					output = $(Clazz.navigationController.jQueryContainer).find("#loginContent").attr('id');
					equal("loginContent", output, "Login Rendered Successfully");
					runOtherTests();
				}, 1500);
			});
		}
	};
	
});
