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

<%@ page import="java.util.List" %>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.SettingsTemplate"%>

<%
	String featureName = (String) request.getAttribute(FrameworkConstants.REQ_FEATURE_NAME);
	List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>)request.getAttribute(FrameworkConstants.REQ_SETTINGS_TEMPLATES);
	if (CollectionUtils.isNotEmpty(settingsTemplates)) {
%>
	<form id="formFeatureConfig" class="form-horizontal">
		<!-- SettingsTemplate Starts -->
		<div class="control-group" id="nameErrDiv">
		    <label class="control-label labelbold popupLbl"><s:text name="lbl.type"/></label>
		    <div class="controls">
				<select name="configTemplateType" class="input-xlarge" onchange="getProperties();">
					<% for (SettingsTemplate settingsTemplate : settingsTemplates) { %>
						<option value="<%= settingsTemplate.getId() %>"><%= settingsTemplate.getName() %></option>
					<% } %>
				</select>	        
		    </div>
		</div>
		<!-- SettingsTemplate Ends -->
		
		<!-- Properties Starts -->
		<div id="propertiesDiv"></div>
		<!-- Properties Ends -->
		
		<!-- Hidden Fields -->
		<input type="hidden" name="featureName" value="<%= featureName %>">
	</form>
<%
	}
%>

<script type="text/javascript">
	$(document).ready(function() {
		getProperties();
	});
	
	function getProperties() {
		var params = getBasicParams();
    	loadContent('showConfigProperties', $('#formFeatureConfig'), $('#propertiesDiv'), params, true);
	}
	
	function popupOnOk(obj) {
		showLoadingIcon();
		$('#popupPage').modal('hide');//To hide the popup
		var params = getBasicParams();
		var url = $(obj).attr("id");
		loadContent(url, $('#formFeatureConfig, #formConfigTempProp'), $("#subcontainer"), params, true);
	}
</script>