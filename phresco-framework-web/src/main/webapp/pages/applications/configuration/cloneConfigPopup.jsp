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
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.photon.phresco.model.*"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>

<% 
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String configPath = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_PATH);
    List<Environment> envInfoValues = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
    String configName = (String) request.getAttribute(FrameworkConstants.CLONE_FROM_CONFIG_NAME);
    String copyFromEnvName = (String)request.getAttribute(FrameworkConstants.CLONE_FROM_ENV_NAME);
	String configType = (String) request.getAttribute(FrameworkConstants.CLONE_FROM_CONFIG_TYPE);
	String configDesc = (String) request.getAttribute(FrameworkConstants.CLONE_FROM_CONFIG_DESC);
%>
	<form id="formClonePopup" class="form-horizontal">
		<fieldset class="popup-fieldset">
			<legend class="fieldSetLegend" >Clone Configuration</legend>
			
			<div class="control-group">
				<label class="control-label labelbold modallbl-color">
					<span class="mandatory">*</span>&nbsp;<s:text name='lbl.name' />
				</label>
				<div class="controls">
					<input type="text" name="configurationName" id="configurationName" class="span3"  placeholder="<s:text name='place.hldr.config.name'/>" 
					value="<%= configName %>" maxlength="30" title="<s:text name='title.30.chars'/>" />
				</div>
			</div>
			
	        <div class="control-group">
				<label class="control-label labelbold modallbl-color">
					&nbsp;&nbsp;<s:text name='lbl.desc'/>
				</label>
				<div class="controls">
					<textarea name="configDescription" id="configDescription" class="input-xlarge" 
						 maxlength="150" title="<s:text name='title.150.chars'/>" placeholder="<s:text name='place.hldr.config.desc'/>"><%= configDesc %></textarea>
				</div>
			</div>
	        
	        <div class="control-group">
                <label class="control-label labelbold modallbl-color">
					<span class="mandatory">*</span>&nbsp;<s:text name='label.environment'/>
				</label>
                  <div class="controls">
                       <select name="envName" id="configEnv" tabindex=3 class="xlarge cloneConfigToEnv">
						<%
						   for(Environment env : envInfoValues ) {
							   if(!env.getName().equals(copyFromEnvName) ) {
							   
	                    %>
	                       <option value="<%= env.getName() %>" title="<%= env.getDesc() %>" id="created"><%= env.getName() %></option>
	                    <% 
							   }
						}
	                    %>
                    </select>
                   </div>
               </div>
		</fieldset>

		<!-- Hidden fields -->
		<!-- <input type="hidden" name="cloneConfigStatus" value="true"> -->
		<input type="hidden" name="cloneFromEnvName" value="<%= copyFromEnvName %>">
		<input type="hidden" name="cloneFromConfigType" value="<%= configType %>">
		<input type="hidden" name="cloneFromConfigName" value="<%= configName %>">
	</form>

<script type="text/javascript">
	
</script>