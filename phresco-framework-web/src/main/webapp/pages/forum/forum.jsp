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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<style>
<!--
.alert-message.block-message {
	margin: 45px 5px;
}
-->
</style>

<%	
	String error = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	String jForumUrl = ""; 
	String bodyBackGroundColor = (String) request.getAttribute(FrameworkConstants.CUST_BODY_BACK_GROUND_COLOR);
	String brandingColor = (String) request.getAttribute(FrameworkConstants.CUST_BRANDING_COLOR);
	String menuBackGround = (String) request.getAttribute(FrameworkConstants.CUST_MENU_BACK_GROUND);
	String menufontColor = (String) request.getAttribute(FrameworkConstants.CUST_MENUFONT_COLOR);
	String labelColor = (String) request.getAttribute(FrameworkConstants.CUST_LABEL_COLOR);
	String disabledLabelColor = (String) request.getAttribute(FrameworkConstants.CUST_DISABLED_LABEL_COLOR);
	String customerId = (String) request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
%>
<div class="page-header"><h1><s:text name="label.phresco.forum"/></h1></div>
<%
	if(error != null ) {
%>
    <div class="alert-message block-message warning" >
		<center><%= error %></center>
	</div>
<%
	} else {
		jForumUrl = (String) request.getAttribute(FrameworkConstants.REQ_JFORUM_URL);
%>
	<iframe src="<%= jForumUrl %>" frameBorder="0" class="iframe_container_forum"></iframe>
<%
	}
 %>

<script>
	$(document).ready(function() {
		hideLoadingIcon();
		reloadIframe();
		$(".styles").click(function() {
			reloadIframe();
		});
		$('#customerList').hide();
	});
	
	function reloadIframe() {
		var theme = localStorage['color'];
		if (theme != null) {
            if (theme == "themes/red_blue/css/red.css") {
                theme == "themes/red_blue/css/red.css";
            } else if(theme == "themes/red_blue/css/blue.css")  {
                theme == "themes/red_blue/css/blue.css";
            } else if(theme == "theme/photon/css/photon_theme.css")  {
                theme == "theme/photon/css/photon_theme.css";
            }
        } else {
            theme = "theme/photon/css/photon_theme.css";
        }
		
		var cssTheme = "css=";
		if ('<%=customerId%>' == '<%=FrameworkConstants.PHOTON%>') {
			cssTheme = cssTheme.concat(theme);
			cssTheme = cssTheme.concat("&customerStyle=");
			cssTheme = cssTheme.concat("#FFF");
			cssTheme = cssTheme.concat("&customerId=");
			cssTheme = cssTheme.concat('<%=FrameworkConstants.PHOTON%>');
		} else {
			cssTheme = cssTheme.concat(theme);
			cssTheme = cssTheme.concat("&brandingColor=");
			cssTheme = cssTheme.concat('<%= brandingColor %>');
			cssTheme = cssTheme.concat("&bodyBackGroundColor=");
			cssTheme = cssTheme.concat('<%= bodyBackGroundColor %>');
			cssTheme = cssTheme.concat("&menuBackGround=");
			cssTheme = cssTheme.concat('<%= menuBackGround %>');
			cssTheme = cssTheme.concat("&menufontColor=");
			cssTheme = cssTheme.concat('<%= menufontColor %>');
			cssTheme = cssTheme.concat("&labelColor=");
			cssTheme = cssTheme.concat('<%= labelColor %>');
			cssTheme = cssTheme.concat("&disabledLabelColor=");
			cssTheme = cssTheme.concat('<%= disabledLabelColor %>');
			cssTheme = cssTheme.concat("&brandingColor=");
			cssTheme = cssTheme.concat('<%= brandingColor %>');
			cssTheme = cssTheme.concat("&customerId=");
			cssTheme = cssTheme.concat("customer");
			cssTheme = cssTheme.concat("&customerStyle=");
			cssTheme = cssTheme.concat("customerStyle.css");
		}
		
		var sourceUrl = '<%= jForumUrl %>';
		sourceUrl = sourceUrl.concat("&" + cssTheme);
		$("iframe").attr({
            src: sourceUrl
        });
	}

</script>