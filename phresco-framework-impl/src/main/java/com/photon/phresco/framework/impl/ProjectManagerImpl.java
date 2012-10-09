package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class ProjectManagerImpl implements ProjectManager, FrameworkConstants, Constants, ServiceClientConstant {
	
	private static final Logger S_LOGGER= Logger.getLogger(ProjectManagerImpl.class);
	private static boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public List<ProjectInfo> discover(String customerId) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method ProjectManagerImpl.discover(String CustomerId)");
		}

		File projectsHome = new File(Utility.getProjectHome());
		if (isDebugEnabled) {
			S_LOGGER.debug("discover( )  projectHome = "+projectsHome);
		}
		
		if (!projectsHome.exists()) {
			return null;
		}

		List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();
		File[] dotPhrescoFolders = projectsHome.listFiles(new PhrescoFileNameFilter(FOLDER_DOT_PHRESCO));
        for (File dotPhrescoFolder : dotPhrescoFolders) {
			File[] dotProjectFiles = dotPhrescoFolder.listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
			fillProjects(dotProjectFiles, projectInfos, customerId);
		}
		
		return projectInfos;
	}

	public ProjectInfo getProject(String projectId) throws PhrescoException {
		return null;
	}

	public ProjectInfo create(ProjectInfo projectInfo) throws PhrescoException {
		return null;
	}

	public ProjectInfo update(ProjectInfo projectInfo) throws PhrescoException {
		return null;
	}

	public boolean delete(ProjectInfo projectInfo) throws PhrescoException {
		return false;
	}
	
	private void fillProjects(File[] dotProjectFiles, List<ProjectInfo> projectInfos, String customerId) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectManagerImpl.fillProjects(File[] dotProjectFiles, List<Project> projects)");

		 if(ArrayUtils.isEmpty(dotProjectFiles)) {
			 return;
		 }

		 Gson gson = new Gson();
		 BufferedReader reader = null;

		 for (File dotProjectFile : dotProjectFiles) {
			 try {
				 reader = new BufferedReader(new FileReader(dotProjectFile));
				 ProjectInfo projectInfo = gson.fromJson(reader, ProjectInfo.class);
				 if (projectInfo.getCustomerIds().get(0).equalsIgnoreCase(customerId)) {
					 projectInfos.add(projectInfo);
				 }
			 } catch (FileNotFoundException e) {
				 throw new PhrescoException(e);
			 } finally {
				 Utility.closeStream(reader);
			 }
		 }
	 }

	private class PhrescoFileNameFilter implements FilenameFilter {
		 private String filter_;
		 public PhrescoFileNameFilter(String filter) {
			 filter_ = filter;
		 }

		 public boolean accept(File dir, String name) {
			 return name.endsWith(filter_);
		 }
	 }
}
