define([], function() {

	Clazz.createPackage("com.components.croneExpression.js.listener");

	Clazz.com.components.croneExpression.js.listener.croneExpressionListener = Clazz.extend(Clazz.WidgetWithTemplate, {
		basePlaceholder :  window.commonVariables.basePlaceholder,
		
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			var self = this;
		},
		
		openccvar : function() {
				var self = this;
				$('.content_main').addClass('z_index_ci');
				
				var clicked = $("#cronepassword");
				var target = $("#crone_triggered");
				var twowidth = window.innerWidth/1.5;
			
				if (clicked.offset().left < twowidth) {	
					$(target).toggle();
					var a = target.height()/2;
					var b = clicked.height()/2;
					var t=clicked.offset().top + (b+12) - (a+12) + 50 ;
					var l=clicked.offset().left + clicked.width() - 44;
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleleft').removeClass('speakstyleright');
				}
				else {
					$(target).toggle();
					var t=clicked.offset().top - target.height()/2;
					var l=clicked.offset().left - (target.width()+ 15);
					$(target).offset({
						top: t,
						left: l
					});
					
					$(target).addClass('speakstyleright').removeClass('speakstyleleft');
			
				}
				self.closeAll(target);
				
			},
		  		
		closeAll : function(placeId) {
			
			$(document).keyup(function(e) {
				if(e.which === 27){
					$(placeId).hide();
				}
			});
			
			$('.dyn_popup_close').click( function() {
				$(placeId).hide();
				$("#cron_expression").hide();
			});
				
		},
		
		rendercrontemp : function(param) {
			var self = this;
			var str = '<table class="table table-striped table_border table-bordered border_div" cellspacing="0" cellpadding="0" border="0"><thead><tr><th colspan="4">Schedule</th></tr></thead><tbody><tr id="scheduleExpression"><td colspan="4"><label><input type="radio" name="scheduleOption" value="Daily" checked>Daily</label><label><input type="radio" name="scheduleOption" value="Weekly">Weekly</label><label><input type="radio" name="scheduleOption" value="Monthly">Monthly</label></td></tr></tbody></table><table class="table table-striped table_border table-bordered border_div" cellspacing="0" cellpadding="0" border="0"><thead><tr><th colspan="2">Crone Expression</th></tr></thead><tbody><tr><td><input id="CroneExpressionValue" type="text"><a href="javascript:;" id="cronepassword"> <img src="themes/default/images/Phresco/question_mark.png"></a></td></tr></tbody></table><div class="flt_right"><input class="btn btn_style" type="button" name="croneOk" value="Ok"><input class="btn btn_style dyn_popup_close" type="button" value="Close"></div></div><div id="crone_triggered" class="dyn_popup" style="display:none"><span>Your Schedule will be triggered using the following pattern<br>Daily Schedule</span><table class="table table-striped table_border table-bordered border_div" border="0" cellpadding="0" cellspacing="0"><thead><tr><th>Date</th></tr></thead><tbody name="scheduleDates"></tbody></table><div class="flt_right"><input name="dyn_popupcon_close" class="btn btn_style dyn_popupcon_close" type="button" value="Close"></div>';
			$(param).html('');
			$(param).append(str);
			
			$("#cronepassword").click(function() {
				self.openccvar();
			});
			
			$("input[name=dyn_popupcon_close]").click(function() {
				$(this).parent().parent().hide();
			});
			
			$(".dyn_popup_close").click(function() {
				$(this).parent().parent().hide();
			});
			
			self.currentEvent($('input[name=scheduleOption]:checked').val());
			$('input[name=scheduleOption]').bind('click', function() {
				$(this).attr('checked', true);
				self.currentEvent($(this).val());
			});
		},
		
		currentEvent : function(value) {
			var self=this, dailySchedule, weeklySchedule, monthlySchedule, toAppend;
			dailySchedule = '<tr id="schedule_daily" class="schedule_date"><td>Every At<input type="checkbox" name="everyAt"></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			weeklySchedule = '<tr id="schedule_weekly" class="schedule_date"><td><select name="weeks" class="selectpicker" multiple data-selected-text-format="count>2">'+self.weeks()+'</select><span>Weeks</span> <span>at</span></td><td><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			monthlySchedule = '<tr id="schedule_monthly" class="schedule_date"><td><span>Every</span><select name="days" class="selectpicker">'+self.days()+'</select></td><td><span>of</span><select name="months" class="selectpicker" multiple data-selected-text-format="count>2">'+self.months()+'</select><span>Months</span></td><td><span>at</span><select name="hours" class="selectpicker">'+self.hours()+'</select><span>Hrs</span></td> <td><select name="minutes" class="selectpicker">'+self.minutes()+'</select><span>Mins</span></td></tr>';
			$('.schedule_date').remove();
				toAppend = $('tr #scheduleExpression:last');
			if (value === 'Daily') {
				$(dailySchedule).insertAfter(toAppend);
			} else if (value === 'Weekly') {
				$(weeklySchedule).insertAfter(toAppend);
			} else {
				$(monthlySchedule).insertAfter(toAppend);
			}
			self.multiselect();
			self.cronExpressionValues(value);
		},
		
		cronExpressionValues : function (value) {
			var self=this, croneJson = {};
			if (value === 'Daily') {
				croneJson.cronBy = value;
				croneJson.every = $('input[name=everyAt]').is(':checked');
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('input[name=everyAt], select[name=hours], select[name=minutes]').bind('change', function(){
					croneJson.every = $('input[name=everyAt]').is(':checked');
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});

			} else if (value === 'Weekly') {
				var weeks = [], val;
				croneJson.cronBy = value;
				if ($('select[name=weeks]').val() === null) {
					val = '*';
				}
				weeks.push(val);
				croneJson.week = weeks;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=weeks], select[name=hours], select[name=minutes]').bind('change', function(){
					var weeks = [];
					if ($('select[name=weeks]').val() === null) {
						val = '*';
						weeks.push(val);
						croneJson.week = weeks;
					} else {
						croneJson.week = $('select[name=weeks]').val();
					}
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			} else {
				var month = [], val;
				croneJson.cronBy = value;
				if ($('select[name=months]').val() === null) {
					val = '*';
				}
				croneJson.day = $('select[name=days]').val();
				month.push(val);
				croneJson.month = month;
				croneJson.hours = $('select[name=hours]').val();
				croneJson.minutes = $('select[name=minutes]').val();
				self.cronExpressionLoad(croneJson);
				$('select[name=days], select[name=months], select[name=hours], select[name=minutes]').bind('change', function(){
					var months = [];
					if ($('select[name=months]').val() === null) {
						val = '*';
						months.push(val);
						croneJson.month = months;
					} else {
						croneJson.month = $('select[name=months]').val();
					}
					croneJson.day = $('select[name=days]').val();
					croneJson.hours = $('select[name=hours]').val();
					croneJson.minutes = $('select[name=minutes]').val();
					self.cronExpressionLoad(croneJson);
				});
			}
		},
		
		cronExpressionLoad : function (croneJson) {
			var self=this, i, tr;
			self.configRequestBody = croneJson;
			self.cronelist(self.getRequestHeader(self.configRequestBody, "cronExpression", ''), function(response) {
				$("#CroneExpressionValue").val(response.data.cronExpression);
				$('tbody[name=scheduleDates]').html('');
				if (response.data.dates !== null) {
					for (i=0; i<response.data.dates.length; i++) {
						tr += '<tr><td>'+response.data.dates[i]+'</td></tr>';
					}
				}
				$('tbody[name=scheduleDates]').append(tr);
			});
			
			$("input[name=croneOk]").click(function(){
				$("#cron_expression").hide();
				$("input[name=scheduler]").val($("#CroneExpressionValue").val());
			});
		},
		
		cronelist : function(header, callback) {
			var self = this;
			try {
				self.showpopupLoad();
				if (self.bcheck === false) {
					//this.loadingScreen.showLoading();
				}
				commonVariables.api.ajaxRequest(header,
					function(response) {
						self.hidePopupLoad();
						if (response !== null) {
							callback(response);
							//self.loadingScreen.removeLoading();
						} else {
							self.hidePopupLoad();
							//self.loadingScreen.removeLoading();
							callback({ "status" : "service failure"});
						}

					},

					function(textStatus) {
						self.hidePopupLoad();
						if (self.bcheck === false) {
							//self.loadingScreen.removeLoading();
						}
					}
				);
			} catch(exception) {
				self.hidePopupLoad();
				if (self.bcheck === false) {
					//self.loadingScreen.removeLoading();
				}
			}

		},
		
		getRequestHeader : function(configRequestBody, action, deleteEnv) {
			var self = this, header, appDirName;
			var customerId = self.getCustomer();
			customerId = (customerId === "") ? "photon" : customerId;
			self.bcheck = false;
			header = {
				contentType: "application/json",
				dataType: "json",
				requestMethod : "GET",
				webserviceurl : ''
			};
			
			self.bcheck = true;
			header.requestMethod = "POST";
			header.requestPostBody = JSON.stringify(configRequestBody);
			header.webserviceurl = commonVariables.webserviceurl+commonVariables.configuration+"/cronExpression";
			return header;
		},
		
		showpopupLoad : function(){
			$('.popuploading').show();
		},
		
		hidePopupLoad : function(){
			$('.popuploading').hide();
		},
		
		hours : function() {
			var self=this, option='', i;
				option = '<option>*</option>';
			for(i=0; i<24; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		minutes : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=0; i<60; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		days : function() {
			var self=this, option='', i;
			option = '<option>*</option>';
			for(i=1; i<32; i++) {
				option += '<option value='+i+'>'+i+'</option>';
			}
			return option;
		},
		
		weeks : function () {
			var self=this, option='', i, weeks = ['*', 'Sunday', 'Monday', 'Tuesday', 'Wendesday', 'Thursday', 'Friday', 'Saturday'];
			
			for(i=0; i<weeks.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+weeks[i]+'</option>';
			}
			return option;
		},
		
		months : function () {
			var self=this, option='', i, months = ["*","January","February","March","April","May","June","July","August","September","October","November","December"];
			
			for(i=0; i<months.length; i++) {
				var val = (i === 0) ? "*" : i;
				option += '<option value='+val+'>'+months[i]+'</option>';
			}
			return option;
		}
		
	});

	return Clazz.com.components.croneExpression.js.listener.croneExpressionListener;
});