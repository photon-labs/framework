package com.photon.phresco.framework.api;

public interface SCMManager {

	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws Exception 
	 
	 */
	boolean importProject(String type, String url, String username,
			String password, String branch, String revision, String projcode) throws Exception ;

	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws Exception 
	
	 */
	boolean updateProject(String type, String url, String username,
			String password, String branch,  String revision, String projcode) throws Exception ;

}
