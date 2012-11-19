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

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.util.Constants"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%@ include file="progress.jsp" %>

<script src="js/reader.js" ></script>

 <%
	ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
	String appId = (String) applicationInfo.getId();
	List<String> sonarTechReports = (List<String>) request.getAttribute("sonarTechReports");
	String sonarError = "error";
	sonarError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	String disabledStr = "";
	if (StringUtils.isNotEmpty(sonarError)) {
        disabledStr = "disabled";
    }
	String checkIphone =  (String) request.getAttribute("checkIphone");
%>  

<form id="code" class="codeList">
	<div class="operation">
        <input type="button" id="codeValidatePopup" class="btn btn-primary" <%= disabledStr %> style=" float: left;" value="<s:text name='lbl.validate'/>" />
           
        <strong id="validateType" class="validateType"><s:text name="lbl.sonar.report"/></strong>&nbsp;
        <select id="validateAgainst" name="validateAgainst">
            <%
                if (CollectionUtils.isNotEmpty(sonarTechReports)) {
                	for (String sonarTechReport : sonarTechReports ) {
            %>
                   <option value="<%= sonarTechReport %>" ><%= sonarTechReport %></option>
            <%
                	}
                } else { %>
               	   <option value="src" ><s:text name="lbl.validateAgainst.source"/></option>
            <%   
                }
            %>
            <option value="functional" ><s:text name="lbl.validateAgainst.functionalTest"/></option>
		</select>
	</div>
	
	 <% if (StringUtils.isNotEmpty(sonarError)) { %>
		<div class="alert alert-block sonarWarning">
            <img id="warning_icon" src="images/icons/warning_icon.png" />
            <s:label cssClass="sonarLabelWarn" key="sonar.not.started" />
        </div>
    <% } %>
</form> 
<div id="sonar_report" class="sonar_report">

</div>

<script>
$('.control-group').addClass("valReportLbl");
    $(document).ready(function() {
    	$('#codeValidatePopup').click(function() {
    		validateDynamicParam('showCodeValidatePopup', '<s:text name="popup.hdr.code.validate"/>', 'codeValidate','<s:text name="lbl.validate"/>', '', '<%= Constants.PHASE_VALIDATE_CODE %>');
    	});

    	// To enable/disable the validate button based on the sonar startup
    	<% if (StringUtils.isNotEmpty(sonarError)) { %>
	    	$("#codeValidatePopup").removeClass("btn-primary"); 
	        $("#codeValidatePopup").addClass("btn-disabled");
    	<% } else { %>
	    	$("#codeValidatePopup").addClass("btn-primary"); 
	        $("#codeValidatePopup").removeClass("btn-disabled");
	        sonarReport();
    	<% } %>
    	
		$('#validateAgainst').change(function() {
			sonarReport();
  		});
  
		$('#closeGenTest, #closeGenerateTest').click(function() {
 			closePopup();
 			sonarReport();
 			$('#popup_div').empty();
  		});
    });
    
    function popupOnOk(obj) {
    	var okUrl = $(obj).attr("id");
        var params = getBasicParams();
        progressPopupAsSecPopup(okUrl, '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.REQ_CODE %>', $("#generateBuildForm"), params);
    }
    
    function sonarReport() {
        $("#sonar_report").empty();
        var reportValue = $('#validateAgainst').val();
        var params = getBasicParams() + '&';
        params = params.concat("validateAgainst=");
        params = params.concat(reportValue);
        loadContent('check', $('#code'), $('#sonar_report'), params);
    }
    
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
</script>