define(["projectlist/projectList"], function(ProjectList) {

	return {
		runTests: function() {
			var self = this, projectlistdata, updateRepoMock, checkForLockUpdateMock, checkForLockCommitMock, checkForLockAddMock;
			var projectlist = new ProjectList();
			module("projectlist.js");
			asyncTest("Test - Project List addrepo popup rendered", function() {
				self.checkForLockAddMock = $.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=addToRepo&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				self.checkForLockCommitMock = $.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=commit&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				self.checkForLockUpdateMock = $.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=update&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=deleteAppln&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
					$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=deleteProj&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				$.mockjax({
				  url: commonVariables.webserviceurl+"repository/repoExists?appDirName=wordpress-WordPress",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200053","data":false,"status":"success"});
				  }
				});

				$('.tooltiptop[name^="addRepo"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("select#type_294187d7-f75a-4adc-bb25-ce9465e0e82f").val();
					var visibility =  $('#addRepo_294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display').trim();
					equal(visibility, "block", "Add to repo popup shown");
					equal(getval, "svn", "Repo type svn listed");
					self.splitDotPhrescoCheckedTest(projectlist);
				}, 1500);
			});
		},
		
		splitDotPhrescoCheckedTest : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List addrepo split .phresco Checked test", function() {
				$(".splitDotPhresco").first().click();
				setTimeout(function() {
					start();
					equal($(".splitDotPhresco").first().parent().hasClass("active"), true, "Test - Project List addrepo split .phresco checked tested");
					self.dotPhrescoTabClickEventTest(projectlist);
				}, 1500);
			});
		},
		
		dotPhrescoTabClickEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - .phresco tab click event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$(".dotPhrescoA").click();
				setTimeout(function() {
					var val = $("#phrescotype_" + dynamicId).val();
					equal(val, "svn", ".phresco tab click event tested");
					start();
					self.splitDotPhrescoNotCheckedTest(projectlist);
				}, 1500);
			});
		},
		
		splitDotPhrescoNotCheckedTest : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List addrepo split .phresco not Checked test", function() {
				$(".splitDotPhresco").first().attr("checked", true).click();
				setTimeout(function() {
					start();
					equal($(".splitDotPhresco").first().parent().hasClass("active"), false, "Test - Project List addrepo split .phresco not checked tested");
					self.splitTestSrcCheckedTest(projectlist);
				}, 1500);
			});
		},
		
		splitTestSrcCheckedTest : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List addrepo split test source checked test", function() {
				$(".splitTest").first().click();
				setTimeout(function() {
					start();
					equal($(".splitTest").first().parent().hasClass("active"), true, "Test - Project List addrepo split test source checked tested");
					self.splitTestSrcNotCheckedTest(projectlist);
				}, 1500);
			});
		},
		
		splitTestSrcNotCheckedTest : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List addrepo split test source not checked test", function() {
				$(".splitTest").first().attr("checked", true).click();
				setTimeout(function() {
					start();
					equal($(".splitTest").first().parent().hasClass("active"), false, "Test - Project List addrepo split test source not checked tested");
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
				
				require(["navigation/navigation"], function(){
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});		

				commonVariables.navListener.getMyObj(commonVariables.projectlist, function(retVal){	
					Clazz.navigationController.push(retVal, commonVariables.animation);
				});
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal(techid, "tech-wordpress", "Project List Service Tested");
					self.addToRepoHasLock(projectlist);					
				}, 1500);
			});
		},
		
		addToRepoHasLock : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Has lock Test", function() {
				$.mockjaxClear(self.checkForLockAddMock);
				self.checkForLockAddMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=addToRepo&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00001","data":{"subModuleLock": false},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="addRepo"]').click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').css("display"), "block", 'Add to repo has lock tested');
					self.addToRepoPopupRenderTest(projectlist);
				}, 1000);
			});
		},
		
		addToRepoPopupRenderTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Popup render Test", function() {
				$.mockjaxClear(self.checkForLockAddMock);
				self.checkForLockAddMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=addToRepo&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":{"subModuleLock": false},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="addRepo"]').click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($('#addRepo_'+dynid).css("display"), "block", 'Add to repo popup render tested');
					self.addToRepoOpenHelpTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoOpenHelpTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Help display test", function() {
				$(".addToRepoHelp").click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($("#addToRepoHelp"+dynid).css("display"), "block", 'Add to repo help display tested');
					self.addToRepoCloseHelpTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoCloseHelpTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Close Help test", function() {
				$(".closeAddToRepoHelp").click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($("#addToRepoHelp"+dynid).css("display"), "none", 'Add to repo close help tested');
					self.addToRepoChkOthrCredTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoChkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Other credentials check Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepocredential').click();
				setTimeout(function() {
					start();
					equal($('#uname_'+dynamicId).attr('readonly'), undefined, 'Username readonly attribute removed');
					equal($('#pwd_'+dynamicId).attr('readonly'), undefined, 'Password readonly attribute removed');
					self.addToRepoUnchkOthrCredTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoUnchkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Other credentials uncheck Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepocredential').click();
				setTimeout(function() {
					start();
					equal($('#uname_'+dynamicId).attr('readonly'), 'readonly', 'Username readonly attribute added');
					equal($('#pwd_'+dynamicId).attr('readonly'), 'readonly', 'Password readonly attribute added');
					self.addToRepoSrcUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoSrcUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Source URL empty validation Test", function() {
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($('#repourl_'+dynid).attr("placeholder"), "Enter Url", 'Source URL empty validation tested');
					self.addToRepoInvalidSrcUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoInvalidSrcUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Source invalid URL validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("sample url");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#repourl_'+dynid).attr("placeholder"), "Invalid Repo Url", 'Source invalid URL validation tested');
					self.addToRepoSrcUsrNameValidation(projectlist);
				}, 1000);
			});
		},
		
		addToRepoSrcUsrNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Source user name empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#uname_'+dynid).attr("placeholder"), "Enter user name", 'Source user name empty validation tested');
					self.addToRepoSrcPasswordValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoSrcPasswordValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Source password empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#pwd_'+dynid).attr("placeholder"), "Enter password", 'Source password empty validation tested');
					self.splitDotPhrescoCheckTest(projectlist);
				}, 1000);
				
			});
		},
		
		splitDotPhrescoCheckTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Split .phresco Check test", function() {
				$(".splitDotPhresco").first().click();
				setTimeout(function() {
					start();
					equal($(".splitDotPhresco").first().parent().hasClass("active"), true, "Split .phresco Check tested");
					self.addToRepoPhrChkOthrCredTest(projectlist);
				}, 1500);
			});
		},
		
		addToRepoPhrChkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco Other credentials check Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepoPhrescocredential').click();
				setTimeout(function() {
					start();
					equal($('#phrescouname_'+dynamicId).attr('readonly'), undefined, '.phresco Username readonly attribute removed');
					equal($('#phrescopwd_'+dynamicId).attr('readonly'), undefined, '.phresco Password readonly attribute removed');
					self.addToRepoPhrUnchkOthrCredTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoPhrUnchkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco Other credentials uncheck Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepoPhrescocredential').click();
				setTimeout(function() {
					start();
					equal($('#phrescouname_'+dynamicId).attr('readonly'), 'readonly', '.phresco Username readonly attribute added');
					equal($('#phrescopwd_'+dynamicId).attr('readonly'), 'readonly', '.phresco Password readonly attribute added');
					self.addToRepoPhrUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoPhrUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco URL empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrescorepourl_'+dynid).attr("placeholder"), "Enter Url", '.phresco URL empty validation tested');
					self.addToRepoInvalidPhrUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoInvalidPhrUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco invalid URL validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("sample url");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrescorepourl_'+dynid).attr("placeholder"), "Invalid Repo Url", '.phresco invalid URL validation tested');
					self.addToRepoPhrUsrNameValidation(projectlist);
				}, 1000);
			});
		},
		
		addToRepoPhrUsrNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco user name empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrescouname_'+dynid).attr("placeholder"), "Enter user name", '.phresco user name empty validation tested');
					self.addToRepoPhrPasswordValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoPhrPasswordValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - .phresco password empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrescopwd_'+dynid).attr("placeholder"), "Enter password", '.phresco password empty validation tested');
					self.splitTestCheckTest(projectlist);
				}, 1000);
				
			});
		},
		
		splitTestCheckTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Split test source check test", function() {
				$(".splitTest").first().click();
				setTimeout(function() {
					start();
					equal($(".splitTest").first().parent().hasClass("active"), true, "Split test source check tested");
					self.addToRepoTestChkOthrCredTest(projectlist);
				}, 1500);
			});
		},
		
		addToRepoTestChkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - test Other credentials check Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepoTestCredential').click();
				setTimeout(function() {
					start();
					equal($('#testuname_'+dynamicId).attr('readonly'), undefined, 'test Username readonly attribute removed');
					equal($('#testpwd_'+dynamicId).attr('readonly'), undefined, 'test Password readonly attribute removed');
					self.addToRepoTestUnchkOthrCredTest(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoTestUnchkOthrCredTest : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - test Other credentials uncheck Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.addToRepoTestCredential').click();
				setTimeout(function() {
					start();
					equal($('#testuname_'+dynamicId).attr('readonly'), 'readonly', 'test Username readonly attribute added');
					equal($('#testpwd_'+dynamicId).attr('readonly'), 'readonly', 'test Password readonly attribute added');
					self.addToRepoTestUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoTestUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Test URL empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("manage");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#testrepourl_'+dynid).attr("placeholder"), "Enter Url", 'Test URL empty validation tested');
					self.addToRepoInvalidTestUrlValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoInvalidTestUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Test invalid URL validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("manage");
				$('#testrepourl_'+dynid).val("sample url");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#testrepourl_'+dynid).attr("placeholder"), "Invalid Repo Url", 'Test invalid URL validation tested');
					self.addToRepoTestUsrNameValidation(projectlist);
				}, 1000);
			});
		},
		
		addToRepoTestUsrNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Test user name empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("manage");
				$('#testrepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#testuname_'+dynid).val('');
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#testuname_'+dynid).attr("placeholder"), "Enter user name", 'Test user name empty validation tested');
					self.addToRepoTestPasswordValidation(projectlist);
				}, 1000);
				
			});
		},
		
		addToRepoTestPasswordValidation : function(projectlist) {
			var self = this;
			asyncTest("Add To Repo - Test password empty validation Test", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("manage");
				$('#testrepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#testuname_'+dynid).val('admin');
				$('#testpwd_'+dynid).val("");
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($('#testpwd_'+dynid).attr("placeholder"), "Enter password", 'Test password empty validation tested');
					self.projectaddrepoVerification(projectlist);
				}, 1000);
			});
		},
		
		projectaddrepoVerification : function(projectlist) {
			var self=this;
			asyncTest("Add to Repo - trigger", function() {
				var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#repourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#uname_'+dynid).val("admin");
				$('#pwd_'+dynid).val("manage");
				$('#phrescorepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#phrescouname_'+dynid).val("admin");
				$('#phrescopwd_'+dynid).val("manage");
				$('#testrepourl_'+dynid).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#testuname_'+dynid).val('admin');
				$('#testpwd_'+dynid).val("manage");
				var addtorepoAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/addProjectToRepo?appDirName=wordpress-WordPress&userId=admin&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f&displayName=Admin',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200019","data":null,"status":"success"});
					}
				});
				$("button[name='addrepobtn']").click();
				setTimeout(function() {
					start();
					equal($("#addRepo_"+dynid).css("display"), "none", "Addrepo service call");
					self.updateRepoHasLock(projectlist);
				}, 2500);
			});
		},
		
		updateRepoHasLock : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Has lock Test", function() {
				$.mockjaxClear(self.checkForLockUpdateMock);
				self.checkForLockUpdateMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=update&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00001","data":{"subModuleLock": false},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').css("display"), "block", 'Update repo has lock tested');
					self.updateRepoPopupRenderTest(projectlist);
				}, 1000);
				
			});
		},
		
		updateRepoPopupRenderTest : function(projectlist) {
			var self=this;
			asyncTest("Update Repo - Popup Render Test", function() {
				$.mockjaxClear(self.checkForLockUpdateMock);
				self.checkForLockUpdateMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=update&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
					}
				});
				
				self.updateRepoMock = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=update',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200022","data":{"srcRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":null,"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"phrescoRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop-phresco","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":null,"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"splitPhresco":true,"testRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop-test","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":null,"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"splitTest":true},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var visibility =  $('#svn_update294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display');
					equal(visibility, "block", "Update repo popup shown");
					self.updateSVNRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "Update Repo svn repo url empty validation tested");
					start();
					self.updateSVNInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Update Repo svn invalid repo url validation tested");
					start();
					self.updateSVNHeadOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNHeadOptionCheckEvent : function (projectlist) {
			var self = this;
			asyncTest("Update Repo - svn Head Option Check Event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("sample url");
				$('input[name=updateHeadoption'+dynamicId+']').first().click();
				setTimeout(function() {
					var readonly = $("#updateRevision"+dynamicId).attr("readonly");
					equal(readonly, "readonly", "Update Repo Head Option Check Event tested");
					start();
					self.updateSVNOthrCredentialsCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNOthrCredentialsCheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn check other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updSrcOtherCredential').click();
				setTimeout(function() {
					equal($('#updateUserName'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for user name field");
					equal($('#updatePassword'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for password field");
					start();
					self.updateSVNOthrCredentialsUncheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNOthrCredentialsUncheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn uncheck other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updSrcOtherCredential').click();
				setTimeout(function() {
					equal($('#updateUserName'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for user name field");
					equal($('#updatePassword'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for password field");
					start();
					self.updateSVNUserNameValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNUserNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn user name empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateUserName'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter user name", "Update Repo svn user name empty validation tested");
					start();
					self.updateSVNPwdValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPwdValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn password empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePassword'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter password", "Update Repo svn password empty validation tested");
					start();
					self.updateSVNDotPhrescoCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateSVNDotPhrescoCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update .phresco check event tested");
					start();
					self.updateSVNDotPhrescoHeadOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNDotPhrescoHeadOptionCheckEvent : function (projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco head Option Check Event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("sample url");
				$('input[name=updatePhrescoHeadoption'+dynamicId+']').first().click();
				setTimeout(function() {
					var readonly = $("#updatePhrescoRevision"+dynamicId).attr("readonly");
					equal(readonly, "readonly", "Update Repo .phresco head Option Check Event tested");
					start();
					self.updateSVNPhrescoRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrescoRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn phresco repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update SVN Phresco repo url empty validation tested");
					start();
					self.updateSVNPhrescoInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrescoInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn Phresco invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update svn Phresco invalid repo url validation tested");
					start();
					self.updateSVNDotPhrescoOthrCredentialsCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNDotPhrescoOthrCredentialsCheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco check other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updPhrOtherCredential').click();
				setTimeout(function() {
					equal($('#updatePhrescoUserName'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for user name field");
					equal($('#updatePhrescoPassword'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for password field");
					start();
					self.updateSVNDotPhrescoOthrCredentialsUncheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNDotPhrescoOthrCredentialsUncheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco check other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updPhrOtherCredential').click();
				setTimeout(function() {
					equal($('#updatePhrescoUserName'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for user name field");
					equal($('#updatePhrescoPassword'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for password field");
					start();
					self.updateSVNPhrescoUserNameValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrescoUserNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn Phresco user name validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('input[name=updateHeadoption'+dynamicId+']').first().attr("checked", true);
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoUserName'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter user name", "update svn Phresco user name validation tested");
					start();
					self.updateSVNPhrescoPasswordValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrescoPasswordValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn Phresco password validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('input[name=updateHeadoption'+dynamicId+']').first().attr("checked", true);
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoPassword'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter password", "update svn Phresco password validation tested");
					start();
					self.updateSVNTestCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update svn test check event tested");
					start();
					self.updateSVNTestOthrCredentialsCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestOthrCredentialsCheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test uncheck other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updTestOtherCredential').click();
				setTimeout(function() {
					equal($('#updateTestUserName'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for user name field");
					equal($('#updateTestPassword'+dynamicId).attr("readonly"), undefined, "Readonly attr removed for password field");
					start();
					self.updateSVNTestOthrCredentialsUncheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestOthrCredentialsUncheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test uncheck other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updTestOtherCredential').click();
				setTimeout(function() {
					equal($('#updateTestUserName'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for user name field");
					equal($('#updateTestPassword'+dynamicId).attr("readonly"), "readonly", "Readonly attr added for password field");
					start();
					self.updateSVNTestHeadOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestHeadOptionCheckEvent : function (projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test revision Option Check Event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("sample url");
				$('input[name=testUpdateHeadoption'+dynamicId+']').first().click();
				setTimeout(function() {
					var readonly = $("#testUpdateRevision"+dynamicId).attr("readonly");
					equal(readonly, "readonly", "Update Repo test head Option Check Event tested");
					start();
					self.updateSVNTestRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updateTestRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update svn test repo url empty validation tested");
					start();
					self.updateSVNTestInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updateTestRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update svn test invalid repo url validation tested");
					start();
					self.updateSVNTestOthrCredentialsCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestOthrCredentialsCheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test check other credentials test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('.updTestOtherCredential').click();
				setTimeout(function() {
					equal($('#updateTestUserName'+dynamicId).attr("readonly"), undefined, "Readonly attr added for user name field");
					equal($('#updateTestPassword'+dynamicId).attr("readonly"), undefined, "Readonly attr added for password field");
					start();
					self.updateSVNTestUserNameValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestUserNameValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test user name validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updateTestRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateTestUserName'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestUserName'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter user name", "update svn test user name validation tested");
					start();
					self.updateSVNTestPasswordValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestPasswordValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test password validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updateTestRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateTestUserName'+dynamicId).val("manage");
				$('#updateTestPassword'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestPassword'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter password", "update svn test password validation tested");
					start();
					self.updateSVNWithHeadOption(projectlist);
				}, 1500);
			});
		},
		
		updateSVNWithHeadOption : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn with head option test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'repository/updateImportedApplication?appDirName=wordpress-WordPress&displayName=Admin',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200018","data":null,"status":"success"});
					}
				});
				
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updateTestRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateTestUserName'+dynamicId).val("admin");
				$('#updateTestPassword'+dynamicId).val("manage");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $("#svn_update"+dynamicId).css("display");
					equal(errMsg, "none", "update svn with head option tested");
					start();
					self.updateSVNRevisionOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNRevisionOptionCheckEvent : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn revision option check test", function() {
				$('.tooltiptop[name^="svn_update"]').click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('input[name=updateHeadoption'+dynamicId+']').last().click();
				setTimeout(function() {
					var readonly = $('#updateRevision'+dynamicId).attr('readonly');
					equal(readonly, undefined, "update svn revision option check tested");
					start();
					self.updateSVNRevisionValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNRevisionValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn revision empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updateRevision'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRevision'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter revision", "update svn revision empty validation tested");
					start();
					self.updateSVNRevisionDotPhrescoCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateSVNRevisionDotPhrescoCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update .phresco check event tested");
					start();
					self.updateSVNPhrRevisionOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrRevisionOptionCheckEvent : function (projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco revision Option Check Event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('input[name=updatePhrescoHeadoption'+dynamicId+']').last().click();
				setTimeout(function() {
					var readonly = $("#updatePhrescoRevision"+dynamicId).attr("readonly");
					equal(readonly, undefined, "Update Repo svn .phresco revision Option Check Event tested");
					start();
					self.updateSVNPhrescoRevisionValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNPhrescoRevisionValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco revision empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updateRevision'+dynamicId).val("12345");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRevision'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter revision", "update svn .phresco revision empty validation tested");
					start();
					self.updateSVNRevisionTestCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateSVNRevisionTestCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update svn test check event tested");
					start();
					self.updateSVNTestRevisionOptionCheckEvent(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestRevisionOptionCheckEvent : function (projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test revision Option Check Event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('input[name=testUpdateHeadoption'+dynamicId+']').last().click();
				setTimeout(function() {
					var readonly = $("#testUpdateRevision"+dynamicId).attr("readonly");
					equal(readonly, undefined, "Update Repo test revision Option Check Event tested");
					start();
					self.updateSVNTestRevisionValidation(projectlist);
				}, 1500);
			});
		},
		
		updateSVNTestRevisionValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn Test revision empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updateRevision'+dynamicId).val("12345");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('#updatePhrescoRevision'+dynamicId).val("12345");
				$('#updateTestRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateTestUserName'+dynamicId).val("admin");
				$('#updateTestPassword'+dynamicId).val("manage");
				$('#testUpdateRevision'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#testUpdateRevision'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter revision", "update svn Test revision empty validation tested");
					start();
					self.updateSVNWithRevisionOption(projectlist);
				}, 1500);
			});
		},
		
		updateSVNWithRevisionOption : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn with revision option test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'repository/updateImportedApplication?appDirName=wordpress-WordPress&displayName=Admin',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200018","data":null,"status":"success"});
					}
				});

				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('input[name=updateHeadoption'+dynamicId+']').last().attr("checked", true);
				$('#updateRevision'+dynamicId).val("12345");
				$('#updatePhrescoRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updatePhrescoUserName'+dynamicId).val("admin");
				$('#updatePhrescoPassword'+dynamicId).val("manage");
				$('input[name=updatePhrescoHeadoption'+dynamicId+']').last().attr("checked", true);
				$('#updatePhrescoRevision'+dynamicId).val("12345");
				$('#updateTestRepourl'+dynamicId).val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#updateTestUserName'+dynamicId).val("admin");
				$('#updateTestPassword'+dynamicId).val("manage");
				$('input[name=testUpdateHeadoption'+dynamicId+']').last().attr("checked", true);
				$('#testUpdateRevision'+dynamicId).val("12345");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $("#svn_update"+dynamicId).css("display");
					equal(errMsg, "none", "update svn with revision option tested");
					start();
					self.updateRepoTypeChangeToGitEveTest(projectlist);
				}, 1500);
			});
		},
		
		updateRepoTypeChangeToGitEveTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Repo type change to git event test", function() {
				$.mockjaxClear(self.updateRepoMock);
				self.updateRepoMock = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=update',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200022","data":{"srcRepoDetail":{"type":"git","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"splitTest":false,"splitPhresco":false,"testRepoDetail":{"type":"git","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"phrescoRepoDetail":{"type":"git","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null}},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					var selectedType =  $('#updateType'+dynamicId).val();
					equal(selectedType, "git", "Update repo popup shown with git repo type selected");
					self.updateGITRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGITRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "Update Repo git repo url empty validation tested");
					start();
					self.updateGITInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGITInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Update Repo git invalid repo url validation tested");
					start();
					self.updateGitDotPhrescoCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateGitDotPhrescoCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).attr("checked", false);
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update git .phresco check event tested");
					start();
					self.updateGitPhrescoRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGitPhrescoRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git Phresco repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update git Phresco repo url empty validation tested");
					start();
					self.updateGitPhrescoInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGitPhrescoInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git Phresco invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update git Phresco invalid repo url validation tested");
					start();
					self.updateGitTestCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateGitTestCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).attr("checked", false);
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update git test check event tested");
					start();
					self.updateGitTestRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGitTestRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git test repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updatePhrescoRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateTestRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update git test repo url empty validation tested");
					start();
					self.updateGitTestInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateGitTestInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git test invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updatePhrescoRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateTestRepourl'+dynamicId).val("sample url");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update git test invalid repo url validation tested");
					start();
					self.updateGitTest(projectlist);
				}, 1500);
			});
		},
		
		updateGitTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - git trigger test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updatePhrescoRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateTestRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $("#svn_update"+dynamicId).css("display");
					equal(errMsg, "none", "update git tested");
					start();
					self.updateRepoTypeChangeToPerforceEveTest(projectlist);
				}, 1500);
			});
		},
		
		updateRepoTypeChangeToPerforceEveTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Repo type change to perforce event test", function() {
				$.mockjaxClear(self.updateRepoMock);
				self.updateRepoMock = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=update',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200022","data":{"srcRepoDetail":{"type":"perforce","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"splitTest":false,"splitPhresco":false,"testRepoDetail":{"type":"perforce","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"phrescoRepoDetail":{"type":"perforce","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null}},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					var selectedType =  $('#updateType'+dynamicId).val();
					equal(selectedType, "perforce", "Update repo popup shown with perforce repo type selected");
					self.updatePerforceRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "Update Repo perforce repo url empty validation tested");
					start();
					self.updatePerforceInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("a:b");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Update Repo perforce invalid repo url validation tested");
					start();
					self.updatePerforceStreamValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceStreamValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce stream validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('.updateStream'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Stream", "Update Repo perforce stream validation tested");
					start();
					self.updatePerforceDotPhrescoCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceDotPhrescoCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).attr("checked", false);
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update perforce .phresco check event tested");
					start();
					self.updatePerforcePhrescoRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforcePhrescoRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce Phresco repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update perforce Phresco repo url empty validation tested");
					start();
					self.updatePerforcePhrescoInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforcePhrescoInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce Phresco invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("a:b");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update perforce Phresco invalid repo url validation tested");
					start();
					self.updatePerforcePhrescoStreamValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforcePhrescoStreamValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce phresco stream validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("localhost:8080");
				$('.updatePhrescoStream'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('.updatePhrescoStream'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Stream", "Update Repo perforce phresco stream validation tested");
					start();
					self.updatePerforceTestCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceTestCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).attr("checked", false);
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update perforce test check event tested");
					start();
					self.updatePerforceTestRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceTestRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - perforce test repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("localhost:8080");
				$('.updatePhrescoStream'+dynamicId).val("sample stream");
				$('#updateTestRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update perforce test repo url empty validation tested");
					start();
					self.updatePerforceTestInvalidRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceTestInvalidRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Perforce test invalid repo url validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("localhost:8080");
				$('.updatePhrescoStream'+dynamicId).val("sample stream");
				$('#updateTestRepourl'+dynamicId).val("a:b");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "update Perforce test invalid repo url validation tested");
					start();
					self.updatePerforceTestStreamValidation(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceTestStreamValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - test stream validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("localhost:8080");
				$('.updatePhrescoStream'+dynamicId).val("test stream");
				$('#updateTestRepourl'+dynamicId).val("localhost:8080");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('.testUpdateStream'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Stream", "Update Repo perforce test stream validation tested");
					start();
					self.updatePerforceTest(projectlist);
				}, 1500);
			});
		},
		
		updatePerforceTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Trigger perforce test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("localhost:8080");
				$('.updateStream'+dynamicId).val("test stream");
				$('#updatePhrescoRepourl'+dynamicId).val("localhost:8080");
				$('.updatePhrescoStream'+dynamicId).val("test stream");
				$('#updateTestRepourl'+dynamicId).val("localhost:8080");
				$('.testUpdateStream'+dynamicId).val("test stream");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $("#svn_update"+dynamicId).css("display");
					equal(errMsg, "none", "update perforce tested");
					start();
					self.updateRepoTypeChangeToBitkeeperEveTest(projectlist);
				}, 1500);
			});
		},
		
		updateRepoTypeChangeToBitkeeperEveTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Repo type change to Bitkeeper event test", function() {
				$.mockjaxClear(self.updateRepoMock);
				self.updateRepoMock = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=update',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200022","data":{"srcRepoDetail":{"type":"bitkeeper","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"splitTest":false,"splitPhresco":false,"testRepoDetail":{"type":"bitkeeper","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null},"phrescoRepoDetail":{"type":"bitkeeper","stream":null,"password":null,"userName":"admin","revision":null,"repoUrl":"https://github.com/santhosh-ja/TestProject_src.git","commitableFiles":null,"commitMessage":null,"repoExist":true,"branch":null,"revisionVal":null,"repoInfoFile":null,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null}},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					var selectedType =  $('#updateType'+dynamicId).val();
					equal(selectedType, "bitkeeper", "Update repo popup shown with Bitkeeper repo type selected");
					self.updateBitkeeperRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "Update Repo Bitkeeper repo url empty validation tested");
					start();
					self.updateBitkeeperDotPhrescoCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperDotPhrescoCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).attr("checked", false);
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update Bitkeeper .phresco check event tested");
					start();
					self.updateBitkeeperPhrescoRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperPhrescoRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper Phresco repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateUserName'+dynamicId).val("admin");
				$('#updatePassword'+dynamicId).val("manage");
				$('#updatePhrescoRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updatePhrescoRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update Bitkeeper Phresco repo url empty validation tested");
					start();
					self.updateBitkeeperTestCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperTestCheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).attr("checked", false);
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update Bitkeeper test check event tested");
					start();
					self.updateBitkeeperTestRepoUrlValidation(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperTestRepoUrlValidation : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper test repo url empty validation test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updatePhrescoRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateTestRepourl'+dynamicId).val("");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $('#updateTestRepourl'+dynamicId).attr('placeholder');
					equal(errMsg, "Enter Url", "update Bitkeeper test repo url empty validation tested");
					start();
					self.updateBitkeeperTest(projectlist);
				}, 1500);
			});
		},
		
		updateBitkeeperTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - Bitkeeper test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#updateRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updatePhrescoRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$('#updateTestRepourl'+dynamicId).val("https://github.com/santhosh-ja/TestProject_src.git");
				$("button[name='updatebtn']").click();
				setTimeout(function() {
					var errMsg = $("#svn_update"+dynamicId).css("display");
					equal(errMsg, "none", "update git tested");
					start();
					self.openUpdateRepoPopup(projectlist);
				}, 1500);
			});
		},
		
		openUpdateRepoPopup : function(projectlist) {
			var self=this;
			asyncTest("Update Repo - Popup Render Test", function() {
				$('.tooltiptop[name^="svn_update"]').click();
				setTimeout(function() {
					start();
					var visibility =  $('#svn_update294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display');
					equal(visibility, "block", "Update repo popup shown");
					self.makeSVNDotPhrescoChecked(projectlist);
				}, 1500);
			});
		},

		makeSVNDotPhrescoChecked : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update .phresco check event tested");
					start();
					self.runSVNDotPhrescoUncheckTest(projectlist);
				}, 1500);
			});
		},

		runSVNDotPhrescoUncheckTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn .phresco uncheck event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateDotPhresco_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateDotphresco"+dynamicId).hasClass("active in");
					equal(hasClass, false, "update .phresco uncheck event tested");
					start();
					self.makeSVNTestChecked(projectlist);
				}, 1500);
			});
		},

		makeSVNTestChecked : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test check event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, true, "update svn test check event tested");
					start();
					self.runSVNTestUncheckEventTest(projectlist);
				}, 1500);
			});
		},

		runSVNTestUncheckEventTest : function(projectlist) {
			var self = this;
			asyncTest("Update Repo - svn test uncheck event test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("#updateTest_"+dynamicId).click();
				setTimeout(function() {
					var hasClass = $("#updateTest"+dynamicId).hasClass("active in");
					equal(hasClass, false, "update svn test check event tested");
					start();
					self.commitRepoHasLock(projectlist);
				}, 1500);
			});
		},
		
		commitRepoHasLock : function(projectlist) {
			var self = this;
			asyncTest("Commit Repo - Has lock Test", function() {
				$.mockjaxClear(self.checkForLockCommitMock);
				self.checkForLockCommitMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=commit&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00001","data":{"subModuleLock": false},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="commit"]').click();
				setTimeout(function() {
					start();
					var dynid = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
					equal($(commonVariables.contentPlaceholder).find('.content_end .msgdisplay').css("display"), "block", 'Commit repo has lock tested');
					self.commitPopupRenderTest(projectlist);
				}, 1000);
				
			});
		},
		
		commitPopupRenderTest : function(projectlist) {
			var self=this;
			asyncTest("Commit Repo - Popup Render Test", function() {
				$.mockjaxClear(self.checkForLockCommitMock);
				self.checkForLockCommitMock = $.mockjax({
					url: commonVariables.webserviceurl+"util/checkLock?actionType=commit&appId=294187d7-f75a-4adc-bb25-ce9465e0e82f",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
					}
				});
				
				self.updateRepoMock = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/popupValues?appDirName=wordpress-WordPress&userId=admin&action=commit',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200021","data":{"srcRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":[{"status":"M","commitFilePath":"/Users/kaleeswaran/workspace/projects/java-webservice-eshop/java-webservice-eshop/pom.xml","contentsStatus":{"id":2,"code":"M"}}],"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"phrescoRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop-phresco","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":[{"status":"M","commitFilePath":"/Users/kaleeswaran/workspace/projects/java-webservice-eshop/java-webservice-eshop-phresco/phresco-pom.xml","contentsStatus":{"id":2,"code":"M"}}],"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"splitPhresco":true,"testRepoDetail":{"type":"svn","stream":null,"password":null,"userName":"loheswaran_g","revision":null,"serverPath":null,"repoUrl":"https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/trunk/java-webservice-eshop-test","commitableFiles":null,"commitMessage":null,"branch":null,"revisionVal":null,"repoInfoFile":[{"status":"M","commitFilePath":"/Users/kaleeswaran/workspace/projects/java-webservice-eshop/java-webservice-eshop-test/test/component/pom.xml","contentsStatus":{"id":2,"code":"M"}}],"repoExist":true,"testCheckOut":false,"testRepoUrl":null,"testUserName":null,"testPassword":null,"testRevision":null,"testRevisionVal":null,"passPhrase":null,"proName":null},"splitTest":true},"status":"success"});
					}
				});
				
				$('.tooltiptop[name^="commit"]').click();
				setTimeout(function() {
					start();
					var visibility =  $('#commit294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display');
					equal(visibility, "block", "Commit repo popup shown");
					self.runCommitSrcOthrCredCheckEventTest(projectlist);
				}, 1500);
			});
		},
		
		runCommitSrcOthrCredCheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Source other credentials check Test", function() {
				$(".cmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitUserName'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for user name');
					equal($('#commitPassword'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for password');
					self.runCommitSrcUserNameValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitSrcUserNameValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Source user name validation test Test", function() {
				$("button[name='commitbtn']").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitUserName'+dynamicId).attr("placeholder"), "Enter user name", 'Source user name validation test Tested');
					self.runCommitSrcPwdValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitSrcPwdValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Source password validation test Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#commitUserName'+dynamicId).val("admin");
				$("button[name='commitbtn']").click();
				setTimeout(function() {
					start();
					equal($('#commitPassword'+dynamicId).attr("placeholder"), "Enter password", 'Source password validation test Tested');
					self.runCommitSrcOthrCredUncheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitSrcOthrCredUncheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Source other credentials uncheck Test", function() {
				$(".cmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitUserName'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for user name');
					equal($('#commitPassword'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for password');
					self.runCommitPhrCheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitPhrCheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco check event Test", function() {
				$(".commitDotPhresco").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitDotphresco'+dynamicId).hasClass("active in"), true, '.phresco check event Tested');
					self.runCommitPhrOthrCredCheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitPhrOthrCredCheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco other credentials check Test", function() {
				$(".phrCmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#phrCommitUserName'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for user name');
					equal($('#phrCommitPassword'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for password');
					self.runCommitPhrUserNameValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitPhrUserNameValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco user name validation test Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("button[name='commitbtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrCommitUserName'+dynamicId).attr("placeholder"), "Enter user name", '.phresco user name validation test Tested');
					self.runCommitPhrPwdValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitPhrPwdValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco password validation test Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#phrCommitUserName'+dynamicId).val("admin");
				$("button[name='commitbtn']").click();
				setTimeout(function() {
					start();
					equal($('#phrCommitPassword'+dynamicId).attr("placeholder"), "Enter password", '.phresco password validation test Tested');
					self.runCommitPhrOthrCredUncheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitPhrOthrCredUncheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco other credentials uncheck Test", function() {
				$(".phrCmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#phrCommitUserName'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for user name');
					equal($('#phrCommitPassword'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for password');
					self.runCommitTestCheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitTestCheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - test check event Test", function() {
				$(".commitTest").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitTest'+dynamicId).hasClass("active in"), true, 'test check event Tested');
					self.runCommitTestOthrCredCheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitTestOthrCredCheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - test other credentials check Test", function() {
				$(".testCmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#testCommitUserName'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for user name');
					equal($('#testCommitPassword'+dynamicId).attr("readonly"), undefined, 'Readonly attr removed for password');
					self.runCommitTestUserNameValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitTestUserNameValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Test user name validation test Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$("button[name='commitbtn']").click();
				setTimeout(function() {
					start();
					equal($('#testCommitUserName'+dynamicId).attr("placeholder"), "Enter user name", 'Test user name validation test Tested');
					self.runCommitTestPwdValidationTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitTestPwdValidationTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Test password validation test Test", function() {
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				$('#testCommitUserName'+dynamicId).val("admin");
				$("button[name='commitbtn']").click();
				setTimeout(function() {
					start();
					equal($('#testCommitPassword'+dynamicId).attr("placeholder"), "Enter password", 'Test password validation test Tested');
					self.runCommitTestOthrCredUncheckEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitTestOthrCredUncheckEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - test other credentials uncheck Test", function() {
				$(".testCmtSrcOtherCredential").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#testCommitUserName'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for user name');
					equal($('#testCommitPassword'+dynamicId).attr("readonly"), "readonly", 'Readonly attr added for password');
					self.runCommitEventTest(projectlist);
				}, 1000);
			});
		},
		
		runCommitEventTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - Trigger Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl + 'repository/commitProjectToRepo?appDirName=wordpress-WordPress&displayName=Admin',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200020","data":null,"status":"success"});
					}
				});
			
				$("button[name='commitbtn']").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($("#commit"+dynamicId).css("display"), "none", 'Commit to repo trigger tested');
					self.openCommitPopup(projectlist);
				}, 1000);
			});
		},
		
		openCommitPopup : function(projectlist) {
			var self=this;
			asyncTest("Commit Repo - Popup Render Test", function() {
				$('.tooltiptop[name^="commit"]').click();
				setTimeout(function() {
					start();
					var visibility =  $('#commit294187d7-f75a-4adc-bb25-ce9465e0e82f').css('display');
					equal(visibility, "block", "Commit repo popup shown");
					self.makeCommitPhrChecked(projectlist);
				}, 1500);
			});
		},
		
		makeCommitPhrChecked : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco check event Test", function() {
				$(".commitDotPhresco").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitDotphresco'+dynamicId).hasClass("active in"), true, '.phresco check event Tested');
					self.commitPhrUncheckTest(projectlist);
				}, 1000);
			});
		},
		
		commitPhrUncheckTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - .phresco uncheck event Test", function() {
				$(".commitDotPhresco").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitDotphresco'+dynamicId).hasClass("active in"), false, '.phresco uncheck event Tested');
					self.makeCommitTestChecked(projectlist);
				}, 1000);
			});
		},
		
		makeCommitTestChecked : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - test check event Test", function() {
				$(".commitTest").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitTest'+dynamicId).hasClass("active in"), true, 'test check event Tested');
					self.commitTestUncheckTest(projectlist);
				}, 1000);
			});
		},
		
		commitTestUncheckTest : function (projectlist) {
			var self = this;			
			asyncTest("Commit Repo - test uncheck event Test", function() {
				$(".commitTest").click();
				var dynamicId = "294187d7-f75a-4adc-bb25-ce9465e0e82f";
				setTimeout(function() {
					start();
					equal($('#commitTest'+dynamicId).hasClass("active in"), false, 'test uncheck event Tested');
					self.projectAddPdfUiVerification(projectlist);
				}, 1000);
			});
		},
		
		projectAddPdfUiVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Project List add Pdf rendered", function() {
				var addpdf = $.mockjax({
					url: commonVariables.webserviceurl + 'pdf/showPopUp?appDirName=wordpress-WordPress&fromPage=All',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200027","data":{"value":false,"json":[]},"status":"success"});
					}
				});

				$('.tooltiptop[name^="pdf_report"]').click();
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("#noReport_294187d7-f75a-4adc-bb25-ce9465e0e82f").text();
					equal("No Reports are Available", getval, "Generate Report tested successfully.");
					self.generatereport(projectlist, addpdf);
				}, 2500);
			});
		}, 
		
		generatereport : function(projectlist, addpdf) {
			$.mockjaxClear(addpdf);
			var self=this;
			asyncTest("Test -Generate Report", function() {
				var printaspdf = $.mockjax({
					url: commonVariables.webserviceurl + 'app/printAsPdf?appDirName=wordpress-WordPress&'+$("input[name='generate']").closest("form").serialize()+'&userId=admin',
					type:'POST',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"service_exception":null,"data":null});
					}
				});
				
				var pdfshow = $.mockjax({
					url: commonVariables.webserviceurl + 'pdf/showPopUp?appDirName=wordpress-WordPress&fromPage=All',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200027","data":{"value":true,"json":[{"type":"crisp","fileName":"sasasas_crisp_20Sep2013_06.19PM.pdf","time":"Sep 20 2013 18.19"},{"type":"crisp","fileName":"sasasas_crisp_20Sep2013_06.17PM.pdf","time":"Sep 20 2013 18.18"},{"type":"detail","fileName":"sasasas_detail_20Sep2013_04.39PM.pdf","time":"Sep 20 2013 16.39"},{"type":"detail","fileName":"sdsdsd_detail.pdf","time":"Sep 20 2013 15.42"}]},"status":"success"});
					}
				});
				
				$("input[name='generate']").click();
				projectlist.projectslistListener.clickFunction('294187d7-f75a-4adc-bb25-ce9465e0e82f');
				setTimeout(function() {
					start();
					$("a[name=downLoad]").click();
					var getvalue = $('.existing_report').children('tbody').children('tr').attr('class');
					equal("generatedRow", getvalue, "Generate Report tested successfully.");
					self.projectDelPdfVerification(projectlist);
				}, 2500);
			});
		},

		projectDelPdfVerification : function(projectlist) {
			var self = this;
			asyncTest("Test - Delete Pdf", function() {
				var pdfshow = $.mockjax({
					url: commonVariables.webserviceurl + 'pdf/showPopUp?appDirName=wordpress-WordPress&fromPage=All',
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify([{"time":"Aug 23 2013 12.03","type":"detail","fileName":"yuimobilewidgetttt_detail_23Aug2013_12.03PM.pdf"}]);
					}
				});
				projectlist.projectslistListener.clickFunction('294187d7-f75a-4adc-bb25-ce9465e0e82f');
				$("a[namedel=delete]").click();
				$('input[name="delpdf"]').click();
				
				setTimeout(function() {
					start();
					var getval = $(commonVariables.contentPlaceholder).find("#noReport_294187d7-f75a-4adc-bb25-ce9465e0e82f").text();
					equal("No Reports are Available", getval, "Delete pdf successfully.");
					self.projectImportAppSuccessVerification(projectlist);
				}, 2500);
			});
		},
		
		projectImportAppSuccessVerification : function(projectlist) {
			var self = this;
			asyncTest("Import Application Success Service Test", function() {
				var importAppAjax = $.mockjax({
					url: commonVariables.webserviceurl + 'repository/importApplication?displayName=Admin',			
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
					self.addrepofailure(projectlist);
				}, 2500);
			});
		}, 
		
		addrepofailure : function(projectlist) {
			var self=this;
			asyncTest("Test -Add to Repo Failure", function() {
				$("input#repourl_294187d7-f75a-4adc-bb25-ce9465e0e82f").val("http://localhost:8080/framework/");
				$("input#uname_294187d7-f75a-4adc-bb25-ce9465e0e82f").val("admin");
				$("input#pwd_294187d7-f75a-4adc-bb25-ce9465e0e82f").val("manage");
				projectlist.projectslistListener.flag1 =1;
				projectlist.projectslistListener.addRepoEvent($("button[name='addrepobtn']"),"294187d7-f75a-4adc-bb25-ce9465e0e82f");
				setTimeout(function() {
					start();
					var getval = $(".success").text();	
					notEqual("Added successfully", getval, "Addrepo service call");
					self.addupdatefailure(projectlist);
				}, 2500);
			});
		}, 
		
		addupdatefailure : function(projectlist) {
			var self=this;
			asyncTest("Test -Update to Repo Failure", function() {
				$("#updateRepourl_294187d7-f75a-4adc-bb25-ce9465e0e82f").val("http://localhost:8080/framework/");
				$("#updateUsername_294187d7-f75a-4adc-bb25-ce9465e0e82f").val('admin');
				$("#updatePassword_294187d7-f75a-4adc-bb25-ce9465e0e82f").val('manage');
				$("#revision_294187d7-f75a-4adc-bb25-ce9465e0e82f").val('asdsad');
				projectlist.projectslistListener.flag1 =0;
				projectlist.projectslistListener.flag3 =1;
				projectlist.projectslistListener.addUpdateEvent ($("button[name='updatebtn']"),"294187d7-f75a-4adc-bb25-ce9465e0e82f","");
				$(".credential").attr('checked','checked');
				$(".credential").click();
				projectlist.projectslistListener.hideShowCredentials('git',$("#updateUsername_294187d7-f75a-4adc-bb25-ce9465e0e82f"),$("#updatePassword_294187d7-f75a-4adc-bb25-ce9465e0e82f"),$("#updateCredential_294187d7-f75a-4adc-bb25-ce9465e0e82f"));
				$("input[name='revision']").attr('checked','true');
				$("input[name='revision']").click();
				setTimeout(function() {
					start();
					var getval = $(".success").text();
					notEqual("Added successfully", getval, "Update service call");
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
				  url: commonVariables.webserviceurl +"project/delete?actionType=application",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Application deleted Successfully","exception":null,"data":null,"responseCode":"PHR200026"});
				  }				
				});
				$('.tooltiptop').click();
				setTimeout(function() {
					start();			
					$("input[name='deleteConfirm']").click();
					projectlist.deleterow("delete", "delete", "wordpress-WordPress" );					
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal(undefined, techid, "Project List Service Tested");
					self.projectholeDeleteSuccessVerification(projectlist);
				}, 1500);
			}); 
		},
		
		projectholeDeleteSuccessVerification : function(projectlist) {
			$('#content').empty();
			var self = this;
			$.mockjaxClear(self.projectlistdata);
			asyncTest("Test - Project whole Delete Service", function() {			
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
				  url: commonVariables.webserviceurl +"project/delete?actionType=project",
				  type: "DELETE",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Application deleted Successfully","exception":null,"data":null,"responseCode":"PHR200010"});
				  }				
				});
				$('.tooltiptop').click();
				setTimeout(function() {
					start();
					$("input[name='holeDelete']").click();
					projectlist.deleterow("delete", "delete", "wordpress-WordPress" );	
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
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId=photon&userId=admin&techId=tech-wordpress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"confuguration Template Fetched successfully","exception":null,"responseCode":null,"data":[{"envSpecific":true,"favourite":false,"templateName":"Scheduler"},{"envSpecific":true,"favourite":false,"templateName":"Server"},{"envSpecific":true,"favourite":false,"templateName":"Database"},{"envSpecific":true,"favourite":false,"templateName":"Email"},{"envSpecific":false,"favourite":false,"templateName":"SAP"}],"status":null});
					}
				});
				
				$.mockjax({
					url:  commonVariables.webserviceurl+commonVariables.configuration+"/types?customerId=photon&userId=admin&projectId=a58a5358-fa43-4fac-9b98-9bf94b7c4d1f",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":"confuguration Template Fetched successfully","exception":null,"responseCode":null,"data":[{"envSpecific":true,"favourite":false,"templateName":"Scheduler"},{"envSpecific":true,"favourite":false,"templateName":"Server"},{"envSpecific":true,"favourite":false,"templateName":"Database"},{"envSpecific":true,"favourite":false,"templateName":"Email"},{"envSpecific":false,"favourite":false,"templateName":"SAP"}],"status":null});
					}
				});
				
				
				$.mockjax({
					url:  commonVariables.webserviceurl+"project/editApplication?appDirName=wordpress-WordPress",
					type:'GET',
					contentType: 'application/json',
					status: 200,
					response: function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200009","data":{"multiModule":false,"preBuilt":true,"endDate":null,"startDate":null,"noOfApps":1,"appInfos":[{"modules":null,"pomFile":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"dependentModules":null,"created":false,"functionalFramework":null,"selectedFrameworks":null,"pilotInfo":null,"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"system":false,"creationDate":1365682608000,"helpText":null,"name":null,"id":"b279980e-390b-4d07-ad26-72fce77f88bf","displayName":null,"description":null,"status":null}],"selectedDatabases":[{"artifactGroupId":"downloads_mysql","artifactInfoIds":["26bb9f28-e847-4099-b255-429706ceb7b9"],"system":false,"creationDate":1365682608000,"helpText":null,"name":null,"id":"c50e90ff-dedb-40b6-bdcf-3776a35e9b68","displayName":null,"description":null,"status":null}],"pilot":true,"selectedModules":["8c978a8d-ebe0-4c18-8e11-5512c61488be","9e7cb57d-3b7d-46aa-9768-4c4ac19451cc","37701b35-aea1-456e-9de3-aa50f64b4705","04aea87f-3ccf-45dd-ad2f-e124a28755a9","9c7c7d7e-e0a8-4cbc-a98f-52504c7398bb","f31fa2af-602b-4eb6-954c-bd1af173017b","eaf60ba6-0259-4c19-b317-6d179585c497","f08fa865-c1ad-481c-b085-7c11b29966fb","173504a5-2c17-480d-af7f-9330dfbb0a5f","89cb6609-d5bc-412d-9291-8d63a5787931","283f9027-61ca-4b47-8a6b-3f2fba059aa7","0053ed62-5509-43d4-a213-bb9bdd1e98b0","8c6fcb31-b32b-4295-a443-414300c5431b","2d41a182-85f1-42a3-a67c-a0836792ba02","cae6a04c-9716-42ff-b730-f3e914d80924","519207c8-01d3-4e8a-9239-5b1c6ec78ad4","b49b6233-94ce-4c7f-802c-4e6d944ac3e4","3dddbeb8-a2b5-4b30-99f4-ddcebf69aa24","d15f5ed0-71d7-4193-9af8-f2316f203596","0286832e-c9f1-4b04-8014-caa923e78845","849eee10-758d-4d71-acf6-94a6653acab7","9b8e3c8c-902d-4c28-b716-b0efc93c0fb2","6ed21722-e683-4ec5-8a77-5f50cac908ae","a3133885-5528-487d-8346-12e8dc0ed7b1","4c5e64cd-263b-436d-abb1-4fe2de5c063b","6d3e706f-85af-4610-835c-eecae263bc5f","74134940-4284-47e8-89ba-56af8323bcae","dbb9ea5d-2a09-4747-93a9-cf5dcad5cdd9","bd317892-9e0a-43a3-95cd-e7dd3cb6a3e4","f20f6862-4a54-4384-845c-649d12aa94d7","d63b468e-8fa1-4690-afeb-852e80ea450d","43fef68c-43fc-4f2d-9eaa-3e2bd0f97fa3","5a5eb78f-f1de-4bfa-8ee6-1bccd5be29e5","98734103-53b2-43e4-a246-b9d9ebec2d30","b966ea3d-3ed0-47bb-8a0a-83cefc4ae41c","4888dbf7-2b73-46b7-b5c6-aa170bde0f8c","f9efa148-53c7-4e01-90b8-d9690121d9d9","f3968ed2-ce47-4396-8f91-5f7f8bb987b6","bc470e0d-f5a8-4725-8785-b1f35cff1c95","455d5918-35c2-4a00-bd9c-903fb533886f","3fbb4c66-2c8c-42f1-9f10-e98b884588f6","a8a705cb-5b5d-4891-9491-f7f0dd0d0d9a","2db250f7-b245-4a5b-bd20-15f4d588ca7b","8377b620-0de6-438b-b7a5-24dddc3149e1","9dda9452-3ee4-4e27-a583-060cf5ed51af","96c4daca-5136-47a9-8568-bf8b822b4000","b00ffc7f-2d2e-458f-a0c4-4fae63087567","e0603d8a-7f0e-4474-ad74-b66be8e217c6","24e6820d-a472-4488-8fdf-a510be5e2f53","105d4c61-91bb-45a5-848e-d961e2ec38be","ec95e483-8522-4dd5-9095-984886fda4b9","14d09a7a-26b9-4f3b-a4b9-75b95cb74501","91b006c7-555b-48d9-8b9c-04ea623a0195","b4ce2df7-71e7-4f34-bab2-d4f0ef3217e1","fea35cf5-94c2-41a6-a1b1-8093137d7a81","418dc224-1e27-4713-9c12-ee18264e4dfe","cb6d3306-704f-4627-a460-3096fb8816f0","a7896a17-eb7a-4262-8557-914e109ff020","49bf9ecd-ae7a-44cd-8dcb-d6cc532fdc64","2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedJSLibs":[],"selectedComponents":[],"selectedWebservices":null,"appDirName":"sone","techInfo":{"appTypeId":"e1af3f5b-7333-487d-98fa-46305b9dd6ee","techGroupId":"java","techVersions":null,"version":"1.6","used":false,"customerIds":null,"system":false,"creationDate":1355150953000,"helpText":null,"name":"java-tomcat","id":"tech-java-webservice","displayName":null,"description":null,"status":null},"functionalFrameworkInfo":null,"version":"1.0","code":"sone","used":false,"customerIds":["photon"],"system":true,"creationDate":1378364543000,"helpText":null,"name":"sone","id":"d17d8389-3201-4a5f-888a-e5c0a0c68e18","displayName":null,"description":null,"status":null}],"projectCode":"sss","version":"1.0","used":false,"customerIds":["photon"],"system":false,"creationDate":1378364557000,"helpText":null,"name":"sss","id":"fea563c3-7b50-4db1-beea-fd5512331309","displayName":null,"description":"","status":null},"status":"success"});
					}
				});
				
				$('a[name=editApplication]').click();
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