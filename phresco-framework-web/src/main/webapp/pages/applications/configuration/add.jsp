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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>

<%@ page import="com.google.gson.Gson"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
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
	}
	
	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>) request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATES);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String favouriteConfigId = (String) request.getAttribute(FrameworkConstants.REQ_FAVOURITE_CONFIG_ID);
	boolean fromFavouriteConfig = (Boolean) request.getAttribute(FrameworkConstants.REQ_FROM_FAVOURITE_CONFIG);
	boolean isEnvSpecific = (Boolean) request.getAttribute(FrameworkConstants.REQ_ENV_SPECIFIC);
	
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
					maxlength="30" title="<s:text name='title.30.chars'/>" class="input-xlarge" type="text" name="configName" onblur="renameUploadedFilesDir('ConfigName');">
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
		
		<%
			if (CollectionUtils.isNotEmpty(environments)) {
				String envClass = "";
				if (FrameworkConstants.EDIT_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_SETTINGS.equals(fromPage)) {
					envClass = "hideContent";
				}
		%>
			<div class="control-group <%= envClass %>" id="configEnvControl">
				<label class="control-label labelbold">
					<span class="mandatory">*</span>&nbsp;<s:text name='lbl.environment'/>
				</label>	
				<div class="controls">
					<select id="environment" name="environment" onchange="renameUploadedFilesDir('Env');">
					<%
						for (Environment env : environments) {
							if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.ADD_SETTINGS.equals(fromPage)) { 
					%>
								<option value='<%= gson.toJson(env) %>'><%= env.getName() %></option>
								
					<% 		} else if (FrameworkConstants.EDIT_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_SETTINGS.equals(fromPage)) {
								if (env.getName().equals(envName)) {
										selectedStr = "selected";
					%>
								<option value='<%= gson.toJson(env) %>' <%= selectedStr %>><%= env.getName() %></option>
	                <% 	
								}
							}
	                	}
	                %>
					</select>
					<span class="help-inline" id="configEnvError"></span>
				</div>
			</div> <!-- Environment -->
		<% } %>
		
		<div class="control-group <%= fromFavouriteConfig ? "hideContent" : "" %>" id="configTypeControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.type'/>
			</label>
			<% if (isEnvSpecific) { %>	
				<div class="controls">
					<select id="templateType" name="templateType">
					<%
						if (CollectionUtils.isNotEmpty(settingsTemplates)) {
							for (SettingsTemplate settingsTemplate : settingsTemplates) {
								if (settingsTemplate.isEnvSpecific()) {
									if (settingsTemplate.getName().equals(selectedType)) {
										selectedStr = "selected";
									} else {
										selectedStr = "";
									}
									if (FrameworkConstants.ADD_CONFIG.equals(fromPage)) {
										if (StringUtils.isNotEmpty(favouriteConfigId) && favouriteConfigId.equals(settingsTemplate.getId())) {
											selectedStr = "selected";
										} else {
											selectedStr = "";
										}
									}
					%>	
									<option value='<%= gson.toJson(settingsTemplate) %>' <%= selectedStr %>><%= StringUtils.isEmpty(settingsTemplate.getDisplayName()) ? settingsTemplate.getName() : settingsTemplate.getDisplayName() %></option>
	                <% 
								}
							}
						}
						if (FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(selectedType)) {
							selectedStr = "selected";
						} else {
							selectedStr = "";
						}
					%>
							<option value="Other" <%= selectedStr %>><%= FrameworkConstants.REQ_CONFIG_TYPE_OTHER %></option>
					</select>
					<span class="help-inline" id="configTypeError"></span>
				</div>
			<% } else { %>
				<div class="controls">
					<select id="templateType" name="templateType">
					<%
						if (CollectionUtils.isNotEmpty(settingsTemplates)) {
							for (SettingsTemplate settingsTemplate : settingsTemplates) {
								if (!settingsTemplate.isEnvSpecific()) {
									if (settingsTemplate.getName().equals(selectedType)) {
										selectedStr = "selected";
									} else {
										selectedStr = "";
									}
									if (FrameworkConstants.ADD_CONFIG.equals(fromPage)) {
										if (StringUtils.isNotEmpty(favouriteConfigId) && favouriteConfigId.equals(settingsTemplate.getId())) {
											selectedStr = "selected";
										} else {
											selectedStr = "";
										}
									}
					%>	
									<option value='<%= gson.toJson(settingsTemplate) %>' <%= selectedStr %>><%= StringUtils.isEmpty(settingsTemplate.getDisplayName()) ? settingsTemplate.getName() : settingsTemplate.getDisplayName() %></option>
	                <% 
								}
							}
						}
						if (FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(selectedType)) {
							selectedStr = "selected";
						} else {
							selectedStr = "";
						}
					%>
							<option value="Other" <%= selectedStr %>><%= FrameworkConstants.REQ_CONFIG_TYPE_OTHER %></option>
					</select>
					<span class="help-inline" id="configTypeError"></span>
				</div>			
			<% } %>
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
    <input type="hidden" id="hiddenConfigName" />
    <input type="hidden" id="hiddenEnvName" />
</form>

<script type="text/javascript">
	var isEnvSpecific = "<%= isEnvSpecific %>";
	var fromFavouriteConfig = "<%= fromFavouriteConfig %>";
	var favouriteConfigId = "<%= favouriteConfigId %>";

	/* To check whether the device is ipad or not */
	$(document).ready(function() {
		if (!isiPad()) {
			$(".content_adder").scrollbars(); //JQuery scroll bar
		}
		
		$('#configName').bind('input propertychange', function (e) {
            var configName = $(this).val();
            configName = checkForSplChrExceptDot(configName);
            $(this).val(configName);
            enableOrDisableUpldBtn();
        });
		
		$('#cancel').click(function() {
			var fromPage = "<%= fromPage%>";
			var configPath = "<%= configPath%>";
			var params = '{ ' + getBasicParamsAsJson() + ', "fromPage": "' + fromPage + '", "configPath": "' + configPath + '" }';
			<% if (FrameworkConstants.ADD_CONFIG.equals(fromPage) || FrameworkConstants.EDIT_CONFIG.equals(fromPage)) { %>
				loadJsonContent('configuration', params,  $('#subcontainer'));
			<% } else { %>
				loadJsonContent('settings', params,  $('#container'));
			<% } %>
		});
		
		$('#templateType option[value="Other"]').each(function() {
			var otherJson = {};
			otherJson["name"] = $(this).val();
		    var finalJsonVal = JSON.stringify(otherJson);
		    $(this).val(finalJsonVal);
		});
		
		$('#templateType').change(function() {
			getPropertyTemplate();
		}).triggerHandler("change");
		
		$('#<%= pageUrl %>').click(function() {
			$("input[name='deploy_dir']").attr("disabled", false);
			var name = $('#configName').val();
			var desc = $('#configDesc').val();
			var env = $('#environment').val();
			$("#certificate").prop("disabled", false); // enable certificate text box before serializing form inorder to pass it value
			// convert form to json, toJson function is not passing filed names with dot.
			var jsonObject = getFormAsJson($('#configProperties'));
			var typeData = $.parseJSON($('#templateType').val());
			var customPropStatus = typeData.customProp;
			var selectedType = typeData.name;
			if(selectedType == "Other" || customPropStatus) {
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
			var fromPage = "<%= fromPage %>";
			var configPath = "<%= configPath %>";
			var oldConfigType = "<%= selectedType %>";
			
			var featureName = $('#featureName').val();
			//var fileName = $('.qq-upload-file').text();//To get the uploaded file name
			var csvFiles = "";
			$('.hidden-fileName').each(function() {
				var propName = $(this).attr("propName");
				var fileName = $(this).val();
				csvFiles = csvFiles.concat(propName);
				csvFiles = csvFiles.concat("#SEP#");
				csvFiles = csvFiles.concat(fileName);
				csvFiles = csvFiles.concat(",");
			});
			csvFiles = csvFiles.substring(0, csvFiles.length - 1);
			
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
			jsonParamObj.envSpecific = isEnvSpecific;
			jsonParamObj.fromFavouriteConfig = fromFavouriteConfig;
			env = jQuery.parseJSON(env);
			jsonParamObj.environment = env;
			jsonParamObj = $.extend(jsonParamObj, jsonObject);
			jsonParamObj.csvFiles = csvFiles;
			var jsonParam = JSON.stringify(jsonParamObj);
			validateJson('<%= pageUrl %>', '', $('#<%= container %>'), jsonParam, '<%= progessTxt %>');
		});
		
		//To select the simpleUI/advanceUI option
		$("#simpleUI, #advanceUI").unbind();
		$("#simpleUI, #advanceUI").click(function() {
			var ancId = $(this).attr("id");
			$(".selectedIcn").hide();
			$("#" + ancId + "Img").show();
			$("#uiType").val(ancId);
			if ('<%= FrameworkConstants.SIMPLE_UI %>' === ancId) {
				displayProp = "none";
			} else if ('<%= FrameworkConstants.ADVANCE_UI %>' === ancId) {
				displayProp = "block";
			}
			$(".simpleUiCtrls").css("display", displayProp);
			hideRemoteDeployBasedOnOptionIds();
// 			getPropertyTemplate();
		});
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
		var isError = false;
		if (!isBlank(data.configNameError)) {
			isError = true;
			showError($("#configNameControl"), $("#configNameError"), data.configNameError);
		} else {
			hideError($("#configNameControl"), $("#configNameError"));
		}
		
		if (!isBlank(data.dynamicField)) {
			var dynamicFields = data.dynamicField.split(",");
			for (var i = 0; i < dynamicFields.length; i++) {
	    		var dynField = dynamicFields[i];
	    		hideError($("#" + dynField + "Control"), $("#" + dynField + "Error"));
	    	}
		}
		
		if (!isBlank(data.dynamicError)) {
	    	var dynamicErrors = data.dynamicError.split(",");
	    	for (var i = 0; i < dynamicErrors.length; i++) {
    		var dynErr = dynamicErrors[i].split(":");
	    		if (!isBlank(dynErr[1])) {
	    			isError = true;
		    		showError($("#" + dynErr[0] + "Control"), $("#" + dynErr[0]+ "Error"), dynErr[1]);
		    	}
    		}
		}
		
		if (!isBlank(data.configEnvError)) {
			isError = true;
			showError($("#configEnvControl"), $("#configEnvError"), data.configEnvError);
		} else {
			hideError($("#configEnvControl"), $("#configEnvError"));
		}
		
		if (!isBlank(data.configTypeError)) {
			isError = true;
			showError($("#configTypeControl"), $("#configTypeError"), data.configTypeError);
		} else {
			hideError($("#configTypeControl"), $("#configTypeError"));
		}
		
		if (!isBlank(data.siteNameError)) {
			isError = true;
			showError($("#siteControl"), $("#siteNameError"), data.siteNameError);
		} else {
			hideError($("#siteControl"), $("#siteNameError"));
		}
		
		if (!isBlank(data.appNameError)) {
			isError = true;
			showError($("#appControl"), $("#appNameError"), data.appNameError);
		} else {
			hideError($("#appControl"), $("#appNameError"));
		}
		
		if (!isBlank(data.siteCoreInstPathError)) {
			isError = true;
			showError($("#siteCoreControl"), $("#siteCoreInstPathError"), data.siteCoreInstPathError);
		} else {
			hideError($("#siteCoreControl"), $("#siteCoreInstPathError"));
		}
		
		if (!isBlank(data.versionError)) {
			isError = true;
			showError($("#versionControl"), $("#versionError"), data.versionError);
		} else {
			hideError($("#versionControl"), $("#versionError"));
		}
		
		if (!isBlank(data.emailError)) {
			isError = true;
			showError($("#emailidControl"), $("#emailidError"), data.emailError);
		} else {
			hideError($("#emailidControl"), $("#emailidError"));
		}
		
		// To disable certificate text box if any error occurs on validation
		if (isError) {
			$("#certificate").prop("disabled", true);
		}
	}
	
	function enableOrDisableUpldBtn() {
		var configName = $('#configName').val();
		var len = $('#contentFilefile-uploader, #themeFilefile-uploader').find('.qq-upload-success').length;
		if (configName != undefined && !isBlank(configName) && len <= 0) {
        	enableUploadButton($(".file-uploader"));
        } else {
        	disableUploadButton($(".file-uploader"));
        }
	}
	
	function getPropertyTemplate() {
		showLoadingIcon();//To show the loading icon
		var selectedConfigname = $('#configName').val();
		var envData;
		var selectedEnv = "";
		<% if (CollectionUtils.isNotEmpty(environments)) { %>
			envData = $.parseJSON($('#environment').val());
			selectedEnv = envData.name;
		<% } %>
		var typeData = $.parseJSON($('#templateType').val());
		var selectedType = typeData.name;
		var selectedConfigId = typeData.id;
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath %>";
		
		var params = '{ ' + getBasicParamsAsJson() + ', "settingTemplate": ' + $('#templateType').val() + ' , "selectedConfigId": "' + selectedConfigId 
			+ '" , "selectedEnv": "' + selectedEnv + '" , "selectedType": "' + selectedType + '", "fromPage": "' + fromPage + '", "configPath": "' + configPath + '", "selectedConfigname": "' + selectedConfigname + '"}';
		loadJsonContent('configType', params,  $('#typeContainer'));
	}
	
	function listUploadedFiles() {
		if (<%= fromPage.equals(FrameworkConstants.EDIT_CONFIG) %>) {
			showLoadingIcon();
			var configName = $("#configName").val();
			var envName = $("#environment option:selected").text();
			var typeData= $.parseJSON($('#templateType').val());
			var selectedType = typeData.name;
			var params = getBasicParams();
			params = params.concat("&envName=");
			params = params.concat(envName);
			params = params.concat("&configName=");
			params = params.concat(configName);
			params = params.concat("&currentConfigType=");
			params = params.concat(selectedType);
			params = params.concat("&envSpecific=");
			params = params.concat(isEnvSpecific);
			
			loadContent("listUploadedFiles", '', '', params, true, true);
		}
	}
</script>