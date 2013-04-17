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

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Configuration"%>

<%
	List<Configuration> configurations = (List<Configuration>) request.getAttribute(FrameworkConstants.REQ_CONFIGURATIONS);
%>

<form id="configListForm" style="height: 97%;">
	<% if (CollectionUtils.isEmpty(configurations)) { %>
		<div class="alert alert-block">
			<s:text name="configuration.error.message"/>
		</div>
	<% } else { %>
		<div class="build_table_div table_div">
			<div class="fixed-table-container">
				<div class="header-background header-backgroundTheme"> </div>
				<div class="fixed-table-container-inner config_subTab">
		        	<table cellspacing="0" class="zebra-striped">
		          		<thead>
			            	<tr>
								<th class="first">
			                		<div class="th-inner">
			                			<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this, $('.check'));">
			                		</div>
			              		</th>
			              		<th class="second">
			                		<div class="th-inner"><s:text name="lbl.name"/></div>
			              		</th>
			              		<th class="third">
			                		<div class="th-inner"><s:text name="lbl.desc"/></div>
		              			</th>
				            </tr>
		        	 	</thead>
		
		         		<tbody>
		         			<%
		         				for (Configuration configuration : configurations) {
		         			%>
					           	<tr>
			        	     		<td class="checkbox_list">
			                			<input type="checkbox" value="<%= configuration.getName() %>" class="check" name="nonEnvConfigNames" onclick="checkboxEvent($('.check'), $('#checkAllAuto'));">
			             			</td>
			           				<td class="second">
			           					<a style="color: #333333;" href="#" onclick="editConfiguration('', '<%= configuration.getType() %>','<%= configuration.getName() %>');" 
											name="edit"><%= configuration.getName() %></a>
	           						</td>
			             			<td style="width: 40%;">
		           						<%= configuration.getDesc() %>
			             			</td>
			             		</tr>
		             		<%
		             			}
		             		%>	
			          	</tbody>
			        </table>
		   		</div>
			</div>
		</div>
	<% } %>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		hideLoadingIcon();
		hideProgressBar();
		
		confirmDialog($("#deleteBtn"), '<s:text name="lbl.hdr.confirm.dialog"/>', '<s:text name="modal.body.text.del.configurations"/>', 'delete','<s:text name="lbl.btn.ok"/>');
	});
</script>