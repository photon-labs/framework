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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Enumeration"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@	page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<%@ page import="com.photon.phresco.framework.model.CIJob" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.User"%>

<%
	String showSettings = (String) request.getAttribute(FrameworkConstants.REQ_SHOW_SETTINGS);
	if(showSettings != null && Boolean.valueOf(showSettings)){
		showSettings = "checked";
	}
	
	String[] weekDays = {"", "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	String[] months = {"", "January","February","March","April","May","June","July","August","September","October","November","December"};

	CIJob existingJob =  (CIJob) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOB);
	String disableStr = existingJob == null ? "" : "disabled";
    ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
    String appId  = appInfo.getId();
    
    String actionStr = "saveJob";
    List<String> confluenceSites = (List<String>)request.getAttribute(FrameworkConstants.REQ_CONFLUENCE_SITES);
    actionStr = (existingJob == null || StringUtils.isEmpty(existingJob.getRepoType())) ? "saveJob" : "updateJob";
    existingJob = (existingJob == null || StringUtils.isEmpty(existingJob.getRepoType())) ? null : existingJob; // when we setup it ll have only jenkins url and port in that case we have to check svnUrl and make null
    List<String> existingClonedWorkspaces = (List<String>) request.getAttribute(FrameworkConstants.REQ_EXISTING_CLONNED_JOBS);
    List<Properties> existingJobsNames = (List<Properties>) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOBS_NAMES);
    Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	String successEmails = "";
	String failureEmails = "";
	if (existingJob != null) {
		successEmails = existingJob.getEmail().get("successEmails");
		failureEmails = existingJob.getEmail().get("failureEmails");
		successEmails = StringUtils.isEmpty(successEmails)? "" : "checked";
		failureEmails = StringUtils.isEmpty(failureEmails)? "" : "checked";
	}
	
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
	
	User userInfo = (User)session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
	String LoginId = "";
    if (userInfo != null) {
        LoginId = userInfo.getName();
    }
%>
<form id="configureForm" name="ciDetails" action="<%= actionStr %>" method="post" class="ci_form form-horizontal">
	<div class="theme_accordion_container clearfix" style="float: none;">
	    <section class="accordion_panel_wid">
	        <div class="accordion_panel_inner adv-settings-accoridan-inner">
	            <section class="lft_menus_container adv-settings-width">
	                <span class="siteaccordion" id="siteaccordion_active">
	                	<div>
		                	<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
		                	<s:text name="lbl.build.basic.config"/>
	                	</div>
                	</span>
	                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
	                    <div class="scrollpanel adv_setting_accordian_bottom">
	                        <section class="scrollpanel_inner">
				
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<span class="red">* </span> <s:text name='lbl.name' />
									</label>
									<div class="controls">
										<input type="text" id="name" name="name" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getName()%>" <%= existingJob == null ? "" : "disabled" %> autofocus>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.svn.type' />
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input type="radio" name="svnType" value="svn"  <%= existingJob == null ? "checked" : "" %> />&nbsp; <s:text name="lbl.svn"/>
										<input type="radio" name="svnType" value="git" />&nbsp; <s:text name="lbl.git"/>
										<input type="radio" name="svnType" value="clonedWorkspace" />&nbsp; <s:text name="lbl.cloned"/>
									</div>
								</div>
								
								<div class="control-group" id="urlControl">
									<label class="control-label labelbold popupLbl">
										<span class="red">* </span> <s:text name='lbl.svn.url' />
									</label>
									<div class="controls">
										<input type="text" id="svnurl" name="svnurl" class="input-xlarge" value="<%= existingJob == null ? "" : existingJob.getSvnUrl()%>">
									</div>
								</div>
					
								<div class="control-group" id="branchControl">
									<label class="control-label labelbold popupLbl">
										<span class="red">* </span> <s:text name='lbl.branch' />
									</label>
									<div class="controls">
										<input type="text" id="branch" name="branch" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
									</div>
								</div>
								
								<!-- Use clonned workspaces -->
					            <div class="control-group" id="parentProjectControl">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.parent.project' />
									</label>
									<div class="controls">
											<select name="usedClonnedWorkspace" class="input-xlarge " >
											<% 
												if (existingClonedWorkspaces != null) {
													for(String clonedWrkSpace : existingClonedWorkspaces) {
														if(existingJob != null && existingJob.getName().equals(clonedWrkSpace)) {
															continue;
														}
														
											%>
												<option value="<%= clonedWrkSpace %>"><%= clonedWrkSpace %></option>
											<% 
													}
												}
											%>
										</select>
									</div>
								</div>
											
								<div class="control-group" id="usernameControl">
									<label class="control-label labelbold popupLbl">
										<span class="red">* </span> <s:text name='lbl.username' />
									</label>
									<div class="controls">
										<input type="text" id="username" name="username" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
									</div>
								</div>
								
								<div class="control-group" id="passwordControl">
									<label class="control-label labelbold popupLbl">
										<span class="red">* </span> <s:text name='lbl.password' />
									</label>
									<div class="controls">
										<input type="password" id="password" name="password" class="input-xlarge" maxlength="63" title="63 Characters only" value="">
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.recipient.mail' />
									</label>
									<div class="controls">
										<div class="input">
											<div class="multipleFields emaillsFieldsWidth">
												<div><input id="successEmail" type="checkbox" name="emails" <%= successEmails %> value="success"/>&nbsp; <s:text name="lbl.when.success"/></div>
											</div>
											<div class="multipleFields">
												<div><input id="successEmailId" type="text" name="successEmailIds"  value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("successEmails")%>" disabled></div>
											</div>
										</div>
										
										<div class="input">
											<div class="multipleFields emaillsFieldsWidth">
												<div><input id="failureEmail" type="checkbox" name="emails"  <%= failureEmails %> value="failure"/> &nbsp;<s:text name="lbl.when.fail"/></div>
											</div>
											<div class="multipleFields">
												<div><input id="failureEmailId" type="text" name="failureEmailIds"  value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("failureEmails")%>" disabled></div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.build.trigger' />
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input id="buildPeriodically" type="checkbox" name="triggers" value="TimerTrigger"/>&nbsp;Build periodically
										<input id="pollSCM" type="checkbox" name="triggers" value="SCMTrigger"/>&nbsp;Poll SCM
									</div>
								</div>
									
								<%
									String schedule = existingJob == null ? "" : (String)existingJob.getScheduleType();
									
									String dailyEvery = "";
									String dailyHour = "";
									String dailyMinute = "";
									
									String weeklyWeek = "";
									String weeklyHour = "";
									String weeklyMinute = "";
									
									String monthlyDay = "";
									String monthlyMonth = "";
									String monthlyHour = "";
									String monthlyMinute = "";
									
									String CronExpre = existingJob == null ? "" : existingJob.getScheduleExpression();
									String[] cronSplit = CronExpre.split(" ");
									if(schedule.equals("Daily")) {
										if(CronExpre.contains("/")) {
											dailyEvery = "checked";
										}
										
							            if (cronSplit[0].contains("/")) {
							            	dailyMinute = cronSplit[0].substring(2) + "";
							            } else {
							            	dailyMinute = cronSplit[0];
							            }
							            
										if (cronSplit[1].contains("/")) {
											dailyHour = cronSplit[1].substring(2) + "";
							            } else {
							            	dailyHour = cronSplit[1];
							            }
										
									} else if(schedule.equals("Weekly")) {
										weeklyWeek = cronSplit[4];
										weeklyHour = cronSplit[1];
										weeklyMinute = cronSplit[0];
										
									} else if(schedule.equals("Monthly")) {
										monthlyDay = cronSplit[2];
										monthlyMonth = cronSplit[3];
										monthlyHour = cronSplit[1];
										monthlyMinute = cronSplit[0];
										
									}
								%>
							<div id="triggerOptions">
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.schedule' />
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input id="scheduleDaily" type="radio" name="schedule" value="Daily"  onChange="javascript:show('Daily');" <%= ((schedule.equals("Daily")) || (schedule.equals(""))) ? "checked" : "" %> />&nbsp; <s:text name="lbl.daily"/>
										<input id="scheduleDaily" type="radio" name="schedule" value="Weekly" onChange="javascript:show('Weekly');" <%= schedule.equals("Weekly") ? "checked" : "" %> />&nbsp; <s:text name="lbl.weekly"/>
										<input id="scheduleDaily" type="radio" name="schedule" value="Monthly" onChange="javascript:show('Monthly');" <%= schedule.equals("Monthly") ? "checked" : "" %>/>&nbsp; <s:text name="lbl.monthly"/>
									</div>
								</div>
								
								
								<div class="clearfix">
										<div  id='Daily' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
										<div><s:text name="lbl.every"/> &nbsp;&nbsp;&nbsp;&nbsp;	<s:text name="lbl.hours"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:text name="lbl.minutes"/></div>
										<div class="dailyInnerDiv">
											<s:text name="lbl.At"/> &nbsp;&nbsp;
											<input type="checkbox" id="daily_every" name="daily_every" onChange="javascript:show('Daily');" <%= dailyEvery %>>
											&nbsp;&nbsp;
						                    <select id="daily_hour" name="daily_hour" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
						                         <option value="*">*</option>
						                         <% 
						                         	for (int i = 1; i < 24; i++) {
						                         		String selectedStr = "";
						                         		if(!StringUtils.isEmpty(dailyHour) && dailyHour.equals("" + i)) {
						                         			selectedStr = "selected";
						                         		}
						                         %>
						                         	<option value="<%= i %>" <%= selectedStr %>> <%= i %> </option>
						                         <% } %>
						                   	</select>
						                     &nbsp;	&nbsp;
										    <select id="daily_minute" name="daily_minute" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
												<option value="*">*</option>            
											<% 
												for (int i = 1; i < 60; i++) { 
					                         		String selectedStr = "";
					                         		if(!StringUtils.isEmpty(dailyMinute) && dailyMinute.equals("" + i)) {
					                         			selectedStr = "selected";
					                         		}
											%>
												<option value="<%= i %>" <%= selectedStr %>><%= i %></option>
											<% } %>
											</select>
					                    </div>
									</div>
									<div id='Weekly' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
					   					<select id="weekly_week" name="weekly_week" multiple onChange="javascript:show('Weekly');" class="schedulerDay alignVertical">
									   		<%
									   			String defaultSelectedStr = "";
									   			if(StringUtils.isEmpty(weeklyWeek)) {
									             	defaultSelectedStr = "selected";
									           	}
									   		%>
									   			<option value="*" <%= defaultSelectedStr %>>*</option>
									   		<%
									   			for(int i = 1; i < weekDays.length; i++){
									   				String selectedStr = "";
									   				if(!StringUtils.isEmpty(weeklyWeek) && weeklyWeek.equals("" + i)) {
					                     				selectedStr = "selected";
					                     			}
											%>
												<option value="<%= i %>" <%= selectedStr %>><%= weekDays[i] %></option>
					                        <%  
					                        	}
									   		%>
					                    </select>
					                    
					                    &nbsp; <s:text name="lbl.smal.at"/> &nbsp;
					                    <select id="weekly_hours" name="weekly_hours" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
					                        <option value="*">*</option>
						                    <% 
						                    	for (int i = 1; i < 24; i++) {
						                    		String selectedStr = "";
						                     		if(!StringUtils.isEmpty(weeklyHour) && weeklyHour.equals("" + i)) {
						                     			selectedStr = "selected";
						                     		}
						                    %>
						                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
						                    <% } %>
					                    </select>&nbsp;<s:text name="lbl.hour"/>&nbsp;
					                    
					                  	<select id="weekly_minute" name="weekly_minute" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
						                  <option value="*">*</option>            
						                  <% 
						                  		for (int i = 1; i < 60; i++) { 
						                    		String selectedStr = "";
						                     		if(!StringUtils.isEmpty(weeklyMinute) && weeklyMinute.equals("" + i)) {
						                     			selectedStr = "selected";
						                     		}
						                  %>
						                      <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
						                  <% } %>
					                   	</select>&nbsp;<s:text name="lbl.minute"/>
					                                                
									</div>
									<div id='Monthly' style="text-align: center; margin-bottom: 5px;"> <!-- class="schedulerWidth" -->
					                   <s:text name="lbl.every"/>
					                   <select id="monthly_day" name="monthly_day" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
					                           <option value="*">*</option>
					                       <% 
					                       		for (int i = 1; i <= 31; i++) { 
						                    		String selectedStr = "";
						                     		if(!StringUtils.isEmpty(monthlyDay) && monthlyDay.equals("" + i)) {
						                     			selectedStr = "selected";
						                     		}
					                       	%>
					                           <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
					                       <% } %>
					                   </select>&nbsp;<s:text name="lbl.of"/>&nbsp;
					                   
					                  	<select id="monthly_month" name="monthly_month" multiple onChange="javascript:show('Monthly');" class="schedulerDay alignVertical">
											<%
									   			defaultSelectedStr = "";
									   			if(StringUtils.isEmpty(monthlyMonth)) {
									             	defaultSelectedStr = "selected";
									           	}
									   		%>
									   			<option value="*" <%= defaultSelectedStr %>>*</option>
									   		<%
									   			for(int i = 1; i < months.length; i++){
									   				String selectedStr = "";
									   				if(!StringUtils.isEmpty(monthlyMonth) && monthlyMonth.equals("" + i)) {
					                     				selectedStr = "selected";
					                     			}
											%>
												<option value="<%= i %>" <%= selectedStr %>><%= months[i] %></option>
					                        <%  
					                        	}
									   		%>
					                    </select>
					                    
										&nbsp; <s:text name="lbl.smal.at"/> &nbsp;
					                    <select id="monthly_hour" name="monthly_hour" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
					                        <option value="*">*</option>
					                    <% 
					                    	for (int i = 1; i < 24; i++) { 
					                    		String selectedStr = "";
					                     		if(!StringUtils.isEmpty(monthlyHour) && monthlyHour.equals("" + i)) {
					                     			selectedStr = "selected";
					                     		}
					                    %>
					                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
					                    <% } %>
					                    </select>&nbsp;<s:text name="lbl.hour"/>
					                    &nbsp;
					                    <select id="monthly_minute" name="monthly_minute" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
					                        <option value="*">*</option>
					                    <% 
					                    	for (int i = 1; i < 60; i++) { 
					                    		String selectedStr = "";
					                     		if(!StringUtils.isEmpty(monthlyMinute) && monthlyMinute.equals("" + i)) {
					                     			selectedStr = "selected";
					                     		}
					                    %>
					                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
					                    <% } %>
					                    </select> &nbsp; <s:text name="lbl.minute"/>
					                                                
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.cron.expression' />
									</label>
									<div class="controls" id="cronValidation">
									</div>
								</div>
							</div>
								<!-- Down stream projects specification -->
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.downstream.projects' />
									</label>
									<div class="controls">
										<select id="downstreamProject" name="downstreamProject" class="input-xlarge">
											<option value="">-- Select DownStream --</option>
											<%
											if (CollectionUtils.isNotEmpty(existingJobsNames)) {
												for (Properties existingJobsName : existingJobsNames) {
													Enumeration em = existingJobsName.keys();
													 while (em.hasMoreElements()) {
														 String key = (String) em.nextElement();
														 String appName = (String) existingJobsName.get(key);
														 if(existingJob != null && existingJob.getName().equals(key)) { 
 															continue;
														 }
														 %>
														 <option value="<%= key %>"><%= key %> - (<%= appName %>)</option> 
													<% }
												}
											}
											%>
										</select>
									</div>
								</div>
								
								<!-- Criteria to run the down stream projects -->
								<div class="control-group" id="downstreamControlGroup">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.downstream.criteria' />
									</label>
									<div class="controls">
										<select id="downstreamCriteria" name="downstreamCriteria" class="input-xlarge">
											<option value="SUCCESS">Trigger only if build succeeds</option>
											<option value="UNSTABLE">Trigger even if the build is unstable</option>
											<option value="FAILURE">Trigger even if the build fails</option>
										</select>
									</div>
								</div>
								
								<!-- clone this workspace -->
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.clone.workspace' />
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input type="radio" name="cloneWorkspace" value="true" />&nbsp; <s:text name="lbl.yes"/>
										<input type="radio" name="cloneWorkspace" value="false" checked/>&nbsp; <s:text name="lbl.no"/>
									</div>
								</div>
											
				            </section>
	                    </div>
	                </div>
	            </section>  
	        </div>
	    </section>
	</div>
		
	<!-- CI basic settings starts -->
	<div class="theme_accordion_container clearfix" style="float: none;">
	    <section class="accordion_panel_wid">
	        <div class="accordion_panel_inner adv-settings-accoridan-inner">
	            <section class="lft_menus_container adv-settings-width">
	                <span class="siteaccordion" id="siteaccordion_active">
	                	<div>
		                	<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
		                	<s:text name="lbl.build.config"/>
	                	</div>
	                </span>
	                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
	                    <div class="scrollpanel adv_setting_accordian_bottom">
	                        <section class="scrollpanel_inner">
	                        <!--  Ci job configuration starts -->
								<!-- Ci operation -->
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.ci.operation' />
									</label>
									<div class="controls">
										<select id="operation" name="operation" class="input-xlarge">
										<%
											if (optionIds.contains(FrameworkConstants.BUILD_KEY)) { %>
									        	<option value="build">Build</option>
									    <% 
									    	}
											if (optionIds.contains(FrameworkConstants.DEPLOY_KEY)) {%>
									        	<option value="deploy">Deploy</option>
									    <%
									    	} 
											if (optionIds.contains(FrameworkConstants.FUNCTIONAL_TEST_KEY)) {%>
									        	<option value="functionalTest">Functional Test</option>
									    <%
									    	}
								    		if (optionIds.contains(FrameworkConstants.UNIT_TEST_KEY)) {%>
									        	<option value="unittest">Unit Test</option>
									    <%
									    	} 
									    	if (optionIds.contains(FrameworkConstants.CODE_KEY)) {%>
									        	<option value="codeValidation">Code Validate</option>
									    <%
									    	} 
									    	if (optionIds.contains(FrameworkConstants.REPORT_KEY)) {%>
									        	<option value="pdfReport">PDF Report</option>
									    <%
									    	} 
									    	if (optionIds.contains(FrameworkConstants.LOAD_TEST_KEY)) {%>
									        	<option value="loadTest">Load Test</option>
									    <%
									    	} 
									    	if (optionIds.contains(FrameworkConstants.PERFORMANCE_TEST_KEY)) {%>
									        	<option value="performanceTest">Performance Test</option>
									    <%
									    	}
									    	if (optionIds.contains(FrameworkConstants.COMPONENT_TEST_KEY)) {%>
									    	<option value="componentTest">Component Test</option>
									    <%
									    	}
									    %>
										</select>
									</div>
								</div>
								<!-- loading dynamic build, deploy and functional test popup pages -->
								<div class="clearfix" id="dynamicConfigLoad">
								</div>
								
							<!--  Ci job configuration ends -->
	                        </section>
	                    </div>
	                </div>
	            </section>  
	        </div>
	    </section>
	</div>
	<!-- CI basic settings ends -->
		
	<!-- build release plugin changes starts -->
	
	<!-- build Collabnet release plugin changes starts -->
	<div class="theme_accordion_container clearfix" style="float: none;" id="collabnetContainer">
	    <section class="accordion_panel_wid">
	        <div class="accordion_panel_inner adv-settings-accoridan-inner">
	            <section class="lft_menus_container adv-settings-width">
	                <span class="siteaccordion" id="siteaccordion_active">
	                	<div>
		                	<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
		                	<s:text name="lbl.build.uploader.config"/>
	                	</div>
	                </span>
	                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
	                    <div class="scrollpanel adv_setting_accordian_bottom">
	                        <section class="scrollpanel_inner">
	
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.enable.build.release' />
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input type="radio" name="enableBuildRelease" value="true" />&nbsp; <s:text name="lbl.yes"/>
										<input type="radio" name="enableBuildRelease" value="false" checked />&nbsp; <s:text name="lbl.no"/>
									</div>
								</div>
								
								<fieldset class="popup-fieldset fieldsetBottom perFieldSet" style="text-align: left;" id="collabNetInfo">
									<legend class="fieldSetLegend"><s:text name="lbl.build.release"/></legend>
									
									<div id="CollabNetConfig">
									
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span>  <s:text name='lbl.build.release.url' />
											</label>
											<div class="controls">
												<input type="text" id="collabNetURL" class="input-xlarge" name="collabNetURL" value="<%= existingJob == null ? "" : existingJob.getCollabNetURL() %>">
											</div>
										</div>
							
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span>  <s:text name='lbl.build.release.username' />
											</label>
											<div class="controls">
												<input type="text" id="collabNetusername" name="collabNetusername" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetusername() %>">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span>  <s:text name='lbl.build.release.password' />
											</label>
											<div class="controls">
												<input type="password" id="collabNetpassword" name="collabNetpassword" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetpassword() %>">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span>  <s:text name='lbl.build.release.project' />
											</label>
											<div class="controls">
												<input type="text" id="collabNetProject" name="collabNetProject" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetProject() %>">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span>  <s:text name='lbl.build.release.package' />
											</label>
											<div class="controls">
												<input type="text" id="collabNetPackage" name="collabNetPackage" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetPackage() %>">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<span class="red">* </span> <s:text name='lbl.build.release.release.name' />
											</label>
											<div class="controls">
												<input type="text" id="collabNetRelease" name="collabNetRelease" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getCollabNetRelease() %>">
											</div>
										</div>
										
<!-- 										<div class="control-group"> -->
<!-- 											<label class="control-label labelbold popupLbl"> -->
<%-- 												<span class="red">* </span> <s:text name='lbl.build.release.file.pattern' /> --%>
<!-- 											</label> -->
<!-- 											<div class="controls"> -->
<%-- 												<input type="text" id="collabNetFileReleasePattern" name="collabNetFileReleasePattern" class="input-xlarge" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "do_not_checkin/build/*.zip" : existingJob.getCollabNetFileReleasePattern() %>"> --%>
<!-- 											</div> -->
<!-- 										</div> -->
										
										<div class="control-group">
											<label class="control-label labelbold popupLbl">
												<s:text name='lbl.build.release.overwrite' />
											</label>
											<div class="controls" style="padding-top: 5px;">
												<input type="radio" name="collabNetoverWriteFiles" value="true" checked />&nbsp; <s:text name="lbl.yes"/>
												<input type="radio" name="collabNetoverWriteFiles" value="false" />&nbsp; <s:text name="lbl.no"/>
											</div>
										</div>
								
									</div>
									
								</fieldset>
								<!-- build Collabnet release plugin changes ends -->
								
								<!-- build Confluence release plugin changes starts -->
							
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.enable.confluence.release' /> 
									</label>
									<div class="controls" style="padding-top: 5px;">
										<input type="radio" name="enableConfluence" value="true" />&nbsp; <s:text name="lbl.yes"/>
										<input type="radio" name="enableConfluence" value="false" checked />&nbsp; <s:text name="lbl.no"/>
									</div>
								</div>
								
								<fieldset class="popup-fieldset fieldsetBottom perFieldSet" style="text-align: left;" id="confluenceInfo">
									<legend class="fieldSetLegend"><s:text name="lbl.confluence.release"/></legend>
										<div id = "confluenceConfig">
										
											<div class="control-group">
												<label class="control-label labelbold popupLbl">
													<span class="red">* </span>  <s:text name='lbl.confluence.site' />
												</label>
												<div class="controls">
													<select id="confluenceSite" name="confluenceSite" class="input-xlarge">
													<%
														if (CollectionUtils.isNotEmpty(confluenceSites)) { 
															for (String confluenceSite : confluenceSites) { 
												 	%> 
																	 <option value="<%= confluenceSite %>"><%= confluenceSite %> </option> 
													<% 
															} 
														} 
													%> 
													</select>
												</div>
											</div>
											
											<div class="control-group">
												<div class="controls">
													<input type="checkbox" id="confluencePublish"  name="confluencePublish" value="<%= existingJob == null ? "" : existingJob.isConfluencePublish() %>">
													<s:text name='lbl.confluence.publish.even.build.fails' />
												</div>
											</div>
											
											<div class="control-group">
												<label class="control-label labelbold popupLbl">
													<span class="red">* </span>  <s:text name='lbl.confluence.space' />
												</label>
												<div class="controls">
													<input type="text" id="confluenceSpace" class="input-xlarge" name="confluenceSpace" value="<%= existingJob == null ? "" : existingJob.getConfluenceSpace() %>">
												</div>
											</div>
											
											<div class="control-group">
												<label class="control-label labelbold popupLbl">
													<span class="red">* </span>  <s:text name='lbl.confluence.page' />
												</label>
												<div class="controls">
													<input type="text" id="confluencePage" class="input-xlarge" name="confluencePage" value="<%= existingJob == null ? "" : existingJob.getConfluencePage() %>">
												</div>
											</div>
											
											<div class="control-group">
												<label class="control-label labelbold popupLbl">
													 <s:text name='lbl.confluence.artifacts' />
												</label>
												<div class="controls">
													<input type="checkbox" id="confluenceArtifacts"  name="confluenceArtifacts" value="<%= existingJob == null ? "" : existingJob.isConfluenceArtifacts() %>">
													<s:text name='lbl.confluence.attach.archived.artifacts' />
												</div>
											</div>
											
											<div class="control-group">
												<label class="control-label labelbold popupLbl">
													 <s:text name='lbl.confluence.other.files.attach' />
												</label>
												<div class="controls">
													<input type="text" id="confluenceOther" class="input-xlarge" name="confluenceOther" value="<%= existingJob == null ? "" : existingJob.getConfluenceOther() %>">
												</div>
											</div>
											
										</div>
									</fieldset>
								<!-- build Confluence release plugin changes ends -->
	                        </section>
	                    </div>
	                </div>
	            </section>  
	        </div>
	    </section>
	</div>
    <!-- build release plugin changes ends -->
	<input type="hidden" name="oldJobName" value="<%= existingJob == null ? "" : existingJob.getName()%>" >
	<input type="hidden" name="isFromCi" value="isFromCi">
</form>

<script type="text/javascript">
	var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	loadSchedule(selectedSchedule);
	
	$(document).ready(function() {
		//
		urlBasedAction();

		$('.siteaccordion').unbind('click');
		accordionOperation();
		
		credentialsDisp();
		$("#name").focus();
		
		$('#confluencePublish').click(function() {
			changeChckBoxValue(this);
		});
		
		$('#confluenceArtifacts').click(function() {
			changeChckBoxValue(this);
		});
		
		
		$("#successEmail").click(function() {
			if($(this).is(":checked")) {
				$("#successEmailId").attr("disabled", false);
			} else {
				$("#successEmailId").attr("disabled", true);
				$("#successEmailId").val('');
	   		}
		});
		
		$("#failureEmail").click(function() {
			if($(this).is(":checked")) {
				$("#failureEmailId").attr("disabled", false);
			} else {
				$("#failureEmailId").attr("disabled", true);
				$("#failureEmailId").val('');
	   		}
		});
		
		$("input:radio[name=schedule]").click(function() {
		    var selectedSchedule = $(this).val();
		    loadSchedule(selectedSchedule);
		});
		
		$("input:radio[name=svnType]").click(function() {
			credentialsDisp();
		});
		
		show(selectedSchedule);
		
		<% 
			if(existingJob != null) {
				if (existingJob.getTriggers() != null) {
					for(String trigger : existingJob.getTriggers()) {
		%>
						$("input[value='<%= trigger%>']").prop("checked", true);
		<%
					}
				}
		%>
			//based on svn type radio button have to be selected
		<%
				String repoType = "svn";
				if (StringUtils.isNotEmpty(existingJob.getRepoType())) {
					repoType = existingJob.getRepoType();
				}
		%>
				$("input:radio[name=svnType][value=<%= existingJob.getRepoType() %>]").prop("checked", true);
		
				$("input:text[name=branch]").val("<%= existingJob.getBranch() %>");
				$("input:text[name=username]").val("<%= existingJob.getUserName() %>");
				$("input[name=password]").val("<%= existingJob.getPassword() %>");
				
				// when the job is not null, have to make selection in radio buttons of collabnet plugin
				$('input:radio[name=enableBuildRelease]').filter("[value='"+<%= existingJob.isEnableBuildRelease() %>+"']").attr("checked", true);
				$('input:radio[name=enableConfluence]').filter("[value='"+<%= existingJob.isEnableConfluence() %>+"']").attr("checked", true);
				$('input:radio[name=collabNetoverWriteFiles]').filter("[value='"+<%= existingJob.isCollabNetoverWriteFiles() %>+"']").attr("checked", true);
				$("select[name=usedClonnedWorkspace] option[value='<%= existingJob.getUsedClonnedWorkspace() %>']").attr('selected', 'selected');
				$("#downstreamProject option[value='<%= existingJob.getDownStreamProject() %>']").attr('selected', 'selected');
				$("#downstreamCriteria option[value='<%= existingJob.getDownStreamCriteria() %>']").attr('selected', 'selected');
				$("#operation option[value='<%= existingJob.getOperation() %>']").attr('selected', 'selected');
				//clone the workspace
				$("input:radio[name=cloneWorkspace][value=<%= existingJob.isCloneWorkspace() %>]").prop("checked", true);
		<%
			}
		%>
	    
		$("#svnurl").blur(function(event) {
      		urlBasedAction();
		});
		
		// show hide downstream project based on the criteria
		
		credentialsDisp();
		
		checkTrigger();
		
		$('#buildPeriodically').click(function() {
			checkTrigger();
		});
		$('#pollSCM').click(function() {
			checkTrigger();
		});
		
		$('input:radio[name=enableBuildRelease]').click(function() {
			enableDisableCollabNet();
			ciConfigureError('errMsg', "");
		});
		
		$('input:radio[name=enableConfluence]').click(function() {
			enableDisableConfluence();
			ciConfigureError('errMsg', "");
		});
		
		// Automation implementation - ci config - based on technology show hide information
		$('#operation').change(function() {
			showConfigBasedOnTech();
		});
		
// 		enableDisableCollabNet();
		//to hide/show collabnet based on operation
		showHideCollabnetAccordion();
		
		$('#operation').change(function() {
			showHideCollabnetAccordion();
		});
		
		//hide downstream criteria if nothin is selected
		showHideDownStreamCriteria();
		
		$('#downstreamProject').change(function() {
			showHideDownStreamCriteria();
		});
		
		enableDisableMailId();
		showConfigBasedOnTech();
		
		enableDisableCheckBoxBasedOnValue();
			
		selectSiteValue();
		
	});
	
	function selectSiteValue() {
		$('#confluenceSite').val('<%= existingJob == null ? "" : existingJob.getConfluenceSite() %>');
	}
	
	function enableDisableCheckBoxBasedOnValue() {
		$('input:checkbox[value="' + true + '"]').attr('checked', true);
		$('input:checkbox[value="' + false + '"]').attr('checked', false);
	}
	
	function enableDisableMailId() {
		if ($("#successEmail").is(":checked")) {
			$("#successEmailId").attr("disabled", false);
		} else {
			$("#successEmailId").attr("disabled", true);
		}
		
		if ($("#failureEmail").is(":checked")) {
			$("#failureEmailId").attr("disabled", false);
		} else {
			$("#failureEmailId").attr("disabled", true);
		}
	}
	
	function showHideDownStreamCriteria() {
		if ($('#downstreamProject').val() == "") {
			$('#downstreamControlGroup').hide();
		} else {
			$('#downstreamControlGroup').show();
		}
	}
	
	function showHideCollabnetAccordion() {
		if ($("#operation").val() == 'build' || $("#operation").val() == 'pdfReport') {
			$("#collabnetContainer").show();
		} else {
			$("#collabnetContainer").hide();
			$('input:radio[name=enableBuildRelease]').filter("[value='false']").attr("checked", true);
		}
		enableDisableCollabNet();
		enableDisableConfluence();
	}

	function showConfigBasedOnTech() {
		var params = getBasicParams();
		params = params.concat("&");
		params = params.concat("operation=");
	    params = params.concat($("#operation").val());
		loadContent('getCiDynamParam','', $('#dynamicConfigLoad'), params, false, true);
	}
	
	// after validation success, show loading icon and creates job
	function configureJob(url, operation) {
		isCiRefresh = true;
		var isFromCi = $('#isFromCI').val();		
// 		getCurrentCSS();	
// 		$('.popupLoadingIcon').css("display","block");
// 		var url = $("#configureForm").attr("action");
		$('#configureForm :input').attr('disabled', false);
		var callLoadContent = true;
		if (isFromCi) {			
			var redirct;
			// ci specification need to be specified
			if ('performanceTest' == operation) {
				redirct = mandatoryValidation('runPerformanceTest', $("#generateBuildForm"), '', 'performance-test', 'performance-test');
			} else if ('loadTest' == operation) {
				redirct = mandatoryValidation('runLoadTest', $("#generateBuildForm"), '', 'load-test', 'load-test');
			}
			
			//validation of templates in performance test .
			if(redirct == false) {
				callLoadContent = false;
			} else {
				callLoadContent = true;
			}						
		}
		
		// Saving the Job
		if(callLoadContent) { 			
 			// show popup loading icon
 			showPopuploadingIcon();
 			loadContent(url, $('#configureForm, #generateBuildForm'), $('#subcontainer'), getBasicParams(), false, true);
 			disableCiFormControls();
 		}
	}
	
	function disableCiFormControls() {
		//To disable controls in ci configure popup
		$("#configureForm :input").attr("disabled", true);
		$("#showPattern").removeAttr("onclick");
		$("#configureForm :input[type=button]").removeClass("btn-primary");
		$("#configureForm").find("img").removeAttr("onclick");
	}
	
	function enableDisableCollabNet() {
		if($('input:radio[name=enableBuildRelease]:checked').val() == "true") {
			$('#collabNetInfo').show();
			$('input:text[name=collabNetURL]').focus();
		} else {
			$('#collabNetInfo').hide();
			//when user selects no resets all the value
			$('input:text[name=collabNetURL], input:text[name=collabNetusername], input:password[name=collabNetpassword]').val('');
			$('input:text[name=collabNetProject], input:text[name=collabNetPackage], input:text[name=collabNetRelease]').val('');
		}
		
	}
	
	function enableDisableConfluence() {
		if($('input:radio[name=enableConfluence]:checked').val() == "true") {
			$('#confluenceInfo').show();
			$('select[name=confluenceSite]').focus();
		} else {
			$('#confluenceInfo').hide();
			//when user selects no resets all the value
			$('select[name=confluenceSite]').val('');
			$('input:text[name=confluenceSpace],input:text[name=confluenceOther], input:text[name=confluencePage]').val('');
			$('input:checkbox[name=confluencePublish], input:checkbox[name=confluenceArtifacts]').attr('checked', false);
		}
		
	}
	
	// when the configure popup is clicked on save button, it should validate mandatory fields before submitting form
	function configureJobValidation() {
		var name= $("#name").val();
		var svnurl= $("#svnurl").val().trim();
		$("#svnurl").val(svnurl);
		var username= $("#username").val();
		var password= $("#password").val();
		var senderEmailId = $("#senderEmailId").val();
		if(isBlank(name)) {
			$("#errMsg").html("Enter the Name");
			$("#name").focus();
			$("#name").val("");
			console.log("Name is not specified ");
			return false;
		} 
		
		if(!isBlank(senderEmailId)) {
			if(isValidEmail(senderEmailId)) {
				$("#errMsg").html("Enter Valid Email");
				$("#senderEmailId").focus();
				console.log("Email is not valid ");
				return false;
			}
		}
		
		if($("input:radio[name=svnType][value='svn']").is(':checked')) {
			
			if (isBlank(svnurl)) {
				$("#errMsg").html("Enter the URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				console.log("url is missing");
				return false;
			} 
			
			if(!isBlank(svnurl) && isValidUrl(svnurl)){
				$("#errMsg").html("Enter valid URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				console.log("url is invalid");
				return false;
			}
			
			if(isBlank($.trim($("input[name= username]").val()))){
				$("#errMsg").html("Enter UserName");
				$("#username").focus();
				$("#username").val("");
				console.log("usename is not specified ");
				return false;
			}
			if(isBlank(password)) {
				$("#errMsg").html("Enter Password");
				$("#password").focus();
				console.log("password is not specified ");
				return false;
			}
		} 
		
		if($("input:radio[name=svnType][value='git']").is(':checked')) {
			if(isValidUrl(svnurl)) {
				$("#errMsg").html("Enter the URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				console.log("Url is not specified ");
				return false;
			}
			
			if(isBlank($.trim($("input[name=branch]").val()))){
				$("#errMsg").html("Enter Branch Name");
				$("#branch").focus();
				$("#branch").val("");
				console.log("branch is not specified ");
				return false;
			}
		} 
		
		if($("input:radio[name=svnType][value='clonedWorkspace']").is(':checked')) {
			if($("select[name=usedClonnedWorkspace] option").length <= 0){
				$("#errMsg").html("There is no parent project to configure");
				console.log("parent project is not choosed ");
				return false;
			}
		}
		
		ciConfigureError('errMsg', "");
		return true;
	}
	
	function collabNetValidation() {
		var collabNetUrl = $('input:text[name=collabNetURL]').val().trim();
		$('#collabNetURL').val(collabNetUrl);
		if (isBlank(collabNetUrl)) {
			ciConfigureError('errMsg', "URL is missing");
			$('input:text[name=collabNetURL]').focus();
			return false;
		} else if(isValidUrl(collabNetUrl)) {
			ciConfigureError('errMsg', "URL is invalid");
			$('input:text[name=collabNetURL]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetusername]').val())) {
			ciConfigureError('errMsg', "Username is missing");
			$('input:text[name=collabNetusername]').focus();
			return false;
		} else if (isBlank($('input:password[name=collabNetpassword]').val())) {
			ciConfigureError('errMsg', "Password is missing");
			$('input:password[name=collabNetpassword]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetProject]').val())) {
			ciConfigureError('errMsg', "Project is missing");
			$('input:text[name=collabNetProject]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetPackage]').val())) {
			ciConfigureError('errMsg', "Package is missing");
			$('input:text[name=collabNetPackage]').focus();
			return false;
		} else if (isBlank($('input:text[name=collabNetRelease]').val())) {
			ciConfigureError('errMsg', "Release is missing");
			$('input:text[name=collabNetRelease]').focus();
			return false;
		} else {
// 			ciConfigureError('errMsg', "");
			return true;
	 	}
	}
	
	function confluenceValidation(){
		if (isBlank($('input:text[name=confluenceSpace]').val())) {
			ciConfigureError('errMsg', "Space is missing");
			$('input:text[name=confluenceSpace]').focus();
			return false;
		} else if (isBlank($('input:text[name=confluencePage]').val())) {
			ciConfigureError('errMsg', "Page is missing");
			$('input:text[name=confluencePage]').focus();
			return false;
		} else {
			ciConfigureError('errMsg', "");
			return true;
	 	}
	}
	
	function credentialsDisp() {
		if($("input:radio[name=svnType][value='svn']").is(':checked')) {
			$('#usernameControl, #passwordControl, #urlControl').show();
			$('#branchControl, #parentProjectControl').hide();
		} else if($("input:radio[name=svnType][value='git']").is(':checked')) {
			$('#usernameControl, #passwordControl, #parentProjectControl').hide();
			$('#branchControl, #urlControl').show();
		}  else if($("input:radio[name=svnType][value='clonedWorkspace']").is(':checked')) {
			$('#usernameControl, #passwordControl, #branchControl, #urlControl').hide();
			$('#parentProjectControl').show();
		}
	}
	
	function ciConfigureError(id, errMsg) {
		$("#" + id ).empty();
		$("#" + id ).append(errMsg);
	}
    
	function loadSchedule(selectedSchedule) {
		hideAllSchedule();
		$("#" + selectedSchedule).show();	
	}
	
	function hideAllSchedule() {
		$("#Daily").hide();
		$("#Weekly").hide();
		$("#Monthly").hide();
	}
	
    function show(ids) {
        var buttonObj = window.document.getElementById("enableButton");

        if(ids == "Daily") {
            var dailyEveryObj = window.document.getElementById("daily_every");
            var dailyHourObj = window.document.getElementById("daily_hour");
            var hours = dailyHourObj.options[dailyHourObj.selectedIndex].value;
            var dailyMinuteObj = window.document.getElementById("daily_minute");
            var minutes = dailyMinuteObj.options[dailyMinuteObj.selectedIndex].value;
    		var params = "cronBy=";
			params = params.concat("Daily");
			params = params.concat("&hours=");
			params = params.concat(hours);
			params = params.concat("&minutes=");
			params = params.concat(minutes);
			params = params.concat("&every=");
			params = params.concat(dailyEveryObj.checked);
			cronValidationLoad(params);
        } else if(ids == "Weekly") {

            var weeklyHourObj = window.document.getElementById("weekly_hours");
            var hours = weeklyHourObj.options[weeklyHourObj.selectedIndex].value;

            var weeklyMinuteObj = window.document.getElementById("weekly_minute");
            var minutes = weeklyMinuteObj.options[weeklyMinuteObj.selectedIndex].value;

            var weekObj = window.document.getElementById("weekly_week");
            var week;
            var count = 0;
            
            if (weekObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < weekObj.options.length; i++){

                    if(weekObj.options[i].selected){
                        if (count == 0) {
                            week = weekObj.options[i].value;
                        } else {
                           week += "," + weekObj.options[i].value;
                        }

                        count++;
                    }
                }
	        	var params = "cronBy=";
				params = params.concat("Weekly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&week=");
				params = params.concat(week);
				cronValidationLoad(params);
        		
            }

      } else if(ids == "Monthly") {

            var monthlyDayObj = window.document.getElementById("monthly_day");
            var day = monthlyDayObj.options[monthlyDayObj.selectedIndex].value;

            var monthlyHourObj = window.document.getElementById("monthly_hour");
            var hours = monthlyHourObj.options[monthlyHourObj.selectedIndex].value;

            var monthlyMinuteObj = window.document.getElementById("monthly_minute");
            var minutes = monthlyMinuteObj.options[monthlyMinuteObj.selectedIndex].value;

            var monthObj = window.document.getElementById("monthly_month");
            var month;
            var count = 0;
            if (monthObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < monthObj.options.length; i++){

                    if(monthObj.options[i].selected){
                        if (count == 0) {
                            month = monthObj.options[i].value;
                        } else {
                           month += "," + monthObj.options[i].value;
                        }

                        count++;
                    }
                }
        		var params = "cronBy=";
				params = params.concat("Monthly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&month=");
				params = params.concat(month);
				params = params.concat("&day=");
				params = params.concat(day);
				cronValidationLoad(params);
            }

        }
    }
    
    function cronValidationLoad(additionalParams) {
		var params = getBasicParams();
		params = params.concat("&");
		params = params.concat(additionalParams);
		params = params.concat("&from=ci");
    	loadContent('cronValidation','', $('#cronValidation'), params, false, true);
    	
    }
    
    function checkTrigger() {
    	if (($('#buildPeriodically').is(':checked')) || ($('#pollSCM').is(':checked')) ) {
    		 $('#triggerOptions').show();
    	} else {
    		 $('#triggerOptions').hide();
    	}
    }

    function urlBasedAction() {
		var svnurl = $("input[name='svnurl']").val();
		if (svnurl.indexOf('insight.photoninfotech.com') != -1) {
 			$("#username").val("<%= LoginId %>"); 
			<%
				String encodedpassword = (String) session.getAttribute(FrameworkConstants.SESSION_USER_PASSWORD);
				String decryptedPass = new String(Base64.decodeBase64(encodedpassword.getBytes()));
			%>
			$("#password").val("<%= decryptedPass %>");
     	} 
   	}
    
</script>