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
  
<%@page import="com.photon.phresco.commons.model.ArtifactGroupInfo"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>

<%@ page import="org.antlr.stringtemplate.StringTemplate" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.PropertyTemplate" %>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil" %>
<%@ page import="com.photon.phresco.framework.commons.ParameterModel" %>

<form id="configProperties">
<%
	ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	Properties propertiesInfo = (Properties) request.getAttribute(FrameworkConstants.REQ_PROPERTIES_INFO); 
	List<PropertyTemplate> properties = (List<PropertyTemplate>) request.getAttribute(FrameworkConstants.REQ_PROPERTIES);
	StringBuilder sb = new StringBuilder();
    for (PropertyTemplate propertyTemplate : properties) {
    	String value = propertiesInfo.getProperty(propertyTemplate.getKey());
    	ParameterModel pm = new ParameterModel();
    	pm.setMandatory(propertyTemplate.isRequired());
    	pm.setLableText(propertyTemplate.getName());
    	pm.setId(propertyTemplate.getKey());
    	pm.setName(propertyTemplate.getKey());
    	pm.setControlGroupId(propertyTemplate.getKey() + "Control");
    	pm.setControlId(propertyTemplate.getKey() + "Error");
    	pm.setShow(true);
    	
        List<String> possibleValues = new ArrayList<String>(8);

       /*  if (FrameworkConstants.SERVER_KEY.equals(propertyTemplate.getKey())) {
        	ArtifactGroupInfo artifactGroupInfo = appInfo.getSelectedServers().get(0);
        	possibleValues = artifactGroupInfo.getArtifactInfoIds();
    	} else if (FrameworkConstants.DATABASE_KEY.equals(propertyTemplate.getKey())) {
    		ArtifactGroupInfo artifactGroupInfo = appInfo.getSelectedDatabases().get(0);
    		possibleValues = artifactGroupInfo.getArtifactInfoIds();
    	} else {  */
    		possibleValues = propertyTemplate.getPossibleValues();
    	/* } */
    	
        if (CollectionUtils.isNotEmpty(possibleValues)) {
        	pm.setObjectValue(possibleValues);
        	//pm.getSelectedValues(value);
        	pm.setMultiple(false);
            StringTemplate dropDownControl = FrameworkUtil.constructSelectElement(pm);
            sb.append(dropDownControl);
        } else {
        	pm.setInputType(propertyTemplate.getType());
        	pm.setPlaceHolder(propertyTemplate.getHelpText());
        	pm.setValue(value);
            StringTemplate inputControl = FrameworkUtil.constructInputElement(pm);
            sb.append(inputControl);
        }
    }
%>
	<%= sb.toString() %>
</form>