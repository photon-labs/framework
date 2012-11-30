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
Map<String, Map<String, String>> jsMinifyMap = (Map<String, Map<String, String>>) request.getAttribute(FrameworkConstants.REQ_JS_MINIFY_MAP);
Map<String, Map<String, String>> cssMinifyMap = (Map<String, Map<String, String>>) request.getAttribute(FrameworkConstants.REQ_CSS_MINIFY_MAP);
%>

<form autocomplete="off" class="minification_form form-horizontal" id="minificationForm">
	<div class="control-group" id="minfiyAllControl">
		<label for="xlInput" class="control-label labelbold popupLbl"><s:text name="lbl.minify.all"/></label>
		<div class="controls">
			<input type="checkbox" class="chckBxAlign" id="minifyAll" value="false" name="minifyAll" onclick="changeChckBoxValue(this);"/>
		</div>
	</div>
	<!--  js minification starts -->
	<fieldset class="popup-fieldset fieldset_center_align minify_popup">
		<legend class="legendHdr"><s:label key="lbl.hdr.js.minification" cssClass="legendLbl"/></legend>
		<div class="minify_ControlGroup">
			<% if (jsMinifyMap != null && !jsMinifyMap.isEmpty()) { //To show already minified JS file details
				String files = "";
				String location = "";
				Set<String> Keys = jsMinifyMap.keySet();
				for (String compressedName : Keys) {
					Map<String, String> jsValuesMap = jsMinifyMap.get(compressedName);
					Set<String> fileKeys = jsValuesMap.keySet();
					for (String fileKey : fileKeys) {
						files = fileKey;
						location = jsValuesMap.get(fileKey);
					}
			%>
				<div class="compressJs_Div">
					<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
					<input type="text" name="minifyFileNames" class="<%= compressedName %>" id="compNameText" value="<%= compressedName %>" disabled style="float:left;"/>
					<input type="button" id="<%= compressedName %>" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" fileType="js" onclick="browseFiles(this);">
					<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="appendRow();"></a>
					<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
					<input type="hidden" tempName="<%= compressedName %>" name="<%= compressedName %>" value="<%= files %>" id="">
					<input type="hidden" name="<%= compressedName %>_fileLocation" value="<%= location %>" id="<%= compressedName %>_fileLocation">
				</div>
					
			<% 	}
			  } else { %>
			<div class="compressJs_Div" id="compressJs_Div">
				<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
				<input type="text" name="minifyFileNames" class="getJsFiles1" id="compNameText" disabled style="float:left;"/>
				<input type="button" id="getJsFiles1" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" fileType="js" onclick="browseFiles(this);">
				<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="appendRow();"></a>
				<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
				<input type="hidden" tempName="getJsFiles1" name="getJsFiles1" value="" id="">
				<input type="hidden" name="fileLocation" value="" id="getJsFiles1_fileLocation">
			</div>
			<% } %>	
		</div>
	</fieldset>	
	<!--  js minification ends -->
	
	<!--  css minification starts -->
	<fieldset class="popup-fieldset fieldset_center_align minify_popup">
		<legend class="legendHdr"><s:label key="lbl.hdr.css.minification" cssClass="legendLbl"/></legend>
		<div class="minifyCSS_ControlGroup">
		<% if (cssMinifyMap != null && !cssMinifyMap.isEmpty()) { //To show already minified CSS file details
				String files = "";
				String location = "";
				Set<String> Keys = cssMinifyMap.keySet();
				for (String compressedName : Keys) {
					Map<String, String> cssValuesMap = cssMinifyMap.get(compressedName);
					Set<String> fileKeys = cssValuesMap.keySet();
					for (String fileKey : fileKeys) {
						files = fileKey;
						location = cssValuesMap.get(fileKey);
					}
		 %>
		 	<div class="compressCss_Div">
					<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
					<input type="text" name="minifyFileNames" class="<%= compressedName %>" id="compNameText" value="<%= compressedName %>" disabled style="float:left;"/>
					<input type="button" id="<%= compressedName %>" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" fileType="css" onclick="browseFiles(this);">
					<a><img title="" src="images/icons/add_icon.png" id="addCSSComp" class="minifyAddIcon" onclick="appendCssRow();"></a>
					<a><img class="cssDel imagealign hide" src="images/icons/minus_icon.png" onclick="removeCSSTag(this);"></a>
					<input type="hidden" tempName="<%= compressedName %>" name="<%= compressedName %>" value="<%= files %>" id="">
					<input type="hidden" name="<%= compressedName %>_fileLocation" value="<%= location %>" id="<%= compressedName %>_fileLocation">
				</div>
		 <%    } 
		 	} else { %>
			<div class="compressCss_Div" id="compressCss_Div">
				<label for="xlInput" class="xlInput labelbold popupLbl minifyLabel" style="float:left;"><s:text name="build.compress.name"/></label>
				<input type="text" name="minifyFileNames" class="getCssFiles1" id="compNameText" disabled style="float:left;"/>
				<input type="button" id="getCssFiles1" class="btn btn-primary" style="float:left;" value="<s:text name="build.minify.browse"/>" fileType="css" onclick="browseFiles(this);">
				<a><img title="" src="images/icons/add_icon.png" id="addCSSComp" class="minifyAddIcon" onclick="appendCssRow();"></a>
				<a><img class="cssDel imagealign hide" src="images/icons/minus_icon.png" onclick="removeCSSTag(this);"></a>
				<input type="hidden" tempName="getCssFiles1" name="getCssFiles1" value="" id="">
				<input type="hidden" name="fileLocation" value="" id="getCssFiles1_fileLocation">
			</div>
		<% } %>	
		</div>
	</fieldset>	
	<!--  css minification ends -->	
</form>

<script type="text/javascript">
$(document).ready(function() {
	showHideMinusIcon($("#addJSComp"));
	showHideMinusIcon($("#addCSSComp"));
});

var textBoxClass = "";
function browseFiles(obj) {
	textBoxClass = $(obj).attr("id");
	var compressName = $('input[class="'+ textBoxClass +'"]').val();
	var alreadyMinifiedFiles = $('input[name="'+ compressName +'"]').val();
	var fileType = $(obj).attr("fileType");
	$('#popupPage').modal('hide');
	var params = "";
	params = params.concat("&fileType=");
	params = params.concat(fileType);
	params = params.concat("&fileOrFolder=All");
	params = params.concat("&from=minification");
	params = params.concat("&compressName=");
	params = params.concat(compressName);
	params = params.concat("&minifiedFiles=");
	params = params.concat(alreadyMinifiedFiles);
	additionalPopup('minifyBrowseFileTree', 'Browse', 'filesToMinify', '', '', params, true);
}

//To update selected files, compressed name and location in hidden field
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
	newMinDiv.html("<label for='xlInput' class='xlInput labelbold popupLbl minifyLabel' style='float:left;'><s:text name='build.compress.name'/></label>" +
		"<input type='text' name='minifyFileNames' class='"+ browseId +"' id='compNameText' disabled style='float:left;'/>" + 
		"<input type='button' id='"+ browseId +"' class='btn btn-primary' style='float:left;' value='<s:text name='build.minify.browse'/>' fileType='js' onclick='browseFiles(this);'>" +
		"<a><img title='' src='images/icons/add_icon.png' id='addJSComp' class='minifyAddIcon' onclick='appendRow();''></a>" +
		"<a><img class='del imagealign hide' src='images/icons/minus_icon.png' onclick='removeTag(this);'></a> " +
		"<input type='hidden' tempName='"+ browseId +"' name='"+ browseId +"' value='' id=''>" +
		"<input type='hidden' name='' value='' id='"+ locationId +"'>");
	newMinDiv.appendTo(".minify_ControlGroup");
	counter++;
	//removeTag();
	showHideMinusIcon($("#addJSComp"));
}

function appendCssRow() {
	var browseId = "getCssFiles"+counter;
	var locationId = browseId + "_fileLocation";
	var newMinDiv = $(document.createElement('div')).attr("id", 'compressCss_Div' + counter).attr("class","compressCss_Div");
	newMinDiv.html("<label for='xlInput' class='xlInput labelbold popupLbl minifyLabel' style='float:left;'><s:text name='build.compress.name'/></label>" +
		"<input type='text' name='minifyFileNames' class='"+ browseId +"' id='compNameText' disabled style='float:left;'/>" + 
		"<input type='button' id='"+ browseId +"' class='btn btn-primary' style='float:left;' value='<s:text name='build.minify.browse'/>' fileType='css' onclick='browseFiles(this);'>" +
		"<a><img title='' src='images/icons/add_icon.png' id='addCSSComp' class='minifyAddIcon' onclick='appendCssRow();''></a>" +
		"<a><img class='cssDel imagealign hide' src='images/icons/minus_icon.png' onclick='removeCSSTag(this);'></a> " +
		"<input type='hidden' tempName='"+ browseId +"' name='"+ browseId +"' value='' id=''>" +
		"<input type='hidden' name='' value='' id='"+ locationId +"'>");
	newMinDiv.appendTo(".minifyCSS_ControlGroup");
	counter++;
	showHideMinusIcon($("#addCSSComp"));
}

function showHideMinusIcon(obj) {
	var plusIconId = $(obj).attr("id");
	var noOfRows = $('img[id='+ plusIconId +']').size();
	if (noOfRows > 1) {
		if (plusIconId == "addJSComp") {
			$(".del").show();	
		} else {
			$(".cssDel").show();	
		}
	} else if (noOfRows === 1) {
		if (plusIconId == "addJSComp") {
			$(".del").hide();	
		} else {
			$(".cssDel").hide();	
		}
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

function removeCSSTag(currentTag) {
	var noOfRows = $('img[id="addCSSComp"]').size();
	if(noOfRows > 1 && currentTag !== undefined) {
		$(currentTag).parent().parent().remove();
		noOfRows--;
	} 
	if (noOfRows === 1) {
		$(".cssDel").hide();
	}
}
</script>