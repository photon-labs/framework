define(["projectlist/projectList"], function(ProjectList) {

	return {
		runTests: function() {
			var self = this, projectlistdata;
			var projectlist = new ProjectList();
			module("projectlist.js;projectlist");
			asyncTest("Test - Project List addrepo popup rendered", function() {
				$('.tooltiptop[name^="addRepo"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#addRepo_294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal("block", visibility, "Add to repo popup shown");
					equal("svn", getval, "Repo type svn listed");
					self.projectListTest(projectlist);
				}, 1500);
			});
		},
		projectListTest : function(projectlist) {
			$('#content').empty();
			var self = this;
			asyncTest("Test - Project List Service", function() {
				var projectlistdata = $.mockjax({
					url:  commonVariables.webserviceurl+'project/list?customerId=photon',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
					}
				});
				
				$(commonVariables.headerPlaceholder).find(".header_left ul li").click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "Project List Service Tested");
					self.runValidationAddrepoTest(projectlist);
				}, 1500);
			});
		},
		
		runValidationAddrepoTest : function (projectlist){
			var self = this;			
				asyncTest("addRepo URL Validation Test", function() {
					$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').val(''); 
					$('input#repomessage_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('.tooltiptop[name^="addRepo"]').click();
					$("input[name='addrepobtn']").click();
					setTimeout(function() {
						start();
						var repo = $(commonVariables.contentPlaceholder).find('#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
						equal("errormessage", repo, 'URL div error class added test');
						self.runValidationAddrepousernameTest(projectlist);
					}, 1000);
					
				});
		},
		runValidationAddrepousernameTest : function (projectlist){
			var self = this;
			asyncTest("addRepo username Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					var username = $(commonVariables.contentPlaceholder).find('#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					equal("uname", username, 'username div error class added test');
					self.runValidationAddrepoPasswordTest(projectlist);
				}, 1000);
			});
		},
		runValidationAddrepoPasswordTest : function (projectlist){
			var self = this;
			asyncTest("addRepo password Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input[name=username]').val('admin');
				$('input[name=password]').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					var password = $(commonVariables.contentPlaceholder).find('#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					notEqual("errormessage", password, 'Password div error class added test');
					self.runValidationCommitRepoTest(projectlist);
				}, 1000);
			}); 
		},
		
		runValidationCommitRepoTest : function (projectlist){
			var self = this;			
				asyncTest("CommitRepo URL Validation Test", function() {
					$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').val(''); 
					$('input#repomessage_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('.tooltiptop[name^="addRepo"]').click();
					$("input[name='commitbtn']").click();
					setTimeout(function() {
						start();
						var repo = $(commonVariables.contentPlaceholder).find('#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
						equal("errormessage", repo, 'URL div error class added test');
						self.runValidationCommitusernameTest(projectlist);
					}, 1000);
					
				});
		},
		runValidationCommitusernameTest : function (projectlist){
			var self = this;
			asyncTest("Commit username Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='commitbtn']").click();
				setTimeout(function() {
					start();
					var username = $(commonVariables.contentPlaceholder).find('#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					equal("uname", username, 'username div error class added test');
					self.runValidationCommiPasswordTest(projectlist);
				}, 1000);
			});
		},
		runValidationCommiPasswordTest : function (projectlist){
			var self = this;
			asyncTest("Commit password Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input[name=username]').val('admin');
				$('input[name=password]').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='commitbtn']").click();
				setTimeout(function() {
					start();
					var password = $(commonVariables.contentPlaceholder).find('#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					notEqual("errormessage", password, 'Password div error class added test');
					self.runValidationSVNupdateTest(projectlist);
				}, 1000);
			}); 
		},
		
		runValidationSVNupdateTest : function (projectlist){
			var self = this;			
				asyncTest("SVNupdate URL Validation Test", function() {
					$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').val(''); 
					$('input#repomessage_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
					$('.tooltiptop[name^="addRepo"]').click();
					$("input[name='updatebtn']").click();
					setTimeout(function() {
						start();
						var repo = $(commonVariables.contentPlaceholder).find('#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
						equal("errormessage", repo, 'URL div error class added test');
						self.runValidationSVNupdateusernameTest(projectlist);
					}, 1000);
					
				});
		},
		runValidationSVNupdateusernameTest : function (projectlist){
			var self = this;
			asyncTest("SVNupdate username Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='updatebtn']").click();
				setTimeout(function() {
					start();
					var username = $(commonVariables.contentPlaceholder).find('#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					equal("uname", username, 'username div error class added test');
					self.runValidationSVNupdatePasswordTest(projectlist);
				}, 1000);
			});
		},
		runValidationSVNupdatePasswordTest : function (projectlist){
			var self = this;
			asyncTest("SVNupdate password Validation Test", function() {
				$('input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f').val('http://localhost');
				$('input[name=username]').val('admin');
				$('input[name=password]').val('');
				$('.tooltiptop[name^="addRepo"]').click();
				$(".credential").click();
				$("input[name='updatebtn']").click();
				setTimeout(function() {
					start();
					var password = $(commonVariables.contentPlaceholder).find('#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f').attr('class');
					notEqual("errormessage", password, 'Password div error class added test');
					self.projectCommitUiVerification(projectlist);
				}, 1000);
			}); 
		},

		/* projectaddrepoVerification : function(projectlist) {
			var self=this;
			asyncTest("Test -Add to Repo trigger", function() {
				$("input[name='repoUrl']").val('https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0');
				$("input[name='username']").val("admin");
				$("input[name='password']").val("manage");
				$("textarea[name='commitMsg']").val("New Project added");
				var addtorepoAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/addProjectToRepo?appDirName=wordpress-WordPress&userId=admin&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200019","data":null,"status":"success"});
					}
				});
				$("input[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					var getval = $(".success").text();
					equal("Project added successfully", getval, "Addrepo service call");
					self.projectCommitUiVerification(projectlist);
				}, 2500);
			});
		}, */
		
		projectCommitUiVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List Commit popup rendered", function() {
				var getcommitfile = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=commit',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Repo Exist for commit","exception":null,"data":{"revision":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0/wordpress-WordPress","testCheckOut":false,"testUserName":null,"testPassword":null,"testRepoUrl":null,"testRevision":null,"revisionVal":null,"commitMessage":null,"commitableFiles":null,"repoExist":true,"repoInfoFile":[],"type":"svn","password":null,"userName":"admin"}});
					}
				});
				
				$('.tooltiptop[name^="commit"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#commit294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal("block", visibility, "Add to Commit popup shown");
					equal("svn", getval, "Commit type svn listed");
					self.projectSVNUiVerification(projectlist);
				}, 2500);
			});
		},
		
		/* projectcommitVerification : function(projectlist) {
			var self=this;
			asyncTest("Test -Commit trigger", function() {
				$("input[name='repoUrl']").val('https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0/wordpress-WordPress');
				$("input[name='username']").val("admin");
				$("input[name='password']").val("manage");
				$("textarea[name='commitMsg']").val("Project comitted");
				var addcommitAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/commitProjectToRepo?appDirName=wordpress-WordPress',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200020","data":null,"status":"success"});
					}
				});
				$("input[name='commitbtn']").click();
				setTimeout(function() {
					start();
					var getvalue = $(".success").text();
					equal("Project committed successfully", getvalue, "Commit service call");
					self.projectSVNUiVerification(projectlist);
				}, 2500);
			});
		}, */
		
		projectSVNUiVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List SVNUPDATE popup rendered", function() {
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#svn_update294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal("block", visibility, "Add to Commit popup shown");
					equal("svn", getval, "Project List SVNUPDATE popup rendered");
					self.projectImportAppSuccessVerification(projectlist);
				}, 2500);
			});
		},
		
		/* projectSVNUpdateVerification : function(projectlist) {
			var self = this;
			asyncTest("Test -SVNUpdate trigger", function() {
				$("input[name='repoUrl']").val('https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0');
				$("input[name='username']").val("admin");
				$("input[name='password']").val("manage");
				$("input[name='revision']").val("head");	
				var updateImportAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/updateImportedApplication?appDirName=wordpress-WordPress',			
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200018","data":null,"status":"success"});
					}
				});
				$("input[name='updatebtn']").click();
				setTimeout(function() {
					start();
					var getval = $(".success").text();
					equal("Project updated successfully", getval, "SVNUpdate service call");
					self.projectImportAppSuccessVerification(projectlist);
				}, 2500);
			});
		}, */
		
		projectImportAppSuccessVerification : function(projectlist) {
			var self = this;
			asyncTest("Import Application Success Service Test", function() {
				var importAppAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/importApplication',			
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"Project imported successfully","exception":null,"data":null,"response":null});
					}
				});
				var projectlistdata = $.mockjax({
					url:  commonVariables.webserviceurl+'project/list?customerId=photon',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
					}
				});
				$("#importApp").click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "Project List Service Tested");
					self.projectDeleteSuccessVerification(projectlist);
				}, 2500);
			});
		}, 
		
		
		projectDeleteSuccessVerification : function(projectlist) {
			var self = this;
			$.mockjaxClear(self.projectlistdata);

			asyncTest("Test - Project Delete Service", function() {			
				self.projectlistdata = $.mockjax({
				  url: commonVariables.webserviceurl+'project/list?customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});
	
				var deletefn = $.mockjax({
				  url: commonVariables.webserviceurl +"project/delete",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Application deleted Successfully","exception":null,"data":null});
				  }				
				});
				$('.tooltiptop').click();
				$("input[name='deleteConfirm']").click();
				projectlist.deleterow("delete", "delete", "wordpress-WordPress" );
				setTimeout(function() {
					start();					
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal(undefined, techid, "Project List Service Tested");
					self.setConfigurationTypeTests(projectlist);
				}, 1500);
			}); 
		},
		
		setConfigurationTypeTests : function (projectlist){
			var self = this;
			asyncTest("Configuration type Test", function() {
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId=photon&userId=admin&techId=tech-html5-jquery-mobile-widget",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"confuguration Template Fetched successfully","exception":null,"responseCode":null,"data":[{"envSpecific":true,"favourite":false,"templateName":"Scheduler"},{"envSpecific":true,"favourite":false,"templateName":"Server"},{"envSpecific":true,"favourite":false,"templateName":"Database"},{"envSpecific":true,"favourite":false,"templateName":"Email"},{"envSpecific":false,"favourite":false,"templateName":"SAP"}],"status":null});
					}
				});
				projectlist.projectslistListener.editApplication("wordpress-WordPress", "tech-html5-jquery-mobile-widget");
				setTimeout(function() {
					start();
					equal("", "", 'Configuration type Test');
					require(["projectTest"], function(projectTest){
						projectTest.runTests();
					});
				}, 1000);
			});
		}
		
	};
});