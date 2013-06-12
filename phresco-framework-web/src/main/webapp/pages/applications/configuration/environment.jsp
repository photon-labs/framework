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
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>

<%@ page import="com.google.gson.Gson"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.Technology" %>
<%@ page import="com.photon.phresco.framework.model.Permissions"%>

<% 
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	List<Technology> allTechnologies  = (List<Technology>) request.getAttribute(FrameworkConstants.REQ_ALL_TECHNOLOGIES);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String configPath = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_PATH);
	boolean isEnvSpecific = (Boolean) request.getAttribute(FrameworkConstants.REQ_ENV_SPECIFIC);
	Gson gson = new Gson();
	List<String> selectedappliesToTechs = null;
	
	Permissions permissions = (Permissions) session.getAttribute(FrameworkConstants.SESSION_PERMISSIONS);
	String per_disabledStr = "";
	String per_disabledClass = "btn-primary";
	if (permissions != null && !permissions.canManageConfiguration()) {
		per_disabledStr = "disabled";
		per_disabledClass = "btn-disabled";
	}
%>

<form id="formEnvironment" method="post" class="form-horizontal">
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
		</div>
	</div>
	
	<% if (FrameworkConstants.SETTINGS.equals(fromPage)) { %>
		<div class="control-group" id="appliesToControl">
			<label class="control-label labelbold modallbl-color">
				<span class="mandatory">*</span>&nbsp;<s:text name='label.applies.to'/>
			</label>
			<div class="controls">
				<div class="settingsTypeFields">
            		<div class="multilist-scroller multiselect" id='multiselectAppliesTo'>
					   <ul>
					   		<li>
								<input type="checkbox" value="" id="checkAllAuto" name="" onclick="checkAllEvent(this, $('.appliesToCheck'));" style="margin: 3px 8px 6px 0;">All
							</li>
					   	<% 
					   		if (CollectionUtils.isNotEmpty(allTechnologies)) {
					   			for (Technology appliesTo : allTechnologies) {
						%>
								<li>
									<input type="checkbox" name="appliesTo" class="check appliesToCheck" 
										onclick= "checkboxEvent($('.appliesToCheck'), $('#checkAllAuto'))" value='<%= appliesTo.getId()%>' title="<%= appliesTo.getDescription() %>" /><%= appliesTo.getName() %>
								</li>
						<%		
				   				}
				   			}
						%>
					   </ul>
			  	 	</div>
				</div>
				<span class="help-inline" id="appliesToError"></span>
			</div>
	    </div>
   <% } %>
   	<div class="controls">
		<input type="button" name="addBtn" value="<s:text name='lbl.btn.add'/>" tabindex=3 id="add" class="btn btn-primary addButton">
	</div>
	
	<fieldset class="popup-fieldset envFieldset">
		<legend class="fieldSetLegend" ><s:text name="lbl.added.environments"/></legend>
		<div class="popupTypeFields" id="typefield">
            <div class="multilist-scroller multiselect" id='multiselect'>
                <ul>
                <% 
                	int i = 1;
                	for (Environment environment : environments) {
                	String envJson = gson.toJson(environment);
				%>
	       			<li>
						<input type="checkbox" name="envNames" onclick="checkboxClickEvent(this)" class="check techCheck" 
							value='<%= envJson %>' title="<%= environment.getDesc() %>"  <%= environment.isDefaultEnv() ? "disabled" : "" %> envName='<%= environment.getName() %>'/>
						<span id="envLabel<%= i %>" class="envLabel"><%= environment.getName() %><%= environment.isDefaultEnv() ? " (Default)" : "" %>
							<script type="text/javascript">
								var id = '<%= i %>';
								<%  if (environment.isDefaultEnv()) { %>
										$('#envLabel' + id).css('color','#1589FF');
								<%	} else { %>
										$("#envLabel" + id).css("color", '');
								<% 	} %>					
							</script>
						</span>
					</li>
				<% 
					i++;
                } %>
				</ul>
            </div>
		</div>
		<div class="popupimage">
			<img src="images/icons/top_arrow.png" title="<s:text name='lbl.title.moveup'/>" id="up" class="imageup"><br>
			<img src="images/icons/delete.png"" title="<s:text name='lbl.title.remove'/>" id="remove" class="imageremove"><br>
			<img src="images/icons/btm_arrow.png" title="<s:text name='lbl.title.movedown'/>" id="down" class="imagedown">
		</div>
		<% if (FrameworkConstants.CONFIG.equals(fromPage)) { %>
			<div class="defaultButton">
				<input type="button" class="btn" disabled value="<s:text name='lbl.btn.set.default'/>" tabindex=5 id="setAsDefault" name="setAsDefault">
			</div>
		<% } %>
	</fieldset>
</form>


<!-- selectedEnvs hidden field will be updated with the newly added environments after clicking the Add button -->
<input type="hidden" id="selectedEnvs" name="selectedEnvs" value="">
<input type="hidden" id="deletableItems" name="deletableItems" value="">


<script type="text/javascript">

$(document).ready(function() {
	
	$('#formEnvironment').submit(function(e) {
	    e.preventDefault();
	});
	
	$("#multiselectAppliesTo").scrollbars(); //JQuery scroll bar
	
	$('#envName').bind('input propertychange', function (e) {
        var envName = $(this).val();
        envName = checkForSplChrExceptDot(envName);
        $(this).val(envName);
    });
	
	hidePopuploadingIcon();
	disablePopupOkButton();
	$('#errMsg').empty();
	
	$('input[name="envNames"]').change(function() {
		if ($(this).is(':checked')) {
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.update'/>");
			var envData = $.parseJSON($(this).val());
			var appliesTos = envData.appliesTo;
			$('#multiselectAppliesTo ul li input[type=checkbox]').each(function() {
				if ($.inArray($(this).val(), appliesTos) != -1) {
					$(this).attr("checked", true);
				}
			});
			
			toEnableSetAsDefault();
			$("#envName").val(envData.name);
			$("#envDesc").val(envData.desc);
		} else {
			toDisableSetAsDefault();
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.add'/>");
			toEmptyNameAndDesc();
			$('#multiselectAppliesTo ul li input[type=checkbox]').each(function() {
				$(this).attr("checked", false);
			});
		}
	});
	
	$('#add').click(function() {
		$('#errMsg').html("");
		var returnVal = true;
		name =($.trim($('#envName').val()));
		desc = $("#envDesc").val();
		if (name == "") {
			$("#errMsg").html("<s:text name='popup.err.msg.empty.env.name'/>");
			$("#envName").focus();
			$("#envName").val("");
			setTimeOut();
			returnVal = false;
		} else {
			var checkToAdd = $('input[name="addBtn"]').val();
			$('#multiselect ul li input[type=checkbox]').each(function() {
				var jsonData = $(this).val();
				var envs = $.parseJSON(jsonData);
				var envName = envs.name;
				if (checkToAdd == "Add") {
					if (name.trim().toLowerCase() == envName.trim().toLowerCase()) {
						$("#errMsg").html("<s:text name='popup.err.msg.env.name.exists'/>");
						setTimeOut();
						returnVal = false;
						return false;
					}
				} else {
					var currentVal = $('#multiselect :checked').val();
					var currentObj = $.parseJSON(currentVal);
					var currentEnvName = currentObj.name;
					if (currentEnvName.toLowerCase() !== name.trim().toLowerCase()) {
						if (name.trim().toLowerCase() == envName.trim().toLowerCase()) {
							$("#errMsg").html("<s:text name='popup.err.msg.env.name.exists'/>");
							setTimeOut();
							returnVal = false;
							return false;
						}
					}
				}
				enablePopupOkButton();
			});
		}
		<% if (FrameworkConstants.SETTINGS.equals(fromPage)) { %>
				var selecedAppliesToSize = $('#multiselectAppliesTo :checked').size();
				disablePopupOkButton();
				if(name == "") {
					disablePopupOkButton();
					setTimeOut();
				} else if (selecedAppliesToSize < 1) {
					$("#errMsg").html("<s:text name='popup.err.msg.select.one.appliesTo'/>");
					setTimeOut();
					return false;
				} else {
					enablePopupOkButton();
				}
        <% } %>
		
		if (returnVal) {
			addRow();		
		}
	});
	
	$('#setAsDefault').click(function() {
		$('#errMsg').empty();
		selectEnv();
		toSelectOnlyOneEnv(); 
		var setAsDefaultEnvsSize = $('#multiselect :checked').size();
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
				enablePopupOkButton();
	        });
	       	
	        $('#multiselect :checked').each( function() {
	        	var selectedEnvData = $(this).val();
				var selectedEnv = $.parseJSON(selectedEnvData);
				selectedEnv.defaultEnv = "true";
				var finalEnvData = JSON.stringify(selectedEnv);
				$(this).val(finalEnvData);
				
	        });
			$("#errMsg").html("<s:text name='popup.err.msg.env.set.as.default'/>");
			setTimeOut();
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
				toEmptyNameAndDesc();
				toUncheckCheckBox();
				$('input[name="addBtn"]').val("<s:text name='lbl.btn.add'/>");
			}
			enablePopupOkButton();
        });
        disableOkBtn();
    });
	
	$('#up').click(function () {
		selectEnv();
		$('.selected').each(function() {
			enablePopupOkButton();
			$(this).prev().before($(this));
		});
	});
	
	$('#down').click(function () {
		selectEnv();
		var length = $('.selected').length;
		$('.selected').each(function() {
			enablePopupOkButton();
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
		var checkedAppliesTo = [];
		toSelectOnlyOneEnv();
		var selecedEnvsSize = $('#multiselect :checked').size();
		if(selecedEnvsSize == 1) {
			var currentChckBoxObj = $('input[name="envNames"]:checked');
			var checkedVal = $.parseJSON($('input[name="envNames"]:checked').val());
			$('#multiselectAppliesTo input[type="checkbox"]:checked').each(function() {
				checkedAppliesTo.push($(this).val());
			});
			var selectedText = $('.envLabel').text();
			checkedVal.name = value;
			checkedVal.desc = desc;
			checkedVal.appliesTo = checkedAppliesTo;
			currentChckBoxObj.val(JSON.stringify(checkedVal));
			currentChckBoxObj.parent().find($('.envLabel')).text(value);
		}
		
		if(selecedEnvsSize == 0) {
			
			$('#multiselectAppliesTo input[type="checkbox"]:checked').each(function() {
				checkedAppliesTo.push("\"" + $(this).val() + "\"");
			});
			
			var checkValue = '{"name": "' + value + '", "desc": "' + desc
					+ '", "appliesTo": [' + checkedAppliesTo +'], "defaultEnv": false}';
					
			var checkbox = '<input type="checkbox" name="envNames" onclick="checkboxClickEvent(this);" class="check techCheck" value=\'' + checkValue + '\' title="' + desc + '" />'
					+ '<span class="envLabel">' + value + '</span>';
	
			if ($("#multiselect ul").has("li").length === 0) {
				$("#multiselect ul").append('<li>' + checkbox + '</li>');
			} else {
				$("#multiselect ul li:last").after('<li>' + checkbox + '</li>');
			}
		}
		
		toEmptyNameAndDesc();
		toUncheckCheckBox();
		if ($('input[name="envNames"]:checkbox').is(':checked')) {
			$('input[name="envNames"]:checked').prop('checked', false);
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.add'/>");
		}
	}

	function toEmptyNameAndDesc() {
		$("#envName").val("");
		$("#envDesc").val("");
	}
	
	function toUncheckCheckBox() {
		if ($('#multiselectAppliesTo :checkbox').is(':checked')) {
			$('#multiselectAppliesTo :checked').prop('checked', false);
		}
	}
	
	function selectEnv() {
		var checkedEnvsSize = $('#multiselect :checked').size();
		if (checkedEnvsSize < 1) {
			$("#errMsg").html("<s:text name='popup.err.msg.select.one.env'/>");
			setTimeOut();
			return false;
		}
	}
	
	function toSelectOnlyOneEnv() {
		var selecedEnvsSize = $('#multiselect :checked').size();
        if (selecedEnvsSize > 1) {
			$("#errMsg").html("<s:text name='popup.err.msg.select.only.one.env'/>");
       	 	return false;
		}
	}
	
	function toEnableSetAsDefault() {
		$("input[name=setAsDefault]").removeAttr("disabled");
		$("#setAsDefault").removeClass("btn-disabled").addClass("btn-primary");
	}
	
	function toDisableSetAsDefault() {
		$("input[name=setAsDefault]").attr("disabled", "disabled");
		$("#setAsDefault").removeClass("btn-primary"); 
        $("#setAsDefault").addClass("btn-disabled");
	}
	
	function findError(data) {
		if (!isBlank(data.configNameError)) {
			$(".yesNoPopupErr").html(data.configNameError);
		} else {
			$(".yesNoPopupErr").empty();
		}
	}	
	
	function popupOnCancel(obj) {
		$("#popupPage").modal('hide');
	}
	
	function disableOkBtn() {
		var envlistSize = $('#multiselect :checkbox').size();
		var btnValue = $('#add').val();
		if (envlistSize < 1 && btnValue == 'Add') {
			disablePopupOkButton();
		}
	}
	
	function enablePopupOkButton() {
		<% if (permissions != null && permissions.canManageConfiguration()) { %>
			$('#createEnvironment').attr("disabled", false);
			$("#createEnvironment").addClass("btn-primary");
		<% } %>
	}
	
	function disablePopupOkButton() {
		$('#createEnvironment').attr("disabled", true);
		$("#createEnvironment").removeClass("btn-primary");
	}
</script>