<%@page import="org.apache.xalan.xsltc.compiler.sym"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<script src="js/reader.js" ></script>

<%
   	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	List<String> reportFiles = (List<String>)request.getAttribute(FrameworkConstants.REQ_PDF_REPORT_FILES);
	String reportGenerationStat = (String)request.getAttribute(FrameworkConstants.REQ_REPORT_STATUS);
	String reportDeletionStat = (String)request.getAttribute(FrameworkConstants.REQ_REPORT_DELETE_STATUS);
	String applicationId = (String)request.getAttribute(FrameworkConstants.REQ_APP_ID);
	String projectId = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_ID);
	String customerId = (String)request.getAttribute(FrameworkConstants.REQ_CUSTOMER_ID);
	String fromPage = (String)request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String sonarUrl = (String)request.getAttribute(FrameworkConstants.REQ_SONAR_URL);
%>

	<style>
		.zebra-striped tbody tr:hover td {
		    background-color: transparent;
		}
	</style>
	
<form class="build_form form-horizontal" id="generatePdf">
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
			              	<th class="second validate_tblHdr pdfDownLoadImg">
			                	<div class="pdfth-inner">Download</div>
			              	</th>
			              	<th class="second validate_tblHdr pdfDelImg">
			                	<div class="pdfth-inner">Delete</div>
			              	</th>
			            </tr>
		          	</thead>
				
		          	<tbody>
		          		<% 
		          			for (String reportFile : reportFiles) { 
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
					              				<s:param name="fromPage"><%= fromPage == null ? "" : fromPage %></s:param>
					              				<s:param name="projectId"><%= projectId %></s:param>
					              				<s:param name="appId"><%= applicationId %></s:param>
					              				<s:param name="customerId"><%= customerId %></s:param>
							          		    <s:param name="reportFileName"><%= reportFile %></s:param>
							          		    </s:url>">
							          		     <img src="images/icons/download.png" class="pdfDownload" title="<%= reportFile %>.pdf"/>
				                            </a>
						   				</div>
				              		</td>
				              		<td>
				              			<div class="" style="color: #000000;">
							          		     <img src="images/icons/delete.png" id="<%= reportFile %>" class="pdfDelete" title="<%= reportFile %>.pdf"/>
						   				</div>
				              		</td>
				            	</tr>
		            	<% 	} %>
		          	</tbody>
		        </table>
	   		</div>
		</div>
	    		
	 <% } else { %>
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
	<!-- Hidden Fields -->
	<input type="hidden" name="fromPage" value="<%= fromPage %>">
	<input type="hidden" name="appId" value="<%= applicationId %>">
	<input type="hidden" name="projectId" value="<%= projectId %>">
	<input type="hidden" name="customerId" value="<%= customerId %>">
    <input type="hidden" name="sonarUrl" value="<%= sonarUrl %>">
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
		printPdfPostActions();
		<%
		    if (StringUtils.isNotEmpty(reportDeletionStat)) {
        %>
               $("#successMsg").css("display", "block");
               $("#successMsg").html("<s:text name='label.report.delete.success'/>").fadeOut(5000);
		<%
            }
        %>
		
		$('.pdfDelete').click(function() {
			showPopuploadingIcon();
			var params = "reportFileName=";
	    	params = params.concat($(this).attr("id"));
			params = params.concat('&testType=');
	    	params = params.concat('<%= testType %>');
	    	loadContent('deleteReport', $('#generatePdf'), $('#popup_div'), params, false, true);
		});
		
	});
	
	function printPdfPostActions() {
		//To show delete,download icon 
		$('.pdfDelete').show();
		$('.pdfDownload').show();
		//To hide loading icon
		hidePopuploadingIcon();
		//To enable generate button
		$('#printAsPdf').removeAttr("disabled");
	    $('#printAsPdf').addClass("btn-primary");
	}
</script>