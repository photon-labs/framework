<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.commons.model.User"%>
<%@ page import="org.tmatesoft.svn.core.wc.SVNStatus"%>
<%@ page import="org.tmatesoft.svn.core.wc.SVNStatusType"%>

<% 
	User user = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	String password = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
	String repoUrl = (String)request.getAttribute(FrameworkConstants.REPO_URL);
	String fromTab = (String)request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
	String applicationId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String action = (String)request.getAttribute(FrameworkConstants.REQ_ACTION);
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	User userInfo = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	Object commitableFilesObj = request.getAttribute(FrameworkConstants.REQ_COMMITABLE_FILES);
	List<SVNStatus> commitableFiles = null;
	if (commitableFilesObj != null) {
		commitableFiles = (List<SVNStatus>) commitableFilesObj;
	}
	
    String LoginId = "";
    if (userInfo != null) {
        LoginId = userInfo.getName();
    }
%>


<form id="repoDetails" name="repoDetails" action="import" method="post" autocomplete="off" class="repo_form form-horizontal">

		<%
 			if (FrameworkConstants.COMMIT.equals(action) && CollectionUtils.isNotEmpty(commitableFiles)) {
 		%>
			<div class="validate-container" style="padding-top: 0px;padding-bottom: 10px;">
	      		<div class="header-background"> </div>
	      		<div class="validate-container-inner validatePopup_tbl">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
				            	<th class="first validate_tblHdr">
				                	<div class="th-inner-validate"><input type="checkbox" id="selectAll"/></div>
				              	</th>
								<th class="first validate_tblHdr">
				                	<div class="th-inner-validate"><s:text name="lbl.file"/></div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="th-inner-validate"><s:text name="label.status"/></div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          		<% 
			          			for(SVNStatus commitableFile : commitableFiles) {
			          				SVNStatusType statusType = commitableFile.getContentsStatus();
			          		%>
			            	<tr>
			            		<td>
			            			<input type="checkbox" class="check" name="commitableFiles" value="<%= commitableFile.getFile() %>"/>
			            		</td>
			              		<td>
				              		<div class = "validateMsg" style="color: #000000; width: 350px;">
				              			<%= commitableFile.getFile() %>
				              		</div>
			              		</td>
			              		<td>
			              		<div class = "validateStatus" style="color: #000000;">
			              				<%=  statusType.getCode() %>
					   				</div>
			              		</td>
			            	</tr>
			            <%
 							}
 						%>
			          	</tbody>
			        </table>
	      		</div>
    		</div>
	    <% } %> 
 			
	<!--   import from type -->
	<div id="typeInfo">
		<div class="control-group" id="typeInfo">
			<label class="control-label labelbold popupLbl">
			<span class="red">* </span> <s:text name='lbl.svn.type' /></label>
			<div class="controls">
				<select name="repoType" class="medium" >
					<option value="<s:text name="lbl.repo.type.svn"/>" selected ><s:text name="lbl.repo.type.svn"/></option>
<%-- 					<% if (!FrameworkConstants.FROM_PAGE_ADD.equals(action)) { %> --%>
						<option value="<s:text name="lbl.repo.type.git"/>"><s:text name="lbl.repo.type.git"/></option>
<%-- 					<% } %> --%>
			    </select>
			</div>
		</div>
	</div>
					
	<div class="control-group">
		<label class="control-label labelbold popupLbl">
			<span class="red">*</span> <s:text name="label.repository.url"/>
		</label>
		<div class="controls">
			<input type="text" name="repoUrl" class="input-xlarge" id="repoUrl" value="<%= StringUtils.isEmpty(repoUrl) ? "http://" : repoUrl %>">&nbsp;&nbsp;<span id="missingURL" class="missingData"></span>
		</div>
	</div>
	
	<div class="control-group" id="otherCredentialInfo">
		<label  class="control-label labelbold popupLbl"> <s:text name="label.other.credential"/></label>
		<div class="controls">
		 	<input type="checkbox" name = "credential" class = "credentials" id="credentials" style="margin-top:8px;" />
		</div>
	</div>
	
	<div class="control-group">
		<label  class="control-label labelbold popupLbl"><span class="red mandatory">*</span> <s:text name="lbl.username"/></label> 
		<div class="controls">
			<input type="text" name="username" class="input-large" id="userName" maxlength="63" title="63 Characters only" >&nbsp;&nbsp;<span id="missingUsername" class="missingData"></span> 
		</div>
	</div>
	
	<div class="control-group">
		<label  class="control-label labelbold popupLbl"><span class="red mandatory">*</span> <s:text name="lbl.password"/></label> 
		<div class="controls">
			<input type="password" name="password" class="input-large" id="password" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingPassword" class="missingData"></span> 
		</div>
	</div>
	
	<% if (!FrameworkConstants.FROM_PAGE_ADD.equals(action) && !FrameworkConstants.COMMIT.equals(action)) { %>
	<div id="svnRevisionInfo">
		<div class="control-group">
			<label  class="control-label labelbold popupLbl"><span class="red">*</span> <s:text name="label.revision"/></label> 
			<div class="controls">
				<input id="revisionHead" type="radio" name="revision" value="HEAD" checked/>&nbsp; HEAD Revision 
			</div>
			<div class="controls">
				<input id="revision" type="radio" name="revision" value="revision"/> &nbsp;Revision &nbsp; &nbsp; &nbsp; &nbsp;<input id="revisionVal" type="text" name="revisionVal" maxLength="10" title="10 Characters only" disabled> 
			</div>
			<div class="controls">
				<span id="missingRevision" class="missingData"></span> 
			</div>
		</div>
	</div>
	<% } %>
	
	<% if (FrameworkConstants.FROM_PAGE_ADD.equals(action) || FrameworkConstants.COMMIT.equals(action)) { %>
		<div class="control-group">
			<label  class="control-label labelbold popupLbl"><s:text name="lbl.commit.message"/></label> 
			<div class="controls">
				 <textarea class="appinfo-desc input-xlarge" maxlength="150" title="<s:text name="title.150.chars"/>" class="xlarge" 
		        	id="textarea" name="commitMessage"></textarea> 
			</div>
		</div>
	<% } %>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		hidePopuploadingIcon();
		$("#repoUrl").focus();
		 // when clicking on save button, popup should not hide
        $('.popupOk').attr("data-dismiss", "");
		 
		// svn import already selected value display
		if(localStorage["svnImport"] != null && !isBlank(localStorage["svnImport"])) {
			$('#credentials').attr("checked", true);
			svnCredentialMark();
		} else {
			svnCredentialMark();
		}

		$("#repoUrl").blur(function(event) {
	       var repoUrl = $("input[name='repoUrl']").val();
	       urlBasedAction();
		});
		
		$('#revision').click(function() {
			$("#revisionVal").removeAttr("disabled");
		});

		$('#revisionHead').click(function() {
			$('#revisionVal').attr("disabled", "disabled");
		});

	  	$('#credentials').click(function() {
		  	svnCredentialMark();
		});
			 
	  	$('[name=repoType]').change(function() {
		  	extraInfoDisplay();
		  	if ($("[name=repoType]").val() == 'svn') {
		  		$(".mandatory").show();
		  	} else if($("[name=repoType]").val() == 'git') {
		  		$(".mandatory").hide();
		  	}
		});
			  
		<% if (StringUtils.isNotEmpty(repoUrl) && repoUrl.contains(FrameworkConstants.GIT)) {%>
		  	$("[name=repoType] option[value='git']").attr('selected', 'selected');
		  	$('#typeInfo').show();
		<% } else if (StringUtils.isNotEmpty(repoUrl)) { %>
		  	$("[name=repoType] option[value='svn']").attr('selected', 'selected');
		 	$('#typeInfo').show();
	    <% } %>
				  	
		extraInfoDisplay();
			
		// add multiple select / deselect functionality
	    $("#repoDetails #selectAll").click(function () {
			$('#repoDetails .check').attr('checked', this.checked);
			if($('#repoDetails #selectAll').is(':checked')) {
				enableButton($("#importUpdateAppln"));
			} else {
				disableButton($("#importUpdateAppln"));
			}
	    });

	    // if all checkbox are selected, check the selectAll checkbox
	    // and viceversa
	    $("#repoDetails .check").click(function() {
			if($("#repoDetails .check").length == $("#repoDetails .check:checked").length) {
	            $("#repoDetails #selectAll").attr("checked", "checked");
	        } else {
	            $("#repoDetails #selectAll").removeAttr("checked");
	        }
			
			if($("#repoDetails .check:checked").length > 0) {
				enableButton($(".popupOk"));
			} else {
				disableButton($(".popupOk"));
			}
	    });
	    
	    // by default for commit opeartion disable button
	    <% if (FrameworkConstants.COMMIT.equals(action)) { %> 
	    	disableButton($(".popupOk"));
	    <% } %>
	    
		<%
			if (FrameworkConstants.COMMIT.equals(action) && CollectionUtils.isEmpty(commitableFiles)) {
		%>
				$("#errMsg").html("<s:text name='lbl.no.change'/>");
		<% } %>
	});

	//base on the repo type credential info need to be displayed
	function extraInfoDisplay() {
		$("#errMsg").html("");
		if($("[name=repoType]").val() == 'svn') {
			$('.credentialDet').show();
			$('#svnRevisionInfo').show();
			  // hide other credential checkbox
			$('#otherCredentialInfo').show();
		} else if($("[name=repoType]").val() == 'git') {
			$('.credentialDet').hide();
			$('#svnRevisionInfo').hide();
			  // hide other credential checkbox
			$('#otherCredentialInfo').hide();
			enableSvnFormDet();
		}
	}

	function urlBasedAction() {
        var repoUrl = $("input[name='repoUrl']").val();
    	if (repoUrl.indexOf('insight.photoninfotech.com') != -1) {
			$('#credentials').attr("checked", false);
	        svnCredentialMark();
       	} else if(!isBlank(repoUrl)) {
			$('#credentials').attr("checked", true);
	        svnCredentialMark();
        }
	}

	function svnCredentialMark() {
		if($('#credentials').is(':checked')) {
			enableSvnFormDet();
			$("#userName").val('');
			$("#password").val('');
			localStorage["svnImport"] = "credentials";
		} else {
			$("#userName").val("<%= LoginId %>");
			$("#password").val("<%= (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD) %>");
			disableSvnFormDet();
			localStorage["svnImport"] = "";
		}
	}

	function enableSvnFormDet() {
		enableCtrl($("input[name='password']"));
		enableCtrl($("input[name='username']"));
	}

	function disableSvnFormDet() {
 		disableCtrl($("input[name='password']"));
 		disableCtrl($("input[name='username']"));
	}
	
	function validateImportAppl() {
// 		When isValidUrl returns false URL is missing information is displayed
		var repoUrl = $("input[name='repoUrl']").val();
		
		if (isBlank(repoUrl)) {
			$("#errMsg").html("URL is missing");
			$("#repoUrl").focus();
			return false;
		}
		
		if (isValidUrl(repoUrl)) {
			$("#errMsg").html("Invalid URL");
			$("#repoUrl").focus();
			return false;
		}
		
		if ($("[name=repoType]").val() == 'svn') {
			if(isBlank($.trim($("input[name='username']").val()))){
				$("#errMsg").html("Username is missing");
				$("#userName").focus();
				$("#userName").val("");
				return false;
		}
			
		if (isBlank($.trim($("input[name='password']").val()))) {
				$("#errMsg").html("Password is missing");
				$("#password").focus();
				$("#password").val("");
				return false;
		}
			
// 			the revision have to be validated
		if ($('input:radio[name=revision]:checked').val() == "revision" && (isBlank($.trim($('#revisionVal').val())))) {
				$("#errMsg").html("Revision is missing");
				$("#revisionVal").focus();
				$("#revisionVal").val("");
				return false;
		}

// 			before form submit enable textboxes
// 			enableSvnFormDet();
		}
		return true;
	}
	
	function importUpdateApp() {
		var params = getBasicParams();
		params = params.concat("projectId =");
		params = params.concat("<%= projectId %>");
		params = params.concat("&appId =");
		params = params.concat("<%= applicationId %>");
		params = params.concat("&loginId =");
		enableSvnFormDet();
		// show popup loading icon
		showPopuploadingIcon();
		loadContent(getAction(), $('#repoDetails'), '', params, true, true);
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "importSVNProject" || pageUrl == "importGITProject" || pageUrl == "updateSVNProject" || pageUrl == "updateGITProject"
			|| pageUrl == "addSVNProject" || pageUrl == "addGITProject" || pageUrl == "commitSVNProject" || pageUrl == "commitGITProject") {
			checkError(pageUrl, data);
		}
	}
	
	function checkError(pageUrl, data) {
		var statusFlag = " ";
		// hide loading icon
		hidePopuploadingIcon();
		if (!data.errorFlag) {
			$("#errMsg").html(data.errorString);
		} else if(data.errorFlag) {
			if ((pageUrl == "importGITProject") || (pageUrl == "importSVNProject")) {
				 statusFlag = "import";
			} else if((pageUrl == "updateGITProject") || (pageUrl == "updateSVNProject")) {
				 statusFlag = "update";
			} else if((pageUrl == "addGITProject") || (pageUrl == "addSVNProject")) {
				 statusFlag = "add";
			}  else if((pageUrl == "commitGITProject") || ( pageUrl == "commitSVNProject")) {
				 statusFlag = "commit";
			}
			var params = getBasicParams();
			params = params.concat("statusFlag=");
			params = params.concat(statusFlag);
			$('#popupPage').modal('hide');
			loadContent("applications", $('#formProjectList'), $("#container"), params, '', true);
		}
	}

	function getAction() {
		var action = "<%= action %>";
		var actionUrl = "";
		if ($("[name=repoType]").val() == 'svn') {
			actionUrl = action + "SVNProject";
		} else if ($("[name=repoType]").val() == 'git') {
			actionUrl = action + "GITProject";
		}
		return actionUrl;
	}
	
</script>