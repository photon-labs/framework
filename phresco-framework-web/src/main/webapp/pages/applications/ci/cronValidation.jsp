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
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.Date"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.model.CIJob" %>

<%
	String from = (String) request.getAttribute(FrameworkConstants.REQ_FROM);
%>

<input type="text" id="cronExpression" name="cronExpression" value="<%= (String)request.getAttribute(FrameworkConstants.REQ_CRON_EXPRESSION)%>">&nbsp; 
<a id="showPattern" href="#" onclick="patternPopUp('block');">
	<img src="images/icons/Help.png" />
</a>

<!-- Cron Expression Pattern starts -->	
<div id="Pattern" class="abtDialog">
	<div class="abt_div">
		<div id="testCaseDesc" class="">
               <table border="0" cellpadding="0" cellspacing="0" class="tbl" width="100%">
               	   <tr>
                       <td width="1%" nowrap><b id="SelectedSchedule" class="popup-label"></b></td>
                      <% if ("ci".equals(from)) { %>
                       		<td><b></b></td>
                       <% } %>	
                   </tr>
                   <tr class="popup-label">
              			<% if ("ci".equals(from)) { %>
                       		<td nowrap class="popup-label"><b><s:text name="lbl.name"/></b></td>
						<% } %>
                       <td  style="width: 100%;"  class="popup-label"><b><s:text name="label.date"/></b></td>
                   </tr>
                <% 	
                   	Date[] dates = (Date[])request.getAttribute(FrameworkConstants.REQ_CRON_DATES);
                   	if (dates != null) {
                    	String jobName = (String)request.getAttribute(FrameworkConstants.REQ_JOB_NAME);
	                    for (int i = 0; i < dates.length; i++) {
	                        Date date = dates[i]; 
                %>
                        <tr class="popup-label">
                        	<% if ("ci".equals(from)) { %>
                            	<td class="jobName popup-label" nowrap></td>
                            <% } %>	
                            <td class="popup-label"><%= date %><%= ((i + 1) == dates.length) ? "    .....</b>" : "" %></td>
                        </tr>
               	<%
                     	} 
                    }
                %>
               </table>
		</div>
	</div>
</div>
<!-- Cron Expression Pattern ends -->

<script type="text/javascript">
   $(document).ready(function() {
	    var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	    $('#SelectedSchedule').html(selectedSchedule + "&nbsp;Schedule");
	    var jobName = $("input:text[name=name]").val();
	    $('.jobName').html(jobName);
		
		$('#closePatternPopup, #closeDialog').click(function() {
			patternPopUp('none');
		});
	   	
   });
   
	function patternPopUp(enableProp) {
		//to hide configure popup
		$("#popupPage").modal('hide');

		//to show cron validation popup
		setTimeout(function() {
			$("#additionalPopup").modal('show');
		}, 600);

		//to hide unwanted controls & fill appropriate label
		makeCronValidationPopUp();

		//to fill cron validation popup body content
		$('#additional_popup_body').html($("#Pattern").find(".abt_div").html());
	}

	function makeCronValidationPopUp() {
		$("#compressNameLbl").hide();
		$("#compressName").hide();
		$(".add_errorMsg").empty();
		$(".add_popupOk").hide();
		$("#browseSelectedLocation").hide();
		$("#add_popupCancel").html('Ok');
		$("#add_popupCancel").attr('okurl', '');
		$("#additional_popupTitle").text('<s:text name="lbl.cronvalidate.title"/>');
	}
</script>