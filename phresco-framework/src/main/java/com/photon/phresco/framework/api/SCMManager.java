package com.photon.phresco.framework.api;

import com.photon.phresco.exception.*;

public interface SCMManager {

	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws PhrescoException
	 */
	void importProject(String type, String url, String username,
			String password, String branch) throws PhrescoException;

	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws PhrescoException
	 */
	void updateProject(String type, String url, String username,
			String password, String branch) throws PhrescoException;

}
