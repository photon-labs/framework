package com.photon.phresco.framework.param.impl;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.ConfigurationException;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues.Value;
import com.photon.phresco.util.IosSdkUtil;
import com.photon.phresco.util.IosSdkUtil.MacSdkType;

public class IosSdkParameterImpl implements DynamicParameter {

	public PossibleValues getValues(Map<String, Object> map) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, PhrescoException {
		PossibleValues possibleValues = new PossibleValues();
		List<String> iphoneSdks = IosSdkUtil.getMacSdks(MacSdkType.iphoneos);
		iphoneSdks.addAll(IosSdkUtil.getMacSdks(MacSdkType.iphonesimulator));
		iphoneSdks.addAll(IosSdkUtil.getMacSdks(MacSdkType.macosx));
		for (String iphoneSdkValue : iphoneSdks) {
				Value value = new Value();
				value.setValue(iphoneSdkValue);
				possibleValues.getValue().add(value);
		}
		
		return possibleValues;
	}

}

