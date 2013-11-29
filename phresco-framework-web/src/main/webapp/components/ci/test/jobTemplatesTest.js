define(["ci/jobTemplates"], function(JobTemplates) {

	return { 

		runTests: function (configData) {
			module("jobTemplates.js");	
			var jobTemplates = new JobTemplates(), templateData = {}, self = this, jobTempList;
			asyncTest("List Job Templates - UI Test", function() {
				var ciAPI = commonVariables.api;
				$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
				commonVariables.projectLevel = true;
				self.jobTempList = $.mockjax({	
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"responseCode":null,"data":[{"name":"code","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":null});
					}
				});
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.jobTemplates);

				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Job Templates", "List Job Templates - UI Tested");
					self.openCreatePopupTest(jobTemplates);
				}, 2500);
			});					
		},


		openCreatePopupTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - open Create Popup Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.projectlistContext + "/appinfos?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0","modules":[{"code":"Common","techInfo":{"version":"1.0","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"OSGI Bundle","id":"54e8eb2c-f320-4f31-a3ac-762ae2cdcfd4","displayName":null,"status":null,"description":null,"creationDate":1379937498000,"helpText":null,"system":false},"rootModule":null,"modified":false,"dependentModules":null,"dependentApps":null,"name":null,"id":"9d528ad6-be05-441b-8e69-f647fe0d1709","displayName":null,"status":null,"description":null,"creationDate":1380870242000,"helpText":null,"system":false},{"code":"Log4j","techInfo":{"version":"1.0","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"OSGI Fragment","id":"b5851876-f4f0-4573-9344-7969ad759448","displayName":null,"status":null,"description":null,"creationDate":1379937498000,"helpText":null,"system":false},"rootModule":null,"modified":false,"dependentModules":null,"dependentApps":null,"name":null,"id":"d00a01a7-f1bf-4114-bba9-51d711cbdc7f","displayName":null,"status":null,"description":null,"creationDate":1380870242000,"helpText":null,"system":false},{"code":"PresentationService","techInfo":{"version":"1.0","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"Jersey-OSGI","id":"8892cc43-c2ab-46a4-be5d-d4752d6dd418","displayName":null,"status":null,"description":null,"creationDate":1379937498000,"helpText":null,"system":false},"rootModule":null,"modified":false,"dependentModules":null,"dependentApps":null,"name":null,"id":"5a11b3bf-7139-4e24-9a4e-19b9a1168744","displayName":null,"status":null,"description":null,"creationDate":1380870242000,"helpText":null,"system":false}],"pomFile":null,"code":"TodayProject","appDirName":"TodayProject","techInfo":{"version":"0.10.9","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"status":null,"description":null,"creationDate":1376927360000,"helpText":null,"system":false},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"TodayProject","id":"cf25973d-9e0e-4455-9ed3-af8371b3b3db","displayName":null,"status":null,"description":null,"creationDate":1376927360000,"helpText":null,"system":false}],"status":"success"});
					}
				});

				$("input[name='jobTemplatePopup']").click();
				setTimeout(function() {
					start();
					var jobTemplatePopup = $(commonVariables.contentPlaceholder).find("#jobTemplatePopup").css('display');
					equal(jobTemplatePopup, "block", "jobTemplates - open Create Popup Tested");					
					self.runChangeEventTest(jobTemplates);
				}, 2500);
			});
		},

		runChangeEventTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - change Operation Event Test", function() {
				$("select[name='type']").val('codeValidation');
				$("select[name='type']").change();
				$("select[name='type']").val('build');
				$("select[name='type']").change();

				setTimeout(function() {
					start();
					var codeValidate = $(commonVariables.contentPlaceholder).find("input[name=enableUploadSettings]").attr('disabled');
					var build = $(commonVariables.contentPlaceholder).find("input[name=enableUploadSettings]").attr('disabled');
					equal(codeValidate, undefined, "jobTemplates - change Operation Tested");
					equal(build, undefined, "jobTemplates - change Operation Event Tested");
					self.repoCheckboxTest(jobTemplates);
				}, 2500);
			});
		},

		repoCheckboxTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - select/deselect Repo Event Test", function() {
				$("input[name=enableRepo]").prop('checked', true);
				$("input[name=enableRepo]").click();

				setTimeout(function() {
					start();
					var repoType = $(commonVariables.contentPlaceholder).find("select[class=selectpicker][name=repoTypes]").next().css('display');				
					equal(repoType, 'block', "jobTemplates - select/deselect Repo Event Tested");
					self.errorNotificationTest(jobTemplates);
				}, 3000);
			});
		},

		errorNotificationTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - save without values Event Test", function() {
				$('input[name=name]').val('');
				$("select[name=appIds]").selectpicker('val', '');
				$("select[name=type]").selectpicker('val', 'build');
				$("select[name=features]").selectpicker('val', ['enableRepo','enableSheduler','enableEmailSettings','enableUploadSettings']);
				$('select[name=uploadTypes]').selectpicker('val', ['collabnet']);
				$("input[name='save']").eq(0).click();
				setTimeout(function() {
					start();
					var name =  $(commonVariables.contentPlaceholder).find('input[name=name]').attr('class');
					var span = $(commonVariables.contentPlaceholder).find('#errMsg').attr('class');
					var appSelect = $(commonVariables.contentPlaceholder).find('select[name=appIds]').next().find('button').attr('class');
					var uploadSelect = $(commonVariables.contentPlaceholder).find('select[name=uploadTypes]').next().find('button').attr('class');

					equal(name, "errormessage", "jobTemplates - save without values Event Tested");	
					equal(span, "flt_left errormessage", "jobTemplates - save without values Event Tested");	
					equal(appSelect, "btn-default dropdown-toggle btn btn-danger", "jobTemplates - save without values Event Tested");	

					self.createJobTemplateTest(jobTemplates);
				}, 2500);
			});
		},

		createJobTemplateTest : function(jobTemplates) {
			var self=this;
			$.mockjaxClear(self.jobTempList);
			var ciAPI = commonVariables.api;
			$('.hProjectId').val('3b33c6c3-2491-4870-b0a9-693817b5b9f8');
			commonVariables.projectLevel = true;
			asyncTest("jobTemplates - save with values Event Test", function() {
				$("select[name=type]").selectpicker('val', 'codeValidation');
				$("select[name=features]").selectpicker('val', ['enableRepo', 'enableSheduler', 'enableEmailSettings']);
				$('input[name=name]').val('newTemplate');
				$("select[name=appIds]").selectpicker('val', ['TodayProject']);
				var name = $('input[name="name"]').val();
				var oldname = $('[name="oldname"]').val();

				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=newTemplate",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"","exception":null,"data":true,"response":null});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'POST',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template added successfully","exception":null,"responseCode":null,"data":{"name":"newTemplate","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},"status":null});	
					}
				}); 
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"responseCode":null,"data":[{"name":"code","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"newTemplate","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":null});	
					}
				});

				$("input[name='save']").click();
				setTimeout(function() {
					start();
					var createdTr = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find("table[id=jobTemplateList]").find("tr:last").find("td:first").text();
					equal(true, createdTr.indexOf('pdf') !== -1, "jobTemplates - save with values Event Tested");
					self.editJobTemplateTest(jobTemplates);
				}, 3000);
			});
		},

		editJobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - edit Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.projectlistContext + 
					"/appinfos?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0","modules":null,"pomFile":null,"code":"TodayProject","appDirName":"TodayProject","techInfo":{"version":"0.10.9","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"status":null,"description":null,"creationDate":1376927360000,"helpText":null,"system":false},"functionalFramework":null,"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"TodayProject","id":"cf25973d-9e0e-4455-9ed3-af8371b3b3db","displayName":null,"status":null,"description":null,"creationDate":1376927360000,"helpText":null,"system":false}],"status":"success"});	
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/validateApp?projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&customerId=photon&name=load&appName=TodayProject",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800015","data":"TodayProject#SEP#true","status":"success"});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/load?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template retrived successfully","exception":null,"responseCode":null,"data":{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},"status":null});	
					}
				});
				$("a[name=editpopup][id=edit_load]").click();
				setTimeout(function() {
					start();
					var display = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('div[id=jobTemplatePopup]').css('display');
					equal("block", display, "jobTemplates - edit Event Test");
					self.updateJobTemplateTest(jobTemplates);
				}, 3000);
			});
		},


		updateJobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - update Event Test", function() {
				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=load&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=load",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"","exception":null,"responseCode":null,"data":true,"status":null});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=load&customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8", 
					type:'PUT',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template updated successfully","exception":null,"responseCode":null,"data":true,"status":null});	
					}
				}); 

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"responseCode":null,"data":[{"name":"code","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"newTemplate","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":null});	
					}
				});
				$(commonVariables.contentPlaceholder).find('input[name=save]').prop('name', 'update');
				$('input[name=update]').click();

				setTimeout(function() {
					start();
					var createdTr = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find("table[id=jobTemplateList]").find("tr:last").find("td:first").text();
					equal(true, createdTr.indexOf('newTemplate') !== -1, "jobTemplates - save with values Event Tested");
					self.deleteJobTemplatesTest(jobTemplates);
				}, 2500);
			});
		},


		deleteJobTemplatesTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - Delete Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=newTemplate",
					type:'DELETE',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template deleted successfully","exception":null,"responseCode":null,"data":null,"status":null});	
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"responseCode":null,"data":[{"name":"code","type":"codeValidation","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":false,"uploadTypes":[]},{"name":"build","type":"build","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"deploy","type":"deploy","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"unit","type":"unittest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"functional","type":"functionalTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"performance","type":"performanceTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"pdf","type":"pdfReport","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"load","type":"loadTest","projectId":"3b33c6c3-2491-4870-b0a9-693817b5b9f8","customerId":"photon","appIds":["TodayProject"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":null});	
					}
				});
				$('#delete_newTemplate').next().find('input[name=delete]').click();
				setTimeout(function() {
					start();
					equal(1, 1, "jobTemplates - Delete Event Tested");	
					require(["continuousDeliveryConfigureTest"], function(continuousDeliveryConfigureTest){
						continuousDeliveryConfigureTest.runTests();
					});
				}, 2500);
			});
		},


	};	
});

