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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.DownloadInfo;
import com.photon.phresco.commons.model.Element;
import com.photon.phresco.commons.model.RequiredOption;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.commons.model.WebService;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.ApplicationsUtil;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;

public class Features extends FrameworkBaseAction {
	private static final long serialVersionUID = 6608382760989903186L;
	private static final Logger S_LOGGER = Logger.getLogger(Features.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
	private String projectCode = null;
	private String externalCode = null;
	private String fromPage = null;
	private String name = null;
	private String code = null;
	private String groupId = null;
	private String projectVersion = null;
	private String artifactId = null;
	private String description = null;
	private String application = null;
	private String technology = null;
	private List<String> techVersion = null;
	private String nameError = null;
	private String moduleId = null;
	private String version = null;
	private String moduleType = null;
	private String techId = null;
	private String preVersion = null;
	private Collection<String> dependentIds = null;
	private Collection<String> dependentVersions = null;
	private Collection<String> preDependentIds = null;
	private Collection<String> preDependentVersions = null;
	private List<String> pilotModules = null;
	private List<String> pilotJSLibs = null;
	private String configServerNames = null;
	private String configDbNames = null;
	private String fromTab = null;
	private List<String> defaultModules =  null;
	private String customerId = null;
	private boolean validated = false;

	public String features() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Features.features()");
		}
		
		String returnPage = APP_FEATURES_ONE_CLM;
		boolean left = false;
		boolean rightBottom = false;
		boolean right = false;

		try {
			ApplicationInfo appInfo = null;
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			if (StringUtils.isEmpty(fromPage) && validate(administrator)) {
				setValidated(true);
				return SUCCESS;
			}
			if (StringUtils.isEmpty(fromPage)
					&& StringUtils.isNotEmpty(projectCode)) { // previous button clicked
				appInfo = (ApplicationInfo) getHttpSession().getAttribute(projectCode);
			} else if (StringUtils.isNotEmpty(fromPage)) { // For edit project
				appInfo = administrator.getProject(projectCode).getApplicationInfo();
				if (description != null) {
					appInfo.setDescription(description);
				}
				// TODO:Lohes
				/*if (projectVersion != null) {
					appInfo.setVersion(projectVersion);
				}
				if (groupId != null) {
					appInfo.setGroupId(groupId);
				}
				if (artifactId != null) {
					appInfo.setArtifactId(artifactId);
				}*/
				application = appInfo.getTechInfo().getAppTypeId();
				technology = appInfo.getTechInfo().getVersion();
				setTechnology(appInfo, administrator);
			} else { // For creating new project
				appInfo = new ApplicationInfo();
			}
			if (StringUtils.isEmpty(fromPage)) {
				setAppInfos(appInfo, administrator);
			}

			getHttpRequest().setAttribute(REQ_TEMP_SELECTED_PILOT_PROJ, getHttpRequest().getParameter(REQ_TEMP_SELECTED_PILOT_PROJ));

			String selectedFeatures = getHttpRequest().getParameter(REQ_TEMP_SELECTEDMODULES);
			if (StringUtils.isNotEmpty(selectedFeatures)) {
				Map<String, String> mapFeatures = ApplicationsUtil.stringToMap(selectedFeatures);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTEDMODULES, mapFeatures);
			}

			String selectedJsLibs = getHttpRequest().getParameter(REQ_SELECTED_JSLIBS);
			if (StringUtils.isNotEmpty(selectedJsLibs)) {
				Map<String, String> mapJsLibs = ApplicationsUtil.stringToMap(selectedJsLibs);
				getHttpRequest().setAttribute(REQ_TEMP_SELECTED_JSLIBS, mapJsLibs);
			}

			setFeaturesInRequest(administrator, appInfo);
			getHttpRequest().setAttribute(REQ_APP_INFO, appInfo);

			List<ArtifactGroup> coreModules = (List<ArtifactGroup>) getHttpRequest().getAttribute(REQ_CORE_MODULES);
			List<ArtifactGroup> customModules = (List<ArtifactGroup>) getHttpRequest().getAttribute(REQ_CUSTOM_MODULES);
			List<ArtifactGroup> allJsLibs = (List<ArtifactGroup>) getHttpRequest().getAttribute(REQ_ALL_JS_LIBS);

			// Assigning the position of the coreModule
			if (CollectionUtils.isNotEmpty(coreModules)) { // Assigning coreModule to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_EXTERNAL_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, coreModules);
			}

			// Assigning the position of the customModule
			if (!left && CollectionUtils.isNotEmpty(customModules)) { // Assigning customModule to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_CUSTOM_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, customModules);
			} else if (left && CollectionUtils.isNotEmpty(customModules)) { // Assigning customModule to the right bottom position
				right = true;
				getHttpRequest().setAttribute(REQ_FEATURES_SECOND_MDL_CAT, REQ_CUSTOM_FEATURES);
				getHttpRequest().setAttribute(REQ_FEATURES_RIGHT_MODULES, customModules);
			}

			// Assigning the position of the JSLibraries
			if (left && right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the right bottom position
				rightBottom = true;
			} else if (left && !right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the right position
				right = true;
				getHttpRequest().setAttribute(REQ_FEATURES_SECOND_MDL_CAT, REQ_JS_LIBS);
				getHttpRequest().setAttribute(REQ_FEATURES_RIGHT_MODULES, allJsLibs);
			} else if (!left && !right && CollectionUtils.isNotEmpty(allJsLibs)) { // Assigning JSLibraries to the left position
				left = true;
				getHttpRequest().setAttribute(REQ_FEATURES_FIRST_MDL_CAT, REQ_JS_LIBS);
				getHttpRequest().setAttribute(REQ_FEATURES_LEFT_MODULES, allJsLibs);
			}

			if (left && right && rightBottom) {
				returnPage = APP_FEATURES_THREE_CLM;
			} else if (left && right && !rightBottom) {
				returnPage = APP_FEATURES_TWO_CLM;
			}
			getHttpRequest().setAttribute(REQ_CONFIG_SERVER_NAMES, configServerNames);
			getHttpRequest().setAttribute(REQ_CONFIG_DB_NAMES, configDbNames);
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			getHttpRequest().setAttribute(REQ_SERVER_URL, configuration.getServerPath());
			getHttpRequest().setAttribute(REQ_CUSTOMER_ID, customerId);
		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Features.list()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature list");
			
			return LOG_ERROR;
		}
		
		return returnPage;
	}

	private void setAppInfos(ApplicationInfo appInfo, ProjectAdministrator administrator) throws PhrescoException {
		appInfo.setName(name);
		appInfo.setCode(code);
		// TODO:Lohes
		/*if (StringUtils.isNotEmpty(externalCode)) {
			appInfo.setProjectCode(externalCode);
		}
		if (StringUtils.isNotEmpty(groupId)) {
			appInfo.setGroupId(groupId);
		}
		if (StringUtils.isNotEmpty(artifactId)) {
			appInfo.setArtifactId(artifactId);
		}*/
		appInfo.setVersion(projectVersion);
		appInfo.setDescription(description);
		appInfo.setTechInfo(new TechnologyInfo(application, technology));
		// To set customerId
		ArtifactGroup artifactGroup = new ArtifactGroup();
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		artifactGroup.setCustomerIds(customerIds);
		appInfo.setPilotContent(artifactGroup);
		//To get pilot project Id
		String pilotProjectId = getHttpRequest().getParameter(REQ_SELECTED_PILOT_PROJ);
		Element elememt = new Element();
		elememt.setId(pilotProjectId);
		appInfo.setPilotInfo(elememt);
		setTechnology(appInfo, administrator);
		FrameworkUtil.setAppInfoDependents(getHttpRequest(), customerId);
	}

	private void setTechnology(ApplicationInfo appInfo, ProjectAdministrator administrator) throws PhrescoException {
		try {
			if (StringUtils.isEmpty(fromPage)) {
			    appInfo.setTechInfo(new TechnologyInfo(application, technology));
			} else {
			    String appTypeId = appInfo.getTechInfo().getAppTypeId();
			    String techId = appInfo.getTechInfo().getVersion();
			    appInfo.setTechInfo(new TechnologyInfo(appTypeId, techId));
			    appInfo.setSelectedModules(appInfo.getSelectedModules());
			    appInfo.setSelectedJSLibs(appInfo.getSelectedJSLibs());
			}
			
			List<DownloadInfo> servers = administrator.getServersByTech(getTechnology(), customerId);
			List<DownloadInfo> databases = administrator.getDatabasesByTech(getTechnology(), customerId);
			List<WebService> webservices = administrator.getWebServices(getTechnology(), customerId);
			
			String selectedServers = getHttpRequest().getParameter(REQ_SELECTED_SERVERS);
			String selectedDatabases = getHttpRequest().getParameter(REQ_SELECTED_DBS);
			String[] selectedWebservices = getHttpRequest().getParameterValues(REQ_WEBSERVICES);
			boolean isEmailSupported = false;
			if (REQ_APPINFO.equals(fromTab)) {
				if (StringUtils.isNotEmpty(selectedServers)) {
					List<String> tempSelectedServers = null;
					if (StringUtils.isNotEmpty(selectedServers)) {
						tempSelectedServers = new ArrayList<String>(
								Arrays.asList(selectedServers.split("#SEP#")));
					}
					appInfo.setSelectedServers(ApplicationsUtil.getSelectedServers(servers, tempSelectedServers));
				}
				
				if (StringUtils.isNotEmpty(selectedDatabases)) {
					List<String> listTempSelectedDatabases = null;
					if (StringUtils.isNotEmpty(selectedDatabases)) {
						listTempSelectedDatabases = new ArrayList<String>(
								Arrays.asList(selectedDatabases.split("#SEP#")));
					}
					appInfo.setSelectedDatabases(ApplicationsUtil.getSelectedDatabases(
                            databases, listTempSelectedDatabases));
				}
				
				if (!ArrayUtils.isEmpty(selectedWebservices)) {
				    appInfo.setSelectedWebservices(ApplicationsUtil.getSelectedWebservices(
                            webservices, ApplicationsUtil.getArrayListFromStrArr(selectedWebservices)));
				}
				
				if (getHttpRequest().getParameter(REQ_EMAIL_SUPPORTED) != null) {
					isEmailSupported = Boolean.parseBoolean(getHttpRequest().getParameter(REQ_EMAIL_SUPPORTED));
				}
				appInfo.setEmailSupported(isEmailSupported);
			} else {
			    appInfo.setSelectedServers(appInfo.getSelectedServers());
			    appInfo.setSelectedDatabases(appInfo.getSelectedDatabases());
			    appInfo.setSelectedWebservices(appInfo.getSelectedWebservices());
			    appInfo.setEmailSupported(appInfo.isEmailSupported());
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}

    private boolean validate(ProjectAdministrator administrator) throws PhrescoException {
    	try {
        	if (StringUtils.isEmpty(name.trim())) {
        		setNameError(ERROR_NAME);
                return true;
            }
            if (administrator.getProject(code) != null) {
            	setNameError(ERROR_DUPLICATE_NAME);
                return true;
            }
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    	
        return false;
    }
    
	public void setFeaturesInRequest(ProjectAdministrator administrator,
			ApplicationInfo appInfo) throws PhrescoException {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Features.setFeaturesInRequest()");
		}
		
		try {
//			Technology selectedTechnology = appInfo.getTechnology();
		    String techId = appInfo.getTechInfo().getVersion();

			List<ArtifactGroup> coreModule = administrator.getCoreModules(techId, customerId);
			if (CollectionUtils.isNotEmpty(coreModule)) {
				getHttpRequest().setAttribute(REQ_CORE_MODULES, coreModule);
			}

			List<ArtifactGroup> customModule = (List<ArtifactGroup>) administrator
					.getCustomModules(techId, customerId);
			if (CollectionUtils.isNotEmpty(customModule)) {
				getHttpRequest().setAttribute(REQ_CUSTOM_MODULES, customModule);
			}

			List<ArtifactGroup> jsLibs = administrator.getJSLibs(techId, customerId);
			if (CollectionUtils.isNotEmpty(jsLibs)) {
				getHttpRequest().setAttribute(REQ_ALL_JS_LIBS, jsLibs);
			}
			
			// TODO:Lohes
			/*if (CollectionUtils.isNotEmpty(appInfo.getSelectedModules())) {
	            getHttpRequest().setAttribute(REQ_PROJECT_INFO_MODULES,
	                    ApplicationsUtil.getMapFromModuleGroups(appInfo.getSelectedModules()));
	        }

	        if (CollectionUtils.isNotEmpty(appInfo.getSelectedJSLibs())) {
	            getHttpRequest().setAttribute(REQ_PROJECT_INFO_JSLIBS,
	                    ApplicationsUtil.getMapFromModuleGroups(appInfo.getSelectedJSLibs()));
	        }*/

			// This attribute for Pilot Project combo box
			getHttpRequest().setAttribute(REQ_PILOTS_NAMES,
					ApplicationsUtil.getPilotNames(techId, customerId));

			getHttpRequest().setAttribute(REQ_FROM_PAGE, fromPage);
			getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	public String fetchPilotProjectModules() throws PhrescoException {
		try {
			String techId = getHttpRequest().getParameter(REQ_TECHNOLOGY);
			pilotModules = new ArrayList<String>();
			pilotModules.addAll(ApplicationsUtil.getPilotModuleIds(techId, customerId));
			pilotModules.addAll(ApplicationsUtil.getPilotJsLibIds(techId, customerId));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return SUCCESS;
	}
	
	public String fetchDefaultModules() {
		String techId = getHttpRequest().getParameter(REQ_TECHNOLOGY);
		try {
			defaultModules = new ArrayList<String>();
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			List<ArtifactGroup> allModules = (List<ArtifactGroup>) administrator.getAllModules(techId, customerId);
			if (CollectionUtils.isNotEmpty(allModules)) {
				for (ArtifactGroup allModule : allModules) {
					List<ArtifactInfo> modules = allModule.getVersions();
					if (CollectionUtils.isNotEmpty(modules)) {
						for (ArtifactInfo module : modules) {
						    List<RequiredOption> appliesTos = module.getAppliesTo();
						    if (CollectionUtils.isNotEmpty(appliesTos)) {
						        for (RequiredOption appliesTo : appliesTos) {
						            if (appliesTo.getTechId().equals(techId) && appliesTo.isRequired()) {
		                                defaultModules.add(module.getId());
		                            }
                                }
						    }
						}
					}
				}
			}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of fetchDefaultModules()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature fetchDefaultModules");
			
			return LOG_ERROR;
		}
		
		return SUCCESS;
	}

	public String checkDependencyModules() {
	    // TODO:Lohes
		/*try {
		    ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
		    List<ArtifactGroup> allModules = (List<ArtifactGroup>) administrator.getAllModules(techId, customerId);
		    if (CollectionUtils.isNotEmpty(allModules)) {
		        for (ArtifactGroup artifactGroup : allModules) {
                    List<ArtifactInfo> versions = artifactGroup.getVersions();
                    if (CollectionUtils.isNotEmpty(versions)) {
                        for (ArtifactInfo artifactInfo : versions) {
                            if (artifactInfo.getId().equals(version)) {
                                
                            }
                        }
                    }
                }
		    }
			for (ArtifactGroup module : allModules) {
				if (module.getId().equals(moduleId)) {
					Module checkedVersion = module.getVersion(version);
					if (StringUtils.isNotEmpty(preVersion)) {
						Module preVerModule = module.getVersion(preVersion);
						preDependentIds = ApplicationsUtil.getIds(preVerModule
								.getDependentModules());
						preDependentVersions = ApplicationsUtil
								.getDependentVersions();
					}
					if (checkedVersion != null) {
						dependentIds = ApplicationsUtil.getIds(checkedVersion
								.getDependentModules());
						dependentVersions = ApplicationsUtil
								.getDependentVersions();
					}
				}
			}

		} catch (PhrescoException e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Features.checkDependency()"
						+ FrameworkUtil.getStackTraceAsString(e));
			}
			new LogErrorReport(e, "Feature Select Dependency");
			
			return LOG_ERROR;
		}*/
		
		return SUCCESS;
	}

	public Collection<String> getDependentIds() {
		return dependentIds;
	}

	public void setDependentIds(Collection<String> dependentIds) {
		this.dependentIds = dependentIds;
	}

	public Collection<String> getDependentVersions() {
		return dependentVersions;
	}

	public void setDependentVersions(Collection<String> dependentVersions) {
		this.dependentVersions = dependentVersions;
	}

	public Collection<String> getPreDependentIds() {
		return preDependentIds;
	}

	public void setPreDependentIds(Collection<String> dependentIds) {
		this.preDependentIds = dependentIds;
	}

	public Collection<String> getPreDependentVersions() {
		return preDependentVersions;
	}

	public void setPreDependentVersions(Collection<String> dependentVersions) {
		this.preDependentVersions = dependentVersions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPreVersion() {
		return preVersion;
	}

	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}

	public List<String> getPilotModules() {
		return pilotModules;
	}

	public void setPilotModules(List<String> pilotModules) {
		this.pilotModules = pilotModules;
	}

	public List<String> getPilotJSLibs() {
		return pilotJSLibs;
	}

	public void setPilotJSLibs(List<String> pilotJSLibs) {
		this.pilotJSLibs = pilotJSLibs;
	}

	public List<String> getTechVersion() {
		return techVersion;
	}

	public void setTechVersion(List<String> techVersion) {
		this.techVersion = techVersion;
	}

	public String getConfigServerNames() {
		return configServerNames;
	}

	public void setConfigServerNames(String configServerNames) {
		this.configServerNames = configServerNames;
	}

	public String getConfigDbNames() {
		return configDbNames;
	}

	public void setConfigDbNames(String configDbNames) {
		this.configDbNames = configDbNames;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	
	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	
	public List<String> getDefaultModules() {
		return defaultModules;
	}

	public void setDefaultModules(List<String> defaultModules) {
		this.defaultModules = defaultModules;
	}
	
	public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}