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
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
%>

<form autocomplete="off" class="marginBottomZero" id="form_test">
	<div class="operation">
		<%-- <% if ((Boolean)request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING)) { %>
			<div class="alert-message warning display_msg" >
				<s:label cssClass="labelWarn" key="build.required.message"/>
		    </div>
		<% } %> --%>
		
		<div class="icon_fun_div printAsPdf">
			<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path" /></a>
		</div>
		
		<ul id="display-inline-block-example">
			<li id="first" style="width: auto;">
				<input type="button" id="functionalTest" class="btn btn-primary" value="<s:text name='lbl.test'/>">
			</li>
			<% if (FrameworkConstants.SELENIUM_GRID.equals(functioanlTestTool)) { %>
				<li id="first" style="width: auto;">
					<input type="button" id="startHubBtn" class="btn btn-primary" value="<s:text name='lbl.functional.start.hub'/>">
				</li>
				<li id="first">
					<input type="button" id="startNodeBtn" class="btn btn-primary" value="<s:text name='lbl.functional.start.node'/>">
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
		        	<div class="alert-message block-message warning hideCtrl" id="errorDiv" style="margin: 5px 0 0 0;">
						<center><label class="errorMsgLabel"></label></center>
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
	hideLoadingIcon();
	
	loadTestSuites();
	
	$('#functionalTest').click(function() {
		validateDynamicParam('showFunctionalTestPopUp', '<s:text name="lbl.functional.test"/>', 'runFunctionalTest','<s:text name="lbl.test"/>', '', '<%= Constants.PHASE_FUNCTIONAL_TEST %>');
	});
	
	$('#startHubBtn').click(function() {
		validateDynamicParam('showStartHubPopUp', '<s:text name="lbl.functional.start.hub"/>', 'startHub','<s:text name="lbl.start"/>', '', '<%= Constants.PHASE_START_HUB %>');
	});
	
	$('#startNodeBtn').click(function() {
		validateDynamicParam('showStartNodePopUp', '<s:text name="lbl.functional.start.node"/>', 'startNode','<s:text name="lbl.start"/>', '', '<%= Constants.PHASE_START_NODE %>');
	});
	
	
// 	yesnoPopup($('#functionalTest'), 'showFunctionalTestPopUp', '<s:text name="lbl.functional.test"/>', 'runFunctionalTest','<s:text name="lbl.test"/>');
// 	yesnoPopup($('#startHubBtn'), 'showStartHubPopUp', '<s:text name="lbl.functional.start.hub"/>', 'startHub','<s:text name="lbl.start"/>');
// 	yesnoPopup($('#startNodeBtn'), 'showStartNodePopUp', '<s:text name="lbl.functional.start.node"/>', 'startNode','<s:text name="lbl.start"/>');
	
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
   		showPopup();
   		$('#popup_div').empty();
		var params = "testType=";
		params = params.concat("functional");
   		popup('printAsPdfPopup', params, $('#popup_div'));
   	    escPopup();
    });
		        
	$('#projectModule').change(function() {
		loadTestResults();
	});

	$('#testSuite').change(function() {
		testReport();
	});
});
			
//To get the testsuites
function loadTestSuites() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
	loadContent('fetchFunctionalTestSuites', $('#form_test'), '', params, true);
}

function testReport() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.FUNCTIONAL %>');
	loadContent('fetchFunctionalTestReport', $('#form_test'), $('#testSuiteDisplay'), params);
	//show print as pdf icon
	$('#pdfPopup').show();
}

function successEvent(pageUrl, data) {
	if (pageUrl == "checkForConfiguration") {
   		successEnvValidation(data);
   	} else if (pageUrl == "checkForConfigType") {
   		successEnvValidation(data);
   	} else if (pageUrl == "fetchBuildInfoEnvs") {
   		fillVersions("environments", data.buildInfoEnvs);
   	} else if (pageUrl === "changeEveDependancyListener") {
		showHideAndUpdateData(data);
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

function validationError(errMsg) {
	$(".errorMsgLabel").html(errMsg);
	$("#errorDiv").show();
	$("#testResultFile, #testSuite, #testSuiteDisplay, #resultView, #testResultLbl, #view").hide();
	enableScreen();
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
    escPopup()
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
		var params = getBasicParams();
		progressPopupAsSecPopup(okUrl, '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.START_HUB %>', $("#generateBuildForm"), params);
	} else if (okUrl === "startNode") {
		var params = getBasicParams();
		progressPopupAsSecPopup(okUrl, '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.START_NODE %>', $("#generateBuildForm"), params);
	} else if (okUrl === "runFunctionalTest") {
		var params = getBasicParams();
		progressPopupAsSecPopup(okUrl, '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.FUNCTIONAL %>', $("#generateBuildForm"), params);
	}
}
</script> 