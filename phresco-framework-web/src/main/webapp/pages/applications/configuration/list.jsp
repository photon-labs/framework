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
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<%@ page import="com.photon.phresco.framework.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.framework.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
	List<Environment> envInfoValues = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	Map<String, String> urls = new HashMap<String, String>();
%>


<form id="formConfigList" class="configList">
    
    <div class="operation">
    	<!-- Add Configuration Button --> 
		<input type="button" class="btn btn-primary" name="configAdd" id="configAdd" 
	         onclick="loadContent('addConfiguration', $('#formCustomers, #formAppMenu'), $('#subcontainer'));" 
		         value="<s:text name='lbl.btn.add'/>"/>

		<!-- Delete Configuration Button -->
		<%-- <input type="button" class="btn" id="del" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>"
			onclick="showDeleteConfirmation('<s:text name='lbl.delete'/>');"/> --%>
			
		<input type="button" class="btn" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>" data-toggle="modal" href="#popupPage"/>

		<!-- Environment Buttton -->
	    <a data-toggle="modal" id="addEnvironments" href="#popupPage" class="btn btn-primary"><s:text name='lbl.app.config.environments'/></a>
		         
		<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg" >
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error"  id="errormsg">
				<s:actionerror />
			</div>
		</s:if>
    </div>
    
    
    <% if (CollectionUtils.isEmpty(envInfoValues)) { %>
		 <div class="alert alert-block">
			<s:text name='lbl.err.msg.list.config'/>
		</div> 
    <% } else { %>	
    	<div class="table_div">
		<% for (Environment envInfoValue : envInfoValues) { %>
			<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container">
							<span class="siteaccordion closereg">
								<span>
									<input type="checkbox" value="" id="<%=envInfoValue.getName() %>" class="accordianChkBox" name="checkAllAuto" onclick="checkAllEvent(this,$('.<%=envInfoValue.getName() %>'), false);"/>
									<a class="vAlignSub"><%=envInfoValue.getName() %></a>
								</span>
							</span>
							<div class="mfbox siteinnertooltiptxt hideContent">
								<div class="scrollpanel">
									<section class="scrollpanel_inner">
								    	<table class="table table-bordered table_border">
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
														List<Configuration> configurations = envInfoValue.getConfigurations();
								    					if (CollectionUtils.isNotEmpty(configurations)) {
															for (Configuration configuration : configurations) {
												%>
															<tr>
																<td class="no-left-bottom-border table-pad">
																	<input type="checkbox" class="check <%=envInfoValue.getName() %>" name="selectedProjects" value="<%= configuration.getName() %>"
																	onclick="checkboxEvent($('.<%=envInfoValue.getName() %>'), $('#<%=envInfoValue.getName() %>'));">
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" onclick="editConfiguration('<%= envInfoValue.getName() %>', '<%= configuration.getType() %>','<%= configuration.getName() %>');" 
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
																	<a href="#" ><img id="<%= configuration.getName()  %>" class="projectUpdate" 
																		src="images/icons/clone.png" title="Update" class="iconSizeinList"/></a>
																</td> 
															</tr>
													<%
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
</form>

<script type="text/javascript">
	yesnoPopup($("#addEnvironments"), 'openEnvironmentPopup', "<s:text name='lbl.environment'/>", 'createEnvironment');
	confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.configuration"/>', '','<s:text name="lbl.btn.ok"/>');
	
	$(document).ready(function() {
		accordion();
		hideLoadingIcon();//To hide the loading icon
	});
	
	 function editConfiguration(currentEnvName, currentConfigType, currentConfigName) {
		 	var params = getBasicParams();
			params = params.concat("&currentEnvName=");
			params = params.concat(currentEnvName);
			params = params.concat("&currentConfigType=");
			params = params.concat(currentConfigType);
			params = params.concat("&currentConfigName=");
			params = params.concat(currentConfigName);
			loadContent("editConfiguration", $("#formConfigAdd"), $('#subcontainer'), params);
	}
	 
	function popupOnOk(self) {
		var envs = [];
		$('[name="envNames"]').each(function() {
			envs.push($(this).val());
		});
		
		var basicParams = getBasicParamsAsJson();
		var params = '{' + basicParams + ', "environments": [' + envs.join(',') + ']}';
		var url = $(self).attr('id');
		loadJsonContent(url, params, $('#subcontainer'));
	}

</script>
