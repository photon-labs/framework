/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.actions.applications;

import java.util.List;

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;

/**
 * Struts Action class for Handling Project related operations 
 * @author jeb
 */
public class Projects extends FrameworkBaseAction {

	private static final long serialVersionUID = 7143239782158726004L;
	
	private static final Logger S_LOGGER = Logger.getLogger(Projects.class);
	private static Boolean s_debugEnabled = S_LOGGER.isDebugEnabled();

	//To list the existing projects
	public String list() {
		if (s_debugEnabled) {
		    S_LOGGER.debug("Entering Method  Projects.list()");
		}

		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			String customerId = "";
			
			List<ProjectInfo> projects = projectManager.discover(customerId);
			
			setReqAttribute(REQ_PROJECTS, projects);

		} catch (PhrescoException e) {
			S_LOGGER.error("Entered into catch block of Projects.discover()" + FrameworkUtil.getStackTraceAsString(e));
			return showErrorPopup(e, "Discovering projects");
		}
		
		return SUCCESS;
	}
	
	//To open the Add New Project Page
	public String add() {
		if (s_debugEnabled) {
		    S_LOGGER.debug("Entering Method  Projects.add()");
		}
		
		return SUCCESS;
	}
	
	//To open the existing project(Project Detail)
	public String edit() {
		if (s_debugEnabled) {
		    S_LOGGER.debug("Entering Method  Projects.edit()");
		}
		
		return SUCCESS;
	}

	//To create a new project by calling the Framework API
	public String save() {
		if (s_debugEnabled) {
		    S_LOGGER.debug("Entering Method  Projects.save()");
		}
		
		return SUCCESS;
	}
	
	//To update the existing project by calling the Framework API
	public String update() {
		if (s_debugEnabled) {
		    S_LOGGER.debug("Entering Method  Projects.update()");
		}
		
		return SUCCESS;
	}
	
}