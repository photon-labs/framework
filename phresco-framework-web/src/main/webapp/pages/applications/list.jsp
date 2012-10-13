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

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
	List<ProjectInfo> projects = (List<ProjectInfo>) request.getAttribute(FrameworkConstants.REQ_PROJECTS);
%>

<div class="page-header">
	<h1 style="float: left;">
		<s:text name="lbl.projects"/>
	</h1>
</div>

<form id="formProjectList" class="projectList">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="addProject" id="addProject" 
	         onclick="loadContent('projectDetails', $('#formProjectList'), $('#container'));" 
		         value="<s:text name='lbl.projects.add'/>"/>
		<input type="button" class="btn btn-primary" name="importAppln" id="importAppln" 
	         onclick="loadContent('importAppln', $('#formProjectList'), $('#subcontainer'));" 
		         value="<s:text name='lbl.applications.import'/>"/>
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.delete'/>"
			onclick="showDeleteConfirmation('<s:text name='lbl.delete'/>');"/>
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
	
	<% if (CollectionUtils.isEmpty(projects)) { %>
		<div class="alert alert-block">
			<s:text name='alert.msg.project.not.available'/>
		</div>
    <% } else { %>	
		<div class="table_div">
			<div class="fixed-table-container">
				<div class="header-background"></div>
				<div class="fixed-table-container-inner">
					<table cellspacing="0" class="zebra-striped">
						<thead>
							<tr>
								<th class="first">
									<div class="th-inner tablehead">
										<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" 
											onclick="checkAllEvent(this,$('.selectedProjects'), false);">
									</div>
								</th>
								<th class="second">
									<div class="th-inner tablehead"><s:label key="lbl.name" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label key="lbl.desc" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label key="lbl.technolgoy" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label key="lbl.print" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label key="lbl.update" theme="simple"/></div>
								</th>
							</tr>
						</thead>
						
						<tbody>
						  	<%
						  		for (ProjectInfo project : projects) {
					  				List<ApplicationInfo> appInfos = project.getAppInfos();
					  				if (CollectionUtils.isNotEmpty(appInfos)) {
					  					for (ApplicationInfo appInfo : appInfos) {
							%>
										<tr>
											<td class="checkboxwidth">
												<input type="checkbox" class="check selectedProjects" name="selectedProjects" value="<%= appInfo.getCode() %>">
											</td>
											<td class="nameConfig">
												<a href="#" onclick="editApplication('<%= project.getId() %>', '<%= appInfo.getId() %>');" name="edit">
													<%= project.getName() %>
												</a>
											</td>
											<td class="descConfig">
												<%= project.getDescription() %>
											</td>
											<td class="hoverAppliesTo">
												<%= appInfo.getDescription() %>
											</td>
											<td id="icon-width">
												<a href="#" id="pdfPopup" class="iconsCenterAlign">
													<img id="<%= appInfo.getCode() %>" class="pdfCreation" src="images/icons/print_pdf.png"
														title="Generate Report" class="iconSizeinList"/>
												</a>
											</td>
											<td id="icon-width">
												<a href="#" id="projectUpdate" class="iconsCenterAlign">
													<img id="<%= appInfo.getCode() %>" class="projectUpdate" src="images/icons/refresh.png"
														title="Update" class="iconSizeinList"/>
												</a>
											</td>
										</tr>
							<%
					  					}
					  				}
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
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
		$(".fixed-table-container-inner").scrollbars();  
	}

	$(document).ready(function() {
		toDisableCheckAll();
		enableScreen();
		
		/*$("td[id = 'hoverAppliesTo']").text(function(index) {
	        return textTrim($(this));
	    });*/
		
   	});
	
    function editApplication(projectId, appId) {
		var params = "projectId=";
		params = params.concat(projectId);
		params = params.concat("&appId");
		params = params.concat(appId);
		loadContent("loadMenu", $("#formCustomers"), $('#container'), params);
	}
    
 	// This method calling from confirmDialog.jsp
    function continueDeletion() {
    	confirmDialog('none','');
    	loadContent('configtempDelete', $('#formProjectList'), $('#container'));
    }
</script>