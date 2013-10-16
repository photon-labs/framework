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

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.Dashboard;
import com.photon.phresco.commons.model.DashboardConfigInfo;
import com.photon.phresco.commons.model.DashboardWidgetConfigInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Widget;
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
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	boolean configureDashboardConfig(String projectid, String datatype , String username, String password, String url) throws PhrescoException;
	

	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	HashMap<String, String> getDashboardConfig(String projectid) throws PhrescoException;
	
	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	boolean updateDashboardConfig(String projectid, String datatype , String username, String password, String url) throws PhrescoException;
	

	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	HashMap<String, Widget> addDashboardWidgetConfig(String projectid, String query , String name, String appid, String appcode, String appname) throws PhrescoException;
	
	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	HashMap<String, Widget> getDashboardWidgetConfig(String projectid, String appid, String appcode, String appname) throws PhrescoException;
	
	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	Boolean updateDashboardWidgetConfig(String projectid, String query , String name, String widgetid, String appid, String appcode, String appname) throws PhrescoException;

	/**
	 * This method returns dashboardconfig of the created dashboard
	 * @param poject id
	 * @param app id
	 * @param app directory
	 * @param dashboard
	 * @return DashboardConfigInfo
	 * @throws PhrescoException
	 */
	HashMap<String, Dashboard> listDashboardWidgetConfig(String projectid) throws PhrescoException;
	
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
