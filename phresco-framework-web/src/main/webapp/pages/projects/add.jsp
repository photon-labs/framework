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
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationType"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyGroup"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyInfo"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>

<%
	ProjectInfo projectInfo = (ProjectInfo)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	List<ApplicationType> layers = (List<ApplicationType>) request.getAttribute(FrameworkConstants.REQ_PROJECT_LAYERS);
	List<TechnologyGroup> appLayerTechGroups = null;
	List<TechnologyGroup> webLayerTechGroups = null;
	List<TechnologyGroup> mobileLayerTechGroups = null;
	String appLayerId = "";
	String webLayerId = "";
	String mobileLayerId = "";
	String appLayerName = "";
	String webLayerName = "";
	String mobileLayerName = "";
	if (CollectionUtils.isNotEmpty(layers)) {
		for (ApplicationType layer : layers) {
		    if (FrameworkConstants.LAYER_APP_ID.equals(layer.getId())) {
		        appLayerId = layer.getId();
		        appLayerName = layer.getName();
		        appLayerTechGroups = layer.getTechGroups();
		    }
			if (FrameworkConstants.LAYER_WEB_ID.equals(layer.getId())) {
			    webLayerId = layer.getId();
			    webLayerName = layer.getName();
			    webLayerTechGroups = layer.getTechGroups();
		    }
			if (FrameworkConstants.LAYER_MOB_ID.equals(layer.getId())) {
			    mobileLayerId = layer.getId();
			    mobileLayerName = layer.getName();
			    mobileLayerTechGroups = layer.getTechGroups();
		    }
		}
	}
	String id = "";
	String name = "";
	String projectCode = "";
	String description = "";
	String version = "";
	List<ApplicationInfo> appInfos = null;
	String disablStr = "";
	if(projectInfo != null){
		disablStr = "disabled";
		id = projectInfo.getId();
		name = projectInfo.getName();
		projectCode = projectInfo.getProjectCode();
		description = projectInfo.getDescription();
		version = projectInfo.getVersion();
		appInfos = projectInfo.getAppInfos();
	}
%>

<div class="page-header">
	<h1>
		<s:text name="lbl.projects.add"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="lbl.mandatory.text"/></small>
	</h1>
</div>

<div class="projectScrollDiv ">
	<form id="formCreateProject" autocomplete="off" class="form-horizontal app_create_project" autofocus="autofocus">
	<div class="content_adder">
		<!-- Name Starts -->
		<div class="control-group" id="projectNameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
			</label>
			<div class="controls">
				<input placeholder="<s:text name="place.hldr.proj.add.name"/>" class="input-xlarge" type="text" 
					value="<%= StringUtils.isNotEmpty(name) ? name : "" %>"<%= disablStr %> name="projectName" maxlength="30" 
					title="<s:text name="title.30.chars"/>">
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
					value="<%= StringUtils.isNotEmpty(projectCode) ? projectCode : "" %>"<%= disablStr %>  name="projectCode" maxlength="12" 
					title="<s:text name="title.12.chars"/>">
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
					rows="3" value="<%= StringUtils.isNotEmpty(description) ? description : "" %>" name="projectDesc" maxlength="150" 
					title="<s:text name="title.150.chars"/>"></textarea>
			</div>
		</div>
		<!-- Description Ends -->
		
		<!-- Version Starts -->
		<div class="control-group" id="projectVersionControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.version' />
			</label>
			<div class="controls">
				<input id="projVersion" class="input-xlarge" type="text" value="<%= StringUtils.isNotEmpty(version) ? version : "" %>"<%= disablStr %> 
					name="projectVersion" placeholder='<s:text name="place.hldr.proj.add.version"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="projectVersionError"></span>
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
		<% 
		//String slctStr = "";
    	String disblStr = "";
    	String chckdStr = "";
		if (projectInfo != null) { 
			
			if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
					for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
					    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
					    if (CollectionUtils.isNotEmpty(techInfos)) {
					        for (TechnologyInfo techInfo : techInfos) {
					        	if(appInfos != null) {
						        	for(ApplicationInfo appInfo: appInfos) {
						        		if(techInfo.getId().equals(appInfo.getTechInfo().getId())){
						        			//slctStr = "selected";
						        			disblStr = "disabled";
						        			chckdStr = "checked";
						        		}
						        	 }
					        	} 
					        }
					    }
					}
				}
		 	} 
		 %>
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container" id="appSec">
						<span class="siteaccordion closereg" id="appLayerControl" onclick="accordionClick(this, $('input[value=<%= appLayerId %>]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= appLayerId %>" <%= chckdStr%> <%= disblStr%>/>
								<a id="appLayerHeading" class="vAlignSub"><%= appLayerName %></a>
								<p id="appLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="control-group">
										<label class="accordion-control-label labelbold"><s:text name='lbl.technology'/></label>
										<div class="controls">
											<select class="input-xlarge" name="<%= appLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY%>" value="" <%= disblStr %> >
												<option value="" selected disabled><s:text name='lbl.default.opt.select.tech'/></option>
												<% 
													if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
														for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
														    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
														    if (CollectionUtils.isNotEmpty(techInfos)) {
														        for (TechnologyInfo techInfo : techInfos) {
														          String slctStr = "";
														        	if(appInfos != null) {
															        	for(ApplicationInfo appInfo: appInfos) {
															        		if(techInfo.getId().equals(appInfo.getTechInfo().getId())){
															        			slctStr = "selected";
															        		}
															        	 }
														        	} 
												%>
																	<option value="<%= techInfo.getId()%>" <%= slctStr%>><%= techInfo.getName() %></option>
												<%
														        }
														    }
														}
													}
												%>
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
		
	<% 
		String disableLayer = "";
		String checkedLayer = "";
		String selectedStr = "";
		String techGroup = "";
		String techName = "";
		String appInfoVersion = "";
		if (projectInfo != null) {
			if (CollectionUtils.isNotEmpty(webLayerTechGroups)) {
			    for (TechnologyGroup webLayerTechGroup : webLayerTechGroups) {
			    	List<TechnologyInfo> webInfos1 = webLayerTechGroup.getTechInfos();	
					if (CollectionUtils.isNotEmpty(webInfos1)) {
					    for (TechnologyInfo webInfo : webInfos1) {
					    	for (ApplicationInfo appInfo : appInfos) {
								if (webInfo.getId().equals(appInfo.getTechInfo().getId())){
									disableLayer = "disabled";
									checkedLayer = "checked";
									selectedStr = "selected";
									techGroup = webLayerTechGroup.getName();
									techName = webInfo.getName();
									appInfoVersion = appInfo.getTechInfo().getVersion();
								}
					    	}
					    }
			    	}
			    }
			}
		}
     %>
				
	
		<!-- Web layer accordion starts -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container" id="webSec">
						<span class="siteaccordion closereg" id="webLayerControl" onclick="accordionClick(this, $('input[value=<%= webLayerId %>]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= webLayerId %>" <%= checkedLayer%> <%= disableLayer %>/>
								<a id="webLayerHeading" class="vAlignSub"><%= webLayerName %></a>
								<p id="webLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
									<div class="align-div-center">
									
									<%	
									//String disableLayer1 = "";
									//String techGroup = "";
									//String techName = "";
									//String appInfoVersion = "";
									/* if (projectInfo != null) {
										if (CollectionUtils.isNotEmpty(webLayerTechGroups)) {
										    for (TechnologyGroup webLayerTechGroup : webLayerTechGroups) {
										    	List<TechnologyInfo> webInfos1 = webLayerTechGroup.getTechInfos();	
												if (CollectionUtils.isNotEmpty(webInfos1)) {
												    for (TechnologyInfo webInfo : webInfos1) {
												    	for (ApplicationInfo appInfo : appInfos) {
															if (webInfo.getId().equals(appInfo.getTechInfo().getId())) { 
																disableLayer1 = "disabled";
																techGroup = webLayerTechGroup.getName();
																techName = webInfo.getName();
																appInfoVersion = appInfo.getTechInfo().getVersion();
															}
												    	}
												    }
										    	}
										    }
										}
									} */  %>
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projects.layer.web'/>
											</label>
										
											<select class="input-medium" name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECH_GROUP %>" value="" <%= disableLayer%>
												onchange="getWebLayerWidgets('<%= webLayerId %>', '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>');">
												<% if (StringUtils.isNotEmpty(techGroup)) { %>
													<option value=""><%= techGroup %></option>
												
												<% } else { %>
												
													<option value="" selected disabled><s:text name='lbl.default.opt.select.weblayer'/></option>
													<%
														if (CollectionUtils.isNotEmpty(webLayerTechGroups)) {
														    for (TechnologyGroup webLayerTechGroup : webLayerTechGroups) {
													%>		
																<option value="<%= webLayerTechGroup.getId() %>"><%= webLayerTechGroup.getName() %></option>
													<%			
														    }
														}
												}
												%>
											</select>
										</div>
										<div class="align-in-row">
											<label class="control-label autoWidth">
												<s:text name='lbl.projcts.add.widget'/>
											</label>
										
											<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" class="input-medium" value="" <%= disableLayer %> onchange="getWidgetVersions(this, '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
												<% if(StringUtils.isNotEmpty(techName)) { %>
												
												<option value="" selected disabled><%= techName %></option>
												<% } else { %>
												<option value="" selected disabled><s:text name='lbl.default.opt.select.widget'/></option>
												<% } %>
											</select>
										</div>
										<div class="float-left">
											<label class="control-label autoWidth">
												<s:text name='lbl.version'/>
											</label>
										
											<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" value="" <%= disableLayer%> class="input-medium">
												<% if(StringUtils.isNotEmpty(appInfoVersion)) { %>
												
												<option value="" selected disabled><%= appInfoVersion %></option>
												<% } else { %>
												<option value="" selected disabled><s:text name='lbl.default.opt.select.version'/></option>
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
		<!-- Web layer accordion ends -->
		
		<!-- Mobile layer accordion starts -->
		<% 
		String checkedWebLayer = "";
		String selectedWebStr = "";
		if (projectInfo != null) {
			if (CollectionUtils.isNotEmpty(mobileLayerTechGroups)) {
			    for (TechnologyGroup mobileLayerTechGroup : mobileLayerTechGroups) {
			    	List<TechnologyInfo> mobInfos1 = mobileLayerTechGroup.getTechInfos();	
					if (CollectionUtils.isNotEmpty(mobInfos1)) {
					    for (TechnologyInfo mobInfo : mobInfos1) {
					    	for (ApplicationInfo appInfo : appInfos) {
								if (mobInfo.getId().equals(appInfo.getTechInfo().getId())){
									checkedWebLayer = "checked";
									selectedWebStr = "selected";
								}
					    	}
					    }
			    	}
			    }
			}
		}
     %>
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container" id="mobSec">
						<span class="siteaccordion closereg" id="mobileLayerControl" onclick="accordionClick(this, $('input[value=<%= mobileLayerId %>]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= mobileLayerId %>" <%= checkedWebLayer%>/>
								<a id="mobileLayerHeading" class="vAlignSub"><%= mobileLayerName %></a>
								<p id="mobileLayerError" class="accordion-error-txt"></p>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt">
							<div class="scrollpanel">
								<section class="scrollpanel_inner">
								<%
									
									if (CollectionUtils.isNotEmpty(mobileLayerTechGroups)) {
									    for (TechnologyGroup mobileLayerTechGroup : mobileLayerTechGroups) {
									    	String techVersion = "";
									    	String checkedMobLayer = "";
											String disabledMobLayer = "";
											String selectedMobLayer = "";
									    	 if (projectInfo !=null) {
										    		List<TechnologyInfo> mobileInfos1 = mobileLayerTechGroup.getTechInfos();	
													if (CollectionUtils.isNotEmpty(mobileInfos1)) {
														    for (TechnologyInfo mobileInfo1 : mobileInfos1) {
														    	for (ApplicationInfo appInfo : appInfos) {
																	if (mobileInfo1.getId().equals(appInfo.getTechInfo().getId())){
																		checkedMobLayer = "checked";
																		disabledMobLayer = "disabled";
																		selectedMobLayer = "selected";
																		techVersion = appInfo.getTechInfo().getVersion();
																	}
													    		}
										   				 }
													}
									    	} 
									    
								%>
									<div class="align-div-center bottom-space" id="<%= mobileLayerTechGroup.getId() %>LayerDiv">
									 
										<div class="align-in-row width">
											<input type="checkbox" name="<%= mobileLayerId %>TechGroup" value="<%= mobileLayerTechGroup.getId() %>" <%= checkedMobLayer%> <%= disabledMobLayer%> id="<%= mobileLayerTechGroup.getId() %>"/>
											<span class="vAlignSub">&nbsp;<%= mobileLayerTechGroup.getName() %>&nbsp;</span>
										</div>
										<div class="align-in-row">
											<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" value="" <%= disabledMobLayer %>
												onchange="getMobileTechVersions('<%= mobileLayerId %>', '<%= mobileLayerTechGroup.getId() %>', '<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
												<option value="" selected disabled><s:text name='lbl.default.opt.select.type'/></option>
												<%
													//String techVersion = "";
													//String checkedMobLayer1 = "";
													//String disabledMobLayer1 = "";
													//String selectedMobLayer1 = "";
													List<TechnologyInfo> mobileInfos = mobileLayerTechGroup.getTechInfos();	
													if (CollectionUtils.isNotEmpty(mobileInfos)) {
													    for (TechnologyInfo mobileInfo : mobileInfos) {

													    	/*  if( projectInfo != null) {
													    		for (ApplicationInfo appInfo : appInfos) {
																	if (mobileInfo.getId().equals(appInfo.getTechInfo().getId())){
																		checkedMobLayer1 = "checked";
																		disabledMobLayer1 = "disabled";
																		selectedMobLayer1 = "selected";
																		techVersion = appInfo.getTechInfo().getVersion();
																	}
													    		}
													    	}  */
													    	
												%>
															<option value="<%= mobileInfo.getId() %>" <%= selectedMobLayer %>><%= mobileInfo.getName() %></option>
												<%
														}
													}
												%>
											</select>
										</div>
										<div class="align-in-row">
											<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" id="<%= mobileLayerTechGroup.getId() %>" value="" <%= disabledMobLayer %>>
												<% if(projectInfo == null){ %>
												<option value="" selected disabled><s:text name='lbl.default.opt.select.version'/></option>
												<% } else { %>
												<option value""><%= techVersion %></option>
												<% } %>
											</select>
										</div>
										<div class="align-in-row width">
											<input type="checkbox" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_PHONE %>" value="true" <%= disabledMobLayer %>/>
											<span class="vAlignSub">&nbsp;<s:text name='lbl.device.type.phone'/></span>
										</div>
										<div class="float-left">
											<input type="checkbox" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TABLET %>" value="true" <%= disabledMobLayer %>/>
											<span class="vAlignSub">&nbsp;<s:text name='lbl.device.type.tablet'/></span>
										</div>
										
									</div>
									<div class="clear"></div>
									<%
									    	}
										}
									%>
								</section>
							</div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- Mobile layer accordion ends -->
	</div>	
		<!-- hidden fields start-->
		<%if(projectInfo != null) {%>
			<input type="hidden" name=id value="<%= id %>"/>
			<input type="hidden" name=projectName value="<%= name %>"/>
			<input type="hidden" name=projectCode value="<%= projectCode %>"/>
			<input type="hidden" name=projectVersion value="<%= version %>"/>
		<% } %>
		
		<!-- hidden fields end -->
	</form>
	<!--  Form Ends -->
</div>

<!--  Submit and Cancel buttons Starts -->
<div class="actions">
	<%if(projectInfo == null) { %>
	<input type="button" id="createProject" value="<s:text name="lbl.btn.create"/>" class="btn btn-primary">
	<% } else { %>
	<input type="button" id="updateProject" value="<s:text name="lbl.btn.update"/>" class="btn btn-primary">
	<% } %>
	<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
		onclick="loadContent('applications', $('#formCustomers'), $('#container'));"> 
</div>
<!--  Submit and Cancel buttons Ends -->

<script type="text/javascript">
	addProjectAccordion();
	//openprojectAccordion();
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()) {
		$(".projectScrollDiv").scrollbars();
	}

	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		
		//Will be triggered when the create project button is clicked
		$('#createProject').click(function() {
			var params = $('#formCustomers').serialize();
			validate('createProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.add.proj'/>');
		});
		
		//Will be triggered when the update project button is clicked
		$('#updateProject').click(function() {
			var params = $('#formCustomers').serialize();
			validate('updateProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.update.proj'/>');
		});
		
		//var cb = $('#appLayerControl').attr('checked')?true:false;
		//var isChecked = $('#appLayerControl:checked').val()?true:false;
		//var isChecked = $('#checkAll1').is(':checked');
		//console.info("isChecked....."+isChecked);
		//if(isChecked == true) {
		//	console.info("checking.........");
		//	accordionOpen('', 'checked');
		//}
		
		<% if(projectInfo != null) { %>
			
			$("input[value='app-layer']:checked").each(
				    function() {
				    	accordionOpen('#appLayerControl', $('input[value=app-layer]'));
				    }
			);
			
			$("input[value='web-layer']:checked").each(
				    function() {
				    	accordionOpen('#webLayerControl', $('input[value=web-layer]'));
				    }
			);
			$("input[value='mob-layer']:checked").each(
				    function() {
				    	accordionOpen('#mobileLayerControl', $('input[value=mob-layer]'));
				    }
			);
		<% } %>
		
	});
	
	function addProjectAccordion() {
	    $('.siteaccordion').removeClass('openreg').addClass('closereg');
	    $('.mfbox').css('display','none');
	}
	
	  function accordionOpen(thisObj, currentChkBoxObj) {
		var _tempIndex = $('.siteaccordion').index(thisObj);
		var isChecked = currentChkBoxObj;
		$(thisObj).removeClass('closereg').addClass('openreg');
		$(thisObj).next('.mfbox').eq(_tempIndex).slideDown(300,function() {});	
    }  
	
	function accordionClick(thisObj, currentChkBoxObj) {
		var _tempIndex = $('.siteaccordion').index(thisObj);
			
		var isChecked = currentChkBoxObj.is(":checked");
		if (isChecked) {
			$(thisObj).removeClass('closereg').addClass('openreg');
			$('.mfbox').eq(_tempIndex).slideDown(300,function() {});			
		} else {
			$(thisObj).removeClass('openreg').addClass('closereg');
			$('.mfbox').eq(_tempIndex).slideUp(300,function() {});
		}
    }
	
	//To show the validation error messages
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
		
		if (!isBlank(data.projectVersionError)) {
			showError($("#projectVersionControl"), $("#projectVersionError"), data.projectVersionError);
		} else {
			hideError($("#projectVersionControl"), $("#projectVersionError"));
		}
		
		if (!isBlank(data.appTechError)) {
			showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), data.appTechError);
		} else {
			hideErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"));
		}
		
		if (!isBlank(data.webTechError)) {
			showErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"), data.webTechError);
		} else {
			hideErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"));
		}
		
		if (!isBlank(data.mobTechError)) {
			showErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"), data.mobTechError);
		} else {
			hideErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"));
		}
	}
	
	var objName; //select box object name
	//To get the versions of the selected mobile technologies
	function getMobileTechVersions(layerId, techGroupId, toBeFilledCtrlName) {
		showLoadingIcon();
		objName = toBeFilledCtrlName;
		var params = getBasicParams();
		params = params.concat("&layerId=");
		params = params.concat(layerId);
		params = params.concat("&techGroupId=");
		params = params.concat(techGroupId);
		loadContent("fetchMobileTechVersions", $('#formCreateProject'), '', params, true);
	}
	
	//To get the widgets of the selected web layer and load in the widget select box 
	function getWebLayerWidgets(layerId, widgetObjName) {
		showLoadingIcon();
		objName = widgetObjName;
		var params = getBasicParams();
		params = params.concat("&layerId=");
		params = params.concat(layerId);
		loadContent("fetchWebLayerWidgets", $('#formCreateProject'), '', params, true);
	}
	
	var map = {};
	//Success event functions
	function successEvent(pageUrl, data) {
		hideLoadingIcon();
		//To fill the versions for the selected mobile technology
		if (pageUrl == "fetchMobileTechVersions") {
			fillSelectbox($("select[name='"+ objName +"']"), data.versions, "No Versions available");
		}
		
		//To fill the widgets for the selected web
		if (pageUrl == "fetchWebLayerWidgets") {
			for (i in data.widgets) {
				fillOptions($("select[name='"+ objName +"']"), data.widgets[i].id, data.widgets[i].name);
				map[data.widgets[i].id] = data.widgets[i].techVersions;
			}
		}
	}
	
	//To fill the versions of the selected widget
	function getWidgetVersions(obj, objectName) {
		var id = $(obj).val();
		fillSelectbox($("select[name='"+ objectName +"']"), map[id], "No Versions available");
	}
</script>