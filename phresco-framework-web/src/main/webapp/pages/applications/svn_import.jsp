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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.commons.model.User"%>
<%@ page import="org.tmatesoft.svn.core.wc.SVNStatus"%>
<%@ page import="org.tmatesoft.svn.core.wc.SVNStatusType"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<% 
	User user = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	String password = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
	String repoUrl = (String)request.getAttribute(FrameworkConstants.REPO_URL);
	String testRepoUrl = (String)request.getAttribute(FrameworkConstants.TEST_REPO_URL);
	String fromTab = (String)request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
	String applicationId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String action = (String)request.getAttribute(FrameworkConstants.REQ_ACTION);
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	User userInfo = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	Object commitableFilesObj = request.getAttribute(FrameworkConstants.REQ_COMMITABLE_FILES);
	List<String> Messages = (List<String>)request.getAttribute(FrameworkConstants.REQ_LOG_MESSAGES);

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
 			
	<!--   import src from type -->
	<div id="typeInfo">
		<div class="control-group" id="typeInfo">
			<label class="control-label labelbold popupLbl">
			<span class="red">* </span> <s:text name='lbl.svn.type' /></label>
			<div class="controls">
				<select name="repoType" class="medium" >
				<option value="bitkeeper"><s:text name="lbl.repo.type.bitkeeper"/></option>
				<option value="git"><s:text name="lbl.repo.type.git"/></option>
				<option value="svn"><s:text name="lbl.repo.type.svn"/></option>
<%-- 					<% if (!FrameworkConstants.FROM_PAGE_ADD.equals(action)) { %> --%>
					
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
			<input type="text" name="repoUrl" class="input-xlarge" id="repoUrl" value="<%= StringUtils.isEmpty(repoUrl) ? "" : repoUrl %>">&nbsp;&nbsp;<span id="missingURL" class="missingData"></span>
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
	
	<% if (!FrameworkConstants.FROM_PAGE_ADD.equals(action) && !FrameworkConstants.COMMIT.equals(action) && !FrameworkConstants.UPDATE.equals(action)) { %>
	<div class="control-group" id="testImportDivControl">
		<label  class="control-label labelbold popupLbl"> <s:text name="label.test.clone"/></label>
		<div class="controls">
		 	<input type="checkbox" name = "testClone" class = "testClone" id="testClone" style="margin-top:8px;" />
		</div>
	</div>
	
	<!--   import test from type -->	
	<div id = "testImportDiv">		
		<div class="control-group">
			<label class="control-label labelbold popupLbl">
				<span class="red">*</span> <s:text name="label.test.repository.url"/>
			</label>
			<div class="controls">
				<input type="text" name="testRepoUrl" class="input-xlarge" id="testRepoUrl" value="<%= StringUtils.isEmpty(testRepoUrl) ? "http://" : testRepoUrl %>">&nbsp;&nbsp;<span id="missingTestURL" class="missingTestData"></span>
			</div>
		</div>
		
		<div class="control-group" id="testOtherCredentialInfo">
			<label  class="control-label labelbold popupLbl"> <s:text name="label.other.credential"/></label>
			<div class="controls">
			 	<input type="checkbox" name = "testCredential" class = "testCredentials" id="testCredentials" style="margin-top:8px;" />
			</div>
		</div>
		
		<div class="control-group">
			<label  class="control-label labelbold popupLbl"><span class="red mandatory">*</span> <s:text name="lbl.username"/></label> 
			<div class="controls">
				<input type="text" name="testUserName" class="input-large" id="testUserName" maxlength="63" title="63 Characters only" >&nbsp;&nbsp;<span id="missingUsername" class="missingData"></span> 
			</div>
		</div>
		
		<div class="control-group">
			<label  class="control-label labelbold popupLbl"><span class="red mandatory">*</span> <s:text name="lbl.password"/></label> 
			<div class="controls">
				<input type="Password" name="testPassword" class="input-large" id="testPassword" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingPassword" class="missingData"></span> 
			</div>
		</div>
		
		<% if (!FrameworkConstants.FROM_PAGE_ADD.equals(action) && !FrameworkConstants.COMMIT.equals(action)) { %>
		<div id="testSvnRevisionInfo">
			<div class="control-group">
				<label  class="control-label labelbold popupLbl"><span class="red">*</span> <s:text name="label.revision"/></label> 
				<div class="controls">
					<input id="testRevisionHead" type="radio" name="testRevision" value="HEAD" checked/>&nbsp; HEAD Revision 
				</div>
				<div class="controls">
					<input id="testRevision" type="radio" name="testRevision" value="revision"/> &nbsp;Revision &nbsp; &nbsp; &nbsp; &nbsp;<input id="testRevisionVal" type="text" name="testRevisionVal" maxLength="10" title="10 Characters only" disabled> 
				</div>
				<div class="controls">
					<span id="missingTestRevision" class="missingTestData"></span> 
				</div>
			</div>
		</div>
		<% } %>
	</div>
	<% } %>
	
	<% if (FrameworkConstants.FROM_PAGE_ADD.equals(action) || FrameworkConstants.COMMIT.equals(action)) { %>
		<div class="control-group">
			<label  class="control-label labelbold popupLbl"><span class="red hideContent" id="commitMsgSpan">*</span> <s:text name="lbl.commit.message"/></label> 
			<div class="controls">
				 <textarea class="appinfo-desc input-xlarge" maxlength="150" title="<s:text name="title.150.chars"/>" class="xlarge" 
		        	id="commitMessage" name="commitMessage"></textarea>
	        	<a href="#" id="logMessagesAnchor"> <img id="img" class="logHistory" src="images/search_icontop.png"
				class="iconSizeinList" title="Log History" /> </a>
			</div>
		</div>
		<div id = "logDiv" class="control-group">
			<label  class="control-label labelbold popupLbl"><s:text name="lbl.select.log"/></label> 
			<div class="controls">
				<select id="logMessages" name="logMessages">
					<option value="Select">---Select---</option>
				</select> 
			</div>
		</div>
	<% } %>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		hidePopuploadingIcon();
		hideShowTestCheckoutDiv();
		$("#repoUrl").focus();
		$('#logDiv').hide();
		$('#testImportDivControl').hide();
		 // when clicking on save button, popup should not hide
        $('.popupOk').attr("data-dismiss", "");
		 
		// svn import already selected value display
		if(localStorage["svnImport"] != null && !isBlank(localStorage["svnImport"])) {
			$('#credentials').attr("checked", true);
			svnCredentialMark();
		} else {
			svnCredentialMark();
		}
		
		// svn import for already selected value display(Test)
	  	if(localStorage["testSvnImport"] != null && !isBlank(localStorage["testSvnImport"])) {
			$('#testCredentials').attr("checked", true);
			svnCredentialTestMark();
		} else {
			svnCredentialTestMark();
		}
		
		$('.logHistory').click(function() {
			fetchMessages();
    	});
		
		$('#logMessages').blur(function() {
			$('#logDiv').hide();
		});

		$("#repoUrl").blur(function(event) {
	       var repoUrl = $("input[name='repoUrl']").val();
	       urlBasedAction();
		});
		
		$("#testRepoUrl").blur(function(event) {
 	   		testUrlBasedAction();
		});
		
		$('#revision').click(function() {
			$("#revisionVal").removeAttr("disabled");
		});
		
		$('#testRevision').click(function() {
			$("#testRevisionVal").removeAttr("disabled");
		});


		$('#revisionHead').click(function() {
			$('#revisionVal').attr("disabled", "disabled");
		});
		
		$('#testRevisionHead').click(function() {
			$('#testRevisionVal').attr("disabled", "disabled");
		});

	  	$('#credentials').click(function() {
		  	svnCredentialMark();
		});
	  	
	  	$('#testCredentials').click(function() {
	  		svnCredentialTestMark();
		});
	  	
	  	$('#logMessages').bind('change', function() {
	  		 $('#commitMessage').val($('#logMessages').val());
	  		$('#logDiv').hide();
		});
	  	
	  	$('#testClone').click(function() {
	  		hideShowTestCheckoutDiv();
	  		changeChckBoxValue($('#testClone'));
	  	});
	  	
	  	$('[name=repoType]').change(function() {
		  	extraInfoDisplay();
		  	if ($("[name=repoType]").val() == 'svn') {
		  		$(".mandatory").show();
		  		$("#commitMsgSpan").hide();
		  		$('#testImportDivControl').show();
		  	} else if($("[name=repoType]").val() == 'git') {
		  		$(".mandatory").hide();
		  		$("#commitMsgSpan").hide();
		  		changeRepoActions();
		  	}
		  	
		  	if ($("[name=repoType]").val() == 'bitkeeper') {
		  		$(".mandatory").hide();
		  		$("#repoUrl").val('');
		  		$("#commitMsgSpan").show();
		  		changeRepoActions();
			}
		});
			  
		<% if (StringUtils.isNotEmpty(repoUrl) && repoUrl.contains(FrameworkConstants.GIT)) {%>
		  	$("[name=repoType] option[value='git']").attr('selected', 'selected');
		  	$('#typeInfo').show();
		  	$("#commitMsgSpan").hide();
	  	<% } else if (StringUtils.isNotEmpty(repoUrl) && repoUrl.contains(FrameworkConstants.BITKEEPER)) {%>
		  	$("[name=repoType] option[value='bitkeeper']").attr('selected', 'selected');
		  	$('#typeInfo').show();
		  	$(".mandatory").hide();
		  	$("#commitMsgSpan").show();
		<% } else if (StringUtils.isNotEmpty(repoUrl)) { %>
		  	$("[name=repoType] option[value='svn']").attr('selected', 'selected');
		 	$('#typeInfo').show();
		 	$("#commitMsgSpan").hide();
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
	    <% if (FrameworkConstants.COMMIT.equals(action) && StringUtils.isNotEmpty(repoUrl) && !repoUrl.contains(FrameworkConstants.BITKEEPER)) { %> 
	    	disableButton($(".popupOk"));
	    <% } %>
	    
		<%
			if (FrameworkConstants.COMMIT.equals(action) && CollectionUtils.isEmpty(commitableFiles) && 
			        StringUtils.isNotEmpty(repoUrl) && !repoUrl.contains(FrameworkConstants.BITKEEPER)) {
		%>
				$("#errMsg").html("<s:text name='lbl.no.change'/>");
		<% } %>
	});
	
	function hideShowTestCheckoutDiv() {
		if ($('#testClone').is(':checked')) {
  			$('#testImportDiv').show();
  		} else {
  			$('#testImportDiv').hide();
  		}
	}
	
	function changeRepoActions() {
		$('#testImportDivControl').hide();
  		$('#testClone').attr('checked', false);
  		changeChckBoxValue($('#testClone'));
  		$('#testImportDiv').hide();
	}
	
	function showSelect() {
		var selectList = document.getElementById("logMessages");
		if (selectList.options.length > 1) { 
			hidePopuploadingIcon();
			$('#logDiv').show();
			$('#logMessages').focus();
	    } else {
	    	$('#logDiv').hide();
	    }
	}
	
	function fetchMessages() {
		$("#errMsg").html(" ");
  		if ($("#userName").val() != '' && $("#password").val() != '' && !isValidUrl($("input[name='repoUrl']").val())) {
		var params;
		if (!($('#credentials').is(':checked'))) {
			params = "username=";
		    params = params.concat($("#userName").val());
		    params = params.concat("&password=")
		    params = params.concat($("#password").val());
		}
	    showPopuploadingIcon();
		loadContent('fetchLogMessages', $('#repoDetails'), '', params, true);
  		} else {
  			$("#errMsg").html("<s:text name='lbl.enter.valid.credentials.or.url'/>");
  		}
	}
	
	function selectList(data) {
  		var logs = data.restrictedLogs;
  		var selectList = document.getElementById("logMessages");
  		selectList.options.length = 1;
		for (i in logs) {
			$('#logMessages').append($('<option>', { 
		        value: logs[i],
		        text : logs[i] 
		    }));
		}
		showSelect();
  	}

	//base on the repo type credential info need to be displayed
	function extraInfoDisplay() {
		$("#errMsg").html("");
		if($("[name=repoType]").val() == 'svn') {
			$('.credentialDet').show();
			$('#svnRevisionInfo').show();
			$('#testSvnRevisionInfo').show();
			  // hide other credential checkbox
			$('#otherCredentialInfo').show();
			$('#testOtherCredentialInfo').show();
		} else if($("[name=repoType]").val() == 'git' || $("[name=repoType]").val() == 'bitkeeper') {
			$('.credentialDet').hide();
			$('#svnRevisionInfo').hide();
			$('#testSvnRevisionInfo').hide();
			  // hide other credential checkbox
			$('#otherCredentialInfo').hide();
			$('#testOtherCredentialInfo').hide();
			enableSvnFormDet();
		}
	}

	function urlBasedAction() {
        var repoUrl = $("input[name='repoUrl']").val();
    	if (repoUrl.indexOf('insight.photoninfotech.com') != -1) {
			$('#credentials').attr("checked", false);
	        svnCredentialMark();
       	} 
	}
	
	function testUrlBasedAction() {
        var repoUrl = $("input[name='testRepoUrl']").val();
    	if (repoUrl.indexOf('insight.photoninfotech.com') != -1) {
			$('#testCredentials').attr("checked", false);
			svnCredentialTestMark();
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
			<%
				String encodedpassword = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
				String decryptedPass = new String(Base64.decodeBase64(encodedpassword.getBytes()));
			%>
			$("#password").val("<%= decryptedPass %>");
			disableSvnFormDet();
			localStorage["svnImport"] = "";
		}
	}
	
	function svnCredentialTestMark() {
		if($('#testCredentials').is(':checked')) {
			enableTestSvnFormDet();
			$("#testUserName").val('');
			$("#testPassword").val('');
			localStorage["testSvnImport"] = "testCredentials";
		} else {
			$("#testUserName").val("<%= LoginId %>");
			<%
				String testEncodedpassword = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
				String testDecryptedPass = new String(Base64.decodeBase64(encodedpassword.getBytes()));
			%>
			$("#testPassword").val("<%= testDecryptedPass %>");
			disableTestSvnFormDet();
			localStorage["testSvnImport"] = "";
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
	
	function enableTestSvnFormDet() {
		$("input[name='testPassword']").removeAttr("readonly");
		$("input[name='testUserName']").removeAttr("readonly");
	}

	function disableTestSvnFormDet() {
		
 		$("input[name='testPassword']").attr("readonly","readonly");
 		$("input[name='testUserName']").attr("readonly","readonly");
	}
	
	function validateImportAppl() {
// 		When isValidUrl returns false URL is missing information is displayed
		var repoUrl = $("input[name='repoUrl']").val();
		
		if (isBlank(repoUrl)) {
			$("#errMsg").html("Application Repository URL is missing");
			$("#repoUrl").focus();
			return false;
		}
		
		if ($("[name=repoType]").val() != 'bitkeeper' && isValidUrl(repoUrl)) {
			$("#errMsg").html("Invalid Application Repository URL");
			$("#repoUrl").focus();
			return false;
		}
		
		if ($("[name=repoType]").val() == 'svn') {
			if (isBlank($.trim($("input[name='username']").val()))) {
				$("#errMsg").html("Application Repository Username is missing");
				$("#userName").focus();
				$("#userName").val("");
				return false;
			}
			
			if (isBlank($.trim($("input[name='password']").val()))) {
				$("#errMsg").html("Application Repository Password is missing");
				$("#password").focus();
				$("#password").val("");
				return false;
			}
			
// 			the revision have to be validated
			if ($('input:radio[name=revision]:checked').val() == "revision" && (isBlank($.trim($('#revisionVal').val())))) {
				$("#errMsg").html("Application Repository Revision is missing");
				$("#revisionVal").focus();
				$("#revisionVal").val("");
				return false;
			}

// 			before form submit enable textboxes
// 			enableSvnFormDet();
		}
		
		if (<%= FrameworkConstants.COMMIT.equals(action) %> && $("[name=repoType]").val() == 'bitkeeper' && isBlank($.trim($("#commitMessage").val()))) {
			$("#errMsg").html("Commit message is missing");
			$("#commitMessage").text("");
			$("#commitMessage").focus();
			return false;
		}
		
		if ($('#testClone').is(':checked')) {
			var testRepoUrl = $("input[name='testRepoUrl']").val();
			
			if (isBlank(testRepoUrl)) {
				$("#errMsg").html("TestCheckout Repository URL is missing");
				$("#testRepoUrl").focus();
				return false;
			}
			
			if ($("[name=repoType]").val() != 'bitkeeper' && isValidUrl(testRepoUrl)) {
				$("#errMsg").html("Invalid TestCheckout Repository URL");
				$("#testRepoUrl").focus();
				return false;
			}
			
			if ($("[name=repoType]").val() == 'svn') {
				if (isBlank($.trim($("input[name='testUserName']").val()))) {
					$("#errMsg").html("TestCheckout Repository Username is missing");
					$("#testUserName").focus();
					$("#testUserName").val("");
					return false;
				}
				
				if (isBlank($.trim($("input[name='testPassword']").val()))) {
					$("#errMsg").html("TestCheckout Repository Password is missing");
					$("#testPassword").focus();
					$("#testPassword").val("");
					return false;
				}
				
//	 			the revision have to be validated
				if ($('input:radio[name=testRevision]:checked').val() == "revision" && (isBlank($.trim($('#testRevisionVal').val())))) {
					$("#errMsg").html("TestCheckout Repository Revision is missing");
					$("#testRevisionVal").focus();
					$("#testRevisionVal").val("");
					return false;
				}

//	 			before form submit enable textboxes
//	 			enableSvnFormDet();
			}
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
		var pageUrl = getAction();
		if ($("[name=repoType]").val() == 'svn'&& pageUrl == 'addSVNProject') {
			$(".yesNoPopupErr").css("width", "58%");
			$(".yesNoPopupErr").css("margin-left", "28px");
			$(".yesNoPopupErr").text('do_not_checkin folder will be ignored, if it exists');
			setTimeout(function() {
				$(".yesNoPopupErr").empty();
			}, 1400);
		}	
		
		loadContent(pageUrl, $('#repoDetails'), '', params, true, true);
	}
	
	function checkError(pageUrl, data) {
		var statusFlag = " ";
		
		$(".yesNoPopupErr").css("width", "61%");
		$(".yesNoPopupErr").css("margin-left", "");

		// hide loading icon
		hidePopuploadingIcon();
		if (!data.errorFlag) {
			$("#errMsg").html(data.errorString);
		} else if(data.errorFlag) {
			if (pageUrl == "importGITProject" || pageUrl == "importSVNProject" || pageUrl == "importBitKeeperProject") {
				 statusFlag = "import";
			} else if(pageUrl == "updateGITProject" || pageUrl == "updateSVNProject" || pageUrl == "updateBitKeeperProject") {
				 statusFlag = "update";
			} else if(pageUrl == "addGITProject" || pageUrl == "addSVNProject") {
				 statusFlag = "add";
			}  else if(pageUrl == "commitGITProject" || pageUrl == "commitSVNProject" || pageUrl == "commitBitKeeperProject") {
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
		} else if ($("[name=repoType]").val() == 'bitkeeper') {
			actionUrl = action + "BitKeeperProject";
		}

		return actionUrl;
	}
	
	function popupOnCancel(obj) {

	}
</script>