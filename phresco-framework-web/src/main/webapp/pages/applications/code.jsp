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

<%@page import="com.photon.phresco.framework.commons.FrameworkUtil"%>
<%@page import="org.antlr.stringtemplate.StringTemplate"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.photon.phresco.framework.api.ActionType"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>

<%@ include file="progress.jsp" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.commons.ApplicationsUtil"%>
<%@ page import="com.photon.phresco.framework.commons.PBXNativeTarget"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Name.Value"%>

<script src="js/reader.js" ></script>

 <%
	ApplicationInfo applicationInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APP_INFO);
	String appId = (String) applicationInfo.getId();
	String technology = (String)applicationInfo.getTechInfo().getVersion();
	String sonarError = (String)request.getAttribute(FrameworkConstants.REQ_ERROR);
// 	String sonarError = "error";
   	//xcode targets
   	//List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
   	
    String goal = (String) request.getAttribute(FrameworkConstants.REQ_GOAL);
    Parameter parameter = (Parameter) request.getAttribute("parameter");
    List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
	String disabledStr = "";
	boolean isIphoneTech = false;
	if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) {
		disabledStr = "disabled";
	}
	if (TechnologyTypes.IPHONES.contains(technology)) {
		isIphoneTech = true;
	}
%>  

<form id="code" class="codeList">
	<div class="operation">
        <input type="button" data-toggle="modal" href="#popupPage" id="codeValidatePopup" class="btn btn-primary" style=" float: left;" value="<s:text name='lbl.validate'/>" />
            <%
	            if (parameter != null) {
	                    Boolean mandatory = false;
	                    String lableTxt = "";
	                    String labelClass = "";
	                    if (Boolean.parseBoolean(parameter.getRequired())) {
	                        mandatory = true;
	                    }
	                    
	                    if (FrameworkConstants.TYPE_LIST.equalsIgnoreCase(parameter.getType()) && parameter.getPossibleValues() != null) { //load select list box
	                      //To construct select box element if type is list and if possible value exists
	                        List<com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value> psblValues = parameter.getPossibleValues().getValue();
	                        List<String> selectedValList = Arrays.asList(parameter.getValue().split(FrameworkConstants.CSV_PATTERN));
	                        StringTemplate selectElmt = FrameworkUtil.constructSelectElement(mandatory, "Validate Report", "validateType" , "", "validateAgainst", "validateAgainst", psblValues, selectedValList, parameter.getMultiple());
	                        
               %>
                        <%= selectElmt %>
               <% }
	             }
	           %> 
    
<!-- 		<input type="button" class="btn disabled" name="validate" id="validate"  -->
<!-- 	         onclick="loadContent('code', $('#formCodeList'), $('#subcontainer'));"  -->
<%-- 		         value="<s:text name='lbl.validate'/>"/>&nbsp;&nbsp; --%>
		        <%--  <strong id="validateType" class="validateType"><s:text name="lbl.sonar.report"/></strong>&nbsp;
		         <select id="validateAgainst" name="validateAgainst">
		         <% if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5.equals(technology) || TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology) || TechnologyTypes.JAVA_WEBSERVICE.equals(technology)) { %>
					<option value="java" ><s:text name="lbl.tech.java"/></option>
					<option value="js" ><s:text name="lbl.tech.javascript"/></option>
					<option value="web" ><s:text name="lbl.tech.jsp"/></option>
					<% } else { %>
					<option value="src" ><s:text name="lbl.validateAgainst.source"/></option>
					<% } %>
					<option value="functional" ><s:text name="lbl.validateAgainst.functionalTest"/></option>
				</select>
		          --%>
	   <%--  <input id="validate" type="button" value="Validate" class="btn primary" <%= disabledStr %>>
		&nbsp;&nbsp;<strong id="lblType" class="noTestAvail"><s:text name="label.sonar.report"/></strong>&nbsp; --%>
		<%-- <select id="report" name="report">
		<% if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5.equals(technology) || TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology) || TechnologyTypes.JAVA_WEBSERVICE.equals(technology)) { %>	
			<option value="java" ><s:text name="label.tech.java"/></option>
			<option value="js" ><s:text name="label.tech.javascript"/></option>
			<option value="web" ><s:text name="label.tech.jsp"/></option>
		<% 
			} else if (isIphoneTech) {
				if (xcodeConfigs != null) {
					for (PBXNativeTarget xcodeConfig : xcodeConfigs) {
		%>
				<option value="<%= xcodeConfig.getName() %>"><%= xcodeConfig.getName() %></option>
		<%
					}
				}
			} else { 
		%>
				<option value="source" ><s:text name="label.validateAgainst.source"/></option>
		<% 	} %>
			<option value="functional" ><s:text name="label.funtional"/></option>
		</select> --%>
	</div>
	 <% if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) { %>
		<div class="alert alert-block sonarWarning">
		<!-- 	<i class="icon-warning-sign"></i> -->
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
    	yesnoPopup($('#codeValidatePopup'), 'showCodeValidatePopup', '<s:text name="popup.hdr.code.validate"/>', 'codeValidate','<s:text name="lbl.validate"/>');
		/** To enable/disable the validate button based on the sonar startup **/
    	<%-- <% if (!TechnologyTypes.IPHONES.contains(technology) && StringUtils.isNotEmpty(sonarError)) { %>
    			$("#validate").removeClass("primary");	
    			$("#validate").addClass("disabled");
    	<% } else { %>
    			$("#validate").addClass("primary");	
				  $("#validate").removeClass("disabled");
    	<% } %> --%>

    	
    	//changeStyle("code");
    	hideLoadingIcon();
    	sonarReport();
    	enableScreen();
    	
		$('#validate').click(function() {
			getCodeValidatePopUp();
  		});
		
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
    
    function progress() {
    	getCurrentCSS();
    	$('#loadingDiv').show();
    	$('#build-output').empty();
    	$('#build-output').html("Validating code...");
		$('#build-outputOuter').show().css("display","block");
		$(".wel_come").show().css("display","block");
		readerHandlerSubmit('progressValidate', '<%= appId %>', '<%= FrameworkConstants.REQ_CODE %>', '', false, getBasicParams());
	}
    
    function sonarReport() {
        $("#sonar_report").empty();
        var reportValue = $('#validateAgainst').val();
        var params = getBasicParams() + '&';
        params = params.concat("validateAgainst=");
        params = params.concat(reportValue);
        loadContent('check', $('#code'), $('#sonar_report'), params);
    }
    
    function getCodeValidatePopUp() {
    	$('#popup_div').empty();
    	loadContent('getCodeValidatePopUp', '', $('#popup_div'));
    }
    
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
</script>