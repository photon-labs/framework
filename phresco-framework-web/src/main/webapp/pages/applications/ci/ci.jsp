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

<%@ include file="../progress.jsp" %>
<%-- <%@ include file="../../userInfoDetails.jsp" %> --%>

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

<script src="js/reader.js" ></script>
<script type="text/javascript" src="js/delete.js" ></script>
<script type="text/javascript" src="js/home-header.js" ></script>

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
	        <input id="setup" type="button" value="<s:text name="lbl.setup"/>" class="btn btn-primary" data-toggle="modal" href="#popupPage" >
	        <input id="startJenkins" type="button" value="<s:text name="lbl.start"/>" class="btn btn-primary" data-toggle="modal" href="#popupPage" >
	        <input id="stopJenkins" type="button" value="<s:text name="lbl.stop"/>" class="btn" disabled="disabled" data-toggle="modal" href="#popupPage" >
	        <input id="configure" type="button" value="<s:text name="lbl.configure"/>" class="btn btn-primary" data-toggle="modal" href="#popupPage"> <!-- additional param -->
	        <input id="build" type="button" value="<s:text name="lbl.build"/>" class="btn" disabled="disabled" onclick="buildCI();">
	        <input id="deleteBuild" type="button" value="<s:text name="lbl.deletebuild"/>" class="btn" disabled="disabled" data-toggle="modal" href="#popupPage">
	        <input id="deleteJob" type="button" value="<s:text name="lbl.deletejob"/>" class="btn" disabled="disabled" data-toggle="modal" href="#popupPage">
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
			                	<span>
	                				<input type="checkbox" class="<%= jobName %>Job" name="Jobs" id="checkBox" value="<%= jobName %>" <%= new Boolean(request.getAttribute(FrameworkConstants.CI_BUILD_JENKINS_ALIVE + jobName).toString()).booleanValue() ? "" : "disabled" %>>
			                		&nbsp;&nbsp;<%= jobName %> &nbsp;&nbsp;
								</span>
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
														<input type="checkbox" value="<%= jobName %>" class="<%= jobName %>AllBuild" name="allBuilds">
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
						                    				<input type="checkbox" value="<%= jobName %>,<%= build.getNumber() %>" class="<%= jobName %>" name="builds">
						                    			</td>
						                    			<td><%= build.getNumber() %></td>
						                    			<td><a href="<%= build.getUrl() %>" target="_blank"><%= build.getUrl().replace("%20", " ") %></a></td>
						                    			<td style="padding-left: 3%;">
						                    				<%
								                                if(build.getStatus().equals("INPROGRESS"))  {
								                            %>
								                                <img src="images/icons/inprogress.png" title="In progress"/>
										              		<% 
								                                } else if(build.getStatus().equals("SUCCESS")) {
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
								                                <img src="images/icons/wrong_icon.png" title="Not available"/>
								                            <%  
								                            	}
										              		%>
														</td>
						                    			<td><%= build.getTimeStamp() %></td>
						                    			<td>
						                    				<% 
								                                if(build.getStatus().equals("SUCCESS")) {
								                            %>
								                                <img src="images/icons/success.png" title="Success">
								                           	<%
								                                } else if(build.getStatus().equals("INPROGRESS"))  { 
								                            %>
								                                <img src="images/icons/inprogress.png" title="In progress"/>
								                            <%
								                                } else { 
								                            %>
								                                <img src="images/icons/failure.png" title="Failure">
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
			                        	    	<%  if(!new Boolean(request.getAttribute(FrameworkConstants.CI_BUILD_JENKINS_ALIVE + jobName).toString()).booleanValue()) { %>
			                        	    		<div class="alert alert-block" style="margin-top: 0px;">
			                        	    		<center>
			                        	    				 <s:text name='ci.server.down.message'/>
			                        	    		</center>
			                        	    		</div>
			                        	    	<% } else { %>
			                        	    		<div class="alert alert-block" style="margin-top: 0px;">
													<center>
														<s:text name='ci.error.message'/>
													</center>
													</div>			                        	    	
			                        	    	<% } %>
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

var isCiRefresh = false; // for ci page use - this should be global : kalees

// buildSize to refresh ci after build completed
var refreshCi = false;
var isJenkinsAlive = false;
var isJenkinsReady = false;

$(document).ready(function() {
	
	$('.siteaccordion').unbind('click');
	accordion();
// 	$("#popup_div").css("display","none");
// 	$("#popup_div").empty();
	enableScreen();
	hideLoadingIcon();
	hideProgressBar(); // when deleting builds and jobs
	
	$("input:checkbox[name='allBuilds']").click(function() {
		$("input:checkbox[class='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		$("input:checkbox[value='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		
		enableDisableDeleteButton($(this).val());
	});
	
	$("input:checkbox[name='builds']").click(function() {
		var isAllChecked = isAllCheckBoxCheked($(this).attr("class"));
		$("input:checkbox[value='" + $(this).attr("class") +"']").attr('checked', isAllChecked);
		
		enableDisableDeleteButton($(this).attr("class"));
	});
	
	$("input:checkbox[name='Jobs']").click(function() {
		$("input:checkbox[class='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		$("input:checkbox[value='" + $(this).val() +"']").attr('checked', $(this).is(':checked'));
		enableDisableDeleteButton($(this).val());
	});
	
	yesnoPopup($('#configure'), 'configure', '<s:text name="lbl.configure"/>', 'saveJob','<s:text name="lbl.save"/>', $('#deleteObjects'));
    
//     $('#closeGenerateTest, #closeGenTest').click(function() {
//     	ProgressShow("none");
//     	refreshAfterServerUp();
//     });
    
    progressPopup($('#setup'), 'setup', '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.CI_SETUP %>', '', '', getBasicParams());
    
    progressPopup($('#startJenkins'), 'startJenkins', '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.CI_START %>', '', '', getBasicParams());
    
    progressPopup($('#stopJenkins'), 'stopJenkins', '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.CI_STOP %>', '', '', getBasicParams());
    
    confirmDialog($("#deleteBuild"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.builds"/>', 'deleteBuild','<s:text name="lbl.btn.ok"/>');
    confirmDialog($("#deleteJob"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.jobs"/>', 'deleteJob','<s:text name="lbl.btn.ok"/>');
    
    if(<%= jenkinsAlive %>) {
    	console.log("jenkins alive , enable configure button ");
    	enableStart();
    	enableButton($("#configure"));
    	disableButton($("#setup"));
    } else {
    	console.log("Jenkins down , disabled configure button ");
    	enableStop();
    	disableButton($("#configure"));
    }
    
// //     RBACK implemented
<%--     if ('<%= disableCI %>' == 'true') { --%>
//     	disableCI();	//Restrict CI
//     }
    
	// when checking on more than one job, configure button should be disabled. it can not show already created job info for more than one job
	$("input[type=checkbox][name='Jobs']").click(function() {
		if (isMoreThanOneJobSelected()) {
			$(".ciAlertMsg").show();
			$(".ciAlertMsg").html('<%= FrameworkConstants.CI_ONE_JOB_REQUIRED%>');
			disableButton($("#configure"));
<%-- 			showHidePopupMsg($(".ciAlertMsg"), '<%= FrameworkConstants.CI_ONE_JOB_REQUIRED%>'); --%>
// 			return false;			
		} else {
			$(".ciAlertMsg").hide();
			$(".ciAlertMsg").html("");
			enableButton($("#configure"));
		}
	});
	
	// if build is in progress disable configure button
    if (<%= isAtleastOneJobIsInProgress %> || <%= isBuildTriggeredFromUI %>) {
    	console.log("build is in progress, disable configure button ");
    	disableButton($("#configure"));
    	disableButton($("#build"));
    	refreshCi = true;
    	console.log("at least one job is in progres...");
    	refreshBuild();
    } 
	
	if(isCiRefresh) {
		refreshAfterServerUp(); // after server restarted , it ll reload builds and ll refresh page (reload page after 10 sec)	
	}
	
});

function buildCI() {
	loadContent('buildCI',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false);
}

function deleteCIBuild() {
	showProgressBar("Deleting Build (s)");
	loadContent('CIBuildDelete',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false);
}
	
function deleteCIJob(){
	showProgressBar("Deleting job (s)");
	loadContent('CIJobDelete',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false);
}

function enableStart() {
    disableButton($("#startJenkins"));
    enableButton($("#stopJenkins"));
}

function enableStop() {
    enableButton($("#startJenkins"));
    disableButton($("#stopJenkins"));
}

//when background build is in progress, have to refresh ci page
function refreshBuild() {
	console.log("refresh build method called value " + refreshCi);
	if(refreshCi) {
		console.log("Going to get no of jobs in progress " + refreshCi);	
		loadContent('getNoOfJobsIsInProgress',$('#deleteObjects'), '', getBasicParams(), true);
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
    		console.log("Build trugger completed in jenkins , but UI is blocking ");
//     		refreshCi = false;
    	} else {
    		showLoadingIcon(); // Loading Icon
    		loadContent('ci', $('#deleteObjects'), $('#subcontainer'), getBasicParams(), false);
    	}
	} else {
		window.setTimeout(refreshBuild, 15000); // wait for 15 sec
	}
}

//after configured , it ll take some time to start server , so we ll get no builds available , in that case have to refresh ci
function refreshAfterServerUp() {
	console.log("Server startup Refreshed...." + isCiRefresh);
	// after configured job , jenkins will take some time to load. In that case after jenkins started(fully up and running), we have to enable this
// 	$("#warningmsg").show();
	
   	localJenkinsAliveCheck (); // checks jenkins status and updates the variable
	
	console.log("Local jenkins alive called ....  jenkins alive ... " + isJenkinsAlive);
	console.log("Local jenkins alive called .... jenkins ready ... " + isJenkinsReady);
	
   	//default : isCiRefresh = true
	if (!isCiRefresh) { //when stop is clicked it will comme here
		console.log("Refreshing it!!!!!!");
		reloadCI();
	} else if(isCiRefresh && (!isJenkinsAlive || !isJenkinsReady)) { // when start is clicked it will come here
		//till page is reloaded disable these buttons.
	   	disableButton($("#configure"));
	   	disableButton($("#build"));
	   	disableButton($("#deleteJob"));
	   	$(".errorMsgLabel").text('<%= FrameworkConstants.CI_BUILD_LOADED_SHORTLY%>');
	   	
		console.log("I ll wait till jenkins gets ready!!!");
		window.setTimeout(refreshAfterServerUp, 15000); // wait for 15 sec
	} else {
		isCiRefresh = false;
		console.log("Server started successfully!");
		reloadCI();
	}
}

function reloadCI() {
	if ($("a[name='appTab'][class='active']").attr("id") == "ci" && $("#popupPage").css("display") == "none"){
		console.log("reload CI called and going to refresh the page ");
//     	showLoadingIcon($("#tabDiv")); // Loading Icon
    	console.log("Server startup completed ..." + isCiRefresh);
		loadContent('ci',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false);
	} else {
		console.log("reload CI : It is not in CI tab or popup available ");
		$(".errorMsgLabel").text('<%= FrameworkConstants.CI_NO_JOBS_AVAILABLE%>');
	}
}

function localJenkinsAliveCheck () {
// 	loadContent('ci',$('#deleteObjects'), $('#subcontainer'), getBasicParams(), false); // 6th param
    $.ajax({
        url : "localJenkinsAliveCheck",
        data: { },
        type: "POST",
        success : function(data) {
        	if($.trim(data) == '200') {
        		console.log("200");
        		isJenkinsAlive = true;
        		isJenkinsReady = true;
        	}
        	if($.trim(data) == '503') {
        		console.log("503");
        		isJenkinsAlive = true;
        		isJenkinsReady = false;
        	}
        	if($.trim(data) == '404') {
        		console.log("404");
        		isJenkinsAlive = false;
        		isJenkinsReady = false;
        	}
        },
        async:false
    });
}

function successEvent(pageUrl, data) {
	if(pageUrl == "getNoOfJobsIsInProgress") {
		successRefreshBuild(data);
	}
}

function enableDisableDeleteButton(atleastOneCheckBoxVal) {
	if (isAtleastOneCheckBoxCheked('jobBuildsList')) {
		enableButton($("#deleteBuild"));
	} else {
		disableButton($("#deleteBuild"));
	}
	
	if ($("input[type=checkbox][name='Jobs']:checked").length > 0) {
		enableButton($("#deleteJob"));
		enableButton($("#build"));
	} else {
		disableButton($("#deleteJob"));
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

function popupClose(closeUrl) {
	console.log("handle load content here " + closeUrl);
	showParentPage();
	if (closeUrl === "setup") {
// 		refreshAfterServerUp();
	} else 	if (closeUrl === "startJenkins") {
		isCiRefresh = true; //after stratup , when closing popup, page should refreshed after some time
// 		refreshAfterServerUp();
	} else 	if (closeUrl === "stopJenkins") {
// 		refreshAfterServerUp();
	}
	
	refreshAfterServerUp();
}

function popupOnOk(obj) {
		var okUrl = $(obj).attr("id");
		if (okUrl == "saveJob" || okUrl == "updateJob" ) {
			if (isOneJobSelected()) {
				console.log("update job validation ");
				okUrl = "updateJob";
			} else {
				console.log("This is save job ");
			}
			// do the validation for collabNet info only if the user selects git radio button
			var validation = configureJobValidation();
			// when validation is true
			if (validation && $("input:radio[name=enableBuildRelease][value='true']").is(':checked')) {
				if(collabNetValidation()) {
					console.log("create job with collabnet plugin ");
					configureJob(okUrl);
				}
			} else if (validation) {
				console.log("create job with OUT collabnet plugin ");
				configureJob(okUrl);
			}
		} else if (okUrl == "deleteBuild" ) {
			deleteCIBuild();
		}  else if (okUrl == "deleteJob" ) {
			deleteCIJob();
		}
}
</script>