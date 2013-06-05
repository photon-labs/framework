define(["jquery", "login/login"], function($, Login) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { runTests: function (configData) {
		module("login.js;login");
		/* asyncTest("login Test", function() {
			$(document).ready(function(){
				var url = "bestbuy/j_spring_security_check"; // the script where you handle the form input.
				var data = {};
				data.j_username = "a1010742";
				data.password = "password";
				
				$.ajax({
					type: "POST",
					url: url,
					data: 'j_username=a1010742&j_password=password', // serializes the form's elements.
					success: function(sucessdata) {
						equal(sucessdata.indexOf('basepage:widget'), "2200", "Success case for Login");
						start();					
						
						//$('<basepage:widget config="src/components/login/test/config.json"></basepage:widget>').appendTo(document.body);
						//$('<script src="src/lib/fastclick.js" type="text/javascript"></script>').appendTo(document.head);
						$('<script src="src/lib/bootstrap.min.js" type="text/javascript"></script>').appendTo(document.head);
						
						//$('<script src="src/components/login/test/BootstrapTest.js" type="text/javascript"></script>').appendTo(document.head);
						
						require(["synonymsTest"], function(synonymsTest) {
							
							synonymsTest.runTests(configData);
							//keywordsTest.runTests(configData);						   
						});
					}
				});

			});
		}); */
		
		var login = new Login();
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
				//runOtherTests();
			}, 1500);
		});
		
		asyncTest("Login Service Test", function() {
			
			var header = {"contentType":"application/json", "requestMethod":"POST", "dataType":"json", "requestPostBody":"{}", "webserviceurl":"framework/login"};
			
			mockDoLogin = mockFunction();
			when(mockDoLogin)(anything()).then(function(arg) {
				var loginresponse = {"response":null,"message":"Login Success","exception":null,"data":{"email":null,"token":"1ibnf3ashmk0s14qv793nst71bo","authType":"LOCAL","customers":[{"options":["Home","Help","Settings","Download"],"frameworkTheme":null,"repoInfo":null,"emailId":null,"zipcode":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableAppTypes":null,"applicableTechnologies":null,"address":null,"context":null,"state":null,"type":"TYPE_BRONZE","country":null,"icon":null,"creationDate":1370260351850,"helpText":null,"system":false,"name":"Photon","id":"photon","displayName":null,"description":"photon","status":null}],"phrescoEnabled":true,"loginId":null,"firstName":null,"lastName":null,"roleIds":["4e8c0bd7-fb39-4aea-ae73-2d1286ae4ad0","4e8c0bd7-fb39-4aea-ae73-2d1286ae4ae0"],"validLogin":true,"permissions":{"manageApplication":true,"importApplication":true,"manageRepo":true,"updateRepo":false,"managePdfReports":true,"manageCodeValidation":true,"manageConfiguration":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false},"password":"462d1b8c3ec910626e1433647b1675e","creationDate":1357304255000,"helpText":null,"system":false,"name":"admin","id":"admin","displayName":"Admin","description":null,"status":null}};
				login.loginListener.setUserInfo(loginresponse.data);
				login.loginListener.appendPlaceholder();
				login.loginListener.renderNavigation();
			});
			
			//login.loginListener.loginAPI.doLogin(
			login.loginListener.loginAPI.doLogin = mockDoLogin;
			
			mockValidation = mockFunction();
			when(mockValidation)().thenReturn(true);
			login.loginListener.loginValidation = mockValidation;
			
            mockProjectList = mockFunction();
            when(mockProjectList)().thenReturn(true);
            /*when(mockProjectList)(anything()).then(function(arg) {
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
                
				var renderFunction = $.proxy(login.loginListener.navigationContent.navigationListener.projectlist.renderTemplate, login.loginListener.navigationContent.navigationListener.projectlist);
                renderFunction(projectListresponse, commonVariables.contentPlaceholder);
			});*/
            
            login.loginListener.navigationContent.navigationListener.renderContent = mockProjectList;
            
            login.loginListener.doLogin();

            
			setTimeout(function() {
				start();
				equal($(commonVariables.headerPlaceholder).find("font:first").text(), "Admin", "Login Service Tested");
				//runOtherTests();
			}, 1500);
		});
	return true;}};
	
});
