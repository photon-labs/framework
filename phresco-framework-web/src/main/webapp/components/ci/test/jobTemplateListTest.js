define(["ci/jobTemplates"], function(JobTemplates) {

	return { runTests: function (configData) {
		module("jobTemplate.js;JobTemplate");

		var jobtemplate = new JobTemplates();
		asyncTest("jobTemplate List Test", function() {
			mockjobTemplateList = mockFunction();	
			when(mockjobTemplateList)(anything()).then(function(arg) {
				var templateData = {};				
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":[{"name":"asd","type":"Build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Confluence"]},{"name":"Test","type":"Build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":true,"uploadTypes":["Collabnet","Confluence"]}],"response":null};	
				templateData.jobTemplates = jobTemplateListresponse.data;				
				jobtemplate.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobtemplate.ciListener.ciAPI.ci = mockjobTemplateList;
			jobtemplate.loadPageTest();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find("#edit_Test").attr('value'),"Test", "jobTemplate List Service Tested");
			}, 1500);
		});

		asyncTest("jobTemplate create Test", function() {
			mockaddjobTemplate = mockFunction();	

			when(mockaddjobTemplate)(anything()).then(function(arg) {
				var templateData = {};
				var jobTemplateListresponse = {"message":"Job Templates listed successfully","exception":null,"data":[{"name":"test","type":"Code Validation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":null}],"response":null}; 	
				templateData.jobTemplates = jobTemplateListresponse.data;
				jobtemplate.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobtemplate.ciListener.ciAPI.ci = mockaddjobTemplate;

			jobtemplate.loadPageTest();
			

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
				jobtemplate.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobtemplate.ciListener.ciAPI.ci = mockupdatejobTemplate;

			jobtemplate.loadPageTest();

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
				jobtemplate.renderTemplate(templateData, commonVariables.contentPlaceholder);
			});

			jobtemplate.ciListener.ciAPI.ci = mockupdatejobTemplate;

			jobtemplate.loadPageTest();

			setTimeout(function() {
				start();
				equal($(commonVariables.contentPlaceholder).find(".alert-block").text(),"Job Templates not available", "jobTemplate delete Service Tested");
			}, 1500);
		});

	}};
});

