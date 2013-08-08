define(["ci/jobTemplates"], function(JobTemplates) {

	return { 

		runTests: function (configData) {

			module("jobTemplates.js");	
			var jobTemplates = new JobTemplates(), templateData = {}, self = this, jobTempList;
			asyncTest("List Job Templates - UI Test", function() {
				var ciAPI = commonVariables.api;
				ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
				ciAPI.localVal.setSession("appDirName" , "");
				self.jobTempList = $.mockjax({						
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"data":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"response":null});
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
					url: commonVariables.webserviceurl + commonVariables.projectlistContext + 
					"/appinfos?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021",							
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Application infos returned Successfully","exception":null,"data":[{"version":"1.0","pomFile":null,"code":"1","appDirName":"1","techInfo":{"version":"1.7","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"Java Standalone","id":"tech-java-standalone","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"customerIds":null,"used":false,"name":"1","id":"efc37f82-3611-4be2-816b-38b2c7331a60","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},{"version":"1.0","pomFile":null,"code":"2","appDirName":"2","techInfo":{"version":"1.7","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"J2EE","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"customerIds":null,"used":false,"name":"2","id":"0ef72126-263c-4330-ac63-2c11e40080ae","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},{"version":"1.0","pomFile":null,"code":"3","appDirName":"3","techInfo":{"version":"0.10.9","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"Node JS","id":"tech-nodejs-webservice","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"customerIds":null,"used":false,"name":"3","id":"0b1f55a8-4f73-4759-836f-d7cc32e83e07","displayName":null,"status":null,"description":null,"creationDate":1371734906000,"helpText":null,"system":false},{"version":"1.2","pomFile":null,"code":"SVNCHECK-wordpress","appDirName":"SVNCHECK-wordpress","techInfo":{"version":"3.4.2","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":null,"id":"tech-wordpress","displayName":null,"status":null,"description":null,"creationDate":1364980309000,"helpText":null,"system":false},"selectedServers":null,"selectedDatabases":null,"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"customerIds":null,"used":false,"name":"SVNCHECK-wordpress","id":"bbb7805f-00ce-4350-8b86-81a5d89b2fb5","displayName":null,"status":null,"description":null,"creationDate":1364980309000,"helpText":null,"system":false}],"response":null});
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
					notEqual(repoType, 'block', "jobTemplates - select/deselect Repo Event Tested");
					self.runCloseButtonTest(jobTemplates);
				}, 3000);
			});
		},

		runCloseButtonTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - Close Button Event Test", function() {
				$("input[name='save']").eq(0).next().click();
				setTimeout(function() {
					start();
					var jobTemplatePopup = $(commonVariables.contentPlaceholder).find("#jobTemplatePopup").css('display');
					equal(jobTemplatePopup, "none", "jobTemplates - open Create Popup Tested");					
					self.errorNotificationTest(jobTemplates);
				}, 2500);
			});
		},

		errorNotificationTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - save without values Event Test", function() {
				$('input[name=name]').val('');
				$("select[name=appIds]").selectpicker('val', '');
				$("select[name=type]").selectpicker('val', 'build');
				$("input[name=enableUploadSettings]").prop('checked', true);
				$("input[name='save']").eq(0).click();
				setTimeout(function() {
					start();
					var name =  $(commonVariables.contentPlaceholder).find('input[name=name]').attr('class');
					var span = $(commonVariables.contentPlaceholder).find('#errMsg').attr('class');
					var appSelect = $(commonVariables.contentPlaceholder).find('select[name=appIds]').next().find('button').attr('class');
					var uploadSelect = $(commonVariables.contentPlaceholder).find('select[name=uploadTypes]').next().find('button').attr('class');

					equal(name, "errormessage", "jobTemplates - save without values Event Tested");	
					equal(span, "flt_left errormessage", "jobTemplates - save without values Event Tested");	
					equal(appSelect, "dropdown-toggle btn btn-danger", "jobTemplates - save without values Event Tested");	
					equal(uploadSelect, "dropdown-toggle btn btn-danger", "jobTemplates - save without values Event Tested");			

					self.createJobTemplateTest(jobTemplates);
				}, 2500);
			});
		},

		createJobTemplateTest : function(jobTemplates) {
			var self=this;
			$.mockjaxClear(self.jobTempList);
			var ciAPI = commonVariables.api;
			ciAPI.localVal.setSession("projectId" , "a4329529-3c9d-476d-a310-e0cf4436e021");
			ciAPI.localVal.setSession("appDirName" , "");
			asyncTest("jobTemplates - save with values Event Test", function() {
				$('input[name=name]').val('Test');
				$("select[name=type]").selectpicker('val', 'codeValidation');
				$("select[name=appIds]").selectpicker('val', ['SVNCHECK-wordpress']);				
				$("input[name=enableRepo]").prop('checked', true);
				var name = $('input[name="name"]').val();
				var oldname = $('[name="oldname"]').val();

				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&name=Test",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"","exception":null,"data":true,"response":null});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates,
					type:'POST',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template added successfully","exception":null,"responseCode":null,"data":{"name":"Bild","type":"build","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},"status":null});	
					}
				}); 

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"data":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"response":null});	
					}
				});

				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.jobTemplates);

				$("input[name='save']").eq(0).click();
				setTimeout(function() {
					start();
					var createdTr = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div[active=true]").find("table[id=jobTemplateList]").find("tr:last").find("td:first").text();
					equal(createdTr, "Test", "jobTemplates - save with values Event Tested");
					self.editJobTemplateTest(jobTemplates);
				}, 3000);
			});
		},

		editJobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - edit Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates,
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0","modules":null,"pomFile":null,"code":"htm","appDirName":"htm","techInfo":{"version":"2.0.2","appTypeId":"web-layer","techGroupId":"HTML5","techVersions":null,"customerIds":null,"used":false,"name":"JQuery Mobile Widget","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1375339017000,"helpText":null,"system":false},"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"d21ae84a-4560-47be-bac7-4e60975cb2ff","displayName":null,"status":null,"description":null,"creationDate":1375339257000,"helpText":null,"system":false}],"selectedDatabases":[{"artifactGroupId":"downloads_db2","artifactInfoIds":["3e49bbaf-79f8-41d0-af71-190e094a1b06"],"name":null,"id":"7eacae6b-3851-4583-b61c-af33485c1f72","displayName":null,"status":null,"description":null,"creationDate":1375339257000,"helpText":null,"system":false}],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"functionalFrameworkInfo":null,"customerIds":null,"used":false,"name":"htm","id":"698137b4-acac-4fcd-841e-a521c4dd3ec4","displayName":null,"status":null,"description":"","creationDate":1375339257000,"helpText":null,"system":false},{"version":"1.0","modules":null,"pomFile":null,"code":"J2ee","appDirName":"J2ee","techInfo":{"version":"1.7","appTypeId":"app-layer","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"J2EE","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1375339017000,"helpText":null,"system":false},"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"9b786236-07f7-4b5a-877b-11e26757f16d","displayName":null,"status":null,"description":null,"creationDate":1375339413000,"helpText":null,"system":false}],"selectedDatabases":[{"artifactGroupId":"downloads_db2","artifactInfoIds":["3e49bbaf-79f8-41d0-af71-190e094a1b06"],"name":null,"id":"de0c7913-75e0-4d13-a1aa-28611902d826","displayName":null,"status":null,"description":null,"creationDate":1375339413000,"helpText":null,"system":false}],"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedWebservices":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"dependentModules":null,"functionalFrameworkInfo":null,"customerIds":null,"used":false,"name":"J2ee","id":"8411b808-abb2-4fd6-ba3a-7afff9b94f62","displayName":null,"status":null,"description":"","creationDate":1375339413000,"helpText":null,"system":false}],"status":"success"});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/bld?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template retrived successfully","exception":null,"responseCode":null,"data":{"name":"codeValidate","type":"codeValidation","projectId":"28352211-a3d6-4ff9-8b55-2734942993b0","customerId":"photon","appIds":["htm","J2ee"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},"status":null});	
					}
				});

				$("a[name=editpopup]").eq(0).click();
				setTimeout(function() {
					start();
					equal(1, 1, "jobTemplates - edit Event Test");
					self.updateJobTemplateTest(jobTemplates);
				}, 3000);
			});
		},


		updateJobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - update Event Test", function() {
				$('input[name=name]').val('Test');
				$("input[name=oldname]").val('oldName');
				$("select[name=type]").selectpicker('val', 'codeValidation');
				$("select[name=appIds]").selectpicker('val', ['SVNCHECK-wordpress']);				
				$("input[name=enableRepo]").prop('checked', true);
				var name = $('input[name="name"]').val();
				var oldname = $('[name="oldname"]').val();

				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=oldName&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&name=Test",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"","exception":null,"data":true,"response":null});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=oldName&projectId=a4329529-3c9d-476d-a310-e0cf4436e021", 
					type:'PUT',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template added successfully","exception":null,"data":{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},"response":null});	
					}
				}); 

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Templates listed successfully","exception":null,"data":[{"name":"bld","type":"build","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["1","2","3","SVNCHECK-wordpress"],"enableRepo":false,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"code","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["2","3","SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"Test","type":"codeValidation","projectId":"a4329529-3c9d-476d-a310-e0cf4436e021","customerId":"photon","appIds":["SVNCHECK-wordpress"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"response":null});	
					}
				});

				$(commonVariables.contentPlaceholder).find('input[name=save]').prop('name', 'update');
				$('input[name=update]').eq(0).click();

				setTimeout(function() {
					start();
					equal(1, 1, "jobTemplates - update Event Tested");
					self.deleteJobTemplatesTest(jobTemplates);
				}, 2500);
			});
		},


		deleteJobTemplatesTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - Delete Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=a4329529-3c9d-476d-a310-e0cf4436e021&name=bld",
					type:'DELETE',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Job Template deleted successfully","exception":null,"responseCode":null,"data":null,"status":null});	
					}
				});

				$("input[name=delete]").eq(0).click();
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

