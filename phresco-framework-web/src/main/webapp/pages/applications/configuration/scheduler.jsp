<%--

    Framework Web Archive

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<%
String[] weekDays = {"", "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
String[] months = {"", "January","February","March","April","May","June","July","August","September","October","November","December"};
String schedulerKey = (String) request.getAttribute(FrameworkConstants.REQ_SCHEDULER_KEY);
%>
  
  <form id="callCron" style="text-align: center; margin-top: 20px;">
	<div class="control-group">
		<label class="control-label labelbold popupLbl"> <s:text name='lbl.schedule' /> </label>
		<div class="controls" style="padding-top: 5px;">
			<input id="scheduleDaily" type="radio" name="schedule" value="Daily" onChange="javascript:show('Daily'); " checked/>&nbsp;<s:text name="lbl.daily" />
			<input id="scheduleDaily" type="radio" name="schedule" value="Weekly" onChange="javascript:show('Weekly');"/>&nbsp;<s:text name="lbl.weekly" />
			<input id="scheduleDaily" type="radio" name="schedule" value="Monthly" onChange="javascript:show('Monthly');"/>&nbsp;<s:text name="lbl.monthly" />
		</div>
	</div>

	<div class="clearfix">

		<div id='Daily' style="text-align: center; margin-bottom: 5px;">
			<!-- class="schedulerWidth" -->
			<div>
				<s:text name="lbl.every" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="lbl.hours" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="lbl.minutes" />
			</div>
			<div class="dailyInnerDiv">

				<s:text name="lbl.At" />
				&nbsp;&nbsp; <input type="checkbox" id="daily_every"
					name="daily_every" onChange="javascript:show('Daily');">&nbsp;&nbsp;

				<select id="daily_hour" name="daily_hour"
					onChange="javascript:show('Daily');" class="schedulerSelectWidth">
					<option value="*">*</option>
					<% for (int i = 1; i < 24; i++) { %>
					<option value="<%= i %>">
						<%= i %>
					</option>
					<% } %>
				</select>&nbsp;&nbsp; <select id="daily_minute" name="daily_minute"
					onChange="javascript:show('Daily');" class="schedulerSelectWidth">
					<option value="*">*</option>
					<%  for (int i = 1; i < 60; i++) { %>
					<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>

			</div>
		</div>

		<div id='Weekly' style="text-align: center; margin-bottom: 5px;">
			<!-- class="schedulerWidth" -->

			<select id="weekly_week" name="weekly_week" multiple
				onChange="javascript:show('Weekly');"
				class="schedulerDay alignVertical">
				<option value="*" selected>*</option>
				<% for(int i = 1; i < weekDays.length; i++){ %>
				<option value="<%= i %>"><%= weekDays[i] %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.smal.at" />
			&nbsp; <select id="weekly_hours" name="weekly_hours"
				onChange="javascript:show('Weekly');"
				class="schedulerSelectWidth alignVertical">
				<option value="*" selected>*</option>
				<% for (int i = 1; i < 24; i++) { %>
				<option value="<%= i %>"><%= i %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.hour" />
			&nbsp; <select id="weekly_minute" name="weekly_minute"
				onChange="javascript:show('Weekly');"
				class="schedulerSelectWidth alignVertical">
				<option value="*" selected>*</option>
				<% for (int i = 1; i < 60; i++) { %>
				<option value="<%= i %>"><%= i %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.minute" />
		</div>

		<div id='Monthly' style="text-align: center; margin-bottom: 5px;">
			<!-- class="schedulerWidth" -->
			<s:text name="lbl.every" />

			<select id="monthly_day" name="monthly_day"
				onChange="javascript:show('Monthly');"
				class="schedulerSelectWidth alignVertical">
				<option value="*" selected>*</option>
				<% for (int i = 1; i <= 31; i++) { %>
				<option value="<%= i %>"><%= i %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.of" />
			&nbsp; <select id="monthly_month" name="monthly_month" multiple
				onChange="javascript:show('Monthly');"
				class="schedulerDay alignVertical">
				<option value="*" selected>*</option>
				<% for(int i = 1; i < months.length; i++){  %>
				<option value="<%= i %>"><%= months[i] %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.smal.at" />
			&nbsp; <select id="monthly_hour" name="monthly_hour"
				onChange="javascript:show('Monthly');"
				class="schedulerSelectWidth alignVertical">
				<option value="*" selected>*</option>
				<%  for (int i = 1; i < 24; i++) { %>
				<option value="<%= i %>"><%= i %></option>
				<% } %>
			</select>&nbsp;
			<s:text name="lbl.hour" />
			&nbsp; <select id="monthly_minute" name="monthly_minute"
				onChange="javascript:show('Monthly');"
				class="schedulerSelectWidth alignVertical">
				<option value="*" selected>*</option>
				<% for (int i = 1; i < 60; i++) {  %>
				<option value="<%= i %>"><%= i %></option>
				<% } %>
			</select> &nbsp;
			<s:text name="lbl.minute" />

		</div>
		
		<div class="control-group">
			<label class="control-label labelbold popupLbl"> <s:text name='lbl.cron.expression' /> </label>
			<div class="controls" id="cronValidation"></div>
		</div>
		
	</div>
</form>

<script type="text/javascript">
	var schedulerKey = '<%= schedulerKey %>';
	var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	loadSchedule(selectedSchedule);
	$(document).ready(function() {
		
		hidePopuploadingIcon();
		$("input:radio[name=schedule]").click(function() {
		    var selectedSchedule = $(this).val();
		    loadSchedule(selectedSchedule);
		});
		
		show(selectedSchedule);
		
		$('#Copy').click(function(){
			$('#popupPage').modal('hide');
		});
	});
	
	function loadSchedule(selectedSchedule) {
		hideAllSchedule();
		$("#" + selectedSchedule).show();
	}
	
	function hideAllSchedule() {
		$("#Daily").hide();
		$("#Weekly").hide();
		$("#Monthly").hide();
	}

	function show(ids) {
        var buttonObj = window.document.getElementById("enableButton");

        if(ids == "Daily") {
            var dailyEveryObj = window.document.getElementById("daily_every");
            var dailyHourObj = window.document.getElementById("daily_hour");
            var hours = dailyHourObj.options[dailyHourObj.selectedIndex].value;
            var dailyMinuteObj = window.document.getElementById("daily_minute");
            var minutes = dailyMinuteObj.options[dailyMinuteObj.selectedIndex].value;
    		var params = "cronBy=";
			params = params.concat("Daily");
			params = params.concat("&hours=");
			params = params.concat(hours);
			params = params.concat("&minutes=");
			params = params.concat(minutes);
			params = params.concat("&every=");
			params = params.concat(dailyEveryObj.checked);
			cronValidationLoad(params);
        } else if(ids == "Weekly") {

        	var weeklyHourObj = window.document.getElementById("weekly_hours");
            var hours = weeklyHourObj.options[weeklyHourObj.selectedIndex].value;

            var weeklyMinuteObj = window.document.getElementById("weekly_minute");
            var minutes = weeklyMinuteObj.options[weeklyMinuteObj.selectedIndex].value;

            var weekObj = window.document.getElementById("weekly_week");
            var week;
            var count = 0;
            
            if (weekObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < weekObj.options.length; i++){

                    if(weekObj.options[i].selected){
                        if (count == 0) {
                            week = weekObj.options[i].value;
                        } else {
                           week += "," + weekObj.options[i].value;
                        }

                        count++;
                    }
                }
	        	var params = "cronBy=";
				params = params.concat("Weekly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&week=");
				params = params.concat(week);
				cronValidationLoad(params);
        		
            }

      } else if(ids == "Monthly") {
            
    	  var monthlyDayObj = window.document.getElementById("monthly_day");
            var day = monthlyDayObj.options[monthlyDayObj.selectedIndex].value;

            var monthlyHourObj = window.document.getElementById("monthly_hour");
            var hours = monthlyHourObj.options[monthlyHourObj.selectedIndex].value;

            var monthlyMinuteObj = window.document.getElementById("monthly_minute");
            var minutes = monthlyMinuteObj.options[monthlyMinuteObj.selectedIndex].value;

            var monthObj = window.document.getElementById("monthly_month");
            var month;
            var count = 0;
            if (monthObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < monthObj.options.length; i++){

                    if(monthObj.options[i].selected){
                        if (count == 0) {
                            month = monthObj.options[i].value;
                        } else {
                           month += "," + monthObj.options[i].value;
                        }

                        count++;
                    }
                }
        		var params = "cronBy=";
				params = params.concat("Monthly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&month=");
				params = params.concat(month);
				params = params.concat("&day=");
				params = params.concat(day);
				cronValidationLoad(params);
            }

        }
    }
    
    function cronValidationLoad(additionalParams) {
		var params = getBasicParams();
		params = params.concat("&");
		params = params.concat(additionalParams);
    	loadContent('cronValidation','', $('#cronValidation'), params, false, true);
    }
</script>