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
				$("input[name='save']").next().click();
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
				$("input[name='save']").click();
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
				
				require(["navigation/navigation"], function(){
						commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		
					
				commonVariables.navListener.onMytabEvent(commonVariables.jobTemplates);
					
				$("input[name='save']").click();
				setTimeout(function() {
					start();
				
					var createdTr = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div[active=true]").find("table[id=jobTemplateList]").find("tr:last").find("td:first").text();
					equal(createdTr, "Test", "jobTemplates - save with values Event Tested");
					require(["continuousDeliveryViewTest"], function(continuousDeliveryViewTest){
						continuousDeliveryViewTest.runTests();
					});
				}, 3000);
			});
		} 
		
		
	};
});


