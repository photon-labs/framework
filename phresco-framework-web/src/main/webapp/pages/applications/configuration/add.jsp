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
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.model.Server" %>
<%@ page import="com.photon.phresco.model.Database"%>
<%@ page import="com.photon.phresco.model.WebService"%>
<%@ page import="com.photon.phresco.model.Technology"%>

<%@ include file="../../userInfoDetails.jsp" %>

<%
	String selectedStr = "";
	String name = "";
	String description = "";
	String selectedType = "";
	String fromPage = "";
	String envName = "";
	String desc = "";
    boolean isDefault = false;
	
    String currentEnv = (String) request.getAttribute(FrameworkConstants.REQ_CURRENTENV);
	SettingsInfo configInfo = (SettingsInfo) request.getAttribute(FrameworkConstants.REQ_CONFIG_INFO);
	request.setAttribute(FrameworkConstants.REQ_CONFIG_INFO , configInfo);
	
	if (configInfo != null) {
		if (StringUtils.isNotEmpty(configInfo.getEnvName())) {
			envName = configInfo.getEnvName();
		}
		if (StringUtils.isNotEmpty(configInfo.getName())) {
			name = configInfo.getName();
		}
		if (StringUtils.isNotEmpty(configInfo.getDescription())) {
			description = configInfo.getDescription();
		}
		if (StringUtils.isNotEmpty(configInfo.getType())) {
			selectedType = configInfo.getType();
		}
	}
	
	if (request.getAttribute(FrameworkConstants.REQ_FROM_PAGE) != null) {
		fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	}
		
	ProjectInfo projectInfo = (ProjectInfo)request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO);
	String projectCode = "";
	List<Server> projectInfoServers = null;
	List<Database> projectInfoDbs = null;
	List<WebService> projectInfoWebServices = null;
	boolean isEmailSuppoterd = false;
	if (projectInfo != null) {
		projectCode = projectInfo.getCode();
		Technology technology = projectInfo.getTechnology();
		projectInfoServers = technology.getServers();
		projectInfoDbs = technology.getDatabases();
		projectInfoWebServices = technology.getWebservices();
		isEmailSuppoterd = technology.isEmailSupported();
	}
	List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
	List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>)request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATES);
%>

<style>
h1 {margin-bottom: 0;}
</style>

<!--  Heading starts -->
<div class="add_configuration">

<%-- <%
	if (CollectionUtils.isEmpty(projectInfo.getTechnology().getServers()) && 
			CollectionUtils.isEmpty(projectInfo.getTechnology().getDatabases()) &&
			CollectionUtils.isEmpty(projectInfo.getTechnology().getWebservices()) &&
			!projectInfo.getTechnology().isEmailSupported()) {
%>
		<div class="alert-message block-message warning" style="margin-top: 4px;">
			<center><s:label key="configuration.type.info" cssClass="errorMsgLabel"/></center>
		</div>
<%		
	} else {
%> --%>
	
	    <% if (StringUtils.isEmpty(fromPage)) { %>
				<h1 class="sub_header"><s:text name="label.add.config"/></h1>
		<%
			} else {
		%>
				<h1 class="sub_header"><s:text name="label.edit.config"/></h1>
		<% } %>
	
	<!--  Heading ends -->

	<!--  Action Messages display starts -->
	<s:if test="hasActionMessages()">
		<div class="alert-message success"  id="successmsg">
			<s:actionmessage />
		</div>
	</s:if>
	<!--  Action Messages display ends -->
	 
	<!--  Form starts -->
	<form class="configurations_add_form" id="formConfigAdd" autocomplete="off">
	<div class="config_div">
	    <!--  Name starts -->
		<div class="clearfix" id="nameErrDiv">
			<label class="new-xlInput"><span class="red">*</span> <s:text name="label.name"/> </label>
			<div class="input new-input">
				<div class="typeFields">
					<input class="xlarge settings_text" id="xlInput" name="configName" type="text" maxlength="30" title="30 Characters only" value ="<%=name%>" 
					autofocus placeholder="<s:text name="label.name.config.placeholder"/>"/>
				</div>
				<div>
					<div class="lblDesc configSettingHelp-block" id="nameErrMsg">
					    
					</div>
				</div>
			</div>
	    </div>
	    <!--  Name ends -->

		<!--  Description starts -->
		<div class="clearfix">
			<s:label key="label.description" theme="simple" cssClass="new-xlInput"/>
			<div class="input new-input">
				<textarea  class="appinfo-desc xxlarge" maxlength="150" title="150 Characters only" class="xxlarge" id="textarea" 
					name="description" placeholder="<s:text name="label.description.config.placeholder"/>"><%= description %></textarea>
			</div>
		</div>
		<!--  Description ends -->
        
        <div class="clearfix" id="envDiv">
      		<label class="new-xlInput"><span class="red">* </span><s:text name="label.environment"/></label>
			<div class="input new-input">
           		<div class="typeFields">
					<select id="environments" name="environments" class="selectEqualWidth">
					<%
						for (Environment env : envs ) {
							envName = env.getName();
							desc = env.getDesc();
							isDefault = env.isDefaultEnv();
							if (isDefault) {
								selectedStr = "selected";
							} else {
								selectedStr = "";
							}
					%>
							<option value="<%= env.getName() %>" <%= selectedStr %>><%= env.getName() %></option>
					<% 
						} 
					%>
					</select>
				</div>
                        
				<div>
					<div class="lblDesc configSettingHelp-block" id="envErrMsg">
		                
			        </div>
		        </div>
			</div>
		</div>
        
		<!--  SettingTemplate starts -->
		<div class="clearfix" id="configTypeDiv">
			<label class="new-xlInput"><s:text name="label.type"/></label>
			<div class="input new-input">
				<div class="typeFields">
					<select id="configType" name="configType" class="selectEqualWidth">
					<%
						if (CollectionUtils.isNotEmpty(settingsTemplates)) {
							for (SettingsTemplate settingsTemplate : settingsTemplates) {
								String type = settingsTemplate.getType();
								if (selectedType != null  && type.equals(selectedType)) {
									selectedStr = "selected";
								} else {
									selectedStr = "";
								}
					%>
					<%			
								if ((Constants.SETTINGS_TEMPLATE_SERVER.equals(type) && CollectionUtils.isEmpty(projectInfoServers)) || 
										(Constants.SETTINGS_TEMPLATE_DB.equals(type) && CollectionUtils.isEmpty(projectInfoDbs)) || 
										(Constants.SETTINGS_TEMPLATE_WEBSERVICE.equals(type) && CollectionUtils.isEmpty(projectInfoWebServices)) || 
										(Constants.SETTINGS_TEMPLATE_EMAIL.equals(type) && !isEmailSuppoterd)) {
								} else {
					%>
									<option value="<%= type %>" <%= selectedStr %>><%= type %></option>
					<%
								}
							}
						}
					%>
					</select>
				</div>
				<div style="float: left; width: 58%;">
					<div class="lblDesc configSettingHelp-block" id="configTypeErrMsg"></div>
				</div>
			</div>
		</div>
		<!--  SettingTemplate ends -->

		<div id="type-child-container" class="settings_type_div"></div>
		</div>
	
	<!--  Submit and Cancel button starts -->
	<div class="clearfix">
		<label>&nbsp;</label>
		<div class="submit_input">
			<% if (StringUtils.isEmpty(fromPage)) { %>
				<input type="button" value="<s:text name="label.save"/>" id="save" class="primary btn">
			<% } else { %>
				<input type="button" value="<s:text name="label.update"/>" id="update" class="primary btn">
			<% } %>
			<a href="#" class="primary btn" id="cancelConfiguration"><s:text name="label.cancel"/></a>
		</div>
	</div>
	<!--  Submit and Cancel button ends -->
	
	<!-- Hidden Fields -->
	<input type="hidden" name="oldName" value="<%= name %>" />
	<input type="hidden" name="oldConfigType" value="<%= selectedType %>" />
	<input type="hidden" name="projectCode" value="<%= projectCode %>" />
            
	</form>
	<!--  Form ends -->
</div>
    
<script type="text/javascript">
	var fromPage = ""; 
	
	/** To remove all the environments except the selected environment **/
	if (<%= StringUtils.isNotEmpty(currentEnv) %>) {
		$('#environments').empty().append($("<option></option>").attr("value",'<%= currentEnv %>').attr("selected", "selected").text('<%= currentEnv %>'));
	}

	/* To check whether the divice is ipad or not */
	if (!isiPad()) {
		/* JQuery scroll bar */
		$(".config_div").scrollbars();
	}
	
	$(document).ready(function() {		
	    configurationsType();
	    
	    changeStyle("configuration");
	    
	    $('#configType').change(function() {
	        configurationsType('true');
	    });
	    
	    $("#cancelConfiguration").click(function() {
	    	performAction("configuration", $('#formConfigAdd'), $('#tabDiv'));
	    	showLoadingIcon($("#tabDiv")); // Loading Icon
	    });
		
		$('#save').click(function() {
			$("input[name=certificate]").prop("disabled", false);
			performAction("saveConfiguration", $('#formConfigAdd'), $('#tabDiv'));
	    });
		
		$('#update').click(function() {
			$("input[name=certificate]").prop("disabled", false);
			performAction("updateConfiguration", $('#formConfigAdd'), $('#tabDiv'));
	    });
		
		window.setTimeout(function () { document.getElementById('xlInput').focus(); }, 250);
	});
	
	function configurationsType(from) {
		performAction('configurationsType', $('#formConfigAdd'), '', true);
	}
	
	function successConfigurationsType(data) {
        $("#type-child-container").html(data);
        $("#appliestodiv").hide();
        if (fromPage != undefined) {
        	$("#Host").focus();
            $("#Protocol").focus();     	
        }
	}
	
	function validationError(data) {
		$(".clearfix").removeClass("error");
		$(".lblDesc").text("");
		if (data.nameError != null) {
			$("#nameErrMsg").text(data.nameError);
	    	$("#nameErrDiv").addClass("error");
	    	$("#xlInput").focus(); 
		}
		
		if (data.envError != null) {
			$("#envErrMsg").text(data.envError);
	    	$("#envDiv").addClass("error");
		} 
		
		if (data.typeError != null) {
            $("#configTypeErrMsg").text(data.typeError);
            $("#configTypeDiv").addClass("error");
        }
		
		if (data.dynamicError != null) {
	    	var dynamicErrors = data.dynamicError.split(",");
	    	for (var i=0; i<dynamicErrors.length; i++) {
	    		var dynErr = dynamicErrors[i].split(":");
	     		$("div[id='" + dynErr[0] + "']").addClass("error");
	     		if (i == 0) {
	     			$("input[name='" + dynErr[0] + "']").focus();     			
	     		}
	        	$("div[id='" + dynErr[0] + "ErrorDiv']").html(dynErr[1]);
	    	}
		}
		
		if (data.portError != null) {
			$("div[id='port']").addClass("error");
        	$("div[id='portErrorDiv']").html(data.portError);
        }
		
		if (data.emailError != null) {
			$("div[id='emailid']").addClass("error");
        	$("div[id='emailidErrorDiv']").html(data.emailError);
        }
    }
	
	var versionFrom = "";
	function successEvent(pageUrl, data) {
		if (pageUrl == "configurationsType") {
			successConfigurationsType(data);
		}
		if (pageUrl == "fetchProjectInfoVersions") {
			fillVersions("version", data.projectInfoVersions);
			if (versionFrom == "") {
				showSetttingsInfoVersion();				
			}
		}
	}
	
	function getCurrentVersions(from) {
		versionFrom = from;
		if (versionFrom == "") {
			showSetttingsInfoServer();			
		}
		performAction('fetchProjectInfoVersions', $('#formConfigAdd'), '', true);
	}
</script>

<%-- <%
	}
%> --%>