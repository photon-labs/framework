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

<%
	List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	Gson gson = new Gson();
	Map<String, String> urls = new HashMap<String, String>();
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String configPath = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_PATH);
	ActionSupport actionSupport = new ActionSupport();
%>

<form id="formConfigList" class="configList">
    
    <div class="operation">
    	<!-- Add Configuration Button --> 
		<input type="button" class="btn btn-primary" name="configAdd" id="configAdd" 
	         onclick="loadContent('addConfiguration', $('#formCustomers, #formAppMenu'), $('#subcontainer'), 'fromPage=add<%=fromPage%>&configPath=<%=configPath%>');" 
		         value="<s:text name='lbl.btn.add'/>"/>

		<!-- Delete Configuration Button -->	
		<input type="button" class="btn" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>" data-toggle="modal" href="#popupPage"/>

		<!-- Environment Buttton -->
	    <a id="addEnvironments" class="btn btn-primary"><s:text name='lbl.app.config.environments'/></a>
		         
		<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg" >
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error"  id="errormsg">
				<s:actionerror />
			</div>
		</s:if>
    </div>
    
    <div id="loadEnv"> </div>
    
</form>



<script type="text/javascript">
		$('#addEnvironments').click(function() {
			yesnoPopup('openEnvironmentPopup', '<s:text name="lbl.environment"/>', 'createEnvironment', '', '', 'fromPage=<%=fromPage%>&configPath=<%=configPath%>');
		});
	
	confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.configuration"/>', 'deleteEnvironment','<s:text name="lbl.btn.ok"/>');
	
	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		var basicParams = getBasicParamsAsJson();
		var fromPage = "<%= fromPage %>";
		var configPath = "<%= configPath %>";
		var params = '{' + basicParams + ', "fromPage" : "' + fromPage + '", "configPath" : "' + configPath + '"}';
		loadJsonContent('envList', params,  $('#loadEnv'));
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
			loadContent("editConfiguration", $("#formConfigAdd"), $('#subcontainer'), params);
	}
	 
	function popupOnOk(self) {
		var envs = [];
		var selectedEnv;
		var selectedConfigData = [];
		$('[name="envNames"]').each(function() {
			envs.push($(this).val());
		});
		
		$('input[name="checkEnv"]:checked').each(function() {
			var selectedEnvData = $.parseJSON($(this).val());
			selectedEnv = selectedEnvData.name;
		});
		
		$('[name="checkedConfig"]:checked').each(function() {
			selectedConfigData = $(this).val();
		}); 
		
		var basicParams = getBasicParamsAsJson();
		var fromPage = "<%= fromPage%>";
		var configPath = "<%= configPath %>";
		var params = '{' + basicParams + ', "configPath" : "' + configPath + '", "fromPage" : "' + fromPage + '", "environments": [' + envs.join(',') + '], "selectedEnvirment" : "' + selectedEnv + '", "selectedConfig": [' + selectedConfigData + ']}';
		var url = $(self).attr('id');
		loadJsonContent(url, params, $('#loadEnv'));		
	}

</script>
