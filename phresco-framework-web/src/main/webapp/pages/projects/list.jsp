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

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
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
		<input type="button" class="btn btn-primary" name="addProject" id="addProject" value="<s:text name='lbl.projects.add'/>"
			onclick="loadContent('addProject', $('#formCustomers'), $('#container'));"/>

		<input type="button" class="btn btn-primary" name="importAppln" id="importAppln" value="<s:text name='lbl.app.import'/>"
			onclick="loadContent('importAppln', $('#formProjectList'), $('#subcontainer'));"/>
		         
		<input type="button" class="btn" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>" data-toggle="modal" href="#popupPage"/>
			
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
			<s:text name='lbl.err.msg.list.project'/>
		</div>
    <% } else { %>	
		<div class="table_div">
		<% for (ProjectInfo project : projects) { %>
			<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container">
							<span class="siteaccordion closereg">
								<span>
									<input type="checkbox" id="<%= project.getId() %>" class="accordianChkBox" onclick="checkAllEvent(this, $('.<%= project.getId() %>'), false);"/>
									<a class="vAlignSub"><%= project.getName() %></a>
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
								    					<s:label key="lbl.technolgoy" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.print" cssClass="labelbold"/>
								    				</th>
								    				<th class="no-left-bottom-border table-pad">
								    					<s:label key="lbl.update" cssClass="labelbold"/>
								    				</th>
								    			</tr>
								    		</thead>
								    		<tbody>
								    			<%
													List<ApplicationInfo> appInfos = project.getAppInfos();
													if (CollectionUtils.isNotEmpty(appInfos)) {
														for (ApplicationInfo appInfo : appInfos) {
												%>
															<tr>
																<td class="no-left-bottom-border table-pad">
																	<input type="checkbox" class="check <%= project.getId() %>" name="selectedProjectId" value="<%= appInfo.getCode() %>"
																		onclick="checkboxEvent($('.<%= project.getId() %>'), $('#<%= project.getId() %>'));">
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" onclick="editApplication('<%= project.getId() %>', '<%= appInfo.getId() %>');" name="edit">
																		<%= appInfo.getName() %>
																	</a>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<%= project.getDescription() %>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<%= appInfo.getTechInfo().getId() %>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" id="pdfPopup">
																		<img id="<%= appInfo.getCode() %>" class="pdfCreation" src="images/icons/print_pdf.png"
																			title="Generate Report" class="iconSizeinList"/>
																	</a>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" id="projectUpdate">
																		<img id="<%= appInfo.getCode() %>" class="projectUpdate" src="images/icons/refresh.png"
																			title="Update" class="iconSizeinList"/>
																	</a>
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
	accordion();//To create the accordion
	
	confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.project"/>', 'build','<s:text name="lbl.btn.ok"/>');
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
 		$(".table_div").scrollbars();  
	}

	$(document).ready(function() {
		toDisableCheckAll();
		
		hideLoadingIcon();//To hide the loading icon
   	});
	
    function editApplication(projectId, appId) {
		var params = "projectId=";
		params = params.concat(projectId);
		params = params.concat("&appId=");
		params = params.concat(appId);
		loadContent("loadMenu", $("#formCustomers"), $('#container'), params);
	}
    
 	function popupOnOk(okUrl) {
 		var params = $("#formCustomers").serialize();
 		loadContent("deleteProject", $("#formProjectList"), $('#container'), params);
 	}
</script>