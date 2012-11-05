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

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<script src="js/reader.js" ></script>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = appInfo.getId();
	String techId = appInfo.getTechInfo().getId();
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
	List<Parameter> parameters = (List<Parameter>) request.getAttribute(FrameworkConstants.REQ_DYNAMIC_PARAMETERS);
%>

<form autocomplete="off" class="marginBottomZero" id="form_test">
	<div class="operation">
            <%-- <%
            	Boolean showWarning = (Boolean) request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING);
            	if (showWarning) {
            %>
	            <div class="alert-message warning display_msg" >
					<s:label cssClass="labelWarn" key="build.required.message"/>
			    </div>
		   <%
            	}
		   %> --%>
		<div class="icon_fun_div printAsPdf">
			<a href="#" id="pdfPopup" style="display: none;">
				<img id="pdfCreation" src="images/icons/print_pdf.png" title="Generate pdf" style="height: 20px; width: 20px;"/>
			</a>
			<a href="#" id="openFolder">
				<img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/>
			</a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
		
		<ul id="display-inline-block-example">
			<li id="first">
				<a data-toggle="modal" href="#popupPage" id="unitTest" class="btn btn-primary"><s:text name='lbl.test'/></a>
			</li>
			<% 
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
				if (TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.contains(techId) || TechnologyTypes.HTML5_WIDGET.contains(techId) || 
					TechnologyTypes.HTML5_MOBILE_WIDGET.contains(techId) || TechnologyTypes.JAVA_WEBSERVICE.contains(techId)) {
					buttonRow = true;
			%>
			<li id="label" class="techLabel">
				&nbsp;<strong><s:text name="label.technolgies"/></strong> 
			</li>
			<li>
				<select id="techReport" name="techReport"> 
					<option value="java" id="java" >Java</option>
				  	<option value="javascript" id="javascript" >Java Script</option>
				</select>
			</li>
			<% 	
				}
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
	<% if (CollectionUtils.isNotEmpty(parameters) || CollectionUtils.isNotEmpty(projectModules)) { %>
		yesnoPopup($('#unitTest'), 'showUnitTestPopUp', '<s:text name="lbl.unit.test"/>', 'runUnitTest','<s:text name="lbl.test"/>');
	<% } else { %>
		progressPopup($('#unitTest'), 'runUnitTest', '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.UNIT %>', '', '', getBasicParams());
	<% } %>
	
	loadTestSuites();
	
	$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").hide();
	
	$('#resultView').change(function() {
		changeView();
	});
	
	// table resize
	var tblheight = ($("#subTabcontainer").height() - $("#form_test").height());
	$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
	
	<%-- $('#testbtn').click(function() {
	 	<% if (TechnologyTypes.ANDROIDS.contains(techId)) { %>
			openAndroidPopup();
		<% } else if (TechnologyTypes.IPHONES.contains(techId)) { %>
			openIphoneNativeUnitTestPopup();
		<% } else { %>
			// If the project is having modules it have to display modules after that it have to display unit test progress
			<% if (CollectionUtils.isNotEmpty(projectModules)) { %>
				openUnitTestPopup();
			<% } else { %>
				unitTestProgress();
			<% } %>
		<% } %>
    }); --%>
    
    $('#openFolder').click(function() {
		openFolder('<%= appId %><%= path %>');
	});
       
	$('#copyPath').click(function() {
		copyPath('<%= appId %><%= path %>');
	});
    
	$('#pdfCreation').click(function() {
   		showPopup();
   		$('#popup_div').empty();
		var params = "testType=";
		params = params.concat("unit");
   		popup('printAsPdfPopup', params, $('#popup_div'));
   	    escPopup();
    });
       
	$('#closeGenerateTest, #closeGenTest').click(function() {
		changeTesting("unit", "testGenerated");
		enableScreen();
		$("#popup_div").css("display","none");
		$("#popup_div").empty();
	});
	
	$('#projectModule, #techReport').change(function() {
		loadTestSuites();
	});

	$('#testSuite').change(function() {
		testReport();
	});
});

//To get the testsuites
function loadTestSuites() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.UNIT %>');
	loadContent('fetchUnitTestSuites', $('#form_test'), '', params, true);
}

function testReport() {
	var params = getBasicParams();
	params = params.concat("&testType=");
	params = params.concat('<%= FrameworkConstants.UNIT %>');
	loadContent('fetchUnitTestReport', $('#form_test'), $('#testSuiteDisplay'), params);
	//show print as pdf icon
	$('#pdfPopup').show();
}

function successEvent(pageUrl, data) {
	if (pageUrl == "fetchUnitTestSuites") {
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

//Handles the ok button click event in the popup
function popupOnOk(obj) {
	var okUrl = $(obj).attr("id");
	var params = getBasicParams();
	progressPopupAsSecPopup(okUrl, '<s:text name="lbl.progress"/>', '<%= appId %>', '<%= FrameworkConstants.UNIT %>', $("#generateBuildForm"), params);
}
	    
/* function openAndroidPopup() {
	showPopup();
	var params = "testType=";
	params = params.concat("unit");
	popup('testAndroid', params, $('#popup_div'));
}
	    
function openIphoneNativeUnitTestPopup() {
	var params = "testType=";
	params = params.concat("unit");
	popup('testIphone', params, $('#popup_div'));
}
		
function openUnitTestPopup() {
	showPopup();
	var params = "testType=";
	params = params.concat("unit");
	popup('generateUnitTest', params, $('#popup_div'));
} */
</script>