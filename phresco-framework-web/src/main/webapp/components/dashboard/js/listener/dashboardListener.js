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
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard" ;
			}  else if(action === "dashboardget") {
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/"+projectRequestBody.dashboardid+"?projectId="+commonVariables.projectId+"" + "&appDirName="+projectRequestBody.appDirName+"";
			}  else if(action === "searchdashboard") {
				projectRequestBody.username = "admin";
				projectRequestBody.password = "devsplunk";
				projectRequestBody.url = self.dashboardURL;
				//projectRequestBody.host = '172.16.8.250'; //"http://172.16.8.250:8089";
				//projectRequestBody.port = '8089'; //"http://172.16.8.250:8089";
				projectRequestBody.datatype = "Splunk";
				projectRequestBody.query = projectRequestBody.query;
				header.requestMethod = "POST";				
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/search";
			} else if(action === "addwidget") {
				header.requestMethod = "POST";	
				projectRequestBody.projectid = commonVariables.projectId;
				projectRequestBody.appdirname = self.currentappname;
				projectRequestBody.dashboardid = self.currentdashboardid;
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";
			}  else if(action === "widgetupdate") {
				header.requestMethod = "PUT";	
				projectRequestBody.projectid = commonVariables.projectId;
				projectRequestBody.properties.x = self.properties.x;
				projectRequestBody.properties.y = self.properties.y;
				projectRequestBody.properties.type = self.properties.type;
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";

			} else if(action === 'widgetget') {
				header.requestMethod = "GET";	
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget/"+projectRequestBody.widgetid+"?projectId="+commonVariables.projectId+""+"&appDirName="+self.currentappname+""+"&dashboardid="+self.currentdashboardid+"";
			} else if(action === 'deletewidget') {
				header.requestMethod = "DELETE";	
				header.requestPostBody = JSON.stringify(projectRequestBody);
				header.webserviceurl = commonVariables.webserviceurl + "dashboard/widget";
				
			}
			
			return header;
		},
		
		createwidgetTableListener : function(widgetKey,currentWidget, response, create){
			var self = this, theadArr = [], thead = [], tbody = '', tColums = '', toappend = '';
			try{
				//result set
				$.each(response.data.results, function(index,currentVal){
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
			
				//commonVariables.api.localVal.setJson(widgetKey, currentWidget);
				if(create){
					toappend = '<div class="noc_view" widgetid="' + widgetKey + '" widgetname="'+currentWidget.name+'" dynid="' + widgetKey + '"><div class="dashboard_wid_title">'+currentWidget.name+'<span><img src="themes/default/images/Phresco/close_white_icon.png" name="close_widget"></span></div><div class="tab_div"><div class="tab_btn"><input type="submit" value="" class="btn btn_style settings_btn settings_img"><input type="submit" value="" class="btn btn_style enlarge_btn"></div><div class="bs-docs-example"><ul class="nav nav-tabs tabchange" id="myTab"><li class="active"><a id="tableview_' + widgetKey + '" data-toggle="tab" href="#">Table View</a></li><li><a id="graphview_' + widgetKey + '" data-toggle="tab" href="#">Graph View</a></li></ul></div></div><div id="content_' + widgetKey + '"><div class="demo-container cssforchart"><div id="placeholder_' + widgetKey + '" class="demo-placeholder"> </div></div> </div><div class="noc_table" id="table_' + widgetKey + '"><table id="widTab_'+ widgetKey +'" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"><thead><tr></tr></thead><tbody></tbody></table></div></div>';
					
					$('.features_content_main').prepend(toappend);
					$('#widTab_' + widgetKey +' thead tr').html(thead);
				}
				
				$('#widTab_' + widgetKey +' tbody').html(tbody);
				
			}catch(exception){
			 //exception
			}
		},
		
		
		getWidgetDataInfoListener : function(widgetKey,currentWidget){
			var self = this, regId = '';
			try{
				self.graphAction(self.getActionHeader(self.actionBody, "searchdashboard"), function(response){
				
					if(response && response.data && response.data.results){
						
						//table creation
						self.createwidgetTableListener(widgetKey,currentWidget, response, true);
						
						//chart creation
						if(currentWidget.properties){
							if(currentWidget.properties.type.toString() === "linechart"){
								self.generateLineChart(widgetKey, currentWidget, response.data.results, self.actionBody);
							}else if(currentWidget.properties.type.toString() === "piechart"){
								self.generatePieChart(widgetKey, currentWidget, response.data.results, self.actionBody);
							}else if(currentWidget.properties.type.toString() === "barchart"){
								self.generateBarChart(widgetKey, currentWidget, response.data.results, self.actionBody);
							}
						}
						
						//CSS changes for widget
						if($('.noc_view').length == 1){
							$('.noc_view').css('width','98%');
						} else if ($('.noc_view').length ==2) {
							$('.noc_view').css('width','48%');
						}else if($('.noc_view').length > 2) {
							$('.noc_view').css('width','31%');
						}
						
						//set default tab
						/* if(currentWidget.properties && currentWidget.properties.defaultTab && currentWidget.properties.defaultTab.toString() === "chart"){
							$("#table_" + widgetKey).hide();
							$("#content_" + widgetKey).show();
						}else{ */
							$("#table_" + widgetKey).show();
							$("#content_" + widgetKey).hide();
						//}
						

						//Click event for widget
					}
					
					
				});
				self.clickFunction();
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
								
								self.getWidgetDataInfoListener(widgetKey,currentWidget);
								
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
		
		generateLineChart : function(widgetKey, currentWidget, results, actionBody) {
			var self = this, graphdata = '<div class="demo-container cssforchart"><div id="placeholder_' + widgetKey + '" class="demo-placeholder"></div></div>',
			xVal, yVal;
			
			$("#content_" + widgetKey).empty();
			$("#content_" + widgetKey).append(graphdata);
			
			xVal = currentWidget.properties.x.toString();
			yVal = currentWidget.properties.y.toString();
			
			if(currentWidget.autorefresh){
				var objxVal = xVal, objyVal = yVal, autorefresh = currentWidget.autorefresh, objwidgetKey = widgetKey, objactionBody = actionBody;
				self.constructLineInfo(widgetKey, xVal, yVal, results);
				var regId = setInterval(function(){
					if(commonVariables.clearInterval[regId]){
						objxVal = commonVariables.clearInterval[regId].objxVal;
						objyVal = commonVariables.clearInterval[regId].objyVal;
						autorefresh = commonVariables.clearInterval[regId].autorefresh;
						objwidgetKey = commonVariables.clearInterval[regId].widgetKey;
						objactionBody = commonVariables.clearInterval[regId].actionBody;
					}
					
					graphdata = '<div class="demo-container cssforchart"><div id="placeholder_' + objwidgetKey + '" class="demo-placeholder"></div></div>'
					$("#content_" + objwidgetKey).empty();
					$("#content_" + objwidgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetTableListener(objwidgetKey,null, response, false);
						//chart update
						self.constructLineInfo(objwidgetKey, objxVal, objyVal, response.data.results);
					});
					
				},autorefresh);
				commonVariables.clearInterval[regId] = { objxVal : xVal, objyVal : yVal, autorefresh : currentWidget.autorefresh, widgetKey : widgetKey, actionBody : actionBody};
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructLineInfo(widgetKey, xVal, yVal, results);}
		},
		
		
		
		constructLineInfo : function(widgetKey, xVal, yVal, results){
			var self = this, xData = [], yData = [];
			try{
				$.each(results, function(key, value) {
					if(xVal == '_time' && key === "_time"){
						var tempVal = new Date(value[xVal]);
						xData.push(new Date(tempVal.getTime() - ((-5.5*60)*60000)));
					}else{xData.push(value[xVal]);}	

					yData.push(value[yVal]);
				});
				
				self.lineChart(widgetKey, xData, yData);
			}catch(exception){
			
			}
		},
		
		generatePieChart : function(widgetKey, currentWidget, results, actionBody) {
			var self = this, graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+widgetKey+'" class="demo-placeholder"> </div></div>', xVal,yVal;
			$("#content_"+widgetKey).empty();			
			$("#content_"+widgetKey).append(graphdata);
			
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
					
					graphdata = '<div class="demo-container cssforchart"><div id="placeholder_' + objwidgetKey + '" class="demo-placeholder"></div></div>'
					$("#content_" + objwidgetKey).empty();
					$("#content_" + objwidgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetTableListener(objwidgetKey,null, response, false);
						//chart update
						self.constructPieInfo(objwidgetKey, objxVal, objyVal, response.data.results);
					});
					
				},autorefresh);
				commonVariables.clearInterval[regId] = { objxVal : xVal, objyVal : yVal, autorefresh : currentWidget.autorefresh, widgetKey : widgetKey, actionBody : actionBody};
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructPieInfo(widgetKey, xVal, yVal, results);}
		},	
		
		constructPieInfo : function(widgetKey, xVal, yVal, result){
			var self = this, xData = [], yData = [];
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
			for(var tttt=0;tttt<xData.length;tttt++) {
				new_data[tttt] =parseFloat(xData[tttt]); 
				sum+=new_data[tttt];
			}
			
			for(var qr=0;qr<new_data.length;qr++) {
				new_data[qr] = (new_data[qr]/sum) * 100;
			}
			self.pieChart(widgetKey, new_data, yData);	
			}catch(exception){
			
			}
		},
		
		generateBarChart : function(widgetKey, currentWidget, results, actionBody) {
			var self = this, graphdata = '<div class="demo-container cssforchart"><div id="placeholder_' + widgetKey + '" class="demo-placeholder"></div></div>',
			xVal, yVal = [];
			
			$("#content_" + widgetKey).empty();
			$("#content_" + widgetKey).append(graphdata);
			
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
					
					graphdata = '<div class="demo-container cssforchart"><div id="placeholder_' + objwidgetKey + '" class="demo-placeholder"></div></div>'
					$("#content_" + objwidgetKey).empty();
					$("#content_" + objwidgetKey).append(graphdata);
					//commonVariables.hideloading = true;
					
					self.graphAction(self.getActionHeader(objactionBody, "searchdashboard"), function(response){
						//table update
						self.createwidgetTableListener(objwidgetKey,null, response, false);
						//chart update
						self.constructBarInfo(objwidgetKey, objxVal, objyVal, response.data.results);
					});
					
				},autorefresh);
				commonVariables.clearInterval[regId] = { objxVal : xVal, objyVal : yVal, autorefresh : currentWidget.autorefresh, widgetKey : widgetKey, actionBody : actionBody};
				
				//commonVariables.clearInterval.push(regId); 
			}else{self.constructBarInfo(widgetKey, xVal, yVal, results);}
		},
		
		constructBarInfo : function(widgetKey, xVal, yVal, result){
			var self = this, xData = [], yData = [], indexforx, collection = {}, totalArr = [];
			try{				
				indexforx = xVal[0];
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
				
				self.newbarchart(widgetKey,totalArr, xData);	
			}catch(exception){
			}
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
				
				$("select.xaxis").parent().parent().show();
				$("select.yaxis").parent().parent().show();
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
				$("select.percentval").parent().parent().show();
				$("select.legendval").parent().parent().show();
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
				$("#tabforbar").show();
				$("select.baraxis").parent().parent().show();							
				$('.connectedSortable').sortable({
					connectWith: '.connectedSortable',
					cancel: ".ui-state-disabled"
				});
			}catch(exception){
				//Exception
			}
		},
		
		clickFunction : function() {
			var self = this, flagg = 0, placeval;
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
			
			$('img[name="close_widget"]').unbind('click');
			$('img[name="close_widget"]').click(function() {
				var widgetKey = $(this).parents('div.noc_view').attr('widgetid');
				self.actionBody = {};
				self.actionBody.dashboardid = self.currentdashboardid;
				self.actionBody.widgetid = 	widgetKey;
				self.actionBody.appdirname = self.currentappname;
				self.graphAction(self.getActionHeader(self.actionBody, "deletewidget"), function(response) {
					$("#" + widgetKey).remove();
				});
			});
			
			$('.enlarge_btn').unbind('click');
			$('.enlarge_btn').click(function(){
				placeval = $(this).parents('div.noc_view').attr('dynid');
				var parent=$(this).closest('.noc_view');
				if(!flagg) {
					$('.noc_view').hide();	 				 
					parent.show();
					parent.css({height:'auto',width:'98%'});
					$("#tooltip_"+placeval).css('display','none');
					if($("#content_"+placeval).children(".demo-container").hasClass('cssforchart')) {
						$("#content_"+placeval).children(".demo-container").removeClass('cssforchart');
					}
					flagg = 1;
				} else {
					$("#content_"+placeval).children(".demo-container").addClass('cssforchart');						
					$('.noc_view').show();
					$('.noc_view').css('height','290px');
					 var count = $('.noc_view').length;
					if(count == 1){
						$('.noc_view').css('width','98%');
					} else if (count ==2) {
						$('.noc_view').css('width','48%');
					}else if(count > 2) {
						$('.noc_view').css('width','31%');
					}  
					flagg = 0;
				}														
			});
			$('.settings_img').unbind('click');
			$('.settings_img').click(function() {
				self.openccpl(this,'firstsettings');
				
				$("#timeoutval_update").bind('keypress',function(e) {
				if((e.which >= 48 && e.which <= 57) || (e.which === 8)){
					return true;
				} else {
					e.preventDefault();
				}
				});
				
				self.actionBody = {};
				self.actionBody.widgetid = $(this).parents('div.noc_view').attr('dynid');
				self.graphAction(self.getActionHeader(self.actionBody, "widgetget"), function(response) {
					$(".settings_textarea").val(response.data.query);
					$(".configwidgetdatatype").val(response.data.properties.type.toString());
					if(response.data.autorefresh) {
						$("#timeoutval_update").show();
						$("#timeoutval_update").val(response.data.autorefresh);
						$('#timeout_update').prop('checked', true);
						
					} else {
						$("#timeoutval_update").hide();
						$('#timeout_update').prop('checked', false);
					}
				});
				//$(".settings_textarea").val(self.query);				
				$("select.xaxis").parent().parent().hide();
				$("select.yaxis").parent().parent().hide();
				$("select.percentval").parent().parent().hide();
				$("select.legendval").parent().parent().hide();
				$("select.baraxis").parent().parent().hide();
				$("#tabforbar").hide();
				if(($(".configwidgetdatatype").val() === 'linechart')) {
					$("#update_tab").attr('disabled','disabled');
					if(!($("select.xaxis option:selected").val()== undefined)) {
						$("select.xaxis").parent().parent().show();
						$("select.yaxis").parent().parent().show();
						$("#update_tab").removeAttr('disabled');
					}	
				} else if($(".configwidgetdatatype").val() === 'piechart') {
					$("#update_tab").attr('disabled','disabled');
					if(!($("select.percentval option:selected").val() == undefined)) {
						$("select.percentval").parent().parent().show();
						$("select.legendval").parent().parent().show();
						$("#update_tab").removeAttr('disabled');
					}	
				} else if($(".configwidgetdatatype").val() === 'barchart') {
					$("#update_tab").attr('disabled','disabled');			
					if(!($("select.baraxis option:selected").val() == undefined)) {
						$("select.baraxis").parent().parent().show();
						$("#tabforbar").show();
						$("#update_tab").removeAttr('disabled');						
					}
				} else {
					$("#update_tab").removeAttr('disabled');
				}	
				var dyn_id = $(this).parents('div.noc_view').attr('dynid');
				$("#firstsettings").attr('dynid',dyn_id);
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
			
			$(".configwidgetdatatype").unbind('change');
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
			});
			
			$("#execute_query").unbind('click');
			$("#execute_query").click(function() {
				self.actionBody = {};
				var queryflag = 0, querydata=[], querycount = 0, textareaval;
				$("select.xaxis").empty();				
				$("select.yaxis").empty();
				$("select.percentval").empty();
				$("select.legendval").empty();
				$("#tabforbar").find('ul').empty();
				textareaval = $(".settings_textarea");
				self.actionBody.query = textareaval.val();
				if(textareaval.val() === '') {
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
					self.actionBody.widgetname = '';
					self.graphAction(self.getActionHeader(self.actionBody, "searchdashboard"), function(response) {
						self.chartdata = response.data.results;
						if($('.configwidgetdatatype').val() === 'linechart') {
							self.lineChartQueryExe(response);
						} else if($('.configwidgetdatatype').val() === 'piechart') {
							self.pieChartQueryExe(response);							
						} else if($('.configwidgetdatatype').val() === 'barchart') {
							self.barChartQueryExe(response);
						}	
					});		
				}	
			});
			
			$('.closeset').unbind('click');
			$('.closeset').click(function() {
				$("#firstsettings").hide();
			});
			
			$('#update_tab').unbind('click');
			$('#update_tab').click(function() {
				var widgetKey = $(this).parent().parent().attr('dynid'), tempdata=[], tempvalue=[], i=0, flag=0, j=0, currentWidget = {}, indexforx, indexfory;
				var typeofwidget = $('input[name="widgettype"]:checked').val();
				var widgetdatatype = $('.configwidgetdatatype').val();	
				var widgetId = $("#update_tab").parents('#firstsettings').attr('dynid');
				var dataforx = [], datafory = [], dataforchart = [], countforx = 0, countfory = 0, sum = 0;
				var SelectedItems = [], collection = {}, totalArr = [], xVal = [],datafory = [];

				self.actionBody = {};
				self.actionBody.query = $(".settings_textarea").val();
				self.actionBody.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
				self.actionBody.starttime = '';
				self.actionBody.endtime = '';
				self.actionBody.widgetid = $("#content_"+widgetId).parent().attr('widgetid');
				self.actionBody.name = $("#content_"+widgetId).parent().attr('widgetname');
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
				}
				
				self.graphAction(self.getActionHeader(self.actionBody, "widgetupdate"), function(response) {

					//removing service call
					$.each(commonVariables.clearInterval, function(key, val){
						if(widgetKey === val.widgetKey){
							clearInterval(key);
							delete commonVariables.clearInterval[key];
							return true;
						}
					});
					
					if(widgetdatatype === 'linechart'){
						currentWidget.properties = {};
						currentWidget.properties.x = $.makeArray(indexforx);
						currentWidget.properties.y = $.makeArray(indexfory);
						currentWidget.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
						
						self.actionBody = {};
						self.actionBody.query = $(".settings_textarea").val();
						self.actionBody.applicationname = self.currentappname;
						self.actionBody.dashboardname = self.dashboardname;
						self.actionBody.widgetname = $("#content_"+widgetId).parent().attr('widgetname');
						
						self.generateLineChart(widgetKey, currentWidget, self.dataforlinechart, self.actionBody);
						
					} else if(widgetdatatype === 'piechart'){					
						currentWidget.properties = {};
						currentWidget.properties.x = $.makeArray(indexforx);
						currentWidget.properties.y = $.makeArray(indexfory);
						currentWidget.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
						
						self.actionBody = {};
						self.actionBody.query = $(".settings_textarea").val();
						self.actionBody.applicationname = self.currentappname;
						self.actionBody.dashboardname = self.dashboardname;
						self.actionBody.widgetname = $("#content_"+widgetId).parent().attr('widgetname');
						
						self.generatePieChart(widgetKey, currentWidget, self.dataforpiechart, self.actionBody);
						
					} else if(widgetdatatype === 'barchart') {				
						currentWidget.properties = {};
						currentWidget.properties.x = $.makeArray(temp123);
						currentWidget.properties.y = SelectedItems;
						currentWidget.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
						self.generateBarChart(widgetKey, currentWidget, self.dataforbarchart, self.actionBody);
					}
					
					//table update
					self.createwidgetTableListener(widgetKey,null, self.chartdata, false);
					
					$("#firstsettings").hide();	
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
				
				
				
				
				/* $("#table_"+widgetKey).empty();
				var tabdata = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"> <thead><tr></tr></thead><tbody></tbody></table>';
				$("#table_"+widgetKey).append(tabdata);
				$.each(self.chartdata,function(index,value) {
					$("#table_"+widgetKey).find('tbody').append('<tr></tr>');
					$.each(value,function(index1,value1) {
						if(flag !==1) {
							tempdata[i] = index1;								
							i++;
						}	
						$("#table_"+widgetKey).find('tbody tr:last').append('<td>'+value1+'</td>');
						j++;
					});
					j = 0;
					flag = 1;
				});					
				for(var ii=0;ii<tempdata.length;ii++) {
					$("#table_"+widgetKey).find('thead tr').append('<th>'+tempdata[ii]+'</th>');
				} */
				
				/* self.actionBody = {};       
				self.actionBody.query = $(".settings_textarea").val();
				self.actionBody.autorefresh = ($('#timeout_update').is(':checked') && $('#timeoutval_update').val().trim() !== "" ? $('#timeoutval_update').val().trim() : null);
				self.actionBody.starttime = '';
				self.actionBody.endtime = '';
				self.actionBody.widgetid = $("#content_"+widgetId).parent().attr('widgetid');
				self.actionBody.name = $("#content_"+widgetId).parent().attr('widgetname');
				self.actionBody.appdirname = self.currentappname;
				self.actionBody.dashboardid = self.currentdashboardid;
				self.actionBody.properties = {};
				self.graphAction(self.getActionHeader(self.actionBody, "widgetupdate"), function(response) {

					$("#firstsettings").hide();	
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
				}); */
			});
		},
		
	});

	return Clazz.com.components.dashboard.js.listener.DashboardListener;
});