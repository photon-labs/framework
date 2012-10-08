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

import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.LogErrorReport;

public class Download extends FrameworkBaseAction {

    private static final long serialVersionUID = -4735573440570585624L;
    
    private static final Logger S_LOGGER = Logger.getLogger(Applications.class);
    private static Boolean s_debugEnabled  = S_LOGGER.isDebugEnabled();
    
    private String projectCode = "";
    
    public String list() {
    	if (s_debugEnabled) {
    		S_LOGGER.debug("Entering Method Download.list()");
    	}
    	
    	try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			ApplicationInfo applicationInfo = administrator.getProject(getProjectCode()).getApplicationInfo();
			String cusomerId = applicationInfo.getCustomerIds().get(0);
			String techId = applicationInfo.getTechInfo().getVersion();
			setReqAttribute(REQ_SERVER_DOWNLOAD_INFO, administrator.getServerDownloadInfos(cusomerId, techId));
			setReqAttribute(REQ_DB_DOWNLOAD_INFO, administrator.getDbDownloadInfos(cusomerId, techId));
			setReqAttribute(REQ_EDITOR_DOWNLOAD_INFO, administrator.getEditorDownloadInfos(cusomerId, techId));
			setReqAttribute(REQ_TOOLS_DOWNLOAD_INFO, administrator.getToolsDownloadInfos(cusomerId, techId));
			setReqAttribute(REQ_OTHERS_DOWNLOAD_INFO, administrator.getOtherDownloadInfos(cusomerId, techId));
		} catch (PhrescoException e) {
			new LogErrorReport(e, "Listing downloads");
		}
    	
        return APP_DOWNLOAD;
    }
    
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}