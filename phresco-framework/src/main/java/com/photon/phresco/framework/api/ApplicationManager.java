package com.photon.phresco.framework.api;

import java.io.File;
import java.io.Reader;
import java.util.List;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.model.BuildInfo;

public interface ApplicationManager {

	/**
	 * This method updates the Application
	 * @param ProjectInfo
	 * @return Returns the updated ProjectInfo which contains the specific ApplicationInfo
	 * @throws PhrescoException
	 */
	ProjectInfo update(ProjectInfo projectInfo) throws PhrescoException;
	
	/**
	 * This method executes the maven command against the provided ActionType
	 * @param actionType
	 * @return Returns the maven output as Reader object
	 * @throws PhrescoException
	 */
	Reader performAction(ActionType actionType) throws PhrescoException;
	
	/**
	 * This method configures the maven site report for the application
	 * @param appInfo
	 * @param reportOptions
	 * @throws PhrescoException
	 */
	void configureReport(ApplicationInfo appInfo, List<String> reportOptions) throws PhrescoException;
	
	/**
	 * This method read the buildInfos from the build.info json file.
	 * @param buildInfoFile
	 * @return
	 * @throws PhrescoException
	 */
	List<BuildInfo> getBuildInfos(File buildInfoFile) throws PhrescoException;
}
