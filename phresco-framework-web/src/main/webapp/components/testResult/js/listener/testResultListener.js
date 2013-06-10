define(["testResult/api/testResultAPI"], function() {

	Clazz.createPackage("com.components.testResult.js.listener");

	Clazz.com.components.testResult.js.listener.TestResultListener = Clazz.extend(Clazz.Widget, {
		
		testResultAPI : null,
		
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
			self.requestBody = {};
			self.requestBody.testSuite = commonVariables.testSuiteName;
			self.getTestResult(self.getActionHeader(self.requestBody, "getTestReport"), function(response) {
				$("#testSuites").hide();
				$("#testCases").show();
				self.constructTestReport(response.data);
				commonVariables.loadingScreen.removeLoading();
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
			var resultTemplate = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0">'+
          							'<thead><tr><th>Name</th><th>Class</th><th>Time</th><th>Status</th><th>Log</th></tr></thead><tbody>';
			for (i in data) {
				var result = data[i];
				resultData = '<tr><td>'+ result.name+'</td><td>'+ result.testClass+'</td><td>'+ result.time+'</td>'+
							'<td><a href="#"><img src="themes/default/images/helios/status_ok.png" width="16" height="13" border="0" alt=""></a></td>'+
	            			'<td><a href="#"><img src="themes/default/images/helios/log_icon.png" width="14" height="19" border="0" alt=""></a></td></tr>';
				resultTemplate = resultTemplate.concat(resultData);
			}
			$("#testReport").html(resultTemplate);
		},
		
		onUnitTestGraph : function() {
			$("#testSuites").hide();
			$("#testCases").hide();
			$("#graphView").show();
			$("#graphicalView").html('')

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
			var appDirName = self.testResultAPI.localVal.getSession('appDirName');
			var techReport = $('#reportOptionsDrop').attr("value");
			var moduleName = $('#modulesDrop').attr("value");
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			}
			
			var currentTab = commonVariables.navListener.currentTab;
			var testType = "";
			if ("unitTest" === currentTab) {
				testType = "unit";
			} else if ("functionalTest" === currentTab) {
				testType = "functional";
			} else if ("componentTest" === currentTab) {
				testType = "component";
			}
			if (action == "getTestSuite") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testsuites?&appDirName=" + appDirName + "&testType=" + testType;
				if (techReport != undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
				if (moduleName != undefined) {
					header.webserviceurl = header.webserviceurl.concat("&moduleName=" + moduleName);
				}
			}
			if (action == "getTestReport") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testreports?&appDirName=" + appDirName + "&testType=" + testType+ "&testSuite=" + requestBody.testSuite;
				if (techReport != undefined) {
					header.webserviceurl = header.webserviceurl.concat("&techReport=" + techReport);
				}
				if (moduleName != undefined) {
					header.webserviceurl = header.webserviceurl.concat("&moduleName=" + moduleName);
				}
			}
			return header;
		},
		
		getTestResult : function(header, callback) {
			var self = this;
			try {
				self.testResultAPI.testResult(header,
					function(response) {
						if (response !== null) {
							callback(response);
//							commonVariables.loadingScreen.removeLoading();
						} else {
							commonVariables.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}
					}
				);
			} catch(exception) {
				
			}
		}
	});

	return Clazz.com.components.testResult.js.listener.TestResultListener;
});