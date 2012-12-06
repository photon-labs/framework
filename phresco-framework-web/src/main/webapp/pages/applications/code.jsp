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
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value"%>

<script src="js/reader.js" ></script>

 <%
	ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
	String appId = (String) appInfo.getId();
	List<Value> techReports = (List<Value>) request.getAttribute(FrameworkConstants.REQ_VALUES);
	String sonarError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	String clangReport =  (String) request.getAttribute(FrameworkConstants.CLANG_REPORT);
	String disabledStr = "";
	// when this is iphone tech there will be a path in clangReport request
	if (StringUtils.isEmpty(clangReport) && StringUtils.isNotEmpty(sonarError)) {
        //disabledStr = "disabled";
    }
%>  

<form id="code" class="codeList">
	<div class="operation">
        <input type="button" id="codeValidatePopup" class="btn btn-primary" <%= disabledStr %> style=" float: left;" value="<s:text name='lbl.validate'/>" />
           
        <strong id="validateType" class="validateType"><s:text name="lbl.sonar.report"/></strong>&nbsp;
        <select id="validateAgainst" name="validateAgainst">
        
            <%
                if (CollectionUtils.isNotEmpty(techReports)) {
                	for (Value value : techReports) {
            %>
                   <option value="<%= value.getKey() %>" ><%= value.getValue() %></option>
            <%
                	}
                } 
            %>
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
    	showLoadingIcon();
    	$('#codeValidatePopup').click(function() {
    		validateDynamicParam('showCodeValidatePopup', '<s:text name="popup.hdr.code.validate"/>', 'codeValidate','<s:text name="lbl.validate"/>', '', '<%= Constants.PHASE_VALIDATE_CODE %>');
    	});

    	// To enable/disable the validate button based on the sonar startup
    	<% if (StringUtils.isNotEmpty(sonarError)) { %>
	    	$("#codeValidatePopup").removeClass("btn-primary"); 
	        $("#codeValidatePopup").addClass("btn-disabled");
	        hideLoadingIcon();
    	<% } else { %>
	    	$("#codeValidatePopup").addClass("btn-primary"); 
	        $("#codeValidatePopup").removeClass("btn-disabled");
	        sonarReport();
    	<% } %>
    	
<%--     	<% if (StringUtils.isNotEmpty(clangReport)) { %> --%>
//     		$("#validateType").html("<s:text name='lbl.target'/>");
<%--     	<% } %> --%>
    	
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
    	$("#popupPage").modal('hide');//To hide popup
        progressPopupAsSecPopup(okUrl, '<%= appId %>', '<%= FrameworkConstants.REQ_CODE %>', $("#generateBuildForm"), params);
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
	
	function popupOnClose(obj) {
		var closeUrl = $(obj).attr("id");
		loadContent("code", '', $("#subcontainer"), getBasicParams());
	}
</script>