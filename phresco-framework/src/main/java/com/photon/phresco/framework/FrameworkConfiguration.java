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
package com.photon.phresco.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.exception.PhrescoException;

public class FrameworkConfiguration implements FrameworkConstants {

    private Properties frameworkConfig;
    private String configFilePath =  "phresco-env-config.xml";
    private String serverUrl;

    public FrameworkConfiguration(String fileName) throws PhrescoException {
        initFrameworkConfig(fileName);
    }

    private void initFrameworkConfig(String fileName) throws PhrescoException {
        InputStream stream = null;
        stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        frameworkConfig = new Properties();
        try {
            frameworkConfig.load(stream);

        } catch (IOException e) {
            throw new PhrescoException(e);
        }
    }

    private List<Configuration> configurationList(String configType) throws PhrescoException {
		InputStream stream = null;
		stream = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
		try {
			ConfigReader configReader = new ConfigReader(stream);
			String environment = System.getProperty("SERVER_ENVIRONMENT");
			if (environment == null || environment.isEmpty() ) {
				environment = configReader.getDefaultEnvName();
			}
			return configReader.getConfigurations(environment, configType);
		} catch (Exception e) {
		    throw new PhrescoException(e);
		}
	}
    
	public String getServerPath() throws PhrescoException {
		List<Configuration> configurations = configurationList("WebService");
		for (Configuration configuration : configurations) {
			String protocol = configuration.getProperties().getProperty("protocol");
			String host = configuration.getProperties().getProperty("host");
			String port = configuration.getProperties().getProperty("port");
			String context = configuration.getProperties().getProperty("context");
			String additionalContext = configuration.getProperties().getProperty("additional_context");
			serverUrl = protocol + "://" + host + ":" +  port + "/" + context+ additionalContext;
		}
		return serverUrl;
	}
	
	public String getVideoServicePath() throws PhrescoException {
		List<Configuration> configurations = configurationList("WebService");
		for (Configuration configuration : configurations) {
			String protocol = configuration.getProperties().getProperty("protocol");
			String host = configuration.getProperties().getProperty("host");
			String port = configuration.getProperties().getProperty("port");
			String context = configuration.getProperties().getProperty("context");
			serverUrl = protocol + "://" + host + ":" +  port + "/" + context;
		}
		return serverUrl;
	}
    
	public String apiKey() throws PhrescoException {
		List<Configuration> configurations = configurationList("Other");
		String apiKey = null;
		for (Configuration configuration : configurations) {
			apiKey = configuration.getProperties().getProperty("apiKey");
		}
		return apiKey;
	}

    public String getCodePrefix() {
        return frameworkConfig.getProperty(PHRESCO_CODE_PREFIX);
    }

    public String getFuctionalTestSuitePath(){
        return frameworkConfig.getProperty(PHRESCO_FUNCTIONAL_TESTSUITE_PATH);
    }

    public String getReportsPath(){
        return frameworkConfig.getProperty(PHRESCO_REPORTS_PATH);
    }

    public boolean isCacheEnabled(){
        String isCacheStr = frameworkConfig.getProperty(PHRESCO_CACHE_ENABLED);
        return Boolean.parseBoolean(isCacheStr);
    }

    public String getSonarReportPath() {
        return frameworkConfig.getProperty(PHRESCO_SONAR_REPORT_PATH);
    }
    
    public String getSonarUrl() {
        return frameworkConfig.getProperty(PHRESCO_SONAR_URL);
    }

    public String getCIServerPath() {
        return frameworkConfig.getProperty(PHRESCO_CI_JENKINS_URL);
    }
}
