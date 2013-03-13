package com.photon.phresco.framework.param.impl;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;

public class ProjectTypeImpl implements DynamicParameter {

	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		Value value = new Value();
		value.setValue("mac");
		possibleValues.getValue().add(value);

		return possibleValues;
	}

}

