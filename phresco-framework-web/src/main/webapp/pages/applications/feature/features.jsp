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
<%@page import="com.photon.phresco.commons.model.ArtifactGroup.Type"%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.ForeachStatement"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroup"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>

<%
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	List<ArtifactGroup> selectedFeatures = (List<ArtifactGroup>) request.getAttribute(FrameworkConstants.REQ_PROJECT_FEATURES);
	ProjectInfo projectInfo = (ProjectInfo)session.getAttribute(appId + FrameworkConstants.SESSION_APPINFO);
	String technologyId = "";
	List<String> selectedModules = null;
	List<String> selectedJSLibs = null;
	List<String> selectedComponents = null;
	if (projectInfo != null) {
		ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
		technologyId = appInfo.getTechInfo().getId();
		selectedModules = appInfo.getSelectedModules();
		selectedJSLibs = appInfo.getSelectedJSLibs();
		selectedComponents = appInfo.getSelectedComponents();
	}
%> 
<form id="formFeatures" class="featureForm">
	<div class="form-horizontal featureTypeWidth">
		<label for="myselect" class="control-label features_cl">Type&nbsp;</label>
		<select id="featureselect" name="type" onchange="featuretype()">
			<option value="<%= ArtifactGroup.Type.FEATURE.name() %>"><s:text name="lbl.options.modules"/></option>
			<option value="<%= ArtifactGroup.Type.JAVASCRIPT.name() %>"><s:text name="lbl.options.js.libs"/></option>
			<option value="<%= ArtifactGroup.Type.COMPONENT.name() %>"><s:text name="lbl.options.components"/></option>
		</select>
	</div>
	<div class="custom_features">
		<div class="tblheader">
		   	<label>
		   		<label class="feature_heading" id="featuresHeading"></label>
	   		</label>
		</div>
		<div id="accordianchange"  class="feature_content_adder">
			
		</div>
        <div style="clear:both"></div>
	</div>
	      
	<input type="button" class="btn btn-primary fea_add_but" onclick="clickToAdd()" value=">>"/>
	<div class="custom_features_wrapper_right">
		<div class="tblheader">
			<label class="feature_heading"><s:text name="lbl.selected.features"/></label></br>
		</div>
		<div class="theme_accordion_container">
			<div id="result"></div>
	   </div>
	</div>
	    
	<div class="features_actions">
		<input type="button" id="previous" value="<s:text name="label.previous"/>" class="btn btn-primary" 
			onclick="showPreviousPage();">
		<input id="finish" type="button" value="<s:text name="label.finish"/>"  class="btn btn-primary"
			onclick="updateApplication();"/>
		<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
			onclick="loadContent('applications', $('#formCustomers'), $('#container'));">
	</div>
	
	 <!-- Hidden fields --> 
	<input type="hidden" name="technologyId" value="<%= technologyId %>">
</form>

<script type="text/javascript">
<%
	 if(selectedFeatures != null){
		 for(ArtifactGroup artifact : selectedFeatures){
			String dispName = artifact.getName();
			String dispValue = artifact.getVersions().get(0).getVersion();
			String hiddenFieldVersion = artifact.getVersions().get(0).getId();
			Type type= artifact.getType();
			String hiddenFieldname = type.toString().toLowerCase();
%>
		constructFeaturesDiv('<%= dispName %>', '<%= dispValue %>', '<%= hiddenFieldname %>', '<%= hiddenFieldVersion %>');
<%		
	 	}
 	}
%>
	inActivateAllMenu($("a[name='appTab']"));
	activateMenu($('#features'));

    $(document).ready(function () {
        hideLoadingIcon();
        fillHeading();
        getFeature();
        accordion();
    });
    
    // Function for the feature list selection
    function featuretype() {
    	fillHeading();
    	getFeature();
        var str = "";
        $("#featureselect option:selected").each(function () {
            str += $(this).text();
        });
    }

    //Function for to get the list of features
    function getFeature() {
    	var params = getBasicParams();
	    loadContent("listFeatures", $('#formFeatures'), $('#accordianchange'), params);
    }
    
    // Function to add the features to the right tab
    function clickToAdd() {
        $('#accordianchange input:checked').each(function () {
        	var hiddenFieldname = ($('#featureselect').val()).toLowerCase();
        	var dispName = $(this).val();
        	var hiddenFieldVersion = $('select[name='+dispName+']').val();
        	var dispValue = $("#" + dispName + " option:selected").text();
        	constructFeaturesDiv(dispName, dispValue, hiddenFieldname, hiddenFieldVersion);
        });
    }
    
    // Function to construct the hidden fields for selected features
    function constructFeaturesDiv(dispName, dispValue, hiddenFieldname, hiddenFieldVersion) {
		$("div[id='"+ dispName +"']").remove();
		$("input[class='"+ dispName +"']").remove();
		$("#result").append('<input type="hidden" class="'+dispName+'" name="'+hiddenFieldname+'" value="'+hiddenFieldVersion+'">');
		$("#result").append('<div id="'+dispName+'">'+dispName+' - '+dispValue+'<a href="#" id="'+dispName+'" onclick="remove(this);">&times;</a></div>');
    }
    
    // Function to remove the final features in right tab  
    function remove(thisObj) {
    	var id = $(thisObj).attr("id");
    	$("#" + id).remove();
    	$("." + id).remove();
    }
    
    // Function to fill the heading of the left tab
    function fillHeading() {
    	var val = $("#featureselect").val();
    	$("#featuresHeading").text(val)
    }
    
  	//Function for to get the list of features
    function updateApplication() {
    	var params = getBasicParams();
    	loadContent('finish', $('#formFeatures'), $('#container'), params);
    }
  	
  	//Function for to get the previous page
  	function showPreviousPage() {
  		var params = getBasicParams();
  		loadContent('previous', $('#formFeatures'), $('#subcontainer'), params);
  	}
</script>