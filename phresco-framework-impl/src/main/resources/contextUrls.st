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
				<input type="checkbox" class="check ctxUrlCheck" onclick="enableDelBtn('ctxUrlCheck');">
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
							<input type="text" name="name" class="input-small" title="Name" maxlength="20" value="$contextUrlsObj.name$" class="screenName">
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
			
			<fieldset class="popup-fieldset perContentFieldsetDiv" style="padding: 0; margin-bottom: 0; width: 55%; float: left;" id="headerLegend">
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
			<textarea placeholder="Request body" name="contextPostData" id="contextPostData" title="JSON data" class="" style="margin: 14px 5px 0 0; width: 40%; float: right;">$contextUrlsObj.contextPostData$</textarea>
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
	if (jsonFlag) {
		var newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'contextDiv').attr('class','contextDivClass').css('margin-bottom','5px');
		newTextBoxDiv.html("<fieldset class='popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset' id='context'>
			<legend class='fieldSetLegend'><input type='checkbox' class='ctxUrlCheck' onclick='enableDelBtn('ctxUrlCheck');'></legend><table style='line-height: 0px;'><thead><tr><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Name</td><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Context</td><td class='noBorder' style='color: #333333; font-weight: bold;'>Type</td><td class='noBorder' style='color: #333333; font-weight: bold;'>Encoding</td></tr></thead><tbody><tr><td class='noBorder'><input type='text' name='name' class='input-small' title='Name' maxlength='20' value='' class='screenName'></td><td class='noBorder'><input type='text' name='context' title='Context' value='' class='input-medium'></td><td class='noBorder'><select name='contextType' id='contextType' class='input-small'><option value='GET'>GET</option><option value='POST'>POST</option><option value='PUT'>PUT</option><option value='DELETE'>DELETE</option></select></td><td class='noBorder'><select name='encodingType' id='encodingType' class='input-small'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select></td></tr></tbody>
			</table><fieldset class='popup-fieldset perContentFieldsetDiv' style='padding: 0; margin-bottom: 0; width: 55%; float: left;' id='headerLegend'><legend class='fieldSetLegend'>Headers</legend><table><tr><td class='noBorder' style='color: #333333; font-weight: bold;'>Key</td><td class='noBorder'><input type='text' class='input-mini key'/></td><td class='noBorder' style='color: #333333; font-weight: bold;''>Value</td><td class='noBorder'><input type='text' class='input-mini value'/></td><td class='noBorder'><input type='button' class='btn btn-primary' value='Add' onclick='addHeader(this);'/></td></tr></table></fieldset><textarea placeholder='Request body' name='contextPostData' id='contextPostData' title='JSON data' class='' style='margin: 14px 5px 0 0; width: 40%; float: right;'></textarea></fieldset></div>");
		newTextBoxDiv.appendTo("#contextDivParent");	
	}
	
	function contextUrls() {
		var contextUrls = [];
		var contexts = "";
		jQuery('.perContextUrlFieldset').each(function() {
			var headers = [];
			var jsonObject = jQuery(this).toJSON();
			jQuery(this).find(jQuery('.headers')).each(function() {
				var key = jQuery(this).find(jQuery("input[name=headerKey]")).val();
				var value = jQuery(this).find(jQuery("input[name=headerValue]")).val();
				var keyValueObj = {};
				keyValueObj.key=key;
				keyValueObj.value=value;
				headers.push(keyValueObj);
			});
			jsonObject.headers=headers;
			contextUrls.push(JSON.stringify(jsonObject));
		});
		var jsonStrFromTemplate = '"contextUrls":[' + contextUrls + ']';
		return jsonStrFromTemplate;
	}
</script>