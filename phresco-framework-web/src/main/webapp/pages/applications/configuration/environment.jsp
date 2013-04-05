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

<% 
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
	List<Technology> allTechnologies  = (List<Technology>) request.getAttribute(FrameworkConstants.REQ_ALL_TECHNOLOGIES);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String configPath = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_PATH);
	Gson gson = new Gson();
	List<String> selectedappliesToTechs = null;
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
						<label id="envLabel<%= i %>" class="envLabel"><%= environment.getName() %><%= environment.isDefaultEnv() ? " (Default)" : "" %>
							<script type="text/javascript">
								var id = '<%= i %>';
								<%  if (environment.isDefaultEnv()) { %>
										$('#envLabel' + id).css('color','#1589FF');
								<%	} else { %>
										$("#envLabel" + id).css("color", '');
								<% 	} %>					
							</script>
						</label>
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
	
	$("#multiselectAppliesTo").scrollbars(); //JQuery scroll bar
	
	hidePopuploadingIcon();
	document.getElementById('createEnvironment').disabled = true; 
	$("#createEnvironment").removeClass("btn-primary");
	$('#errMsg').empty();
	
	<% if (FrameworkConstants.CONFIG.equals(fromPage)) { %>
		setAsDefaultBtnStatus();
	<% } %>
	
	var update = false;
	$('input[name="envNames"]').change(function() {
		if ($(this).is(':checked')) {
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.update'/>");
			update = true;
			var envData = $.parseJSON($(this).val());
			var appliesTos = envData.appliesTo;
			$('#multiselectAppliesTo ul li input[type=checkbox]').each(function() {
				if ($.inArray($(this).val(), appliesTos) != -1) {
					$(this).attr("checked", true);
				}
			});

			$("#envName").val(envData.name);
			$("#envDesc").val(envData.desc);
		} else {
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.add'/>");
			$("#envName").val("");
			$("#envDesc").val("");
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
		if(name == "") {
			$("#errMsg").html("<s:text name='popup.err.msg.empty.env.name'/>");
			$("#envName").focus();
			$("#envName").val("");
			setTimeOut();
			returnVal = false;
		} else {
			$('#multiselect ul li input[type=checkbox]').each(function() {
				var jsonData = $(this).val();
				var envs = $.parseJSON(jsonData);
				var envName = envs.name;
				if(!update) {
					if (name.trim().toLowerCase() == envName.trim().toLowerCase()) {
						$("#errMsg").html("<s:text name='popup.err.msg.env.name.exists'/>");
						setTimeOut();
						returnVal = false;
						return false;
					}
				}
				document.getElementById('createEnvironment').disabled = false;
				$("#createEnvironment").addClass("btn-primary");
			});
		}
		<% if (FrameworkConstants.SETTINGS.equals(fromPage)) { %>
				var selecedAppliesToSize = $('#multiselectAppliesTo :checked').size();
				document.getElementById('createEnvironment').disabled = true;
				$("#createEnvironment").removeClass("btn-primary");
				if(name == "") {
					document.getElementById('createEnvironment').disabled = true;
					$("#createEnvironment").removeClass("btn-primary");
					setTimeOut();
				} else if (selecedAppliesToSize < 1) {
					$("#errMsg").html("<s:text name='popup.err.msg.select.one.appliesTo'/>");
					setTimeOut();
					return false;
				} else {
					document.getElementById('createEnvironment').disabled = false;
					$("#createEnvironment").addClass("btn-primary");
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
				document.getElementById('createEnvironment').disabled = false;
				$("#createEnvironment").addClass("btn-primary");
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
			}
			document.getElementById('createEnvironment').disabled = false;
			$("#createEnvironment").addClass("btn-primary");
        });
        <% if (FrameworkConstants.CONFIG.equals(fromPage)) { %>
        	setAsDefaultBtnStatus(); 
        <% } %>
    });
	
	$('#up').click(function () {
		$('.selected').each(function() {
			document.getElementById('createEnvironment').disabled = false;
			$("#createEnvironment").addClass("btn-primary");
			$(this).prev().before($(this));
		});
	});
	
	$('#down').click(function () {
		var length = $('.selected').length;
		$('.selected').each(function() {
			document.getElementById('createEnvironment').disabled = false;
			$("#createEnvironment").addClass("btn-primary");
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
					+ '<label class="envLabel">' + value + '</label>';
	
			if ($("#multiselect ul").has("li").length === 0) {
				$("#multiselect ul").append('<li>' + checkbox + '</li>');
			} else {
				$("#multiselect ul li:last").after('<li>' + checkbox + '</li>');
			}
		}
		
		$("#envName").val("");
		$("#envDesc").val("");
		
		if ($('input[name="envNames"]:checkbox').is(':checked')) {
			$('input[name="envNames"]:checked').prop('checked', false);
			$('input[name="addBtn"]').val("<s:text name='lbl.btn.add'/>");
		}
		if ($('#multiselectAppliesTo :checkbox').is(':checked')) {
			$('#multiselectAppliesTo :checked').prop('checked', false);
		}
		
		<% if (FrameworkConstants.CONFIG.equals(fromPage)) { %>
			setAsDefaultBtnStatus();
		<% } %>
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
	
	function popupOnCancel(obj) {
		$("#popupPage").modal('hide');
	}
</script>