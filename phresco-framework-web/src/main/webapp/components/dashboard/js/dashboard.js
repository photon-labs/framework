define(["framework/widgetWithTemplate", "dashboard/listener/dashboardListener"], function() {
	Clazz.createPackage("com.components.dashboard.js");

	Clazz.com.components.dashboard.js.Dashboard = Clazz.extend(Clazz.WidgetWithTemplate, {
		onLoginEvent : null,
		dashboardListener : null,
		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "components/dashboard/template/dashboard.tmp",
		configUrl: "components/dashboard/config/config.json",
		name : commonVariables.dashboard,
		localConfig: null,
		actionBody: {},
		currentwidgetid : null,
		dashboardnametemp : null,
		widgetnametemp : null,

		/***
		 * Called in initialization time of this class 
		 */
		initialize : function(){
			var self = this;
			
			if(self.onLoginEvent === null){
				self.onLoginEvent = new signals.Signal();
			}
			if(self.dashboardListener === null){
				self.dashboardListener = new Clazz.com.components.dashboard.js.listener.DashboardListener();
			}	
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage : function(){
			$(commonVariables.basePlaceholder).empty();
			Clazz.navigationController.jQueryContainer = commonVariables.basePlaceholder;
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {	
			var self = this, temparray = [], appdirnamearray = [], dashboardid = [], dashboardnamearray = [], usernamearray = [], passwordarray = [], url = [], i=0, widgetcount = 0, appcount = 0, p=0, collection = {}, flag_naya = 0, tempdata = [], i =0, j=0, kl = 0, i=0, placeindex, idgenerate;
			
			self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "get"), function(response) {
				$.each(response.data.appInfos,function(index,value) {
					$(".appdirnamedropdown").append('<option id='+value.id+' code='+value.code+' appDirName='+value.appDirName+' value='+value.name+'>'+value.name+'</option>');	
				});
				self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "dashboardlistall"), function(response) {
					if(response.status  === 'success') {
						appcount = 0;
						$.each(response.data,function(index,value) {							
							self.dashboardListener.currentappname = index;
							$(".appdirnamedropdown option").each(function() {								
								if(index === $(this).val()) {									
									widgetcount = 0;									
									$.each(response.data[index].dashboards,function(index1,value1) {		
										appdirnamearray[i] = index;
										dashboardid[i] = index1;
										dashboardnamearray[i] = value1.dashboardname;
										usernamearray[i] = value1.username;
										passwordarray[i] = value1.password;
										url[i] = value1.url;
										i++;
										if(appcount === 0) {
										if(widgetcount === 0) {
											self.dashboardListener.currentdashboardid = index1;
											collection['"' + index1 + '"'] = [];
											if(value1.widgets !== null) {
												$.each(value1.widgets,function(index2,value2) {
													self.actionBody = {};
													self.actionBody.query = value2.query;
													self.actionBody.applicationname = index;
													self.actionBody.dashboardname = value1.dashboardname;
													self.dashboardnametemp = value1.dashboardname;
													self.actionBody.widgetname = value2.name;
													self.widgetnametemp = value2.name;
													 self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "searchdashboard"), function(response) {
														idgenerate = Date.now();
														placeindex = p + idgenerate;
														var toappend = '<div class="noc_view" widgetid="'+index2+'" widgetname="'+value2.name+'" dynid="'+placeindex+'"><div class="tab_div"><div class="tab_btn"><input type="submit" value="" class="btn btn_style settings_btn settings_img"><input type="submit" value="" class="btn btn_style enlarge_btn"></div><div class="bs-docs-example"><ul class="nav nav-tabs tabchange" id="myTab"><li class="active"><a id="tableview_'+placeindex+'" data-toggle="tab" href="#">Table View</a></li><li><a id="graphview_'+placeindex+'" data-toggle="tab" href="#">Graph View</a></li></ul></div></div><div id="content_'+placeindex+'"><div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div></div> </div><div class="noc_table" id="table_'+placeindex+'"></div></div>';
														$('.features_content_main').prepend(toappend);
														$("#table_"+placeindex).empty();
														var tabdata = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"> <thead><tr></tr></thead><tbody></tbody></table>';
														$("#table_"+placeindex).append(tabdata);
														$.each(response.data.results,function(index4,value4) {
														$("#table_"+placeindex).find('tbody').append('<tr></tr>');
														$.each(value4,function(index3,value3) {
															if(flag_naya !==1) {
																tempdata[kl] = index3;								
																kl++;
															}	
															$("#table_"+placeindex).find('tbody tr:last').append('<td>'+value3+'</td>');
															j++;
														});
														j = 0;
														flag_naya = 1;
														});	
														for(var ii=0;ii<tempdata.length;ii++) {
															$("#table_"+placeindex).find('thead tr').append('<th>'+tempdata[ii]+'</th>');
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
														self.dashboardListener.clickFunction();
														p++;
														if(!(value2.properties === null)) {
															var proptype,propvalue;
															proptype = value2.properties.type
															propvalue = proptype.toString();
															if(propvalue === 'linechart') {
																$("#content_"+placeindex).empty();
																var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
																$("#content_"+placeindex).append(graphdata);
																var indexforx, indexfory, dataforx = [],datafory = [], countforx = 0, countfory = 0,temp1,temp2;
																temp1 = value2.properties.x;
																temp2 = value2.properties.y;
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
																self.dashboardListener.lineChart(placeindex,dataforx,datafory);
															}if(propvalue === 'piechart') {
																$("#content_"+placeindex).empty();
																var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
																$("#content_"+placeindex).append(graphdata);
																var indexforx, indexfory, dataforx = [], datafory = [], dataforchart = [], countforx = 0, countfory = 0, sum = 0,temp1,temp2;
																temp1 = value2.properties.x;
																temp2 = value2.properties.y;
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
																self.dashboardListener.pieChart(placeindex,new_data,datafory);
															}if(propvalue === 'barchart') {
																$("#content_"+placeindex).empty();
																var graphdata = '<div class="demo-container cssforchart"><div id="placeholder_'+placeindex+'" class="demo-placeholder"> </div>';
																$("#content_"+placeindex).append(graphdata);					
																var SelectedItems = [], collection = {}, totalArr = [], xVal = [],datafory = [], temp1,indexforx;
																SelectedItems = value2.properties.y;
																temp1 = value2.properties.x;																
																indexforx = temp1[0];
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
																
																self.dashboardListener.newbarchart(placeindex,totalArr, xVal);
															}
														}
													});
													/* collection['"' +index1 + '"'].push([index2,value2.autorefresh]);
													collection['"' +index1 + '"'].push([index2,value2.endtime]);
													collection['"' +index1 + '"'].push([index2,value2.name]);
													collection['"' +index1 + '"'].push([index2,value2.properties]);
													collection['"' +index1 + '"'].push([index2,value2.query]);
													collection['"' +index1 + '"'].push([index2,value2.starttime]); */		
												});		
											}	
											widgetcount = 1;
										}	
										}
									});
									//}
									appcount = 1;
								}
							});				
							
							/* for(nnop=0;nnop<dashboardnamearray.length;nnop++) {
								console.info(nnop);
								$("ul.dashboardslist").append('<li class="dropdown" url_url='+url[nnop]+' appdirname='+appdirnamearray[nnop]+' username='+usernamearray[nnop]+' password='+passwordarray[nnop]+' id='+dashboardid[nnop]+'><a href="javascript:void(0)" value='+dashboardnamearray[nnop]+'>'+dashboardnamearray[nnop]+'</a></li>');
							} */
						});	
						var nnop;
						for(nnop=0;nnop<dashboardnamearray.length;nnop++) {
								$("ul.dashboardslist").append('<li class="dropdown" url_url='+url[nnop]+' appdirname='+appdirnamearray[nnop]+' username='+usernamearray[nnop]+' password='+passwordarray[nnop]+' id='+dashboardid[nnop]+'><a href="javascript:void(0)" value='+dashboardnamearray[nnop]+'>'+dashboardnamearray[nnop]+'</a></li>');
						}
					} else {
						$(".forlistingdash").hide();
					}	
					self.dashboardListener.dropdownclick();	
				});
			});
		},
		
		 /* preRender: function(whereToRender, renderFunction){
		 	var self = this;
			
		},  */

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			var flag=0, toappend, counter = 0, placeholderval;
			var count = $('.noc_view').length;
			if(count == 1){
				$('.noc_view').css('width','98%');
			} else if (count ==2) {
				$('.noc_view').css('width','48%');
			}else if(count > 2) {
				$('.noc_view').css('width','31%');
			}
			$("select.xaxis").parent().parent().hide();
			$("select.yaxis").parent().parent().hide();
			$("select.percentval").parent().parent().hide();
			$("select.legendval").parent().parent().hide();
			$("select.baraxis").parent().parent().hide();
			$("#timeoutval").hide();
			$("#tabforbar").hide();
			$("#dashlist").hide();
			
			$('#add_wid').click(function() {
				self.openccpl(this,'add_widget');
			});
			
			$("#timeout").unbind('change');
			$("#timeout").change(function() {
				if($(this).is(':checked')) {
					$("#timeoutval").show();
				} else {
					$("#timeoutval").hide();
				}		
			});
			
			$("#timeoutval").bind('keypress',function(e) {
				if((e.which >= 48 && e.which <= 57) || (e.which === 8)){
					return true;
				} else {
					e.preventDefault();
				}
			});
			
			$('#config_noc').click(function() {
				self.openccpl(this,'noc_config');
				$('#noc_config').children().find('input[type="text"]').each(function() {
					if($(this).hasClass('errormessage')) {
						$(this).removeClass('errormessage');
						$(this).removeAttr('placeholder');
					} else {
						$(this).val('');
					}
				});
				if(self.dashboardListener.flag_d === 0) {					
					$("#dashboard_name").val($(".dashboardslist").children(0).children('a').attr('value'));
					$("#conf_username").val($(".dashboardslist").children(0).attr('username'));
					$("#conf_password").val($(".dashboardslist").children(0).attr('password'));
					$("#conf_url").val($(".dashboardslist").children(0).children('a').attr('url_url'));
				} else {
					$("#dashboard_name").val(self.dashboardListener.arrayy.dashboardname);	
					$("#conf_username").val(self.dashboardListener.arrayy.username);	
					$("#conf_password").val(self.dashboardListener.arrayy.password);	
					$("#conf_url").val(self.dashboardListener.arrayy.url);	
				}	
				
			});
			
			$("#configure_widget").unbind('click');
			$("#configure_widget").click(function() {
				var user = $("#conf_username");
				var pass = $("#conf_password");
				var url = $("#conf_url");
				var dashname = $("#dashboard_name");
				if(dashname.val() === '') {
					dashname.addClass('errormessage');
					dashname.focus();
					dashname.attr('placeholder','Enter Dashboard Name');
					dashname.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(user.val() === '') {
					user.addClass('errormessage');
					user.focus();
					user.attr('placeholder','Enter Username');
					user.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(pass.val() === '') {
					pass.addClass('errormessage');
					pass.focus();
					pass.attr('placeholder','Enter Password');
					pass.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else if(!self.isValidUrl(url.val())){
					url.addClass("errormessage");
					url.focus();						
					url.attr('placeholder','Invalid Repourl');						
					url.val('');
					url.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else {
					self.dashboardListener.currentappname = $("select.appdirnamedropdown option:selected").attr('appDirName');
					self.actionBody = {};
					self.actionBody.dashboardname = dashname.val();
					self.actionBody.username = user.val();
					self.actionBody.password = pass.val();
					self.actionBody.datatype = $("#data_type").val();
					self.actionBody.url = url.val();
					self.actionBody.appname = $("select.appdirnamedropdown option:selected").val();
					self.actionBody.appdirname = $("select.appdirnamedropdown option:selected").attr('appDirName');
					self.actionBody.appcode = $("select.appdirnamedropdown option:selected").attr('code');
					self.actionBody.appid = $("select.appdirnamedropdown option:selected").attr('id');
				     self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody,"dashboardconfigure"), function(response) { 
						$(".noc_view").remove();
						$("#noc_config").hide();
						if(response.status === 'success') {							
							self.dashboardListener.currentdashboardid = response.data;
						}	
					});
				}
			});	
			$('#widgetadd').click(function() {
				var tempdata=[],tempvalue=[],i=0, flag=0,j=0, errorflag = 0;
				counter = $('.noc_view').length;
				placeholderval = counter + 1;
				var nameofwid = $("#nameofwidget");
				var query_add = $("#query_add");
				if(nameofwid.val() === '') {
					nameofwid.addClass('errormessage');
					nameofwid.focus();
					nameofwid.attr('placeholder','Enter Widget Name');
					nameofwid.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
					errorflag = 1;
				} else if(query_add.val() === '') {
					query_add.addClass('errormessage');
					query_add.focus();
					query_add.attr('placeholder','Enter Query');
					query_add.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
					errorflag = 1;
				}
				if(errorflag !=1) {	
					self.dashboardListener.query = query_add.val();

					toappend = '<div class="noc_view" dynid="'+placeholderval+'"><div class="tab_div"><div class="tab_btn"><input type="submit" value="" class="btn btn_style settings_btn settings_img"><input type="submit" value="" class="btn btn_style enlarge_btn"></div><div class="bs-docs-example"><ul class="nav nav-tabs tabchange" id="myTab"><li class="active"><a id="tableview_'+placeholderval+'" data-toggle="tab" href="#">Table View</a></li><li><a id="graphview_'+placeholderval+'" data-toggle="tab" href="#">Graph View</a></li></ul></div></div><div id="content_'+placeholderval+'"><div class="demo-container cssforchart"><div id="placeholder_'+placeholderval+'" class="demo-placeholder"></div></div></div><div class="noc_table" id="table_'+placeholderval+'"></div></div>';
					$('.features_content_main').prepend(toappend);
					
					$("#table_"+placeholderval).empty();
						var tabdata = '<table class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"> <thead><tr></tr></thead><tbody></tbody></table>';
						$("#table_"+placeholderval).append(tabdata);
						self.actionBody = {};
						self.actionBody.query = $("#query_add").val();
						self.actionBody.applicationname = self.dashboardListener.currentappname;
						self.actionBody.dashboardname = self.dashboardnametemp;
						self.actionBody.widgetname = self.widgetnametemp;
					   self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody,"searchdashboard"), function(response) {
							$.each(response.data.results,function(index,value) {
								$("#table_"+placeholderval).find('tbody').append('<tr></tr>');
								$.each(value,function(index1,value1) {
									if(flag !==1) {
										tempdata[i] = index1;								
										i++;
									}	
									$("#table_"+placeholderval).find('tbody tr:last').append('<td>'+value1+'</td>');
									j++;
								});
								j = 0;
								flag = 1;
							});					
							for(var ii=0;ii<tempdata.length;ii++) {
								$("#table_"+placeholderval).find('thead tr').append('<th>'+tempdata[ii]+'</th>');
							}
							self.actionBody = {};
							self.actionBody.name = nameofwid.val();
							self.actionBody.query = query_add.val();
							self.actionBody.autorefresh = '10';
							self.actionBody.starttime = '12122012';
							self.actionBody.endtime = '13122013';
							/* if(self.dashboardListener.flag_d === 0) {
								console.info("enter");
								self.dashboardListener.currentdashboardid = $(".dashboardslist").children(0).attr('id');
							}	 */
					
							self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "addwidget"), function(response) {
								self.currentwidgetid = response.data;
								$("#content_"+placeholderval).parent().attr('widgetid',self.currentwidgetid);
								$("#content_"+placeholderval).parent().attr('widgetname',nameofwid.val());
							});
						});	
						
					$("#table_"+placeholderval).show();
					$("#content_"+placeholderval).hide();
					self.dashboardListener.clickFunction();
					var count = $('.noc_view').length;
					if(count == 1){
						$('.noc_view').css('width','98%');
					} else if (count ==2) {
						$('.noc_view').css('width','48%');
					}else if(count > 2) {
						$('.noc_view').css('width','31%');
					}
					$('#add_widget').hide();
					
					$('.demo-container').each(function() {
						if(!$(this).hasClass('cssforchart')) {
							$(this).addClass('cssforchart');
						}
					});
					$('.noc_view').show();	 
					$('.noc_view').css('height','260px');				
				}
			});
			
			$(".dyn_popup").hide();
		  $(window).resize(function() {
			$(".dyn_popup").hide();
		  });
		}
	});

	return Clazz.com.components.dashboard.js.Dashboard;
});