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
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.regex.*"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<%@ page import="com.photon.phresco.framework.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.framework.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<%
	List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	Gson gson = new Gson();
	Map<String, String> urls = new HashMap<String, String>();
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String configPath = (String) request.getAttribute(FrameworkConstants.REQ_SETTINGS_PATH);
	boolean isEnvSpecific = (Boolean) request.getAttribute(FrameworkConstants.REQ_ENV_SPECIFIC);
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	String per_disabledClass = "btn-primary";
	if (permissions != null && !permissions.canManageConfiguration()) {
		per_disabledStr = "disabled";
		per_disabledClass = "btn-disabled";
	}
	ActionSupport actionSupport = new ActionSupport();
	String disabledStr = "";
	if (CollectionUtils.isEmpty(envs)) {
		disabledStr = "disabled";
	}
 %>

<form id="formSettingsList" class="settingsList">

	<div class="page-header">
	    <h1>
	        <%= FrameworkConstants.SETTINGS_HEADER %>
	    </h1>
	</div>
    
    <div class="operation">
    	<!-- Add Configuration Button --> 
    	
		<input type="button" class="btn <%= per_disabledClass %>" name="configAdd" id="configAdd" <%= per_disabledStr %> value="<s:text name='lbl.btn.add'/>" <%=disabledStr %>/>

		<!-- Delete Configuration Button -->	
		<input type="button" class="btn" name="deleteBtn" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>" data-toggle="modal" href="#popupPage"/>

		<!-- Environment Buttton -->
		<input type="button" id="addEnvironments" class="btn <%= per_disabledClass %>" <%= per_disabledStr %> value="<s:text name='lbl.app.config.environments'/>"/>
		         
		<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg" >
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error alert-message"  id="errormsg">
				<s:actionerror />
			</div>
		</s:if>
    </div>
    
</form>

<div id="loadEnv"> </div>

<script type="text/javascript">
	var isEnvSpecific = "<%= isEnvSpecific %>";
	
	$(document).ready(function() {
		$('#successmsg').hide();
		$('#customerList').show();
		var basicParams = getBasicParamsAsJson();
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath%>";
		var params = '{' + basicParams + ', "fromPage" : "' + fromPage + '", "configPath" : "' + configPath + '", "envSpecific" : ' + isEnvSpecific + '}';
		loadJsonContent('envList', params,  $('#loadEnv'));
		
		confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.configuration"/>', 'delete','<s:text name="lbl.btn.ok"/>');
		
    	//Trigerred when add btn is clicked
    	$('#configAdd').click(function() {
    		showLoadingIcon();
    		loadContent('addConfiguration', $('#formCustomers, #formAppMenu'), $('#container'), 'fromPage=add<%=fromPage%>&configPath=<%=configPath%>&envSpecific=<%=isEnvSpecific%>', '', true);
    	});
    	
    	$('#addEnvironments').click(function() {
    		yesnoPopup('openEnvironmentPopup', '<s:text name="lbl.environment"/>', 'createEnvironment', '<s:text name="label.ok"/>', '', 'fromPage=<%=fromPage%>&configPath=<%=configPath%>');
    	});
	});
	
	 function editConfiguration(currentEnvName, currentConfigType, currentConfigName) {
	 	var params = getBasicParams();
	 	var fromPage = "edit<%= fromPage%>";
	 	var configPath = "<%= configPath%>";
		params = params.concat("&currentEnvName=");
		params = params.concat(currentEnvName);
		params = params.concat("&currentConfigType=");
		params = params.concat(currentConfigType);
		params = params.concat("&currentConfigName=");
		params = params.concat(currentConfigName);
		params = params.concat("&fromPage=");
		params = params.concat(fromPage);
		params = params.concat("&configPath=");
		params = params.concat(configPath);
		params = params.concat("&envSpecific=");
		params = params.concat(isEnvSpecific);
		showLoadingIcon();
		loadContent("editConfiguration", $("#formConfigAdd"), $('#container'), params, '', true);
	}
	 
	function cloneConfiguration(configName, envName, configType, currentConfigDesc) {
	 	var params = getBasicParams();
	 	var fromPage = "<%= fromPage%>";
	 	var configPath = "<%= configPath%>";
        params = params.concat("&configName=");
        params = params.concat(configName);
        params = params.concat("&envName=");
        params = params.concat(envName);
        params = params.concat("&configType=");
        params = params.concat(configType);
        params = params.concat("&fromPage=");
		params = params.concat(fromPage);
		params = params.concat("&configPath=");
		params = params.concat(configPath);
		params = params.concat("&currentConfigDesc=");
		params = params.concat(currentConfigDesc);
		params = params.concat("&envSpecific=");
		params = params.concat(isEnvSpecific);
        yesnoPopup('cloneConfigPopup', 'Clone Environment', 'cloneConfiguration', '<s:text name="lbl.clone"/>', '', params);
	}
	 
	function popupOnOk(self) {
		var url = $(self).attr('id');
		var returnVal = true;
		if (url == "cloneConfiguration") {
			var EnvSelection = $("#configEnv").val();
			if (EnvSelection == null ) {
				$("#errMsg").html("<s:text name='popup.err.msg.add.env'/>");
				returnVal = false;
			}
			var cloneConfigName = $('#configurationName').val();
			if (cloneConfigName== "") {
				$("#errMsg").html("<s:text name='popup.err.msg.empty.config.name'/>");
				$("#configurationName").focus();
				$("#configurationName").val("");
				returnVal = false;
			}
			
			if (returnVal) {
				var params = getBasicParams();
				var fromPage = "<%= fromPage%>";
				var configPath = "<%= configPath%>";
				var copyFromEnvName = $('[name=cloneFromEnvName]').val();
				var configType = $('[name=cloneFromConfigType]').val();
				var configName = $('[name=cloneFromConfigName]').val();
				var currentConfigName = $('#configurationName').val();
				var currentConfigDesc = $('#configDescription').val();
				var currentEnvName = $('#configEnv').val();
				params = params.concat("&copyFromEnvName=");
		        params = params.concat(copyFromEnvName);
		       	params = params.concat("&configType=");
		        params = params.concat(configType);
		        params = params.concat("&configName=");
		        params = params.concat(configName);
				params = params.concat("&fromPage=");
				params = params.concat(fromPage);
				params = params.concat("&configPath=");
				params = params.concat(configPath);
				params = params.concat("&currentConfigName=");
		        params = params.concat(currentConfigName);
				params = params.concat("&currentConfigDesc=");
				params = params.concat(currentConfigDesc);
				params = params.concat("&currentEnvName=");
				params = params.concat(currentEnvName);
				params = params.concat("&envSpecific=");
				params = params.concat('<%= isEnvSpecific %>');
				$("#popupPage").modal('hide');//To hide popup
				loadContent("cloneConfiguration", $("#formClonePopup"), $('#loadEnv'), params, '', true);
			 }
		} else {
			var envs = [];
			var envInfo = [];
			var selectedEnvs = new Array();
			var selectedConfigData = [];
			$('[name="envNames"]').each(function() {
				envs.push($(this).val());
			});
			
			$('input[name="checkEnv"]:checked').each(function() {
				envInfo.push($(this).val());
				var selectedEnvData = $.parseJSON($(this).val());
				selectedEnvs.push(selectedEnvData.name);
			});
			
			$('[name="checkedConfig"]:checked').each(function() {
				selectedConfigData.push($(this).val());
			}); 
			
			var basicParams = getBasicParamsAsJson();
			var fromPage = "<%= fromPage%>";
			var configPath = "<%= configPath%>";
			var params = '{' + basicParams + ', "fromPage" : "' + fromPage + '", "envSpecific" : ' + isEnvSpecific + ', "configPath" : "' + configPath + '", "environmentsInfo": [' + envInfo.join(',') + '], "environments": [' + envs.join(',') + '], "selectedEnvirment" : "' + selectedEnvs + '", "selectedConfigurations": [' + selectedConfigData.join(',') + ']}';
			var url = $(self).attr('id');
			if (url == 'createEnvironment') {
				validateJson(url, '', $('#loadEnv'), params, '');
			} else {
				$("#popupPage").modal('hide');
				loadJsonContent(url, params, $('#loadEnv'));
			}		
		}
	}
</script>