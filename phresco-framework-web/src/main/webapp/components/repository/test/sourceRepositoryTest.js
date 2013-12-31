define(["repository/sourceRepository"], function(SourceRepository) {
	return { 
		runTests : function (configData) {
			module("sourceRepository.js");
			var self = this;
			
			asyncTest("Source Repository - Template Render Test", function() {
			
				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				
				commonVariables.projectCode = "test-query";
				commonVariables.api.localVal.setSession('projectId', "b95a4b23-c7a9-47db-b5a6-f35c6fdb8568");
				$.mockjax({
					  url: commonVariables.webserviceurl +"repository/browseSourceRepo?userId=admin&customerId=photon&projectId=b95a4b23-c7a9-47db-b5a6-f35c6fdb8568&userName=admin&password=manage",
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  status: 200,
					  response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR00001","data":["<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><item name=\"rootItem\" type=\"folder\"><item name=\"https://github.com/kumar-s/maven-release-plugin-test.git\" type=\"folder\"><item name=\"branches\" type=\"folder\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"><item name=\"agra\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"boston\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"firstbranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"gitnewbranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"master\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"masterbranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"my-branch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"my-branch1\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"newbranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"possitive\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday1branch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday2branch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturdaybranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"superbranch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"suresh-branch\" nature=\"branches\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/></item><item name=\"tags\" type=\"folder\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"><item name=\"1.0\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"1.10tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"1.11-tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"1.11-tag2\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"1.12-tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"demo\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"four\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"myfirsttag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"newTag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.0.0\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.1\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.2\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.3\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.4\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.6\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.7\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.8\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"release-plugin-test-1.9\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday2tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday3tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday4tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturday5tag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/><item name=\"saturdaytag\" nature=\"tags\" type=\"file\" url=\"https://github.com/kumar-s/maven-release-plugin-test.git\"/></item></item></item></root>"],"status":"success"});
					  }	
				});
				
				$("#editprojecttitle").attr('projectname', 'TestJquery');
				commonVariables.api.localVal.setProjectInfo(null);
				commonVariables.navListener.onMytabEvent("sourceRepo");
				setTimeout(function() {
					start();
					var len = $('input[name=rep_release]').length;
					equal(len, 1, "Source Repository test template rendering tested");
					self.createBranchDisplayTest();
				}, 3000);
			});
		},

		createBranchDisplayTest : function() {
			var self = this;
			asyncTest("Source Repository - Create branch page display test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl +"repository/version?url=https://github.com/kumar-s/maven-release-plugin-test.git&currentbranchname=agra",
					type: "GET",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR00003","data":"1.11-SNAPSHOT","status":"success"});
					  }	
				});
				
				$('a[url="https://github.com/kumar-s/maven-release-plugin-test.git"]').first().parent().parent().find('.badge-warning').click();
				setTimeout(function() {
					start();
					equal($(".file_view").css("display"), "block", "Create branch display tested");
					self.createBtnClickTest();
				}, 1500);
			}); 
		},
		
		createBtnClickTest : function() {
			var self = this;
			asyncTest("Source Repository - Create popup display test", function() {			
				$("input[name=rep_create]").click();
				setTimeout(function() {
					start();
					equal($("#rep_create").css("display"), "block", "Create popup display tested");
					self.versionPopupRenderTest();
				}, 1500);
			}); 
		},
		
		versionPopupRenderTest : function() {
			var self = this;
			asyncTest("Source Repository - Version popup display test", function() {			
				$("#createBranchVersion").click();
				setTimeout(function() {
					start();
					equal("", "", "Version popup display tested");
					self.submitVersionTest();
				}, 1500);
			}); 
		},
		
		submitVersionTest : function() {
			var self = this;
			asyncTest("Source Repository - Submit version test", function() {			
				$("#submitVersion").click();
				setTimeout(function() {
					start();
					equal($("#version_popup").is(":visible"), false, "Submit version tested");
					self.createBranchBranchNameValidationTest();
				}, 1500);
			}); 
		},
		
		createBranchBranchNameValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Branch Branch name validation test", function() {			
				$("#createBranch").click();
				setTimeout(function() {
					start();
					equal($("#newBranchName").attr("placeholder"), "Enter branch name", "Branch name validation tested");
					self.createBranchUserNameValidationTest();
				}, 1500);
			}); 
		},
		
		createBranchUserNameValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Branch User name validation test", function() {
				$("#newBranchName").val("branch name");	
				$("#createBranch").click();
				setTimeout(function() {
					start();
					equal($("#branchUsername").attr("placeholder"), "Enter Username", "User name validation tested");
					self.createBranchPasswordValidationTest();
				}, 1500);
			}); 
		},
		
		createBranchPasswordValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Branch Password validation test", function() {
				$("#newBranchName").val("test");
				$("#branchUsername").val("admin");
				$("#createBranch").click();
				setTimeout(function() {
					start();
					equal($("#branchPassword").attr("placeholder"), "Enter Password", "Password validation tested");
					self.createBranchTest();
				}, 1500);
			}); 
		},
		
		createBranchTest : function() {
			var self = this;
			asyncTest("Source Repository - Create branch test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl +"repository/createBranch?url=https://github.com/kumar-s/maven-release-plugin-test.git&version=1.0.0-SNAPSHOT&username=admin&password=manage&comment=&currentbranchname=agra&branchname=test&downloadoption=false",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR00002","data":null,"status":"success"});
					  }	
				});
				$("#newBranchName").val("test");
				$("#branchUsername").val("admin");
				$("#branchPassword").val("manage");
				$("#createBranch").click();
				setTimeout(function() {
					start();
					equal($(".tree_view").css("display"), "block", "Create branch tested");
					self.createBtnClickForTagTest();
				}, 1500);
			}); 
		},
		
		createBtnClickForTagTest : function() {
			var self = this;
			asyncTest("Source Repository - Create popup display test", function() {			
				$("input[name=rep_create]").click();
				setTimeout(function() {
					start();
					equal($("#rep_create").css("display"), "block", "Create popup display tested");
					self.tagTabClickTest();
				}, 1500);
			}); 
		},
		
		tagTabClickTest : function() {
			var self = this;
			asyncTest("Source Repository - Tag tab display test", function() {			
				$("a[href='#tags']").click();
				setTimeout(function() {
					start();
					equal($("#tags").css("display"), "block", "Tag tab display tested");
					self.createTagTagNameValidationTest();
				}, 1500);
			}); 
		},
		
		createTagTagNameValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Tag Tag name validation test", function() {			
				$("#createTag").click();
				setTimeout(function() {
					start();
					equal($("#tagName").attr("placeholder"), "Enter Tag name", "Tag name validation tested");
					self.createTagUserNameValidationTest();
				}, 1500);
			}); 
		},
		
		createTagUserNameValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Tag User name validation test", function() {
				$("#tagName").val("testTag");	
				$("#createTag").click();
				setTimeout(function() {
					start();
					equal($("#tagUsername").attr("placeholder"), "Enter Username", "User name validation tested");
					self.createTagPasswordValidationTest();
				}, 1500);
			}); 
		},
		
		createTagPasswordValidationTest : function() {
			var self = this;
			asyncTest("Source Repository - Tag Password validation test", function() {
				$("#tagName").val("testTag");
				$("#tagUsername").val("admin");
				$("#createTag").click();
				setTimeout(function() {
					start();
					equal($("#tagPassword").attr("placeholder"), "Enter Password", "Password validation tested");
					self.createTagTest();
				}, 1500);
			}); 
		},
		
		createTagTest : function() {
			var self = this;
			asyncTest("Source Repository - Tag Password validation test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl +		"repository/createTag?url=https://github.com/kumar-s/maven-release-plugin-test.git&username=admin&password=manage&version=&comment=&currentbranchname=agra&tagname=testTag&downloadoption=false",
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					status: 200,
					response : function() {
						this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRSR00002","data":null,"status":"success"});
					}	
				});
				
				$("#tagName").val("testTag");
				$("#tagUsername").val("admin");
				$("#tagPassword").val("manage");
				$("#createTag").click();
				setTimeout(function() {
					start();
					equal($(".tree_view").css("display"), "block", "Create tag tested");
				}, 1500);
			}); 
		}
	};
});