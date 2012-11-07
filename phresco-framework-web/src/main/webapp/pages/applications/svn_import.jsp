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

<%@ page import="com.photon.phresco.commons.model.User"%>

<% 
	User user = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	String password = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
	String repoUrl = (String)request.getAttribute(FrameworkConstants.REPO_URL);
	String fromTab = (String)request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
	String applicationId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String action = StringUtils.isEmpty(fromTab) ? "import" : "update";
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	System.out.println(FrameworkConstants.REQ_FROM_TAB+" : "+fromTab);

	User userInfo = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
    String LoginId = "";
    if (userInfo != null) {
        LoginId = userInfo.getName();
    }
%>


<form id="repoDetails" name="repoDetails" action="import" method="post" autocomplete="off" class="repo_form form-horizontal">
	<!--   import from type -->
	<div id="typeInfo">
		<div class="control-group" id="typeInfo">
			<label class="control-label labelbold popupLbl" ">
			<span class="red">* </span> <s:text name='lbl.svn.type' /></label>
			<div class="controls">
				<select name="repoType" class="medium" >
					<option value="<s:text name="lbl.repo.type.svn"/>" selected ><s:text name="lbl.repo.type.svn"/></option>
					<option value="<s:text name="lbl.repo.type.git"/>"><s:text name="lbl.repo.type.git"/></option>
			    </select>
			</div>
		</div>
	</div>
					
	<div class="control-group">
		<label class="control-label labelbold popupLbl">
			<span class="red">*</span> <s:text name="label.repository.url"/>
		</label>
		<div class="controls">
			<input type="text" name="repourl" id="repoUrl" value="<%= StringUtils.isEmpty(repoUrl) ? "http://" : repoUrl %>">&nbsp;&nbsp;<span id="missingURL" class="missingData"></span>
		</div>
	</div>
	
	<div class="control-group" id="otherCredentialInfo">
		<label  class="control-label labelbold popupLbl"> <s:text name="label.other.credential"/></label>
		<div class="controls">
		 	<input type="checkbox" name = "credential" class = "credentials" id="credentials" style="margin-top:8px;" />
		</div>
	</div>
	
	<div class="control-group">
		<label  class="control-label labelbold popupLbl"><span class="red">*</span> <s:text name="lbl.username"/></label> 
		<div class="controls">
			<input type="text" name="username" id="userName" maxlength="63" title="63 Characters only" >&nbsp;&nbsp;<span id="missingUsername" class="missingData"></span> 
		</div>
	</div>
	
	<div class="control-group">
		<label  class="control-label labelbold popupLbl"><span class="red">*</span> <s:text name="lbl.password"/></label> 
		<div class="controls">
			<input type="password" name="password" id="password" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingPassword" class="missingData"></span> 
		</div>
	</div>
	
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
</form>

<script type="text/javascript">
	$(document).ready(function() {
		
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
			
			 $("#repoUrl").keyup(function(event) {
		        var repoUrl = $("input[name='repourl']").val();
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
			  });
			  
				<%
				  if (StringUtils.isNotEmpty(repoUrl) && repoUrl.contains(FrameworkConstants.GIT)) {
				  %>
				  $("[name=repoType] option[value='git']").attr('selected', 'selected');
				  $('#typeInfo').show();
				  <%
				  } else if (StringUtils.isNotEmpty(repoUrl)) {
				  %>
				  $("[name=repoType] option[value='svn']").attr('selected', 'selected');
				  $('#typeInfo').show();
				  <%
				  }
				  %>
				  	
					extraInfoDisplay();
					$("#userName").val("<%= LoginId %>");
			});

			//base on the repo type credential info need to be displayed
			function extraInfoDisplay() {
			$("#errMsg").html("");
			if($("[name=repoType]").val() == 'svn') {
			$('.credentialDet').show();
			$('#svnRevisionInfo').show();
			  // hide other credential checkbox
			$('#otherCredentialInfo').show();
			  // to make check box untick (fill with insight username and password)
			urlBasedAction();
			} else if($("[name=repoType]").val() == 'git') {
			$('.credentialDet').hide();
			$('#svnRevisionInfo').hide();
			  // hide other credential checkbox
			$('#otherCredentialInfo').hide();
			enableSvnFormDet();
			}
			}

			function urlBasedAction() {
			      var repoUrl = $("input[name='repourl']").val();
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
			  $("#userName").val('');
			  $("#password").val('');
			  disableSvnFormDet();
			  localStorage["svnImport"] = "";
			}
			}

			function enableSvnFormDet() {
			  enableControl($("input[name='password']"), "");
			  enableControl($("input[name='username']"), "");
			}

			function disableSvnFormDet() {
			  disableControl($("input[name='password']"), "");
			  disableControl($("input[name='username']"), "");
			}
	
	function validateImportAppl() {
		alert("validate import application called ");
// 		When isValidUrl returns false URL is missing information is displayed
		var repoUrl = $("input[name='repourl']").val();
		
		if(isValidUrl(repoUrl)){
			alert(isValidUrl(repoUrl));
			alert("chkin valid url");
			$("#errMsg").html("URL is missing");
			$("#repoUrl").focus();
			return false;
		}
		alert("url came out with false");
		if($("[name=repoType]").val() == 'svn') {
				
			if(isBlank($.trim($("input[name='username']").val()))){
				alert("chkin name return false");
				$("#errMsg").html("Username is missing");
				$("#userName").focus();
				$("#userName").val("");
				return false;
			}
			
			if(isBlank($.trim($("input[name='password']").val()))){
				alert("chkin pass return false");
				$("#errMsg").html("Password is missing");
				$("#password").focus();
				$("#password").val("");
				return false;
			}
			
// 			the revision have to be validated
			if($('input:radio[name=revision]:checked').val() == "revision" && (isBlank($.trim($('#revisionVal').val())))) {
				alert("chkin revision return false");
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
		alert("importUpdateApp");
		var params = getBasicParams();
		params = params.concat("projectId =");
		params = params.concat("<%= projectId %>");
		params = params.concat("&appId =");
		params = params.concat("<%= applicationId %>");
		params = params.concat("&loginId =");
<%-- 		params = params.concat("<%= LoginId %>"); --%>
// 		console.info("param " + params);
		alert(params);
		alert("get Action called ========> " + getAction());
		// show popup loading icon
		showPopuploadingIcon();
		loadContent(getAction(), $('#repoDetails'), '', params, true);
	}
	
	function successEvent(pageUrl, data){
		if(pageUrl == "importSVNProject" || pageUrl == "importGITProject" || pageUrl == "updateSVNProject" || pageUrl == "updateGITProject"){
			checkError(data);
		}
	}
	
	function checkError(data) {
		// hide loading icon
		hidePopuploadingIcon();
		if (!data.errorFlag) {
			alert("Exception "+data.errorString);
		} else if(data.errorFlag) {
			alert(data.errorString);
			//has to be fixed
			$('#popupPage').modal('hide');
			loadContent("applications",$('#formProjectList'),$("#container"),getBasicParams());
		}
// 			alert("fetching...");
// 		$("#errMsg").empty();
// 		$('.popupLoadingIcon').hide();
<%-- 		if(data.svnImportMsg == "<%= FrameworkConstants.IMPORT_SUCCESS_PROJECT%>") { --%>
// 		$("#reportMsg").html(data.svnImportMsg);
// 		} else {
// 		$("#errMsg").html(data.svnImportMsg);
// 		}
// 		performAction('applications', '', $("#container"));
// 		setTimeout(function(){ $("#popup_div").hide(); }, 200);
// 		} else{ // Import Project Fails
// 		$("#errMsg").empty();
// 		$('.popupLoadingIcon').hide();
// 		$("#errMsg").html(data.svnImportMsg);
// 		}
		}

function getAction() {
	var action = "<%= action %>";
	console.info("action :"+action);
	var actionUrl = "";
	if ($("[name=repoType]").val() == 'svn') {
		console.info("repotye :"+"svn");
		actionUrl = action + "SVNProject";
	} else if ($("[name=repoType]").val() == 'git') {
		actionUrl = action + "GITProject";
	}
	console.info("URL "+ actionUrl);
	return actionUrl;
}
	
</script>