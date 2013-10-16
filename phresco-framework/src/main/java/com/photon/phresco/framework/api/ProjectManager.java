/**
 * Phresco Framework
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.api;

import java.util.List;

import org.json.JSONObject;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.DashboardConfigInfo;
import com.photon.phresco.commons.model.DashboardWidgetConfigInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.DashboardSearchInfo;

public interface ProjectManager {

	/**
	 * This method provides the list of projects available in the local phresco workspace for the given customerId
	 * @param CustomerId
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discover(String customerId) throws PhrescoException;
	
	/**
	 * This method provides the list of projects of all customers available in the local phresco workspace 
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discover() throws PhrescoException;
	
	/**
	 * This method returns the Project for the given projectId
	 * @param projectId
	 * @return Returns the ProjectInfo with all the ApplicationInfos
	 * @throws PhrescoException
	 */
	ProjectInfo getProject(String projectId, String customerId) throws PhrescoException;
	
	/**
	 * This method creates the project with the selected application layers
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo create(ProjectInfo projectInfo, ServiceManager serviceManager) throws PhrescoException;
	
	/**
	 * This method updates the project
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo updateApplication(ProjectInfo projectInfo, ServiceManager serviceManager, String oldAppDirName) throws PhrescoException;
	
	
	ProjectInfo updateApplicationFeatures(ProjectInfo projectInfo, ServiceManager serviceManager) throws PhrescoException;
	/**
	 * This method deletes the project in local filesystem and not is server
	 * @param ProjectInfo
	 * @return boolean - returns true if deletion is success and false if deletion fails.
	 * @throws PhrescoException
	 */
	boolean delete(List<String> appDirNames) throws PhrescoException;
	
	/**
	 * This method returns the ProjectInfo of the given appId
	 * @param projectId
	 * @param customerId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	ProjectInfo getProject(String projectId, String customerId, String appId) throws PhrescoException;
	
	/**
	 * This method returns the Dashboardinfo of the given projectid
	 * @param projectId
	 * @return
	 * @throws PhrescoException
	 */
	DashboardConfigInfo getDashboardInfo(String projectId) throws PhrescoException;
	
	/**
	 * This method returns void
	 * @param dashboardConfigInfo
	 * @return
	 * @throws PhrescoException
	 */
	void updateDashboardInfo(DashboardConfigInfo dashboardConfigInfo) throws PhrescoException;
	
	/**
	 * This method returns void
	 * @param dashboardConfigInfo
	 * @return
	 * @throws PhrescoException
	 */
	void addDashboardInfo(DashboardConfigInfo dashboardConfigInfo) throws PhrescoException;
	
	/**
	 * This method returns list of widgets of given project id
	 * @param dashboardWidgetConfigInfo
	 * @param projectId
	 * @return
	 * @throws PhrescoException
	 */
	DashboardWidgetConfigInfo addDashboardWidgetInfo(DashboardWidgetConfigInfo dashboardWidgetConfigInfo , String projectId) throws PhrescoException;
	
	/**
	 * This method returns true if widget is present
	 * @param dashboardWidgetConfigInfo
	 * @return
	 * @throws PhrescoException
	 */
	Boolean configureDashboardWidgetInfo(DashboardWidgetConfigInfo dashboardWidgetConfigInfo , String projectId) throws PhrescoException;
	
	/**
	 * This method returns list of all widgets in the workspace
	 * @param poject id
	 * @return
	 * @throws PhrescoException
	 */
	List<DashboardWidgetConfigInfo> listAllDashboardWidgetInfo(String projectId) throws PhrescoException;
	
	/**
	 * This method returns the Search result of the given Dashboard query for a project
	 * @param projectId
	 * @param customerId
	 * @param appId
	 * @return
	 * @throws PhrescoException
	 */
	JSONObject getsplunkdata(DashboardSearchInfo dashboardsearchinfo) throws PhrescoException;
}
