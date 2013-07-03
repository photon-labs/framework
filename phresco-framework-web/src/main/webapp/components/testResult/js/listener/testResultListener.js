define(["testResult/api/testResultAPI"], function() {

	Clazz.createPackage("com.components.testResult.js.listener");

	Clazz.com.components.testResult.js.listener.TestResultListener = Clazz.extend(Clazz.Widget, {
		
		testResultAPI : null,
		allTestCases : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.testResultAPI === null) {
				self.testResultAPI = new Clazz.com.components.testResult.js.api.TestResultAPI();
			}
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
			requestBody.testSuite = commonVariables.testSuiteName;
			self.performAction(self.getActionHeader(requestBody, "getTestReport"), function(response) {
				$("#testSuites").hide();
				$("#testCases").show();
				self.allTestCases = response.data;
				self.constructTestReport(response.data);
				//commonVariables.loadingScreen.removeLoading();
				self.getPieChartGraphData(function(graphData) {
					self.createPieChart(graphData);
				});
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
		
		constructTestReport : function(data) {
			var self = this;
			var currentTab = commonVariables.navListener.currentTab;
			
			var resultTemplate = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
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
				resultTemplate = resultTemplate.concat('<td>');
				if (result.testCaseFailure !== null) {
					resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/cross_red.png" width="16" height="13" border="0" alt=""></td>');
					resultTemplate = resultTemplate.concat('<td><a href="#" type="failure" testcaseName="'+ result.name +'" class="log"><img src="themes/default/images/helios/log_icon.png" width="16" height="13" border="0" alt=""></a></td>');
				}  else if (result.testCaseError !== null) {
					resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/cross_red.png" width="16" height="13" border="0" alt=""></td>');
					resultTemplate = resultTemplate.concat('<td><a href="#" type="error" testcaseName="'+ result.name +'" class="log"><img src="themes/default/images/helios/log_icon.png" width="16" height="13" border="0" alt=""></a></td>');
				} else {
					resultTemplate = resultTemplate.concat('<img src="themes/default/images/helios/tick_green.png" width="16" height="13" border="0" alt=""></td>');
				}
				
				if ("functionalTest" === currentTab) {
					resultTemplate = resultTemplate.concat('<td><a href="#"><img src="themes/default/images/helios/screenshot_icon.png" width="19" height="16" border="0" alt="" /></a></td>');
				}
				resultTemplate = resultTemplate.concat('</tr>');
			}
			resultTemplate = resultTemplate.concat('</tbody></table>');
			$(commonVariables.contentPlaceholder).find('#testCases').html(resultTemplate);
			setTimeout(function() {
				self.resizeTestResultTable("testCases");
				self.showLog(data);
			}, 400);	
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
			var userInfo = JSON.parse(self.testResultAPI.localVal.getSession('userInfo'));
			var userId = userInfo.id;
			var appDirName = self.testResultAPI.localVal.getSession('appDirName');
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
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testsuites?appDirName=" + appDirName + "&testType=" + testType;
				if (techReport !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
				if (moduleName !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&moduleName=" + moduleName);
				}
			} else if (action === "getTestReport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testreports?appDirName=" + appDirName +
										"&testType=" + testType+ "&testSuite=" + requestBody.testSuite;
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
				self.testResultAPI.testResult(header,
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
				self.openConsole();
			} else {
				self.closeConsole();
			}
		},
		
		openConsole : function() {
			$('.testSuiteTable').append('<div class="mask" style="display: block;"></div>');
			$('.mask').show();
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
			
			var height = $(window).height();
			var resultvalue = 0;
			$('.mainContent').prevAll().each(function() {
				var rv = $(this).height();
				resultvalue = resultvalue + rv; 
			});
			var footervalue = $('.footer_section').height();
			resultvalue = resultvalue + footervalue + 200;
			finalHeight = height - resultvalue;
			$(".unit_progress").css("height", finalHeight + 10);
			$('.unit_progress').find('.scrollContent').css("height", finalHeight - 20);
		},
		
		closeConsole : function() {
			var value = $('.unit_info').width();
			var value1 = $('.unit_progress').width();
			$('.unit_info').animate({left: '20'},500);
			$('.unit_progress').animate({right: -value1},500);
			$('.unit_close').animate({right: '0px'},500);
			$('.unit_info table').removeClass("small").addClass("big");
			$('#consoleImg').attr('data-flag','true');
			$('.mask').fadeOut(500, function() {
				$('.mask').remove();
			});
		},
		
		resizeTestResultTable : function(divId) {
			var w1 = $("#" + divId + " .scrollContent tr td:first-child").width();
			var w2 = $("#" + divId + " .scrollContent tr td:nth-child(2)").width();
			var w3 = $("#" + divId + " .scrollContent tr td:nth-child(3)").width();
			var w4 = $("#" + divId + " .scrollContent tr td:nth-child(4)").width();
			var w5 = $("#" + divId + " .scrollContent tr td:nth-child(5)").width();
			var w6 = $("#" + divId + " .scrollContent tr td:nth-child(6)").width();
			
			$("#" + divId + " .fixedHeader tr th:first-child").css("width", w1);
			$("#" + divId + " .fixedHeader tr th:nth-child(2)").css("width", w2);
			$("#" + divId + " .fixedHeader tr th:nth-child(3)").css("width", w3);
			$("#" + divId + " .fixedHeader tr th:nth-child(4)").css("width", w4);
			$("#" + divId + " .fixedHeader tr th:nth-child(5)").css("width", w5);
			$("#" + divId + " .fixedHeader tr th:nth-child(6)").css("width", w6);
		},
		
		resizeConsoleWindow : function() {
			var twowidth = window.innerWidth-60;
			var progwidth = window.innerWidth/2;
			var onewidth = window.innerWidth - (twowidth+70);
			$('.unit_info').css("width",twowidth);
			$('.unit_progress').css("width",progwidth);
			$('.unit_progress').css("right",-twowidth);
			$('.unit_close').css("right",0);
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
			var content = "";
			if (pdfReports !== undefined && pdfReports !== null && pdfReports.length > 0) {
				$("#noReport").hide();
				$("#availablePdfRptsTbl").show();
				var content = "";
				for (i in pdfReports) {
					content = content.concat('<tr class="generatedRow"><td>' + pdfReports[i].time + '</td>');
					content = content.concat('<td>' + pdfReports[i].type + '</td>');
					content = content.concat('<td><a class="tooltiptop" fileName="' + pdfReports[i].fileName + '" href="#"');
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
		showLog : function(data) {
			$('.log').on("click", function() {
				var testcaseName = $(this).attr("testcaseName");
				var logType = $(this).attr("type");
				var description = "";
				var type = "";
				if (data !== null) {
					for (i in data) {
						var testcase = data[i];
						if (testcaseName === testcase.name) {
							if ("failure" === logType) {
								description = testcase.testCaseFailure.description;
								type = testcase.testCaseFailure.failureType;
							} else if ("error" === logType) {
								description = testcase.testCaseError.description;
								type = testcase.testCaseError.errorType;
							}
							break;
						}
					}
				}
			});
		}
	});

	return Clazz.com.components.testResult.js.listener.TestResultListener;
});