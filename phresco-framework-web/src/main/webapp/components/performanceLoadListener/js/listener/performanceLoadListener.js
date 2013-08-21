define(["lib/jquery-tojson-1.0",'lib/RGraph_common_core-1.0','lib/RGraph_common_tooltips-1.0','lib/RGraph_common_effects-1.0','lib/RGraph_pie-1.0','lib/RGraph_bar-1.0','lib/RGraph_line-1.0','lib/RGraph_common_key-1.0'], function() {

	Clazz.createPackage("com.components.performanceLoadListener.js.listener");

	Clazz.com.components.performanceLoadListener.js.listener.PerformanceLoadListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		
		dynamicpage : null,
		dynamicPageListener : null,
		mavenServiceListener : null,
		showDevice : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		onGraphicalView : function() {
			var self = this;
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_on.png" width="25" height="25" border="0" alt=""><b>Graph View</b>');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_off.png" width="25" height="25" border="0" alt="">Table View');
			$('.perfResultInfo').hide();
			$('#graphView').show();

			if ($('#graphForDrop').attr("value") !== "all") {
				$("#allData").hide();
			} else {
				$("#allData").show();
			}
		},
		
		onTabularView : function() {
			var self = this;
			$("#graphicalView").html('<img src="themes/default/images/helios/quality_graph_off.png" width="25" height="25" border="0" alt="">Graph View');
			$("#tabularView").html('<img src="themes/default/images/helios/quality_table_on.png" width="25" height="25" border="0" alt=""><b>Table View</b>');
			$('#graphView').hide();
			$('.perfResultInfo').show();

		},
		
		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(requestBody, action) {
			var self = this, appDirName = commonVariables.api.localVal.getSession("appDirName"),
			header = {
				contentType: "application/json",				
				dataType: "json",
				webserviceurl: ''
			};
			var fromAction = $('.testingType').val();
			var userInfo = JSON.parse(commonVariables.api.localVal.getSession('userInfo'));
			var userId = userInfo.id;	
			if (action === "loadResultAvailable") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.load + "?appDirName="+appDirName;				
			} else if(action === "resultAvailable") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performance + "?appDirName="+appDirName;				
			} else if (action === "getTestResults") {
				var testAgainst = "";
				var resultFileName = requestBody.testResultFiles[0];
				var deviceId = "";
				
				if (!self.isBlank(requestBody.testAgainsts[0])) {
					testAgainst = requestBody.testAgainsts[0];
				} 
				
				if (!self.isBlank(requestBody.device) && requestBody.devices.length !== 0) {
					deviceId = requestBody.devices[0].split("#SEP#")[0];
				}

				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performanceTestResults + "?appDirName="+appDirName+
					"&testAgainst=" + testAgainst + "&resultFileName=" + resultFileName + "&deviceId=" + deviceId + "&showGraphFor=responseTime&from=" + requestBody.from;
			} else if (action === "getTestResultsOnChange") {
				var testAgainst = requestBody.testAgainst;
				var resultFileName = requestBody.resultFileName;
				var showGraphFor = requestBody.showGraphFor;
				var deviceId = requestBody.deviceId;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/" + commonVariables.performanceTestResults + "?appDirName="+appDirName+
					"&testAgainst=" + testAgainst + "&resultFileName=" + resultFileName + "&deviceId=" + deviceId + "&showGraphFor=" + showGraphFor +"&from=" +fromAction;
			} else if (action === "getfiles") {
				header.requestMethod = "POST";
				header.requestPostBody = requestBody;				
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/testResultFiles?actionType="+fromAction+"&appDirName="+appDirName;				
			} else if (action === "getPdfReports") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/showPopUp?appDirName=" + appDirName + "&fromPage="+requestBody.from;
			} else if (action === "generatePdfReport") {
				var data = $('#pdfReportForm').serialize();
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + "app/printAsPdf?appDirName=" + appDirName + "&" + data + "&userId=" + userId;
			} else if (action === "deletePdfReport") {
				header.requestMethod = "DELETE";
				header.webserviceurl = commonVariables.webserviceurl + "pdf/deleteReport?appDirName=" + appDirName + "&fromPage="+ requestBody.from + "&reportFileName=" + requestBody.fileName;
			} else if(action === "getDevices") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.qualityContext + "/devices?appDirName="+appDirName+"&resultFileName="+requestBody.resultFileName;
			} else if (action === "validation") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "util/validation?appDirName="+appDirName+"&customerId="+self.getCustomer()+"&phase="+requestBody.phase+"&"+requestBody.queryString;
			}

			return header;
		},
		
		getTestReportOptions : function(header, whereToRender, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
					if (response !== null) {
						self.showDevice = response.data.showDevice;
						callback(response, whereToRender);
					} else {
						callback({"status" : "service failure"}, whereToRender);
					}
				},
				function(textStatus){
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
					self.renderlocales(commonVariables.contentPlaceholder);		
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
				});
			} catch (exception) {
			}
		},

		renderLoadTemplate  : function(response, renderFunction, whereToRender, callback) {
			var self = this, responseData = response.data;
			var loadTestOptions = {};
			loadTestOptions.testAgainsts = responseData.testAgainsts;
			loadTestOptions.resultAvailable = responseData.resultAvailable;
			loadTestOptions.testResultFiles = responseData.testResultFiles;
			loadTestOptions.from = "load-test";
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			loadTestOptions.userPermissions = userPermissions;
			renderFunction(loadTestOptions, whereToRender);
			if (loadTestOptions.testAgainsts.length !== 0 && !self.isBlank(loadTestOptions.testResultFiles) && loadTestOptions.testResultFiles.length !== 0) {
				self.getTestResults(self.getActionHeader(loadTestOptions, "getTestResults"), function(response) {
					var resultData = response.data;
					if (resultData.perfromanceTestResult.length > 0) {
						self.constructResultTable(resultData, whereToRender);
						self.drawChart(resultData);
						self.showScreenShot(resultData.images);
					}
					if (callback !== undefined) {
						callback();
					}
				});
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
			var userPermissions = JSON.parse(commonVariables.api.localVal.getSession('userPermissions'));
			performanceTestOptions.userPermissions = userPermissions;
			performanceTestOptions.from = 'performance-test';
			renderFunction(performanceTestOptions, whereToRender);
			if ((performanceTestOptions.testAgainsts.length !== 0 || performanceTestOptions.devices.length !== 0) && !self.isBlank(performanceTestOptions.testResultFiles) && performanceTestOptions.testResultFiles.length !== 0) {
				self.getTestResults(self.getActionHeader(performanceTestOptions, "getTestResults"), function(response) {
					var resultData = response.data;
					if (!self.isBlank(resultData)	 && resultData.perfromanceTestResult.length > 0) {
						self.constructResultTable(resultData, whereToRender);
						self.drawChart(resultData);
						self.showScreenShot(resultData.images);
					}
					if (callback !== undefined) {
						callback();
					}
				});
			} 
		},

		drawChart : function(data) {
			var self = this, chartTitle = "";
            var chartUnit = "";
            if (data.graphFor === "responseTime") {
            	chartTitle = "Aggr Response Time";
            	chartUnit = "ms";
            } else if (data.graphFor === "throughPut") {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            } else if (data.graphFor === "minResponseTime") {
            	chartTitle = "Min Response Time";
            	chartUnit = "ms";
            } else if (data.graphFor === "maxResponseTime") {
            	chartTitle = "Max Response Time";
            	chartUnit = "ms";
            } else if (data.graphFor === "all") {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            }

			//line chart color
	      	var minColor = "#00A8F0";
	      	var maxColor = "#008000";
	      	var avgColor = "red";

	      	var graphDataArray = data.graphData.replace("[","").replace("]","").split(",");
	      	var values = [];
	      	$.each(graphDataArray, function(index, value){
				values.push(parseInt(value));
			});

	      	var labelArr = data.label.replace("[", "").replace("]", "").split(",");
	      	var labelString = "";

	      	$.each(labelArr, function(index, value){
				if (index === labelArr.length - 1) {
					labelString += value.replace("'","").replace("'","");
				} else {
					labelString += value.replace("'","").replace("'","") + ",";
				}
			});

	      	RGraph.Clear(document.getElementById('myCanvas'));
	      	RGraph.Clear(document.getElementById('allData'));
			var bar = new RGraph.Bar('myCanvas',[values]);
	        bar.Set('chart.gutter.left', 70);
	        bar.Set('chart.text.color', "#4C4C4C");
	        bar.Set('chart.background.barcolor1', 'transparent');
	        bar.Set('chart.background.barcolor2', 'transparent');
	        bar.Set('chart.background.grid', true);
	        bar.Set('chart.colors', ["#39BC67"]);
	        bar.Set('chart.background.grid.width', 0.5);
	        bar.Set('chart.text.angle', 45);
	        bar.Set('chart.gutter.bottom', 140);
	        bar.Set('chart.background.grid.color', "#4F577C");
	        bar.Set('chart.axis.color', "#323232");
	        bar.Set('chart.title', chartTitle);
	        bar.Set('chart.title.color', "#4C4C4C");
	        bar.Set('chart.labels', labelString.split(","));
	        bar.Set('chart.units.post', chartUnit);
	        bar.Draw();

        	if (data.graphFor ===  "all") {
				var line = new RGraph.Line('allData', [values]);
		        line.Set('chart.background.grid', true);
		        line.Set('chart.linewidth', 5);
		        line.Set('chart.gutter.left', 85);
		        line.Set('chart.text.color', "#4C4C4C");
		        line.Set('chart.hmargin', 5);
		        if (!document.all || RGraph.isIE9up()) {
		            line.Set('chart.shadow', true);
		        }
		        line.Set('chart.tickmarks', 'endcircle');
		        line.Set('chart.units.post', 's');
		        line.Set('chart.colors', [minColor, avgColor, maxColor]);
		        line.Set('chart.background.grid.autofit', true);
		        line.Set('chart.background.grid.autofit.numhlines', 10);
		        line.Set('chart.curvy', true);
		        line.Set('chart.curvy.factor', 0.5); // This is the default
		        line.Set('chart.animation.unfold.initial',0);
		        line.Set('chart.labels',labelString.split(","));
		        line.Set('chart.title','Response Time');// Title
		        line.Set('chart.axis.color', "#323232");
		        line.Set('chart.text.angle', 45);
		        line.Set('chart.gutter.bottom', 140);
		        line.Set('chart.key', ['Min','Avg','Max']);
		        line.Set('chart.background.grid.color', "#4F577C");
		        line.Set('chart.title.color', "#4C4C4C");
		        line.Set('chart.shadow', false);
		        line.Draw();
        	}
		},

		fileNameChangeEvent : function(whereToRender){
			var self = this;
			//To select the test result file
			$('li a[name="resultFileName"]').unbind("click");
			$('li a[name="resultFileName"]').click(function() {
				var currentOption = $(this).text();
				$("#testResultFileDrop").html(currentOption   + '<b class="caret"></b>');
				$("#testResultFileDrop").attr("value", currentOption);
				$(".perfResultInfo").html('');
				var showGraphFor = $("#graphForDrop").attr("value");
				self.getResultOnChangeEvent($("#testAgainstsDrop").attr("value"), $(this).text(), showGraphFor, '', whereToRender);
			});		
		},
		
		deviceChangeEvent : function (whereToRender) {
			var self = this;
			$('li a[name="devices"]').unbind("click");
			$('li a[name="devices"]').click(function() {
				var previousDevice = $("#deviceDropDown").attr("value");
				var currentDevice = $(this).attr("deviceid");
				if (previousDevice !== currentDevice) {
					$("#deviceDropDown").html($(this).text()  + '<b class="caret"></b>');
					$("#deviceDropDown").attr("value", currentDevice);
					self.getResultOnChangeEvent('',$("#testResultFileDrop").attr("value"), '', currentDevice, whereToRender, function() {
						self.setResponseTime();
					});
				}
			});
		},

		showScreenShot : function(screenShots) {
			if (screenShots.length > 0) {
				$('.performanceScreenShotDiv').show();
				var imgArray = [];
				for(var i = 0; i < screenShots.length ; i++) {
					var srcJson = {};
					srcJson.src = $('<div class="text_center"><img src="data:image/png;base64,'+screenShots[i].split("#NAME_SEP#")[0]+'"><div class="fullscreen_desc">'+screenShots[i].split("#NAME_SEP#")[1]+'</div></div>');
					srcJson.type = 'inline';
					imgArray.push(srcJson);
				}
				$('.performanceScreenShot').magnificPopup({
					items: imgArray,
					gallery: {
					  enabled: true
					},
					type: 'image'
				});
			} else {
				$('.performanceScreenShotDiv').hide();
			}
		},

		getResultFiles : function (testAgainst, whereToRender) {
			var self = this;
			self.getTestResults(self.getActionHeader(JSON.stringify([testAgainst]), "getfiles"), function(response) {
				if(response.data !== null){
					self.hideErrorAndShowControls();
					var returnVal = '';
					$("#testResultFileDrop").html(response.data[0] + "<b class='caret'></b>");
					$("#testResultFileDrop").attr("value", response.data[0]);
					$.each(response.data, function(index, value){
						returnVal += '<li class="testResultFilesOption"><a href="#" name="resultFileName">'+ value +'</a></li>';
					});
					$("#resultFiles").html(returnVal);
					self.setResponseTime();
					self.fileNameChangeEvent(whereToRender);
					self.getResultOnChangeEvent(testAgainst, response.data[0], '', '', whereToRender);
				} else if (response.status === "error" || response.status === "failure") {
					self.showErrorAndHideControls(response.responseCode);
				}
			});
		},
		
		setResponseTime : function () {
			//to make aggr response time as default option in graphical view
			$("#graphForDrop").attr("value","responseTime");
			$("#graphForDrop").html("Aggr Response Time<b class='caret'></b>");
			//to hide alldata canvas
			$("#allData").hide();
		},

		//To generate the pdf report
		generatePdfReport : function(from) {
			var self = this;
			var requestBody = {};
			self.performAction(self.getActionHeader(requestBody, "generatePdfReport"), function(response) {
				if (response.status === "success") {
					self.getPdfReports(from);
				}
			});
		},

		//To get the existing pdf reports
		getPdfReports : function(from) {
			var self = this;
			var requestBody = {};
			requestBody.from = from;
			self.performAction(self.getActionHeader(requestBody, "getPdfReports"), function(response) {
				self.listPdfReports(response, from);
			});
		},

			//To list the generated PDF reports
		listPdfReports : function(pdfReports, from) {
			var self = this;
			var content = "";
			if (pdfReports !== undefined && pdfReports !== null && pdfReports.length > 0) {
				$(".noReport").hide();
				$("#availablePdfRptsTbl").show();
				var content = "";
				for (var i = 0; i < pdfReports.length; i++) {
					content = content.concat('<tr class="generatedRow"><td>' + pdfReports[i].time + '</td>');
					content = content.concat('<td>' + pdfReports[i].type + '</td>');
					content = content.concat('<td><a class="tooltiptop donloadPdfReport" from="'+from+'" fileName="' + pdfReports[i].fileName + '" href="#"');
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="downLoad" data-original-title="Download Pdf" title="">');
					content = content.concat('<img src="themes/default/images/helios/download_icon.png" width="15" height="18" border="0" alt="0"></a></td>');
					content = content.concat('<td><a class="tooltiptop deletePdf" from="'+from+'" fileName="' + pdfReports[i].fileName + '" href="#"');
					content = content.concat(' data-toggle="tooltip" data-placement="top" name="delete" data-original-title="Delete Pdf" title="">');
					content = content.concat('<img src="themes/default/images/helios/delete_row.png" width="14" height="18" border="0" alt="0"></a></td></tr>');
				}
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbdy").html(content);
				self.deletePdfReport();
				self.downloadPdfReport();
			} else {
				$(commonVariables.contentPlaceholder).find("#availablePdfRptsTbl").hide();
				$(".noReport").show();
				$(".noReport").html("No Report are Available");
			}
			$('#pdfReportLoading').hide();
		},

		//To delete the selected pdf report
		deletePdfReport : function() {
			var self = this;
			$('.deletePdf').on("click", function() {
				$('#pdfReportLoading').show();
				var from = $(this).attr('from');
				var requestBody = {};
				requestBody.fileName = $(this).attr('fileName');
				requestBody.from = from;
				self.performAction(self.getActionHeader(requestBody, "deletePdfReport"), function(response) {
					if (response.status === "success" ) {
						self.getPdfReports(from);
					}
				});
			});
		},
		
		downloadPdfReport : function () {
			var self = this;
			$('.donloadPdfReport').on("click", function() {
				var fileName = $(this).attr("fileName"), from=$(this).attr("from"), appDirName = commonVariables.api.localVal.getSession("appDirName");
				var pdfDownloadUrl = commonVariables.webserviceurl + "pdf/downloadReport?appDirName="+appDirName+"&reportFileName="+fileName+"&fromPage="+from;
				window.open(pdfDownloadUrl, '_self');
			});
		},

		showErrorAndHideControls : function (errorCode) {
			var self = this;
			$('.perfError').show();
			$(".perfError").attr('data-i18n', 'errorCodes.' + errorCode);
			self.renderlocales(commonVariables.contentPlaceholder);
			$('.testResultDropdown').hide();
			$('.testResultDiv').hide();
			$('.performanceView').hide();
			$('#graphView').hide();
			$('.performanceScreenShotDiv').hide();
		},

		hideErrorAndShowControls : function () {
			$('.perfError').hide();
			$('.perfError').text("");
			$('.testResultDropdown').show();
			$('.testResultDiv').show();
			$('.performanceView').show();
		},

		getResultOnChangeEvent : function (testAgainst, resultFileName, showGraphFor, deviceId, whereToRender, callback) {
			var self = this;
			var reqData = {};
			if (!self.isBlank(testAgainst)) {
				reqData.testAgainst = testAgainst;
			} else {
				reqData.testAgainst = "";
			}
			
			if (!self.isBlank(deviceId)) {
				reqData.deviceId = deviceId;
			} else {
				reqData.deviceId = "";
			}

			reqData.resultFileName = resultFileName;
			
			if (self.isBlank(showGraphFor)) {
				reqData.showGraphFor = "responseTime";
			} else {
				reqData.showGraphFor = showGraphFor;
			}

			self.getTestResults(self.getActionHeader(reqData, "getTestResultsOnChange"), function(response) {
				if(response.responseCode === "PHRQ500001" || response.responseCode === "PHRQ500004"){
					var resultData = response.data;
					self.constructResultTable(resultData, whereToRender);
					self.drawChart(resultData);
					self.showScreenShot(resultData.images);
					//tabular view
					if($("#tabularView").find('img').attr('src') === "themes/default/images/helios/quality_table_on.png") {
						$(".perfResultInfo").show();
						$("#graphView").hide();
					} else {
						$("#graphView").show();
						$(".perfResultInfo").hide();
					}

					if (callback !== undefined) {
						callback(response);
					}
				}
			});
		},
		
		getDevices : function (resultFileName, showGraphFor, whereToRender, callback) {
			var self = this, reqData = {}, graphFor = "";
			reqData.resultFileName = resultFileName;
			if (self.isBlank(showGraphFor)) {
				reqData.showGraphFor = "responseTime";
			} else {
				reqData.showGraphFor = showGraphFor;
			}

			self.performAction(self.getActionHeader(reqData, "getDevices"), function(response) {
				if (response.responseCode === "PHRQ500003") {
					$("#deviceDropDown").attr("value", response.data[0].split("#SEP#")[0]);
					$("#deviceDropDown").html(response.data[0].split("#SEP#")[1] + '<b class="caret"></b>');
					var returnVal = "";
					$.each(response.data, function(index, value){
						returnVal += '<li class="devicesOption"><a href="#" name="devices" deviceid="'+value.split("#SEP#")[0]+'">'+ value.split("#SEP#")[1] +'</a></li>';
					});
					$("#deviceDropUL").html(returnVal);
					self.getResultOnChangeEvent('',resultFileName, showGraphFor, response.data[0].split("#SEP#")[0], whereToRender);
					self.deviceChangeEvent(whereToRender);
				}
			}); 
		},

		getTestResults : function (header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
					if (response !== null) {
						callback(response);
					} else {
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				},
				function(textStatus){
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
					self.renderlocales(commonVariables.contentPlaceholder);		
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
				}
				);
			} catch (exception) {
				
			}
		},

		performAction : function (header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequest(header, function(response) {
					if (response !== null) {
						callback(response);
					} else {
						$(".content_end").show();
						$(".msgdisplay").removeClass("success").addClass("error");
						$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
						self.renderlocales(commonVariables.contentPlaceholder);	
						$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
						setTimeout(function() {
							$(".content_end").hide();
						},2500);
					}
				},
				function(textStatus){
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'commonlabel.errormessage.serviceerror');
					self.renderlocales(commonVariables.contentPlaceholder);		
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
				}
				);
			} catch (exception) {
				
			}
		},
		
		constructResultTable : function(resultData, whereToRender) {
			var self = this;
			var resultTable = "";
			var totalValue = resultData.length, NoOfSample = 0, avg = 0, min = 0, max = 0, StdDev = 0, Err = 0, KbPerSec = 0, sumOfBytes = 0;
			resultTable += '<div class="fixed-table-container"><div class="header-background"></div><div class="fixed-table-container-inner"><table cellspacing="0" cellpadding="0" border="0" class="table table-striped table_border table-bordered perf_load_table" id="testResultTable">'+
						  '<thead class="height_th"><tr><th><div class="th-inner">Label</div></th><th><div class="th-inner">Samples</div></th><th><div class="th-inner">Averages</div></th><th><div class="th-inner">Min</div></th><th><div class="th-inner">Max</div></th><th><div class="th-inner">Std.Dev</div></th><th><div class="th-inner">Error %</div></th><th><div class="th-inner">Throughput /sec </div></th>' +
						  '<th><div class="th-inner">KB / sec</div></th><th><div class="th-inner">Avg.Bytes</div></th></tr></thead><tbody>';	
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
                    		  '<td>' + value.max + '</td><td>'+ value.stdDev +'</td><td>' + value.err.toFixed(2) + ' %</td><td>' + value.throughtPut + '</td>' + 
                    		  '<td>' + value.kbPerSec + '</td><td>' + value.avgBytes + '</td></tr>';
			});
			
			var avgBytes = parseInt(sumOfBytes) / parseInt(totalValue);
          	var totAvg = parseInt(avg) / parseInt(totalValue);
			resultTable += '</tbody><tfoot><tr><td>Total</td><td>'+ resultData.aggregateResult.sample +'</td><td>'+resultData.aggregateResult.average+'</td><td>'+resultData.aggregateResult.min+'</td><td>'+resultData.aggregateResult.max+'</td>'+
			'<td>'+resultData.aggregateResult.stdDev+'</td><td>'+ resultData.aggregateResult.error+' %</td><td>'+resultData.aggregateResult.throughput+'</td><td>'+resultData.aggregateResult.kb+'</td><td>'+resultData.aggregateResult.avgBytes+'</td></tr></tfoot></table></div></div>';
			whereToRender.find(".perfResultInfo").html(resultTable);
			
			self.tableScrollbar();
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

		mandatoryValidation : function (phase, queryString, dynamicPageObject) {
			var self = this, requestBody = {};
			requestBody.phase = phase;
			requestBody.queryString = queryString;
			self.performAction(self.getActionHeader(requestBody, "validation"), function(response) {console.info(response);
				if ((response.errorFound) || (response.status === "error") || (response.status === "failure")){
					$(".content_end").show();
					$(".msgdisplay").removeClass("success").addClass("error");
					$(".error").attr('data-i18n', 'errorCodes.' + response.responseCode);
					self.renderlocales(commonVariables.contentPlaceholder);	
					$(".error").show();
					$(".error").fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500).fadeOut(5);
					setTimeout(function() {
						$(".content_end").hide();
					},2500);
					dynamicPageObject.showDynamicErrors(response);
				} else {
					self.preTest(phase);
				}
			}); 
		},

		preTest : function(phase) {
			var self = this;
			if ("performance-test" === phase) {
				self.preTriggerPerformanceTest();
			} else {
				self.preTriggerloadTest();
			}
		},

		preTriggerPerformanceTest : function () {
			var self = this, testBasis = "", testAgainst = "", redirect = true, jsonString = "";
			testBasis = $("#testBasis").val();
			testAgainst = $("#testAgainst").val();
			//if performance test is triggered against parameters
			if (testAgainst !== undefined && testBasis !== undefined && testBasis === "parameters") {

				//call template mandatory fn for server or webservice
				if (testAgainst === "server" || testAgainst === "webservice") {
					redirect = self.contextUrlsMandatoryVal();
				} else if (testAgainst === "database") {//call template mandatory fn for database
					redirect = self.dbContextUrlsMandatoryVal();
				} 
			} 
			if (redirect) {
				self.triggerPerformanceTest();
			}
		},

		preTriggerloadTest : function () {
			var self = this, testBasis = "", testAgainst = "", redirect = true, jsonString = "";
			testBasis = $("#testBasis").val();
			testAgainst = $("#testAgainst").val();
			//if test is triggered against parameters
			if (testAgainst !== undefined && testBasis !== undefined && testBasis === "parameters") {
				//call template mandatory fn for server or webservice
				redirect = self.contextUrlsMandatoryVal();
			} 
			if (redirect) {
				self.constructLoadTestJson();
			}
		}, 

		constructLoadTestJson : function () {
			var self = this, formJsonStr = JSON.stringify($('#loadForm').serializeObject()), templJsonStr = "", testBasis = $("#testBasis").val(), testAgainst = $("#testAgainst").val();
			if (!self.isBlank(testBasis) && !self.isBlank(testAgainst) && testBasis === "parameters") {
				templJsonStr = self.contextUrls();
				formJsonStr = formJsonStr.slice(0,formJsonStr.length-1);
				formJsonStr = formJsonStr + ',' + templJsonStr + '}';
			}
			var json = JSON.parse('{' + templJsonStr + '}');
			self.executeTest($('#loadForm').serialize(), json, 'load', $("#loadPopup"),function(response) {
				commonVariables.api.localVal.setSession('loadConsole', $('#testConsole').html());
				$('.progress_loading').hide();
				commonVariables.navListener.onMytabEvent(commonVariables.loadTest);
			});
		},

		triggerPerformanceTest : function () {
			var self = this, jsonString = "";
			self.constructPerformanceTestJson();
		},

		executeTest : function (queryString, json, testAction, popupObj, callback) {
			queryString = queryString.concat("&testAction=" +testAction);
			var self = this, appInfo = commonVariables.api.localVal.getJson('appdetails');
			popupObj.toggle();
			self.openConsole();
			if(appInfo !== null){
				queryString +=	'&customerId='+ self.getCustomer() +'&appId='+ appInfo.data.appInfos[0].id +'&projectId=' + appInfo.data.id + '&username=' + commonVariables.api.localVal.getSession('username');
			}
			self.callMavenService(queryString, json, testAction, callback);
		},

		callMavenService : function (queryString, json, testAction, callback) {
			var self = this; 
			if (self.mavenServiceListener === null) {
				commonVariables.navListener.getMyObj(commonVariables.mavenService, function(retVal){
					self.mavenServiceListener = retVal;
					self.execute(self.mavenServiceListener, queryString, json, testAction, callback);
				});
			} else {
				self.execute(self.mavenServiceListener, queryString, json, testAction, callback);	
			}
		},

		execute : function (mavenService, queryString, postBody, testAction, callback) {
			if ('load' === testAction) {
				mavenService.mvnLoadTest(queryString, '#testConsole', postBody, function(response){
					callback(response);
				});
			} else {
				mavenService.mvnPerformanceTest(queryString, '#testConsole', postBody, function(response){
					callback(response);
				});
			}
		},

		constructPerformanceTestJson : function () {
			var self = this, formJsonStr = JSON.stringify($('#performanceForm').serializeObject()), templJsonStr = "", testBasis = $("#testBasis").val(), testAgainst = $("#testAgainst").val();
			if (!self.isBlank(testBasis) && !self.isBlank(testAgainst) && testBasis === "parameters") {
				templJsonStr = self.contextUrls() + "," + self.dbContextUrls();
				formJsonStr = formJsonStr.slice(0,formJsonStr.length-1);
				formJsonStr = formJsonStr + ',' + templJsonStr + '}';
			}
			var json = JSON.parse('{' + templJsonStr + '}');
			self.executeTest($('#performanceForm').serialize(), json, 'performance', $("#performancePopup"),function(response) {
				commonVariables.api.localVal.setSession('performanceConsole', $('#testConsole').html());
				$('.progress_loading').hide();
				commonVariables.navListener.onMytabEvent(commonVariables.performanceTest);
			});
		},

		contextUrlsMandatoryVal : function () {
			var self = this, redirect = true;
			$('.contextDivClass').each(function() {
				if (self.isBlank($(this).find($('input[name=httpName]')).val())) {
					redirect = false;
					$(this).find($('input[name=httpName]')).val('');
					$(this).find($('input[name=httpName]')).focus();
					$(this).find($('input[name=httpName]')).attr('placeholder','Http name is missing');
					$(this).find($('input[name=httpName]')).addClass("errormessage");
				} 
			});
			
			return redirect;
		},

		dbContextUrlsMandatoryVal : function () {
			var redirect = true;
			$('.perDBContextUrlFieldset').each(function() {
				if ($(this).find($('input[name=dbName]')).val() === "" || isBlank($(this).find($('input[name=dbName]')).val())) {
					redirect = false;
					$('.yesNoPopupErr').text('Name is missing');
					$(this).find($('input[name=dbName]')).val('');
					$(this).find($('input[name=dbName]')).focus();
				} else if ($(this).find($('textarea[name=query]')).val() === "" || isBlank($(this).find($('textarea[name=query]')).val())) {
					redirect = false;
					$('.yesNoPopupErr').text('Query is missing');
					$(this).find($('textarea[name=query]')).val('');
					$(this).find($('textarea[name=query]')).focus();
				} 
			});
			
			return redirect;
		},
	
		contextUrls : function () {
		var contextUrls = [];
		var contexts = "";
		$('.contextDivClass').each(function() {
			var jsonObject = {};
			jsonObject.name = $(this).find($("input[name=httpName]")).val();
			jsonObject.context = $(this).find($("input[name=context]")).val();
			jsonObject.contextType = $(this).find($("select[name=contextType]")).val();
			jsonObject.encodingType = $(this).find($("select[name=encodingType]")).val();
			jsonObject.contextPostData = $(this).find($("textarea[name=contextPostData]")).val(); 

			jsonObject.redirectAutomatically = $(this).find($("input[name=redirectAutomatically]")).is(':checked'); 
			jsonObject.followRedirects = $(this).find($("input[name=followRedirects]")).is(':checked'); 
			jsonObject.keepAlive = $(this).find($("input[name=keepAlive]")).is(':checked'); 
			jsonObject.multipartData = $(this).find($("input[name=multipartData]")).is(':checked'); 
			jsonObject.compatibleHeaders = $(this).find($("input[name=compatibleHeaders]")).is(':checked');  			
			
			var headers = [];
			$(this).find($('.headers')).each(function() {
				var key = $(this).find($("input[name=headerKey]")).val();
				var value = $(this).find($("input[name=headerValue]")).val();
				var keyValueObj = {};
				keyValueObj.key=key;
				keyValueObj.value=value;
				headers.push(keyValueObj);
			});
			jsonObject.headers=headers;
			
			var parameters = [];
			$(this).find($('.parameterRow')).each(function() {
				var name = $(this).find($("input[name=parameterName]")).val();
				var value = $(this).find($("input[name=parameterValue]")).val();
				var encode = $(this).find($("input[name=parameterEncode]")).is(':checked');
				var nameValueObj = {};
				nameValueObj.name=name;
				nameValueObj.value=value;
				nameValueObj.encode=encode;
				parameters.push(nameValueObj);
			});
			jsonObject.parameters=parameters;

			contextUrls.push(JSON.stringify(jsonObject));
		});
		var jsonStrFromTemplate = '"contextUrls":[' + contextUrls + ']';
		return jsonStrFromTemplate;
	},

	dbContextUrls : function () {
		var dbContextUrls = [];
		$('.dbContextDivClass').each(function() {
			var jsonObject = {};
			jsonObject.name = $(this).find($("input[name=dbName]")).val();
			jsonObject.queryType = $(this).find($("select[name=queryType]")).val();
			jsonObject.query = $(this).find($("textarea[name=query]")).val(); 
			var dbContexts = JSON.stringify(jsonObject);
			dbContextUrls.push(dbContexts);
		});
		var jsonStrFromTemplate = '"dbContextUrls":[' + dbContextUrls + ']';
		return jsonStrFromTemplate;
	}

	});

	return Clazz.com.components.performanceLoadListener.js.listener.PerformanceLoadListener;
});