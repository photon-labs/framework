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

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
Map<String, Map<String, String>> minifyMap = (Map<String, Map<String, String>>) request.getAttribute(FrameworkConstants.REQ_MINIFY_MAP);
%>

<form autocomplete="off" class="minification_form form-horizontal" id="minificationForm">
	<div class="control-group" id="minfiyAllControl">
		<label for="xlInput" class="control-label labelbold popupLbl"><s:text name="lbl.minify.all"/></label>
		<div class="controls">
			<input type="checkbox" class="chckBxAlign" id="minifyAll" value="false" name="minifyAll" onclick="changeChckBoxValue(this);"/>
		</div>
	</div>
	
	<fieldset class="popup-fieldset fieldset_center_align minify_popup">
		<div class="control-group minify_ControlGroup">
			<% if (minifyMap != null && !minifyMap.isEmpty()) { 
				String files = "";
				String location = "";
				Set<String> Keys = minifyMap.keySet();
				for (String compressedName : Keys) {
					Map<String, String> valuesMap = minifyMap.get(compressedName);
					Set<String> fileKeys = valuesMap.keySet();
					for (String fileKey : fileKeys) {
						files = fileKey;
						location = valuesMap.get(fileKey);
					}
			%>
				<div class="compressJs_Div">
					<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.js.minification"/></label>
					<input type="button" id="<%= compressedName %>" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" onclick="browseFiles(this);">
					<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
					<input type="text" name="minifyFileNames" class="<%= compressedName %>" id="compNameText" value="<%= compressedName %>" disabled style="float:left;"/>
					<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="appendRow();"></a>
					<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
					<input type="hidden" tempName="<%= compressedName %>" name="<%= compressedName %>" value="<%= files %>" id="">
					<input type="hidden" name="<%= compressedName %>_fileLocation" value="<%= location %>" id="fileLocation">
				</div>
					
			<% 	}
			  } else { %>
			<div class="compressJs_Div" id="compressJs_Div">
				<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.js.minification"/></label>
				<input type="button" id="getJsFiles1" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" onclick="browseFiles(this);">
				<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
				<input type="text" name="minifyFileNames" class="getJsFiles1" id="compNameText" disabled style="float:left;"/>
				<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="appendRow();"></a>
				<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
				<input type="hidden" tempName="getJsFiles1" name="getJsFiles1" value="" id="">
				<input type="hidden" name="fileLocation" value="" id="getJsFiles1_fileLocation">
			</div>
			<% } %>	
		</div>
	</fieldset>	
</form>

<script type="text/javascript">

$(document).ready(function() {
	showHideMinusIcon();
});
var textBoxClass = "";
function browseFiles(obj) {
	textBoxClass = $(obj).attr("id");
	//var jsName = $('input[class="'+ textBoxClass +'"]').val();
	//var jsFiles = $('input[name="'+ jsName +'"]').val();
	
	$('#popupPage').modal('hide');
	var params = "";
	params = params.concat("&fileType=js");
	params = params.concat("&fileOrFolder=All");
	params = params.concat("&from=minification");
	additionalPopup('minifyBrowseFileTree', 'Browse', 'filesToMinify', '', '', params, true);
}

function updateMinifyData(compressedName, files, location) {
	$("."+textBoxClass).val(compressedName);
	$('input[tempName="'+ textBoxClass +'"]').attr("name", compressedName);
	$('input[name="'+ compressedName +'"]').val(files);
	$("#"+textBoxClass+"_fileLocation").val(location);
	$("#"+textBoxClass+"_fileLocation").attr("name", compressedName + "_fileLocation");
}

var counter = 2;
function appendRow(){
	var browseId = "getJsFiles"+counter;
	var locationId = browseId + "_fileLocation";
	var newMinDiv = $(document.createElement('div')).attr("id", 'compressJs_Div' + counter).attr("class","compressJs_Div");
	newMinDiv.html("<label for='xlInput' class='xlInput labelbold popupLbl minifyLabel' style='float:left;'><s:text name='build.js.minification'/></label>" +
		"<input type='button' id='"+ browseId +"' class='btn btn-primary' style='float:left;' value='<s:text name='build.minify.browse'/>' onclick='browseFiles(this);'>" +
		"<label for='xlInput' class='xlInput labelbold popupLbl minifyLabel' style='float:left;'><s:text name='build.compress.name'/></label>" +
		"<input type='text' name='minifyFileNames' class='"+ browseId +"' id='compNameText' disabled style='float:left;'/>" + 
		"<a><img title='' src='images/icons/add_icon.png' id='addJSComp' class='minifyAddIcon' onclick='appendRow();''></a>" +
		"<a><img class='del imagealign hide' src='images/icons/minus_icon.png' onclick='removeTag(this);'></a> " +
		"<input type='hidden' tempName='"+ browseId +"' name='"+ browseId +"' value='' id=''>" +
		"<input type='hidden' name='' value='' id='"+ locationId +"'>");
	newMinDiv.appendTo(".minify_ControlGroup");
	counter++;
	//removeTag();
	showHideMinusIcon();
}

function showHideMinusIcon() {
	var noOfRows = $('img[id="addJSComp"]').size();
	if (noOfRows > 1) {
		$(".del").show();
	} else if (noOfRows === 1) {
		$(".del").hide();
	}
}

function removeTag(currentTag) {
	var noOfRows = $('img[id="addJSComp"]').size();
	if(noOfRows > 1 && currentTag !== undefined) {
		$(currentTag).parent().parent().remove();
		noOfRows--;
	} 
	if (noOfRows === 1) {
		$(".del").hide();
	}
}

</script>