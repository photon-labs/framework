
define(["build/build"], function(Build) {

	return { runTests: function (configData) {
		
		module("build.js;Build");
		
		var build = new Build();
		asyncTest("Test - Build list test", function() {
		
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
		
		/*asyncTest("Test - Start Server", function() {
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
				console.info('log = ' , $(commonVariables.contentPlaceholder).find("#logContent"));
				equal($(commonVariables.contentPlaceholder).find("#logContent").text(), "btn btn_style_off", "Server start Test");
			}, 2500);
		}); */
		
	}};
});