<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2013 Photon Infotech Inc.
  
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
  
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONArray"%>


<%
	String from = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	JSONObject json = new JSONObject(); 
	if (FrameworkConstants.EDIT.equals(from)) {
		json = (JSONObject) request.getAttribute(FrameworkConstants.REQ_CSS_JSON);
	}
%>

<form id="form_themeBuilder" autocomplete="off" class="form-horizontal" autofocus="autofocus" style="height:99%;">
	<div class="content_adder">
		<div class="operation">
			<ul id="display-inline-block-example">
				<li id="first" style="width: auto;">
					<input type="button" id="addRule" class="btn btn-primary" value="Add Rule">
				</li>
				<li id="first">
					<input type="button" id="deleteRule" disabled class="btn" value="Delete Rule">
				</li>
			</ul>
		</div>
		<div class="parserWholeDiv">
		<% if (FrameworkConstants.EDIT.equals(from) && json != null) { //For edit 
			JSONArray jsonArray = json.getJSONArray("css");
			for (int i=0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
	    	    String selector = item.getString("selector");
	    %>
	    		<fieldset class="popup-fieldset cssParserFieldset">			
					<legend class="themeBuilderfieldSetLegend">
						<input type="checkbox" class="cssRule" onclick="enableDisableDeleteRule(this);" >
						<label class="control-label labelbold themeBuilderLbl">
							<span class="mandatory">*</span>&nbsp;<s:text name="lbl.selector"/>
						</label>
						<input placeholder="Selector" class="input themeBuilderTxt" type="text" 
							value="<%= selector %>" name="cssSelector" maxlength="30" 
							id="cssSelector" title="<s:text name="title.30.chars"/>">
					</legend>
					<table style="line-height: 0px;margin-top:8px;">
					<thead>
						<tr>
							<td class="noBorder" style="color: #333333; font-weight: bold;">
								<span class="red">*</span>&nbsp;<s:text name="lbl.property"/>
							</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;"><s:text name="lbl.type"/>
							</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;">
								<span class="red">*</span>&nbsp;<s:text name="lbl.value"/>
							</td>
						</tr>
					</thead>
					<tbody id="propertiesDiv">
	    <%	    
	    	    JSONArray propertiesArray = item.getJSONArray("properties");
	    	    for (int j=0; j < propertiesArray.length() ; j++) {
	    	    	JSONObject properties = propertiesArray.getJSONObject(j);
	    	    	String property = properties.getString("property");
	    	    	String value = properties.getString("value");
	    %>
						<tr class="propertiesTR">
							<td class="noBorder propertyTD">
								<input type="text" name="property" class="input-medium" title="" maxlength="20" value="<%= property %>">
							</td>
							<td class="noBorder">
								<select name="cssType" id="cssType" class="input-small" onchange="changeElements(this)">
									<option value="string"><s:text name="lbl.string"/></option>
									<!-- <option value="image">Image</option> -->
									<option value="color"><s:text name="lbl.color"/></option>
								</select>
							</td>
							<td class="noBorder valueTD">
								<input type="text" name="value" title="" value="<%= value %>" class="input-medium">
							</td>
							<td class="noBorder">
								<a>
									<img class="add imagealign" src="images/icons/add_icon.png" onclick="addCssProperty(this);">
								</a>
							</td>
							<td class="noBorder">
								<a>
									<img id="deleteIcon" class="del imagealign hideContent" src="images/icons/minus_icon.png" 
										onclick="removeCssProperty(this);">
								</a>
							</td>
						</tr>
					
	    <%	    	
	    	    }
   	    %> 		
   	    			</tbody>
				</table>
	    	</fieldset>
	    
	    <%	    
			}
		 } else { //For add %>
			<fieldset class="popup-fieldset cssParserFieldset">			
				<legend class="themeBuilderfieldSetLegend">
					<input type="checkbox" class="cssRule" onclick="enableDisableDeleteRule(this);" >
					<label class="control-label labelbold themeBuilderLbl">
						<span class="mandatory">*</span>&nbsp;<s:text name="lbl.selector"/>
					</label>
					<input placeholder="Selector" class="input themeBuilderTxt" type="text" 
						value="" name="cssSelector" maxlength="30" 
						id="cssSelector" title="<s:text name="title.30.chars"/>">
				</legend>
				<div class="themeBuilderErr">
				</div>
				<table style="line-height: 0px;margin-top:8px;">
					<thead>
						<tr>
							<td class="noBorder" style="color: #333333; font-weight: bold;">
								<span class="red">*</span>&nbsp;<s:text name="lbl.property"/>
							</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;"><s:text name="lbl.type"/>
							</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;">
								<span class="red">*</span>&nbsp;<s:text name="lbl.value"/>
							</td>
						</tr>
					</thead>
					<tbody id="propertiesDiv">
						<tr class="propertiesTR">
							<td class="noBorder propertyTD">
								<input type="text" name="property" class="input-medium" title="" maxlength="20" value="">
							</td>
							<td class="noBorder">
								<select name="cssType" id="cssType" class="input-small" onchange="changeElements(this)">
									<option value="string"><s:text name="lbl.string"/></option>
									<!-- <option value="image">Image</option> -->
									<option value="color"><s:text name="lbl.color"/></option>
								</select>
							</td>
							<td class="noBorder valueTD">
								<input type="text" name="value" title="" value="" class="input-medium">
							</td>
							<td class="noBorder">
								<a>
									<img class="add imagealign" src="images/icons/add_icon.png" onclick="addCssProperty(this);">
								</a>
							</td>
							<td class="noBorder">
								<a>
									<img id="deleteIcon" class="del imagealign hideContent" src="images/icons/minus_icon.png" 
										onclick="removeCssProperty(this);">
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>	
		<% } %>	
		</div>	
	</div>
</form>
<div class="actions">
	<input type="button" id="" value="Save" class="btn btn-primary" onclick="validateEntries();">
	<input type="button" id="" value="Cancel" class="btn btn-primary" onclick="themeBuilderCancel();">
</div>	  
<script type="text/javascript">
	$(document).ready(function() {
		hideLoadingIcon();
	});	
	
	//To add property row
	function addCssProperty(obj) {
		$(obj).closest("#propertiesDiv").append('<tr class="propertiesTR"><td class="noBorder propertyTD"><input type="text" name="property" class="input-medium" title="" maxlength="20" value=""></td>'+
			'<td class="noBorder"><select name="cssType" id="cssType" class="input-small" onchange="changeElements(this)">'+
			'<option value="string"><s:text name="lbl.string"/></option><option value="color"><s:text name="lbl.color"/></option></select></td>'+
			'<td class="noBorder valueTD"><input type="text" name="value" title="" value="" class="input-medium"></td><td class="noBorder">'+
			'<a><img class="add imagealign" src="images/icons/add_icon.png" onclick="addCssProperty(this);"></a></td><td class="noBorder">'+
			'<a><img id="deleteIcon" class="del imagealign" src="images/icons/minus_icon.png" onclick="removeCssProperty(this);"></a></td></tr>');
		togglePlusMinus(obj, 'add', 'del');
	}
	
	//To remove property row
	function removeCssProperty(obj) {
		var parentObj = $(obj).closest("#propertiesDiv");
		
		$(obj).parent().parent().parent().remove();
		togglePlusMinus(parentObj, 'add', 'del');
		
	}
	
	function togglePlusMinus(obj, plus, minus) {
		var size = $(obj).closest("#propertiesDiv").find("."+plus).size();
		if (size > 1) {
			$(obj).closest("#propertiesDiv").find("."+minus).show();
		} else {
			$(obj).closest("#propertiesDiv").find("."+minus).hide();
		}
	}
	
	//To change value as color picker control if the type is color
	function changeElements(obj) {
		var selectedVal = $(obj).val();
		$(obj).closest("tr").find(".valueTD").empty();
		if (selectedVal == "string") {
			$(obj).closest("tr").find(".valueTD")
			.append('<input type="text" name="value" title="" value="" class="input-medium">');
		} else if (selectedVal == "color") {
			$(obj).closest("tr").find(".valueTD")
			.append('<input type="text" class="input-medium colorPicker" name="colorPicker" value="" id="colorPicker">');
			triggerColorPicker($(obj).closest("tr").find(".colorPicker"));
		} 
	}
	
	//triggers color picker event
	function triggerColorPicker(obj) {
		$(obj).colorpicker({
			format: 'hex'
		}); 
	}
	
	//To add new rule fieldset
	$("#addRule").click(function() {
	 	$(".parserWholeDiv").append('<fieldset class="popup-fieldset cssParserFieldset">'+
		'<legend class="themeBuilderfieldSetLegend"><input type="checkbox" class="cssRule" onclick="enableDisableDeleteRule(this);"><label class="control-label labelbold themeBuilderLbl">'+
		'<span class="mandatory">*</span>&nbsp;<s:text name="lbl.selector"/></label><input placeholder="Selector" class="input themeBuilderTxt" type="text"'+
		'value="" name="cssSelector" maxlength="30" id="cssSelector" title="<s:text name="title.30.chars"/>">'+
		'</legend><div class="themeBuilderErr"></div><table style="line-height: 0px;margin-top:8px;"><thead><tr><td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;<s:text name="lbl.property"/></td>' +
		'<td class="noBorder" style="color: #333333; font-weight: bold;"><s:text name="lbl.type"/></td><td class="noBorder"' + 
		'style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;<s:text name="lbl.value"/></td></tr></thead><tbody id="propertiesDiv"><tr class="propertiesTR">'+
		'<td class="noBorder propertyTD"><input type="text" name="property" class="input-medium" title="" maxlength="20" value=""><td class="noBorder">'+
		'<select name="cssType" id="cssType" class="input-small" onchange="changeElements(this)"><option value="string"><s:text name="lbl.string"/></option><option value="color">' +
		'<s:text name="lbl.color"/></option></select></td></td><td class="noBorder valueTD">' +
		'<input type="text" name="value" title="" value="" class="input-medium"></td><td class="noBorder"><a>' +
		'<img class="add imagealign" src="images/icons/add_icon.png" onclick="addCssProperty(this);"></a></td><td class="noBorder"><a>' +
		'<img id="deleteIcon" class="del imagealign hideContent" src="images/icons/minus_icon.png" onclick="removeCssProperty(this);"></a></td>' +
		'</tr></tbody></table></fieldset>');
	 	enableDisableDeleteRule();
	});
	
	//To delete rule fieldset
	$("#deleteRule").click(function() {
		$(".cssRule").each(function() {
			if ($(this).is(':checked')) {
				$(this).closest('fieldset').remove();
			}
		});
		enableDisableDeleteRule();
	});	
	
	//To enable/disable delete rule button
	function enableDisableDeleteRule() {
		var totalChkBox = $(".cssRule").size();
		var checkedCount = $(".cssRule:checked").length;
		if (totalChkBox != checkedCount) {
			if (checkedCount > 0) {
				enableButton($("#deleteRule"));
			} else {
				disableButton($("#deleteRule"));
			}			
		} else {
			disableButton($("#deleteRule"));
		}
	}
	
	//Empty validation for mandatory fields
	function validateEntries() {
		var redirect = true;//flag to redirect next method
		$(".cssParserFieldset").each(function() {
			var loop = true;//flag to skip out of loop
			var selector = $(this).closest('fieldset').find('input[name=cssSelector]').val();
			if (isBlank(selector)) {//validates selector text box and skip out of loop
				$(this).closest('fieldset').find('input[name=cssSelector]').focus();
				$(this).find(".themeBuilderErr").text("Enter Selector");
				redirect = false;
				return false;
			} else {
				$(this).find(".themeBuilderErr").empty();
				$(this).find('.propertiesTR').each(function() {
					var property = $(this).find('input[name=property]').val();
					if (isBlank(property)) {//validates property text box and skip out of loop
						$(this).find('input[name=property]').focus();
						$(this).closest('fieldset').find(".themeBuilderErr").text("Enter property");
						loop = false;
						redirect = false;
						return false;
					} else {
						var selectedValue = $(this).find('#cssType').val();
						var value = "";
						if (selectedValue  == "string") {
							value = $(this).find('input[name=value]').val();
						} else if (selectedValue == "color") {
							value = $(this).find('input[name=colorPicker]').val();
						}
						//validates value field and skip out of loop
						if (isBlank(value) && selectedValue  == "string") {
							$(this).find('input[name=value]').focus();
							$(this).closest('fieldset').find(".themeBuilderErr").text("Enter value");
							loop = false;
							redirect = false;
							return false;
						} else if (isBlank(value) && selectedValue  == "color") {
							$(this).find('input[name=colorPicker]').focus();
							$(this).closest('fieldset').find(".themeBuilderErr").text("Choose color");
							loop = false;
							redirect = false;
							return false;
						}
					}	 
				});
			}	
			
			if (!loop) {//To skip out of this method if err found
				return false;
			}
		});
		
		//redirects to action class if no error found
		if (redirect) {
			constructCssJson();
		}
	}
	
	function constructCssJson() {
		showLoadingIcon();
		var cssJson = [];
		$(".cssParserFieldset").each(function() {
			var jsonObj = {};
			var selector = $(this).closest('fieldset').find('input[name=cssSelector]').val();
			jsonObj.selector = selector;
			var properties = [];
			$(this).find('.propertiesTR').each(function() {
				var propertyValues = {};
				var selectedValue = $(this).find('#cssType').val();
				var property = $(this).find('input[name=property]').val();
				var value = "";
				if (selectedValue  == "string") {
					value = $(this).find('input[name=value]').val();
				} else if (selectedValue == "color") {
					value = $(this).find('input[name=colorPicker]').val();
				}
				propertyValues.property = property;
				propertyValues.value = value;
				properties.push(propertyValues);
			});
			jsonObj.properties = properties;
			cssJson.push(jsonObj);
		});
		var css = '{"css":'+ JSON.stringify(cssJson) +'}';
		cssParser(css);
	}
	
	function cssParser(value) {
		var css = escape(value);
		var params = "";
		params = params.concat("&css=");
		params = params.concat(css);
		loadContent('themeBuilderSave', $('#formAppMenu, #formCustomers'), $("#subcontainer"), params, false, true);
	}
	
	function themeBuilderCancel() {
		showLoadingIcon();
		loadContent("themeBuilderList", $('#formAppMenu, #formCustomers'), $("#subcontainer"), '', false, true); 
	}
</script>