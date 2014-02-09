define(["unitTest/unitTest"], function(UnitTest) {
	return { 
		runTests : function (configData) {
			module("UnitTest.js");
			var unitTest = new UnitTest(), self = this, unitTestMock, testsuitesMock, parametersMock, executeTestMock;
			
			asyncTest("Unit Test Template Render Test", function() {
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ100001","data":{"reportOptions":["js","java"],"projectModules":["phresco-framework","phresco-framework-impl", "phresco-framework-web"]},"status":"success"});
				  	}
				});

				self.testsuitesMock = $.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=unit&moduleName=phresco-framework&techReport=js",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ010001","data":null,"status":"success"});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});

				$('.hProjectId').val("7406c2c3-4de1-4d19-a709-961764a65485");
				commonVariables.api.localVal.setProjectInfo({"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"cccccc","appDirName":"test","techInfo":{"version":"7.x","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"Black Berry","techVersions":null,"customerIds":null,"used":false,"name":"mobile-app-bb10","id":"tech-blackberry-hybrid","displayName":null,"status":null,"description":null,"creationDate":1378204910000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":[],"selectedComponents":[],"selectedServers":null,"selectedDatabases":null,"selectedJSLibs":["4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedWebservices":null,"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":false,"dependentModules":null,"created":false,"customerIds":null,"used":false,"name":"cccccc","id":"5bc15f33-faba-49fc-95fa-bcc9b3d0e93a","displayName":null,"status":null,"description":null,"creationDate":1378204910000,"helpText":null,"system":false}],"projectCode":"ccccccc","noOfApps":1,"startDate":null,"endDate":null,"preBuilt":false,"multiModule":false,"customerIds":["photon"],"used":false,"name":"ccccccc","id":"7406c2c3-4de1-4d19-a709-961764a65485","displayName":null,"status":null,"description":"","creationDate":1378204910000,"helpText":null,"system":false}},"status":"success"});

				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.unit_text').text().trim(), "Unit Test", "Unit test template rendering tested");
					self.techOptionsTest();
				}, 3000);
			});
		},
		
		techOptionsTest : function() {
			var self = this;
			asyncTest("Unit Test Technology Options Render Test", function() {
				setTimeout(function() {
					start();
					equal($('#reportOptionsDrop').text(), "js", "Unit test tech options rendering tested");
					self.moduleOptionsTest();
				}, 700);
			});
		},

		moduleOptionsTest : function() {
			var self = this;
			asyncTest("Unit Test Module Options Render Test", function() {
				setTimeout(function() {
					start();
					equal($('#modulesDrop').text(), "phresco-framework", "Unit test module options rendering tested");
					self.noTestsuitesTest();
				}, 700);
			});
		},

		noTestsuitesTest : function() {
			var self = this;
			asyncTest("Unit Test No Testsuites Test", function() {
				setTimeout(function() {
					start();
					equal($('#messagedisp').length, 1, "Unit test no testsuites tested");
					self.testsuitesRenderTest();
				}, 700);
			});
		},

		testsuitesRenderTest : function() {
			var self = this;
			asyncTest("Unit Test Testsuites Render Test", function() {
				$(commonVariables.contentPlaceholder).empty();
				$.mockjaxClear(self.unitTestMock);
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ100001","data":{"reportOptions":["js","java"],"projectModules":["phresco-framework","phresco-framework-impl", "phresco-framework-web"]},"status":"success"});
				  	}
				});

				$.mockjaxClear(self.testsuitesMock);
				self.testsuitesMock = $.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testsuites?appDirName=test&testType=unit&moduleName=phresco-framework&techReport=js",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":"Test Suites listed successfully","exception":null,"data":[{"name":"SampleUnitTest","file":null,"total":1.0,"time":"0.003561","errors":0.0,"assertions":null,"success":1.0,"failures":0.0,"notApplicable":0.0,"blocked":0.0,"notExecuted":0.0,"testCoverage":0.0,"testCases":null,"tests":0.0,"testSteps":null}],"response":null});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.api.localVal.setSession("appDirName", "test");
				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test testsuites render tested");
					self.showTestsuiteConsole();
				}, 3000);
			});
		},

		showTestsuiteConsole : function() {
			var self = this;
			asyncTest("Unit Test Testsuite Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					equal($('#consoleImg').attr('data-flag'), "false", "Unit test testsuite open console tested");
					self.graphicalViewTest();
				}, 700);
			});
		},

		graphicalViewTest : function() {
			var self = this;
			asyncTest("Unit Test Testsuite Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testSuites").css("display"), "block", "Unit test testsuite graphical view tested");
					self.tabularViewTest();
				}, 700);
			});
		},

		tabularViewTest : function() {
			var self = this;
			asyncTest("Unit Test Testsuite Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Unit test testsuite tabular view tested");
					self.techOptionChangeTest();
				}, 700);
			});
		},

		techOptionChangeTest : function() {
			var self = this;
			asyncTest("Unit Test Tech Options Change Test", function() {
				$('#testResult').html("");
				$(commonVariables.contentPlaceholder).find('.reportOption:first').click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test tech options change tested");
					self.moduleOptionChangeTest();
				}, 700);
			});
		},

		moduleOptionChangeTest : function() {
			var self = this;
			asyncTest("Unit Test Module Options Change Test", function() {
				
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/techOptions?moduleName=phresco-framework&appDirName=test",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ100001","data":["js"],"status":"success"});
				  	}
				});
				
				$('#testResult').html("");
				$(commonVariables.contentPlaceholder).find('.projectModule:first').click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test module options change tested");
					self.runUnitTestWithNoParamTest();
				}, 1500);
			});
		},

		runUnitTestWithNoParamTest : function() {
			var self = this;
			asyncTest("Unit Test Run Test Without Parameters Test", function() {
				$.mockjax({
				  url: commonVariables.webserviceurl+"util/checkLock?actionType=unit&appId=6d6753e8-b081-48d8-9924-70a14f3663d4",
				  type: "GET",
				  dataType: "json",
				  contentType: "application/json",
				  status: 200,
				  response : function() {
					  this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR10C00002","data":null,"status":"success"});
				  }
				});
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[],"status":"success"});
				  	}
				});

				self.executeTestMock = $.mockjax({
					url: commonVariables.webserviceurl+"app/runUnitTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=unit-test&phase=unit-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&&displayName=Admin&appDirName=test",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});
				
				var projectInfo = {"message":null,"exception":null,"responseCode":"PHR200009","data":{"embedList":{},"projectInfo":{"version":"1.0","appInfos":[{"version":"1.0","modules":null,"pomFile":null,"code":"responsive-web","appDirName":"test","techInfo":{"version":"3.10.3","multiModule":false,"appTypeId":"1dbcf61c-e7b7-4267-8431-822c4580f9cf","techGroupId":"html5","techVersions":null,"customerIds":null,"used":false,"name":"responsive-web","id":"tech-html5-jquery-widget","displayName":null,"status":null,"description":null,"creationDate":1354009498000,"helpText":null,"system":false},"functionalFramework":null,"selectedModules":["2d41a182-85f1-42a3-a67c-a0836792ba02"],"selectedComponents":[],"selectedServers":[{"artifactGroupId":"downloads_apache-tomcat","artifactInfoIds":["0e34ab53-1b9e-493d-aa72-6ecacddc5338"],"name":null,"id":"1e22614f-0490-4600-9f2d-dba1d200e700","displayName":null,"status":null,"description":null,"creationDate":1365682608000,"helpText":null,"system":false}],"selectedDatabases":null,"selectedJSLibs":["402ded74-e007-4cdf-8fe5-ee11ca01b7db","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85","444cd5e9-6d16-4e38-8f95-c3f1d84f3c6e","bb9b5d04-2afe-4722-b87b-c1d9cdefbf8e","c4a8d772-305e-441a-993e-703e63795aac","c7008489-b264-442c-ad8c-2c422284d171","ceb6006b-b7aa-4600-9cdb-d52f5ad724ff","6afdf1d3-80f0-44a5-a9f5-843ce3db7ea0","deda98f8-c350-47f1-8b22-a0816a695127","4f889fa1-fe7a-4dee-8ed8-fb95605dcc85"],"selectedWebservices":["restjson"],"functionalFrameworkInfo":null,"pilotInfo":null,"selectedFrameworks":null,"emailSupported":false,"pilotContent":null,"embedAppId":null,"phoneEnabled":false,"tabletEnabled":false,"pilot":true,"dependentModules":null,"created":false,"customerIds":["photon"],"used":false,"name":"responsive-web","id":"5bf18d69-3902-497b-8cd2-65dbdc9cd377","displayName":null,"status":null,"description":null,"creationDate":1379332579000,"helpText":null,"system":true}],"projectCode":"UNITTest","noOfApps":2,"startDate":null,"endDate":null,"preBuilt":true,"multiModule":false,"customerIds":["photon"],"used":false,"name":"UNITTest","id":"b1a829b3-bbfa-45c4-b5f0-003eca66abf5","displayName":null,"status":null,"description":"","creationDate":1379332635000,"helpText":null,"system":false}},"status":"success"};
				$('.hProjectId').val(projectInfo.data.projectInfo.appInfos[0].id)
				commonVariables.api.localVal.setProjectInfo(projectInfo);
				commonVariables.api.localVal.setSession('username', "admin");
				commonVariables.appDirName = "test";
				$(".headerAppId").attr("value","6d6753e8-b081-48d8-9924-70a14f3663d4");	
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test run test without parameters tested");
					self.testBtnClickTest();
				}, 4000);
			});
		},

		testBtnClickTest : function() {
			var self = this;
			asyncTest("Unit Test Test-Btn Click Test", function() {
				$.mockjaxClear(self.parametersMock);
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":[{"pluginParameter":"plugin","mavenCommands":{"mavenCommand":[{"key":"java","value":"-Pjava"},{"key":"js","value":"-Pjs -DskipTests"}]},"name":{"value":[{"value":"Test Against","lang":"en"}]},"type":"List","childs":null,"dynamicParameter":null,"required":"true","editable":"true","description":null,"key":"testAgainst","possibleValues":{"value":[{"value":"java","key":"java","dependency":null},{"value":"js","key":"js","dependency":"showSettings,environmentName"}]},"multiple":"false","value":"java","sort":false,"show":true},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Show Settings","lang":"en"}]},"type":"Boolean","childs":null,"dynamicParameter":null,"required":"false","editable":"true","description":"","key":"showSettings","possibleValues":null,"multiple":"false","value":"false","sort":false,"show":false,"dependency":"environmentName"},{"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Environment","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.commons","artifactId":"phresco-commons","type":"jar","version":"2.3.0.8001-SNAPSHOT"}},"class":"com.photon.phresco.impl.EnvironmentsParameterImpl"},"required":"true","editable":"true","description":null,"key":"environmentName","possibleValues":{"value":[{"value":"Production","key":null,"dependency":null}]},"multiple":"false","value":"Production","sort":false,"show":true}],"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				setTimeout(function() {
					start();
					equal($('#testAgainst').length, 1, "Unit test test-btn click tested");
					self.runUnitTestBtnClickTest();
				}, 4000);
			});
		},

		runUnitTestBtnClickTest : function() {
			var self = this;
			asyncTest("Unit Test Run Test-Btn Click Test", function() {
				$.mockjaxClear(self.executeTestMock);
				self.executeTestMock = $.mockjax({
					url: commonVariables.webserviceurl+"app/runUnitTest?username=admin&appId=5bf18d69-3902-497b-8cd2-65dbdc9cd377&customerId=photon&goal=unit-test&phase=unit-test&projectId=b1a829b3-bbfa-45c4-b5f0-003eca66abf5&testAgainst=java&environmentName=Production&displayName=Admin&appDirName=test",
				  	type: "POST",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHRQ100002","status":"COMPLETED","log":"STARTED","connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":"8f141cf7-31ff-4522-868a-743ba9188e17","service_exception":"","configErrorMsg":null});
				  	}
				});
				
				$.mockjax({
					url: commonVariables.webserviceurl+"util/validation?appDirName=test&customerId=photon&phase=unit-test&testAgainst=java&environmentName=Production&moduleName=phresco-framework",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"responseCode":"PHR9C00001","status":"success","log":null,"connectionAlive":false,"errorFound":false,"configErr":false,"parameterKey":null,"uniquekey":null,"service_exception":null,"configErrorMsg":null});
				  	}
				});
				
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#runUnitTest").click();
				setTimeout(function() {
					start();
					equal($('.testsuiteClm a[name=testDescription]').text(), "SampleUnitTest", "Unit test run test-btn click tested");
					self.noParametersTest();
				}, 4000);
			});
		},

		noParametersTest : function() {
			var self = this;
			asyncTest("Unit Test No Parameters Test", function() {
				$.mockjaxClear(self.parametersMock);
				self.parametersMock = $.mockjax({
					url: commonVariables.webserviceurl+"parameter/dynamic?appDirName=test&goal=unit-test&phase=unit-test&customerId=photon&userId=admin",
				  	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR1C00001","data":null,"status":"success"});
				  	}
				});
				commonVariables.appDirName = "test";
				$(commonVariables.contentPlaceholder).find("#unitTestBtn").click();
				setTimeout(function() {
					start();
					equal($('.dyn_popup').css("display"), "none", "Unit test no parameters tested");
					self.testcasesRenderTest();
				}, 4000);
			});
		},

		testcasesRenderTest : function() {
			var self = this;
			asyncTest("Unit Test Testcases Render Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+commonVariables.qualityContext+"/testreports?appDirName=test&testType=unit&testSuite=SampleUnitTest&moduleName=phresco-framework&techReport=js",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ000002","data":[{"name":"hello","file":null,"time":"0.015","testClass":"com.photon.phresco.service.TestCase","assertions":0.0,"line":0.0,"testCaseFailure":null,"testCaseError":null}],"status":"success"});
				  	}
				});

				$(commonVariables.contentPlaceholder).find("a[name=testDescription]:first").click();
				setTimeout(function() {
					start();
					equal($('#testcases').length, 1, "Unit test testcases render tested");
					self.testcaseGraphicalViewTest();
				}, 4000);
			});
		},

		testcaseGraphicalViewTest : function() {
			var self = this;
			asyncTest("Unit Test Testcase Graphical View Test", function() {
				$(commonVariables.contentPlaceholder).find('.table1, .table2').click();
				setTimeout(function() {
					start();
					equal($("#testcases").css("display"), "block", "Unit test testcase graphical view tested");
					self.testcaseTabularViewTest();
				}, 700);
			});
		},

		testcaseTabularViewTest : function() {
			var self = this;
			asyncTest("Unit Test Testcase Tabular View Test", function() {
				$(commonVariables.contentPlaceholder).find('.graph1, .graph2').click();
				setTimeout(function() {
					start();
					equal($("#graphView").css("display"), "block", "Unit test testcase tabular view tested");
					self.showTestcaseConsole();
				}, 700);
			});
		},

		showTestcaseConsole : function() {
			var self = this;
			asyncTest("Unit Test Testcase Open Console Test", function() {
				$(commonVariables.contentPlaceholder).find('#consoleImg').click();
				setTimeout(function() {
					start();
					$('#consoleImg').attr('data-flag','false');
					equal($('#consoleImg').attr('data-flag'), "false", "Unit test testcase open console tested");
					self.pdfIconBtnClickTest();
				}, 700);
			});
		},

		pdfIconBtnClickTest : function() {
			var self = this;
			asyncTest("Unit Test pdfIcon-Btn Click Test", function() {
				$.mockjax({
					url: commonVariables.webserviceurl+"pdf/showPopUp?appDirName=test&fromPage=unit",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
				  		this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHR200015","data":{"value":true,"json":[{"time":"Jul 23 2013 16.28","type":"crisp","fileName":"Raj_crisp.pdf"},{"time":"Jul 23 2013 16.08","type":"crisp","fileName":"tech_overall_crisp.pdf"}]},"status":"success"});
				  	}
				});
				commonVariables.api.localVal.setSession('username', "admin");
				$(commonVariables.contentPlaceholder).find("#pdfIcon").click();
				setTimeout(function() {
					start();
					equal('1', '1', "PdfIcon-btn click tested");
					equal($(commonVariables.contentPlaceholder).find("#pdf_report").css("display") , "block", "Pdf empty list tested");
					self.optionsFailureTest();
				}, 4000);
			});
		},

		optionsFailureTest : function() {
			var self = this;
			asyncTest("Unit Test Failed To Get The Report Options Test", function() {
				$(commonVariables.contentPlaceholder).empty();
				$.mockjaxClear(self.unitTestMock);
				self.unitTestMock = $.mockjax({
				  	url: commonVariables.webserviceurl+commonVariables.qualityContext+"/unit?userId=admin&appDirName=test",
				 	type: "GET",
				  	dataType: "json",
				  	contentType: "application/json",
				  	status: 200,
				  	response : function() {
					  	this.responseText = JSON.stringify({"message":null,"exception":null,"responseCode":"PHRQ110001","data":"","status":"error"});
				  	}
				});

				require(["navigation/navigation"], function() {
					commonVariables.navListener = new Clazz.com.components.navigation.js.listener.navigationListener();
				});
				commonVariables.navListener.onMytabEvent("unitTest");
				setTimeout(function() {
					start();
					equal($('.msgdisplay').text(), "Unable to get unit test report options", "Unit test failed to get the report options tested");
					require(["componentTestTest"], function(componentTestTest){
						componentTestTest.runTests();
					});
				}, 4000);
			});
		}
	};
});