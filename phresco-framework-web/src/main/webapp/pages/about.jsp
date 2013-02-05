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

<div id="versionInfo" class="hideContent">
	<div class="abt_logo">
		<img src="images/phresco.png" alt="logo" class="abt_logo_img">
	</div>
	
	<div class="abt_content">
	Phresco is a next-generation development framework of 
	frameworks.It is a platform for creating next generation
	web,mobile and multi-channel presences leveraging existing
	investments combined with accepted industry best practices.
	</div>
</div>

<div class="latestVersionContent">
	<div class="curVersContent">
		<div class="lftDiv">
			<s:text name="label.framework.current.version"/>
		</div>
		
		<div class="rhtDiv">
			<span id="CurrentVersion"></span>
		</div>
	</div>
	<div class="verInfo">
		<div class="lftDiv">
			<s:text name="label.latest.version"/>
		</div>
		
		<div class="rhtDiv">
			<span id="LatestVersion"></span>
		</div>
	</div>	
	<div class="verInfo">
		<span id="updateAvailableInfo"></span>
	</div>
</div>

<div class="abt_modal-body updatedInfoContent">
	<span id="updatedVersionInfo"></span>
</div>

<!-- Hidden Fields -->
<input type="hidden" name="latestVersion" value="" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#updateMsg').html('&copy; 2013.Photon Infotech Pvt Ltd');
		$("#versionInfo").show();
		$(".latestVersionContent").hide();
		$(".updatedInfoContent").hide();
		showVersionInfo();
	});
	
	function showVersionInfo() {
		loadContent("versionInfo", '', '', '', true, false,'');
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "versionInfo") {
			$('#phrescoVersion').html(data.currentVersion);
			$('#CurrentVersion').html(data.currentVersion);
     		$('#LatestVersion').html(data.latestVersion);
			$('input[name=latestVersion]').val(data.latestVersion);
     		$('#updateAvailableInfo').html(data.message);
  		 	$("#popupTitle").html("Phresco " + data.currentVersion);
  		 	if (data.updateAvail) {
				enableButton($("#updateAvailable"));
			} else {
				disableButton($("#updateAvailable"));
			}
			$('.popupLoadingIcon').hide();
			hidePopuploadingIcon();	
		} else if (pageUrl == "updateVersion") {
			$(".latestVersionContent").hide();
			$(".updatedInfoContent").show();
			$("#updatePhresco").hide();
			$('#popupCancel').val("ok");
			$('#updateMsg').empty();
			if (data.updateVersionStatus != undefined && !isBlank(data.updateVersionStatus) && data.updateVersionStatus) {
				$("#updatedVersionInfo").html(data.updateVersionMessage);
			} else {
				$("#updatedVersionInfo").html(data.updateVersionMessage);
			}
		}
	}
	
	function popupOnOk(obj) {
		var okUrl = $(obj).attr("id");
		if (okUrl === "updateAvailable") {
			$('#versionInfo').hide();
			$('.latestVersionContent').show();
			$('#updateAvailable').attr("id","updatePhresco");
			$('#updatePhresco').val("Yes");
			$('#popupCancel').val("No");
			$('#updateMsg').html('Do u want to update ?');
		} else if (okUrl === "updatePhresco") {
			var latestVersion = $('input[name=latestVersion]').val();
			var params = "latestVersion="
			params = params.concat(latestVersion);
			loadContent("updateVersion", '', '', params, true, false,'');
		}
	}
</script>