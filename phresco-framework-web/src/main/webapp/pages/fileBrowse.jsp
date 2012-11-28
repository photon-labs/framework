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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.commons.ApplicationsUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.model.CertificateInfo"%>
	
	<!--[if IE]>
	<script src="js/html5.js"></script>
	<![endif]-->
	
<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
	String projectLocation = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_LOCATION);
	String fileTypes = (String) request.getAttribute(FrameworkConstants.FILE_TYPES);
	String fileorfolder = (String) request.getAttribute(FrameworkConstants.FILE_BROWSE);
	String from = (String) request.getAttribute(FrameworkConstants.REQ_FROM);

	boolean isCertAvailable = false;
	if (request.getAttribute(FrameworkConstants.REQ_RMT_DEP_IS_CERT_AVAIL) != null) {
		isCertAvailable = (Boolean) request.getAttribute(FrameworkConstants.REQ_RMT_DEP_IS_CERT_AVAIL);
	}
	String fileBrowseFrom = (String)request.getAttribute(FrameworkConstants.REQ_RMT_DEP_FILE_BROWSE_FROM);
	List<CertificateInfo> certificates = (List<CertificateInfo>)request.getAttribute("certificates");
%>

<form action="build" method="post" autocomplete="off" class="build_form" id="browseLocation">
<div class="" id="generateBuild_Modal">

	<div class="fileTreeBrowseOverflow">
		<div id="JQueryFTD" class="JQueryFTD"></div>
		
		<div id="crtFileDiv" class="hideContent">
			<!-- Modules -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.certificates"/></label>
				<div class="input">
					<select id="certificates" name="certificates" class="xlarge">
					<%
						if (CollectionUtils.isNotEmpty(certificates)) {
							for (CertificateInfo certificate : certificates) {
					%>
								<option value="<%= certificate.getDisplayName() %>"><%= certificate.getDisplayName() %></option>
					<% 
							}
						} 
					%>
					</select>
				</div>
			</div>
		</div>
	</div>
</div>
</form> 

<script type="text/javascript">
    
	if(!isiPad()){
	    /* JQuery scroll bar */
		$(".generate_build").scrollbars();
	}
	
	$(document).ready(function() {
		<% 
			if (!isCertAvailable) {
		%>
			$('#crtFileDiv').hide();
			var location = "<%= projectLocation %>";
			$('#JQueryFTD').fileTree({
				root: '<%= projectLocation %>',
				script: 'pages/jqueryFileTree.jsp',
				expandSpeed: 1000,
				collapseSpeed: 1000,
				multiFolder: true,
				fileTypes: '<%= fileTypes %>',
				fileOrFolder: '<%= fileorfolder %>',
				from: '<%= from %>'
			}, function(file) {
				$('#browseSelectedLocation').val(file);
			});
		<%
			} else {
		%>
			$('#JQueryFTD').hide();
			$('#browseSelectedLocation').hide();
			$('#crtFileDiv').show()
		<%
			}
		%>
		
	});
		
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
	

	function add_popupOnOk(obj) {
		setTimeout(function () {
			$('#popupPage').modal('show');
	    }, 600);
		if ($(obj).attr("id") === "filesToMinify") {
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	    	params = params.concat("browseLocation=");
	    	params = params.concat($("#browseSelectedLocation").val());
	    	params = params.concat("&compressName=");
	    	params = params.concat($("#compressName").val());
	    	
	    	loadContent('filesToMinify', '', '', params, true);
		} else {
			alert("else case");
			$('#fileLocation').val($('#browseSelectedLocation').val());			
		}
	}
</script>