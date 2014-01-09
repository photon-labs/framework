package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.exception.PhrescoException;

public interface RepositoryManager {

	List<String> getSource(String customerId, String projectId, String username, String password, String srcRepoUrl) throws PhrescoException  ;
	
	
}
