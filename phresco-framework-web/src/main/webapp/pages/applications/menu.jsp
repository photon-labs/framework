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
		var params = $('#formCustomers').serialize();
		clickMenu($("a[name='appTab']"), $("#subcontainer"), params);
		loadContent("appInfo", $('#formAppMenu'), $("#subcontainer"), params);
		activateMenu($("#appinfo"));
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
			<a href="#" class="active" name="appTab" id="appInfo"><s:label key="lbl.app.menu.appinfo" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="features"><s:label key="lbl.app.menu.feature" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="code"><s:label key="lbl.app.menu.code" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="configuration"><s:label key="lbl.app.menu.config" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="buildView"><s:label key="lbl.app.menu.build" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="appTab" id="quality"><s:label key="lbl.app.menu.quality" theme="simple"/></a>
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