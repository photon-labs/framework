define(["projectlist/projectList"], function(ProjectList) {

	return { runTests: function (configData) {
		
		module("projectlist.js;projectlist");
		
		var projectlist = new ProjectList();
		asyncTest("Test - Project List Service", function() {
			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {				
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
				equal("tech-wordpress", getval, "Project List Service Tested");
			}, 3000);
		});
		
		asyncTest("Test - Project Delete Service", function() {			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			var deleteproject = "wordpress-WordPress";
			projectlist.deleterow("delete", "delete", deleteproject);;		
			
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
				equal("tech-wordpress", getval, "Project List Service Tested");
			}, 1500);
		});
		
		
		asyncTest("Test - Project List delete failure test case", function() {			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			projectlist.deleterow("delete", "delete", "wordpress-WordPress203" );		
			
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
				equal("tech-wordpress", getval, "Project List delete failure test case Tested");
			}, 1500);
		}); 
		
		
		asyncTest("Test - Project List addrepo popup rendered", function() {			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {				
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f option[value=svn]").text();				
				equal("SVN", getval, "Project List addrepo popup rendered");
			}, 1500);
		}); 
		
		
		asyncTest("Test - Project List Commit popup rendered", function() {			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {				
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find("select#commitType_294187d7-f75a-4adc-bb25-ce9465e0e82f option[value=svn]").text();				
				equal("SVN", getval, "Project List addrepo popup rendered");
			}, 1500);
		});  
		
		
		asyncTest("Test - Project List SVNPopup popup rendered", function() {			
			mockProjectList = mockFunction();
            when(mockProjectList)(anything()).then(function(arg) {				
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				projectlist.projectlist = projectListresponse.data;				
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.getProjectList = mockProjectList;
			projectlist.loadPage(false);
			setTimeout(function() {
				start();
				var getval = $(commonVariables.contentPlaceholder).find("select#updateType_294187d7-f75a-4adc-bb25-ce9465e0e82f option[value=svn]").text();				
				equal("SVN", getval, "Project List SVNPopup popup rendered");
			}, 1500);
		}); 
		
		asyncTest("Existing pdfs Service Test", function() {
			
			mockPdfList = mockFunction();
            when(mockPdfList)().thenReturn(true);
            when(mockPdfList)(anything()).then(function(arg) {
				
				var mockPdfListresponse = [{"time":"Jun 07 2013 18.59","type":"detail","fileName":"Copy (2) of ipn_nat_eshop-iphonenative_detail_Jun 07 2013 18.59.pdf"},{"time":"Jun 07 2013 18.59","type":"detail","fileName":"Copy (3) of ipn_nat_eshop-iphonenative_detail_Jun 07 2013 18.59.pdf"},{"time":"Jun 07 2013 18.59","type":"detail","fileName":"Copy (4) of ipn_nat_eshop-iphonenative_detail_Jun 07 2013 18.59.pdf"}];
				
				var collection = {};
				collection.getProjectList = mockPdfListresponse;
				projectlist.renderTemplate(collection, commonVariables.contentPlaceholder);
			});
			
			projectlist.projectslistListener.projectListAPI.projectlist = mockPdfList;
			
			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("thead[name=pdfHeader]").attr('show'), "true", "Pdf List Service Tested");
			}, 1500);
		});
		
		asyncTest("Import Application Service Test", function() {
			
			/* var header = {"contentType":"application/json", "requestMethod":"POST", "dataType":"json", "requestPostBody":"{}", "webserviceurl":"framework/login"}; */
			
			mockImportApp = mockFunction();
            when(mockImportApp)(anything()).then(function(arg) {
				
				var mockImportAppresponse = {"message":"Project imported successfully","exception":null,"data":null,"response":null};
				
				var collection = {};
				collection.addImportEvent = mockImportAppresponse;
				projectlist.renderTemplate(collection, commonVariables.contentPlaceholder);
			});
			
                     
			projectlist.projectslistListener.projectListAPI.projectlist = mockImportApp;
			
			mockProjectList = mockFunction();
			when(mockProjectList)(anything()).then(function(arg) {
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				var projectlist = {};
				projectlist.projectlist = projectListresponse.data;
				projectlist.renderTemplate(projectlist, commonVariables.contentPlaceholder);
			}); 
			projectlist.projectslistListener.projectListAPI.projectslist = mockProjectList;
			
			setTimeout(function() {
				start();
				var output = $(commonVariables.contentPlaceholder).find("#projectlist").find("tbody[class=scrollContent]").find("tr:last-child").attr("class");
				equal(output, "wordpress-WordPress", "Import Application Service Tested");
			}, 1500);
		});
		
	}};
});