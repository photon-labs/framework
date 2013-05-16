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

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="com.phresco.pom.site.Reports"%>
<%@ page import="com.phresco.pom.site.ReportCategories"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<script src="js/reader.js" ></script>

<%
	String appId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
    List<Reports> selectedReports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_REPORTS);
	String requestIp = (String) request.getAttribute(FrameworkConstants.REQ_REQUEST_IP);
	String showIcons = (String) session.getAttribute(requestIp);
    String disabledStr = "";
    if (CollectionUtils.isNotEmpty(selectedReports)) {
	    for (Reports reports : selectedReports) {
    		if (FrameworkConstants.REQ_SITE_SLECTD_REPORTSCATEGORIES.equals(reports.getArtifactId())) {
   				 disabledStr = "";
%>
				<script type="text/javascript">
					$("#generate").addClass("btn-primary"); 
		        	$("#generate").removeClass("disabled");
				</script>
		    		
<% 			break;	
			} else {
   				disabledStr = "disabled";
%>
				<script type="text/javascript">
					$("#generate").removeClass("btn-primary");
			        $("#generate").addClass("disabled");
				</script>
<%
    		}
		}
    } else {
    	disabledStr = "disabled";
    }

    Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
%>

<form id="formReportList" class="reportList">
	<div class="operation">
		<!-- Generate Report Button --> 
		 <input type="button" name="generate" id="generate" class="btn btn-primary" additionalParam="getBasicParams();" value="<s:text name='lbl.site.report.generate'/>"<%= disabledStr %>/>
		 <a class="btn btn-primary" id="configurePopup" additionalParam="getBasicParams();"><s:text name='lbl.configure'/></a>        
	</div>
	
	<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg" >
				<s:actionmessage />
			</div>
	</s:if>

	<div id="site_report" class="site_report">
		
	</div>
</form>

<script>
	// To enable/disable the Generate button based on the site configured
	<%
		if (permissions != null && !permissions.canManageMavenReports()) {
	%>
			$("#generate").removeClass("btn-primary").addClass("btn-disabled");
	<%
		} else {
			if (CollectionUtils.isEmpty(selectedReports)) {
	%>
		        $("#generate").removeClass("btn-primary").addClass("btn-disabled");
	<%
			} else {
	%>
		        $("#generate").removeClass("btn-disabled").addClass("btn-primary");
	<%
			}
		}
	%>
	
    $(document).ready(function() {
    	
    	<% if (CollectionUtils.isEmpty(selectedReports)) { %>
		    	$("#generate").removeClass("btn-primary");
		        $("#generate").addClass("disabled");
    	<% } %>
    	$('#configurePopup').click(function() {
    		hidePopuploadingIcon();
    		yesnoPopup('siteConfigure', '<s:text name="header.site.report.configure"/>', 'createReportConfig', '<s:text name="lbl.btn.ok"/>');
    	});
    	
    	$('#generate').click(function() {
    		progressPopup('generateReport', '<%= appId %>', '<%= FrameworkConstants.REQ_SITE_REPORT %>', '', '', getBasicParams(), '', '', '<%= showIcons %>');
    	});

    	hideLoadingIcon();
    	enableScreen();
    	checkForSiteReport();
    });
    
   	function checkForSiteReport() {
    	$("#site_report").empty();
    	 var params = getBasicParams();
    	loadContent('checkForSiteReport', '', $('#site_report'), params, '', true);
    } 
    
    function popupOnOk(obj) {
    	var okUrl = $(obj).attr("id");
		if (okUrl === "createReportConfig") {
			$('input:checkbox[value="maven-project-info-reports-plugin"]').prop('checked', true);
		    $('input:checkbox[value="index"]').removeAttr('disabled', true);
		    var params = getBasicParams();
			$("#popupPage").modal('hide');//To hide popup
		    loadContent('createReportConfig',$('#formConfigureList'), $('#subcontainer'), params, '', true);
		}
	}
    
   	function popupOnClose(obj) {
   		var closeUrl = $(obj).attr("id");
    	showParentPage();
		if (closeUrl === "generateReport") {
 		    var params = getBasicParams();
		    loadContent('veiwSiteReport', $('#formReportList'), $('#subcontainer'), params, '', true);
		}
	}
</script>