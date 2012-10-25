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
<%@ page import="com.photon.phresco.framework.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.framework.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
    Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
    ApplicationInfo selectedInfo = null;
    String projectCode = null;
    if(project != null) {
        selectedInfo = project.getApplicationInfo();
        projectCode = selectedInfo.getCode();
    }
	List<Environment> envInfoValues = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
   
    List<SettingsInfo> configurations = (List<SettingsInfo>)request.getAttribute("configuration");
	Map<String, String> urls = new HashMap<String, String>();
%>


<form id="formConfigList" class="configList">
    
    <div class="operation">
    	<!-- Add Configuration Button --> 
		<input type="button" class="btn btn-primary" name="configAdd" id="configAdd" 
	         onclick="loadContent('addConfiguration', $('#formConfigList, #formCustomers, #formAppMenu'), $('#subcontainer'));" 
		         value="<s:text name='lbl.btn.add'/>"/>

		<!-- Delete Configuration Button -->
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.delete'/>"
			onclick="showDeleteConfirmation('<s:text name='lbl.delete'/>');"/>

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
    
    
    <% if (CollectionUtils.isEmpty(configurations)) { %>
		<div class="alert alert-block">
			<s:text name='lbl.err.msg.list.config'/>
		</div>
    <% } else { %>	
		<div class="table_div">
			<div class="fixed-table-container">
				<div class="header-background"> </div>
				<div class="fixed-table-container-inner">
					<table cellspacing="0" class="zebra-striped">
						<thead>
								<tr>
									<th class="first">
										<div class="th-inner tablehead">
											<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this,$('.selectedProjects'), false);">
										</div>
									</th>
									<th class="second">
										<div class="th-inner tablehead"><s:label key="lbl.name" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.desc" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.type" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.environment" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.status" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.clone" theme="simple"/></div>
									</th>
								</tr>
						</thead>
						
						<tbody>
						  	<%
								for (SettingsInfo configuration : configurations) {
									ApplicationInfo projectInfo = project.getApplicationInfo();
							%>
								<tr>
									<td class="checkboxwidth">
										<input type="checkbox" class="check selectedProjects" name="selectedProjects" value="<%= configuration.getName() %>">
									</td>
									<td class="nameConfig">
										<a href="#" onclick="editApplication('<%= configuration.getName() %>');" name="edit">
											<%= configuration.getName() %>
										</a>
									</td>
									<td class="descConfig">
										<%= configuration.getDescription() %>
									</td>
									<td class="hoverAppliesTo">
										<%= configuration.getType() %>
									</td>
									<td class="hoverAppliesTo">
										<%= configuration.getEnvName() %>
									</td>
									<td id="icon-width">
										<a href="#" id="projectUpdate" class="iconsCenterAlign"><img id="<%= configuration.getName()  %>" class="projectUpdate" 
											src="images/icons/inprogress.png" title="Update" class="iconSizeinList"/></a>
									</td>
									<td id="icon-width">
										<a href="#" id="projectUpdate" class="iconsCenterAlign"><img id="<%= configuration.getName()  %>" class="projectUpdate" 
											src="images/icons/clone.png" title="Update" class="iconSizeinList"/></a>
									</td>
								</tr>
							<% 
								}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<% } %>
</form>

<script type="text/javascript">
	yesnoPopup($("#addEnvironments"), 'openEnvironmentPopup', "<s:text name='lbl.environment'/>", 'createEnvironment');
	
	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
	});
	
	function popupOnOk(self) {
		var envs = [];
		$('[name="envNames"]').each(function() {
			envs.push($(this).val());
		});
		
		var basicParams = getBasicParamsAsJson();
		var params = '{' + basicParams + ', "environments": [' + envs.join(',') + ']}';
		var url = $(self).attr('id');

		$.ajax({
			url : url,
			data : params,
			type : "POST",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			success : function(data) {
			}
		});	
	}

</script>
