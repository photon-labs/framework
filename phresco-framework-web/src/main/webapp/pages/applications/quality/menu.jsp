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

<script type="text/javascript">
    $(document).ready(function() {
    	var params = getBasicParams();
    	clickMenu($("a[name='qualityTab']"), $("#subTabcontainer"), $('#formAppMenu, #formCustomers'));//handles the click event of the quality sub tabs
		loadContent("unit", '', $("#subTabcontainer"), params, '', true);
		activateMenu($("#unit"));
	});
</script>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<div id="subTabcontent">
	<div id="navigation" class="qualitymenutabs">
		<ul>
			<li>
				<a href="#" class="active" name="qualityTab" id="unit"><s:label key="lbl.quality.menu.unit" theme="simple"/></a>
			</li>
			<li>
				<a href="#" class="inactive" name="qualityTab" id="functional"><s:label key="lbl.quality.menu.funtional" theme="simple"/></a>
			</li>
			<li>
				<a href="#" class="inactive" name="qualityTab" id="performance"><s:label key="lbl.quality.menu.performance" theme="simple"/></a>
			</li>
			<li>
				<a href="#" class="inactive" name="qualityTab" id="load"><s:label key="lbl.quality.menu.load" theme="simple"/></a>
			</li>
		</ul>
	</div>
	<div id="subTabcontainer">

	</div>
</div>