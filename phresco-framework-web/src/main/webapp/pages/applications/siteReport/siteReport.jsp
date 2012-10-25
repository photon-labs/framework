<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ include file="../progress.jsp" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="com.phresco.pom.site.Reports"%>

<script src="js/reader.js" ></script>

<%
	String appId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
    List<Reports> selectedReports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_REPORTS);
    String disabledStr = "";
    if (CollectionUtils.isEmpty(selectedReports)) {
        disabledStr = "disabled";
    } else {
        disabledStr = "";
    }
%>

<form id="formReportList" class="reportList">
	<div class="operation">
		<!-- Generate Report Button --> 
		 <input type="button" data-toggle="modal" href="#popupPage" name="generate" id="generate" class="btn btn-primary" additionalParam="getBasicParams();" value="<s:text name='lbl.site.report.generate'/>"<%= disabledStr %>/>
		 <a data-toggle="modal" href="#popupPage" class="btn btn-primary" id="configurePopup" additionalParam="getBasicParams();"><s:text name='lbl.configure'/></a>        
	</div>
	
	<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg" >
				<s:actionmessage />
			</div>
	</s:if>

	<div id="site_report">
		
	</div>
</form>

<script>
	/** To enable/disable the Generate button based on the site configured **/
	<%  if (CollectionUtils.isEmpty(selectedReports)){ %>
	        $("#generate").removeClass("btn-primary");  
	        $("#generate").addClass("disabled");
	<% } else { %>
	        $("#generate").addClass("btn-primary"); 
	        $("#generate").removeClass("disabled");
	<% } %>
	
	

    $(document).ready(function() {
    	yesnoPopup($('#configurePopup'),'siteConfigure', '<s:text name="header.site.report.configure"/>', 'createReportConfig','<s:text name="lbl.btn.ok"/>');
    	progressPopup($('#generate'), 'generateReport', '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.REQ_SITE_REPORT %>', '', '', getBasicParams());
    	hideLoadingIcon();
    	enableScreen();
    	checkForSiteReport();
    });
    
   	function checkForSiteReport() {
    	$("#site_report").empty();
    	 var params = getBasicParams();
    	loadContent('checkForSiteReport', '', $('#site_report'), params);
    } 
    
    function popupOnOk(obj) {
    	showParentPage();
    	var okUrl = $(obj).attr("id");
		if (okUrl === "createReportConfig") {
			$('input:checkbox[value="maven-project-info-reports-plugin"]').prop('checked', true);
		    $('input:checkbox[value="index"]').removeAttr('disabled', true);
		    var params = getBasicParams();
		    loadContent('createReportConfig',$('#formConfigureList'), $('#subcontainer'), params);
		}
	}
    
   	function popupClose(closeUrl) {
    	showParentPage();
		if (closeUrl === "generateReport") {
		    var params = getBasicParams();
		    loadContent('veiwSiteReport',$('#formConfigureList'), $('#subcontainer'), params);
		}
	}
    
</script>