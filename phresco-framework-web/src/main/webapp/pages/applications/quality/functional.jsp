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

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = appInfo.getId();
	String appDirName = appInfo.getAppDirName();
	String techId = appInfo.getTechInfo().getId();
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String functioanlTestTool = (String) request.getAttribute(FrameworkConstants.REQ_FUNCTEST_SELENIUM_TOOL);
	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
	boolean hasFunctionalLogFile = (Boolean) request.getAttribute(FrameworkConstants.REQ_HAS_FUNCTIONAL_LOG_FILE);
	String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	String projectId = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
%>

<form autocomplete="off" class="marginBottomZero" id="form_test">
	<div class="operation">
		<%-- <% if ((Boolean)request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING)) { %>
			<div class="alert-message warning display_msg" >
				<s:label cssClass="labelWarn" key="build.required.message"/>
		    </div>
		<% } %> --%>
		
		<div class="icon_fun_div printAsPdf">
			<% if (hasFunctionalLogFile) { %>
				<a href="<s:url action='downloadFunctionalLogFile'>
					<s:param name="appId"><%= appId %></s:param>
					<s:param name="customerId"><%= customerId %></s:param>
					<s:param name="projectId"><%= projectId %></s:param>
					</s:url>"><img src="images/icons/download.png" title="<s:text name='title.download.log'/>"/>
				</a>
			<% } %>
			<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path" /></a>
		</div>
		
		<ul id="display-inline-block-example">
			<% if (FrameworkConstants.SELENIUM_GRID.equalsIgnoreCase(functioanlTestTool)) {
					boolean hubStatus = (Boolean) request.getAttribute(FrameworkConstants.REQ_HUB_STATUS);
					String disabledStr = "disabled";
					if (hubStatus) {
					    disabledStr = "";
					}
			%>
					<li id="first" style="width: auto;">
						<input type="button" id="functionalTest" class="btn <%= StringUtils.isNotEmpty(disabledStr) ? "" : "btn-primary"%>" 
							<%= disabledStr %> value="<s:text name='lbl.test'/>">
					</li>
					<li id="first" style="width: auto;">
						<input type="button" id="startHubBtn" class="btn btn-primary" value="<s:text name='lbl.functional.start.hub'/>">
					</li>
					<li id="first">
						<input type="button" id="startNodeBtn" class="btn btn-primary" value="<s:text name='lbl.functional.start.node'/>">
					</li>
			<%
				} else {
			%>
					<li id="first" style="width: auto;">
						<input type="button" id="functionalTest" class="btn btn-primary" value="<s:text name='lbl.test'/>">
					</li>
			<%
				}
	  	 		String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
       			if (StringUtils.isNotEmpty(testError)) {
    		%>
		    	<div class="alert-message block-message warning hideContent" id="errorDiv" style="margin: 5px 0 0 0;">
					<%= testError %>
				</div>
			<% 
				} else {
	        		boolean buttonRow = false;
        			if (CollectionUtils.isNotEmpty(projectModules)) {
        				buttonRow = true;
        	%>
						<li id="label">
							&nbsp;<strong><s:text name="lbl.module"/></strong> 
						</li>
						<li>
							<select id="projectModule" name="projectModule" class="input-large"> 
								<% for (String projectModule : projectModules) { %>
									<option value="<%= projectModule %>"><%= projectModule %></option>
								<% } %>
							</select>
						</li>
			<%
					}
					if (buttonRow) {
			%>
						</ul>
			<%
					}
			%>
		        	<div class="alert alert-block hideContent" id="errorDiv" style="margin: 5px 0 0 0;">
					</div>
			<% 
					if (buttonRow) {
			%>
						<ul id="display-inline-block-example">
							<li id="first"></li>
			<% 
					}
			%>
			
					<li id="label">
						&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="label.test.suite"/></strong> 
					</li>
					<li>
						<select id="testSuite" name="testSuite" class="hideContent"></select>
					</li>
					<li id="label">
						&nbsp;<strong id="view" class="hideCtrl"><s:text name="label.test.result.view"/></strong> 
					</li>
					<li>
						<select id="resultView" name="resultView" class="input-medium hideContent"> 
							<option value="tabular"><s:text name="lbl.test.view.tabular"/></option>
							<option value="graphical"><s:text name="lbl.test.view.graphical"/></option>
						</select>
					</li>
				</ul>
			<%
				}
			%>
	</div>
</form>

<!-- Test suite chart display starts -->
<div id="testSuiteDisplay" class="testSuiteDisplay responsiveTableDisplay" style="height: 87%;"></div>
<!-- Test suite chart display ends -->

<script type="text/javascript">
$(document).ready(function() {
	$("#popupPage").modal('hide');
	showLoadingIcon();
	
	loadTestSuites();
	
	$('#functionalTest').click(function() {
		validateDynamicParam('showFunctionalTestPopUp', '<s:text name="lbl.functional.test"/>', 'runFunctionalTest','<s:text name="lbl.test"/>', '', '<%= Constants.PHASE_FUNCTIONAL_TEST + FrameworkConstants.HYPHEN + functioanlTestTool %>');
	});
	
	$('#startHubBtn').click(function() {
		validateDynamicParam('showStartHubPopUp', '<s:text name="lbl.functional.start.hub"/>', 'startHub','<s:text name="lbl.start"/>', '', '<%= Constants.PHASE_START_HUB %>');
	});
	
	$('#startNodeBtn').click(function() {
		validateDynamicParam('showStartNodePopUp', '<s:text name="lbl.functional.start.node"/>', 'startNode','<s:text name="lbl.start"/>', '', '<%= Constants.PHASE_START_NODE %>');
	});
	
	$("#testResultFile, #testSuite, #testSuiteDisplay, #resultView, #testResultLbl, #view").hide();
				
	$('#resultView').change(function() {
		changeView();
	});
				
	// table resize
	var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
	$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
				
	$('#closeGenerateTest, #closeGenTest').click(function() {
		changeTesting("functional", "testGenerated");
		$(".wel_come").show().css("display","none");
		$("#popup_div").css("display","none");
		$("#popup_div").empty();
	});
				
	$('#testbtn').click(function() {
		$("#popup_div").empty();
		showPopup();
	 	<% 
	 		if (TechnologyTypes.IPHONE_NATIVE.equals(techId)) { 
	 	%>
	 			generateTest('testIphone', 'popup_div');
	 	<% 
	 		} else if (TechnologyTypes.ANDROIDS.contains(techId)) {
	 	%>
	 			generateTest('testAndroid', 'popup_div');
		<% 
	 		} else if (TechnologyTypes.IPHONE_HYBRID.equals(techId)) {
	 	%>
	 			iphone_HybridTest('testIphone', 'Iphone_HybridTest');
		<% 
	 		} else {
	 	%>
	 			generateTest('generateTest', 'popup_div');			 	
	 	<% 
	 		}
	 	%>
	});
	
	$('#openFolder').click(function() {
		openFolder('<%= appDirName %><%= path %>');
	});
       
	$('#copyPath').click(function() {
		copyPath('<%= appDirName %><%= path %>');
	});
		        
	$('#pdfCreation').click(function() {
		var params = "fromPage=";
		params = params.concat("functional");
		params = params.concat("&testType=");
        params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
    });
		        
	$('#projectModule').change(function() {
		loadTestResults();
	});

	$('#testSuite').change(function() {
		testReport();
	});
});
			
//To get the testsuites
function loadTestSuites(updateCahe) {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
	if (updateCahe !== undefined && !isBlank(updateCahe)) {
		params = params.concat("&updateCache=");
		params = params.concat(updateCahe);
	}
	loadContent('fetchFunctionalTestSuites', $('#form_test'), '', params, true, true);
}

function testReport() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
	loadContent('fetchFunctionalTestReport', $('#form_test'), $('#testSuiteDisplay'), params, '', true);
	//show print as pdf icon
	$('#pdfPopup').show();
}

function successEvent(pageUrl, data) {
	if (pageUrl == "checkForConfiguration") {
   		successEnvValidation(data);
   	} else if (pageUrl == "checkForConfigType") {
   		successEnvValidation(data);
   	} else if (pageUrl === "checkForHub") { 
		if (data.connectionAlive) {
			progressPopup('showStartedHubLog', '<%= appId %>', '<%= FrameworkConstants.START_HUB %>', '', '', getBasicParams(), 'stopHub');
		} else {
			yesnoPopup('showStartHubPopUp', '<s:text name="lbl.functional.start.hub"/>', 'startHub', '<s:text name="lbl.start"/>');
		}
	} else if (pageUrl === "checkForNode") { 
		if (data.connectionAlive) {
			progressPopup('showStartedNodeLog', '<%= appId %>', '<%= FrameworkConstants.START_NODE %>', '', '', getBasicParams(), 'stopNode');
		} else {
			yesnoPopup('showStartNodePopUp', '<s:text name="lbl.functional.start.node"/>', 'startNode', '<s:text name="lbl.start"/>');
		}
	} else if (pageUrl === "stopHub") {
		hideLoadingIcon();
		reloadFunctionalPage();
	} else {
   		if ((data != undefined || !isBlank(data)) && data != "") {
			if (data.validated != undefined && data.validated) {
				return validationError(data.showError);
			}
			var testSuiteNames = data.testSuiteNames;
			if ((testSuiteNames != undefined || !isBlank(testSuiteNames))) {
				$("#errorDiv").hide();
				$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView, strong").show();
				$("#testResultFile").hide();
				$('#testSuite').empty();
				$('#testSuite').append($("<option></option>").attr("value", "All").text("All"));
				for (i in testSuiteNames) {
					$('#testSuite').append($("<option></option>").attr("value", testSuiteNames[i]).text(testSuiteNames[i]));
				}
				testReport();
			}
		}
   	}
}

function popupOnStop(obj) {
	var url = $(obj).attr("id");
	var params = getBasicParams();
	if (url === "stopHub") {
		$(".popupStop").hide();
		$("#popup_progress_div").empty();
		showPopuploadingIcon();
		readerHandlerSubmit(url, '<%= appId %>', '<%= FrameworkConstants.STOP_HUB %>', '', '', params, $("#popup_progress_div"));
	} else if (url === "stopNode") {
		$(".popupStop").hide();
		$("#popup_progress_div").empty();
		showPopuploadingIcon();
		readerHandlerSubmit(url, '<%= appId %>', '<%= FrameworkConstants.STOP_NODE %>', '', '', params, $("#popup_progress_div"));
	}
}

function validationError(errMsg) {
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("#testResultFile, #testSuite, #testSuiteDisplay, #resultView, #testResultLbl, #view").hide();
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
			
function generateTest(urlAction, container, event) {
	// load testSuite
	if ($('#testSuites').find("option:selected").attr("id") != undefined) {
		var results = $('#testSuites').find("option:selected").attr("id").split(",");
		var failures = results[0];
		var errors = results[1];
		var tests = results[2];
		var selectedTestResultFile = results[3];
	}
	var testSuite = $("#testSuites").val();
	//changeTestResultFile
	if (event == "testResultFiles") {
		selectedTestResultFile = $('#testResultFiles').val();
	}
	var params = "";
   	if (!isBlank($('form').serialize())) {
   		params = $('form').serialize() + "&";
   	}
	params = params.concat("testSuite=");
	params = params.concat(testSuite);
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
	params = params.concat("&failures=");
	params = params.concat(failures);
	params = params.concat("&errs=");
	params = params.concat(errors);
	params = params.concat("&tests=");
	params = params.concat(tests);
	params = params.concat("&selectedTestResultFileName=");
	params = params.concat(selectedTestResultFile);
	showLoadingIcon($('#'+ container)); // Loading Icon
	performAction(urlAction, params, $('#'+ container));
    escPopup();
}

function iphone_HybridTest() {
	$("#popup_div").css("display","none");
	showConsoleProgress('block');
	readerHandlerSubmit('functional', '<%= appId %>', '<%= FrameworkConstants.FUNCTIONAL %>', '');
}

//Handles the ok button click event in the popup
function popupOnOk(obj) {
	var okUrl = $(obj).attr("id");
	if (okUrl === "startHub") {
		mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'start-hub', 'start-hub', '<%= FrameworkConstants.START_HUB %>', '<%= appId %>', 'stopHub');
	} else if (okUrl === "startNode") {
		mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'start-node', 'start-node', '<%= FrameworkConstants.START_NODE %>', '<%= appId %>', 'stopNode');
	} else if (okUrl === "runFunctionalTest") {
		var goal = 'functional-test-' + '<%= functioanlTestTool %>';
		mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'functional-test', goal, '<%= FrameworkConstants.FUNCTIONAL %>', '<%= appId %>');
	} else if (okUrl === "printAsPdf") {
		printPdfPreActions();
		loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false, true);
	}
}

//after executing the test. when clicking Progress popup, it will call this methid to load test results
function popupOnClose(obj) {
	var closeUrl = $(obj).attr("id");
	if (closeUrl === "startHub" || closeUrl === "showStartedHubLog") {
		showLoadingIcon();
		reloadFunctionalPage();
	} else {
		loadTestSuites("true");
	}
}

function reloadFunctionalPage() {
	var params = getBasicParams();
	loadContent("functional", '', $("#subTabcontainer"), params, '', true);
}
</script> 