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

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.collections.MapUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.BuildInfo"%>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<style type="text/css">
   	table th {
		padding: 0 0 0 9px;  
	}
	   	
	td {
	 	padding: 5px;
	 	text-align: left;
	}
	  
	th {
	 	padding: 0 5px;
	 	text-align: left;
	}
</style>

<%
    List<BuildInfo> buildInfos = (List<BuildInfo>) request.getAttribute(FrameworkConstants.REQ_BUILD);
    ApplicationInfo applicationInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	String appId = "";
	if (applicationInfo != null) {
		appId = applicationInfo.getId();
	}
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
    String appDirectory = "";
    if (applicationInfo != null) {
    	appDirectory = applicationInfo.getAppDirName(); 
    }
    Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
	
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	if (permissions != null && !permissions.canManageBuilds()) {
		per_disabledStr = "disabled";
	}
%>

<% if (CollectionUtils.isEmpty(buildInfos)) { %>
	<div class="alert alert-block" style="margin-top: 5px">
	    <center><s:text name='configuration.info.message'/></center>
	</div>
<% } else { %>
		<div class="build_table_div">
			<div class="fixed-table-container">
	      		<div class="header-background header-backgroundTheme"> </div>
	      		<div class="fixed-table-container-inner builds-list-table">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first">
				                	<div class="th-inner">
				                		<input type="checkbox" value="" <%= per_disabledStr %> id="checkAllAuto" name="checkAllAuto">
				                	</div>
				              	</th>
				              	<th class="second">
				                	<div class="th-inner">#</div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner"><s:text name="label.date"/></div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner"><s:text name="lbl.download"/></div>
				              	</th>
				              	<%
									if (optionIds != null && optionIds.contains(FrameworkConstants.DEPLOY_KEY)) {
								%>
				              	<th class="third" id="th_deploy">
				                	<div class="th-inner"><s:text name="label.deploy"></s:text></div>
				              	</th>
				              	<%
									}
				              	%>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          	<%
			        		for (BuildInfo buildInfo : buildInfos) {
						%>
			            	<tr>
			              		<td class="buildNo" width="17px">
			              			<input type="checkbox" <%= per_disabledStr %> class="check" name="build-number" value="<%= buildInfo.getBuildNo() %>">
			              		</td>
			              		<td><%= buildInfo.getBuildNo() %></td>
			              		<td class="buildName" style="width: 40%;">
	              					<label class="bldLable" title="Configured with <%= buildInfo.getEnvironments() %>"><%= buildInfo.getTimeStamp() %></label>
			              		</td>
			              		<td>
			              			<a href="<s:url action='downloadBuild'>
					          		     <s:param name="buildNumber"><%= buildInfo.getBuildNo() %></s:param>
					          		     <s:param name="appDirectory"><%= appDirectory %></s:param>
					          		     <s:param name="appId"><%= applicationInfo.getId() %></s:param>
										 <s:param name="customerId"><%= customerId %></s:param>
										<s:param name="projectId"><%= projectId %></s:param>
					          		     </s:url>"><img src="images/icons/download.png" title="<%= buildInfo.getBuildName()%>"/>
		                            </a>
		                            <%
										if (optionIds != null && optionIds.contains(FrameworkConstants.EXE_DOWNLOAD)) {
			                            	boolean createIpa = false;
			                            	boolean deviceDeploy = false;
		                            		createIpa = MapUtils.getBooleanValue(buildInfo.getOptions(), FrameworkConstants.CAN_CREATE_IPA);
		                            		deviceDeploy = MapUtils.getBooleanValue(buildInfo.getOptions(), FrameworkConstants.DEPLOY_TO_DEVICE);
			                            	if (createIpa && deviceDeploy)  {
									%>
												<a href="<s:url action='downloadBuildIpa'> 
							          		    <s:param name="buildNumber"><%= buildInfo.getBuildNo() %></s:param>
							          		    <s:param name="appDirectory"><%= appDirectory %></s:param>
							          		    <s:param name="appId"><%= applicationInfo.getId() %></s:param>
												<s:param name="customerId"><%= customerId %></s:param>
												<s:param name="projectId"><%= projectId %></s:param>
		                                        </s:url>"><img src="images/icons/downloadipa.jpg" title="ipa Download"/>
		                                    	</a>
									<% 
			                            	}
			                            }
									%>
									<%
 										if (optionIds != null && optionIds.contains(FrameworkConstants.PROCESS_BUILD)) {
									%>
											<a class="processBuild" href="#" additionalParam="buildNumber=<%= buildInfo.getBuildNo() %>&appDirectory=<%= appDirectory %>&from=processBuild">
												<img src="images/icons/commit_icon.png" title="Check-in build"/>
	                                    	</a>
									<% 
 			                            }
									%>
			              		</td>
			              		<%
									if (optionIds != null && optionIds.contains(FrameworkConstants.DEPLOY_KEY)) {
								%>
		              			<td>
		              				<% if (permissions != null && !permissions.canManageBuilds()) { %>
		              					<a href="#">
			              				 	<img src="images/icons/deploy_off.png" />
			              				</a>
	              					<% } else { %>
			              				<a class="deploy" additionalParam="from=deploy&buildNumber=<%= buildInfo.getBuildNo() %>">
			              				 	<img src="images/icons/deploy.png" />
			              				</a>
		              				<% } %>
			              		</td>
			              		<%
									}
				              	%>
			              	</tr>	
			            <%
							}
						%>	
			          	</tbody>
			        </table>
	      		</div>
    		</div>
		</div>
	<% } %>
	<input type="hidden" id="deployAddParam"> 

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
		$(".fixed-table-container-inner").scrollbars();
	}
	
	$(document).ready(function() {
		hideLoadingIcon();//To hide the loading icon
		
		$('.deploy').click(function() {
			checkForLock('<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
			var additionalParam = $(this).attr('additionalParam'); //additional params if any
			$("#deployAddParam").val(additionalParam);
    	});
		
		$('.processBuild').click(function() {
			var additionalParam = $(this).attr('additionalParam'); //additional params if any
			var params = getBasicParams();
			params = params.concat("&");
			params = params.concat(additionalParam);
			validateDynamicParam('showProcessBuild', '<s:text name="label.process.build"/>', 'processBuild','<s:text name="label.process.build"/>', '', '<%= Constants.PHASE_PROCESS_BUILD %>', true, additionalParam);
    	});
		$(".buildName").text(function(index) {
		    return textTrim($(this), 20);
	    });
	
	});
	
	// By default disable all Run buttons under builds
    $(".nodejs_startbtn").attr("class", "btn disabled");
    $(".nodejs_startbtn").attr("disabled", true);
</script>