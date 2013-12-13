define(["navigation/navigation"], function() {

	return { 
		runTests: function (configData) {
			var self = this;
			module("navigation.js");
			
			asyncTest("Test - Navigation design", function() {
				setTimeout(function() {
					var length = $("#projectList").length;
					equal(length, 1, "Navigation Successfully Rendered");
					start();
					self.importPopupRenderTest();
				}, 1500);
			});
		},
		
		importPopupRenderTest : function() {
			var self = this;
			asyncTest("Import Application - Import popup render test", function() {
				$("#importApp").click();
				setTimeout(function() {
					var display = $("#project_list_import").css("display");
					equal(display, "block", "Import popup render tested");
					start();
					self.svnRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn repo url empty validation test", function() {
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import svn repo url empty validation tested");
					start();
					self.svnInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn invalid repo url validation test", function() {
				$('#importRepourl').val("sample url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import svn invalid repo url validation tested");
					start();
					self.svnUserNameValidation();
				}, 1500);
			});
		},
		
		svnUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn user name empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import svn user name empty validation tested");
					start();
					self.svnPwdValidation();
				}, 1500);
			});
		},
		
		svnPwdValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn password empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import svn password empty validation tested");
					start();
					self.importDotPhrescoCheckEventTest();
				}, 1500);
			});
		},
		
		importDotPhrescoCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import .phresco check event test", function() {
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, true, "Import .phresco check event tested");
					start();
					self.importDotPhrescoTabClickEventTest();
				}, 1500);
			});
		},
		
		importDotPhrescoTabClickEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import .phresco tab click event test", function() {
				$("#importDotPhrescoA").click();
				setTimeout(function() {
					equal($("#phrescoImportType").val(), $("#importType").val(), "Import .phresco tab click event tested");
					equal($("#testImportType").val(), $("#importType").val(), "Import .phresco tab click event tested");
					start();
					self.svnPhrescoRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnPhrescoRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Phresco repo url empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import svn Phresco repo url empty validation tested");
					start();
					self.svnPhrescoInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnPhrescoInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Phresco invalid repo url validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("sample phresco repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import svn Phresco invalid repo url validation tested");
					start();
					self.svnPhrescoUserNameValidation();
				}, 1500);
			});
		},
		
		svnPhrescoUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Phresco user name validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import svn Phresco user name validation tested");
					start();
					self.svnPhrescoPasswordValidation();
				}, 1500);
			});
		},
		
		svnPhrescoPasswordValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Phresco password validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import svn Phresco password validation tested");
					start();
					self.importTestCheckEventTest();
				}, 1500);
			});
		},
		
		importTestCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import test check event test", function() {
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTest").hasClass("active in");
					equal(hasClass, true, "Import test check event tested");
					start();
					self.svnTestRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnTestRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn test repo url empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').first().attr("checked", true);
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import svn test repo url empty validation tested");
					start();
					self.svnTestInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		svnTestInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn test invalid repo url validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').first().attr("checked", true);
				$('#importTestRepourl').val("sample test repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import svn test invalid repo url validation tested");
					start();
					self.svnTestUserNameValidation();
				}, 1500);
			});
		},
		
		svnTestUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn test user name validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').first().attr("checked", true);
				$('#importTestRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importTestUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import svn test user name validation tested");
					start();
					self.svnTestPasswordValidation();
				}, 1500);
			});
		},
		
		svnTestPasswordValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn test password validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').first().attr("checked", true);
				$('#importTestRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import svn test password validation tested");
					start();
					self.svnImportWithHeadOption();
				}, 1500);
			});
		},
		
		svnImportWithHeadOption : function() {
			var self = this;
			asyncTest("Import Application - Import svn project with head option test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"repository/importApplication?displayName=Admin",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200017","data":null,"status":"success"});
					}
				});
				
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').first().attr("checked", true);
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').first().attr("checked", true);
				$('#importTestRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("manage");
				$('input[name=testHeadoption]').first().attr("checked", true);
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import svn project with head option tested");
					start();
					self.svnRevisionCheckTest();
				}, 1500);
			});
		},
		
		svnRevisionCheckTest : function() {
			var self = this;
			asyncTest("Import Application - Import svn revision check event test", function() {
				$("#importApp").click();
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importDotPhrescoSrc').click();
				$('#importTestSrc').click();
				$('input[name=headoption]').last().click();
				$('input[name=phrescoHeadoption]').last().click();
				$('input[name=testHeadoption]').last().click();
				setTimeout(function() {
					equal($('#revision').attr('readonly'), undefined, "Import svn revision check event tested");
					equal($('#phrescoRevision').attr('readonly'), undefined, "Import svn .phresco revision check event tested");
					equal($('#testRevision').attr('readonly'), undefined, "Import svn test revision check event tested");
					start();
					self.svnRevisionValidation();
				}, 3000);
			});
		},
		
		svnRevisionValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn revision empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#revision').attr('placeholder');
					equal(errMsg, "Enter revision", "Import svn revision empty validation tested");
					start();
					self.svnPhrescoRevisionValidation();
				}, 1500);
			});
		},
		
		svnPhrescoRevisionValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Phresco revision empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#revision').val("5689455");
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#phrescoRevision').attr('placeholder');
					equal(errMsg, "Enter revision", "Import svn Phresco revision empty validation tested");
					start();
					self.svnTestRevisionValidation();
				}, 1500);
			});
		},
		
		svnTestRevisionValidation : function() {
			var self = this;
			asyncTest("Import Application - Import svn Test revision empty validation test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#revision').val("5689455");
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#phrescoRevision').val("9875634");
				$('#importTestRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("manage");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#testRevision').attr('placeholder');
					equal(errMsg, "Enter revision", "Import svn Test revision empty validation tested");
					start();
					self.svnImportWithRevisionOption();
				}, 1500);
			});
		},
		
		svnImportWithRevisionOption : function() {
			var self = this;
			asyncTest("Import Application - Import svn project with revision option test", function() {
				$('#importRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('input[name=headoption]').last().attr("checked", true);
				$('#revision').val("5689455");
				$('#importPhrescoRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('input[name=phrescoHeadoption]').last().attr("checked", true);
				$('#phrescoRevision').val("9875634");
				$('#importTestRepourl').val("https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/2.0/TestProject/");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("manage");
				$('input[name=testHeadoption]').last().attr("checked", true);
				$('#testRevision').val("6549875634");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import svn project with revision option tested");
					start();
					self.importTypeChangeToGitEveTest();
				}, 1500);
			});
		},
		
		importTypeChangeToGitEveTest : function() {
			var self = this;
			asyncTest("Import Application - Import type change to git event test", function() {
				$("#importApp").click();
				$("#importType").val("git");
				$("#importType").change();
				setTimeout(function() {
					var svnDataDisplay = $(".svndata").css("display");
					equal(svnDataDisplay, "none", "Import SVN data hidded");
					var perforceDataDisplay = $(".perforcedata").css("display");
					equal(perforceDataDisplay, "none", "Import Perforce data hidded");
					var importCredentialDisplay = $(".importCredential").css("display");
					equal(importCredentialDisplay, "none", "Import credential check box hidded");
					var gitDataDisplay = $(".gitdata").css("display");
					equal(gitDataDisplay, "table-row", "Import Git data shown");
					start();
					self.gitRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git repo url empty validation test", function() {
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import git repo url empty validation tested");
					start();
					self.gitInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git invalid repo url validation test", function() {
				$('#importRepourl').val("sample url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import git invalid repo url validation tested");
					start();
					self.importGitDotPhrescoCheckEventTest();
				}, 1500);
			});
		},
		
		importGitDotPhrescoCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import Git .phresco check event test", function() {
				$("#importDotPhrescoSrc").attr("checked", false);
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, true, "Import Git .phresco check event tested");
					start();
					self.gitPhrescoRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitPhrescoRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git Phresco repo url empty validation test", function() {
				$('#importRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importPhrescoRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import git Phresco repo url empty validation tested");
					start();
					self.gitPhrescoInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitPhrescoInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git Phresco invalid repo url validation test", function() {
				$('#importRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importPhrescoRepourl').val("sample phresco repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import git Phresco invalid repo url validation tested");
					start();
					self.importGitTestCheckEventTest();
				}, 1500);
			});
		},
		
		importGitTestCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import Git test check event test", function() {
				$("#importTestSrc").attr("checked", false);
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTest").hasClass("active in");
					equal(hasClass, true, "Import test check event tested");
					start();
					self.gitTestRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitTestRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git test repo url empty validation test", function() {
				$('#importRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importPhrescoRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importTestRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import git test repo url empty validation tested");
					start();
					self.gitTestInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		gitTestInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import git test invalid repo url validation test", function() {
				$('#importRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importPhrescoRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importTestRepourl').val("sample test repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import git test invalid repo url validation tested");
					start();
					self.gitImportTest();
				}, 1500);
			});
		},
		
		gitImportTest : function() {
			var self = this;
			asyncTest("Import Application - Import git project test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"repository/importApplication?displayName=Admin",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200017","data":null,"status":"success"});
					}
				});
				
				$('#importRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("https://github.com/santhosh-ja/TestProject_phresco.git");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("manage");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import git project tested");
					start();
					self.importTypeChangeToPerforceEveTest();
				}, 1500);
			});
		},
		
		importTypeChangeToPerforceEveTest : function() {
			var self = this;
			asyncTest("Import Application - Import type change to perforce event test", function() {
				$("#importApp").click();
				$("#importType").val("perforce");
				$("#importType").change();
				setTimeout(function() {
					var svnDataDisplay = $(".svndata").css("display");
					equal(svnDataDisplay, "none", "Import SVN data hidded");
					var perforceDataDisplay = $(".perforcedata").css("display");
					equal(perforceDataDisplay, "table-row", "Import Perforce data shown");
					var importCredentialDisplay = $(".importCredential").css("display");
					equal(importCredentialDisplay, "none", "Import credential check box hidded");
					var bitkeeperDataDisplay = $(".bitkeeperdata").css("display");
					equal(bitkeeperDataDisplay, "table-row", "Import Perforce data shown");
					start();
					self.perforceRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforceRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce repo url empty validation test", function() {
				$('#importRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import perforce repo url empty validation tested");
					start();
					self.perforceInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforceInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce invalid repo url validation test", function() {
				$('#importRepourl').val("sample:url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import perforce invalid repo url validation tested");
					start();
					self.perforceStreamValidation();
				}, 1500);
			});
		},
		
		perforceStreamValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce stream validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('.stream').attr('placeholder');
					equal(errMsg, "Enter Stream", "Import perforce stream validation tested");
					start();
					self.importPerforceDotPhrescoCheckEventTest();
				}, 1500);
			});
		},
		
		importPerforceDotPhrescoCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import perforce .phresco check event test", function() {
				$("#importDotPhrescoSrc").attr("checked", false);
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, true, "Import perforce .phresco check event tested");
					start();
					self.perforcePhrescoRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforcePhrescoRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce phresco repo url empty validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('.stream').val("sample stream");
				$('#importPhrescoRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import perforce phresco repo url empty validation tested");
					start();
					self.perforcePhrescoInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforcePhrescoInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce invalid phresco repo url validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('#importPhrescoRepourl').val("sample:url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import perforce invalid phresco repo url validation tested");
					start();
					self.perforcePhrescoStreamValidation();
				}, 1500);
			});
		},
		
		perforcePhrescoStreamValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce phresco stream validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('#importPhrescoRepourl').val("localhost:2468");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('.phrescoStream').attr('placeholder');
					equal(errMsg, "Enter Stream", "Import perforce phresco stream validation tested");
					start();
					self.importPerforceTestCheckEventTest();
				}, 1500);
			});
		},
		
		importPerforceTestCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import Perforce test check event test", function() {
				$("#importTestSrc").attr("checked", false);
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTest").hasClass("active in");
					equal(hasClass, true, "Import test check event tested");
					start();
					self.perforceTestRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforceTestRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce test repo url empty validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('.stream').val("sample stream");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('.phrescoStream').val("sample stream");
				$('#importTestRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import perforce test repo url empty validation tested");
					start();
					self.perforceTestInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		perforceTestInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce invalid test repo url validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('.stream').val("sample stream");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('.phrescoStream').val("sample stream");
				$('#importTestRepourl').val("sample:url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import perforce invalid test repo url validation tested");
					start();
					self.perforceTestStreamValidation();
				}, 1500);
			});
		},
		
		perforceTestStreamValidation : function() {
			var self = this;
			asyncTest("Import Application - Import perforce test stream validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('.stream').val("sample stream");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('.phrescoStream').val("sample stream");
				$('#importTestRepourl').val("localhost:2468");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('.testStream').attr('placeholder');
					equal(errMsg, "Enter Stream", "Import perforce test stream validation tested");
					start();
					self.perforceImportTest();
				}, 1500);
			});
		},
		
		perforceImportTest : function() {
			var self = this;
			asyncTest("Import Application - Import perforce project test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"repository/importApplication?displayName=Admin",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200017","data":null,"status":"success"});
					}
				});
				
				$('#importRepourl').val("localhost:2468");
				$('.stream').val("sample stream");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('.phrescoStream').val("sample stream");
				$('#importTestRepourl').val("localhost:2468");
				$('.testStream').val("sample stream");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import perforce project tested");
					start();
					self.importTypeChangeToBitkeeperEveTest();
				}, 1500);
			});
		},
		
		importTypeChangeToBitkeeperEveTest : function() {
			var self = this;
			asyncTest("Import Application - Import type change to bitkeeper event test", function() {
				$("#importApp").click();
				$("#importType").val("bitkeeper");
				$("#importType").change();
				setTimeout(function() {
					var svnDataDisplay = $(".svndata").css("display");
					equal(svnDataDisplay, "none", "Import SVN data hidded");
					var importCredentialDisplay = $(".importCredential").css("display");
					equal(importCredentialDisplay, "none", "Import credential check box hidded");
					var bitkeeperDataDisplay = $(".bitkeeperdata").css("display");
					equal(bitkeeperDataDisplay, "table-row", "Import Perforce data shown");
					start();
					self.bitkeeperRepoUrlValidation();
				}, 1500);
			});
		},
		
		bitkeeperRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper repo url empty validation test", function() {
				$('#importRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import bitkeeper repo url empty validation tested");
					start();
					self.importBitkeeperDotPhrescoCheckEventTest();
				}, 1500);
			});
		},
		
		importBitkeeperDotPhrescoCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper .phresco check event test", function() {
				$("#importDotPhrescoSrc").attr("checked", false);
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, true, "Import bitkeeper .phresco check event tested");
					start();
					self.bitkeeperPhrescoRepoUrlValidation();
				}, 1500);
			});
		},
		
		bitkeeperPhrescoRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper phresco repo url empty validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('#importPhrescoRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import bitkeeper phresco repo url empty validation tested");
					start();
					self.importBitkeeperTestCheckEventTest();
				}, 1500);
			});
		},
		
		importBitkeeperTestCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper test check event test", function() {
				$("#importTestSrc").attr("checked", false);
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTest").hasClass("active in");
					equal(hasClass, true, "Import bitkeeper test check event tested");
					start();
					self.bitkeeperTestRepoUrlValidation();
				}, 1500);
			});
		},
		
		bitkeeperTestRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper test repo url empty validation test", function() {
				$('#importRepourl').val("localhost:2468");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('#importTestRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import bitkeeper test repo url empty validation tested");
					start();
					self.bitkeeperImportTest();
				}, 1500);
			});
		},
		
		bitkeeperImportTest : function() {
			var self = this;
			asyncTest("Import Application - Import bitkeeper project test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"repository/importApplication?displayName=Admin",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200017","data":null,"status":"success"});
					}
				});
				
				$('#importRepourl').val("localhost:2468");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("localhost:2468");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("localhost:2468");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("manage");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import bitkeeper project tested");
					start();
					self.importTypeChangeToTfsEveTest();
				}, 1500);
			});
		},
		
		importTypeChangeToTfsEveTest : function() {
			var self = this;
			asyncTest("Import Application - Import type change to tfs event test", function() {
				$("#importApp").click();
				$("#importType").val("tfs");
				$("#importType").change();
				setTimeout(function() {
					var svnDataDisplay = $(".svndata").css("display");
					equal(svnDataDisplay, "table-row", "Import SVN data shown");
					var svnDataOptDisplay = $(".svnheadopt").css("display");
					equal(svnDataOptDisplay, "none", "Import SVN data hided");
					var perforceDataDisplay = $(".perforcedata").css("display");
					equal(perforceDataDisplay, "none", "Import Perforce data hidded");
					var importCredentialDisplay = $(".importCredential").css("display");
					equal(importCredentialDisplay, "none", "Import credential check box hidded");
					var gitDataDisplay = $(".gitdata").css("display");
					equal(gitDataDisplay, "none", "Import Git data shown");
					var tfsDataDisplay = $(".tfsdata").css("display");
					equal(tfsDataDisplay, "table-row", "Import tfs data shown");
					start();
					self.tfsRepoUrlValidation();
				}, 1500);
			});
		},
		
		
		tfsRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs repo url empty validation test", function() {
				$('#importRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import tfs repo url empty validation tested");
					start();
					self.tfsInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		tfsInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs invalid repo url validation test", function() {
				$('#importRepourl').val("sample url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import tfs invalid repo url validation tested");
					start();
					self.tfsUserNameValidation();
				}, 1500);
			});
		},
		
		tfsUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs user name empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import tfs user name empty validation tested");
					start();
					self.tfsPwdValidation();
				}, 1500);
			});
		},
		
		tfsPwdValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs password empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import tfs password empty validation tested");
					start();
					self.importTfsDotPhrescoCheckEventTest();
				}, 1500);
			});
		},
		
		importTfsDotPhrescoCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import Tfs .phresco check event test", function() {
				$("#importDotPhrescoSrc").attr("checked", false);
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, true, "Import Tfs .phresco check event tested");
					start();
					self.tfsPhrescoRepoUrlValidation();
				}, 1500);
			});
		},
		
		tfsPhrescoRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import Tfs Phresco repo url empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import tfs Phresco repo url empty validation tested");
					start();
					self.tfsPhrescoInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		tfsPhrescoInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs Phresco invalid repo url validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("sample phresco repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import tfs Phresco invalid repo url validation tested");
					start();
					self.tfsPhrescoUserNameValidation();
				}, 1500);
			});
		},
		
		tfsPhrescoUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs user name empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import tfs user name empty validation tested");
					start();
					self.tfsPhrescoPwdValidation();
				}, 1500);
			});
		},
		
		tfsPhrescoPwdValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs password empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importPhrescoPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import tfs password empty validation tested");
					start();
					self.importTfsTestCheckEventTest();
				}, 1500);
			});
		},
		
		importTfsTestCheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import Tfs test check event test", function() {
				$("#importTestSrc").attr("checked", false);
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTest").hasClass("active in");
					equal(hasClass, true, "Import test check event tested");
					start();
					self.tfsTestRepoUrlValidation();
				}, 1500);
			});
		},
		
		tfsTestRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs test repo url empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Enter Url", "Import tfs test repo url empty validation tested");
					start();
					self.tfsTestInvalidRepoUrlValidation();
				}, 1500);
			});
		},
		
		tfsTestInvalidRepoUrlValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs test invalid repo url validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("sample test repo url");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestRepourl').attr('placeholder');
					equal(errMsg, "Invalid Repo Url", "Import tfs test invalid repo url validation tested");
					start();
					self.tfsTestUserNameValidation();
				}, 1500);
			});
		},
		
		tfsTestUserNameValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs user name empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importTestUserName').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestUserName').attr('placeholder');
					equal(errMsg, "Enter user name", "Import tfs user name empty validation tested");
					start();
					self.tfsTestPwdValidation();
				}, 1500);
			});
		},
		
		tfsTestPwdValidation : function() {
			var self = this;
			asyncTest("Import Application - Import tfs password empty validation test", function() {
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("admin");
				$('#importPassword').val("manage");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("admin");
				$('#importPhrescoPassword').val("manage");
				$('#importTestRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importTestUserName').val("admin");
				$('#importTestPassword').val("");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					var errMsg = $('#importTestPassword').attr('placeholder');
					equal(errMsg, "Enter password", "Import tfs password empty validation tested");
					start();
					self.tfsImportTest();
				}, 1500);
			});
		},
		
		tfsImportTest : function() {
			var self = this;
			asyncTest("Import Application - Import tfs project test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"repository/importApplication?displayName=Admin",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200017","data":null,"status":"success"});
					}
				});
				
				$('#importRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importUserName').val("vivekraja.vasudevan@photoninfotech.com");
				$('#importPassword').val("phresco123");
				$('#projcName').val("raj");
				$('#servPath').val("raj/jsa");
				$('#importPhrescoRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importPhrescoUserName').val("vivekraja.vasudevan@photoninfotech.com");
				$('#importPhrescoPassword').val("phresco123");
				$('#importTestRepourl').val("https://vivekraja.visualstudio.com/DefaultCollection");
				$('#importTestUserName').val("vivekraja.vasudevan@photoninfotech.com");
				$('#importTestPassword').val("phresco123");
				$("button[name='importbtn']").click();
				setTimeout(function() {
					equal($("#project_list_import").css("display"), "none", "Import TFS project tested");
					start();
					self.importCredentialCheckEveTest();
				}, 1500);
			});
		},
		
		importCredentialCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Credential check event test", function() {
				var checkbox = $("#importCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importUserName').attr("readonly");
					var userPwdReadonly = $('#importPassword').attr("readonly");
					equal(userNameReadonly, undefined, "User Name readonly attribute removed");
					equal(userPwdReadonly, undefined, "Password readonly attribute removed");
					start();
					self.importCredentialUncheckEveTest()
				}, 1500);
			});
		},
		
		importCredentialUncheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Credential uncheck event test", function() {
				var checkbox = $("#importCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importUserName').attr("readonly");
					var userPwdReadonly = $('#importPassword').attr("readonly");
					equal(userNameReadonly, "readonly", "User Name readonly attribute added");
					equal(userPwdReadonly, "readonly", "Password readonly attribute added");
					start();
					self.importPhrescoCredentialCheckEveTest();
				}, 1500);
			});
		},
		
		importPhrescoCredentialCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Phresco Credential check event test", function() {
				var checkbox = $("#importPhrescoCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importPhrescoUserName').attr("readonly");
					var userPwdReadonly = $('#importPhrescoPassword').attr("readonly");
					equal(userNameReadonly, undefined, "Phresco User Name readonly attribute removed");
					equal(userPwdReadonly, undefined, "Phresco Password readonly attribute removed");
					start();
					self.importPhrescoCredentialUncheckEveTest()
				}, 1500);
			});
		},
		
		importPhrescoCredentialUncheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Phresco Credential uncheck event test", function() {
				var checkbox = $("#importPhrescoCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importPhrescoUserName').attr("readonly");
					var userPwdReadonly = $('#importPhrescoPassword').attr("readonly");
					equal(userNameReadonly, "readonly", "Phresco User Name readonly attribute added");
					equal(userPwdReadonly, "readonly", "Phresco Password readonly attribute added");
					start();
					self.importTestCredentialCheckEveTest();
				}, 1500);
			});
		},
		
		importTestCredentialCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Test Credential check event test", function() {
				var checkbox = $("#importTestCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importTestUserName').attr("readonly");
					var userPwdReadonly = $('#importTestPassword').attr("readonly");
					equal(userNameReadonly, undefined, "Test User Name readonly attribute removed");
					equal(userPwdReadonly, undefined, "Test Password readonly attribute removed");
					start();
					self.importTestCredentialUncheckEveTest()
				}, 1500);
			});
		},
		
		importTestCredentialUncheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Test Credential uncheck event test", function() {
				var checkbox = $("#importTestCredential").find('input[type=checkbox]');
				checkbox.click();
				setTimeout(function() {
					var userNameReadonly = $('#importTestUserName').attr("readonly");
					var userPwdReadonly = $('#importTestPassword').attr("readonly");
					equal(userNameReadonly, "readonly", "Test User Name readonly attribute added");
					equal(userPwdReadonly, "readonly", "Test Password readonly attribute added");
					start();
					self.importHeadOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importHeadOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Head Option check event test", function() {
				var option = $("input[name=headoption]").first();
				option.click();
				setTimeout(function() {
					var readonly = $("#revision").attr("readonly");
					equal(readonly, "readonly", "Head Option check event tested");
					start();
					self.importRevisionOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importRevisionOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Revision Option check event test", function() {
				var option = $("input[name=headoption]").last();
				option.click();
				setTimeout(function() {
					var readonly = $("#revision").attr("readonly");
					equal(readonly, undefined, "Revision Option check event tested");
					start();
					self.importPhrescoHeadOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importPhrescoHeadOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - .Phresco Head Option check event test", function() {
				var option = $("input[name=phrescoHeadoption]").first();
				option.click();
				setTimeout(function() {
					var readonly = $("#phrescoRevision").attr("readonly");
					equal(readonly, "readonly", ".Phresco Head Option check event tested");
					start();
					self.importPhrescoRevisionOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importPhrescoRevisionOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - .phresco Revision Option check event test", function() {
				var option = $("input[name=phrescoHeadoption]").last();
				option.click();
				setTimeout(function() {
					var readonly = $("#phrescoRevision").attr("readonly");
					equal(readonly, undefined, ".phresco revision Option check event tested");
					start();
					self.importDotPhrescoUncheckEventTest();
				}, 1500);
			});
		},
		
		importDotPhrescoUncheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import .phresco uncheck event test", function() {
				$("#importDotPhrescoSrc").click();
				setTimeout(function() {
					var hasClass = $("#importDotphresco").hasClass("active in");
					equal(hasClass, false, "Import .phresco uncheck event tested");
					start();
					self.importTestHeadOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importTestHeadOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Test Head Option check event test", function() {
				var option = $("input[name=testHeadoption]").first();
				option.change();
				setTimeout(function() {
					var readonly = $("#testRevision").attr("readonly");
					equal(readonly, "readonly", "Test Head Option check event tested");
					start();
					self.importTestRevisionOptionCheckEveTest();
				}, 1500);
			});
		},
		
		importTestRevisionOptionCheckEveTest : function() {
			var self = this;
			asyncTest("Import Application - Test Revision Option check event test", function() {
				var option = $("input[name=testHeadoption]").last();
				option.click();
				setTimeout(function() {
					var readonly = $("#testRevision").attr("readonly");
					equal(readonly, undefined, "test revision Option check event tested");
					start();
					self.importTestUncheckEventTest();
				}, 1500);
			});
		},
		
		importTestUncheckEventTest : function() {
			var self = this;
			asyncTest("Import Application - Import test uncheck event test", function() {
				$("#importTestSrc").click();
				setTimeout(function() {
					var hasClass = $("#importTestSrc").hasClass("active in");
					equal(hasClass, false, "Import test uncheck event tested");
					start();
//					require(["projectlistTest"], function(projectlistTest){
//						projectlistTest.runTests();
//					});
				}, 1500);
			});
		},
	};
});
