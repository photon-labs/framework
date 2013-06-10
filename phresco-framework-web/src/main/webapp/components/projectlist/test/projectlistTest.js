
define(["projectlist/projectList"], function(ProjectList) {

	return { runTests: function (configData) {
		module("projectlist.js;projectlist");
		
		var projectlist = new ProjectList();
		asyncTest("ProjectList Service Test", function() {
			
			/* var header = {"contentType":"application/json", "requestMethod":"POST", "dataType":"json", "requestPostBody":"{}", "webserviceurl":"framework/login"}; */
			
			mockProjectList = mockFunction();
            when(mockProjectList)().thenReturn(true);
            when(mockProjectList)(anything()).then(function(arg) {
				
				var projectListresponse = {"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]};
				
				projectlist.data = projectListresponse;
				
				projectlist.renderTemplate(projectlist.data, commonVariables.contentPlaceholder)
                projectlist.render(commonVariables.contentPlaceholder);
				/* var renderFunction = $.proxy(projectlist.renderTemplate, projectlist);
                renderFunction(projectListresponse, commonVariables.contentPlaceholder);  */
			});
			
			projectlist.projectslistListener.projectListAPI.projectslist = mockProjectList;
			
			projectlist.projectslistListener.getProjectList();
			
			//projectlist.render(commonVariables.contentPlaceholder);
			
			setTimeout(function() {
				start();
				console.info("html", $(commonVariables.contentPlaceholder).html());
				equal("", "", "Project List Service Tested");
			}, 1500);
		});
	}};
});