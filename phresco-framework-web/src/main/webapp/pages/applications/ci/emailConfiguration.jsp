<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@	page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>

<%
	String senderEmailId = (String) request.getAttribute(FrameworkConstants.REQ_SENDER_EMAILID);
	String senderEmailPassword = (String) request.getAttribute(FrameworkConstants.REQ_SENDER_EMAIL_PASSWORD);
    ApplicationInfo appInfo = (ApplicationInfo) request.getAttribute(FrameworkConstants.REQ_APP_INFO);
    String appId  = appInfo.getId();
    
    String actionStr = "saveEmailConfiguration";
%>
<form id="configureEmailForm" name="ciDetails" action="<%= actionStr %>" method="post" class="ci_form form-horizontal">
<!-- 	<div class="theme_accordion_container clearfix" style="float: none;"> -->
<%-- 	    <section class="accordion_panel_wid"> --%>
<!-- 	        <div class="accordion_panel_inner adv-settings-accoridan-inner"> -->
<%-- 	            <section class="lft_menus_container adv-settings-width"> --%>
<%-- 	                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="lbl.ci.mail.config"/></span></span> --%>
<!-- 	                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett"> -->
<!-- 	                    <div class="scrollpanel adv_setting_accordian_bottom"> -->
<%-- 	                        <section class="scrollpanel_inner"> --%>
				
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.sender.mail' />
									</label>
									<div class="controls">
										<input type="text" name="senderEmailId" id="senderEmailId" class="input-xlarge" value="<%= senderEmailId == null ? "" : senderEmailId %>">
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label labelbold popupLbl">
										<s:text name='lbl.sender.pwd' />
									</label>
									<div class="controls">
										<input type="password" name="senderEmailPassword" id="senderEmailPassword" class="input-xlarge" value="<%= senderEmailPassword == null ? "" : senderEmailPassword %>">
									</div>
								</div>
								
<%-- 				            </section> --%>
<!-- 	                    </div> -->
<!-- 	                </div> -->
<%-- 	            </section>   --%>
<!-- 	        </div> -->
<%-- 	    </section> --%>
<!-- 	</div> -->
</form>

<script type="text/javascript">
	$(document).ready(function() {
// 		$('.siteaccordion').unbind('click');
// 		accordion();
		
		hidePopuploadingIcon();
	});
	
	// when the configure popup is clicked on save button, it should validate mandatory fields before submitting form
	function emailConfigureValidation() {
		var senderEmailId = $("#senderEmailId").val();
		var senderEmailPassword = $("#senderEmailPassword").val();
		
		if (isBlank(senderEmailId)) {
			$("#errMsg").html("Email is empty ");
			$("#senderEmailId").focus();
			console.log("Email is empty ");
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
		
		if (isBlank(senderEmailPassword)) {
			$("#errMsg").html("Email Password is empty ");
			$("#senderEmailPassword").focus();
			console.log("Email Password is empty ");
			return false;
		}
		
		ciConfigureError('errMsg', "");
		return true;
	}
	
	// after validation success, show loading icon and configure email
	function configureEmail(url) {
		$('#configureForm :input').attr('disabled', false);
		loadContent(url, $('#configureEmailForm'), $('#subcontainer'), getBasicParams(), false, true);
	}
	
	function ciConfigureError(id, errMsg){
		$("#" + id ).empty();
		$("#" + id ).append(errMsg);
	}
</script>