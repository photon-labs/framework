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
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.regex.*"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>
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
	List<Configuration> configurations = null;
%>

    <% if (CollectionUtils.isEmpty(envs)) { %>
		 <div class="alert alert-block">
			<img id="config_warning_icon" src="images/icons/warning_icon.png" />
			<%= actionSupport.getText("lbl.err.msg.list." + fromPage)%>
		</div> 
    <% } else { %>
    	<div class="table_div" >
    		<s:if test="hasActionMessages()">
				<div class="alert alert-success alert-message" id="envSuccessmsg">
					<s:actionmessage />
				</div>
			</s:if>
		<% 
			for (Environment env : envs) { 
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
								    			configurations = env.getConfigurations();
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
																	<% 
																		Properties Properties = configuration.getProperties();
													    				String host = "";
													    				String port = "";
													    				String protocol = "";
													    				host = Properties.getProperty("host");
												    					port = Properties.getProperty("port");
												    					protocol = Properties.getProperty("protocol");
												    					if(StringUtils.isEmpty(protocol)){
												    						protocol = "http";
												    					}
													    				String configName = configuration.getName() + configuration.getType() + configuration.getEnvName();
													    				Pattern pattern = Pattern.compile("\\s+");
													    				Matcher matcher = pattern.matcher(configName);
													    				boolean check = matcher.find();
													    				String configNameForId = matcher.replaceAll("");
													    				urls.put(configNameForId, protocol +","+ host + "," + port);
																	%> 
																		<img src="images/icons/inprogress.png" alt="status-up" title="Loading" id="isAlive<%= configNameForId %>">
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
		hideProgressBar();
		accordion();
		
		<% if (CollectionUtils.isEmpty(envs)) { %>
			$("input[name=configAdd]").attr("disabled", "disabled");
			$("#configAdd").removeClass("btn-primary"); 
	        $("#configAdd").addClass("btn-disabled");
		<% } else { %>
		  	$("input[name=configAdd]").removeAttr("disabled");
		  	$("#configAdd").addClass("btn-primary");
			$("#configAdd").removeClass("btn-disabled");
		<% } %>
		
		if ($('.table_div').find("input[type='checkbox']:checked").length < 1) {
			$("input[name=deleteBtn]").attr("disabled", "disabled");
			$("#deleteBtn").removeClass("btn-primary"); 
	        $("#deleteBtn").addClass("btn-disabled");
		} else {
			$("input[name=deleteBtn]").removeAttr("disabled");
			$("#deleteBtn").addClass("btn-primary");
			$("#deleteBtn").removeClass("btn-disabled");
		}
		
		<% 
			if(urls != null) {
	    	Iterator iterator = urls.keySet().iterator();  
				while (iterator.hasNext()) {
				String id = iterator.next().toString();  
				String url = urls.get(id).toString();
	 	%>
				isConnectionAlive('<%= url%>', '<%= id%>');
		<%
		    	}
			}
		%>
	});

	function isConnectionAlive(url, id) {
	    $.ajax({
	    	url : 'connectionAliveCheck',
	    	data : {
	    		'url' : url,
	    	},
	    	type : "get",
	    	datatype : "json",
	    	success : function(data) {
	    		if($.trim(data) == 'true') {
	    			$('#isAlive' + id).attr("src","images/icons/status-up.png");
	    			$('#isAlive' + id).attr("title","Alive");
	    		}
				if($.trim(data) == 'false') {
					$('#isAlive' + id).attr("src","images/icons/status-down.png");
					$('#isAlive' + id).attr("title","Down");
				}
			}
		});
	}
	
</script>
