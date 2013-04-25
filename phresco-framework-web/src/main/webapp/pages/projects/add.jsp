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

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationType"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyGroup"%>
<%@ page import="com.photon.phresco.commons.model.TechnologyInfo"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>

<script src="js/project.js" ></script>

<%
	ProjectInfo projectInfo = (ProjectInfo) request.getAttribute(FrameworkConstants.REQ_PROJECT);
	List<ApplicationType> layers = (List<ApplicationType>) request.getAttribute(FrameworkConstants.REQ_PROJECT_LAYERS);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<TechnologyGroup> appLayerTechGroups = null;
	List<TechnologyGroup> webLayerTechGroups = null;
	List<TechnologyGroup> mobileLayerTechGroups = null;
	String appLayerId = "";
	String webLayerId = "";
	String mobileLayerId = "";
	String appLayerName = "";
	String webLayerName = "";
	String mobileLayerName = "";
	boolean hasAppLayer = false;
	boolean hasWebLayer = false;
	boolean hasMobileLayer = false;
	boolean appLayerInfosAvailable = false;
	if (CollectionUtils.isNotEmpty(layers)) {
		for (ApplicationType layer : layers) {
		    if (FrameworkConstants.LAYER_APP_ID.equals(layer.getId())) {
		        appLayerId = layer.getId();
		        appLayerName = layer.getName();
		        appLayerTechGroups = layer.getTechGroups();
		        
		        hasAppLayer = true;
		    }
			if (FrameworkConstants.LAYER_WEB_ID.equals(layer.getId())) {
			    webLayerId = layer.getId();
			    webLayerName = layer.getName();
			    webLayerTechGroups = layer.getTechGroups();
			    hasWebLayer = true;
		    }
			if (FrameworkConstants.LAYER_MOB_ID.equals(layer.getId())) {
			    mobileLayerId = layer.getId();
			    mobileLayerName = layer.getName();
			    mobileLayerTechGroups = layer.getTechGroups();
			    hasMobileLayer = true;
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
	if (projectInfo != null) {
		disablStr = "disabled";
		id = projectInfo.getId();
		name = projectInfo.getName();
		projectCode = projectInfo.getProjectCode();
		description = projectInfo.getDescription();
		version = projectInfo.getVersion();
		appInfos = projectInfo.getAppInfos();
	}
	
	//To check whether app layer infos are available or not in edit page
	if (hasAppLayer && FrameworkConstants.FROM_PAGE_EDIT.equals(fromPage)) {
		if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
			for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
			    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
			    for (ApplicationInfo appInfo : appInfos) {
					for (TechnologyInfo techInfo : techInfos) {
						if (appInfo.getTechInfo().getId().equals(techInfo.getId())) {
							appLayerInfosAvailable = true;
						}
					}
			    }
			}
		}	
	}
%>

<div class="page-header">
	<% if (projectInfo == null) {  %>
	<h1>
		<s:text name="lbl.projects.add"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="lbl.mandatory.text"/></small>
	</h1>
	<% } else { %>
	<h1>
		<s:text name="lbl.projects.edit"/> <small><span class="mandatory">*</span>&nbsp;<s:text name="lbl.mandatory.text"/></small>
	</h1>
	<% } %>
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
					id="projectName" title="<s:text name="title.30.chars"/>">
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
				<input placeholder='<s:text name="place.hldr.proj.add.code"/>' class="input-xlarge" type="text" id="projectCode"
					value="<%= StringUtils.isNotEmpty(projectCode) ? projectCode : "" %>" disabled />
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
					rows="3" name="projectDesc" maxlength="150" 
					title="<s:text name="title.150.chars"/>"><%= StringUtils.isNotEmpty(description) ? description : "" %></textarea>
			</div>
		</div>
		<!-- Description Ends -->
		
		<!-- Version Starts -->
		<div class="control-group" id="projectVersionControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.version' />
			</label>
			<div class="controls">
				<input id="projVersion" class="input-xlarge" type="text" value="<%= StringUtils.isNotEmpty(version) ? version : "1.0" %>"<%= disablStr %> 
					name="projectVersion" placeholder='<s:text name="place.hldr.proj.add.version"/>' maxlength="20" 
					title="<s:text name="title.20.chars"/>">
				<span class="help-inline" id="projectVersionError"></span>
			</div>
		</div>
		<!-- Version Starts -->
		
		<!-- Build it myself Starts -->
		<%-- <div class="control-group">
			<label class="control-label labelbold"></label>
			<div class="controls">
				 <ul class="inputs-list">
					<li> 
						<input type="radio" name="buildType" value="buildItMyself" checked="checked"/> 
						<span class="vAlignMiddle buildTypeSpan"><s:text name="lbl.projects.build.type.myself"/></span>
					</li>
				</ul>
			</div>
		</div> --%>
		<!-- PreBuild/Build it myself ends -->
	
		<!-- Application layer accordion starts --> 
		<% 
	    	String disblStr = "";
	    	String chckdStr = "";
			if (projectInfo != null) { 
				if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
					for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
					    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
					    if (CollectionUtils.isNotEmpty(techInfos)) {
					        for (TechnologyInfo techInfo : techInfos) {
					        	if (CollectionUtils.isNotEmpty(appInfos)) {
						        	for (ApplicationInfo appInfo: appInfos) {
						        		if (techInfo.getId().equals(appInfo.getTechInfo().getId())){
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
		<% if (hasAppLayer) {
		%>		
				<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container" id="appSec">
							<span class="siteaccordion closereg" id="appLayerControl" onclick="accordionClick(this, $('input[value=<%= appLayerId %>]'));">
								<span class="mandatory">
									<% if (layers.size() == 1) { %>
											*&nbsp;
									<% } %>
									<input type="checkbox" id="checkAll1" class="accordianChkBox appLayerChkBox" name="layer" value="<%= appLayerId %>" <%= chckdStr%> <%= disblStr%>/>
									<a id="appLayerHeading" class="vAlignSub"><%= appLayerName %></a>
									<p id="appLayerError" class="accordion-error-txt"></p>
								</span>
							</span>
							<div class="mfbox siteinnertooltiptxt hideContent">
								<div class="scrollpanel">
										<% //For edit page , if app layer infos are available
										if (FrameworkConstants.FROM_PAGE_EDIT.equals(fromPage) && appLayerInfosAvailable) {
										%>
											<section class="scrollpanel_inner" id="appLayerContent">
										<%	
											if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
												for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
												    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
												    int rowCount = 1;
												    for (ApplicationInfo appInfo : appInfos) {
												    	String[] appCode = null;
												    	String appTechId = "";
												    	String appTechVersion = "";
												    	String techName = "";
														for (TechnologyInfo techInfo : techInfos) {
															if (appInfo.getTechInfo().getId().equals(techInfo.getId())) {
																appTechId = appInfo.getTechInfo().getId();
																appCode = appInfo.getCode().split("-");
																appTechVersion = appInfo.getTechInfo().getVersion();
																techName = techInfo.getName();
										%>					
															
																<div class="appLayerContents" style="height:33px;">
																	<div class="align-div-center">
																		<div class="align-in-row">
																			<label class="control-label autoWidth">																				
																				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.app.code' />
																			</label>
																			<input type="text" class="appLayerProjName" name="appLayerProjName" onblur="checkForApplnDuplicate(this);" temp="<%= rowCount %>" value="<%= appCode[0] %>" 
																				placeholder='<s:text name="place.hldr.proj.app.code"/>' style="float:left" disabled>
																		</div>
																		<div class="align-in-row">
																			<label class="control-label autoWidth"><s:text name='lbl.technology'/></label>
																			<select class="input-medium" temp="<%= rowCount %>" id="<%= rowCount %>_App_Technology"  layer="<%= appLayerId %>" name="<%= appLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" disabled selected onchange="getAppLayerTechVersions(this);">
																				<option additionalParam="<%= appLayerTechGroup.getId() %>" value="<%= appTechId %>"><%= techName %></option>
																			</select>
																		</div>
																		<div class="float-left">
																			<label class="control-label autoWidth"><s:text name='lbl.version'/></label>
																			<select class="input-medium" id="<%= rowCount %>_App_Version" name="<%= appLayerId %>Version" disabled selected>
																				<option value="" selected disabled><%= appTechVersion %></option>
																			</select>
																		</div>
																		<div class="float-left">
																			<img class="appLayerAdd imagealign" src="images/icons/add_icon.png" onclick="addAppLayer(this)">
																		</div>
																	</div>
																</div>
																
																
										<%						
																rowCount++;
															}
													    }
												    }
												}
											}
										%>
											 </section>
										<%
										} else {
										%>
										<section class="scrollpanel_inner" id="appLayerContent">
											<div class="appLayerContents" style="height:33px;">
												<div class="align-div-center">
													<div class="align-in-row">
														<label class="control-label autoWidth">
															<span class="mandatory">*</span>&nbsp;<s:text name='lbl.app.code' />
														</label>
														<input type="text" class="appLayerProjName" name="appLayerProjName" onblur="checkForApplnDuplicate(this);" temp="1" style="float:left"
															placeholder='<s:text name="place.hldr.proj.app.code"/>' maxlength="12" title="<s:text name="title.12.chars"/>">
													</div>
													<div class="align-in-row">
														<label class="control-label autoWidth"><s:text name='lbl.technology'/></label>
														<select class="input-medium" temp="1" id="1_App_Technology"  layer="<%= appLayerId %>" name="<%= appLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" <%= disblStr %> onchange="getAppLayerTechVersions(this);">
															<option value="" selected disabled><s:text name='lbl.default.opt.select.tech'/></option>
															<% String techIdVersion = "";
																String singleTechVersion = "";
																if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
																	for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
																	    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
																	    if (CollectionUtils.isNotEmpty(techInfos)) {
																	        for (TechnologyInfo techInfo : techInfos) {
																	        	String slctStr = "";
																	        	List<String> techVersions = techInfo.getTechVersions();
																	        	//If Layer contains single technology and version ,it should be selected as default
															        			if (appLayerTechGroups.size() == 1 && techInfos.size() == 1 && techVersions.size() == 1 && layers.size() == 1) {
																		    		slctStr = "selected";
																		    		singleTechVersion = techVersions.get(0);
															        			}
																	        	if (CollectionUtils.isNotEmpty(appInfos)) {
																		        	for (ApplicationInfo appInfo: appInfos) {
																		        		if (techInfo.getId().equals(appInfo.getTechInfo().getId())){
																		        			slctStr = "selected";
																		        			techIdVersion = appInfo.getTechInfo().getVersion();
																		        		}
																		        	}
																	        	}
															%>
																				<option additionalParam="<%= appLayerTechGroup.getId() %>" value="<%= techInfo.getId()%>" <%= slctStr%>><%= techInfo.getName() %></option>
															<%
																	        }
															%>
																		<script type="text/javascript">
																			<% if (appLayerTechGroups.size() == 1 && techInfos.size() == 1 && StringUtils.isNotEmpty(singleTechVersion) && layers.size() == 1) { %>
																				$('input[name="layer"]').attr("checked", true);
																			<% } %>
																		</script>
															<%															        
																	    }
																	}
																}
															%>
														</select>
													</div>
													<div class="float-left">
														<label class="control-label autoWidth"><s:text name='lbl.version'/></label>
														<%if (CollectionUtils.isNotEmpty(appInfos) && StringUtils.isNotEmpty(techIdVersion)) { %>
															<select class="input-medium" id="1_App_Version" name="<%= appLayerId %>Version" <%= disblStr %> >
																<option value="" selected disabled><%= techIdVersion %></option>
															</select>
											        	<%	} else { %>
															<select class="input-medium" id="1_App_Version" name="<%= appLayerId %>Version" <%= disblStr %> >
																<% if(StringUtils.isNotEmpty(singleTechVersion) ) { %>		    	
																		<option value="<%= singleTechVersion %>" selected><%= singleTechVersion %></option>
																<% } else { %>
																		<option value="" selected disabled><s:text name='lbl.default.opt.select.version'/></option>
															<% } %>
															</select>
														<% } %>
													</div>
													<div class="float-left">
														<img class="appLayerAdd imagealign" src="images/icons/add_icon.png" onclick="addAppLayer(this)">
														<img id="deleteIcon" class="hideContent appLayerMinus imagealign" src="images/icons/minus_icon.png" onclick="removeAppLayer(this);">
													</div>
												</div>
											</div>
										</section>
										<%	
										}	
										%>
								</div>
							</div>
						</section>  
					</div>
				</section>
			</div>	
		<%	
			} 
		%>
		<!-- Application layer accordion ends -->
		
		<!-- Web layer accordion starts -->
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
		<% if (hasWebLayer) { %>
			<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container" id="webSec">
							<span class="siteaccordion closereg" id="webLayerControl" onclick="accordionClick(this, $('input[value=<%= webLayerId %>]'));">
								<span class="mandatory">
									<% if (layers.size() == 1) { %>
										*&nbsp;
									<% } %>
									<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="<%= webLayerId %>" <%= checkedLayer%> <%= disableLayer %>/>
									<a id="webLayerHeading" class="vAlignSub"><%= webLayerName %></a>
									<p id="webLayerError" class="accordion-error-txt"></p>
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
											
												<select class="input-medium" name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECH_GROUP %>" <%= disableLayer%>
													onchange="getWebLayerWidgets('<%= webLayerId %>', '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>');">
													<% 
														String singleWidget = "";
														String singleWidgetVersion = "";
														if (StringUtils.isNotEmpty(techGroup)) { %>
														<option value=""><%= techGroup %></option>
													<% } else { %>
															<option value="" selected disabled><s:text name='lbl.default.opt.select.weblayer'/></option>
													<%
															if (CollectionUtils.isNotEmpty(webLayerTechGroups)) {
															    for (TechnologyGroup webLayerTechGroup : webLayerTechGroups) {
																    List<TechnologyInfo> webInfos1 = webLayerTechGroup.getTechInfos();	
																	if (CollectionUtils.isNotEmpty(webInfos1)) {
																	    for (TechnologyInfo webInfo : webInfos1) {
																	    	List<String> webversions = webInfo.getTechVersions();
																	    	//If Layer contains single technology and version ,it should be selected as default
																		    if (webLayerTechGroups.size() == 1 && webInfos1.size() == 1 && webversions.size() == 1 && layers.size() == 1) {
																				selectedStr = "selected";
																				singleWidget = webInfo.getName();
																				singleWidgetVersion = webversions.get(0);
																	    	}
																	    }
																	}
													%>
																	<script type="text/javascript">
																		<% if (webLayerTechGroups.size() == 1 && webInfos1.size() == 1 && StringUtils.isNotEmpty(singleWidgetVersion) && layers.size() <= 1) { %>
																			$('input[name="layer"]').attr("checked", true);
																		<% } %>
																	</script>
													<%
																	
													%>		
																	<option value="<%= webLayerTechGroup.getId() %>" <%= selectedStr%>><%= webLayerTechGroup.getName() %></option>
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
											
												<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" class="input-medium" <%= disableLayer %> onchange="getWidgetVersions(this, '<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
													<% if (StringUtils.isNotEmpty(techName)) { %>
														<option value="" selected disabled><%= techName %></option>
													<% } else { 
															if(StringUtils.isNotEmpty(singleWidget)) {
													%>
															<option value="" selected disabled><%= singleWidget %></option>
															<% } else { %>
															<option value="" selected disabled><s:text name='lbl.default.opt.select.widget'/></option>
													<% 
															}
														}
													%>
												</select>
											</div>
											<div class="float-left">
												<label class="control-label autoWidth">
													<s:text name='lbl.version'/>
												</label>
											
												<select name="<%= webLayerId + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" <%= disableLayer %> class="input-medium">
													<% if (StringUtils.isNotEmpty(appInfoVersion)) { %>
														<option value="" selected disabled><%= appInfoVersion %></option>
													<% } else { 
															if(StringUtils.isNotEmpty(singleWidgetVersion)) {	
													%>
															<option value="" selected disabled><%= singleWidgetVersion %></option>
															<% } else { %>		
															<option value="" selected disabled><s:text name='lbl.default.opt.select.version'/></option>
													<% 	 	}
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
		<% } %>
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
		<% if (hasMobileLayer) { %>
			<div class="theme_accordion_container">
				<section class="accordion_panel_wid">
					<div class="accordion_panel_inner">
						<section class="lft_menus_container" id="mobSec">
							<span class="siteaccordion closereg" id="mobileLayerControl" onclick="accordionClick(this, $('input[value=<%= mobileLayerId %>]'));">
								<span class="mandatory">
									<% if (layers.size() == 1) { %>
										*&nbsp;
									<% } %>
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
												if (projectInfo != null) {
													List<TechnologyInfo> mobileInfos = mobileLayerTechGroup.getTechInfos();	
													if (CollectionUtils.isNotEmpty(mobileInfos)) {
													    for (TechnologyInfo mobileInfo : mobileInfos) {
													    	for (ApplicationInfo appInfo : appInfos) {
																if (mobileInfo.getId().equals(appInfo.getTechInfo().getId())){
																	checkedMobLayer = "checked";
																	disabledMobLayer = "disabled";
																	selectedMobLayer = "selected";
																	techVersion = appInfo.getTechInfo().getVersion();
																}
												    		}
														}
													}
										    	} else {
										    		List<TechnologyInfo> mobileInfos = mobileLayerTechGroup.getTechInfos();	
										    		for (TechnologyInfo mobileInfo : mobileInfos) {
												    	List<String> mobileVersions = mobileInfo.getTechVersions();
												    	if(CollectionUtils.isNotEmpty(mobileVersions)) {
												    		//If Layer contains single technology and version ,it should be selected as default
											    			if (mobileLayerTechGroups.size() == 1 && mobileInfos.size() == 1 && mobileVersions.size() == 1 && layers.size() == 1) {
											    				checkedMobLayer = "checked";
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
														<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" <%= disabledMobLayer %>
															onchange="getTechVersions('<%= mobileLayerId %>', '<%= mobileLayerTechGroup.getId() %>', '<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>');">
															<option value="" selected disabled><s:text name='lbl.default.opt.select.type'/></option>
															<%
																String singleTechVersion = "";
																List<TechnologyInfo> mobileInfos = mobileLayerTechGroup.getTechInfos();	
																if (CollectionUtils.isNotEmpty(mobileInfos)) {
																    for (TechnologyInfo mobileInfo : mobileInfos) {
																    	String selectTech = "";
																    	List<String> mobileVersions = mobileInfo.getTechVersions();
																    	if(mobileLayerTechGroups.size() == 1 && mobileInfos.size() == 1 && layers.size() <= 1 ) {
																    		if(CollectionUtils.isNotEmpty(mobileVersions) && mobileVersions.size() == 1) {
																    			selectTech = "selected";
																    			singleTechVersion = mobileVersions.get(0);
																    		}
																    		if (CollectionUtils.isEmpty(mobileVersions)) {
																    			selectTech = "selected";
																    			singleTechVersion = "No versions available";
																    		}
																    	}
													    	%>
																<script type="text/javascript">
																	<% if (mobileLayerTechGroups.size() == 1 && mobileInfos.size() == 1 && StringUtils.isNotEmpty(singleTechVersion) && layers.size() == 1) { %>
																		$('input[name="layer"]').attr("checked", true);
																	<% } %>
																</script>
															<%
																    	if (CollectionUtils.isNotEmpty(appInfos)) {
																	    	for (ApplicationInfo appInfo : appInfos) {
																				if (mobileInfo.getId().equals(appInfo.getTechInfo().getId())){
																					selectTech = "selected";
																				}
																    		}
																    	}
															%>
																		<option value="<%= mobileInfo.getId() %>" <%= selectTech %>><%= mobileInfo.getName() %></option>
															<%
																	}
																}
															%>
														</select>
													</div>
													<div class="align-in-row">
														<select class="input-medium" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_VERSION %>" id="<%= mobileLayerTechGroup.getId() %>" value="" <%= disabledMobLayer %>>
															<%
																if (projectInfo == null) { 
																	if (StringUtils.isNotEmpty(singleTechVersion)) {
															%>		 
																		 <option value="" selected disabled><%= singleTechVersion %></option>
															<%
																	} else {
															%>
																		<option value="" selected disabled><s:text name='lbl.default.opt.select.version'/></option>
															<% 		}
																} else { %>
																	<option value=""><%= techVersion %></option>
															<%
																}
															%>
														</select>
													</div>
													<div class="align-in-row width" id="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_PHONE %>">
														<input type="checkbox" name="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_PHONE %>" value="true" <%= disabledMobLayer %>/>
														<span class="vAlignSub">&nbsp;<s:text name='lbl.device.type.phone'/></span>
													</div>
													<div class="float-left element-width" id="<%= mobileLayerTechGroup.getId() + FrameworkConstants.REQ_PARAM_NAME_TABLET %>">
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
		<% } %>
		<!-- Mobile layer accordion ends -->
	</div>	
		<!-- hidden fields start-->
		<% if (projectInfo != null) {%>
			<input type="hidden" name="id" value="<%= id %>"/>
			<input type="hidden" name="projectName" value="<%= name %>"/>
			<input type="hidden" name="projectVersion" value="<%= version %>"/>
			<input type="hidden" name="fromTab" value="edit">
		<% } else {%>
			<input type="hidden" name="fromTab" value="add">
		<% } %>
		<input type="hidden" name="projectCode" value="<%= projectCode %>"/>
		<!-- hidden fields end -->
	</form>
	<!--  Form Ends -->
</div>

<!--  Submit and Cancel buttons Starts -->
<div class="actions">
	<%if (projectInfo == null) { %>
		<input type="button" id="createProject" value="<s:text name="lbl.btn.create"/>" class="btn btn-primary">
	<% } else { %>
		<input type="button" id="updateProject" value="<s:text name="lbl.btn.update"/>" class="btn btn-primary">
	<% } %>
	<input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
		onclick="loadContent('applications', $('#formCustomers'), $('#container'), '', '', true);"> 
</div>
<!--  Submit and Cancel buttons Ends -->

<script type="text/javascript">
	addProjectAccordion();
	//openprojectAccordion();
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
		$(".projectScrollDiv").scrollbars();
	}
	var appLayerChckBoxEnabled = false;

	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		preOpenAccordian();//To open accordian for single technology
		
		<% if (appInfos != null) {
			for (ApplicationInfo appInfo : appInfos) {
				if (appInfo.getTechInfo().getId().equalsIgnoreCase("tech-iphone-library")) { %>
					$("#iphonePhone").hide();
		 			$("#iphoneTablet").hide();
			<%	}
    		}
		}%>
		
		//Will be triggered when the create project button is clicked
		$('#createProject').click(function() {
			var params = $('#formCustomers').serialize();
			
			//To construct user selected app layer infos 
			//Iterate each row and construct app layer infos 
			var appLayerInfos = new Array();
			if ($('input[value=app-layer]').is(':checked')) {//if applayer checkbox is checked
				$('input[name=appLayerProjName]').each(function() {//iterate each row and construct params
					var count = $(this).attr("temp");
					var appCode = $(this).val();
					var techId = $('#'+count+'_App_Technology').val();
					var techVersion = $('#'+count+'_App_Version').val();
					appLayerInfos.push(appCode + "#APPSEP#" + techId + "#VSEP#" + techVersion + "#ROWSEP#" + count);
				});
				params = params.concat("&appLayerInfos=");
				params = params.concat(appLayerInfos);
			}	
				params = checkForAppCodeId(params);
			validate('createProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.add.proj'/>');
		});
		

		//Will be triggered when the update project button is clicked
		
		$('#updateProject').click(function() {
			var params = $('#formCustomers').serialize();
			
			//To construct user selected app layer infos 
			//Iterate each row and construct app layer infos 
			var appLayerInfos = new Array();
			if ($('input[value=app-layer]').is(':checked')) {
				$('input[name=appLayerProjName]').each(function() {
					if (!$(this).prop('disabled')) {//To check for newly added app layer row
						$('input[value=app-layer]').prop("disabled", false);//To enable applayer accordion check box if new row is added
						appLayerChckBoxEnabled = true;
						var count = $(this).attr("temp");
						var appCode = $(this).val();
						var techId = $('#'+count+'_App_Technology').val();
						var techVersion = $('#'+count+'_App_Version').val();
						appLayerInfos.push(appCode + "#APPSEP#" + techId + "#VSEP#" + techVersion + "#ROWSEP#" + count);
					}
				});
				params = params.concat("&appLayerInfos=");
				params = params.concat(appLayerInfos);
				params = checkForAppCodeId(params);
			}	
			validate('updateProject', $('#formCreateProject'), $("#container"), params, '<s:text name='progress.txt.update.proj'/>');
		});
		
	});
	
	var objName; //select box object name
	//To get the versions of the selected mobile technologies
	function getTechVersions(layerId, techGroupId, toBeFilledCtrlName, techId, obj) {
		showLoadingIcon();
		$("#"+techGroupId+"<%= FrameworkConstants.REQ_PARAM_NAME_PHONE %>").show();
		$("#"+techGroupId+"<%= FrameworkConstants.REQ_PARAM_NAME_TABLET %>").show();
		objName = toBeFilledCtrlName;
		$('select[name="'+techGroupId+'<%= FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>"]').each(function () {
	 		var id = $(this).val();
	 		if (id === "tech-iphone-library") {
	 			$("#"+techGroupId+"<%= FrameworkConstants.REQ_PARAM_NAME_PHONE %>").hide();
	 			$("#"+techGroupId+"<%= FrameworkConstants.REQ_PARAM_NAME_TABLET %>").hide();
	 		}
       	});
		var params = getBasicParams();
		params = params.concat("&layerId=");
		params = params.concat(layerId);
		params = params.concat("&techGroupId=");
		params = params.concat(techGroupId);
		if (techId != undefined && !isBlank(techId)) {
			params = params.concat("&" + techGroupId + "<%= FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY %>" + "=");
			params = params.concat(techId);
		}
		
		var pageUrl = "";
		if (layerId == 'app-layer') {
			//url for loading versions in app layer
			pageUrl = "fetchTechVersions";
		} else {
			//url for loading versions in mobile layer
			pageUrl = "fetchMobTechVersions";
		}
		loadContent(pageUrl, $('#formCreateProject'), '', params, true, true);
				
		if (obj != undefined && !isBlank(obj)) {
			checkForApplnDuplicate(obj);			
		}
	}
	
	//Creats app layer row
	var count = 2;
	function addAppLayer(obj) {
		<% if (FrameworkConstants.FROM_PAGE_EDIT.equals(fromPage)) { %>
			count = $(".appLayerProjName").size() + 1;
		<% } %>
		var applayer = '<%= appLayerId %>';
		var newAppLayerRow = $(document.createElement('div')).attr("class", "appLayerContents").css("height","33px");
		newAppLayerRow.html("<div class='align-div-center'><div class='align-in-row'><label class='control-label autoWidth'><span class='mandatory'>*</span>&nbsp;<s:text name='lbl.app.code' /></label>" + 
							"<input type='text' class='appLayerProjName' onblur='checkForApplnDuplicate(this);' name='appLayerProjName' temp='"+count+"' "+
							" maxlength='12' title='12 Characters only' style='float:left' placeholder='<s:text name='place.hldr.proj.app.code'/>'></div>" +
							"<div class='align-in-row'><label class='control-label autoWidth'><s:text name='lbl.technology'/></label>" +
							"<select class='input-medium' name='app-layerTechnology' temp='"+ count +"' id='" + count + "_App_Technology' layer='" + applayer + "' onchange='getAppLayerTechVersions(this);'>" +
							"<option value='' selected disabled>Select Technology</option></select></div>" +
							"<div class='float-left'><label class='control-label autoWidth'><s:text name='lbl.version'/></label>" +
							"<select class='input-medium' name='Version' id='"+count+"_App_Version'> <option value='' selected disabled>Select Version</option></select></div>" + 
							"<div class='float-left'><img class='appLayerAdd imagealign'src='images/icons/add_icon.png' onclick='addAppLayer(this)'>" +
							"<img id='deleteIcon' class='hideContent appLayerMinus imagealign' src='images/icons/minus_icon.png' style='margin-left: 5px;' onclick='removeAppLayer(this);'></div></div>");
		newAppLayerRow.appendTo("#appLayerContent");
		loadTechnology(count);
		enableDisableMinusBtn('appLayerAdd', 'appLayerMinus');
		count++;
	}
	
	//load technology for new added app layer row
	function loadTechnology(rowCount) {
		<%
		if (CollectionUtils.isNotEmpty(appLayerTechGroups)) {
			for (TechnologyGroup appLayerTechGroup : appLayerTechGroups) {
			    List<TechnologyInfo> techInfos = appLayerTechGroup.getTechInfos();
			    if (CollectionUtils.isNotEmpty(techInfos)) {
			    	for (TechnologyInfo techInfo : techInfos) {
		%>
						var techGrpId = '<%= appLayerTechGroup.getId() %>';
						var techId = '<%= techInfo.getId() %>';
						var techName = '<%= techInfo.getName() %>'; 
						$("#" + rowCount +"_App_Technology").append('<option additionalParam="'+techGrpId+'" value="'+ techId +'">'+techName+'</option>');
		<%
			    	}
			    }
			}   
		}
		%>
	}
	
	function validateProjLayers() {
		var redirect = true;
		//To hide project name, code, version error msg
		hideError($("#projectNameControl"), $("#projectNameError"));
		hideError($("#projectCodeControl"), $("#projectCodeError"));
		hideError($("#projectVersionControl"), $("#projectVersionError"));
		
		//Check for app layer details empty validation 
		if ($('input[value=app-layer]').is(':checked')) {
			//To hide moblayer, web layer accordian error msg
			hideErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"));
			hideErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"));
			$('.appLayerProjName').each(function() {
				var tempAtr = $(this).attr("temp");
				if (isBlank($(this).val())) {//app code empty validation 
					$(this).focus();
					showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), '<s:text name='err.msg.app.code.missing'/>');
					redirect = false;
					return false;
				}
				if (isBlank($("#"+ tempAtr +"_App_Technology").val())) {// technology validation
					$("#"+ tempAtr +"_App_Technology").focus();
					showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), '<s:text name='err.msg.select.technology'/>');
					redirect = false;
					return false;
				}
			});
		}
		
		//To make appLayer checkbox disable again if errour found -- In edit page
		if (appLayerChckBoxEnabled && !redirect) {
			$('input[value=app-layer]').prop("disabled", true);
		}
			
		return redirect;
	}
	
	//Check for App Layer - app code duplication
	function checkForApplnDuplicate(obj) {
		var inputClass = $(obj).attr("class");
		var tech = $(obj).parent().parent().find('select[name=app-layerTechnology]').val();
		var currentVal = $(obj).val() + tech;
		$('.'+inputClass).each(function() {
			if (currentVal != "" && !isBlank(currentVal)) {
				var existingVal = $(this).val() + $(this).parent().parent().find('select[name=app-layerTechnology]').val();
				//To match app code with current row with other rows, and check for duplication
				if ($(obj).attr("temp") !== $(this).attr("temp") && currentVal.toLowerCase() === existingVal.toLowerCase()) {
					$(obj).val("");
					$(obj).focus();
					showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), '<s:text name='err.msg.app.code.unique'/>');
					return false;
				} else {
					hideErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"));
				}
			} 
		});
	}
	
	//Check for App Code Id
	 function checkForAppCodeId(params) {
		var hasAppCodeId;
		 if(($("input[class='appLayerProjName']").val().length > 0)) {
			 	hasAppCodeId = true;
				params = params.concat("&hasAppCodeId=");
				params = params.concat(hasAppCodeId); 

		} else {
				hasAppCodeId = false;
				params = params.concat("&hasAppCodeId=");
				params = params.concat(hasAppCodeId); 
		}
		 return params;
	 }
</script>