/**
 * Phresco Framework Implementation
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.impl.ConfigManagerImpl;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class DynamicFetchSqlImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> map) throws PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		try {
			String rootModulePath = "";
			String subModuleName = "";
			ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
        	String rootModule = (String) map.get(KEY_ROOT_MODULE);
			String envName = (String) map.get(KEY_ENVIRONMENT);
			String dbname = (String) map.get(KEY_DATABASE);
			String projectCode = (String) map.get(KEY_PROJECT_CODE);
		 	if (StringUtils.isNotEmpty(rootModule)) {
    			rootModulePath = Utility.getProjectHome() + rootModule;
    			subModuleName = applicationInfo.getAppDirName();
    		} else {
    			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
    		}
			String sqlFilePath = getSqlFilePath(rootModulePath, subModuleName);
        	String configPath = getConfigurationPath(rootModulePath, subModuleName).toString();
			String settingsPath = getSettingsPath(projectCode);
			ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
			List<Configuration> settingsconfig = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
			if(CollectionUtils.isNotEmpty(settingsconfig)) {
				fetchSqlFilePath(possibleValues, applicationInfo, sqlFilePath, dbname, settingsconfig, rootModulePath, subModuleName);
			}
			configManager = new ConfigManagerImpl(new File(configPath));
			List<Configuration> configurations = configManager.getConfigurations(envName, SETTINGS_TEMPLATE_DB);
			if(CollectionUtils.isNotEmpty(configurations)) {
				fetchSqlFilePath(possibleValues, applicationInfo, sqlFilePath, dbname, configurations, rootModulePath, subModuleName);
			}
			return possibleValues;

		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	private void fetchSqlFilePath(PossibleValues possibleValues, ApplicationInfo applicationInfo, String sqlFilePath,
			String dbname, List<Configuration> configurations, String rootModulePath, String subModuleName) throws PhrescoException {
		String dbVersion = "";
		String sqlFileName = "";
		String path = "";
		for (Configuration configuration : configurations) {
			String dbType = configuration.getProperties().getProperty(DB_TYPE).toLowerCase();
			if (dbType.equals(dbname)) { 
				dbVersion =configuration.getProperties().getProperty(DB_VERSION);
				ProjectInfo info = Utility.getProjectInfo(rootModulePath, subModuleName);
				File srcFolderLocation = Utility.getSourceFolderLocation(info, rootModulePath, subModuleName);
				StringBuilder sb = new StringBuilder(srcFolderLocation.toString());
				sb.append(File.separator).append(sqlFilePath).append(dbname)
				.append(File.separator).append(dbVersion);
				
				File[] dbSqlFiles = new File(sb.toString()).listFiles(new DumpFileNameFilter());
				for (int i = 0; i < dbSqlFiles.length; i++) {
					if (!dbSqlFiles[i].isDirectory()) {
					Value value = new Value();
					sqlFileName = dbSqlFiles[i].getName();
					path = sqlFilePath + dbname + "/" +  dbVersion + "/" + sqlFileName;
					value.setKey(dbname);
					value.setValue(path);
		    		possibleValues.getValue().add(value);
				}
			  }
			}
		}
	}
	
	class DumpFileNameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			return !(name.startsWith("."));
		}
	}
	
	private StringBuilder getConfigurationPath(String rootModulePath, String subModuleName) throws PhrescoException {
		String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
		StringBuilder builder = new StringBuilder(dotPhrescoFolderPath);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE);
		 
		 return builder;
	 }
	
	private String getSqlFilePath(String rootModulePath, String subModuleName) throws PhrescoPomException, PhrescoException {
		File pomFileLocation = Utility.getPomFileLocation(rootModulePath, subModuleName);
		PomProcessor processor = new PomProcessor(pomFileLocation);
		String sqlFilePath = processor.getProperty(POM_PROP_KEY_SQL_FILE_DIR);

		return sqlFilePath;
	}
	
	 private String getSettingsPath(String projectCode) {
	    	return Utility.getProjectHome() + projectCode + Constants.SETTINGS_XML;
	    }
}
