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


<form id="formTestSuite">
	<div class="manualScrollDiv" style="margin-top:5%;">
		<!-- Test-Scenario Name starts -->
		<div class="control-group" id="nameControl">
		    <label class="accordion-control-label labelbold"><span class="red">*</span>&nbsp;<s:text name="label.testsuite.name"/></label>
		    <div class="controls">
		       <input id="testSuiteName" placeholder="<s:text name='label.test.name.placeholder'/>" class="input-xlarge" name="testScenarioName" type="text" 
				    value="" maxlength="30" title="30 Characters only">
					<span class="help-inline" id="nameError"></span>
		    </div>
		</div>
		<!-- Test-Scenario Name ends -->
		
		<%-- <!-- Total success starts -->
		<div class="control-group" id="successControl">
			 <label class="accordion-control-label labelbold"><s:text name="label.testsuite.success"/></label>
			<div class="controls">
				<input id="totalSuccess" class="input-xlarge" type="text" value="" 
					name="totalSuccess" placeholder='<s:text name="label.succes.placeholder"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="totalSuccessError"></span>
			</div>
		</div>
		<!-- TotalSuccess ends -->
		
		<!-- Total failures starts -->
		<div class="control-group" id="failureControl">
			 <label class="accordion-control-label labelbold"><s:text name="label.testsuite.failure"/></label>
			<div class="controls">
				<input id="totalFailures" class="input-xlarge" type="text" value="" 
					name="totalFailures" placeholder='<s:text name="label.failure.placeholder"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="totalFailuresError"></span>
			</div>
		</div>
		<!-- Total failures ends  -->
		
		<!-- Total TestCases starts -->
		<div class="control-group" id="totalControl">
			 <label class="accordion-control-label labelbold"><s:text name="label.testsuite.total"/></label>
			<div class="controls">
				<input id="totalTestCases" class="input-xlarge" type="text" value="" 
					name="totalTestCases" placeholder='<s:text name="label.total.placeholder"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="totalTestCasesError"></span>
			</div>
		</div>
		<!--  Total TestCases ends -->
		
		<!-- Test Coverage starts -->
		<div class="control-group" id="TestCoverageControl">
			 <label class="accordion-control-label labelbold"><s:text name="label.testsuite.testCoverage"/></label>
			<div class="controls">
				<input id="testCoverage" class="input-xlarge" type="text" value="" 
					name="testCoverage" placeholder='<s:text name="label.testCoverage.placeholder"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="testCoverageError"></span>
			</div>
		</div> --%>
		<!--  Total Coverage ends -->
		<div class="actions">
			<input type="button" id="createTestSuite" value="<s:text name="lbl.save"/>" class="btn btn-primary">
			<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary"/>
		</div>
	</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
	//$("#popupPage").modal('hide');
	if(!isiPad()){
		/* JQuery scroll bar */
		$("#formTestSuite").scrollbars();
	}
	hideLoadingIcon();
	
	$('#cancel').click(function() {
		var param = getBasicParams();
		loadContent('manual', $('#formTestSuite'), $('#subcontainer'), param);
	});
	
	$('#createTestSuite').click(function() {
		//showLoadingIcon();
		var param = getBasicParams();
		//loadContent('saveTestSuites', $('#formTestSuite'), $('#subcontainer'), param);
		validate('saveTestSuites', $('#formTestSuite'), $("#subcontainer"), param, 'Creating TestSuite');
	});
});	

	function findError(data) {
		hideLoadingIcon();
		if (!isBlank(data.nameError)) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
	}
	
</script> 