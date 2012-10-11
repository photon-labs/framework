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
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%@ include file="progress.jsp" %>

<%
	List<Project> projects = (List<Project>) request.getAttribute(FrameworkConstants.REQ_PROJECTS);
	String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
%>

<div class="page-header">
	<h1 style="float: left;">
		<s:text name="lbl.projects"/>
	</h1>
</div>

<form id="formProjectList" class="projectList">

	<div class="operation">
		<input type="button" class="btn btn-primary" name="configTemplate_add" id="configtempAdd" 
	         onclick="loadContent('applicationDetails', $('#formProjectList'), $('#container'));" 
		         value="<s:text name='lbl.projects.add'/>"/>
		<input type="button" class="btn btn-primary" name="configTemplate_add" id="configtempAdd" 
	         onclick="loadContent('configtempAdd', $('#formProjectList'), $('#subcontainer'));" 
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
			<s:text name='alert.msg.configTemp.not.available'/>
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
											<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this,$('.configtempltes'), false);">
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
								for (Project project : projects) {
									ApplicationInfo projectInfo = project.getApplicationInfo();
							%>
								<tr>
									<td class="checkboxwidth">
										<input type="checkbox" class="check" name="selectedProjects" value="<%= projectInfo.getCode() %>">
									</td>
									<td class="nameConfig">
										<a href="#" onclick="editConfigTemp('<%= projectInfo.getId() %>');" name="edit" id="" >
											<%= projectInfo.getName() %>
										</a>
									</td>
									<td class="descConfig">
										<%= projectInfo.getDescription() %>
									</td>
									<td class="hoverAppliesTo">
										<%= projectInfo.getDescription() %>
									</td>
									<td id="icon-width">
										<a href="#" id="pdfPopup" class="iconsCenterAlign"><img id="<%= projectInfo.getCode() %>" class="pdfCreation" 
											src="images/icons/print_pdf.png" title="Generate Report" class="iconSizeinList"/></a>
									</td>
									<td id="icon-width">
										<a href="#" id="projectUpdate" class="iconsCenterAlign"><img id="<%= projectInfo.getCode() %>" class="projectUpdate" 
											src="images/icons/refresh.png" title="Update" class="iconSizeinList"/></a>
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
	
	<!-- Hidden Fields -->
	<input type="hidden" name="customerId" value="<%= customerId %>">
</form>
