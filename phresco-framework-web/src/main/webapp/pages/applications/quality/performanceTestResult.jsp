<%--

    Framework Web Archive

    Copyright (C) 1999-2013 Photon Infotech Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>

<%@ page import="org.apache.commons.lang.StringUtils"%>

<%@ page import="com.photon.phresco.framework.model.PerformanceTestResult"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.ApplicationInfo"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil"%>

<style type="text/css">
	.btn.success, .alert-message.success {
       	margin-top: -35px;
   	}
   	
   	table th {
		padding: 0 0 0 9px;  
	}
	   	
	td {
	 	padding: 5px;
	 	text-align: left;
	}
	  
	th {
	 	padding: 0 5px;
	 	text-align: left;
	}
</style>

    <% 
    	ApplicationInfo appInfo = (ApplicationInfo)request.getAttribute(FrameworkConstants.REQ_APPINFO);
        String errorDeviceData = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_DATA);
        if (StringUtils.isNotEmpty(errorDeviceData)) {
    %>
    	<div class="alert alert-block">
            <%= FrameworkConstants.ERROR_ANDROID_DATA %>
        </div>
    
        <script type="text/javascript">
			$("#resultView").hide();
		</script>
    <% 
    	} else { 
            String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
            double totalStdDev = (Double)request.getAttribute(FrameworkConstants.REQ_TOTAL_STD_DEV);
            double totalThroughput = (Double)request.getAttribute(FrameworkConstants.REQ_TOTAL_THROUGHPUT);
    %>
    	<script type="text/javascript">
    		$("#resultView").show();
    		$(".noTestAvail").show();
			$("#graphBasedOn").show();
			$("#noFiles").hide();
			
			<%-- <%if(TechnologyTypes.ANDROIDS.contains(project.getApplicationInfo().getTechInfo().getVersion())) {%>
				disableType();
			<%
				}
			%> --%>
		</script>
    <%
            Map<String, PerformanceTestResult> performanceReport = (Map<String, PerformanceTestResult>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT);
            String graphData = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_DATA);
			String graphLabel = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_LABEL);
            Set<String> keySet = performanceReport.keySet();
            
            String graphAllData = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_ALL_DATA);
            
            String graphFor = (String)request.getAttribute(FrameworkConstants.REQ_SHOW_GRAPH);
            
            String chartTitle = "";
            String chartUnit = "";
            if (graphFor.equals("responseTime")) {
            	chartTitle = "Avg Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("throughPut")) {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            } else if (graphFor.equals("minResponseTime")) {
            	chartTitle = "Min Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("maxResponseTime")) {
            	chartTitle = "Max Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("all")) {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            }
            
    %>
            <div class="table_div_unit" id="tabularView">
            	<div class="fixed-table-container">
	      			<div class="header-background"> </div>
		      		<div class="fixed-table-container-inner" >
				        <div style="overflow: auto;">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
						            <tr>
										<th class="first"><div class="th-inner-test"><s:text name="label.label"/></div></th>
						              	<th class="second"><div class="th-inner-test"><s:text name="label.sample"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.average"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.min"/></div></th>
						              	<th class="first"><div class="th-inner-test"><s:text name="label.max"/></div></th>
						              	<th class="second"><div class="th-inner-test"><s:text name="label.std.dev"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.error"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.throughput"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.kb.sec"/></div></th>
						              	<th class="third"><div class="th-inner-test"><s:text name="label.avg.bytes"/></div></th>
						            </tr>
					          	</thead>
					
					          	<tbody>
					          	<%	int totalValue = keySet.size();
				          			int NoOfSample = 0; 
				          			double avg = 0; 
					          		int min = 0;
					          		int max = 0;
					          		double StdDev = 0;
					          		double Err = 0;
					          		double KbPerSec = 0;
					          		double sumOfBytes = 0;
					          		int i = 1;
						          	for (String key : keySet) {
			                        	PerformanceTestResult performanceTestResult = performanceReport.get(key);
			                        	NoOfSample = NoOfSample + performanceTestResult.getNoOfSamples();
			                        	avg = avg + performanceTestResult.getAvg();
			                        	
			                        	if (i == 1) {
			                        		min = performanceTestResult.getMin();
			                        		max = performanceTestResult.getMax();
			                        	}
			                        	if (i != 1 && performanceTestResult.getMin() < min) {
			                        		min = performanceTestResult.getMin();
			                        	}
			                        	if (i != 1 && performanceTestResult.getMax() > max) {
			                        		max = performanceTestResult.getMax();
			                        	}
			                        	StdDev = StdDev + performanceTestResult.getStdDev();
			                        	Err = Err + performanceTestResult.getErr();
			                        	sumOfBytes = sumOfBytes + performanceTestResult.getAvgBytes();
								%>
					            	<tr>
					              		<td style="width: 10%;"><%= performanceTestResult.getLabel() %></td>
					              		<td style="width: 10%;"><%= performanceTestResult.getNoOfSamples() %></td>
					              		<td style="width: 10%;"><%= (int)performanceTestResult.getAvg() %></td>
					              		<td style="width: 8%;"><%= performanceTestResult.getMin() %></td>
					              		<td style="width: 8%;"><%= performanceTestResult.getMax() %></td>
					              		<td style="width: 8%;"><%= FrameworkUtil.roundFloat(2, performanceTestResult.getStdDev()) %></td>
					              		<td style="width: 8%;"><%= String.format("%.2f", performanceTestResult.getErr()) %> %</td>
					              		<td style="width: 12%;"><%= FrameworkUtil.roundFloat(1, performanceTestResult.getThroughtPut()) %></td>
					              		<td style="width: 10%;"><%= FrameworkUtil.roundFloat(2, performanceTestResult.getKbPerSec()) %></td>
					              		<td style="width: 10%;"><%= performanceTestResult.getAvgBytes() %></td>
					            	</tr>
					            <%
					            	i++;
									}
						          	double avgBytes = sumOfBytes / totalValue;
						          	KbPerSec = (avgBytes / 1024) * totalThroughput;
								%>	
					          	</tbody>
					          	 <tfoot>
					          	 	<tr>
					          	 		<% PerformanceTestResult performanceTestResult = new PerformanceTestResult(); %>
							              <td style="width: 10%;font-weight: bold">Total</td>
							              <td style="width: 10%;font-weight: bold"><%= NoOfSample %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,avg/totalValue) %></td>
							              <td style="width: 8%;font-weight: bold"><%= min %></td>
							              <td style="width: 8%;font-weight: bold"><%= max %></td>
							              <td style="width: 8%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,totalStdDev) %></td>
							              <td style="width: 8%;font-weight: bold"><%= String.format("%.2f", Err/totalValue) %> %</td>
							              <td style="width: 12%;font-weight: bold"><%= FrameworkUtil.roundFloat(1,totalThroughput) %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,KbPerSec) %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,avgBytes) %></td>
					          	 	</tr>
					          	 </tfoot>
					        </table>
						</div>
						<div>
							
						</div>
		      		</div>
    			</div>
    		</div>
            <div class="" id="graphicalView" style="margin-left: 15px;">
                <!--<div class="jm_canvas_div">
                    <iframe src="<%= request.getContextPath() %>/pages/applications/quality/jmeter_graph.jsp"  frameborder="0" width="100%" height="100%"></iframe>
                </div>-->
                <% if(request.getAttribute("showGraphFor").toString().equals("all")) { %>
                	<canvas id="allData" width="420" height="300">[No canvas support]</canvas>
                <% } %>
                <canvas id="myCanvas" width="420" height="300">[No canvas support]</canvas>
                
            </div>
   

<script type="text/javascript">
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if (!isiPad()) {
		$(".fixed-table-container-inner").scrollbars();
		$("#graphicalView").scrollbars();
	}
	
	$(document).ready(function() {
		hideLoadingIcon();
		changeView ();//Change to graphical/tabular view based on the selection
		hideLoadingIcon();//To hide the loading icon once the page is loaded
		canvasInit();//To draw the graph
		
		if ($.browser.safari) {
    		$(".th-inner-test").css("top","235px"); 
		}
		
		var OSName="Unknown OS";
        if (navigator.appVersion.indexOf("Mac")!=-1) {
              OSName="MacOS";
        }
        
        if (OSName == "MacOS") { 
            $(".th-inner-test").css("top","225px");  
            $(".th-inner-testtech").css("top","225px");
        }
		
		$(".styles").click(function() {
			canvasInit();
		});
	});
	
	$('#resultView').change(function() {
		changeView ();
	});
	
	//To change the graph when based on is changed
	function changeGraph() {
// 		showLoadingIcon(); // Loading Icon
		loadContent('fetchPerformanceTestResult', $('#formPerformance'), $('#testResultDisplay'), getBasicParams(), '', true);
		$("#graphicalView").show();
	}
	
	//To change the view to graph/table
	function changeView() {
		var resultView = $('#resultView').val();
		if (resultView == 'graphical') {
			$("#graphBasedOn").show();
			$('#graphicalView').show();
			$('#tabularView').hide();
		} else  {
			$("#graphBasedOn").hide();
			$('#graphicalView').hide();
			$('#tabularView').show();
		}
	}
	
	//To draw the graph based on the graph data
	function canvasInit() {
        var chartTextColor = "";
        var chartGridColor = "";
        var chartAxisColor = "";
        var chartBarColor = "";
      	//line chart color
      	var minColor = "";
      	var maxColor = "";
      	var avgColor = "";
      	var theme = localStorage["color"];
		if (theme == "theme/red_blue/css/red.css") {
	        chartTextColor = "white"; // axis text color
	        chartGridColor = "white"; // grid
	        chartAxisColor = "white"; // axis color
	        chartBarColor = "#B1121D"; //Bar color
	        
	        //line chart color
	      	minColor = "#FF9900";
	      	maxColor = "#B2B2FF";
	      	avgColor = "red";
		} else if (theme == "theme/red_blue/css/blue.css") {
	        chartTextColor = "#4C4C4C";
	        chartGridColor = "#4C4C4C";
	        chartAxisColor = "#4C4C4C";
	        chartBarColor = "#00A8F0";
	        
	      	//line chart color
	      	minColor = "#00A8F0";
	      	maxColor = "#008000";
	      	avgColor = "red";
		} else if (theme == undefined || theme == "theme/photon/css/photon_theme.css") {
	        chartTextColor = "#4C4C4C";
	        chartGridColor = "#4F577C";
	        chartAxisColor = "#323232";
	        chartBarColor = "#39BC67";
	        
	      	//line chart color
	      	minColor = "#00A8F0";
	      	maxColor = "#008000";
	      	avgColor = "red";
		}
		
		var data = <%= graphData %>;
		var bar = new RGraph.Bar('myCanvas', data);
        bar.Set('chart.gutter.left', 70);
        bar.Set('chart.text.color', chartTextColor);
        bar.Set('chart.background.barcolor1', 'transparent');
        bar.Set('chart.background.barcolor2', 'transparent');
        bar.Set('chart.background.grid', true);
        bar.Set('chart.colors', [chartBarColor]);
        bar.Set('chart.background.grid.width', 0.5);
        bar.Set('chart.text.angle', 45);
        bar.Set('chart.gutter.bottom', 140);
        bar.Set('chart.background.grid.color', chartGridColor);
        bar.Set('chart.axis.color', chartAxisColor);
        bar.Set('chart.title', '<%= chartTitle %>');
        bar.Set('chart.title.color', chartTextColor);
        bar.Set('chart.labels',<%= graphLabel %>);
        bar.Set('chart.units.post', '<%= chartUnit %>');
        bar.Draw();
        
        <% 
        	if (request.getAttribute(FrameworkConstants.REQ_SHOW_GRAPH).equals(FrameworkConstants.REQ_TEST_SHOW_ALL_GRAPH)) {
        %>
				var line = new RGraph.Line('allData', <%= graphAllData %>);
		        line.Set('chart.background.grid', true);
		        line.Set('chart.linewidth', 5);
		        line.Set('chart.gutter.left', 85);
		        line.Set('chart.text.color', chartTextColor);
		        line.Set('chart.hmargin', 5);
		        if (!document.all || RGraph.isIE9up()) {
		            line.Set('chart.shadow', true);
		        }
		        line.Set('chart.tickmarks', 'endcircle');
		        line.Set('chart.units.post', 's');
		        line.Set('chart.colors', [minColor, avgColor, maxColor]);
		        line.Set('chart.background.grid.autofit', true);
		        line.Set('chart.background.grid.autofit.numhlines', 10);
		        line.Set('chart.curvy', true);
		        line.Set('chart.curvy.factor', 0.5); // This is the default
		        line.Set('chart.animation.unfold.initial',0);
		        line.Set('chart.labels',<%= graphLabel %>);
		        line.Set('chart.title','Response Time');// Title
		        line.Set('chart.axis.color', chartAxisColor);
		        line.Set('chart.text.angle', 45);
		        line.Set('chart.gutter.bottom', 140);
		        line.Set('chart.key', ['Min','Avg','Max']);
		        line.Set('chart.background.grid.color', chartGridColor);
		        line.Set('chart.title.color', chartTextColor);
		        line.Set('chart.shadow', false);
		        line.Draw();
        <% } %>
	}
<% } %>
</script>