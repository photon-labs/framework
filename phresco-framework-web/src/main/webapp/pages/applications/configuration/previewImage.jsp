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

<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ page import="org.apache.commons.lang.StringUtils"%>

<%
	String img = (String) request.getAttribute("imgStream");
	String imgName = (String) request.getAttribute("imgName");
%>

<img class="" id="previewImage" src="">

<script type="text/javascript">
	$(document).ready(function() {
		<% if (StringUtils.isNotEmpty(img)) { %>
			$('#previewImage').attr("src",  "data:image/png;base64," + '<%= img %>');
		<% } else { %>
			$("#popup_div").text("No preview available or file missing");
		<% } %>
		hidePopuploadingIcon();
		$('.popupClose').show(); //no need close button since yesno popup
		$('.popupOk, .popupCancel').hide()
	});	
</script>	