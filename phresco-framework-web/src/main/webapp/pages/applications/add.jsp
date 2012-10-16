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

<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.Technology"%>

<%
	List<Technology> technologies = (List<Technology>) request.getAttribute(FrameworkConstants.REQ_ALL_TECHNOLOGIES);
%>

<div class="page-header">
	<h1>
		<s:text name="lbl.projects.add"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="label.mandatory"/></small>
	</h1>
</div>

<div class="appInfoScrollDiv">
	<form id="formCreateProject" autocomplete="off" class="form-horizontal app_create_project" autofocus="autofocus">
	<div class="content_adder">
		<!-- Name Starts -->
		<div class="control-group" id="projectNameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
			</label>
			<div class="controls">
				<input placeholder="<s:text name="place.hldr.proj.add.name"/>" class="input-xlarge" type="text" 
					name="projectName" maxlength="30" title="<s:text name="title.30.chars"/>">
				<span class="help-inline" id="projectNameError"></span>
			</div>
		</div>
		<!-- Name Ends -->
		
		<!-- Code Starts -->
		<div class="control-group" id="projectCodeControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.code' />
			</label>
			<div class="controls">
				<input placeholder='<s:text name="place.hldr.proj.add.code"/>' class="input-xlarge" type="text" 
					name="projectCode" maxlength="12" title="<s:text name="title.12.chars"/>">
				<span class="help-inline" id="projectCodeError"></span>
			</div>
		</div>
		<!-- Code Ends -->
		
		<!-- Description Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.description' />
			</label>
			<div class="controls">
				<textarea id="projDesc" class="input-xlarge" placeholder='<s:text name="place.hldr.proj.add.desc"/>' 
					rows="3" name="projectDesc" maxlength="150" title="<s:text name="title.150.chars"/>"></textarea>
			</div>
		</div>
		<!-- Description Ends -->
		
		<!-- Version Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.project.version' />
			</label>
			<div class="controls">
				<input id="projVersion" class="input-xlarge" type="text" name="projectVersion" 
					placeholder='<s:text name="place.hldr.proj.add.version"/>' maxlength="20" title="<s:text name="title.20.chars"/>">
			</div>
		</div>
		<!-- Version Starts -->
		
		<!-- PreBuild/Build it myself Starts -->
		<div class="control-group">
			<label class="control-label labelbold"></label>
			<div class="controls">
				 <ul class="inputs-list">
					<li> 
						<input type="radio" name="buildType" value="buildItMyself" checked="checked"/> 
						<span class="vAlignMiddle buildTypeSpan"><s:text name="lbl.projects.build.type.myself"/></span>
						<input type="radio" name="buildType" value="preBuild" disabled/> 
						<span class="vAlignMiddle"><s:text name="lbl.projects.build.type.prebuilt"/></span>
					</li>
				</ul>
			</div>
		</div>
		<!-- PreBuild/Build it myself ends -->
	
		<!-- Application layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox"/>
								<a class="vAlignSub"><s:text name='lbl.projects.layer.app'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="control-group">
										<label class="accordion-control-label labelbold"><s:text name='label.technology'/></label>
										<div class="controls">
											<select class="input-xlarge" name="technology">
												<% for (Technology technology : technologies) { %>
													<option value='<%= technology.getId() %>'> <%= technology.getName() %> </option>
												<% } %>
											</select>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Application layer accordion ends -->
		
		<!-- Web layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox"/>
								<a class="vAlignSub"><s:text name='lbl.projects.layer.web'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="align-div-center">
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projects.layer.web'/>
											</label>
										
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="html5"> HTML5 </option>
											</select>
										</div>
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projcts.add.widget'/>
											</label>
										
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="yui"> YUI</option>
												<option value="jquery"> Jquery </option>
											</select>
										</div>
										<div class="float-left">
											<label class="control-label autoWidth">
												<s:text name='lbl.version'/>
											</label>
										
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="1.0"> 1.0 </option>
												<option value="1.1"> 1.2 </option>
											</select>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Web layer accordion ends -->
		
		<!-- Mobile layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox"/>
								<a class="vAlignSub"><s:text name='lbl.projects.layer.mobile'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="align-div-center bottom-space">
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;iOS </span>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --version-- </option>
												<option value="1.0"> 4.2 </option>
												<option value="1.0"> 5.1 </option>
												<option value="1.0"> 6.0 </option>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="Hybrid"> Hybrid </option>
												<option value="Native"> Native </option>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;iPhone</span>
										</div>
										<div class="float-left">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;iPad</span>
										</div>
									</div>
									<div class="clear"></div>
									
									<div class="align-div-center bottom-space">
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Android </span>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --version-- </option>
												<option value="1.0"> 2.3 </option>
												<option value="1.0"> 3.0 </option>
												<option value="1.0"> 4.0 </option>
												<option value="1.0"> 4.1 </option>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="Hybrid"> Hybrid </option>
												<option value="Native"> Native </option>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Phone</span>
										</div>
										<div class="float-left">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Tablet</span>
										</div>
									</div>
									<div class="clear"></div>
									
									<div class="align-div-center bottom-space">
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Windows </span>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --version-- </option>
												<option value="1.0"> 7.0 </option>
												<option value="1.0"> 8.0 </option>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="Hybrid"> Hybrid </option>
												<option value="Native"> Native </option>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Phone</span>
										</div>
										<div class="float-left">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Tablet</span>
										</div>
									</div>
									<div class="clear"></div>
									
									<div class="align-div-center">
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;BlackBerry </span>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --version-- </option>
												<option value="1.0"> 5.0 </option>
												<option value="1.0"> 6.1 </option>
												<option value="1.0"> 7.1 </option>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium">
												<option value=""> --select-- </option>
												<option value="Hybrid"> Hybrid </option>
												<option value="Native"> Native </option>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Phone</span>
										</div>
										<div class="float-left">
											<input type="checkbox"/>
											<span class="vAlignSub">&nbsp;Tablet</span>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Mobile layer accordion ends -->
	</div>	
	</form>
	<!--  Form Ends -->
</div>

<!--  Submit and Cancel buttons Starts -->
<div class="actions">
	<input type="button" id="createProject" value="<s:text name="lbl.create"/>" class="btn btn-primary">
	<input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="btn btn-primary">
</div>
<!--  Submit and Cancel buttons Ends -->

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()){
		$(".appInfoScrollDiv").scrollbars();
	}

	$(document).ready(function() {
		enableScreen();//To enable the page after the full page is rendered
		
		//Will be triggered when the create project button is clicked
		$('#createProject').click(function() {
			var params = $('#formCustomers').serialize();
			validate('createProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.add.proj'/>');
		});
	});
	
	function findError(data) {
		if (!isBlank(data.projectNameError)) {
			showError($("#projectNameControl"), $("#projectNameError"), data.projectNameError);
		} else {
			hideError($("#projectNameControl"), $("#projectNameError"));
		}
		
		if (!isBlank(data.projectCodeError)) {
			showError($("#projectCodeControl"), $("#projectCodeError"), data.projectCodeError);
		} else {
			hideError($("#projectCodeControl"), $("#projectCodeError"));
		}
	}
</script>