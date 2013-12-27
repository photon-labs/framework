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
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

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

public class EnvironmentDataBasesImpl implements DynamicParameter, Constants {

	private static final long serialVersionUID = 1L;

	@Override
	public PossibleValues getValues(Map<String, Object> paramMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		String rootModulePath = "";
		String subModuleName = "";
		PossibleValues possibleValues = new PossibleValues();
    	ApplicationInfo applicationInfo = (ApplicationInfo) paramMap.get(KEY_APP_INFO);
    	String rootModule = (String) paramMap.get(KEY_ROOT_MODULE);
    	String envName = (String) paramMap.get(KEY_ENVIRONMENT);
    	String projectCode = (String) paramMap.get(KEY_PROJECT_CODE);
    	if (StringUtils.isNotEmpty(rootModule)) {
			rootModulePath = Utility.getProjectHome() + rootModule;
			subModuleName = applicationInfo.getAppDirName();
		} else {
			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
		}
    	//To search for db type in settings.xml
    	ConfigManager configManager = new ConfigManagerImpl(new File(getSettingsPath(projectCode))); 
    	List<Configuration> configurations = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
    	for (Configuration configuration : configurations) {
    	    Value value = new Value();
            value.setValue(configuration.getName());
            possibleValues.getValue().add(value);
		}
    	
    	//To search for db type in phresco-env-config.xml if it doesn't exist in settings.xml
    	if (CollectionUtils.isEmpty(possibleValues.getValue())) {
    		String configPath = getConfigurationPath(rootModulePath, subModuleName).toString();
        	configManager = new ConfigManagerImpl(new File(configPath)); 
        	configurations = configManager.getConfigurations(envName, Constants.SETTINGS_TEMPLATE_DB);
        	for (Configuration configuration : configurations) {
        	    Value value = new Value();
        	    value.setValue(configuration.getName());
        	    possibleValues.getValue().add(value);
    		}
    	}
    	
    	return possibleValues;
    }
    
    private StringBuilder getConfigurationPath(String rootModulePath, String subModuleName) throws PhrescoException {
    	String dotPhrescoFolderPath = Utility.getDotPhrescoFolderPath(rootModulePath, subModuleName);
		 StringBuilder builder = new StringBuilder(dotPhrescoFolderPath);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE);
		 
		 return builder;
	 }
    
    private String getSettingsPath(String projectCode) {
    	return Utility.getProjectHome() + projectCode +  SETTINGS_XML;
    }
}
