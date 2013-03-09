<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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


<%@ page import="java.io.FilenameFilter"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
/**
  * jQuery File Tree JSP Connector
  * Version 1.0
  * Copyright 2008 Joshua Gould
  * 21 April 2008
*/	
    String dir = request.getParameter("dir");
	String rootDir = request.getParameter("rootDir");
    String fileTypes = request.getParameter("restrictFileTypes");
    String filesOrFolders = request.getParameter("filesOrFolders");
    String fromPage = request.getParameter(FrameworkConstants.REQ_FROM_PAGE);
    String minifiedFiles = request.getParameter("minifiedFilesList");
	
    if (!"funcTestAgaistJar".equals(fromPage)) {
    	if (dir != "" && dir.charAt(dir.length()-1) == '\\') {
        	dir = dir.substring(0, dir.length()-1) + "/";
    	} else if (dir != "" && dir.charAt(dir.length()-1) != '/') {
    	    dir += "/";
    	} 
    }
	
 	dir = java.net.URLDecoder.decode(dir, "UTF-8");
 	
 	fileTypes = java.net.URLDecoder.decode(fileTypes, "UTF-8");	
 	filesOrFolders = java.net.URLDecoder.decode(filesOrFolders, "UTF-8");	
 	minifiedFiles = java.net.URLDecoder.decode(minifiedFiles, "UTF-8");
 	List<String> minifiedFilesList = Arrays.asList(minifiedFiles.split(FrameworkConstants.CSV_PATTERN));
 	
	final String[] includeFileTypes = fileTypes.split(",");
	
	//If dir is empty , file browse popup wil lists all root directories
	if(!new File(dir).exists()) {
		File[] roots = File.listRoots();
		for (int i=0; i < roots.length; i++) {
			out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
			out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + roots[i].toString() + "/\">"
					+ roots[i].toString() + "</a></li>");
			out.print("</ul>");
		}
	}
	
	if (FrameworkConstants.PACKAGE.equals(fromPage)) {
		if (new File(dir).exists()) {
		    File rootFile = new File(dir);
		    File[] files = rootFile.listFiles();
		    out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
		    
		    //To list all the directories
		    for (File file : files) {
			    if (file.isDirectory()) {
			        String directory = file.getPath();
			        directory = directory.replace("\\", "/");
			        directory = directory.replace(rootDir, "");
					out.print("<li class=\"directory collapsed\"><a href=\"#\" directory=\"" + directory + "\"  rel=\"" + file.getPath() + "/\">"
						+ file.getName() + "</a></li>");
			    }
			}
		    
		    //To list the files
		    for (File file : files) {
			    if (!file.isDirectory()) {
			        int dotIndex = file.getName().lastIndexOf('.');
					String ext = dotIndex > 0 ? file.getName().substring(dotIndex + 1) : "";
					if(!fileTypes.equals("")) {
						if (!Arrays.asList(includeFileTypes).contains(ext)) {
				    		continue;
				    	}
					}
					String filePath = file.getPath();
				    filePath = filePath.replace("\\", "/");
					filePath = filePath.replace(rootDir, "");
					out.print("<li class=\"file ext_" + ext + "\"><input type=checkbox name=filesToMinify id=\""+file.getName()+"\" value=\""+ filePath +"\" onclick=\"uncheckOthrDirFiles();\"><span class=jsmin-span >"
							+ file.getName() + "</li>"); 
			    }
			}
		    
		    out.print("</ul>");
		}
	} else {
	    if (new File(dir).exists()) {
			String[] files = new File(dir).list(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
					return name.charAt(0) != '.';
			    }
			});
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
			out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
			// All dirs
			for (String file : files) {
			    if (new File(dir, file).isDirectory()) {
					out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file + "/\">"
						+ file + "</a></li>");
			    }
			}
			// All files
			if (filesOrFolders.equals("File") || filesOrFolders.equals("All")) {
				for (String file : files) {
				    if (!new File(dir, file).isDirectory()) {
						int dotIndex = file.lastIndexOf('.');
						String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";

						if (!fileTypes.equals("")) {
							if (!Arrays.asList(includeFileTypes).contains(ext)) {
					    		continue;
					    	}
						}
						if (StringUtils.isNotEmpty(fromPage) && FrameworkConstants.REQ_MINIFICATION.equals(fromPage)) {
							String checkedStr = "";
							if (minifiedFilesList.contains(file)) {
								checkedStr = "checked";
							} else {
								checkedStr = "";
							}
							out.print("<li><input type=checkbox name=filesToMinify id=\""+file+"\" value=\""+file+"\" "+checkedStr+"><span class=jsmin-span >"
									+ file + "</li>"); 
						} else {
							out.print("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir + file + "\">"
									+ file + "</a></li>");
						}
				    }
				}
			}
			out.print("</ul>");
	    }
	}
     
%>
<script type="text/javascript">
	$(document).ready(function() {
		hidePopuploadingIcon();
		
		<% if (FrameworkConstants.REQ_MINIFICATION.equals(fromPage)) { %>
			$('#browseSelectedLocation').hide();
			$('#compressName').show();
			$('#compressNameLbl').show();
		<% } else { %>
			$('#compressName').hide();
			$('#compressNameLbl').hide();
			$('#browseSelectedLocation').show();
		<% } %>
		
		<% if ("package".equals(fromPage)) { %>
			$('#browseSelectedLocation').hide();
		<% } %>
	});
	
	//To uncheck the files that are selected in the directories other than the current directory
	function uncheckOthrDirFiles() {
		var selectedDir = $(".selectedFolder").attr("directory");
		$("input[name=filesToMinify]:checked").each(function() {
			var selectedVal = $(this).val();
			if (selectedDir != undefined && !isBlank(selectedDir)) {
				var lwrSelectedVal = selectedVal.toLowerCase();
				var lwrSelectedDir = selectedDir.toLowerCase();
				if (!lwrSelectedVal.startsWith(lwrSelectedDir)) {
					$(this).attr("checked", false);
				}
			}
		});
	}
</script>