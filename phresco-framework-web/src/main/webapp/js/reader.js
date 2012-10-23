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
function readerHandler(data, appId, testType, pageUrl) {
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
	   $('#build-output').prop('scrollTop', $('#build-output').prop('scrollHeight'));
	   
	   if(testType == "build") {
		   refreshTable();
	   }
	   return;
   }
	   
	$("a[name='appTabs']").each(function(index, value) {
		if ($(this).attr('class') === 'selected') {
			if ($(this).attr('id') === 'buildView' && (testType === 'SonarPath' || testType === 'unit' || testType === 'functional' || testType === 'performance' || testType === 'load')) {
				console.info('returning...');
				return;
				
			}
		   $("#build-output").append(data + '<br>');
		   $('#build-output').prop('scrollTop', $('#build-output').prop('scrollHeight')); 
			asyncHandler(appId, testType, pageUrl);
		}
	});
	
	// if apps tab is not present proceed async handler
	if($("a[name='appTabs']").length == 0) {
		  $("#build-output").append(data + '<br>');
		  $('#build-output').prop('scrollTop', $('#build-output').prop('scrollHeight')); 
		  asyncHandler(appId, testType, pageUrl);
	}
}


function asyncHandler(appId, testType, pageUrl) {
   $.ajax({
        url : 'pages/applications/reader.jsp',
        type : "POST",
        data : {
            'appId' : appId,
            'testType' : testType
        },
        success : function(data) { 
            if ((pageUrl == "restartServer" || pageUrl == "runAgainstSource") && 
            		($.trim(data)).indexOf("Compilation failure") > -1) {
            	runAgainstSrcSDown ();
            }
            readerHandler(data, appId, testType, pageUrl); 
        }
    });
}

function refreshTable() {
	loadContent('builds', '', $('#build-body-container'), getBasicParams());
 }