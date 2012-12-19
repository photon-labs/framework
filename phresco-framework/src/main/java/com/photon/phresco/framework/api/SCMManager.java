package com.photon.phresco.framework.api;

import java.io.*;

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
			String password, String branch, String revision) throws Exception ;

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
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @throws Exception 
	 
	 */
	boolean importToRepo(String type, String url, String username,
			String password, String branch, String revision, File dir, String commitMsg) throws Exception ;

}
