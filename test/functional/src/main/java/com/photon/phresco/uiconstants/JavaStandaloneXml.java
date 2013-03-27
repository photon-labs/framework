/**
 * Phresco Framework Root
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
