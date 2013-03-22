/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */

	
// To restrict the user in typing the special charaters in projectCode and projectVersion
$('#projectCode, #projVersion').bind('input propertychange', function (e) {
	var str = $(this).val();
	str = checkForSplChrExceptDot(str);
	str = removeSpaces(str);
	$(this).val(str);
});

// project code is autofilled while giving project name in project creation
$('#projectName').bind('input propertychange', function (e) {
	var str = $(this).val();
	str = checkForSplChrExceptDot(str);
	str = removeSpaces(str);
	$('#projectCode').val(str);
	$('input[name=projectCode]').val(str);
});

//To restrict the user in typing the special charaters in App code
$('input[name=appLayerProjName]').live('input propertychange', function(e) {
	var str = $(this).val();
	str = checkForSplChrExceptDot(str);
	str = removeSpaces(str);
	$(this).val(str);
});	

function addProjectAccordion() {
    $('.siteaccordion').removeClass('openreg').addClass('closereg');
    $('.mfbox').css('display','none');
}

//opens accordian
function accordionOpen(thisObj, currentChkBoxObj) {
	var _tempIndex = $('.siteaccordion').index(thisObj);
	var isChecked = currentChkBoxObj;
	$(thisObj).removeClass('closereg').addClass('openreg');
	$(thisObj).next('.mfbox').eq(_tempIndex).slideDown(300,function() {});	
}  

//triggers while clicking on accordian or layer checkbox
function accordionClick(thisObj, currentChkBoxObj) {
	var _tempIndex = $('.siteaccordion').index(thisObj);
		
	var isChecked = currentChkBoxObj.is(":checked");
	if (isChecked) {
		$(thisObj).removeClass('closereg').addClass('openreg');
		$('.mfbox').eq(_tempIndex).slideDown(300,function() {});			
	} else {
		$(thisObj).removeClass('openreg').addClass('closereg');
		$('.mfbox').eq(_tempIndex).slideUp(300,function() {});
	}
}

//to open accordian in proj edit page
function preOpenAccordian() {
	if ($('input[value=app-layer]').is(':checked')) {
		accordionOpen('#appLayerControl', $('input[value=app-layer]'));			
	}

	if ($('input[value=web-layer]').is(':checked')) {
		accordionOpen('#webLayerControl', $('input[value=web-layer]'));		
	}
	
	if ($('input[value=mob-layer]').is(':checked')) {
		accordionOpen('#mobileLayerControl', $('input[value=mob-layer]'));		
	}
}

//To show the validation error messages
function findError(data) {
	if (!isBlank(data.projectNameError)) {
		showError($("#projectNameControl"), $("#projectNameError"), data.projectNameError);
	} else {
		hideError($("#projectNameControl"), $("#projectNameError"));
	}
	
	if (!isBlank(data.projectCodeError)) {
		showError($("#projectCodeControl"), $("#projectCodeError"), data.projectCodeError);
	} else {
		hideError($("#projectCodeControl"), $("#projectCodeError"));
	}
	
	if (!isBlank(data.projectVersionError)) {
		showError($("#projectVersionControl"), $("#projectVersionError"), data.projectVersionError);
	} else {
		hideError($("#projectVersionControl"), $("#projectVersionError"));
	}
	
	var appLayerChecked = false;
	if (!isBlank(data.appTechError)) {
		showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), data.appTechError);
	} else {
		appLayerChecked = true;
		hideErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"));
	}
	
	if (appLayerChecked) {
		if(!isBlank(data.appCodeError)) {
			if (data.fromTab == "edit") {
				$('input[value=app-layer]').prop("disabled",true);
			}
			$('input[temp="'+data.appLayerRowCount+'"]').focus();
			showErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"), data.appCodeError);
		} else {
			hideErrorInAccordion($("#appLayerControl"), $('#appLayerHeading'), $("#appLayerError"));
		}
	}
	
	if (!isBlank(data.webTechError)) {
		showErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"), data.webTechError);
	} else {
		hideErrorInAccordion($("#webLayerControl"), $('#webLayerHeading'), $("#webLayerError"));
	}
	
	if (!isBlank(data.mobTechError)) {
		showErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"), data.mobTechError);
	} else {
		hideErrorInAccordion($("#mobileLayerControl"), $('#mobileLayerHeading'), $("#mobileLayerError"));
	}
}

//To get versions for app-layer technologies
function getAppLayerTechVersions(obj) {
	var layerId = $(obj).attr("layer");
	var toBeFilledCtrlName = $(obj).attr("temp") + "_App_Version";
	var techGroupId = $('option:selected', obj).attr('additionalParam');
	var techId = $(obj).val();
	var appCodeObj = $(obj).parent().parent().find('input[name=appLayerProjName]');
	getTechVersions(layerId, techGroupId, toBeFilledCtrlName, techId, appCodeObj);
}

//To get the widgets of the selected web layer and load in the widget select box 
function getWebLayerWidgets(layerId, widgetObjName) {
	showLoadingIcon();
	objName = widgetObjName;
	var params = getBasicParams();
	params = params.concat("&layerId=");
	params = params.concat(layerId);
	loadContent("fetchWebLayerWidgets", $('#formCreateProject'), '', params, true, true);
}

var map = {};
//Success event functions
function successEvent(pageUrl, data) {
	hideLoadingIcon();
	//To fill the versions for the selected app technology --- used select control's ID to update corresponding row
	if (pageUrl == "fetchTechVersions") {
		fillSelectbox($("select[id='"+ objName +"']"), data.versions, "No Versions available");
	}
	
	//To fill the versions for the selected mobile technology
	if (pageUrl == "fetchMobTechVersions") {
		fillSelectbox($("select[name='"+ objName +"']"), data.versions, "No Versions available");
	}
	
	//To fill the widgets for the selected web
	if (pageUrl == "fetchWebLayerWidgets") {
		for (i in data.widgets) {
			fillOptions($("select[name='"+ objName +"']"), data.widgets[i].id, data.widgets[i].name);
			map[data.widgets[i].id] = data.widgets[i].techVersions;
		}
	}
}

//To fill the versions of the selected widget
function getWidgetVersions(obj, objectName) {
	var id = $(obj).val();
	fillSelectbox($("select[name='"+ objectName +"']"), map[id], "No Versions available");
}

//To enable or disable minus icon based on row count
function enableDisableMinusBtn(imgClass, minuImg) {
	if ($('.'+imgClass).size() > 1) {
		$('.' + minuImg).show();
	} else {
		$('.' + minuImg).hide();
	}
}

//removes app layer row
function removeAppLayer(obj) {
	$(obj).parent().parent().parent().remove();
	enableDisableMinusBtn('appLayerAdd', 'appLayerMinus');
}
