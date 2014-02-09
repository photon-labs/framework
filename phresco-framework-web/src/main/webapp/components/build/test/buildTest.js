define(["build/build"], function(Build) {

	return { 
		setuserInfo : function(){
			commonVariables.api.localVal.setSession("username","kavinraj_m");
			commonVariables.api.localVal.setSession('appDirName', 'HTML');
			commonVariables.appDirName = "HTML";
			
			commonVariables.api.localVal.setSession('userInfo','{"token":"237jt3kkj9klf1amat0t1d74ph0","email":"kavinraj.mani@photoninfotech.net","authType":"LOCAL","customers":[{"frameworkTheme":null,"options":["Home","Help","Settings","Download"],"applicableAppTypes":null,"repoInfo":null,"emailId":null,"zipcode":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"photon","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"creationDate":1378991832974,"helpText":null,"system":false,"name":"Photon","id":"photon","displayName":null,"description":"photon","status":null}],"phrescoEnabled":true,"validLogin":true,"firstName":"","lastName":"","loginId":"","roleIds":["4e8c0bed7-fb39-4erta-ae73-2d1286ae4ad0","4e8c0bd7-fb39-4aea-ae73-2d1286ae4ae0"],"permissions":{"manageCodeValidation":true,"managePdfReports":true,"manageConfiguration":true,"manageRepo":true,"updateRepo":false,"importApplication":true,"manageApplication":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false},"password":"b28a72debd125abe8ab48eb7f6e90df","creationDate":1376017521287,"helpText":"","system":false,"name":"kavinraj_m","id":"kavinraj_m","displayName":"Kavinraj Mani","description":"","status":null}');
		},
		
		setiPhoneAppInfo : function(){
			commonVariables.api.localVal.setSession('fe5478f9-7636-42d0-9093-e0878dd83dfd','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList": {}, "projectInfo":{"endDate":null,"multiModule":false,"startDate":null,"noOfApps":1,"preBuilt":false,"appInfos":[{"modules":null,"pomFile":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[],"selectedDatabases":[],"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"selectedWebservices":[],"appDirName":"NativeApp","techInfo":{"appTypeId":"mobile-layer","techGroupId":"Iphone","techVersions":null,"version":"","used":false,"customerIds":null,"helpText":null,"creationDate":1374732729000,"system":false,"name":"Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},"version":"1.0","code":"NativeApp","used":false,"customerIds":null,"helpText":null,"creationDate":1374733413000,"system":false,"name":"NativeApp","id":"00195d40-60fb-433c-ac1a-2b57251a17da","displayName":null,"description":"","status":null}],"projectCode":"iPohneTechNativeApp","version":"1.0","used":false,"customerIds":["photon"],"helpText":null,"creationDate":1374732729000,"system":false,"name":"iPohneTechNativeApp","id":"fe5478f9-7636-42d0-9093-e0878dd83dfd","displayName":null,"description":"","status":null}},"status":"success"}');
			$('.hProjectId').val('fe5478f9-7636-42d0-9093-e0878dd83dfd');
		},
		
		setHTMLAppInfo : function(){
			commonVariables.api.localVal.setSession('d6528c06-b2f6-4388-8001-54e7e25d59db','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList": {}, "projectInfo":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"HTML","techInfo":{"appTypeId":"web-layer","techVersions":null,"techGroupId":"HTML5","version":"2.0.2","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1373294442000,"name":"JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"selectedWebservices":[],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"helpText":null,"system":false,"creationDate":1373535598000,"name":null,"id":"4386478c-b603-4adf-b5fe-df669b626248","displayName":null,"description":null,"status":null}],"selectedDatabases":[],"functionalFrameworkInfo":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"pilotInfo":null,"selectedFrameworks":null,"version":"1.0","code":"HTML","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1373535598000,"name":"HTML","id":"137223b6-8454-4041-b52d-07c110ab57fb","displayName":null,"description":"","status":null}],"projectCode":"Java App","preBuilt":false,"endDate":1373481000000,"startDate":1373221800000,"multiModule":false,"noOfApps":2,"version":"1.0","customerIds":["photon"],"used":false,"helpText":null,"system":false,"creationDate":1373294442000,"name":"Java App","id":"d6528c06-b2f6-4388-8001-54e7e25d59db","displayName":null,"description":"testsing","status":null}},"status":"success"}');
			$('.hProjectId').val('d6528c06-b2f6-4388-8001-54e7e25d59db');
		},
		
		setNodeAppInfo : function(){
			commonVariables.api.localVal.setSession('3b33c6c3-2491-4870-b0a9-693817b5b9f8','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList": {}, "projectInfo":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"node","techInfo":{"appTypeId":"app-layer","techVersions":null,"techGroupId":null,"version":"0.10.9","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1374748085000,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"selectedWebservices":[],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"helpText":null,"system":false,"creationDate":1374749912000,"name":null,"id":"885ef7a4-24b8-4be2-837a-219be6cf8f46","displayName":null,"description":null,"status":null}],"functionalFrameworkInfo":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"pilotInfo":null,"selectedFrameworks":null,"version":"1.0","code":"node","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1374749912000,"name":"node","id":"f1aed01f-f4fb-44da-8ae5-e3a74b60e88c","displayName":null,"description":"","status":null}],"projectCode":"run","preBuilt":false,"endDate":null,"startDate":null,"multiModule":false,"noOfApps":1,"version":"1.0","customerIds":["photon"],"used":false,"helpText":null,"system":false,"creationDate":1374748085000,"name":"run","id":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","displayName":null,"description":"","status":null}},"status":"success"}');
			$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
		},
	
		runTests: function() {
			//module("build.js;Build");
			module("build.js");
			var build = new Build(), self = this, buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			asyncTest("Build Component UI Test", function(){
				var output;
				
				$(commonVariables.contentPlaceholder).children().remove();
				self.setiPhoneAppInfo();
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'NativeApp');
				
				//mock remote machine check 
				var remoteMachine = $.mockjax({
				  url: commonVariables.webserviceurl + 'util/checkMachine',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR13C00001","data":"true","status":"success"});
				  }
				});
				
				//mock build list ajax call 
				var buildList = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/list?appDirName=NativeApp',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Buildinfo listed Successfully","exception":null,"responseCode":null,"data":[{"options":{"deviceDeploy":true,"canCreateIpa":true},"serverName":null,"environments":["Production"],"buildNo":1,"deliverables":"/Documents and Settings/kavinraj_m/workspace/projects/NativeApp/do_not_checkin/build/NativeApp-1.0_25-Jul-2013-11-54-48.zip","buildName":"NativeApp-1.0_25-Jul-2013-11-54-48.zip","moduleName":null,"deployLocation":"/Documents and Settings/kavinraj_m/workspace/projects/NativeApp/do_not_checkin/build/NativeApp-1.0_25-Jul-2013-11-54-48/Phresco.app","importsql":null,"buildStatus":"SUCCESS","databaseName":null,"filePath":null,"webServiceName":null,"context":null,"timeStamp":"25/Jul/2013 11:54:48"}],"status":null});
				  }
				});
				
				//mock Run Again Source status ajax call 
				var checkRAS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=NativeApp',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Run against source not yet performed","exception":null,"responseCode":null,"data":false,"status":null});
				  }
				});
				
				commonVariables.animation = false;
				build.loadPage();
				$("#buildConsole").click();
				
				setTimeout(function() {
					start();
					output = $(commonVariables.contentPlaceholder).find("form[name=buildForm] .dyn_popup").attr('id');
					equal("build_genbuild", output, "Build Component Rendered and Run Again Source Status Check Successfully");
					self.ipaFileDownload(build, self, buildListener); // call delete build event
				}, 1500);
			});
		},
		
		ipaFileDownload : function(build, self, buildListener){
			asyncTest("IPA file download", function(){
				var openfolder = $.mockjax({
					url: commonVariables.webserviceurl + 'buildinfo/Ipadownload?appDirName=NativeApp&buildNumber=1',
					type: "POST",
					dataType: "json",
					contentType: "multipart/form-data",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":"IPA file download successfully","exception":null,"responseCode":null,"data":null,"status":null});
					}
				});
			
				$('img[name=ipaDownload]').click();
				setTimeout(function(){
					start();
					equal("", "", "IPA file download successfully");
					self.deleteBuild(build, self, buildListener); 
				}, 1500);
			});
		},
		
		//Delete build
		deleteBuild : function(build, self, buildListener){
			asyncTest("Build Delete Test", function(){
				var output;
			
				self.setiPhoneAppInfo();
			
				//mock delete build ajax call 
				var buildDelete = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/deletebuild?customerId=photon&appId=00195d40-60fb-433c-ac1a-2b57251a17da&projectId=fe5478f9-7636-42d0-9093-e0878dd83dfd',
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Build deleted Successfully","exception":null,"responseCode":"PHR700002","data":null,"status":null});
				  }
				});
		
				$('input[name=buildDelete]').click();
				setTimeout(function() {
					start();
					output = $(commonVariables.contentPlaceholder).find('#buildRow tr td:first').text();
					equal("", output, "Build deleted Successfully");
					self.genBuildDynamicParam(build, self, buildListener); // call delete build event
				}, 1500);
			});
		},
		
		//Dynamic param for generate build
		genBuildDynamicParam : function(build, self, buildListener){
			asyncTest("Dynamic param for generate build", function(){
				var output;
				self.setuserInfo();
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=build&appId=",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				//mock gen build dynamic param ajax call 
				var buildparam = $.mockjax({
				  url: commonVariables.webserviceurl + 'parameter/dynamic?appDirName=HTML&goal=package&phase=package&customerId=photon&userId=kavinraj_m',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Number","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildNumber","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.14003"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"true","value":"Production","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTest","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"showErrors","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-Dmaven.yuicompressor.skip=true"},{"key":"false","value":"-Dmaven.yuicompressor.skip=false"}]},"name":{"value":[{"value":"Minify","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"minify","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Pack Minified Files","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"packMinifiedFiles","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"package-type","possibleValues":{"value":[{"value":"war","key":"war","dependency":null},{"value":"zip","key":"zip","dependency":null}]},"multiple":"false","value":"war","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Package File Browse","lang":"en"}]},"type":"packageFileBrowse","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"packageFileBrowse","possibleValues":null,"multiple":"false","sort":false,"show":true}],"status":null});
				  }
				});
				$(".hProjectId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$("input[name=build_genbuild]").click();
				setTimeout(function() {
					start();
					output = $('#build_genbuild ul.row li:first').attr('id');
					equal("buildNameLi", output, "Build dynamic param render Successfully");
					self.generateBuild(build, self, buildListener); 
				}, 1500);
			});
		},
		
		generateBuild : function(build, self, buildListener){
			asyncTest("Generate build", function(){
				var output;
				self.setuserInfo();
				self.setHTMLAppInfo();
				
				//mock generate build ajax call 
				var genBuild = $.mockjax({
				  url: commonVariables.webserviceurl + 'app/build?appDirName=HTML&buildName=&buildNumber=&environmentName=Production&logs=showErrors&package-type=war&minify=true&displayName=Kavinraj Mani&customerId=photon&appId=137223b6-8454-4041-b52d-07c110ab57fb&projectId=d6528c06-b2f6-4388-8001-54e7e25d59db&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				var paramValidation = $.mockjax({
				  url: commonVariables.webserviceurl + 'util/validation?appDirName=HTML&customerId=photon&phase=package&buildName=&buildNumber=&environmentName=Production&logs=showErrors&package-type=war&minify=true&displayName=Kavinraj Mani',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"uniquekey":null,"configErrorMsg":null,"service_exception":null,"parameterKey":null,"configErr":false,"responseCode":"PHR9C00001","status":"success"});
				  }
				});
				
				var getBuild = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/list?appDirName=HTML',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR700001","data":[{"options":null,"serverName":null,"environments":["Production"],"buildNo":1,"deliverables":null,"buildName":"PHR125_01-Aug-2013-13-34-23.zip","moduleName":null,"deployLocation":null,"importsql":null,"buildStatus":"SUCCESS","databaseName":null,"filePath":null,"webServiceName":null,"context":null,"timeStamp":"01/Aug/2013 13:34:23"}],"status":"success"});
				  }
				});
				
				$("#buildRun").click();
				buildListener.onPrgoress();
				$("#buildConsole").click();
				setTimeout(function() {
					start();
					output = $('#buildRow tr td:first').text();
					equal("1", output, "Build Generater Successfully");
					self.downloadBuild(build, self, buildListener); 
				}, 2000);
			});
		},
		
		downloadBuild : function(build, self, buildListener){
			asyncTest("Download build", function(){
				var downloadBuild = $.mockjax({
					url: commonVariables.webserviceurl + 'buildinfo/downloadBuild?appDirName=HTML&buildNumber=1',
					type: "GET",
					dataType: "json",
					contentType: "multipart/form-data",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":"Build download Successfully","exception":null,"responseCode":null,"data":[],"status":null});
					}
				});
			
				$("img[name=downloadBuild]").click();
				setTimeout(function() {
					start();
					equal("", "", "Build download Successfully");
					self.processBuildDynamicParam(build, self, buildListener); 
				}, 1500);
			});
		},

		processBuildDynamicParam : function(build, self, buildListener){
			asyncTest("Dynamic param for process build", function(){
				var output;
				self.setuserInfo();
				//mock process build dynamic param ajax call 
				var buildparam = $.mockjax({
				  url: commonVariables.webserviceurl + 'parameter/dynamic?appDirName=HTML&goal=process-build&phase=process-build&customerId=photon&userId=kavinraj_m',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"URL","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"url","possibleValues":null,"multiple":"false","value":"test","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Username","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"username","possibleValues":null,"multiple":"false","value":"sda","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Password","lang":"en"}]},"type":"password","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"password","possibleValues":null,"multiple":"false","value":"VmpKMGIxUXlTbGhVV0d4V1ZrUkJPUT09","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Commit Message","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"message","possibleValues":null,"multiple":"false","value":"sdaf","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Number","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildNumber","possibleValues":null,"multiple":"false","value":"4","sort":false,"show":true}],"status":null});
				  }
				});
				
				$("img[name=procBuild]").click();
				setTimeout(function() {
					start();
					output = $('#process_build ul.row li:first').attr('id');
					equal("", "", "Build dynamic param render Successfully");
					self.processBuild(build, self, buildListener); 
				}, 1500);
			});
		},
		
		processBuild : function(build, self, buildListener){
			asyncTest("Process build", function(){
				var output;
				self.setuserInfo();
				self.setHTMLAppInfo();
				
				//mock generate build ajax call 
				var genBuild = $.mockjax({
				  url: commonVariables.webserviceurl + 'app/processBuild?buildNumber=1&url=https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/IphoneAutomationInJe/&username=admin&password=manage&message=testing&customerId=photon&appId=137223b6-8454-4041-b52d-07c110ab57fb&projectId=d6528c06-b2f6-4388-8001-54e7e25d59db&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				buildListener.onPrgoress();
				$("#buildConsole").click();
				setTimeout(function() {
					start();
					output = $('#buildRow tr td:first').text();
					equal("1", output, "Process Build Generater Successfully");
					self.openFolder(build, self, buildListener); 
				}, 1500);
			});
		},
		
		openFolder : function(build, self, buildListener){
			asyncTest("Open build folder", function(){
				
				commonVariables.typeBuild = "build"
				commonVariables.openFolderContext = "util/openFolder";
				
				var openfolder = $.mockjax({
					url: commonVariables.webserviceurl + 'util/openFolder?type=build&appDirName=HTML',
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":"Folder opened successfully","exception":null,"responseCode":null,"data":null,"status":null});
					}
				});
			
				$("#openFolder").click();
				setTimeout(function(){
					start();
					equal("", "", "Folder opened successfully");
					self.copyPath(build, self, buildListener); 
				}, 1500);
			
			});
		},
		
		copyPath : function(build, self, buildListener){
			asyncTest("Copy build folder path", function(){
			
				commonVariables.typeBuild = "build"
				commonVariables.copyPathContext = "util/copyPath";
				
				var openfolder = $.mockjax({
					url: commonVariables.webserviceurl + 'util/copyPath?type=build&appDirName=HTML',
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":"Path copied successfully","exception":null,"responseCode":null,"data":null,"status":null});
					}
				});
			
				$("#copyPath").click();
				setTimeout(function(){
					start();
					equal("", "", "Path copied successfully");
					self.consoleLogCopy(build, self, buildListener); 
				}, 1500);
			});
		},
		
		consoleLogCopy : function(build, self, buildListener){
			asyncTest("Console log content copy", function(){
			
				commonVariables.copyToClipboardContext = "util/copyToClipboard";
				
				var openfolder = $.mockjax({
					url: commonVariables.webserviceurl + 'util/copyToClipboard',
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":"Log copied successfully","exception":null,"responseCode":null,"data":null,"status":null});
					}
				});
			
				$('#buildCopyLog').click();
				setTimeout(function(){
					start();
					equal("", "", "Console Log copied successfully");
					self.getProjectList(build, self, buildListener); 
				}, 1500);
			});
		},
		
		getProjectList : function(build, self, buildListener){
			asyncTest("Get project list for run again source", function(){
				var output;
				
				$(commonVariables.contentPlaceholder).children().remove();
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'node');
				
				//mock build list ajax call 
				var buildList = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/list?appDirName=node',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Buildinfo listed Successfully","exception":null,"responseCode":null,"data":[{"options":null,"serverName":null,"environments":["Production"],"buildNo":100,"deliverables":null,"buildName":"Latest.zip","moduleName":null,"deployLocation":null,"importsql":null,"buildStatus":"SUCCESS","databaseName":null,"filePath":null,"webServiceName":null,"context":null,"timeStamp":"30/Jul/2013 13:48:36"}],"status":null});
				  }
				});
				
				//mock Run Again Source status ajax call 
				var checkRAS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=node',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Run against source not yet performed","exception":null,"responseCode":null,"data":false,"status":null});
				  }
				});
				
				commonVariables.animation = false;
				build.loadPage();
				setTimeout(function() {
					start();
					output = $(commonVariables.contentPlaceholder).find("form[name=buildForm] .dyn_popup").attr('id');
					equal("build_genbuild", output, "Build Component Rendered and Run Again Source Status Check Successfully");
					self.rasPopup(build, self, buildListener); 
				}, 1500);
			});
		},
		
		rasPopup : function(build, self, buildListener){
			asyncTest("Run Again Source Pop Up", function(){
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'node');
				commonVariables.appDirName = "node";
				
				self.setNodeAppInfo();
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=Start&appId=",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				//mock RAS popup ajax call 
				var RASPopUp = $.mockjax({
				  url: commonVariables.webserviceurl + 'parameter/dynamic?appDirName=node&goal=start&phase=run-against-source&customerId=photon&userId=kavinraj_m',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.18001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"dataBase,fetchSql"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Execute Sql","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"executeSql","possibleValues":null,"value":"true","sort":false,"show":true,"dependency":"dataBase"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"DataBase","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.18001"}},"class":"com.photon.phresco.framework.param.impl.DynamicDataBaseImpl"},"required":"false","editable":"true","description":"","key":"dataBase","possibleValues":{"value":[{"value":"mysql","key":null,"dependency":null}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"fetchSql"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.19004-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.DynamicFetchSqlImpl"},"required":"false","editable":"true","description":"","key":"fetchSql","possibleValues":{"value":[{"value":"/source/sql/mysql/5.5.1/site.sql","key":"site.sql","dependency":null}]},"multiple":"false","value":"","sort":true,"show":false}],"status":null});
				  }
				});
				$(".hProjectId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$("input[name=build_runagsource]").click();
				setTimeout(function() {
					start();
					output = $('#build_runagsource ul li:first').attr('id');
					equal("showSettingsLi", output, "Run again source pop up render Successfully");
					self.rasRun(build, self, buildListener); 
				}, 1500);
			});
		},
		
		rasRun : function(build, self, buildListener){
			asyncTest("Run Again Source Run", function(){
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'node');
				commonVariables.appDirName = "node";
				commonVariables.mvnRunagainstSource = "app/runAgainstSource";
				
				self.setNodeAppInfo();
				
				var paramValidation = $.mockjax({
				  url: commonVariables.webserviceurl + 'util/validation?appDirName=node&customerId=photon&phase=run-against-source&environmentName=Production&executeSql=true&dataBase=mysql&fetchSql={}&displayName=Kavinraj Mani',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"uniquekey":null,"configErrorMsg":null,"service_exception":null,"parameterKey":null,"configErr":false,"responseCode":"PHR9C00001","status":"success"});
				  }
				});
				
				
				//mock Run Again Source status ajax call 
				var checkRAS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=node',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Run against source not yet performed","exception":null,"responseCode":null,"data":false,"status":null});
				  }
				});
				
				//mock RAS run ajax call 
				var RASPopUp = $.mockjax({

				  url: commonVariables.webserviceurl + 'app/runAgainstSource?appDirName=node&environmentName=Production&executeSql=true&dataBase=mysql&fetchSql={}&displayName=Kavinraj Mani&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				$("#runSource").click();
				setTimeout(function() {
					start();
					equal("", "", "Run again source run Successfully");
					self.rasRunRestart(build, self, buildListener); 
				}, 1500);
			});
		},
		
		rasRunRestart : function(build, self, buildListener){
			asyncTest("Run Again Source Restart", function(){
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'node');
				commonVariables.appDirName = "node";
				commonVariables.mvnRestartServer = "app/restartServer";
				
				self.setNodeAppInfo();
				
				var response = {};
				response.data = true;
				
				build.changeBtnStatus(response, '');
				
				//mock Run Again Source status ajax call 
				var checkRAS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=node',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Run against source not yet performed","exception":null,"responseCode":null,"data":false,"status":null});
				  }
				});
				
				//mock RAS restart ajax call 
				var RASPopUp = $.mockjax({

				  url: commonVariables.webserviceurl + 'app/restartServer?appDirName=node&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				$("#restart").click();
				setTimeout(function() {
					start();
					equal("", "", "Run again source restart Successfully");
					self.rasStop(build, self, buildListener); 
				}, 1500);
			});
		},
		
		rasStop : function(build, self, buildListener){
			asyncTest("Run Again Source Stop", function(){
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'node');
				commonVariables.appDirName = "node";
				commonVariables.mvnStopServer = "app/stopServer";
				
				self.setNodeAppInfo();
				
				var response = {};
				response.data = true;
				
				build.changeBtnStatus(response, '');
				
				//mock Run Again Source status ajax call 
				var checkRAS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=node',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Run against source not yet performed","exception":null,"responseCode":null,"data":false,"status":null});
				  }
				});
				
				//mock RAS restart ajax call 
				var RASPopUp = $.mockjax({

				  url: commonVariables.webserviceurl + 'app/stopServer?appDirName=node&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				$("#stop").click();
				setTimeout(function() {
					start();
					equal("", "", "Run again source stop Successfully");
					//self.Deployebuild(build, self, buildListener); 
				}, 1500);
			});
		}/*,
		
		Deployebuild : function(build, self, buildListener){
			asyncTest("Build Deploy", function(){
			});
		}*/
	};
});