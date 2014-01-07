package com.photon.phresco.framework.api;

import java.util.List;

public interface RepositoryManager {

	List<String> getSource(String customerId, String projectId, String username, String password, String srcRepoUrl);
	
	
	
}
