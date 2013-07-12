define(["performanceTest/api/performanceTestAPI"], function() {

	Clazz.createPackage("com.components.performanceTest.js.listener");

	Clazz.com.components.performanceTest.js.listener.PerformanceTestListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		performanceTestAPI : null,
		dynamicpage : null,
		dynamicPageListener : null,
		mavenServiceListener : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
			if (self.performanceTestAPI === null) {
				self.performanceTestAPI =  new Clazz.com.components.performanceTest.js.api.PerformanceTestAPI();
			}
			if (self.mavenServiceListener === null)	{
			}
		},
		
		onGraphicalView : function() {
			var self = this;
		},
		
		onTabularView : function() {
			var self = this;
		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, appDirName = self.performanceTestAPI.localVal.getSession("appDirName"),
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
					
			if(action === "resultAvailable") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performance + "?appDirName="+appDirName;				
			} else if (action === "getTestResults") {
				var testAgainst = requestBody.testAgainsts[0];
				var resultFileName = requestBody.testResultFiles[0];
				var deviceId = "";
				
				if (requestBody.devices.length !== 0) {
					deviceId = requestBody.devices[0];
				}
				
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performanceTestResults + "?appDirName="+appDirName+
					"&testAgainst=" + testAgainst + "&resultFileName=" + resultFileName + "&deviceId=" + deviceId + "&showGraphFor=responseTime";
			
			} else if (action === "getTestResultsOnChange") {
				var testAgainst = requestBody.testAgainst;
				var resultFileName = requestBody.resultFileName;
				var deviceId = "";
				
				/* if (requestBody.devices.length !== 0) {
					deviceId = requestBody.devices[0];
				} */
				
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performanceTestResults + "?appDirName="+appDirName+
					"&testAgainst=" + testAgainst + "&resultFileName=" + resultFileName + "&deviceId=" + deviceId + "&showGraphFor=responseTime";
			} else if (action === "getfiles") {
				header.requestMethod = "POST";
				header.requestPostBody = requestBody;				
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testResultFiles?actionType=performance-test&appDirName="+appDirName;				
			}
			return header;
		},
		
		getPerformanceTestReportOptions : function(header, whereToRender, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				self.performanceTestAPI.performanceTest(header, function(response) {
					if (response !== null) {
						//commonVariables.loadingScreen.removeLoading();
						callback(response, whereToRender);
					} else {
						//commonVariables.loadingScreen.removeLoading();
						callback({"status" : "service failure"}, whereToRender);
					}
				},
				function(textStatus){
					//commonVariables.loadingScreen.removeLoading();
					callback({"status" : "service failure"});
				});
			} catch (exception) {
				//commonVariables.loadingScreen.removeLoading();
			}
		},


		renderPerformanceTemplate  : function(response, renderFunction, whereToRender, callback) {
			var self = this, responseData = response.data;
			var performanceTestOptions = {};
			performanceTestOptions.testAgainsts = responseData.testAgainsts;
			performanceTestOptions.resultAvailable = responseData.resultAvailable;
			performanceTestOptions.showDevice = responseData.showDevice;
			performanceTestOptions.testResultFiles = responseData.testResultFiles;
			performanceTestOptions.devices = responseData.devices;
			var userPermissions = JSON.parse(self.performanceTestAPI.localVal.getSession('userPermissions'));
			performanceTestOptions.userPermissions = userPermissions;
			renderFunction(performanceTestOptions, whereToRender);
			if ((performanceTestOptions.testAgainsts.length !== 0 || performanceTestOptions.devices.length !== 0) && performanceTestOptions.testResultFiles.length !== 0) {
				self.getTestResults(self.getActionHeader(performanceTestOptions, "getTestResults"), function(response) {
					
					var resultData = response.data;
					if (resultData.perfromanceTestResult.length > 0) {
						self.constructResultTable(resultData, whereToRender);
					}
				});
			} 
		},

		fileNameChangeEvent : function(whereToRender){
			var self = this;
			//To select the test result file
			$('li a[name="resultFileName"]').unbind("click");
			$('li a[name="resultFileName"]').click(function() {
				$("#testResultFileDrop").text($(this).text());
				$(".perfResultInfo").html('');
				self.getResultOnChangeEvent($("#testAgainstsDrop").text(),$(this).text(), whereToRender);
			});		
		},
		
		getResultFiles : function (testAgainst, whereToRender) {
			var self = this;
			self.getTestResults(self.getActionHeader(JSON.stringify([testAgainst]), "getfiles"), function(response) {
				if(response.data !== null){
					var returnVal = '';
					$("#testResultFileDrop").html(response.data[0]);
					$.each(response.data, function(index, value){
						returnVal += '<li class="testResultFilesOption"><a href="#" name="resultFileName">'+ value +'</a></li>';
					});
					$("#resultFiles").html(returnVal);
					self.fileNameChangeEvent(whereToRender);
					self.getResultOnChangeEvent(testAgainst, response.data[0], whereToRender);
				}
			});
		},
				
		getResultOnChangeEvent : function (testAgainst, resultFileName, whereToRender) {
			var self = this;
			var reqData = {};
			reqData.testAgainst = testAgainst;
			reqData.resultFileName = resultFileName;
			self.getTestResults(self.getActionHeader(reqData, "getTestResultsOnChange"), function(response) {
				//console.info('test = ' , JSON.stringify(response));
				if(response.message === "Parameter returned successfully"){
					var resultData = response.data;
					self.constructResultTable(resultData, whereToRender);
				}
			});
		},
		
		getTestResults : function (header, callback) {
			var self = this;
			try {
				//commonVariables.loadingScreen.showLoading();
				self.performanceTestAPI.performanceTest(header, function(response) {
					if (response !== null) {
						//commonVariables.loadingScreen.removeLoading();
						callback(response);
					} else {
						//commonVariables.loadingScreen.removeLoading();
						callback({"status" : "service failure"});
					}
				},
				function(textStatus){
					//commonVariables.loadingScreen.removeLoading();
					callback({"status" : "service failure"});
				}
				);
			} catch (exception) {
				
			}
		},
		
		constructResultTable : function(resultData, whereToRender) {
			var self = this;
			var resultTable = "";
			//$(".perfResultInfo").html('');
			var totalValue = resultData.length, NoOfSample = 0, avg = 0, min = 0, max = 0, StdDev = 0, Err = 0, KbPerSec = 0, sumOfBytes = 0;
			resultTable += '<table cellspacing="0" cellpadding="0" border="0" class="table table-striped table_border table-bordered" id="testResultTable">'+
						  '<thead><tr><th>Label</th><th>Samples</th><th>Averages</th><th>Min</th><th>Max</th><th>Std.Dev</th><th>Error %</th><th>Throughput /sec </th>' +
						  '<th>KB / sec</th><th>Avg.Bytes</th></tr></thead><tbody>';	
			$.each(resultData.perfromanceTestResult, function(index, value) {
				NoOfSample = parseInt(NoOfSample) + parseInt(value.noOfSamples);
				avg = avg + value.avg;
				if (index === 0) {
					min = value.min;
            		max = value.max;
				}
				if (index !== 0 && value.min < min) {
            		min = value.min;
            	}
				if (index !== 0 && value.max > max) {
            		max = value.max;
            	}
				StdDev = parseInt(StdDev) + parseInt(value.stdDev);
				Err = parseInt(Err) + parseInt(value.err);
            	sumOfBytes = parseInt(sumOfBytes) + parseInt(value.avgBytes);
            	
				resultTable += '<tr><td>'+ value.label +'</td><td>' + value.noOfSamples + '</td><td>' + value.avg + '</td><td>' + value.min + '</td>' + 
                    		  '<td>' + value.max + '</td><td>'+ value.stdDev +'</td><td>' + value.err + ' %</td><td>' + value.throughtPut + '</td>' + 
                    		  '<td>' + value.kbPerSec + '</td><td>' + value.avgBytes + '</td></tr>';
			});
			
			var avgBytes = parseInt(sumOfBytes) / parseInt(totalValue);
          	//	KbPerSec = (parseInt(avgBytes) / 1024) * parseInt(totalThroughput);
          	var totAvg = parseInt(avg) / parseInt(totalValue);
			resultTable += '</tbody><tfoot><tr><td>Total</td><td>'+ resultData.aggregateResult.sample +'</td><td>'+resultData.aggregateResult.average+'</td><td>'+resultData.aggregateResult.min+'</td><td>'+resultData.aggregateResult.max+'</td>'+
			'<td>'+resultData.aggregateResult.stdDev+'</td><td>'+ resultData.aggregateResult.error+' %</td><td>'+resultData.aggregateResult.throughput+'</td><td>'+resultData.aggregateResult.kb+'</td><td>'+resultData.aggregateResult.avgBytes+'</td></tr></tfoot></table>';
			whereToRender.find(".perfResultInfo").html(resultTable);
			

			self.resizeConsoleWindow();
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
		
		showHideConsole : function() {
			var self = this;
			var check = $('#consoleImg').attr('data-flag');
			if (check === "true") {
				self.openConsole();
			} else {
				self.closeConsole();
			}
		},
		
		resultBodyResize : function(){
			var resultvalue = 0;
			$('.content_main').prevAll().each(function() {
				resultvalue = resultvalue + $(this).height(); 
			});
			
			resultvalue = resultvalue + $('.footer_section').height() + 65;
			$('.performanceTestResults').height($(window).height() - (resultvalue + 80));
		},

	});

	return Clazz.com.components.performanceTest.js.listener.PerformanceTestListener;
});