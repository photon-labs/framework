/*
 * ###
 * Service Web Archive
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
 
function yesnoPopup(modalObj, url, title, okUrl, okLabel) {
	modalObj.click(function() {
		$('#popupClose').hide();

		$('#popupTitle').html(title); // Title for the popup
		$('#popupClose').hide(); //no need close button since yesno popup
		$('.popupOk, #popupCancel').show(); // show ok & cancel button
	
		$(".popupOk").attr('id', okUrl); // popup action mapped to id
		if (okLabel !== undefined && !isBlank(okLabel)) {
			$('#' + okUrl).html(okLabel); // label for the ok button 
		}
		
		var data = "";
		data = getBasicParams(); //customerid, projectid, appid
		data = data.concat("&");
		var additionalParam = $(this).attr('additionalParam'); //additional params if any
		data = data.concat(additionalParam);

		$('.modal-body').empty();
		$('.modal-body').load(url, data); //url to render the body content for the popup
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

function progressPopup(btnObj, pageUrl, title, appId, actionType, form, callSuccessEvent, additionalParams) {
	btnObj.click(function() {
		if (title !== undefined && !isBlank(title)) {
			$('#popupTitle').html(title);
		}
		$('.modal-body').empty();
		$('#popupClose').show();
		$('.popupOk, #popupCancel').hide(); // hide ok & cancel button
		readerHandlerSubmit(pageUrl, appId, actionType, form, callSuccessEvent, additionalParams);
	});
	$('#popupClose').click(function() {
		popupClose(pageUrl); // this function will be kept in where the progressPopup() called
	});
}

function progressPopupAsSecPopup(url, title, appId, actionType, form, additionalParams) {
	setTimeout(function () {
		$('#popupPage').modal('show')
    }, 600);
	$('#popupTitle').html(title);
	$('.modal-body').empty();
	$('.popupOk').hide(); // hide ok & cancel button
	$('#popupCancel').hide();
	$('#popupClose').show();

	readerHandlerSubmit(url, appId, actionType, form, '', additionalParams);
}

function clickMenu(menu, tag, form, additionalParam) {
	menu.click(function() {
		showLoadingIcon();
		inActivateAllMenu(menu);
		activateMenu($(this));
		var selectedMenu = $(this).attr("id");
//		var additionalParam = $(this).attr('additionalParam');
		loadContent(selectedMenu, form, tag, additionalParam);
	});
}

function clickButton(button, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, '', tag);
	});
}

function loadContent(pageUrl, form, tag, additionalParams, callSuccessEvent) {
//	showLoadingIcon(tag);
	var params = getParameters(form, additionalParams);
	$.ajax({
		url : pageUrl,
		data : params,
		type : "POST",
		success : function(data) {
			loadData(data, tag, pageUrl, callSuccessEvent);
		}
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

function loadData(data, tag, pageUrl, callSuccessEvent) {
	//To load the login page if the user session is not available
	if (data != undefined && data != "[object Object]" && data != "[object XMLDocument]" 
		&& !isBlank(data) && data.indexOf("Remember me") >= 0) {
		window.location.href = "logout.action";
	} else {
		if (callSuccessEvent != undefined && !isBlank(callSuccessEvent) && callSuccessEvent) {
			successEvent(pageUrl, data);
		} else {
			tag.empty();
			tag.html(data);
			setTimeOut();
		}
	}
}

function readerHandlerSubmit(pageUrl, appId, actionType, form, callSuccessEvent, additionalParams) {
	var params = getParameters(form, additionalParams);
//	showParentPage();
//	enableScreen();
    $.ajax({
        url : pageUrl,
        data : params,
        type : "POST",
        cache: false,
        success : function(data) {
        	//if (checkForUserSession(data)) {
            	$("#modal-body").empty();
            	readerHandler(data, appId, actionType, pageUrl);
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
	if (form != undefined && form != "" && !isBlank(form.serialize())) {
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
		$('#successmsg').fadeOut("slow", function () {
			$('#successmsg').remove();
		});
	}, 2000);
	
	setTimeout(function() {
		$('#errormsg').fadeOut("slow", function () {
			$('#errormsg').remove();
		});
	}, 2000);
}

function accordion() {
	/** Accordian starts **/
	var showContent = 0;	
    $('.siteaccordion').removeClass('openreg').addClass('closereg');
    $('.mfbox').css('display','none');
    
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

function showLoadingIcon() {
	var src = "theme/photon/images/loading_blue.gif";
	var theme =localStorage["color"];
    if (theme == undefined || theme == "theme/photon/css/red.css") {
    	src = "theme/photon/images/loading_red.gif";
    }
    $("#loadingIconDiv").show();
	$("#loadingIconImg").attr("src", src);
    disableScreen();
}

function hideLoadingIcon() {
	$("#loadingIconDiv").hide();
	enableScreen();
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

//It allows A-Z, a-z, 0-9, - , _ and .
function checkForSplChrExceptDot(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
}

//It allows A-Z, a-z, 0-9
function allowAlphaNum(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9]+/g, '');
}

//It allows 0-9,- and +
function allowNumHyphenPlus(numbr) {
	return numbr.replace(/[^0-9\-\+]+/g, '');
}

function changeTheme() {
  	if (localStorage["color"] != null) {
        $("link[title='phresco']").attr("href", localStorage["color"]);
    } else {
        $("link[title='phresco']").attr("href", "theme/photon/css/red.css");
    } 
}

function showWelcomeImage() {
	var theme = localStorage['color'];
	if (theme == "theme/photon/css/blue.css") {
		$("link[id='theme']").attr("href", localStorage["color"]);
		$('.headerlogoimg').attr("src","theme/photon/images/phresco_header_blue.png");
		$('.phtaccinno').attr("src","theme/photon/images/acc_inov_blue.png");
		$('.welcomeimg').attr("src","theme/photon/images/welcome_photon_blue.png");
	} else if (theme == null || theme == undefined || theme == "theme/photon/css/red.css") {
		$("link[id='theme']").attr("href", "theme/photon/css/red.css");
		$('.headerlogoimg').attr("src","theme/photon/images/phresco_header_red.png");
		$('.phtaccinno').attr("src","theme/photon/images/acc_inov_red.png");
		$('.welcomeimg').attr("src","theme/photon/images/welcome_photon_red.png");
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
function fillSelectbox(obj, data, selectTxt) {
	obj.empty();
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

function confirmDialog(title, bodyText, okUrl, okLabel) {
	$("#deleteBtn").click(function() {
//		disableScreen();
		$('#popupTitle').html(title); // Title for the popup
		$('#popupClose').hide();
		
		$(".popupOk").attr('id', okUrl);
	
		$('.modal-body').html(bodyText);
		
		if (okLabel !== undefined && !isBlank(okLabel)) {
			$('a [class ~= "popupOk"]').attr('id', okUrl);
			$('#' + okUrl).html(okLabel); // label for the ok button 
		}
	});
	
	$('#' + okUrl).click(function() {
		popupOnOk(okUrl); // this function will be kept in where the yesnoPopup() called
	});
}

//to dynamically update dependancy data into controls 
function constructElements(data, pushToElement, isMultiple, controlType) {
	if (isMultiple === undefined && controlType === undefined) {
		constructMultiSelectOptions(data, pushToElement);
	} else if (isMultiple === false) {
		// for single select list box
	} else if (controlType !== undefined ) {
		//other controls ( text box)
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
function constructMultiSelectOptions(data, pushToElement) {
	//See step 1
	var selected = new Array();
	$('#'+pushToElement+' input:checked').each(function() {
	    selected.push($(this).val());
	});
	//See step 2
	$("#"+pushToElement).empty();
	//See step 3
	var ulElement =$(document.createElement('ul'));
	var checkedStr = "";
	//See step 4
	for (i in data.dependentValues) {
		//See step 5
		if($.inArray(data.dependentValues[i].value, selected) > -1){
			checkedStr = "checked";
		} else {
			checkedStr = "";
		}
		//See step 6
		var liElement = "<li><input type='checkbox' name='"+ pushToElement +"' value='" + data.dependentValues[i].value + "' class='popUpChckBox'"+checkedStr+">"+data.dependentValues[i].value+"</li>";
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
