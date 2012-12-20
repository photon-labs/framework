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

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.google.gson.Gson" %>

<%@ page import="com.photon.phresco.commons.model.ArtifactGroup"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroup.Type"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.SelectedFeature"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
	List<SelectedFeature> features = (List<SelectedFeature>)request.getAttribute(FrameworkConstants.REQ_SELECTED_FEATURES);
	String oldAppDirName = (String) request.getAttribute(FrameworkConstants.REQ_OLD_APPDIR);
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	List<ArtifactGroup> selectedFeatures = (List<ArtifactGroup>) request.getAttribute(FrameworkConstants.REQ_PROJECT_FEATURES);
	ProjectInfo projectInfo = (ProjectInfo)session.getAttribute(appId + FrameworkConstants.SESSION_APPINFO);
	String technologyId = "";
	List<String> selectedModules = null;
	List<String> selectedJSLibs = null;
	List<String> selectedComponents = null;
	Gson gson = new Gson();
	if (projectInfo != null) {
		ApplicationInfo appInfo = projectInfo.getAppInfos().get(0);
		technologyId = appInfo.getTechInfo().getId();
	}
	Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
%> 
<form id="formFeatures" class="featureForm">
	<div class="form-horizontal featureTypeWidth">
		<label for="myselect" class="control-label features_cl">Type&nbsp;:</label>
		 <select id="featureselect" name="type" onchange="featuretype()">
	        <option value="<%= ArtifactGroup.Type.FEATURE.name() %>" selected="selected" data-imagesrc="images/features.png"
	            data-description="Description with Modules"><s:text name="lbl.options.modules"/></option>
	        <option value="<%= ArtifactGroup.Type.JAVASCRIPT.name() %>" data-imagesrc="images/libraries.png"
	            data-description="Description with Javascript"><s:text name="lbl.options.js.libs"/></option>
	        <option value="<%= ArtifactGroup.Type.COMPONENT.name() %>" data-imagesrc="images/components.png"
	            data-description="Description with Components"><s:text name="lbl.options.components"/></option>
	    </select>
	    <div class="alert alert-success alert-message hideContent" id="successmsg">
			<s:text name="succ.feature.configure"/>
		</div>
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
			<label class="feature_heading"><s:text name="lbl.selected.features"/></label>
		</div>
		<div class="theme_accordion_container">
			<div id="result"></div>
	   </div>
	</div>
	    
	<div class="features_actions">
		<input type="button" id="previous" value="<s:text name="label.previous"/>" class="btn btn-primary" 
			onclick="showAppInfoPage();">
		<input id="finish" type="button" value="<s:text name="label.finish"/>"  class="btn btn-primary"
			onclick="updateApplication();"/>
		<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
			onclick="loadContent('applications', $('#formCustomers'), $('#container'), '', '', true);">
	</div>
	
	<!-- Hidden fields --> 
	<input type="hidden" name="technologyId" value="<%= technologyId %>">
	<input type="hidden" name="oldAppDirName" value="<%= oldAppDirName %>">
</form>

<script type="text/javascript">
<%	
	if (CollectionUtils.isNotEmpty(features)) {
		for (SelectedFeature feature : features) {
%>
			constructFeaturesDiv('<%= feature.getDispName() %>', '<%= feature.getDispValue() %>', '<%= feature.getType() %>', '<%= feature.getVersionID() %>', '<%= feature.getModuleId() %>', true);
<%		
	 	}
	}
%>

	inActivateAllMenu($("a[name='appTab']"));
	activateMenu($('#features'));

	var selectedType = "";
    $(document).ready(function () {
    	//To check whether the device is ipad or not and then apply jquery scrollbar
    	if (!isiPad()) {
     		$("#accordianchange").scrollbars();  
    	}
    	showLoadingIcon();
        
        $('#featureselect').ddslick({
        	onSelected: function(data) {
        		selectedType = data.selectedData.value;
        		featureType(data.selectedData.value, data.selectedData.text); 
        	}
        });
    });
    
    // Function for the feature list selection
    function featureType(selectedFeatureValue, selectedFeatureText) {
    	fillHeading(selectedFeatureText);
    	getFeature(selectedFeatureValue);
    }

    //Function for to get the list of features
    function getFeature(selectedType) {
    	var params = getBasicParams() + '&type=' + selectedType;
	    loadContent("listFeatures", $('#formFeatures'), $('#accordianchange'), params, '', true);
    }
    
    //Function to add the features to the right tab
    function clickToAdd() {
        $('#accordianchange input:checked').each(function () {
        	var dispName = $(this).val();
        	var id = removeSpaces(dispName);
        	var hiddenFieldVersion = $('#'+id).val();
        	var moduleId = $('#'+id).attr('moduleId');
        	var dispValue = $("#" + id + " option:selected").text();
        	constructFeaturesDiv(dispName, dispValue, selectedType, hiddenFieldVersion, moduleId);
        });
    }
    
    // Function to construct the hidden fields for selected features
    function constructFeaturesDiv(dispName, dispValue, hiddenFieldname, hiddenFieldVersion, moduleId, showConfigImg) {
		var jsonParamObj = {};
		jsonParamObj.dispName = dispName;
		jsonParamObj.moduleId = moduleId;
		jsonParamObj.dispValue = dispValue;
		jsonParamObj.versionID = hiddenFieldVersion;
		jsonParamObj.type = hiddenFieldname;
		var jsonParam = JSON.stringify(jsonParamObj);
		var ctrlClass = removeSpaces(dispName);
		$("div[id='"+ ctrlClass +"Div']").remove();
		if (showConfigImg) {
			$("#result").append('<div id="'+ctrlClass+'Div">'+dispName+' - '+dispValue+
					'<a href="#" onclick="remove(this);">&nbsp;&times;</a>'+
					'<input type="hidden" class="'+ctrlClass+'" name="jsonData">' +
					<%
						if (optionIds != null && optionIds.contains(FrameworkConstants.FEATURES_KEY) || optionIds.contains(FrameworkConstants.COMPONENT_CONFIG)) {
					%>
						'<a href="#" id="'+dispName+'" onclick="showFeatureConfigPopup(this);">'+ 
						'<img src="images/icons/gear.png" title="Configure"/></a>' +
					<% 
						}
					%>
					'</div>');
		} else {
			$("#result").append('<div id="'+ctrlClass+'Div">'+dispName+' - '+dispValue+
					'<a href="#" onclick="remove(this);">&times;</a>'+
					'<input type="hidden" class="'+ctrlClass+'" name="jsonData"></div>');
		}
		$("."+ctrlClass).val(jsonParam);
    }
    
    // Function to remove the final features in right tab  
    function remove(thisObj) {
    	$(thisObj).closest('div').remove();
    }
    
    // Function to fill the heading of the left tab
    function fillHeading(selectedType) {
    	$("#featuresHeading").text(selectedType)
    }
    
  	//Function to get the list of features
    function updateApplication() {
    	var params = getBasicParams();
    	showProgressBar('<s:text name='progress.txt.update.app'/>');
    	loadContent('finish', $('#formFeatures'), $('#container'), params, false, true);
    }
  	
  	//To show the configuration popup
  	function showFeatureConfigPopup(obj) {
  		var featureName = $(obj).attr("id");
  		var params = "&featureName=";
  		params = params.concat(featureName);
  		yesnoPopup('showFeatureConfigPopup', '<s:text name="lbl.configure"/>', 'configureFeature', '<s:text name="lbl.configure"/>', '', params);
  	}
  	
  	function successEvent(url, data) {
  		if (url === "showConfigProperties") {
  			$('#propertiesDiv').empty();
  			$('#propertiesDiv').html(data);
  		} else if (url === "configureFeature") {
  			hideLoadingIcon();
  			$("#successmsg").show();
  			setTimeOut();
  		} else if (url === "fetchDefaultFeatures") {
			makeFeaturesSelected(data.depArtifactGroupNames, data.depArtifactInfoIds);
		} else if (url === "fetchDependentFeatures") {
			if (data.dependency) {
				makeFeaturesSelected(data.depArtifactGroupNames, data.dependencyIds);
			}
		}
  	}
</script>