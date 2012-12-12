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
<%@ page import="java.util.ArrayList"%>

<%@ page import="org.antlr.stringtemplate.StringTemplate"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroupInfo"%>
<%@ page import="com.photon.phresco.commons.model.PropertyTemplate"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child"%>
<%@ page import="com.photon.phresco.framework.commons.BasicParameterModel"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil"%>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel"%>

<form id="formConfigTempProp">
<% 
	boolean hasCustomProperty = (Boolean) request.getAttribute(FrameworkConstants.REQ_HAS_CUSTOM_PROPERTY);
	String selectedType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TYPE);
	List<PropertyTemplate> properties = (List<PropertyTemplate>)request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	ParameterModel pm = null;
	StringBuilder sb = new StringBuilder();
	if (CollectionUtils.isNotEmpty(properties)) {
	    for (PropertyTemplate property : properties) {
	    	pm = new ParameterModel();
	    	pm.setMandatory(property.isRequired());
	    	pm.setLableText(property.getName());
	    	pm.setLableClass("control-label popupLbl");
	    	pm.setId(property.getKey());
	    	pm.setName(property.getKey());
	    	pm.setControlGroupId(property.getKey() + "Control");
	    	pm.setControlId(property.getKey() + "Error");
	    	pm.setShow(true);
	
	        List<String> possibleValues = new ArrayList<String>(8);
			possibleValues = property.getPossibleValues();
	        if (CollectionUtils.isNotEmpty(possibleValues)) {
	        	pm.setObjectValue(possibleValues);
	        	pm.setMultiple(false);
	            StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
	            sb.append(dropDownControl);
	        } else if ("Boolean".equals(property.getType())) {
	        	pm.setPlaceHolder(property.getHelpText());
	            StringTemplate inputControl = FrameworkUtil.constructCheckBoxElement(pm);
				sb.append(inputControl);
	        } else {
	        	pm.setInputType(property.getType());
	        	pm.setPlaceHolder(property.getHelpText());
	            StringTemplate inputControl = FrameworkUtil.constructInputElement(pm);
				sb.append(inputControl);
	        }
	    }
	}
	if (hasCustomProperty) {
		StringTemplate customParamElement = FrameworkUtil.constructCustomParameters();
		sb.append(customParamElement);
	}
%>
	<%= sb.toString() %>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		<% if (FrameworkConstants.CONFIG_TYPE_FEATURES.equals(selectedType)) { %>
			hideLoadingIcon();
		<% } %>
		$(".popupLbl").text(function(index) {
	        return textTrim($(this), 18);
	    });
	});
</script>