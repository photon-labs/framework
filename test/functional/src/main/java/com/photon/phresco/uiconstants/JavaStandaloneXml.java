package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class JavaStandaloneXml {
	private ReadXMLFile readXml; 
	
	public   String APPINFO_JAVASTANDALONE_NONEPROJ_NAME_VALUE="appInfoJavaStandaloneNoneNameValue";
	public   String APPINFO_JAVASTANDALONE_DESCRIPTION_VALUE="appInfoJavaStandaloneDescValue"; 
	public   String CREATEDPROJECT_JAVASTANDALONE = "createdJavaStandaloneProject";
	public   String WEBAPP_JAVASTANDALONE_CLICK = "appInfoTechWebappjavaStandaloneClick";

	
	public JavaStandaloneXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadJavaStandaloneConstants();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
	}
	
}
