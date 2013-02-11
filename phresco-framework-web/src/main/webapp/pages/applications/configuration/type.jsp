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
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Enumeration"%>

<%@ page import="com.google.gson.Gson"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>
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
	String appId = "";
	if (appInfo != null) {
	    appId = appInfo.getId();
	}
	Properties propertiesInfo = (Properties) request.getAttribute(FrameworkConstants.REQ_PROPERTIES_INFO); 
	SettingsTemplate settingsTemplate = (SettingsTemplate) request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATE);	
	String selectedType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TYPE);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<String> typeValues  = (List<String>) request.getAttribute(FrameworkConstants.REQ_TYPE_VALUES);
	List<String> appinfoServers  = (List<String>) request.getAttribute(FrameworkConstants.REQ_APPINFO_SERVERS);
	List<String> appinfoDbases  = (List<String>) request.getAttribute(FrameworkConstants.REQ_APPINFO_DBASES);
	List<PropertyTemplate> properties = (List<PropertyTemplate>) request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	List<String> options = (List<String>) request.getAttribute(FrameworkConstants.REQ_TECH_OPTIONS);
	Gson gson = new Gson(); 
	
 %>
   <form id="configProperties">
<% 
	StringBuilder sb = new StringBuilder();
	ParameterModel pm = new ParameterModel();
	if (CollectionUtils.isNotEmpty(properties)) {
	    for (PropertyTemplate propertyTemplate : properties) {
	    	pm.setMandatory(propertyTemplate.isRequired());
	    	pm.setLableText(propertyTemplate.getName());
	    	pm.setId(propertyTemplate.getKey());
	    	pm.setName(propertyTemplate.getKey());
	    	pm.setControlGroupId(propertyTemplate.getKey() + "Control");
	    	pm.setControlId(propertyTemplate.getKey() + "Error");
	    	pm.setShow(true);
	
	    	String value = "";
	    	
	    	if (!FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(propertyTemplate.getType())) {
		    	if (propertiesInfo != null) {
		    		value = propertiesInfo.getProperty(propertyTemplate.getKey());
		    	}
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
				String mandatoryStr = "";
				if (propertyTemplate.isRequired()) {
					mandatoryStr = "<span class=mandatory>*</span>&nbsp;";
				}
				
	    %>
	   			<script type="text/javascript">
	   				createFileUploader('<%= mandatoryStr %>', '<%= propertyTemplate.getName() %>');
	   				
	   				function createFileUploader(mandatory, controlLabel) {
	   					$('#fileControlLabel').html(mandatory + controlLabel);
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
	   					listUploadedFiles();
	   				}
	   				
	   				function listUploadedFiles() {
	   					var params = getBasicParams();
	   					params = params.concat("&configTempType=");
	   					params = params.concat($("#templateType option:selected").text());
	   					loadContent("listUploadedFiles", '', '', params, true, true);
	   				}
	   			</script>
		<%
			} else if (FrameworkConstants.TYPE_ACTIONS.equals(propertyTemplate.getType())) {
		%>
	            <script type="text/javascript">
	            	constructBtnElement('<%= pm.getLableText() %>', '<%= pm.getId() %>');
	            	
	            	function constructBtnElement(btnVal, btnId) {
	   					var ctrlGroup = "<div class='control-group'><label for='xlInput' class='control-label labelbold'></label>" +
	   									"<div class='controls'><input type='button' class='btn btn-primary' onclick='performBtnEvent(this)'" + 
	   									"id='"+ btnId +"' value='"+ btnVal +"'/></div></div>";
	   					$('#configProperties').append(ctrlGroup);
	   				}
				</script>
		<%
			} else if (FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(propertyTemplate.getType())) {
				if (propertiesInfo == null) {
					pm.setValue("");
					List<Object> values = new ArrayList<Object>();
					values.add("");
					pm.setObjectValue(values);
					StringTemplate customParamElement = FrameworkUtil.constructCustomParameters(pm);
					sb.append(customParamElement);
				} else {
					Enumeration em = propertiesInfo.keys();
					boolean firstRow = true;
					while(em.hasMoreElements()) {
						String otherKey = (String)em.nextElement();
						Object otherValue = propertiesInfo.get(otherKey);
						pm.setValue(otherKey);
						List<Object> values = new ArrayList<Object>();
						values.add(otherValue);
						pm.setObjectValue(values);
						if (firstRow) {
							pm.setShowMinusIcon(false);							
						} else {
							pm.setShowMinusIcon(true);
						}
						StringTemplate customParamElement = FrameworkUtil.constructCustomParameters(pm);
						sb.append(customParamElement);
						firstRow = false;
					}
				}
	        } else if (!propertyTemplate.isMultiple() && CollectionUtils.isNotEmpty(possibleValues)) {
	        	pm.setObjectValue(possibleValues);
	        	List<String> alreadySelectedValue = new ArrayList<String>();
	        	alreadySelectedValue.add(value);
	        	pm.setSelectedValues(alreadySelectedValue);
	        	pm.setMultiple(false);
	            StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
	            sb.append(dropDownControl);
	        } else if ("Boolean".equals(propertyTemplate.getType())) {
	        	pm.setOnClickFunction("changeChckBoxValue(this)");
	        	pm.setPlaceHolder(propertyTemplate.getHelpText());
	        	pm.setValue(value);
	            StringTemplate inputControl = FrameworkUtil.constructCheckBoxElement(pm);
				sb.append(inputControl);
	        } else if (propertyTemplate.isMultiple() && CollectionUtils.isNotEmpty(possibleValues)) {
	        	List<String> selectedValList = Arrays.asList(value.split(FrameworkConstants.CSV_PATTERN));	
	        	pm.setSelectedValues(selectedValList);
	        	pm.setMultiple(true);
	        	pm.setObjectValue(possibleValues);
	        	StringTemplate multiSelectBox = FrameworkUtil.constructConfigMultiSelectBox(pm);
	        	sb.append(multiSelectBox);
	        }  else {
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
						if(txtBoxName != '<%= FrameworkConstants.EMAIL_ID %>') {
							validateInput(value, type, txtBoxName);
						}
					});
				</script>	
		<%
			}
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
	}
%>
	
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
	if (appInfo != null && FrameworkConstants.TECH_SITE_CORE.equals(appInfo.getTechInfo().getId()) && FrameworkConstants.SERVER.equals(selectedType)) { 
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

	<div class="control-group hideContent" id="fileControl" style="margin-bottom: -19px;">
		<label class="control-label labelbold" id="fileControlLabel"> 
		 	
		</label>
		<div class="controls">
			<div id="file-uploader" class="file-uploader">
				<noscript>
					<p>Please enable JavaScript to use file uploader.</p>
					<!-- or put a simple form for upload here -->
				</noscript>
			</div>
		</div>
		<span class="help-inline fileError" id="fileError"></span>
	</div>
	
	<%
		List<String> propKeys = new ArrayList<String>();
	 	for (PropertyTemplate propertyTemplate : properties) {
	 		String propKey = propertyTemplate.getKey();
	 		propKeys.add(propKey);
	 	}
		if(settingsTemplate.isCustomProp()) {
			if (propertiesInfo == null) {
				pm.setValue("");
				List<Object> values = new ArrayList<Object>();
				values.add("");
				pm.setObjectValue(values);
				StringTemplate customParamElement = FrameworkUtil.constructCustomParameters(pm);
				sb.append(customParamElement);
			} else {
				Enumeration em = propertiesInfo.keys();
				boolean firstRow = true;
				while(em.hasMoreElements()) {
					String key = (String)em.nextElement();
					if(propKeys.contains(key) || FrameworkConstants.REQ_VERSION_INFO.equals(key) || FrameworkConstants.SETTINGS_TEMP_SITECORE_INST_PATH.equals(key) || FrameworkConstants.SETTINGS_TEMP_KEY_APP_NAME.equals(key) || FrameworkConstants.SETTINGS_TEMP_KEY_SITE_NAME.equals(key)) {
						
					} else {
						Object value = propertiesInfo.get(key);
						pm.setValue(key);
						List<Object> values = new ArrayList<Object>();
						values.add(value);
						pm.setObjectValue(values);
						if (firstRow) {
							pm.setShowMinusIcon(false);							
						} else {
							pm.setShowMinusIcon(true);
						}
						StringTemplate customParamElement = FrameworkUtil.constructCustomParameters(pm);
						sb.append(customParamElement);
						firstRow = false;
					}
				}
			}
		}
	%>
	
	<%= sb.toString() %>
	
</form>

<script type="text/javascript">
	$("div#certificateControl").hide();
	
	$(document).ready(function() {
		remoteDeplyChecked();
		hideLoadingIcon();//To hide the loading icon
		technologyBasedRemoteDeploy();
		var typeData= $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		var customPropStatus = typeData.customProp;
		var serverType = $('#type').val();
		
		<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) { %>
				getVersions();
		<% } else { %>
				if(selectedType == "Other" || customPropStatus) {
					$("#configProperties table").removeClass('custParamTable');
					$("#configProperties table").addClass('otherCustParamTable');
				}
				getSettingsVersions();
		<% } %>
			
		if(selectedType == "Server") {
			if (serverType == "IIS") {
				hideContext();
				hideRemoteDeply();
				$('#iisDiv').css("display", "block");
			}
			
		}
		
		<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) {
				if (CollectionUtils.isNotEmpty(options) && !options.contains(FrameworkConstants.REMOTE_DEPLOYMENT)) { 
		%>
				hideRemoteDeply();
		<%	
				}
			}
		%>
		
		if (serverType == "NodeJs" || serverType == "NodeJs Mac") {
			hideDeployDir();
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
			if (appInfo != null && FrameworkConstants.TECH_SITE_CORE.equals(appInfo.getTechInfo().getId())) { 
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
			newVal = checkForSplChrForString(value);
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
		var propType = $('#type').val();
		var params = getBasicParams();
		params = params.concat("&selectedType=");
		params = params.concat(selectedType);
		params = params.concat("&propType=");
		params = params.concat(propType);
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
		} else if (pageUrl == "fetchSettingProjectInfoVersions") {
			fillSelectbox($("select[name='version']"), data.versions);
		} else if (pageUrl == "listUploadedFiles") {
			if (data.uploadedFiles != undefined && !isBlank(data.uploadedFiles)) {
				disableUploadButton($("#file-uploader"));
				for (i in data.uploadedFiles) {
					var fileName = data.uploadedFiles[i];
					var ul = '<ul class="qq-upload-list"><li>' +
				                '<span class="qq-upload-file">' + fileName + '</span>' +
				                '<img class="qq-upload-remove" src="images/icons/delete.png" style="cursor:pointer;" alt="Remove" '+
				                'eleAttr="file-uploader" fileName="'+ fileName +'" onclick="removeUploadedFile(this);"/>' +
			            		'</li></ul>';
					$('#file-uploader').append(ul);
				}
			}
		}
	}
	
	function removeUploadedFile(obj) {
		var eleAttr = $(obj).attr("eleAttr");
		enableUploadButton($("#" + eleAttr));
		
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
		
	}
	
	function performBtnEvent(obj) {
		var url = $(obj).attr("id");
		var params = getBasicParams();
		progressPopupAsSecPopup(url, '<%= appId %>', url, $("#generateBuildForm"), params);
	}
	
	//To update the hidden field for the config property whose type is multiple
	function updateHdnFieldForMultType(obj) {
		var key = $(obj).attr("class");
		var hiddenFieldValue = "";
		$('input[class="'+ key +'"]:checked').each(function() {
			hiddenFieldValue = $(this).val() + ',' + hiddenFieldValue;
		});
		hiddenFieldValue = hiddenFieldValue.substring(0, hiddenFieldValue.length - 1);
		$('input[name="'+ key +'"]').val(hiddenFieldValue);
	}
	
	function callCron(obj) {
		var param = "schedulerKey="
		param = param.concat($(obj).attr("connector"));
		yesnoPopup('calcCron', 'Cron Expression', 'Copy', 'Copy', '', param);
	}
	
	function popupOnOk(self) {
		$('#'+schedulerKey).val($('#cronExpression').val());
	}
</script>