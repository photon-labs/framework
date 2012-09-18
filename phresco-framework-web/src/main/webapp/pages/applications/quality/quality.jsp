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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes"%>

<%@ include file="../../userInfoDetails.jsp" %>

<%
    List<SettingsInfo> serverSettings = (List<SettingsInfo>)request.getAttribute(FrameworkConstants.REQ_ENV_SERVER_SETTINGS);
	String testTypeSelected = (String)request.getAttribute(FrameworkConstants.REQ_TEST_TYPE_SELECTED);
	ProjectInfo projectInfo = (ProjectInfo)request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO);
    String projectCode = projectInfo.getCode();
    String technology = projectInfo.getTechnology().getId();
%>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<form id="formQuality">
	<div id="subTabcontent">
		<div id="navigation">
			<ul>
				<li><a href="#" class="unselected" name="quality" id="unit"><s:text name="label.unit"/></a></li>
				<li><a href="#" class="unselected" name="quality" id="functional"><s:text name="label.funtional"/></a></li>
				<%
					if (!TechnologyTypes.IPHONES.contains(technology)) {
				%>
						<li><a href="#" class="unselected" name="quality" id="performance"><s:text name="label.performance"/></a></li>
				<%
					}
					if (!(TechnologyTypes.ANDROIDS.contains(technology) || TechnologyTypes.IPHONES.contains(technology))) {
				%>
						<li><a href="#" class="unselected" name="quality" id="load"><s:text name="label.load"/></a></li>
				<%
					}
				%>
			</ul>
		</div>
		
		<div id="subTabcontainer">
	
		</div>
	</div>
	
	<!-- Hidden Fields -->
	<input type="hidden" name="projectCode" value="<%= projectCode %>" />
</form>

<script type="text/javascript">
    $(document).ready(function() {
		var testType = "<%= testTypeSelected%>"
		
		if (testType == "null") {
			testType = "unit";
			$("a[id='unit']").attr("class", "selected");	
		} else {
			$("a[id='" + testType + "']").attr("class", "selected");
		}		

		changeTesting(testType);

		$("a[name='quality']").click(function() {
			$("a[name='quality']").attr("class", "unselected");
			$(this).attr("class", "selected");
			var testingType = $(this).attr("id");
			showLoadingIcon();
			changeTesting(testingType);
		});

	});

	//This function is to handle the change event for testing
	function changeTesting(testingType, fromPage) {
		$("#subTabcontainer").empty(); 
		var params = "testType=";
		params = params.concat(testingType);
        if (fromPage != undefined) {
            params = params.concat("&fromPage=");
            params = params.concat(fromPage);
        }
		performAction('testType', $('#formQuality'), $('#subTabcontainer'), '', params);
	}
	
	function getConfigNames() {
        var envName = $('#environments').val();
        var type = $('input:radio[name=jmeterTestAgainst]:checked').val();
        $.ajax({
            url : "getConfigNames",
            data: {
                'envName': envName,
                'type': type,
                'projectCode' : '<%= projectCode %>',
            },
            type: "POST",
            success : function(data) {
                if (data.configName != "") {
                    fillSelectData(type, data.configName);
                } else {
                    $('#' + type).find('option').remove();
                    $('#' + type).val("");
                }
            },
            async:false
        }); 
    }
    
    /** This method is to fill data in the appropriate controls **/
    function fillSelectData(type, data) {
    	if (type == "Server") {
    		$('#' + type).val(data);
    	} else {
	    	$('#' + type).find('option').remove();
            for (i in data) {
            	$('#' + type).append($("<option></option>").attr("value", data[i]).text(data[i]));
            }
    	}
    }
</script>