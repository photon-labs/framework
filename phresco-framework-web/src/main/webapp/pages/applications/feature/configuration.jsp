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

<%@page import="java.util.Properties"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>

<%@ page import="org.antlr.stringtemplate.StringTemplate"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroupInfo"%>
<%@ page import="com.photon.phresco.commons.model.PropertyTemplate"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child"%>
<%@ page import="com.photon.phresco.framework.commons.BasicParameterModel"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil"%>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel"%>

<form id="formConfigTempProp" class="form-horizontal">
<% 
	String featureName = (String) request.getAttribute(FrameworkConstants.REQ_FEATURE_NAME);
	String featureType = (String) request.getAttribute(FrameworkConstants.REQ_TYPE);
	boolean hasCustomProperty = (Boolean) request.getAttribute(FrameworkConstants.REQ_HAS_CUSTOM_PROPERTY);
	String selectedType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TYPE);
	List<PropertyTemplate> properties = (List<PropertyTemplate>)request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	Properties valueProperties = (Properties)request.getAttribute(FrameworkConstants.REQ_PROPERTIES_INFO);
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
	        	pm.setValue((String) valueProperties.get(property.getKey()));
	            StringTemplate inputControl = FrameworkUtil.constructInputElement(pm);
				sb.append(inputControl);
	        }
	    }
	}
	if (hasCustomProperty) {
		pm = new ParameterModel();
		pm.setValue("");
		List<Object> values = new ArrayList<Object>();
		values.add("");
		pm.setObjectValue(values);
		StringTemplate customParamElement = FrameworkUtil.constructCustomParameters(pm);
		sb.append(customParamElement);
	}
%>
	<%= sb.toString() %>
	
	<!-- Hidden Fields -->
	<% if (StringUtils.isNotEmpty(featureName)) { %>
		<input type="hidden" name="featureName" value="<%= featureName %>">
	<% } %>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		<% if (FrameworkConstants.CONFIG_TYPE_FEATURES.equals(selectedType) || FrameworkConstants.CONFIG_TYPE_COMPONENTS.equals(selectedType)) { %>
			hideLoadingIcon();
		<% } else { %>
			hidePopuploadingIcon();
		<% } %>
		$(".popupLbl").text(function(index) {
	        return textTrim($(this), 18);
	    });
	});
	
	function popupOnOk(obj) {
		var url = $(obj).attr("id");
		if (url === "configureFeature") {
			var form = $('#formConfigTempProp').serializeArray();
			var key = form[0].value;
			var value = form[1].value;
			if (!isBlank(key) && !isBlank(value)) {
				showLoadingIcon();
				$('#popupPage').modal('hide');//To hide the popup
				var params = getBasicParams();
				params = params.concat("&featureType=");
		  		params = params.concat("<%= featureType %>");
				loadContent(url, $('#formConfigTempProp'), $("#subcontainer"), params, true);
			} else {
				if(isBlank(key)) {
					$("#errMsg").html("key is missing");
					setTimeOut();
				} else {
					$("#errMsg").html("value is missing");
					setTimeOut();
				}
			}
		}
	}
	
	function setTimeOut() { 
		setTimeout(function() {
			$('#errMsg').empty("slow", function () {
			});
		}, 5000);
	}
</script>