package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;

public interface ProjectManager {

	/**
	 * This method provides the list of projects available in the local phresco workspace for the given customerId
	 * @param CustomerId
	 * @return Returns list of ProjectInfos
	 * @throws PhrescoException
	 */
	List<ProjectInfo> discover(String customerId) throws PhrescoException;
	
	/**
	 * This method returns the Project for the given projectId
	 * @param projectId
	 * @return Returns the ProjectInfo with all the ApplicationInfos
	 * @throws PhrescoException
	 */
	ProjectInfo getProject(String projectId) throws PhrescoException;
	
	/**
	 * This method creates the project with the selected application layers
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo create(ProjectInfo projectInfo) throws PhrescoException;
	
	/**
	 * This method updates the project
	 * @param ProjectInfo
	 * @return ProjectInfo
	 * @throws PhrescoException
	 */
	ProjectInfo update(ProjectInfo projectInfo) throws PhrescoException;
	
	/**
	 * This method deletes the project in local filesystem and not is server
	 * @param ProjectInfo
	 * @return boolean - returns true if deletion is success and false if deletion fails.
	 * @throws PhrescoException
	 */
	boolean delete(ProjectInfo projectInfo) throws PhrescoException;
}
