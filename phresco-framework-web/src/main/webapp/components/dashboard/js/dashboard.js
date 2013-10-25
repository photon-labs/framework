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
			var self = this, p=0, collection = {}, i =0, j=0, kl = 0, dashBoardListItems = '', bChech = false;
			
			
			self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "get"), function(response){
				$("#editprojecttitle").html("Edit - " +response.data.name);
				$.each(response.data.appInfos,function(index,value) {
					$(".appdirnamedropdown").append('<option id='+value.id+' code='+value.code+' appDirName='+value.appDirName+' value='+value.name+'>'+value.name+'</option>');	
				});
				
				//Get dashboard data
				self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "dashboardlistall"), function(response) {
					if(response.status  === 'success'){
						//appcount = 0;
						
						//looping list of application info
						$.each(response.data,function(key,currentApp) {
							
							self.dashboardListener.currentappname = key;
							
							//looping comparing first app from the list
							//$(".appdirnamedropdown option").each(function() {
							
								//if(index === $(this).val()) {									
									//widgetcount = 0;		

									//looping all the dashboard list
									$.each(currentApp.dashboards,function(dashBkey,currentdashB){
										
										dashBoardListItems += '<li class="dropdown" url_url='+ currentdashB.url + ' appdirname=' + key + ' username=' + currentdashB.username +' password=' + currentdashB.password + ' id=' + dashBkey +'><a href="javascript:void(0)" value=' + currentdashB.dashboardname +'>' + currentdashB.dashboardname + '</a></li>';
										
										if(!bChech) {
											bChech = true;
											self.dashboardListener.currentdashboardid = dashBkey;											
											//if(widgetcount === 0) {
											
											//clearing exist service call
											self.dashboardListener.clearService();
											
											if(currentdashB.widgets){
											
												//looping all widgets inside of dashboard
												$.each(currentdashB.widgets,function(widgetKey,currentWidget) {
												
													self.actionBody = {};
													self.actionBody.query = currentWidget.query;
													self.actionBody.applicationname = key;
													
													self.actionBody.dashboardname = currentdashB.dashboardname;
													self.dashboardListener.dashboardname = currentdashB.dashboardname;
													
													self.actionBody.widgetname = currentWidget.name;
													self.widgetnametemp = currentWidget.name;
													
													
													self.getWidgetDataInfo(widgetKey,currentWidget);
													
													/*  self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "searchdashboard"), function(response) {
														
													}); */
													/* collection['"' +index1 + '"'].push([index2,value2.autorefresh]);
													collection['"' +index1 + '"'].push([index2,value2.endtime]);
													collection['"' +index1 + '"'].push([index2,value2.name]);
													collection['"' +index1 + '"'].push([index2,value2.properties]);
													collection['"' +index1 + '"'].push([index2,value2.query]);
													collection['"' +index1 + '"'].push([index2,value2.starttime]); */		
												});		
											}	
											//widgetcount = 1;
											//}	
										}
									});
									
									$("ul.dashboardslist").append(dashBoardListItems);
									$("#click_listofdash").text($(".dashboardslist").children(0).children('a').attr('value'));
									$("#click_listofdash").append('<b class="caret"></b>');
									//}
									//appcount = 1;
								//}
							//});				
														
							
							return true;
						});	
					} else {
						$(".forlistingdash").hide();
					}	
					self.dashboardListener.dropdownclick();	
				});
				var height = $(this).height();
				
				var resultvalue = 0;
				$('.features_content_main').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv;
				});
				var footervalue = $('.footer_section').height();
				resultvalue = resultvalue + footervalue + 150;

				$('.features_content_main').height(height - resultvalue);			
				$('.features_content_main').css('overflow','auto');
			});
		},
		
		/* createwidgetTable : function(widgetKey,currentWidget, response){
			var self = this, theadArr = [], thead = [], tbody = '', tColums = '';
			try{
				commonVariables.api.localVal.setJson(widgetKey, currentWidget);
				var toappend = '<div class="noc_view" widgetid="' + widgetKey + '" widgetname="'+currentWidget.name+'" dynid="' + widgetKey + '"><div class="dashboard_wid_title">Widget Title<span><img src="themes/default/images/Phresco/close_white_icon.png"</span></div><div class="tab_div"><div class="tab_btn"><input type="submit" value="" class="btn btn_style settings_btn settings_img"><input type="submit" value="" class="btn btn_style enlarge_btn"></div><div class="bs-docs-example"><ul class="nav nav-tabs tabchange" id="myTab"><li class="active"><a id="tableview_' + widgetKey + '" data-toggle="tab" href="#">Table View</a></li><li><a id="graphview_' + widgetKey + '" data-toggle="tab" href="#">Graph View</a></li></ul></div></div><div id="content_' + widgetKey + '"><div class="demo-container cssforchart"><div id="placeholder_' + widgetKey + '" class="demo-placeholder"> </div></div> </div><div class="noc_table" id="table_' + widgetKey + '"><table id="widTab_'+ widgetKey +'" class="table table-striped table_border table-bordered" cellpadding="0" cellspacing="0" border="0"><thead><tr></tr></thead><tbody></tbody></table></div></div>';
				$('.features_content_main').prepend(toappend);
				
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
				$('#widTab_' + widgetKey +' thead tr').append(thead);
				$('#widTab_' + widgetKey +' tbody').append(tbody);
			}catch(exception){
			 //exception
			}
		}, */
		
		
		//get each widget info
		getWidgetDataInfo : function(widgetKey,currentWidget){
			var self = this, regId = '';
			try{
				self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "searchdashboard"), function(response){
				
					if(response && response.data && response.data.results){
						
						//table creation
						self.dashboardListener.createwidgetTableListener(widgetKey,currentWidget, response, true);
						
						//chart creation
						if(currentWidget.properties){
							if(currentWidget.properties.type.toString() === "linechart"){
								self.dashboardListener.generateLineChart(widgetKey, currentWidget, response.data.results, self.actionBody);
							}else if(currentWidget.properties.type.toString() === "piechart"){
								self.dashboardListener.generatePieChart(widgetKey, currentWidget, response.data.results, self.actionBody);
							}else if(currentWidget.properties.type.toString() === "barchart"){
								self.dashboardListener.generateBarChart(widgetKey, currentWidget, response.data.results, self.actionBody);
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
						if(currentWidget.properties && currentWidget.properties.defaultTab && currentWidget.properties.defaultTab.toString() === "chart"){
							$("#table_" + widgetKey).hide();
							$("#content_" + widgetKey).show();
						}else{
							$("#table_" + widgetKey).show();
							$("#content_" + widgetKey).hide();
						}
						

						//Click event for widget
						self.dashboardListener.clickFunction();
					}
					
					
				});
			}catch(exception){
				//exception
			}
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
			$("#timeoutval_update").hide();
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
			
			$("#timeout_update").unbind('change');
			$("#timeout_update").change(function() {
				if($(this).is(':checked')) {
					$("#timeoutval_update").show();
				} else {
					$("#timeoutval_update").hide();
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
					$("#config_url").val($(".dashboardslist").children(0).attr('url_url'));
				} else {
					$("#dashboard_name").val(self.dashboardListener.arrayy.dashboardname);	
					$("#conf_username").val(self.dashboardListener.arrayy.username);	
					$("#conf_password").val(self.dashboardListener.arrayy.password);	
					$("#config_url").val(self.dashboardListener.arrayy.url);	
				}	
			});
			
			$("#configure_widget").unbind('click');
			$("#configure_widget").click(function() {
				var user = $("#conf_username");
				var pass = $("#conf_password");
				var url = $("#config_url");
				
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
				} else if(url.val() === ''){
				//} else if(self.isValidUrl(url.val())){
					url.addClass("errormessage");
					url.focus();						
					url.attr('placeholder','Invalid URL');						
					url.val('');
					url.bind('keypress', function() {
						$(this).removeClass("errormessage");
						$(this).removeAttr("placeholder");
					});
				} else {
					self.dashboardListener.currentappname = $("select.appdirnamedropdown option:selected").attr('appDirName');
					self.actionBody = {};
					self.actionBody.dashboardname = dashname.val();
					self.dashboardListener.dashboardname = dashname.val();
					self.actionBody.username = user.val();
					self.actionBody.password = pass.val();
					self.actionBody.datatype = $("#data_type").val();
					self.actionBody.url = url.val();
					self.dashboardListener.dashboardURL = url.val();
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
					self.actionBody.autorefresh = ($('#timeout').is(':checked') && $('#timeoutval').val().trim() !== "" ? $('#timeoutval').val().trim() : null);
					self.actionBody.starttime = '';
					self.actionBody.endtime = '';
			
					self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "addwidget"), function(response) {
						self.currentwidgetid = response.data;
						
						self.actionBody = {};
						
						self.actionBody.query = query_add.val();
						self.dashboardListener.query = query_add.val();
						
						self.actionBody.applicationname = self.dashboardListener.currentappname;
						self.actionBody.dashboardname = self.dashboardListener.dashboardname;
						self.actionBody.url = self.dashboardListener.dashboardURL;
						self.actionBody.widgetname = nameofwid.val();
						
						self.getWidgetDataInfo(response.data, {name : nameofwid.val()});
						$('#add_widget').hide();
						console.info(commonVariables.api.localVal.getJson(self.currentwidgetid));
					
						$('.demo-container').each(function() {
							if(!$(this).hasClass('cssforchart')) {
								$(this).addClass('cssforchart');
							}
						});
						$('.noc_view').show();	 
						$('.noc_view').css('height','290px');	
					});
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