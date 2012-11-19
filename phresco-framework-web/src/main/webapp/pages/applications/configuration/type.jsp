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
  
<%@page import="com.photon.phresco.commons.model.ArtifactGroupInfo"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>

<%@ page import="org.antlr.stringtemplate.StringTemplate" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.PropertyTemplate" %>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel" %>

<form id="configProperties">

<%
	ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	Properties propertiesInfo = (Properties) request.getAttribute(FrameworkConstants.REQ_PROPERTIES_INFO); 
	String selectedType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TYPE);
	List<PropertyTemplate> properties = (List<PropertyTemplate>) request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	StringBuilder sb = new StringBuilder();
    for (PropertyTemplate propertyTemplate : properties) {
    	ParameterModel pm = new ParameterModel();
    	pm.setMandatory(propertyTemplate.isRequired());
    	pm.setLableText(propertyTemplate.getName());
    	pm.setId(propertyTemplate.getKey());
    	pm.setName(propertyTemplate.getKey());
    	pm.setControlGroupId(propertyTemplate.getKey() + "Control");
    	pm.setControlId(propertyTemplate.getKey() + "Error");
    	pm.setShow(true);

    	String value = "";
    	if (propertiesInfo != null) {
    		value = propertiesInfo.getProperty(propertyTemplate.getKey());
    	}
        List<String> possibleValues = new ArrayList<String>(8);

         if (FrameworkConstants.SERVER_KEY.equals(propertyTemplate.getKey())) {
        	List<ArtifactGroupInfo> artifactGroupInfos = appInfo.getSelectedServers();
        	if(CollectionUtils.isNotEmpty(artifactGroupInfos)) {
        		ArtifactGroupInfo artifactGroupInfo = artifactGroupInfos.get(0);
        		possibleValues = artifactGroupInfo.getArtifactInfoIds();
        	}
    	} else if (FrameworkConstants.DATABASE_KEY.equals(propertyTemplate.getKey())) {
    		List<ArtifactGroupInfo> artifactGroupInfos = appInfo.getSelectedDatabases();
    		if(CollectionUtils.isNotEmpty(artifactGroupInfos)) {
    			ArtifactGroupInfo artifactGroupInfo = artifactGroupInfos.get(0);
    			possibleValues = artifactGroupInfo.getArtifactInfoIds(); 
    		}
    	} else {  
    		possibleValues = propertyTemplate.getPossibleValues();
    	 } 
    	
        if (CollectionUtils.isNotEmpty(possibleValues)) {
        	pm.setObjectValue(possibleValues);
        	//pm.setSelectedValues(value);
        	pm.setMultiple(false);
            StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
            sb.append(dropDownControl);
        } else if ("Boolean".equals(propertyTemplate.getType())) {
        	pm.setPlaceHolder(propertyTemplate.getHelpText());
        	pm.setValue(value);
            StringTemplate inputControl = FrameworkUtil.constructCheckBoxElement(pm);
			sb.append(inputControl);
        } else {
        	pm.setInputType(propertyTemplate.getType());
        	pm.setPlaceHolder(propertyTemplate.getHelpText());
        	pm.setValue(value);
            StringTemplate inputControl = FrameworkUtil.constructInputElement(pm);
			sb.append(inputControl);
        }
    }
%>
	<%= sb.toString() %>
	
<div id="iisDiv" class="hideContent">
	<div class="control-group" id="siteControl">
		<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.site.name'/>
		</label>
		<div class="controls">
			<%
					String siteName = "";
					if (propertiesInfo != null && propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_KEY_SITE_NAME) != null) {
						siteName = propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_KEY_SITE_NAME);							
					}
			%> 
			<input class="xlarge settings_text" id="siteName" placeholder="<s:text name='placeholder.site.name'/>" 
				value="<%= siteName %>" type="text" name="siteName">
			<span class="help-inline" id="siteNameError"></span>
		</div>
	</div>
    
    <div class="control-group" id="appControl">
		<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.app.name'/>
		</label>
		<div class="controls">
			<%
					String appName = "";
					if (propertiesInfo != null && propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_KEY_APP_NAME) != null) {
						appName = propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_KEY_APP_NAME);							
					}
			%> 
			<input class="xlarge settings_text" id="appName" name="appName" type="text" placeholder="<s:text name="placeholder.app.name"/>"
				value="<%= appName %>"/>
			<span class="help-inline" id="appNameError"></span>
		</div>
    </div>
</div>

<% 
	if (appInfo.getTechInfo().getId().equals("tech-sitecore") && selectedType.equals("Server")) { 
	%>
	<div class="control-group" id="siteCoreControl">
		<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.sitecore.inst.path'/>
		</label>
		<div class="controls">
				<%
					String siteCoreInstPath = "";
					if (propertiesInfo != null && propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_SITECORE_INST_PATH) != null) {
						siteCoreInstPath = propertiesInfo.getProperty(FrameworkConstants.SETTINGS_TEMP_SITECORE_INST_PATH);							
					}
				%> 
				<input class="xlarge settings_text" id="siteCoreInstPath" name="siteCoreInstPath" type="text" placeholder="<s:text name="placeholder.sitecore.inst.path"/>" 
					value="<%= siteCoreInstPath %>"/>
				<span class="help-inline" id="siteCoreInstPathError"></span>
		</div>
	  </div>
	<% 	
		}
	%>

</form>

<script type="text/javascript">
$("div#certificateControl").hide();
	
	$(document).ready(function() {
		var typeData= $.parseJSON($('#type').val());
		var selectedType = typeData.name;
		var serverType = $('#server').val();
		 if(selectedType == "Server"){
			if (serverType == "IIS") {
				hideContext();
				hideRemoteDeply();
				$('#iisDiv').css("display", "block");
			}
		 }
		
		 $("#type").change(function() {
			 technologyBasedRemoteDeploy();
		 });
		 
		 $("#server").change(function() {
			 if($(this).val() == "Apache Tomcat" || $(this).val() == "Jboss" || $(this).val() == "WebLogic"){
				 $('#remoteDeploymentControl').show();	 
				 remoteDeplyChecked();
		     } else {
		    	 hideRemoteDeply(); 
		     }
			 
			 if ($(this).val() == "IIS") {
				 hideContext();
				 $('#iisDiv').css("display", "block");
			 } else {
				 showContext();
				 $('#iisDiv').css("display", "none");
			 }
			 
			 remoteDeplyChecked();
			 if( $(this).val() != "Apache Tomcat" || $(this).val() != "JBoss" || $(this).val() != "WebLogic"){
				 remoteDeplyChecked();
			 }
			 if ($(this).val() == "IIS" || $(this).val() == "NodeJS") {
				 $("input[name='remoteDeployment']").attr("checked",false);
			 }
			 
			 technologyBasedRemoteDeploy();
		 });
		 
		// hide deploy dir if remote Deployment selected
		$("input[name='remoteDeployment']").change(function() {
			remoteDeplyChecked(); 
		});
		 
	});

	function remoteDeplyChecked() {
		var isRemoteChecked = $("input[name='remoteDeployment']").is(":checked");
		if (isRemoteChecked) {
			hideDeployDir();
			/* $("#admin_username label").html('<span class="red">* </span>Admin Username');
			$("#admin_password label").html('<span class="red">* </span>Admin Password'); */
		} else {
			$('#deploy_dirControl').show();
		}
	}
	
	function technologyBasedRemoteDeploy() {
		<% 
		if (appInfo.getTechInfo().getId().equals("tech-sitecore")) { 
		%>
				hideDeployDir();
		<% } %>
	} 
	
	function hideRemoteDeply() {
		$('#remoteDeploymentControl').hide();
	}
	
	function hideContext() {
		$('#contextControl').hide();
		$('#additional_contextControl').hide();
	}
	
	function showContext() {
		$('#contextControl').show();
		$('#additional_contextControl').show();
	}
	
	function hideDeployDir() {
		$("input[name='deploy_dir']").val("");
		$('#deploy_dirControl').hide();
	}
	
	
	
//To show the validation error messages
	/* function findError(data) {
		if (!isBlank(data.siteNameError)) {
			showError($("#siteControl"), $("#siteNameError"), data.siteNameError);
		} else {
			hideError($("#siteControl"), $("#siteNameError"));
		}
		
		if (!isBlank(data.appNameError)) {
			showError($("#appControl"), $("#appNameError"), data.appNameError);
		} else {
			hideError($("#appControl"), $("#appNameError"));
		}
	} */


</script>
