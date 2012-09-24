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
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationType" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.Technology" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes"%>

<%
    ApplicationInfo selectedAppInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	List<Technology> technologies = (List<Technology>) request.getAttribute(FrameworkConstants.REQ_APPTYPE_TECHNOLOGIES);
    String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    String disabled = "disabled";
    if (StringUtils.isEmpty(fromPage)) {
        disabled = "";
        fromPage = "";
    } 
    String projectCode = "";
    if (selectedAppInfo != null) {
        projectCode = selectedAppInfo.getCode();
    } else {
        projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
        selectedAppInfo = (ApplicationInfo) session.getAttribute(projectCode);
    }
    
    String selectedTechVersion = null;
	if (selectedAppInfo != null) {
	    selectedTechVersion = selectedAppInfo.getTechInfo().getVersion();
	}
%>

<div class="clearfix">
    <label for="xlInput" Class="new-xlInput"><span class="red">*</span> <s:text name="label.technology"/></label>
    
    <!--  Technologies are loaded here starts-->
    <div class="input new-input">
		<div class="app_type_float_left">
			<select id="technology" name="technology" class="xlarge" <%= disabled %> >
			<%
				// TODO:lohes
				/* Technology selectedTech = null;
				if (selectedInfo != null) {
					selectedTech = selectedInfo.getTechnology();
				} */
				
				String selectedStr = "";
				if (CollectionUtils.isNotEmpty(technologies)) {
					for (Technology technology : technologies) {
						String id = technology.getId();
						String name = technology.getName();
						// TODO:lohes
						/* if (selectedTech != null && selectedTech.getId().equals(id)) {
							selectedStr = "selected";
						} else {
							selectedStr = "";
						} */
			%>
					<option value="<%= id %>" <%= selectedStr %> > <%= name %> </option>
			<%
					}
				}
			%>
			</select>
		</div>
		
		<div id="technologyVersionDiv" style="display: none;">
			<div class="app_type_version">
				<s:text name="label.versions"/>
			</div>
			
			<div class="app_type_version_select_div" id="techVersionDiv">
				<select id="techVersion" name="techVersion" class="app_type_version_select" <%= disabled %>>
				
				</select>
			</div>
		</div>
    </div>
    <!--  Technologies are loaded here ends -->
</div>

<!--  Technology dependency are loaded here starts-->
<div id="techDependency"></div>
<!--  Technology dependency are loaded here ends-->
                    
<script type="text/javascript">
	$(document).ready(function() {
	    techDependencies();
	    
	    $('#technology').change(function() {
	        techDependencies();
	    });
	});
	    
	function techDependencies() {
		$("#alreadyConstructed").val("");
		popup('technology', $("#formAppInfo"), $('#techDependency'), true);
	}
	
	function techVersions() {
		popup("techVersions", $("#formAppInfo"), '', true);
		<% if (StringUtils.isNotEmpty(fromPage)) { %>
			$("input[name='application']").prop("disabled", true);
			$("select[name='technology']").prop("disabled", true);
   		<% } %>
	}
	
	function showPrjtInfoTechVersion() {
		<% if (StringUtils.isNotEmpty(selectedTechVersion)) { %>
			$("#techVersion option").each(function() {
				if ($(this).val().trim() == '<%= selectedTechVersion %>') {
					$(this).prop("selected", "selected");
				}
			});
		<% } %>
		
		var selectedTechnology = $("#technology").val();
		if ('<%= TechnologyTypes.ANDROID_NATIVE %>' == selectedTechnology) {
			if ($("#pilotProjects").val() != "") {
				removeLowerTechVersions();
			}
		}
	}
</script>