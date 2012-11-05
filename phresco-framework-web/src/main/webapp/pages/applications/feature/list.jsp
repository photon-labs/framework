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
<%@ page import="com.photon.phresco.commons.model.ArtifactGroup"%>
<%@ page import="com.photon.phresco.commons.model.ArtifactInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>

<% 
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	List<ArtifactGroup> moduleGroups = (List<ArtifactGroup>)request.getAttribute(FrameworkConstants.REQ_FEATURES_MOD_GRP);
	String type = (String) request.getAttribute(FrameworkConstants.REQ_FEATURES_TYPE);
	if (CollectionUtils.isNotEmpty(moduleGroups)) {
		for(ArtifactGroup artifactGroup : moduleGroups) {
%>
		<div  class="accordion_panel_inner">
		    <section class="lft_menus_container">	
				<span class="siteaccordion">
					<span>
						<input class="feature_checkbox" type="checkbox" value=<%=artifactGroup.getName() %> id="checkAll1"/>
						<a style="float: left; margin-left:2%;" href="#"><%=artifactGroup.getName() %></a>
						
						<select class="input-mini features_ver_sel" id="<%=artifactGroup.getName() %>"  name="<%=artifactGroup.getName() %>" >
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
						String desc = artifactGroup.getDescription();
						if(desc!=null && !desc.isEmpty()){
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
	});
</script>