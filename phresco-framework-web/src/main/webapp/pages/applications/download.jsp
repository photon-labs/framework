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

<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.model.ArtifactInfo"%>
<%@ page import="com.photon.phresco.commons.model.DownloadInfo"%>


<%
	List<DownloadInfo> serverDownloadInfos = (List<DownloadInfo>) request.getAttribute(FrameworkConstants.REQ_SERVER_DOWNLOAD_INFO);
	List<DownloadInfo> dbDownloadInfos = (List<DownloadInfo>) request.getAttribute(FrameworkConstants.REQ_DB_DOWNLOAD_INFO);
	List<DownloadInfo> editorDownloadInfos = (List<DownloadInfo>) request.getAttribute(FrameworkConstants.REQ_EDITOR_DOWNLOAD_INFO);
	List<DownloadInfo> toolsDownloadInfos = (List<DownloadInfo>) request.getAttribute(FrameworkConstants.REQ_TOOLS_DOWNLOAD_INFO);
	List<DownloadInfo> othersDownloadInfos = (List<DownloadInfo>) request.getAttribute(FrameworkConstants.REQ_OTHERS_DOWNLOAD_INFO);
	boolean serverDownloadUrl = false;
	boolean dbDownloadUrl = false;
	boolean editorDownloadUrl = false;
	boolean toolsDownloadUrl = false;
	boolean othersDownloadUrl = false;
	Long fileSize = null;
%>

<div class="page-header">
	<h1 style="float: left;">
		<s:text name="lbl.hdr.download"/>
	</h1>
</div>

<div class="theme_accordion_container">
    <section class="accordion_panel_wid">
        <div class="accordion_panel_inner">
            <section class="lft_menus_container">
            	<% 
            		if (CollectionUtils.isNotEmpty(serverDownloadInfos)) {
            			serverDownloadUrl = false;
            			for (DownloadInfo serverDownloadInfo : serverDownloadInfos) {
                			List<ArtifactInfo> infos = serverDownloadInfo.getArtifactGroup().getVersions();
							if (CollectionUtils.isNotEmpty(infos)) {
								for (ArtifactInfo info : infos) {
									if (StringUtils.isNotEmpty(info.getDownloadURL())) {
										serverDownloadUrl = true;
										break;
									}
								}
							}
							if (serverDownloadUrl) {
								break;
							}
            			}
            		if (serverDownloadUrl) { 
            	%>
	                <span class="siteaccordion closereg">
		                <div>
			                <img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
			                <s:text name="lbl.servers"/>
			            </div>
			        </span>
	                <div class="mfbox siteinnertooltiptxt downloadContent">
	                    <div class="scrollpanel">
	                        <section class="scrollpanel_inner">
	                        	<table class="download_tbl">
		                        	<thead>
		                            	<tr class="download_tbl_header">
	                            			<th><s:text name="lbl.name"/></th>
	                            			<th><s:text name="lbl.version"/></th>
	                            			<th><s:text name="lbl.size"/></th>
	                            			<th class="label_center"><s:text name="lbl.download"/></th>
	                            		</tr>
		                            </thead>
		                            
		                        	<tbody>
			                    	<%
										for (DownloadInfo serverDownloadInfo : serverDownloadInfos) {
		                    			List<ArtifactInfo> infos = serverDownloadInfo.getArtifactGroup().getVersions();
											if (CollectionUtils.isNotEmpty(infos)) {
												for (ArtifactInfo info : infos) {
													if (StringUtils.isNotEmpty(info.getDownloadURL()) && info.getFileSize() != 0) {
														fileSize = info.getFileSize();
														String size = "";
														if (fileSize > 1048576) {
															size = Long.toString(fileSize/1048576) + FrameworkConstants.MEGABYTE;
														} else {
															size = Long.toString(fileSize/1024) + FrameworkConstants.KILOBYTE;
														}
		                    		%> 
						                    		<tr>
						                    			<td><%= serverDownloadInfo.getName() %></td>
						                    			<td><%= info.getVersion() %></td>
						                    			<td><%= size %></td>
						                    			<td class="label_center">
						                    				<a href="<%= info.getDownloadURL()%>"> 
						                    					<img src="images/icons/download.png" title="<%=serverDownloadInfo.getName() %>" />
						                    				</a>
						                    			</td> 
						                    		</tr>
			                    	<%	
													}
												}
			                    			}
			                    		} 
			                    	%> 
	                        	</table>
	                        </section>
	                    </div>
	                </div>
                <% 
                	}
           		} if (CollectionUtils.isNotEmpty(dbDownloadInfos)) { 
						dbDownloadUrl = false;
	        			for (DownloadInfo dbDownloadInfo : dbDownloadInfos) {
	            			List<ArtifactInfo> infos = dbDownloadInfo.getArtifactGroup().getVersions();
							if (CollectionUtils.isNotEmpty(infos)) {
								for (ArtifactInfo info : infos) {
									if (StringUtils.isNotEmpty(info.getDownloadURL())) {
										dbDownloadUrl = true;
										break;
									}
								}
							}
							if (dbDownloadUrl) {
								break;
							}
	        			}
	        		if (dbDownloadUrl) { 
                %>
	                <span class="siteaccordion closereg">
	                	<div>
	                		<s:text name="lbl.database"/>
	                		<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
	                	</div>
	                </span>
	                <div class="mfbox siteinnertooltiptxt downloadContent">
	                    <div class="scrollpanel">
	                        <section class="scrollpanel_inner">
	                        	<table class="download_tbl">
		                        	<thead>
		                            	<tr class="download_tbl_header">
	                            			<th><s:text name="lbl.name"/></th>
	                            			<th><s:text name="lbl.version"/></th>
	                            			<th><s:text name="lbl.size"/></th>
	                            			<th class="label_center"><s:text name="lbl.download"/></th>
	                            		</tr>	
		                            </thead>
		                            
		                        	<tbody>
			                    	<%
										for (DownloadInfo dbDownloadInfo : dbDownloadInfos) {
			                    		List<ArtifactInfo> infos = dbDownloadInfo.getArtifactGroup().getVersions();
											if (CollectionUtils.isNotEmpty(infos)) {
												for (ArtifactInfo info : infos) { 
													if (StringUtils.isNotEmpty(info.getDownloadURL()) && info.getFileSize() != 0) {
														fileSize = info.getFileSize();
														String size = "";
														if (fileSize > 1048576) {
															size = Long.toString(fileSize/1048576) + FrameworkConstants.MEGABYTE;
														} else {
															size = Long.toString(fileSize/1024) + FrameworkConstants.KILOBYTE;
														}
			                    	%>
						                    		<tr>
						                    			<td><%= dbDownloadInfo.getName() %></td>
						                    			<td><%= info.getVersion() %></td>
						                    			<td><%= size %></td>
						                    			<td class="label_center">
						                    				<a href="<%= 
						                    					info.getDownloadURL()
						                    					
						                    				%>"> 
						                    					<img src="images/icons/download.png" title="<%=dbDownloadInfo.getName()%>"/>
						                    				</a>
						                    			</td>
						                    		</tr>
									<%	
					                    			}
												}
					                    	}
					                    }
									%>
		                    		</tbody>
	                        	</table>
	                        </section>
	                    </div>
	                </div>
                <% 
						}                	
					} if (CollectionUtils.isNotEmpty(editorDownloadInfos)) {
						editorDownloadUrl = false;
	        			for (DownloadInfo editorDownloadInfo : editorDownloadInfos) {
	            			List<ArtifactInfo> infos = editorDownloadInfo.getArtifactGroup().getVersions();
							if (CollectionUtils.isNotEmpty(infos)) {
								for (ArtifactInfo info : infos) {
									if (StringUtils.isNotEmpty(info.getDownloadURL())) {
										editorDownloadUrl = true;
										break;
									}
								}
							}
							if (editorDownloadUrl) {
								break;
							}
	        			}
					if (editorDownloadUrl) {
                %>
		                <span class="siteaccordion closereg">
			                <div>
		                		<s:text name="lbl.editors"/>
		                		<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
		                	</div>
		                </span>
		                <div class="mfbox siteinnertooltiptxt downloadContent">
		                    <div class="scrollpanel">
		                        <section class="scrollpanel_inner">
		                        	<table class="download_tbl">
			                        	<thead>
			                            	<tr class="download_tbl_header">
		                            			<th><s:text name="lbl.name"/></th>
		                            			<th><s:text name="lbl.version"/></th>
		                            			<th><s:text name="lbl.size"/></th>
		                            			<th class="label_center"><s:text name="lbl.download"/></th>
		                            		</tr>
			                            </thead>
			                        	
			                        	<tbody>
				                    	<%
											for (DownloadInfo editorDownloadInfo : editorDownloadInfos) {
											List<ArtifactInfo> infos = editorDownloadInfo.getArtifactGroup().getVersions();
												if (CollectionUtils.isNotEmpty(infos)) {
													for (ArtifactInfo info : infos) {
														if (StringUtils.isNotEmpty(info.getDownloadURL()) && info.getFileSize() != 0) {
															fileSize = info.getFileSize();
															String size = "";
															if (fileSize > 1048576) {
																size = Long.toString(fileSize/1048576) + FrameworkConstants.MEGABYTE;
															} else {
																size = Long.toString(fileSize/1024) + FrameworkConstants.KILOBYTE;
																
															}
				                    	%> 
							                    		<tr>
							                    			<td><%= editorDownloadInfo.getName() %></td>
							                    			<td><%= info.getVersion() %></td>
							                    			<td><%= size %></td>
							                    			<td class="label_center">
							                    				<a href= "<%= info.getDownloadURL() %>">
							                    					<img src="images/icons/download.png" title="<%= editorDownloadInfo.getName()%>"/>
							                    				</a>
							                    			</td> 
							                    		</tr>
										<%	
														}
													} 
												}
											} 
										%>
			                    		</tbody>
		                        	</table>
		                        </section>
		                    </div>
		                </div>
                
                <% 
						}
					} if (CollectionUtils.isNotEmpty(toolsDownloadInfos)) {
						toolsDownloadUrl = false;
							for (DownloadInfo toolsDownloadInfo : toolsDownloadInfos) {
								List<ArtifactInfo> infos = toolsDownloadInfo.getArtifactGroup().getVersions();
								if (CollectionUtils.isNotEmpty(infos)) {
									for (ArtifactInfo info : infos) {
										if (StringUtils.isNotEmpty(info.getDownloadURL())) {
											toolsDownloadUrl = true;
											break;
										}
									}
								}
							if (toolsDownloadUrl) {
								break;
								}
							}
					if (toolsDownloadUrl) {
                %>
	                <span class="siteaccordion closereg">
	                 	<div>
	                		<s:text name="lbl.tools"/>
	                		<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
	                	</div>
	                </span>
	                <div class="mfbox siteinnertooltiptxt downloadContent">
	                    <div class="scrollpanel">
	                        <section class="scrollpanel_inner">
	                        	<table class="download_tbl">
		                        	<thead>
		                            	<tr class="download_tbl_header">
	                            			<th><s:text name="lbl.name"/></th>
	                            			<th><s:text name="lbl.version"/></th>
	                            			<th><s:text name="lbl.size"/></th>
	                            			<th class="label_center"><s:text name="lbl.download"/></th>
	                            		</tr>	
		                            </thead>
		                            
		                        	<tbody>
			                    	<%
										for (DownloadInfo toolsDownloadInfo : toolsDownloadInfos) {
										List<ArtifactInfo> infos = toolsDownloadInfo.getArtifactGroup().getVersions();
											if (CollectionUtils.isNotEmpty(infos)) {
												for (ArtifactInfo info : infos) {
													if (StringUtils.isNotEmpty(info.getDownloadURL()) && info.getFileSize() != 0) {
														fileSize = info.getFileSize();
														String size = "";
														if (fileSize > 1048576) {
															size = Long.toString(fileSize/1048576) + FrameworkConstants.MEGABYTE;
														} else {
															size = Long.toString(fileSize/1024) + FrameworkConstants.KILOBYTE;
															
														}
			                    	%> 
						                    		<tr>
						                    			<td><%= toolsDownloadInfo.getName() %></td>
						                    			<td><%= info.getVersion() %></td>
						                    			<td><%= size %></td>
						                    			<td class="label_center">
						                    				<a href= "<%= info.getDownloadURL() %>">
						                    					<img src="images/icons/download.png" title="<%=toolsDownloadInfo.getName() %>"/>
						                    				</a>
						                    			</td> 
						                    		</tr>
		                    		<%	
													}
												}
											}
										}
									%>
		                    		</tbody>
	                        	</table>
	                        </section>
	                    </div>
	                </div>
                
                <% 
						}
                	} if (CollectionUtils.isNotEmpty(othersDownloadInfos)) {
                		othersDownloadUrl = false;
						for (DownloadInfo othersDownloadInfo : othersDownloadInfos) {
							List<ArtifactInfo> infos = othersDownloadInfo.getArtifactGroup().getVersions();
							if (CollectionUtils.isNotEmpty(infos)) {
								for (ArtifactInfo info : infos) {
									if (StringUtils.isNotEmpty(info.getDownloadURL())) {
										othersDownloadUrl = true;
										break;
									}
								}
							}
						if (othersDownloadUrl) {
							break;
							}
						}
				if (othersDownloadUrl) {
                %>
	                <span class="siteaccordion closereg">
	                	<div>
	                		<s:text name="lbl.others"/>
	                		<img src="images/r_arrowclose.png" class ="accImg" id="" onclick="accordionClickOperation(this);">
	                	</div>
	                </span>
	                <div class="mfbox siteinnertooltiptxt downloadContent">
	                    <div class="scrollpanel">
	                        <section class="scrollpanel_inner">
	                        	<table class="download_tbl">
		                        	<thead>
		                            	<tr class="download_tbl_header">
	                            			<th><s:text name="lbl.name"/></th>
	                            			<th><s:text name="lbl.version"/></th>
	                            			<th><s:text name="lbl.size"/></th>
	                            			<th class="label_center"><s:text name="lbl.download"/></th>
	                            		</tr>	
		                            </thead>
		                            
		                        	<tbody>
			                    	<%
										for (DownloadInfo otherDownloadInfo : othersDownloadInfos) {
										List<ArtifactInfo> infos = otherDownloadInfo.getArtifactGroup().getVersions();
											if (CollectionUtils.isNotEmpty(infos)) {
												for (ArtifactInfo info : infos) {
													if (StringUtils.isNotEmpty(info.getDownloadURL()) && info.getFileSize() != 0) {
														fileSize = info.getFileSize();
														String size = "";
														if (fileSize > 1048576) {
															size = Long.toString(fileSize/1048576) + FrameworkConstants.MEGABYTE;
														} else {
															size = Long.toString(fileSize/1024) + FrameworkConstants.KILOBYTE;
															
														}
		                    		%> 
						                    		<tr>
						                    			<td><%= otherDownloadInfo.getName() %></td>
						                    			<td><%= info.getVersion() %></td>
						                    			<td><%= size %></td>
						                    			<td class="label_center">
						                    				<a href= "<%= info.getDownloadURL() %>">
						                    					<img src="images/icons/download.png" title="<%= otherDownloadInfo.getName() %>"/>
						                    				</a>
						                    			</td> 
						                    		</tr>
									<%	
													}
												}
											}
										} 
									%>
		                    		</tbody>
	                        	</table>
	                        </section>
	                    </div>
	                </div>
                <% 
						}
					}
                %>
            </section> 
                
			<% if (serverDownloadUrl == false && dbDownloadUrl == false && editorDownloadUrl == false && toolsDownloadUrl == false && othersDownloadUrl == false) { %>
					<div class="alert alert-block">
						<s:text name="lbl.err.msg.list.downloads"/>
					</div> 
			<% } %>
        </div>
    </section>
</div>

<script type="text/javascript">
	accordionOperation();//To create the accordion
	
	//To check whether the device is ipad or not and then apply jquery scrollbar
	if(!isiPad()) {
		$(".theme_accordion_container").scrollbars();		
	}
	
	$(document).ready(function(){
		hideLoadingIcon();
	});
	
</script>