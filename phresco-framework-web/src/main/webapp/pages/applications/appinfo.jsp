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

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>

<%@ page import="com.photon.phresco.commons.model.ArtifactGroupInfo"%>
<%@ page import="com.photon.phresco.commons.model.Technology"%>
<%@ page import="com.photon.phresco.commons.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.WebService"%>
<%@ page import="com.photon.phresco.commons.model.DownloadInfo"%>

<%
	String appId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	Technology technology = (Technology) session.getAttribute(FrameworkConstants.REQ_TECHNOLOGY);
	List<ApplicationInfo> pilotProjects = (List<ApplicationInfo>) session.getAttribute(FrameworkConstants.REQ_PILOT_PROJECTS);
	ProjectInfo projectInfo = (ProjectInfo) session.getAttribute(appId + FrameworkConstants.SESSION_APPINFO);
	List<WebService> webservices = (List<WebService>)request.getAttribute(FrameworkConstants.REQ_WEBSERVICES);
	String appDir = (String) request.getAttribute(FrameworkConstants.REQ_OLD_APPDIR);
	ApplicationInfo webLayerAppInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_WEB_LAYER_APPINFO);
	boolean hasServer = (Boolean) request.getAttribute(FrameworkConstants.REQ_TECH_HAS_SERVER);
	boolean hasDb = (Boolean) request.getAttribute(FrameworkConstants.REQ_TECH_HAS_DB);
	boolean hasWebservice = (Boolean) request.getAttribute(FrameworkConstants.REQ_TECH_HAS_WEBSERVICE);
	Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
	
	String id = "";
	String name = "";
	String code = "";
	String description = "";
	String version = "";
	String pilotInfo = "";
	String technologyId = "";
	String technologyVersion = "";
	String oldAppDirName = "";
	String embedAppId = "";
	String appTypeId = "";
	List<String> selectedWebservices = null;
	List<ArtifactGroupInfo> pilotServers = null;
	List<ArtifactGroupInfo> pilotDatabases = null;
	List<String> pilotWebservices = null;
	List<ArtifactGroupInfo> selectedServers = null;
	List<ArtifactGroupInfo> selectedDatabases = null;
	if (projectInfo != null) {
		ApplicationInfo selectedInfo = projectInfo.getAppInfos().get(0);
		technologyId = selectedInfo.getTechInfo().getId();
		technologyVersion = selectedInfo.getTechInfo().getVersion();
		id = selectedInfo.getId();
		name = selectedInfo.getName();
		code = selectedInfo.getCode();
		oldAppDirName = selectedInfo.getAppDirName();
		description = selectedInfo.getDescription();
		version = selectedInfo.getVersion();
		embedAppId = selectedInfo.getEmbedAppId();
		appTypeId = selectedInfo.getTechInfo().getAppTypeId();
		if (selectedInfo.getPilotInfo() != null) {
			pilotInfo = selectedInfo.getPilotInfo().getId();
		}
		
		if (CollectionUtils.isNotEmpty(selectedInfo.getSelectedWebservices())) {
			selectedWebservices = selectedInfo.getSelectedWebservices();
		}
		
		if (CollectionUtils.isNotEmpty(selectedInfo.getSelectedServers())) {
			selectedServers = selectedInfo.getSelectedServers();
		}
		
		if (CollectionUtils.isNotEmpty(selectedInfo.getSelectedDatabases())) {
			selectedDatabases = selectedInfo.getSelectedDatabases();
		}
	} else {
		oldAppDirName = appDir;
	}
%>

<!--  Form Starts -->
<form id="formAppInfo" autocomplete="off" class="form-horizontal app_add_form" autofocus="autofocus">
    <div class="appInfoScrollDiv">
              
		<!--  Name Starts -->
		<div class="control-group" id="nameControl">
		    <label class="accordion-control-label labelbold"><span class="red">*</span>&nbsp;<s:text name="lbl.name"/></label>
		    <div class="controls">
		        <input class="input-xlarge" id="name" name="name" maxlength="30" title="<s:text name="title.30.chars"/>"
		            type="text"  value ="<%= name %>" autofocus="autofocus" placeholder="<s:text name="label.name.placeholder"/>" />
		        <span class="help-inline" id="nameError"></span>
		    </div>
		</div>
		<!--  Name Ends -->
	
		<!--  Code Starts -->
		<div class="control-group" id="codeControl">
		    <label class="accordion-control-label labelbold"><span class="red">*</span>&nbsp;<s:text name='lbl.code'/></label>
		    <div class="controls">
				<input class="input-xlarge" id="code" name="code"
		            type="text" maxlength="30" value ="<%= StringUtils.isNotEmpty(code) ? code : "" %>" title="<s:text name="title.30.chars"/>" 
		            placeholder="<s:text name="place.hldr.app.edit.code"/>"/>
	            <span class="help-inline" id="codeError"></span>
		    </div>
		</div>
		<!--  Code Ends -->
	         
		<!--  AppDirectory Starts -->
		<div class="control-group" id="appDirControl">
		    <label class="accordion-control-label labelbold"><s:text name='lbl.AppDir'/></label>
		    <div class="controls">
				 <input class="input-xlarge" id="appDir" name="appDir" maxlength="30" title="<s:text name="title.30.chars"/>"
		            type="text"  value ="<%= oldAppDirName %>" autofocus="autofocus" placeholder="<s:text name="label.name.placeholder"/>" />
		        <span class="help-inline" id="appDirError">
		           
		        </span>
		    </div>
		</div>
		<!--  AppDirectory Ends -->
		               
		<!--  Description Starts -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='lbl.desc'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" maxlength="150" title="<s:text name="title.150.chars"/>" class="xlarge" 
		        	id="textarea" placeholder="<s:text name="label.description.placeholder"/>"
		        	name="description"><%= StringUtils.isNotEmpty(description) ? description : "" %></textarea>
		    </div>
		</div>
		<!--  Description Ends -->
		
		<!--  Version Starts -->
		<div class="control-group" id="versionControl">
		    <label class="accordion-control-label labelbold"><span class="red">*</span>&nbsp;<s:text name='lbl.version'/></label>
		    <div class="controls">
				<input class="input-xlarge" id="applicationVersion" placeholder="<s:text name="place.hldr.app.edit.version"/>"
					name="applicationVersion" maxlength="20" title="<s:text name="title.20.chars"/>"
					type="text"  value ="<%= StringUtils.isNotEmpty(version) ? version : "" %>"/>
					<span class="help-inline" id="applicationVersionError"></span>
		    </div>
		</div>
		<!--  Version Ends -->
		
		<!-- Technology version start -->
		<div class="control-group">
			<label class="accordion-control-label labelbold"><s:text name='lbl.technology'/></label>
			<div class="controls">
				<input type="text" class="input-xlarge" value="<%= technology.getName() %>" disabled="disabled"/>
				<input type="hidden" name="technology" value="<%= technologyId %>"/>
				<% if (technologyVersion != null && !technologyVersion.isEmpty()) {%>
					<input type="text" class="input-medium" value="<%= technologyVersion %>" disabled="disabled"/>
				<% } %>
				<input type="hidden" name="technologyVersion" value="<%= technologyVersion %>"/>
			</div>
		</div>
		<!-- Technology version ends -->
		
		<!-- pilot project start -->
		<% 
		  	if (CollectionUtils.isNotEmpty(pilotProjects)) {
		%>
			<div class="control-group">
				<label class="accordion-control-label labelbold"><s:text name='lbl.pilot.project'/></label>
				<div class="controls">
					<select class="input-xlarge appinfoTech" name="pilotProject" onchange="showPilotProjectInfo(this);">
						<option value="" selected disabled><s:text name='lbl.default.opt.select.pilot'/></option>
							<%
								for (ApplicationInfo appInfo : pilotProjects) {
									String selectedStr = "";
									if (appInfo.getId().equals(pilotInfo)) {
										selectedStr = "selected";
									}
		                    %>
	                   					<option value = "<%= appInfo.getId() %>" <%= selectedStr %>><%= appInfo.getName() %></option>
							<% 	 
								}
							%> 
					</select>
				</div>
			</div>
		<% 
			}
		%>
		<!-- pilot project ends -->
		
		<!-- Technology version start -->
		<%
			if (webLayerAppInfo != null) {
			   String checkedStr = "";
			   if (webLayerAppInfo.getId().equals(embedAppId)) {
			        checkedStr = "checked";
			   }
			   if (optionIds != null && optionIds.contains(FrameworkConstants.EMBED_APPLICATION)) {     
		%>
					<div class="control-group">
						<label class="accordion-control-label labelbold"><s:text name='lbl.embed'/></label>
						<div class="controls">
							<input type="checkbox" name="embedAppId" value="<%= webLayerAppInfo.getId() %>" <%= checkedStr %>/>
							<%= webLayerAppInfo.getName() %>
						</div>
					</div>
		<% 
			   }
			} 
		%>
		<!-- Technology version ends -->
		
		<!-- servers start -->
		<%
			if (hasServer) {
				String checkedServerStr = "";
			  	if (CollectionUtils.isNotEmpty(selectedServers)) {
					checkedServerStr = "checked";
			  	} 
		%> 
				<div class="theme_accordion_container" id="serverControl">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion closereg" id="serverLayerControl" onclick="accordionClick(this, $('input[value=serverLayer]'));">
									<span>
										<input type="checkbox" id="checkAll1" class="accordianChkBox" name="serverLayer" value="serverLayer" <%= checkedServerStr %>/>
										<a class="vAlignSub"><s:text name='lbl.servers'/></a>
									</span>
									<p id="serverError" class="accordion-error-txt errorNotification"></p>
								</span>
								
								<div class="mfbox siteinnertooltiptxt hideContent" id="technologyControl">
									<table class="table_for_downloadInfos table_borderForDownloadInfos" align="center">
										<thead class="header-background">
											<tr class="noBorder">
												<th class="noBorder"><s:text name='lbl.servers'/></th>
												<th class="noBorder"><s:text name='lbl.version'/></th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody id="propTempTbody">
											
										</tbody>
									</table>
								</div>
							</section>  
						</div>
					</section>
				</div>
		<%
			}
		%>
		<!-- servers ends -->
		
		<!-- databases start -->
		<%
			if (hasDb) {
				String checkedDatabaseStr = "";
				if (CollectionUtils.isNotEmpty(selectedDatabases)) {
					checkedDatabaseStr = "checked";
				}
		%>
				<div class="theme_accordion_container" id="databaseControl">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion closereg" id="databaseLayerControl" onclick="accordionClick(this, $('input[value=databaseLayer]'));">
									<span>
										<input type="checkbox" id="checkAll2" class="accordianChkBox" name="dbLayer" value="databaseLayer" <%= checkedDatabaseStr %>/>
										<a class="vAlignSub"><s:text name='lbl.database'/></a>
									</span>
									<p id="databaseError" class="accordion-error-txt errorNotification"></p> 
								</span>
								<div class="mfbox siteinnertooltiptxt hideContent">
									<table class="table_for_downloadInfos table_borderForDownloadInfos"  align="center">
											<thead class ="header-background">
												<tr >
													<th class="noBorder"><s:text name='lbl.database'/></th>
													<th class="noBorder"><s:text name='lbl.version'/></th>
													<th></th>
													<th></th>
												</tr>
											</thead>
											<tbody id="propTempTbodyDatabase">
												
											</tbody>
									</table>
								</div>
							</section>  
						</div>
					</section>
				</div>
		<%
			}
		%>
		<!-- databases ends -->
		
		<!-- webservice start -->
		<% 
			if(hasWebservice) {
				String checkedWebserviceStr = "";
				if (CollectionUtils.isNotEmpty(selectedWebservices)) {
					checkedWebserviceStr = "checked";
				}
		%>
				<div class="theme_accordion_container">
					<section class="accordion_panel_wid" >
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion closereg" id="webserviceLayerControl" onclick="accordionClick(this, $('input[value=webserviceLayer]'));">
									<span>
										<input type="checkbox" id="checkAll3" class="accordianChkBox" name="webserviceLayer" value="webserviceLayer" <%= checkedWebserviceStr %>/>
										<a class="vAlignSub"><s:text name='lbl.webservice'/></a>
									</span>
									<p id="webserviceError" class="accordion-error-txt errorNotification"></p> 
								</span>
								<div class="mfbox siteinnertooltiptxt hideContent">
									<div class="control-group autoWidthForWebservice">
							            <div class="controls typeFields">
							                <div class="multilist-scroller multiselct multiselectForWebservice">
								                <ul>
								                    <%
								                        if (CollectionUtils.isNotEmpty(webservices)) {
						   									for (WebService webservice : webservices) {
						   										String selectedStr= "";
						   										if (CollectionUtils.isNotEmpty(selectedWebservices)) {
						   											for (String string : selectedWebservices) {
						   												if (webservice.getId().equals(string)) {
						   													selectedStr = "checked";
						   												} 
						   											}
																}
								                    %>
									                   			<li>
																	<input type="checkbox" name="webservice" value="<%= webservice.getId() %>" <%= selectedStr%>
																		class="check techCheck"><%= webservice.getName() %>
																</li>
													<% 	 
															}
														}
													%> 
												</ul>
							                </div>
										</div>
							        </div>
							        <span class="help-inline applyerror"></span>
								</div>
							</section>  
						</div>
					</section>
				</div>
		<%
			}
		%>
		<!-- webservice ends -->
	                    
		<!--  Dependecies are loaded -->
		<div class="Create_project_inner" id="AjaxContainer"></div>
	</div>
	
    <!--  Submit and Cancel buttons Starts -->
    <div class="actions">
        <input id="next" type="button" value="<s:text name="label.next"/>" class="btn btn-primary" 
        	onclick="showFeaturesPage();"/>
        <input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
			onclick="loadContent('applications', $('#formCustomers'), $('#container'), '', '', true);">
    </div>
    <!--  Submit and Cancel buttons Ends -->
    
    <!-- Hidden Field -->
	<input type="hidden" name="appTypeId" value="<%= appTypeId %>"/>
	<input type="hidden" name="techId" value="<%= technologyId %>"/>
	<input type="hidden" name="oldAppDirName" value="<%= oldAppDirName %>"/>
	<input type="hidden" name="fromTab" value="appInfo">
	
</form> 
<!--  Form Ends -->
    
<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
		$(".appInfoScrollDiv").scrollbars();
		$(".multilistVersion-scroller").scrollbars();
	}
	
	inActivateAllMenu($("a[name='appTab']"));
	activateMenu($('#appInfo'));
	
	var serverCounter = 1;
	var databaseCounter = 1;

	$(document).ready(function() {
   		hideLoadingIcon();//To hide the loading icon
   		chkForServerCount();
   		chkForDBCount();
   		checkDownloadInfoForServer();
   		checkDownloadInfoForDatabase();
   		
		$("#name").focus();
        
        $("input[name='applicationType']").click(function() {
            changeApplication();
        });
        
        // To restrict the user in typing the special charaters
		$('#name').bind('input propertychange', function (e) {
        	var projNname = $(this).val();
        	projNname = checkForSplChr(projNname);
        	$(this).val(projNname);
		});
        
		// To restrict the user in typing the special charaters in projectCode and projectVersion
		$('#code, #applicationVersion').bind('input propertychange', function (e) {
			var str = $(this).val();
			str = checkForSplChrExceptDot(str);
			str = removeSpaces(str);
        	$(this).val(str);
		});
		
		// To restrict the user in typing the special charaters in app dir
		$('#appDir').bind('input propertychange', function (e) {
			var str = $(this).val();
			str = checkForSplChrExceptDot(str);
        	$(this).val(str);
		});
        
		<% if (projectInfo != null) { %>
			$("input[value='serverLayer']:checked").each(function() {
		    	accordionOpen('#serverLayerControl', $('input[value=serverLayer]'));
		    });
			
			$("input[value='databaseLayer']:checked").each(function() {
		    	accordionOpen('#databaseLayerControl', $('input[value=databaseLayer]'));
		    });
			
			$("input[value='webserviceLayer']:checked").each(function() {
		    	accordionOpen('#webserviceLayerControl', $('input[value=webserviceLayer]'));
		    });
		<% } %>
	
		window.setTimeout(function () { document.getElementById('name').focus(); }, 250);
	});
    
  //To show the validation error messages
	function findError(data) {
		hideLoadingIcon();
		if (!isBlank(data.nameError)) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
		
		if (!isBlank(data.codeError)) {
			showError($("#codeControl"), $("#codeError"), data.codeError);
		} else {
			hideError($("#codeControl"), $("#codeError"));
		}
		
		if (!isBlank(data.applicationVersionError)) {
			showError($("#versionControl"), $("#applicationVersionError"), data.applicationVersionError);
		} else {
			hideError($("#versionControl"), $("#applicationVersionError"));
		}
		
		if (!isBlank(data.appDirError)) {
			showError($("#appDirControl"), $("#appDirError"), data.appDirError);
		} else {
			hideError($("#appDirControl"), $("#appDirError"));
		}
        
        if(!isBlank(data.serverError)) {
			showError($("#" + $(this).attr("id")), $("#serverError"), data.serverError + " " + data.serverName);
		} else {
			hideError($("#serverLayerControl") , $("#serverError"));
		}
		
		if(!isBlank(data.databaseError)) {
			showError($("#" + $(this).attr("id")), $("#databaseError"), data.databaseError + " " + data.databaseName);
		} else {
			hideError($("#databaseLayerControl") , $("#databaseError"));
		}
		
		if(!isBlank(data.webServiceError)) {
			showError($("#" + $(this).attr("id")), $("#webserviceError"), data.webServiceError);
		} else {
			hideError($("#webserviceLayerControl") , $("#webserviceError"));
		}
       
	}
	
	/* function getPilotProject(obj) {
    	var piltProject = $(obj).val();
    	pilotProjectData(piltProject);
    } */
    
    function showPilotProjectInfo(obj) {
 		var piltProject = $(obj).val();
 		if (isBlank(piltProject)) {
 			deletePilots();
 		} else {
    <% 			
    		if (CollectionUtils.isNotEmpty(pilotProjects)) {	
    			for (ApplicationInfo appInfo : pilotProjects) {
   	%>
					if (piltProject == '<%= appInfo.getId()%>') {
    <% 					pilotServers = appInfo.getSelectedServers();
						pilotDatabases = appInfo.getSelectedDatabases();	
						pilotWebservices = appInfo.getSelectedWebservices();
	%>
						showPilotSelectedDownloadInfo();
					}
	<%
				}
 			}
    %>
 		}
    }
    
	function deletePilots() {
		<%-- <% if(pilotProjects != null) { %>
		$('select[name=server]').each(function () {
	  		var server = $(this).val();
	   		if(server == '<%=pilotServers.get(0).getArtifactGroupId()%>') {
			   	var serverId = $(this).attr("id");
			   	$('#'+serverId).closest('tr').remove();
			   	chkForServerCount();
		   }	
	    });
		var noOfRows = $('select[name=server]').size();
		if(noOfRows == 0) {
			addServer();
			$('#serverLayerControl').each(function () {
			document.getElementById("checkAll1").checked=false
		  	});
			accordionClose('#serverLayerControl', $('input[value=serverLayer]'));
		}
		
		$('select[name=database]').each(function () {
	  		var database = $(this).val();
	   		if(database == '<%=pilotDatabases.get(0).getArtifactGroupId()%>') {
			   	var databaseId = $(this).attr("id");
			   	$('#'+databaseId).closest('tr').remove();
			   	chkForDBCount();
		   }	
	    });
		var noOfDatabase = $('select[name=database]').size();
		if(noOfDatabase == 0) {
			addDatabase();
			$('#databaseLayerControl').each(function () {
			document.getElementById("checkAll2").checked=false
		  	});
			accordionClose('#databaseLayerControl', $('input[value=databaseLayer]'));
		} 
	
		selectDelselectWebService(false);
		$('#webserviceLayerControl').each(function () {
			document.getElementById("checkAll3").checked = false;
       	});
		accordionClose('#webserviceLayerControl', $('input[value=webserviceLayer]'));
		$('input[name=webservice]').each(function () {
	 		var webservice = $(this).val();
	 			if ($(this).attr("checked") == "checked") {
	 				$('#webserviceLayerControl').each(function () {
	 					document.getElementById("checkAll3").checked = true;
	 		       	});
	 				accordionOpen('#webserviceLayerControl', $('input[value=webserviceLayer]'));
	 			}
       	});
		<% } %> --%>
	}	
    
    function showPilotSelectedDownloadInfo() {
   		
 	<% 	if (pilotServers != null) {
			for(ArtifactGroupInfo artifactGrpInfo : pilotServers) {
				String server = artifactGrpInfo.getArtifactGroupId();
				List<String> serverVersions = artifactGrpInfo.getArtifactInfoIds();
	%>			
				var pilotsrver = '<%= server %>';
				$('select[name=server] option').each(function () {
			 		var server = $(this).val();
			 		if (pilotsrver === server) {
			 			accordionOpen('#serverLayerControl', $('input[value=serverLayer]'));
			 			$('#serverLayerControl').each(function () {
							document.getElementById("checkAll1").checked=true
			        	});
			 		}
			   	});
				
				addServer('<%= server %>', '<%= serverVersions %>', pilotsrver);
	<%
		    }
	    }
	%>
	
	<% 	if (pilotDatabases != null) {
			for(ArtifactGroupInfo artifactGrpInfo : pilotDatabases) {
				String database = artifactGrpInfo.getArtifactGroupId();
				List<String> databaseVersions = artifactGrpInfo.getArtifactInfoIds();
	%>
				var pilotDb = '<%= database %>';
				$('select[name=database] option').each(function () {
			 		var database = $(this).val();
			 		if (pilotDb === database) {
			 			accordionOpen('#databaseLayerControl', $('input[value=databaseLayer]'));
						$('#databaseLayerControl').each(function () {
							document.getElementById("checkAll2").checked=true
			        	});
			 		}
			   	});
				addDatabase('<%= database%>', '<%=databaseVersions%>', pilotDb);
	<%
		    }
    	}
	%>
	<% 
		if (pilotWebservices != null) {
	%>	
			accordionOpen('#webserviceLayerControl', $('input[value=webserviceLayer]'));

			$('#webserviceLayerControl').each(function () {
				document.getElementById("checkAll3").checked = true;
	       	});
			selectDelselectWebService(true);
	<%
		}
	%>
    }
    
    function selectDelselectWebService(checkedStr) {
    	<% 
			if (pilotWebservices != null) {
				for (String webservice : pilotWebservices) {
		%>
					$('input[name=webservice]').each(function () {
				 		var webservice = $(this).val();
				 		if ('<%= webservice %>' === webservice) {
				 			$(this).attr("checked", checkedStr);
				 		}
			       	});
		<%
				}
			}
		%>
    }
    
    function removeTag(currentTag) {
		$(currentTag).parent().parent().remove();
		chkForServerCount();
    	chkForDBCount();
	}
    
    function accordionOpen(thisObj, currentChkBoxObj) {
		var _tempIndex = $('.siteaccordion').index(thisObj);
		var isChecked = currentChkBoxObj;
		$(thisObj).removeClass('closereg').addClass('openreg');
		$(thisObj).next('.mfbox').eq(_tempIndex).slideDown(300,function() {});	
    } 
    
    function accordionClose(thisObj, currentChkBoxObj) {
    	var _tempIndex = $('.siteaccordion').index(thisObj);
    	var isChecked = currentChkBoxObj;
    	$(thisObj).removeClass('openreg').addClass('closereg');
    	$(thisObj).next('.mfbox').eq(_tempIndex).slideUp(300,function() {});	
    } 
    
    function getScrollBar(){
    	$(".multilistVersion-scroller").scrollbars();
    }
    
    function checkDownloadInfoForServer() {
    	<% if (selectedServers == null) { %> 
     		addServer();
       	<%} else { %> 
      		selectedServersFromProjectInfo();
     	 <% } %> 
    }
    
    function checkDownloadInfoForDatabase() {
    	<% if (selectedDatabases == null) { %> 
     		addDatabase();
       	<%} else { %> 
      		selectedDatabasesFromProjectInfo();
     	 <% } %> 
    }
    
    function selectedServersFromProjectInfo() {
		<% if (selectedServers != null) {
				for(ArtifactGroupInfo artifactGrpInfo : selectedServers) {
					String server = artifactGrpInfo.getArtifactGroupId();
					List<String> serverVersions = artifactGrpInfo.getArtifactInfoIds();
		%>
					addServer('<%= server%>', '<%=serverVersions%>');
		<%
			    }
		    }
		%>
	}
    
	function selectedDatabasesFromProjectInfo() {
		<% if (selectedDatabases != null) {
				for(ArtifactGroupInfo artifactGrpInfo : selectedDatabases) {
					String database = artifactGrpInfo.getArtifactGroupId();
					List<String> databaseVersions = artifactGrpInfo.getArtifactInfoIds();
		%>
					addDatabase('<%= database%>', '<%=databaseVersions%>');
		<%
			    }
		    }
		%>
	}
	
    function addServer(selectedServer, serverVersions, pilotsrver) {
    	
		var trId = serverCounter + "_serverdynamicadd";
		var servrName = serverCounter + "_serverName";
		var servrVerson = serverCounter + "_serverVersion";
		
		var newPropTempRow = $(document.createElement('tr')).attr("id", trId).attr("class", "noBorder");
		newPropTempRow.html("<td class='noBorder'><select class='input-medium' tempId='"+ serverCounter +"' id='"+ servrName +"'  name='server' onchange='getServerVersions(this);'><option>Select Server</option></select></td>" +
				"<td class='noBorder'><div class=' multilistVersion-scroller ' id='"+ servrVerson +"'></div>"+
				"<td class='noBorder'><a ><img class='add imagealign' " + 
		 			" temp='"+ servrName +"' src='images/icons/add_icon.png' onclick='addServer(this);'></a></td><td class='noBorder'><img id='deleteIcon' class = 'del imagealign'" + 
		 			"src='images/icons/minus_icon.png' onclick='removeTag(this);'></td>")
	 	newPropTempRow.appendTo("#propTempTbody");		
		if (pilotsrver != undefined) {
			var noOfServers = $('select[name=server]').size();
			var value = $('select[name=server]').val();
			var trId1 = (serverCounter-1) + "_serverdynamicadd";
			var selectVal = $('#'+trId1+' :selected').val();
			if (selectVal == undefined || isBlank(selectVal)) {
			 $('#'+trId1).closest('tr').remove();
			}
			if (value == pilotsrver) {
				$('#'+trId1).closest('tr').remove();
			}
		}
		serverCounter++;
		chkForServerCount();
		getScrollBar();
		getDownloadInfo('<%= DownloadInfo.Category.SERVER.name() %>', $("select[id='"+ servrName +"']"),'<s:text name='label.select.server'/>', selectedServer, serverVersions, servrName);
		
    }			
    
    function addDatabase(selectedDatabase, databaseVersions, pilotDb) {
		var trId = databaseCounter + "_databasedynamicadd";
		var databaseName = databaseCounter + "_databaseName";
		var databaseVerson = databaseCounter + "_databaseVersion";
		
		var newPropTempRow = $(document.createElement('tr')).attr("id", trId).attr("class", "noBorder");
		newPropTempRow.html("<td class='noBorder'><select class='input-medium' tempId='"+databaseCounter+"' id='"+ databaseName +"'  name='database' onchange='getDatabaseVersions(this);'><option>Select Database</option></select></td>" +
				"<td class='noBorder'><div class='multilistVersion-scroller ' id='"+ databaseVerson +"'></div>"+
				"<td class='noBorder'><a ><img class='add imagealign' " + 
		 			" temp='"+ databaseName +"' src='images/icons/add_icon.png' onclick='addDatabase(this);'></a></td><td class='noBorder'><img id='deleteImgIcon' class = 'del imagealign'" + 
		 			"src='images/icons/minus_icon.png' onclick='removeTag(this);'></td>")
	 	newPropTempRow.appendTo("#propTempTbodyDatabase");	
		if (pilotDb != undefined) {
			var noOfDatabase = $('select[name=database]').size();
			var value = $('select[name=database]').val();
			var trId1 = (databaseCounter-1) + "_databasedynamicadd";
			var selectVal = $('#'+trId1+' :selected').val();
			if (selectVal == undefined || isBlank(selectVal)) {
			 $('#'+trId1).closest('tr').remove();
			}			
			if (value == pilotDb) {
				$('#'+trId1).closest('tr').remove();
			}
		}
		databaseCounter++;
		chkForDBCount();
		getScrollBar();
		getDownloadInfo('<%= DownloadInfo.Category.DATABASE.name() %>', $("select[id='"+ databaseName +"']"), '<s:text name='label.select.db'/>', selectedDatabase, databaseVersions, databaseName);
    }
    
    function chkForServerCount() {
    	var noOfRows = $('select[name=server]').size();
		if(noOfRows > 1){
			$("#deleteIcon").show();
		}else{
			$("#deleteIcon").hide();
		}
    }  
    
    function chkForDBCount() {
		var noOfRows = $('select[name=database]').size();
		if(noOfRows > 1){
			$("#deleteImgIcon").show();
		}else{
			$("#deleteImgIcon").hide();
		}
    }
    
    var selectBoxobj;
    var selectedDb;
    var selectVer;
    function getDownloadInfo(type, toBeFilledCtrlObj, defaultOptTxt, selectedDatabase, databaseVersions, databaseName) {
    	showLoadingIcon();
    	selectedDb = selectedDatabase;
    	selectVer = databaseVersions;
    	selectBoxobj = toBeFilledCtrlObj;
		var params = getBasicParams();
		params = params.concat("&type=");
		params = params.concat(type);
		params = params.concat("&techId=");
		params = params.concat('<%= technologyId %>');
		params = params.concat("&selectedDownloadInfo=");
		params = params.concat(selectedDb);
		params = params.concat("&selectedDownloadInfoVersion=");
		params = params.concat(selectVer);
		params = params.concat("&selectBoxId=");
		params = params.concat(databaseName);
		params = params.concat("&defaultOptTxt=");
		params = params.concat(defaultOptTxt);
		loadContent("fetchDownloadInfos", '', '', params, true, true);
	}
    
    var map = {};
    var selectedData;
    var selectedDataVersion = new Array();
    var selectedDataId;
    var downloadInfoType;
    var defaultOption;
	//Success event functions
	function successEvent(pageUrl, data) {
		//To fill the servers/database for the selected 
		if (pageUrl == "fetchDownloadInfos") {
			hideLoadingIcon();
			selectBoxobj = $('#'+ data.selectBoxId);
			
			defaultOption = data.defaultOptTxt;
			selectBoxobj.empty();
			selectBoxobj.append($("<option value='' selected disabled></option>").text(defaultOption));
			
			downloadInfoType = data.downloadInfoType;
			
			selectedData = data.selectedDownloadInfo;
			selectedDataVersion = data.selectedDownloadInfoVersion;
			
			//To make server as selected if it is single
			if ($(data.downloadInfos).size() == 1) {
				selectBoxobj.append($("<option></option>").attr("value", data.downloadInfos[0].id).text(data.downloadInfos[0].name).attr("selected", "selected"));
				var versions = data.downloadInfos[0].artifactGroup.versions;
				map[data.downloadInfos[0].id] = versions;
				if (downloadInfoType === "DATABASE") {
					getDatabaseVersions(selectBoxobj);
				}
				if (downloadInfoType === "SERVER") {
					getServerVersions(selectBoxobj);
				}
			} else {
				for (i in data.downloadInfos) {
					fillOptions(selectBoxobj, data.downloadInfos[i].id, data.downloadInfos[i].name);
					var versions = data.downloadInfos[i].artifactGroup.versions;
					map[data.downloadInfos[i].id] = versions;
				}
			}
			if (selectedData != undefined && downloadInfoType==="DATABASE") {
				selectBoxobj.find('option').each(function() {
 					if (selectedData === $(this).val()) {
 						$(this).attr("selected", "selected");
 						getDatabaseVersions(selectBoxobj, selectedDataVersion);
 						return false;
 					}
 				});
			}
			if (selectedData != undefined && downloadInfoType==="SERVER") {
				selectBoxobj.find('option').each(function() {
 					if (selectedData === $(this).val()) {
 						$(this).attr("selected", "selected");
 						getServerVersions(selectBoxobj, selectedDataVersion);
 						return false;
 					}
 				});
			}
		}
	}
	
	function getServerVersions(obj, selectedDataVersion) {
		var id = $(obj).attr("tempId");
		var toObj = $("#" + id + "_serverVersion");
		getVersions($(obj), toObj, selectedDataVersion);
	}
	
	function getDatabaseVersions(obj, selectedDataVersion) {
		var id = $(obj).attr("tempId");
		var toObj = $("#" + id + "_databaseVersion");
		getVersions($(obj), toObj, selectedDataVersion);
	}
	
	//To get the versions of the selected server/Db
	function getVersions(obj, toBeFilledCtrlObj, selectedDataVersion) {
		toBeFilledCtrlObj.empty();
		var id = obj.val();
		var versions = map[id];
		//To make version as selected if it is single
		if ($(versions).size() == 1) { 
			fillVersions(toBeFilledCtrlObj, versions[0].id, versions[0].version, id, versions[0].id);
		} else {
			for (i in versions) {
				fillVersions(toBeFilledCtrlObj, versions[i].id, versions[i].version, id, selectedDataVersion);
			}
		}
	}
	
	function fillVersions(obj, value, text, parentValue, selectedDataVersion) {
		var checkedStr = "";
		var version;
		if (selectedDataVersion != undefined) {
			var arrayVersions = new Array();
			if (selectedDataVersion.indexOf(",") != -1) {
				version = trim(selectedDataVersion);
				arrayVersions = version.split(",");
				for (var i=0; i<arrayVersions.length; i++) {
					var ver = removeSpaces(arrayVersions[i]);  
					if(ver === value) {
						checkedStr = "checked";
					}
				}
			} else {
				arrayVersions[0] = trim(selectedDataVersion);
				if (value === arrayVersions[0]) {
					checkedStr = "checked";
				}
			}
		}
		$('<div style="color: #000000;"><input class="check techCheck" type="checkbox" name="'+parentValue+'" value="' + value + '" '+checkedStr+' style="margin-right:5%;">'+ text +'</div>').appendTo(obj);
	}

	function trim(stringToTrim) {
		return stringToTrim.replace(/\[|\]/g,'');
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
</script>