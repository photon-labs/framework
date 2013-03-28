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
  
  
<%@ taglib uri="/struts-tags" prefix="s"%>
  
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
	List<String> files = (List<String>)request.getAttribute(FrameworkConstants.REQ_THEME_FILES);
%>
  
  <form id="themeBuilderList" class="marginBottomZero" style="height: 114%;overflow-x: hidden;overflow-y: hidden	;margin-top: 1px;">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="themeBuilderAdd" id="themeBuilderAdd" value="Create"/>

		<input type="button" class="btn" id="deleteBtn" disabled value="<s:text name='lbl.delete'/>" data-toggle="modal" href="#popupPage"/>
	</div>
	<s:if test="hasActionMessages()">
		<div class="alert alert-success alert-message" id="successmsg" >
			<s:actionmessage />
		</div>
	</s:if>
	<% if (CollectionUtils.isEmpty(files)) { %>
		<div class="alert alert-block">
			<s:text name='lbl.err.msg.theme.builder.empty'/>
		</div>
	<% } else {
	%>
	
	<div class="table_div_manual qtyTable_view" id="themeBuilderList" style="width:99%; overflow: auto;">
           	<div class="fixed-table-container responsiveFixedTableContainer qtyFixedTblContainer">
      			<div class="header-background"> </div>
	      		<div class="table-container-themeBuilderList" style="height: auto !important;">
			        <div style="overflow: auto;">
				        <table cellspacing="0" class="zebra-striped">
				          	<thead>
					            <tr>
					           		<th class="firstDiv" style="width:1%">
										<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto">
									</th>
									<th class="firstDiv" style="width:33%">
										<!-- <input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto"> -->
										<div id="thName" class="th-inner-test">Files</div>
									</th>
					            </tr>
				          	</thead>
				          	<tbody>
							<% for (String file : files) {	 %>			          	
				          		<tr>
				          			<td class="checkbox_list">
				          				<input type="checkbox" class="check" name="" value="">
				          			</td>
				          			<td class="">
				          				<a href="#" name="edit">
											<%= file %>
										</a>
				          			</td>
				          		</tr>
				          	<% } %>	
				          	</tbody>
				        </table>
					</div>
   				</div>
			</div>
		</div>
	<%		 
		}
	%>	
</form>	

<script type="text/javascript">
$(document).ready(function() {
	hideLoadingIcon();
});	

function editThemeBuilder(file) {
	showLoadingIcon();
	var params = getBasicParams();
	params = params.concat("&themeBuilderFile=");
	params = params.concat(file);
	loadContent("themeBuilderEdit", $("#themeBuilderList"), $("#subcontainer"), params, false, true);
}

$("#themeBuilderAdd").click(function() {
	showLoadingIcon();
	loadContent("themeBuilderAdd", $("#themeBuilderList"), $("#subcontainer"), '', false, true);	
});
</script>