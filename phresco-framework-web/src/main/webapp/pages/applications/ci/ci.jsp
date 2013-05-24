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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.IOException"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.collections.MapUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.exception.PhrescoException"%>
<%@ page import="com.photon.phresco.framework.model.CIJob" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.model.CIBuild"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>
<%@ page import="com.photon.phresco.framework.actions.util.FrameworkActionUtil"%>

<style type="text/css">
    #warningmsg {
     	display: none;
     	width: auto; 
		left: 521px;
     	position: absolute;
     	text-align: center;
     	padding: 4px 14px;
     	margin-top: 5px;
     	float: right;
     	font-weight: bold;
    }
</style>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String appId  = null;
    if(appInfo != null) {
    	appId  = appInfo.getId();
    }
    String jenkinsAlive = request.getAttribute(FrameworkConstants.CI_JENKINS_ALIVE).toString();
    boolean isAtleastOneJobIsInProgress = false;
    String isBuildTriggeredFromUI = request.getAttribute(FrameworkConstants.CI_BUILD_TRIGGERED_FROM_UI).toString();
    String functioanlTestTool = (String) request.getAttribute(FrameworkConstants.REQ_FUNCTEST_SELENIUM_TOOL);
    Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
%>

<s:if test="hasActionMessages()">
    <div class="alert alert-message success" id="successmsg" style="margin-top: 3px; margin-left: 100px;">
        <s:actionmessage />
    </div>
</s:if>

<div class="alert alert-block ciAlertMsg"  id="warningmsg">
	<s:label cssClass="labelWarn ciLabelWarn" key="ci.warn.message" />
</div>

<form action="CIBuildDelete" name="ciBuilds" id="deleteObjects" method="post" class="configurations_list_form ciFormJob">
    <div class="operation ciOperationDiv">
    	<div class="ciOperationEleme">
    		<%
	    		String per_disabledStr = "";
				String per_disabledClass = "btn-primary";
				if (permissions != null && !permissions.canManageCIJobs()) {
					per_disabledStr = "disabled";
					per_disabledClass = "btn-disabled";
				}
    		%>
    		
	        <input id="setup" type="button" value="<s:text name="lbl.setup"/>" class="btn <%= per_disabledClass %>" <%= per_disabledStr %>>
	        <input id="startJenkins" type="button" value="<s:text name="lbl.start"/>" class="btn <%= per_disabledClass %>" <%= per_disabledStr %>>
	        <input id="stopJenkins" type="button" value="<s:text name="lbl.stop"/>" class="btn" disabled="disabled" >
	        <input id="configure" type="button" value="<s:text name="lbl.configure"/>" class="btn <%= per_disabledClass %>" <%= per_disabledStr %>>
	        <input id="build" type="button" value="<s:text name="lbl.build"/>" class="btn" disabled="disabled" onclick="buildCI();">
	        <input id="deleteBuild" type="button" value="<s:text name="lbl.deletebuild"/>" class="btn" disabled="disabled">
	        <input id="deleteJobBtn" type="button" value="<s:text name="lbl.deletejob"/>" class="btn"  disabled="disabled" data-toggle="modal" href="#popupPage"/>
	        <input id="emailConfiguration" type="button" value="<s:text name="lbl.email.configuration"/>" class="btn <%= per_disabledClass %>" <%= per_disabledStr %>>
	        <input id="confluenceConfiguration" type="button" value="<s:text name="lbl.confluence.configuration"/>" class="btn <%= per_disabledClass %>" <%= per_disabledStr %>>
        </div>
    </div>
    
    <% 
	    boolean isExistJob = true;
        int noOfJobsIsinProgress = Integer.parseInt(request.getAttribute(FrameworkConstants.CI_NO_OF_JOBS_IN_PROGRESS).toString());
        Map<String, List<CIBuild>> existingJobs = (Map<String, List<CIBuild>>)request.getAttribute(FrameworkConstants.REQ_EXISTING_JOBS);
        // when no job's jenkins is alive , have to display no jobs available
        if (MapUtils.isEmpty(existingJobs) ) {
        	isExistJob = false;
    %>
		<!--  jobs not available message -->
        <div class="alert alert-block" >
            <center><s:text name='ci.jobs.error.message'/></center>
        </div>
    <%
		} else {
	%>

		<div id="ciList" class="ciListElem" >
			<div class="scrollCiList ciScrollListElem">
			    <section class="accordion_panel_wid">
			        <div id="CiBuildsList" class="accordion_panel_inner" style="height: 365px">
			        	<%
				        	Iterator iterator = existingJobs.keySet().iterator();  
					    	while (iterator.hasNext()) {
					    	   String jobName = iterator.next().toString();  
					    	   List<CIBuild> builds = existingJobs.get(jobName);
					    	   if(new Boolean(request.getAttribute(FrameworkConstants.CI_BUILD_IS_IN_PROGRESS + jobName).toString()).booleanValue()) {
					    		   isAtleastOneJobIsInProgress = true;
					    	   }
			        	%>
			            <section class="lft_menus_container">
			                <span class="siteaccordion" id="siteaccordion_active">
			                	<div>
			                		<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
			                		<%
							    		per_disabledStr = "disabled";
										per_disabledClass = "btn-disabled";
										if (permissions != null && (permissions.canManageCIJobs() || permissions.canExecuteCIJobs())) {
											per_disabledStr = "";
											per_disabledClass = "btn-primary";
										}
						    		%>
	                				<input type="checkbox" class="<%= jobName %>Job" name="Jobs" id="checkBox" <%= per_disabledStr %> value="<%= jobName %>" 
	                					<%= new Boolean(request.getAttribute(FrameworkConstants.CI_BUILD_JENKINS_ALIVE + jobName).toString()).booleanValue() ? "" : "disabled" %>>
			                		&nbsp;&nbsp;<%= jobName %> &nbsp;&nbsp;
								</div>
			                </span>
			                <div class="mfbox siteinnertooltiptxt">
			                    <div class="scrollpanel">
			                        <section class="scrollpanel_inner">
			                        	<%
			                        		if (CollectionUtils.isNotEmpty(builds)) {
			                        	%>
			                        	<table class="download_tbl">
				                        	<thead>
				                            	<tr class="download_tbl_header">
				                            		<th>
				                            			<%
												    		per_disabledStr = "disabled";
															per_disabledClass = "btn-disabled";
															if (permissions != null && (permissions.canManageCIJobs() || permissions.canExecuteCIJobs())) {
																per_disabledStr = "";
																per_disabledClass = "btn-primary";
															}
											    		%>
														<input type="checkbox" value="<%= jobName %>" class="<%= jobName %>AllBuild" name="allBuilds" <%= per_disabledStr %>>
				                            		</th>
				                            		<th>#</th>
			                            			<th><s:text name="lbl.url"/></th>
			                            			<th><s:text name="lbl.download"/></th>
			                            			<th><s:text name="lbl.time"/></th>
			                            			<th><s:text name="lbl.status"/></th>
			                            		</tr>
				                            </thead>
				                            
				                        	<tbody id="<%= jobName %>" class="jobBuildsList">
				                        		<%
				                        			for (CIBuild build : builds) {
				                        		%>
						                    		<tr>
						                    			<td>
						                    				<%
													    		per_disabledStr = "disabled";
																per_disabledClass = "btn-disabled";
																if (permissions != null && (permissions.canManageCIJobs() || permissions.canExecuteCIJobs())) {
																	per_disabledStr = "";
																	per_disabledClass = "btn-primary";
																}
												    		%>
						                    				<input type="checkbox" <%= per_disabledStr %> value="<%= jobName %>,<%= build.getNumber() %>" class="<%= jobName %>" name="builds">
						                    			</td>
						                    			<td><%= build.getNumber() %></td>
						                    			<td><a href="<%= build.getUrl() %>" target="_blank"><%= build.getUrl().replace("%20", " ") %></a></td>
						                    			<td style="padding-left: 3%;">
						                    				<%
								                                if(build.getStatus().equals(FrameworkConstants.INPROGRESS))  {
								                            %>
								                                <img src="images/icons/inprogress.png" title="In progress"/>
										              		<% 
								                                } else if(build.getStatus().equals(FrameworkConstants.CI_SUCCESS_FLAG) && StringUtils.isNotEmpty(build.getDownload())) {
								                                	String downloadUrl = build.getUrl()+ FrameworkConstants.CI_JOB_BUILD_ARTIFACT;
								                                	if (StringUtils.isNotEmpty(build.getDownload())) {
								                                		downloadUrl = downloadUrl + FrameworkConstants.FORWARD_SLASH + build.getDownload().replaceAll("\"","");								                                		
								                                	}
								                            %>
										                		<a href="<s:url action='CIBuildDownload'>
												          		     <s:param name="buildDownloadUrl"><%= downloadUrl %></s:param>
												          		     <s:param name="appId"><%= appId %></s:param>
												          		     <s:param name="customerId"><%= customerId %></s:param>
												          		     <s:param name="projectId"><%= projectId %></s:param>
												          		     <s:param name="buildNumber"><%= build.getNumber() %></s:param>
												          		     <s:param name="downloadJobName"><%= jobName %></s:param>
												          		     </s:url>"><img src="images/icons/download.png" title="Download"/>
									                            </a>
						
								                            <%  
								                            	}  else {
									                        %>
								                                <img src="images/icons/downloadFailure.png" title="Not available"/>
								                            <%  
								                            	}
										              		%>
														</td>
						                    			<td><%= build.getTimeStamp() %></td>
						                    			<td>
						                    				<% 
								                                if(build.getStatus().equals(FrameworkConstants.CI_SUCCESS_FLAG)) {
								                            %>
								                                <img src="images/icons/success.png" title="Success">
								                           	<%
								                                } else if(build.getStatus().equals(FrameworkConstants.INPROGRESS))  { 
								                            %>
								                                <img src="images/icons/inprogress.png" title="In progress"/>
								                            <%
								                                } else { 
								                            %>
								                                <img src="images/icons/error.png" title="Failure">
								                            <%  } 
								                            %>
						                    			</td>
						                    		</tr>
					                    		<% 
					                    			}
				                        		%>
				                    		</tbody>
			                        	</table>
			                        	<% 
			                        		} else {
			                        	%>
			                        	    <div class="alert-message block-message warning" >
			                        	    	<%  
			                        	    		String jobAliveStatus = request.getAttribute(FrameworkConstants.CI_BUILD_JENKINS_ALIVE + jobName).toString();
			                        	    		String title = FrameworkActionUtil.getTitle(FrameworkConstants.CI_ERROR + jobAliveStatus + FrameworkConstants.CI_MESSAGE, FrameworkConstants.CI);
			                        	    	%>
		                        	    		<div class="alert alert-block" style="margin-top: 0px;">
			                        	    		<center class="errorMsgLbl">
			                        	    			<%= title %>
			                        	    		</center>
		                        	    		</div>
									        </div>
			                        	<% } %>
			                        </section>
			                    </div>
			                </div>
			            </section>  
			            <%	
			            	}
					    %>
			        </div>
			    </section>
		    </div>
		</div>
    <%
        }
    %>
</form>

<script type="text/javascript">

/* To check whether the device is ipad or not */
if(!isiPad()){
	$("#CiBuildsList").css("height", ($('#tabDiv').height() - $('.ciOperationDiv').height()));
	/* JQuery scroll bar */
	$(".accordion_panel_inner").scrollbars();
}

// buildSize to refresh ci after build completed
var refreshCi = false;
var isJenkinsAlive = false;
var isJenkinsReady = false;

$(document).ready(function() {
	
	$('.siteaccordion').unbind('click');
	accordionOperation();
	// hide popup
	$('#popupPage').modal('hide');

	hideLoadingIcon();
	hidePopuploadingIcon();
	hideProgressBar(); // when deleting builds and jobs
	
	$("input:checkbox[name='allBuilds']").click(function() {
		$("input:checkbox[class='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		$("input:checkbox[value='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		<% if (permissions != null && permissions.canManageCIJobs()) { %>
			enableDisableDeleteButton($(this).val());				
		<% } %>
	});
	
	$("input:checkbox[name='builds']").click(function() {
		var isAllChecked = isAllCheckBoxCheked($(this).attr("class"));
		$("input:checkbox[value='" + $(this).attr("class") +"']").attr('checked', isAllChecked);
		<% if (permissions != null && permissions.canManageCIJobs()) { %>
			enableDisableDeleteButton($(this).attr("class"));
		<% } %>
	});
	
	$("input:checkbox[name='Jobs']").click(function() {
		$("input:checkbox[class='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		$("input:checkbox[value='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		<% if (permissions != null && permissions.canManageCIJobs()) { %>
			enableDisableDeleteButton($(this).val());
		<% } %>
	});
	
	$('#configure').click(function() {
		var showPopup = repopulateConfiurePopup(true);
		if (showPopup) {
			yesnoPopup('configure', '<s:text name="lbl.configure"/>', 'saveJob','<s:text name="lbl.save"/>', $('#deleteObjects'));	
		}
	});
	
	$('#emailConfiguration').click(function() {
		yesnoPopup('showEmailConfiguration', '<s:text name="lbl.email.configuration"/>', 'saveEmailConfiguration','<s:text name="lbl.save"/>', $('#deleteObjects'));
	});
	
	$('#confluenceConfiguration').click(function() {
		yesnoPopup('showconfluenceConfiguration', '<s:text name="lbl.confluence.configuration"/>', 'saveConfluenceConfiguration','<s:text name="lbl.save"/>');
	});
    
    $('#setup').click(function() {
    	progressPopup('setup', '<%= appId %>', '<%= FrameworkConstants.CI_SETUP %>', '', '', getBasicParams());
	});
    
	$('#startJenkins').click(function() {
		progressPopup('startJenkins', '<%= appId %>', '<%= FrameworkConstants.CI_START %>', '', '', getBasicParams());
	});
    
	$('#stopJenkins').click(function() {
		progressPopup('stopJenkins', '<%= appId %>', '<%= FrameworkConstants.CI_STOP %>', '', '', getBasicParams());
	});
    
	$('#deleteBuild').click(function() {
		deleteCIBuild();
	});
	
//	$('#deleteJob').click(function() {
//		deleteCIJob();
//	});
	
    confirmDialog($("#deleteBuild"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.builds"/>', 'deleteBuild','<s:text name="lbl.btn.ok"/>');
    confirmDialog($("#deleteJobBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.jobs"/>', 'deleteJob','<s:text name="lbl.btn.ok"/>');
    
    <% if (permissions != null && permissions.canManageCIJobs()) { %>
	    if (<%= jenkinsAlive %>) {
	    	console.log("jenkins alive , enable configure button ");
	    	enableStart();
	    	enableButton($("#configure"));
	    	enableButton($("#emailConfiguration"));
	    	enableButton($("confluenceConfiguration"));
	    	disableButton($("#setup"));
	    } else {
	    	console.log("Jenkins down , disabled configure button ");
	    	enableStop();
	    	disableButton($("#configure"));
	    	disableButton($("#emailConfiguration"));
	    	disableButton($("#confluenceConfiguration"));
	    }
    <% } %>
    
	// when checking on more than one job, configure button should be disabled. it can not show already created job info for more than one job
	$("input[type=checkbox][name='Jobs']").click(function() {
		 repopulateConfiurePopup();
	});
	
	// if build is in progress disable configure button
    if (<%= isAtleastOneJobIsInProgress %> || <%= isBuildTriggeredFromUI %>) {
    	console.log("build is in progress, disable configure button ");
    	disableButton($("#configure"));
    	refreshCi = true;
    	console.log("at least one job is in progres...");
    	refreshBuild();
    } 
	
	if(isCiRefresh) {
		refreshAfterServerUp(); // after server restarted , it ll reload builds and ll refresh page (reload page after 10 sec)	
	}
	
});

function repopulateConfiurePopup(showText) {
	if (isMoreThanOneJobSelected()) {
		if (showText) {
			disableButton($("#configure"));
			$(".ciAlertMsg").show();
			$(".ciAlertMsg").html('<%= FrameworkConstants.CI_ONE_JOB_REQUIRED%>');			
		}
		return false;
	} else {
		$(".ciAlertMsg").hide();
		$(".ciAlertMsg").html("");
		// when jenkins is getting ready
		if (isCiRefresh) {
			jenkinsGettingReady();
		}
		
		// when build is in progress, user should not configure
		if (refreshCi) {
			disableButton($("#configure"));
		}
		<% if (permissions != null && permissions.canManageCIJobs()) { %>
			enableButton($("#configure"));
		<% } %>
		return true;
	}
}

function buildCI() {
	loadContent('buildCI',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
}

function deleteCIBuild() {
	showProgressBar("Deleting Build (s)");
	loadContent('CIBuildDelete',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
}
	
function deleteCIJob(){
	loadContent('CIJobDownStreamCheck',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), true, true);
}

function enableStart() {
    disableButton($("#startJenkins"));
    disableButton($("#setup"));
    enableButton($("#stopJenkins"));
}

function enableStop() {
    enableButton($("#startJenkins"));
    enableButton($("#setup"));
    disableButton($("#stopJenkins"));
}

//when background build is in progress, have to refresh ci page
function refreshBuild() {
	console.log("refresh build method called value " + refreshCi);
	if(refreshCi) {
		console.log("Going to get no of jobs in progress " + refreshCi);	
		loadContent('getNoOfJobsIsInProgress',$('#deleteObjects'), '', getBasicParams(), true, true);
	}
}

function successRefreshBuild(data) {
	console.log("noOfJobsIsinProgress....." + <%= noOfJobsIsinProgress %>);
	console.log("successRefreshBuild....." + data.numberOfJobsInProgress);
	console.log("refreshCi ... " + refreshCi);
	//data can be zero when no build is in progress, can be int value for each running job
	// noOfJobsIsinProgress also can be zero  when no jobs in in progress
	if (data.numberOfJobsInProgress < <%= noOfJobsIsinProgress %> || data.numberOfJobsInProgress > <%= noOfJobsIsinProgress %>) { // When build is increased or decreased on a job refresh the page , refresh the page
    	console.log("build succeeded going to load builds.....");
    	if ($("a[name='appTab'][class='active']").attr("id") == "ci" && $("#popupPage").css("display") == "block") {
    		console.log("Build trigger completed in jenkins , but UI is blocking ");
//     		refreshCi = false;
    	} else {
    		loadContent('ci', $('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
    	}
	} else {
		window.setTimeout(refreshBuild, 15000); // wait for 15 sec
	}
}

//after configured , it ll take some time to start server , so we ll get no builds available , in that case have to refresh ci
function refreshAfterServerUp() {
	console.log("Server startup Refreshed...." + isCiRefresh);
	// after configured job , jenkins will take some time to load. In that case after jenkins started(fully up and running), we have to enable this
	
   	localJenkinsAliveCheck (); // checks jenkins status and updates the variable
	
	console.log("Local jenkins alive called ....  jenkins alive ... " + isJenkinsAlive);
	console.log("Local jenkins alive called .... jenkins ready ... " + isJenkinsReady);
	
   	//default : isCiRefresh = true
	if (!isCiRefresh) { //when stop is clicked it will comme here
		console.log("Refreshing it!!!!!!");
		reloadCI();
	} else if(isCiRefresh && (!isJenkinsAlive || !isJenkinsReady)) { // when start is clicked it will come here
		//till page is reloaded disable these buttons.
		// when jenkins is getting ready, disable the buttons
		jenkinsGettingReady();
	   	$(".errorMsgLbl").text('<%= FrameworkConstants.CI_BUILD_LOADED_SHORTLY%>');
	   	
		console.log("I ll wait till jenkins gets ready!!!");
		window.setTimeout(refreshAfterServerUp, 10000); // wait for 10 sec
	} else {
		isCiRefresh = false;
		console.log("Server started successfully!");
		reloadCI();
	}
}

function jenkinsGettingReady() {
   	disableButton($("#configure"));
   	disableButton($("#build"));
   	disableButton($("#deleteJobBtn"));
   	disableButton($("#deleteBuild"));
}

function reloadCI() {
	if ($("a[name='appTab'][class='active']").attr("id") == "ci" && $("#popupPage").css("display") == "none"){
		console.log("reload CI called and going to refresh the page ");
    	console.log("Server startup completed ..." + isCiRefresh);
		loadContent('ci',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
	} else {
		console.log("reload CI : It is not in CI tab or popup available ");
		$(".errorMsgLbl").text('<%= FrameworkConstants.CI_NO_JOBS_AVAILABLE%>');
	}
}

function localJenkinsAliveCheck () {
	console.log("local jenkins alive check called ");
	loadContent('localJenkinsAliveCheck',$('#deleteObjects'), '', getBasicParams(), true, false);
}

function successLocalJenkinsAliveCheck (data) {
	if ($.trim(data.localJenkinsAlive) == '200') {
		console.log("200");
		isJenkinsAlive = true;
		isJenkinsReady = true;
	}
	if ($.trim(data.localJenkinsAlive) == '503') {
		console.log("503");
		isJenkinsAlive = true;
		isJenkinsReady = false;
	}
	if ($.trim(data.localJenkinsAlive) == '404') {
		console.log("404");
		isJenkinsAlive = false;
		isJenkinsReady = false;
	}
}

function successEvent(pageUrl, data) {
	if (pageUrl == "getNoOfJobsIsInProgress") {
		hideLoadingIcon();
		successRefreshBuild(data);
	} else if (pageUrl == "localJenkinsAliveCheck") {
		hideLoadingIcon();
		console.log("success jenkins alive check called ");
		successLocalJenkinsAliveCheck(data);
	} else if (pageUrl == "CIJobDownStreamCheck") {
		if (data.downStreamAvailable) {
			$('#popupPage').modal('show');
			$('#errMsg').html("");
			$('#successMsg').html("");
			$('#updateMsg').html("");
			$('#popupTitle').html('<s:text name="lbl.app.warnin.title"/>');
			$('#popup_div').empty();
			$('#popup_div').html('<s:text name="lbl.app.downstream.warnin"/>');
			$('.popupOk').val('Yes');
			$('#popupCancel').val('No');
			$('.popupClose').hide();
			$('.popupOk').show();
			$('#popupCancel').show();
			hidePopuploadingIcon();
		} else {
			showProgressBar("Deleting job (s)");	
	 	 	loadContent('CIJobDelete',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
		}
	} else if (pageUrl == 'saveConfluenceConfiguration'){
		hidePopuploadingIcon();
		$('#popupPage').modal('hide');
	} 
}

function enableDisableDeleteButton(atleastOneCheckBoxVal) {
	if (isAtleastOneCheckBoxCheked('jobBuildsList')) {
		enableButton($("#deleteBuild"));
	} else {
		disableButton($("#deleteBuild"));
	}
	
	if ($("input[type=checkbox][name='Jobs']:checked").length > 0) {
		<% if (permissions != null && permissions.canManageCIJobs()) { %>
			enableButton($("#deleteJobBtn"));
		<% } %>
		enableButton($("#build"));
	} else {
		disableButton($("#deleteJobBtn"));
		disableButton($("#build"));
	}
}

function isAllCheckBoxCheked(tagControlId) {
	if (!$("tbody#" + tagControlId + " input[type=checkbox]:not(:checked)").length) {
		 return true;
	} else {
		return false;
	}
}

function isAtleastOneCheckBoxCheked(tagControlId) {
	if ($("tbody." + tagControlId + " input[type=checkbox]:checked").length > 0) {
		 return true;
	} else {
		return false;
	}
}

function isMoreThanOneJobSelected() {
	if ($("input[type=checkbox][name='Jobs']:checked").length > 1)  {
		return true;
	} else {
		return false;
	}
}

function isOneJobSelected() {
	if ($("input[type=checkbox][name='Jobs']:checked").length > 0)  {
		return true;
	} else {
		return false;
	}
}

function popupOnClose(obj) {
	var closeUrl = $(obj).attr("id");
	console.log("handle load content here " + closeUrl);
	if (closeUrl === "setup") {
		console.log("setup called ");
	} else 	if (closeUrl === "startJenkins") {
		console.log("start called ");
		isCiRefresh = true; //after stratup , when closing popup, page should refreshed after some time
	} else 	if (closeUrl === "stopJenkins") {
		console.log("stop called ");
	}
	
	refreshAfterServerUp();
}
var validation = false;
var okUrl;
function popupOnOk(obj) {
		var operation=$("#operation").val();
		okUrl = $(obj).attr("id");
		if (okUrl == "saveJob" || okUrl == "updateJob" ) {
			if (isOneJobSelected()) {
				console.log("update job validation ");
				okUrl = "updateJob";
			} else {
				console.log("This is save job ");
			}
			// do the validation for collabNet info only if the user selects git radio button
			validation = configureJobValidation();
			
			if (validation) {
				var ciGoal = "";
				if(operation == "build") {
					ciGoal = "package";
				} else if (operation == "deploy" ) {
					ciGoal = "deploy";
				} else if (operation == "functionalTest") {
					ciGoal = "functional-"+'<%= functioanlTestTool%>';
					ciGoal = "functional-test-"+'<%= functioanlTestTool %>';
				} else if (operation == "unittest") {
					ciGoal = "unit-test";
				} else if (operation == "codeValidation") {
					ciGoal = "validate-code";
				} else if (operation == "pdfReport") {
					ciGoal = "pdf-report";
				} else if (operation == "loadTest") {
					ciGoal = "load-test";
				} else if (operation == "performanceTest") {
					ciGoal = "performance-test";
				}
				mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'ci', ciGoal);
			}
		} else if (okUrl == "deleteBuild" ) {
			deleteCIBuild();
		}  else if (okUrl == "deleteJob" ) {
			$('#popupPage').modal('hide');
			showProgressBar("Deleting job (s)");	
	 	 	loadContent('CIJobDelete',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false, true);
		} else if (okUrl == "saveEmailConfiguration" || okUrl == "updateEmailConfiguration" ) {
			if(emailConfigureValidation()) {
				configureEmail(okUrl);
				// show popup loading icon
	 			showPopuploadingIcon();
			}
		} else if (okUrl == 'saveConfluenceConfiguration') {
			showPopuploadingIcon();
			constructJsonString();
		}
}

function redirectCiConfigure() {
	var collabnet = $("input:radio[name=enableBuildRelease][value='true']").is(':checked');
	var confluence = $("input:radio[name=enableConfluence][value='true']").is(':checked')
	var ciOperation = $('#operation').val();
	console.log('ciOperation.......',ciOperation);
	if (validation) {
		var colabStatus = false;
		var conflStatus = false;
		if (collabnet || confluence ) {
			if (confluence) {
				if(confluenceValidation()) {
					conflStatus = true;
				}
			}
			
			if (collabnet) {
				if(collabNetValidation()) {
					colabStatus = true;
				}
			}
			
			if (collabnet && colabStatus) {
				if (confluence && conflStatus) {
					configureJob(okUrl, ciOperation);
				} else if ((collabnet && colabStatus) && !confluence) {
					configureJob(okUrl, ciOperation);
				}
			} else if ((confluence && conflStatus) && !collabnet) {
				configureJob(okUrl, ciOperation);
			} 
		} else {
			configureJob(okUrl, ciOperation);
		}
	}

}
</script>