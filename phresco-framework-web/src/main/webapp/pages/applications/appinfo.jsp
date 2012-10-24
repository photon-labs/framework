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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.commons.model.WebService"%>
<%@ page import="com.photon.phresco.commons.model.DownloadInfo"%>

<%
	ApplicationInfo selectedInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APPINFO);
	List<WebService> webservices = (List<WebService>)request.getAttribute(FrameworkConstants.REQ_WEBSERVICES);
	String name = "";
	String code = "";
	String description = "";
	String version = "";
	if (selectedInfo != null) {
		name = selectedInfo.getName();
		code = selectedInfo.getCode();
		description = selectedInfo.getDescription();
		version = selectedInfo.getVersion();
	}
%>

<!--  Form Starts -->
<form id="formAppInfo" autocomplete="off" class="app_add_form" autofocus="autofocus">
    <div class="appInfoScrollDiv">          
		<!--  Name Starts -->
		<div class="control-group" id="nameErrDiv">
		    <label class="accordion-control-label labelbold"><span class="red">*</span> <s:text name="lbl.name"/></label>
		    <div class="controls">
		        <input class="input-xlarge" id="name" name="name" maxlength="30" title="<s:text name="title.30.chars"/>"
		            type="text"  value ="<%= name %>" autofocus="autofocus" placeholder="<s:text name="label.name.placeholder"/>" />
		        <span class="help-inline" id="nameErrMsg">
		           
		        </span>
		    </div>
		</div>
		<!--  Name Ends -->
	
		<!--  Code Starts -->
		<div class="control-group">
		    <label class="accordion-control-label labelbold"><s:text name='lbl.code'/></label>
		    <div class="controls">
				<input class="input-xlarge" id="externalCode" name="code"
		            type="text" maxlength="12" value ="<%= StringUtils.isNotEmpty(code) ? code : "" %>" title="<s:text name="title.12.chars"/>" 
		            placeholder="<s:text name="label.code.placeholder"/>"/>
		    </div>
		</div>
		<!--  Code Ends -->
	                    
		<!--  Description Starts -->
		<div class="control-group">
		     <label class="accordion-control-label labelbold"><s:text name='lbl.desc'/></label>
		    <div class="controls">
		        <textarea class="appinfo-desc input-xlarge" maxlength="150" title="<s:text name="title.150.chars"/>" class="xlarge" 
		        	id="textarea" placeholder="<s:text name="label.description.placeholder"/>"
		        	name="description" value="<%= StringUtils.isNotEmpty(description) ? description : "" %>"></textarea>
		    </div>
		</div>
		<!--  Description Ends -->
		
		<!--  Version Starts -->
		<div class="control-group">
		    <label class="accordion-control-label labelbold"><s:text name='lbl.version'/></label>
		    <div class="controls">
				<input class="input-xlarge" id="projectVersion" placeholder="<s:text name="label.project.version.placeholder"/>"
					name="projectVersion" maxlength="20" title="<s:text name="title.20.chars"/>"
					type="text"  value ="<%= StringUtils.isNotEmpty(version) ? version : "" %>"/>
		    </div>
		</div>
		<!--  Version Ends -->
		
		<!-- Technology version start -->
		<div class="control-group">
			<label class="accordion-control-label labelbold"><s:text name='lbl.technology'/></label>
			<div class="controls">
				<input type="text" class="input-xlarge" value="<%= selectedInfo.getTechInfo().getId() %>" disabled="disabled"/>
				<input type="hidden" name="technologyId" value="<%= selectedInfo.getTechInfo().getId() %>"/>
			</div>
		</div>
		<!-- Technology version ends -->
		
		<!-- pilot project start -->
		<div class="control-group">
			<label class="accordion-control-label labelbold"><s:text name='lbl.pilot.project'/></label>
			<div class="controls">
				<select class="input-xlarge" name="<%= FrameworkConstants.REQ_PARAM_NAME_TECHNOLOGY%>">
					<option value="" selected disabled>Select Pilot Projects</option>
				</select>
			</div>
		</div>
		<!-- pilot project ends -->
		
		<!-- servers start -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" onclick="accordionClick(this, $('input[value=serverLayer]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="serverLayer" 
									onclick="getDownloadInfo('<%= DownloadInfo.Category.SERVER.name() %>', $('#1_server'), '<s:text name='lbl.default.opt.select.server'/>')"/>
								<a  class="vAlignSub"><s:text name='lbl.servers'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<table class="table_for_downloadInfos" align="center">
								<thead class="fieldset-tableheader">
									<tr class="noBorder">
										<th class="table_header"><s:text name='lbl.servers'/></th>
										<th><s:text name='lbl.version'/></th>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tbody id="propTempTbody">
									<tr class="noBorder 1_serverdynamicadd">
										<td>
											<select class="input-medium" id="1_server" name="server" 
												onchange="getVersions($('#1_server'), $('#1_serverVersion'));">
											</select>
										</td>
										<td>
											<select class="input-medium" id="1_serverVersion" name="serverVersion">
												<option><s:text name='lbl.default.opt.select.version'/></option>
											</select>
										</td>
										<td>
										  	<a>
										  		<img class="add imagealign" src="images/icons/add_icon.png" onclick="addServer(this);">
								  			</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</section>  
				</div>
			</section>
		</div>
		
		<!-- servers ends -->
		
		<!-- databases start -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid">
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" onclick="accordionClick(this, $('input[value=databaseLayer]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="databaseLayer"
								onclick="getDownloadInfo('<%= DownloadInfo.Category.DATABASE.name() %>', $('#1_database'), '<s:text name='lbl.default.opt.select.database'/>')"/>
								<a class="vAlignSub"><s:text name='lbl.database'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<table class="table_for_downloadInfos"  align="center">
									<thead class = "fieldset-tableheader">
										<tr class="noBorder">
											<th class="table_header"><s:text name='lbl.database'/></th>
											<th><s:text name='lbl.version'/></th>
											<th></th>
											<th></th>
										</tr>
									</thead>
									<tbody id="propTempTbodyDatabase">
										<tr class="noBorder _databasedynamicadd" >
											<td>
												<select class="input-medium" id="1_database" name="database"
												onchange="getVersions($('#1_database'), $('#1_databaseVersion'));" >
												</select>
											</td>
											<td>
												<select class="input-medium" id="1_databaseVersion" name="databaseVersion">
												<option><s:text name='lbl.default.opt.select.version'/></option>
												</select>
											</td>
											<td>
											  	<a>
											  		<img class="add imagealign" src="images/icons/add_icon.png" onclick="addDatabase(this);">
										  		</a>
											</td>
										</tr>
									</tbody>
							</table>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- databases ends -->
		
		<!-- webservice start -->
		<div class="theme_accordion_container">
			<section class="accordion_panel_wid" >
				<div class="accordion_panel_inner">
					<section class="lft_menus_container">
						<span class="siteaccordion closereg" onclick="accordionClick(this, $('input[value=webserviceLayer]'));">
							<span>
								<input type="checkbox" id="checkAll1" class="accordianChkBox" name="layer" value="webserviceLayer"/>
								<a class="vAlignSub"><s:text name='lbl.webservice'/></a>
							</span>
						</span>
						<div class="mfbox siteinnertooltiptxt hideContent">
							<div class="control-group autoWidthForWebservice">
					            <div class="controls">
					            	<div class="typeFields">
						                <div class="multilist-scroller multiselct multiselectForWebservice">
							                <ul>
							                    <%
							                        if (CollectionUtils.isNotEmpty(webservices)) {
					   									for (WebService webservice : webservices) {
							                    %>
						                   			<li>
														<input type="checkbox" name="webservice" value="<%= webservice.getId() %>"
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
				                <span class="help-inline applyerror" id="techError"></span>
					        </div>
						</div>
					</section>  
				</div>
			</section>
		</div>
		<!-- webservice ends -->
	                    
		<!--  Dependecies are loaded -->
		<div class="Create_project_inner" id="AjaxContainer"></div>
	</div>
	
    <!--  Submit and Cancel buttons Starts -->
    <div class="actions">
        <input id="next" type="button" value="<s:text name="label.next"/>" class="btn btn-primary" 
        	onclick="loadContent('featuresList', $('#formAppInfo'), $('#subcontainer'));"/>
        <input type="button" id="cancel" value="<s:text name="lbl.btn.cancel"/>" class="btn btn-primary" 
			onclick="loadContent('applications', $('#formCustomers'), $('#container'));">
    </div>
    <!--  Submit and Cancel buttons Ends -->
    
</form> 
<!--  Form Ends -->
    
<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()){
	    /* JQuery scroll bar */
		$(".appInfoScrollDiv").scrollbars();
	}
	
    $(document).ready(function() {
   		hideLoadingIcon();//To hide the loading icon
    	
		$("#name").focus();
        //	changeStyle("appinfo");
        $("input[name='applicationType']").click(function() {
            changeApplication();
        });
        
        // To restrict the user in typing the special charaters
        $('#name').bind('input propertychange', function (e) {
        	var projNname = $(this).val();
        	projNname = checkForSplChr(projNname);
        	$(this).val(projNname);
         });
        
		/*  $('#cancel').click(function() {
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("fromPage=");
			params = params.concat("edit");
	    	showLoadingIcon($("#tabDiv")); // Loading Icon
			//performAction('applications', params, $('#container'));
		});   */
		
		window.setTimeout(function () { document.getElementById('name').focus(); }, 250);
	});

    function removeTag(currentTag) {
		$(currentTag).parent().parent().remove();
	}
    
    var serverCounter = 2;
    function addServer(){
		var trId = serverCounter + "_serverdynamicadd";
		var servrName = serverCounter + "_serverName";
		var servrVerson = serverCounter + "_serverVersion";
		
		var newPropTempRow = $(document.createElement('tr')).attr("id", trId).attr("class", "noBorder");
		newPropTempRow.html("<td class='textwidth'><select class='input-medium' tempId='"+ serverCounter +"' id='"+ servrName +"'  name='serverName' onchange='getServerVersions(this);'><option>Select Server</option></select></td>" +
				"<td class='textwidth'><select class='input-medium' id='"+ servrVerson +"'  name='serverVersion'><option>Select Version</option></select></td>"+
				"<td class='imagewidth'><a ><img class='add imagealign' " + 
		 			" temp='"+ servrName +"' src='images/icons/add_icon.png' onclick='addServer(this);'></a></td><td><img class = 'del imagealign'" + 
		 			"src='images/icons/minus_icon.png' onclick='removeTag(this);'></td>")
	 	newPropTempRow.appendTo("#propTempTbody");		
		serverCounter++;
		getDownloadInfo('<%= DownloadInfo.Category.SERVER.name() %>', $("select[id='"+ servrName +"']"),'<s:text name='label.select.server'/>');
		
    }			
    
    var databaseCounter = 2;
    function addDatabase(){
		var trId = databaseCounter + "_databasedynamicadd";
		var databaseName = databaseCounter + "_databaseName";
		var databaseVerson = databaseCounter + "_databaseVersion";
		
		var newPropTempRow = $(document.createElement('tr')).attr("id", trId).attr("class", "noBorder");
		newPropTempRow.html("<td class='textwidth'><select class='input-medium' tempId='"+databaseCounter+"' id='"+ databaseName +"'  name='databaseName' onchange='getDatabaseVersions(this);'><option>Select Database</option></select></td>" +
				"<td class='textwidth'><select class='input-medium' id='"+ databaseVerson +"'  name='databaseVersion'><option>Select Version</option></select></td>"+
				"<td class='imagewidth'><a ><img class='add imagealign' " + 
		 			" temp='"+ databaseName +"' src='images/icons/add_icon.png' onclick='addDatabase(this);'></a></td><td><img class = 'del imagealign'" + 
		 			"src='images/icons/minus_icon.png' onclick='removeTag(this);'></td>")
	 	newPropTempRow.appendTo("#propTempTbodyDatabase");		
		databaseCounter++;
		getDownloadInfo('<%= DownloadInfo.Category.DATABASE.name() %>', $("select[id='"+ databaseName +"']"), '<s:text name='label.select.db'/>');
    }		
    
    var selectBoxobj;
    var defaultOption = "";
    function getDownloadInfo(type, toBeFilledCtrlObj, defaultOptTxt) {
    	defaultOption = defaultOptTxt;
    	selectBoxobj = toBeFilledCtrlObj;
		var params = getBasicParams();
		params = params.concat("&type=");
		params = params.concat(type);
		params = params.concat("&techId=");
		params = params.concat('<%= selectedInfo.getTechInfo().getId() %>');
		loadContent("fetchDownloadInfos", '', '', params, true);
	}
    
    var map = {};
	//Success event functions
	function successEvent(pageUrl, data) {
		//To fill the servers/database for the selected 
		if (pageUrl == "fetchDownloadInfos") {
			selectBoxobj.empty();
			selectBoxobj.append($("<option value='' selected disabled></option>").text(defaultOption));
			for (i in data.downloadInfos) {
				fillOptions(selectBoxobj, data.downloadInfos[i].id, data.downloadInfos[i].name);
				var versions = data.downloadInfos[i].artifactGroup.versions;
				map[data.downloadInfos[i].id] = versions;
			}
		}
	}
	
	function getServerVersions(obj) {
		var id = $(obj).attr("tempId");
		var toObj = $("#" + id + "_serverVersion");
		getVersions($(obj), toObj);
	}
	
	function getDatabaseVersions(obj) {
		var id = $(obj).attr("tempId");
		var toObj = $("#" + id + "_databaseVersion");
		getVersions($(obj), toObj);
	}
	
	//To get the versions of the selected server/Db
	function getVersions(obj, toBeFilledCtrlObj) {
		toBeFilledCtrlObj.empty();
		var id = obj.val();
		var versions = map[id];
		for (i in versions) {
			fillOptions(toBeFilledCtrlObj, versions[i].id, versions[i].version);
		}
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