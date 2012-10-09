package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.commons.model.ProjectInfo;

public interface ProjectManager {

	/**
	 * This method provides the list of projects available in the local phresco workspace for the given customerId
	 * @param CustomerId
	 * @return Returns list of ProjectInfos
	 */
	List<ProjectInfo> discover(String CustomerId);
	
	/**
	 * This method returns the Project for the given projectId
	 * @param projectId
	 * @return Returns the ProjectInfo with all the ApplicationInfos
	 */
	ProjectInfo getProject(String projectId);
	
	/**
	 * This method creates the project with the selected application layers
	 * @param ProjectInfo
	 * @return ProjectInfo
	 */
	ProjectInfo create(ProjectInfo ProjectInfo);
	
	/**
	 * This method updates the project
	 * @param ProjectInfo
	 * @return ProjectInfo
	 */
	ProjectInfo update(ProjectInfo ProjectInfo);
	
	/**
	 * This method deletes the project in local filesystem and not is server
	 * @param ProjectInfo
	 * @return boolean - returns true if deletion is success and false if deletion fails.
	 */
	boolean delete(ProjectInfo ProjectInfo);
}
