<%--

    Framework Web Archive

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.actions.applications.Projects"%>

<%
    Projects projectsObj = new Projects(); 
	List<ProjectInfo> projects = (List<ProjectInfo>) request.getAttribute(FrameworkConstants.REQ_PROJECTS);
	String recentProjectId = (String) request.getAttribute(FrameworkConstants.REQ_RECENT_PROJECT_ID);
	String recentAppId = (String) request.getAttribute(FrameworkConstants.REQ_RECENT_APP_ID);
	String uiType = (String) request.getAttribute(FrameworkConstants.REQ_UI_TYPE);
	String uiTypeClass = "";
	if (FrameworkConstants.SIMPLE_UI.equals(uiType)) {
		uiTypeClass = "hideContent";
	}
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
		<%
			String checkedStr = "";
			for (ProjectInfo project : projects) {
		%>
				<div class="theme_accordion_container">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion closereg">
									<span>
										<input type="checkbox" <%= checkedStr %> id="<%= project.getId() %>" class="accordianChkBox" 
											onclick="checkAllEvent(this, $('.<%= project.getId() %>'), false);"/>
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
									    				<th class="no-left-bottom-border table-pad repoTh <%= uiTypeClass %>">
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
																		<input type="checkbox" class="check <%= project.getId() %>" appId="<%= appInfo.getId() %>" name="selectedAppInfo" value='<%= gson.toJson(appInfo) %>'
																			<%= checkedStr %> onclick="checkboxEvent($('.<%= project.getId() %>'), $('#<%= project.getId() %>'));">
																	</td>
																	<td class="no-left-bottom-border table-pad">
																		<a href="#" id="appNameInHover" onclick="editApplication('<%= project.getId() %>', '<%= appInfo.getId() %>');" name="edit">
																			<%= appInfo.getName() %>
																		</a>
																	</td>
																	<td class="no-left-bottom-border table-pad">
																		<span id="descriptionInHover">
																			<%= project.getDescription() %>
																		</span>
																	</td>
																	<td class="no-left-bottom-border table-pad">
																		<%= projectsObj.getTechNamefromTechId(appInfo.getTechInfo().getId()) %>
																	</td>
																	<td class="no-left-bottom-border table-pad">
																		<a href="#" id="pdfPopup">
																			<img id="<%= appInfo.getCode() %>" class="pdfCreation" src="images/icons/print_pdf.png" additionalParam="projectId=<%= project.getId() %>&appId=<%= appInfo.getId() %>&fromPage=All" 
																				title="Generate Report" class="iconSizeinList"/>
																		</a>
																	</td>
																	<td class="no-left-bottom-border table-pad repo-tab-width repoTd <%= uiTypeClass %>">
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
	
	//To open the recently opened project's accordion and check the check boxes accordingly
	var recentProjectId = '<%= recentProjectId %>';
	var recentAppId = '<%= recentAppId %>';
	if (recentProjectId != undefined && !isBlank(recentProjectId)) {
		$("#" + recentProjectId).parent().parent().removeClass('closereg').addClass('openreg');
		$("#" + recentProjectId).parent().parent().next('.mfbox').show();
		$("." + recentProjectId).each(function () {
			if ($(this).attr("appId") === recentAppId) {
				$(this).attr("checked", true);
				checkboxEvent($('.' + recentProjectId), $('#' + recentProjectId));
				return false;
			}
		});
	}
	if (recentProjectId != undefined && !isBlank(recentProjectId) && isBlank(recentAppId)) {
		$("#" + recentProjectId).attr("checked", true);
		$("." + recentProjectId).attr("checked", true);
	}
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
 		$(".table_div").scrollbars();  
	}

	$(document).ready(function() {
		hideLoadingIcon();
		hideProgressBar();
		toDisableCheckAll();
		deleteButtonStatus();
		
		$("td a[id ='appNameInHover']").text(function(index) {
	        return textTrim($(this), 45);
	    });
		
		$("td [id ='descriptionInHover']").text(function(index) {
	        return textTrim($(this), 25);
	    });
		
		$('#customerList').show();
		
		$('#importAppln').click(function() {
			var params = "action=import";
			yesnoPopup('importAppln', '<s:text name="lbl.app.import"/>', 'importUpdateAppln','<s:text name="lbl.app.import"/>', '', params);
    	});
		
		$('.projectUpdate').click(function() {
			showLoadingIcon();
			var params = $(this).attr("additionalParam");
			loadContent('repoExistCheckForUpdate', $('#formCustomers'), '', params, true);	
    	});
		
		$('.addProject').click(function() {
			var params = $(this).attr("additionalParam");
			yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.add.to.repo"/>', 'importUpdateAppln','<s:text name="lbl.app.add.to.repo"/>', '', params);
    	});
		
		$('.commitProject').click(function() {
			showLoadingIcon();
			var params = $(this).attr("additionalParam");
			loadContent('repoExistCheckForCommit', $('#formCustomers'), '', params, true);		
    	});
		
    	$('.pdfCreation').click(function() {
    		var params = $(this).attr("additionalParam");
    		params = params.concat("&actionType=");
    		params = params.concat("AllPdfReport");
    		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params,"pdfReport");
    	});
    	
    	//Trigerred when add btn is clicked
    	$('#addProject').click(function() {
    		showLoadingIcon();
    		loadContent('addProject', $('#formCustomers'), $('#container'), '', '', true);		
    	});
    	
    	$('.table_div').find("input[type='checkbox']").change(function() {
			deleteButtonStatus();
		});
    	
    	//To select the simpleUI/advanceUI option
		$("#simpleUI, #advanceUI").unbind();
		$("#simpleUI, #advanceUI").click(function() {
			var ancId = $(this).attr("id");
			$(".selectedIcn").hide();
			$("#" + ancId + "Img").show();
			$("#uiType").val(ancId);
			var displayProp = "";
			if ('<%= FrameworkConstants.SIMPLE_UI %>' === ancId) {
				$(".repoTh").hide();
				$(".repoTd").hide();
			} else if ('<%= FrameworkConstants.ADVANCE_UI %>' === ancId) {
				$(".repoTh").show();
				$(".repoTd").show();
			}
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
 			if (validateImportAppl()) {
 				importUpdateApp();
 			}
 		} else if (okUrl === "printAsPdf") {
 			printPdfPreActions();
			loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false, true);
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
		//to check for project already checked-in for commit
		if (pageUrl == 'repoExistCheckForCommit' || pageUrl == 'repoExistCheckForUpdate') {
			//if already exists
			if (data.repoExistForCommit) {
				var params = "projectId=";
				params = params.concat(data.projectId);
				params = params.concat("&appId=");
				params = params.concat(data.appId);
				params = params.concat("&action=");
				params = params.concat(data.action);
				hideLoadingIcon();
				yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.commit"/>', 'importUpdateAppln','<s:text name="lbl.app.commit"/>', '', params);
			} else if (data.repoExistForUpdate) {
				var params = "projectId=";
				params = params.concat(data.projectId);
				params = params.concat("&appId=");
				params = params.concat(data.appId);
				params = params.concat("&action=");
				params = params.concat(data.action);
				hideLoadingIcon();
				yesnoPopup('updateProjectPopup', '<s:text name="lbl.app.update"/>', 'importUpdateAppln','<s:text name="lbl.app.update"/>', '', params);
			} else {//warning message if not exist
				hideLoadingIcon();
				$('#popupPage').modal('show');
				$('#errMsg').html("");
				$('#successMsg').html("");
				$('#updateMsg').html("");
				$('#popupTitle').html('<s:text name="lbl.app.warnin.title"/>');
				$('#popup_div').empty();
				if (pageUrl == 'repoExistCheckForCommit') {
					$('#popup_div').html('<s:text name="lbl.app.warnin.message.for.commit"/>');
				} else if(pageUrl == 'repoExistCheckForUpdate') {
					$('#popup_div').html('<s:text name="lbl.app.warnin.message.for.update"/>');
				}
				$('.popupOk').hide();
				$('.popupCancel').hide();
				$('.popupClose').show();
				hidePopuploadingIcon();
			}
		} else if(pageUrl == "importSVNProject" || pageUrl == "importGITProject" || pageUrl == "importBitKeeperProject" || pageUrl == "updateSVNProject" || pageUrl == "updateGITProject"
			|| pageUrl == "updateBitKeeperProject" || pageUrl == "addSVNProject" || pageUrl == "addGITProject" || pageUrl == "commitSVNProject" || pageUrl == "commitBitKeeperProject" || pageUrl == "commitGITProject") {
			checkError(pageUrl, data);
		} else if(pageUrl == 'fetchLogMessages') {
			if (data.logMessage != "") {
				hidePopuploadingIcon();
				$('#errMsg').html(data.logMessage);
			} else if (data.logMessage == "") {
				$('#errMsg').html("");
				selectList(data);
			}
		} 
		console.log("success event called !!! ");
	}
	
	//To handle the cancel btn events
	function popupOnCancel(obj) {
		var params = $(obj).attr("params");
		loadContent("killProcess", '', '', params);
	}
</script>