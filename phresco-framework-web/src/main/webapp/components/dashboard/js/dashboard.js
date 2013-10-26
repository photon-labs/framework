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
				
				// Radialize the colors - it should initialize only once since this block is here.
				Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
					return {
						radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
						stops: [
							[0, color],
							[1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
						]
					};
				});
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

			// Radialize the colors
			
			
			self.dashboardListener.graphAction(self.dashboardListener.getActionHeader(self.actionBody, "get"), function(response){
				$("#editprojecttitle").html("Edit - " +response.data.name);
				$.each(response.data.appInfos,function(index,value) {
					$(".appdirnamedropdown").append('<option id='+value.id+' code='+value.code+' appDirName='+value.appDirName+' value='+value.name+'>'+value.name+'</option>');	
				});

				var headervalue = $('#header').height();
				var titlenav = $('.title_nav').height();
				var footervalue = $('.footer_section').height();
				var resultvalue = headervalue + titlenav + footervalue + 65;
				$('.features_content_main').height($(window).height() - resultvalue);			
				
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
											self.dashboardListener.currentdashboardid = dashBkey;self.dashboardListener.dashboardURL = currentdashB.url;
											self.dashboardListener.dashboardusername = currentdashB.username;
											self.dashboardListener.dashboardpassword = currentdashB.password;	
											self.dashboardListener.dashboardname = currentdashB.dashboardname;
											
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
													
													self.dashboardListener.dashboardURL = currentdashB.url;
													self.dashboardListener.dashboardusername = currentdashB.username;
													self.dashboardListener.dashboardpassword = currentdashB.password;

													//self.dashboardListener.createwidgetTable(widgetKey,currentWidget, self.actionBody, true);
													self.dashboardListener.getWidgetDataInfo(widgetKey,currentWidget, self.actionBody, true);
													
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
						$(".code_report").hide();
					}	
					self.dashboardListener.dropdownclick();	
				});
				
				/*console.info('place holder........', $(".placeholder"));
				$(".placeholder").each(function() {
					console.info("action "+ JSON.stringfy(self.actionBody));
					console.info("place load height "+ $('#'+$(this).attr('id')), $('#'+$(this).attr('id')).height());
				});*/
				//var height = $(this).height();
				
				/*var resultvalue = 0;
				$('.features_content_main').prevAll().each(function() {
					var rv = $(this).height();
					resultvalue = resultvalue + rv;
				});*/
				$('.features_content_main').css('overflow','auto');
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
			self.dashboardListener.clickFunction();
			var flag=0, toappend, counter = 0, placeholderval;
				var count = $('.noc_view').length;
				if(count == 1){
					$('.noc_view').css('width','98%');
				} else if (count >=2) {
					$('.noc_view').css('width','48%');
				}	
			//$("select.xaxis").parent().parent().hide();
			//$("select.yaxis").parent().parent().hide();
			//$("select.percentval").parent().parent().hide();
			//$("select.legendval").parent().parent().hide();
			//$("select.baraxis").parent().parent().hide();
			$("#timeoutval_update").hide();
			$("#timeoutval").hide();
			//$("#tabforbar").hide();
			$("#dashlist").hide();
			$("#lineChartOpt").hide();
			
			$('#add_wid').click(function() {
				self.openccpl(this,'add_widget');
				self.dashboardListener.addwidgetflag = 1;
				$('.he-view').removeAttr('style');
				window.hoverFlag = 0;
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
				$('.he-view').removeAttr('style');
				window.hoverFlag = 0;
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
					//self.actionBody.url = url.val();
					self.dashboardListener.dashboardURL = url.val();
					self.dashboardListener.dashboardpassword = pass.val();
					self.dashboardListener.dashboardusername = user.val();
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
			
			
			$(".dyn_popup").hide();
			$(window).resize(function() {
				$(".dyn_popup").hide();
			});
		}
	});

	return Clazz.com.components.dashboard.js.Dashboard;
});