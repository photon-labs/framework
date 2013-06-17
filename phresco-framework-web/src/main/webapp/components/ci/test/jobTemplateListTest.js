define(["ci/jobTemplates"], function(JobTemplateList) {

	return { runTests: function (configData) {
		module("jobTemplate.js;JobTemplate");

		var jobTemplateList = new JobTemplateList();

		asyncTest("jobTemplate List Test", function() {
			mockjobTemplateList = mockFunction();	

			when(mockjobTemplateList)(anything()).then(function(arg) {
				var templateData = {};
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":[{"name":"asd1","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null},{"name":"asd2","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null},{"name":"asd3","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null},{"name":"asd4","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null},{"name":"asd5","type":"Build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null},{"name":"test","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"git","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":null},{"name":"test100","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":null}],"response":null};	
				templateData.jobTemplates = jobTemplateListresponse.data;

				jobTemplateList.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobTemplateList.ciListener.ciAPI.ci = mockjobTemplateList;

			jobTemplateList.loadPage();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#edit_asd1").attr('value'),"asd1", "jobTemplate List Service Tested");
			}, 1500);
		});

		asyncTest("jobTemplate create Test", function() {
			mockaddjobTemplate = mockFunction();	

			when(mockaddjobTemplate)(anything()).then(function(arg) {
				var templateData = {};
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":[{"name":"test","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":null}],"response":null}; 	
				templateData.jobTemplates = jobTemplateListresponse.data;

				jobTemplateList.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobTemplateList.ciListener.ciAPI.ci = mockaddjobTemplate;

			jobTemplateList.loadPage();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#edit_test").attr('value'),"test", "jobTemplate Add Service Tested");
			}, 1500);
		});

		asyncTest("jobTemplate update Test", function() {
			mockupdatejobTemplate = mockFunction();	

			when(mockupdatejobTemplate)(anything()).then(function(arg) {
				var templateData = {};
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":[{"name":"testRenamed","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":null}],"response":null}; 
				templateData.jobTemplates = jobTemplateListresponse.data;

				jobTemplateList.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobTemplateList.ciListener.ciAPI.ci = mockupdatejobTemplate;

			jobTemplateList.loadPage();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#edit_testRenamed").attr('value'),"testRenamed", "jobTemplate update Service Tested");
			}, 1500);
		});

		asyncTest("jobTemplate delete Test", function() {
			mockupdatejobTemplate = mockFunction();	

			when(mockupdatejobTemplate)(anything()).then(function(arg) {
				var templateData = {};
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":null,"response":null}; 
				templateData.jobTemplates = jobTemplateListresponse.data;

				jobTemplateList.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobTemplateList.ciListener.ciAPI.ci = mockupdatejobTemplate;

			jobTemplateList.loadPage();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".alert-block").text(),"Job Templates not available", "jobTemplate delete Service Tested");
			}, 1500);
		});

	}};
});

