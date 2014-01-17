define(["ci/continuousDeliveryConfigure"], function(ContinuousDeliveryConfigure) {
	return { 
		runTests: function (configData) {

			module("continuousDeliveryConfigure.js");	
			var continuousDeliveryConfigure = new ContinuousDeliveryConfigure(), templateData = {}, self = this, sortList;
			asyncTest("ContinuousDeliveryConfigure - UI Test", function() {
				$('#content').html('');
				var ciAPI = commonVariables.api;
				$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
				commonVariables.projectLevel = true;
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/listEnvironmentsByProjectId?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR600002","data":["test","Production"],"status":"success"});
					}
				});
				
				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&rootModule=&envName=test",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800019","data":{"{\"appName\":\"JqueryMobile\",\"appDirName\":\"JqueryMobile\"}":[{"name":"code","type":"codeValidation","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"componentTest","type":"componentTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest","type":"functionalTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest","type":"performanceTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest","type":"loadTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest","type":"pdfReport","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"build","type":"build","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Release","type":"release","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"codeGit","type":"codeValidation","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"git","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":"success"});
					}
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.continuousDeliveryConfigure);
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal(envName, "test", "ContinuousDeliveryConfigure - UI Tested");
					equal(length, 11, "ContinuousDeliveryConfigure - UI Tested");
//					$(".widget-maincontent-div").remove();
					self.changeEnvironmentTest(continuousDeliveryConfigure);
				}, 2500);
			});		
		},

		changeEnvironmentTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("changeEnvironmentTest - UI Test", function() {
				$('select[name=environments]').val('Production');
				var ciAPI = commonVariables.api;
				$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
				commonVariables.projectLevel = true;
				ciAPI.localVal.setSession("customerId" , "photon");
				self.sortList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/getJobTemplatesByEnvironment?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&rootModule=&envName=Production",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800019","data":{"{\"appName\":\"JqueryMobile\",\"appDirName\":\"JqueryMobile\"}":[{"name":"deploy","type":"deploy","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"componentTest","type":"componentTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest","type":"functionalTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest","type":"performanceTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest","type":"loadTest","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest","type":"pdfReport","module":null,"appIds":["JqueryMobile"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"Release","type":"release","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Confluence","Cobertura","TestFlight"]},{"name":"codeGit","type":"codeValidation","module":null,"appIds":["JqueryMobile"],"enableRepo":true,"repoTypes":"git","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"DrupalSingle\",\"appDirName\":\"DrupalSingle\"}":[{"name":"deploy","type":"deploy","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest","type":"unittest","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest","type":"functionalTest","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest","type":"performanceTest","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest","type":"loadTest","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest","type":"pdfReport","module":null,"appIds":["DrupalSingle"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"codeTfs","type":"codeValidation","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"tfs","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Confluence","Cobertura","TestFlight"]},{"name":"codeGit","type":"codeValidation","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"git","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"javaRoot\",\"appDirName\":\"javaRoot\",\"moduleName\":\"web\"}":[{"name":"code-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy-web","type":"deploy","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest-web","type":"unittest","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"componentTest-web","type":"componentTest","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest-web","type":"functionalTest","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest-web","type":"performanceTest","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest-web","type":"loadTest","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest-web","type":"pdfReport","module":"web","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"build-web","type":"build","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"javaRoot\",\"appDirName\":\"javaRoot\",\"moduleName\":\"webYui\"}":[{"name":"code-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy-webYui","type":"deploy","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest-webYui","type":"unittest","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"componentTest-webYui","type":"componentTest","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest-webYui","type":"functionalTest","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest-webYui","type":"performanceTest","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest-webYui","type":"loadTest","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest-webYui","type":"pdfReport","module":"webYui","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"build-webYui","type":"build","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"{\"appName\":\"javaRoot\",\"appDirName\":\"javaRoot\",\"moduleName\":\"java\"}":[{"name":"deploy-java","type":"deploy","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unitTest-java","type":"unittest","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"componentTest-java","type":"componentTest","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functionalTest-java","type":"functionalTest","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performanceTest-java","type":"performanceTest","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"loadTest-java","type":"loadTest","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdfTest-java","type":"pdfReport","module":"java","appIds":["javaRoot"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"build-java","type":"build","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}]},"status":"success"});
					}
				});
				$('select[name=environments]').change();
				setTimeout(function() {
					start();
					var envName = $(commonVariables.contentPlaceholder).find('select[name=environments]').val();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable1]').find('li').length;
					equal(envName, "Production", "changeEnvironmentTest - UI Tested");
					equal(length, 48, "changeEnvironmentTest - UI Tested");
					self.sortableTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},
		
		sortableTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("sortableTest - UI Test", function() {
				//sort1
				var sortable1LiObj = $("#sortable1 li[temp=ci]");
				var code = sortable1LiObj.find('a[id=DrupalSinglecode]');
//				var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable2]').find('li').length;
				var ui = {};
				ui.item = code.parent();
				continuousDeliveryConfigure.ciListener.sortableTwoChange(ui);
				continuousDeliveryConfigure.ciListener.sortableTwoReceive(ui);
				//sort2
				/*var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var code = sortable1LiObj.find('a[id=DrupalSinglecode]');
				var ui = {};
				ui.item = code.parent();*/
				continuousDeliveryConfigure.ciListener.sortableOneChange(ui);
				continuousDeliveryConfigure.ciListener.sortableOneReceive(ui);
				
				var dcode = sortable1LiObj.find('a[id=DrupalSinglecode]').closest('li');
				var ddeploy = sortable1LiObj.find('a[id=DrupalSingledeploy]').closest('li');
				var dunit = sortable1LiObj.find('a[id=DrupalSingleunitTest]').closest('li');
				var dfunc = sortable1LiObj.find('a[id=DrupalSinglefunctionalTest]').closest('li');
				var dperf = sortable1LiObj.find('a[id=DrupalSingleperformanceTest]').closest('li');
				var dload = sortable1LiObj.find('a[id=DrupalSingleloadTest]').closest('li');
				var dpdf = sortable1LiObj.find('a[id=DrupalSinglepdfTest]').closest('li');
				var dbuild = sortable1LiObj.find('a[id=DrupalSinglebuild]').closest('li');
				var dcodeGit = sortable1LiObj.find('a[id=DrupalSinglecodeGit]').closest('li');
				var dcodeTfs = sortable1LiObj.find('a[id=DrupalSinglecodeTfs]').closest('li');
				var mcode = sortable1LiObj.find('a[id=javaRootcode-java]').closest('li');
				
				$("#sortable2").append(dcode);
				$("#sortable2").append(ddeploy);
				$("#sortable2").append(dunit);
				$("#sortable2").append(dfunc);
				$("#sortable2").append(dperf);
				$("#sortable2").append(dload);
				$("#sortable2").append(dpdf);
				$("#sortable2").append(dbuild);
				$("#sortable2").append(dcodeGit);
				$("#sortable2").append(dcodeTfs);
				$("#sortable2").append(mcode);
				
				setTimeout(function() {
					start();
					var text = $("#sortable1 li[temp=ci]").eq(0).find('span').text();
					var length = $(commonVariables.contentPlaceholder).find('ul[id=sortable2]').find('li').length;
					equal(text, "deploy - deploy", "sortableTest - UI Tested");
					equal(length, 11, "sortableTest - UI Tested");
					self.dcodeJobsTest(continuousDeliveryConfigure);
				}, 2500);
			});
		},
		
		dcodeJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dcodeJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"skipTests"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.configuration + "/cronExpression",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Cron Expression calculated successfully","exception":null,"responseCode":null,"data":{"hours":null,"minutes":null,"month":null,"day":null,"cronBy":null,"every":null,"week":null,"cronExpression":"* * * * *","dates":["Wed Aug 07 15:13:00 IST 2013","Wed Aug 07 15:14:00 IST 2013","Wed Aug 07 15:15:00 IST 2013","Wed Aug 07 15:16:00 IST 2013"]},"status":null});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecode]');
				configure.click();
				$('input[name=jobName]').val('0job');
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=password]').val('asd');
				
				$('input[name=phrescoUrl]').val('asd');
				$('input[name=phrescoUsername]').val('asd');
				$('input[name=phrescoPassword]').val('asd');
				
				$('input[name=testUrl]').val('asd');
				$('input[name=testUsername]').val('asd');
				$('input[name=testPassword]').val('asd');
				
				$(".urlDotPhresco").click();
				$(".urlTest").click();
				
				$("input:checkbox[name=triggers][value=TimerTrigger]").prop('checked', true);
				$("input:checkbox[name=triggers][value=SCMTrigger]").prop('checked', true);
				
				$("select[name=hours]").val('6');
				$("select[name=minutes]").val('2');
				
				$('#CroneExpressionValue').val('2 6 * * *');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecode]').data('jobJson');
					equal(jobjson.jobName, "0job" 	, "deployJobsTest - UI Tested");
					self.dbuildJobsErrorTest(continuousDeliveryConfigure);
				}, 10000);
			});
		},
		
		dbuildJobsErrorTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dbuildJobsErrorTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/confluence",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800021","data":[{"type":null,"stream":null,"password":"@Mj1g1007","userName":"jagadeesh_r","revision":null,"serverPath":null,"repoUrl":"http://sdafsdf/asdf/123/","commitableFiles":null,"commitMessage":null,"branch":null,"testPassword":null,"revisionVal":null,"repoInfoFile":null,"repoExist":false,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/testFlight",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800026","data":[{"tokenPairName":"ttest123","apiToken":"sdga","teamToken":"@Mj1g1007","filePath":null,"notes":null,"notify":null,"distributionLists":null}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=package&phase=ci-package&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"true","value":"Production","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglebuild]');
				
				configure.click();
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					equal(1, 1 	, "dbuildJobsErrorTest - UI Tested");
					self.dbuildJobsTest(continuousDeliveryConfigure);
				}, 10000);
			});
		},
		
		dbuildJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dbuildJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/confluence",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800021","data":[{"type":null,"stream":null,"password":"@Mj1g1007","userName":"jagadeesh_r","revision":null,"serverPath":null,"repoUrl":"http://sdafsdf/asdf/123/","commitableFiles":null,"commitMessage":null,"branch":null,"testPassword":null,"revisionVal":null,"repoInfoFile":null,"repoExist":false,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/testFlight",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800026","data":[{"tokenPairName":"ttest123","apiToken":"sdga","teamToken":"@Mj1g1007","filePath":null,"notes":null,"notify":null,"distributionLists":null}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=package&phase=ci-package&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"true","value":"Production","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglebuild]');
				configure.click();
				
				$('input[name=jobName]').val('1job');
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=password]').val('asd');
				
				$('input[name=phrescoUrl]').val('asd');
				$('input[name=phrescoUsername]').val('asd');
				$('input[name=phrescoPassword]').val('asd');
				
				$('input[name=testUrl]').val('asd');
				$('input[name=testUsername]').val('asd');
				$('input[name=testPassword]').val('asd');
				
				$(".urlDotPhresco").click();
				$(".urlTest").click();
				
				$("input:checkbox[name=triggers][value=TimerTrigger]").prop('checked', true);
				$("input:checkbox[name=triggers][value=SCMTrigger]").prop('checked', true);
				
				$('input[name=scheduleType][value=Daily]').prop('checked', false);
				$('input[name=scheduleType][value=Weekly]').prop('checked', true);
				$('input[name=scheduleType][value=Monthly]').prop('checked', false);
				
				$('#CroneExpressionValue').val('0 1 * * 1');
				
				$('input[name=collabNetURL]').val('asd');
				$('input[name=collabNetusername]').val('asd');
				$('input[name=collabNetpassword]').val('asd');
				$('input[name=collabNetProject]').val('asd');
				$('input[name=collabNetPackage]').val('asd');
				$('input[name=collabNetRelease]').val('asd');
				
				$('input[name=confluenceSpace]').val('asd');
				$('input[name=confluencePage]').val('asd');
				$('input[name=confluenceOther]').val('asd');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglebuild]').data('jobJson');
					equal(jobjson.jobName, "1job" 	, "dbuildJobsTest - UI Tested");
					self.ddeployJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		ddeployJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("ddeployJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=deploy&phase=ci-deploy&customerId=photon&userId=admin&buildNumber=null&iphoneDeploy=",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"dataBase,fetchSql"},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Execute Sql","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"executeSql","possibleValues":null,"value":"true","sort":false,"show":true,"dependency":"dataBase"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"DataBase","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.DynamicDataBaseImpl"},"required":"false","editable":"true","description":"","key":"dataBase","possibleValues":{"value":[{"value":"mysql","key":null,"dependency":null}]},"multiple":"false","value":"mysql","sort":false,"show":false,"dependency":"fetchSql"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"FetchSql","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.DynamicFetchSqlImpl"},"required":"false","editable":"true","description":"","key":"fetchSql","possibleValues":{"value":[{"value":"/source/sql/mysql/5.5.1/site.sql","key":"mysql","dependency":null}]},"multiple":"false","value":"","sort":true,"show":false}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingledeploy]');
				configure.click();
				
				$('input[name=jobName]').val('2job');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingledeploy]').data('jobJson');
					equal(jobjson.jobName, "2job" 	, "ddeployJobsTest - UI Tested");
					self.dunitJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		dunitJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dunitJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=unit-test&phase=ci-unit-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00002","data":null,"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingleunitTest]');
				configure.click();
				
				$('input[name=jobName]').val('3job');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingleunitTest]').data('jobJson');
					equal(jobjson.jobName, "3job" 	, "dunitJobsTest - UI Tested");
					self.dfuncJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		dfuncJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dfuncJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=functional-test&phase=ci-functional-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"showSettings"}]},"value":"build","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":false},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglefunctionalTest]');
				configure.click();
				
				$('input[name=jobName]').val('4job');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglefunctionalTest]').data('jobJson');
					equal(jobjson.jobName, "4job" 	, "dfuncJobsTest - UI Tested");
					self.dperfJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		dperfJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dperfJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testBasis","possibleValues":{"value":[{"value":"Parameters","key":"parameters","dependency":"testAgainst,showSettings,environmentName,configurations,testName,rampUpPeriod,authManager,contextUrls,dbContextUrls"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"contextUrls"},{"value":"Web Services","key":"webservice","dependency":"contextUrls"},{"value":"Database","key":"database","dependency":"dbContextUrls"}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"configurations,testName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"configurations"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Configurations","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.PerformanceConfigurationsImpl"},"required":"false","editable":"true","description":"","key":"configurations","possibleValues":{"value":[{"value":"Server Configuration","key":null,"dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Result Name","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestResultNamesImpl"},"required":"true","editable":"edit","description":"","key":"testName","possibleValues":{"value":[{"value":"Drupal7","key":"Drupal7","dependency":"contextUrls"}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Ramp-Up Period","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"rampUpPeriod","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Auth Manager","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authManager","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"authorizationUrl,authorizationUserName,authorizationPassword,authorizationDomain,authorizationRealm"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Url","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Username","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUserName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Password","lang":"en"}]},"type":"Password","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationPassword","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Domain","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationDomain","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Realm","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationRealm","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"contextUrls","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"DB Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.8001"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"dbContextUrls","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"IsFromCI?","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"isFromCI","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/template?appDirName=DrupalSingle&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin&parameterKey=contextUrls",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR7C00001","data":"<div class=\"middle_div\"> \t<span>Context URLs</span>    <div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar jsonFlag = true;</script><div class=\"contextDivParent\" id=\"contextDivParent\">\t\t<script type=\"text/javascript\">\t\t\tjsonFlag = false;\t\t</script>\t\t\t<div id=\"contextDiv\" class=\"contextDivClass\">\t\t\t\t<table class=\"table table-striped table_border table-bordered context_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t<thead>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<th colspan=\"4\">\t\t\t\t\t\t\t<img width=\"15\" height=\"15\" title=\"Add Context\" style=\"cursor:pointer;\" border=\"0\" alt=\"\" src=\"themes/default/images/Phresco/small_plus_icon.png\" name=\"contextAdd\"\t\t\t\t\t\t\t class=\"contextAdd\">\t\t\t\t\t\t\t<div class=\"delete_icon removeContext\">\t\t\t\t\t\t\t\t<img class=\"removeContextImg\" src=\"themes/default/images/Phresco/delete_row.png\">\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t</th>\t\t\t\t\t\t</tr>\t\t\t\t\t</thead>\t\t\t\t\t<tbody>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>HTTP Name<sup>*</sup></label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"Helloworld\" name=\"httpName\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Additional Context</label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"/\" name=\"context\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Type</label>\t\t\t\t\t\t\t\t<select id=\"contextTypeHelloworld\" name=\"contextType\">\t\t\t\t\t\t\t\t\t<option value=\"GET\">GET</option>\t\t\t\t\t\t\t\t\t<option value=\"POST\">POST</option>\t\t\t\t\t\t\t\t\t<option value=\"PUT\">PUT</option>\t\t\t\t\t\t\t\t\t<option value=\"DELETE\">DELETE</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Encoding</label>\t\t\t\t\t\t\t\t<select id=\"encodingTypeHelloworld\" name=\"encodingType\">\t\t\t\t\t\t\t\t\t<option value=\"UTF-8\">UTF-8</option>\t\t\t\t\t\t\t\t\t<option value=\"UTF-16\">UTF-16</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"redirectAutomatically\" class=\"redirectAutomatically\">Redirect Automatically</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"true\" name=\"followRedirects\" class=\"followRedirects\">Follow Redirects</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"keepAlive\" class=\"keepAlive\">Use Keep Alive</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"multipartData\" class=\"multipartData\">Use Mulipart data</td>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"checkbox\" checkFlag=\"false\" name=\"compatibleHeaders\" class=\"compatibleHeaders\">Browser Compatible Headers</td>\t\t\t\t\t\t\t\t\t\t</tr> \t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" checkflag=\"false\" name=\"regexExtractor\" class=\"regexExtractor\">\t\t\t\t\t\t\t\t\t\t\t\tRegular Expression Extractor\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td></td><td></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Apply To</label>\t\t\t\t\t\t\t\t\t\t\t\t<select id=\"applyToHelloworld\" name=\"applyTo\">\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"main\">Main sample</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"all\">Main & sub samples</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"children\">Sub sample</option>\t\t\t\t\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Response Field</label>\t\t\t\t\t\t\t\t\t\t\t\t<select id=\"responseFieldHelloworld\" name=\"responseField\">\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"false\">Body</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"unescaped\">Body(unescaped)</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"as_document\">Body as document</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"true\">Headers</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"URL\">URL</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"code\">Response code</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"message\">Response message</option>\t\t\t\t\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Reference Name</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"referenceName\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Regular Expression</label>\t\t\t\t\t\t\t\t\t\t\t\t<textarea type=\"text\" name=\"regex\"></textarea>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Template</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"template\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Match No.</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"matchNo\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Default Value</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"defaultValue\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td></td><td></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t</table> \t\t\t\t\t\t\t</td>                                  \t\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table table-striped table_border table-bordered header_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<th>Headers</th>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"headerKey\" placeholder=\"Key\"><input class=\"headerValue\" type=\"text\" placeholder=\"Value\"><input type=\"button\" value=\"Add\" class=\"btn btn_style headerKeyAdd\"></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"add_test\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t </tbody>\t\t\t\t\t\t\t\t   </table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t\t\t <tr>\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t <table class=\"table table-striped table_border table-bordered header_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t<th>Parameters</th>\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class=\"parameterRow\">\t\t\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"parameterName\" value=\"\" placeholder=\"Name\" name=\"parameterName\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<textarea class=\"parameterValue\" name=\"parameterValue\" placeholder=\"Value\"></textarea>\t\t\t\t\t\t\t\t\t\t\t\t\t<input class=\"parameterEncode\" type=\"checkbox\"  name=\"parameterEncode\" checkFlag=\"false\"/>Encode\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"themes/default/images/Phresco/minus_icon.png\" class=\"add_test_icon removeParamter\" style=\"display:none;\">\t\t\t\t\t\t\t\t\t\t\t\t\t<img class=\"add_test_icon addParameter\" src=\"themes/default/images/Phresco/plus_icon.png\"></td>\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t</tbody>\t\t\t\t</table>\t\t\t</div>   \t\t\t<script type=\"text/javascript\">\t\t\t\tdocument.getElementById(\"contextTypeHelloworld\").value = 'GET';\t\t\t\tdocument.getElementById(\"encodingTypeHelloworld\").value = 'UTF-8';\t\t\t\tdocument.getElementById(\"responseFieldHelloworld\").value = '';\t\t\t\tdocument.getElementById(\"applyToHelloworld\").value = '';\t\t\t</script>\t\t\t</div>     <script type=\"text/javascript\">\tif(jsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table border_div' cellpadding='0' cellspacing='0' \tborder='0'><thead><tr><th colspan='4'><img width='15' height='10' style='cursor:pointer;' title='Add Context' border='0' alt='' src='themes/default/images/Phresco/small_plus_icon.png' name='contextAdd' class='contextAdd'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Additional Context</label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically' checked>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr><tr><td><input type='checkbox'  name='regexExtractor' class='regexExtractor'>Regular Expression Extractor</td><td></td><td></td></tr><tr class='regexTr hideContent'><td><label>Apply To</label><select name='applyTo'><option value='main'>Main sample</option><option value='all'>Main & sub samples</option><option value='children'>Sub sample</option></select></td><td><label>Response Field</label><select name='responseField'><option value='false'>Body</option><option value='unescaped'>Body(unescaped)</option><option value='as_document'>Body as document</option><option value='true'>Headers</option><option value='URL'>URL</option><option value='code'>Response code</option><option value='message'>Response message</option></select></td><td><label>Reference Name</label><input type='text' value='' name='referenceName'></td></tr><tr class='regexTr hideContent'><td><label>Regular Expression</label><textarea type='text'  name='regex'></textarea></td><td><label>Template</label><input type='text' value='$1$' name='template'></td><td><label>Match No.</label><input type='text' value='1' name='matchNo'></td></tr><tr class='regexTr hideContent'><td><label>Default Value</label><input type='text' value='NOT FOUND' name='defaultValue'></td><td></td><td></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><textarea class='parameterValue' value='' name='parameterValue' placeholder='Value'></textarea>&nbsp;<input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/Phresco/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/Phresco/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#contextDivParent\");\t}  else {\t\tcheckEncodeCheckBox();\t}\t\tfunction checkEncodeCheckBox() {\t\tjQuery('.parameterEncode').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.redirectAutomatically').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.followRedirects').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.keepAlive').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.multipartData').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.compatibleHeaders').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.regexExtractor').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t\tjQuery(this).closest('tbody').find('.regexTr').show();\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t\tjQuery(this).closest('tbody').find('.regexTr').hide();\t\t\t}\t\t});\t}</script>","status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/template?appDirName=DrupalSingle&goal=performance-test&phase=ci-performance-test&customerId=photon&userId=admin&parameterKey=dbContextUrls",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR7C00001","data":"<div class=\"middle_div\">\t<span>DB Context URLs</span>\t<div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar dbJsonFlag = true;</script><div id=\"dbContextDivParent\">\t\t\t\t</div>\t<script type=\"text/javascript\">\tif(dbJsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'dbContextDiv').attr('class','dbContextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table border_div' cellpadding='0' cellspacing='0' border='0'>\t\t\t<thead><tr><th colspan='4'><img width='15' height='10' style='cursor:pointer;' title='Add DB Context' border='0' alt='' src='themes/default/images/Phresco/small_plus_icon.png' name='dbContextAdd' class='dbContextAdd'><div class='delete_icon removeDBContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr>\t\t\t</thead><tbody><tr>\t\t\t<td><label>Name<sup>*</sup></label><input type='text' name='dbName' value=''></td><td><label>Query Type<sup>*</sup></label><select name='queryType'><option value='Select Statement'>Select Statement</option><option value='Update Statement'>Update Statement</option></select></td><td><label>Query<sup>*</sup></label><textarea name='query' id='query'></textarea></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#dbContextDivParent\");\t\t}</script>\t","status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + "app/ciPerformanceTest?jobName=5job&repoType=clonedWorkspace&testUrl=asd&testUsername=asd&testPassword=asd&downStreamCriteria=SUCCESS&triggers=TimerTrigger&triggers=SCMTrigger&scheduleType=Weekly&hours=*&minutes=*&scheduleExpression=*+*+*+*+*&testBasis=parameters&testAgainst=server&environmentName=Production&configurations=Server+Configuration&testName=Drupal7&rampUpPeriod=10&authorizationUrl=&authorizationUserName=&authorizationPassword=&authorizationDomain=&authorizationRealm=&logs=showErrors&dbName=&queryType=Select+Statement&query=&isFromCI=true&appDirName=DrupalSingle&rootModule=&testAction=performance",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"responseCode":null,"status":"success","log":null,"connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":null,"service_exception":null,"configErrorMsg":null});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingleperformanceTest]');
				configure.click();
				
				setTimeout(function() {
					$('input[name=jobName]').val('5job');
					$("[name=configure]").click();
				}, 1500);
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingleperformanceTest]').data('jobJson');
					equal(jobjson.jobName, "5job" 	, "dperfJobsTest - UI Tested");
					self.dloadJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		
		dloadJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dloadJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=load-test&phase=ci-load-test&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testBasis","possibleValues":{"value":[{"value":"Parameters","key":"parameters","dependency":"testAgainst,showSettings,environmentName,configurations,testName,noOfUsers,loopCount,rampUpPeriod,authManager,loadContextUrl"}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":"loadContextUrl"},{"value":"Web Services","key":"webservice","dependency":"loadContextUrl"}]},"multiple":"false","value":"","sort":false,"show":false,"dependency":"configurations,testName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"customTestAgainst","possibleValues":{"value":[{"value":"Server","key":"server","dependency":null},{"value":"Web Services","key":"webservice","dependency":null}]},"multiple":"false","value":"server","sort":false,"show":false,"dependency":"availableJmx"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.2002-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true,"dependency":"configurations"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Configurations","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.2002-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceConfigurationsImpl"},"required":"false","editable":"true","description":"","key":"configurations","possibleValues":{"value":[{"value":"Server Configuration","key":null,"dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Result Name","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.2002-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestResultNamesImpl"},"required":"true","editable":"edit","description":"","key":"testName","possibleValues":{"value":[{"value":"Drupal7","key":"Drupal7","dependency":"loadContextUrl"}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"No Of Users","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"noOfUsers","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Ramp-Up Period","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"rampUpPeriod","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Loop Count","lang":"en"}]},"type":"Number","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"loopCount","possibleValues":null,"multiple":"false","value":"10","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Auth Manager","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authManager","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true,"dependency":"authorizationUrl,authorizationUserName,authorizationPassword,authorizationDomain,authorizationRealm"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Url","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Username","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationUserName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Password","lang":"en"}]},"type":"Password","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":"","key":"authorizationPassword","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Domain","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationDomain","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Realm","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"authorizationRealm","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Context URLs","lang":"en"}]},"type":"DynamicPageParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.2.0.2002-SNAPSHOT"}},"class":"com.photon.phresco.framework.param.impl.LoadTestDetailsImpl"},"required":"false","editable":"true","description":"","key":"loadContextUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"JMX","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"3.2.0.2002-SNAPSHOT"}},"class":"com.photon.phresco.impl.JmxFilesImpl"},"required":"true","editable":"true","description":null,"key":"availableJmx","possibleValues":{"value":[]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"IsFromCI?","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"isFromCI","possibleValues":null,"multiple":"false","value":"true","sort":false,"show":false},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/template?appDirName=DrupalSingle&goal=load-test&phase=ci-load-test&customerId=photon&userId=admin&parameterKey=loadContextUrl",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR7C00001","data":"<div class=\"middle_div\"> \t<span>Context URLs</span>    <div class=\"clear\"></div></div><script type=\"text/javascript\">\tvar jsonFlag = true;</script><div class=\"contextDivParent\" id=\"contextDivParent\">\t\t<script type=\"text/javascript\">\t\t\tjsonFlag = false;\t\t</script>\t\t\t<div id=\"contextDiv\" class=\"contextDivClass\">\t\t\t\t<table class=\"table table-striped table_border table-bordered context_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t<thead>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<th colspan=\"4\">\t\t\t\t\t\t\t<img width=\"15\" height=\"15\" title=\"Add Context\" style=\"cursor:pointer;\" border=\"0\" alt=\"\" src=\"themes/default/images/Phresco/small_plus_icon.png\" name=\"contextAdd\"\t\t\t\t\t\t\t class=\"contextAdd\">\t\t\t\t\t\t\t<div class=\"delete_icon removeContext\">\t\t\t\t\t\t\t\t<img class=\"removeContextImg\" src=\"themes/default/images/Phresco/delete_row.png\">\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t</th>\t\t\t\t\t\t</tr>\t\t\t\t\t</thead>\t\t\t\t\t<tbody>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>HTTP Name<sup>*</sup></label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"Helloworld\" name=\"httpName\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Additional Context</label>\t\t\t\t\t\t\t\t<input type=\"text\" value=\"/\" name=\"context\">\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Type</label>\t\t\t\t\t\t\t\t<select id=\"contextTypeHelloworld\" name=\"contextType\">\t\t\t\t\t\t\t\t\t<option value=\"GET\">GET</option>\t\t\t\t\t\t\t\t\t<option value=\"POST\">POST</option>\t\t\t\t\t\t\t\t\t<option value=\"PUT\">PUT</option>\t\t\t\t\t\t\t\t\t<option value=\"DELETE\">DELETE</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t<label>Encoding</label>\t\t\t\t\t\t\t\t<select id=\"encodingTypeHelloworld\" name=\"encodingType\">\t\t\t\t\t\t\t\t\t<option value=\"UTF-8\">UTF-8</option>\t\t\t\t\t\t\t\t\t<option value=\"UTF-16\">UTF-16</option>\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t</td>\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"redirectAutomatically\" class=\"redirectAutomatically\">Redirect Automatically</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"true\" name=\"followRedirects\" class=\"followRedirects\">Follow Redirects</td>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"keepAlive\" class=\"keepAlive\">Use Keep Alive</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" checkFlag=\"false\" name=\"multipartData\" class=\"multipartData\">Use Mulipart data</td>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"checkbox\" checkFlag=\"false\" name=\"compatibleHeaders\" class=\"compatibleHeaders\">Browser Compatible Headers</td>\t\t\t\t\t\t\t\t\t\t</tr> \t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" checkflag=\"false\" name=\"regexExtractor\" class=\"regexExtractor\">\t\t\t\t\t\t\t\t\t\t\t\tRegular Expression Extractor\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td></td><td></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Apply To</label>\t\t\t\t\t\t\t\t\t\t\t\t<select id=\"applyToHelloworld\" name=\"applyTo\">\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"main\">Main sample</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"all\">Main & sub samples</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"children\">Sub sample</option>\t\t\t\t\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Response Field</label>\t\t\t\t\t\t\t\t\t\t\t\t<select id=\"responseFieldHelloworld\" name=\"responseField\">\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"false\">Body</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"unescaped\">Body(unescaped)</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"as_document\">Body as document</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"true\">Headers</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"URL\">URL</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"code\">Response code</option>\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"message\">Response message</option>\t\t\t\t\t\t\t\t\t\t\t\t</select>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Reference Name</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"referenceName\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Regular Expression</label>\t\t\t\t\t\t\t\t\t\t\t\t<textarea type=\"text\" name=\"regex\"></textarea>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Template</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"template\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Match No.</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"matchNo\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr class=\"regexTr\">\t\t\t\t\t\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\t\t\t\t\t<label>Default Value</label>\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"\" name=\"defaultValue\">\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t<td></td><td></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t</table> \t\t\t\t\t\t\t</td>                                  \t\t\t\t\t\t\t</tr>\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t<table class=\"table table-striped table_border table-bordered header_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<th>Headers</th>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"headerKey\" placeholder=\"Key\"><input class=\"headerValue\" type=\"text\" placeholder=\"Value\"><input type=\"button\" value=\"Add\" class=\"btn btn_style headerKeyAdd\"></td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"add_test\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t </tbody>\t\t\t\t\t\t\t\t   </table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t\t\t <tr>\t\t\t\t\t\t\t\t<td colspan=\"4\">\t\t\t\t\t\t\t\t\t <table class=\"table table-striped table_border table-bordered header_table border_div\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\t\t\t\t<thead>\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t<th>Parameters</th>\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t</thead>\t\t\t\t\t\t\t\t\t\t<tbody>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class=\"parameterRow\">\t\t\t\t\t\t\t\t\t\t\t\t\t<td><input type=\"text\" class=\"parameterName\" value=\"\" placeholder=\"Name\" name=\"parameterName\"/>\t\t\t\t\t\t\t\t\t\t\t\t\t<textarea class=\"parameterValue\" name=\"parameterValue\" placeholder=\"Value\"></textarea>\t\t\t\t\t\t\t\t\t\t\t\t\t<input class=\"parameterEncode\" type=\"checkbox\"  name=\"parameterEncode\" checkFlag=\"false\"/>Encode\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"themes/default/images/Phresco/minus_icon.png\" class=\"add_test_icon removeParamter\" style=\"display:none;\">\t\t\t\t\t\t\t\t\t\t\t\t\t<img class=\"add_test_icon addParameter\" src=\"themes/default/images/Phresco/plus_icon.png\"></td>\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\t\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\t\t </td>\t\t\t\t\t\t\t </tr>\t\t\t\t\t</tbody>\t\t\t\t</table>\t\t\t</div>   \t\t\t<script type=\"text/javascript\">\t\t\t\tdocument.getElementById(\"contextTypeHelloworld\").value = 'GET';\t\t\t\tdocument.getElementById(\"encodingTypeHelloworld\").value = 'UTF-8';\t\t\t\tdocument.getElementById(\"responseFieldHelloworld\").value = '';\t\t\t\tdocument.getElementById(\"applyToHelloworld\").value = '';\t\t\t</script>\t\t\t</div>     <script type=\"text/javascript\">\tif(jsonFlag) {\t\tvar newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');\t\tnewTextBoxDiv.html(\"<table class='table table-striped table_border table-bordered context_table border_div' cellpadding='0' cellspacing='0' \tborder='0'><thead><tr><th colspan='4'><img width='15' height='10' style='cursor:pointer;' title='Add Context' border='0' alt='' src='themes/default/images/Phresco/small_plus_icon.png' name='contextAdd' class='contextAdd'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Additional Context</label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically' checked>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr><tr><td><input type='checkbox'  name='regexExtractor' class='regexExtractor'>Regular Expression Extractor</td><td></td><td></td></tr><tr class='regexTr hideContent'><td><label>Apply To</label><select name='applyTo'><option value='main'>Main sample</option><option value='all'>Main & sub samples</option><option value='children'>Sub sample</option></select></td><td><label>Response Field</label><select name='responseField'><option value='false'>Body</option><option value='unescaped'>Body(unescaped)</option><option value='as_document'>Body as document</option><option value='true'>Headers</option><option value='URL'>URL</option><option value='code'>Response code</option><option value='message'>Response message</option></select></td><td><label>Reference Name</label><input type='text' value='' name='referenceName'></td></tr><tr class='regexTr hideContent'><td><label>Regular Expression</label><textarea type='text'  name='regex'></textarea></td><td><label>Template</label><input type='text' value='$1$' name='template'></td><td><label>Match No.</label><input type='text' value='1' name='matchNo'></td></tr><tr class='regexTr hideContent'><td><label>Default Value</label><input type='text' value='NOT FOUND' name='defaultValue'></td><td></td><td></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><textarea class='parameterValue' name='parameterValue' placeholder='Value'></textarea>&nbsp;<input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/Phresco/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/Phresco/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>\");\t\tnewTextBoxDiv.appendTo(\"#contextDivParent\");\t}  else {\t\tcheckEncodeCheckBox();\t}\t\tfunction checkEncodeCheckBox() {\t\tjQuery('.parameterEncode').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.redirectAutomatically').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.followRedirects').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.keepAlive').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.multipartData').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.compatibleHeaders').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t}\t\t});\t\tjQuery('.regexExtractor').each(function() {\t\t\tvar checkFlag = jQuery(this).attr(\"checkFlag\");\t\t\tif (checkFlag == \"true\") {\t\t\t\tjQuery(this).prop(\"checked\", true);\t\t\t\tjQuery(this).closest('tbody').find('.regexTr').show();\t\t\t} else {\t\t\t\tjQuery(this).prop(\"checked\", false);\t\t\t\tjQuery(this).closest('tbody').find('.regexTr').hide();\t\t\t}\t\t});\t}</script>","status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + "app/ciPerformanceTest?jobName=6job&repoType=clonedWorkspace&testUrl=asd&testUsername=asd&testPassword=asd&downStreamCriteria=SUCCESS&triggers=TimerTrigger&triggers=SCMTrigger&scheduleType=Weekly&hours=*&minutes=*&scheduleExpression=*+*+*+*+*&testBasis=parameters&testAgainst=server&customTestAgainst=server&environmentName=Production&configurations=Server+Configuration&testName=Drupal7&noOfUsers=10&rampUpPeriod=10&loopCount=10&authorizationUrl=&authorizationUserName=&authorizationPassword=&authorizationDomain=&authorizationRealm=&logs=showErrors&isFromCI=true&appDirName=DrupalSingle&rootModule=&testAction=load",
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"responseCode":null,"status":"success","log":null,"connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":null,"service_exception":null,"configErrorMsg":null});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingleloadTest]');
				configure.click();
				
				setTimeout(function() {
					$('input[name=jobName]').val('6job');
					$("[name=configure]").click();
				}, 1500);
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingleloadTest]').data('jobJson');
					equal(jobjson.jobName, "6job" 	, "dloadJobsTest - UI Tested");
					self.dpdfJobsTest(continuousDeliveryConfigure);
				}, 3500);
			});
		},
		
		dpdfJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dpdfJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=pdf-report&phase=ci-pdf-report&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Report Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportType","possibleValues":{"value":[{"value":"Overall","key":"crisp","dependency":null},{"value":"Detailed","key":"detail","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Pdf Report Name","lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"reportName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Type","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"testType","possibleValues":{"value":[{"value":"All","key":"All","dependency":null},{"value":"unit","key":"unit","dependency":null},{"value":"functional","key":"functional","dependency":null},{"value":"performance","key":"performance","dependency":null},{"value":"load","key":"load","dependency":null},{"value":"manual","key":"manual","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Sonar Url","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonarUrl","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Logo","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"logo","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Theme","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"theme","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Technology Name","lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"technologyName","possibleValues":null,"multiple":"false","value":"","sort":false,"show":false}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglepdfTest]');
				configure.click();
				
				$('input[name=jobName]').val('7job');
				
				$('input[name=collabNetURL]').val('asd');
				$('input[name=collabNetusername]').val('asd');
				$('input[name=collabNetpassword]').val('asd');
				$('input[name=collabNetProject]').val('asd');
				$('input[name=collabNetPackage]').val('asd');
				$('input[name=collabNetRelease]').val('asd');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglepdfTest]').data('jobJson');
					equal(jobjson.jobName, "7job" 	, "dpdfJobsTest - UI Tested");
					self.dcodeGitJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		dcodeGitJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dcodeGitJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"skipTests"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecodeGit]');
				configure.click();
			
				$('input[name=jobName]').val('8job');
				
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=branch]').val('asd');
				$('input[name=password]').val('asd');
				
				$('input[name=phrescoUrl]').val('asd');
				$('input[name=phrescoUsername]').val('asd');
				$('input[name=phrescobranch]').val('asd');
				$('input[name=phrescoPassword]').val('asd');
				
				$('input[name=testUrl]').val('asd');
				$('input[name=testUsername]').val('asd');
				$('input[name=testbranch]').val('asd');
				$('input[name=testPassword]').val('asd');
				
				$(".urlDotPhresco").click();
				$(".urlTest").click();
				
				$("input:checkbox[name=triggers][value=TimerTrigger]").prop('checked', true);
				$("input:checkbox[name=triggers][value=SCMTrigger]").prop('checked', true);
				
				$('input[name=scheduleType][value=Daily]').prop('checked', false);
				$('input[name=scheduleType][value=Weekly]').prop('checked', false);
				$('input[name=scheduleType][value=Monthly]').prop('checked', true);
				
				$('#CroneExpressionValue').val('0 7 1 1 *');
				
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecodeGit]').data('jobJson');
					equal(jobjson.jobName, "8job" 	, "dcodeGitJobsTest - UI Tested");
					self.dcodeTfsJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		dcodeTfsJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("dcodeTfsJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=DrupalSingle&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"skipTests"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecodeTfs]');
				configure.click();
				$('input[name=jobName]').val('9job');
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=projectPath]').val('asd');
				$('input[name=password]').val('asd');
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecodeTfs]').data('jobJson');
					equal(jobjson.jobName, "9job" 	, "dcodeTfsJobsTest - UI Tested");
					self.mcodeJobsTest(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		mcodeJobsTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("mcodeJobsTest - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.paramaterContext + "/" + commonVariables.dynamicPageContext + "?appDirName=javaRoot&goal=validate-code&phase=ci-validate-code&customerId=photon&userId=admin&moduleName=java",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Validate Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"sonar","possibleValues":{"value":[{"value":"Source","key":"src","dependency":"src,skipTests"},{"value":"Functional Test","key":"functional","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"js","value":"-Pjs"},{"key":"java","value":"-Pjava"},{"key":"html","value":"-Phtml"},{"key":"web","value":"-Pweb"}]},"name":{"value":[{"value":"Technology","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"src","possibleValues":{"value":[{"value":"js","key":"js","dependency":null},{"value":"java","key":"java","dependency":null},{"value":"html","key":"html","dependency":null},{"value":"jsp/jsf","key":"web","dependency":null}]},"multiple":"false","value":"","sort":false,"show":false},{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"true","value":"-DskipTests=true"},{"key":"false","value":"-DskipTests=false"}]},"name":{"value":[{"value":"Skip Unit Test","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"skipTests","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":true},{"pluginParameter":"framework","mavenCommands":{"mavenCommand":[{"key":"showErrors","value":"-e"},{"key":"hideLogs","value":"-q"},{"key":"showDebug","value":"-X"}]},"name":{"value":[{"value":"Logs","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":null,"key":"logs","possibleValues":{"value":[{"value":"Show Errors","key":"showErrors","dependency":null},{"value":"Hide Logs","key":"hideLogs","dependency":null},{"value":"Show Debug","key":"showDebug","dependency":null}]},"multiple":"false","value":"","sort":false,"show":true}],"status":"success"});
					}
				});
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				var configure = sortable2LiObj.find('a[name=jobConfigurePopup][id=javaRootcode-java]');
				configure.click();
				$('input[name=jobName]').val('10job');
				
				$('input[name=url]').val('asd');
				$('input[name=username]').val('asd');
				$('input[name=password]').val('asd');
				
				$('input[name=phrescoUrl]').val('asd');
				$('input[name=phrescoUsername]').val('asd');
				$('input[name=phrescoPassword]').val('asd');
				
				$('input[name=testUrl]').val('asd');
				$('input[name=testUsername]').val('asd');
				$('input[name=testPassword]').val('asd');
				
				$(".urlDotPhresco").click();
				$(".urlTest").click();
				
				$("input:checkbox[name=triggers][value=TimerTrigger]").prop('checked', true);
				$("input:checkbox[name=triggers][value=SCMTrigger]").prop('checked', true);
				
				$('input[name=scheduleType][value=Daily]').prop('checked', true);
				$('input[name=scheduleType][value=Weekly]').prop('checked', false);
				$('input[name=scheduleType][value=Monthly]').prop('checked', false);
				
				$('#CroneExpressionValue').val('10 */3 * * *');
				
				
				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaRootcode-java]').data('jobJson');
					equal(jobjson.jobName, "10job" 	, "mcodeJobsTest - UI Tested");
					self.openJobs0Test(continuousDeliveryConfigure);
				}, 1500);
			});
		},
		
		openJobs0Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs0Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecode]').click();

				$("[name=configure]").click();
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecode]').data('jobJson');
					equal(jobjson.jobName, "0job" 	, "openJobs0Test - UI Tested");
					self.openJobs1Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs1Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs1Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglebuild]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglebuild]').data('jobJson');
					equal(jobjson.jobName, "1job" 	, "openJobs1Test - UI Tested");
					self.openJobs8Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs5Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs5Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingleperformanceTest]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingleperformanceTest]').data('jobJson');
					equal(jobjson.jobName, "5job" 	, "openJobs5Test - UI Tested");
					sself.openJobs6Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs6Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs6Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSingleloadTest]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSingleloadTest]').data('jobJson');
					equal(jobjson.jobName, "6job" 	, "openJobs6Test - UI Tested");
					self.openJobs8Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs8Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs8Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecodeGit]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecodeGit]').data('jobJson');
					equal(jobjson.jobName, "8job" 	, "openJobs8Test - UI Tested");
					self.openJobs9Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs9Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs9Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=DrupalSinglecodeTfs]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=DrupalSinglecodeTfs]').data('jobJson');
					equal(jobjson.jobName, "9job" 	, "openJobs9Test - UI Tested");
					self.openJobs10Test(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		openJobs10Test : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("openJobs10Test - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("customerId" , "photon");
				
				var sortable2LiObj = $("#sortable2 li[temp=ci]");
				sortable2LiObj.find('a[name=jobConfigurePopup][id=javaRootcode-java]').click();
				$("[name=configure]").click();
				
				setTimeout(function() {
					start();
					var jobjson = $(commonVariables.contentPlaceholder).find('a[id=javaRootcode-java]').data('jobJson');
					equal(jobjson.jobName, "10job" 	, "openJobs10Test - UI Tested");
					self.createContinuousIntegrationTest(continuousDeliveryConfigure);
				}, 500);
			});
		},
		
		

		createContinuousIntegrationTest : function(continuousDeliveryConfigure) {
			var self=this;
			asyncTest("createContinuousIntegrationTest - UI Test", function() {
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/validation?name=0job,2job,3job,4job,5job,6job,7job,1job,8job,9job,10job&flag=Add&userId=admin",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800003","data":true,"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/create?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&userId=admin&rootModule=",						
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800002","data":{"name":"contDelivery","jobs":[{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"asd","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"code","appName":"DrupalSingle","upstreamApplication":"","jenkinsUrl":"172.16.25.44","jobName":"0job","downstreamApplication":"1job","tokenPairName":"","operation":"codeValidation","repoType":"svn","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Daily","scheduleExpression":"2 6 * * *","mvnCommand":"phresco:validate-code -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"1job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":"asd","phrescoUsername":"asd","phrescoPassword":"asd","testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"asd","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"build","appName":"DrupalSingle","upstreamApplication":"0job","jenkinsUrl":"172.16.25.44","jobName":"1job","downstreamApplication":"2job","tokenPairName":"ttest123","operation":"build","repoType":"svn","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":true,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"Production","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":"0 1 * * 1","mvnCommand":"-Pci clean phresco:package -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":true,"collabNetURL":"asd","collabNetusername":"asd","collabNetpassword":"asd","collabNetProject":"asd","collabNetPackage":"asd","collabNetRelease":"asd","collabNetoverWriteFiles":true,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"0job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=package -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"\\do_not_checkin/build/*.zip","zipAlign":"","enableConfluence":true,"confluenceSite":"www.google.com","confluencePublish":false,"confluenceSpace":"asd","confluencePage":"asd","confluenceArtifacts":false,"confluenceOther":"asd","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"2job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":true,"projectPath":null,"phrescoUrl":"asd","phrescoUsername":"asd","phrescoPassword":"asd","testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"deploy","appName":"DrupalSingle","upstreamApplication":"1job","jenkinsUrl":"172.16.25.44","jobName":"2job","downstreamApplication":"3job","tokenPairName":"","operation":"deploy","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"Production","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"phresco:deploy -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"1job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=deploy -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"true","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"true","dataBase":"mysql","fetchSql":"{}","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"3job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"unitTest","appName":"DrupalSingle","upstreamApplication":"2job","jenkinsUrl":"172.16.25.44","jobName":"3job","downstreamApplication":"4job","tokenPairName":"","operation":"unittest","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"phresco:unit-test -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"2job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=unit-test -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"4job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"functionalTest","appName":"DrupalSingle","upstreamApplication":"3job","jenkinsUrl":"172.16.25.44","jobName":"4job","downstreamApplication":"5job","tokenPairName":"","operation":"functionalTest","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"Production","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"phresco:functional-test -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"3job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=functional-test -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"5job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"Server Configuration","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"true","selectedFiles":"","testBasis":"parameters","appDirName":"DrupalSingle","buildName":"","templateName":"performanceTest","appName":"DrupalSingle","upstreamApplication":"4job","jenkinsUrl":"172.16.25.44","jobName":"5job","downstreamApplication":"6job","tokenPairName":"","operation":"performanceTest","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"Production","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"phresco:performance-test -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"4job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=performance-test -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"Drupal7","noOfUsers":"","rampUpPeriod":"10","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"Select Statement","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"6job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"Server Configuration","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"true","selectedFiles":"","testBasis":"parameters","appDirName":"DrupalSingle","buildName":"","templateName":"loadTest","appName":"DrupalSingle","upstreamApplication":"5job","jenkinsUrl":"172.16.25.44","jobName":"6job","downstreamApplication":"7job","tokenPairName":"","operation":"loadTest","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"Production","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"phresco:load-test -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"5job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=load-test -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"server","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"Drupal7","noOfUsers":"10","rampUpPeriod":"10","loopCount":"10","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"server","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"7job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":null,"pomLocation":"pom.xml","username":null,"password":null,"email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"pdfTest","appName":"DrupalSingle","upstreamApplication":"6job","jenkinsUrl":"172.16.25.44","jobName":"7job","downstreamApplication":"8job","tokenPairName":"","operation":"pdfReport","repoType":"clonedWorkspace","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"All","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"http://localhost:9000/dashboard","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Weekly","scheduleExpression":null,"mvnCommand":"-Pci clean phresco:pdf-report -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":true,"collabNetURL":"asd","collabNetusername":"asd","collabNetpassword":"asd","collabNetProject":"asd","collabNetPackage":"asd","collabNetRelease":"asd","collabNetoverWriteFiles":true,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"6job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=pdf-report -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"{\"consoleHeaderColor\":\"\",\"editNavigationActiveBackGroundTop\":\"#a8a8a8\",\"copyRightLabel\":\"©2014.Photon Interactive Pvt Ltd. | \\u003ca href\\u003d\\\"http://www.photon.in\\\" target\\u003d\\\"_blank\\\"\\u003ewww.photon.in\\u003c/a\\u003e\",\"bottomButtonPanelTop\":\"#ededed\",\"pageLogoPadding\":\"\",\"editNavigationLink\":\"#1a1a1a\",\"buttonBackGroundColor\":\"#ed7940\",\"loginLogoMargin\":\"\",\"pageTitleBackGroundBottom\":\"#d2d2d2\",\"pageTitleColor\":\"#1a1a1a\",\"headerLinkColor\":\"#FFFFFF\",\"footerBackGroundcolorTop\":\"#3e3e3e\",\"pageTitleBackGroundTop\":\"#EEEEEE\",\"welcomeUserIcon\":\"0px\",\"projectTitleBackGroundColorTop\":\"\",\"projectTitleBackGroundColorBottom\":\"\",\"headerBackGroundcolorBottom\":\"#1b1b1b\",\"editNavigationActiveBackGroundBottom\":\"#5d5d5d\",\"bottomButtonPanelBottom\":\"#d1d1d1\",\"customerTitle\":\"Phresco\",\"headerBackGroundcolorTop\":\"#3E3E3E\",\"tableheaderBackGroundcolorBottom\":\"\",\"tableheaderBackGroundcolorTop\":\"\",\"headerActiveLinkColor\":\"#FF844A\",\"copyrightLabelColor\":\"\",\"footerBackGroundcolorBottom\":\"#1d1d1d\",\"customerBaseColor\":\"#E15432\"}","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"","skipTests":"","logo":"iVBORw0KGgoAAAANSUhEUgAAANcAAAAhCAYAAABKtj6xAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABqFJREFUeNrsXUuL7EQUrhlm782I3sV4XWR+QrcgvvCR9h90XLqRNIgILiQBUbmI0MGFICJ0NqKIj84/sOMDFbyOk58wWQiiV/FG9zqxSk9m0pl06tQjj36c4aOHTj1P6lSdV9J7WZaRHbVHf918uk/DGVOYFC6FUbqWUvjwf0iRNDSGAYUFn+OK66zvGPoPNfdpwNyrKAIwPgT5l3e9/jm6k730NWsBHcmSX7oRGJpRODXXJ8UJIYg3hxEwqo7OYKHJUALjjRH9yPADS8dIIWC8msIiE52jr2mBuzBnUZ57CmNwV2wkGGJzD67dXMTYCgfkPNPBJAI3y4fJbxuZMH8CAjaBzz6SWxirzBynEptfWbBnChuZIyFcDozbUOAba8P581WLnZ6Ta29EKVe4svNM941jzLMbVCP6Tuw0OFVcgH0TrKpTTIamNWpYUzRfoW6qqNJW+spTtvHmF1G9cP2TNbG45qCKpWR7aQbzD3syHkuTYEWSqq+q+SFKBvQ5aKrtO96To8Ppl1GbwpULWK4+bDNNC0ZxH4RdB/lrIFikQcFa6uOPl58Y3f3WV5UCdnD+d2Pewlw3TrZYuExJG6GJU4tn44RgLyeFOk5JpZI5teYdCNa0BcG6mN/vLz0+vOftr5OKk+ucV3l07zvfXDD0txcfy/VOB8G0cQ8Wli4qexzzhdcGD1TtN94YqxxRuSBZICCGpCNh3MFG0qZdZ4BWMFJ2aED5EMAzFjdJuMoUAHhu9AHcgC5VQ94uHnJsrCEs2Ehw0YnYeEy4q0IZLlkdA2tS/RUS6NsvPOpcf/fbpQ1wnzBZ4WFJupbgc+qaqPbXGx6iTNd8UO0/oZgI1nEoDES5gGIP1lJUcZ19b1Mcr7he7rMrXl85LQ+IaIbGcvkYsXttOqVgp5hrzAe2MOwGbG6d6m4CqpfL0ZQ6s69vP//I+Pp734UFtVBQts7Jjq4KjokQwD7TGLxrHtET/B4geOJL2pF+zX2wOuajVVSxVdXCAUKd2HS1cIpUq7ocY4woY1GcUpz9p+LgVLq6tnj88DTP0enBWhiXbK6McFFWCy/hcuomqPavYkZYRjEelmQ/KqA3M1vAZ105urCztIPxlceALUttloxuGNkdijO4x4ZgfwPO9aCBOZod85jB+NV52LxUC+VMLqwrPt4g9W+h4FVUpRnCCxaQ1UH7gMglrBbzCQNQG1NkPcLxQLbtEW3N9gL7EOXQWPzy3EOkw4W1zhT3hAcpCIaKmzqPWdkI4eAJcROJBUaPhAtOruY6afIZoHXxIto9Gk8AY5orLuAFwT3Cs84OHi2035BhlzZgsK4TWDxm2EOHTgjxokCxnZmiw8PY6Pufn1xE/5PIKexs23hqBbCjhz0eYwK22YRcPqwomi7Ey5lMEfXjBtZdX/gr59BAqIKeBsHS/SRyGxSS9XsKIOexB/wTycsbcBZY3XWrAeHi9dm6cO1rUjU8UDfsDY5tBdwYx//xoSb6nkCKUB0mGlRZdh8P4Z7yyg8U4mpOR7G8ppEevf99IqIWjo4+uHVhvP787IPb6qAIwaCvS7HJn99a5xBE7ojhJWWbHC8pTy2cEr2vhIh6skYEHRpFyrYaE8TJPN8Qg91XPAUTbqKr3Anm1pxcSecOo6JwqSVobB1SCo9TxqSY9XT8cwoDWTbmXE841wNEHzMBXjG+LiicmjJ+h7xNjj68FZVOrp10CYLaI5nPKTOGFKI+jduBcZ0iU4UGiilVATLty4EUNndFGhv7fg6pWBaiz6Qj/vpX41w7kiGMR9Ql3T4CUQ7+ugV754zwHwnheQ15dlUqaFNNwetbVramgnzs4tV+0X0f/XDFu71TC+VhI9UeU0NfM2QK86q+nIprrM1TCrf0vQXq15gzpggxbqYahi3fl7Bl9ZCZCpUhGNWHJbeZYtglp5wTg3ndhj05tapiVQMi/so1EY+o3UEcMo/btRH3sm98fFKpxezUQjXyEYtMZvHqJNnXN9epe6LBctVcRBkatRASGVHBWjmvnSteHTbkUvLcx+POHuDTP18Zl/dI0b0vk986RAbEZdquFaydcOlBglw0s45ennKsKYk6BQGJFNrwoI2k5c1vgtgAhZKfb3xywj2J96kdzP1blq3dX8WfTxFyyhgUc/jsYnx7FB5FKlGf1TukiDSMhbVxDG0mEvUDyTqHCvPP2xje+PTEpkAlCe/tfp+rXfrpmQf6MIzc2DdXuOTznwvS+ZtYdTZpH3+fKy70dxHDuv+zH9Gd/CvAACPojPw7BJpQAAAAAElFTkSuQmCC","reportType":"crisp","attachmentsPattern":"do_not_checkin/archives/cumulativeReports/*.pdf","enableArtifactArchiver":true,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"\\do_not_checkin/archives/cumulativeReports/*.pdf","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"8job","successEmailIds":"","failureEmailIds":"","technologyName":"php-Drupal7","redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":null,"phrescoUsername":null,"phrescoPassword":null,"testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"sad","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"codeGit","appName":"DrupalSingle","upstreamApplication":"7job","jenkinsUrl":"172.16.25.44","jobName":"8job","downstreamApplication":"9job","tokenPairName":"","operation":"codeValidation","repoType":"git","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Monthly","scheduleExpression":"0 7 1 1 *","mvnCommand":"phresco:validate-code -N","triggers":["TimerTrigger","SCMTrigger"],"branch":"asd","enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":true,"buildNotes":"","usedClonnedWorkspace":"7job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"9job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":"asd","phrescoUsername":"asd","phrescoPassword":"asd","testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":"asd","phrescoProjectPath":null,"testbranch":"asd","testProjectPath":null},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"asd","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"DrupalSingle","buildName":"","templateName":"codeTfs","appName":"DrupalSingle","upstreamApplication":"8job","jenkinsUrl":"172.16.25.44","jobName":"9job","downstreamApplication":"10job","tokenPairName":"","operation":"codeValidation","repoType":"tfs","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Monthly","scheduleExpression":null,"mvnCommand":"phresco:validate-code -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"buildNotes":"","usedClonnedWorkspace":"8job","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"SUCCESS","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"10job","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":"asd","phrescoUrl":"","phrescoUsername":"","phrescoPassword":"","testUrl":"","testUsername":"","testPassword":"","phrescobranch":null,"phrescoProjectPath":"","testbranch":null,"testProjectPath":""},{"context":"","type":"","defaultValue":"","query":"","mainClassName":"","module":"java","configuration":"","alias":"","url":"asd","pomLocation":"pom.xml","username":"asd","password":"asd","email":null,"buildNumber":"1","target":"","configurations":"","family":"","src":"js","mode":"","tagName":"","serialNumber":"","followRedirects":"","headerValue":"","keepAlive":"","keystore":"","keypass":"","parameterName":"","projectModule":"","isFromCI":"","selectedFiles":"","testBasis":"","appDirName":"javaRoot","buildName":"","templateName":"code-java","appName":"javaRoot","upstreamApplication":"9job","jenkinsUrl":"172.16.25.44","jobName":"10job","downstreamApplication":"","tokenPairName":"","operation":"codeValidation","repoType":"svn","jenkinsPort":"3579","jenkinsPath":"/ci","jenkinsProtocol":"http","testType":"","coberturaPlugin":false,"developmentVersion":"","releaseVersion":"","releaseUsername":"","releasePassword":"","releaseMessage":"","environmentName":"","sonarUrl":"","template":"","contextUrls":"","dbContextUrls":"","scheduleType":"Daily","scheduleExpression":"* * * * *","mvnCommand":"phresco:validate-code -DmoduleName=java -N","triggers":["TimerTrigger","SCMTrigger"],"branch":null,"enableBuildRelease":false,"collabNetURL":"","collabNetusername":"","collabNetpassword":"","collabNetProject":"","collabNetPackage":"","collabNetRelease":"","collabNetoverWriteFiles":false,"cloneWorkspace":false,"buildNotes":"","usedClonnedWorkspace":"","enablePostBuildStep":false,"enablePreBuildStep":true,"prebuildStepCommands":["phresco:ci-prestep -DjobName=${env.JOB_NAME} -Dgoal=ci -Dphase=validate-code -DcreationType=global -Did=98adc01c-b07f-495f-850a-8d66e08270cb -DcontinuousDeliveryName=contDelivery -N -DmoduleName=java"],"postbuildStepCommands":null,"logs":"showErrors","packageType":"","sdk":"","encrypt":"","plistFile":"","skipTest":"","proguard":"","coverage":"","signing":"","storepass":"","minify":"","deviceType":"","sdkVersion":"","devices":"","testAgainst":"","browser":"","resolution":"","showSettings":"false","projectType":"","keyPassword":"","buildEnvironmentName":"","executeSql":"false","dataBase":"","fetchSql":"","jarName":"","jarLocation":"","triggerSimulator":"false","packMinifiedFiles":"","deviceId":"","theme":"","deviceKeyPassword":"","emulatorKeyPassword":"","platform":"","sonar":"src","skipTests":"","logo":"","reportType":"","attachmentsPattern":"","enableArtifactArchiver":false,"headerKey":"","addHeader":"","testName":"","noOfUsers":"","rampUpPeriod":"","loopCount":"","httpName":"","contextType":"","encodingType":"","parameterValue":"","dbName":"","queryType":"","packageFileBrowse":"","unittest":"","downStreamCriteria":"","deviceList":"","collabNetFileReleasePattern":"","zipAlign":"","enableConfluence":false,"confluenceSite":null,"confluencePublish":false,"confluenceSpace":"","confluencePage":"","confluenceArtifacts":false,"confluenceOther":"","loadContextUrl":"","reportName":"","customTestAgainst":"","availableJmx":"","authManager":"","authorizationUrl":"","authorizationUserName":"","authorizationPassword":"","authorizationDomain":"","authorizationRealm":"","clonnedWorkspaceName":"","successEmailIds":"","failureEmailIds":"","technologyName":null,"redirectAutomatically":"","multipartData":"","compatibleHeaders":"","regexExtractor":"","applyTo":"","responseField":"","referenceName":"","regex":"","matchNo":"","fetchDependency":null,"device":"","enableTestFlight":false,"projectPath":null,"phrescoUrl":"asd","phrescoUsername":"asd","phrescoPassword":"asd","testUrl":"asd","testUsername":"asd","testPassword":"asd","phrescobranch":null,"phrescoProjectPath":null,"testbranch":null,"testProjectPath":null}],"envName":"Production"},"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.ci + "/pipeline?projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=&name=contDelivery&customerId=photon&rootModule=",						
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR810042","data":false,"status":"success"});
					}
				});
				
				$("input[name=continuousDeliveryName]").val("contDelivery");
				$(".content_end").find("input[type=submit][value=Add]").click();
				setTimeout(function() {
					start();
					equal(1, 1, "createContinuousIntegrationTest - UI Tested");
					require(["continuousDeliveryViewTest"], function(continuousDeliveryViewTest){
						continuousDeliveryViewTest.runTests();
					});
				}, 3500);
			});
		},
		
	};
});