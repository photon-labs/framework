package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class PhrescoEnvConstants {
	public ReadXMLFile readXml;
	public String HOST = "host";
	public String CONTEXT = "context";
	public String PROTOCOL = "protocol";
	public String BROWSER = "Browser";
	public String SPEED = "speed";
	public String PORT = "port";
	
	
	public PhrescoEnvConstants() {
		
		Field localField = null;
		try {
			readXml = new ReadXMLFile();
			readXml.loadPhrescoEnvConstansts();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				 localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed for the constant--->" + localField.getName(),
					localException);
		}
	}
}
