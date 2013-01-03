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

function loadJsonContent(url, jsonParam, containerTag, progressText, callSuccessEvent) {
	if (progressText !== undefined && !isBlank(progressText)) {
		showProgressBar(progressText);
	} 
	$.ajax({
		url : url,
		data : jsonParam,
		type : "POST",
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			loadData(data, containerTag, url, callSuccessEvent);
		}
	});	
}

function getBasicParams() {
	var params = $('#formCustomers').serialize();
	params = params.concat("&");
	params = params.concat($('#formAppMenu').serialize());
	
	return params;
}

function getBasicParamsAsJson() {
	var customersJson = $('#formCustomers').toJSON();
	var appMenuJson = $('#formAppMenu').toJSON();
	var jsonObject = $.extend(customersJson, appMenuJson);
	return '"customerId": "' + jsonObject.customerId + '", "projectId": "' + jsonObject.projectId + '", "appId": "' + jsonObject.appId + '"'; 
}

function getBasicParamsAsJsonObj() {
	var customersJson = $('#formCustomers').toJSON();
	var appMenuJson = $('#formAppMenu').toJSON();
	var jsonObject = $.extend(customersJson, appMenuJson);
	return jsonObject; 
}

function progressPopup(pageUrl, appId, actionType, form, callSuccessEvent, additionalParams, stopUrl) {
	$('#progressPopup').modal('show');//To show the progress popup
	$('#popup_progress_div').empty();
	$(".progressPopupClose").attr('id', pageUrl); // popup close action mapped to id
	
	if (stopUrl != undefined && !isBlank(stopUrl)) {
		$(".popupStop").show();
		$(".popupStop").attr('id', stopUrl); // popup stop action mapped to id
	}
	$("#popup_progress_div").empty();
	showPopuploadingIcon();
	readerHandlerSubmit(pageUrl, appId, actionType, form, callSuccessEvent, additionalParams, $("#popup_progress_div"));
}

function progressPopupAsSecPopup(url, appId, actionType, form, additionalParams, stopUrl) {
	setTimeout(function () {
		$('#progressPopup').modal('show');
    }, 600);
	$('#popup_progress_div').empty();
	$(".progressPopupClose").show();
	$(".progressPopupClose").attr('id', url); // popup close action mapped to id
	
	if (stopUrl != undefined && !isBlank(stopUrl)) {
		$(".popupStop").show();
		$(".popupStop").attr('id', stopUrl); // popup stop action mapped to id
	}
	$("#popup_progress_div").empty();
	showPopuploadingIcon();
	readerHandlerSubmit(url, appId, actionType, form, '', additionalParams, $("#popup_progress_div"));
}

function clickMenu(menu, tag, form, params) {
	menu.click(function() {
		showLoadingIcon();
		inActivateAllMenu(menu);
		activateMenu($(this));
		var selectedMenu = $(this).attr("id");
		if (selectedMenu != "appInfo" && selectedMenu != "features") {
			if (params === undefined || isBlank(params)) {
				params = "";
			}
			var additionalParam = $(this).attr('additionalParam');
			if (additionalParam !== undefined && !isBlank(additionalParam)) {
				params +=  '&'  + additionalParam;
			}
			loadContent(selectedMenu, form, tag, params);
		}
	});
}

function clickButton(button, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, '', tag);
	});
}

function loadContent(pageUrl, form, tag, additionalParams, callSuccessEvent, ajaxCallType, callbackFunction) {
	/*if (ajaxCallType == undefined || ajaxCallType == "") {
		ajaxCallType = true;
	}*/
	
	var params = getParameters(form, additionalParams);
	$.ajax({
		url : pageUrl,
		data : params,
		type : "POST",
		cache : false,
		success : function(data) {
			loadData(data, tag, pageUrl, callSuccessEvent, callbackFunction);
		},
		async: ajaxCallType
	});
}

function clickSave(pageUrl, params, tag, progressText) {
	if (progressText !== undefined) {
		showProgressBar(progressText);
	} 
	$.ajax({
		url : pageUrl,
		data : params,
		type : "POST",
		success : function(data) {
			hideProgressBar();
			loadData(data, tag);
		}
	});
}

function mandatoryValidation(pageUrl, form, additionalParams, phase, goal, actionType, appId, stopBtnAction) {
	var params = getBasicParams();
	params = params.concat("&phase=");
	params = params.concat(phase);
	params = params.concat("&goal=");
	params = params.concat(goal);
	params = params.concat("&");
	params = params.concat(getParameters(form, additionalParams));
	$.ajax({
		url : "dynamciParamMandatoryCheck",
		data : params,
		type : "POST",
		async: false,
		success : function(data) {
			if (data.errorFound != undefined && data.errorFound) {
				$(".yesNoPopupErr").html(data.errorMsg);
			} else {
				$(".yesNoPopupErr").empty();
				if (pageUrl == "build" || pageUrl == "deploy") {//build,deploy
					buildValidateSuccess(pageUrl, actionType);
				} else if (pageUrl == "startNode" || pageUrl == "startHub") {//start-hub, start-node
					$("#popupPage").modal('hide');
					progressPopupAsSecPopup(pageUrl, appId, actionType, form, params, stopBtnAction);
				} else if (pageUrl == "codeValidate" || pageUrl == "runLoadTest" || pageUrl == "runFunctionalTest") {//codevalidate, loadtest,functional test
					$("#popupPage").modal('hide');
					progressPopupAsSecPopup(pageUrl, appId, actionType, form, params);
				} else if (pageUrl == "runPerformanceTest") {//performance test
					runPerformanceTest();
				}
			}
		}
	});
}

function validate(pageUrl, form, tag, additionalParams, progressText, disabledDiv) {
	if (disabledDiv != undefined && disabledDiv != "") {
		enableDivCtrls(disabledDiv);
	}
	var params = getParameters(form, additionalParams);
	$.ajax({
		url : pageUrl + "Validate",
		data : params,
		type : "POST",
		success : function(data) {
			if (data.errorFound != undefined && data.errorFound) {
				findError(data);
			} else {
				clickSave(pageUrl, params, tag, progressText);
			}
		}
	});
}

function validateDynamicParam(successUrl, title, okUrl, okLabel, form, goal, needProgressPopUp, additionalParam) {
	var params = getBasicParams();
	params = params.concat("&goal=");
	params = params.concat(goal);
	if (additionalParam != undefined && !isBlank(additionalParam)) {
		params = params.concat("&");
		params = params.concat(additionalParam);
	}
	
	$.ajax({
		url : "validateDynamicParam",
		data : params,
		type : "POST",
		success : function(data) {
			hidePopuploadingIcon();
			if (data.paramaterAvailable != undefined && data.paramaterAvailable) {
				if (successUrl === "showStartHubPopUp") {
					loadContent("checkForHub", '', '', getBasicParams(), true, '', '');
				} else if (successUrl === "showStartNodePopUp") {
					loadContent("checkForNode", '', '', params, true, '', '');
				} else {
					//showPopuploadingIcon();
					yesnoPopup(successUrl, title, okUrl, okLabel, form, additionalParam);
				}
			} else if (needProgressPopUp) {
				if (data.availableParams != undefined && !isBlank(data.availableParams)) {
					additionalParam = additionalParam.concat("&");
					additionalParam = additionalParam.concat(data.availableParams);
				}
				window[okUrl](additionalParam);
			}
		}
	});
}

function yesnoPopup(url, title, okUrl, okLabel, form, additionalParam) {
	$('#popupPage').modal('show');//To show the popup
	showPopuploadingIcon();
	$('.popupClose').hide();
	$('#popupTitle').html(title); // Title for the popup
	$('.popupClose').hide(); //no need close button since yesno popup
	$('.popupOk, #popupCancel').show(); // show ok & cancel button
	enableButton($(".popupOk")); // enable button

	$(".popupOk").attr('id', okUrl); // popup action mapped to id
	if (okLabel !== undefined && !isBlank(okLabel)) {
		$('#' + okUrl).val(okLabel); // label for the ok button
	}
	
	var data = getBasicParams(); //customerid, projectid, appid

	var params = getParameters(form, '');
	if (!isBlank(params)) {
		data = data.concat("&");
		data = data.concat(params);
	}
	
	if (!isBlank(additionalParam)) {
		data = data.concat("&");
		data = data.concat(additionalParam);
	}
	
	$("#updateMsg").empty();
	$("#errMsg").empty();
	$('#successMsg').empty();
	$('#popup_div').empty();
	$('#popup_div').css("height", "300px");
	$('#popup_div').load(url, data); //url to render the body content for the popup
}

function additionalPopup(url, title, okUrl, okLabel, form, additionalParam, showLocationBox) {
	setTimeout(function () {
		$('#additionalPopup').modal('show');//To show the popup
    }, 600);
	
	$('#additional_popupTitle').html(title); // Title for the popup
	$('.add_popupClose').hide(); //no need close button since yesno popup
	$('.add_popupOk, #add_popupCancel').show(); // show ok & cancel button

	$(".add_popupOk").attr('id', okUrl); // popup action mapped to id
	
	if (showLocationBox !== undefined && showLocationBox) {//To show selected files location in text box in modal footer(for browse file tree)
		$('#browseSelectedLocation').show();
		$('#browseSelectedLocation').val('');
	} 
	
	if (okLabel !== undefined && !isBlank(okLabel)) {
		$('#' + okUrl).val(okLabel); // label for the ok button
	}
	
	var data = getBasicParams(); //customerid, projectid, appid

	var params = getParameters(form, '');
	if (!isBlank(params)) {
		data = data.concat("&");
		data = data.concat(params);
	}
	
	if (!isBlank(additionalParam)) {
		data = data.concat("&");
		data = data.concat(additionalParam);
	}
	
	$(".add_errorMsg").empty();//To clear error msg
	$('#additional_popup_body').empty();
	$('#additional_popup_body').load(url, data); //url to render the body content for the popup
}

function add_popupCancel() {
	setTimeout(function () {
		$('#popupPage').modal('show');
	}, 600);	
}

function validateJson(url, form, containerTag, jsonParam, progressText, disabledDiv) {
	if (disabledDiv != undefined && disabledDiv != "") {
		enableDivCtrls(disabledDiv);
	}
	
	$.ajax({
		url : url + "Validate",
		data : jsonParam,
		type : "POST",
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			if (data.errorFound != undefined && data.errorFound) {
				findError(data);
			} else {
				loadJsonContent(url, jsonParam, containerTag, progressText);
			}
		}
	});
}

function loadData(data, tag, pageUrl, callSuccessEvent, callbackFunction) {
	//To load the login page if the user session is not available
	if (data != undefined && data != "[object Object]" && data != "[object XMLDocument]" 
		&& !isBlank(data) && data.indexOf("Remember me") >= 0) {
		window.location.href = "logout.action";
	} else {
		if (callSuccessEvent != undefined && !isBlank(callSuccessEvent) && callSuccessEvent) {
			if (callbackFunction != undefined && !isBlank(callbackFunction)) {
				window[callbackFunction](data);
			} else {
				successEvent(pageUrl, data);
			}
		} else {
			tag.empty();
			tag.html(data);
			setTimeOut();
		}
	}
}

function readerHandlerSubmit(pageUrl, appId, actionType, form, callSuccessEvent, additionalParams, progressConsoleObj) {
	var params = getParameters(form, additionalParams);
    $.ajax({
        url : pageUrl,
        data : params,
        type : "POST",
        cache: false,
        success : function(data) {
        	//if (checkForUserSession(data)) {
            	$("#console_div").empty();
            	readerHandler(data, appId, actionType, pageUrl, progressConsoleObj);
            	if (callSuccessEvent != undefined && !isBlank(callSuccessEvent)) {
            		successEvent(pageUrl, data);
            	}
        	//}
        }
    });
}

//To get the parameters based on the availability
function getParameters(form, additionalParams) {
	var params = "";
	if (form !== undefined && form !== "" && !isBlank(form.serialize())) {
		params = form.serialize();
		if (!isBlank(additionalParams)) {
			params = params.concat("&");
			params = params.concat(additionalParams);
		}
	} else {
		params = additionalParams;
	}
	return params;
}

function inActivateAllMenu(allLink) {
	allLink.attr("class", "inactive");
}

function activateMenu(selectedMenu) {
	selectedMenu.attr('class', "active");
}

function checkAllHandler(parentCheckBox, childCheckBox) {
	 // add multiple select / deselect functionality
    $(parentCheckBox).click(function () {
          $(childCheckBox).attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(childCheckBox).click(function(){
 
        if($(childCheckBox).length == $(childCheckBox + ":checked").length) {
            $(parentCheckBox).attr("checked", "checked");
        } else {
            $(parentCheckBox).removeAttr("checked");
        }
 
    });
}

function checkAllEvent(currentCheckbox, childCheckBox, disable) {
	var checkAll = $(currentCheckbox).prop('checked');
	childCheckBox.prop('checked', checkAll);
	buttonStatus(checkAll);
	if (!checkAll) {
		disable = false;
	}
	toDisableAllCheckbox(currentCheckbox,childCheckBox, disable);
}

function checkboxEvent(childChkbxObj, parentChkbxObj) {
	var chkboxStatus = childChkbxObj.is(':checked');
	buttonStatus(chkboxStatus);
	if (childChkbxObj.length == childChkbxObj.filter(':checked').length) {
		parentChkbxObj.prop('checked', true);
	} else {
		parentChkbxObj.prop('checked', false);
	}
}

function buttonStatus(checkAll) {
	$('#deleteBtn').attr('disabled', !checkAll);
	if (checkAll) {
		$('#deleteBtn').addClass('btn-primary');
	} else {
		$('#deleteBtn').removeClass('btn-primary');
	}
}

function toDisableCheckAll() {
	if ($(".check:checkbox").length > 0 ) {
		$('#checkAllAuto').prop('disabled', false);
	} else {
		$('#checkAllAuto').prop('disabled', true);
	}
}

//To disable the given button object 
function disableButton(buttonObj) {
	buttonObj.removeClass('btn-primary');
	buttonObj.attr("disabled", true);
}

//To enable the given button object
function enableButton(buttonObj) {
	buttonObj.addClass('btn-primary');
	buttonObj.attr("disabled", false);
}

//Enables button
function enableControl(tagControl, css) {
	tagControl.attr("class", css);
	tagControl.attr("disabled", false);
}

// Disables button
function disableControl(tagControl, css) {
	tagControl.attr("class", css);
	tagControl.attr("disabled", true);
}

function toDisableAllCheckbox(currentCheckbox,childCheckBox, disable) {
	if($(currentCheckbox).is(':checked')){
		childCheckBox.prop('disabled', disable);
	} else {
		childCheckBox.prop('disabled', disable);
		childCheckBox.prop('checked', false);
	}
}

function showError(tag, span, errmsg) {
	tag.addClass("error");
	span.text(errmsg);
}

function hideError(tag, span) {
	tag.removeClass("error");
	span.empty();
}

function showErrorInAccordion(tag, headingObj, span, errmsg) {
	tag.addClass("accordion-error");
	headingObj.addClass("accordion-hdr-color");
	span.text(errmsg);
}

function hideErrorInAccordion(tag, headingObj, span) {
	tag.removeClass("accordion-error");
	headingObj.removeClass("accordion-hdr-color");
	span.empty();
}

function setTimeOut() {
	setTimeout(function() {
		$('#successmsg, #envSuccessmsg').fadeOut("slow", function () {
			$('#successmsg').hide();
		});
	}, 2000);
	
	setTimeout(function() {
		$('#errormsg').fadeOut("slow", function () {
			$('#errormsg').hide();
		});
	}, 2000);
}

function openFolder(path) {
	var params = "path=";
	params = params.concat(path);
	loadContent('openFolder', '', '', params, '', '', '');
}

function copyPath(path) {
	var params = "path=";
	params = params.concat(path);
	loadContent('copyPath', '', '', params, '', '', '');
}

function accordion() {
	/** Accordian starts **/
	var showContent = 0;	
    $('.siteaccordion').removeClass('openreg').addClass('closereg');
    $('.mfbox').css('display','none');
//    $('.mfbox').eq(showContent).css('display','block');
//    $('.siteaccordion').eq(showContent).attr("id", "siteaccordion_active");
    
    $('.siteaccordion').bind('click',function(e) {
        var _tempIndex = $('.siteaccordion').index(this);
        $('.siteaccordion').removeClass('openreg').addClass('closereg');
        $('.mfbox').each(function(e) {
            if ($(this).css('display') == 'block') {
                $(this).slideUp('300');
            }
        });
        if ($('.mfbox').eq(_tempIndex).css('display') == 'none') {
            $(this).removeClass('closereg').addClass('openreg');
            $('.mfbox').eq(_tempIndex).slideDown(300,function() {});
        }
    });
}

function getLoadingImgPath(imageFor) {
	var localstore = localStorage["color"];
	var imgSrc = "";
	
	if (localstore == "theme/red_blue/css/blue.css") {
		imgSrc = "theme/red_blue/images/loading_blue.gif";
	} else if (localstore == "theme/red_blue/css/red.css") {
		imgSrc = "theme/red_blue/images/loading_red.gif";
	} else if (imageFor != undefined && !isBlank(imageFor) && imageFor === "popup") {
		imgSrc = "theme/photon/images/loading_green_popUp.gif";
	} else {
		imgSrc = "theme/photon/images/loading_green.gif";
	}
	
	return imgSrc;
}

function showLoadingIcon() {
	$("#loadingIconImg").attr("src", "");
    $("#loadingIconDiv").show();
	$("#loadingIconImg").attr("src", getLoadingImgPath());
    disableScreen();
}

function hideLoadingIcon() {
	$("#loadingIconDiv").hide();
	enableScreen();
}

function showPopuploadingIcon() {
	$("#errMsg").empty(); // remove error message while displaying loading icon
    $(".popuploadingIcon").show();
    $(".popuploadingIcon").css("float", "left");
	$(".popuploadingIcon").attr("src", getLoadingImgPath("popup"));
}

function hidePopuploadingIcon() {
	$(".popuploadingIcon").hide();
}

function showProgressBar(progressText) {
	$("#progressnum").html(progressText);
	$(".modal-backdrop").show();
	$("#progressbar").show();
	setInterval(prog, 100);
}

function hideProgressBar() {
	$(".modal-backdrop").hide();
	$("#progressbar").hide();
}

// It allows A-Z, a-z, 0-9, - and _ 
function checkForSplChr(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
}

//It allows A-Z, a-z,
function allowAlpha(state) {
	return state.replace(/[^a-zA-Z]+/g, '');
}

//It allows A-Z, a-z, 0-9, - , _ , ., /, \, :
function checkForSplChrForString(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\.\-\_\/\:\\]+/g, '');
}

//It allows A-Z, a-z, 0-9, - , _ and .
function checkForSplChrExceptDot(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
}

//It allows A-Z, a-z, 0-9
function allowAlphaNum(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9]+/g, '');
}

//It allows 0-9,- 
function allowNumHyphen(numbr) {
	return numbr.replace(/[^0-9\-]+/g, '');
}

//It removes all empty spaces
function removeSpaces(str) {
	return str.replace(/\s+/g, '');
}

function applyTheme() {
	var theme = localStorage["color"];
	if (theme != null) {
		changeTheme(theme);
		showWelcomeImage(theme);
	} else {
		theme = "theme/photon/css/photon_theme.css";
		changeTheme(theme);
		showWelcomeImage(theme);
	}
}

function changeTheme(localstore) {
	$("#app_stylesheet").remove();
	if (localstore == "theme/red_blue/css/red.css") {
        $("link[title='phresco']").attr("href", localstore);
        $("link[id='phresco']").attr("href", "theme/red_blue/css/phresco.css");
        $("link[id='media-query']").attr("href", "theme/red_blue/css/media-queries.css");
        $("script[id='windowResizer']").attr("src", "js/windowResizer.js");
    } else if (localstore == "theme/red_blue/css/blue.css") {
        $("link[title='phresco']").attr("href", localstore);
        $("link[id='phresco']").attr("href", "theme/red_blue/css/phresco.css");
        $("link[id='media-query']").attr("href", "theme/red_blue/css/media-queries.css");
        $("script[id='windowResizer']").attr("src", "js/windowResizer.js");
    } else if (localstore == "theme/photon/css/photon_theme.css") {
        $("link[title='phresco']").attr("href", localstore);
        $("link[id='phresco']").attr("href", "theme/photon/css/phresco_default.css");
        $("link[id='media-query']").attr("href", "theme/photon/css/media-queries.css");
        $("script[id='windowResizer']").attr("src", "js/windowResizer_default.js");
    } 
}

function showWelcomeImage(localstore) {
	if (localstore == "theme/red_blue/css/blue.css") {
		$('.headerlogoimg').attr("src","theme/red_blue/images/phresco_header_blue.png");
		$('.phtaccinno').attr("src","theme/red_blue/images/acc_inov_blue.png");
		$('.welcomeimg').attr("src","theme/red_blue/images/welcome_photon_blue.png");
		$('.logoimage').attr("src","theme/red_blue/images/phresco_header_blue.png");
	} else if (localstore == "theme/red_blue/css/red.css") {
		$('.headerlogoimg').attr("src","theme/red_blue/images/phresco_header_red.png");
		$('.phtaccinno').attr("src","theme/red_blue/images/acc_inov_red.png");
		$('.welcomeimg').attr("src","theme/red_blue/images/welcome_photon_red.png");
		$('.logoimage').attr("src","theme/red_blue/images/phresco_header_red.png");
	} else if (localstore == "theme/photon/css/photon_theme.css") {
		$('.headerlogoimg').attr("src","theme/photon/images/photon_phresco_logo.png");
		/*$('.phtaccinno').attr("src","theme/photon/images/acc_inov_green.png");*/
		$('.welcomeimg').attr("src","theme/photon/images/welcome_photon.png");
	}
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

//To disable the screen by showing an overlay
function disableScreen() {
	$(".modal-backdrop").show();
}

//To enable the screen by hiding an overlay
function enableScreen() {
	$(".modal-backdrop").hide();
}

//To fill the pom details in the textbox if available while uploading the files
function fillTextBoxes(responseJSON, type, fileName) {
	if (type === "pluginJar"){
		$('#jarDetailsDivPopup').show();
	} else if (type === "videoFile") {
		$('#videoDetailsDiv').show();
	} else {
		$('#jarDetailsDiv').show();
	}
	if (responseJSON.mavenJar) {
		disableEnableTextBox(responseJSON.groupId, responseJSON.artifactId, responseJSON.version, true, type, fileName);
	} else {
		disableEnableTextBox('', '', '', false, '', '');
	}
}

function disableEnableTextBox(groupId, artifactId, jarVersion, isEnable, type, fileName) {
	if (type === "pluginJar") {
		var groupid = "grouId" ;
		var artifId = "artifId" ;
		var versnId = "versnId" ;
		var fileDetParentDiv = $(document.createElement('div')).attr("id", fileName).attr("class","fileClass");
		fileDetParentDiv.html("<div style='float: left; margin-right: 13px;'><label class='control-label labelbold' style='color:black;'> Group Id </label>" +
				           "<div class='controls'><input id='" +groupid +"' class='groupId' class='input-xlarge' name='"+fileName+"_groupId" +"' maxlength='40' title='40 Characters only' type='text'  value='" + groupId +"' >" +
				           "</div></div>"); 
		fileDetParentDiv.append("<div style='float: left; margin-right: 7px;'> <label class='control-label labelbold' style='color:black;'> " +
				              "Artifact Id </label><div class='controls'><input id='" +artifId +"'class='artifactId' class='input-xlarge' name='"+fileName+"_artifactId" +"' maxlength='40' title='40 Characters only' type='text' value='" + artifactId +"' >" +
				              "</div></div>");
		fileDetParentDiv.append("<div style='float: left; margin-right: 7px;'><label class='control-label labelbold' style='color:black;'> Version </label> "+
		                   "<div class='controls'><input id='" +versnId +"'class='jarVersion' name='"+fileName+"_version" +"'  maxlength='30' title='30 Characters only' class='input-xlarge' type='text' value='" +jarVersion +"' > " +
		                   "</div></div>");
		fileDetParentDiv.append("</div>")
		fileDetParentDiv.appendTo("#jarDetailsDivPopup");
	} else {
		$('.groupId').val(groupId).attr('disabled', isEnable);
		$('.artifactId').val(artifactId).attr('disabled', isEnable);
		$('.jarVersion').val(jarVersion).attr('disabled', isEnable);
		$('input[name=groupId]').val(groupId);
		$('input[name=artifactId]').val(artifactId);
		$('input[name=jarVersion]').val(jarVersion);
	}
}

function isiPad() {
    return (
        (navigator.platform.indexOf("iPhone") != -1) ||
        (navigator.platform.indexOf("iPad") != -1)
    );
}

//This method is calling from triggering list.jsp
function showDeleteConfirmation(confirmMsg) {
	confirmDialog("block", confirmMsg);
}

function copyToClipboard(data) {
    var params = "copyToClipboard=";
    params = params.concat(data);
    $.ajax({
		url : "copyToClipboard",
		data : params,
		type : "POST",
		success : function() {
		}
	});
}

//trim the long content
function textTrim(obj) {
    var val = $(obj).text();
    $(obj).attr("title", val);
    var len = val.length;
    if(len > 50) {
        val = val.substr(0, 50) + "...";
        return val;
    }
    return val;
}

/*$(document).keydown(function(e) {
    // ESCAPE key pressed
	if (e.keyCode == 27) {
	   showParentPage();
    }
});*/

//Shows the parent page
function showParentPage() {
	enableScreen();
	$('#popupPage').hide();
}

//To disable the given control
function disableCtrl(control) {
	control.attr("disabled", true);
}

//To enable the given control
function enableCtrl(control) {
	control.removeAttr("disabled");
}

function enableDivCtrls(disabledDiv) {
	disabledDiv.removeAttr("disabled");
}

/** To fill the data in the select box **/
function fillSelectbox(obj, data, selectTxt, selectVal) {
	obj.empty();
	if (!isBlank(selectTxt) && !isBlank(selectVal)) {
		obj.append($("<option></option>").attr("value", selectVal).text(selectTxt));
	}
	if (isBlank(data)) {
		obj.append($("<option></option>").attr("value", "").text(selectTxt));
	}
	if (data != undefined && !isBlank(data)) {
		for (i in data) {
			obj.append($("<option></option>").attr("value", data[i]).text(data[i]));
		}
	}
}

/** To fill the given text and value into the select box **/
function fillOptions(obj, value, text, selectTxt) {
	obj.append($("<option></option>").attr("value", value).text(text));
}

function confirmDialog(obj, title, bodyText, okUrl, okLabel) {
	obj.click(function() {
		$("#errMsg").empty();
		$('#popupTitle').html(title); // Title for the popup
		$('.popupClose').hide();
		
		$(".popupOk").attr('id', okUrl);
	
		$('#popup_div').css("height", "50px");
		$('#popup_div').html(bodyText);
		
		if (okLabel !== undefined && !isBlank(okLabel)) {
			$('a [class ~= "popupOk"]').attr('id', okUrl);
			$('#' + okUrl).val(okLabel); // label for the ok button
		}
	});
	
	$('#' + okUrl).click(function() {
		popupOnOk(okUrl); // this function will be kept in where the yesnoPopup() called
	});
}

//to dynamically update dependancy data into controls 
function constructElements(data, pushToElement, isMultiple, controlType) {
	if ($("#"+pushToElement+"Control").prop('tagName') == 'FIELDSET') {
		constructFieldsetOptions(data, pushToElement+"Control");
	} else if (isMultiple === undefined && controlType === undefined) {
		constructMultiSelectOptions(data, pushToElement);
	} else if (isMultiple === "false") {
		constructSingleSelectOptions(data, pushToElement);
	} else if (controlType !== undefined ) {
		//other controls ( text box)
	}
}

function constructFieldsetOptions(dependentValues, pushToElement) {
	var fileName, filePath;
	$("#avaliableSourceScript").empty();
	for(i in dependentValues) {
		fileName = dependentValues[i].value.substring(dependentValues[i].value.lastIndexOf('#') + 1);
		filePath = dependentValues[i].value.replace('#SEP#','/');
		
		var optionElement = "<option value='"+ filePath +"'>"+fileName+"</option>";
		$("#avaliableSourceScript").append(optionElement);
	}
}

function constructSingleSelectOptions(dependentValues, pushToElement) {
	var control = $('#'+ pushToElement + ' option:selected');
	var selected = control.val();
	var additionalParam = control.attr('additionalParam'); 
	var editbleComboClass = $('#'+ pushToElement + ' option').attr('class');
	$("#" + pushToElement).empty();
	var selectedStr = "";
	var dynamicFirstValue = dependentValues[0].value;
	var isEditableCombo = false;
	if (editbleComboClass == "jecEditableOption") {//convert to editable combobox
		var optionElement = "<option class='jecEditableOption'>Type or Select from list</option>";
		$("#" + pushToElement).append(optionElement);
		isEditableCombo = true;
	}
	for(i in dependentValues) {
		if(dependentValues[i].value == selected) {
			selectedStr = "selected";
		} else {
			selectedStr = "";
		}
		if (dependentValues[i].dependency != undefined && !isBlank(dependentValues[i].dependency)) {
			var dynamicDependency = "dependency=" + dependentValues[i].dependency;
			$("<option></option>", {value: dependentValues[i].value, text: dependentValues[i].value, additionalParam: dynamicDependency}).appendTo("#" + pushToElement);	
		} else {
			$("<option></option>", {value: dependentValues[i].value, text: dependentValues[i].value, additionalParam: additionalParam}).appendTo("#" + pushToElement);			
		}
	}
	
	if (isEditableCombo) {// execute only for jec combo box
		$('#'+ pushToElement + ' option[value="'+ dynamicFirstValue +'"]').prop("selected","selected");//To preselect select first value
	}
}

/**
 * To dynamically update dependancy data into multi select checkbox
 * 
 * Step 1. Reads already existing values in a array
 * Step 2. Clear the content of multiselect control
 * Step 3. create ul element
 * Step 4. iterate resultant json data  
 * Step 5. check whether result data matches already existed data and if it exist make it as checked
 * Step 6. construct li element with result data
 * Step 7. append constructed ul element multi select control 
 * 
 */
function constructMultiSelectOptions(dependentValues, pushToElement) {
	//See step 1
	var selected = new Array();
	$('#'+pushToElement+' input:checked').each(function() {
	    selected.push($(this).val());
	});
	//See step 2
	$("#" + pushToElement).empty();
	//See step 3
	var ulElement =$(document.createElement('ul'));
	var checkedStr = "";
	//See step 4
	for (i in dependentValues) {
		//See step 5
		if($.inArray(dependentValues[i].value, selected) > -1){
			checkedStr = "checked";
		} else {
			checkedStr = "";
		}
		//See step 6
		var liElement = "<li><input type='checkbox' name='"+ pushToElement +"' value='" + dependentValues[i].value + "' class='popUpChckBox'"+checkedStr+">"+dependentValues[i].value+"</li>";
		ulElement.append(liElement);
	}
	//See step 7
	$("#"+pushToElement).append(ulElement);
}

//To disable the given button object 
function disableButton(buttonObj) {
    buttonObj.removeClass('btn-primary');
    buttonObj.attr("disabled", true);
}

//To enable the given button object
function enableButton(buttonObj) {
    buttonObj.addClass('btn-primary');
    buttonObj.attr("disabled", false);
}

//To check whether the url is valid or not
function isValidUrl(url) {
    if (/^(http|https|ftp|git):\/\/[a-z0-9]+([-.]{1}[a-z0-9]+)*.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/i.test(url)) {
      return false;
    } else {
      return true;
    }   
}

//Used for the dynamic parameter
//To set the previous dependency for the control in onfocus
function setPreviousDependent(self) {
	var additionalParam = $('option:selected', self).attr('additionalParam');
	if (additionalParam != undefined) {
		$(self).attr('additionalParam', 'previous' +  additionalParam.charAt(0).toUpperCase() + additionalParam.slice(1));
	}
}

// This will parse a delimited string into an array of
// arrays. The default delimiter is the comma, but this
// can be overriden in the second argument.
function CSVToArray(strData, strDelimiter) {
	// Check to see if the delimiter is defined. If not,
	// then default to comma.
	strDelimiter = (strDelimiter || ",");

	// Create a regular expression to parse the CSV values.
	var objPattern = new RegExp(
		(
			// Delimiters.
			"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

			// Quoted fields.
			"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

			// Standard fields.
			"([^\"\\" + strDelimiter + "\\r\\n]*))"
		),
		"gi"
		);


	// Create an array to hold our data. Give the array
	// a default empty first row.
	var arrData = [[]];

	// Create an array to hold our individual pattern
	// matching groups.
	var arrMatches = null;


	// Keep looping over the regular expression matches
	// until we can no longer find a match.
	while (arrMatches = objPattern.exec( strData )){

		// Get the delimiter that was found.
		var strMatchedDelimiter = arrMatches[ 1 ];

		// Check to see if the given delimiter has a length
		// (is not the start of string) and if it matches
		// field delimiter. If id does not, then we know
		// that this delimiter is a row delimiter.
		if (
			strMatchedDelimiter.length &&
			(strMatchedDelimiter != strDelimiter)
			){

			// Since we have reached a new row of data,
			// add an empty row to our data array.
			arrData.push( [] );

		}


		// Now that we have our delimiter out of the way,
		// let's check to see which kind of value we
		// captured (quoted or unquoted).
		if (arrMatches[ 2 ]){

			// We found a quoted value. When we capture
			// this value, unescape any double quotes.
			var strMatchedValue = arrMatches[ 2 ].replace(
				new RegExp( "\"\"", "g" ),
				"\""
				);

		} else {

			// We found a non-quoted value.
			var strMatchedValue = arrMatches[ 3 ];

		}


		// Now that we have our value string, let's add
		// it to the data array.
		arrData[ arrData.length - 1 ].push( strMatchedValue );
	}

	// Return the parsed data.
	return( arrData );
}

//For Execute Sql
//execute sql codes
function buttonAdd() {
	addDbWithVersions();
	$('#avaliableSourceScript > option:selected').appendTo('#selectedSourceScript');
}
	
function buttonRemove() {
	updateDbWithVersionsForRemove();
}

function buttonRemoveAll() {
	updateDbWithVersionsForRemoveAll();
}

//To move up the values
function moveUp() {
	$('#selectedSourceScript option:selected').each( function() {
		var newPos = $('#selectedSourceScript  option').index(this) - 1;
		if (newPos > -1) {
			$('#selectedSourceScript  option').eq(newPos).before("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
			$(this).remove();
		}
	});
}

//To move down the values
function moveDown() {
	var countOptions = $('#selectedSourceScript option').size();
	$('#selectedSourceScript option:selected').each( function() {
		var newPos = $('#selectedSourceScript  option').index(this) + 1;
		if (newPos < countOptions) {
			$('#selectedSourceScript  option').eq(newPos).after("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
			$(this).remove();
		}
	});
}

function updateDbWithVersionsForRemoveAll() {
	var dbType = $('#dataBase').val();
	$("#selectedSourceScript > option").each(function(i,available) {
		var availableSource = $(available).val();
		var availableText = $(available).text();
		var jsonValue = $('#fetchSql').val();
		var allFiles = jQuery.parseJSON(jsonValue);
		var matchType = availableSource.match(dbType);
		if (matchType == dbType) {
			$("#selectedSourceScript option[value='"+ availableSource +"']").remove();
			var alreadyExists = isAvailable(availableSource);
			if (alreadyExists == false) {
				$('#avaliableSourceScript').append("<option value="+ availableSource +">" + availableText + "</option>");
			}
			$.each(allFiles, function(dbType, value) {
				 for(i=0; i<allFiles[dbType].length; i++){
					if(allFiles[dbType][i] === availableSource){
						allFiles[dbType].splice(i,1);
					}
			    }
			});
			
		 var newJsonValue = JSON.stringify(allFiles);
		 $('#fetchSql').val(newJsonValue);
		}
	});
}



function buttonAddAll() {
	var dbType = $('#dataBase').val();
	var jsonValue = $('#fetchSql').val();
	$("#avaliableSourceScript > option").each(function(i, available) {
		var sqlFiles = [];
		sqlFiles.push($(available).val());
		var selected = $(available).val();
		var selectedText = $(available).text();
		var alreadyExists = $("#selectedSourceScript option[value='"+ selected + "']").length > 0;
		if (alreadyExists == true) {
			$("#avaliableSourceScript > option").each(function(i, available) {
				var select = $(available).val();
				$("#avaliableSourceScript option[value='"+ select +"']").remove();
			});
			return false;
		}
		
		$("#avaliableSourceScript option[value='"+ selected +"']").remove();
		$('#selectedSourceScript').append("<option value="+ selected +">" + selectedText + "</option>");
		
		if (jsonValue === undefined || isBlank(jsonValue)) {
			jsonValue = '{"' + dbType + '" : ["' + sqlFiles.join(',') + '"]}';
		} else {
			var allFiles = jQuery.parseJSON(jsonValue);
			var dbFiles = allFiles[dbType];
			if (dbFiles !== undefined && !isBlank(dbFiles)) {
				sqlFiles = dbFiles.concat(sqlFiles);
				allFiles[dbType] = sqlFiles;
				jsonValue = JSON.stringify(allFiles);
			} else {
				jsonValue = jsonValue.substring(0, jsonValue.length - 1);
				jsonValue = jsonValue + ', "' + dbType + '" : ["' + sqlFiles.join('","') + '"]}';
			}
		}
	});
	$('#fetchSql').val(jsonValue);
	$('#avaliableSourceScript > option:selected').appendTo('#selectedSourceScript');
}

function addDbWithVersions() {
	var dbType = $('#dataBase').val();
	var jsonValue = $('#fetchSql').val();
	$("#avaliableSourceScript :selected").each(function(i, available) {
		var sqlFiles = [];
		sqlFiles.push($(available).val());
		var selected = $(available).val();
		
		var alreadyExists = $("#selectedSourceScript option[value='"+ selected + "']").length > 0;
		if (alreadyExists == true) {
			$("#avaliableSourceScript option[value='"+ selected +"']").remove();
			return false;
		}
		
		if (jsonValue === undefined || isBlank(jsonValue)) {
			jsonValue = '{"' + dbType + '" : ["' + sqlFiles.join(',') + '"]}';
		} else {
			
			var allFiles = jQuery.parseJSON(jsonValue);
			var dbFiles = allFiles[dbType];
			if (dbFiles !== undefined && !isBlank(dbFiles)) {
				sqlFiles = dbFiles.concat(sqlFiles);
				allFiles[dbType] = sqlFiles;
				jsonValue = JSON.stringify(allFiles);
			} else {
				jsonValue = jsonValue.substring(0, jsonValue.length - 1);
				jsonValue = jsonValue + ', "' + dbType + '" : ["' + sqlFiles.join('","') + '"]}';
			}
		}
	});
	$('#fetchSql').val(jsonValue);
}

	function updateDbWithVersionsForRemove() {
		$("#selectedSourceScript option:selected").each(function(i, optionSelected) {
			var jsonValue = $('#fetchSql').val();
			var allFiles = [];
			var selected = $(optionSelected).val();
			var selectedText = $(optionSelected).text();
			allFiles = jQuery.parseJSON(jsonValue);
			var dbType = $('#dataBase').val();
			var entry = document.getElementById("selectedSourceScript");
			var selectedSource = entry.options[entry.selectedIndex].value;
			
			var matchType = selectedSource.match(dbType);
			
			if (dbType == matchType) {
				var available = isAvailable(selected);
				if(!available) {
					$('#avaliableSourceScript').append("<option value="+ selected +">" + selectedText + "</option>");
				}
				$("#selectedSourceScript option[value='"+ selected +"']").remove();
				var flag = isAvailable(selected);
				if (flag == true) {
					$.each(allFiles, function(dbType, value) {
						 for(i=0; i<allFiles[dbType].length; i++){
							if(allFiles[dbType][i] === selected){
								allFiles[dbType].splice(i,1);
							}
					    }
					});
				}
				var newJsonValue = JSON.stringify(allFiles);
				 $('#fetchSql').val(newJsonValue);
				}
		});
	}
	
	function isAvailable(selected) {
		var flag = false;
		$("#avaliableSourceScript > option").each(function() {
			var availableSource = this.value ;
			if (availableSource == selected) {
				flag = true;
				return false;
			}
		});
		return flag;
	}

function hideDbWithVersions() {
	// getting existing data list
	var nameSep = new Array();
	nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
	for (var i=0; i < nameSep.length - 1; i++) {
		var addedDbs = nameSep[i].split("#VSEP#");
		var addedSqlName = addedDbs[1].split("#NAME#");
		if($('#dataBase').val() == addedDbs[0]) {
			$("#avaliableSourceScript option[value='" + addedSqlName[0] + "']").remove();
		}
	}
	// show corresponding DB sql files
	showSelectedDBWithVersions();
}

function showSelectedDBWithVersions() {
	$('#selectedSourceScript').empty();
	var nameSep = new Array();
	nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
	for (var i=0; i < nameSep.length - 1; i++) {
		var addedDbs = nameSep[i].split("#VSEP#");
		var addedSqlName = addedDbs[1].split("#NAME#");
		if($('#dataBase').val() == addedDbs[0]) {
			$('#selectedSourceScript').append($("<option></option>").attr("value",addedSqlName[0]).text(addedSqlName[1])); 
		}
	}
}

function hideControl(controls) {
	for (i in controls) {
		$('#' + controls[i] + 'Control').hide();
		$('.' + controls[i] + 'PerformanceDivClass').hide();
	}
}

function showControl(controls) {
	for (i in controls) {
		$('#' + controls[i] + 'Control').show();
		$('.' + controls[i] + 'PerformanceDivClass').show();//for performance context urls
	}
}

function changeChckBoxValue(obj) {
	if ($(obj).is(':checked')) {
		$(obj).val("true");
	} else {
		$(obj).val("false");
	}
}

//trim the long content
function textTrim(obj, maxLength) {
    var val = $(obj).text();
    $(obj).attr("title", val);
    var len = val.length;
    if(len > maxLength) {
        val = val.substr(0, maxLength) + "...";
        return val;
    }
    return val;
}

//triggered when the plus btn is clicked to add a new row
function addRow(obj) {
	var removeIconTd = $(document.createElement('td')).attr("class", "borderForLoad noBorder");
	var removeIconAnchr = $(document.createElement('a'));
	var removeIcon = $(document.createElement('img')).attr("class", "add imagealign").attr("src", "images/icons/minus_icon.png").attr("onclick", "removeRow(this)");
	removeIconAnchr.append(removeIcon);
	removeIconTd.append(removeIconAnchr);
	var columns = $(obj).closest('table').children('tbody').children('tr:first').html();
	var newRow = $(document.createElement('tr')).attr("class", "borderForLoad");
	newRow.append(columns);
	newRow.append(removeIconTd);
	newRow.appendTo("#propTempTbodyForHeader");
}

//triggered when the minus btn is clicked to add a remove the current row
function removeRow(obj) {
	$(obj).closest('tr').remove();
}

function disableUploadButton(controlObj) {
	controlObj.find("input[type='file']").attr('disabled', 'disabled');
	controlObj.find($(".qq-upload-button")).removeClass("btn-primary qq-upload-button").addClass("disabled");
}

function enableUploadButton(controlObj) {
	controlObj.find("input[type='file']").attr('disabled', false);
	controlObj.find($(".btn")).removeClass("disabled").addClass("btn-primary qq-upload-button");
}