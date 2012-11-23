/*
 * ###
 * Framework Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.Element;
import com.photon.phresco.commons.model.PropertyTemplate;
import com.photon.phresco.commons.model.SettingsTemplate;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.framework.model.CertificateInfo;
import com.photon.phresco.framework.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class Configurations extends FrameworkBaseAction {
    private static final long serialVersionUID = -4883865658298200459L;
    
    private Map<String, String> dbDriverMap = new HashMap<String, String>(8);
    
    private static final Logger S_LOGGER = Logger.getLogger(Configurations.class);
    private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    
    private List<Environment> environments = new ArrayList<Environment>(8);
    private Environment environment = null;
  
	//private Configuration selectedConfig = null;
    private SettingsTemplate settingTemplate = null;
    private String selectedEnvirment = null;
    private String configId = null;
    private String selectedConfigId = null;
    private String selectedType = null;
    private String selectedEnv = null;
	private String selectedConfigname = null;
	private String configName = null;
    private String description = null;
    private String oldName = null;
    private String[] appliesto = null;
    private String projectCode = null;
    private boolean errorFound = false;
	private String configNameError = null;
	private String configEnvError = null;
	private String configTypeError = null;
	private String nameError = null;
    private String typeError = null;
    private String portError = null;
    private String dynamicError = "";
    private boolean isValidated = false;
    private String envName = null;
    private String configType = null;
    private String oldConfigType = null;
	private String envError = null;
	private String emailError = null;
	private String remoteDeploymentChk = null;
	private String currentEnvName = null;
	private String currentConfigType = null;
	private String currentConfigName = null;
   
	// Environemnt delete
    private boolean isEnvDeleteSuceess = true;
    private String envDeleteMsg = null;
    private List<String> projectInfoVersions = null;
    
    // For IIS server
    private String appName = "";
	private String siteName = "";
	private String siteCoreInstPath = "";
    private String appNameError = null;
    private String siteNameError = null;
    private String siteCoreInstPathError = null;
    
    //set as default envs
    private String setAsDefaultEnv = "";
    private String fromPage = null;
    private String configPath = null;
    
    private boolean flag = false;
    
	public String configList() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Configurations.configList()");
		}
        
    	try {
    	    setReqAttribute(REQ_FROM_PAGE, REQ_CONFIG);
    	    setReqAttribute(REQ_CONFIG_PATH, getAppConfigPath().replace(File.separator, FORWARD_SLASH));
            String cloneConfigStatus = getHttpRequest().getParameter(CLONE_CONFIG_STATUS); 
            if (cloneConfigStatus != null) {
            	addActionMessage(getText(ENV_CLONE_SUCCESS));
            }
        } catch (PhrescoException e) {
        	return showErrorPopup(e,  getText(EXCEPTION_CONFIGURATION_LIST_ENV));
        }
        
        return APP_LIST;
    }
	
	
	public String settingsList() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Configurations.settingsList()");
		}
		try {
			setConfigPath(getGlobalSettingsPath().replace(File.separator, FORWARD_SLASH));
			List<Environment> environments = getAllEnvironments();
    	    setReqAttribute(REQ_FROM_PAGE, REQ_SETTINGS);
    	    setReqAttribute(REQ_SETTINGS_PATH, getGlobalSettingsPath().replace(File.separator, FORWARD_SLASH));
    	    setReqAttribute(REQ_ENVIRONMENTS, environments);
            String cloneConfigStatus = getHttpRequest().getParameter(CLONE_CONFIG_STATUS); 
            if (cloneConfigStatus != null) {
            	addActionMessage(getText(ENV_CLONE_SUCCESS));
            }
        } catch (PhrescoException e) {
        	return showErrorPopup(e,  getText(EXCEPTION_SETTINGS_LIST_ENV));
        } catch (ConfigurationException e) {
        	return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_SETTINGS_LIST_CONFIG));
		} 
        
        return APP_LIST;
	}

	public String envList() {
		if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.envList()");
		}  
        
    	try {
    	    List<Environment> environments = getAllEnvironments();
    	    setReqAttribute(REQ_FROM_PAGE, fromPage);
            setReqAttribute(REQ_ENVIRONMENTS, environments);
            String cloneConfigStatus = getHttpRequest().getParameter(CLONE_CONFIG_STATUS); 
            if (cloneConfigStatus != null) {
            	addActionMessage(getText(ENV_CLONE_SUCCESS));
            }
        } catch (PhrescoException e) {
        	return showErrorPopup(e,  getText(EXCEPTION_CONFIGURATION_READ_ENV_LIST));
        } catch (ConfigurationException e) {
        	  return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_ENV_LIST));
		}
        
        return APP_ENV_LIST;
    }
	
    /**
     * @return 
     * @throws PhrescoException
     * @throws ConfigurationException
     */
	private List<Environment> getAllEnvironments() throws PhrescoException, ConfigurationException {
		ConfigManager configManager = getConfigManager(getConfigPath());
		return configManager.getEnvironments();
	}

	public String openEnvironmentPopup() {
        try {
            setReqAttribute(REQ_ENVIRONMENTS, getAllEnvironments());
        } catch (PhrescoException e) {
            return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_OPEN_ENV_POPUP));
        } catch (ConfigurationException e) {
            return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_UPDATE_FAILS));
        }
        return APP_ENVIRONMENT;
    }
    

    /**
     * @return
     * @throws PhrescoException
     */
    protected ConfigManager getConfigManager(String configPath) throws PhrescoException {
        File appDir = new File(configPath);
        return PhrescoFrameworkFactory.getConfigManager(appDir);
    }
    
    public String add() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.add()");
    	}
        try {
            List<Environment> environments = getAllEnvironments();
            List<SettingsTemplate> configTemplates = getServiceManager().getconfigTemplates(getCustomerId());
            setReqAttribute(REQ_SETTINGS_TEMPLATES, configTemplates);
            setReqAttribute(REQ_ENVIRONMENTS, environments);
            setReqAttribute(REQ_FROM_PAGE, getFromPage());
            setReqAttribute(REQ_CONFIG_PATH, getConfigPath());
        } catch (PhrescoException e) {
            return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_ADD));
        } catch (ConfigurationException e) {
        	return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_UPDATE_FAILS));
        }

        return APP_CONFIG_ADD;
    }
    
    
    
    public String saveConfiguration() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.saveConfiguration()");
		}
    	
    	try {
			save(getAppConfigPath());
		} catch (PhrescoException e) {
			 return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_SAVE_CONFIG));
		} catch (ConfigurationException e) {
			return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_UPDATE_FAILS));
		}
    	
    	return configList();
    }
    
    public String saveSettings() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.saveSettings()");
		}
    	try {
    		save(getGlobalSettingsPath());
		} catch (PhrescoException e) {
			return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_SAVE_SETTINGS));
		} catch (ConfigurationException e) {
			return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_UPDATE_FAILS));
		}
    	
    	return settingsList();
    }
    
    
    
    private void save(String configPath) throws ConfigurationException, PhrescoException {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.save()");
		}  
        	Configuration config = getConfigInstance();
            Environment environment = getEnvironment();
            List<Configuration> configurations = environment.getConfigurations();
            configurations.add(config);
            ConfigManager configManager = getConfigManager(configPath);
            configManager.createConfiguration(environment.getName(), config);
    }


	private Configuration getConfigInstance() throws PhrescoException {
		boolean isIISServer = false;
		SettingsTemplate configTemplate = getServiceManager().getConfigTemplate(getConfigId(), getCustomerId());
		Properties properties = new Properties();
		List<PropertyTemplate> property = configTemplate.getProperties();
		for (PropertyTemplate propertyTemplate : property) {
		    String key = propertyTemplate.getKey();
		    String value = getActionContextParam(key);
		    if (StringUtils.isNotEmpty(value)) {
		        properties.put(key, value);
		    }
		    if ("server".equals(key) && "IIS".equals(value)) {
		    	isIISServer = true;
		    }
		}
		
		ApplicationInfo applicationInfo = getApplicationInfo();
		if (applicationInfo != null && applicationInfo.getTechInfo().getId().equals("tech-sitecore")) {
			properties.put(SETTINGS_TEMP_SITECORE_INST_PATH, siteCoreInstPath);
		}
		
		if (isIISServer) {
			properties.put(SETTINGS_TEMP_KEY_APP_NAME, appName);
			properties.put(SETTINGS_TEMP_KEY_SITE_NAME, siteName);
		}
		
		Configuration config = new Configuration(getConfigName(), getConfigType());
		config.setDesc(getDescription());
		config.setProperties(properties);
		return config;
	}
    
    /**
     * To validate the form fields
     * @return
     * @throws PhrescoException 
     */
    public String validateConfiguration() throws PhrescoException {
    	
    	boolean hasError = false;
    	boolean isIISServer = false;
    	
    	if (StringUtils.isEmpty(getConfigName())) {
    		setConfigNameError(getText(ERROR_NAME));
            hasError = true;
        }
    	
    	if (StringUtils.isEmpty(getConfigType())) {
    		setConfigTypeError(getText(ERROR_CONFIG_TYPE));
            hasError = true;
        }
    	
    	SettingsTemplate configTemplate = getServiceManager().getConfigTemplate(getConfigId(), getCustomerId());
        List<PropertyTemplate> properties = configTemplate.getProperties();
        for (PropertyTemplate propertyTemplate : properties) {
            String key = propertyTemplate.getKey();
            String value = getActionContextParam(key);
            if ("server".equals(key) && "IIS".equals(value)) {
            	isIISServer = true;
            }
            
            if(isIISServer && "context".equals(key)){
            	propertyTemplate.setRequired(false);
            }
            
            if (propertyTemplate.isRequired() && StringUtils.isEmpty(value)) {
            	String field = propertyTemplate.getName();
            	dynamicError += key + ":" + field + " is empty" + ",";
            }
        }
        
    	if (isIISServer) {
        	if (StringUtils.isEmpty(getAppName())) {
        		setAppNameError(getText(ERROR_CONFIG_APP_NAME ));
        		 hasError = true;
        	}
        	if (StringUtils.isEmpty(getSiteName())) {
        		setSiteNameError(getText(ERROR_CONFIG_SITE_NAME));
        		 hasError = true;
        	}
        }
        
        if (StringUtils.isNotEmpty(dynamicError)) {
	        dynamicError = dynamicError.substring(0, dynamicError.length() - 1);
	        setDynamicError(dynamicError);
	        hasError = true;
	   	}
        if (hasError) {
            setErrorFound(true);
        }
        
        return SUCCESS;
    }
    
    
    private String getDbDriver(String dbtype) {
		return dbDriverMap.get(dbtype);
	}
    
    private void initDriverMap() {
		dbDriverMap.put("mysql", "com.mysql.jdbc.Driver");
		dbDriverMap.put("oracle", "oracle.jdbc.OracleDriver");
		dbDriverMap.put("hsql", "org.hsql.jdbcDriver");
		dbDriverMap.put("mssql", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbDriverMap.put("db2", "com.ibm.db2.jcc.DB2Driver");
		dbDriverMap.put("mongodb", "com.mongodb.jdbc.MongoDriver");
	}
    
    private void saveCertificateFile(String path) throws PhrescoException {
    	try {
    		String host = (String) getHttpRequest().getParameter(SERVER_HOST);
			int port = Integer.parseInt(getHttpRequest().getParameter(SERVER_PORT));
			String certificateName = (String)getHttpRequest().getParameter("certificate");
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			List<CertificateInfo> certificates = administrator.getCertificate(host, port);
			if (CollectionUtils.isNotEmpty(certificates)) {
				for (CertificateInfo certificate : certificates) {
					if (certificate.getDisplayName().equals(certificateName)) {
						administrator.addCertificate(certificate, new File(Utility.getProjectHome() + projectCode + "/" + path));
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    }
    
    public String createEnvironment() {
        if (debugEnabled) {
            S_LOGGER.debug("Entered into Configurations.createEnvironment()");
        }
        
    	try {
    	    ConfigManager configManager = getConfigManager(getConfigPath());
			configManager.addEnvironments(getEnvironments());
    	} catch(Exception e) {
    	    return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_CREATE_ENVIRONMENT));
    	}
    	return envList();
    }
    
    public String deleteEnvironment() {
    	try {
    		if(selectedEnvirment != null) {
    			ConfigManager configManager = getConfigManager(getConfigPath());
    			configManager.deleteEnvironment(selectedEnvirment);
    		} 
    		
    	} catch(Exception e) {
    		 return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_DELETE_ENVIRONMENT));
    	}
    	return envList();
    }
    
    public String checkForRemove(){
		try {
    		ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
    		Project project = administrator.getProject(projectCode);
    		String removeItems = getHttpRequest().getParameter(DELETABLE_ENVS);
    		List<String> unDeletableEnvs = new ArrayList<String>();
	    	List<String> envs = Arrays.asList(removeItems.split(","));
	    	for (String env : envs) {
				// Check if configurations are already exist
				List<SettingsInfo> configurations = administrator.configurationsByEnvName(env, project);
                if (CollectionUtils.isNotEmpty(configurations)) {
                	unDeletableEnvs.add(env);
                	if(unDeletableEnvs.size() > 1){
                		setEnvError(getText(ERROR_ENVS_REMOVE, Collections.singletonList(unDeletableEnvs)));
                	} else{
                		setEnvError(getText(ERROR_ENV_REMOVE, Collections.singletonList(unDeletableEnvs)));
                	}
                }
			}
    	} catch(Exception e) {
                S_LOGGER.error("Entered into catch block of Configurations.checkForRemove()" + FrameworkUtil.getStackTraceAsString(e));
    	}
		return SUCCESS;
	}
    
    public String checkDuplicateEnv() throws PhrescoException {
        try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = administrator.getProject(projectCode);
            String envs = getHttpRequest().getParameter(ENVIRONMENT_VALUES);
            Collection<String> availableConfigEnvs = administrator.getEnvNames(project);
            for (String env : availableConfigEnvs) {
                if(env.equalsIgnoreCase(envs)) {
                    setEnvError(getText(ERROR_ENV_DUPLICATE, Collections.singletonList(envs)));
                }
            }
            availableConfigEnvs = administrator.getEnvNames();
            for (String env : availableConfigEnvs) {
                if(env.equalsIgnoreCase(envs)){
                    setEnvError(getText(ERROR_DUPLICATE_NAME_IN_SETTINGS, Collections.singletonList(envs)));
                }
            }

        } catch(Exception e) {
            throw new PhrescoException(e);
        }
        return SUCCESS;
    }
    
	public String edit() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.edit()");
    	}
        try {
        	List<Environment> environments = getAllEnvironments();
        	setReqAttribute(REQ_ENVIRONMENTS, environments);
        	ConfigManager configManager = getConfigManager(getConfigPath());
        	Configuration selectedConfigInfo = configManager.getConfiguration(currentEnvName, 
        			currentConfigType, currentConfigName);
        	
        	List<SettingsTemplate> configTemplates = getServiceManager().getconfigTemplates(getCustomerId());
            setReqAttribute(REQ_SETTINGS_TEMPLATES, configTemplates);
        	setReqAttribute(REQ_CONFIG_INFO, selectedConfigInfo);
        	setReqAttribute(REQ_FROM_PAGE, getFromPage());
        	
        } catch (PhrescoException e) {
        	return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_EDIT));
        } catch (ConfigurationException e) {
        	return showErrorPopup(new PhrescoException(e), getText(EXCEPTION_CONFIGURATION_UPDATE_FAILS));
		}

        getHttpRequest().setAttribute(REQ_SELECTED_MENU, APPLICATIONS);
        return APP_CONFIG_EDIT;
    }
	
	public String updateConfiguration(){
		if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.updateConfiguration()");
		}  
		
		try {
			update(getAppConfigPath());
		} catch (PhrescoException e) {
			return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_UPDATE_CONFIG));
         }
		
		return configList();
	}
	
	public String updateSettings(){
		
		try {
			update(getGlobalSettingsPath());
		} catch (PhrescoException e) {
			return showErrorPopup(e, getText(EXCEPTION_CONFIGURATION_UPDATE_SETTINGS));
         }
		
		return settingsList();
	}
    
    private void update(String configPath) {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method  Configurations.update()");
		}  
        try {
        	Environment env = getEnvironment();
        	ConfigManager configManager = getConfigManager(configPath);
        	Configuration config = getConfigInstance();
        	configManager.updateConfiguration(env.getName(), oldName, config);
        	
        } catch (PhrescoException e) {
        	new LogErrorReport(e, "Configurations update");
        } catch (ConfigurationException e) {
			e.printStackTrace();
		}
    }
    
    public String showProperties() {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method  Settings.settingsType()");
		}
		try {
			
			ApplicationInfo appInfo = getApplicationInfo();
		    List<PropertyTemplate> properties = getSettingTemplate().getProperties();
            setReqAttribute(REQ_PROPERTIES, properties);
            setReqAttribute(REQ_APPINFO, appInfo);
            List<Element> possibleTypes = getSettingTemplate().getPossibleTypes();
            List<String> typeValues = new ArrayList<String>();
            for (Element possibleType : possibleTypes) {
            	typeValues.add(possibleType.getName());
			}
            
            ConfigManager configManager = getConfigManager(getConfigPath());
            Configuration configuration = configManager.getConfiguration(selectedEnv, 
            		selectedType, selectedConfigname);

            
            if (configuration != null) {
                Properties selectedProperties = configuration.getProperties();
                setReqAttribute(REQ_PROPERTIES_INFO, selectedProperties);
            }
            
            setReqAttribute(REQ_FROM_PAGE, fromPage);
            setReqAttribute(REQ_TYPE_VALUES, typeValues);
            setReqAttribute(REQ_SELECTED_TYPE, selectedType);
		} catch (PhrescoException e) {
        	return showErrorPopup(e,  getText(EXCEPTION_CONFIGURATION_SHOW_PROPERTIES));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return SETTINGS_TYPE;
	}
    
   /* private void setDynamicParameter(String fieldName) {
        Class aClass = this.getClass();
        Class[] paramTypes = new Class[1]; 
            paramTypes[0] = String.class; // get the actual param type

        String methodName = "set"+fieldName; // fieldName String
        Method m = null;
              try {
                m = aClass.getMethod(methodName, paramTypes);
             }
             catch (NoSuchMethodException nsme) {
           nsme.printStackTrace();
          }
    }*/
    
    public String setAsDefault() {
    	S_LOGGER.debug("Entering Method  Configurations.setAsDefault()");
    	S_LOGGER.debug("SetAsdefault" + setAsDefaultEnv);
		try {
			
			ConfigManager configManager = getConfigManager(getAppConfigPath());
			configManager.addEnvironments(environments);
    		/*ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
    		Project project = administrator.getProject(projectCode);
    		
    		if (StringUtils.isEmpty(setAsDefaultEnv)) {
    			setEnvError(getText(SELECT_ENV_TO_SET_AS_DEFAULT));
    			S_LOGGER.debug("Env value is empty");
    		}
    		
    		//List<Environment> enviroments = getAllEnvironments();
    		List<Environment> enviroments = administrator.getEnvironments(project);
    		boolean envAvailable = false;
    		for (Environment environment : enviroments) {
				if(environment.getName().equals(setAsDefaultEnv)) {
					envAvailable = true;
				}
			}
    		
    		if(!envAvailable) {
    			setEnvError(getText(ENV_NOT_VALID));
    			S_LOGGER.debug("unable to find configuration in xml");
    			return SUCCESS;
    		}
	    	administrator.setAsDefaultEnv(setAsDefaultEnv, project);
	    	setEnvError(getText(ENV_SET_AS_DEFAULT_SUCCESS, Collections.singletonList(setAsDefaultEnv)));*/
	    	// set flag value to indicate , successfully env set as default
	    	flag = true;
	    	S_LOGGER.debug("successfully updated the config xml");
    	} catch(Exception e) {
    		setEnvError(getText(ENV_SET_AS_DEFAULT_ERROR));
            S_LOGGER.error("Entered into catch block of Configurations.setAsDefault()" + FrameworkUtil.getStackTraceAsString(e));
    	}
		return SUCCESS;
    }
    
    public String cloneConfigPopup() {
    	S_LOGGER.debug("Entering Method  Configurations.cloneConfigPopup()");
    	try {
    		ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
    		Project project = administrator.getProject(projectCode);
    		
    		List<Environment> environments = administrator.getEnvironments(project);
    		getHttpRequest().setAttribute(REQ_ENVIRONMENTS, environments);
    		getHttpRequest().setAttribute(REQ_PROJECT_CODE, projectCode);
    		getHttpRequest().setAttribute(CLONE_FROM_CONFIG_NAME, configName);
    		getHttpRequest().setAttribute(CLONE_FROM_ENV_NAME, envName);
    		getHttpRequest().setAttribute(CLONE_FROM_CONFIG_TYPE, configType);
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of Configurations.cloneConfigPopup()" + FrameworkUtil.getStackTraceAsString(e));
	    	}
		}
    	return SUCCESS;
    }

	public String cloneConfiguration() {
		S_LOGGER.debug("Entering Method  Configurations.cloneConfiguration()");
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			Project project = administrator.getProject(projectCode);
			
			String cloneFromEnvName = getHttpRequest().getParameter(CLONE_FROM_ENV_NAME);
			String cloneFromConfigType = getHttpRequest().getParameter(CLONE_FROM_CONFIG_TYPE);
			String cloneFromConfigName = getHttpRequest().getParameter(CLONE_FROM_CONFIG_NAME);
			
			boolean configExists = isConfigExists(project, envName, cloneFromConfigType, cloneFromConfigName);
			if (!configExists) {
				List<SettingsInfo> configurations = administrator.configurations(project, cloneFromEnvName, cloneFromConfigType, cloneFromConfigName);
				if (CollectionUtils.isNotEmpty(configurations)) {
						SettingsInfo settingsInfo = configurations.get(0);
						settingsInfo.setName(configName);
						settingsInfo.setDescription(description);
						administrator.createConfiguration(settingsInfo, envName, project);
				}
				flag = true;
			} else {
				setEnvError(getText(CONFIG_ALREADY_EXIST));
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			setEnvError(getText(CONFIGURATION_CLONNING_FAILED));
		}
		return SUCCESS;
	}
    
	public boolean isConfigExists(Project project, String envName, String configType, String cloneFromConfigName) throws PhrescoException {
		try {
		    if (configType.equals(Constants.SETTINGS_TEMPLATE_SERVER) || configType.equals(Constants.SETTINGS_TEMPLATE_EMAIL)) {
		    	ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	            List<SettingsInfo> settingsinfos = administrator.configurations(project, envName, configType, cloneFromConfigName);
	            if(CollectionUtils.isNotEmpty( settingsinfos)) {
//	                setTypeError(CONFIG_ALREADY_EXIST);
	                return true;
	            }
	    	}
		    return false;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
    public String fetchProjectInfoVersions() {
    	try {
//	    	String configType = getHttpRequest().getParameter("configType");
//	    	String name = getHttpRequest().getParameter("name");
//	    	ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
//	    	Project project = administrator.getProject(projectCode);
	    	//TODO:Need to handle
	    	/*Technology technology = project.getApplicationInfo().getTechnology();
	    	if ("Server".equals(configType)) {
	    		List<Server> servers = technology.getServers();
	    		if (servers != null && CollectionUtils.isNotEmpty(servers)) {
	    			for (Server server : servers) {
						if (server.getName().equals(name)) {
							setProjectInfoVersions(server.getVersions());
						}
					}
	    		}
	    	}
	    	if ("Database".equals(configType)) {
	    		List<Database> databases = technology.getDatabases();
	    		if (databases != null && CollectionUtils.isNotEmpty(databases)) {
	    			for (Database database : databases) {
						if (database.getName().equals(name)) {
							setProjectInfoVersions(database.getVersions());
						}
					}
	    		}
	    	}*/
    	} catch (Exception e) {
    		
    	}
    	return SUCCESS;
    }
    
    private String getGlobalSettingsPath() throws PhrescoException {
    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    	builder.append(getCustomerId());
		builder.append("-");
		builder.append(SETTINGS_INFO_FILE_NAME);
		return builder.toString();
    }
    
    private String getAppConfigPath() throws PhrescoException {
    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    	builder.append(getApplicationInfo().getAppDirName());
    	builder.append(File.separator);
    	builder.append(FOLDER_DOT_PHRESCO);
    	builder.append(File.separator);
    	builder.append(CONFIGURATION_INFO_FILE_NAME);
    	return builder.toString();
    }
    
    public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getDescription() {
   		return description;
     
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

	public String[] getAppliesto() {
		return appliesto;
	}

	public void setAppliesto(String[] appliesto) {
		this.appliesto = appliesto;
	}
	
	public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    
    public String getNameError() {
		return nameError;
	}

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	
	public String getTypeError() {
        return typeError;
    }

    public void setTypeError(String typeError) {
        this.typeError = typeError;
    }
	
	public String getDynamicError() {
		return dynamicError;
	}

	public void setDynamicError(String dynamicError) {
		this.dynamicError = dynamicError;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	
	public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
    
    public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}
	
	public String getEnvError() {
		return envError;
	}

	public void setEnvError(String envError) {
		this.envError = envError;
	}

	public boolean isEnvDeleteSuceess() {
		return isEnvDeleteSuceess;
	}

	public void setEnvDeleteSuceess(boolean isEnvDeleteSuceess) {
		this.isEnvDeleteSuceess = isEnvDeleteSuceess;
	}

	public String getEnvDeleteMsg() {
		return envDeleteMsg;
	}

	public void setEnvDeleteMsg(String envDeleteMsg) {
		this.envDeleteMsg = envDeleteMsg;
	}
	
	public List<String> getProjectInfoVersions() {
		return projectInfoVersions;
	}

	public void setProjectInfoVersions(List<String> projectInfoVersions) {
		this.projectInfoVersions = projectInfoVersions;
	}
	
	public String getOldConfigType() {
		return oldConfigType;
	}

	public void setOldConfigType(String oldConfigType) {
		this.oldConfigType = oldConfigType;
	}
	
	public String getPortError() {
		return portError;
	}

	public void setPortError(String portError) {
		this.portError = portError;
	}
	
	public String getEmailError() {
		return emailError;
	}

	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}

	public String getRemoteDeploymentChk() {
		return remoteDeploymentChk;
	}

	public void setRemoteDeploymentChk(String remoteDeploymentChk) {
		this.remoteDeploymentChk = remoteDeploymentChk;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppNameError() {
		return appNameError;
	}

	public void setAppNameError(String appNameError) {
		this.appNameError = appNameError;
	}

	public String getSiteNameError() {
		return siteNameError;
	}

	public void setSiteNameError(String siteNameError) {
		this.siteNameError = siteNameError;
	}

	public String getSiteCoreInstPath() {
		return siteCoreInstPath;
	}

	public void setSiteCoreInstPath(String siteCoreInstPath) {
		this.siteCoreInstPath = siteCoreInstPath;
	}

	public String getSiteCoreInstPathError() {
		return siteCoreInstPathError;
	}

	public void setSiteCoreInstPathError(String siteCoreInstPathError) {
		this.siteCoreInstPathError = siteCoreInstPathError;
	}

	public String getSetAsDefaultEnv() {
		return setAsDefaultEnv;
	}

	public void setSetAsDefaultEnv(String setAsDefaultEnv) {
		this.setAsDefaultEnv = setAsDefaultEnv;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String getConfigNameError() {
		return configNameError;
	}
	
	public void setConfigNameError(String configNameError) {
		this.configNameError = configNameError;
	}
	
	public String getConfigEnvError() {
		return configEnvError;
	}

	public void setConfigEnvError(String configEnvError) {
		this.configEnvError = configEnvError;
	}
	
	public String getConfigTypeError() {
		return configTypeError;
	}

	public void setConfigTypeError(String configTypeError) {
		this.configTypeError = configTypeError;
	}

	 public boolean isErrorFound() {
		return errorFound;
	}
	
	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}
	
    
    public List<Environment> getEnvironments() {
        return environments;
    }
    
    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public SettingsTemplate getSettingTemplate() {
        return settingTemplate;
    }

    public void setSettingTemplate(SettingsTemplate settingTemplate) {
        this.settingTemplate = settingTemplate;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
    
    public String getCurrentEnvName() {
		return currentEnvName;
	}

	public void setCurrentEnvName(String currentEnvName) {
		this.currentEnvName = currentEnvName;
	}

	public String getCurrentConfigType() {
		return currentConfigType;
	}

	public void setCurrentConfigType(String currentConfigType) {
		this.currentConfigType = currentConfigType;
	}

	public String getCurrentConfigName() {
		return currentConfigName;
	}

	public void setCurrentConfigName(String currentConfigName) {
		this.currentConfigName = currentConfigName;
	}

	public String getSelectedEnvirment() {
		return selectedEnvirment;
	}

	public void setSelectedEnvirment(String selectedEnvirment) {
		this.selectedEnvirment = selectedEnvirment;
	}

	public String getSelectedConfigId() {
		return selectedConfigId;
	}

	public void setSelectedConfigId(String selectedConfigId) {
		this.selectedConfigId = selectedConfigId;
	}
	
	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}
	
	public String getSelectedEnv() {
		return selectedEnv;
	}

	public void setSelectedEnv(String selectedEnv) {
		this.selectedEnv = selectedEnv;
	}

	public String getSelectedConfigname() {
		return selectedConfigname;
	}

	public void setSelectedConfigname(String selectedConfigname) {
		this.selectedConfigname = selectedConfigname;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}


	public String getConfigPath() {
		return configPath;
	}


	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	
	/*public Configuration getSelectedConfig() {
		return selectedConfig;
	}

	public void setSelectedConfig(Configuration selectedConfig) {
		this.selectedConfig = selectedConfig;
	}*/

}