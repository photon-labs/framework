define([], function() {

	Clazz.createPackage("com.components.dashboard.js.listener");

	Clazz.com.components.dashboard.js.listener.DashboardListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		localStorageAPI : null,
		headerContent : null,
		footerContent : null,
		navigationContent : null,
		actionBody : null,
		dataforlinechart : null,
		dataforpiechart : null,
		dataforbarchart : null,
		chartdata : null,
		currentappname : null,
		currentdashboardid : null,
		flag_d : null,
		arrayy : null,
		properties : {},
		query : null,
		dashboardURL : '',
		dashboardname : null,
		dashboardpassword : null,
		dashboardusername : null,
		datafortable : null,
		piechartdata_new : {},
		totalArrforbar : {},
		xDataforbar : {},
		addwidgetflag : null,
		lineLegentName : {},
		totalArr : {},
		xVal : {},
		yVal : {},
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;	
		},

		graphAction : function(header, callback) {
			var self = this;
			try {
				commonVariables.api.ajaxRequestDashboard(header,
					function(response) {
						if (response !== null && response.status !== "error" && response.status !== "failure") {
							callback(response);
						} else {
							//commonVariables.api.showError(response.responseCode ,"error", false);
							callback(response);
						}
					},
					function(textStatus) {
						commonVariables.api.showError("serviceerror" ,"error", true);
					}
				);
			} catch(exception){
			}
		},
		
		getActionHeader : function(projectRequestBody, action) {
			var self=this, header, data = {};
			header = {
				contentType: "application/json",		
				webserviceurl: ''
			};				
			
			if(action === 'get') {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "project/edit?customerId="+self.getCustomer()+"&projectId="+commonVariables.projectId+"";
			}	
			else if(action === "dashboardlistall") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "dashboard?projectId="+commonVariables.projectId+"" ;
			} else if(action === "dashboardconfigure") {
				header.requestMethod = "POST";
				projectRequestBody.projectid = commonVariables.projectId;
				projectRequestBody.url = self.dashboardURL;
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard" ;
			}  else if(action === "dashboardget") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/"+projectRequestBody.dashboardid+"?projectId="+commonVariables.projectId+"" + "&appDirName="+projectRequestBody.appDirName+"";
			}  else if(action === "searchdashboard") {
				projectRequestBody.username = self.dashboardusername;
				projectRequestBody.password = self.dashboardpassword;
				projectRequestBody.url = self.dashboardURL;//"http://172.16.8.250:8089";
				projectRequestBody.datatype = $('#data_type option:selected').val();
				projectRequestBody.query = projectRequestBody.query;
				header.requestMethod = "POST";				
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/search";
			} else if(action === "addwidget" || action === "widgetupdate") {
				header.requestMethod = "POST";	
				if(action === "widgetupdate"){header.requestMethod = "PUT";}
				
				projectRequestBody.projectid = commonVariables.projectId;
				if(self.properties){
					projectRequestBody.properties.x = self.properties.x;
					projectRequestBody.properties.y = self.properties.y;
					projectRequestBody.properties.type = self.properties.type;
				}
				projectRequestBody.appdirname = self.currentappname;
				projectRequestBody.dashboardid = self.currentdashboardid;
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";
				
			}else if(action === 'widgetget') {
				header.requestMethod = "GET";	
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget/"+projectRequestBody.widgetid+"?projectId="+commonVariables.projectId+""+"&appDirName="+self.currentappname+""+"&dashboardid="+self.currentdashboardid+"";
			} else if(action === 'deletewidget') {
				header.requestMethod = "DELETE";	
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";
			}
			return header;
		},
		
		//get each widget info
		getWidgetDataInfo : function(widgetKey,currentWidget, actionBody, isCreated){
			var self = this;
			try{
				//create widget layout
				if(isCreated){self.createwidgetLayout(widgetKey,currentWidget);}
				
				self.graphAction(self.getActionHeader(actionBody, "searchdashboard"), function(response){
					if(response && response.data && response.data.results && currentWidget.properties){
						commonVariables.api.localVal.setJson(widgetKey, currentWidget);

						//chart creation
						if(currentWidget.properties.type.toString() === "linechart"){
							self.generateLineChart(widgetKey, currentWidget, response.data.results);
						}else if(currentWidget.properties.type.toString() === "piechart"){
							self.generatePieChart(widgetKey, currentWidget, response.data.results, self.actionBody);
						}else if(currentWidget.properties.type.toString() === "barchart"){
							self.generateBarChart(widgetKey, currentWidget, response.data.results, self.actionBody);
						}else if(currentWidget.properties.type.toString() === "table"){
							self.generateTable(widgetKey, response.data.results);
						}
					
						//Click event for widget
						self.clickFunction();
					}
				});
				
			}catch(exception){
				//exception
			}
		},
		
		generateTable : function(widgetKey, results){
			var self = this, theadArr = [],thead = [], tbody = '', tColums = '';
			try{
				//result set
				$.each(results, function(index,currentVal){
					tColums = '';
					//looping key values
					$.each(currentVal, function(key, val){
						if($.inArray(key, theadArr) < 0){
							theadArr.push(key);
							thead += '<th>' + key + '</th>';
						}
						
						tColums += '<td>'+ val +'</td>';
					});
					tbody += '<tr>' + tColums + '</tr>';
				});
				
				var nocTable = $('<div class="minitable"><table id="widTab_'+ widgetKey +'" class="table table_border border_div table-bordered tablesorter-default" style="height:50%; width:98%;" align="center"><thead><tr></tr></thead><tbody></tbody></table></div>');
				$('#placeholder_' + widgetKey).append(nocTable);
				$('#widTab_' + widgetKey +' thead tr').html(thead);
				$('#widTab_' + widgetKey +' tbody').html(tbody);
				$('.border_div').tablesorter();
			}catch(ec){
				//ex
			}
		},
		
		createwidgetLayout : function(widgetKey,currentWidget){
			var self = this;
			try{
				nocview = $('<div class="he-wrap noc_view" widgetid="' + widgetKey + '" widgetname="'+currentWidget.name+'" dynid="' + widgetKey + '" id="content_' + widgetKey + '"></div>');
				$('.features_content_main').prepend(nocview);

				nocview.append($('<div class="wid_name">'+currentWidget.name+'</div>'));
				var graph = $('<div class="graph_table"></div>');
				graph.append($('<div id="placeholder_' + widgetKey + '" class="placeholder" style="height:90%"> </div>'));
				nocview.append(graph);

				var graphType = currentWidget.properties === null ? '' : currentWidget.properties.type.toString();
				var heview = $('<div class="he-view"></div>');
				var heviewChild = $('<div class="a0" data-animate="fadeIn"><div class="center-bar"><a href="#" class="a0" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style settings_btn settings_img"></a><a href="#" class="a1" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style enlarge_btn" proptype="'+graphType+'"></a><a href="#" class="a2" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style close_widget" name="close_widget" ></a></div>');
				heview.append(heviewChild);
				nocview.append(heview);
			}catch(exception){
			 //exception
			}
		},
		
		dropdownclick : function() {
			var self = this;
			self.flag_d = 0;
			$("#click_listofdash").click(function() {
				$("#dashlist").show();
			});
			
			$(document).keyup(function(e) {
					if(e.which === 27){
						$("#dashlist").hide();
					}
			});
			
			$(".dashboardslist li a").click(function() {
					$("#click_listofdash").text($(this).text());
					$("#click_listofdash").append('<b class="caret"></b>');
					var p = 0, flag_naya = 0, tempdatanew = [], kl = 0, j=0, idgenerate, placeindex;					
					self.actionBody = {};
					self.actionBody.dashboardid = $(this).parent().attr('id');
					self.currentdashboardid = $(this).parent().attr('id');
					self.actionBody.appDirName = $(this).parent().attr('appdirname');
					self.currentappname = $(this).parent().attr('appdirname');
					self.dashboardURL = $(this).parent().attr('url_url');
					self.dashboardusername = $(this).parent().attr('username');
					self.dashboardpassword = $(this).parent().attr('password');
					self.dashboardname = $(this).val();
					self.graphAction(self.getActionHeader(self.actionBody, "dashboardget"), function(response) {
					$("#dashlist").hide();
					self.flag_d = 1;
					self.arrayy = response.data;
					$(".noc_view").each(function() {
						$(this).remove();
					});
					if(response.data !== null) {
						if(response.data.widgets !== null) {	
							//clearing exist service call
							self.clearService();
							$.each(response.data.widgets,function(widgetKey,currentWidget) {
								self.actionBody = {};
								self.actionBody.query = currentWidget.query;
								self.actionBody.applicationname = self.currentappname;
								self.actionBody.dashboardname = self.dashboardname;
								self.actionBody.widgetname = currentWidget.name;
								self.actionBody.earliest_time = currentWidget.starttime;
								self.actionBody.latest_time = currentWidget.endtime;
								self.actionBody.properties = {};
								self.getWidgetDataInfo(widgetKey, currentWidget, self.actionBody, true);
								
							});
						}
					}	
				});
			});
		},
		
		//clearing exist service call
		clearService : function(){
			try{
				$.each(commonVariables.clearInterval, function(key, val){ clearInterval(key);});
				commonVariables.clearInterval = {};
			}catch(exception){
			}
		},
		
		generateLineChart : function(widgetKey, currentWidget, results) {
			var self = this, graphdata = '<div class="demo-container1" id="placeholder_' + widgetKey + '"></div>',
			xVal, yVal;
			
			$("#content_" + widgetKey).children('.graph_table').empty();
			$("#content_" + widgetKey).find('.graph_table').append(graphdata);
			
			xVal = currentWidget.properties.x.toString();
			yVal = currentWidget.properties.y.toString();
			self.constructLineInfo(widgetKey, currentWidget, xVal, yVal, results);
		},
		
		constructLineInfo : function(widgetKey, currentWidget, xVal, yVal, results, callback){
			var self = this, xData, yData = '', totalArr = [];
			try{
				$.each(results, function(index, value) {
					if(xVal === '_time'){xData = Date.parse(value[xVal]);}else{xData = value[xVal];}	
					try{yData = parseInt(value[yVal],10) === NaN ? null : parseInt(value[yVal],10) ;}catch(ex){ yData =null;}
					totalArr.push([xData,yData])
				});
				
				self.lineLegentName[widgetKey]=currentWidget.name;
				self.totalArr[widgetKey]=totalArr;
				self.xVal[widgetKey]=xVal;
				self.yVal[widgetKey]=yVal;
				if(callback){callback(totalArr);}else{self.highChartLine(widgetKey);}
			}catch(exception){
				//ex
			}
		},
		
		highChartLine : function(widgetKey, enlarge){
			var self=this;
			$('#placeholder_' + widgetKey).highcharts({
				chart: {
					type: 'spline',
					plotBackgroundColor: null,
					plotBorderWidth: null,
					backgroundColor: null,
					plotShadow: false,
					height: $('#placeholder_'+widgetKey).parent().height() - 20,
					width: $('#placeholder_' + widgetKey).width(),
					events: {
						load: function(){
							if(!enlarge){
								// set up the updating of the chart each second
								var widInfo = commonVariables.api.localVal.getJson(widgetKey),
								appName = self.currentappname, dashName = self.dashboardname;
								series = this.series[0];
								
								if(widInfo.autorefresh){
									var regId = setInterval(function(){
										var widgetInfo = commonVariables.api.localVal.getJson(widgetKey), objactionBody = {};
										if(widgetInfo){
											objactionBody.query = widgetInfo.query;
											objactionBody.widgetname = widgetInfo.name;
											objactionBody.applicationname = appName;
											objactionBody.dashboardname = dashName;
											objactionBody.earliest_time = widgetInfo.starttime;
											objactionBody.latest_time = widgetInfo.endtime;

											self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
												//chart update
												self.constructLineInfo(widgetKey, widgetInfo, widgetInfo.properties.x.toString(), widgetInfo.properties.y.toString(), response.data.results, function(dataVal){
													series.setData(dataVal);
												});
											});
										}
									},widInfo.autorefresh);
									commonVariables.clearInterval[regId] = widgetKey;
								}
							}
						}
					}
				},
				title: {
					text: null
				},
				subtitle: {
					text: null
				},
				exporting: {
					enabled: false
				},
				xAxis: {
					type: 'datetime',
					gridLineWidth: 1,
					labels: {
						overflow: 'justify',
						formatter: function () {
						var date = new Date(this.value);
						return Highcharts.dateFormat('%H:%M:%S <br/>%e, %b', date);
						//return date;/*('0' + date.getDate()).slice(-2)+'-'+('0' + date.getMonth()).slice(-2)+' ' + ('0' + date.getHours()).slice(-2)+':'+('0'+date.getMinutes()).slice(-2)+':'+('0'+date.getSeconds()).slice(-2);-*/
						}
					},
					title: {
						text: self.xVal[widgetKey]
					}
				},
				yAxis: {
					title: {
						text: self.yVal[widgetKey]
					},
					min: 0
				},
				/*scrollbar: {
					enabled: false
				},*/
				tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+
						Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' m';
					}
				},
				
				series: [{
					name: self.lineLegentName[widgetKey],
					data: self.totalArr[widgetKey]
				}]
			});
		},
		
		generatePieChart : function(widgetKey, currentWidget, results, actionBody) {
			var self = this, graphdata = '<div class="demo-container1" id="placeholder_' + widgetKey + '"></div>',
			xVal, yVal;
			
			$("#content_" + widgetKey).children('.graph_table').empty();
			$("#content_" + widgetKey).find('.graph_table').append(graphdata);
			
			xVal = currentWidget.properties.x.toString();
			yVal = currentWidget.properties.y.toString();
			
			self.constructPieInfo(widgetKey, xVal, yVal, results);
		},	
		
		constructPieInfo : function(widgetKey, xVal, yVal, result, callback){
			var self = this, xData = [], yData = [], newXdata = [], new_data=[], sum = 0;;
			try{				
				$.each(result,function(index,value7) {
					$.each(value7,function(index8,value8) {							
						if(index8 === xVal) {
							if($.isNumeric(value8)) {
								xData.push(value8);
							} else {
								xData.push(null);
							}	
						}
						if(index8 === yVal) {
							yData.push(value8);
						}	
					});
				});

				for(var looper=0;looper<xData.length;looper++) {
					new_data[looper] =parseFloat(xData[looper]); 
					sum+=new_data[looper];
				}
				
				for(var dataadder=0;dataadder<new_data.length;dataadder++) {
					new_data[dataadder] = (new_data[dataadder]/sum) * 100;
					newXdata.push([yData[dataadder],new_data[dataadder]]);
					self.piechartdata_new[widgetKey] = newXdata;
				}
				
				if(callback){callback(newXdata);}else{self.highChartPie(widgetKey,-10,-5);}
			}catch(exception){
				//ex
			}
		},
		
		highChartPie : function(widgetKey,chartMarginTop,legendX, enlarge) {
			var self = this, width = $('#placeholder_' + widgetKey).width() / 4, height = $('#placeholder_' + widgetKey).parent().height()-40;
			$('#placeholder_' + widgetKey).highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					backgroundColor: null,
					plotShadow: false,
					height: $('#placeholder_'+widgetKey).parent().height()+30,
					width: $('#placeholder_' + widgetKey).parent().width(),
					marginTop: chartMarginTop, //-40,
					marginLeft: -150,
					events: {
						load: function(){
							if(!enlarge){
								// set up the updating of the chart each second
								var widInfo = commonVariables.api.localVal.getJson(widgetKey),
								appName = self.currentappname, dashName = self.dashboardname;
								series = this.series[0];
								
								if(widInfo.autorefresh){
									var regId = setInterval(function(){
										var widgetInfo = commonVariables.api.localVal.getJson(widgetKey), objactionBody = {};
										if(widgetInfo){
											objactionBody.query = widgetInfo.query;
											objactionBody.widgetname = widgetInfo.name;
											objactionBody.applicationname = appName;
											objactionBody.dashboardname = dashName;
											objactionBody.earliest_time = widgetInfo.starttime;
											objactionBody.latest_time = widgetInfo.endtime;

											self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
												//chart update
												self.constructPieInfo(widgetKey, widgetInfo.properties.x.toString(), widgetInfo.properties.y.toString(), response.data.results, function(dataVal){
													series.setData(dataVal);
												});
											});
										}
									},widInfo.autorefresh);
									commonVariables.clearInterval[regId] = widgetKey;
								}
							}
						}
					}
				},
				title: {
					text: null
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage}%</b>',
					percentageDecimals: 1
				},
				legend: {
					layout: 'vertical',
					align: 'center',
					verticalAlign: 'top',
					labelFormat: '{name} <br/>{y}%',
					floating: true,
					padding: 3,
					itemMarginTop: 3,
					itemMarginBottom: 3,
					itemStyle: {
						lineHeight: '14px'
					},
					maxHeight: height,
					x: width-legendX //width - 5,
					
				},
				exporting: {
					enabled: false
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						showInLegend: true,
						dataLabels: {
							showLabel: false,
							enabled: true,
						}
					}
				},
				series: [{
					type: 'pie',
					name: 'Percent',
					data: self.piechartdata_new[widgetKey]
				}]
			});
		},
		
		
		generateBarChart : function(widgetKey, currentWidget, results, actionBody) {
			var self = this, graphdata = '<div class="demo-container1" id="placeholder_' + widgetKey + '"></div>',
			xVal, yVal = [];
			
			$("#content_" + widgetKey).children('.graph_table').empty();
			$("#content_" + widgetKey).find('.graph_table').append(graphdata);
			
			xVal = currentWidget.properties.x;
			yVal = currentWidget.properties.y;
			
			self.constructBarInfo(widgetKey, xVal, yVal, results);
		},
		
		constructBarInfo : function(widgetKey, xVal, yVal, result, callback){
			var self = this, xData = [], yData = [], indexforx, collection = {}, totalArr = [], temparr = [];
			try{				
				indexforx = xVal[0];
				$.each(result,function(key,value) {
					$.each(value,function(itemkey,itemvalue) {
						if(itemkey === indexforx)
						xData.push(itemvalue);
					});
				});
				
				$.each(yVal, function(index,currentItem) {
					var temp = {};
					temp['name'] = currentItem;
					temparr = [];
					$.each(result, function(key,currentResult){
						$.each(currentResult,function(currkey,currvalue) {
							if(currkey === currentItem) {
								temparr.push(parseFloat(currvalue));
							}	
						});	
					});
					temp['data'] = temparr; 
					totalArr.push(temp);
				});
				self.totalArrforbar[widgetKey] = totalArr;
				self.xDataforbar[widgetKey] = xData;
				
				if(callback){callback(totalArr);}else{self.highChartBar(widgetKey);}
			}catch(exception){
			}
		},
		
		highChartBar : function(widgetKey, enlarge) {
			var self = this;
			$('#placeholder_' + widgetKey).highcharts({
				chart: {
					type: 'column',
					height: $('#placeholder_'+widgetKey).parent().height() - 20,
					width: $('#placeholder_' + widgetKey).width(),
					backgroundColor: null,
					events: {
						load: function(){
							if(!enlarge){
								// set up the updating of the chart each second
								var widInfo = commonVariables.api.localVal.getJson(widgetKey),
								appName = self.currentappname, dashName = self.dashboardname;
								series = this.series[0];
								
								if(widInfo.autorefresh){
									var regId = setInterval(function(){
										var widgetInfo = commonVariables.api.localVal.getJson(widgetKey), objactionBody = {};
										if(widgetInfo){
											objactionBody.query = widgetInfo.query;
											objactionBody.widgetname = widgetInfo.name;
											objactionBody.applicationname = appName;
											objactionBody.dashboardname = dashName;
											objactionBody.earliest_time = widgetInfo.starttime;
											objactionBody.latest_time = widgetInfo.endtime;

											self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
												//chart update
												self.constructPieInfo(widgetKey, widgetInfo.properties.x.toString(), widgetInfo.properties.y.toString(), response.data.results, function(dataVal){
													series.setData(dataVal);
												});
											});
										}
									},widInfo.autorefresh);
									commonVariables.clearInterval[regId] = widgetKey;
								}
							}
						}
					}
				},
				
				title: {
					text: null
				},
				subtitle: {
					text: null
				},
				xAxis: {
					min: 0,
					max: 3,
					categories: self.xDataforbar[widgetKey]
				},
				yAxis: {
					min: 0,
					title: {
						text: null
					}
				},
				scrollbar: {
					enabled: true
				},
				tooltip: {
					headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
					pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
						'<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
					footerFormat: '</table>',
					shared: true,
					useHTML: true
				},
				
				exporting: {
						enabled: false
					},
				
				plotOptions: {
					column: {
						pointPadding: 0.2,
						borderWidth: 0
					}
				},
				series: self.totalArrforbar[widgetKey]
			});
		},
		
		//Line Chart Query Execution
		lineChartQueryExe : function(response){
			var self = this, drOpt = '', drOptArr = [];
			try{
				self.dataforlinechart = response.data.results;
				$.each(response.data.results, function(index,currentVal){
					//looping key values
					$.each(currentVal, function(key, val){
						if($.inArray(key, drOptArr) < 0){
							drOptArr.push(key);
							drOpt += '<option value=' + key + '>' + key + '</option>';
						}
					});
				});
				
				$("select.xaxis").html(drOpt);
				$("select.yaxis").html(drOpt);
				
				self.optionsshowhide('lineChartOpt');
				$("#update_tab").removeAttr('disabled');
			}catch(exception){
				//Exception
			}
		},
		
		//Pie Chart Query Execution
		pieChartQueryExe : function(response){
			var self = this;
			try{
				var queryflag = 0, querycount = 0, querydata = [];
				self.dataforpiechart = response.data.results;
				$.each(response.data.results,function(index,value) {
					$.each(value,function(index1,value1) {							
						if(queryflag !==1) {
							querydata[querycount] = index1;								
							querycount++;
						}										
					});
					queryflag = 1;	
				});			
				for(var z=0;z<querydata.length;z++) {
					$("select.percentval").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');
					$("select.legendval").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');						
				}
				self.optionsshowhide('pieChartOpt');
				$("#update_tab").removeAttr('disabled');	
			}catch(exception){
				//Exception
			}
		},
		
		//Bar Chart Query Execution
		barChartQueryExe : function(response){
			var self = this;
			try{
				var queryflag = 0, querycount = 0, querydata = [];
				self.dataforbarchart = response.data.results;
				$("#update_tab").removeAttr('disabled');	
				$.each(response.data.results,function(index,value) {
					$.each(value,function(index1,value1) {							
						if(queryflag !==1) {
						$('ul[name="sortable1"]').append('<li value='+index1+'><div class="bar_radio"><input type="checkbox" name="optionsRadiosfd" value='+index1+'></div><div name="envListName" class="bar_name">'+index1+'</div><div class="colorbar_div"><input class="pick_color colorpick" placeholder="Pick Color" type="text"></li>');	
						
						querydata[querycount] = index1;								
						querycount++;
						}										
					});
					
					queryflag = 1;	
				});
				$('.colorpick').colorpicker();
				for(var z=0;z<querydata.length;z++) {
					$("select.baraxis").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');
				}
				self.optionsshowhide('barChartOpt');							
				$('.connected').sortable({
					connectWith: '.connected'
				});
			}catch(exception){
				//Exception
			}
		},
		
		optionsshowhide : function(tr_toshow) {
			var self = this;
			$("#lineChartOpt").hide();
			$("#pieChartOpt").hide();
			$("#barChartOpt").hide();
			if(tr_toshow !== '' && tr_toshow !== 'table') {
				if($("#"+tr_toshow).find('select').children().length) {
					$("#"+tr_toshow).show();
				}	
				$("input[name='execute_query']").removeAttr('disabled');
				$("#update_tab").attr('disabled','disabled');
			} else {
				if(!($("#update_tab").val() === 'Update')) {
					$("input[name='execute_query']").attr('disabled','disabled');
					$("#update_tab").removeAttr('disabled');
				}	
			}	
		 },
		 
		createwidgetfunction : function() {
			var self = this,
			widgetTag = $("#nameofwidget"), query_add = $("#query_add"),
			autoref = ($('#timeout').is(':checked') && $('#timeoutval').val().trim() !== "" ? $('#timeoutval').val().trim() : null);
			
			if(widgetTag.val() === '') {
				widgetTag.addClass('errormessage');
				widgetTag.focus();
				widgetTag.attr('placeholder','Enter Widget Name');
				widgetTag.bind('keypress', function() {
					$(this).removeClass("errormessage");
					$(this).removeAttr("placeholder");
				});
			}else if(query_add.val() === '') {
				query_add.addClass('errormessage');
				query_add.focus();
				query_add.attr('placeholder','Enter Query');
				query_add.bind('keypress', function() {
					$(this).removeClass("errormessage");
					$(this).removeAttr("placeholder");
				});
			}else{
				self.actionBody = {};
				self.actionBody.name = widgetTag.val();
				self.actionBody.query = query_add.val();					
				self.actionBody.autorefresh = autoref;
				self.actionBody.starttime = $("#fromTime").val();
				self.actionBody.endtime = $("#toTime").val();
				self.actionBody.properties = {};

				if($("#widgetType option:selected").val() === 'linechart'){					
					self.properties.type = ['linechart'];
					self.properties.x = $.makeArray($("select.xaxis option:selected").val());
					self.properties.y = $.makeArray( $("select.yaxis option:selected").val());
				}else if($("#widgetType option:selected").val() === 'piechart'){
					self.properties.x = $.makeArray( $("select.percentval option:selected").val());
					self.properties.y = $.makeArray( $("select.legendval option:selected").val());
					self.properties.type = ['piechart'];
				}else if($("#widgetType option:selected").val() === 'barchart'){
					var SelectedItems = [];
					$('input[name="optionsRadiosfd"]:checked').each(function(i){
						  SelectedItems[i] = $(this).val();
					});
					self.properties.x = $.makeArray($("select.baraxis option:selected").val());
					self.properties.y = SelectedItems;
					self.properties.type = ['barchart'];
				}else if($("#widgetType option:selected").val() === 'table'){
					self.properties.type = ['table'];
				}
		
				self.graphAction(self.getActionHeader(self.actionBody, "addwidget"), function(response) {
					if(response){
						self.currentwidgetid = response.data;
						self.getWidgetInfo(self.currentwidgetid, function(result){
							if(result){
								self.actionBody = {};
								self.actionBody.query = query_add.val();
								self.actionBody.applicationname = self.currentappname;
								self.actionBody.dashboardname = self.dashboardname;
								self.actionBody.widgetname = widgetTag.val();
								self.actionBody.earliest_time = $("#fromTime").val();
								self.actionBody.latest_time = $("#toTime").val();
								
								self.getWidgetDataInfo(self.currentwidgetid, result.data, self.actionBody, true);
								$('#add_widget').hide();
								$('.noc_view').css('width','48%');
							}
						});
					}
				});
			}
		},
		
		getWidgetInfo : function(widgetId, callBack){
			var self = this;
			try{
				self.actionBody = {};
				self.actionBody.widgetid = widgetId;
				self.graphAction(self.getActionHeader(self.actionBody, "widgetget"), function(response) {
					callBack(response);
				});
			}catch(ex){
				callBack(null);
			}
		},
		
		clickFunction : function() {
			var hoverFlag=0;
			var self = this, flagg = 0, placeval;
			
			$("#widgetType").unbind('change');
			$("#widgetType").change(function() {
				var typeofchart = $(this).children('option:selected').val();
				if(typeofchart === 'table') {
					self.optionsshowhide('');
				} else if(typeofchart === 'linechart') {
					self.optionsshowhide('lineChartOpt');
				} else if(typeofchart === 'piechart') {
					self.optionsshowhide('pieChartOpt');
				} else if(typeofchart === 'barchart') {
					self.optionsshowhide('barChartOpt');
				}
			});
			
			$('.tabchange li').unbind('click');
			$('.tabchange li').click(function() {
				placeval = $(this).parents('div.noc_view').attr('dynid');
				var temp = $(this).children('a').attr('id');
				if(temp === 'tableview_'+placeval+'') {
					$("#table_"+placeval).show();
					$("#content_"+placeval).hide();
				} else if(temp === 'graphview_'+placeval+'') {
					$("#table_"+placeval).hide();
					$("#content_"+placeval).show();
				}
			});
			
			$('input[name="close_widget"]').unbind('click');
			$('input[name="close_widget"]').click(function() {
				var widgetKey = $(this).parents('div.noc_view').attr('widgetid');
				var obj =  $(this).parents('div.noc_view');
				self.actionBody = {};
				self.actionBody.dashboardid = self.currentdashboardid;
				self.actionBody.widgetid = 	widgetKey;
				self.actionBody.appdirname = self.currentappname;
				self.graphAction(self.getActionHeader(self.actionBody, "deletewidget"), function(response) {
					$(obj).remove();
					var count = $('.noc_view').length;
					if(count == 1){
						$('.noc_view').css('width','98%');
					} else if (count >=2) {
						$('.noc_view').css('width','48%');
					}	
				});
			});
			
			$('.enlarge_btn').unbind('click');
			$('.enlarge_btn').click(function(){
				placeval = $(this).parents('div.noc_view').attr('dynid');
				var parent=$(this).closest('.noc_view');
				if(!flagg) {
					$('.noc_view').hide();	 				 
					parent.show();
					parent.css({height:'97%',width:'98%'});
					$("#tooltip_"+placeval).css('display','none');
					if($("#content_"+placeval).children(".demo-container1").hasClass('cssforchart')) {
						$("#content_"+placeval).children(".demo-container1").removeClass('cssforchart');
					}
					flagg = 1;
					
					if($(this).attr('proptype') === 'piechart') {
						self.highChartPie(placeval, -10, 0, true);	
					}  else if($(this).attr('proptype') === 'barchart') {
						self.highChartBar(placeval, true);
					} else if($(this).attr('proptype') === 'linechart') {
						self.highChartLine(placeval, true);
					} else if($(this).attr('proptype') === 'table') {
						$('.placeholder').css('height','100%');
					}
				} else {
					$("#content_"+placeval).children(".demo-container1").addClass('cssforchart');						
					$('.noc_view').show();
					var count = $('.noc_view').length;
					$('.noc_view').css('height','45%');
					$('.noc_view').css('width','48%');
					flagg = 0;
					if($(this).attr('proptype') === 'piechart') {
						self.highChartPie(placeval, -10, 5, true);	
					} else if($(this).attr('proptype') === 'barchart') {
						self.highChartBar(placeval, true);
					} else if($(this).attr('proptype') === 'linechart') {
						self.highChartLine(placeval, true);
					} else if($(this).attr('proptype') === 'table') {
						$('.placeholder').css('height','90%');
					}
				}														
			});
			
			$('.he-wrap').mouseenter(function(event){
				var heView = $(this).find('.he-view');
				heView.addClass('heasasas-view-show');
			});
			
			$('.settings_img').unbind('click');
			$('.settings_img').click(function() {
				var dyn_id = '', currentObj = this;
				
				self.addwidgetflag = 0;
				$("#update_tab").val('Update');
				$(currentObj).closest('.he-view').css('visibility', 'visible');
				
				self.getWidgetInfo($(currentObj).parents('div.noc_view').attr('dynid'), function(result){
					$("#query_add").val(result.data.query);
					$("#nameofwidget").attr('disabled','disabled');
					$("#nameofwidget").val(result.data.name);
					if(result.data.properties) {
						$("#widgetType").val(result.data.properties.type.toString());
						$(".filter-option").text(result.data.properties.type.toString());
					}	
					self.optionsshowhide($("#widgetType").val());
					$("input[name='execute_query']").removeAttr('disabled');
					if(result.data.autorefresh) {
						$("#timeoutval").show();
						$("#timeoutval").val(result.data.autorefresh);
						$('#timeout').prop('checked', true);
					} else {
						$("#timeoutval").hide();
						$('#timeout').prop('checked', false);
					}
					
					var dyn_id = $(currentObj).parents('div.noc_view').attr('dynid');
					$("#add_widget").attr('dynid',dyn_id);
					
					self.openccdashboardsettings(currentObj,'add_widget');
				});
			});
			
			$("input[name='execute_query']").unbind('click');
			$("input[name='execute_query']").click(function() {
				self.actionBody = {};
				var queryflag = 0, querydata=[], querycount = 0, textareaval, nameofwid = '';
				$("select.xaxis").empty();				
				$("select.yaxis").empty();
				$("select.percentval").empty();
				$("select.legendval").empty();
				$("#tabforbar").find('ul').empty();
				textareaval = $("#query_add");
				nameofwid = $("#nameofwidget");
				self.actionBody.query = textareaval.val();
				
				if(nameofwid.val() === '') {
					nameofwid.addClass('errormessage');
					nameofwid.focus();
					nameofwid.attr('placeholder','Enter Widget Name');
					nameofwid.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				}
				else if(textareaval.val() === '') {
					textareaval.addClass('errormessage');
					textareaval.focus();
					textareaval.attr('placeholder','Enter Query');
					textareaval.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else {
					self.actionBody.applicationname = self.currentappname;
					self.actionBody.dashboardname = self.dashboardname;
					self.actionBody.widgetname = nameofwid.val();
					self.actionBody.earliest_time = $("#fromTime").val();
					self.actionBody.latest_time = $("#toTime").val();
					
					self.graphAction(self.getActionHeader(self.actionBody, "searchdashboard"), function(response) {
						self.chartdata = response.data.results;
						self.datafortable = response;
						if($('#widgetType option:selected').val() === 'linechart') {
							self.lineChartQueryExe(response);
						} else if($('#widgetType option:selected').val() === 'piechart') {
							self.pieChartQueryExe(response);							
						} else if($('#widgetType option:selected').val() === 'barchart') {
							self.barChartQueryExe(response);
						}	
					});		
				}	
				$("#update_tab").removeAttr('disabled');
			});
			
			$('.closeset').unbind('click');
			$('.closeset').click(function() {
				$("#add_widget").hide();
			});
			
			$('#update_tab').unbind('click');
			$('#update_tab').click(function() {
			if(self.addwidgetflag !==1) {
				var widgetKey = $(this).parent().parent().attr('dynid'), indexforx, indexfory, SelectedItems = [],
				widgetdatatype = $('#widgetType option:selected').val(), widgetId = $("#update_tab").parents('#add_widget').attr('dynid');

				self.actionBody = {};
				self.actionBody.query = $("#query_add").val();
				self.actionBody.autorefresh = ($('#timeout').is(':checked') && $('#timeoutval').val().trim() !== "" ? $('#timeoutval').val().trim() : null);
				self.actionBody.starttime = $("#fromTime").val();
				self.actionBody.endtime = $("#toTime").val();
				self.actionBody.widgetid = $("#content_"+widgetId).attr('widgetid');
				self.actionBody.name = $("#content_"+widgetId).attr('widgetname');
				self.actionBody.appdirname = self.currentappname;
				self.actionBody.dashboardid = self.currentdashboardid;
				self.actionBody.properties = {};
				
				if(widgetdatatype === 'linechart'){
					indexforx = $("select.xaxis option:selected").val();
					indexfory = $("select.yaxis option:selected").val();
					self.properties.type = ['linechart'];
					self.properties.x = $.makeArray(indexforx);
					self.properties.y = $.makeArray(indexfory);
				}else if(widgetdatatype === 'piechart'){
					indexforx = $("select.percentval option:selected").val();
					indexfory = $("select.legendval option:selected").val();
					self.properties.x = $.makeArray(indexforx);
					self.properties.y = $.makeArray(indexfory);
					self.properties.type = ['piechart'];
				}else if(widgetdatatype === 'barchart'){
					$('input[name="optionsRadiosfd"]:checked').each(function(i){
						SelectedItems[i] = $(this).val();
					});
					self.properties.x = $.makeArray($("select.baraxis option:selected").val());
					self.properties.y = SelectedItems;
					self.properties.type = ['barchart'];
				} else if(widgetdatatype === 'table'){
					self.properties.type = ['table'];
				}
				
				self.graphAction(self.getActionHeader(self.actionBody, "widgetupdate"), function(response) {
					if(response && response.status === "success"){
						//get widget Info
						self.getWidgetInfo(widgetId, function(currentWidget){
							//removing service call
							$.each(commonVariables.clearInterval, function(key, val){
								if(widgetKey === val){
									clearInterval(key);
									delete commonVariables.clearInterval[key];
									return true;
								}
							});

							$('#placeholder_' + widgetKey).empty();
							self.getWidgetInfo(widgetKey, function(result){
								if(result){
									self.actionBody = {};
									self.actionBody.query = $("#query_add").val();
									self.actionBody.earliest_time = $("#fromTime").val();
									self.actionBody.latest_time = $("#toTime").val();
									self.actionBody.widgetname =  $("#content_"+widgetId).attr('widgetname');
									self.actionBody.applicationname = self.currentappname;
									self.actionBody.dashboardname = self.dashboardname;
									self.getWidgetDataInfo(widgetKey, result.data, self.actionBody, false);
								}
							});
							$("#add_widget").hide();	
						});
					}
					$("#content_"+widgetKey).find('.enlarge_btn').attr('proptype',widgetdatatype);
					$('.he-view').removeAttr('style');
					window.hoverFlag = 0;
				});
			} else {
				self.createwidgetfunction();
			}	
			});
		},
	});

	return Clazz.com.components.dashboard.js.listener.DashboardListener;
});