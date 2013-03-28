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

public class SharepointConstantsXml {
	private ReadXMLFile readXml;
	
	public String SHAREPOINT = "appInfoTechWebappSharepoint";
	public String CREATEDPROJECT_SHAREPOINT = "sharepointProj";
	public String SHAREPOINT_RESOURCEMNGMT = "appInfoSharepointResourceMngmt";
	public String MYSHAREPOINTSERVER = "sharepointBuildGenBuildSPServer";
	public String MYSHAREPOINTSERVER_CLICK = "sharepointBuildGenBuildSPServerClick";
	public String APPINFO_SHAREPOINT_NAME_VALUE = "appInfoSharepointNameValue";
	public String APPINFO_SHAREPOINT_NONE_NAME_VALUE = "appInfoSharepointNoneNameValue";
	public String APPINFO_SHAREPOINT_DESCRIPTION_VALUE = "appInfoSharepointDescValue";
	public String SHAREPOINT_SERVER_CONFIGURATION_NAME_VALUE = "sharepointServerNameValue";
	public String SHAREPOINT_SERVER_CONFIGURATION_DESCRIPTION_VALUE = "sharepointServerDescValue";
	public String SHAREPOINT_SETTINGS_SERVER_TYPE_VALUE = "sharepointServerTypeValue";
	public String SHAREPOINT_SETTINGS_SERVER_CONTEXT_VALUE = "sharepointServerContextValue";
	public String SHAREPOINT_SETTINGS_SERVER_DEPLOYDIR_VALUE = "sharepointServerDeployDirValue";
	public String SHAREPOINT_SETTINGS_PORT_VALUE = "sharepointServerPortValue";
	public String SHAREPOINT_SETTINGS_HOST_VALUE = "sharepointServerHostValue";
	public String CREATEDPROJECT_SHAREPOINT_NONE = "createdsharepointProjNone";
	
	public SharepointConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadSharepointConstants();
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
