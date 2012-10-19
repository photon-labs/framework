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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ApplicationType;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.TechnologyGroup;
import com.photon.phresco.commons.model.TechnologyInfo;
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

    private String projectName = "";
    private String projectCode = "";
    private String projectDesc = "";
    private String projectVersion = "";
    private List<String> layer = null;

    private List<TechnologyInfo> widgets = null;
    private List<String> versions = null;
    
    private List<String> selectedProjectId = new ArrayList<String>();

    private boolean errorFound = false;
    private String projectNameError = "";
    private String projectCodeError = "";
    private String layerError = "";
    private String mobTechError = "";
    private String appTechError = "";
    private String webTechError = "";

    /**
     * To get the list of projects
     * @return
     */
    public String list() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.list()");
        }

        try {
            ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
            List<ProjectInfo> projects = projectManager.discover(getCustomerId());
            setReqAttribute(REQ_PROJECTS, projects);
            setReqAttribute(REQ_SELECTED_MENU, APPLICATIONS);
            removeSessionAttribute(projectCode);
        } catch (PhrescoException e) {
            S_LOGGER.error("Entered into catch block of Applications.list()" + FrameworkUtil.getStackTraceAsString(e));
            return showErrorPopup(e, EXCEPTION_PROJECT_LIST);
        }

        return APP_LIST;
    }

    /**
     * To get the add project page
     * @return
     */
    public String addProject() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.applicationDetails()");
        }

        try {
            List<ApplicationType> layers = getServiceManager().getApplicationTypes(getCustomerId());
            setReqAttribute(REQ_PROJECT_LAYERS, layers);
        } catch (PhrescoException e) {
            return showErrorPopup(e, EXCEPTION_PROJECT_ADD);
        }

        return APP_APPLICATION_DETAILS;
    }

    /**
     * To get the selected mobile technology's version
     * @return
     */
    public String fetchMobileTechVersions() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.fetchMobileTechVersions()");
        }

        try {
            String layerId = getHttpRequest().getParameter(REQ_PARAM_NAME_LAYER_ID);
            String techGroupId = getHttpRequest().getParameter(REQ_PARAM_NAME_TECH_GROUP_ID);
            List<TechnologyGroup> techGroups = filterLayer(layerId).getTechGroups();
            TechnologyGroup technologyGroup = filterTechnologyGroup(techGroups, techGroupId);
            String techId = getHttpRequest().getParameter(technologyGroup.getId() + REQ_PARAM_NAME_TECHNOLOGY);
            List<TechnologyInfo> techInfos = technologyGroup.getTechInfos();
            if (CollectionUtils.isNotEmpty(techInfos)) {
                for (TechnologyInfo techInfo : techInfos) {
                    if (techInfo.getId().equals(techId)) {
                        setVersions(techInfo.getTechVersions());
                        break;
                    }
                }
            }
        } catch (PhrescoException e) {
            return showErrorPopup(e, EXCEPTION_PROJECT_MOB_TECH_VERSIONS);
        }

        return SUCCESS;
    }

    /**
     * To the selected web layer's widgets
     * @return
     */
    public String fetchWebLayerWidgets() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.fetchMobileTechVersions()");
        }

        try {
            String layerId = getHttpRequest().getParameter(REQ_PARAM_NAME_LAYER_ID);
            String techGroupId = getHttpRequest().getParameter(layerId + REQ_PARAM_NAME_TECH_GROUP);
            List<TechnologyGroup> techGroups = filterLayer(layerId).getTechGroups();
            TechnologyGroup technologyGroup = filterTechnologyGroup(techGroups, techGroupId);
            setWidgets(technologyGroup.getTechInfos());
        } catch (PhrescoException e) {
            return showErrorPopup(e, EXCEPTION_PROJECT_WEB_LAYER_WIDGETS);
        }

        return SUCCESS;
    }

    /**
     * To get the layer based on the given layer name
     * @param layers
     * @param layerName
     * @return
     * @throws PhrescoException 
     */
    private ApplicationType filterLayer(String layerId) throws PhrescoException {
        List<ApplicationType> layers = getServiceManager().getApplicationTypes(getCustomerId());
        if (CollectionUtils.isNotEmpty(layers)) {
            for (ApplicationType layer : layers) {
                if (layer.getId().equals(layerId)) {
                    return layer;
                }
            }
        }

        return null;
    }

    /**
     * To get the technology group based on the given technology group Id
     * @param technologyGroups
     * @param id
     * @return
     */
    private TechnologyGroup filterTechnologyGroup(List<TechnologyGroup> technologyGroups, String id) {
        if (CollectionUtils.isNotEmpty(technologyGroups)) {
            for (TechnologyGroup technologyGroup : technologyGroups) {
                if (technologyGroup.getId().equalsIgnoreCase(id)) {
                    return technologyGroup;
                }
            }
        }

        return null;
    }

    /**
     * To create the project with the selected applications
     * @return
     */
    public String createProject() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.createProject()");
        }

        try {
            PhrescoFrameworkFactory.getProjectManager().create(getProjectInfo(), getServiceManager());
        } catch (PhrescoException e) {
            return showErrorPopup(e, EXCEPTION_PROJECT_CREATE);
        }

        return list();
    }

    /**
     * To get the projectInfo with the selected application infos
     * @return
     * @throws PhrescoException
     */
    private ProjectInfo getProjectInfo() throws PhrescoException {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setName(getProjectName());
        projectInfo.setVersion(getProjectVersion());
        projectInfo.setDescription(getProjectDesc());
        projectInfo.setProjectCode(getProjectCode());
        projectInfo.setCustomerIds(Collections.singletonList(getCustomerId()));
        List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
        if (CollectionUtils.isNotEmpty(getLayer())) {
            for (String layerId : getLayer()) {
                if (LAYER_MOB_ID.equals(layerId)) {
                    getMobileLayerAppInfos(appInfos, layerId);
                } else {
                    getOtherLayerAppInfos(appInfos, layerId);
                }
            }
        }
        projectInfo.setAppInfos(appInfos);
        projectInfo.setNoOfApps(appInfos.size());

        return projectInfo;
    }

    /**
     * To get the application infos for mobile technology
     * @param appInfos
     * @param layerId
     * @return
     * @throws PhrescoException
     */
    private List<ApplicationInfo> getMobileLayerAppInfos(List<ApplicationInfo> appInfos, String layerId) throws PhrescoException {
        String[] techGroupIds = getHttpRequest().getParameterValues(layerId + REQ_PARAM_NAME_TECH_GROUP);
        if (!ArrayUtils.isEmpty(techGroupIds)) {
            for (String techGroupId : techGroupIds) {
                String techId = getHttpRequest().getParameter(techGroupId + REQ_PARAM_NAME_TECHNOLOGY);
                String version = getHttpRequest().getParameter(techGroupId + REQ_PARAM_NAME_VERSION);
                boolean phoneEnabled = Boolean.parseBoolean(getHttpRequest().getParameter(techGroupId + REQ_PARAM_NAME_PHONE));
                boolean tabletEnabled = Boolean.parseBoolean(getHttpRequest().getParameter(techGroupId + REQ_PARAM_NAME_TABLET));
                appInfos.add(getAppInfo(getProjectName() + HYPHEN + techGroupId, techId, version, phoneEnabled, tabletEnabled));
            }
        }

        return appInfos;
    }

    /**
     * To get the application infos for app and web technologies
     * @param appInfos
     * @param layerId
     * @return
     * @throws PhrescoException
     */
    private List<ApplicationInfo> getOtherLayerAppInfos(List<ApplicationInfo> appInfos, String layerId) throws PhrescoException {
        String techId = getHttpRequest().getParameter(layerId + REQ_PARAM_NAME_TECHNOLOGY);
        String version = getHttpRequest().getParameter(layerId + REQ_PARAM_NAME_VERSION);
        appInfos.add(getAppInfo(getProjectName() + HYPHEN + techId, techId, version, false, false));

        return appInfos;
    }

    /**
     * To get the single application info for the given technology
     * @param dirName
     * @param techId
     * @param version
     * @param phoneEnabled
     * @param tabletEnabled
     * @return
     * @throws PhrescoException
     */
    private ApplicationInfo getAppInfo(String dirName, String techId, String version, boolean phoneEnabled, boolean tabletEnabled) throws PhrescoException {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        TechnologyInfo techInfo = new TechnologyInfo();
        techInfo.setId(techId);
        techInfo.setVersion(version);
        applicationInfo.setTechInfo(techInfo);
        applicationInfo.setName(dirName);
        applicationInfo.setAppDirName(dirName);
        applicationInfo.setPhoneEnabled(phoneEnabled);
        applicationInfo.setTabletEnabled(tabletEnabled);

        return applicationInfo;
    }
    
    /**
     * To delete the selected projects or applications
     * @return
     */
    public String delete() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.validateForm()");
        }
        
        /*try {
            ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
            if (CollectionUtils.isNotEmpty(getSelectedProjectId())) {
                List<ProjectInfo> deletableProjectInfos = new ArrayList<ProjectInfo>();
                for (String projectId : getSelectedProjectId()) {
                    deletableProjectInfos.add(projectManager.getProject(projectId, getCustomerId()));
                    ProjectInfo projectInfo = projectManager.getProject(projectId, getCustomerId());
                }
            }
            projectManager.delete(null);
        } catch (PhrescoException e) {

        }*/
        
        return list();
    }

    /**
     * To validate the form fields
     * @return
     */
    public String validateForm() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.validateForm()");
        }

        boolean hasError = false;
        //empty validation for name
        if (StringUtils.isEmpty(getProjectName())) {
            setProjectNameError(getText(ERROR_NAME));
            hasError = true;
        }
        //empty validation for projectCode
        if (StringUtils.isEmpty(getProjectCode())) {
            setProjectCodeError(getText(ERROR_CODE));
            hasError = true;
        }
        //validate if none of the layer is selected
        if (CollectionUtils.isEmpty(getLayer())) {
            setAppTechError(getText(ERROR_TECHNOLOGY));
            setWebTechError(getText(ERROR_TECHNOLOGY));
            setMobTechError(getText(ERROR_TECHNOLOGY));
            hasError = true;
        }
        //empty validation for technology in the selected layer
        if (CollectionUtils.isNotEmpty(getLayer())) {
            for (String layerId : getLayer()) {
                String techId = getHttpRequest().getParameter(layerId + REQ_PARAM_NAME_TECHNOLOGY);
                if (LAYER_APP_ID.equals(layerId)) {//for application layer
                    if (StringUtils.isEmpty(techId)) {
                        setAppTechError(getText(ERROR_TECHNOLOGY));
                        hasError = true;
                    }
                }
                if (LAYER_WEB_ID.equals(layerId)) {//for web layer
                    if (StringUtils.isEmpty(techId)) {
                        setWebTechError(getText(ERROR_TECHNOLOGY));
                        hasError = true;
                    }
                }
                if (LAYER_MOB_ID.equals(layerId)) {//for mobile layer
                    String[] techGroupIds = getHttpRequest().getParameterValues(layerId + REQ_PARAM_NAME_TECH_GROUP);
                    if (ArrayUtils.isEmpty(techGroupIds)) {//empty validation for technology group
                        setMobTechError(getText(ERROR_TECHNOLOGY));
                        hasError = true;
                    } else {
                        for (String techGroupId : techGroupIds) {//empty validation for technology in the selected technology group
                            techId = getHttpRequest().getParameter(techGroupId + REQ_PARAM_NAME_TECHNOLOGY);
                            if (StringUtils.isEmpty(techId)) {
                                setMobTechError(getText(ERROR_LAYER));
                                hasError = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (hasError) {
            setErrorFound(true);
        }

        return SUCCESS;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public boolean isErrorFound() {
        return errorFound;
    }

    public void setErrorFound(boolean errorFound) {
        this.errorFound = errorFound;
    }

    public String getProjectNameError() {
        return projectNameError;
    }

    public void setProjectNameError(String projectNameErr) {
        this.projectNameError = projectNameErr;
    }

    public String getProjectCodeError() {
        return projectCodeError;
    }

    public void setProjectCodeError(String projectCodeErr) {
        this.projectCodeError = projectCodeErr;
    }

    public List<TechnologyInfo> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<TechnologyInfo> widgets) {
        this.widgets = widgets;
    }

    public List<String> getLayer() {
        return layer;
    }

    public void setLayer(List<String> layer) {
        this.layer = layer;
    }

    public String getMobTechError() {
        return mobTechError;
    }

    public void setMobTechError(String mobTechError) {
        this.mobTechError = mobTechError;
    }

    public String getAppTechError() {
        return appTechError;
    }

    public void setAppTechError(String appLayerError) {
        this.appTechError = appLayerError;
    }

    public String getWebTechError() {
        return webTechError;
    }

    public void setWebTechError(String webTechError) {
        this.webTechError = webTechError;
    }

    public String getLayerError() {
        return layerError;
    }

    public void setLayerError(String layerError) {
        this.layerError = layerError;
    }
    
    public List<String> getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(List<String> selectedProject) {
        this.selectedProjectId = selectedProject;
    }
}