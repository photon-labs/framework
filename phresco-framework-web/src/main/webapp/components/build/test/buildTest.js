define(["build/build"], function(Build) {

	return { 
		setuserInfo : function(){
			commonVariables.api.localVal.setSession("username","kavinraj_m");
			commonVariables.api.localVal.setSession('appDirName', 'HTML');
			commonVariables.appDirName = "HTML";
			
			commonVariables.api.localVal.setSession('userInfo','{"email":"kavinraj.mani@photoninfotech.net","token":"b4h3ifhfbmoinspufpftf1jlac","authType":"LOCAL","customers":[{"options":["Help","Settings","Download","Home"],"emailId":null,"zipcode":null,"frameworkTheme":{"SubMenuBackGround":"#001C41","ButtonColor":"#001C41","CopyRight":"@ 2013 AEO Management Co. All Rights Reserved.","LabelColor":"#001C41","DisabledLabelColor":"#FFFFFF","brandingColor":"#001C41","accordionBackGroundColor":"#001C41","MenuBackGround":"#001C41","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#001C41","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"AEO","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"American Eagle Outfitters","id":"c1e5fd70-abd2-44c7-ae48-590df1150749","displayName":null,"description":"","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"CopyRight":"BestBuy","LabelColor":"#f8eb1c","DisabledLabelColor":"#FDEA00","MenuBackGround":"#223D62","CopyRightColor":"#000000","loginlogo":"63px","ButtonColor":"#003b64","logopadding":"12px","accordionBackGroundColor":"#638E97","brandingColor":"#0060a5","bodyBackGroundColor":"#FFFFFF","MenufontColor":"#FFFFFF","PageHeaderColor":"#f8eb1b"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"bb","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"BestBuy","id":"217bd500-a109-4c1d-bfa1-02c89652b989","displayName":null,"description":"BestBuy","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#000000","CopyRight":"EsteenLouncher","LabelColor":"#000000","DisabledLabelColor":"#B4B4B4","brandingColor":"#D5BF88","accordionBackGroundColor":"#D5BF88","MenuBackGround":"#0D091A","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#3F3F3F","PageHeaderColor":"#0D091A","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"estee","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Estee Lauder","id":"c32171c4-90e5-4ede-9c51-0ff370eae974","displayName":null,"description":"Estee LauderClinique","status":null},{"options":["Settings","Download","Home"],"emailId":null,"zipcode":null,"frameworkTheme":{"SubMenuBackGround":"#62666A","ButtonColor":"#4D4D4D","CopyRight":"Copyright © 2013 FIS and/or its subsidiaries. All Rights Reserved.","LabelColor":"","DisabledLabelColor":"#000000","brandingColor":"#A1CE61","accordionBackGroundColor":"#FFFFFF","MenuBackGround":"#62666A","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#62666A","MenufontColor":"#000000"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"fis","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"FIS Global","id":"ef90e3ed-01e1-427f-8671-67a687f3beb2","displayName":null,"description":"FIS is the world largest global provider dedicated to banking and payments technologies.","status":null},{"options":["Download"],"emailId":null,"zipcode":null,"frameworkTheme":{"CopyRight":"Johnson And Johnson Pvt Ltd","LabelColor":"#35d9ff","DisabledLabelColor":"#B51C1C","MenuBackGround":"#9a1515","CopyRightColor":"#797979","loginlogo":"88px","SubMenuBackGround":"#274c54","ButtonColor":"#ab170b","logopadding":"10px","accordionBackGroundColor":"#B51C1C","brandingColor":"#b41c1b","bodyBackGroundColor":"#FFFFFF","MenufontColor":"#FFFFFF","PageHeaderColor":"#f8eb1b"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"JnJ","state":null,"type":"TYPE_GOLD","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Johnson And Johnson","id":"a145ab5e-5eb6-4323-9c88-db61a79c9f3c","displayName":null,"description":"Johnson And Johnson","status":null},{"options":["Home","Help","Settings","Download"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#332026","CopyRight":"LorealPVTLtd","LabelColor":"#443639","DisabledLabelColor":"#443639","brandingColor":"#443639","accordionBackGroundColor":"#443639","MenuBackGround":"#96797C","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#85797D","PageHeaderColor":"#85797D","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"loreal","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Loreal","id":"0f3d8d9d-a7d0-49b8-b662-2cc25a5ee88b","displayName":null,"description":"Loreal","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#4D4D4D","CopyRight":"Macys.Pvt.Ltd","LabelColor":"#000000","DisabledLabelColor":"#999999","brandingColor":"#E21A2C","accordionBackGroundColor":"#FFFFFF","MenuBackGround":"#000000","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#000000","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"macys","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Macys","id":"4ab793a9-99fc-45bb-8971-0fd2b26acf8e","displayName":null,"description":"Macys","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#FF7200","CopyRight":"#009C98","LabelColor":"#000000","DisabledLabelColor":"#FF7200","brandingColor":"#009C98","accordionBackGroundColor":"#FFFFFF","MenuBackGround":"#676767","CopyRightColor":"#009C98","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#676767","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"merck","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Merck","id":"b6b5b856-97d8-4e2a-8b42-a5a23568fe51","displayName":null,"description":"Merck","status":null},{"options":["Home","Help","Settings","Download"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#56B0DC","CopyRight":"MetLife Pvt Ltd","LabelColor":"#B8EC95","DisabledLabelColor":"#FFFFFF","brandingColor":"#6C97B9","accordionBackGroundColor":"#6C97B9","MenuBackGround":"#007DC3","CopyRightColor":"#CCCCD3","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#477AA7","MenufontColor":"#76A2C1"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"metlife","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"MetLife","id":"05c80933-95d4-46c8-a58d-ceceb4bcce48","displayName":null,"description":"MetLife","status":null},{"options":["Help","Settings","Download","Home"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#4D4D4D","CopyRight":"","LabelColor":"#be1a44","DisabledLabelColor":"#000000","brandingColor":"#be1a44","accordionBackGroundColor":"#000000","MenuBackGround":"#C0C0C0","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#C0C0C0","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"marcus","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Neiman Marcus","id":"170a7a47-95f7-4829-84dc-e5cffab0cd7a","displayName":null,"description":"Neiman Marcus","status":null},{"options":["Home","Help","Settings","Download"],"emailId":null,"zipcode":null,"frameworkTheme":null,"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"photon","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Photon","id":"photon","displayName":null,"description":"photon","status":null},{"options":["Help","Settings","Download","Home"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#0c1b32","CopyRight":"© State Street Corporation","LabelColor":"#333333","DisabledLabelColor":"#28A8E0","brandingColor":"#1e457b","accordionBackGroundColor":"#FFFFFF","MenuBackGround":"#205d93","CopyRightColor":"#929292","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#000000","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"ssbt","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"SSBT","id":"1c65af0e-6a45-4841-abe7-7d1a07047b1d","displayName":null,"description":"SSBT","status":null},{"options":["Help","Settings","Download","Home"],"emailId":null,"zipcode":null,"frameworkTheme":{"SubMenuBackGround":"#333333","ButtonColor":"#C42B1D","CopyRight":"© 2013 Target Brands, Inc. Target, the Bullseye Design and Bullseye Dog are trademarks of Target Brands, Inc. All rights reserved.","LabelColor":"#C42B1D","DisabledLabelColor":"#FFFFFF","brandingColor":"#C42B1D","accordionBackGroundColor":"#C42B1D","MenuBackGround":"#333333","CopyRightColor":"#C42B1D","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#C42B1D","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"target","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Target","id":"92873861-be73-466f-9e9d-b880ceab5212","displayName":null,"description":"Target","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#000000","CopyRight":"TouchtunePVTLTD","LabelColor":"#000000","DisabledLabelColor":"#49A2D4","brandingColor":"#007EBD","accordionBackGroundColor":"#007CBA","MenuBackGround":"#353232","CopyRightColor":"#FFFFFF","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#000815","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"touchtunes","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"TouchTunes","id":"878b124c-8dac-470e-92c2-7e975b6b2746","displayName":null,"description":"TouchTunes\r\n","status":null},{"options":["Home","Help","Settings"],"emailId":null,"zipcode":null,"frameworkTheme":{"ButtonColor":"#000000","CopyRight":"Walgreens","LabelColor":"#000000","DisabledLabelColor":"#000000","brandingColor":"#464646","accordionBackGroundColor":"#FFFFFF","MenuBackGround":"#e01935","CopyRightColor":"#e01935","bodyBackGroundColor":"#FFFFFF","PageHeaderColor":"#007ED3","MenufontColor":"#FFFFFF"},"applicableAppTypes":null,"repoInfo":null,"contactNumber":null,"fax":null,"validFrom":null,"validUpto":null,"applicableTechnologies":null,"context":"walgreens","state":null,"type":"TYPE_BRONZE","address":null,"country":null,"icon":null,"helpText":null,"system":false,"creationDate":1375345184912,"name":"Walgreens","id":"a41228d1-5e74-4c7e-ac2e-b80d946376fc","displayName":null,"description":"Walgreens","status":null}],"phrescoEnabled":true,"validLogin":true,"loginId":"","firstName":"","lastName":"","roleIds":["4e8c0bed7-fb39-4erta-ae73-2d1286ae4ad0","4e8c0bd7-fb39-4aea-ae73-2d1286ae4ae0"],"permissions":{"manageCodeValidation":true,"manageBuilds":true,"manageTests":true,"manageCIJobs":true,"executeCIJobs":false,"manageMavenReports":false,"updateRepo":false,"manageRepo":true,"manageApplication":true,"importApplication":true,"managePdfReports":true,"manageConfiguration":true},"password":"b28a72debd125abe8ab48eb7f6e90df","helpText":"","system":false,"creationDate":1375338432120,"name":"kavinraj_m","id":"kavinraj_m","displayName":"Kavinraj Mani","description":"","status":null}');
		},
		
		setiPhoneAppInfo : function(){
			commonVariables.api.localVal.setSession('appdetails','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"endDate":null,"multiModule":false,"startDate":null,"noOfApps":1,"preBuilt":false,"appInfos":[{"modules":null,"pomFile":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[],"selectedDatabases":[],"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"selectedWebservices":[],"appDirName":"NativeApp","techInfo":{"appTypeId":"mobile-layer","techGroupId":"Iphone","techVersions":null,"version":"","used":false,"customerIds":null,"helpText":null,"creationDate":1374732729000,"system":false,"name":"Native","id":"tech-iphone-native","displayName":null,"description":null,"status":null},"version":"1.0","code":"NativeApp","used":false,"customerIds":null,"helpText":null,"creationDate":1374733413000,"system":false,"name":"NativeApp","id":"00195d40-60fb-433c-ac1a-2b57251a17da","displayName":null,"description":"","status":null}],"projectCode":"iPohneTechNativeApp","version":"1.0","used":false,"customerIds":["photon"],"helpText":null,"creationDate":1374732729000,"system":false,"name":"iPohneTechNativeApp","id":"fe5478f9-7636-42d0-9093-e0878dd83dfd","displayName":null,"description":"","status":null},"status":"success"}');
		},
		
		setHTMLAppInfo : function(){
			commonVariables.api.localVal.setSession('appdetails','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"HTML","techInfo":{"appTypeId":"web-layer","techVersions":null,"techGroupId":"HTML5","version":"2.0.2","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1373294442000,"name":"JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"description":null,"status":null},"selectedWebservices":[],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"helpText":null,"system":false,"creationDate":1373535598000,"name":null,"id":"4386478c-b603-4adf-b5fe-df669b626248","displayName":null,"description":null,"status":null}],"selectedDatabases":[],"functionalFrameworkInfo":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"pilotInfo":null,"selectedFrameworks":null,"version":"1.0","code":"HTML","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1373535598000,"name":"HTML","id":"137223b6-8454-4041-b52d-07c110ab57fb","displayName":null,"description":"","status":null}],"projectCode":"Java App","preBuilt":false,"endDate":1373481000000,"startDate":1373221800000,"multiModule":false,"noOfApps":2,"version":"1.0","customerIds":["photon"],"used":false,"helpText":null,"system":false,"creationDate":1373294442000,"name":"Java App","id":"d6528c06-b2f6-4388-8001-54e7e25d59db","displayName":null,"description":"testsing","status":null},"status":"success"}');
		},
		
		setNodeAppInfo : function(){
			commonVariables.api.localVal.setSession('appdetails','{"message":null,"exception":null,"responseCode":"PHR200009","data":{"appInfos":[{"modules":null,"pomFile":null,"appDirName":"node","techInfo":{"appTypeId":"app-layer","techVersions":null,"techGroupId":null,"version":"0.10.9","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1374748085000,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"description":null,"status":null},"selectedWebservices":[],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":[],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"helpText":null,"system":false,"creationDate":1374749912000,"name":null,"id":"885ef7a4-24b8-4be2-837a-219be6cf8f46","displayName":null,"description":null,"status":null}],"functionalFrameworkInfo":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"pilotInfo":null,"selectedFrameworks":null,"version":"1.0","code":"node","customerIds":null,"used":false,"helpText":null,"system":false,"creationDate":1374749912000,"name":"node","id":"f1aed01f-f4fb-44da-8ae5-e3a74b60e88c","displayName":null,"description":"","status":null}],"projectCode":"run","preBuilt":false,"endDate":null,"startDate":null,"multiModule":false,"noOfApps":1,"version":"1.0","customerIds":["photon"],"used":false,"helpText":null,"system":false,"creationDate":1374748085000,"name":"run","id":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","displayName":null,"description":"","status":null},"status":"success"}');
		},
	
		runTests: function() {
			//module("build.js;Build");
			module("build.js");
			var build = new Build(), self = this, buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			asyncTest("Build Component UI Test", function(){
				var output;
				
				$(commonVariables.contentPlaceholder).children().remove();
				
				//set app directory name to local stroage
				commonVariables.api.localVal.setSession('appDirName', 'NativeApp');
				
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
					  this.responseText = JSON.stringify({"message":"Build deleted Successfully","exception":null,"responseCode":null,"data":null,"status":null});
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
				  url: commonVariables.webserviceurl + 'app/build?buildName=&buildNumber=&environmentName=Production&logs=showErrors&package-type=war&minify=true&customerId=photon&appId=137223b6-8454-4041-b52d-07c110ab57fb&projectId=d6528c06-b2f6-4388-8001-54e7e25d59db&username=kavinraj_m',
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"log":null,"connectionAlive":false,"errorFound":false,"configErrorMsg":null,"uniquekey":"30b3faf1-620a-42fd-b2db-7d633a9f456d","service_exception":null,"responseCode":null,"status":"COMPLETED"});
				  }
				});
				
				var getBuild = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/list?appDirName=HTML',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Buildinfo listed Successfully","exception":null,"responseCode":null,"data":[{"options":null,"serverName":null,"environments":["Production"],"buildNo":1,"deliverables":null,"buildName":"PHR125_01-Aug-2013-13-34-23.zip","moduleName":null,"deployLocation":null,"importsql":null,"buildStatus":"SUCCESS","databaseName":null,"filePath":null,"webServiceName":null,"context":null,"timeStamp":"01/Aug/2013 13:34:23"}],"status":null});
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
				}, 1500);
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

				  url: commonVariables.webserviceurl + 'app/runAgainstSource?environmentName=Production&executeSql=on&dataBase=mysql&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
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

				  url: commonVariables.webserviceurl + 'app/restartServer?&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
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

				  url: commonVariables.webserviceurl + 'app/stopServer?&customerId=photon&appId=f1aed01f-f4fb-44da-8ae5-e3a74b60e88c&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&username=kavinraj_m',
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
		},
		
		Deployebuild : function(build, self, buildListener){
			/* asyncTest("Build Deploy", function(){
			}); */
		}
	};
});