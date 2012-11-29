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
		reloadIframe();
		$(".styles").click(function() {
			reloadIframe();
		});
	});
	
	function reloadIframe() {
		var theme = localStorage['color'];
		if (theme != null) {
            if (theme == "themes/red_blue/css/red.css") {
                theme == "themes/red_blue/css/red.css"
            } else {
                theme == "themes/red_blue/css/blue.css"
            }
        } else {
            theme = "theme/photon/css/photon_theme.css";
        }
		var source = "<%= jForumUrl %>&css=" + theme;
		
		$("iframe").attr({
            src: source
        });
		$('iframe').load(function() {
			$(".loadingIcon").hide();
		});
	}

</script>