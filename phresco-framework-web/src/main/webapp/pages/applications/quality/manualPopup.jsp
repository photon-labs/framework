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

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.util.Constants"%>


<form>
	<div id="fileLocationControl" class="control-group" style="display: block;">
		<label class="control-label labelbold popupLbl" for="xlInput"> File Location</label>
		<div class="controls">
			<input id="xlsxFileLocation" class="filePath" type="text" name="xlsxFileLocation" style="margin-right:5px;">
			<input id="browseButton" class="btn-primary btn_browse browseFileLocation" type="button" onclick="browseFileForXlsx(this);" filetypes="xlsx" value="Browse">
		</div>
</div>
</form>

<script type="text/javascript">
$(document).ready(function() {
	//$("#popupPage").modal('hide');
	 hidePopuploadingIcon();
});	
	function browseFileForXlsx(obj) {
		 $('#popupPage').modal('hide');
		var fileTypes = $(obj).attr("fileTypes");
		var params = "fileOrFolder=All";
		if (fileTypes != undefined && !isBlank(fileTypes)) {
			params = params.concat("&fileType=");
			params = params.concat(fileTypes);
		}
		
		additionalPopup('openBrowseFileTree', 'Browse', 'manualTestFilePath', '', '', params, true); 
	}
	
</script> 