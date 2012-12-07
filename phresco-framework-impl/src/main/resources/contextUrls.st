<input type="button" value="Add Context" class="btn btn-primary">
<input type="button" value="Delete" class="btn" id="deleteContext" disabled>
$myObject:{ obj |
	<div id="contextDiv">
		<fieldset class="popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset" id="context">
			<legend class="fieldSetLegend" style="width: 105px;">
				<input type="checkbox" class="check" onclick="enableDelBtn();"> Context URLs
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
							<input type="text" name="name" class="input-small" title="Name" maxlength="20" value="$obj.name$" class="screenName">
						</td>
						<td class="noBorder">
							<input type="text" name="context" title="Context" value="$obj.context$" class="input-medium">
						</td>
						<td class="noBorder">
							<select name="contextType" class="input-small">
								<option value="GET">GET</option>
								<option value="POST">POST</option>
								<option value="PUT">PUT</option>
								<option value="DELETE">DELETE</option>
							</select>
						</td>
						<td class="noBorder">
							<select name="encodingType" class="input-small">
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
						<td class="noBorder"><input type="text" class="input-mini" name="key"/></td>
						<td class="noBorder" style="color: #333333; font-weight: bold;">Value</td>
						<td class="noBorder"><input type="text" class="input-mini" name="value"/></td>
						<td class="noBorder"><input type="button" class="btn btn-primary" value="Add" onclick="addHeader(this);"/></td>
					</tr>
				</table>
			</fieldset>
			
			<textarea placeholder="Request body" title="JSON data" class="input-xlarge" style="margin: 2px 5px 0 0; width: 40%; float: right;"></textarea>
		</fieldset>
	</div>
}$