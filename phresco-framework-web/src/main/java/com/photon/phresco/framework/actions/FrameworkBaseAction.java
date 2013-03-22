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
package com.photon.phresco.framework.actions;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.LogInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.commons.FrameworkActions;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;

public class FrameworkBaseAction extends ActionSupport implements FrameworkConstants, FrameworkActions, ServiceClientConstant {
	private static final Logger S_LOGGER = Logger.getLogger(FrameworkBaseAction.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();

    private static final long serialVersionUID = 1L;
    private String path = null;
    private String copyToClipboard = null;
    private String customerId = "";
    private String projectId = "";
    private String appId = "";
    private Map<String, Object> map = null;
    private String fileName = "";
    private String configTempType = "";
    private String envName = "";
    private String configName = "";
    private String fromConfig = "";
    
    private static ServiceManager serviceManager = null;
    
    protected ServiceManager getServiceManager() {
		return serviceManager;
	}
    
    public ActionContext getActionContext() {
        return ActionContext.getContext();
    }
    
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }
    
    public HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }
    
    public void openFolder() {
		if (debugEnabled) {
			S_LOGGER.debug("Entered FrameworkBaseAction.openFolder()");
		}
		try {
			if (Desktop.isDesktopSupported()) {
				File dir = new File(Utility.getProjectHome() + path);
				if(dir.exists()){
	    		Desktop.getDesktop().open(new File(Utility.getProjectHome() + path));
				}else{
					Desktop.getDesktop().open(new File(Utility.getProjectHome()));
				}
	    	}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Unable to open the Path, " + FrameworkUtil.getStackTraceAsString(e));
				new LogErrorReport(e, "Open Folder");
			}
		}
	}
    
    public void copyPath() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entered FrameworkBaseAction.copyPath");
    	}
    	File dir = new File(Utility.getProjectHome() + path);
    	if(dir.exists()){
    		copyToClipboard = Utility.getProjectHome() + path;
    	}else{
    		copyToClipboard = Utility.getProjectHome();
    	}
    	copyToClipboard ();
    }
    
    public void copyToClipboard () {
    	S_LOGGER.debug("Entered FrameworkBaseAction.copyToClipboard");
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(new StringSelection(copyToClipboard.replaceAll("(?m)^[ \t]*\r?\n", "")), null);
    }
    
    protected List<String> getProjectModules(String appDirName) throws PhrescoException {
    	try {
            StringBuilder builder = getProjectHome(appDirName);
            builder.append(File.separatorChar);
            builder.append(POM_XML);
    		File pomPath = new File(builder.toString());
    		PomProcessor processor = new PomProcessor(pomPath);
    		Modules pomModule = processor.getPomModule();
    		if (pomModule != null) {
    			return pomModule.getModule();
    		}
    	} catch (PhrescoPomException e) {
    		 throw new PhrescoException(e);
    	}
    	
    	return null;
    }

	private StringBuilder getProjectHome(String appDirName) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(appDirName);
		
		return builder;
	}
    
    protected List<String> getWarProjectModules(String appDirName) throws PhrescoException {
    	try {
			List<String> projectModules = getProjectModules(appDirName);
			List<String> warModules = new ArrayList<String>(5);
			if (CollectionUtils.isNotEmpty(projectModules)) {
				for (String projectModule : projectModules) {
					StringBuilder pathBuilder = getProjectHome(appDirName);
					pathBuilder.append(File.separatorChar);
					pathBuilder.append(projectModule);
					pathBuilder.append(File.separatorChar);
					pathBuilder.append(POM_XML);
					PomProcessor processor = new PomProcessor(new File(pathBuilder.toString()));
					String packaging = processor.getModel().getPackaging();
					if (StringUtils.isNotEmpty(packaging) && WAR.equalsIgnoreCase(packaging)) {
						warModules.add(projectModule);
					}
				}
			}
			return warModules;
		} catch (PhrescoException e) {
			throw new PhrescoException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		}
    }
    
	public static boolean isConnectionAlive(String protocol, String host, int port) {
		boolean isAlive = true;
		try {
			URL url = new URL(protocol, host, port, "");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			isAlive = false;
		}
		return isAlive;
	}
    
    protected String showErrorPopup(PhrescoException e, String action) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stacktrace = sw.toString();
            User userInfo = (User) getSessionAttribute(SESSION_USER_INFO);
            LogInfo log = new LogInfo();
            log.setMessage(e.getLocalizedMessage());
            log.setTrace(stacktrace);
            log.setAction(action);
            log.setUserId(userInfo.getId());
            setReqAttribute(REQ_LOG_REPORT, log);
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    //Do nothing due to error popup
                }
            }
        }
        return LOG_ERROR;
    }
    
    protected User doLogin(Credentials credentials) {
        try {
            String userName = credentials.getUsername();
            String password = credentials.getPassword();
            ServiceContext context = new ServiceContext();
            FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
            context.put(SERVICE_URL, configuration.getServerPath());
            context.put(SERVICE_USERNAME, userName);
            context.put(SERVICE_PASSWORD, password);
            context.put(SERVICE_API_KEY, configuration.apiKey());
        
            serviceManager = ServiceClientFactory.getServiceManager(context);
        } catch (PhrescoWebServiceException ex) {
            S_LOGGER.error(ex.getLocalizedMessage());
            throw new PhrescoWebServiceException(ex.getResponse());
        } catch (PhrescoException e) {
        	throw new PhrescoWebServiceException(e);
		}
        return serviceManager.getUserInfo();
    }
    
	protected ProjectInfo getProjectInfo() throws PhrescoException {
		ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
		ProjectInfo projectInfo = projectManager.getProject(getProjectId(), getCustomerId(), getAppId());
		return projectInfo;
	}
	
    public ApplicationInfo getApplicationInfo() throws PhrescoException {
        ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
        return applicationManager.getApplicationInfo(getCustomerId(), getProjectId(), getAppId());
    }
    
    public String getTechId() throws PhrescoException {
    	TechnologyInfo techInfo = getApplicationInfo().getTechInfo();
    	if (techInfo != null) {
    		return techInfo.getId();
    	}
    	throw new PhrescoException("Technology not Found");
    }
    
    public String getApplicationHome() throws PhrescoException {
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(getApplicationInfo().getAppDirName());
        return builder.toString();
    }
    
    public String getAppPom() throws PhrescoException {
        StringBuilder builder = new StringBuilder(getApplicationHome());
        builder.append(File.separator);
        builder.append(POM_FILE);
        return builder.toString();
    }
    
    /**
     * To get path of the application
     * @param applicationInfo
     */
    protected String getAppDirectoryPath(ApplicationInfo applicationInfo) {
    	return Utility.getProjectHome() + applicationInfo.getAppDirName();
    }
    
    protected String getBuildInfosFilePath(ApplicationInfo applicationInfo) {
    	return getAppDirectoryPath(applicationInfo) + FILE_SEPARATOR + BUILD_DIR + FILE_SEPARATOR +BUILD_INFO_FILE_NAME;
    }
    
    protected String getSettingsPath() {
    	return Utility.getProjectHome() + getCustomerId() +  SETTINGS_XML;
    }

    protected void setReqAttribute(String key, Object value) {
        getHttpRequest().setAttribute(key, value);
    }
    
    protected Object getReqAttribute(String key) {
        return getHttpRequest().getAttribute(key);
    }
    
    private Map<String, Object> getContextParameters() {
        if (map == null) {
            map = getActionContext().getParameters();
        }
        return map;
    }
    
    protected String getActionContextParam(String key) {
		return (String) getContextParameters().get(key);
	}
    
    protected String getReqParameter(String key) {
        return getHttpRequest().getParameter(key);
    }
	
	protected String[] getReqParameterValues(String Key) {
		return getHttpRequest().getParameterValues(Key);
	}
    
    protected void setSessionAttribute(String key, Object value) {
        getHttpSession().setAttribute(key, value);
    }
    
    protected void removeSessionAttribute(String key) {
        getHttpSession().removeAttribute(key);
    }
    
    protected Object getSessionAttribute(String key) {
        return getHttpSession().getAttribute(key);
    }
    
    protected HttpServletResponse getHttpResponse() {
        return (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
    }
    
    protected ApplicationProcessor getApplicationProcessor() throws PhrescoException {
        ApplicationProcessor applicationProcessor = null;
        try {
            Customer customer = getServiceManager().getCustomer(getCustomerId());
            RepoInfo repoInfo = customer.getRepoInfo();
            StringBuilder sb = new StringBuilder(getApplicationHome())
            .append(File.separator)
            .append(Constants.DOT_PHRESCO_FOLDER)
            .append(File.separator)
            .append(Constants.APPLICATION_HANDLER_INFO_FILE);
            MojoProcessor mojoProcessor = new MojoProcessor(new File(sb.toString()));
            ApplicationHandler applicationHandler = mojoProcessor.getApplicationHandler();
            if (applicationHandler != null) {
                List<ArtifactGroup> plugins = setArtifactGroup(applicationHandler);
                PhrescoDynamicLoader dynamicLoader = new PhrescoDynamicLoader(repoInfo, plugins);
                applicationProcessor = dynamicLoader.getApplicationProcessor(applicationHandler.getClazz());
            }
        } catch (PhrescoException e) {
           throw new PhrescoException(e);
        }

        return applicationProcessor;
    }

    protected List<ArtifactGroup> setArtifactGroup(ApplicationHandler applicationHandler) {
        List<ArtifactGroup> plugins = new ArrayList<ArtifactGroup>();
        ArtifactGroup artifactGroup = new ArtifactGroup();
        artifactGroup.setGroupId(applicationHandler.getGroupId());
        artifactGroup.setArtifactId(applicationHandler.getArtifactId());
        List<ArtifactInfo> artifactInfos = new ArrayList<ArtifactInfo>();
        ArtifactInfo artifactInfo = new ArtifactInfo();
        artifactInfo.setVersion(applicationHandler.getVersion());
        artifactInfos.add(artifactInfo);
        artifactGroup.setVersions(artifactInfos);
        plugins.add(artifactGroup);
        return plugins;
    }
    
    //To get byte array of uploaded jar
    protected byte[] getByteArray() throws PhrescoException {
        PrintWriter writer = null;
        byte[] byteArray = null;
        try {
            writer = getHttpResponse().getWriter();
            setFileName(getHttpRequest().getHeader(X_FILE_NAME));
            setConfigTempType(getHttpRequest().getHeader(CONFIG_TEMP_TYPE));
            setProjectId(getHttpRequest().getHeader(REQ_PROJECT_ID));
            setAppId(getHttpRequest().getHeader(REQ_APP_ID));
            setCustomerId(getHttpRequest().getHeader(REQ_CUSTOMER_ID));
            setEnvName(getHttpRequest().getHeader("envName"));
            setConfigName(getHttpRequest().getHeader("configName"));
            setFromConfig(getHttpRequest().getHeader("from"));
            InputStream is = getHttpRequest().getInputStream();
            byteArray = IOUtils.toByteArray(is);
        } catch (Exception e) {
            getHttpResponse().setStatus(getHttpResponse().SC_INTERNAL_SERVER_ERROR);
            writer.print(SUCCESS_FALSE);
            throw new PhrescoException(e);
        }
        return byteArray;
    }

    protected String getLogoImageString() throws PhrescoException {
    	String encodeImg = "";
    	try {
        	InputStream fileInputStream = null;
    		fileInputStream = getServiceManager().getIcon(getCustomerId());
    		if (fileInputStream != null) {
        		byte[] imgByte = null;
        		imgByte = IOUtils.toByteArray(fileInputStream);
        	    byte[] encodedImage = Base64.encodeBase64(imgByte);
                encodeImg = new String(encodedImage);
    		}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    	return encodeImg;
    }
    
    protected String getThemeColorJson() throws PhrescoException {
    	String themeJsonStr = "";
    	try {
    		User user = (User) getSessionAttribute(SESSION_USER_INFO);
    		List<Customer> customers = user.getCustomers();
    		for (Customer customer : customers) {
				if (customer.getId().equals(getCustomerId())) {
					Map<String, String> frameworkTheme = customer.getFrameworkTheme();
					if (frameworkTheme != null) {
						Gson gson = new Gson();
						themeJsonStr = gson.toJson(frameworkTheme);
					}
				}
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    	return themeJsonStr;
    }
    
    public Comparator sortByNameInAlphaOrder() {
		return new Comparator() {
		    public int compare(Object firstObject, Object secondObject) {
		    	ProjectInfo projectInfo1 = (ProjectInfo) firstObject;
		    	ProjectInfo projectInfo2 = (ProjectInfo) secondObject;
		       return projectInfo1.getName().compareToIgnoreCase(projectInfo2.getName());
		    }
		};
	}
    
    public Comparator sortValuesInAlphaOrder() {
		return new Comparator() {
		    public int compare(Object firstObject, Object secondObject) {
		    	String val1 = (String) firstObject;
		    	String val2 = (String) secondObject;
		       return val1.compareToIgnoreCase(val2);
		    }
		};
	}
    
    public void updateLatestProject() throws PhrescoException {
        try {
            File tempPath = new File(Utility.getPhrescoTemp() + File.separator + USER_PROJECT_JSON);
            User user = (User) getSessionAttribute(SESSION_USER_INFO);
            JSONObject userProjJson = null;
            JSONParser parser = new JSONParser();
            if (tempPath.exists()) {
                FileReader reader = new FileReader(tempPath);
                userProjJson = (JSONObject)parser.parse(reader);
                reader.close();
            } else {
                userProjJson = new JSONObject();
            }
            
            userProjJson.put(user.getId(), getProjectId());
            FileWriter  writer = new FileWriter(tempPath);
            writer.write(userProjJson.toString());
            writer.close();
        } catch (IOException e) {
            throw new PhrescoException(e);
        } catch (ParseException e) {
            throw new PhrescoException(e);
        }
    }

    public String getPath() {
    	return path;
    }

    public void setPath(String path) {
    	this.path = path;
    }

	public String getCopyToClipboard() {
		return copyToClipboard;
	}

	public void setCopyToClipboard(String copyToClipboard) {
		this.copyToClipboard = copyToClipboard;
	}

	public String getCustomerId() {
	    return customerId;
	}

	public void setCustomerId(String customerId) {
	    this.customerId = customerId;
	}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConfigTempType() {
        return configTempType;
    }

    public void setConfigTempType(String configTempType) {
        this.configTempType = configTempType;
    }
    
    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
    
    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public void setFromConfig(String fromConfig) {
        this.fromConfig = fromConfig;
    }

    public String getFromConfig() {
        return fromConfig;
    }
}