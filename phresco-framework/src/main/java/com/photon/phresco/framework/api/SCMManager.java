/**
 * Phresco Framework
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.framework.api;

import java.io.File;

import com.photon.phresco.commons.model.ApplicationInfo;

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
	ApplicationInfo importProject(String type, String url, String username,
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
			String password, String branch,  String revision, ApplicationInfo appInfo) throws Exception ;
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @param dir
	 * @param commit message
	 * @throws Exception 
	 
	 */
	boolean importToRepo(String type, String url, String username,
			String password, String branch, String revision, ApplicationInfo appInfo, String commitMsg) throws Exception ;
	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param branch
	 * @param dir
	 * @param commit message
	 * @throws Exception 
	 
	 */
	boolean commitToRepo(String type, String url, String username,
			String password, String branch, String revision, File dir, String commitMsg) throws Exception ;

}
