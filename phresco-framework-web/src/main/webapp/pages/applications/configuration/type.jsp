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

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>

<%@ page import="com.google.gson.Gson"%>
<%@ page import="org.antlr.stringtemplate.StringTemplate" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<%@ page import="com.photon.phresco.commons.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.PropertyTemplate" %>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel" %>
<%@ page import="com.photon.phresco.commons.model.Element" %>

<%
	ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	Properties propertiesInfo = (Properties) request.getAttribute(FrameworkConstants.REQ_PROPERTIES_INFO); 
	SettingsTemplate settingsTemplate = (SettingsTemplate) request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATE);	  
	String selectedType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TYPE);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	
	List<String> typeValues  = (List<String>) request.getAttribute(FrameworkConstants.REQ_TYPE_VALUES);
	List<String> appinfoServers  = (List<String>) request.getAttribute(FrameworkConstants.REQ_APPINFO_SERVERS);
	List<String> appinfoDbases  = (List<String>) request.getAttribute(FrameworkConstants.REQ_APPINFO_DBASES);
	List<String> selectedAppliesTos  = (List<String>) request.getAttribute(FrameworkConstants.REQ_APPLIES_TO);
	List<PropertyTemplate> properties = (List<PropertyTemplate>) request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	Gson gson = new Gson(); 
	
	if (FrameworkConstants.ADD_SETTINGS.equals(fromPage) || FrameworkConstants.EDIT_SETTINGS.equals(fromPage)) { %>
		<div class="control-group" id="appliesToControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.applies.to'/>
			</label>
			<div class="controls">
				<div class="settingsTypeFields">
            		<div class="multilist-scroller multiselect" id='multiselectAppliesTo'>
					   <ul>
					   	<% 
					   		String checkedStr = "";
					   		List<Element> appliesToTechs = settingsTemplate.getAppliesToTechs();
				   			for (Element appliesTo : appliesToTechs) {
				   				if (selectedAppliesTos != null ) {
					   			for (String selectedAppliesTo : selectedAppliesTos) {
					   				if (selectedAppliesTos.contains(appliesTo.getName()) ) {
										checkedStr = "checked";
									} else {
										checkedStr = "";
									}
					   			}
				   			}
					   	%>
							<li>
								<input type="checkbox" name="appliesTo" class="check appliesToCheck" 
									value='<%= appliesTo.getName() %>' title="<%= appliesTo.getDescription() %>"  <%= checkedStr %> /><%= appliesTo.getName() %>
							</li>
						<%		
				   		}
					%>
					   </ul>
			  	 	</div>
				</div>
				<span class="help-inline" id="appliesToError"></span>
			</div>
	    </div>
   <% } %>
   
   <form id="configProperties">
<% 
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
		if (FrameworkConstants.SERVER.equals(settingsTemplate.getName()) && FrameworkConstants.CONFIG_TYPE.equals(propertyTemplate.getKey())) {
			if (FrameworkConstants.ADD_SETTINGS.equals(fromPage) || FrameworkConstants.EDIT_SETTINGS.equals(fromPage)) {
				possibleValues = typeValues;
			} else {
       			if(appInfo != null && CollectionUtils.isNotEmpty(appInfo.getSelectedServers())) {
       				possibleValues = appinfoServers;
				}
			}
    	} else if (FrameworkConstants.DATABASE.equals(settingsTemplate.getName()) && FrameworkConstants.CONFIG_TYPE.equals(propertyTemplate.getKey())) {
    		if (FrameworkConstants.ADD_SETTINGS.equals(fromPage) || FrameworkConstants.EDIT_SETTINGS.equals(fromPage)) {
				possibleValues = typeValues;
			} else {
	    		if(appInfo != null && CollectionUtils.isNotEmpty(appInfo.getSelectedDatabases())) {
	    			possibleValues = appinfoDbases; 
	    		}
			}
    	} else {
			possibleValues = propertyTemplate.getPossibleValues();
    	}
		
		if (FrameworkConstants.TYPE_FILE.equals(propertyTemplate.getType())) {
    %>
   			<script type="text/javascript">
   				createFileUploader('<%= propertyTemplate.getName() %>');
   			</script>
	<%
		} else if (CollectionUtils.isNotEmpty(possibleValues)) {
        	pm.setObjectValue(possibleValues);
        	//pm.setSelectedValues(value);
        	pm.setMultiple(false);
            StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
            sb.append(dropDownControl);
        } else if ("Boolean".equals(propertyTemplate.getType())) {
        	pm.setOnClickFunction("changeChckBoxValue(this)");
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
		%>
			<script type="text/javascript">
				$('input[name="<%= propertyTemplate.getKey() %>"]').live('input propertychange',function(e) {
					var value = $(this).val();
					var type = '<%= propertyTemplate.getType() %>';
					var txtBoxName = '<%= propertyTemplate.getKey() %>';
					validateInput(value, type, txtBoxName);
				}); 
			</script>	
       <% } 
        if (FrameworkConstants.CONFIG_TYPE.equals(propertyTemplate.getKey())) {
        	pm.setMandatory(true);
        	pm.setLableText("Version");
        	pm.setId("version");
        	pm.setName("version");
        	pm.setControlGroupId("versionControl");
        	pm.setControlId("versionError");
        	pm.setShow(true);
        	pm.setObjectValue(null);
        	StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
        	sb.append(dropDownControl);
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
	if (appInfo != null && appInfo.getTechInfo().getId().equals("tech-sitecore") && selectedType.equals("Server")) { 
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

	<div class="control-group hideContent" id="fileControl">
		<label class="control-label labelbold" id="fileControlLabel"> 
		 	
		</label>
		<div class="controls">
			<div id="file-uploader" class="file-uploader">
				<noscript>
					<p>Please enable JavaScript to use file uploader.</p>s
					<!-- or put a simple form for upload here -->
				</noscript>
			</div>
		</div>
		<span class="help-inline fileError" id="fileError"></span>
	</div>
</form>

<script type="text/javascript">
	$("div#certificateControl").hide();
	
	$(document).ready(function() {
		remoteDeplyChecked();
		hideLoadingIcon();//To hide the loading icon
		technologyBasedRemoteDeploy();
		
		<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) { %>
				getVersions();
		<% } else { %>
				getSettingsVersions();
		<% } %>
			
		var typeData= $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		var serverType = $('#type').val();
		if(selectedType == "Server"){
			if (serverType == "IIS") {
				hideContext();
				hideRemoteDeply();
				$('#iisDiv').css("display", "block");
			}
		}
		
		if (serverType == "NodeJs" || serverType == "NodeJs Mac") {
			$("#deploy_dirControl label").html('Deploy Directory');
		}
		 
		$("#type").change(function() {
			if ($(this).val() == "Apache Tomcat" || $(this).val() == "Jboss" || $(this).val() == "WebLogic"){
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
			if ($(this).val() != "Apache Tomcat" || $(this).val() != "JBoss" || $(this).val() != "WebLogic") {
				remoteDeplyChecked();
			}
			if ($(this).val() == "IIS" || $(this).val() == "NodeJs") {
				$("input[name='remoteDeployment']").attr("checked",false);
			}
			
			technologyBasedRemoteDeploy();
			
			<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) { %>
					getVersions();
			<% } else { %>
					getSettingsVersions();
			<% } %>
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
			$("#admin_usernameControl label").html('<span class="red">* </span>Admin Username');
			$("#admin_passwordControl label").html('<span class="red">* </span>Admin Password'); 
		} else {
			$("#admin_usernameControl label").html('Admin Username');
			$("#admin_passwordControl label").html('Admin Password'); 
			$('#deploy_dirControl').show();
		}
	}
	
	function technologyBasedRemoteDeploy() {
		<%
			if (appInfo != null && appInfo.getTechInfo().getId().equals("tech-sitecore")) { 
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
	
	function validateInput(value, type, txtBoxName) {
		var newVal = "";
		if(type == "String") {
			newVal = removeSpaces(checkForSplChrForString(value));
		} else if(type == "Number") {
			newVal = removeSpaces(allowNumHyphen(value));
		} else {
			newVal = removeSpaces(value);
		}
		$("#"+txtBoxName).val(newVal);
	}
	
	function getVersions() {
		var typeData= $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		var params = getBasicParams();
		params = params.concat("&selectedType=");
		params = params.concat(selectedType);
		loadContent("fetchProjectInfoVersions", $('#configProperties'), '', params, true, true);
	}
	
	function getSettingsVersions() {
		var typeData= $.parseJSON($('#templateType').val());
		var propType = $('#type').val();
		var selectedType = typeData.name;
		var params = getBasicParams();
		params = params.concat("&selectedType=");
		params = params.concat(selectedType);
		params = params.concat("&propType=");
		params = params.concat(propType);
		loadContent("fetchSettingProjectInfoVersions", $('#configProperties'), '', params, true, true);
	}
	
	function successEvent(pageUrl, data) {
		//To fill the versions 
		if (pageUrl == "fetchProjectInfoVersions") {
			fillSelectbox($("select[name='version']"), data.versions);
		}
		
		if (pageUrl == "fetchSettingProjectInfoVersions") {
			fillSelectbox($("select[name='version']"), data.versions);
		}
	}
	
	function createFileUploader(controlLabel) {
		$('#fileControlLabel').html(controlLabel);
		$('#fileControl').show();
		var imgUploader = new qq.FileUploader ({
            element : document.getElementById('file-uploader'),
            action : 'uploadFile',
            multiple : false,
            allowedExtensions : ["zip"],
            buttonLabel : '<s:text name="lbl.upload" />',
            typeError : '<s:text name="err.invalid.file.type" />',
            debug: true
        });
	}
	
	function removeUploadedFile(obj) {
		$(obj).parent().remove();
		
		var params = "fileName=";
		params = params.concat($(obj).attr("filename"));
		params = params.concat("&");
		params = params.concat(getBasicParams());
		params = params.concat("&configTempType=");
		params = params.concat($("#templateType option:selected").text());
		$.ajax({
			url : "removeConfigFile",
			data : params,
			type : "POST",
			success : function(data) {
			}
		});
	}
	
	function fileError(data, type) {
		var controlObj;
		var msgObj;
		if (type == "customerImageFile") {
			controlObj = $("#fileControl");
			msgObj = $("#fileError");
		}
		if (data != undefined && !isBlank(data)) {
			showError(controlObj, msgObj, data);
		} else {
			hideError(controlObj, msgObj);
		}
	}
</script>