<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<script src="js/reader.js" ></script>

<%
   	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	System.out.println("handle it here !!!! ");
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	List<String> reportFiles = (List<String>)request.getAttribute(FrameworkConstants.REQ_PDF_REPORT_FILES);
	String reportGenerationStat = (String)request.getAttribute(FrameworkConstants.REQ_REPORT_STATUS);
	String reportDeletionStat = (String)request.getAttribute(FrameworkConstants.REQ_REPORT_DELETE_STATUS);
	String applicationId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	String fromPage = (String)request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	System.out.println("From page => " + fromPage);
%>

<style>
	.zebra-striped tbody tr:hover td {
	    background-color: transparent;
	}
</style>

<form action="printAsPdf" method="post" autocomplete="off" class="build_form form-horizontal" id="generatePdf">
		<%
			if (CollectionUtils.isNotEmpty(reportFiles)) {
		%>
			<div class="tabl-fixed-table-container" style="padding-bottom: 20px; padding-top: 0px; height: auto;">
	      		<div class="header-background"></div>
	      		<div id="reportPopupTbl" class="tabl-fixed-table-container-inner validatePopup_tbl" style="height: auto;overflow-x: hidden; overflow-y: auto;">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first validate_tblHdr">
				                	<div class="pdfth-inner">Existing Reports</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="pdfth-inner">Type</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="pdfth-inner">Download</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="pdfth-inner">Delete</div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          		<% 
			          			for(String reportFile : reportFiles) { 
			          				String type = "";
			          		%>
			            	<tr>
			              		<td>
			              			<div class ="pdfName" style="color: #000000;">
				              			<%	
			              					String[] reportType = reportFile.split(FrameworkConstants.UNDERSCORE);
			              					type = reportType[0];
				              			%>
				              			<%= reportType[1] %>
			              			</div>
			              		</td>
			              		<td>
			              			<div style="color: #000000;">
				              			<%
				              				if ("crisp".equals(type)) {
				              					type = FrameworkConstants.MSG_REPORT_OVERALL;
				              			%>
				              				<%= type %>
				              			<%
				              				} else {
				              					type = FrameworkConstants.MSG_REPORT_DETAIL;
				              			%>
				              				<%= type %>
				              			<% } %>
			              			</div>
			              		</td>
			              		<td>
			              			<div class="pdfType" style="color: #000000;">
				              			<a href="<s:url action='downloadReport'>
				              				<s:param name="testType"><%= testType == null ? "" : testType %></s:param>
				              				<s:param name="projectId"><%= projectId %></s:param>
				              				<s:param name="appId"><%= applicationId %></s:param>
				              				<s:param name="customerId"><%= customerId %></s:param>
						          		    <s:param name="reportFileName"><%= reportFile %></s:param>
						          		    </s:url>">
						          		     <img src="images/icons/download.png" title="<%= reportFile %>.pdf"/>
			                            </a>
					   				</div>
			              		</td>
			              		<td>
			              			<div class="pdfDelete" style="color: #000000;">
						          		     <img src="images/icons/delete.png" id="reportName" class="<%= reportFile %>" title="<%= reportFile %>.pdf"/>
					   				</div>
			              		</td>
			            	</tr>
			            	<% } %>
			          	</tbody>
			        </table>
	      		</div>
    		</div>
    		
    	<%
			} else { %>
    		<div class="alert alert-block" >
				<center><s:text name="label.report.unavailable"/></center>
			</div>
    	<% } %>
    	
    	 <div class="control-group">
			<label class="control-label labelbold popupLbl">
				Report Type
			</label>
			<div class="controls">
				<select name="reportDataType" id="reportDataType" class="input-xlarge ">
					<option value="crisp"><s:text name="label.report.overall"/></option>
					<option value="detail"><s:text name="label.report.detail"/></option>
				</select>
			</div>
		</div>
		
		<input type="hidden" name="projectId" value="<%= projectId %>">
		<% if (!"All".equals(fromPage)) { %>
		<input type="hidden" name="customerId" value="<%= customerId %>">
		<% } %>
		<input type="hidden" name="appId" value="<%= applicationId %>">
		<input type="hidden" name="fromPage" value="<%= fromPage %>">
		
</form>

<script type="text/javascript">
	if(!isiPad()){
		/* JQuery scroll bar */
// 		$("#reportPopupTbl").scrollbars();
	}
	$(document).ready(function() {
		
		// when clicking on save button, popup should not hide
		$('.backdrop > fade > in').attr('display', 'block');
		$('.popupOk').attr("data-dismiss", "");
		hidePopuploadingIcon();
		<%
		    if (StringUtils.isNotEmpty(reportDeletionStat)) {
        %>
               $("#errMsg").html("<s:text name='label.report.delete.success'/>");
		<%
            }
        %>
// 		disableScreen();
		
// 		$('#generateReport').click(function() {
// 			// show popup loading icon
// 			showPopuploadingIcon();
// 			var params = getBasicParams();
// 			params = params.concat("&fromPage=");
<%-- 	    	params = params.concat('<%= fromPage %>'); --%>
// 	    	alert(params);
// 	    	loadContent('printAsPdf', $('#generatePdf'), $('#popup_div'), params, false);
            
// 		});
		
		$('.pdfDelete').click(function() {
			showPopuploadingIcon();
			var params = ""; 
	    	params = params.concat("reportFileName=");
	    	params = params.concat($('#reportName').attr("class"));
 	    	params = params.concat("&fromPage=");
	    	params = params.concat('<%= fromPage %>');
			params = params.concat('&testType=');
	    	params = params.concat('<%= testType %>');
	    	loadContent('deleteReport', $('#generatePdf'), $('#popup_div'), params, false, true);
		});
		
<%-- 	<%
			if (StringUtils.isNotEmpty(reportGenerationStat)) {
		%>
			showHidePopupMsg($("#reportMsg"), '<%= reportGenerationStat %>');
		<%
			}
		%>
		
		<%
			if (StringUtils.isNotEmpty(reportDeletionStat)) {
		%>
			showHidePopupMsg($("#reportMsg"), '<%= reportDeletionStat %>');
		<%
			}
		%>
	
		<%
			if(!(Boolean) request.getAttribute(FrameworkConstants.REQ_TEST_EXE)) {
		%>
			$("#reportMsg").html('<%= FrameworkConstants.MSG_REPORT%>');
			disableControl($("#generateReport"), "btn disabled");
		<%
			} else {
		%>
			enableControl($("#generateReport"), "btn primary");
		<% 
			}
		%> --%>
			
	});
	
</script>