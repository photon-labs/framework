define(['lib/RGraph_common_core-1.0','lib/RGraph_common_tooltips-1.0','lib/RGraph_common_effects-1.0','lib/RGraph_pie-1.0','lib/RGraph_bar-1.0','lib/RGraph_line-1.0','lib/RGraph_common_key-1.0'], function() {

	Clazz.createPackage("com.components.testResult.js.listener");

	Clazz.com.components.testResult.js.listener.TestResultListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		allTestCases : null,
		hasError: false,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
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
					console.info("testSuites[i].failures====",testSuites[i].failures)
					graphData.success = Math.round(testSuites[i].success);
					console.info("testSuites[i].success===",testSuites[i].success)
					graphData.notApplicable = Math.round(testSuites[i].notApplicable);
					console.info("testSuites[i].notApplicable====",testSuites[i].notApplicable)
					graphData.blocked = Math.round(testSuites[i].blocked);
					console.info("testSuites[i].blocked===", testSuites[i].blocked)
					graphData.total = testSuites[i].total;
					console.info("testSuites[i].total====", testSuites[i].total);
					graphData.notExecuted = testSuites[i].notExecuted;
					console.info(" testSuites[i].notExecuted=====",  testSuites[i].notExecuted);
					callback(graphData);
					break;
				}
			}
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
			
			var pie2 = new RGraph.Pie('pie',[graphData.success, graphData.failures, graphData.notApplicable, graphData.blocked, graphData.notExecuted]); // Create the pie object
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
			$("#graphicalView").html('<img src="themes/default/images/Phresco/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
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
			var projectInfo = commonVariables.api.localVal.getProjectInfo();
			var appDirName = "";
			var techReport = $('#reportOptionsDrop').attr("value");
			// var moduleName = $('#modulesDrop').attr("value");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			
			var moduleParam = self.isBlank($('.moduleName').val()) ? "" : '&moduleName='+$('.moduleName').val();
			var rootModuleParam = moduleParam;
			
			if (!self.isBlank(moduleParam)) {
				appDirName = $('.rootModule').val()
			} else if(projectInfo !== null && projectInfo !== undefined) {
				appDirName = projectInfo.data.projectInfo.appInfos[0].appDirName;
			}
			
			if (self.isBlank(moduleParam) && !self.isBlank($('#modulesDrop').attr('value'))) {
				moduleParam = '&moduleName='+$('#modulesDrop').attr('value');
			}

			var currentTab = commonVariables.navListener.currentTab;
			var testType = "";
			if ("unitTest" === currentTab) {
				testType = "unit";
			} else if ("functionalTest" === currentTab) {
				testType = "functional";
			} else if ("componentTest" === currentTab) {
				testType = "component";
			} else if ("manualTest" === currentTab) {
				testType = "manual";
			} else if ("integrationTest" === currentTab) {
			    appDirName = $("#editprojecttitle").attr('projectname');
 				testType = "integration";
			}
			if (action === "getTestSuite") {
				header.requestMethod = "GET";
				if ("manualTest" === currentTab) {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + "/testsuites?appDirName=" + appDirName + moduleParam;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testsuites?appDirName=" + appDirName + "&testType=" + testType + moduleParam;
					if ("integrationTest" === currentTab) {
						header.webserviceurl = header.webserviceurl + "&projectCode=" + commonVariables.projectCode;
					}
				}
				
				if (techReport !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
			} else if (action === "getTestReport") {
				header.requestMethod = "GET";
				if ("manualTest" === currentTab) {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + "/testcases?appDirName=" + appDirName+"&testSuiteName=" + requestBody.testSuite + moduleParam;
				} else {
					header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testreports?appDirName=" + appDirName +
					"&testType=" + testType+ "&testSuite=" + requestBody.testSuite + moduleParam;
					if ("integrationTest" === currentTab) {
						header.webserviceurl = header.webserviceurl + "&projectCode=" + commonVariables.projectCode;
					}
				}
				
				if (techReport !== undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
			} else if (action === "generatePdfReport") {
				commonVariables.hideloading = true;
				var data = $('#pdfReportForm').serialize();
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + "app/printAsPdf?appDirName=" + appDirName + "&" + data + "&userId=" + userId + rootModuleParam;
			} else if (action === "getPdfReports") {
				var data = $('#pdfReportForm').serialize();
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/showPopUp?appDirName=" + appDirName + "&fromPage=" + testType + rootModuleParam;
			} else if (action === "deletePdfReport") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/deleteReport?appDirName=" + appDirName + "&fromPage=" + testType + "&reportFileName=" + requestBody.fileName + rootModuleParam;
			} else if (action === "downloadPdfReport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/downloadReport?appDirName=" + appDirName + "&fromPage=" + testType + "&reportFileName=" + requestBody.fileName + rootModuleParam;
			} else if (action === "updateTestcase") {
				header.requestMethod = "PUT";
				header.requestPostBody = JSON.stringify(requestBody);
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.manual + "/testcases?appDirName=" + appDirName+"&testSuiteName=" + requestBody.testSuite + moduleParam;
			} else if (action === "validation") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "util/validation?appDirName="+appDirName+"&customerId="+self.getCustomer()+"&phase="+requestBody.phase+"&"+requestBody.queryString + moduleParam;
			}
			return header;
		},
		
		mandatoryValidation : function (phase, goal, queryString, callback) {
			var self = this, requestBody = {};
			requestBody.phase = phase;
			requestBody.goal = goal;
			requestBody.queryString = queryString;
			self.performAction(self.getActionHeader(requestBody, "validation"), function(response) {
				if ((response.errorFound) || (response.status === "error") || (response.status === "failure")){
					if (response.configErr) {
						commonVariables.api.showError(response.responseCode ,"error", true)
					} else {
						self.showDynamicErrors(response);
					}
					callback(false);
				} else {
					callback(true);
				}
			}); 
		},
		
		performAction : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header,
					function(response) {
						if (response !== null && (response.status !== "error" || response.status !== "failure")) {
							callback(response);
						} else {
							//commonVariables.loadingScreen.removeLoading();
							$(".content_end").show();
							$(".msgdisplay").removeClass("success").addClass("error");
							$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
							self.renderlocales(commonVariables.contentPlaceholder);	
							$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
							setTimeout(function() {
								$(".content_end").hide();
							},2500);
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
			self.copyLog();
			
		},
		
		//To get the existing pdf reports
		getPdfReports : function() {
			var self = this;
			var requestBody = {};
			commonVariables.hideloading = true;
			self.performAction(self.getActionHeader(requestBody, "getPdfReports"), function(response) {
				$('.progress_loading').hide();
				self.listPdfReports(response.data.json);
				commonVariables.hideloading = false;
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
					content = content.concat('<td><a class="tooltiptop" fileName="' + pdfReports[i].fileName + '" href="javascript:void(0)"');
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="downloadPdfReport" data-original-title="Download Pdf" title="">');
					content = content.concat('<img src="themes/default/images/Phresco/download_icon.png" width="15" height="18" border="0" alt="0"></a></td>');
					content = content.concat('<td><a class="tooltiptop deletePdf" fileName="' + pdfReports[i].fileName + '" href="javascript:void(0)"');
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="delete" data-original-title="Delete Pdf" title="">');
					content = content.concat('<img src="themes/default/images/Phresco/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>');
				}
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbdy").html(content);
				self.pdfReportEvents();
			} else {
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbl").hide();
				$("#noReport").show();
				$("#noReport").html("No Report are Available");
			}
			self.hidePopupLoading($('#pdfReportLoading'));
			$(".tooltiptop").tooltip();
		},
		
		//To generate the pdf report
		generatePdfReport : function() {
			var self = this;
			var requestBody = {};
			self.performAction(self.getActionHeader(requestBody, "generatePdfReport"), function(response) {
				$("input[name=pdfName]").val('');
				self.getPdfReports();
			});
		},
		
		getTestsuites : function(callback) {
			var self = this;
			self.performAction(self.getActionHeader(self.requestBody, "getTestSuite"), function(response) {
				if (!self.isBlank(response.data)) {
					$('#tabularView').show();
				} else {
					$('#tabularView').hide();
				}
				callback(response);
			});
		},
		
		updateManualTestCase_popup : function() {
			var self = this;
			// update testcase
			$('.dyn_popup_close').unbind("click");
			$('.dyn_popup_close').click(function() {
				var dynClass = $('a[name=updateManualTestCase_popup]').attr('class');
				$('div[id=' + dynClass + ']').hide();
			});
		},
		
		pdfReportEvents : function() {
			var self = this;
			//To delete the selected pdf report
			$('.deletePdf').on("click", function() {
				self.showPopupLoading($('#pdfReportLoading'));
				var requestBody = {};
				requestBody.fileName = $(this).attr('fileName');
				self.performAction(self.getActionHeader(requestBody, "deletePdfReport"), function(response) {
					self.getPdfReports();
				});
			});

			//To download the selected pdf report
			$('a[name=downloadPdfReport]').on("click", function() {
				var requestBody = {};
				requestBody.fileName = $(this).attr('fileName');
				var header = self.getActionHeader(requestBody, "downloadPdfReport");
				window.open(header.webserviceurl, '_self');
			});
			
			$("input[name='pdfName']").unbind('input');
			$("input[name='pdfName']").bind('input propertychange', function(){
				$(this).val(self.specialCharValidation($(this).val().replace(/\s/g, "")));
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
		},
		
		
		valid : function(item, msg) {
			$(item).focus();
			$(item).attr('placeholder', msg);
			$(item).addClass("errormessage");
			$(item).bind('keypress', function() {
				$(this).removeClass("errormessage");
			});
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
			$('.unit_progress').height(deductionHeight);
		}
		
	});

	return Clazz.com.components.testResult.js.listener.TestResultListener;
});