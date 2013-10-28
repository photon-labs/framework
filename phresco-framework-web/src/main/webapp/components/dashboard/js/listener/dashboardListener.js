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
		piechartdata_new : null,
		totalArrforbar : null,
		xDataforbar : null,
		addwidgetflag : null,
		lineLegentName : null,
		totalArr : null,
		xVal : null,
		yVal : null,
		
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;	
		},
		
		lineChart : function(placeholderindex,dataforx,datafory) {
			if($("#placeholder_"+placeholderindex).length > 0){
				var dataCollection = [], plot = '', placeholder = '', lineOptions = '';
				
				for (var i = 0; i < dataforx.length; i++) {
					dataCollection.push([dataforx[i], datafory[i]]);
				}

				placeholder = $("#placeholder_"+placeholderindex);

				lineOptions = {
					series: {
						lines: {
							show: true
						},
						points: {
							show: true
						}
					},
					grid: {
						hoverable: true,
						clickable: true
					} ,
					yaxis: {
						min: 0
					},
					xaxis: {
					  mode: "time",
					  timeformat: "%y/%m/%d %H:%M"
					}
				};
				
				plot = $.plot(placeholder, [dataCollection], lineOptions);

				function showTooltip(x, y, contents) {
					$('<div id="tooltip_'+placeholderindex+'">' + contents + '</div>').css({
						position: 'absolute',
						top: y + 5,
						left: x + 5,
						border: '1px solid #fdd',
						padding: '2px',
						'background-color': '#fee',
						opacity: 0.80
					}).appendTo("body");//.fadeIn(200);
				}
				
				$("#placeholder_"+placeholderindex).bind("plothover", function (event, pos, item) {
					if (item) {
						if ($(this).data('previous-post') != item.seriesIndex) {						
							$(this).data('previous-post', item.seriesIndex);
						}
						$("#tooltip_"+placeholderindex).remove();
						var x = item.datapoint[0].toFixed(2), y = item.datapoint[1].toFixed(2);
						z = " of " + x + " = " + y;
						showTooltip(pos.pageX, pos.pageY,'Value' + z);
						$("#tooltip_"+placeholderindex).css('z-index','4');
					} else {
						$("#tooltip_"+placeholderindex).css('display','none');
					}	
				});
			}
		},
		
		barChart : function(placeholderindex) {
			var d1 = [];
			for (var i = 0; i <= 10; i += 1) {
				d1.push([i, parseInt(Math.random() * 30)]);
			}

			var d2 = [];
			for (var i = 0; i <= 10; i += 1) {
				d2.push([i, parseInt(Math.random() * 30)]);
			}

			var d3 = [];
			for (var i = 0; i <= 10; i += 1) {
				d3.push([i, parseInt(Math.random() * 30)]);
			}
			var placeholder = $("#placeholder_"+placeholderindex);

			var stack = 0,
				bars = true,
				lines = false,
				steps = false;

			function plotWithOptions() {
				$.plot("#placeholder_"+placeholderindex+"", [ d1,d2,d3], {
					series: {
						stack: stack,
						lines: {
							show: lines,
							steps: steps
						},
						bars: {
							show: bars,
							fill: 1,
							barWidth: 0.6
						}
					},
					grid: {
							hoverable: true
						}
				});
			}

			plotWithOptions();

			$(".stackControls button").click(function (e) {
				e.preventDefault();
				stack = $(this).text() == "With stacking" ? true : null;
				plotWithOptions();
			});
		
			/* placeholder.resize(function () {
				$(".message").text("Placeholder is now "
					+ $(this).width() + "x" + $(this).height()
					+ " pixels");
			});

			$(".demo-container").resizable({
				maxWidth: 900,
				maxHeight: 500,
				minWidth: 450,
				minHeight: 250,
			}); */

			function showTooltip(x, y, contents) {
				$('<div id="tooltip_'+placeholderindex+'">' + contents + '</div>').css( {
					position: 'absolute',
					top: y + 5,
					left: x + 5,
					border: '1px solid #fdd',
					padding: '2px',
					'background-color': '#fee',
					opacity: 0.80
				}).appendTo("body");//.fadeIn(200);
			 }
			 
			 $("#placeholder_"+placeholderindex).bind("plothover", function (event, pos, item) {
				if (item) {
					if ($(this).data('previous-post') != item.seriesIndex) {						
						$(this).data('previous-post', item.seriesIndex);
					}
					$("#tooltip_"+placeholderindex).remove();
					var x = item.datapoint[0].toFixed(2), y = item.datapoint[1].toFixed(2);
					z = " of " + x + " = " + y;
					showTooltip(pos.pageX, pos.pageY, item.series.label + z);
					$("#tooltip_"+placeholderindex).css('z-index','4');
				} else {
					$("#tooltip_"+placeholderindex).css('display','none');
				}	
			});		

		},
		
		pieChart : function(placeholderindex,datatosend,seriesvalue) {
			var data = [],
			//series = Math.floor(Math.random() * 6) + 3;
			  series = datatosend.length;
			for (var i = 0; i < series; i++) {
				data[i] = {
					label: seriesvalue[i] + '(' + datatosend[i] + ')',
					//data: Math.floor(Math.random() * 100) + 1
					data: datatosend[i]
				}
			}

			var placeholder = $("#placeholder_"+placeholderindex);
			$.plot(placeholder, data, {
				series: {
					pie: { 
						show: true
					}
					},
					grid: {
						hoverable: true
					}
			});
			
			/* placeholder.resize(function () {
				$(".message").text("Placeholder is now "
					+ $(this).width() + "x" + $(this).height()
					+ " pixels");
			});

			$(".demo-container").resizable({
				maxWidth: 900,
				maxHeight: 500,
				minWidth: 450,
				minHeight: 250,
			}); */
	
			function showTooltip(x, y, contents) {
				$('<div id="tooltip_'+placeholderindex+'">' + contents + '</div>').css( {
					position: 'absolute',
					top: y + 5,
					left: x + 5,
					border: '1px solid #fdd',
					padding: '2px',
					'background-color': '#fee',
					opacity: 0.80
				}).appendTo("body");//.fadeIn(200);
			 }

			$("#placeholder_"+placeholderindex).bind("plothover", function (event, pos, item) {
				if (item) {
					if ($(this).data('previous-post') != item.seriesIndex) {
						$(this).data('previous-post', item.seriesIndex);
					}
					$("#tooltip_"+placeholderindex).remove();
					y = 'Percent ' + ': ' + item.datapoint;
					showTooltip(pos.pageX, pos.pageY, item.series.label + " " + y);
					$("#tooltip_"+placeholderindex).css('z-index','4');
				} else {
					$("#tooltip_"+placeholderindex).css('display','none');
				}	
			});		
			$("#tooltip_"+placeholderindex).css('display','none');
		},
		
		newbarchart : function(placeholderindex,totalArr, xlabelVal) {
			/* var d1 = [{"label":"count","data":[[1,25],[2,5],[3,3],[4,2]]},
                 {"label":"percentage","data":[[1,71.428571],[2,14.285714],[3,8.571429],[4,5.714286]]}];
                 //{"label":"CAR","data":[[1,5],[2,10],[3,15],[4,20],[5,25],[6,5],[7,10],[8,15],[9,20],[10,25]]}];
		 var ms_ticks = [[1,"127.0.0.1"],[2,"172.16.22.107"],[3,"172.16.23.25"],[4,"172.16.17.228"]]; */
			
			var option={
				bars: { 
					show: true, 
					barWidth: 0.10, 
					series_spread: true, 
					align: "center", 
					order: 1 
				},
				xaxis: { 
					ticks: xlabelVal, 
					autoscaleMargin: .01 
				},
				grid: { 
					hoverable: true, 
					clickable: true 
				}
			};

		function plotWithOptions() {
			$.plot("#placeholder_"+placeholderindex, totalArr, option);
		}
		plotWithOptions();
		
		function showTooltip(x, y, contents) {
				$('<div id="tooltip_'+placeholderindex+'">' + contents + '</div>').css( {
					position: 'absolute',
					top: y + 5,
					left: x + 5,
					border: '1px solid #fdd',
					padding: '2px',
					'background-color': '#fee',
					opacity: 0.80
				}).appendTo("body");//.fadeIn(200);
			 }

			$("#placeholder_"+placeholderindex).bind("plothover", function (event, pos, item) {
				if (item) {
					if ($(this).data('previous-post') != item.seriesIndex) {
						$(this).data('previous-post', item.seriesIndex);
					}
					$("#tooltip_"+placeholderindex).remove();
					y = 'Value ' + ': ' + item.datapoint[1];
					showTooltip(pos.pageX, pos.pageY, item.series.label + " " + y);
					$("#tooltip_"+placeholderindex).css('z-index','4');
				} else {
					$("#tooltip_"+placeholderindex).css('display','none');
				}	
			});		
			$("#tooltip_"+placeholderindex).css('display','none');

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
			} catch(exception) {
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
				projectRequestBody.url = self.dashboardURL;
				//projectRequestBody.host = '172.16.8.250'; //"http://172.16.8.250:8089";
				//projectRequestBody.port = '8089'; //"http://172.16.8.250:8089";
				projectRequestBody.datatype = "Splunk";
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
				
			}  /* else if(action === "widgetupdate") {
				header.requestMethod = "PUT";	
				projectRequestBody.projectid = commonVariables.projectId;
				if(self.properties){
					projectRequestBody.properties.x = self.properties.x;
					projectRequestBody.properties.y = self.properties.y;
					projectRequestBody.properties.type = self.properties.type;
				}else{
					projectRequestBody.properties.x = null;
					projectRequestBody.properties.y = null;
					projectRequestBody.properties.type = null;
				}
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";

			} */ else if(action === 'widgetget') {
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
			var self = this, regId = '', theadArr = [],thead = [], tbody = '', tColums = '';
			try{
				//create widget layout
				self.createwidgetLayout(widgetKey,currentWidget);
				
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
				
				var nocTable = $('<table id="widTab_'+ widgetKey +'" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"><thead><tr></tr></thead><tbody></tbody></table>');
				$('#placeholder_' + widgetKey).append(nocTable);
				$('#widTab_' + widgetKey +' thead tr').html(thead);
				$('#widTab_' + widgetKey +' tbody').html(tbody);
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
				graph.append($('<div id="placeholder_' + widgetKey + '" class="placeholder"> </div>'));
				nocview.append(graph);

				var graphType = currentWidget.properties === null ? '' : currentWidget.properties.type.toString();
				var heview = $('<div class="he-view"></div>');
				var heviewChild = $('<div class="a0" data-animate="fadeIn"><div class="center-bar"><a href="#" class="a0" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style settings_btn settings_img"></a><a href="#" class="a1" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style enlarge_btn" proptype="'+graphType+'"></a><a href="#" class="a2" data-animate="rotateInLeft"><input type="submit" value="" class="btn btn_style close_widget" name="close_widget" ></a></div>');
				heview.append(heviewChild);
				nocview.append(heview);

				//CSS changes for widget
				//$('.noc_view').css('width','48%');
				//$('.features_content_main').show();
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
								
								self.getWidgetDataInfo(widgetKey,currentWidget, self.actionBody, true);
								
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
			
			if(currentWidget.autorefresh){
				var widgetInfo = commonVariables.api.localVal.getJson(widgetKey), objactionBody = {}, appName = self.currentappname, dashName = self.dashboardname;
				self.constructLineInfo(widgetKey, currentWidget, xVal, yVal, results);
				
				var regId = setInterval(function(){
					objactionBody = {};
					objactionBody.query = widgetInfo.data.query;
					objactionBody.widgetname = widgetInfo.data.name;
					objactionBody.applicationname = appName;
					objactionBody.dashboardname = dashName;
					
					graphdata = '<div class="demo-container1 cssforchart"><div id="placeholder_' + widgetKey + '" class=""></div></div>'
					$("#content_" + widgetKey).empty();
					$("#content_" + widgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetLayout(widgetKey,null, response, false);
						//chart update
						self.constructLineInfo(widgetKey, currentWidget, widgetInfo.data.properties.x, widgetInfo.data.properties.y, response.data.results);
					});
					
				},currentWidget.autorefresh);
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructLineInfo(widgetKey, currentWidget, xVal, yVal, results);}
		},
		
		constructLineInfo : function(widgetKey, currentWidget, xVal, yVal, results){
			var self = this, xData, yData = '', totalArr = [];
			try{
				$.each(results, function(index, value) {
					if(xVal === '_time'){ console.info('xdart' ,value[xVal]);xData = Date.parse(value[xVal]);}else{xData = value[xVal];}	
					try{yData = parseInt(value[yVal],10) === NaN ? null : parseInt(value[yVal],10) ;}catch(ex){ yData =null;}
					totalArr.push([xData,yData])
				});
				
				self.lineLegentName=currentWidget.name;
				self.totalArr=totalArr;
				self.xVal=xVal;
				self.yVal=yVal;
				self.highChartLine(widgetKey);
			}catch(exception){
			
			}
		},
		
		highChartLine : function(widgetKey){
			var self=this;
				
			self.objlineChart = $('#placeholder_' + widgetKey).highcharts({
				chart: {
					type: 'spline',
					plotBackgroundColor: null,
					plotBorderWidth: null,
					backgroundColor: null,
					plotShadow: false,
					height: $('#placeholder_'+widgetKey).parent().height() - 20,
					width: $('#placeholder_' + widgetKey).width()
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
						formatter: function () {
							var date = new Date(this.value);
							console.info('date',date);
							return Highcharts.dateFormat('%e. %b', date);
							//return date;/*('0' + date.getDate()).slice(-2)+'-'+('0' + date.getMonth()).slice(-2)+' ' + ('0' + date.getHours()).slice(-2)+':'+('0'+date.getMinutes()).slice(-2)+':'+('0'+date.getSeconds()).slice(-2);*/
						} 
					},
					title: {
						text: self.xVal
					}
				},
				yAxis: {
					title: {
						text: self.yVal
					},
					min: 0
				},
				tooltip: {
					formatter: function() {
							return '<b>'+ this.series.name +'</b><br/>'+
							Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' m';
					}
				},
				
				series: [{
					name: self.lineLegentName,
					data: self.totalArr
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
			
			if(currentWidget.autorefresh){
				var objxVal = xVal, objyVal = yVal, autorefresh = currentWidget.autorefresh, objwidgetKey = widgetKey, objactionBody = actionBody;
				self.constructPieInfo(widgetKey, xVal, yVal, results);
				var regId = setInterval(function(){
					if(commonVariables.clearInterval[regId]){
						objxVal = commonVariables.clearInterval[regId].objxVal;
						objyVal = commonVariables.clearInterval[regId].objyVal;
						autorefresh = commonVariables.clearInterval[regId].autorefresh;
						objwidgetKey = commonVariables.clearInterval[regId].widgetKey;
						objactionBody = commonVariables.clearInterval[regId].actionBody;
					}
					
					graphdata = '<div class="demo-container1"><div id="placeholder_' + objwidgetKey + '" class=""></div></div>'
					//$("#content_" + objwidgetKey).empty();
					$("#content_" + objwidgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetLayout(objwidgetKey,null, response, false);
						//chart update
						self.constructPieInfo(objwidgetKey, objxVal, objyVal, response.data.results);
					});
					
				},autorefresh);
				commonVariables.clearInterval[regId] = { objxVal : xVal, objyVal : yVal, autorefresh : currentWidget.autorefresh, widgetKey : widgetKey, actionBody : actionBody};
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructPieInfo(widgetKey, xVal, yVal, results);}
		},	
		
		constructPieInfo : function(widgetKey, xVal, yVal, result){
			var self = this, xData = [], yData = [], newXdata = [];
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
			var new_data=[], sum = 0;
			for(var looper=0;looper<xData.length;looper++) {
				new_data[looper] =parseFloat(xData[looper]); 
				sum+=new_data[looper];
			}
			
			for(var dataadder=0;dataadder<new_data.length;dataadder++) {
				new_data[dataadder] = (new_data[dataadder]/sum) * 100;
				newXdata.push([yData[dataadder],new_data[dataadder]]);
				self.piechartdata_new = newXdata;
			}
			self.highChartPie(widgetKey, newXdata,-10,-5);	//self.highchartpie(widgetKey, newXdata,-40,-5);	
			var dat = commonVariables.api.localVal.getSession(widgetKey);
			}catch(exception){
			
			}
		},
		
		highChartPie : function(widgetKey,newXdata,chartMarginTop,legendX) {
			//$('#content_' + widgetKey).empty();
			var width = $('#placeholder_' + widgetKey).width() / 4;
			$('#placeholder_' + widgetKey).highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					backgroundColor: null,
					plotShadow: false,
					height: $('#placeholder_'+widgetKey).parent().height()+30,
					width: $('#placeholder_' + widgetKey).parent().width(),
					marginTop: chartMarginTop, //-40,
					marginLeft: -150
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
					data: newXdata
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
			
			if(currentWidget.autorefresh){
				var objxVal = xVal, objyVal = yVal, autorefresh = currentWidget.autorefresh, objwidgetKey = widgetKey, objactionBody = actionBody;
				self.constructBarInfo(widgetKey, xVal, yVal, results);
				var regId = setInterval(function(){
					if(commonVariables.clearInterval[regId]){
						objxVal = commonVariables.clearInterval[regId].objxVal;
						objyVal = commonVariables.clearInterval[regId].objyVal;
						autorefresh = commonVariables.clearInterval[regId].autorefresh;
						objwidgetKey = commonVariables.clearInterval[regId].widgetKey;
						objactionBody = commonVariables.clearInterval[regId].actionBody;
					}
					
					graphdata = '<div class="demo-container1"><div id="placeholder_' + objwidgetKey + '" class=""></div></div>'
					$("#content_" + objwidgetKey).empty();
					$("#content_" + objwidgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetLayout(objwidgetKey,null, response, false);
						//chart update
						self.constructBarInfo(objwidgetKey, objxVal, objyVal, response.data.results);
					});
					
				},autorefresh);
				commonVariables.clearInterval[regId] = { objxVal : xVal, objyVal : yVal, autorefresh : currentWidget.autorefresh, widgetKey : widgetKey, actionBody : actionBody};
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructBarInfo(widgetKey, xVal, yVal, results);}
		},
		
		constructBarInfo : function(widgetKey, xVal, yVal, result){
			var self = this, xData = [], yData = [], indexforx, collection = {}, totalArr = [], temparr = [];
			try{				
				/* indexforx = xVal[0];
				$.each(yVal, function(index, currentItem){
					collection['"' + currentItem + '"'] = [];
					$.each(result, function(index,currentResult){
						collection['"' + currentItem + '"'].push([index, currentResult[currentItem]]);
					});
				});

				$.each(result, function(index,currentResult){
				xData.push([index,currentResult[indexforx]]);
				});

				$.each(collection, function(key, current){
				var temp = {};

				temp['label'] = key;
				temp['data'] = current;
				totalArr.push(temp);
				});		
				console.info(totalArr);
				self.newbarchart(widgetKey,totalArr, xData);	 */
				
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
				self.totalArrforbar = totalArr;
				self.xDataforbar = xData;
				self.highChartBar(widgetKey,totalArr, xData);
				
				
			}catch(exception){
			}
		},
		
		highChartBar : function(widgetKey,totalArr,xData) {
			//$('#content_' + widgetKey).empty();
			$('#placeholder_' + widgetKey).highcharts({
				chart: {
					type: 'column',
					height: $('#placeholder_'+widgetKey).parent().height() - 20,
					width: $('#placeholder_' + widgetKey).width(),
					backgroundColor: null,
				},
				
				title: {
					text: null
				},
				subtitle: {
					text: null
				},
				xAxis: {
					categories: xData
				},
				yAxis: {
					min: 0,
					title: {
						text: null
					}
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
				series: totalArr
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
			self.optionsshowhide('pieChartOpt');
				for(var z=0;z<querydata.length;z++) {
					$("select.percentval").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');
					$("select.legendval").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');						
				}
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
						$('ul[name="sortable1"]').append('<li class="ui-state-default" value='+index1+'>'+index1+'</li>');	
						querydata[querycount] = index1;								
						querycount++;
						}										
					});
					queryflag = 1;	
				});
				for(var z=0;z<querydata.length;z++) {
					$("select.baraxis").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');
				}
				self.optionsshowhide('barChartOpt');							
				$('.connectedSortable').sortable({
					connectWith: '.connectedSortable',
					cancel: ".ui-state-disabled"
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
				$("#"+tr_toshow).show();
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
			var self = this;
				var nameofwid = $("#nameofwidget"), query_add = $("#query_add");
				
				if(nameofwid.val() === '') {
					nameofwid.addClass('errormessage');
					nameofwid.focus();
					nameofwid.attr('placeholder','Enter Widget Name');
					nameofwid.bind('keypress', function() {
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
					self.actionBody.name = nameofwid.val();
					self.actionBody.query = query_add.val();					
					var autoref = ($('#timeout').is(':checked') && $('#timeoutval').val().trim() !== "" ? $('#timeoutval').val().trim() : null);
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
						$(".sortable2").children('li').each(function(index, current) {
							SelectedItems.push($(current).text());
						});
						self.properties.x = $.makeArray($("select.baraxis option:selected").val());
						self.properties.y = SelectedItems;
						self.properties.type = ['barchart'];
					}else if($("#widgetType option:selected").val() === 'table'){
						self.properties.type = ['table'];
					}
			
					self.graphAction(self.getActionHeader(self.actionBody, "addwidget"), function(response) {
						self.currentwidgetid = response.data;
						
						self.actionBody = {};
						
						self.actionBody.query = query_add.val();
						self.query = query_add.val();
						
						self.actionBody.applicationname = self.currentappname;
						self.actionBody.dashboardname = self.dashboardname;
						//self.actionBody.url = self.dashboardListener.dashboardURL;
						self.actionBody.widgetname = nameofwid.val();
						self.getWidgetDataInfo(response.data, {name : nameofwid.val(),properties : {type : self.properties.type, x : self.properties.x, y :self.properties.y},autoRefresh : autoref}, self.actionBody, true);
						$('#add_widget').hide();
						/* var chartinfo = {},indexforx,indexfory,SelectedItems = [];	
						if($("#widgetType option:selected").val() === 'linechart') {
						indexforx = $("select.xaxis option:selected").val();
						indexfory = $("select.yaxis option:selected").val();
						chartinfo.properties.type = ['linechart'];
						chartinfo.properties.x = $.makeArray(indexforx);
						chartinfo.properties.y = $.makeArray(indexfory);
						} else if($("#widgetType option:selected").val() === 'piechart') {
							chartinfo.properties = {};
							indexforx = $("select.percentval option:selected").val();
							indexfory = $("select.legendval option:selected").val();
							chartinfo.properties.x = $.makeArray(indexforx);
							chartinfo.properties.y = $.makeArray(indexfory);
							chartinfo.properties.type = ['piechart'];
							self.generatePieChart(self.currentwidgetid, chartinfo, self.dataforpiechart, '');
						} else if($("#widgetType option:selected").val() === 'barchart') {
							chartinfo.properties = {};
							$(".sortable2").children('li').each(function(index, current) {
								SelectedItems.push($(current).text());
							});
							chartinfo.properties.x= $.makeArray($("select.baraxis option:selected").val());
							chartinfo.properties.y = SelectedItems;
							chartinfo.properties.type = ['barchart'];
							self.generateBarChart(self.currentwidgetid, chartinfo, self.dataforbarchart, '');
						} */	
						var count = $('.noc_view').length;
						if(count == 1){
							$('.noc_view').css('width','98%');
						} else if (count >=2) {
							$('.noc_view').css('width','48%');
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
						self.highChartPie(placeval, self.piechartdata_new, -10, 0);	
					}  else if($(this).attr('proptype') === 'barchart') {
						self.highChartBar(placeval,self.totalArrforbar, self.xDataforbar);
					} else if($(this).attr('proptype') === 'linechart') {
						self.highChartLine(placeval);
					}
				} else {
					$("#content_"+placeval).children(".demo-container1").addClass('cssforchart');						
					$('.noc_view').show();
					var count = $('.noc_view').length;
					if(count == 1){
						$('.noc_view').css('height','97%');
						$('.noc_view').css('width','98%');
					} else if (count >=2) {
						$('.noc_view').css('height','45%');
						$('.noc_view').css('width','48%');
					}	 
					flagg = 0;
					if($(this).attr('proptype') === 'piechart') {
						self.highChartPie(placeval, self.piechartdata_new, -10, 5);	
					} else if($(this).attr('proptype') === 'barchart') {
						self.highChartBar(placeval,self.totalArrforbar, self.xDataforbar);
					} else if($(this).attr('proptype') === 'linechart') {
						self.highChartLine(placeval);
					}
				}														
			});
			
			$('.he-wrap').mouseenter(function(event){
				var heView = $(this).find('.he-view');
				heView.addClass('heasasas-view-show');
			});
			
			
			
			$('.settings_img').unbind('click');
			$('.settings_img').click(function() {
				self.addwidgetflag = 0;
				var heView = $(this).closest('.he-view');
				heView.css('visibility', 'visible');
				self.openccdashboardsettings(this,'add_widget');
				$("#update_tab").val('Update');
				
				$("#timeoutval_update").bind('keypress',function(e) {
				if((e.which >= 48 && e.which <= 57) || (e.which === 8)){
					return true;
				} else {
					e.preventDefault();
				}
				});
				
				//self.actionBody = {};
				//self.actionBody.widgetid = $(this).parents('div.noc_view').attr('dynid');
				
				self.getWidgetInfo($(this).parents('div.noc_view').attr('dynid'), function(result){
					$("#query_add").val(result.data.query);
					$("#nameofwidget").attr('disabled','disabled');
					$("#nameofwidget").val(result.data.name);
					if(result.data.properties) {
						$("#widgetType").val(result.data.properties.type.toString());
					}	
					self.optionsshowhide($("#widgetType").val());
					$("input[name='execute_query']").removeAttr('disabled');
					/* if(result.data.autorefresh) {
						$("#timeoutval_update").show();
						$("#timeoutval_update").val(result.data.autorefresh);
						$('#timeout_update').prop('checked', true);
						
					} else {
						$("#timeoutval_update").hide();
						$('#timeout_update').prop('checked', false);
					} */
				});
				
				/* self.graphAction(self.getActionHeader(self.actionBody, "widgetget"), function(response) {

				}); */
				//$(".settings_textarea").val(self.query);				
				/* $("select.xaxis").parent().parent().hide();
				$("select.yaxis").parent().parent().hide();
				$("select.percentval").parent().parent().hide();
				$("select.legendval").parent().parent().hide();
				$("select.baraxis").parent().parent().hide();
				$("#tabforbar").hide(); */
				/* if(($("#widgetType option:selected").val() === 'linechart')) {
					$("#update_tab").attr('disabled','disabled');
					if(!($("select.xaxis option:selected").val()== undefined)) {
						$("select.xaxis").parent().parent().show();
						$("select.yaxis").parent().parent().show();
						$("#update_tab").removeAttr('disabled');
					}	
				} else if($("#widgetType option:selected").val() === 'piechart') {
					$("#update_tab").attr('disabled','disabled');
					if(!($("select.percentval option:selected").val() == undefined)) {
						$("select.percentval").parent().parent().show();
						$("select.legendval").parent().parent().show();
						$("#update_tab").removeAttr('disabled');
					}	
				} else if($("#widgetType option:selected").val() === 'barchart') {
					$("#update_tab").attr('disabled','disabled');			
					if(!($("select.baraxis option:selected").val() == undefined)) {
						$("select.baraxis").parent().parent().show();
						$("#tabforbar").show();
						$("#update_tab").removeAttr('disabled');						
					}
				} else {
					$("#update_tab").removeAttr('disabled');
				} */	
				var dyn_id = $(this).parents('div.noc_view').attr('dynid');
				$("#add_widget").attr('dynid',dyn_id);
			});
			
			/* $("input[name='widgettype']").unbind('change');
			$("input[name='widgettype']").change(function() {
				if($(".configwidgetdatatype").val() === 'linechart') {
					$("#update_tab").attr('disabled','disabled');
				} else if($(".configwidgetdatatype").val() === 'piechart') {
					$("#update_tab").attr('disabled','disabled');
				} else if($(".configwidgetdataype").val() === 'barchart') {
					$("#update_tab").attr('disabled','disabled');
				} else {
					$("#update_tab").removeAttr('disabled');
					$("select.xaxis").parent().parent().hide();
					$("select.yaxis").parent().parent().hide();
					$("select.percentval").parent().parent().hide();
					$("select.legendval").parent().parent().hide();
				}
			}); */
			
			/* $(".configwidgetdatatype").unbind('change');
			$(".configwidgetdatatype").change(function() {
				if($(this).val() === 'linechart') {
					$("#update_tab").attr('disabled','disabled');
					if(!($("select.xaxis option:selected").val()== undefined)) {
						$("select.xaxis").parent().parent().show();
						$("select.yaxis").parent().parent().show();
						$("#update_tab").removeAttr('disabled');	
					}	
					$("select.percentval").parent().parent().hide();
					$("select.legendval").parent().parent().hide();
					$("#tabforbar").hide();
					$("select.baraxis").parent().parent().hide();
				} else if($(this).val() === 'piechart') {
					$("#update_tab").attr('disabled','disabled');
					$("select.xaxis").parent().parent().hide();
					$("select.yaxis").parent().parent().hide();
					if(!($("select.percentval option:selected").val() == undefined)) {
						$("select.percentval").parent().parent().show();
						$("select.legendval").parent().parent().show();
						$("#update_tab").removeAttr('disabled');	
					}	
					$("#tabforbar").hide();
					$("select.baraxis").parent().parent().hide();
				} else if($(this).val() === 'barchart') {
					$("#update_tab").attr('disabled','disabled');
					$("select.xaxis").parent().parent().hide();
					$("select.yaxis").parent().parent().hide();
					$("select.percentval").parent().parent().hide();
					$("select.legendval").parent().parent().hide();	
					if(!($("select.baraxis option:selected").val() == undefined)) {
						$("select.baraxis").parent().parent().show();
						$("#tabforbar").show();
						$("#update_tab").removeAttr('disabled');						
					}					
				}
			}); */
			
			$("input[name='execute_query']").unbind('click');
			$("input[name='execute_query']").click(function() {
				self.actionBody = {};
				var queryflag = 0, querydata=[], querycount = 0, textareaval;
				$("select.xaxis").empty();				
				$("select.yaxis").empty();
				$("select.percentval").empty();
				$("select.legendval").empty();
				$("#tabforbar").find('ul').empty();
				textareaval = $("#query_add");
				self.actionBody.query = textareaval.val();
				var nameofwid = $("#nameofwidget");
				
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
				var widgetKey = $(this).parent().parent().attr('dynid'), tempdata=[], tempvalue=[], i=0, flag=0, j=0, currentWidget = {}, indexforx, indexfory;
				var typeofwidget = $('input[name="widgettype"]:checked').val();
				var widgetdatatype = $('#widgetType option:selected').val();	
				var widgetId = $("#update_tab").parents('#add_widget').attr('dynid');
				var dataforx = [], datafory = [], dataforchart = [], countforx = 0, countfory = 0, sum = 0;
				var SelectedItems = [], collection = {}, totalArr = [], xVal = [],datafory = [];

				self.actionBody = {};
				self.actionBody.query = $("#query_add").val();
				self.actionBody.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
				self.actionBody.starttime = '';
				self.actionBody.endtime = '';
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
					$(".sortable2").children('li').each(function(index, current) {
						SelectedItems.push($(current).text());
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
								if(widgetKey === val.widgetKey){
									clearInterval(key);
									delete commonVariables.clearInterval[key];
									return true;
								}
							});

							$('#placeholder_' + widgetKey).empty();
							
							if(widgetdatatype === 'linechart'){
								self.generateLineChart(widgetKey, currentWidget.data, self.dataforlinechart);
							} else if(widgetdatatype === 'piechart'){					
								currentWidget.data.properties = {};
								currentWidget.data.properties.x = $.makeArray(indexforx);
								currentWidget.data.properties.y = $.makeArray(indexfory);
								currentWidget.data.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
								
								self.actionBody = {};
								self.actionBody.query = $("#query_add").val();
								self.actionBody.applicationname = self.currentappname;
								self.actionBody.dashboardname = self.dashboardname;
								self.actionBody.widgetname = $("#content_"+widgetId).parent().attr('widgetname');
								
								self.generatePieChart(widgetKey, currentWidget.data, self.dataforpiechart, self.actionBody);
								
							} else if(widgetdatatype === 'barchart') {				
								currentWidget.data.properties = {};
								currentWidget.data.properties.x = $.makeArray($("select.baraxis option:selected").val());
								currentWidget.data.properties.y = SelectedItems;
								currentWidget.data.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
								self.generateBarChart(widgetKey, currentWidget.data, self.dataforbarchart, self.actionBody);
							} else if(widgetdatatype === 'table'){
								self.generateTable(widgetKey, self.datafortable.data.results);
							}
							
							$("#add_widget").hide();	
							if(typeofwidget === 'table') {
								$('#tableview_'+widgetKey).parent('li').addClass('active');
								$('#graphview_'+widgetKey).parent('li').removeClass('active');	
								$("#table_"+widgetKey).show();
								$("#content_"+widgetKey).hide();	
							} else {
								$('#tableview_'+widgetKey).parent('li').removeClass('active');
								$('#graphview_'+widgetKey).parent('li').addClass('active');	
								$("#table_"+widgetKey).hide();
								$("#content_"+widgetKey).show();	
							}
						});
					}
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