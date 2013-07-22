define(["build/build"], function(Build) {

	return { 
		runTests: function(configData, runOtherTests) {
			//module("build.js;Build");
			module("build.js");
			var build = new Build(), self = this, appdirName = '', buildListener = new Clazz.com.components.build.js.listener.BuildListener();
			asyncTest("Build Component UI Test", function(){
				$("#custom").append(commonVariables.basePlaceholder);
				$("#custom").append(commonVariables.headerPlaceholder);
				$("#custom").append(commonVariables.navigationPlaceholder);
				$("#fixture").append(commonVariables.contentPlaceholder);
				$("#fixture").append(commonVariables.footerPlaceholder);
				
				var output;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				
				//set app directory name to local stroage
				buildListener.buildAPI.localVal.setSession('appDirName', 'HTML');
				
				//mock build list ajax call 
				var buildList = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/list?appDirName=HTML',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Buildinfo listed Successfully","exception":null,"data":[{"options":null,"deployLocation":null,"environments":["Production"],"serverName":null,"buildNo":123,"deliverables":null,"buildName":"kavin.zip","moduleName":null,"databaseName":null,"buildStatus":"SUCCESS","webServiceName":null,"filePath":null,"importsql":null,"context":null,"timeStamp":"11/Jul/2013 18:18:16"}]});
				  }
				});
				
				//mock Run Again Source status ajax call 
				var checkRGS = $.mockjax({
				  url: commonVariables.webserviceurl + 'buildinfo/checkstatus?appDirName=HTML',
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Connection Not Alive","exception":null,"data":false});
				  }
				});
				
				build.loadPageType();
				setTimeout(function() {
					start();
					output = $(commonVariables.contentPlaceholder).find("form[name=buildForm] .dyn_popup").attr('id');
					equal("build_genbuild", output, "Build Component Rendered Successfully");
					self.runServiceTest(build, runOtherTests);
				}, 1500);
			});
		},

		runServiceTest : function(build, runOtherTests){
			//runOtherTests();
		}
			
			/*asyncTest("Test - Build list test", function() {
			
				mockpreRender = mockFunction();
				when(mockpreRender)(anything()).then(function(arg) {
					var getBuildInfo = {"response":null,"message":"Buildinfo listed Successfully","exception":null,"data":[{"options":null,"serverName":null,"buildNo":1,"deliverables":null,"buildName":"PHR1_21-Jun-2013-10-59-20.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 10:59:20"},{"options":null,"serverName":null,"buildNo":2,"deliverables":null,"buildName":"PHR2_21-Jun-2013-12-34-12.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 12:34:12"}]};
					build.renderTemplate(getBuildInfo, commonVariables.contentPlaceholder);
					
				});

				build.preRender = mockpreRender;
				
				build.loadPageType();
				
				mockpostRender = mockFunction();
				when(mockpostRender)(anything()).then(function(arg) {
					var connectionStatus = {"response":null,"message":"Connection Not Alive","exception":null,"data":true};
					build.changeBtnStatus(connectionStatus , $(commonVariables.contentPlaceholder).find("input[name=build_runagsource]"));
				});

				build.postRender = mockpostRender;
				
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#buildRow tr td[name=1]").text(), "1", "Build List Test");
				}, 1000);
			});		
			
			asyncTest("Test - RAS Server status ", function() {
				mockpreRender = mockFunction();
				when(mockpreRender)(anything()).then(function(arg) {
					var getBuildInfo = {"response":null,"message":"Buildinfo listed Successfully","exception":null,"data":[{"options":null,"serverName":null,"buildNo":1,"deliverables":null,"buildName":"PHR1_21-Jun-2013-10-59-20.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 10:59:20"},{"options":null,"serverName":null,"buildNo":2,"deliverables":null,"buildName":"PHR2_21-Jun-2013-12-34-12.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 12:34:12"}]};
					build.renderTemplate(getBuildInfo, commonVariables.contentPlaceholder);
					
				});

				build.preRender = mockpreRender;
				
				build.loadPageType();
				
				mockpostRender = mockFunction();
				when(mockpostRender)(anything()).then(function(arg) {
					var connectionStatus = {"response":null,"message":"Connection Not Alive","exception":null,"data":true};
					build.changeBtnStatus(connectionStatus , $(commonVariables.contentPlaceholder).find("input[name=build_runagsource]"));
				});

				build.postRender = mockpostRender;

				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("input[name=build_runagsource]").attr('class'), "btn btn_style_off", "RAS server status Test");
				}, 1000);
			});
			
			asyncTest("Test - Start Server", function() {
				mockgetBuildInfo = mockFunction();
				when(mockgetBuildInfo)(anything()).then(function(arg) {
					var getBuildInfo = {"response":null,"message":"Buildinfo listed Successfully","exception":null,"data":[{"options":null,"serverName":null,"buildNo":1,"deliverables":null,"buildName":"PHR1_21-Jun-2013-10-59-20.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 10:59:20"},{"options":null,"serverName":null,"buildNo":2,"deliverables":null,"buildName":"PHR2_21-Jun-2013-12-34-12.zip","moduleName":null,"environments":[],"deployLocation":null,"buildStatus":"SUCCESS","databaseName":null,"webServiceName":null,"importsql":null,"context":null,"timeStamp":"21/Jun/2013 12:34:12"}]};
					
					build.renderTemplate(getBuildInfo, commonVariables.contentPlaceholder);
					
				});
					commonVariables.navListener.getMyObj(commonVariables.dynamicPage, function(retVal){
						build.dynamicPage = retVal;
						build.dynamicPageListener = build.dynamicPage.dynamicPageListener;
						build.dynamicpage.showParameters();
						build.dynamicPageListener.controlEvent();					
					});
				build.buildListener.getBuildInfo = mockgetBuildInfo;
				build.loadPageTest();
				
				mockgetserverStart = mockFunction();
				when(mockgetserverStart)(anything()).then(function(arg) {
					var serverStatus = {"connectionAlive":false,"uniquekey":"5d2acb46-79b6-48ef-8a2b-04cbab483304","service_exception":"","status":"STARTED","log":"STARTED"};
					//build.changeBtnStatus(serverStatus , $(commonVariables.contentPlaceholder).find("input[name=build_runagsource]"));
				});
				build.buildListener.runAgainstSource = mockgetserverStart;
						
				setTimeout(function() {
					start();
					equal($(commonVariables.contentPlaceholder).find("#logContent").text(), "btn btn_style_off", "Server start Test");
				}, 2500);
			}); */
		};
});