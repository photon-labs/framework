define([], function() {

	return {
		runTests: function () {
			var self = this;
			module("projectlist.js;projectlist");
			asyncTest("Test - Project List addrepo popup rendered", function() {
				$('.tooltiptop[name^="addRepo"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#addRepo_294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal("block", visibility, "Add to repo popup shown");
					equal("svn", getval, "Repo type svn listed");
					self.projectaddrepoVerification();
				}, 1500);
			});
		},
/* 		projectDeleteSuccessVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Project Delete Service", function() {			
				var projectlistdata = $.mockjax({
				  url: 'framework/project/list?customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});
	
				var deletefn = $.mockjax({
				  url: "framework/project/delete",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Application deleted Successfully","exception":null,"data":null});
				  }
				
				});
				
				setTimeout(function() {
					start();
					$(".tooltiptop").click();
					$("input[name='deleteConfirm']").click();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					notEqual("wordpress-WordPress", techid, "Project List Service Tested");
					self.projectDeletefailureVerification(projectlist);
				}, 1500);
			});
		},
		projectDeletefailureVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List delete Failure Service Tested", function() {			
				var projectlistdata = $.mockjax({
				  url: 'framework/project/list?customerId=photon',
				  type:'GET',
				  contentType: 'application/json',
				  status: 200,
				  response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});
	
				var deletefn = $.mockjax({
				  url: "framework/project/delete",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Application deleted Successfully","exception":null,"data":null});
				  }
				
				});
				
				setTimeout(function() {
					start();
					$(".tooltiptop").click();
					$("input[name='deleteConfirm']").click();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					notEqual("wordpress", techid, "Project List delete Failure Service Tested");
					self.projectaddrepoUiVerification(projectlist);
				}, 1500);
			});
		}, */
		
		
		projectaddrepoVerification : function() {
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
						this.responseText = JSON.stringify({"response":null,"message":"SVN project added Successfully","exception":null,"data":null});
					}
				});
				$("input[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					var getval = $('#myModal').text();
					equal("SVN project added Successfully", getval, "Addrepo service call");
					self.projectCommitUiVerification();
				}, 2500);
			});
		},
		
		projectCommitUiVerification : function() {
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
					self.projectcommitVerification();
				}, 2500);
			});
		},
		
		projectcommitVerification : function() {
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
						this.responseText = JSON.stringify({"response":null,"message":"SVN project committed Successfully","exception":null,"data":null});
					}
				});
				$("input[name='commitbtn']").click();
				setTimeout(function() {
					start();
					var getvalue = $('#myModal').text();
					equal("SVN project committed Successfully", getvalue, "Commit service call");
					self.projectSVNUiVerification();
				}, 2500);
			});
		},
		
		projectSVNUiVerification : function() {
			logs('Project List');
			var self = this;
			asyncTest("Test - Project List SVNUPDATE popup rendered", function() {
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#svn_update294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal("block", visibility, "Add to Commit popup shown");
					equal("svn", getval, "Project List SVNUPDATE popup rendered");
					self.projectSVNUpdateVerification();
				}, 2500);
			});
		},
		
		projectSVNUpdateVerification : function() {
			logs('Project List');
			var self = this;
			asyncTest("Test -SVNUpdate trigger", function() {
				$("input[name='repoUrl']").val('https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0');
				$("input[name='username']").val("admin");
				$("input[name='password']").val("manage");
				$("input[name='revision']").val("head");	
				var addtorepoAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/updateImportedApplication?appDirName=wordpress-WordPress',			
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"response":null,"message":"update svn project Successfully","exception":null,"data":null});
					}
				});
				$("input[name='updatebtn']").click();
				setTimeout(function() {
					start();
					var getval = $('#myModal').text();
					equal("update svn project Successfully", getval, "SVNUpdate service call");
					self.projectImportAppSuccessVerification();
				}, 2500);
			});
		},
		
		projectImportAppSuccessVerification : function() {
			logs('Project List');
			var self = this;
			asyncTest("Import Application Success Service Test", function() {
				var addtorepoAjax = $.mockjax({
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
					self.projectListTest();
				}, 2500);
			});
		}, 
		
		projectListTest : function() {
			$('#content').empty();
			var self = this;
			asyncTest("Test - Project List Service", function() {
				var projectlistdata = $.mockjax({
					url:  commonVariables.webserviceurl+'project/list?customerId=photon',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						logs('Project List');
						this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
					}
				});
				
				$(commonVariables.headerPlaceholder).find(".header_left ul li").click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "Project List Service Tested");
					//self.projectDeleteSuccessVerification();
				}, 2500);
			});
		}
/* 		projectDeleteSuccessVerification : function() {
			logs("delete")
			var self = this;
			asyncTest("Test - Project Delete Service", function() {			
				var projectlistdata = $.mockjax({
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
				$("input[name='deleteConfirm']").click();
				setTimeout(function() {
					start();					
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					notEqual("wordpress-WordPress", techid, "Project List Service Tested");
				}, 1500);
			}); 
			
			
		}*/
	};
});