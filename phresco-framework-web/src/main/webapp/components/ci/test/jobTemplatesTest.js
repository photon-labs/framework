define(["ci/jobTemplates"], function(JobTemplates) {

	return { 

		runTests: function (configData) {
			$(commonVariables.contentPlaceholder).html('');
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
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});
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
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"DrupalSingle","appDirName":"DrupalSingle","techInfo":{"version":"7.8","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"customerIds":null,"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["63e031f4-808f-4a5a-9354-f9aedc70bd0b","2e35e54c-894c-4a8f-adfa-adc92671648b","9a54f56b-5161-490f-8985-514f7946632a","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","ea7cb09e-6dc1-49e1-b5ee-d148362dec11"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"63e031f4-808f-4a5a-9354-f9aedc70bd0b":"","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce":"","ea7cb09e-6dc1-49e1-b5ee-d148362dec11":"","9a54f56b-5161-490f-8985-514f7946632a":"","2e35e54c-894c-4a8f-adfa-adc92671648b":""},"phrescoPomFile":null,"selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"DrupalSingle","id":"131eb7e8-a5c0-47e8-9d11-9ce3d99ae6f1","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},{"version":"3.2.0-SNAPSHOT","modules":[{"code":"java","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"43985b8f-cc7a-466d-b3c2-58c340d77d3f","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"webYui","techInfo":{"version":"3.9.1","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"07070bb2-f312-40c8-be51-23d9a1f285d2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"web","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"ca4ba1bd-9003-4571-a290-414018bdaa7c","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"javaRoot","appDirName":"javaRoot","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2":"","cd3d6838-1a7e-4b9a-b989-478fe1a8c774":"","e75b7088-8d9f-4b00-8077-9e76b5c478fe":"","283f9027-61ca-4b47-8a6b-3f2fba059aa7":"","06339a87-8c20-4da6-904a-7f3b0a80a165":"","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"","b9a7072c-720d-4b6e-af83-57c233abd7e6":"","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c":"","ce0bf34e-aea1-456a-a2b5-96492e46d469":"","d63b468e-8fa1-4690-afeb-852e80ea450d":"","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"javaRoot","id":"2e43c65d-38e0-4890-813c-eb0f2a425ef2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"JqueryMobile","appDirName":"JqueryMobile","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1354009203000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":"","selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"93829766-1c48-4431-b5d8-90fea4f2e2e3","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false}],"selectedDatabases":[],"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"compile","deda98f8-c350-47f1-8b22-a0816a695127":"","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0":"","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"compile","c7008489-b264-442c-ad8c-2c422284d171":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":["restjson"],"dependentModules":null,"functionalFrameworkInfo":{"version":"2.35","frameworkIds":"e060a07a-c845-47eb-a82d-da85df167e63","iframeUrl":null,"frameworkGroupId":"a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","functionalFrameworkIds":null,"name":null,"id":"38563eac-6efc-476a-b378-365b0aa204f6","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false},"selectedJSLibs":["c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127"],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"created":true,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"JqueryMobile","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"status":null,"description":"","creationDate":1388488983000,"helpText":null,"system":false}],"status":"success"});
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
					var disabled = $(commonVariables.contentPlaceholder).find("#upload").attr('disabled');
					equal(disabled, undefined, "jobTemplates - change Operation Tested");
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
				$("select[name=features]").selectpicker('val', ['enableRepo', 'enableSheduler', 'enableEmailSettings', 'enableUploadSettings']);
				$('input[name=name]').val('sampleTest2');
				$("select[name=appIds]").selectpicker('val', ['DrupalSingle']);
				$("select[name=uploadTypes]").selectpicker('val', ['Collabnet']);
				var name = $('input[name="name"]').val();
				var oldname = $('[name="oldname"]').val();

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'POST',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800016","data":{"name":"sampleTemplate2","type":"build","module":null,"appIds":[""],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},"status":"success"});
					}
				}); 
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"sampleTemplate2","type":"build","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});
					}
				});

				$("input[name='save']").click();
				$(commonVariables.contentPlaceholder).html('');
				setTimeout(function() {
					start();
					var createdTr = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find("table[id=jobTemplateList] tr").eq(2).find('td').eq(0).text();
					equal(createdTr, "sampleTemplate2", "jobTemplates - save with values Event Tested");
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
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"DrupalSingle","appDirName":"DrupalSingle","techInfo":{"version":"7.8","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"customerIds":null,"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["63e031f4-808f-4a5a-9354-f9aedc70bd0b","2e35e54c-894c-4a8f-adfa-adc92671648b","9a54f56b-5161-490f-8985-514f7946632a","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","ea7cb09e-6dc1-49e1-b5ee-d148362dec11"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"63e031f4-808f-4a5a-9354-f9aedc70bd0b":"","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce":"","ea7cb09e-6dc1-49e1-b5ee-d148362dec11":"","9a54f56b-5161-490f-8985-514f7946632a":"","2e35e54c-894c-4a8f-adfa-adc92671648b":""},"phrescoPomFile":null,"selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"DrupalSingle","id":"131eb7e8-a5c0-47e8-9d11-9ce3d99ae6f1","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},{"version":"3.2.0-SNAPSHOT","modules":[{"code":"java","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"43985b8f-cc7a-466d-b3c2-58c340d77d3f","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"webYui","techInfo":{"version":"3.9.1","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"07070bb2-f312-40c8-be51-23d9a1f285d2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"web","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"ca4ba1bd-9003-4571-a290-414018bdaa7c","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"javaRoot","appDirName":"javaRoot","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2":"","cd3d6838-1a7e-4b9a-b989-478fe1a8c774":"","e75b7088-8d9f-4b00-8077-9e76b5c478fe":"","283f9027-61ca-4b47-8a6b-3f2fba059aa7":"","06339a87-8c20-4da6-904a-7f3b0a80a165":"","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"","b9a7072c-720d-4b6e-af83-57c233abd7e6":"","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c":"","ce0bf34e-aea1-456a-a2b5-96492e46d469":"","d63b468e-8fa1-4690-afeb-852e80ea450d":"","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"javaRoot","id":"2e43c65d-38e0-4890-813c-eb0f2a425ef2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"JqueryMobile","appDirName":"JqueryMobile","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1354009203000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":"","selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"93829766-1c48-4431-b5d8-90fea4f2e2e3","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false}],"selectedDatabases":[],"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"compile","deda98f8-c350-47f1-8b22-a0816a695127":"","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0":"","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"compile","c7008489-b264-442c-ad8c-2c422284d171":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":["restjson"],"dependentModules":null,"functionalFrameworkInfo":{"version":"2.35","frameworkIds":"e060a07a-c845-47eb-a82d-da85df167e63","iframeUrl":null,"frameworkGroupId":"a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","functionalFrameworkIds":null,"name":null,"id":"38563eac-6efc-476a-b378-365b0aa204f6","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false},"selectedJSLibs":["c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127"],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"created":true,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"JqueryMobile","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"status":null,"description":"","creationDate":1388488983000,"helpText":null,"system":false}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/validateApp?projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&customerId=photon&name=sampleTemplate2&appName=DrupalSingle",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800015","data":"DrupalSingle#SEP#true","status":"success"});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/sampleTemplate2?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800014","data":{"name":"sampleTemplate2","type":"build","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},"status":"success"});
					}
				});
				$("a[name=editpopup][id=edit_sampleTemplate2]").click();
				setTimeout(function() {
					start();
					var display = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('div[id=jobTemplatePopup]').css('display');
					equal(display, "block", "jobTemplates - edit Event Test");
					self.updateJobTemplateTest(jobTemplates);
				}, 3000);
			});
		},


		updateJobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - update Event Test", function() {
				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=sampleTemplate2&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=sampleTemplate2",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800015","data":true,"status":"success"});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=sampleTemplate2&customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8", 
					type:'PUT',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800017","data":true,"status":"success"});	
					}
				}); 

				self.jobTempList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"sampleTemplate2","type":"build","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});	
					}
				});
				$(commonVariables.contentPlaceholder).find('input[name=save]').prop('name', 'update');
				$('input[name=update]').click();
				setTimeout(function() {
					start();
					var updatedTr =  $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find("table[id=jobTemplateList] tr").eq(2).find('td').eq(0).text();
					equal(updatedTr, "sampleTemplate2", "jobTemplates - save with values Event Tested");
					self.edit2JobTemplateTest(jobTemplates);
				}, 2500);
			});
		},
		
		edit2JobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - edit Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.projectlistContext + "/appinfos?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200002","data":[{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"DrupalSingle","appDirName":"DrupalSingle","techInfo":{"version":"7.8","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"PHP","techVersions":null,"customerIds":null,"used":false,"name":"php-Drupal7","id":"tech-phpdru7","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["63e031f4-808f-4a5a-9354-f9aedc70bd0b","2e35e54c-894c-4a8f-adfa-adc92671648b","9a54f56b-5161-490f-8985-514f7946632a","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce","ea7cb09e-6dc1-49e1-b5ee-d148362dec11"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"63e031f4-808f-4a5a-9354-f9aedc70bd0b":"","55f384ef-3718-4f12-b4ee-4e41ff9ef6ce":"","ea7cb09e-6dc1-49e1-b5ee-d148362dec11":"","9a54f56b-5161-490f-8985-514f7946632a":"","2e35e54c-894c-4a8f-adfa-adc92671648b":""},"phrescoPomFile":null,"selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"DrupalSingle","id":"131eb7e8-a5c0-47e8-9d11-9ce3d99ae6f1","displayName":null,"status":null,"description":null,"creationDate":1389283462000,"helpText":null,"system":false},{"version":"3.2.0-SNAPSHOT","modules":[{"code":"java","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"43985b8f-cc7a-466d-b3c2-58c340d77d3f","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"webYui","techInfo":{"version":"3.9.1","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web-yui","id":"tech-html5","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"07070bb2-f312-40c8-be51-23d9a1f285d2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"code":"web","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":null,"techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"rootModule":"javaRoot","modified":false,"dependentModules":[],"dependentApps":null,"name":null,"id":"ca4ba1bd-9003-4571-a290-414018bdaa7c","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false}],"pomFile":"pom.xml","code":"javaRoot","appDirName":"javaRoot","techInfo":{"version":"1.7","multiModule":false,"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"Java","techVersions":null,"customerIds":null,"used":false,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":null,"selectedModules":["e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3","e75b7088-8d9f-4b00-8077-9e76b5c478fe","283f9027-61ca-4b47-8a6b-3f2fba059aa7","cd3d6838-1a7e-4b9a-b989-478fe1a8c774","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","d63b468e-8fa1-4690-afeb-852e80ea450d","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a","2d41a182-85f1-42a3-a67c-a0836792ba02","06339a87-8c20-4da6-904a-7f3b0a80a165","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"","d90eadeb-dc49-46a5-98a7-7ac2f9c5d2c2":"","cd3d6838-1a7e-4b9a-b989-478fe1a8c774":"","e75b7088-8d9f-4b00-8077-9e76b5c478fe":"","283f9027-61ca-4b47-8a6b-3f2fba059aa7":"","06339a87-8c20-4da6-904a-7f3b0a80a165":"","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"","b9a7072c-720d-4b6e-af83-57c233abd7e6":"","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c":"","ce0bf34e-aea1-456a-a2b5-96492e46d469":"","d63b468e-8fa1-4690-afeb-852e80ea450d":"","50d9b6bb-804a-41c2-82a1-74a2a3cafb6a":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":null,"dependentModules":null,"functionalFrameworkInfo":null,"selectedJSLibs":[],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"created":false,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"javaRoot","id":"2e43c65d-38e0-4890-813c-eb0f2a425ef2","displayName":null,"status":null,"description":null,"creationDate":1388396846000,"helpText":null,"system":false},{"version":"1.0.0-SNAPSHOT","modules":null,"pomFile":"pom.xml","code":"JqueryMobile","appDirName":"JqueryMobile","techInfo":{"version":"2.0.2","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"mobile-web","id":"tech-html5-jquery-mobile-widget","displayName":null,"status":null,"description":null,"creationDate":1354009203000,"helpText":null,"system":false},"functionalFramework":null,"rootModule":"","selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02","28f6d426-277e-4337-b2c4-772c7f11deb3"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"93829766-1c48-4431-b5d8-90fea4f2e2e3","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false}],"selectedDatabases":[],"selectedFeatureMap":{"2d41a182-85f1-42a3-a67c-a0836792ba02":"compile","deda98f8-c350-47f1-8b22-a0816a695127":"","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0":"","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff":"","28f6d426-277e-4337-b2c4-772c7f11deb3":"compile","c7008489-b264-442c-ad8c-2c422284d171":""},"phrescoPomFile":"phresco-pom.xml","selectedWebservices":["restjson"],"dependentModules":null,"functionalFrameworkInfo":{"version":"2.35","frameworkIds":"e060a07a-c845-47eb-a82d-da85df167e63","iframeUrl":null,"frameworkGroupId":"a62bd26f-4948-493d-a9eb-0dbf3dfbbe15","functionalFrameworkIds":null,"name":null,"id":"38563eac-6efc-476a-b378-365b0aa204f6","displayName":null,"status":null,"description":null,"creationDate":1388488983000,"helpText":null,"system":false},"selectedJSLibs":["c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127"],"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"created":true,"showServer":true,"showDatabase":true,"showWebservice":true,"showTestingFramework":true,"customerIds":null,"used":false,"name":"JqueryMobile","id":"PHTN_html5_jquery_mobilewidget_eshop","displayName":null,"status":null,"description":"","creationDate":1388488983000,"helpText":null,"system":false}],"status":"success"});
					}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/validateApp?projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&customerId=photon&name=SampleTemplate-webYui&appName=javaRoot",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800015","data":"javaRoot#SEP#true","status":"success"});
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "/SampleTemplate-webYui?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800014","data":{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},"status":"success"});
					}
				});
				
				$("a[name=editpopup][id=edit_SampleTemplate-webYui]").click();
				setTimeout(function() {
					start();
					var display = $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find('div[id=jobTemplatePopup]').css('display');
					equal(display, "block", "jobTemplates - edit Event Test");
					self.update2JobTemplateTest(jobTemplates);
				}, 3000);
			});
		},


		update2JobTemplateTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - update Event Test", function() {
				$.mockjax({
					url : commonVariables.webserviceurl + commonVariables.jobTemplates + "/validate?customerId=photon&oldname=SampleTemplate-webYui&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=SampleTemplate-webYui",
					type:'GET',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800015","data":true,"status":"success"});	
					}
				});

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?oldname=SampleTemplate-webYui&customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8", 
					type:'PUT',
					contentType:'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800017","data":true,"status":"success"});	
					}
				}); 

				self.jobTempList = $.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"sampleTemplate2","type":"build","module":null,"appIds":["DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":true,"enableEmailSettings":true,"enableUploadSettings":true,"uploadTypes":["Collabnet"]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});	
					}
				});
				
				$(commonVariables.contentPlaceholder).find('input[name=save]').prop('name', 'update');
				$('input[name=update]').click();
				setTimeout(function() {
					start();
					var updatedTr =  $(commonVariables.contentPlaceholder).find(".widget-maincontent-div").find("table[id=jobTemplateList] tr").eq(2).find('td').eq(0).text();
					equal(updatedTr, "sampleTemplate2", "jobTemplates - save with values Event Tested");
					self.reRenderTest(jobTemplates);
				}, 2500);
			});
		},
		
		reRenderTest : function(jobTemplates) {
			var self=this;
			$.mockjaxClear(self.jobTempList);
			asyncTest("jobTemplates - reRenderTest Test", function() {
				$(commonVariables.contentPlaceholder).html('');
				
				self.jobTempList = $.mockjax({	
					url: commonVariables.webserviceurl + commonVariables.jobTemplates + "?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});
					}
				});
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.onMytabEvent(commonVariables.jobTemplates);

				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Job Templates", "reRrenderTest - UI Tested");
					self.openDeletePopupTest(jobTemplates);
				}, 2500);
			});
		},


		openDeletePopupTest : function(jobTemplates) {
			var self=this;
			asyncTest("jobTemplates - Delete Event Trigger Tested", function() {
				$('#delete_sampleTemplate2').click();
				setTimeout(function() {
					start();
					equal($('#yesnopopup_sampleTemplate2').css('display'), 'block', "jobTemplates - Delete Event Trigger Tested");
					self.clickDeleteTest(jobTemplates);
				}, 2500);
			});
		},
		
		clickDeleteTest : function(jobTemplates) {
			var self=this;
			$.mockjaxClear(self.jobTempList);
			asyncTest("jobTemplates - Delete Event Test", function() {

				$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&name=sampleTemplate2",
					type:'DELETE',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800018","data":null,"status":"success"});	
					}
				});
				
			/*	$.mockjax({
					url: commonVariables.webserviceurl + commonVariables.jobTemplates +"?customerId=photon&projectId=3b33c6c3-2491-4870-b0a9-693817b5b9f8&appDirName=",
					type:'GET',
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR800013","data":[{"name":"SampleTemplate","type":"codeValidation","module":null,"appIds":["JqueryMobile","DrupalSingle"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-java","type":"codeValidation","module":"java","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-webYui","type":"codeValidation","module":"webYui","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]},{"name":"SampleTemplate-web","type":"codeValidation","module":"web","appIds":["javaRoot"],"enableRepo":true,"repoTypes":"svn","enableSheduler":false,"enableEmailSettings":false,"enableUploadSettings":false,"uploadTypes":[]}],"status":"success"});	
					}
				});*/
				
				$('#yesnopopup_sampleTemplate2').find('input[name=delete]').click();
				setTimeout(function() {
					start();
					equal(1, 1, "jobTemplates - Delete Event Tested");
					require(["continuousDeliveryConfigureTest"], function(continuousDeliveryConfigureTest){
						continuousDeliveryConfigureTest.runTests();
					});
					$(commonVariables.contentPlaceholder).html('');
				}, 2500);
			});
		},
		


	};	
});

