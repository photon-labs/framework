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

<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues"%>
<%@ page import="com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value"%>

<%
	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = appInfo.getId();
	String appDirName = appInfo.getAppDirName();
	String techId = appInfo.getTechInfo().getId();
	List<Value>  testAgainstPsblVals= (List<Value>) request.getAttribute(FrameworkConstants.REQ_TEST_AGAINST_VALUES);
	String showDevice = (String) request.getAttribute(FrameworkConstants.SHOW_ANDROID_DEVICE);
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
%>

<style>
.perOpenAndCopyPath {
    float: right;
    padding-right: 3px;
    position: relative;
    top: 2px;
}

.perTabularView {
    float: right;
    position: relative;
    top: -32px;
    width: auto;
    left: -79px;
}
</style>
	
<form autocomplete="off" style="margin-bottom: 0px; height: 100%;" id="formPerformance">
	<div class="operation">
        <%-- <%
        	if ((Boolean)request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING)) {
        %>
			<div class="alert-message warning display_msg" >
				<s:label cssClass="labelWarn" key="build.required.message"/>
	   		</div>
   		<%
           	}
		%> --%>
	    <input id="performanceTest" type="button" value="<s:text name='lbl.test'/>" class="btn btn-primary">
	    <div class="perOpenAndCopyPath">
	    	<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>&nbsp;
		
		<% if (CollectionUtils.isNotEmpty(testAgainstPsblVals)) { %>
		    <strong id="lblType" class="noTestAvail"><s:text name="label.types"/></strong>&nbsp;
	    	<select class="noTestAvail" id="testResultsType" name="testResultsType" style="width :100px;"> 
				<% for (Value testAgainstPsblVal : testAgainstPsblVals) { %>
					<option value='<%= testAgainstPsblVal.getKey() %>'><%= testAgainstPsblVal.getValue() %></option>
				<% } %>	    	
			</select>&nbsp;
		<% } %>
		
    	<strong class="noTestAvail" id="testResultFileTitle"><s:text name="label.test.results"/></strong> 
        <select  class="noTestAvail" id="testResultFile" name="testResultFile">
        </select>
        <%
        if (StringUtils.isNotEmpty(showDevice) && Boolean.parseBoolean(showDevice)) {
        %>
	        <strong class="noTestAvail" id="testResultDeviceName"><s:text name="label.device"/></strong> 
	        <select  class="noTestAvail" id="testResultDeviceId" name="testResultDeviceId" style="width :100px;">
	        </select>
        <%
        	}
        %>
	</div>
		
	<div class="noTestAvail perTabularView" id="dropdownDiv">
		
		<div class="resultView" style="float: left;" >
			<strong class="noTestAvail" id="dropdownDiv"><s:text name="lbl.test.result.view"/></strong> 
			<select id="resultView" name="resultView232"> 
				<option value="tabular" ><s:text name="lbl.test.view.tabular"/></option>
				<option value="graphical" ><s:text name="lbl.test.view.graphical"/></option>
			</select>
		</div>
	</div>
	
	<div id="graphBasedOn" class="functional_header perBasedOn">
		<div class="perBasedOnLbl" style="padding-left: 81px;">
			&nbsp;&nbsp;
			<strong id="lblType" class="noTestAvail"><s:text name="label.based.on"/></strong>
			<select  name="showGraphFor" id="showGraphFor" onchange="changeGraph(this.options[this.selectedIndex].value)"  style="width :210px;">
				<option value="responseTime"><s:text name="lbl.performance.avg.response.time"/></option>
				<option value="throughPut"><s:text name="lbl.performance.throughput"/></option>
				<option value="minResponseTime"><s:text name="lbl.performance.min.res.time"/></option>
				<option value="maxResponseTime"><s:text name="lbl.performance.max.res.time"/></option>
				<option value="all"><s:text name="lbl.performance.all"/></option>
			</select>
		</div>&nbsp;&nbsp;
	</div>
		
    <div id="testResultDisplay" class="testSuiteDisplay">
    </div>
	    
	<div class="perErrorDis" id="noFiles">
		<div class="alert alert-block" id="performanceError">
			<s:text name="performancetest.not.executed"/>
		</div>
	</div>
</form>

<script type="text/javascript">
    $(document).ready(function() {
    	showLoadingIcon();
    	isResultFileAvailbale();//Check for the performance test result
    	
    	$('#performanceTest').click(function() {
    		$('#popupPage').css("width", "675px");
    		validateDynamicParam('showPerformanceTestPopUp', '<s:text name="lbl.performance.test"/>', 'runPerformanceTest','<s:text name="lbl.test"/>', '', '<%= Constants.PHASE_PERFORMANCE_TEST  %>');
    	});
    	
		$(".noTestAvail").hide();// When there is no result table, it hides everything except test button
		$("#graphBasedOn").hide();
		// This specifies the recent test type , runned by user
		// make the seelct option selected which is selected in performance pop-up(When user close popup it is hidded)
		var selectJmeterTest = $('input:radio[name=jmeterTestAgainst]:checked').val();
		if (selectJmeterTest != undefined && !isBlank(selectJmeterTest)) {
			$("#testResultsType").val( selectJmeterTest ).attr('selected',true);
       	}
		
		$('#testResultsType').change(function() {
			performanceTestResultsFiles();
		});
		
		$('#testResultFile').change(function() {
			<%
			if (StringUtils.isNotEmpty(showDevice) && Boolean.parseBoolean(showDevice)) {
			%>
				getDevicesName();
			<%
				} else {
			%>
				performanceTestResults($("#testResultsType").val());
			<%
				}
			%>
		});
		
		$('#testResultDeviceId').change(function() {
			performanceTestResults($("#testResultsType").val());
		});
		
        $('#openFolder').click(function() {
            openFolder('<%= appDirName %><%= path %>');
        });
         
        $('#copyPath').click(function() {
        	copyPath('<%= appDirName %><%= path %>');
        });
        
        $('#pdfCreation').click(function() {
    		var params = "fromPage=";
    		params = params.concat("performance");
    		params = params.concat("&testType=");
            params = params.concat('<%= FrameworkConstants.PERFORMACE %>');
    		yesnoPopup('showGeneratePdfPopup', '<s:text name="lbl.app.generatereport"/>', 'printAsPdf','<s:text name="lbl.app.generate"/>', '', params);
	    });
        
    });
   
    var testResultsType = "";
    
	function generateJmeter(testType) {
		 $('#popup_div').empty();
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	showPopup();
		performAction('generateJmeter', params, $('#popup_div'));
  		$(document).keydown(function(e) {
			// ESCAPE key pressed
          	if (e.keyCode == 27) {
              	$(".wel_come").show().css("display","none");
              	$('.popup_div').show().css("display","none");
            }
      	});
		return false;
        
	}
       
	//To check for the performance test result 
    function isResultFileAvailbale() {
    	loadContent('performanceTestResultAvail', '', '', getBasicParams(), true, true);
    }
    
	//To get the test result files
    function performanceTestResultsFiles() {
        testResultsType = $("#testResultsType").val();
        loadContent('fetchPerformanceTestResultFiles', $('#formPerformance'), '', getBasicParams(), true, true);
    }
    
    function successPerfTestResultsFiles(data) {
    	var resultFiles = data.testResultFiles;
		if (resultFiles != null && !isBlank(resultFiles)) {
       		$('#testResultFile').show();
       		$('#testResultFile').empty();
			for (i in resultFiles) {
            	$('#testResultFile').append($("<option></option>").attr("value",resultFiles[i]).text(resultFiles[i]));
            }
            $("#noFiles").hide(); // hides no result found error message when there is test result file
            $("#testResultDisplay").show();
			<%
			if (StringUtils.isNotEmpty(showDevice) && Boolean.parseBoolean(showDevice)) {
			%>
				getDevicesName();
				disableType();
			<%
				} else {
			%>
					performanceTestResults(testResultsType);
			<%
				}
			%>
       	} else {
       		$("#noFiles").show();       		
       		$('#performanceError').empty();
       		$('#performanceError').html("Performance test not yet executed for " + testResultsType);
       		testResultAvailShowList();
       	}
    }
    
    function performanceTestResults() {
		loadContent('fetchPerformanceTestResult', $('#formPerformance'), $('#testResultDisplay'), getBasicParams(), '', true);
		//show print as pdf icon
		$('#pdfPopup').show();
    }
    
    function testResultAvailShowList() {
    	$(".noTestAvail").show(); // shows list alone
    	testResultNotAvail();
    }
    
	function testResultNotAvail() {
		// When there is no test result file in any folder hide everything except test btn
   		$('#testResultFile').hide();
   		$('#testResultFileTitle').hide();
   		$("#dropdownDiv").hide();
		$("#graphBasedOn").hide();
		$("#testResultDisplay").empty(); 
		$("#testResultDisplay").hide();
    }
	
	function getDevicesName() {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	params = params.concat("testType=");
	    params = params.concat('<%= FrameworkConstants.PERFORMACE %>');

    	loadContent('getDevices', '', '', params, true, true, false);
	}
	
	function successGetDeviceName(data) {
		if (data != null && !isBlank(data)) {
			$('#testResultDeviceId').empty();
			$.each(data,function(key, value){
				$('#testResultDeviceId').append($("<option></option>").attr("value",key).text(value));
			});
			$('#testResultDeviceId').append($("<option></option>").attr("value","").text("All"));
			var testResultsType = $("#testResultsType").val();
			performanceTestResults(testResultsType);
		} else {
			// When there is no result
       		$('#performanceError').empty();
       		$('#performanceError').html("Performance test not yet executed for " + testResultsType);
       		testResultAvailShowList();
		}
	}
	
	function disableType() {	// for android technology disable type list box
		$('#lblType').css("display","none");
		$('#testResultsType').css("display","none");
	}
	
	//successEvent actions
	function successEvent(pageUrl, data) {
		if (pageUrl == "performanceTestResultAvail") {
			if (!data.resultFileAvailable) {
	       		$("#noFiles").show();
	       		testResultNotAvail();
	       	 	hideLoadingIcon();
			} else {
	       		testResultAvailShowList();
	       		performanceTestResultsFiles();
	       	}
		} else if (pageUrl == "fetchPerformanceTestResultFiles") {
			successPerfTestResultsFiles(data);
		} else if (pageUrl == "getDevices") {
			successGetDeviceName(data);
		} else if (pageUrl == "checkForConfigType") {
			successEnvValidation(data);
		} else if (pageUrl == "getPerfTestJSONData") {
			fillJSONData(data);
		}
	}
	
	//Handles the ok button click event in the popup
	function popupOnOk(obj) {
		var okUrl = $(obj).attr("id");
		if (okUrl === "printAsPdf") {
			printPdfPreActions();
			loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), '', false, true);
		} else if (okUrl === "runPerformanceTest") {
			mandatoryValidation(okUrl, $("#generateBuildForm"), '', 'performance-test', 'performance-test');
		}
	}	 
	
	function popupOnClose(obj) {
		isResultFileAvailbale();
	}
</script>