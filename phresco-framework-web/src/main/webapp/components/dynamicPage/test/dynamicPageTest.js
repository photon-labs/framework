
define(["dynamicPage/dynamicPage"], function(DyanmicPage) {

	return { 
		runTests: function (configData) {
			module("dynamicPage.js");
			var dyanmicPage = new DyanmicPage();
			var self = this;
			//self.testTextBox(dyanmicPage, self);
			asyncTest("Test - Render dynamic page", function() {
				var divElement = $("<div id='testDynamicParam'><ul>sdsd</ul></div>");
				var parameterResponse = {"response": null,"message": "Parameter returned successfully","exception": null,"data": [
										{ "pluginParameter": null,"mavenCommands": null,"name": {"value": [{"value": "Build Name","lang": "en"
										}]},"type": "String","childs": null,"dynamicParameter": null,"required": "false","editable": "true","description": "",
										"key": "buildName","possibleValues": null,"multiple": "false","value": "Test","sort": false,"show": true},
										{"pluginParameter": null,"mavenCommands": null,"name": {"value": [{"value": "Build Number","lang": "en"}]
										},"type": "Number","childs": null,"dynamicParameter": null,"required": "false","editable": "true","description": "",
										"key": "buildNumber","possibleValues": null,"multiple": "false","value": "","sort": false,"show": true},
										{"pluginParameter": null,"mavenCommands": null,"name": {"value": [{"value": "Show Settings","lang": "en"}
										]},"type": "Boolean","childs": null,"dynamicParameter": null,"required": "false","editable": "true","description": "",
										"key": "showSettings","possibleValues": null,"multiple": "false","value": "false","sort": false,"show": true,
										"dependency": "environmentName"},{"pluginParameter": null,"mavenCommands": null,"name": {"value": [{"value": "Environment",
										"lang": "en"}]},"type": "DynamicParameter","childs": null,"dynamicParameter": {"dependencies": {"dependency": {
										"groupId": "com.photon.phresco.commons","artifactId": "phresco-commons","type": "jar","version": "3.0.0.13000"}
										 },"class": "com.photon.phresco.impl.EnvironmentsParameterImpl"},"required": "true","editable": "true","description": null,
										"key": "environmentName","possibleValues": {"value": [{"value": "Production","key": null,"dependency": null},{"value": "Testing",
										"key": null,"dependency": null},{"value": "Development","key": null,"dependency": null}]},"multiple": "true","value": "Development",
										"sort": false,"show": true},{"pluginParameter": "framework","mavenCommands": {"mavenCommand": [{"key": "showErrors",
										"value": "-e"},{"key": "hide","value": "-q"},{"key": "showDebug","value": "-X"}]},"name": {"value": [{"value": "logs",
										"lang": "en"}]},"type": "List","childs": null,"dynamicParameter": null,"required": "false","editable": "true","description": null,
										"key": "logs","possibleValues": {"value": [{"value": "Show Errors","key": "showErrors","dependency": null},{"value": "Hide logs",
										"key": "hidelogs","dependency": null},{"value": "Show Debug","key": "showDebug","dependency": null}]},"multiple": "false",
										"value": "hidelogs","sort": false,"show": true}]};
				setTimeout(function() {
					start();
					dyanmicPage.dynamicPageListener.constructHtml(parameterResponse, divElement.find('ul'), '', '', 'package');
					equal(true, divElement.find('li').length > 0, "Dynamic page rendering tested");
					self.testRenderWithEmpty(dyanmicPage, self);
				}, 500);
			});
		},
	
		testRenderWithEmpty : function (dyanmicPage, self) {
			asyncTest("Test - Render dynamic page with empty value", function() {
				var divElement = $("<div id='testDynamicParamEmpty'></div>");
				var parameterResponse = {"response": null,"message": "Parameter returned successfully","exception": null,"data": []};
				dyanmicPage.dynamicPageListener.constructHtml(parameterResponse, divElement, '', '', 'package');

				setTimeout(function() {
					start();
					equal(true, divElement.find('li').length === 0, "Dynamic page rendering with empty value tested");
					self.testTextBox(dyanmicPage, self);
				}, 500);
			});
		},
	
		testTextBox : function (dyanmicPage, self) {
			asyncTest("Test - construct textbox element", function() {
				var divElement = $("<div id='testDynamicText'></div>");
				var parameter = {"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name",
										"lang":"en"}]},"type":"String","childs":null,"dynamicParameter":null,"required":"false",
										"editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false",
										"value":"Test","sort":false,"show":true};
				dyanmicPage.dynamicPageListener.constructTxtCtrl(parameter, "", divElement);
				setTimeout(function() {
					start();
					equal("text", divElement.find('input').attr("type"), "construct textbox element tested");
					self.testHidden(dyanmicPage, self);
				}, 200);
			});
		},
	
		testHidden : function(dyanmicPage, self) {
			asyncTest("Test - construct hidden element", function() {
				var divElement = $("<div id='testDynamicText'></div>");
				var parentDiv = $("<div id='parentDiv'><div class='hiddenControls'></div></div>");
				parentDiv.append(divElement);
				var parameter = {"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Build Name",
										"lang":"en"}]},"type":"Hidden","childs":null,"dynamicParameter":null,"required":"false",
										"editable":"true","description":"","key":"buildName","possibleValues":null,"multiple":"false",
										"value":"Test","sort":false,"show":true};
				dyanmicPage.dynamicPageListener.constructHiddenCtrl(parameter, "", divElement);
				setTimeout(function() {
					start();
					equal("hidden", parentDiv.find('input').attr("type"), "construct hidden element tested");
					self.testSingleSelect(dyanmicPage, self);
				}, 200);
			});
		},

		testSingleSelect : function (dyanmicPage, self) {
			asyncTest("Test - construct single select element", function() {
				var divElement = $("<div id='testDynamicSingleSelect'></div>");
				var parameter = {"name":{"value":[{"value": "logs","lang": "en"}]},"type": "List","childs": null,"dynamicParameter": null,
								"required": "false","editable": "true","description": null,"key": "logs","possibleValues":{"value":[{"value": "Show Errors",
								"key": "showErrors","dependency": null},{"value": "Hide logs","key": "hidelogs","dependency": null},{"value": "Show Debug",
								"key": "showDebug","dependency": null}]},"multiple": "false","value": "","sort": false,"show": true}
				dyanmicPage.dynamicPageListener.constructListCtrl(parameter, "", parameter.possibleValues, divElement);
				setTimeout(function() {
					start();
					equal("single", divElement.find('li').find("select").attr("selection"), "construct single select element tested");
					self.testEditableCombo(dyanmicPage, self);
				}, 200);
			});
		},
		
		testEditableCombo : function(dynamicPage, self) {
			asyncTest("Test - construct editable combo box", function() {
				var divElement = $("<div id='testEditableCombo'></div>");
				var parameter = {"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"Test Result Name","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.20000"}},"class":"com.photon.phresco.framework.param.impl.PerformanceTestResultNamesImpl"},"required":"true","editable":"edit","description":"","key":"testName","possibleValues":{"value":[{"value":"test-server","key":"test-server","dependency":"contextUrls"}]},"multiple":"false","value":"test-server","sort":false,"show":false};
				dynamicPage.dynamicPageListener.constructDynamicCtrl(parameter, "", parameter.possibleValues, divElement);
				setTimeout(function() {
					start();
					equal(true, divElement.find('select').hasClass('editableComboBox'), "editable combo box test");
					self.testDragDropControl(dynamicPage, self);
				}, 200);
			});
		},

		testDragDropControl : function(dynamicPage, self) {
			asyncTest("Test - drag & drop control", function() {
				var divElement = $("<div id='testEditableCombo'></div>");
				var parameter = {"pluginParameter":null,"mavenCommands":null,"name":{"value":[{"value":"FetchSql","lang":"en"}]},"type":"DynamicParameter","childs":null,"dynamicParameter":{"dependencies":{"dependency":{"groupId":"com.photon.phresco.framework","artifactId":"phresco-framework-impl","type":"jar","version":"3.0.0.22004"}},"class":"com.photon.phresco.framework.param.impl.DynamicFetchSqlImpl"},"required":"false","editable":"true","description":"","key":"fetchSql","possibleValues":{"value":[{"value":"/source/sql/mysql/5.5.1/custom.sql","key":"mysql","dependency":null},{"value":"/source/sql/mysql/5.5.1/site.sql","key":"mysql","dependency":null}]},"multiple":"false","value":"{\"mysql\":[\"/source/sql/mysql/5.5.1/custom.sql\"]}","sort":true,"show":false};
				dynamicPage.dynamicPageListener.consDragnDropcnt(parameter, "", divElement);				
				setTimeout(function() {
					start();
					equal("fetchSql_table", divElement.find('table').attr('name'), "drap and drop control test");
					self.testMultiSelect(dynamicPage, self);
				}, 200);
			});
		},

		testMultiSelect : function (dyanmicPage, self) {
			asyncTest("Test - construct multi select element", function() {
				var divElement = $("<div id='testDynamicMultiSelect'></div>");
				var parameter = {"name":{"value":[{"value": "logs","lang": "en"}]},"type": "List","childs": null,"dynamicParameter": null,
								"required": "false","editable": "true","description": null,"key": "logs","possibleValues":{"value":[{"value": "Show Errors",
								"key": "showErrors","dependency": null},{"value": "Hide logs","key": "hidelogs","dependency": null},{"value": "Show Debug",
								"key": "showDebug","dependency": null}]},"multiple": "true","value": "","sort": false,"show": true}
				dyanmicPage.dynamicPageListener.constructListCtrl(parameter, "", parameter.possibleValues, divElement);
				setTimeout(function() {
					start();
					equal("multiple", divElement.find('li').find("select").attr("selection"), "construct multi select element tested");
					require(["performanceTestTest"], function(performanceTestTest){
						performanceTestTest.runTests();
					});
				}, 200);
			}); 
		},
	};
});