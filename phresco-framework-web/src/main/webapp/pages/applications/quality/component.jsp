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

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<script src="js/reader.js" ></script>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = appInfo.getId();
	String appDirName = appInfo.getAppDirName();
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<Parameter> parameters = (List<Parameter>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_PARAMETERS);
	String requestIp = (String) request.getAttribute(FrameworkConstants.REQ_REQUEST_IP);
    String showOpenFolderIcon = (String) session.getAttribute(requestIp);
%>

<form autocomplete="off" class="marginBottomZero" id="form_test">
	<div class="operation">
		<div class="icon_fun_div printAsPdf">
			<a href="#" id="pdfPopup" style="display: none;">
				<img id="pdfCreation" src="images/icons/print_pdf.png" title="Generate pdf" style="height: 20px; width: 20px;"/>
			</a>
		 <% if (Boolean.parseBoolean(showOpenFolderIcon))  {%>
			<a href="#" id="openFolder">
				<img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/>
			</a>
		 <% } %>	
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
		
		<ul id="display-inline-block-example">
			<li id="first">
				<a id="componentTest" class="btn btn-primary"><s:text name='lbl.test'/></a>
			</li>
			<% 
				boolean buttonRow = false;
				if (buttonRow) {
			%>
				</ul>
			<% } %>

        	<div class="alert alert-block hideContent" id="errorDiv" style="margin-left: 0; margin-top: 5px;">
				
			</div>

			<% if (buttonRow) { %>
				<ul id="display-inline-block-example">
					<li id="first"></li>
			<% } %>
					<li id="label">
						&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="lbl.test.suite"/></strong> 
					</li>
					<li>
						<select id="testSuite" name="testSuite" class="hideContent"></select>
					</li>
					
					<li id="label">
						&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="lbl.test.result.view"/></strong> 
					</li>
					<li>
						<select id="resultView" name="resultView" class="input-medium hideContent"> 
							<option value="tabular"><s:text name="lbl.test.view.tabular"/></option>
							<option value="graphical"><s:text name="lbl.test.view.graphical"/></option>
						</select>
					</li>
				</ul>
	</div>
</form>

<!-- Test suite chart display starts -->
<div id="testSuiteDisplay" class="testSuiteDisplay responsiveTableDisplay"></div>
<!-- Test suite chart display ends -->

<script type="text/javascript">
$(document).ready(function() {
	showLoadingIcon();
	
	$('#componentTest').click(function() {
		validateDynamicParam('showComponentTestPopUp', '<s:text name="lbl.component.test"/>', 'runComponentTest','<s:text name="lbl.test"/>', '', '<%= Constants.PHASE_COMPONENT_TEST %>', true);
	});
	
	loadTestSuites();
	
	$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").hide();
	
	$('#resultView').change(function() {
		changeView();
	});
	
	// table resize
	var tblheight = ($("#subTabcontainer").height() - $("#form_test").height());
	$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
	
    $('#openFolder').click(function() {
		openFolder('<%= appDirName %><%= path %>');
	});
       
	$('#copyPath').click(function() {
		copyPath('<%= appDirName %><%= path %>');
	});
    
	$('#pdfCreation').click(function() {
		var params = "fromPage=component";
		params = params.concat("&testType=");
	    params = params.concat('<%= FrameworkConstants.COMPONENT %>');
		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
    });
       
	$('#testSuite').change(function() {
		testReport();
	});
});

//To get the testsuites
function loadTestSuites(updateCahe) {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.COMPONENT %>');
	if (updateCahe !== undefined && !isBlank(updateCahe)) {
		params = params.concat("&updateCache=");
		params = params.concat(updateCahe);
	}
	loadContent('fetchComponentTestSuites', $('#form_test'), '', params, true, true);
}

function testReport() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.COMPONENT %>');
	loadContent('fetchComponentTestReport', $('#form_test'), $('#testSuiteDisplay'), params, '', true);
	//show print as pdf icon
	$('#pdfPopup').show();
}

function successEvent(pageUrl, data) {
	if (pageUrl == "fetchComponentTestSuites") {
		if ((data != undefined || !isBlank(data))) {
			if (data.validated != undefined && data.validated) {
				return validationError(data.showError);
			}
			var testSuiteNames = data.testSuiteNames;
			if ((testSuiteNames != undefined || !isBlank(testSuiteNames))) {
				$("#errorDiv").hide();
				$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").show();
				$("#testResultFile").hide();
				$('#testSuite').empty();
				$('#testSuite').append($("<option></option>").attr("value", "All").text("All"));
				fillSelectbox($('#testSuite'), testSuiteNames, 'All', 'All');
				testReport();
			}
		}		
	}
}

function validationError(errMsg) {
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").hide();
	hideLoadingIcon();
}

function changeView() {
	var resultView = $('#resultView').val();
	if (resultView == 'graphical') {
		$("#graphicalView").show();
		$("#tabularView").hide();
	} else  {
		$("#graphicalView").hide();
		$("#tabularView").show();
	}
}

//Handles the ok button click event in the popup
function popupOnOk(obj) {
	var okUrl = $(obj).attr("id");
	if (okUrl === "printAsPdf") {
		printPdfPreActions();
		loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false);
	} else {
		$("#popupPage").modal('hide');
		var params = getBasicParams();
		progressPopupAsSecPopup(okUrl, '<%= appId %>', '<%= FrameworkConstants.COMPONENT %>', $("#generateBuildForm"), params);
	}
}

// after executing the test. when clicking Progress popup , it will call this methid to load test results
function popupOnClose(obj) {
	showLoadingIcon();
	var closeUrl = $(obj).attr("id");
	loadTestSuites("true");
}

//To handle the cancel btn events
function popupOnCancel(obj) {
	var params = getBasicParams();
	params = params.concat("&actionType=");
	params = params.concat("componentPdfReport");
	loadContent("killProcess", '', '', params);
}
</script>