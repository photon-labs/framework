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
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.photon.phresco.api.ConfigManager;
import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
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
			ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
			boolean isMultiModule = (Boolean) map.get(KEY_MULTI_MODULE);
        	String rootModule = (String) map.get(KEY_ROOT_MODULE);
			String sqlFilePath = getSqlFilePath(applicationInfo, isMultiModule, rootModule);
			String appDirectory = applicationInfo.getAppDirName();
			String envName = (String) map.get(KEY_ENVIRONMENT);
			String dbname = (String) map.get(KEY_DATABASE);
			String projectCode = (String) map.get(KEY_PROJECT_CODE);
        	String configPath = getConfigurationPath(appDirectory, isMultiModule, rootModule).toString();
			String settingsPath = getSettingsPath(projectCode);
			ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
			List<Configuration> settingsconfig = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
			if(CollectionUtils.isNotEmpty(settingsconfig)) {
				fetchSqlFilePath(possibleValues, applicationInfo, sqlFilePath, dbname, settingsconfig, isMultiModule, rootModule);
			}
			configManager = new ConfigManagerImpl(new File(configPath));
			List<Configuration> configurations = configManager.getConfigurations(envName, SETTINGS_TEMPLATE_DB);
			if(CollectionUtils.isNotEmpty(configurations)) {
				fetchSqlFilePath(possibleValues, applicationInfo, sqlFilePath, dbname, configurations, isMultiModule, rootModule);
			}
			return possibleValues;

		} catch (PhrescoPomException e) {
			throw new PhrescoException(e);
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
	}

	private void fetchSqlFilePath(PossibleValues possibleValues, ApplicationInfo applicationInfo, String sqlFilePath,
			String dbname, List<Configuration> configurations, boolean isMultiModule, String rootModule) {
		String dbVersion = "";
		String sqlFileName = "";
		String path = "";
		for (Configuration configuration : configurations) {
			String dbType = configuration.getProperties().getProperty(DB_TYPE).toLowerCase();
			if (dbType.equals(dbname)) { 
				dbVersion =configuration.getProperties().getProperty(DB_VERSION);
				StringBuilder sb = new StringBuilder(Utility.getProjectHome());
				if (isMultiModule) {
					sb.append(rootModule).append(File.separator);
				}
				sb.append(applicationInfo.getAppDirName()).append(sqlFilePath).append(dbname)
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
	
	private StringBuilder getConfigurationPath(String projectDirectory, boolean isMultiModule, String rootModule) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 if (isMultiModule) {
			 builder.append(rootModule).append(File.separator);
		 }
		 builder.append(projectDirectory);
		 builder.append(File.separator);
		 builder.append(DOT_PHRESCO_FOLDER);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE);
		 
		 return builder;
	 }
	
	private String getSqlFilePath(ApplicationInfo applicationInfo, boolean isMultiModule, String rootModule) throws PhrescoPomException {
		StringBuilder pom = new StringBuilder(Utility.getProjectHome());
		if (isMultiModule) {
			pom.append(rootModule).append(File.separator);
		}
		pom.append(applicationInfo.getAppDirName()).append(File.separator)
		.append(Utility.getPhrescoPomFile(applicationInfo));
		PomProcessor processor = new PomProcessor(new File(pom.toString()));
		String sqlFilePath = processor.getProperty(POM_PROP_KEY_SQL_FILE_DIR);

		return sqlFilePath;
	}
	
	 private String getSettingsPath(String projectCode) {
	    	return Utility.getProjectHome() + projectCode + Constants.SETTINGS_XML;
	    }
}
