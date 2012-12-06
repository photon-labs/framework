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
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.regex.*"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<%@ page import="com.photon.phresco.framework.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.framework.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
	List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	Gson gson = new Gson();
	Map<String, String> urls = new HashMap<String, String>();
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	ActionSupport actionSupport = new ActionSupport();
%>

    <% if (CollectionUtils.isEmpty(envs)) { %>
		 <div class="alert alert-block">
			<%= actionSupport.getText("lbl.err.msg.list." + fromPage)%>
		</div> 
    <% } else { %>	
    	<div class="table_div" >
		<% for (Environment env : envs) { 
			String envJson = gson.toJson(env);
		%>
			<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container">
							<span class="siteaccordion closereg">
								<span>
									<input type="checkbox" value='<%= envJson %>' id="<%=env.getName() %>" class="accordianChkBox" name="checkEnv" onclick="checkAllEvent(this,$('.<%=env.getName() %>'), false);"/>
									<a class="vAlignSub"><%=env.getName() %></a>
								</span>
							</span>
							<div class="mfbox siteinnertooltiptxt hideContent">
								<div class="scrollpanel">
									<section class="scrollpanel_inner">
								    	<table class="table table-bordered table_border">
								    	<% 
								    			List<Configuration> configurations = env.getConfigurations();
								    			if (CollectionUtils.isEmpty(configurations)) {
								    		%> 
								    			 <div class="alert alert-block">
													<%= actionSupport.getText("lbl.err.msg.list.config")%>
												</div> 
											<% } else { %>
								    	
								    		<thead>
								    			<tr class="header-background">
								    				<th class="no-left-bottom-border table-pad table-chkbx">
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.name" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.desc" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.type" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.status" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.clone" cssClass="labelbold"/>
								    				</th>
								    			</tr> 
								    		</thead>
								    		
								    		<tbody>
								    		<% 
							    					if (CollectionUtils.isNotEmpty(configurations)) {
														for (Configuration configuration : configurations) {
															String configJson = gson.toJson(configuration);
											%>
															<tr>
																<td class="no-left-bottom-border table-pad">
																	<input type="checkbox" class="check <%=env.getName() %>" name="checkedConfig" value='<%= configJson %>'
																		onclick="checkboxEvent($('.<%=env.getName() %>'), $('#<%=env.getName() %>'));">
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" onclick="editConfiguration('<%= env.getName() %>', '<%= configuration.getType() %>','<%= configuration.getName() %>');" 
																	name="edit"><%= configuration.getName() %>
																	</a>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<%= configuration.getDesc() %>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<%= configuration.getType() %>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" ><img id="<%= configuration.getName()  %>" class="projectUpdate" 
																		src="images/icons/inprogress.png" title="Update" class="iconSizeinList"/></a>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" title="<%= configuration.getName() %>" id="cloneEnvId" onclick="cloneConfiguration(
																	'<%= configuration.getName()%>', '<%= configuration.getEnvName() %>', '<%= configuration.getType() %>', '<%= configuration.getDesc()%>')" >
																	<img src="images/icons/clone.png" alt="Clone"  title="clone configuration"></a>
																</td> 
															</tr>
													<%
																}
															}
														}
													%>  
								    		</tbody>
										</table>
									</section>
								</div>
							</div>
						</section>  
					</div>
				</section>
			</div>
			<% } %>
		</div>
	<% } %>
	
<script type="text/javascript">

	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		accordion();
	});

</script>
