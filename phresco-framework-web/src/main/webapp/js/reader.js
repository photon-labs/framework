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
function readerHandler(data, appId, actionType, pageUrl) {
	// from auto close
	if($.trim(data) == 'Test is not available for this project') {
		data = '<b>Test is not available for this project</b>';
		showSuccessComplete = false;
	}
	if($.trim(data) == '[INFO] ... tomcatProcess stopped. ReturnValue:1') { // When CI server starts it wont give EOF, forced it.
		data = 'EOF';
	}

   if ($.trim(data) == 'EOF') {
	   $("#warningmsg").hide();
	   $('#loadingDiv').hide();
	   $('#buildbtn').prop("disabled", false);
	   // from auto close
//	   if(showSuccessComplete) {
//		   $("#build-output").append("Successfully Completed" + '<br>');
//	   }
	   $('#console_div').prop('scrollTop', $('#console_div').prop('scrollHeight'));
	   
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
			$("#console_div").append(data + '<br>');
			$('#console_div').prop('scrollTop', $('#console_div').prop('scrollHeight')); 
			asyncHandler(appId, actionType, pageUrl);
		}
	});
	
	// if apps tab is not present proceed async handler
	if($("a[name='appTab']").length == 0) {
		  $("#console_div").append(data + '<br>');
		  $('#console_div').prop('scrollTop', $('#console_div').prop('scrollHeight')); 
		  asyncHandler(appId, actionType, pageUrl);
	}
}

function asyncHandler(appId, actionType, pageUrl) {
   $.ajax({
        url : 'pages/applications/reader.jsp',
        type : "POST",
        data : {
            'appId' : appId,
            'actionType' : actionType
        },
        success : function(data) { 
            if ((pageUrl == "restartServer" || pageUrl == "runAgainstSource") && 
            		($.trim(data)).indexOf("Compilation failure") > -1) {
            	runAgainstSrcSDown ();
            }
            readerHandler(data, appId, actionType, pageUrl); 
        }
    });
}

function refreshTable() {
	loadContent('builds', '', $('#build-body-container'), getBasicParams());
 }