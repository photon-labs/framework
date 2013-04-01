package com.photon.phresco.framework.param.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.Technology;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Constants;

public class TechnologyVersionImpl implements DynamicParameter, Constants {

	public PossibleValues getValues(Map<String, Object> paramsMap) throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		ApplicationInfo applicationInfo = (ApplicationInfo) paramsMap.get(KEY_APP_INFO);
		ServiceManager serviceManager = (ServiceManager) paramsMap.get(REQ_SERVICE_MANAGER);
		
         String techId = applicationInfo.getTechInfo().getId();
         Technology technology = serviceManager.getTechnology(techId);
         List<String> techVersions = technology.getTechVersions();
         for (String techVersion : techVersions) {
			Value value = new Value();
			value.setValue(techVersion);
			possibleValues.getValue().add(value);
		}
		
		return possibleValues;
	}

}
