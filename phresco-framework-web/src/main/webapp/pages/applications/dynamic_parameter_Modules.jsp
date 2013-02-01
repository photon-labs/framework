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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>

<%@page import="com.photon.phresco.framework.actions.applications.Features"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.antlr.stringtemplate.StringTemplate" %>

<%@ page import="com.photon.phresco.plugins.util.ModulesProcessor"%>
<%@ page import="com.photon.phresco.framework.actions.applications.DynamicParameterAction"%>

<%@ page import="com.photon.phresco.configuration.Environment"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter"%>
<%@ page import="com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.Name.Value"%>

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel"%>
<%@ page import="com.photon.phresco.framework.commons.BasicParameterModel"%>

<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
	String featureName = (String) request.getAttribute(FrameworkConstants.REQ_FEATURE_NAME);
   	String from = (String) request.getAttribute(FrameworkConstants.REQ_BUILD_FROM);
   	//To read parameter list from phresco-plugin-info.xml
   	List<Parameter> parameters = (List<Parameter>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_PARAMETERS);
    ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
    
    String appId  = applicationInfo.getId();
    String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
    Features features = new Features();
    File featureManifest = new File(features.getManifest(applicationInfo, featureName));
%>

<form autocomplete="off" class="build_form form-horizontal" id="generateConfigTemplate">
<div id="ConfigTemplate_Modal">

	<!-- dynamic parameters starts -->
	 <%	
		if (CollectionUtils.isNotEmpty(parameters)) {
			for (Parameter parameter: parameters) {
				ParameterModel parameterModel = new ParameterModel();
	   			Boolean mandatory = false;
	   			String lableTxt = "";
	   			String labelClass = "";
				if (Boolean.parseBoolean(parameter.getRequired())) {
					mandatory = true;
	 			}
				
				if (!FrameworkConstants.TYPE_HIDDEN.equalsIgnoreCase(parameter.getType())) {
					Value value = parameter.getName().getValue();	
					if (value.getLang().equals("en")) {	//to get label of parameter
						labelClass = "popupLbl";
						lableTxt = value.getValue();
					}
				} 
				
				parameterModel.setMandatory(mandatory);
				parameterModel.setLableText(lableTxt);
				parameterModel.setLableClass(labelClass);
				parameterModel.setName(parameter.getKey());
				parameterModel.setId(parameter.getKey());
				parameterModel.setControlGroupId(parameter.getKey() + "Control");
				parameterModel.setControlId(parameter.getKey() + "Error");
				parameterModel.setShow(Boolean.parseBoolean(parameter.getShow()));
				
				// load input text box
				if (FrameworkConstants.TYPE_STRING.equalsIgnoreCase(parameter.getType()) || FrameworkConstants.TYPE_NUMBER.equalsIgnoreCase(parameter.getType()) || 
					FrameworkConstants.TYPE_PASSWORD.equalsIgnoreCase(parameter.getType()) || FrameworkConstants.TYPE_HIDDEN.equalsIgnoreCase(parameter.getType())) {
					
					parameterModel.setInputType(parameter.getType());
					parameterModel.setValue(StringUtils.isNotEmpty(parameter.getValue()) ? parameter.getValue():"");
					
					StringTemplate txtInputElement = FrameworkUtil.constructInputElement(parameterModel);
	%> 	
					<%= txtInputElement %>
	<% 
				} else if(FrameworkConstants.TYPE_LIST.equalsIgnoreCase(parameter.getType()) && parameter.getPossibleValues() != null)  {
					
					List<com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.PossibleValues.Value> psblValues = parameter.getPossibleValues().getValue();
					parameterModel.setInputType(parameter.getType());
					parameterModel.setObjectValue(psblValues);
					StringTemplate selectElmnt = FrameworkUtil.constructSelectElement(parameterModel);
// 				}
	%>
					<%= selectElmnt %>
    <%			} else if(FrameworkConstants.TYPE_BOOLEAN.equalsIgnoreCase(parameter.getType())){
					String cssClass = "chckBxAlign";
					String onClickFunction = "";
					String onChangeFunction = "";
					if (StringUtils.isNotEmpty(parameter.getDependency())) {
						//If current control has dependancy value 
						List<String> dependancyList = Arrays.asList(parameter.getDependency().split(FrameworkConstants.CSV_PATTERN));
						onChangeFunction = "changeChckBoxValue(this);";
					} else {
						onClickFunction = "changeChckBoxValue(this);";
					}
					
					parameterModel.setOnChangeFunction(onChangeFunction);
					parameterModel.setOnClickFunction(onClickFunction);
					parameterModel.setCssClass(cssClass);
					parameterModel.setValue(parameter.getValue());
					
					StringTemplate chckBoxElement = FrameworkUtil.constructCheckBoxElement(parameterModel);
	%>
					<%= chckBoxElement%>	
	<%
			 	}else if (FrameworkConstants.TYPE_FILE_BROWSE.equalsIgnoreCase(parameter.getType())) {
					parameterModel.setFileType(parameter.getFileType());
					StringTemplate browseFileElement = FrameworkUtil.constructBrowseFileTreeElement(parameterModel);
	%>
					<%= browseFileElement %>
	<%			
				} else if (FrameworkConstants.TYPE_DYNAMIC_PARAMETER.equalsIgnoreCase(parameter.getType()) && (!Boolean.valueOf(parameter.getSort()))) {
						List<com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.PossibleValues.Value> dynamicPsblValues = (List<com.photon.phresco.plugins.model.Module.Configurations.Configuration.Parameter.PossibleValues.Value>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_POSSIBLE_VALUES + parameter.getKey());
						parameterModel.setObjectValue(dynamicPsblValues);
						parameterModel.setMultiple(Boolean.parseBoolean(parameter.getMultiple()));
						parameterModel.setDependency(parameter.getDependency());
						
						StringTemplate selectDynamicElmnt = FrameworkUtil.constructSelectElement(parameterModel);
	%>
						<%= selectDynamicElmnt %>
	<%
				    }
			}
		}
	%>
	<!-- dynamic parameters ends -->
</div>
<%-- <input type="hidden" name="stFileFunction" id="stFileFunction" value="<%= StringUtils.isNotEmpty(stFileFunction.toString()) ? stFileFunction.toString() : "" %>"/>
<input type="hidden" name="resultJson" id="resultJson" value=""/> --%>
<!-- Hidden Fields -->
	<% if (StringUtils.isNotEmpty(featureName)) { %>
		<input type="hidden" name="featureName" value="<%= featureName %>">
	<% } %>
</form>

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()) {
		$(".generate_build").scrollbars();
	}

	var url = "";
	var readerSession = "";
	$(document).ready(function() {
		hidePopuploadingIcon();
		$('.jecEditableOption').click(function() {
	       $('.jecEditableOption').text("");
	    });
	});
	
	function popupOnOk(obj) {
		var url = $(obj).attr("id");
		if (url === "configureFeature") {
			showLoadingIcon();
			$('#popupPage').modal('hide');//To hide the popup
			var params = getBasicParams();
			loadContent('configureFeatureParam', $('#generateConfigTemplate'), $("#subcontainer"), params, true);
		}
	}
</script> 