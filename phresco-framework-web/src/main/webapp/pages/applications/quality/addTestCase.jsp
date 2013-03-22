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
<%@page import="com.photon.phresco.commons.model.TestCase"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%
	String testSuiteName = (String) request.getAttribute(FrameworkConstants.REQ_TESTSUITE);
	TestCase testCase = (TestCase) request.getAttribute(FrameworkConstants.REQ_TESTCASE);
	String featureId = "";
	String testCaseId = "";
	String testDesc = "";
	String testSteps = "";
	String expectedResult = "";
	String actualResult = "";
	String status = "";
	String strDisable = "";
	if(testCase != null) {
		featureId = testCase.getFeatureId();
		testCaseId = testCase.getTestCaseId();
		testDesc = testCase.getDescription();
		testSteps = testCase.getSteps();
		expectedResult = testCase.getExpectedResult();
		actualResult = testCase.getActualResult();
		status = testCase.getStatus();
		strDisable = "disabled";
	}
%>

<form id="formTestCase">
	<div class="manualScrollDiv" style="margin-top:4%;">
	
		<!-- TestSuiteName start -->
		<div class="control-group" id="nameControl">
		    <label class="accordion-control-label labelbold"><s:text name="label.testsuite.name"/></label>
		    <div class="controls">
		       <input id="testSuiteName" placeholder="<s:text name='label.name.placeholder'/>" class="input-xlarge" name="testScenarioName" type="text" 
				    maxlength="30" title="30 Characters only" value="<%= StringUtils.isNotEmpty(testSuiteName) ? testSuiteName : "" %>" disabled >
					<span class="help-inline" id="nameError"></span>
		    </div>
		</div>
		<!-- TestSuite Name ends -->
		
	    <!-- FeatureId starts -->
		<div class="control-group" id="featureIdControl">
		    <label class="accordion-control-label labelbold"><s:text name="label.testcase.featureId"/></label>
		    <div class="controls">
		       <input id="featureId" placeholder="<s:text name='label.featureId.placeholder'/>" class="input-xlarge" name="featureId" type="text" 
				    value="<%= StringUtils.isNotEmpty(featureId) ? featureId : "" %>" <%= strDisable %>>
					<span class="help-inline" id="featureIdError"></span>
		    </div>
		</div>
		<!-- FeatureId ends -->
		
		<!-- TestCaseId start -->
		<div class="control-group" id="testCaseIdControl">
			 <label class="accordion-control-label labelbold"><s:text name="label.testcase.Id"/></label>
			<div class="controls">
				<input id="testCaseId" class="input-xlarge" type="text" value="<%= StringUtils.isNotEmpty(testCaseId) ? testCaseId : "" %>" <%= strDisable %>
					name="testCaseId" placeholder='<s:text name="label.testCaseId.placeholder"/>'>
				<span class="help-inline" id="testCaseIdError"></span>
			</div>
		</div>
		<!-- TestCaseId ends -->
		
		<!-- TestCase Description start -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='label.testcase.desc'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" class="xlarge" 
		        	id="testDescription" placeholder="<s:text name="label.testCase.desc.placeholder"/>"
		        	name="testDescription" <%= strDisable %>><%= StringUtils.isNotEmpty(testDesc) ? testDesc : "" %></textarea>
		    </div>
		</div>
		<!-- TestCase Description ends -->
		
		<!-- TestCase Steps start -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='label.testcase.steps'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" class="xlarge" 
		        	id="testSteps" placeholder="<s:text name="label.testcase.steps.placeholder"/>"
		        	name="testSteps" <%= strDisable %>><%= StringUtils.isNotEmpty(testSteps) ? testSteps : "" %></textarea>
		    </div>
		</div>
		<!-- TestCase Steps ends -->
		
		<!-- Expected Result start -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='label.testcase.expected.result'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" class="xlarge" 
		        	id="expectedResult" placeholder="<s:text name="label.expected.placeholder"/>"
		        	name="expectedResult" <%= strDisable %>><%= StringUtils.isNotEmpty(expectedResult) ? expectedResult : "" %></textarea>
		    </div>
		</div>
		<!-- Expected Result ends -->
		
		<!-- Actual Result start -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='label.testcase.Actual.result'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" class="xlarge" 
		        	id="actualResult" placeholder="<s:text name="label.actual.placeholder"/>"
		        	name="actualResult" <%= strDisable %>><%= StringUtils.isNotEmpty(actualResult) ? actualResult : "" %></textarea>
		    </div>
		</div>
		<!-- Actual Result ends -->
		
		<!-- Status start -->
		<div class="control-group" id="status">
			 <label class="accordion-control-label labelbold"><s:text name="label.testcase.status"/></label>
			<div class="controls">
				<input id="status" class="input-xlarge" type="text" value="<%= StringUtils.isNotEmpty(status) ? status : "" %>" <%= strDisable %>
					name="status" placeholder='<s:text name="label.testcase.status.placeholder"/>'>
			</div>
		</div>
		<!-- Status ends -->
		
		<!-- BugComment start -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='label.testcase.comment'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" maxlength="150" title="<s:text name="title.150.chars"/>" class="xlarge" 
		        	id="bugComment" placeholder="<s:text name="label.testcase.comment.placeholder"/>"
		        	name="bugComment"></textarea>
		    </div>
		</div>
		<!-- BugComment ends -->
		<input type="hidden" name="testScenarioName" value="<%= testSuiteName %>"/>
		<% if(testCase != null) { %>
			<input type="hidden" name="featureId" value="<%= featureId %>"/>
			<input type="hidden" name="testCaseId" value="<%= testCaseId %>"/>
			<input type="hidden" name="testDescription" value="<%= testDesc %>"/>
			<input type="hidden" name="testSteps" value="<%= testSteps %>"/>
			<input type="hidden" name="expectedResult" value="<%= expectedResult %>"/>
			<input type="hidden" name="actualResult" value="<%= actualResult %>"/>
			<input type="hidden" name="status" value="<%= status %>"/>
			<input type="hidden" name="fromTab" value="edit"/>
		<% } %>
		
		<div class="actions">
			<input type="button" id="createTestCase" value="<s:text name="lbl.save"/>" class="btn btn-primary">
			<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary"/>
		</div>
	</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
	//$("#popupPage").modal('hide');
	hideLoadingIcon();
	$('#cancel').click(function() {
		var param = getBasicParams();
		loadContent('manual', $('#formTestCase'), $('#subcontainer'), param);
	});
	
	$('#createTestCase').click(function() {
		showLoadingIcon();
		var param = getBasicParams();
		loadContent('saveTestCases', $('#formTestCase'), $('#subcontainer'), param);
		//validate('features', $('#formAppInfo'), $('#subcontainer'), params);
	});
	
});	
	
</script> 