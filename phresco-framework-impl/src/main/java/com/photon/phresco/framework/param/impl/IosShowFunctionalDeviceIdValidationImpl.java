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

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.apache.commons.collections.*;
import org.sonatype.aether.util.*;
import org.xml.sax.*;

import com.photon.phresco.api.*;
import com.photon.phresco.commons.model.*;
import com.photon.phresco.exception.*;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.*;
import com.photon.phresco.util.*;

public class IosShowFunctionalDeviceIdValidationImpl implements DynamicParameter {
	private static final String DEVICE_DEPLOY = "deviceDeploy";
	private static final String CAN_CREATE_IPA = "canCreateIpa";
	private static final String DEVICE_ID = "deviceId";
	private String BUILD_INFO_FILE_NAME = "build.info";
	private String DO_NOT_CHECKIN_DIR = "do_not_checkin";
	private String BUILD = "build";
	
    @Override
    public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
        PossibleValues possibleValues = new PossibleValues();
        ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
        String buildInfoPath = getBuildInfoPath(applicationInfo.getAppDirName()).toString();
        List<BuildInfo> buildInfos = Utility.getBuildInfos(new File(buildInfoPath));
        if (buildInfos != null) {
            for (BuildInfo buildInfo : buildInfos) {
                Value value = new Value();
                value.setValue(Integer.toString(buildInfo.getBuildNo()));
                String dependency = getDependency(buildInfo.getBuildNo(), applicationInfo.getAppDirName());
                if (!StringUtils.isEmpty(dependency)) {
                	value.setDependency(dependency);
                }
                possibleValues.getValue().add(value);
            }
        }
        return possibleValues;
    }
    
	private String getDependency(int buildNumber, String appDirName) throws PhrescoException {
		try {
            BuildInfo buildInfo = Utility.getBuildInfo(buildNumber, getBuildInfoPath(appDirName).toString());
            if (buildInfo == null) {
            	throw new PhrescoException("Build info is not found for build number " + buildNumber);
            }
            
            Map<String, Boolean> options = buildInfo.getOptions();
            if (options != null) {
            	boolean createIpa = MapUtils.getBooleanValue(buildInfo.getOptions(), CAN_CREATE_IPA);
            	boolean deviceDeploy = MapUtils.getBooleanValue(buildInfo.getOptions(), DEVICE_DEPLOY);
            	
            	if (!createIpa && !deviceDeploy) { // if it is simulator, show popup for following dependency
                    return "";
            	} else { // if it is device, it should return null and should not show any popup
            		return DEVICE_ID;
            	}
            }
            
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return "";
	}

	private StringBuilder getBuildInfoPath(String projectDirectory) {
	    StringBuilder builder = new StringBuilder(Utility.getProjectHome());
	    builder.append(projectDirectory);
	    builder.append(File.separator);
	    builder.append(DO_NOT_CHECKIN_DIR);
	    builder.append(File.separator);
	    builder.append(BUILD);
	    builder.append(File.separator);
	    builder.append(BUILD_INFO_FILE_NAME);
	    return builder;
	}
}
