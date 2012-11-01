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

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<script type="text/javascript" src="js/delete.js" ></script>
<script type="text/javascript" src="js/confirm-dialog.js" ></script>
<script type="text/javascript" src="js/loading.js" ></script>
<script type="text/javascript" src="js/home-header.js" ></script>
<script type="text/javascript" src="js/reader.js" ></script>

<%
	ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = "";
	if (applicationInfo != null) {
		appId = applicationInfo.getId();
	}
	
	//TODO:Need to handle
// 	String projectCode = appInfo.getCode();
// 	technology = appInfo.getTechInfo().getVersion();
	
	/* List<BuildInfo> buildInfos = (List<BuildInfo>) request.getAttribute(FrameworkConstants.REQ_BUILD);
    String selectedAppType = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_APP_TYPE);
    String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
  	StringBuilder sbBuildPath = new StringBuilder();
  	sbBuildPath.append(projectCode);
  	sbBuildPath.append("/");    //File separator is not working for this function.
  	sbBuildPath.append(FrameworkConstants.CHECKIN_DIR);
  	sbBuildPath.append("/");
  	sbBuildPath.append(FrameworkConstants.BUILD_PATH);*/
    
  	boolean serverStatus = false;
//	boolean serverStatus = Boolean.parseBoolean((String) session.getAttribute(appId + FrameworkConstants.SESSION_SERVER_STATUS));
 	if (session.getAttribute(appId + FrameworkConstants.SESSION_SERVER_STATUS) == null) {
		serverStatus = false;
		} else {
 		serverStatus = session.getAttribute(appId + FrameworkConstants.SESSION_SERVER_STATUS).toString().equals("true") ? true : false;
 	}
	String runAgsSrcLog = (String) request.getAttribute(FrameworkConstants.REQ_SERVER_LOG);
%>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<div class="alert-message warning"  id="warningmsg" style="display:none;">
	<s:label cssClass="labelWarn" key="build.warn.message" />
</div>

<form action="deleteBuild" method="post" autocomplete="off" id="deleteObjects" class="build_form">
    <div class="operation build">
    	<div class="icon_div">
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
			
		<div class="build_delete_btn_div">
		    <a data-toggle="modal" href="#popupPage" id="generateBuild" class="btn btn-primary" additionalParam="from=generateBuild"><s:text name='label.generatebuild'/></a>
			<input id="deleteButton" type="button" value="<s:text name="label.delete"/>" class="btn" disabled="disabled"/>
			<input type="button" class="btn btn-primary" data-toggle="modal" href="#popupPage" id="runAgainstSourceStart" value="<s:text name='label.runagainsrc'/>"/>
		    <input type="button" class="btn" id="runAgainstSourceStop" value="<s:text name='label.stop'/>" disabled onclick="stopServer();"/>
		    <input type="button" class="btn" id="runAgainstSourceRestart" value="<s:text name='label.restart'/>" disabled onclick="restartServer();"/>
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
					<table class="zebra-striped" style="height: 29px;"> 
						<tr class="tr_color">
		    				<th><s:text name="label.progress"/></th>
		    				<th><img src="images/icons/clipboard-copy.png" alt="clipboard" id="clipboard" title="Copy to clipboard"/></th>
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
	if (<%=serverStatus%>) {
		runAgainstSrcServerRunning();
	} else {
		runAgainstSrcServerDown();
	}
	
    $(document).ready(function() {
    	yesnoPopup($('#generateBuild'), 'generateBuild', '<s:text name="label.generatebuild"/>', 'build','<s:text name="label.build"/>');
    	yesnoPopup($('#runAgainstSourceStart'),'showRunAgainstSourcePopup', '<s:text name="label.runagainstsource"/>', 'startServer','<s:text name="label.run"/>');
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
        
        $('#deleteButton').click(function() {
			$("#confirmationText").html("Do you want to delete the selected build(s)");
		    dialog('block');
		    escBlockPopup();
        });
        
        $('form').submit(function() {
			showProgessBar("Deleting Build (s)", 100);
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize();
	    	}
			performAction('deleteBuild', params, $("#tabDiv"));
	        return false;
	    });
        
       <%--  $('#openFolder').click(function() {
            openFolder('<%= sbBuildPath %>');
        });
        
        $('#copyPath').click(function() {
           copyPath('<%= sbBuildPath %>');
        }); --%>
    });
    
	$('#clipboard').click(function() {
		copyToClipboard($('#build-output').text());
	});
    
 	// Its used by iphone alone
    function deploy(obj) {
    	$('#popup_div').empty();
        $("#build-output").empty();
        $("#build-output").html('<%= FrameworkConstants.MSG_IPHONE_DEPLOY %>');
        var currentId = obj.id;
        var idArray = currentId.split('#');
        var buildNumber = idArray[1];
        var params = "buildNumber=";
        params = params.concat(buildNumber);
		<%-- readerHandlerSubmit('deploy', '<%= projectCode %>', 'Deploy', params); --%>
    }
 	
    function copyToClipboard(data) {
        var params = "copyToClipboard=";
        params = params.concat(data);
        performAction('copyToClipboard', params, '');
	}
    
    function generateBuild(projectCode, from, obj) {
    	$('#popup_div').empty();
		showPopup();
       	var currentId = obj.id;
       	var idArray = currentId.split('#');
       	var buildNumber = idArray[1];
       	var buildName = $(obj).attr("buildName");
        var params = "from=";
		params = params.concat(from);
       	//popup('generateBuild', $('#formAppMenu'), $('#popup_div'), '', '', params);
       // escPopup();
    }
    
    function deployAndroid(obj){
    	$('#popup_div').empty();
    	var currentId = obj.id;
        var idArray = currentId.split('#');
        var buildNumber = idArray[1];
		showPopup();
       	var params = "buildNumber=";
		params = params.concat(buildNumber);
       	popup('deployAndroid', params, $('#popup_div'));
    }
   
    function deployIphone(obj) {
    	$('#popup_div').empty();
        var currentId = obj.id;
        var idArray = currentId.split('#');
        var buildNumber = idArray[1];
		showPopup();
       	var params = "buildNumber=";
		params = params.concat(buildNumber);
       	popup('deployIphone', params, $('#popup_div'));
    }
    
    // When  server is  running disable run against source button
    function runAgainstSrcServerRunning() {
    	disableButton($("#runAgainstSourceStart"));
    	enableButton($("#runAgainstSourceStop"));
    	enableButton($("#runAgainstSourceRestart"));
	}
    	
 	// When server is not running enable run against source button
    function runAgainstSrcServerDown() {
    	disableButton($("#runAgainstSourceStop"));
    	disableButton($("#runAgainstSourceRestart"));
    	enableButton($("#runAgainstSourceStart"))
    }
 	
 	// when server is restarted in run against source 
 	function restartServer() {
 		$("#console_div").empty();
 		$("#console_div").html("Server is restarting...");
 		disableButton($("#runAgainstSourceStop"));
		disableButton($("#runAgainstSourceRestart"));
		readerHandlerSubmit('restartServer', '<%= appId %>', '<%= FrameworkConstants.REQ_START %>', '', false, getBasicParams());
 	}
 	
 	// when server is stopped in run against source 
	function stopServer() {
		$("#console_div").empty();
		$("#console_div").html("Server is stopping...");
		disableButton($("#runAgainstSourceStop"));
		disableButton($("#runAgainstSourceRestart"));
		readerHandlerSubmit('stopServer', '<%= appId %>', '<%= FrameworkConstants.REQ_STOP %>', '', true, getBasicParams());
 	}
	
	function popupOnOk(obj) {
 		var okUrl = $(obj).attr("id");
		if (okUrl === "build") {
			if ($('input[type=checkbox][name=signing]').is(':checked') && isBlank($('#profileAvailable').val())) {
				$("#errMsg").html('<%= FrameworkConstants.PROFILE_CREATE_MSG %>');
				return false;
			}
			/* enable text box only if any file selected for minification */
			$('input[name="jsFileName"]').each(function () {
				if($(this).val() !== "") {
					$(this).attr("disabled", false);
				}
			});
			buildValidateSuccess("build", '<%= FrameworkConstants.REQ_BUILD %>');
		} else if (okUrl === "deploy") {
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			buildValidateSuccess("deploy", '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
		} else if (okUrl === "startServer") {
			$("#console_div").html("Server is starting...");
			disableButton($("#runAgainstSourceStart"));
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			readerHandlerSubmit('startServer', '<%= appId %>', '<%= FrameworkConstants.REQ_START %>', $("#generateBuildForm"), false, getBasicParams());
		}
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "stopServer") {
     		runAgainstSrcServerDown();
		} else if(pageUrl == "checkForConfiguration") {
    		successEnvValidation(data);
    	} else if(pageUrl == "checkForConfigType") {
    		successEnvValidation(data);
    	} else if(pageUrl == "fetchBuildInfoEnvs") {
    		fillVersions("environments", data.buildInfoEnvs);
    	} else if (pageUrl == "createProfile") {
			successProfileCreation(data);
		} else if (pageUrl == "getSqlDatabases") {
			if (isBlank(data.databases)) {
				$("#errMsg").html('<%= FrameworkConstants.CONFIGURATION_UNAVAILABLE %>'); 
				hideLoadingIcon();
			} else {
				fillVersions("databases", data.databases , "");
				//getting sql files to be executed
				getSQLFiles();
			}
		} else if (pageUrl == "fetchSQLFiles") {
			fillVersions("avaliableSourceScript", data.sqlFiles , "getSQLFiles");
			// after sql files are loaded, already selected value should be hidden in available list
			hideDbWithVersions();
		} else if (pageUrl == "jsToMinify") {
			updateHiddenField(data.jsFinalName, data.selectedJs, data.browseLocation);
    	} else if (pageUrl == "dependancyListener") {
    		updateDependantValue(data);
    	}
    }
</script>