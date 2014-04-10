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
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.plugins.util.MojoProcessor;
import com.photon.phresco.util.Utility;

public class IosDeployValidationImpl implements DynamicParameter {
	private String BUILD_INFO_FILE_NAME = "build.info";
	private String DO_NOT_CHECKIN_DIR = "do_not_checkin";
	private String BUILD = "build";
    private String FALSE = "false";
	private String TRUE = "true";
	
	public PossibleValues getValues(Map<String, Object> paramsMap)
			throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, PhrescoException {
		String rootModulePath = "";
		String subModuleName = "";
		PossibleValues possibleValues = new PossibleValues();
		
		try {
            ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
            String buildNumber = (String) paramsMap.get(KEY_BUILD_NO);
            String rootModule = (String) paramsMap.get(KEY_ROOT_MODULE);
            if (StringUtils.isNotEmpty(rootModule)) {
    			rootModulePath = Utility.getProjectHome() + rootModule;
    			subModuleName = applicationInfo.getAppDirName();
    		} else {
    			rootModulePath = Utility.getProjectHome() + applicationInfo.getAppDirName();
    		}
            
            if (StringUtils.isEmpty(buildNumber)) {
            	throw new PhrescoException("Build number is empty ");
            }
            
            BuildInfo buildInfo = Utility.getBuildInfo(Integer.parseInt(buildNumber), getBuildInfoPath(rootModulePath,subModuleName).toString());
            if (buildInfo == null) {
            	throw new PhrescoException("Build info is not found for build number " + buildNumber);
            }
            
            Map<String, Boolean> options = buildInfo.getOptions();
            if (options != null) {
            	boolean createIpa = MapUtils.getBooleanValue(buildInfo.getOptions(), "canCreateIpa");
            	boolean deviceDeploy = MapUtils.getBooleanValue(buildInfo.getOptions(), "deviceDeploy");
            	
            	if (!createIpa && !deviceDeploy) { // if it is simulator, show popup for following dependency
            		Value value = new Value();
                    value.setValue("simulator");
//                    value.setDependency("sdkVersion,family,logs,buildNumber");
                    possibleValues.getValue().add(value);
                    setShowPropValue(paramsMap, "sdkVersion", true);
                    setShowPropValue(paramsMap, "family", true);
                    setShowPropValue(paramsMap, "logs", true);
                    setShowPropValue(paramsMap, "buildNumber", true);
                    // set trigger simulator value
                    setTriggerSimulatorValue(paramsMap, TRUE);
                    return possibleValues;
            	} else { // if it is device, it should return null and should not show any popup
            		Value value = new Value();
                    value.setValue("device");
                    possibleValues.getValue().add(value);
                    setShowPropValue(paramsMap, "sdkVersion", false);
                    setShowPropValue(paramsMap, "family", false);
                    setShowPropValue(paramsMap, "logs", false);
                    setShowPropValue(paramsMap, "buildNumber", false);
                    // set trigger simulator value
                    setTriggerSimulatorValue(paramsMap, FALSE);
            		return possibleValues;
            	}
            }
            
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return possibleValues;
	}

	private void setTriggerSimulatorValue(Map<String, Object> paramsMap, String triggerSimulator) throws PhrescoException {
    	MojoProcessor mojo = (MojoProcessor) paramsMap.get(KEY_MOJO);
    	String goal = (String) paramsMap.get(KEY_GOAL);
    	Parameter parameter = mojo.getParameter(goal, KEY_TRIGGER_SIMULATOR);
		parameter.setValue(triggerSimulator);
    	mojo.save();
	}
	
	private void setShowPropValue(Map<String, Object> paramsMap, String key, boolean isShow) throws PhrescoException {
    	MojoProcessor mojo = (MojoProcessor) paramsMap.get(KEY_MOJO);
    	String goal = (String) paramsMap.get(KEY_GOAL);
    	Parameter parameter = mojo.getParameter(goal, key);
		parameter.setShow(isShow);
    	mojo.save();
	}
	
	private StringBuilder getBuildInfoPath(String rootModulePath, String subModuleName) throws PhrescoException {
		File pomFileLocation = Utility.getPomFileLocation(rootModulePath, subModuleName);
	    StringBuilder builder = new StringBuilder(pomFileLocation.getParent());
	    builder.append(File.separator);
	    builder.append(DO_NOT_CHECKIN_DIR);
	    builder.append(File.separator);
	    builder.append(BUILD);
	    builder.append(File.separator);
	    builder.append(BUILD_INFO_FILE_NAME);
	    return builder;
	}
}
