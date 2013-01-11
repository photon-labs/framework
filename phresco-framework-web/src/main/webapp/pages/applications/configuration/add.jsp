<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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
<%@ page import="java.util.Properties"%>

<%@ page import="com.google.gson.Gson"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.antlr.stringtemplate.StringTemplate"%>

<%@ page import="com.photon.phresco.commons.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<%@ page import="com.photon.phresco.framework.actions.util.FrameworkActionUtil"%>

<%
    String selectedStr = "";
	String name = "";
	String description = "";
	String selectedType = "";
	String selectedAppliesTo = "";
	String error = "";
	String envName = "";
	String desc = "";
    boolean isDefault = false;
	
    String currentEnv = (String) request.getAttribute(FrameworkConstants.REQ_CURRENTENV);
    Configuration configInfo = (Configuration) request.getAttribute(FrameworkConstants.REQ_CONFIG_INFO);
    String configPath = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_PATH);
    StringBuilder sb = new StringBuilder();
	if (configInfo != null) {
	    envName = configInfo.getEnvName();
		name = configInfo.getName();
		description = configInfo.getDesc();
		selectedType = configInfo.getType();
		selectedAppliesTo = configInfo.getAppliesTo();
	}
	
	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>) request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATES);
	String fromPage = request.getParameter(FrameworkConstants.REQ_FROM_PAGE);
	
	String title = FrameworkActionUtil.getTitle(FrameworkConstants.CONFIG, fromPage);
	String buttonLbl = FrameworkActionUtil.getButtonLabel(fromPage);
	String pageUrl = FrameworkActionUtil.getPageUrl(FrameworkConstants.CONFIG, fromPage);
	String progessTxt = FrameworkActionUtil.getProgressTxt(FrameworkConstants.CONFIG, fromPage);
	
	Gson gson = new Gson();
	String container = "subcontainer"; //load for configuration
%>
<%
	if (fromPage.contains(FrameworkConstants.REQ_SETTINGS)) {
		container = "container";
%>
		<div class="page-header">
		    <h1>
		       <%= title %> 
		    </h1>
		</div>
<% 
	} else { %>
		<h4>
			<%= title %> 
		</h4>
<% 
	}
%>

<form id="formConfigAdd" class="form-horizontal formClass">
	<div class="content_adder">
		<div class="control-group" id="configNameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name'/>
			</label>
			<div class="controls">
				<input id="configName" placeholder="<s:text name='place.hldr.config.name'/>" value="<%= name %>" 
					maxlength="30" title="<s:text name='title.30.chars'/>" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="configNameError"></span>
			</div>
		</div> <!-- Name -->
			
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.desc'/>
			</label>
			<div class="controls">
				<textarea id="configDesc"  placeholder="<s:text name='place.hldr.config.desc'/>" class="input-xlarge"
					maxlength="150" title="<s:text name='title.150.chars'/>" name="desc"><%= description %></textarea>
			</div>
		</div> <!-- Desc -->
		
		<div class="control-group" id="configEnvControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.environment'/>
			</label>	
			<div class="controls">
				<select id="environment" name="environment">
				<% for (Environment env : environments) {
						if (env.getName().equals(envName)) {
							selectedStr = "selected";
						} else {
							selectedStr = "";
						}
				%>
						<option value='<%= gson.toJson(env) %>' <%= selectedStr %>><%= env.getName() %></option>
                <% 		 
                	}
                %>
				</select>
				<span class="help-inline" id="configEnvError"></span>
			</div>
		</div> <!-- Environment -->
		
		<div class="control-group" id="configTypeControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.type'/>
			</label>	
			<div class="controls">
				<select id="templateType" name="templateType">
				<% 
					for (SettingsTemplate settingsTemplate : settingsTemplates) { 
						if (settingsTemplate.getName().equals(selectedType) || FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(selectedType)) {
							selectedStr = "selected";
						} else {
							selectedStr = "";
						}	
				%>	
						<option value='<%= gson.toJson(settingsTemplate) %>' <%= selectedStr %>><%= settingsTemplate.getName() %></option>
                <% } %>
                		<option value="Other" <%= selectedStr %>>Other</option>
				</select>
				<span class="help-inline" id="configTypeError"></span>
			</div>
		</div> <!-- Type -->
		<div id="featureListContainer"></div>
		<div id="typeContainer"></div>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="<%= pageUrl %>" class="btn btn-primary" value='<%= buttonLbl %>' />
		<input type="button" id="cancel" class="btn btn-primary" value="<s:text name='lbl.btn.cancel'/>" /> 
	</div>
	
	<!-- Hidden Fields -->
    <input type="hidden" name="fromPage" value="<%= fromPage %>"/>
    <input type="hidden" name="oldName" value="<%= name %>"/>
    <input type="hidden" name="oldConfigType" value="<%= selectedType %>"/>
</form>

<script type="text/javascript">
	/* To check whether the device is ipad or not */
	$(document).ready(function() {
		if (!isiPad()) {
			$(".content_adder").scrollbars(); //JQuery scroll bar
		}
	});
	
	$('#cancel').click(function() {
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath%>";
		var params = '{ ' + getBasicParamsAsJson() + ', "fromPage": "' + fromPage + '", "configPath": "' + configPath + '" }';
		<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) { %>
			loadJsonContent('configuration', params,  $('#subcontainer'));
		<% } else { %>
			loadJsonContent('settings', params,  $('#container'));
		<%	} %>
	});
	
	$('#templateType option[value="Other"]').each(function() {
		var otherJson = {};
		otherJson["name"] = $(this).val();
	    var finalJsonVal = JSON.stringify(otherJson);
	    $(this).val(finalJsonVal);
	});
	
	$('#templateType').change(function() {
		showLoadingIcon();//To hide the loading icon
		var selectedConfigname = $('#configName').val();
		var envData = $.parseJSON($('#environment').val());
		var selectedEnv = envData.name;
		var typeData = $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		var selectedConfigId = typeData.id;
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath %>";
		
		var params = '{ ' + getBasicParamsAsJson() + ', "settingTemplate": ' + $('#templateType').val() + ' , "selectedConfigId": "' + selectedConfigId 
			+ '" , "selectedEnv": "' + selectedEnv + '" , "selectedType": "' + selectedType + '", "fromPage": "' + fromPage + '", "configPath": "' + configPath + '", "selectedConfigname": "' + selectedConfigname + '"}';
		loadJsonContent('configType', params,  $('#typeContainer'));
	}).triggerHandler("change");
	
	$('#<%= pageUrl %>').click(function() {
		var name = $('#configName').val();
		var desc = $('#configDesc').val();
		var env = $('#environment').val();
		var jsonObject = $('#configProperties').toJSON();
		var configStr = JSON.stringify(jsonObject);
		var typeData = $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		if(selectedType == "Other") {
			var keys = [];
			$('#configProperties').find('input[name="key"]').each(function() {
				keys.push(this.value);
			});
			var values = [];
			$('#configProperties').find('input[name="value"]').each(function() {
				values.push(this.value);
			});
			jsonObject.key = keys;
			jsonObject.value = values;
		}
		
		if ($.isEmptyObject(jsonObject)) {//It will be used for getting the features template values
			var $formType = $("#formConfigTempProp");
			jsonObject = getFormData($formType);
			var keys = [];
			$('#formConfigTempProp').find('input[name="key"]').each(function() {
				keys.push(this.value);
			});
			var values = [];
			$('#formConfigTempProp').find('input[name="value"]').each(function() {
				values.push(this.value);
			});
			jsonObject.key = keys;
			jsonObject.value = values;
		}
		
		var template = $.parseJSON($('#templateType').val());
		var type = template.name;
		var oldName = "<%= name %>";
		var configId = template.id;
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath%>";
		var oldConfigType = "<%= selectedType%>";
		
		var selectedAppliesTos = new Array();
		$('input[name="appliesTo"]:checked').each(function() {
			selectedAppliesTos.push($(this).val());
		});
		
		var featureName = $('#featureName').val();
		var fileName = $('.qq-upload-file').text();//To get the uploaded file name
		
		var jsonParamObj = getBasicParamsAsJsonObj();
		jsonParamObj.configName = name;
		jsonParamObj.description = desc;
		jsonParamObj.configType = type;
		jsonParamObj.configId = configId;
		jsonParamObj.featureName = featureName;
		jsonParamObj.oldName = oldName;
		jsonParamObj.oldConfigType = oldConfigType;
		jsonParamObj.configPath = configPath;
		jsonParamObj.fromPage = fromPage;
		jsonParamObj.appliesTos = selectedAppliesTos;
		env = jQuery.parseJSON(env);
		jsonParamObj.environment = env;
		jsonParamObj = $.extend(jsonParamObj, jsonObject);
		jsonParamObj.fileName = fileName;
		var jsonParam = JSON.stringify(jsonParamObj);
		validateJson('<%= pageUrl %>', '', $('#<%= container %>'), jsonParam, '<%= progessTxt %>');
	});
	
	function getFormData($form) {
	    var unindexed_array = $form.find('input:not([name="key"]),input:not([name="value"]').serializeArray();
	    var indexed_array = {};

	    $.map(unindexed_array, function(n, i) {
			indexed_array[n['name']] = n['value'];
	    });

	    return indexed_array;
	}
	
	//To show the validation error messages
	function findError(data) {
		if (!isBlank(data.configNameError)) {
			showError($("#configNameControl"), $("#configNameError"), data.configNameError);
		} else {
			hideError($("#configNameControl"), $("#configNameError"));
		}
		
		if (!isBlank(data.dynamicError)) {
	    	var dynamicErrors = data.dynamicError.split(",");
	    	for (var i = 0; i < dynamicErrors.length; i++) {
    		var dynErr = dynamicErrors[i].split(":");
	    		if (!isBlank(dynErr[1])) {
		    		showError($("#" + dynErr[0] + "Control"), $("#" + dynErr[0]+ "Error"), dynErr[1]);
		    	} else {
					hideError($("#" + dynErr[0] + "Control"), $("#" + dynErr[0]+ "Error"));
				}
    		}
		} 
		
		if (!isBlank(data.configEnvError)) {
			showError($("#configEnvControl"), $("#configEnvError"), data.configEnvError);
		} else {
			hideError($("#configEnvControl"), $("#configEnvError"));
		}
		
		if (!isBlank(data.configTypeError)) {
			showError($("#configTypeControl"), $("#configTypeError"), data.configTypeError);
		} else {
			hideError($("#configTypeControl"), $("#configTypeError"));
		}
		
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
		
		if (!isBlank(data.siteCoreInstPathError)) {
			showError($("#siteCoreControl"), $("#siteCoreInstPathError"), data.siteCoreInstPathError);
		} else {
			hideError($("#siteCoreControl"), $("#siteCoreInstPathError"));
		}
		
		if (!isBlank(data.appliesToError)) {
			showError($("#appliesToControl"), $("#appliesToError"), data.appliesToError);
		} else {
			hideError($("#appliesToControl"), $("#appliesToError"));
		}
		
		if (!isBlank(data.versionError)) {
			showError($("#versionControl"), $("#versionError"), data.versionError);
		} else {
			hideError($("#versionControl"), $("#versionError"));
		}
		
		if (!isBlank(data.emailError)) {
			showError($("#emailidControl"), $("#emailidError"), data.emailError);
		} else {
			hideError($("#emailidControl"), $("#emailidError"));
		}
	}
</script>