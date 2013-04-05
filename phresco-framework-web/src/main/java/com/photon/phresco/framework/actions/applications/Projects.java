/**
 * Framework Web Archive
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
package com.photon.phresco.framework.actions.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ApplicationType;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.commons.model.TechnologyGroup;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.configuration.ConfigurationInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Profile;
import com.phresco.pom.util.PomProcessor;

/**
 * Struts Action class for Handling Project related operations 
 * @author jeb
 */
public class Projects extends FrameworkBaseAction {

    private static final long serialVersionUID = 7143239782158726004L;

    private static final Logger S_LOGGER = Logger.getLogger(Projects.class);
    private static Boolean s_debugEnabled = S_LOGGER.isDebugEnabled();
    
    private static Map<String, ApplicationType> s_layerMap = new HashMap<String, ApplicationType>();
    private static Map<String, TechnologyGroup> s_technologyGroupMap = new HashMap<String, TechnologyGroup>();

    private String projectName = "";
    private String projectCode = "";
    private String projectDesc = "";
    private String projectVersion = "";
    private List<String> layer = new ArrayList<String>(8);
    private String layerId = "";
    private String techGroupId = "";

    private List<TechnologyInfo> widgets = new ArrayList<TechnologyInfo>(8);
    private List<String> versions = new ArrayList<String>(8);
    private List<ApplicationInfo> selectedAppInfos = new ArrayList<ApplicationInfo>();
    private String appDirName ="";

    private boolean errorFound = false;
    private String projectNameError = "";
    private String projectCodeError = "";
    private String appCodeError = "";
    private String projectVersionError = "";
    private String layerError = "";
    private String mobTechError = "";
    private String appTechError = "";
    private String webTechError = "";
    private String statusFlag = "" ;
    private String id = "";
    private String fromTab = "";
    private int appLayerRowCount;
    private String actionType = "";
    
    public void clearMap() {
    	s_layerMap.clear();
    	s_technologyGroupMap.clear();
	}

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
            if (CollectionUtils.isNotEmpty(projects)) {
            	Collections.sort(projects, sortByNameInAlphaOrder());
            }
            setReqAttribute(REQ_PROJECTS, projects);
            setReqAttribute(REQ_SELECTED_MENU, APPLICATIONS);
            removeSessionAttribute(projectCode);
            setRecentProjectIdInReq();
            if (IMPORT.equals(getStatusFlag())) {
            	addActionMessage(getText(IMPORT_SUCCESS_PROJECT));
            } else if (UPDATE.equals(getStatusFlag())) {
            	addActionMessage(getText(SUCCESS_PROJECT_UPDATE));
            } else if (ADD.equals(getStatusFlag())) {
            	addActionMessage(getText(SUCCESS_PROJECT_ADD));
            } else if (COMMIT.equals(getStatusFlag())) {
            	addActionMessage(getText(SUCCESS_PROJECT_COMMIT));
            }
        } catch (PhrescoException e) {
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.list()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_LIST));
        }

        return APP_LIST;
    }
    
    private void setRecentProjectIdInReq() throws PhrescoException {
        FileReader reader = null;
        try {
            File tempPath = new File(Utility.getPhrescoTemp() + File.separator + USER_PROJECT_JSON);
            User user = (User) getSessionAttribute(SESSION_USER_INFO);
            JSONObject userProjJson = null;
            JSONParser parser = new JSONParser();
            String recentProjectId = "";
            String recentAppId = "";
            if (tempPath.exists()) {
                reader = new FileReader(tempPath);
                userProjJson = (JSONObject)parser.parse(reader);
                String csv = (String) userProjJson.get(user.getId());
                if (StringUtils.isNotEmpty(csv)) {
                    String[] split = csv.split(Constants.STR_COMMA);
                    recentProjectId = split[0];
                    if (split.length > 1) {
                        recentAppId = split[1];
                    }
                }
            }
            setReqAttribute(REQ_RECENT_PROJECT_ID, recentProjectId);
            setReqAttribute(REQ_RECENT_APP_ID, recentAppId);
        } catch (IOException e) {
            throw new PhrescoException(e);
        } catch (ParseException e) {
            throw new PhrescoException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new PhrescoException(e);
                }
            }
        }
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
        	User user = (User) getSessionAttribute(SESSION_USER_INFO);
        	List<Customer> customers = user.getCustomers();
        	for (Customer customer : customers) {
				if (customer.getId().equals(getCustomerId())) {
					setReqAttribute(REQ_PROJECT_LAYERS, customer.getApplicableAppTypes());
					break;
				}
			}
        	setReqAttribute(REQ_FROM_PAGE, FROM_PAGE_ADD);
        } catch (Exception e) {
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.addProject()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_PROJECT_ADD));
        }

        return APP_APPLICATION_DETAILS;
    }

    /**
     * To get the selected mobile technology's version
     * @return
     */
    public String fetchTechVersions() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.fetchMobileTechVersions()");
        }

        try {
            List<TechnologyGroup> techGroups = filterLayer(getLayerId()).getTechGroups();            
            TechnologyGroup technologyGroup = filterTechnologyGroup(techGroups, getTechGroupId());            
            String techId = getReqParameter(technologyGroup.getId() + REQ_PARAM_NAME_TECHNOLOGY);  
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
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.fetchMobileTechVersions()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_MOB_TECH_VERSIONS));
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
            String techGroupId = getReqParameter(getLayerId() + REQ_PARAM_NAME_TECH_GROUP);
            List<TechnologyGroup> techGroups = filterLayer(getLayerId()).getTechGroups();            
            TechnologyGroup technologyGroup = filterTechnologyGroup(techGroups, techGroupId);
            setWidgets(technologyGroup.getTechInfos());
        } catch (PhrescoException e) {
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.fetchWebLayerWidgets()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_WEB_LAYER_WIDGETS));
        }

        return SUCCESS;
    }

    /**
     * To get the layer based on the given layer id
     * @param layers
     * @param layerName
     * @return
     * @throws PhrescoException 
     */
    private ApplicationType filterLayer(String layerId) throws PhrescoException {
    	if (s_layerMap.get(layerId) == null) {
    		User user = (User) getSessionAttribute(SESSION_USER_INFO);
    		List<Customer> customers = user.getCustomers();
    		for (Customer customer : customers) {
    			if (customer.getId().equals(getCustomerId())) {
    				List<ApplicationType> layers = customer.getApplicableAppTypes();
    				if (CollectionUtils.isNotEmpty(layers)) {
    					for (ApplicationType layer : layers) {    						
    						s_layerMap.put(layer.getId(), layer);
    					}
    				}
    				break;
    			}
    		}    		
    	}

    	return s_layerMap.get(layerId);
    }

    /**
     * To get the technology group based on the given technology group Id
     * @param technologyGroups
     * @param id
     * @return
     */
    private TechnologyGroup filterTechnologyGroup(List<TechnologyGroup> technologyGroups, String id) {    
        if (CollectionUtils.isNotEmpty(technologyGroups)) {
            if (s_technologyGroupMap.get(id) == null) {
                for (TechnologyGroup technologyGroup : technologyGroups) {                
                    s_technologyGroupMap.put(technologyGroup.getId(), technologyGroup);
                }
            }
        }

        return s_technologyGroupMap.get(id);
    }
    
    public String editProject() {
    	ProjectInfo projectInfo = null;
		try {
			ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
			projectInfo = projectManager.getProject(getProjectId(), getCustomerId());			
			projectInfo.setId(getProjectId());
			User user = (User) getSessionAttribute(SESSION_USER_INFO);
        	List<Customer> customers = user.getCustomers();
        	for (Customer customer : customers) {
				if (customer.getId().equals(getCustomerId())) {
					setReqAttribute(REQ_PROJECT_LAYERS, customer.getApplicableAppTypes());
					break;
				}
			}
			setReqAttribute(REQ_PROJECT, projectInfo);
			setReqAttribute(REQ_FROM_PAGE, FROM_PAGE_EDIT);
		} catch (PhrescoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "projectDetails";
    	
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
            ProjectInfo projectInfo = PhrescoFrameworkFactory.getProjectManager().create(createProjectInfo(), getServiceManager());
            addActionMessage(getText(ACT_SUCC_PROJECT_CREATE, Collections.singletonList(getProjectName())));
            setProjectId(projectInfo.getId());
            updateLatestProject();
        } catch (PhrescoException e) {
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.createProject()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_CREATE));
        }

        return list();
    }

    /**
     * To update the project with the selected applications
     * @return
     */
    public String updateProject() {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.updateProject()");
        }

        try {
        	ProjectInfo projectInfo = createProjectInfo();
        	projectInfo.setId(getId());
            PhrescoFrameworkFactory.getProjectManager().update(projectInfo, getServiceManager(), null);
        } catch (PhrescoException e) {
            if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.updateProject()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_UPDATE));
        }

        return list();
    }
    
    /**
     * 
     * @param techId
     * To get Technology Name based on corresponding Technology Id
     * @throws PhrescoException
     */
    public String getTechNamefromTechId(String techId) throws PhrescoException {
    	if (s_debugEnabled) {
    		S_LOGGER.debug("Entering Method  Applications.getTechNamefromTechId()");
    	}

    	Technology technology = getServiceManager().getTechnology(techId);
    	return technology.getName(); 
    }
    
    /**
     * To get the projectInfo with the selected application infos
     * @return
     * @throws PhrescoException
     */
    private ProjectInfo createProjectInfo() throws PhrescoException {
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
                } else if (LAYER_APP_ID.equals(layerId)) { 
                    getAppLayerAppInfos(appInfos, layerId);
                } else {
                	getWebLayerAppInfos(appInfos, layerId);
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
        String[] techGroupIds = getReqParameterValues(layerId + REQ_PARAM_NAME_TECH_GROUP);
        if (!ArrayUtils.isEmpty(techGroupIds)) {
            for (String techGroupId : techGroupIds) {
                String techId = getReqParameter(techGroupId + REQ_PARAM_NAME_TECHNOLOGY);
                String version = getReqParameter(techGroupId + REQ_PARAM_NAME_VERSION);
                boolean phoneEnabled = Boolean.parseBoolean(getReqParameter(techGroupId + REQ_PARAM_NAME_PHONE));
                boolean tabletEnabled = Boolean.parseBoolean(getReqParameter(techGroupId + REQ_PARAM_NAME_TABLET));
                Technology technology = getServiceManager().getTechnology(techId);
                String techName = technology.getName().replaceAll("\\s", "").toLowerCase();
                String dirName = getProjectCode() + HYPHEN + techName;
                String projectName = getProjectName() + HYPHEN + techName;
                appInfos.add(getAppInfo(projectName, dirName, techId, version, phoneEnabled, tabletEnabled, technology.getAppTypeId()));
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
    private List<ApplicationInfo> getAppLayerAppInfos(List<ApplicationInfo> appInfos, String layerId) throws PhrescoException {
    	String applnLayerInfo = getReqParameter(REQ_APP_LAYER_INFOS);
    	if (StringUtils.isNotEmpty(applnLayerInfo)) {
    		String[] split = applnLayerInfo.split(Constants.STR_COMMA);//Split multiple app layer app infos by comma
         	for (String string : split) {//iterate 
     			String[] techInfos = string.split(APP_SEPERATOR);
     			String[] techIdVersion = techInfos[1].split(VERSION_SEPERATOR);
     			String applnCode = techInfos[0];//app code
     			String techId = techIdVersion[0];//tech id
     			String[] versionStr = techIdVersion[1].split(ROW_SEPERATOR);
     			String techVersion = versionStr[0];//tech version
     			Technology technology = getServiceManager().getTechnology(techId);
     	        String techName = technology.getName().replaceAll("\\s", "").toLowerCase();
     	        String dirName = applnCode + HYPHEN + techName;
     	        String projectName = getProjectName() + HYPHEN + techName;
     	        appInfos.add(getAppInfo(projectName, dirName, techId, techVersion, false, false, technology.getAppTypeId()));
     		}
    	}

     	return appInfos;
    }
    
    private List<ApplicationInfo> getWebLayerAppInfos(List<ApplicationInfo> appInfos, String layerId) throws PhrescoException {
    	String techId = getReqParameter(layerId + REQ_PARAM_NAME_TECHNOLOGY);
        String version = getReqParameter(layerId + REQ_PARAM_NAME_VERSION);
        Technology technology = getServiceManager().getTechnology(techId);
        String techName = technology.getName().replaceAll("\\s", "").toLowerCase();
        String dirName = getProjectCode() + HYPHEN + techName;
        String projectName = getProjectName() + HYPHEN + techName;
        appInfos.add(getAppInfo(projectName, dirName, techId, version, false, false, technology.getAppTypeId()));
		
        return appInfos;
    }

    /**
     * To get the single application info for the given technology
     * @param techId
     * @param version
     * @param phoneEnabled
     * @param tabletEnabled
     * @return
     * @throws PhrescoException
     */
    private ApplicationInfo getAppInfo(String projectName, String appDir, String techId, String version,
            boolean phoneEnabled, boolean tabletEnabled, String appTypeId) throws PhrescoException {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        TechnologyInfo techInfo = new TechnologyInfo();
        techInfo.setId(techId);
        techInfo.setVersion(version);
        techInfo.setAppTypeId(appTypeId);
        applicationInfo.setTechInfo(techInfo);
        applicationInfo.setVersion(getProjectVersion());
        applicationInfo.setName(projectName);
        applicationInfo.setCode(appDir);
        applicationInfo.setAppDirName(appDir);
        applicationInfo.setPhoneEnabled(phoneEnabled);
        applicationInfo.setTabletEnabled(tabletEnabled);

        return applicationInfo;
    }
    
    /**
     * To delete the selected projects or applications
     * @return
     * @throws PhrescoException 
     */
    public String delete() {
    	if (s_debugEnabled) {
    		S_LOGGER.debug("Entering Method  Applications.delete()");
    	}
    	BufferedReader reader = null;
    	try {
	    	ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
	    	if (CollectionUtils.isNotEmpty(getSelectedAppInfos())) {
	    	    for (ApplicationInfo appInfo : getSelectedAppInfos()) {
	    	        StringBuilder sb = new StringBuilder(Utility.getProjectHome())
	    	        .append(appInfo.getAppDirName())
	    	        .append(File.separator)
	    	        .append(FOLDER_DOT_PHRESCO)
	    	        .append(File.separator)
	    	        .append(RUNAGNSRC_INFO_FILE);
	    	        File file = new File(sb.toString());
	    	        if (file.exists()) {
	    	            Gson gson = new Gson();
	    	            reader = new BufferedReader(new FileReader(file));
	    	            ConfigurationInfo configInfo = gson.fromJson(reader, ConfigurationInfo.class);
	    	            int port = Integer.parseInt(configInfo.getServerPort());
	    	            boolean connectionAlive = Utility.isConnectionAlive(HTTP_PROTOCOL, LOCALHOST, port);
	    	            if (connectionAlive) {
	    	                addActionError(getText(ACT_ERR_RUNAGNSRC_IN_PROGRESS, Collections.singletonList(appInfo.getName())));
	    	                return list();
	    	            }
	    	        }
	    	    }
	    	}
	    	boolean connectionAlive = Utility.isConnectionAlive(HTTP_PROTOCOL, LOCALHOST, 9000);
	    	if(connectionAlive) {
	    		deleteSonarProject();
	    	}
	    	projectManager.delete(getSelectedAppInfos());
	    	addActionMessage(getText(ACT_SUCC_PROJECT_DELETE, Collections.singletonList(getProjectName())));
    	} catch (PhrescoException e) {
    		if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.delete()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(e, getText(EXCEPTION_PROJECT_DELETE));
		} catch (FileNotFoundException e) {
		    if (s_debugEnabled) {
                S_LOGGER.error("Entered into catch block of Projects.delete()" + FrameworkUtil.getStackTraceAsString(e));
            }
            return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_PROJECT_DELETE));
        } catch (PhrescoPomException e) {
        	S_LOGGER.error("Entered into catch block of Projects.delete()" + FrameworkUtil.getStackTraceAsString(e));
		} catch (IOException e) {
			S_LOGGER.error("Entered into catch block of Projects.delete()" + FrameworkUtil.getStackTraceAsString(e));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					S_LOGGER.error("Entered into catch block of Projects.delete()" + FrameworkUtil.getStackTraceAsString(e));
				}
			}
		}
    	
    	return list();
    }
    
    private void deleteSonarProject() throws PhrescoException,
    PhrescoPomException, IOException {
    	List<ApplicationInfo> selectedAppInfos = getSelectedAppInfos();
    	for (ApplicationInfo appInfo : selectedAppInfos) {
    		List<String> sonarProfiles = getSonarProfile(appInfo);
    		for (String sonarProfile : sonarProfiles) {
    			FrameworkUtil util = new FrameworkUtil();
    			PomProcessor pomProcessor = util.getPomProcessor(appInfo.getAppDirName());
    			String projectName = pomProcessor.getGroupId() + COLON + pomProcessor.getArtifactId() + COLON + sonarProfile;
    			if(sonarProfile.equals(SONAR_SOURCE)) {
    				projectName = pomProcessor.getGroupId() + COLON + pomProcessor.getArtifactId();
    			} if(sonarProfile.equals(FUNCTIONAL)) {
    				String funTestDir = pomProcessor.getProperty(Constants.POM_PROP_KEY_FUNCTEST_DIR);
    				String pompath = Utility.getProjectHome() + appInfo.getAppDirName() + File.separator + funTestDir + File.separator + POM_XML;
    				PomProcessor funPom = new PomProcessor(new File(pompath));
    				if(funPom.isPomValid()) { 
    					projectName = funPom.getGroupId() + COLON + funPom.getArtifactId() + COLON + sonarProfile;
    				}
    			}
    			Runtime.getRuntime().exec("curl -u admin:admin -X DELETE " + util.getSonarHomeURL() + "/api/projects/" + projectName);
    		}
    	}
    }

    private List<String> getSonarProfile(ApplicationInfo appInfo) throws PhrescoException {
    	List<String> sonarTechReports = new ArrayList<String>(6);
    	StringBuilder builder = new StringBuilder();
    	builder.append(Utility.getProjectHome())
    	.append(appInfo.getAppDirName())
    	.append(File.separator)
    	.append(POM_XML);
    	try {
    		File pomPath = new File(builder.toString());
    		PomProcessor pomProcessor = new PomProcessor(pomPath);
    		Model model = pomProcessor.getModel();
    		S_LOGGER.debug("model... " + model);
    		Profiles modelProfiles = model.getProfiles();
    		if (modelProfiles != null && modelProfiles.getProfile() != null) {
    			S_LOGGER.debug("model Profiles... " + modelProfiles);
    			List<Profile> profiles = modelProfiles.getProfile();
    			S_LOGGER.debug("profiles... " + profiles);
    			for (Profile profile : profiles) {
    				S_LOGGER.debug("profile...  " + profile);
    				if (profile.getProperties() != null) {
    					List<Element> any = profile.getProperties().getAny();
    					int size = any.size();
    					for (int i = 0; i < size; ++i) {
    						boolean tagExist = 	any.get(i).getTagName().equals(SONAR_LANGUAGE);
    						if (tagExist){
    							S_LOGGER.debug("profile.getId()... " + profile.getId());
    							sonarTechReports.add(profile.getId());
    						}
    					}
    				}
    			}
    		} if (CollectionUtils.isEmpty(sonarTechReports)) {
    			sonarTechReports.add(SONAR_SOURCE);
    		} 
    		sonarTechReports.add(FUNCTIONAL);
    	} catch(PhrescoPomException e) {
    		throw new PhrescoException(e);
    	}
    	return sonarTechReports;
    }
    
    public String killProcess() throws PhrescoException {
    	 ApplicationInfo appInfo = getApplicationInfo();
         
         // TO kill the Process
         String baseDir = Utility.getProjectHome()+ appInfo.getAppDirName();
         Utility.killProcess(baseDir, getActionType());
    	
		return SUCCESS;
    }
    /**
     * To validate the form fields
     * @return
     * @throws PhrescoException 
     */
    public String validateForm() throws PhrescoException {
        if (s_debugEnabled) {
            S_LOGGER.debug("Entering Method  Applications.validateForm()");
        }
        
        ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
	    List<ProjectInfo> projects = projectManager.discover();
	        
        boolean hasError = false;
        boolean hasMobLayer=false;
    	
        if (FROM_PAGE_EDIT.equals(getFromTab())||FROM_PAGE_ADD.equals(getFromTab())) {
  	       	//check project name is already exists or not
        	if (FROM_PAGE_ADD.equals(getFromTab())) {
	        	if(StringUtils.isNotEmpty(getProjectName())) {
	  	        	for(ProjectInfo project : projects) {
	  	        		
	  	        		if(project.getName().equals(getProjectName())) {
	  	    	       	 	setProjectNameError(getText(ERROR_NAME_EXISTS));
	  	    	            hasError = true;
	  	    	            break;
	  	        		}
	  	        	}
	  	        }
	  	        //check project code is already exists or not
	  	        if(StringUtils.isNotEmpty(getProjectCode())) {
	  	        	for(ProjectInfo project : projects) {
	  	        		if(project.getProjectCode().toLowerCase().equals(getProjectCode().toLowerCase())) {
	  	    	        	setProjectCodeError(getText(ERROR_CODE_EXISTS));
	  	    	            hasError = true;
	  	    	            break;
	  	        		}
	  	        	}
	  	        }	
        	}
  	      //validate if none of the layer is selected
  	        if(FROM_PAGE_ADD.equals(getFromTab())){
	        	if (CollectionUtils.isEmpty(getLayer())) {
	  	            setAppTechError(getText(ERROR_TECHNOLOGY));
	  	            setWebTechError(getText(ERROR_TECHNOLOGY));
	  	            setMobTechError(getText(ERROR_TECHNOLOGY));
	  	            hasError = true;
	  	        }
        	}
  	        //empty validation for technology in the selected layer
  	        if (CollectionUtils.isNotEmpty(getLayer())) {
  	            for (String layerTypeId : getLayer()) {
  	                String techId = getReqParameter(layerTypeId + REQ_PARAM_NAME_TECHNOLOGY);
  	                if (LAYER_APP_ID.equals(layerTypeId) && StringUtils.isEmpty(techId)) {
						if (getReqParameter("appId").equals("false")) {
							setAppTechError(getText(ERROR_APP_CODE_MISSING));
						} else {
							setAppTechError(getText(ERROR_SELECT_TECHNOLOGY));
						}
	                }
  	                
  	                if (LAYER_WEB_ID.equals(layerTypeId) && StringUtils.isEmpty(techId)) {
                        setWebTechError(getText(ERROR_TECHNOLOGY));
                        hasError = true;
  	                }
  	                
					if (FROM_PAGE_EDIT.equals(getFromTab())) {
						ProjectInfo project = projectManager.getProject(getId(), getCustomerId());
						List<ApplicationInfo> appInfos = project.getAppInfos();
						for (ApplicationInfo appInfo : appInfos) {
							if (appInfo.getTechInfo().getAppTypeId().equals(LAYER_MOB_ID)) {
								hasMobLayer=true;
							}
						}
					}
					
  	                if (LAYER_MOB_ID.equals(layerTypeId)) {
  	                    String[] techGroupIds = getReqParameterValues(layerTypeId + REQ_PARAM_NAME_TECH_GROUP);
  	                    if (ArrayUtils.isEmpty(techGroupIds)) {
  	                    	if(!hasMobLayer) {
	  	                        setMobTechError(getText(ERROR_TECHNOLOGY));
	  	                        hasError = true;
  	                    	}
  	                    } else {
  	                        for (String techGroupId : techGroupIds) {//empty validation for technology in the selected technology group
  	                            techId = getReqParameter(techGroupId + REQ_PARAM_NAME_TECHNOLOGY);
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
        }
        //validate for app code duplication
        String applnLayerInfos = getReqParameter(REQ_APP_LAYER_INFOS);
      	if (StringUtils.isNotEmpty(applnLayerInfos)) {
      		boolean skipLoop = false;
      		String[] splittedAppInfos = applnLayerInfos.split(Constants.STR_COMMA);//Split multiple app layer app infos by comma
           	for (String applnLayerInfo : splittedAppInfos) {//iterate 
       			String[] techInfos = applnLayerInfo.split(APP_SEPERATOR);
       			if (!ArrayUtils.isEmpty(techInfos) && StringUtils.isNotEmpty(techInfos[0]) && StringUtils.isNotEmpty(techInfos[1])) {
	       			String applnCode = techInfos[0];//app code
       				String[] techIdVersion = techInfos[1].split(VERSION_SEPERATOR);
	       			if (!ArrayUtils.isEmpty(techIdVersion) && StringUtils.isNotEmpty(techIdVersion[0])) {
	       				String techId = techIdVersion[0];//tech id
	       				String[] rowCount = techIdVersion[1].split(ROW_SEPERATOR);
		       			Technology technology = getServiceManager().getTechnology(techId);
		       	        String techName = technology.getName().replaceAll("\\s", "").toLowerCase();
		       	        String currentAppCode = applnCode + HYPHEN + techName;
			       	     for(ProjectInfo project : projects) {//iterate all project  infos
		       	    	 	List<ApplicationInfo> appInfos = project.getAppInfos();
		       	    	 	for (ApplicationInfo appInfo : appInfos) {//iterate all app infos
								if (appInfo.getCode().toLowerCase().equals(currentAppCode.toLowerCase())) {// match with existing appcode with current appcode
									hasError = true;
									skipLoop = true;
									setFromTab(getFromTab());
									setAppCodeError(getText(ERROR_APP_CODE_EXISTS));
									setAppLayerRowCount(Integer.parseInt(rowCount[1]));
									break;
								}
							}
		       	    	 	if (skipLoop) {
		       	    	 		break;
		       	    	 	}
		  	        	}
	       			}
       			}
       		}
      	}
      	
        //empty validation for name
        if (StringUtils.isEmpty(getProjectName().trim())) {
            setProjectNameError(getText(ERROR_NAME));
            hasError = true;
        }
        //empty validation for projectCode
        if (StringUtils.isEmpty(getProjectCode().trim())) {
            setProjectCodeError(getText(ERROR_CODE));
            hasError = true;
        }
        //empty validation for projectVersion
        if (StringUtils.isEmpty(getProjectVersion().trim())) {
            setProjectVersionError(getText(ERROR_VERSION));
            hasError = true;
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

    public String getProjectVersionError() {
        return projectVersionError;
    }

    public void setProjectVersionError(String projectVersionError) {
        this.projectVersionError = projectVersionError;
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
    
    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getTechGroupId() {
        return techGroupId;
    }

    public void setTechGroupId(String techGroupId) {
        this.techGroupId = techGroupId;
    }
    
    public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getAppDirName() {
		return appDirName;
	}

	public void setAppDirName(String appDirName) {
		this.appDirName = appDirName;
	}

	public List<ApplicationInfo> getSelectedAppInfos() {
		return selectedAppInfos;
	}

	public void setSelectedAppInfos(List<ApplicationInfo> selectedAppInfos) {
		this.selectedAppInfos = selectedAppInfos;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public void setAppCodeError(String appCodeError) {
		this.appCodeError = appCodeError;
	}

	public String getAppCodeError() {
		return appCodeError;
	}

	public void setAppLayerRowCount(int appLayerRowCount) {
		this.appLayerRowCount = appLayerRowCount;
	}

	public int getAppLayerRowCount() {
		return appLayerRowCount;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}