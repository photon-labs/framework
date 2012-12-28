<div style="background-color: #323232;color: #FFFFFF;">
     <table class="zebra-striped">
         <tr>
             <th class="" style="padding:8px 0 5px 9px">Context URLs</th>
         </tr>
     </table>
</div>
<input type="button" style="margin-top:4px;" value="Add Context" class="btn btn-primary" onclick="addContext('dbContextDiv');">
<input type="button" style="margin-top:4px, margin-left:4px;" value="Delete" class="btn" id="deleteContext" disabled onclick="deleteContextUrl();">
<script type="text/javascript">
	var dbJsonFlag = true;
</script>
<div id="dbContextDivParent">
$myObject:{ obj |
	$obj.dbContextUrls: { dbContextUrlsObj |
		<script type="text/javascript">
			dbJsonFlag = false;
		</script>
		<div id="dbContextDiv" style="margin-bottom:5px;">
			<fieldset class="popup-fieldset fieldsetBottom perFieldSet perDBContextUrlFieldset" id="context">
				<legend class="fieldSetLegend">
					<input type="checkbox" class="check" onclick="enableDelBtn();">
				</legend>
				
				<table style="line-height: 0px;">
					<thead>
						<tr>
							<td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;Name</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;QueryType</td>
							<td class="noBorder" style="color: #333333; font-weight: bold;"><span class="red">*</span>&nbsp;Query</td>
						</tr>							
					</thead>
					<tbody>
						<tr>
							<td class="noBorder">
								<input type="text" name="name" class="input-small" title="Name" maxlength="20" value="$dbContextUrlsObj.name$" class="screenName">
							</td>
							<td class="noBorder">
								<select name="queryType" id="queryType$dbContextUrlsObj.name$" class="input-small" style="width:140px;">
									<option value="Select Statement">Select Statement</option>
									<option value="Update Statement">Update Statement</option>
								</select>
							</td>
							<td class="noBorder">
								<textarea placeholder="" title="JSON data" name="query" id="query" class="" style="margin: 2px 5px 0 0; width: 140%; float: left;">$dbContextUrlsObj.query$</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>		
		</div>
		<script type="text/javascript">
			document.getElementById("queryType$dbContextUrlsObj.name$").value = '$dbContextUrlsObj.queryType$';
		</script>
	}$	
}$
</div>
<script type="text/javascript">
	if (dbJsonFlag) {
		var newTextBoxDiv = jQuery(document.createElement('div')).attr('id', 'dbContextDiv').css('margin-bottom','5px');
		newTextBoxDiv.html("<fieldset class='popup-fieldset fieldsetBottom perFieldSet perDBContextUrlFieldset' id='context'>" +
			"<legend class='fieldSetLegend'><input type='checkbox' class='check' onclick='enableDelBtn();'>" +
			"</legend><table style='line-height: 0px;'><thead><tr><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Name</td><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;QueryType</td><td class='noBorder' style='color: #333333; font-weight: bold;'><span class='red'>*</span>&nbsp;Query</td></tr></thead><tbody><tr><td class='noBorder'><input type='text' name='name' class='input-small' title='Name' maxlength='20' value='' class='screenName'></td><td class='noBorder'>
			<select name='queryType' id='queryType' class='input-small' style='width:140px;'><option value='Select Statement'>Select Statement</option><option value='Update Statement'>Update Statement</option></select></td><td class='noBorder'><textarea placeholder='' title='JSON data' name='query' id='query' class='' style='margin: 2px 5px 0 0; width: 140%; float: left;'></textarea></td></tr></tbody></table></div>");
		newTextBoxDiv.appendTo("#dbContextDivParent");	
	}
	
	function dbContextUrls() {
		var dbContextUrls = [];
		jQuery('.perDBContextUrlFieldset').each(function() {
			var jsonObject = jQuery(this).toJSON();
			var dbContexts = JSON.stringify(jsonObject);
			dbContextUrls.push(dbContexts);
		});
		var jsonStrFromTemplate = '"dbContextUrls":[' + dbContextUrls + ']';
		return jsonStrFromTemplate;
	}
</script>
