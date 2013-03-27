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

