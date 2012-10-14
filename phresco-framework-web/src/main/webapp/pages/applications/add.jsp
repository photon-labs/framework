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
	String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
%>

<div class="page-header">
	<h1>
		<s:text name="lbl.projects.add"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="label.mandatory"/></small>
	</h1>
</div>
<div class="createProjDiv">
	<form id="formCreateProject" autocomplete="off" class="form-horizontal app_create_project" autofocus="autofocus">
	<div class="projOperation" id="projOperation">
	</div>
	<div class="content_adder">
		<!--  Name Starts -->
		<div class="control-group" id="nameControl">
			<label class="control-label labelbold"> <span
				class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
			</label>
			<div class="controls">
				<input id="projName" placeholder='<s:text name="label.name.placeholder"/>' class="input-xlarge" type="text" 
					name="projectName" value="" maxlength="30" title="30 Characters only">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		<!--  Name Ends -->
		
		<!--  Code Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.code' />
			</label>
			<div class="controls">
				<input id="projCode" placeholder='<s:text name="label.code.placeholder"/>' class="input-xlarge" type="text" 
					name="projectCode" value="" maxlength="12" title="12 Characters only">
			</div>
		</div>
		<!--  Code Ends -->
		
		<!--  Description Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.description' />
			</label>
			<div class="controls">
				<textarea id="projDesc" class="input-xlarge" placeholder='<s:text name="label.description.placeholder"/>' 
					rows="3" name="projectDesc" maxlength="150" title="150 Characters only"></textarea>
			</div>
		</div>
		<!--  Description Ends -->
		
		<!--  Version Starts -->
		<div class="control-group">
			<label class="control-label labelbold"><s:text name='label.project.version' />
			</label>
			<div class="controls">
				<input id="projVersion" placeholder='' class="input-xlarge" type="text" 
					name="projectVersion" value="" maxlength="20" title="20 Characters only">
			</div>
		</div>
		<!--  Version Starts -->
		
		<!--  PreBuild/Build it myself End -->
		<div class="control-group">
			<label class="control-label labelbold">
			</label>
			<div class="controls">
				<ul class="inputs-list">
						<li> 
							<input type="radio" name="radio" id="buidItMyself" value="" checked="checked"/> 
							<span class="textarea_span">Build it Myself</span>
							<input type="radio" name="radio" id="preBuild" value="" style="margin-left:20px" /> 
							<span class="textarea_span">Pre Build</span>
						</li>
					</ul>
				<!-- <input id="projVersion" placeholder='' class="input-xlarge" type="text" 
					name="version" value="" maxlength="20" title="20 Characters only"> -->
			</div>
		</div>
		<!--  PreBuild/Build it myself Starts -->
	
		<div class="theme_accordion_container" id="coremodule_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" name="layers" value="data" class="accordianChkBox" id="checkAll1" onclick=""/>
								<a>Data Access Layer</a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt" style="display: none;">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
								<div class="control-group">
									<div class="webServicesDiv" id="webServicesDiv">
											<label class="control-label labelbold"><input type="checkbox" id="webCheckId"/> 
										<span style="color:#fff;">Web Service</span>
											</label>
											<div class="multilist-scroller multiselect">
												<ul>
													<li>
														<input type="checkbox" id="webservices" name="webService" value=""
															class="webcheck" >REST/JSON 1.0 
													</li>
													<li>
														<input type="checkbox" id="webservices" name="webService" value=""
															class="webcheck" >REST/XML 1.0 
													</li>
													<li>
														<input type="checkbox" id="webservices" name="webService" value=""
															class="webcheck">SOAP 1.1
													</li>
													<li>
														<input type="checkbox" id="webservices" name="webService" value=""
															class="webcheck">SOAP 1.2
													</li>
		
												</ul>
											</div>
										</div>
									</div>
									<div class="control-group">
										<div id="accesLayerContainer">
											<div class="access_layer" id="1_accessLayer">
												<div class="controls" style="margin-top:17px;">
													<div class="dbDiv" id="1_dbDiv" >
														<input type="checkbox" id="dbCheckId" class="dbcheckbox"> 
													<span class="dbSpan" >Data Base</span>
														<select class="accesdbSelect" name="database" id="1_accessDb" style="width:150px; float:left">
															<option value="MSSQL"> MSSQL </option>
															<option value="Oracle"> Oracle </option>
															<option value="MongoDB"> MongoDB </option>
															<option value="DB2"> DB2 </option>
														</select>
													</div>
													<div class="dbVersion" id="1_dbVersion" style="height:86px; width:30%; float: left;">
														<label class="control-label" for="input01" style="float: left; width: auto; margin: 0px 16px;">Version</label>
														<div class="accessVersionDiv" id="accessVersionDiv" style="width:295px; height:95px;">
															<div class="multilist-scroller multiselect" class="checkDb" style="height:65px; width:77%; border:1px solid #CCCCCC">
																<ul>
																	<li>
																		<input type="checkbox" id="accesDbVersion" name="dbVersion" value="5.5.1"
																			class="checkdbversion">5.5.1
																	</li>
																	<li>
																		<input type="checkbox" id="accesDbVersion" name="dbVersion" value="5.5"
																			class="checkdbversion">5.5
																	</li>
																	<li>
																		<input type="checkbox" id="accesDbVersion" name="dbVersion" value="5.1"
																			class="checkdbversion">5.1
																	</li>
																</ul>
															</div>
														</div>
													</div>
													<span><img class="add imagealign"  src="../../images/add_icon.png" temp="1" style="float: right; margin-right: 65px;" onclick="addAccessDb(this);"></span>
												</div>
											</div>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" name="layers" value="application" id="checkAll1" class="accordianChkBox"/>
								<a>Application Layer</a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt" style="display: none;">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="techLabel">
										<label class="control-label" style="margin-left:-22px;" for="input01">Technology</label>
									</div>
									<div class="controls">
										<select class="xlarge" name="technology" id="webLayer" style="float:left; margin-left:-22px;" onchange="getServers();">
											<% for (Technology technology : technologies) { %>
												<option value='<%= technology.getId() %>'> <%= technology.getName() %> </option>
											<% } %>
										</select>
									</div>
									<div class="control-group" style="margin-top:17px ;">
										<div class="appServDiv" id="appServDiv" >
											<label class="control-label" for="input01" style="float: left; width: auto; margin: 0px 21px;">Supported server</label>
											<select class="xlarge" name="server" id="appServer" style="width:150px; float:left">
											<!-- 	<option value="Apache Tomcat">   Apache Tomcat   </option>
												<option value="JBoss">  JBoss  </option>
												<option value="IIS">  IIS  </option>
												<option value="WebLogic">  WebLogic  </option>
												<option value="NodeJS">  NodeJS  </option>
												<option value="Apache">  Apache  </option> -->
											</select>
										</div>
										<div class="servVersion" id="servVersion" >
											<label class="control-label" for="input01" style="float: left; width: auto; margin: 0px 16px;">Version</label>
											<div class="multilist-scroller multiselect" class="checkservr" style="height:65px; width:33.6%; border:1px solid #CCCCCC">
												<ul>
													<li>
														<input type="checkbox" id="appServVersion" name="serverVersion" value="5.5.1"
															class="check">5.5.1
													</li>
													<li>
														<input type="checkbox" id="appServVersion" name="serverVersion" value="5.5"
															class="check">5.5
													</li>
													<li>
														<input type="checkbox" id="appServVersion" name="serverVersion" value="5.1"
															class="check">5.1
													</li>
													
												</ul>
											</div>
										</div>	
									</div>
									<span><img class="add imagealign"  src="../../images/add_icon.png" style="float:right; margin-top:-72px; margin-right: 52px; " onclick="addAppTech(this);"></span>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" name="layers" value="web" id="checkAll1" class="accordianChkBox"/>
								<a>Web Layer</a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div id="webLayerContainer">
										<div class="control-group" id="1_webLayer">
											<div class="control-group">
												<label class="control-label" style="margin-left:-22px;"  for="input01">Web Layer</label>
												<div class="controls">
													<select class="xlarge" name="webLayer" id="webLayer" style="float:left; margin-left:-22px;" onchange="showWidgetVersion();">
														<option value=""> --select-- </option>
														<option value="html5"> HTML5 </option>
													</select>
												</div>
											</div>
											<div class="control-group" style="margin-top:17px ;">
												<div class="webDiv" id="webDiv" >
													<label class="control-label" for="input01" style="float: left; width: auto; margin-left: 76px;">Widget</label>
													<select class="xlarge" name="widget" id="webWidget" style="width:150px; float:left; margin-left: 21px;">
														<option value=""> --select-- </option>
														<option value="yui"> YUI</option>
														<option value="jquery"> Jquery </option>
													</select>
												</div>
												<div class="wdgtVersionDiv" id="wdgtVersionDiv" >
													<label class="control-label" for="input01" style="float: left; width: auto; margin: 0px 16px;">Version</label>
													<select class="xlarge" name="widgetVersion" id="webVersion" style="width:150px;">
														<option value=""> --select-- </option>
														<option value="1.0"> 1.0 </option>
														<option value="1.1"> 1.2 </option>
													</select>
												</div>	
											</div>
										</div>
									</div>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg">
							<span>
								<input type="checkbox" name="layers" value="mobile" id="checkAll1" class="accordianChkBox"/>
								<a>Mobile Layer</a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<ul style="list-style:none; color:black;">
										<li style="color:black">
											<div class="control-group" style="margin-left: 44px;">
												<div class="iosCheck" style="float: left; width: 90px;">
													<input type="checkbox" value="ios" id="iOS" name="mobileLayer" style=""/>
													<span style="color:#fff;">&nbsp;iOS </span>
												</div>
												<div>
													<select class="xlarge" name="" id="iosVersion" style="width:100px;">
														<option value=""> --version-- </option>
														<option value="1.0"> 4.2 </option>
														<option value="1.0"> 5.1 </option>
														<option value="1.0"> 6.0 </option>
													</select>
													<select class="xlarge" name="iosType" id="iosList" style="width:100px; margin-left:12px;">
														<option value=""> --select-- </option>
														<option value="Hybrid"> Hybrid </option>
														<option value="Native"> Native </option>
													</select>
													<input type="checkbox" value="iPhone" id="iosIphone" style="margin-left:5px;"/>
														<span style="color:#fff;">&nbsp;iPhone<span>																
													<input type="checkbox" value="iPad" id="iosIpad" style="margin-left:5px;"/>
													<span style="color:#fff;">&nbsp;iPad<span>																	
												</div>
											</div>
										</li>	
										<li style="color:black">
											<div class="control-group" style="margin-left: 44px;">
												<div class="androidCheck" style="float: left; width: 90px;">
													<input type="checkbox" name="mobileLayer" value="Android" id="android" style=""/><span style="color:#fff;">&nbsp; Android</span>
												</div>	
												<div>	
													<select class="xlarge" name="" id="androidVersion" style="width:100px;">
														<option value=""> --version-- </option>
														<option value="1.0"> 2.3 </option>
														<option value="1.0"> 3.0 </option>
														<option value="1.0"> 4.0 </option>
														<option value="1.0"> 4.1 </option>
													</select>
													<select class="xlarge" name="" id="androidList" style="width:100px; margin-left:12px;">
														<option value=""> --select-- </option>
														<option value="Hybrid"> Hybrid </option>
														<option value="Native"> Native </option>
													</select>
													<input type="checkbox" value="Phone" id="androidPhone" style="margin-left:5px;"/> 
													<span style="color:#fff;">&nbsp;Phone <span>	
													<input type="checkbox" value="Tablet" id="androidTablet" style="margin-left:7px;"/> <span style="color:#fff;">&nbsp;Tablet <span>
												</div>	
											</div> 
										</li>
										<li style="color:black">
											<div class="control-group" style="margin-left: 44px;">
												<div class="windowsCheck" style="float: left; width: 90px;">
													<input type="checkbox" value="Windows" id="windows" style=""/><span style="color:#fff;">&nbsp; Windows</span>
												</div>	
												<div>	
													<select class="xlarge" name="" id="windowsVersion" style="width:100px;">
														<option value=""> --version-- </option>
														<option value="1.0"> 7.0 </option>
														<option value="1.0"> 8.0 </option>
													</select>
													<select class="xlarge" name="" id="windowsList" style="width:100px; margin-left:12px;">
														<option value=""> --select-- </option>
														<option value="Hybrid"> Hybrid </option>
														<option value="Native"> Native </option>
													</select>
													<input type="checkbox" value="iPhone" id="windowsiPhone" style="margin-left:5px;"/>
													<span style="color:#fff;">&nbsp;Phone</span>
													<input type="checkbox" value="Tablet" id="windowsTablet" style="margin-left:5px;"/>
													<span style="color:#fff;">&nbsp;Tablet </span>
												</div>	
											</div>
										</li>
										<li style="color:black">
											<div class="control-group" style="margin-left: 44px;">
												<div class="iosCheck" style="float: left; width: 90px;">
													<input type="checkbox" value="iOS" id="iOS" style=""/> 
													<span style="color:#fff;">&nbsp;BlackBerry</span>
												</div>
												<div>
													<select class="xlarge" name="" id="iosVersion" style="width:100px;">
														<option value=""> --version-- </option>
														<option value="1.0"> 5.0 </option>
														<option value="1.0"> 6.1 </option>
														<option value="1.0"> 7.1 </option>
													</select>
													<select class="xlarge" name="" id="iosList" style="width:100px; margin-left:12px;">
														<option value=""> --select-- </option>
														<option value="Hybrid"> Hybrid </option>
														<option value="Native"> Native </option>
													</select>
													<input type="checkbox" value="iPhone" id="iosIphone" style="margin-left:5px;"/> 
													<span style="color:#fff;">&nbsp;Phone</span>
												</div>
											</div>
										</li>																	
									</ul>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		</div>	
		
		<!--  Submit and Cancel buttons Starts -->
	    <div class="actions">
	        <input type="button" id="createProject" value="Create" class="btn btn-primary">
	        <input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="btn btn-primary">
	    </div>
	    <!--  Submit and Cancel buttons Ends -->
	</form> 
</div>
<!--  Form Ends -->

<script type="text/javascript">
	$(document).ready(function() {
		$('.wel_come').css('display','none');
		
		getServers();//To the servers on document ready
		
		//Will be triggered when the create project button is clicked
		$('#createProject').click(function() {
			var params = $('#formCustomers').serialize();
			loadContent('createProject', $('#formCreateProject'), $("#container"), params);
		});
	});
	
	//To get the servers
	function getServers() {
		loadContent('getServers', $('#formCreateProject'), '', '', true);
	}
	
	//Success event operations
	function successEvent(pageURL, data) {
		//To show the list of servers in the server select box
		if (pageURL == "getServers" && data != undefined && data.servers.length > 0) {
			$('#appServer').find('option').remove();
			for (var i = 0; i < data.servers.length; i++) {
				$("#appServer").append($('<option></option>')
						.attr("value", data.servers[i].id).text(data.servers[i].name));				
			}
		}
	}
</script>