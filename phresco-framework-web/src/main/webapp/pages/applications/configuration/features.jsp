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
	List<String> featureNames = (List<String>)request.getAttribute(FrameworkConstants.REQ_FEATURE_NAMES);
	if (CollectionUtils.isNotEmpty(featureNames)) {
%>
		<div class="control-group" id="nameErrDiv">
		    <label class="control-label labelbold"><s:text name="lbl.cust.feature"/></label>
		    <div class="controls">
				<select id = "featureName" name="featureName" class="input-xlarge" onchange="getProperties();">
					<% for (String featureName : featureNames) { %>
						<option value="<%= featureName %>"><%= featureName %></option>
					<% } %>
				</select>	        
		    </div>
		</div>
		
		<div id="featurePropContainer"></div>
		
		<!-- Hidden Fields -->
		<input type="hidden" name="configTemplateType" value="config_Features">
<%
	}
%>

<script type="text/javascript">
	$(document).ready(function() {
		getProperties();
	});
	
	function getProperties() {
		var params = getBasicParams();
    	loadContent('showConfigProperties', $('#formConfigAdd'), '', params, true);
	}

	function successEvent(pageUrl, data) {
		$('#featurePropContainer').empty();
		$('#featurePropContainer').html(data);
	}
</script>