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

<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APP_INFO);
	String appId = appInfo.getId();
	String appDirName = appInfo.getAppDirName();
	String techId = appInfo.getTechInfo().getId();
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	//Must be Removed
	Boolean popup = Boolean.FALSE;
	String path = (String) request.getAttribute(FrameworkConstants.PATH); 
	String requestIp = (String) request.getAttribute(FrameworkConstants.REQ_REQUEST_IP);
 	String showIcons = (String) session.getAttribute(requestIp);
    String iconClass = "";
	if (!Boolean.parseBoolean(showIcons))  {
		iconClass = "hideIcons";
	}
	
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	String per_disabledClass = "btn-primary";
	if (permissions != null && !permissions.canManageTests()) {
		per_disabledStr = "disabled";
		per_disabledClass = "btn-disabled";
	}
%>


<style type="text/css">
	.testSuiteDisplay {
	    height: 98%;
	    position: relative;
	    top: -32px;
	}
	.testSuiteListAdj {
	    margin-bottom: 8px;
	    margin-top: 6px;
	    position: relative;
	     top: -30px;
	 }
</style>

<form action="load" method="post" autocomplete="off" class="marginBottomZero" id="form_load">
   <!--  <div class="frame-header frameHeaderPadding btnTestPadding"> -->
     <div class="operation">
		<input id="loadTestBtn" type="button" value="<s:text name="label.test"/>" <%= per_disabledStr %> additionalParam="from=load" 
			class="btn <%= per_disabledClass %> env_btn">
        <div class="icon_fun_div printAsPdf">
        	<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" class="<%= iconClass %>"  id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" class="<%= iconClass %>"  id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
		 
		<strong id="lblType" class="hideContent noTestAvail"><s:text name="label.types"/></strong>&nbsp;
    	<select class="hideContent noTestAvail" id="testResultsType" name="testResultsType" style="width :100px;"> 
			<option value="server" ><s:text name="label.application.server"/></option>
			<option value="webservice" ><s:text name="label.webservices"/></option>
		</select>&nbsp; 
		
		<strong class="hideContent noTestAvail" id="testResultFileTitle"><s:text name="label.test.results"/></strong> 
        <select  class="hideContent noTestAvail" id="testResultFile" name="testResultFile">
        </select>
        
        
        <div class="hideContent noTestAvail perTabularView" id="loadDropdownDiv">
			<div class="resultView" style="float: left;" >
				<strong class="noTestAvail viewTitle" id="dropdownDiv"><s:text name="lbl.test.result.view"/></strong> 
				<select id="loadResultView" name="resultView232"> 
					<option value="tabular" ><s:text name="lbl.test.view.tabular"/></option>
					<option value="graphical" ><s:text name="lbl.test.view.graphical"/></option>
				</select>
			</div>
		</div>
    </div>
    <div class="perErrorDis" id="noFiles">
		<div class="alert alert-block" id="loadError">
			<s:text name="loadtest.not.executed"/>
		</div>
	</div>
</form>
<!-- load test button ends -->

<div id="testResultDisplay" class="testSuiteDisplay" style="margin-top:44px;">
</div>


	

<script type="text/javascript">
    
	    $(document).ready(function() {
	    	isLoadResultFileAvailbale(); //check for load result files;
	    	hideLoadingIcon();
	    	$('#loadTestBtn').click(function() {
	    		var params = getBasicParams();
	    		params = params.concat("&actionType=");
	    		params = params.concat('<%= FrameworkConstants.LOAD %>');
	    		loadContent("checkForLock", '', '', params, true, true);
	    	});
	    	
	    	//Disable test button for load
	    	if(<%= popup %>){
	    		disableControl($("#testbtn"), "btn disabled");	
	    	}
	    	
	        $('#testResultsType').change(function() {
	        	loadTestResultsFiles();
			});
	        
	        $('#testResultFile').change(function() {
	        	loadTestResults();
	        });
	        
	        $('#openFolder').click(function() {
	             openFolder('<%= appDirName %><%= path %>');
	         });
	         
	         $('#copyPath').click(function() {
	            copyPath('<%= appDirName %><%= path %>');
	         });
	         
	        $('#pdfCreation').click(function() {
	    		var params = "fromPage=";
	    		params = params.concat("load");
	    		params = params.concat("&testType=");
	            params = params.concat('<%= FrameworkConstants.LOAD %>');
	    		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
	 	    });
	    });
    	var testResultsType = "";
	    function isLoadResultFileAvailbale() {
	    	var params = getBasicParams();
	    	params = params.concat("&testType=");
	    	params = params.concat('<%= FrameworkConstants.LOAD %>');
	    	loadContent('loadTestResultAvail', '', '', params, true, true);
	    }
	    
	    function successEvent(pageUrl, data) {
	    	if (pageUrl == "loadTestResultAvail") {
				if (!data.resultFileAvailable) {
		       		$("#noFiles").show();
		       		loadTestResultNotAvail();
		       	 	hideLoadingIcon();
				} else {
		       		loadTestResultsFiles();
		       	}
			} else if (pageUrl == "fetchLoadTestResultFiles") {
				successLoadTestResultsFiles(data);
			} else if (pageUrl == "checkForLock") {
				if (!data.locked) {
					validateDynamicParam('showLoadTestPopup', '<s:text name="label.load.test"/>', 'runLoadTest','<s:text name="label.test"/>', '', '<%= Constants.PHASE_LOAD_TEST %>', true);
				} else {
					var warningMsg = '<s:text name="lbl.app.warnin.msg"/> ' + data.lockedBy + ' at ' + data.lockedDate +".";
					showWarningMsg('<s:text name="lbl.app.warnin.title"/>', warningMsg);
				}
			}
	    }
	    
	    function loadTestResultNotAvail() {
	    	$("#testResultFile").hide();
	    	$("#testResultFileTitle").hide();
	    	$("#lblType").hide();
	    	$("#testResultsType").hide();
	    	$("#testResultDisplay").empty(); 
			$("#testResultDisplay").hide();
			$(".perTabularView").hide();
	    }
	    
	    function successLoadTestResultsFiles(data) {
	    	var resultFiles = data.testResultFiles;
	    	$('#lblType').show();
			$('#testResultsType').show();
			if (resultFiles != null && !isBlank(resultFiles)) {
				$('#testResultFileTitle').show();
	       		$('#testResultFile').show();
	       		$('.perTabularView').show();
	       		$('.viewTitle').show();
	       		$('#loadResultView').show();
	       		$('#testResultFile').empty();
				for (i in resultFiles) {
	            	$('#testResultFile').append($("<option></option>").attr("value",resultFiles[i]).text(resultFiles[i]));
	            }
	            $("#noFiles").hide(); // hides no result found error message when there is test result file
	            $("#testResultDisplay").show();
	            loadTestResults();
	       	} else {
	       		$("#noFiles").show();
	       		$('.viewTitle').hide();
	       		$("#testResultFile").hide();
		    	$("#testResultFileTitle").hide();
	       		$("#testResultDisplay").empty(); 
	    		$("#testResultDisplay").hide();
	       		$('#loadError').empty();
	       		$('#loadResultView').hide();
	       		$('.perTabularView').hide();
	       		$('#loadError').html("Load test not yet executed for " + testResultsType);
	       		testResultAvailShowList();
	       	}
	    }
	    
	    function loadTestResults() {
			//show print as pdf icon
			$('#pdfPopup').show();
	    	var params = getBasicParams();
	    	var testResult = $("#testResultFile").val();
	    	params = params.concat("&testType=");
	    	params = params.concat('<%= FrameworkConstants.LOAD %>');
			params = params.concat("&testResultFile=");
			params = params.concat(testResult);
	        $("#testResultDisplay").empty();
	        loadContent('loadTestResult', $('#form_load'), $('#testResultDisplay'), params, '', true);
	   } 
	    
	  //To get the test result files
	    function loadTestResultsFiles() {
	    	testResultsType = $("#testResultsType").val();
	        loadContent('fetchLoadTestResultFiles', $('#form_load'), '', getBasicParams(), true, true);
	    }
	  
	    function generateJmeter(testType) {
			showPopup();
			$('#popup_div').empty();
			var params = "testType=";
			params = params.concat(testType);
			popup('generateJmeter', params, $('#popup_div'));
			escPopup();
        }
        
		function openAndroidPopup(){
			$('#popup_div').empty();
			showPopup();
			var params = "testType=";
			params = params.concat("load");
			popup('testAndroid', params, $('#popup_div'));
		}
		
		//Handles the ok button click event in the popup
		function popupOnOk(obj) {
			var okUrl = $(obj).attr("id");
			if (okUrl === "printAsPdf") {
				printPdfPreActions();
				loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false, true);
			} else {
				mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'load-test', 'load-test', '<%= FrameworkConstants.LOAD %>', '<%= appId %>', '',  '<%= showIcons %>');
			}
		}		
		
		function popupOnClose(obj) {
			//var closeUrl = $(obj).attr("id");
			isLoadResultFileAvailbale();
		}
		
		//To get the testsuites
		function loadTestSuites() {
			var params = getBasicParams();
			params = params.concat("&testType=");
			params = params.concat('<%= FrameworkConstants.LOAD%>');
			loadContent('testType', $('#testResultDisplay'), $("#subTabcontainer"), params, '', true);
		}
		
		function popupOnCancel(obj) {
			var params = getBasicParams();
			params = params.concat("&actionType=");
			params = params.concat("loadPdfReport");
			loadContent("killProcess", '', '', params);
		}
		
		function runLoadTest() {
			progressPopup('runLoadTest', '<%= appId %>', '<%= FrameworkConstants.LOAD %>', '', '', getBasicParams());
		}
    </script>