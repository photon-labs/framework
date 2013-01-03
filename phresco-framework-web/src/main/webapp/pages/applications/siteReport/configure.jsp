<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>

<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="com.phresco.pom.site.Reports"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.phresco.pom.site.ReportCategories"%>

<%
	List<Reports> reports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_REPORTS);
	List<Reports> selectedReports = (List<Reports>) request.getAttribute(FrameworkConstants.REQ_SITE_SLECTD_REPORTS);
%>

<div class="" id="configure-popup">
	<form id="formConfigureList" class="configureList">
		<fieldset class="popup-fieldset" style="border: 1px solid #CCCCCC; height: 97%;">
			<legend class="fieldSetLegend"><s:text name="header.site.report.availableRpts"/></legend>
			<div class="tblheader">
                   <table class="zebra-striped">
                       <thead>
                          <tr id=allReport>
                              <th class="report-header1">
                                  <input type="checkbox" value="" id="checkAllReports" name="checkAllAuto">&nbsp;<s:text name="lbl.reports"/>
                              </th>
                              <th class="report-header2"></th>
                          </tr>   
                      </thead>
                   </table>
               </div>
			<div class="report_scroll" id="reports-div">
        		<ul id="availableReports" class="xlarge">
					<%
						if (CollectionUtils.isNotEmpty(reports)) {
							for (Reports report : reports) {
								String checkedStr = "";
								List<ReportCategories> selectedReportCategories = null;
								if (CollectionUtils.isNotEmpty(selectedReports)) {
									for (Reports selectedReport : selectedReports) {
										if (selectedReport.getGroupId().equals(report.getGroupId()) && selectedReport.getArtifactId().equals(report.getArtifactId())) {
											selectedReportCategories = selectedReport.getReportCategories();
											checkedStr = "checked";
										}
									}
								}
							%>
								<div class="theme_accordion_container" style="border: none;">
								    <section class="accordion_panel_wid" style="margin-top: -6px;">
								        <div class="accordion_panel_inner" style="padding:0; border:1px solid #cccccc;">
								            <section class="lft_menus_container" style="width:100%;">
								                <span class="siteaccordion <%= CollectionUtils.isEmpty(report.getReportCategories()) ? "closereg_empty" : "closereg" %> reportcolor" style="border: none;">
									                <span id="reportList">
									                  <input type="checkbox" class="parentCheck parentCheck<%= report.getArtifactId() %>" name="reports" 
									                  		value="<%= report.getArtifactId() %>" <%= checkedStr %>>&nbsp;<%= report.getDisplayName() %>
									                </span>
								                </span>
								                <%
									                List<ReportCategories> reportCategories = report.getReportCategories();
													if (CollectionUtils.isNotEmpty(reportCategories)) {
								                %>
								                <div class="mfbox siteinnertooltiptxt hideContent">
								                    <div class="scrollpanel adv_setting_accordian_bottom">
								                        <section class="scrollpanel_inner">
                                                               <fieldset class="popup-fieldset fieldset_center_align categoryList" style="border: none;">
                                                                <div class="clearfix">
                                                                    <div class="xlInput" id="reportcategy">
                                                                    
                                                                           <%
                                                                               String categoryChk = "";
                                                                               for (ReportCategories reportCategory : reportCategories) {
                                                                                   String indexCheck = "";
                                                                                   if(reportCategory.getName().equals("index")){
                                                                                       indexCheck = "checked";
                                                                                   }
                                                                                   if (CollectionUtils.isNotEmpty(selectedReportCategories)) {
                                                                                       for (ReportCategories selectedReportCategory : selectedReportCategories) {
                                                                                           if(reportCategory.getName().equals(selectedReportCategory.getName())) {
                                                                                               categoryChk = "checked";
                                                                                               break;
                                                                                           } else {
                                                                                               categoryChk = "";
                                                                                           }
                                                                                       }
                                                                                   }
                                                                           %>
                                                                                
                                                                                   <li class="environment_list" style="margin: 0;">
                                                                                       <input type="checkbox" class="check <%= report.getArtifactId() %>"  name="<%= report.getArtifactId() %>" 
                                                                                       		value="<%= reportCategory.getName() %>" <%= indexCheck %> <%= categoryChk %>>&nbsp;<%= reportCategory.getName() %>
                                                                                   </li>
                                                                           <%  
                                                                                   
                                                                           }       
                                                                           %>
                                                                     
                                                                     	<script type="text/javascript">
                                                                     	$(document).ready(function() {
                                                                     		checkAllHandler($('.parentCheck<%= report.getArtifactId() %>'), $('.<%= report.getArtifactId() %>'));
                                                                     	});
                                                                     	</script>
																	</div>    
                                                                </div>
                                                            </fieldset>
								                        </section>
								                    </div>
								                </div>
								                <%
													}
								                %>
								            </section>  
								        </div>
								    </section>
								</div>
					<%
							}
						}
					%>
		        </ul>
	        </div>
        </fieldset>			
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		hidePopuploadingIcon();
		$(".report_scroll").scrollbars(); // jquery scroll bar
		
		/** Accordian starts **/
		var showContent = 0;
	    $('.siteaccordion').bind('click',function(e) {
	        var _tempIndex = $('.siteaccordion').index(this);
	            $('.siteaccordion').removeClass('openreg').addClass('closereg_empty');
	            $('.mfbox').each(function(e){
	                if($(this).css('display')=='block'){
	                    $(this).find('.scrollpanel').slideUp('300');
	                    $(this).slideUp('300');
	                }
	            })
	        if($('.mfbox').eq(_tempIndex).css('display')=='none'){
	            $(this).removeClass('closereg_empty').addClass('openreg');
	            $('.mfbox').eq(_tempIndex).slideDown(300,function(){
	                $('.mfbox').eq(_tempIndex).find('.scrollpanel').slideDown('300');
	            });
	        }
	    });
	    /** Accordian ends **/
	    
		indexHandler();
		checkAllReports();
		
		$('.parentCheck').click(function() { //index checkbox should be always checked and disabled
			indexHandler();
		});
		
		$('#checkAllReports').click(function() { //check all reports
			checkAllReports(this.checked);
		});
		
		$('.parentCheck, .check').click(function() { // check all report and report's category wise
			if ($('input:checkbox').length - 1 !== $('input:checkbox:checked').length) {
				$('#checkAllReports').attr("checked", false);
			} else {
				$('#checkAllReports').attr("checked", true);
			}
		});
		
	});
	
	function checkAllReports(checkStatus) {
		$('input:checkbox').attr('checked', checkStatus);
		indexHandler();
	}

	function indexHandler() { //index checkbox should be always checked and disabled
		$('input:checkbox[value="index"]').attr('checked', true);
		$('input:checkbox[value="index"]').attr('disabled', true);
	}
	
</script>