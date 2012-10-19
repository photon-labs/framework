package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.model.BuildInfo;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;
import com.sun.jersey.api.client.ClientResponse;

public class ApplicationManagerImpl implements ApplicationManager {

	private static final Logger S_LOGGER= Logger.getLogger(ApplicationManagerImpl.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	@Override
	public ProjectInfo update(ProjectInfo projectInfo,
			ServiceManager serviceManager) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.update(" +
					"ProjectInfo projectInfo, ServiceManager serviceManager)");
			S_LOGGER.debug("performAction() ProjectInformation = "+ projectInfo.getAppInfos().get(0));
		}
		ClientResponse response = serviceManager.updateProject(projectInfo);
		if (response.getStatus() == 200) {
				//TODO Define post update object and execute the corresponding technology implementation
//				updateProjectPOM(projectInfo);
//				if (TechnologyTypes.WIN_METRO.equalsIgnoreCase(techId)) {
//					ItemGroupUpdater.update(projectInfo, projectPath);
//				}
			
		} else if(response.getStatus() == 401){
			throw new PhrescoException("Session expired");
		} else {
			throw new PhrescoException("Project updation failed");
		}
		
		return projectInfo;
	}

	@Override
	public Reader performAction(ProjectInfo projectInfo, ActionType action, List<String> mavenArgCommands, String workingDirectory) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.performAction(" +
					"Project project, ActionType action, Map<String, String> paramsMap,CallBack callBack)");
			S_LOGGER.debug("performAction() ProjectInformation = "+projectInfo.getAppInfos().get(0));
		}
//    	StringBuilder command = action.getCommand();
//    	if (action.getCommand() == null) {
    		StringBuilder command = buildMavenCommand(action, mavenArgCommands);
//    	}
    	return executeMavenCommand(projectInfo, action, command, workingDirectory);
	}

	@Override
	public void configureReport(ApplicationInfo appInfo,List<String> reportOptions) throws PhrescoException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BuildInfo> getBuildInfos(File buildInfoFile) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.getBuildInfos(File buildInfoFile)");
		}
		 try {
			 return readBuildInfo(buildInfoFile);
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	}
	
	@Override
	public ApplicationInfo getApplicationInfo(String customerId, String projectId, String appId) throws PhrescoException {
		ProjectManager projectManager = PhrescoFrameworkFactory.getProjectManager();
		List<ProjectInfo> projectInfos = projectManager.discover(customerId);
		for (ProjectInfo projectInfo : projectInfos) {
			if(projectInfo.getId().equals(projectId)) {
				List<ApplicationInfo> appInfos = projectInfo.getAppInfos();
				for (ApplicationInfo applicationInfo : appInfos) {
					if(applicationInfo.getId().equals(appId)) {
						return applicationInfo;
					}
				}
			}
		}

		return null;
	}
	
	
	public StringBuilder buildMavenCommand(ActionType actionType, List<String> mavenArgCommands) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationManagerImpl.buildMavenCommand(ProjectInfo projectInfo, ActionType actionType)");
		}
        StringBuilder builder = new StringBuilder(Constants.MVN_COMMAND);
        builder.append(Constants.SPACE);
        builder.append(actionType.getActionType());
        
        if (CollectionUtils.isNotEmpty(mavenArgCommands)) {
        	for (String mavenArgCommand : mavenArgCommands) {
        		builder.append(Constants.SPACE);
        		builder.append(mavenArgCommand);
			}
        }
        return builder;
    }
	
	private BufferedReader executeMavenCommand(ProjectInfo projectInfo, ActionType action, StringBuilder command, String workingDirectory) throws PhrescoException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method ApplicationManagerImpl.executeMavenCommand(Project project, ActionType action, StringBuilder command)");
    		S_LOGGER.debug("executeMavenCommand() Project Code = " + projectInfo.getProjectCode());
    		S_LOGGER.debug("executeMavenCommand() Command = " + command.toString());
    		S_LOGGER.debug("executeMavenCommand() ActionType Name = " + action.getActionType());
//    		S_LOGGER.debug("executeMavenCommand() ActionType Working directory = " + action.getWorkingDirectory());
		}
		Commandline cl = new Commandline(command.toString());
//        String workingDirectory = action.getWorkingDirectory();
//        if (StringUtils.isNotEmpty(workingDirectory)) {
//            cl.setWorkingDirectory(workingDirectory);
//        } else {
            cl.setWorkingDirectory(workingDirectory);
//        }
        try {
            Process process = cl.execute();
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (CommandLineException e) {
            throw new PhrescoException(e);
        }
    }
	
	private List<BuildInfo> readBuildInfo(File path) throws IOException {
		 S_LOGGER.debug("Entering Method ApplicationManagerImpl.readBuildInfo(File path)");
		 S_LOGGER.debug("getBuildInfos() File Path = "+path.getPath());

		 if (!path.exists()) {
			 S_LOGGER.error("readBuildInfo() > " + FrameworkImplConstants.ERROR_FILE_PATH_INCORRECT + path);
			 return new ArrayList<BuildInfo>(1);
		 }

		 BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<BuildInfo>>(){}.getType();

		 List<BuildInfo> buildInfos = gson.fromJson(bufferedReader, type);
		 //TODO:Need to handle
		 //Collections.sort(buildInfos, new BuildInfoComparator());
		 bufferedReader.close();

		 return buildInfos;
	 }
}
