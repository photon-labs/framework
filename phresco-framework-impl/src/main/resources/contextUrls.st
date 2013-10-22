<div class="middle_div">
 	<span>Context URLs</span>
    <div class="clear"></div>
</div>
<script type="text/javascript">
	var jsonFlag = true;
</script>
<div class="contextDivParent" id="contextDivParent">
	$myObject:{ obj |
		$obj.contextUrls: { contextUrlsObj |
		<script type="text/javascript">
			jsonFlag = false;
		</script>
			<div id="contextDiv" class="contextDivClass">
				<table class="table table-striped table_border table-bordered context_table border_div
" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<th colspan="4">
							<img width="15" height="15" title="Add Context" style="cursor:pointer;" border="0" alt="" src="themes/default/images/Phresco/small_plus_icon.png" name="contextAdd"
							 class="contextAdd">
							<div class="delete_icon removeContext">
								<img class="removeContextImg" src="themes/default/images/Phresco/delete_row.png">
							</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<label>HTTP Name<sup>*</sup></label>
								<input type="text" value="$contextUrlsObj.name$" name="httpName">
							</td>
							<td>
								<label>Additional Context</label>
								<input type="text" value="$contextUrlsObj.context$" name="context">
							</td>
							<td>
								<label>Type</label>
								<select id="contextType$contextUrlsObj.name$" name="contextType">
									<option value="GET">GET</option>
									<option value="POST">POST</option>
									<option value="PUT">PUT</option>
									<option value="DELETE">DELETE</option>
								</select>
							</td>
							<td>
								<label>Encoding</label>
								<select id="encodingType$contextUrlsObj.name$" name="encodingType">
									<option value="UTF-8">UTF-8</option>
									<option value="UTF-16">UTF-16</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table class="table" cellpadding="0" cellspacing="0" border="0">
									<tbody>
										<tr>
											<td><input type="checkbox" checkFlag="$contextUrlsObj.redirectAutomatically$" name="redirectAutomatically" class="redirectAutomatically">Redirect Automatically</td>
											<td><input type="checkbox" checkFlag="$contextUrlsObj.followRedirects$" name="followRedirects" class="followRedirects">Follow Redirects</td>
											<td><input type="checkbox" checkFlag="$contextUrlsObj.keepAlive$" name="keepAlive" class="keepAlive">Use Keep Alive</td>
										</tr>
										<tr>
											<td><input type="checkbox" checkFlag="$contextUrlsObj.multipartData$" name="multipartData" class="multipartData">Use Mulipart data</td>
											<td colspan="2"><input type="checkbox" checkFlag="$contextUrlsObj.compatibleHeaders$" name="compatibleHeaders" class="compatibleHeaders">Browser Compatible Headers</td>
										</tr> 
										<tr>
											<td>
												<input type="checkbox" checkflag="$contextUrlsObj.regexExtractor$" name="regexExtractor" class="regexExtractor">
												Regular Expression Extractor
											</td>
											<td></td><td></td>
										</tr>
										<tr class="regexTr">
											<td>
												<label>Apply To</label>
												<select id="applyTo$contextUrlsObj.name$" name="applyTo">
													<option value="main">Main sample</option>
													<option value="all">Main & sub samples</option>
													<option value="children">Sub sample</option>
												</select>
											</td>
											<td>
												<label>Response Field</label>
												<select id="responseField$contextUrlsObj.name$" name="responseField">
													<option value="false">Body</option>
													<option value="unescaped">Body(unescaped)</option>
													<option value="as_document">Body as document</option>
													<option value="true">Headers</option>
													<option value="URL">URL</option>
													<option value="code">Response code</option>
													<option value="message">Response message</option>
												</select>
											</td>
											<td>
												<label>Reference Name</label>
												<input type="text" value="$contextUrlsObj.referenceName$" name="referenceName">
											</td>
										</tr>
										<tr class="regexTr">
											<td>
												<label>Regular Expression</label>
												<textarea type="text" name="regex">$contextUrlsObj.regex$</textarea>
											</td>
											<td>
												<label>Template</label>
												<input type="text" value="$contextUrlsObj.template$" name="template">
											</td>
											<td>
												<label>Match No.</label>
												<input type="text" value="$contextUrlsObj.matchNo$" name="matchNo">
											</td>
										</tr>
										<tr class="regexTr">
											<td>
												<label>Default Value</label>
												<input type="text" value="$contextUrlsObj.defaultValue$" name="defaultValue">
											</td>
											<td></td><td></td>
										</tr>
									</tbody>
								</table> 
							</td>                                  	
						</tr>
						<tr>
							<td colspan="4">
								<table class="table table-striped table_border table-bordered header_table border_div
" cellpadding="0" cellspacing="0" border="0">
									<thead>
										<tr>
											<th>Headers</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" class="headerKey" placeholder="Key"><input class="headerValue" type="text" placeholder="Value"><input type="button" value="Add" class="btn btn_style headerKeyAdd"></td>
										</tr>
										<tr>
											<td colspan="4">
												<div class="add_test">
													$contextUrlsObj.headers: { headersObj |
														<div class="add_remove_test headers">$headersObj.key$ : $headersObj.value$<input type="hidden" value="$headersObj.key$" name="headerKey"> <input type="hidden" value="$headersObj.value$" name="headerValue"> <img src="themes/default/images/Phresco/remove_test.png" class="removeHeaders"></div>
													}$
												</div>
											</td>
										</tr>
									 </tbody>
								   </table>
								 </td>
							 </tr>
							 <tr>
								<td colspan="4">
									 <table class="table table-striped table_border table-bordered header_table border_div
" cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th>Parameters</th>
											</tr>
										</thead>
										<tbody>
											$contextUrlsObj.parameters: { parametersObj |
												<tr class="parameterRow">
													<td><input type="text" class="parameterName" value="$parametersObj.name$" placeholder="Name" name="parameterName"/>
													<textarea class="parameterValue" name="parameterValue" placeholder="Value">$parametersObj.value$</textarea>
													<input class="parameterEncode" type="checkbox"  name="parameterEncode" checkFlag="$parametersObj.encode$"/>Encode
													<img src="themes/default/images/Phresco/minus_icon.png" class="add_test_icon removeParamter" style="display:none;">
													<img class="add_test_icon addParameter" src="themes/default/images/Phresco/plus_icon.png"></td>
												</tr>
											}$				
										</tbody>
									</table>
								 </td>
							 </tr>
					</tbody>
				</table>
			</div>   
			<script type="text/javascript">
				document.getElementById("contextType$contextUrlsObj.name$").value = '$contextUrlsObj.contextType$';
				document.getElementById("encodingType$contextUrlsObj.name$").value = '$contextUrlsObj.encodingType$';
				document.getElementById("responseField$contextUrlsObj.name$").value = '$contextUrlsObj.responseField$';
				document.getElementById("applyTo$contextUrlsObj.name$").value = '$contextUrlsObj.applyTo$';
			</script>
		}$
	}$
</div>     
<script type="text/javascript">
	if(jsonFlag) {
		var newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');
		newTextBoxDiv.html("<table class='table table-striped table_border table-bordered context_table border_div
' cellpadding='0' cellspacing='0' 	border='0'><thead><tr><th colspan='4'><img width='15' height='10' style='cursor:pointer;' title='Add Context' border='0' alt='' src='themes/default/images/Phresco/small_plus_icon.png' name='contextAdd' class='contextAdd'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/Phresco/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Additional Context</label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically' checked>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr><tr><td><input type='checkbox'  name='regexExtractor' class='regexExtractor'>Regular Expression Extractor</td><td></td><td></td></tr><tr class='regexTr hideContent'><td><label>Apply To</label><select name='applyTo'><option value='main'>Main sample</option><option value='all'>Main & sub samples</option><option value='children'>Sub sample</option></select></td><td><label>Response Field</label><select name='responseField'><option value='false'>Body</option><option value='unescaped'>Body(unescaped)</option><option value='as_document'>Body as document</option><option value='true'>Headers</option><option value='URL'>URL</option><option value='code'>Response code</option><option value='message'>Response message</option></select></td><td><label>Reference Name</label><input type='text' value='' name='referenceName'></td></tr><tr class='regexTr hideContent'><td><label>Regular Expression</label><textarea type='text'  name='regex'></textarea></td><td><label>Template</label><input type='text' value='\$1\$' name='template'></td><td><label>Match No.</label><input type='text' value='1' name='matchNo'></td></tr><tr class='regexTr hideContent'><td><label>Default Value</label><input type='text' value='NOT FOUND' name='defaultValue'></td><td></td><td></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div
' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table border_div
' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><textarea class='parameterValue' value='' name='parameterValue' placeholder='Value'></textarea>&nbsp;<input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/Phresco/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/Phresco/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>");
		newTextBoxDiv.appendTo("#contextDivParent");
	}  else {
		checkEncodeCheckBox();
	}
	
	function checkEncodeCheckBox() {
		jQuery('.parameterEncode').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.redirectAutomatically').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.followRedirects').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.keepAlive').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.multipartData').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.compatibleHeaders').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
			} else {
				jQuery(this).prop("checked", false);
			}
		});
		jQuery('.regexExtractor').each(function() {
			var checkFlag = jQuery(this).attr("checkFlag");
			if (checkFlag == "true") {
				jQuery(this).prop("checked", true);
				jQuery(this).closest('tbody').find('.regexTr').show();
			} else {
				jQuery(this).prop("checked", false);
				jQuery(this).closest('tbody').find('.regexTr').hide();
			}
		});
	}
</script>
