package com.photon.phresco.framework.param.impl;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

import com.photon.phresco.api.*;
import com.photon.phresco.exception.*;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.*;
import com.photon.phresco.util.*;
import com.photon.phresco.util.IosSdkUtil.*;

public class IosSimSDKVersionsParameterImpl implements DynamicParameter  {

	public PossibleValues getValues(Map<String, Object> map)
			throws IOException, ParserConfigurationException, SAXException,
			ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		List<String> iphoneSimSdkVersions = IosSdkUtil.getMacSdksVersions(MacSdkType.iphonesimulator);
		for (String iphoneSimSdkVersion : iphoneSimSdkVersions) {
				Value value = new Value();
				value.setValue(iphoneSimSdkVersion);
				possibleValues.getValue().add(value);
		}
		return possibleValues;
	}

}
