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
// from auto close
var showSuccessComplete = true;
function readerHandler(data, appId, actionType, pageUrl, progressConsoleObj) {
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