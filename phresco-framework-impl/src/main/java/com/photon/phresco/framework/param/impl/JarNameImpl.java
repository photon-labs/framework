package com.photon.phresco.framework.param.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class JarNameImpl implements DynamicParameter, Constants {

	@Override
	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		try {
			ApplicationInfo applicationInfo = (ApplicationInfo) map.get(KEY_APP_INFO);
			String pomPath = Utility.getProjectHome() + File.separator + applicationInfo.getAppDirName() + File.separator + FrameworkConstants.POM_FILE;
			PomProcessor pomProcessor = new PomProcessor(new File(pomPath));
			String finalName = pomProcessor.getFinalName();
			Value value = new Value();
			value.setValue(finalName);
    		possibleValues.getValue().add(value);
		} catch (PhrescoPomException e) {
		}
		
		return possibleValues;
	}
}
