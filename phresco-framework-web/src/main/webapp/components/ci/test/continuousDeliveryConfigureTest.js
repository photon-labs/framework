define(["ci/continuousDeliveryConfigure"], function(ContinuousDeliveryConfigure) {
	return { 

		runTests: function (configData) {

			module("continuousDeliveryConfigure.js");	
			var continuousDeliveryConfigure = new ContinuousDeliveryConfigure(), templateData = {}, self = this, sortList;
			asyncTest("ContinuousDeliveryView - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				$.mockjax({						
					url: commonVariables.webserviceurl + commonVariables.ci+"/list?projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=", 
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Continuous Delivery List Successfully","exception":null,"data":{"id":"a4329529-3c9d-476d-a310-e0cf4436e021","continuousDeliveries":null},"response":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/isAlive",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Jenkins is Alive","exception":null,"responseCode":null,"data":"200","status":null});
					}
				});

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryView);

				setTimeout(function() {
					start();
					equal(1, 1, "ContinuousDeliveryView - UI Tested");
					self.continuousDeliveryConfigure(continuousDeliveryConfigure);
				}, 2500);
			});					
		},

		continuousDeliveryConfigure : function(continuousDeliveryConfigure) {
			var self=this;
			$('#content').html('');
			asyncTest("ContinuousDeliveryConfigure - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				ciAPI.localVal.setSession("appDirName" , "");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Environments Listed successfully","exception":null,"data":["Production","Testing"],"response":null});
					}
				});


				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&envName=Production",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"htm\",\"appDirName\":\"htm\"}":[{"name":"Deploy","type":"deploy","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"funcTest","type":"functionalTest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Cobertura"]},{"name":"pdf","type":"pdfReport","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Confluence"]}],"{\"appName\":\"J2ee\",\"appDirName\":\"J2ee\"}":[{"name":"Deploy","type":"deploy","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"funcTest","type":"functionalTest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Cobertura"]},{"name":"pdf","type":"pdfReport","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Confluence"]}]},"status":null});
					}
				});

				$("input[id='createContinuousDelivery']").click();

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryConfigure);
				setTimeout(function() {
					start();

					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal(1, 1, "ContinuousDeliveryConfigure - UI Tested");
					equal(1, 1, "ContinuousDeliveryConfigure - UI Tested");
					self.changeEnvironmentTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},

		changeEnvironmentTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("changeEnvironmentTest - UI Test", function() {
				$('select[name=environments]').val('Testing');
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				ciAPI.localVal.setSession("appDirName" , "");
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&envName=Testing",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"responseCode":null,"data":{"{\"appName\":\"htm\",\"appDirName\":\"htm\"}":[{"name":"Deploy","type":"deploy","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"funcTest","type":"functionalTest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Cobertura"]},{"name":"pdf","type":"pdfReport","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Confluence"]}]},"status":null});
					}
				});
				$('select[name=environments]').change();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal("Testing", envName, "changeEnvironmentTest - UI Tested");
					equal(1, 1, "changeEnvironmentTest - UI Tested");
					self.configureJobTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},

		configureJobTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("configureJobTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/cronExpression",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Cron Expression calculated successfully","exception":null,"responseCode":null,"data":{"hours":null,"minutes":null,"month":null,"day":null,"cronBy":null,"every":null,"week":null,"cronExpression":"* * * * *","dates":["Wed Aug 07 15:13:00 IST 2013","Wed Aug 07 15:14:00 IST 2013","Wed Aug 07 15:15:00 IST 2013","Wed Aug 07 15:16:00 IST 2013"]},"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"src,skipTests,showSettings,environmentName"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"js","value":"-Pjs"},{"key":"java","value":"-Pjava"},{"key":"html","value":"-Phtml"},{"key":"web","value":"-Pweb"}]},"name":{"value":[{"value":"Technology","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"src","possibleValues":{"value":[{"value":"js","key":"js","dependency":"showSettings,environmentName"},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true -Dmaven.test.skip=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTests","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.19005-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null},{"value":"dev","key":null,"dependency":null},{"value":"clone","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=package&phase=ci-package&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.19005-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null},{"value":"dev","key":null,"dependency":null},{"value":"clone","key":null,"dependency":null}]},"multiple":"true","value":"Production","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTest","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"true","value":"-Dmaven.yuicompressor.skip=true"},{"key":"false","value":"-Dmaven.yuicompressor.skip=false"}]},"name":{"value":[{"value":"Minify","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"minify","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Package File Browse","lang":"en"}]},"type":"packageFileBrowse","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"packageFileBrowse","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true}],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=deploy&phase=ci-deploy&customerId=photon&userId=admin&buildNumber=null&iphoneDeploy=",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"},{"value":"Envit","lang":"cv"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.19005-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null},{"value":"dev","key":null,"dependency":null},{"value":"clone","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=unit-test&phase=ci-unit-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":"showSettings,environmentName"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.19005-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null},{"value":"dev","key":null,"dependency":null},{"value":"clone","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=functional-test&phase=ci-functional-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"showSettings"}]},"value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.0.0.19005-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null},{"value":"dev","key":null,"dependency":null},{"value":"clone","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Resolution","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"edit","description":"","key":"resolution","possibleValues":{"value":[{"value":"320x480","key":"320x480","dependency":null},{"value":"1024x768","key":"1024x768","dependency":null},{"value":"1280x800","key":"1280x800","dependency":null},{"value":"1280x960","key":"1280x960","dependency":null},{"value":"1280x1024","key":"1280x1024","dependency":null},{"value":"1360x768","key":"1360x768","dependency":null},{"value":"1440x900","key":"1440x900","dependency":null},{"value":"1600x900","key":"1600x900","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":null});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=htm&goal=pdf-report&phase=ci-pdf-report&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Parameter returned successfully","exception":null,"responseCode":null,"data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Report Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportType","possibleValues":{"value":[{"value":"Overall","key":"crisp","dependency":null},{"value":"Detailed","key":"detail","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Pdf Report Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testType","possibleValues":{"value":[{"value":"All","key":"All","dependency":null},{"value":"unit","key":"unit","dependency":null},{"value":"functional","key":"functional","dependency":null},{"value":"performance","key":"performance","dependency":null},{"value":"load","key":"load","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Sonar Url","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonarUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Logo","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"logo","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Theme","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"theme","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Technology Name","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"technologyName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false}],"status":null});
					}
				});

				var sortable1LiObj = $("#sortable1 li[temp=ci]");
				$(sortable1LiObj).each(function(index) {
					$("#sortable2").append($(this));
				});

				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				$(sortable2LiObj).each(function(index) {
					var thisJobJsonData = {};
					var anchorTag = $(this).find('a');
					var templateJson = anchorTag.data("templateJson");
					thisJobJsonData.appDirName = "SVNCHECK-wordpress";
					thisJobJsonData.appName = "SVNCHECK-wordpress";
					thisJobJsonData.jobName = index+"job";
					thisJobJsonData.operation = templateJson.type;
					if (templateJson.repoTypes == 'svn' || templateJson.repoTypes == 'git') {
						thisJobJsonData.url = "url";
						thisJobJsonData.username = "username";
						thisJobJsonData.password = "password";
					}
					if(templateJson.jobtemplatename == 'build') {
						$('#jonConfiguration').find('input[name=collabNetURL]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetusername]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetpassword]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetProject]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetPackage]').val('asd');
						$('#jonConfiguration').find('input[name=collabNetRelease]').val('asd');
					}

					if(templateJson.jobtemplatename == 'pdf') {
						$('#jonConfiguration').find('input[name=confluenceSpace]').val('asd');
						$('#jonConfiguration').find('input[name=confluencePage]').val('asd');
					}

					anchorTag.data("jobJson", thisJobJsonData)
				});

				$("#sortable2 li.ui-state-default a").show();
				$("#sortable1 li.ui-state-default a").hide();

				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				$(sortable1LiObj).each(function(index) {
					$(this).find('a[name=jobConfigurePopup]').click();
					$("[name=configure]").click();
				});

				setTimeout(function() {
					start();
					equal(1, 1, "configureJobTest - UI Tested");
					self.createContinuousIntegrationTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},


		createContinuousIntegrationTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("createContinuousIntegrationTest - UI Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/create?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&userId=admin",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"data":{"{\"appName\":\"SVNCHECK-wordpress\",\"appDirName\":\"SVNCHECK-wordpress\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"response":null});
					}
				});

				$("input[name=continuousDeliveryName]").val("contDelivery");
				$("input[type=submit]").click();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal(1, 1, "createContinuousIntegrationTest - UI Tested");
					self.updateContinuousIntegrationTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},

		updateContinuousIntegrationTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("updateContinuousIntegrationTest - UI Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/update?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=&userId=admin",						
					type:'PUT',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates Fetched Successfully","exception":null,"data":{"{\"appName\":\"SVNCHECK-wordpress\",\"appDirName\":\"SVNCHECK-wordpress\"}":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"response":null});
					}
				});

				$("input[name=continuousDeliveryName]").val("contDelivery");
				$("input[type=submit]").attr('value', 'Update');
				$("input[type=submit]").attr('name', 'update');
				$("input[type=submit][value=Update]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "updateContinuousIntegrationTest - UI Tested");
					require(["continuousDeliveryViewTest"], function(continuousDeliveryViewTest){
						continuousDeliveryViewTest.runTests();
					});
				}, 2500);
			});
		},





	};
});
