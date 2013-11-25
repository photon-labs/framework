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

public class DynamicDataBaseImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap) throws PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		try {
			ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
			String envName = (String) paramMap.get(KEY_ENVIRONMENT);
			boolean isMultiModule = (Boolean) paramMap.get(KEY_MULTI_MODULE);
        	String rootModule = (String) paramMap.get(KEY_ROOT_MODULE);
        	String projectCode = (String) paramMap.get(KEY_PROJECT_CODE);
			//To search for db type in settings.xml
			String settingsPath = getSettingsPath(projectCode);
			ConfigManager configManager = new ConfigManagerImpl(new File(settingsPath)); 
			List<Configuration> configurations = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
			if(CollectionUtils.isNotEmpty(configurations)) {
			for (Configuration configuration : configurations) {
				Value value = new Value();
				value.setValue(configuration.getProperties().getProperty("type").toLowerCase());
				possibleValues.getValue().add(value);
			}
		}
			
			//To search for db type in phresco-env-config.xml if it doesn't exist in settings.xml
			if (CollectionUtils.isEmpty(possibleValues.getValue())) {
				String appDirectory = applicationInfo.getAppDirName();
				String configPath = getConfigurationPath(appDirectory, isMultiModule, rootModule).toString();
				configManager = new ConfigManagerImpl(new File(configPath)); 
				configurations = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
				if(CollectionUtils.isNotEmpty(configurations)) {
				for (Configuration configuration : configurations) {
					String dbType = configuration.getProperties().getProperty("type");
					List<Value> availableValues = possibleValues.getValue();
					boolean alreadyAvailable = false;
					if (CollectionUtils.isNotEmpty(availableValues)) {
						for (Value availableValue : availableValues) {
							if (availableValue.getValue().equalsIgnoreCase(dbType)) {
								alreadyAvailable = true;
								break;
							}
						}
					}
					if (!alreadyAvailable) {
						Value value = new Value();
			    		value.setValue(dbType.toLowerCase());
			    		possibleValues.getValue().add(value);
					}
				}
			}
			}
		} catch (ConfigurationException e) {
			throw new PhrescoException(e);
		}
		return possibleValues;
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
    
    private String getSettingsPath(String projectCode) {
    	return Utility.getProjectHome() + projectCode + "-settings.xml";
    }
}
