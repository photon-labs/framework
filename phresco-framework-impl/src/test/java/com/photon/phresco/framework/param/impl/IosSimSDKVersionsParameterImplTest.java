package com.photon.phresco.framework.param.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.api.DynamicParameter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration.Parameters.Parameter.PossibleValues;

public class IosSimSDKVersionsParameterImplTest {
	private static final String MAC = "mac";
	private static final String OS_NAME = "os.name";
	boolean isMacOS;
	DynamicParameter dynamicParameter = null;
	
	@Before
	public void setUp() throws Exception {
		String osType = System.getProperty(OS_NAME).toLowerCase();
		isMacOS = osType.indexOf(MAC) >= 0;
		dynamicParameter = new IosSimSDKVersionsParameterImpl();
	}

	@After
	public void tearDown() throws Exception {
		isMacOS = false;
		if (dynamicParameter != null) {
			dynamicParameter = null;
		} 
	}

	@Test
	public void testGetSimulatorSDKValues() throws PhrescoException {
		try {
			Map<String, Object> map = null;
			PossibleValues values = dynamicParameter.getValues(map);
			if (isMacOS) {
				Assert.assertTrue(values.getValue().size() > 0);
			} else {
				Assert.assertTrue(values.getValue().size() == 0);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

}
