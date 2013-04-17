// from auto close
var showSuccessComplete = true;
var sessionParam = "";
function readerHandler(data, appId, actionType, pageUrl, progressConsoleObj) {
	sessionParam = "appId=";
	sessionParam = sessionParam.concat(appId);
	sessionParam = sessionParam.concat('&actionType=');
	sessionParam = sessionParam.concat(actionType);
	
	// from auto close
	if($.trim(data) == 'Test is not available for this project') {
		data = '<b>Test is not available for this project</b>';
		showSuccessComplete = false;
	}
	if($.trim(data) == '[INFO] ... tomcatProcess stopped. ReturnValue:1') { // When CI server starts it wont give EOF, forced it.
		data = 'EOF';
	}
	if ($.trim(data) == 'EOF') {
		hidePopuploadingIcon();
		$("#warningmsg").hide();
		$('#loadingDiv').hide();
		$('#buildbtn').prop("disabled", false);
	   // from auto close
//	   if(showSuccessComplete) {
//		   $("#build-output").append("Successfully Completed" + '<br>');
//	   }
		progressConsoleObj.prop('scrollTop', progressConsoleObj.prop('scrollHeight'));
	   
		if(actionType == "build") {
			refreshTable(appId);
		}
		return;
	}
	
	$("a[name='appTab']").each(function() {
		if ($(this).attr('class') === 'active') {
			if ($(this).attr('id') === 'buildView' && (actionType === 'SonarPath' || actionType === 'unit' || actionType === 'functional' || actionType === 'performance' || actionType === 'load')) {
				console.info('returning...');
				return;
			}
			progressConsoleObj.append(data + '<br>');
			progressConsoleObj.prop('scrollTop', progressConsoleObj.prop('scrollHeight')); 
			asyncHandler(appId, actionType, pageUrl, progressConsoleObj);
		}
	});
	
	// if apps tab is not present proceed async handler
	if($("a[name='appTab']").length == 0) {
		progressConsoleObj.append(data + '<br>');
		progressConsoleObj.prop('scrollTop', progressConsoleObj.prop('scrollHeight')); 
		  asyncHandler(appId, actionType, pageUrl, progressConsoleObj);
	}
}

function asyncHandler(appId, actionType, pageUrl, progressConsoleObj) {
   $.ajax({
        url : 'pages/applications/reader.jsp',
        type : "POST",
        data : {
            'appId' : appId,
            'actionType' : actionType
        },
        success : function(data) { 
        	// To Change Button for Run Against Source depending on Server 
            if ((pageUrl == "startServer" || pageUrl == "restartServer")) {
            	if ($.trim(data).indexOf("BUILD FAILURE") > -1 || $.trim(data).indexOf("Server startup failed") > -1) {
            		runAgainstSrcServerDown();
            	} else if ($.trim(data).indexOf("server started") > -1 || $.trim(data).indexOf("Running war") > -1) {
            		runAgainstSrcServerRunning();
            	}
            }
            readerHandler(data, appId, actionType, pageUrl, progressConsoleObj); 
        }
    });
}

function refreshTable() {
	loadContent('builds', '', $('#build-body-container'), getBasicParams());
}

$('.progressPopupClose, .close').bind('click', function () {
	loadContent("removeReaderFromSession", '', '', sessionParam, true, true, '');
});

document.onkeydown = function(evt) {
    evt = evt || window.event;
    if (evt.keyCode == 27) {
    	if ($("#additionalPopup").css("display") == "block") {
    		$("#additionalPopup").modal('hide');
			add_popupCancel($('#add_popupCancel'));
    	}
    	$("#popupPage, #progressPopup").modal('hide');
    	loadContent("removeReaderFromSession", '', '', sessionParam, true, true, '');
    }
};