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
		chartdata : null,
		currentappname : null,
		currentdashboardid : null,
		flag_d : null,
		arrayy : null,
		properties : {},
		query : null,
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
			var d1 = [];
		for (var i = 0; i < dataforx.length; i++) {
			d1.push([dataforx[i], datafory[i]]);
		}

		var placeholder = $("#placeholder_"+placeholderindex);
		var plot = $.plot(placeholder, [d1], {
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
			}
		});

		// The plugin includes a jQuery plugin for adding resize events to any
		// element.  Add a callback so we can display the placeholder size.

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
					showTooltip(pos.pageX, pos.pageY,'Value' + z);
					$("#tooltip_"+placeholderindex).css('z-index','4');
				} else {
					$("#tooltip_"+placeholderindex).css('display','none');
				}	
			});	
		
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
					label: seriesvalue[i],
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
				commonVariables.api.ajaxRequest(header,
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
				projectRequestBody.host = "172.16.8.250";
				projectRequestBody.port = "8089";	
				projectRequestBody.datatype = "Splunk";
				projectRequestBody.query = "search " + projectRequestBody.query;
				header.requestMethod = "POST";				
				header.requestPostBody = JSON.stringify(projectRequestBody);
				var tempp = '172.16.25.236:8080';
				//console.info($(tempp).split(':'));
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

			} 
			
			return header;
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
					var p = 0, flag_naya = 0, tempdatanew = [], kl = 0, j=0, idgenerate, placeindex;					
					self.actionBody = {};
					self.actionBody.dashboardid = $(this).parent().attr('id');
					self.currentdashboardid = $(this).parent().attr('id');
					self.actionBody.appDirName = $(this).parent().attr('appdirname');
					self.currentappname = $(this).parent().attr('appdirname');
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
							$.each(response.data.widgets,function(index,value) {
								self.actionBody = {};
								self.actionBody.query = value.query;
								self.actionBody.applicationname = self.currentappname;
								self.actionBody.dashboardname = self.dashboardname;
								self.actionBody.widgetname = value.name;
								
								 self.graphAction(self.getActionHeader(self.actionBody, "searchdashboard"), function(response) {							
									idgenerate = Date.now();
									placeindex = p + idgenerate;
									var toappend = '<div class="noc_view" widgetid="'+index+'" widgetname="'+value.name+'" dynid="'+placeindex+'"><div class="tab_div"><div class="tab_btn"><input type="submit" value="" class="btn btn_style settings_btn settings_img"><input type="submit" value="" class="btn btn_style enlarge_btn"></div><div class="bs-docs-example"><ul class="nav nav-tabs tabchange" id="myTab"><li class="active"><a id="tableview_'+placeindex+'" data-toggle="tab" href="#">Table View</a></li><li><a id="graphview_'+placeindex+'" data-toggle="tab" href="#">Graph View</a></li></ul></div></div><div id="content_'+placeindex+'"><div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div></div> </div><div class="noc_table" id="table_'+placeindex+'"></div></div>';
									$('.features_content_main').prepend(toappend);
									$("#table_"+placeindex).empty();
									var tabdata = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"> <thead><tr></tr></thead><tbody></tbody></table>';
									$("#table_"+placeindex).append(tabdata);
									$.each(response.data.results,function(index4,value4) {
									$("#table_"+placeindex).find('tbody').append('<tr></tr>');
									$.each(value4,function(index3,value3) {
										if(flag_naya !==1) {
											tempdatanew[kl] = index3;								
											kl++;
										}	
										$("#table_"+placeindex).find('tbody tr:last').append('<td>'+value3+'</td>');
										j++;
									});
									j = 0;
									flag_naya = 1;
									});	
									for(var ijk=0;ijk<tempdatanew.length;ijk++) {
										$("#table_"+placeindex).find('thead tr').append('<th>'+tempdatanew[ijk]+'</th>');
									}
									var count = $('.noc_view').length;
									if(count == 1){
										$('.noc_view').css('width','98%');
									} else if (count ==2) {
										$('.noc_view').css('width','48%');
									}else if(count > 2) {
										$('.noc_view').css('width','31%');
									}
									$("#table_"+placeindex).show();
									$("#content_"+placeindex).hide();
									self.clickFunction();
									p++;
									
									if(!(value.properties === null)) {
											var zzz = value.properties.type,zzzz;
											zzzz = zzz.toString();
											if(zzzz === 'linechart') {
											$("#content_"+placeindex).empty();
											var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
											$("#content_"+placeindex).append(graphdata);
											var indexforx, indexfory, dataforx = [],datafory = [], countforx = 0, countfory = 0, temp1, temp2;
											temp1 = value.properties.x;
											temp2 = value.properties.y;
											indexforx = temp1.toString();
											indexfory = temp2.toString();
											$.each(response.data.results,function(index7,value7) {
												$.each(value7,function(index8,value8) {							
												if(index8 === indexforx) {
												dataforx[countforx] = value8;
												countforx++;
												}
												if(index8 === indexfory) {
												datafory[countfory] = value8;
												countfory++;
												}	
												});
											});
											self.lineChart(placeindex,dataforx,datafory);
										}if(zzzz === 'piechart') {
											$("#content_"+placeindex).empty();
											var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
											$("#content_"+placeindex).append(graphdata);
											var indexforx, indexfory, dataforx = [], datafory = [], dataforchart = [], countforx = 0, countfory = 0, sum = 0, temp1, temp2;
											temp1 = value.properties.x;
											temp2 = value.properties.y;
											indexforx = temp1.toString();
											indexfory = temp2.toString();
											$.each(response.data.results,function(index,value7) {
											$.each(value7,function(index8,value8) {							
											if(index8 === indexforx) {
											if($.isNumeric(value8)) {
											dataforx[countforx] = value8;
											countforx++;
											} else {
											dataforx[countforx] = null;
											countforx++;
											}	
											}
											if(index8 === indexfory) {
											datafory[countfory] = value8;
											countfory++;
											}	
											});
											});
											var new_data=[];
											for(var tttt=0;tttt<dataforx.length;tttt++) {
											new_data[tttt] =parseFloat(dataforx[tttt]); 
											sum+=new_data[tttt];
											}

											for(var qr=0;qr<new_data.length;qr++) {
											new_data[qr] = (new_data[qr]/sum) * 100;
											}
											self.pieChart(placeindex,new_data,datafory);
										}if(zzzz === 'barchart') {
											$("#content_"+placeindex).empty();
											var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
											$("#content_"+placeindex).append(graphdata);					
											var SelectedItems = [], collection = {}, totalArr = [], xVal = [],datafory = [], temp1, indexforx;
											SelectedItems = value.properties.y;
											temp1 = value.properties.x;
											indexforx = temp1.toString();
											$.each(SelectedItems, function(index, currentItem){
											collection['"' + currentItem + '"'] = [];
											$.each(response.data.results, function(index,currentResult){
											collection['"' + currentItem + '"'].push([index, currentResult[currentItem]]);
											});
											});

											$.each(response.data.results, function(index,currentResult){
											xVal.push([index,currentResult[indexforx]]);
											});

											$.each(collection, function(key, current){
											var temp = {};

											temp['label'] = key;
											temp['data'] = current;
											totalArr.push(temp);
											});		

											self.newbarchart(placeindex,totalArr, xVal);
										}
									}
									
								});
							});
						}
					}	
				});
			});
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
			$('.enlarge_btn').unbind('click');
			$('.enlarge_btn').click(function(){
				placeval = $(this).parents('div.noc_view').attr('dynid');
				var parent=$(this).closest('.noc_view');
				if(!flagg) {
					$('.noc_view').hide();	 				 
					parent.show();
					parent.css({height:'100%',width:'98%'});
					$("#tooltip_"+placeval).css('display','none');
					if($("#content_"+placeval).children(".demo-container").hasClass('cssforchart')) {
						$("#content_"+placeval).children(".demo-container").removeClass('cssforchart');
					}
					flagg = 1;
				} else {
					$("#content_"+placeval).children(".demo-container").addClass('cssforchart');						
					$('.noc_view').show();
					$('.noc_view').css('height','260px');
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
				$(".settings_textarea").val(self.query);
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
							self.dataforlinechart = response.data.results;
							$.each(response.data.results,function(index,value) {
								$.each(value,function(index1,value1) {							
									if(queryflag !==1) {
										querydata[querycount] = index1;								
										querycount++;
									}										
								});
								queryflag = 1;	
							});	
							$("select.xaxis").parent().parent().show();
							$("select.yaxis").parent().parent().show();
							for(var z=0;z<querydata.length;z++) {
								$("select.xaxis").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');
								$("select.yaxis").append('<option value='+querydata[z]+'>'+querydata[z]+'</option>');						
							}
							$("#update_tab").removeAttr('disabled');	
						} else if($('.configwidgetdatatype').val() === 'piechart') {
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
						} else if($('.configwidgetdatatype').val() === 'barchart') {
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
				var t = $(this).parent().parent().attr('dynid') ,tempdata=[],tempvalue=[],i=0, flag=0,j=0;
				var typeofwidget = $('input[name="widgettype"]:checked').val();
				var widgetdatatype = $('.configwidgetdatatype').val();								
				self.actionBody = {};				
				if(widgetdatatype === 'linechart') {
					$("#content_"+t).empty();
					var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+t+'" class="demo-placeholder"> </div>';
					$("#content_"+t).append(graphdata);
					var indexforx, indexfory, dataforx = [],datafory = [], countforx = 0, countfory = 0;
					indexforx = $("select.xaxis option:selected").val();
					indexfory = $("select.yaxis option:selected").val();
					self.properties.type = ['linechart'];
					self.properties.x = $.makeArray(indexforx);
					self.properties.y = $.makeArray(indexfory);
					$.each(self.dataforlinechart,function(index,value) {
						$.each(value,function(index1,value1) {							
							if(index1 === indexforx) {
								dataforx[countforx] = value1;
								countforx++;
							}
							if(index1 === indexfory) {
								datafory[countfory] = value1;
								countfory++;
							}	
						});
					});
					self.lineChart(t,dataforx,datafory);
				} else if(widgetdatatype === 'piechart') {
					$("#content_"+t).empty();
					var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+t+'" class="demo-placeholder"> </div>';
					$("#content_"+t).append(graphdata);
					var indexforx, indexfory, dataforx = [], datafory = [], dataforchart = [], countforx = 0, countfory = 0, sum = 0;
					indexforx = $("select.percentval option:selected").val();
					indexfory = $("select.legendval option:selected").val();
					self.properties.x = $.makeArray(indexforx);
					self.properties.y = $.makeArray(indexfory);
					self.properties.type = ['piechart'];
					$.each(self.dataforpiechart,function(index,value) {
						$.each(value,function(index1,value1) {							
							if(index1 === indexforx) {
								if($.isNumeric(value1)) {
									dataforx[countforx] = value1;
									countforx++;
								} else {
									dataforx[countforx] = null;
									countforx++;
								}	
							}
							if(index1 === indexfory) {
								datafory[countfory] = value1;
								countfory++;
							}	
						});
					});
					var new_data=[];
					for(var tttt=0;tttt<dataforx.length;tttt++) {
						new_data[tttt] =parseFloat(dataforx[tttt]); 
						sum+=new_data[tttt];
					}
					
					for(var qr=0;qr<new_data.length;qr++) {
						new_data[qr] = (new_data[qr]/sum) * 100;
					}
					self.pieChart(t,new_data,datafory);
				} else if(widgetdatatype === 'barchart') {
					$("#content_"+t).empty();
					var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+t+'" class="demo-placeholder"> </div>';
					$("#content_"+t).append(graphdata);					
					var SelectedItems = [], collection = {}, totalArr = [], xVal = [],datafory = [];
					
					$(".sortable2").children('li').each(function(index, current) {
						SelectedItems.push($(current).text());
					});
					
					var temp123 = $("select.baraxis option:selected").val();
					self.properties.x = $.makeArray(temp123);
					self.properties.y = SelectedItems;
					self.properties.type = ['barchart'];
					$.each(SelectedItems, function(index, currentItem){
						collection['"' + currentItem + '"'] = [];
						$.each(self.chartdata, function(index,currentResult){
							collection['"' + currentItem + '"'].push([index, currentResult[currentItem]]);
						});
					});
					
					$.each(self.chartdata, function(index,currentResult){
						xVal.push([index,currentResult[$("select.baraxis option:selected").val()]]);
					});
					
					$.each(collection, function(key, current){
						var temp = {};
						
						temp['label'] = key;
						temp['data'] = current;
						totalArr.push(temp);
					});		
					
					self.newbarchart(t,totalArr, xVal);
				}
					$("#table_"+t).empty();
					var tabdata = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"> <thead><tr></tr></thead><tbody></tbody></table>';
					$("#table_"+t).append(tabdata);
					$.each(self.chartdata,function(index,value) {
						$("#table_"+t).find('tbody').append('<tr></tr>');
						$.each(value,function(index1,value1) {
							if(flag !==1) {
								tempdata[i] = index1;								
								i++;
							}	
							$("#table_"+t).find('tbody tr:last').append('<td>'+value1+'</td>');
							j++;
						});
						j = 0;
						flag = 1;
					});					
					for(var ii=0;ii<tempdata.length;ii++) {
						$("#table_"+t).find('thead tr').append('<th>'+tempdata[ii]+'</th>');
					}
					
					self.actionBody = {};       
					self.actionBody.query = $(".settings_textarea").val();
					self.actionBody.autorefresh = '10';
					self.actionBody.starttime = '12122012';
					self.actionBody.endtime = '13122013';
					var finddynid = $("#update_tab").parents('#firstsettings').attr('dynid');
					self.actionBody.widgetid = $("#content_"+finddynid).parent().attr('widgetid');
					self.actionBody.name = $("#content_"+finddynid).parent().attr('widgetname');
					self.actionBody.appdirname = self.currentappname;
					self.actionBody.dashboardid = self.currentdashboardid;
					self.actionBody.properties = {};
					self.graphAction(self.getActionHeader(self.actionBody, "widgetupdate"), function(response) {							
					});
					
				$("#firstsettings").hide();	
				if(typeofwidget === 'table') {
					$('#tableview_'+t).parent('li').addClass('active');
					$('#graphview_'+t).parent('li').removeClass('active');	
					$("#table_"+t).show();
					$("#content_"+t).hide();	
				} else {
					$('#tableview_'+t).parent('li').removeClass('active');
					$('#graphview_'+t).parent('li').addClass('active');	
					$("#table_"+t).hide();
					$("#content_"+t).show();	
				}
			});
		},
		
	});

	return Clazz.com.components.dashboard.js.listener.DashboardListener;
});