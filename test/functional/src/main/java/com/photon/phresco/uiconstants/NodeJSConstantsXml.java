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

public class NodeJSConstantsXml {
	private ReadXMLFile readXml; 
	
	public String NODEJS_PROJECT_CREATION_ID = "createdNodeJSProject";
	public String NODEJS_PROJET_NAME = "appInfoNodeJSNameValue";
	public String NODEJS_PROJET_NONE_NAME = "appInfoNodeJSNoneNameValue";
	public String NODEJS_PROJECT_DESCRIPTION = "appInfoNodeJSDescValue";
	public String NODEJS_PILOTPROJ_ESHOPWS = "appInfoTechEshopWebServ";
	public String NODEJS_SELECT_TECHNOLOGY = "appInfoTechWebServNodeJS";
	public String NODEJS_CONFIG_SERVER_NAME = "appInfoNodeJSServNameValue";
	public String NODEJS_CONFIG_SERVER_DESCRIPTION = "NodeJSServerDescValue";
	public String NODEJS_CONFIG_SERVER_HOST = "NodeJSServerHostValue";
	public String NODEJS_NONE_CONFIG_SERVER_PORT = "NodeJSServNoneServerPortValue";
	public String NODEJS_ESHOP_CONFIG_SERVER_PORT = "NodeJSServEshopServerPortValue";
	public String NODEJS_CONFIG_SERVER_DEPLOY_DIR = "NodeJSServerDeployDirValue";
	public String NODEJS_NONE_CONFIG_SERVER_CONTEXT_NAME = "NodeJSServerNoneContextValue";
	public String NODEJS_ESHOP_CONFIG_SERVER_CONTEXT_NAME = "NodeJSServerEshopContextValue";
	public String NODEJS_CONFIG_DB_NAME = "NodeJSDbNameValue";
	public String NODEJS_CONFIG_DB_DESCRIPTION = "NodeJSDbDescValue";
	public String NODEJS_CONFIG_DB_HOST = "NodeJSDbHostValue";
	public String NODEJS_CONFIG_DB_PORT = "NodeJSDbPortValue";
	public String NODEJS_CONFIG_DB_USERNAME = "NodeJSDbUserNameValue";
	public String NODEJS_CONFIG_DB_DBNAME = "NodeJSDbNameValue";
	public String NODEJS_BUILD_RUNAGAINSTSRC_BTN = "NodeJSBuildRunAgainstSrcButton";
	public String NODEJS_BUILD_RUN_BTN = "NodeJSBuildRunButton";
	public String NODEJS_BUILD_STOP_BTN = "NodeJSBuildStopButton";
	public String NODEJS_CONFIG_CHOOSE_SERVER_NAME="NodeJSBuildGenBuildNodeJSServer";
	public String NODEJS_CONFIG_CHOOSE_DB_NAME="NodeJSBuildGenBuildNodeJSDb";
	public String NODEJS_GENERATE_BUILD_SERVER_CLICK="NodeJSBuildGenBuildNodeJSServerClick";
	public String NODEJS_GENERATE_BUILD_DB_CLICK="NodeJSBuildGenBuildNodeJSDbClick";
	public String NODEJS_GENERATE_BUILD_DOWNLOAD="NodeJSBuildGentdBuildDownload";
	public String NODEJS_PROJECT_CREATION_NONE ="createdNodeJSProjectNone";
	public String CONFIGURATIONS_NODEJS_SERVER_USERNAME_VALUE= "NodeJSServServerUsernameValue";
	public String CONFIGURATIONS_NODEJS_SERVER_PASSWORD_VALUE= "NodeJSServServerPasswordValue";
	
	public NodeJSConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			readXml.loadNodejsConstants();
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
