/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class DynamicallyFetchDependencyJarsImpl implements DynamicParameter,
		Constants {

	private static final long serialVersionUID = 1L;

	public PossibleValues getValues(Map<String, Object> paramMap)
			throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		ApplicationInfo applicationInfo = (ApplicationInfo) paramMap
				.get(KEY_APP_INFO);
		boolean isMultiModule = (Boolean) paramMap.get(KEY_MULTI_MODULE);
    	String rootModule = (String) paramMap.get(KEY_ROOT_MODULE);
		List<String> projectDependencies = getProjectDependencies(applicationInfo
				.getAppDirName(), isMultiModule, rootModule);
		if (projectDependencies != null) {
			for (String module : projectDependencies) {
				Value value = new Value();
				value.setValue(module);
				possibleValues.getValue().add(value);
			}
		}
		return possibleValues;
	}

	private StringBuilder getDependencyJarPath(String appDirName, boolean isMultiModule, String rootModule) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		if (isMultiModule) {
			builder.append(rootModule).append(File.separator);
		}
		builder.append(appDirName);
		builder.append(File.separator);
		builder.append("do_not_checkin");
		builder.append(File.separator);
		builder.append("dependencyJars");
		return builder;
	}

	protected List<String> getProjectDependencies(String appDirName, boolean isMultiModule, String rootModule)
			throws PhrescoException {
		try {
			StringBuilder builder = getDependencyJarPath(appDirName, isMultiModule, rootModule);
			File dir = new File(builder.toString());
			List<String> fileList = new ArrayList<String>();
			if (dir.exists()) {
				for (File nextFile : dir.listFiles()) {
					fileList.add(nextFile.getName());
				}
			}
			return fileList;
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

}
