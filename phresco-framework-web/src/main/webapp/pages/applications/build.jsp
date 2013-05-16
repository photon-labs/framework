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

<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<%
	ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = "";
	if (applicationInfo != null) {
		appId = applicationInfo.getId();
	}
	
	String projectCode = applicationInfo.getAppDirName();
	
    String selectedAppType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_APP_TYPE);
    String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
  	StringBuilder sbBuildPath = new StringBuilder();
  	sbBuildPath.append(projectCode);
  	sbBuildPath.append("/");    //File separator is not working for this function.
  	sbBuildPath.append(FrameworkConstants.CHECKIN_DIR);
  	sbBuildPath.append("/");
  	sbBuildPath.append(FrameworkConstants.BUILD_PATH);
    
  	boolean serverStatus = false;
	boolean logFileExists = false;
 	if (session.getAttribute(appId + FrameworkConstants.SESSION_SERVER_STATUS) == null) {
		serverStatus = false;
	} else {
 		serverStatus = session.getAttribute(appId + FrameworkConstants.SESSION_SERVER_STATUS).toString().equals("true") ? true : false;
 	}
 	
	if (request.getAttribute(FrameworkConstants.REQ_LOG_FILE_EXISTS) == null) {
		logFileExists = false;
	} else {
		logFileExists = (Boolean) request.getAttribute(FrameworkConstants.REQ_LOG_FILE_EXISTS);
 	}
 	
	String runAgsSrcLog = (String) request.getAttribute(FrameworkConstants.REQ_SERVER_LOG);
	Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
	
	String requestIp = (String) request.getAttribute(FrameworkConstants.REQ_REQUEST_IP);
    String showIcons = (String) session.getAttribute(requestIp);
	String iconClass = "";
	if (!Boolean.parseBoolean(showIcons))  {
		iconClass = "hideIcons";
	}
	
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	String per_disabledClass = "btn-primary";
	if (permissions != null && !permissions.canManageBuilds()) {
		per_disabledStr = "disabled";
		per_disabledClass = "btn-disabled";
	}
%>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<div class="alert alert-block" id="warningmsg" style="display:none;">
	<s:text name='build.warn.message'/>
</div>

<form action="deleteBuild" method="post" autocomplete="off" id="deleteObjects" class="build_form">
    <div class="operation build">
    	<div class="build_delete_btn_div">
		    <input type="button" id="generateBuild" class="btn <%= per_disabledClass %>" <%= per_disabledStr %> additionalParam="from=generateBuild" 
		    	value="<s:text name='label.generatebuild'/>">
			<input id="deleteButton" type="button" value="<s:text name="label.delete"/>" class="btn" disabled="disabled" data-toggle="modal" href="#popupPage"/>
			<%
				if (optionIds != null && optionIds.contains(FrameworkConstants.MINIFICATION_KEY)) {
			%>
				<input id="minifyButton" type="button" value="<s:text name="lbl.minifier"/>" <%= per_disabledStr %> class="btn <%= per_disabledClass %>"/>
			<%
				}
			%>
		</div>
		
		<div class="runagint_source" id="runagainst_source">
			<%
				if (optionIds != null && optionIds.contains(FrameworkConstants.RUN_AGAINST_KEY)) {
			%>
			<div id="nodeJS_btndiv" class="nodeJS_div">
				<input type="button" class="btn <%= per_disabledClass %>" id="runAgainstSourceStart" value="<s:text name='label.runagainsrc'/>"/>
		    	<input type="button" class="btn" id="runAgainstSourceStop" value="<s:text name='lbl.stop'/>" disabled onclick="stopServer();"/>
		    	<input type="button" class="btn" id="runAgainstSourceRestart" value="<s:text name='label.restart'/>" disabled onclick="restartServer();"/>
			</div>
			<% 
				}
			%>
		    <div class="icon_div">
				<a href="#" class="<%= iconClass %>" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
				<a href="#"  class="<%= iconClass %>"id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
			</div>
		</div>
		<div class="clear"></div>
	</div>

	<div class="buildDiv">
	    <div class="build_detail_div" id="build-body-container">
	        <!-- Data Display starts -->
	
			<!-- Data Display ends -->            
		</div>

		<div class="build_progress_div">
			<div class="build_table_div">
    			<!-- Command Display Heading starts -->
				<div class="tblheader" style="height: 29px;">
					<table class="zebra-striped deployPopup" style="height: 29px;"> 
						<tr class="tr_color">
		    				<th><s:text name="label.progress"/></th>
		    				<th class="<%= iconClass %>"><img src="images/icons/clipboard-copy.png" alt="clipboard" id="clipboard" title="Copy to clipboard"/></th>
						</tr>
		           </table>
          		</div>

				<!-- Command Display starts -->
				<div class="build_cmd_div" id="console_div">
			    	 <%= runAgsSrcLog %>
				</div>
				<!-- Command Display starts -->
            </div>
        </div>
	</div>
</form>

<script type="text/javascript">
	if (<%=serverStatus%> && <%=logFileExists%> ) {
		runAgainstSrcServerRunning();
	} else {
		runAgainstSrcServerDown();
	}
	
    $(document).ready(function() {
    	hideProgressBar();
    	confirmDialog($("#deleteButton"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.builds"/>', 'deleteBuild','<s:text name="lbl.btn.ok"/>');
    	
    	$('#generateBuild').click(function() {
    		validateDynamicParam('generateBuild', '<s:text name="label.generatebuild"/>', 'build','<s:text name="lbl.btn.ok"/>', '', '<%= Constants.PHASE_PACKAGE %>');
    	});
    	
    	$('#runAgainstSourceStart').click(function() {
    		validateDynamicParam('showRunAgainstSourcePopup', '<s:text name="label.runagainstsource"/>', 'startServer','<s:text name="label.run"/>', '', '<%= Constants.PHASE_RUNGAINST_SRC_START %>');
    	});
    	
    	$('#minifyButton').click(function(){
    		hidePopuploadingIcon();
    		yesnoPopup('minfiyPopup', '<s:text name="lbl.minification"/>', 'minifier','<s:text name="lbl.minify"/>');
    	});

    	if ($.browser.safari && $.browser.version == 530.17) {
    		$(".buildDiv").show().css("float","left");
    	}
    	
    	refreshTable();
    	
        if (($('#database option').length == 0) && ($('#server option').length == 0)) {
                 $('#buildbtn').prop("disabled", true);
        }
        
        $('#showSettings').click(function(){
            $('.build_form').attr("action", "buildView");
            $('.build_form').submit();
        });
        
        /* $('#deleteButton').click(function() {
			$("#confirmationText").html("Do you want to delete the selected build(s)");
		    dialog('block');
		    escBlockPopup();
        }); */
        
        $('form').submit(function() {
			showProgessBar("Deleting Build (s)", 100);
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize();
	    	}
			performAction('deleteBuild', params, $("#tabDiv"));
	        return false;
	    });
        
         $('#openFolder').click(function() {
            openFolder('<%= sbBuildPath %>');
        });
        
        $('#copyPath').click(function() {
           copyPath('<%= sbBuildPath %>');
        });
    });
    
	$('#clipboard').click(function() {
		copyToClipboard($('#console_div').text());
	});
    
    // When  server is  running disable run against source button
    function runAgainstSrcServerRunning() {
    	<% if (permissions != null && !permissions.canManageBuilds()) { %>
	    	disableButton($("#runAgainstSourceStart"));
	    	enableButton($("#runAgainstSourceStop"));
	    	enableButton($("#runAgainstSourceRestart"));
    	<% } %>
	}
    	
 	// When server is not running enable run against source button
    function runAgainstSrcServerDown() {
    	<% if (permissions != null && !permissions.canManageBuilds()) { %>
	    	disableButton($("#runAgainstSourceStop"));
	    	disableButton($("#runAgainstSourceRestart"));
	    	enableButton($("#runAgainstSourceStart"));
    	<% } %>
    }
 	
 	// when server is restarted in run against source 
 	function restartServer() {
 		<% if (permissions != null && !permissions.canManageBuilds()) { %>
	 		$("#console_div").empty();
	 		$("#console_div").html("Server is restarting...");
	 		disableButton($("#runAgainstSourceStop"));
			disableButton($("#runAgainstSourceRestart"));
			readerHandlerSubmit('restartServer', '<%= appId %>', '<%= FrameworkConstants.REQ_RE_START %>', '', false, getBasicParams(), $("#console_div"));
		<% } %>
 	}
 	
 	// when server is stopped in run against source 
	function stopServer() {
		<% if (permissions != null && !permissions.canManageBuilds()) { %>
			$("#console_div").empty();
			$("#console_div").html("Server is stopping...");
			disableButton($("#runAgainstSourceStop"));
			disableButton($("#runAgainstSourceRestart"));
			readerHandlerSubmit('stopServer', '<%= appId %>', '<%= FrameworkConstants.REQ_STOP %>', '', true, getBasicParams(), $("#console_div"));
		<% } %>
 	}
	
	function popupOnOk(obj) {
 		var okUrl = $(obj).attr("id");
		if (okUrl === "build") {
			mandatoryValidation("build", $("#generateBuildForm"), '', 'package', 'package', '<%= FrameworkConstants.REQ_BUILD %>');
		} else if (okUrl === "deploy") {
			mandatoryValidation("deploy", $("#generateBuildForm"), '', 'deploy', 'deploy', '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
		} else if (okUrl === "processBuild") {
			mandatoryValidation("processBuild", $("#generateBuildForm"), '', 'process-build', 'process-build', '<%= FrameworkConstants.REQ_PROCESS_BUILD %>', '<%= appId %>');
		} else if (okUrl === "startServer") {
			$("#console_div").html("Server is starting...");
			disableButton($("#runAgainstSourceStart"));
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			$("#popupPage").modal('hide');
			readerHandlerSubmit('startServer', '<%= appId %>', '<%= FrameworkConstants.REQ_START %>', $("#generateBuildForm"), false, getBasicParams(), $("#console_div"));
		} else if (okUrl === "minifier") {
			var isChecked = $("#minifyAll").is(":checked");
			
			var valueAvailable;
			$('input[name="minifyFileNames"]').each(function () {
				if($(this).val() !== "") {
					valueAvailable = true;
					return false;
				}
			});
			if (!isChecked && !valueAvailable) {
				$("#errMsg").html("Please Select any option");
			} else {
				//To enable compress name text boxes only if it has value
				$('input[name="minifyFileNames"]').each(function () {
					if($(this).val() !== "") {
						$(this).attr("disabled", false);
					}
				});
				
				$('#popupPage').modal('hide');
				progressPopupAsSecPopup('minification', '<%= appId %>', 'minify', $("#minificationForm"), getBasicParams(), '', '', '<%= showIcons %>');
			}	
		} else if(okUrl === "deleteBuild") {
			$("#popupPage").modal('hide');
			// show popup loading icon
 			showProgressBar('<s:text name="progress.txt.delete.build"/>');
			var params = getBasicParams();
			loadContent("deleteBuild", $('#deleteObjects'), $("#subcontainer"), params, '', true);
		}
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "stopServer") {
     		runAgainstSrcServerDown();
		} else if(pageUrl == "checkForConfiguration") {
    		successEnvValidation(data);
    	} else if(pageUrl == "checkForConfigType") {
    		successEnvValidation(data);
    	} else if (pageUrl == "createProfile") {
			successProfileCreation(data);
		} else if (pageUrl == "jsToMinify") {
			updateHiddenField(data.jsFinalName, data.selectedJs, data.browseLocation);
    	} else if (pageUrl == "filesToMinify") {
    		updateMinifyData(data.compressName, data.selectedFilesToMinify, data.browseLocation);
    	}
    }
	
	function deploy(additionalParam) {
		var params = getBasicParams();
		params = params.concat("&");
		params = params.concat(additionalParam);
		readerHandlerSubmit('deploy', '<%= appId %>', '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>', '', false, params, $("#console_div"));
    }
	
	function popupOnCancel(obj) {
		$("#popupPage").modal('hide');
	}
</script>