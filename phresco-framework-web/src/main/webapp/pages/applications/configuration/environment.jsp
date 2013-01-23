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
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>

<%@ page import="com.google.gson.Gson"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<% 
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	Gson gson = new Gson();
%>

<form id="formEnvironment" class="form-horizontal">
	<div class="control-group">
		<label class="control-label labelbold modallbl-color">
			<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
		</label>
		<div class="controls">
			<input type="text" name="envName" id="envName" class="span3"  placeholder="<s:text name='place.hldr.env.name'/>" 
			maxlength="30" title="<s:text name='title.30.chars'/>" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label labelbold modallbl-color">
			<s:text name='lbl.desc'/>
		</label>
		<div class="controls">
			<textarea name="envDesc" id="envDesc" class="input-xlarge" 
				 maxlength="150" title="<s:text name='title.150.chars'/>" placeholder="<s:text name='place.hldr.env.desc'/>"></textarea>
			<input type="button" value="<s:text name='lbl.btn.add'/>" tabindex=3 id="add" class="btn btn-primary addButton">
		</div>
	</div>

	<fieldset class="popup-fieldset">
		<legend class="fieldSetLegend" ><s:text name="lbl.added.environments"/></legend>
		<div class="popupTypeFields" id="typefield">
            <div class="multilist-scroller multiselect" id='multiselect'>
                <ul>
                <% for (Environment environment : environments ) {
                	String envJson = gson.toJson(environment);
                	
                 %>
	       			<li>
						<input type="checkbox" name="envNames" onclick="checkboxClickEvent(this)" class="check techCheck" 
							value='<%= envJson %>' title="<%= environment.getDesc() %>"  <%= environment.isDefaultEnv() ? "disabled" : "" %> envName='<%= environment.getName() %>'/><%= environment.getName() %>
					</li>
				<% } %>
				</ul>
            </div>
		</div>
		<div class="popupimage">
			<img src="images/icons/top_arrow.png" title="<s:text name='lbl.title.moveup'/>" id="up" class="imageup"><br>
			<img src="images/icons/delete.png"" title="<s:text name='lbl.title.remove'/>" id="remove" class="imageremove"><br>
			<img src="images/icons/btm_arrow.png" title="<s:text name='lbl.title.movedown'/>" id="down" class="imagedown">
		</div>
		<div class="defaultButton">
			<input type="button" class="btn" disabled value="<s:text name='lbl.btn.set.default'/>" tabindex=5 id="setAsDefault" name="setAsDefault">
		</div>
	</fieldset>
</form>


<!-- selectedEnvs hidden field will be updated with the newly added environments after clicking the Add button -->
<input type="hidden" id="selectedEnvs" name="selectedEnvs" value="">
<input type="hidden" id="deletableItems" name="deletableItems" value="">


<script type="text/javascript">

$(document).ready(function() {
	hidePopuploadingIcon();
	$('#errMsg').empty();
	setAsDefaultBtnStatus();
	
	$('#add').click(function() {
		$('#errMsg').html("");
		var returnVal = true;
		name =($.trim($('#envName').val()));
		desc = $("#envDesc").val();
		if(name == "") {
			$("#errMsg").html("<s:text name='popup.err.msg.empty.env.name'/>");
			$("#envName").focus();
			$("#envName").val("");
			returnVal = false;
		} else {
			$('#multiselect ul li input[type=checkbox]').each(function() {
				var jsonData = $(this).val();
				var envs = $.parseJSON(jsonData);
				var envName = envs.name;
				if (name.trim().toLowerCase() == envName.trim().toLowerCase()) {
					$("#errMsg").html("<s:text name='popup.err.msg.env.name.exists'/>");
					returnVal = false;
					return false;
				}
			});
		}
		
		if (returnVal) {
			addRow();		
		}
	});
	
	$('#setAsDefault').click(function() {
		$('#errMsg').empty();
		selectEnv();
		var setAsDefaultEnvsSize = $('#multiselect :checked').size();
		
        if (setAsDefaultEnvsSize > 1) {
			$("#errMsg").html("<s:text name='popup.err.msg.select.only.one.env'/>");
       	 	return false;
		}  
        if (setAsDefaultEnvsSize == 1) {
			$('#multiselect ul li input[type=checkbox]').each( function() {
				var env = $.parseJSON($(this).val());
				env.defaultEnv = "false";
	        });
	        
	       	var setAsDefaultEnvs = new Array();
	        $('#multiselect :checkbox').each( function() {
	        	var checkboxValue = $(this).val();
	        	var allCheckboxVal = $.parseJSON(checkboxValue);
	        	allCheckboxVal.defaultEnv = "false";
				var finalEnvData = JSON.stringify(allCheckboxVal);
				$(this).val(finalEnvData);
	        });
	       	
	        $('#multiselect :checked').each( function() {
	        	var selectedEnvData = $(this).val();
				var selectedEnv = $.parseJSON(selectedEnvData);
				selectedEnv.defaultEnv = "true";
				var finalEnvData = JSON.stringify(selectedEnv);
				$(this).val(finalEnvData);
	        });
			$("#errMsg").html("<s:text name='popup.err.msg.env.set.as.default'/>");
		}
   });
	
	//To remove the added Environment value in UI
    $('#remove').click(function() {
    	$('#errMsg').empty();
    	 selectEnv();
    	 
    	 var checkBoxSize = $('#multiselect :checkbox').size();
    	 if(checkBoxSize < 1) {
    		 $("#errMsg").html("<s:text name='popup.err.msg.add.env.to.remove'/>");
    	 }
    	 
    	 // To remove the Environments from the list box which is not in the XML
        $('#multiselect ul li input[type=checkbox]:checked').each( function() {
			var checkedDataObj = $.parseJSON($(this).val());
			var envName = checkedDataObj.name;
			var configLength = "";
			var env = checkedDataObj.defaultEnv; // selected checkbox
			if(checkedDataObj.configurations == undefined) {
				$(this).parent().remove();
			}
			
			if( checkedDataObj.configurations != null) {
				configLength = checkedDataObj.configurations.length;
			} 
			
			if (env) {
				$("#errMsg").html("<s:text name='popup.err.msg.cant.remove.defaultEnv'/>");
			} 
			
			if (configLength > 0 ) {
				$("#errMsg").html("<s:text name='popup.err.msg.config.exists'/>");
			}
			
			if (!env && configLength <= 0) {
				$(this).parent().remove();
			}
        });
        setAsDefaultBtnStatus(); 
    });
	
	$('#up').click(function () {
		$('.selected').each(function() {
			$(this).prev().before($(this));
		});
	});
	
	$('#down').click(function () {
		var length = $('.selected').length;
		$('.selected').each(function() {
			var element = $(this); 
			for (var i=0; i<length; i++) {
				element = element.next(); 
			}
			$(element).after($(this));
		});
	});
});
	
	function checkboxClickEvent(obj) {
		if ($(obj).prop('checked')) {
			$(obj).parent().attr('class', 'selected');
		} else {
			$(obj).parent().removeAttr('class');
		}
	}
	

	function addRow() {
		var value = $('#envName').val();
		var desc = $('#envDesc').val();
		var checkValue = '{"name": "' + value + '", "desc": "' + desc
				+ '", "defaultEnv": false }';
		var checkbox = '<input type="checkbox" name="envNames" onclick="checkboxClickEvent(this);" class="check techCheck" value=\'' + checkValue + '\' title="' + desc + '" />'
				+ value;

		if ($("#multiselect ul").has("li").length === 0) {
			$("#multiselect ul").append('<li>' + checkbox + '</li>');
		} else {
			$("#multiselect ul li:last").after('<li>' + checkbox + '</li>');
		}
		$("#envName").val("");
		$("#envDesc").val("");
		setAsDefaultBtnStatus();
	}

	function selectEnv() {
		var checkedEnvsSize = $('#multiselect :checked').size();
		if (checkedEnvsSize < 1) {
			$("#errMsg").html("<s:text name='popup.err.msg.select.one.env'/>");
			return false;
		}
	}
	
	function setAsDefaultBtnStatus() {
		if ( $('#multiselect :checkbox').size() < 1) {
			$("input[name=setAsDefault]").attr("disabled", "disabled");
			$("#setAsDefault").removeClass("btn-primary"); 
	        $("#setAsDefault").addClass("btn-disabled");
		} else {
			$("input[name=setAsDefault]").removeAttr("disabled");
			$("#setAsDefault").addClass("btn-primary");
			$("#setAsDefault").removeClass("btn-disabled");
		}
	}
	
	function findError(data) {
		if (!isBlank(data.configNameError)) {
			$(".yesNoPopupErr").html(data.configNameError);
		} else {
			$(".yesNoPopupErr").empty();
		}
	}	
</script>