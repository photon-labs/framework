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

<%@ page import="com.photon.phresco.commons.model.LogInfo" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
	LogInfo log = (LogInfo)request.getAttribute(FrameworkConstants.REQ_LOG_REPORT); 
%>

<div class="hideContent" id="trace">
	<%= log.getTrace() %>
</div>

<form class="hideContent" id="logForm">
	<textarea name="userid"><%= log.getUserId() %></textarea>
	<textarea name="action"><%= log.getAction() %></textarea>
	<textarea name="trace"><%= log.getTrace() %></textarea>
	<textarea name="message"><%= log.getMessage() %></textarea>
</form>
	
<script>
	$('document').ready(function() {
		$('#popupTitle').html("Error Report"); // Title for the popup
		$('.hideClipBoardImage').show();
		$('.popupClose').hide(); //no need close button since yesno popup
		$(".popupOk").attr("value", "<s:text name="label.sent.report"/>");
		$('.popupOk, .popupCancel').show(); // show ok & cancel button
		$('.modal-body').html($('#trace').html());
		$('#popupPage').modal('show');
		$('.popupCancel').attr('id', "errorReport"); 
		hidePopuploadingIcon();
	});
	
	function popupOnOk(obj) {
		loadContent("sendReport", $("#logForm"), '', '', true, true);  
	}
	
	function successEvent(pageUrl, data) {
		if (pageUrl == "sendReport") {
			if (data.sendReportStatus != undefined && !isBlank(data.sendReportStatus) && data.sendReportStatus) {
				$("#successMsg").html(data.sendReportMsg);
				$(".popupOk").attr("disabled", true);
				$(".popupOk").removeClass("btn-primary");
			} 
		}
	}
	
	$('#clipboard').click(function() {
		copyToClipboard($('#popup_div').text());
	});
	
	/* $("#popupCancel, .close").click(function() {
		var params = getBasicParams(); 
		loadContent('applications','', $('#container'), params);
		$("#successMsg").empty();
		$(".popupOk").attr("disabled", false);
		$(".popupOk").addClass("btn-primary");
		$('.hideClipBoardImage').hide();
    }); */
	
	function popupOnCancel(obj) {
    	var url = $(obj).attr("id");
    	if (url === "errorReport") {
			var params = getBasicParams(); 
			loadContent('applications','', $('#container'), params);
			$("#successMsg").empty();
			$(".popupOk").attr("disabled", false);
			$(".popupOk").addClass("btn-primary");
			$('.hideClipBoardImage').hide();
    	}
	}
	
</script>