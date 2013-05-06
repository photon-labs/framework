<div style="background-color: #323232;color: #FFFFFF;">
     <table class="zebra-striped">
         <tr>
             <th class="" style="padding:8px 0 5px 9px">Context URLs</th>
         </tr>
     </table>
</div>
<input type="button" style="margin-top:4px;" value="Add Context" class="btn btn-primary" onclick="addContext('contextDiv');">
<input type="button" style="margin-top:4px; margin-left:4px;" value="Delete" class="btn" id="ctxUrlCheckDel" disabled onclick="deleteContextUrl('ctxUrlCheck');">
<script type="text/javascript">
	var jsonFlag = true;
</script>
<div id="contextDivParent">
$myObject:{ obj |
	$obj.contextUrls: { contextUrlsObj |
	<script type="text/javascript">
		jsonFlag = false;
	</script>
	<div id="contextDiv" class="contextDivClass" style="margin-bottom:5px;">
		<fieldset class="popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset" id="context">
			<legend class="fieldSetLegend">
				<input type="checkbox" class="ctxUrlCheck" onclick="enableDelBtn(this);">
			</legend>
			
			<table style="line-height: 0px;">
				<thead>
					<tr>
						<td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;Name</td>
						<td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;Context</td>
						<td class="noBorder" style="color: #333333; font-weight: bold;">Type</td>
						<td class="noBorder" style="color: #333333; font-weight: bold;">Encoding</td>
					</tr>							
				</thead>
				<tbody>
					<tr>
						<td class="noBorder">
							<input type="text" name="contextName" class="input-small" title="Name" maxlength="20" value="$contextUrlsObj.name$" class="screenName">
						</td>
						<td class="noBorder">
							<input type="text" name="context" title="Context" value="$contextUrlsObj.context$" class="input-medium">
						</td>
						<td class="noBorder">
							<select name="contextType" id="contextType$contextUrlsObj.name$" class="input-small">
								<option value="GET">GET</option>
								<option value="POST">POST</option>
								<option value="PUT">PUT</option>
								<option value="DELETE">DELETE</option>
							</select>
						</td>
						<td class="noBorder">
							<select name="encodingType" id="encodingType$contextUrlsObj.name$" class="input-small">
								<option value="UTF-8">UTF-8</option>
								<option value="UTF-16">UTF-16</option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
			<table style="line-height: 0px;">
				<thead>
					<tr>
						<td style="color: #333333; font-weight: bold;" class="noBorder">
							<input type="checkbox" checkFlag="$contextUrlsObj.redirectAutomatically$" name="redirectAutomatically" class="redirectAutomatically" onclick="checkUncheck(this);" class="">&nbsp;Redirect Automatically
						</td>
						<td style="color: #333333; font-weight: bold;" class="noBorder">
							<input type="checkbox" checkFlag="$contextUrlsObj.followRedirects$" name="followRedirects" class="followRedirects" onclick="checkUncheck(this);" class="">&nbsp;Follow Redirects
						</td>
						<td style="color: #333333; font-weight: bold;" class="noBorder">
							<input type="checkbox" checkFlag="$contextUrlsObj.keepAlive$" name="keepAlive" onclick="" class="keepAlive">&nbsp;Use Keep Alive
						</td>
					</tr>
				</thead>
			</table>
			<table style="line-height: 0px;">
				<thead>
					<tr>
						<td style="color: #333333; font-weight: bold; width: 45.3%;" class="noBorder">
							<input type="checkbox" checkFlag="$contextUrlsObj.multipartData$" name="multipartData" onclick="" class="multipartData">&nbsp;Use multipart data
						</td>
						<td style="color: #333333; font-weight: bold;" class="noBorder">
							<input type="checkbox" checkFlag="$contextUrlsObj.compatibleHeaders$" name="compatibleHeaders" onclick="" class="compatibleHeaders">&nbsp;Browser Compatible headers
						</td>
					</tr>
				</thead>
			</table>
			<fieldset class="popup-fieldset perContentFieldsetDiv" style="padding: 0; margin-bottom: 0; width: 97%; margin-left:8px; float: left;" id="headerLegend">
				<legend class="fieldSetLegend">Headers</legend>
				<table>
					<tr>
						<td class="noBorder" style="color: #333333; font-weight: bold;">Key</td>
						<td class="noBorder"><input type="text" class="input-mini key"/></td>
						<td class="noBorder" style="color: #333333; font-weight: bold;">Value</td>
						<td class="noBorder"><input type="text" class="input-mini value"/></td>
						<td class="noBorder"><input type="button" class="btn btn-primary" value="Add" onclick="addHeader(this);"/></td>
					</tr>
				</table>
				$contextUrlsObj.headers: { headersObj |
					<div id="headerkeyId" class="headers" style="background-color: #bbbbbb; width: 40%; margin-bottom:2px; height: auto; border-radius: 6px; padding: 0 0 0 10px; position: relative" ><a onclick="removeHeader(this);" style="text-decoration: none; margin-right: 10px; color: #000000; margin-left: 92%;" href="#">&times;</a><div style="cursor: pointer; color: #000000; height: auto; position: relative; width: 90%; line-height: 17px; margin-top: -14px; padding: 0 0 6px 1px;">$headersObj.key$ : $headersObj.value$</div><input type="hidden" value="$headersObj.key$" name="headerKey"><input type="hidden" value="$headersObj.value$" name="headerValue"></div>
				}$
			</fieldset>
			
			<fieldset class="popup-fieldset perContentFieldsetDiv parameterFieldset" style="padding: 0; margin-bottom: 0; width: 97%; 		margin-left:8px; float: left;" id="parameterFieldset">
				<legend class="fieldSetLegend">Parameters</legend>
				<table>
					<thead>
						<tr>
							<td style="color: #333333; font-weight: bold;" class="noBorder">Name</td>
							<td style="color: #333333; font-weight: bold;" class="noBorder">Value</td>
							<td style="color: #333333; font-weight: bold;" class="noBorder">Encode</td>
						</tr>
					</thead>
					<tbody>
						$contextUrlsObj.parameters: { parametersObj |
							<tr class="parameterRow">
								<td class="noBorder">
									<input type="text" class="input-medium parameterName" value="$parametersObj.name$" name="parameterName"/>
								</td>
								<td class="noBorder">
									<input type="text" class="input-medium parameterValue" value="$parametersObj.value$" name="parameterValue"/>
								</td>
								
								<td class="noBorder">
									<input type="checkbox" class="input-medium parameterEncode" name="parameterEncode" checkFlag="$parametersObj.encode$"/>
								</td>
								<td class="noBorder">
									<a>
										<img onclick="appendParameterRow(this);" class="parameterPlus" id="parameterPlus" src="images/icons/add_icon.png">
									</a>
									<a>
										<img onclick="removeParameterRow(this);" src="images/icons/minus_icon.png" class="del imagealign hide parameterMinus">
									</a>
								</td>
							</tr>
						}$	
					</tbody>	
				</table>
			</fieldset>
		</fieldset>
	</div>
	<script type="text/javascript">
		document.getElementById("contextType$contextUrlsObj.name$").value = '$contextUrlsObj.contextType$';
		document.getElementById("encodingType$contextUrlsObj.name$").value = '$contextUrlsObj.encodingType$';
	</script>
	}$
}$
</div>
<script type="text/javascript">
	console.info("contexturl template created");
	if (jsonFlag) {
		var newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass').css('margin-bottom','5px');
		newTextBoxDiv.html("<fieldset class='popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset' id='context'>
			<legend class='fieldSetLegend'><input type='checkbox' class='ctxUrlCheck' onclick='enableDelBtn(this);'></legend><table style='line-height: 0px;'><thead><tr><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Http Name</td><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Context</td><td class='noBorder' style='color: #333333; font-weight: bold;'>Type</td><td class='noBorder' style='color: #333333; font-weight: bold;'>Encoding</td></tr></thead><tbody><tr><td class='noBorder'><input type='text' name='contextName' class='input-small' title='Name' maxlength='20' value='' class='screenName'></td><td class='noBorder'><input type='text' name='context' title='Context' value='' class='input-medium'></td><td class='noBorder'><select name='contextType' id='contextType' class='input-small'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td class='noBorder'><select name='encodingType' id='encodingType' class='input-small'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr></tbody>
			</table><table style='line-height: 0px;'><thead><tr><td style='color: #333333; font-weight: bold;' class='noBorder'><input type='checkbox' name='redirectAutomatically' class='redirectAutomatically' onclick='checkUncheck(this);' class=''>&nbsp;Redirect Automatically</td><td style='color: #333333; font-weight: bold;' class='noBorder'><input type='checkbox' name='followRedirects' class='followRedirects' onclick='checkUncheck(this);' class=''>&nbsp;Follow Redirects</td><td style='color: #333333; font-weight: bold;' class='noBorder'><input type='checkbox' name='keepAlive' onclick='' class=''>&nbsp;Use Keep Alive</td></tr></tbody></table><table style='line-height: 0px;'><thead><tr><td style='color: #333333; font-weight: bold; width: 45.3%;' class='noBorder'><input type='checkbox' name='multipartData' onclick='' class=''>&nbsp;Use multipart data</td><td style='color: #333333; font-weight: bold;' class='noBorder'><input type='checkbox' name='compatibleHeaders' onclick='' class=''>&nbsp;Browser Compatible headers</td></tr></thead></table>
			<fieldset class='popup-fieldset perContentFieldsetDiv' style='padding: 0; margin-bottom: 0; width: 97%; margin-left:8px; float: left;' id='headerLegend'><legend class='fieldSetLegend'>Headers</legend><table><tr><td class='noBorder' style='color: #333333; font-weight: bold;'>Key</td><td class='noBorder'><input type='text' class='input-medium key'/></td><td class='noBorder' style='color: #333333; font-weight: bold;''>Value</td><td class='noBorder'><input type='text' class='input-medium value'/></td><td class='noBorder'><input type='button' class='btn btn-primary' value='Add' onclick='addHeader(this);'/></td></tr></table></fieldset>
			<fieldset class='popup-fieldset perContentFieldsetDiv parameterFieldset' style='padding: 0; margin-bottom: 0; width: 97%; margin-left:8px; float: left;' id='parameterFieldset'><legend class='fieldSetLegend'>Parameters</legend><table><thead><tr><td style='color: #333333; font-weight: bold;' class='noBorder'>Name</td><td style='color: #333333; font-weight: bold;' class='noBorder'>Value</td><td style='color: #333333; font-weight: bold;' class='noBorder'>Encode</td></tr></thead><tbody><tr class='parameterRow'><td class='noBorder'><input type='text' class='input-medium parameterName' name='parameterName'/></td><td class='noBorder'><input type='text' class='input-medium parameterValue' name='parameterValue'/></td><td class='noBorder'><input type='checkbox' class='input-medium parameterEncode' name='parameterEncode'/></td><td class='noBorder'><a><img onclick='appendParameterRow(this);' class='parameterPlus' id='parameterPlus' src='images/icons/add_icon.png'></a><a><img onclick='removeParameterRow(this);' src='images/icons/minus_icon.png' class='del imagealign hide parameterMinus' style='margin-left:4px;'></a></td></tr></tbody></table></fieldset>
			</fieldset></div>");
		newTextBoxDiv.appendTo("#contextDivParent");	
	} else {
		checkEncodeCheckBox();
		disableEnableMinus();
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
	
	
	function disableEnableMinus() {
		jQuery('.perContextUrlFieldset').each(function() {
			var size = jQuery(this).find('.parameterPlus').size();
			if (size > 1) {
				jQuery(this).find('.parameterMinus').show();
			}
		});
	}
	function contextUrlsMandatoryVal() {
		var redirect = true;
		jQuery('.perContextUrlFieldset').each(function() {
			if (jQuery(this).find(jQuery('input[name=contextName]')).val() == "" || isBlank(jQuery(this).find(jQuery('input[name=contextName]')).val())) {
				redirect = false;
				jQuery('.yesNoPopupErr').text('Name is missing');
				jQuery(this).find(jQuery('input[name=contextName]')).val('');
				jQuery(this).find(jQuery('input[name=contextName]')).focus();
			} else if (jQuery(this).find(jQuery('input[name=context]')).val() == "" || isBlank(jQuery(this).find(jQuery('input[name=context]')).val())) {
				redirect = false;
				jQuery('.yesNoPopupErr').text('Context is missing');
				jQuery(this).find(jQuery('input[name=context]')).val('');
				jQuery(this).find(jQuery('input[name=context]')).focus();
			} 
		});
		
		return redirect;
	}
	
	function contextUrls() {
		var contextUrls = [];
		var contexts = "";
		jQuery('.perContextUrlFieldset').each(function() {
			var jsonObject = {};
			jsonObject.name = jQuery(this).find(jQuery("input[name=contextName]")).val();
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
				var value = jQuery(this).find(jQuery("input[name=parameterValue]")).val();
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
		return jsonStrFromTemplate;
	}
</script> 