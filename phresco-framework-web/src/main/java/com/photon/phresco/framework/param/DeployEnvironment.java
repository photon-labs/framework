package com.photon.phresco.framework.param;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Utility;

public class DeployEnvironment implements DynamicParameter, FrameworkConstants {
	public Map<Object, Object> dynamicParamMap = new HashMap<Object, Object>();
	
	@Override
	public PossibleValues getValues(Map<String, Object> deployEnvMap) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		ApplicationManager applicationManager = PhrescoFrameworkFactory.getApplicationManager();
		ApplicationInfo applicationInfo = (ApplicationInfo) deployEnvMap.get(KEY_APP_INFO);
		String buildNumber = (String) deployEnvMap.get(KEY_BUILD_NO);
		String buildInfoFileDirectory = getBuildInfosFilePath(applicationInfo);
		BuildInfo buildInfo = applicationManager.getBuildInfo(Integer.parseInt(buildNumber), buildInfoFileDirectory);
		
		List<String> environments = buildInfo.getEnvironments();
		for (String environment : environments) {
			Value value = new Value();
    		value.setValue(environment);
    		possibleValues.getValue().add(value);
		}

		return possibleValues;
	}
	
	private String getBuildInfosFilePath(ApplicationInfo applicationInfo) {
    	return getAppDirectoryPath(applicationInfo) + FILE_SEPARATOR + BUILD_DIR + FILE_SEPARATOR +BUILD_INFO_FILE_NAME;
    }
	
	private String getAppDirectoryPath(ApplicationInfo applicationInfo) {
    	return Utility.getProjectHome() + applicationInfo.getAppDirName();
    }
}
