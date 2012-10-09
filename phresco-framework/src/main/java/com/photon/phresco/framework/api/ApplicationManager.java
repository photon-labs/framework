package com.photon.phresco.framework.api;

import java.io.Reader;
import java.util.List;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;

public interface ApplicationManager {

	/**
	 * This method updates the Application
	 * @param ProjectInfo
	 * @return Returns the updated ProjectInfo which contains the specific ApplicationInfo
	 */
	ProjectInfo update(ProjectInfo ProjectInfo);
	
	/**
	 * This method executes the maven command against the provided ActionType
	 * @param actionType
	 * @return Returns the maven output as Reader object
	 */
	Reader performAction(ActionType actionType);
	
	/**
	 * This method configures the maven site report for the application
	 * @param appInfo
	 * @param reportOptions
	 */
	void configureReport(ApplicationInfo appInfo, List<String> reportOptions);
}
