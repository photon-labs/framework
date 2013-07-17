
define(["dynamicPage/dynamicPage"], function(DyanmicPage) {

	return { 
		runTests: function (configData) {
			module("dynamicPage.js;DynamicPage");
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
				}, 1500);
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
				}, 1500);
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
				}, 1500);
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
				}, 1500);
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
					self.testMultiSelect(dyanmicPage, self);
				}, 1500);
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
				}, 1500);
			}); 
		},
	};
});