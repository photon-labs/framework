<div class="middle_div">
 	<span>Context URLs</span>
    <span><input type="button" value="Add" id="contextAdd" class="btn btn_style add_icon_btn"></span>
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
				<table class="table table-striped table_border table-bordered context_table" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<th colspan="4">
							<div class="delete_icon removeContext">
								<img class="removeContextImg" src="themes/default/images/helios/delete_row.png">
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
								<label>Context<sup>*</sup></label>
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
									</tbody>
								</table> 
							</td>                                  	
						</tr>
						<tr>
							<td colspan="4">
								<table class="table table-striped table_border table-bordered header_table" cellpadding="0" cellspacing="0" border="0">
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
														<div class="add_remove_test headers">$headersObj.key$ : $headersObj.value$<input type="hidden" value="$headersObj.key$" name="headerKey"> <input type="hidden" value="$headersObj.value$" name="headerValue"> <img src="themes/default/images/helios/remove_test.png" class="removeHeaders"></div>
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
									 <table class="table table-striped table_border table-bordered header_table" cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th>Parameters</th>
											</tr>
										</thead>
										<tbody>
											$contextUrlsObj.parameters: { parametersObj |
												<tr class="parameterRow">
													<td><input type="text" class="parameterName" value="$parametersObj.name$" placeholder="Name" name="parameterName"/>
													<input type="text" class="parameterValue" value="$parametersObj.value$" name="parameterValue" placeholder="Value"/>
													<input class="parameterEncode" type="checkbox"  name="parameterEncode" checkFlag="$parametersObj.encode$"/>Encode
													<img src="themes/default/images/helios/minus_icon.png" class="add_test_icon removeParamter" style="display:none;">
													<img class="add_test_icon addParameter" src="themes/default/images/helios/plus_icon.png"></td>
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
			</script>
		}$
	}$
</div>     
<script type="text/javascript">
	if(jsonFlag) {
		var newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass');
		newTextBoxDiv.html("<table class='table table-striped table_border table-bordered context_table' cellpadding='0' cellspacing='0' 	border='0'><thead><tr><th colspan='4'><div class='delete_icon removeContext'><img class='removeContextImg' src='themes/default/images/helios/delete_row.png'></div></th></tr></thead><tbody><tr><td><label>HTTP Name<sup>*</sup></label><input type='text' value='' name='httpName'></td><td><label>Context<sup>*</sup></label><input type='text' value='' name='context'></td><td><label>Type</label><select name='contextType'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td><label>Encoding</label><select name='encodingType'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr><tr><td colspan='4'><table class='table' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically'>Redirect Automatically</td><td><input type='checkbox'  name='followRedirects' class='followRedirects'>Follow Redirects</td><td><input type='checkbox' name='keepAlive' class='keepAlive'>Use Keep Alive</td></tr><tr><td><input type='checkbox' name='multipartData' class='multipartData'>Use Mulipart data</td><td colspan='2'><input type='checkbox' name='compatibleHeaders' class='compatibleHeaders'>Browser Compatible Headers</td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Headers</th></tr></thead><tbody><tr><td><input type='text' class='headerKey' placeholder='Key'><input class='headerValue' type='text' placeholder='Value'><input type='button' value='Add' class='btn btn_style headerKeyAdd'></td></tr><tr class='headerContent' style='display:none;'><td><div class='add_test'></div></td></tr></tbody></table></td></tr><tr><td colspan='4'><table class='table table-striped table_border table-bordered header_table' cellpadding='0' cellspacing='0' border='0'><thead><tr><th>Parameters</th></tr></thead><tbody><tr class='parameterRow'><td><input type='text' class='parameterName' value='' placeholder='Name' name='parameterName'/><input type='text' class='parameterValue' value='' name='parameterValue' placeholder='Value'/><input class='parameterEncode' type='checkbox'  name='parameterEncode'/>Encode<img src='themes/default/images/helios/minus_icon.png' class='add_test_icon removeParamter' style='display:none;'><img class='add_test_icon addParameter' src='themes/default/images/helios/plus_icon.png'></td></tr></tbody></table></td></tr></tbody></table>");
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
	}
	
	function contextUrls() {
        console.info("testing json str");
		var contextUrls = [];
		var contexts = "";
		jQuery('.contextDivClass').each(function() {
			var jsonObject = {};
			jsonObject.name = jQuery(this).find(jQuery("input[name=httpName]")).val();
			jsonObject.context = jQuery(this).find(jQuery("input[name=context]")).val();
			jsonObject.contextType = jQuery(this).find(jQuery("select[name=contextType]")).val();
			jsonObject.encodingType = jQuery(this).find(jQuery("select[name=encodingType]")).val();
			jsonObject.contextPostData = jQuery(this).find(jQuery("textarea[name=contextPostData]")).val(); 

			jsonObject.redirectAutomatically = jQuery(this).find(jQuery("input[name=redirectAutomatically]")).is(':checked'); 
			jsonObject.followRedirects = jQuery(this).find(jQuery("input[name=followRedirects]")).is(':checked'); 
			jsonObject.keepAlive = jQuery(this).find(jQuery("input[name=keepAlive]")).is(':checked'); 
			jsonObject.multipartData = jQuery(this).find(jQuery("input[name=multipartData]")).is(':checked'); 
			jsonObject.compatibleHeaders = jQuery(this).find(jQuery("input[name=compatibleHeaders]")).is(':checked');  			
			
			var headers = [];
			jQuery(this).find(jQuery('.headers')).each(function() {
				var key = jQuery(this).find(jQuery("input[name=headerKey]")).val();
				var value = jQuery(this).find(jQuery("input[name=headerValue]")).val();
				var keyValueObj = {};
				keyValueObj.key=key;
				keyValueObj.value=value;
				headers.push(keyValueObj);
			});
			jsonObject.headers=headers;
			
			var parameters = [];
			jQuery(this).find(jQuery('.parameterRow')).each(function() {
				var name = jQuery(this).find(jQuery("input[name=parameterName]")).val();
				var value = jQuery(this).find(jQuery("textarea[name=parameterValue]")).val();
				var encode = jQuery(this).find(jQuery("input[name=parameterEncode]")).is(':checked');
				var nameValueObj = {};
				nameValueObj.name=name;
				nameValueObj.value=value;
				nameValueObj.encode=encode;
				parameters.push(nameValueObj);
			});
			jsonObject.parameters=parameters;

			contextUrls.push(JSON.stringify(jsonObject));
		});
		var jsonStrFromTemplate = '"contextUrls":[' + contextUrls + ']';
        console.info("b4 return > ",jsonStrFromTemplate);
		return jsonStrFromTemplate;
	}
</script>
