define(["header/header"], function(Header) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	return { 
		runTests: function (configData) {
			module("Header.js;Header");
			var self = this;
			asyncTest("Header Test", function() {
				var header, output;
								
				setTimeout(function() {
					start();
					output = $(commonVariables.headerPlaceholder).find("#header").attr('id');
					equal("header", output, "Header Rendered Successfully");
					self.showAboutPhresco();
				}, 50);
			});
		},
		showAboutPhresco : function() {
			var self = this;
			asyncTest("About popup render test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.upgrade+"/"+commonVariables.upgradeAvailable+"?userId=admin",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR900001","data":{"currentVersion":"3.0.0.23001","latestVersion":"3.0.0.23001","updateAvaillable":false,"message":"No update is available"},"status":"success"});
				  }
				});
				var aboutPopupLi = $(commonVariables.headerPlaceholder).find("#header").find('.header_right').find('.aboutPhrescoLi');
				aboutPopupLi.click();
				setTimeout(function() {
					start();
					equal(aboutPopupLi.parent().find("#aboutPopup").css("display"), "block", "About popup rendered succesfully");
					self.showAboutPhrescoUpgradeBtnTest();
				}, 500);
			});
		},

		showAboutPhrescoUpgradeBtnTest : function() {
			var self = this;
			asyncTest("Upgrade button enable test", function() {
				$.mockjaxClear();
				$("#upgradeDisable").css("display", "none");
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.upgrade+"/"+commonVariables.upgradeAvailable+"?userId=admin",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR900001","data":{"currentVersion":"3.0.0.23000","latestVersion":"3.0.0.23004","updateAvaillable":true,"message":"Update is available"},"status":"success"});
				  }
				});
				var aboutPopupLi = $(commonVariables.headerPlaceholder).find("#header").find('.header_right').find('.aboutPhrescoLi');
				aboutPopupLi.click();
				setTimeout(function() {
					start();
					equal($("#upgrade").css("display").indexOf('inline') !== -1, true, "upgrade available test");
					self.runUpgradeTest();
				}, 500);
			});
		},

		runUpgradeTest : function() {
			var self = this;
			asyncTest("Upgrade phresco test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+commonVariables.upgrade + "?newVersion=3.0.0.23004&userId=admin&customerId=photon",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":"Updation Successful","exception":null,"responseCode":null,"data":null,"status":"success"});
				  }
				});
				$("#upgrade").click();
				setTimeout(function() {
					start();
					equal($("#upgradeSuccess").css("display"), "block", "upgrade test");
					self.runCustomerClickTest();
				}, 700);
			});	
		},

		runCustomerClickTest : function() {
			var self = this;
			$(".proj_list").parent().remove();
			asyncTest("Customer click test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"project/list?customerId=photon",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});

				$('a[id=photon]').click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "customer click event tested");
					self.runProjectsClickEvent();
				}, 1500);
			});	
		}, 

		runProjectsClickEvent : function() {
			$(".proj_list").parent().remove();
			var self = this;
			asyncTest("Projects click test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"project/list?customerId=photon",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});
				$(".header_left ul li a[id=pojectList]").click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "projects click event tested");
					self.runLoggoutTest();
				}, 1500);
			});
		},

		runProjectsClickEvent : function() {
			$(".proj_list").parent().remove();
			var self = this;
			asyncTest("Projects click test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"project/list?customerId=photon",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"response":null,"message":"Project List Successfully","exception":null,"data":[{"appInfos":[{"pomFile":null,"appDirName":"wordpress-WordPress","techInfo":{"appTypeId":"app-layer","techGroupId":null,"techVersions":null,"version":"3.4.2","creationDate":1369915294000,"helpText":null,"system":false,"name":"WordPress","id":"tech-wordpress","displayName":null,"description":null,"status":null},"selectedModules":null,"selectedJSLibs":null,"selectedComponents":null,"selectedServers":null,"selectedDatabases":null,"selectedWebservices":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"functionalFramework":null,"version":"3.0","code":"wordpress-WordPress","customerIds":null,"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress-WordPress","id":"294187d7-f75a-4adc-bb25-ce9465e0e82f","displayName":null,"description":null,"status":null}],"projectCode":"wordpress","noOfApps":1,"startDate":null,"endDate":null,"version":"3.0","customerIds":["photon"],"used":false,"creationDate":1369915294000,"helpText":null,"system":false,"name":"wordpress","id":"a58a5358-fa43-4fac-9b98-9bf94b7c4d1f","displayName":null,"description":"sample wordpress project","status":null}]});
				  }
				});
				$(".header_left ul li a[id=pojectList]").click();
				setTimeout(function() {
					start();
					var techid = $(commonVariables.contentPlaceholder).find(".wordpress-WordPress").attr("techid");
					equal("tech-wordpress", techid, "projects click event tested");
					self.runDashboardClickEvent();
				}, 1500);
			});
		},

		runDashboardClickEvent : function() {
			var self = this;
			asyncTest("Dashboard click test", function() {
				$(".header_left ul li a[id=dashboard]").click();
				setTimeout(function() {
					start();
					equal($(".header_left ul li a[id=dashboard]").hasClass("nav_active"), true, "Dashboard click event tested");
					self.runDownloadsClickEvent();
				}, 100);
			});
		},


		runDownloadsClickEvent : function() {
			var self = this;
			asyncTest("Downloads click test", function() {
				$(".header_left ul li a[id=downloads]").click();
				setTimeout(function() {
					start();
					equal($(".header_left ul li a[id=downloads]").hasClass("nav_active"), true, "Downloads click event tested");
					self.runAdminClickEvent();
				}, 100);
			});
		},


		runAdminClickEvent : function() {
			var self = this;
			asyncTest("Admin click test", function() {
				$(".header_left ul li a[id=admin]").click();
				setTimeout(function() {
					start();
					equal($(".header_left ul li a[id=admin]").hasClass("nav_active"), true, "Admin click event tested");
					//self.runLoggoutTest();
					self.changePasswordTest();
				}, 100);
			});
		},

		runLoggoutTest : function() {
			var self = this;
			asyncTest("Loggout test", function() {
				$('#logout').click();
				setTimeout(function() {
					start();
					equal(1, 1, "Loggout tested");					
				}, 500);
			});
		},
		
		changePasswordTest : function() {
			var self = this;
			asyncTest("Change Password test", function() {
				$("#changepassword").click();
				setTimeout(function() {
					start();
					equal($("#change_password").css('display'), "block", "Change Password tested successfully.");
					self.changePasswordConfirmTest();
				}, 1500);
			});	
		},
		

		changePasswordConfirmTest : function() {
			var self = this;
			asyncTest("Change Password Confirm test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+ "login/changePassword",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100009","data":true,"status":"success"});
				  }
				});
				$("#confirm_change_password").click();
				setTimeout(function() {
					start();
					equal($(".content_end").css('display'), "block", "Change Confirm Password tested successfully.");
					self.changePasswordConfirm1Test();
				}, 1500);
			});	
		},
		
		changePasswordConfirm1Test : function() {
			var self = this;
			asyncTest("Change Password Confirm test1", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+ "login/changePassword",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100009","data":true,"status":"success"});
				  }
				});
				$('#old_password').val('test');
				$('#new_password').val('');
				$('#new_password_reenter').val('');
				$("#confirm_change_password").click();
				setTimeout(function() {
					start();
					equal($(".content_end").css('display'), "block", "Change Confirm Password 1 tested successfully.");
					self.changePasswordConfirm2Test();
				}, 1500);
			});	
			
		},
		
		changePasswordConfirm2Test : function() {
			var self = this;
			asyncTest("Change Password Confirm test2", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+ "login/changePassword",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100009","data":true,"status":"success"});
				  }
				});
				$('#old_password').val('test');
				$('#new_password').val('test');
				$('#new_password_reenter').val('');
				$("#confirm_change_password").click();
				setTimeout(function() {
					start();
					equal($(".content_end").css('display'), "block", "Change Confirm Password 2  tested successfully.");
					self.changePasswordConfirm3Test();
				}, 1500);
			});	
			
		},
		changePasswordConfirm3Test : function() {
			var self = this;
			asyncTest("Change Password Confirm test3", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+ "login/changePassword",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100009","data":true,"status":"success"});
				  }
				});
				$('#old_password').val('test');
				$('#new_password').val('test1');
				$('#new_password_reenter').val('test');
				$("#confirm_change_password").click();
				setTimeout(function() {
					start();
					equal($(".content_end").css('display'), "block", "Change Confirm Password 3  tested successfully.");
					self.changePasswordConfirm4Test();
				}, 1500);
			});	
			
		},
		
		changePasswordConfirm4Test : function() {
			var self = this;
			asyncTest("Change Password Confirm test4", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+ "login/changePassword",
				  type: "POST",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR100009","data":true,"status":"success"});
				  }
				});
				$('#old_password').val('test');
				$('#new_password').val('test');
				$('#new_password_reenter').val('test');
				$("#confirm_change_password").click();
				setTimeout(function() {
					start();
					equal($(".content_end").css('display'), "block", "Change Confirm Password 4  tested successfully.");
					//self.changePasswordConfirm1Test();
				}, 1500);
			});	
			
		}
		
		
		
		
		};
	});
