<div style="background-color: #323232;color: #FFFFFF;">
     <table class="zebra-striped">
         <tr>
             <th class="" style="padding:8px 0 5px 9px">Context URLs</th>
         </tr>
     </table>
</div>
<input type="button" style="margin-top:4px;" value="Add Context" class="btn btn-primary" onclick="addContext('dbContextDiv');">
<input type="button" style="margin-top:4px;" value="Delete" class="btn" id="deleteContext" disabled onclick="deleteContextUrl();">
$myObject:{ obj |
	<div id="dbContextDiv">
		<fieldset class="popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset" id="context">
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
							<input type="text" name="name" class="input-small" title="Name" maxlength="20" value="$obj.name$" class="screenName">
						</td>
						<td class="noBorder">
							<select name="queryType" class="input-small" style="width:140px;">
								<option value="Select Statement">Select Statement</option>
								<option value="Update Statement">Update Statement</option>
							</select>
						</td>
						<td class="noBorder">
							<textarea placeholder="" title="JSON data" name="query" class="" style="margin: 2px 5px 0 0; width: 140%; float: left;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
	</div>
}$