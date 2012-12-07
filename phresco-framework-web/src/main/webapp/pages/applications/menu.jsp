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
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<script type="text/javascript">
	$(document).ready(function() {
		showLoadingIcon();
		clickMenu($("a[name='appTab']"), $("#subcontainer"), $('#formAppMenu, #formCustomers'));
		loadContent("editApplication", $('#formAppMenu, #formCustomers'), $("#subcontainer"));
		activateMenu($("#appinfo"));
	});
	
	//To get the appInfo page when the appInfo tab and previous btn in features jsp is clicked
  	function showAppInfoPage() {
  		var params = getBasicParams();
  		loadContent('appInfo', $('#formFeatures'), $('#subcontainer'), params);
  	}
	
  	//To get the features page when the features tab and next btn in appInfo jsp is clicked
  	function showFeaturesPage() {
		var params = getBasicParams();
		loadContent('features', $('#formAppInfo'), $('#subcontainer'), params);
	}
  	
  	$('#testmenu').hide();
  	
  	$(".tabs li a").click(function() {
  		if($(this).attr("id")=="quality") {
        	$("#testmenu").slideDown();
  		}
  		else if($(this).attr("name")=="appTab") {
  	        $("#testmenu").slideUp(); 
  		}
      });
  
</script>

<%
	String projectId = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String currentAppName = (String) request.getAttribute(FrameworkConstants.REQ_CURRENT_APP_NAME);
%>
<form id="formAppMenu">
	<!-- Hidden Fields -->
	<input type="hidden" name="projectId" value="<%= projectId %>"/>
	<input type="hidden" name="appId" value="<%= appId %>"/>
</form>

<div class="page-header">
	<h1 style="float: left;">
		<s:text name="lbl.app.edit"/> - <%= currentAppName %>
	</h1>
</div>

<nav>
	<ul class="tabs">
		<li>
			<a href="#" class="active" name="appTab" id="appInfo" onclick="showAppInfoPage();"><s:label key="lbl.app.menu.appinfo" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="features" onclick="showFeaturesPage();"><s:label key="lbl.app.menu.feature" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="code"><s:label key="lbl.app.menu.code" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="configuration" additionalParam="fromPage=config"><s:label key="lbl.app.menu.config" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="buildView"><s:label key="lbl.app.menu.build" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="quality"><s:label key="lbl.app.menu.quality" theme="simple"/></a>
		
			<ul id="testmenu">
				<li>
					<a href="#" class="active" name="qualityTab" id="unit"><s:label key="lbl.quality.menu.unit" theme="simple"/></a>
				</li>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="functional"><s:label key="lbl.quality.menu.funtional" theme="simple"/></a>
				</li>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="performance"><s:label key="lbl.quality.menu.performance" theme="simple"/></a>
				</li>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="load"><s:label key="lbl.quality.menu.load" theme="simple"/></a>
				</li>
			</ul>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="ci"><s:label key="lbl.app.menu.ci"  theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="veiwSiteReport"><s:label key="lbl.app.menu.report"  theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="download"><s:label key="lbl.app.menu.download"  theme="simple"/></a>
		</li>
	</ul>
</nav>			

<section id="subcontainer" class="navTopBorder">

</section>