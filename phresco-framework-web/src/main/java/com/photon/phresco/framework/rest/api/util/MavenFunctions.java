package com.photon.phresco.framework.rest.api.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.api.ApplicationProcessor;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroup;
import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.RepoInfo;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.impl.ProjectManagerImpl;
import com.photon.phresco.framework.rest.api.ServiceManagerMap;
import com.photon.phresco.framework.rest.api.util.MavenServiceConstants;
import com.photon.phresco.plugins.model.Mojos.ApplicationHandler;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.Childs.Child;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.MavenCommands.MavenCommand;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.PhrescoDynamicLoader;
import com.photon.phresco.util.Utility;



public class MavenFunctions implements Constants ,FrameworkConstants,MavenServiceConstants {
	

	
	private final String PHASE_RUNAGAINST_SOURCE = "run-against-source";
    public static final java.lang.String SERVICE_URL = "phresco.service.url";
    public static final java.lang.String SERVICE_USERNAME = "phresco.service.username";
    public static final java.lang.String SERVICE_PASSWORD = "phresco.service.password";
    public static final java.lang.String SERVICE_API_KEY = "phresco.service.api.key";
    
	private static final Logger S_LOGGER= Logger.getLogger(MavenFunctions.class);
	private static boolean isInfoEnabled = S_LOGGER.isInfoEnabled();
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
    
	String appId="";
	String projectId="";
	String customerId="";
	String selectedFiles="";
	String phase="";
	String username="";
	/*String uniquekey="";*/
	

	HttpServletRequest request;
	
	private ServiceManager serviceManager = null;
	
	
	/*public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}*/
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
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
	public String getSelectedFiles() {
		return selectedFiles;
	}
	public void setSelectedFiles(String selectedFiles) {
		this.selectedFiles = selectedFiles;
	}
	
	public void prePopulateModelData(HttpServletRequest request) throws PhrescoException {
		
		
		try {
			
			this.request = request;
			
			
			/*if( !("".equalsIgnoreCase(request.getParameter(UNIQUE_KEY))) && (request.getParameter(UNIQUE_KEY) != null) && !("null".equalsIgnoreCase(request.getParameter(UNIQUE_KEY))) )
			{
				setUniquekey(request.getParameter(UNIQUE_KEY));	
			}
			else
			{
				throw new PhrescoException("No valid Unique Key is passed");
			}*/
			
			if( !("".equalsIgnoreCase(request.getParameter(APP_ID))) && (request.getParameter(APP_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(APP_ID))) )
			{
				setAppId(request.getParameter(APP_ID));	
			}
			else
			{
				throw new PhrescoException("No valid App Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(PROJECT_ID))) && (request.getParameter(PROJECT_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(PROJECT_ID))) )
			{
				setProjectId(request.getParameter(PROJECT_ID));
			}
			else
			{
				throw new PhrescoException("No valid Project Id Passed");
			}
			
			if( !("".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) && (request.getParameter(CUSTOMER_ID) != null) && !("null".equalsIgnoreCase(request.getParameter(CUSTOMER_ID))) )
			{
				setCustomerId(request.getParameter(CUSTOMER_ID));
			}
			else
			{
				throw new PhrescoException("No valid Customer Id Passed");
			}
			if( !("".equalsIgnoreCase(request.getParameter(USERNAME))) && (request.getParameter(USERNAME) != null) && !("null".equalsIgnoreCase(request.getParameter(USERNAME))) )
			{
				setUsername(request.getParameter(USERNAME));
			}
			else
			{
				throw new PhrescoException("No User Id passed");
			}
			
			setSelectedFiles(request.getParameter(SELECTED_FILES));
			
		} catch (Exception e) {
			throw new PhrescoException("Required parameters are not passed");
		}
	}
	
	
    public MavenResponse processBuildRequest(HttpServletRequest request){

		boolean continue_status = true;
		MavenResponse response = new MavenResponse();
		
			try {
			
					try	{
			
						prePopulateModelData(request);
					
					}catch (PhrescoException e) {
			
						S_LOGGER.error(e.getMessage());
						continue_status = false;
						
						response.setStatus(ERROR);
						response.setLog("");
						response.setService_exception(e.getMessage());
						response.setUniquekey("");
					}
					
					
					if(continue_status)
					{
							
						if (isDebugEnabled) {
							S_LOGGER.debug("APPP ID received :"+getAppId());
							S_LOGGER.debug("PROJECT ID received :"+getProjectId());
							S_LOGGER.debug("CUSTOMER ID received :"+getCustomerId());
							S_LOGGER.debug("USERNAME  received :"+getUsername());
						}
						
		            	try {
		            		
						     BufferedReader server_logs = build(getUsername());
						     
						     UUID uniqueKey = UUID.randomUUID();
						     String unique_key = uniqueKey.toString();
						     BufferMap.addBufferReader(unique_key, server_logs);
						     
						     	response.setStatus(STARTED);
								response.setLog(STARTED);
								response.setService_exception("");
								response.setUniquekey(unique_key);
						     
						     /*String line="";
				                while((line=server_logs.readLine())!=null) {
				                	
				                	System.out.println(line);
				                }*/
						} catch (PhrescoException e) {
							
							response.setStatus(ERROR);
							response.setLog("");
							response.setService_exception(e.getMessage());
							response.setUniquekey("");
							S_LOGGER.error(e.getMessage());
						} catch (Exception e) {
							
							response.setStatus(ERROR);
							response.setLog("");
							response.setService_exception(e.getMessage());
							response.setUniquekey("");
							S_LOGGER.error(e.getMessage());
						}
						            	
					}
				            
	        
			}catch(Exception e){
				
				response.setStatus(ERROR);
				response.setLog("");
				response.setService_exception(e.getMessage());
				response.setUniquekey("");
				S_LOGGER.error(e.getMessage());
		}
			
			return response;
    }
	

	
	public BufferedReader build(String username) throws PhrescoException, IOException {

		BufferedReader reader=null;
		try {
			
			ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
			ProjectInfo projectInfo = getProjectInfo();
			ApplicationInfo applicationInfo = getApplicationInfo();
			MojoProcessor mojo = new MojoProcessor(new File(getPhrescoPluginInfoFilePath(PHASE_PACKAGE)));
			persistValuesToXml(mojo, PHASE_PACKAGE);
			//To get maven build arguments
			List<Parameter> parameters = getMojoParameters(mojo, PHASE_PACKAGE);
			List<String> buildArgCmds = getMavenArgCommands(parameters);
			buildArgCmds.add(HYPHEN_N);
			String workingDirectory = getAppDirectoryPath(applicationInfo);
			getApplicationProcessor(username).preBuild(getApplicationInfo());
			reader = applicationManager.performAction(projectInfo, ActionType.BUILD, buildArgCmds, workingDirectory);
			
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException("Exception occured in the build process");
		}
		 catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException("Exception occured in the build process");
			}

		return reader;
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
	 
	 public String getPhrescoPluginInfoFilePath(String goal) throws PhrescoException {
			StringBuilder sb = new StringBuilder(getApplicationHome());
			sb.append(File.separator);
			sb.append(FOLDER_DOT_PHRESCO);
			sb.append(File.separator);
			sb.append(PHRESCO_HYPEN);
			// when phase is CI, it have to take ci info file for update dependency
			if (PHASE_CI.equals(getPhase())) {
				sb.append(getPhase());
			} else if (StringUtils.isNotEmpty(goal) && goal.contains(FUNCTIONAL)) {
				sb.append(PHASE_FUNCTIONAL_TEST);
			} else if (PHASE_RUNGAINST_SRC_START.equals(goal)|| PHASE_RUNGAINST_SRC_STOP.equals(goal) ) {
				sb.append(PHASE_RUNAGAINST_SOURCE);
			} else {
				sb.append(goal);
			}
			sb.append(INFO_XML);

			return sb.toString();
		}
	 
	 public String getApplicationHome() throws PhrescoException {
	        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
	        builder.append(getApplicationInfo().getAppDirName());
	        return builder.toString();
	    }
	 
	 
	 String[] getReqParameterValues (String name) throws PhrescoException {
		 
		 return request.getParameterValues(name);
	 }
	 
	 String getReqParameter(String name) throws PhrescoException {
		 
		 return request.getParameter(name);
		 
	 }
	 
	 
	 protected void persistValuesToXml(MojoProcessor mojo, String goal) throws PhrescoException {
		    try {
		        List<Parameter> parameters = getMojoParameters(mojo, goal);
		        if (CollectionUtils.isNotEmpty(parameters)) {
		            for (Parameter parameter : parameters) {
		                StringBuilder csParamVal = new StringBuilder();
		                if (Boolean.parseBoolean(parameter.getMultiple())) {
		                	if (getReqParameterValues(parameter.getKey()) == null) {
		                		parameter.setValue("");
		                	} else {
		                		String[] parameterValues = getReqParameterValues(parameter.getKey());
			                    for (String parameterValue : parameterValues) {
			                        csParamVal.append(parameterValue);
			                        csParamVal.append(",");
			                    }
			                    String csvVal = csParamVal.toString();
			                    parameter.setValue(csvVal.toString().substring(0, csvVal.lastIndexOf(",")));
		                	}
		                } else if (TYPE_BOOLEAN.equalsIgnoreCase(parameter.getType())) {
		                    if (getReqParameter(parameter.getKey()) != null) {
		                        parameter.setValue(getReqParameter(parameter.getKey()));
		                    } else {
		                        parameter.setValue(Boolean.FALSE.toString());
		                    }
		                } else if (parameter.getType().equalsIgnoreCase(TYPE_MAP)) {
		                    List<Child> childs = parameter.getChilds().getChild();
		                    String[] keys = getReqParameterValues(childs.get(0).getKey());
		                    String[] values = getReqParameterValues(childs.get(1).getKey());
		                    Properties properties = new Properties();
		                    for (int i = 0; i < keys.length; i++) {
		                        properties.put(keys[i], values[i]);
		                    }
		                    StringWriter writer = new StringWriter();
		                    properties.store(writer, "");
		                    String value = writer.getBuffer().toString();
		                    parameter.setValue(value);
		                } else if (parameter.getType().equalsIgnoreCase(TYPE_PACKAGE_FILE_BROWSE)) {
		                	parameter.setValue(getSelectedFiles());
		                } else if(parameter.getType().equalsIgnoreCase(TYPE_PASSWORD)) {
		                	byte[] encodedPwd = Base64.encodeBase64(getReqParameter(parameter.getKey()).getBytes());
		                	String encodedString = new String(encodedPwd);
		                	parameter.setValue(encodedString);
		                } else {
		                    parameter.setValue(StringUtils.isNotEmpty(getReqParameter(parameter.getKey())) ? (String)getReqParameter(parameter.getKey()) : "");
		                }
		            }
		        }
		        mojo.save();
		        
		    } catch (IOException e) {
		        throw new PhrescoException(e);
		    }  catch (Exception e) {
		        throw new PhrescoException(e);
		    }
		    
		}

	 
	 protected List<Parameter> getMojoParameters(MojoProcessor mojo, String goal) throws PhrescoException {
			com.photon.phresco.plugins.model.Mojos.Mojo.Configuration mojoConfiguration = mojo.getConfiguration(goal);
			if (mojoConfiguration != null) {
			    return mojoConfiguration.getParameters().getParameter();
			}
			
			return null;
		}
	 
	 protected List<String> getMavenArgCommands(List<Parameter> parameters) throws PhrescoException {
			List<String> buildArgCmds = new ArrayList<String>();	
			if(CollectionUtils.isEmpty(parameters)) {
				return buildArgCmds;
			}
			for (Parameter parameter : parameters) {
				if (parameter.getPluginParameter()!= null && PLUGIN_PARAMETER_FRAMEWORK.equalsIgnoreCase(parameter.getPluginParameter())) {
					List<MavenCommand> mavenCommand = parameter.getMavenCommands().getMavenCommand();
					for (MavenCommand mavenCmd : mavenCommand) {
						if (StringUtils.isNotEmpty(parameter.getValue()) && parameter.getValue().equalsIgnoreCase(mavenCmd.getKey())) {
							buildArgCmds.add(mavenCmd.getValue());
						}
					}
				}
			}
			return buildArgCmds;
		}
	 
	 protected String getAppDirectoryPath(ApplicationInfo applicationInfo) throws PhrescoException {
	    	return Utility.getProjectHome() + applicationInfo.getAppDirName();
	    }
	 
	 protected ApplicationProcessor getApplicationProcessor(String username) throws PhrescoException {
	        ApplicationProcessor applicationProcessor = null;
	        try {
	        	//doLogin();
	            Customer customer = getServiceManager(username).getCustomer(getCustomerId());
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
	        	e.printStackTrace();
	            throw new PhrescoException(e);
	        }catch (Exception e) {
	        	e.printStackTrace();
		        throw new PhrescoException(e);
		    }

	        return applicationProcessor;
	    }
	 
	 protected List<ArtifactGroup> setArtifactGroup(ApplicationHandler applicationHandler) throws PhrescoException {
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
	 
	 
	 protected ServiceManager getServiceManager(String username) throws PhrescoException {
		this.serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(username);
		return serviceManager;
		}
	 
	 //------------ Will be replaced by reusing the login service coding .
	 
	 
	 //private static ServiceManager serviceManager = null;
	    
	   /* protected ServiceManager getServiceManager(String username) {
			return serviceManager;
		}
	    
	    
	    
	    protected User doLogin() {
	    	
	        try {
	        	Credentials credentials = new Credentials("demouser", "phresco");
	        	
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
	            throw new PhrescoWebServiceException(ex.getResponse());
	        } catch (PhrescoException e) {
	        	throw new PhrescoWebServiceException(e);
			}
	        return serviceManager.getUserInfo();
	    }*/
	    
	    
	    

}

