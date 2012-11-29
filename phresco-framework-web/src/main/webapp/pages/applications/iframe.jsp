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
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%
	String error = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	String clangReport =  (String) request.getAttribute(FrameworkConstants.CLANG_REPORT);
		  
	String sonarPath = "";
	
	if (StringUtils.isNotEmpty(error)) {
%>      
        <div class="alert alert-block">
			<%= error %>
		</div>
<% 
    } else { 
		sonarPath = (String) request.getAttribute(FrameworkConstants.REQ_SONAR_PATH);
%>
		 <iframe src="" frameBorder="0" class="iframe_container"></iframe>
		
<% } %>
	
<script>
	$(document).ready(function() {
		hideLoadingIcon();
	    reloadIframe();
	    $(".styles").click(function() {
	        reloadIframe();
	    });
	});
	
	function reloadIframe() {
		var theme = localStorage["color"];
		if (theme != null) {
			if (theme == "themes/red_blue/css/red.css") {
                theme == "themes/red_blue/css/red.css"
            } else {
                theme == "themes/red_blue/css/blue.css"
            }
		} else {
			theme = "theme/photon/css/photon_theme.css";
		}
		
	    var source = "";
	     <% 
	    	if (StringUtils.isNotEmpty(clangReport)) { 
	    %>
	    	source = "<%= sonarPath %>";
	    <% } else { %>
	    	source = "<%= sonarPath %>?css=" + theme;
		<%	
	    	} 
	    %> 
	    
	    $('iframe').attr("src", source);
	}
	
</script>