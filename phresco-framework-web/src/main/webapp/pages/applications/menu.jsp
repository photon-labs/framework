<%--
  ###
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
  ###
  --%>
<%@page import="org.apache.commons.collections.CollectionUtils"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<script type="text/javascript">
	$(document).ready(function() {
		showLoadingIcon();
		clickMenu($("a[name='appTab']"), $("#subcontainer"), $('#formAppMenu, #formCustomers'));
		clickMenu($("a[name='qualityTab']"), $("#subcontainer"), $('#formAppMenu, #formCustomers'));
		clickMenu($("a[name='themeTab']"), $("#subcontainer"), $('#formAppMenu, #formCustomers'));
		loadContent("editApplication", $('#formAppMenu, #formCustomers'), $("#subcontainer"), '', '', true);
		activateMenu($("#appinfo"));
	});
	
	//To get the appInfo page when the appInfo tab and previous btn in features jsp is clicked
  	function showAppInfoPage() {
  		var params = getBasicParams();
  		loadContent('appInfo', $('#formFeatures'), $('#subcontainer'), params, '', true);
  	}
	
  	//To get the features page when the features tab and next btn in appInfo jsp is clicked
  	function showFeaturesPage() {
   		var isError = false;
  		$('#serverError').html("");
 		$('#databaseError').html("");

 		$("select[name='server'] option").each(function() {
 			var val = $(this).val();	
 			if ($("select[name='server'] option:selected[value='"+ val +"']").length > 1) {	
		 		if ($('#propTempTbody').find("tr").length > 1) {
			 		$("#propTempTbody .versionDiv ").each(function() {
			 		    var serverLen = $(this).find(":checkbox").length;
			 		    var selectedServerLen = $(this).find(":checkbox:checked").length;
			 		    if (serverLen > 0 && selectedServerLen == 0) {
							$('#serverError').html("Server Version is Missing");
							isError = true;
			 		    }
			 		});
		 		}
 			}
 		});
 		
 		$("select[name='database'] option").each(function() {
 			var val = $(this).val();	
 			if ($("select[name='database'] option:selected[value='"+ val +"']").length > 1) {
		 		if ($('#propTempTbodyDatabase').find("tr").length > 1) {
			 		$("#propTempTbodyDatabase .versionDiv ").each(function() {
			 		    var dbLen = $(this).find(":checkbox").length;
			 		    var selectedDbLen = $(this).find(":checkbox:checked").length;
			 		    if (dbLen > 0 && selectedDbLen == 0) {
							$('#databaseError').html("Database Version is Missing");
							isError = true;
			 		    }
			 		});
		 		}
 			}
 		});
  		
    	if (!isError) {
   			$('#serverError').html("");
 			$('#databaseError').html("");
  			showLoadingIcon();
  			var params = getBasicParams();
  			validate('features', $('#formAppInfo'), $('#subcontainer'), params);
   		}
	}
  	
  	$('#testmenu').hide();
  	$('#thememenu').hide();
  	
  	$(".tabs li a").click(function() {
		if($(this).attr("id")=="quality") {
			$("#thememenu").slideUp();
			$("#testmenu").slideDown();
			$("#testmenu .active").removeClass("active").addClass("inactive");
			$("#testmenu li:first-child a").addClass("active");	
		} else if($(this).attr("id")=="configuration") {
			$("#testmenu").slideUp();
			$("#thememenu").slideDown();	
		} else if($(this).attr("name")=="appTab") {
			$("#testmenu").slideUp();
			$("#thememenu").slideUp();
		}else if($(this).attr("id")=="themeBuilderList") {
			$("#thememenu").slideDown();
		}
    });
	
  	// Script related to menu left slide
	
	var tabwidth = $(".tabs").width();
	var tabwidthwindow = tabwidth + 15;
	$('.tabs').css("left",-tabwidthwindow);
	$('#subcontainer').css("width",'97.3%');
  	$(".menuarrow").click(function(e) {
		clickedobj=e.currentTarget;
			if ($(clickedobj).attr("dataflag") == "true") {
				$(".menuarrow img").attr("src","images/menu_arrow_open.gif");
				$('#subcontainer').animate({width: '97.3%'},350);
				$('.tabs').animate({left:-tabwidthwindow},350);
				$(clickedobj).animate({left:'2px'},350);
				$(clickedobj).attr("dataflag", "false");
			}
			else{
				$(".menuarrow img").attr("src","images/menu_arrow_close.gif");
				$('#subcontainer').animate({width: '83%'},350);
				$('.tabs').animate({left:'0px'},350);
				$(clickedobj).animate({left:tabwidthwindow},350);
				$(clickedobj).attr("dataflag", "true");
			}
  	});	

  	var isCiRefresh = false; // for ci page use - this should be global
  	
</script>

<%
	String projectId = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String appId = (String) request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String currentAppName = (String) request.getAttribute(FrameworkConstants.REQ_CURRENT_APP_NAME);
	Object optionsObj = session.getAttribute(FrameworkConstants.REQ_OPTION_ID);
	List<String> optionIds  = null;
	if (optionsObj != null) {
		optionIds  = (List<String>) optionsObj;
	}
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
		<%
			if(optionIds != null && optionIds.contains(FrameworkConstants.CODE_KEY)) {
		%>
			<li>
				<a href="#" class="inactive" name="appTab" id="code"><s:label key="lbl.app.menu.code" theme="simple"/></a>
			</li>
		<% 
			}
		%>
		<li>
			<a href="#" class="inactive" name="appTab" id="configuration" additionalParam="fromPage=config"><s:label key="lbl.app.menu.config" theme="simple"/></a>
			<ul id="thememenu">
			<%
				if(optionIds != null && optionIds.contains(FrameworkConstants.THEME_BUILDER_KEY)) {
			%>
				<li>
					<a href="#" class="inactive" name="themeTab" id="themeBuilderList"><s:label key="lbl.app.menu.theme.builder" theme="simple"/></a>
				</li>
			<% 
				}
			%>	
			</ul>
		</li>
		<%
			if(optionIds != null && optionIds.contains(FrameworkConstants.BUILD_KEY)) {
		%>
			<li>
				<a href="#" class="inactive" name="appTab" id="buildView"><s:label key="lbl.app.menu.build" theme="simple"/></a>
			</li>
		<%
			}
		%>
		
		<%
			if(CollectionUtils.isNotEmpty(optionIds)) {
				if(optionIds.contains(FrameworkConstants.UNIT_TEST_KEY) || optionIds.contains(FrameworkConstants.FUNCTIONAL_TEST_KEY)
				    	|| optionIds.contains(FrameworkConstants.MANUAL_TEST_KEY) || optionIds.contains(FrameworkConstants.PERFORMANCE_TEST_KEY)
				    	|| optionIds.contains(FrameworkConstants.LOAD_TEST_KEY) ) {
		%>   		
		
		<li>
			<a href="#" class="inactive" name="appTab" id="quality"><s:label key="lbl.app.menu.quality" theme="simple"/></a>
			<ul id="testmenu">
				<%
					if (optionIds != null && optionIds.contains(FrameworkConstants.UNIT_TEST_KEY)) {
				%>
				<li>
					<a href="#" class="active" name="qualityTab" id="unit"><s:label key="lbl.quality.menu.unit" theme="simple"/></a>
				</li>
				<%
					} if (optionIds != null && optionIds.contains(FrameworkConstants.FUNCTIONAL_TEST_KEY)) {
				%>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="functional"><s:label key="lbl.quality.menu.funtional" theme="simple"/></a>
				</li>
				<%
					} if (optionIds != null && optionIds.contains(FrameworkConstants.MANUAL_TEST_KEY)) {
				%>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="manual"><s:label key="lbl.quality.menu.manual" theme="simple"/></a>
				</li>
				<%
					} if (optionIds != null && optionIds.contains(FrameworkConstants.PERFORMANCE_TEST_KEY)) {
				%>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="performance"><s:label key="lbl.quality.menu.performance" theme="simple"/></a>
				</li>
				<%
					} if (optionIds != null && optionIds.contains(FrameworkConstants.LOAD_TEST_KEY)) {
				%>
				<li>
					<a href="#" class="inactive" name="qualityTab" id="load"><s:label key="lbl.quality.menu.load" theme="simple"/></a>
				</li>
				<%
					}
				%>
			</ul>
		</li>
		<%
		    		}
			}
		%>
		
		<%
			if (optionIds != null && optionIds.contains(FrameworkConstants.CI_KEY)) {
		%>
			<li>
				<a href="#" class="inactive" name="appTab" id="ci"><s:label key="lbl.app.menu.ci"  theme="simple"/></a>
			</li>
		<%
			} if(optionIds != null && optionIds.contains(FrameworkConstants.REPORT_KEY)) {
		%>
			<li>
				<a href="#" class="inactive" name="appTab" id="veiwSiteReport"><s:label key="lbl.app.menu.report"  theme="simple"/></a>
			</li>
		<%
			}
		%>
	</ul>
	<div class="menuarrow" dataflag="false" style= "position: absolute; top: 87px; cursor: pointer;">
		<img src="images/menu_arrow_open.gif">
	</div>
</nav>			
<section id="subcontainer" class="navTopBorder">
	
</section>