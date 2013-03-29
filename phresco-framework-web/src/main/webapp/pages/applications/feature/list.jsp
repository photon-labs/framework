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
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List" %>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.google.gson.Gson"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroup"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactGroup.Type"%>
<%@ page import="com.photon.phresco.commons.model.CoreOption"%>

<%
	Gson gson = new Gson();
	boolean defaultModule = false;
	String techId = (String) request.getAttribute(FrameworkConstants.REQ_TECHNOLOGY);
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	List<ArtifactGroup> artifactGroups = (List<ArtifactGroup>)request.getAttribute(FrameworkConstants.REQ_FEATURES_MOD_GRP);
	String type = (String) request.getAttribute(FrameworkConstants.REQ_FEATURES_TYPE);
	if (CollectionUtils.isNotEmpty(artifactGroups)) {
		for (ArtifactGroup artifactGroup : artifactGroups) {
		    List<CoreOption> coreOptions = artifactGroup.getAppliesTo();
		    boolean canConfigure = false;
		    for (CoreOption coreOption : coreOptions) {
		        if (coreOption.getTechId().equals(techId) && !coreOption.isCore()) {
		            canConfigure = true;
		            break;
		        }
		    }
		    if (Type.COMPONENT.equals(artifactGroup.getType())) {
		        canConfigure = true;
		    }
		    
		    if (Type.JAVASCRIPT.equals(artifactGroup.getType())) {
		        canConfigure = false;
		    }
		    
		    String artifactGrpName = artifactGroup.getName().replaceAll("\\s","");
%>
		<div  class="accordion_panel_inner">
		    <section class="lft_menus_container">	
				<span class="siteaccordion">
					<span>
						<input class="feature_checkbox" type="checkbox" defaultModule="<%= defaultModule %>" canConfigure="<%= canConfigure %>" 
							value="<%= artifactGroup.getName() %>" dispName="<%= artifactGroup.getDisplayName() %>" scope="compile" packaging="<%= artifactGroup.getPackaging() %>" onclick="checkboxEvent($('.feature_checkbox'), $('#checkAllAuto'));"/>
						<a style="float: left; margin-left:2%;" href="#"><%= artifactGroup.getDisplayName() %></a>
						
						<select class="input-mini features_ver_sel" id="<%= artifactGrpName %>" artifactGroupId="<%= artifactGroup.getId() %>" moduleId="<%= artifactGroup.getId() %>" name="<%=artifactGroup.getName() %>" >
							<%
								List<ArtifactInfo> artifactInfos = artifactGroup.getVersions();
								for (ArtifactInfo artifactInfo : artifactInfos) {
							%>
									<option value="<%= artifactInfo.getId() %>"><%= artifactInfo.getVersion() %></option>
							<% } %>
							<div style="clear: both;"></div>
						</select>
					</span>
				</span>
				<div class="mfbox siteinnertooltiptxt">
					<%
						String desc = artifactGroup.getHelpText();
						if (StringUtils.isNotEmpty(desc)) {
					%>
					    <div class="scrollpanel">
				        <section class="scrollpanel_inner">
							<img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
							<p class="version_des">
								<%= desc %>
							</p>
						</section>
				    </div>
				    <%} %>
				</div>
			</section>
		</div>
<% 
		}
	} else {
%>
		<div class="alert alert-block">
			<s:text name='lbl.err.msg.list.features'/>
		</div>
<%		
	}
%>

<script type="text/javascript">
	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		accordion();
		getSelectedFeatures();
		getDefaultFeatures();
		hideProgressBar();
	});
	
	var unCheck;
	//To get the dependent features
	$("input:checkbox").change(function() {
		var jsonObjectParam = {};
		var jsonObject = <%= gson.toJson(artifactGroups) %>;
		jsonObjectParam.artifactGroups = jsonObject;
		var selectedValue = $(this).val();	
		var moduleId = $("select[name='"+ selectedValue + "']").val();
		unCheck = false;
		var status = $(this).attr("checked");
		if (status != "checked") {
			unCheck = true;
		}
		jsonObjectParam.moduleId = moduleId;
		var jsonString = JSON.stringify(jsonObjectParam);
		loadJsonContent("fetchDependentFeatures", jsonString, '', '', true);
	});

	//To get the default features
	function getDefaultFeatures() {
		var jsonObjectParam = {};
		var jsonObject = <%= gson.toJson(artifactGroups) %>;
		jsonObjectParam.artifactGroups = jsonObject;
		jsonObjectParam.technology = '<%= techId %>';
		var jsonString = JSON.stringify(jsonObjectParam);
		loadJsonContent("fetchDefaultFeatures", jsonString, '', '', true);
	}
	
	//To get the selected features
	function getSelectedFeatures() {
		var params = getBasicParams();
		params = params.concat("&type=");
		params = params.concat('<%= type%>');
		params = params.concat("&techId=");
		params = params.concat('<%= techId %>');
		loadContent("fetchSelectedFeatures", '', '', params, true, true);
	}
	
	//To check the Features and the corressponding version
	function makeFeaturesSelected(defaultModules, depArtifactInfoIds, from) {
		for (i in defaultModules) {  //To check the default feature
			$("input:checkbox[value='" + defaultModules[i] + "']").attr('checked', true);
			if (from != undefined && !isBlank(from) && from === "defaultFeature") {
				$("input:checkbox[value='" + defaultModules[i] + "']").attr('defaultModule', true);
			}
		}
		for (i in defaultModules) {  //To select the default version
			var featureName = defaultModules[i];
			$("select[name='"+ featureName + "'] option").each(function() {
				var currentVal = $(this).val();
				if (($.inArray(currentVal, depArtifactInfoIds)) > -1) {
					$(this).attr("selected", "selected");
					return false;
				}
			});
		}
		if(unCheck) {
			for (i in defaultModules) {  //To check the default feature
				$("input:checkbox[value='" + defaultModules[i] + "']").attr('checked', false);
			}
			for (i in defaultModules) {  //To select the default version
				var featureName = defaultModules[i];
				$("select[name='"+ featureName + "'] option").each(function() {
					var currentVal = $(this).val();
					if (($.inArray(currentVal, depArtifactInfoIds)) > -1) {
						$(this).attr("selected", false);
						return false;
					}
				});
			}
		}
		checkboxEvent($('.feature_checkbox'), $('#checkAllAuto'));
		if (from === "defaultFeature" && from != "fetchSelectedFeatures") {
			clickToAdd();
		} 
	}
	
</script>