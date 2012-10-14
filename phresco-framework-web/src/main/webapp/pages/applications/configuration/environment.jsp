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

<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<% 
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String fromTab = (String)request.getAttribute(FrameworkConstants.SETTINGS_FROM_TAB);
   	List<Environment> envInfoValues = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
%>

<form id="environment" class="form-horizontal">
	<div class="control-group">
		<s:label key="lbl.name" cssClass="control-label labelbold modallbl-color" theme="simple"/>
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
			<textarea placeholder="<s:text name='place.hldr.configTemp.add.desc'/>" class="input-xlarge" 
				name="envDesc" maxlength="150" title="<s:text name='title.150.chars'/>" placeholder="<s:text name='place.hldr.env.desc'/>">
			</textarea>
			<input type="button" value="<s:text name='lbl.btn.add'/>" tabindex=3 id="add" class="btn btn-primary addButton">
		</div>
	</div>

	<fieldset class="popup-fieldset">
		<legend class="fieldSetLegend" ><s:text name="lbl.added.environments"/></legend>
		<div class="popupTypeFields" id="typefield">
            <div class="multilist-scroller multiselect" style="height: 95px; width:300px;">
                <ul>
	       			<li>
						<input type="checkbox" name="technology" value="One" class="check techCheck" style="margin: 3px 8px 6px 0;">One
					</li>
				</ul>
            </div>
		</div>
		<div class="popupimage">
			<img src="images/icons/top_arrow.png" title="<s:text name='lbl.title.moveup'/>" id="up" class="imageup"><br>
			<img src="images/icons/delete.png"" title="<s:text name='lbl.title.remove'/>" id="remove" class="imageremove"><br>
			<img src="images/icons/btm_arrow.png" title="<s:text name='lbl.title.movedown'/>" id="down" class="imagedown">
		</div>
		<div class="defaultButton">
			<input type="button" value="Set as Default" tabindex=5 id="setAsDefault" class="btn btn-primary">
		</div>
	</fieldset>
			<!--<fieldset class="popup-fieldset" style="height: 115px;">
				<legend class="fieldSetLegend" ><s:text name="label.added.environment"/></legend>
				<select name="selectedEvn" id="selectedEvn" tabindex=4 class="xlarge" multiple="multiple" style="height: 88px; width: 300px; float: left; margin-left: 100px;" >
					<%
						 for(Environment env : envInfoValues ) {
					%>
	                	   <option value="<%= env.getName() %>" title="<%= env.getDesc() %>" <%= env.isDefaultEnv() ? "disabled" : ""%> id="created"><%= env.getName() %></option>
		            <% 
				       }
		            %>
				</select>
				<div style="float: left; margin-left: 5px;">
					<img src="images/icons/top_arrow.png" title="Move up" id="up" style="cursor: pointer;"><br>
					<img src="images/icons/delete(1).png" title="Remove" id="remove" style="cursor: pointer; margin-top: 20px;"><br>
					<img src="images/icons/btm_arrow.png" title="Move down" id="down" style="cursor: pointer; margin-top: 16px;" >
				</div>
				<div id="setAsDefaultDiv" style="float: right; margin-right: 35px;">
				    <input type="button" value="Set as Default" tabindex=5 id="setAsDefault" class="primary btn" style="margin-top: 30px;">
				</div>
			</fieldset>-->
</form>


<!-- selectedEnvs hidden field will be updated with the newly added environments after clicking the Add button -->
<input type="hidden" id="selectedEnvs" name="selectedEnvs" value="">
<input type="hidden" id="deletableItems" name="deletableItems" value="">
<script type="text/javascript">
	//escPopup();
	
	var name =  "";
	var desc =  "";
	var deletableEnvs = "";
	var deletableItems = "";
	$(document).ready(function() {
		
		if(<%= "settings".equals(fromTab) %>) {
			$('#setAsDefaultDiv').hide();
		}
		
		$("#envName").focus();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		//To add the entered values into the selectbox
		$("#Add").click(function() {
			emptyMessages();
			
			var returnVal = true;
			name =($.trim($("#envName").val()));
			desc = $("#envDesc").val();
			if(name == "") {
				$("#errMsg").html("Enter Environment Name");
				$("#envName").focus();
				$("#envName").val("");
				returnVal = false;
			} else {
				$("#selectedEvn option").each(function() {
					if (name.trim().toLowerCase() == $(this).val().trim().toLowerCase()) {
						$("#errMsg").html("Environment Name already exists");
						returnVal = false;
						return false;
					}
				});				
			}
			
			if (returnVal) {
				validateEnv(name, desc);				
			}
		});
		  
		
         $("#setAsDefault").click(function() {
             var setAsDefaultEnvsSize = $('#selectedEvn option:selected').size();
             if(setAsDefaultEnvsSize < 1 || setAsDefaultEnvsSize > 1) {
            	 showHidePopupMsg($("#popupErrorMsg"), "Please select one environment");
             }
             
             var setAsDefaultEnvs = new Array();
             $('#selectedEvn option:selected').each( function() {
            	 setAsDefaultEnvs.push($(this).val());
             });
             
             var params = "";
             if (!isBlank($('form').serialize())) {
                 params = $('form').serialize() + "&";
             }
             params = params.concat("setAsDefaultEnv=");
             params = params.concat(setAsDefaultEnvs.join(","));           
             performAction('setAsDefault', params, '', true); 
        });
		
		//To remove the entered value
		$('#remove').click(function() {
			var deletetableEnvsArr = new Array();
			deletableItems = "";
			var nameSep = new Array();
			var hiddenFieldVal = $("#selectedEnvs").val();
			nameSep = hiddenFieldVal.split("#SEP#");
			var finalValue = "";
			
			// To remove the Environments from the list box which is not in the XML
		    $('#selectedEvn option:selected').each( function() {
		    	var currentVal = $(this).val(); // selected option
				for(var i=0; i < nameSep.length; i++) {
	                var avail = nameSep[i].indexOf(currentVal);
	                if(avail > -1) {
	                    delete nameSep[i];
	                    $(this).remove();
	                } else {
	                    if(nameSep[i] != "") {
	                        finalValue = finalValue + nameSep[i] + "#SEP#";
	                    }
	                }
	            } 
				deletetableEnvsArr.push(currentVal);
				$("#selectedEnvs").val(finalValue);
			});
		    deletableItems = deletetableEnvsArr.join(",");
		    validateRemove(deletableItems);
		});
		
		//To move up the values
		$('#up').bind('click', function() {
			$('#selectedEvn option:selected').each( function() {
				var newPos = $('#selectedEvn  option').index(this) - 1;
				if (newPos > -1) {
					$('#selectedEvn  option').eq(newPos).before("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
					$(this).remove();
				}
			});
		});
		
		//To move down the values
		$('#down').bind('click', function() {
			var countOptions = $('#selectedEvn option').size();
			$('#selectedEvn option:selected').each( function() {
				var newPos = $('#selectedEvn  option').index(this) + 1;
				if (newPos < countOptions) {
					$('#selectedEvn  option').eq(newPos).after("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
					$(this).remove();
				}
			});
		});
		
		$("#save").click(function() {
			emptyMessages();
			
			var envs = $("#selectedEnvs").val();
			var deletableData = $('#deletableItems').val();
           	if(deletableData == "" ) {
           		if(envs == "") {
	                $("#errMsg").html("Add Environments");
	            } else {
	            	generateXML(envs, deletableData);
	            }
           	} else {
           		generateXML(envs, deletableData);
           	}
        });
		
		$("#envName").bind('input propertychange',function(e){ 	//envName validation
	     	var name = $(this).val();
	     	name = checkForSplChr(name);
	     	$(this).val(name);
		});
	});

	function generateXML(envs, deletableData) {
		var url = "";
		var conatiner = "";
		if(<%= "settings".equals(fromTab) %>) {
			url = "createSettingsEnvironment";
			conatiner = $("#container");
		}
		else {
			url = "createEnvironment";
			conatiner = $("#tabDiv");
		}
        showParentPage();
       	var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		if (envs != undefined  && envs != "") {
			params = params.concat("envs=");
			params = params.concat(envs);			
		}
		if (deletableData != undefined && deletableData != "") {
			if (envs != undefined  && envs != "") {
				params = params.concat("&deletableEnvs=");
			} else {
				params = params.concat("deletableEnvs=");
			}
			params = params.concat(deletableData);
		}
		performAction(url, params, conatiner);
    }
	
	function validateEnv(envs, desc) {
       	var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		params = params.concat("envs=");
		params = params.concat(envs);
		if(<%= "settings".equals(fromTab) %>) {
		    performAction('checkDuplicateEnvSettings', params, '', true);
		} else {
			performAction('checkDuplicateEnv', params, '', true);	
		}
	}
	
	function successValidateEnv(data) {
    	if(data.envError == undefined) {
    		$("#errMsg").empty();
    		var hiddenFieldVal = $("#selectedEnvs").val();
			hiddenFieldVal = hiddenFieldVal + name + "#DSEP#" + desc + "#SEP#";
			$('#selectedEnvs').val(hiddenFieldVal);
			$("#selectedEvn").append($("<option></option>").attr("value", name).attr("title", desc).text(name));
			$("#envName").val("");
			$("#envDesc").val("");
		} else {
		    $("#errMsg").html(data.envError);
		}
	}
	
	function emptyEnvVal(evt) {
		var evt = (evt) ? evt : ((event) ? event : null);
		var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
		if ((evt.keyCode == 13) && (node.type=="text"))  {return false;}
	}

	document.onkeypress = emptyEnvVal; 
	
	function successEvent(pageUrl, data) {
		if (pageUrl == "checkDuplicateEnvSettings" || pageUrl == "checkDuplicateEnv") {
			successValidateEnv(data);
		}
		if (pageUrl == "checkForRemoveSettings" || pageUrl == "checkForRemove" || pageUrl == "setAsDefault") {
			emptyMessages();
			
			if (data.envError != undefined) {
                //when setting a default , after successfull operation , it will come here
                if(data.flag && pageUrl == "setAsDefault") {
                    setDefaultAsDisable();
                    $("#reportMsg").html(data.envError);
                } else {
                    $("#errMsg").html(data.envError);
                    deletableItems = "";
                }
			} else {
				$("#errMsg, #reportMsg").html("");
				var availDeletableItems = $("#deletableItems").val();
				if(availDeletableItems == ""){
					availDeletableItems = availDeletableItems + deletableItems;
				} else {
					availDeletableItems = availDeletableItems + "," + deletableItems;
				}
				
				$('#deletableItems').val(availDeletableItems);
				var deletableData = $('#deletableItems').val();  // This is for removing data from popup 
				var deleteThis = new Array();
				deleteThis = deletableData.split(",");
				for (deletableEnv in deleteThis ) {
					$("#selectedEvn option[value='" + deleteThis[deletableEnv] + "']").remove();
				}
			}
		}
	}
	
	function emptyMessages() {
		$("#errMsg, #reportMsg").html("");
	}
	
	function setDefaultAsDisable() {
         $('#selectedEvn option').each( function() {
        	$(this).prop('disabled', false);
         });
         
         $('#selectedEvn option:selected').prop('disabled', true);
         $('#selectedEvn option:selected').prop('selected', false);
	}	
	
	function validateRemove(deletableItems) {
       	var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		params = params.concat("deletableEnvs=");
		params = params.concat(deletableItems);
		if(<%= "settings".equals(fromTab) %>) {
		    performAction('checkForRemoveSettings', params, '', true);
		} else {
			performAction('checkForRemove', params, '', true);	
		}
	}
</script>