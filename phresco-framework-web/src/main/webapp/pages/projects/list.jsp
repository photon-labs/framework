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
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
	List<ProjectInfo> projects = (List<ProjectInfo>) request.getAttribute(FrameworkConstants.REQ_PROJECTS);
	Gson gson = new Gson();
%>

<div class="page-header">
	<h1 style="float: left;">
		<s:text name="lbl.projects"/>
	</h1>
</div>

<form id="formProjectList" class="projectList">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="addProject" id="addProject" value="<s:text name='lbl.projects.add'/>"/>

		<input type="button" class="btn btn-primary" name="importAppln" id="importAppln" value="<s:text name='lbl.app.import'/>"/>
		         
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
									<a class="vAlignSub" onclick="editProject('<%= project.getId() %>');"><%= project.getName() %></a>
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
								    					<s:label key="lbl.repository" cssClass="labelbold"/>
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
																	<input type="checkbox" class="check <%= project.getId() %>" name="selectedAppInfo" value='<%= gson.toJson(appInfo) %>'
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
																		<img id="<%= appInfo.getCode() %>" class="pdfCreation" src="images/icons/print_pdf.png" additionalParam="projectId=<%= project.getId() %>&appId=<%= appInfo.getId() %>&fromPage=All" 
																			title="Generate Report" class="iconSizeinList"/>
																	</a>
																</td>
																<td class="no-left-bottom-border table-pad">
																	<a href="#" id="repoImport">
																		<img id="<%= appInfo.getCode() %>" class="addProject" src="images/icons/add_icon.png"
																			 additionalParam="projectId=<%= project.getId() %>&appId=<%= appInfo.getId() %>&action=add" title="Add to repo" class="iconSizeinList"/>
																	</a>
																	<a href="#" id="repoImport">
																		<img id="<%= appInfo.getCode() %>" class="commitProject" src="images/icons/commit_icon.png"
																			 additionalParam="projectId=<%= project.getId() %>&appId=<%= appInfo.getId() %>&action=commit" title="Commit" class="iconSizeinList"/>
																	</a>
																	<a href="#" id="projectUpdate">
																		<img id="<%= appInfo.getCode() %>" class="projectUpdate" src="images/icons/refresh.png"
																			 additionalParam="projectId=<%= project.getId() %>&appId=<%= appInfo.getId() %>&action=update" title="Update" class="iconSizeinList"/>
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
	
	confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.project"/>', 'deleteProject','<s:text name="lbl.btn.ok"/>');
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
 		$(".table_div").scrollbars();  
	}

	$(document).ready(function() {
		hideLoadingIcon();
		hideProgressBar();
		toDisableCheckAll();
		
		$('#importAppln').click(function() {
			var params = "action=import";
			yesnoPopup('importAppln', '<s:text name="lbl.app.import"/>', 'importUpdateAppln','<s:text name="lbl.app.import"/>', '', params);
    	});
		
		$('.projectUpdate').click(function() {
			var params = $(this).attr("additionalParam");
			yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.update"/>', 'importUpdateAppln','<s:text name="lbl.app.update"/>', '', params);
    	});
		
		$('.addProject').click(function() {
			var params = $(this).attr("additionalParam");
			yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.add.to.repo"/>', 'importUpdateAppln','<s:text name="lbl.app.add.to.repo"/>', '', params);
    	});
		
		$('.commitProject').click(function() {
			var params = $(this).attr("additionalParam");
			yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.commit"/>', 'importUpdateAppln','<s:text name="lbl.app.commit"/>', '', params);
    	});
		
    	$('.pdfCreation').click(function() {
    		var params = $(this).attr("additionalParam");
    		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
    	});
    	
    	//Trigerred when add btn is clicked
    	$('#addProject').click(function() {
    		showLoadingIcon();
    		loadContent('addProject', $('#formCustomers'), $('#container'), '', '', true);		
    	});
   	});
	
    function editApplication(projectId, appId) {
    	showLoadingIcon();
		var params = "projectId=";
		params = params.concat(projectId);
		params = params.concat("&appId=");
		params = params.concat(appId);
		loadContent("loadMenu", $("#formCustomers"), $('#container'), params, '', true);
	}
    
    function editProject(projectId) {
    	showLoadingIcon();
		var params = "projectId=";
		params = params.concat(projectId);
		loadContent("editProject", $("#formCustomers"), $('#container'), params, '', true);
	}
    
 	function popupOnOk(obj) {
 		//$("#popupPage").modal('hide');
 		var okUrl = $(obj).attr("id");
 		if (okUrl == "importUpdateAppln") {
 			if(validateImportAppl()) {
 				importUpdateApp();
 			} 			
 		} else if (okUrl === "printAsPdf") {
			// show popup loading icon
			showPopuploadingIcon();
			loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), getBasicParams(), false, true);
		} else if (okUrl === "deleteProject") {
			$("#popupPage").modal('hide');
			// show popup loading icon
 			showProgressBar('<s:text name="progress.txt.delete.app"/>');
 			var basicParams = getBasicParamsAsJson();
 			appInfos = [];
 			$('input[name="selectedAppInfo"]:checked').each(function() {
 				appInfos.push($(this).val());	
 			});
 			var params = '{' + basicParams + ', "selectedAppInfos": [' + appInfos.join(',') + ']}';
 			loadJsonContent("deleteProject", params, $('#container'));
		}
 	}
 	
	function popupOnClose(obj) {
		console.log("popup on close called ");
	}
	
	function successEvent(pageUrl, data) {
		console.log("success event called !!! ");
	}
</script>