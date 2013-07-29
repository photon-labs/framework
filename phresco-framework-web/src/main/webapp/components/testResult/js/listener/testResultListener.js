define([], function() {

	Clazz.createPackage("com.components.testResult.js.listener");

	Clazz.com.components.testResult.js.listener.TestResultListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		allTestCases : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onTestResult : function() {
			$(".unit_view").show();
		},
		
		getBarChartGraphData : function(testSuites, callback) {
			var graphDatas = [];
			var testSuiteLabels = [];
			for (i in testSuites) {
				var success = Math.round(testSuites[i].success);
				var errors = Math.round(testSuites[i].errors);
				var failures = Math.round(testSuites[i].failures);
				var graphData = [];
				graphData.push(success);
				graphData.push(failures);
				graphData.push(errors);
				graphDatas.push(graphData);
				testSuiteLabels.push(testSuites[i].name);
			}
			callback(graphDatas, testSuiteLabels);
		},
		
		getPieChartGraphData : function(callback) {
			var self = this;
			var testSuites = commonVariables.testSuites;
			for (i in testSuites) {
				if (testSuites[i].name === commonVariables.testSuiteName) {
					var success = Math.round(testSuites[i].success);
					var errors = Math.round(testSuites[i].errors);
					var failures = Math.round(testSuites[i].failures);
					var total = testSuites[i].total;
					var failurePercentage = Math.round((failures / total) * 100);
					var errorsPercentage = Math.round((errors / total) * 100);
					var successPercentage = Math.round((success / total) * 100);
					var data = {};
					data.success = success;
					data.errors = errors;
					data.failures = failures;
					data.total = total;
					data.failurePercentage = failurePercentage;
					data.errorsPercentage = errorsPercentage;
					data.successPercentage = successPercentage;
					callback(data);
					break;
				}
			}
		},
		
		// for manual
		getManualPieChartGraphData : function(callback) {
			var testSuites = commonVariables.testSuites.testSuites;
			
			for (i in testSuites) {
				if (testSuites[i].name === commonVariables.testSuiteName) {
					var graphData = {};
					graphData.failures = Math.round(testSuites[i].failures);
					graphData.success = Math.round(testSuites[i].success);
					graphData.notApplicable = Math.round(testSuites[i].notApplicable);
					graphData.blocked = Math.round(testSuites[i].blocked);
					graphData.total = testSuites[i].total;
					graphData.notExecuted = testSuites[i].notExecuted;
					callback(graphData);
					break;
				}
			}
		},
		
		showGraphicalView : function() {
			var self = this;
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_off.png" width="25" height="25" border="0" alt="">Table View');
			$("#testSuites").hide();
			$("#testCases").hide();
			$("#graphView").show();
			if (commonVariables.from === "all") {
				$('#bar').show();
				$('#pie').hide();
			} else {
				self.getPieChartGraphData(function(graphData) {
					self.createPieChart(graphData);
				});
				$('#bar').hide();
				$('#pie').show();
			}
		},
		
		showTabularView : function() {
			var self = this;
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_off.png" width="25" height="25" border="0" alt="">Graph View');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_on.png" width="25" height="25" border="0" alt=""><b>Table View</b>');
			$("#graphView").hide();
			if (commonVariables.from === "all") {
				$("#testSuites").show();
				$("#testCases").hide();
			} else {
				$("#testSuites").hide();
				$("#testCases").show();
			}
		},
		
		onTestResultDesc : function(callback) {
			var self = this;
			var requestBody = {};
			var currentTab = commonVariables.navListener.currentTab;
			requestBody.testSuite = commonVariables.testSuiteName;
			self.performAction(self.getActionHeader(requestBody, "getTestReport"), function(response) {
				$("#testSuites").hide();
				$("#testCases").show();
				self.allTestCases = response.data;
				self.constructTestReport(response.data);
				//commonVariables.loadingScreen.removeLoading();
				if ("manualTest" === currentTab) {
					self.getManualPieChartGraphData(function(graphData) {
						self.createManualPieChart(graphData);
					});
				} else {
					self.getPieChartGraphData(function(graphData) {
						self.createPieChart(graphData);
					});
				}
			});
		},
		
		createBarChart : function(graphData, testSuiteLabels) {
	        var chartTextColor = "#4C4C4C";
	        var chartGridColor = "#4C4C4C";
	        var chartAxisColor = "#4C4C4C";
	        var chartBarColor = "#00A8F0";
	      	var successColor = "#6f6";
	      	var failureColor = "orange";
	      	var errorColor = "red";
			
	        var bar1 = new RGraph.Bar('bar', graphData);
			bar1.Set('chart.background.barcolor1', 'transparent');
			bar1.Set('chart.background.barcolor2', 'transparent');
			bar1.Set('chart.labels', testSuiteLabels);
			bar1.Set('chart.key', ['Success', 'Failure', 'Error']);
			bar1.Set('chart.key.position.y', 35);
			bar1.Set('chart.key.position', 'gutter');
			bar1.Set('chart.colors', [successColor, failureColor, errorColor]);
			bar1.Set('chart.shadow', false);
			bar1.Set('chart.shadow.blur', 0);
			bar1.Set('chart.shadow.offsetx', 0);
			bar1.Set('chart.shadow.offsety', 0);
			bar1.Set('chart.key.linewidth', 0);
			bar1.Set('chart.yaxispos', 'left');
			bar1.Set('chart.strokestyle', 'rgba(0,0,0,0)');
			bar1.Set('chart.text.angle', 45);
			bar1.Set('chart.text.color', chartTextColor);
			bar1.Set('chart.axis.color', chartAxisColor);
			bar1.Set('chart.gutter.left', 60);
			bar1.Set('chart.background.grid.color', chartGridColor);				
			bar1.Set('chart.gutter.bottom', 175);
			bar1.Set('chart.background.grid.autofit',true);
			bar1.Draw();
		},
		
		// for manual
		createManualBarChart : function(graphData) {
			var chartTextColor = "#4C4C4C";
	        var chartGridColor = "#4C4C4C";
	        var chartAxisColor = "#4C4C4C";
	        var chartBarColor = "#00A8F0";
	        //line chart color
	      	var successColor = "#6f6";
	      	var failureColor = "red";
	      	var notExeColor = "grey";
	      	var notAppColor = "#800000";
	      	var blockedColor = "orange";
			
	        var bar1 = new RGraph.Bar('bar', graphData);
			bar1.Set('chart.background.barcolor1', 'transparent');
			bar1.Set('chart.background.barcolor2', 'transparent');
			bar1.Set('chart.key', ['Total Failure','Total NotExecuted','Total Success','Total NotApplicable', 'Total Blocked']);
			bar1.Set('chart.key.position.y',240);
			bar1.Set('chart.key.position', 'gutter');
			bar1.Set('chart.colors', [failureColor, notExeColor, successColor, notAppColor, blockedColor]);
			bar1.Set('chart.shadow', false);
			bar1.Set('chart.shadow.blur', 0);
			bar1.Set('chart.shadow.offsetx', 0);
			bar1.Set('chart.shadow.offsety', 0);
			bar1.Set('chart.key.linewidth', 0);
			bar1.Set('chart.yaxispos', 'left');
			bar1.Set('chart.strokestyle', 'rgba(0,0,0,0)');
			bar1.Set('chart.text.angle', 45);
			bar1.Set('chart.text.color', chartTextColor);
			bar1.Set('chart.axis.color', chartAxisColor);
			bar1.Set('chart.gutter.left', 60);
			bar1.Set('chart.background.grid.color', chartGridColor);				
			bar1.Set('chart.gutter.bottom', 175);
			bar1.Set('chart.background.grid.autofit',true);
			bar1.Draw();
		},
		
		// for manual
		getManualBarChartGraphData : function(data, callback) {
			var graphDatas = [];
			var totalFailure = Math.round(data.totalFailure);
			var totalNotExecuted = Math.round(data.totalNotExecuted);
			var totalSuccess = Math.round(data.totalSuccess);
			var totalNotApplicable = Math.round(data.totalNotApplicable);
			var totalBlocked = Math.round(data.totalBlocked);
			var graphData = [];
			graphData.push(totalFailure);
			graphData.push(totalNotExecuted);
			graphData.push(totalSuccess);
			graphData.push(totalNotApplicable);
			graphData.push(totalBlocked);
			graphDatas.push(graphData);
			callback(graphDatas);
		},
		
		createPieChart : function(graphData) {
			var self = this;
			var success = graphData.success;
			var errors = graphData.errors;
			var failures = graphData.failures;
			var total = graphData.total;
			var failurePercent = graphData.failurePercentage;
			var errorsPercent = graphData.errorsPercentage;
			var successPercent = graphData.successPercentage;
			
			var percentageData = [];
			percentageData.push(parseInt(failurePercent));
			percentageData.push(parseInt(errorsPercent));
			percentageData.push(parseInt(successPercent));
			
			var keyData = [];
			keyData.push('Failures (' + failurePercent + '%)['+ failures +']');
			keyData.push('Errors (' + errorsPercent + '%)['+ errors +']');
			keyData.push('Success (' + successPercent + '%)['+ success +']');
			keyData.push('Total (' + total + ' Tests)');
		
			var pie2 = new RGraph.Pie('pie', percentageData); // Create the pie object
			pie2.Set('chart.gutter.left', 45);
			pie2.Set('chart.colors', ['orange', 'red', '#6f6']);
			pie2.Set('chart.key', keyData);
			pie2.Set('chart.key.background', 'white');
			pie2.Set('chart.strokestyle', 'white');
			pie2.Set('chart.linewidth', 3);
			pie2.Set('chart.title',  commonVariables.testSuiteName + ' Report');
			pie2.Set('chart.title.size',10);
			pie2.Set('chart.title.color', '#8A8A8A');
			pie2.Set('chart.exploded', [5,5,0]);
			pie2.Set('chart.shadow', true);
			pie2.Set('chart.shadow.offsetx', 0);
			pie2.Set('chart.shadow.offsety', 0);
			pie2.Set('chart.shadow.blur', 25);
			pie2.Set('chart.radius', 100);
			pie2.Set('chart.background.grid.autofit',true);
			if (RGraph.isIE8()) {
		    	pie2.Draw();
			} else {
		    	RGraph.Effects.Pie.RoundRobin(pie2);
			}
		},
		
		//for manual
		createManualPieChart : function(data) {
			var graphData = {};
			graphData.total = data.total;
			graphData.success = data.success;
			graphData.failures = data.failures;
			graphData.notApplicable = data.notApplicable;
			graphData.blocked = data.blocked;
			graphData.notExecuted = data.notExecuted;
			
			var pieData = [];
			pieData.push('Total (' + graphData.total + ' Tests)');
			pieData.push('Success (' + graphData.success + ')');
			pieData.push('Failures (' + graphData.failures + ')');
			pieData.push('NotApplicable (' + graphData.notApplicable + ')');
			pieData.push('Blocked (' + graphData.blocked + ' Tests)');
			pieData.push('NotExecuted (' + graphData.notExecuted + ' Tests)');
			
			var pie2 = new RGraph.Pie('pie',[graphData.success, graphData.failures, graphData.notApplicable, graphData.blocked, graphData.notExecuted, graphData.total]); // Create the pie object
			pie2.Set('chart.gutter.left', 45);
			pie2.Set('chart.colors', ['#6f6', 'red', '#7474F7', 'orange', 'grey']);
			pie2.Set('chart.key', ['Success ('+graphData.success+')', 'Failures ('+graphData.failures+')', 'NotApplicable ('+graphData.notApplicable+')', 'Blocked ('+graphData.blocked+')', 'NotExecuted ('+graphData.notExecuted+')','Total Tests ('+graphData.total+')']);
			pie2.Set('chart.key.background', 'white');
			pie2.Set('chart.strokestyle', 'white');
			pie2.Set('chart.linewidth', 3);
			pie2.Set('chart.title',  commonVariables.testSuiteName + ' Report');
			pie2.Set('chart.title.size',10);
			pie2.Set('chart.title.color', '#494949');
			pie2.Set('chart.exploded', [5,5,0]);
			pie2.Set('chart.shadow', true);
			pie2.Set('chart.shadow.offsetx', 0);
			pie2.Set('chart.shadow.offsety', 0);
			pie2.Set('chart.shadow.blur', 25);
			pie2.Set('chart.radius', 100);
			pie2.Set('chart.background.grid.autofit',true);
			if (RGraph.isIE8()) {
		    	pie2.Draw();
			} else {
		    	RGraph.Effects.Pie.RoundRobin(pie2);
			}
		},
		
		constructTestReport : function(data) {
			var self = this;
			var currentTab = commonVariables.navListener.currentTab;
			var imgArray = [];
			var resultTemplate = "";
			if ("manualTest" === currentTab) {
				resultTemplate = self.constructManualTestCaseTbl(resultTemplate, data);
			} else {
				resultTemplate = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
				'<thead class="fixedHeader"><tr><th>Name</th><th>Class</th><th>Time</th><th>Status</th><th>Log</th>';
				if ("functionalTest" === currentTab) {
					resultTemplate = resultTemplate.concat('<th>Screenshot</th>');
				}
				resultTemplate = resultTemplate.concat('</tr></thead><tbody class="scrollContent" style="height:475px;">');
				for (i in data) {
					var result = data[i];
					resultTemplate = resultTemplate.concat('<tr><td>'+ result.name +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.testClass +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.time +'</td>');
					resultTemplate = resultTemplate.concat('<td class="list_img">');
					if (result.testCaseFailure !== null) {
						resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/cross_red.png" width="16" height="13" border="0" alt=""></td>');
						resultTemplate = resultTemplate.concat('<td class="list_img"><a href="#" type="failure" resultName="'+result.name+'" class="log"><img src="themes/default/images/helios/log_icon.png" width="16" height="13" border="0" alt=""></a>');
						resultTemplate = resultTemplate.concat('<div id="'+result.name+'" style="display: none; width:500px"><div style="word-wrap: break-word;"><b>'+result.testCaseFailure.failureType+' : </b>'+ result.testCaseFailure.description+'</div><div class="flt_right">');
						resultTemplate = resultTemplate.concat('<input type="button" value="Close" class="btn btn_style dyn_popup_close"></div></div></td>');
					}  else if (result.testCaseError !== null) {
						resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/cross_red.png" width="16" height="13" border="0" alt=""></td>');
						resultTemplate = resultTemplate.concat('<td class="list_img"><a href="#" type="error" resultName="'+result.name+'" class="log"><img src="themes/default/images/helios/log_icon.png" width="16" height="13" border="0" alt=""></a>');
						resultTemplate = resultTemplate.concat('<div id="'+result.name+'" style="display: none; width:500px"><div style="word-wrap: break-word;"><b>'+result.testCaseError.errorType+' : </b>'+ result.testCaseError.description+'</div><div class="flt_right">');
						resultTemplate = resultTemplate.concat('<input type="button" value="Close" class="btn btn_style dyn_popup_close"></div></div></td>');
					} else {
						resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/tick_green.png" width="16" height="13" border="0" alt=""></td><td>&nbsp;</td>');
					}
					
					if ("functionalTest" === currentTab) {
						var json = {};
						json.eventClass = result.name;
						if (result.testCaseFailure !== null && result.testCaseFailure.hasFailureImg) {
							json.filePath = result.testCaseFailure.screenshotPath;
							imgArray.push(json);
							resultTemplate = resultTemplate.concat('<td><a href="#" class="'+result.name+'"><img src="themes/default/images/helios/screenshot_icon.png" width="19" height="16" border="0" alt="" /></a></td>');
						} else if (result.testCaseError !== null && result.testCaseError.hasErrorImg) {
							json.filePath = result.testCaseFailure.screenshotPath;
							imgArray.push(json);
							resultTemplate = resultTemplate.concat('<td><a href="#" class="'+result.name+'"><img src="themes/default/images/helios/screenshot_icon.png" width="19" height="16" border="0" alt="" /></a></td>');
						} else {
							resultTemplate = resultTemplate.concat('<td>&nbsp;</td>');
						}
					}
					resultTemplate = resultTemplate.concat('</tr>');
					resultTemplate = resultTemplate.concat('</tbody></table>');
				}
			}

			$(commonVariables.contentPlaceholder).find('#testCases').html(resultTemplate);
			setTimeout(function() {
				self.resizeTestResultColumn("testCases");
				self.showErrorLog();
				$("#testResult .scrollContent").mCustomScrollbar({
					autoHideScrollbar:true,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true}
				});
			}, 400);

			self.resizeTestResultDiv();
			self.showScreenShot(imgArray);
		},
		
		constructManualTestCaseTbl : function(resultTemplate, data) {
			var self = this;
			$("#addTestCase").show();
			$("#addTestSuite").hide();
			if (data.length > 0) {
				resultTemplate = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
				'<thead class="fixedHeader"><tr><th>Feature Id</th><th>Test Cases</th><th>Expected Result</th><th>Actual Result</th><th class="">Status</th><th class="">Bug Comments</th><th>Edit</th>';
				resultTemplate = resultTemplate.concat('</tr></thead><tbody class="scrollContent" name="manualTest" style="height:475px;">');
				for (i in data) {
					var result = data[i];
					resultTemplate = resultTemplate.concat('<tr><td>'+ result.featureId +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.testCaseId +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.expectedResult +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.actualResult +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.status +'</td>');
					resultTemplate = resultTemplate.concat('<td>'+ result.bugComment +'</td>');
					resultTemplate = resultTemplate.concat('<td><a class="editManualTestcase" name="updateManualTestCase_popup" href="#" testcaseName="'+ result.testCaseId +'"><img alt="Edit" src="themes/default/images/helios/edit_icon.png"/></a></td></tr>');
				}
				resultTemplate = resultTemplate.concat('</tbody></table>');
			} else {
				resultTemplate = '<div class="alert alert-block" style="text-align: center; margin: auto;">No Testcases available</div>';
			}
			return resultTemplate;
		},
		
		showScreenShot : function(imgArray) {
			for (i in imgArray) {
				var data = imgArray[i];
				var filePath = "data:image/png;base64," + data.filePath;
				$('.'+data.eventClass).magnificPopup({
					items: [
						{
							src: $('<div class="text_center"><img src="'+filePath+'"><div class="fullscreen_desc">'+data.eventClass+'</div></div>'), // Dynamically created element
							type: 'inline'
						}
					],
					gallery: {
						enabled: true
					},
					type: 'image'
				});
			}
		},

		onUnitTestGraph : function() {
			$("#testSuites").hide();
			$("#testCases").hide();
			$("#graphView").show();
			$("#graphicalView").html('');
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this;
			var header, userId;
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var userId = userInfo.id;
			var appDirName = commonVariables.api.localVal.getSession('appDirName');
			var techReport = $('#reportOptionsDrop').attr("value");
			var moduleName = $('#modulesDrop').attr("value");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			
			var currentTab = commonVariables.navListener.currentTab;
			var testType = "";
			if ("unitTest" === currentTab) {
				testType = "unit";
			} else if ("functionalTest" === currentTab) {
				testType = "functional";
			} else if ("componentTest" === currentTab) {
				testType = "component";
			}
			if (action === "getTestSuite") {
				header.requestMethod = "GET";
				if ("manualTest" === currentTab) {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + "/testsuites?appDirName=" + appDirName;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testsuites?appDirName=" + appDirName + "&testType=" + testType;
				}
				
				if (techReport !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
				if (moduleName !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&moduleName=" + moduleName);
				}
			} else if (action === "getTestReport") {
				header.requestMethod = "GET";
				if ("manualTest" === currentTab) {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + "/testcases?appDirName=" + appDirName+"&testSuiteName=" + requestBody.testSuite;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testreports?appDirName=" + appDirName +
					"&testType=" + testType+ "&testSuite=" + requestBody.testSuite;
				}
				
				if (techReport !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
				if (moduleName !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&moduleName=" + moduleName);
				}
			} else if (action === "generatePdfReport") {
				var data = $('#pdfReportForm').serialize();
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + "app/printAsPdf?appDirName=" + appDirName + "&" + data + "&userId=" + userId;
			} else if (action === "getPdfReports") {
				var data = $('#pdfReportForm').serialize();
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/showPopUp?appDirName=" + appDirName + "&fromPage=" + testType;
			} else if (action === "deletePdfReport") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/deleteReport?appDirName=" + appDirName + "&fromPage=" + testType + "&reportFileName=" + requestBody.fileName;
			}
			return header;
		},
		
		performAction : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null) {
							callback(response);
						} else {
							//commonVariables.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					}
				);
			} catch(exception) {
				
			}
		},
		
		showHideConsole : function() {
			var self = this;
			var check = $('#consoleImg').attr('data-flag');
			if (check === "true") {
				self.openConsoleDiv();
			} else {
				self.closeConsole();
				$('.progress_loading').hide();
			}
		},
		
		resizeTestResultColumn : function(divId) {
			var w1 = $("#" + divId + " .scrollContent tr td:first-child").width();
			var w2 = $("#" + divId + " .scrollContent tr td:nth-child(2)").width();
			var w3 = $("#" + divId + " .scrollContent tr td:nth-child(3)").width();
			var w4 = $("#" + divId + " .scrollContent tr td:nth-child(4)").width();
			var w5 = $("#" + divId + " .scrollContent tr td:nth-child(5)").width();
			var w6 = $("#" + divId + " .scrollContent tr td:nth-child(6)").width();
			var w7 = $("#" + divId + " .scrollContent tr td:nth-child(7)").width();
			var w8 = $("#" + divId + " .scrollContent tr td:nth-child(8)").width();
			var w9 = $("#" + divId + " .scrollContent tr td:nth-child(9)").width();
			
			$("#" + divId + " .fixedHeader tr th:first-child").css("width", w1);
			$("#" + divId + " .fixedHeader tr th:nth-child(2)").css("width", w2);
			$("#" + divId + " .fixedHeader tr th:nth-child(3)").css("width", w3);
			$("#" + divId + " .fixedHeader tr th:nth-child(4)").css("width", w4);
			$("#" + divId + " .fixedHeader tr th:nth-child(5)").css("width", w5);
			$("#" + divId + " .fixedHeader tr th:nth-child(6)").css("width", w6);
			$("#" + divId + " .fixedHeader tr th:nth-child(7)").css("width", w7);
			$("#" + divId + " .fixedHeader tr th:nth-child(8)").css("width", w8);
			$("#" + divId + " .fixedHeader tr th:nth-child(9)").css("width", w9);
		},
		
		//To get the existing pdf reports
		getPdfReports : function() {
			var self = this;
			var requestBody = {};
			self.performAction(self.getActionHeader(requestBody, "getPdfReports"), function(response) {
				self.listPdfReports(response);
			});
		},
		
		//To list the generated PDF reports
		listPdfReports : function(pdfReports) {
			var self = this;
			if (pdfReports !== undefined && pdfReports !== null && pdfReports.length > 0) {
				$("#noReport").hide();
				$("#availablePdfRptsTbl").show();
				var content = "";
				for (i in pdfReports) {
					content = content.concat('<tr class="generatedRow"><td>' + pdfReports[i].time + '</td>');
					content = content.concat('<td>' + pdfReports[i].type + '</td>');
					content = content.concat('<td><a class="tooltiptop" href="#" fileName="' + pdfReports[i].fileName + '" fromPage="' + pdfReports[i].fromPage);
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="downLoad" data-original-title="Download Pdf" title="">');
					content = content.concat('<img src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt="0"></a></td>');
					content = content.concat('<td><a class="tooltiptop deletePdf" fileName="' + pdfReports[i].fileName + '" href="#"');
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="delete" data-original-title="Delete Pdf" title="">');
					content = content.concat('<img src="themes/default/images/helios/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>');
				}
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbdy").html(content);
				self.deletePdfReport();
			} else {
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbl").hide();
				$("#noReport").show();
				$("#noReport").html("No Report are Available");
			}
			self.hidePopupLoading($('#pdfReportLoading'));
		},
		
		//To generate the pdf report
		generatePdfReport : function() {
			var self = this;
			var requestBody = {};
			self.performAction(self.getActionHeader(requestBody, "generatePdfReport"), function(response) {
				self.getPdfReports();
			});
		},
		
		//To delete the selected pdf report
		deletePdfReport : function() {
			var self = this;
			$('.deletePdf').on("click", function() {
				self.showPopupLoading($('#pdfReportLoading'));
				var requestBody = {};
				requestBody.fileName = $(this).attr('fileName');
				self.performAction(self.getActionHeader(requestBody, "deletePdfReport"), function(response) {
					self.getPdfReports();
				});
			});
		},
		
		showPopupLoading : function(obj) {
			obj.show();
		},
		
		hidePopupLoading : function(obj) {
			obj.hide();
		},
		
		//To show the failure/error log
		showErrorLog : function() {
			var self = this;
			$('.log').on("click", function() {
				var id = $(this).attr("resultname");
				self.opencc(this, id);
			});
		},
		
		
			updateManualTestcase : function() {
				self.openccpl(this, 'updateManualTestCase_popup','');
//				var updateTemplate = "";
//				updateTemplate = updateTemplate.concat('<div id="show_manualTestCase_popup" class="dyn_popup" style="display:none">');
//				
//	            <form id="manualTestTestCaseForm">
//					<table class="table node_table" cellpadding="0" cellspacing="0" border="0">
//						<tbody>
//							<tr>
//								<td>Test Scenarios<br>
//									<input type="text" id="testSuiteName" placeholder="TestSuite Name" name="testSuiteName" value="">
//								</td>
//								<td>Features<sup>*</sup><br>
//									<input type="text" placeholder="Name of the Features" name="featureId" value="">
//								</td>
//							</tr>
//							<tr>
//								<td>TestCases<sup>*</sup><br>
//									<input type="text" placeholder="Name of the TestCases" name="testCaseId" value="">
//								</td>
//								<td>Status<br>
//									<select name="status">
//										<option value="success">Success</option>
//										<option value="failure">Failure</option>
//									</select>
//								</td>
//							</tr>
//							<tr>
//								<td>TestCase Description<br>
//									<textarea name="description"></textarea>
//								</td>
//								<td>Test Steps<br>
//									<textarea name="steps"></textarea>
//								</td>
//							</tr>
//							<tr>
//								<td>Expected Result<br>
//									<textarea name="expectedResult"></textarea>
//								</td>
//								<td>Actual Result<br>
//									<textarea name="actualResult"></textarea>
//								</td>
//							</tr>
//							<tr>
//								<td colspan="2">Bug ID/Comments<br>
//									<textarea name="bugComment"></textarea>
//								</td>
//							 </tr>
//						</tbody>
//					</table>
//					<div class="flt_right">
//						<input type="button" value="Save" class="btn btn_style" name="addTestCase">
//						<input type="button" value="Close" class="btn btn_style dyn_popup_close">
//					</div>
//				</form>
//			</div>
//			});
		},
		
		
		
		
		
		openConsoleDiv : function() {
			$('.testSuiteTable').append('<div class="mask"></div>');
			$('.mask').fadeIn("slow");
			$('.unit_close').css("z-index", 1001);
			$('.unit_progress').css("z-index", 1001);
			$('.unit_close').css("height", 0);
			var value = $('.unit_info').width();
			var value1 = $('.unit_progress').width();
			$('.unit_info').animate({left: -value},500);
			$('.unit_progress').animate({right: '10px'},500);
			$('.unit_close').animate({right: value1+10},500);
			$('.unit_info table').removeClass("big").addClass("small");
			$('#consoleImg').attr('data-flag','false');

			var height = $('#testResult table').height();
			$('.unit_progress').height(height);
			$('.progress_loading').show();
		},

		resizeTestResultDiv : function() {
			//To set the height of the test result section  
			var marginTop = $('.testSuiteTable').css("margin-top");
			if (marginTop !== undefined) {
				marginTop = marginTop.substring(0, marginTop.length - 2);
			}
			var paddingTop = $('.testSuiteTable').css("padding-top");
			if (paddingTop !== undefined) {
				paddingTop = paddingTop.substring(0, paddingTop.length - 2);
			}
			var footerHeight = $('#footer').height();
			var windowHeight = $(window).height();
			var deductionHeight = Number(marginTop) + Number(footerHeight) + Number(paddingTop) + Number(paddingTop);
			deductionHeight = windowHeight - deductionHeight;
			$('.testSuiteTable').height(deductionHeight);
			$('#testSuites').height(deductionHeight);
			deductionHeight = deductionHeight - (Number(paddingTop) + Number(paddingTop) + Number(paddingTop));
			$('.testSuiteTable .scrollContent').height(deductionHeight);
		},
		
		setConsoleScrollbar : function(bcheck){
			if(bcheck){
				$("#unit_progress .scrollContent").mCustomScrollbar("destroy");
				$("#unit_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar: false,
					scrollInertia: 1000,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true},
					callbacks:{
						onScrollStart:function(){
							$("#unit_progress .scrollContent").mCustomScrollbar("scrollTo","bottom");
						}
					}
				});
			}else{
				$("#unit_progress .scrollContent").mCustomScrollbar("destroy");
				$("#unit_progress .scrollContent").mCustomScrollbar({
					autoHideScrollbar:true,
					scrollInertia: 200,
					theme:"light-thin",
					advanced:{ updateOnContentResize: true}
				});
			}
		}
	});

	return Clazz.com.components.testResult.js.listener.TestResultListener;
});