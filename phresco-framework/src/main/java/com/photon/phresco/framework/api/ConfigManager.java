/*
 * ###
 * Phresco Framework
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;


public interface ConfigManager {
	
	/**
	 * Returns the list of environments with the names provided
	 * @param names names of the environments to be returned
	 * @return returns the environments with the matching names
	 */
	List<Environment> getEnvironments(List<String> names) throws PhrescoException;
	
	/**
	 * Adds the environments to the existing list of environments
	 * @param environments
	 * @throws PhrescoException
	 */
	void addEnvironments(List<Environment> environments) throws PhrescoException;

	/**
	 * Updates the environment
	 * @param environment
	 * @throws PhrescoException
	 */
	void updateEnvironment(Environment environment) throws PhrescoException;
	
	/**
	 * Deletes the environment provided by the name
	 * @param envName
	 * @throws PhrescoException
	 */
	void deleteEnvironment(String envName) throws PhrescoException;

}
